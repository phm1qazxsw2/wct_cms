package web;

import dbo.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import java.io.*;

public class Start extends HttpServlet {
	public static String version = "v0_5";
	
    public void init(ServletConfig config) 
        throws ServletException
    {
        try {
            ServletContext ctx = config.getServletContext();
            File f = new File(ctx.getRealPath("/"));
            File dsource = new File(f, "WEB-INF/datasource");
            DataSource.setup(dsource.getAbsolutePath());
            System.out.println("# cms started..");
        }
        catch (Exception e) {
            throw new ServletException(e);
        } 
    } 

    public void destroy() {
        System.out.println("cms stopping...");
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("[" + df.format(new java.util.Date()) + "] ** releasing db connections");
        DataSource.shutdown();
    }
}
