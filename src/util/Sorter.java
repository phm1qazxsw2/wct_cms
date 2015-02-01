package util;

import java.util.*;
import java.lang.reflect.*;
 
public class Sorter<T>
{
	List<T> list;

    public Sorter(List<T> list)
    {
        this.list = list;
    }
    
    public List<T> sortDescending(String[] getIdMethods, Object arg) 
	    throws Exception
	{
		return doSort(getIdMethods, true, arg);
	}
	
	public List<T> sortAscending(String[] getIdMethods, Object arg) 
	    throws Exception
	{
		return doSort(getIdMethods, false, arg);
	}    

    public List<T> sortDescending(String getIdMethod, Object arg) 
	    throws Exception
	{
    	String[] getIdMethods = { getIdMethod };
    	return doSort(getIdMethods, true, arg);
	}

    public List<T> sortAscending(String getIdMethod, Object arg) 
	    throws Exception
	{
    	String[] getIdMethods = { getIdMethod };
		return doSort(getIdMethods, false, arg);
	}
    
    protected List<T> doSort(String[] getIdMethods, boolean descending, Object arg)
	    throws Exception
	{
	    ArrayList<T> ret = new ArrayList<T>();
	    if (list.size()==0)
	        return ret;
	    Comparator comp = new MyComparator(getIdMethods, descending, arg);
	    Object[] objs = new Object[list.size()];
	    Iterator<T> iter = list.iterator();
	    int i = 0;
	    while (iter.hasNext())
	        objs[i++] = iter.next();
	    Arrays.sort(objs, comp);
	    for (i=objs.length-1; i>=0; i--) {
	        T t = (T)objs[i];
	        ret.add(t);
	    }
	    return ret;
	}

	class MyComparator implements Comparator
	{
	    String[] getters = null;
	    boolean descending = true;
	    boolean hasError = false;
	    Object arg = null;
	    int realtype = 0;
	    
	    MyComparator(String[] getters, boolean descending, Object arg)
	    {
	        this.getters = getters;
	        this.descending = descending;
	        this.arg = arg;
	    }
	    
	    public int compare(Object o1, Object o2)
	    {
	    	return compare(o1, o2, 0);	    	
	    }
	
	    public int compare(Object o1, Object o2, int idx)
	    {
	        try {
	            Class c = o1.getClass();
	            Class[] paramTypes = {};
	            Class[] paramTypes1 = { new Object().getClass() };
	            Method m = null;
	            Object[] params = null;
	            String getter = this.getters[idx];
	            if (arg==null) {
	            	Object[] tmp = {};
	            	params = tmp;
	            	m = c.getMethod(getter, paramTypes);
	            }
	            else {
	            	Object[] tmp = { arg };
	            	params = tmp;
	            	m = c.getMethod(getter, paramTypes1);
	            }
	            
	            Object w1 = (Object) m.invoke(o1, params);
	            Object w2 = (Object) m.invoke(o2, params);
	            
	            if (realtype==0) {
	            	if (w1.getClass().getName().equals("java.lang.Integer"))
	            		realtype = 1;
	            	else if (w1.getClass().getName().equals("java.lang.Float"))
	            		realtype = 2;
	            	else if (w1.getClass().getName().equals("java.lang.Double"))
	            		realtype = 3;
	            	else if (w1.getClass().getName().equals("java.lang.String"))
	            		realtype = 4;
	            	else if (w1.getClass().getName().equals("java.util.Date") || w1.getClass().getName().equals("java.sql.Timestamp"))
	            		realtype = 5;
	            	else if (w1.getClass().getName().equals("java.lang.Long"))
	            		realtype = 6;
	            }
	            
	            if (realtype==1) {
	            	Integer v1 = (Integer) w1;
	            	Integer v2 = (Integer) w2;
		            int r = (descending)?v1.compareTo(v2):v2.compareTo(v1);
		            return (r!=0 || idx>=this.getters.length-1)?r:compare(o1,o2,idx+1);
	            }
	            else if (realtype==2) {
	            	Float v1 = (Float) w1;
	            	Float v2 = (Float) w2;           	
		            int r = (descending)?v1.compareTo(v2):v2.compareTo(v1);
		            return (r!=0 || idx>=this.getters.length-1)?r:compare(o1,o2,idx+1);
	            }
	            else if (realtype==3) {
	            	Double v1 = (Double) w1;
	            	Double v2 = (Double) w2;
		            int r = (descending)?v1.compareTo(v2):v2.compareTo(v1);
		            return (r!=0 || idx>=this.getters.length-1)?r:compare(o1,o2,idx+1);
	            }
	            else if (realtype==4) {
	            	String v1 = (String) w1;
	            	String v2 = (String) w2;
		            int r = (descending)?v1.compareTo(v2):v2.compareTo(v1);
		            return (r!=0 || idx>=this.getters.length-1)?r:compare(o1,o2,idx+1);
	            }
	            else if (realtype==5) {
	            	Date v1 = (Date) w1;
	            	Date v2 = (Date) w2;
		            int r = (descending)?v1.compareTo(v2):v2.compareTo(v1);
		            return (r!=0 || idx>=this.getters.length-1)?r:compare(o1,o2,idx+1);
	            }
	            else if (realtype==6) {
	            	Long v1 = (Long) w1;
	            	Long v2 = (Long) w2;
		            int r = (descending)?v1.compareTo(v2):v2.compareTo(v1);
		            return (r!=0 || idx>=this.getters.length-1)?r:compare(o1,o2,idx+1);
	            }
	        }
	        catch (Exception e) {
	        	if (!hasError) {
	        		e.printStackTrace();
	        	}
	        	hasError = true;
	        }
	        return (descending)?-1:1;
	    }
	
	    public boolean equal(Object o)
	    {
	        return (compare(this, o)==0);
	    }
	}
}

