package dbo;

import java.io.*;
import java.util.*;
import java.sql.*;
import java.text.*;
//import com.axiom.util.DbPool;

public class DataSource
{
    private static Map sourcemap = new HashMap();
    private static String conf = null;
    private static Handler handler = null;
    
    public static void setup(String configfile)
        throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(
            new FileInputStream(configfile)));
        String line;
        while((line=br.readLine())!=null)
        {
            if (line.trim().length()==0)
                continue;
            String[] tokens = line.split("=");
            String ds = tokens[0];
            String[] jdbc_args = tokens[1].split(",");
            int poolsize = (jdbc_args.length>4)?Integer.parseInt(jdbc_args[4]):5;
            
            DbConnectionPool p = new DbConnectionPool(
                jdbc_args[0], jdbc_args[1], jdbc_args[2], jdbc_args[3], poolsize);
            System.out.println("ds=" + ds);
            sourcemap.put(ds, p);    
        }
        conf = configfile;
        if (handler!=null)
        	handler.stop = true;
        handler = new Handler();
        new Thread(handler).start();
    }

	/*
    private static String legacy_driver = null;
    private static String legacy_url = null;
    private static String legacy_user = null;
    private static String legacy_password = null;
    private static beans.jdbc.DbConnectionPool legacy_pool;
	*/
	/*
    public static void setupLegacy(String driver, String url, String user, String password)
        throws Exception
    {
        legacy_driver = driver;
        legacy_url = url;
        legacy_user = user;
        legacy_password = password;

        legacy_pool = new beans.jdbc.DbConnectionPool(legacy_driver, legacy_url, 
            legacy_user, legacy_password);
        DbPool.setDbPool(legacy_pool); 
    }
	*/

    public static void resetDbConns()
        throws Exception
    {
        System.out.println("[" + sdf.format(new java.util.Date())+ "] resetting db connections");
        cleanPool();
        //legacy_pool.closePool();
        setup(conf);
        //setupLegacy(legacy_driver, legacy_url, legacy_user, legacy_password);
    }

    public static boolean hasSource(String ds)
    {
        DbConnectionPool p = (DbConnectionPool)sourcemap.get(ds);
        return p!=null;
    }

    public static Connection getDbConnection(String ds)
        throws Exception
    {
        DbConnectionPool p = (DbConnectionPool)sourcemap.get(ds);
        Connection con;
        return (Connection)p.getConnection(5000);
    }
    
    public static void releaseDbConnection(String ds, Connection con)
    {
        try {
            DbConnectionPool p = (DbConnectionPool) sourcemap.get(ds);
            p.recycleConnection(con);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }   


    public static void kickoutDbConnection(String ds, Connection con)
    {
        try {
            DbConnectionPool p = (DbConnectionPool) sourcemap.get(ds);
            p.disconnect(con);
            p.kickout(con);
            System.out.println("## kicking out one bad db connection, remaining " + p.countConnections());
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }   

    public static void shutdown()
    {
    	cleanPool();
    	handler.stop = true;
    }
    
    public static void reset(boolean do_reset) 
    {
    	handler.no_reset = !do_reset;
    }

    public static void cleanPool()
    {
        try {
            Set s = sourcemap.keySet();
            Iterator iter = s.iterator();
            while (iter.hasNext())
            {
                String k = (String) iter.next();
                System.out.println("shuting datasource [" + k + "]");
                DbConnectionPool p = (DbConnectionPool) sourcemap.get(k);
                p.closePool();
            }   
        }
        catch (Exception e)
        {
            e.printStackTrace(); 
        }        
    }
    
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static class Handler extends Thread {
    	boolean stop = false;
    	boolean no_reset = false;

        public void run()
        {
            while(!stop){
                try
                {
                    Thread.sleep(3*60*60*1000); // sleep 3 hour
                    if (no_reset)
                    	continue;
                    if (stop)
                    	break;
                	// Thread.sleep(10*1000); // sleep 10 seconds
                    // 每天半夜3點自己 reset db connection
                    //Calendar c = Calendar.getInstance();
                    //if (c.get(Calendar.HOUR_OF_DAY)==3) 
                    resetDbConns();
                }
                catch (Exception e)
                {
                }
            }
        }
    }    
}