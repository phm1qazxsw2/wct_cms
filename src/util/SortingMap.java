package util;

import java.util.*;
import java.lang.reflect.*;

public class SortingMap<Y,T>
{
    ArrayList<T> list;

    public SortingMap(List<T> list)
    {
        this.list = (ArrayList) list;
    }

    public SortingMap(ArrayList<T> list)
    {
        this.list = list;
    }

    public SortingMap(Object[] l)
    {
        this.list = new ArrayList<T>((l==null)?0:l.length);
        for (int i=0; l!=null&&i<l.length; i++)
            this.list.add((T)l[i]);
    }

    public SortingMap(Vector<T> v)
    {
        this.list = new ArrayList<T>();
        for (int i=0; v!=null&&i<v.size(); i++)
            this.list.add(v.get(i));
    }

    public SortingMap()
    {
    }


    public Map<Y, Vector<T>> doSort(Object[] objs, ArrayList<T> l, String methodName)
        throws Exception
    {
        for (int i=0; objs!=null && i<objs.length; i++)
            l.add((T)objs[i]);
        this.list = l;
        return doSort(methodName);
    }

    public Map<Y,T> doSortSingleton(String getIdMethod)
        throws Exception
    {
        Map<Y, Vector<T>> m = doSort(getIdMethod);
        Iterator<Y> iter = m.keySet().iterator();
        Map<Y, T> ret = new LinkedHashMap<Y, T>();
        while (iter.hasNext()) {
            Y y = iter.next();
            Vector<T> vt = m.get(y);
            ret.put(y, (vt!=null)?vt.get(0):null);
        }
        return ret;
    }



    public Map<Y,Vector<T>> doSort(String getIdMethod)
        throws Exception
    {
        Map<Y, Vector<T>> ret = new LinkedHashMap<Y, Vector<T>>();
        doSort(getIdMethod, ret);
        return ret;
    }

    public void doSort(String getIdMethod, Map<Y,Vector<T>> ret)
        throws Exception
    {
        if (list==null || list.size()==0)
            return;
        Class c = ((T)list.get(0)).getClass();
        Class[] paramTypes = {};
        Method m = c.getMethod(getIdMethod, paramTypes);

        Object[] params = {};
        Iterator<T> iter = list.iterator();
        while (iter.hasNext()) {
            T t = iter.next();
            Y r = (Y) m.invoke(t, params);
            Vector<T> v = ret.get(r);
            if (v==null) {
                v = new Vector<T>();
                ret.put(r, v);
            }
            v.add(t);
        }
    }


    public ArrayList<T> descendingBy(String getIdMethod)
        throws Exception
    {
        ArrayList<T> ret = new ArrayList<T>();
        if (list.size()==0)
            return ret;
        IntComparator comp = new IntComparator(getIdMethod);
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

    class IntComparator implements Comparator
    {
        String getter = null;
        IntComparator(String getter)
        {
            this.getter = getter;
        }

        public int compare(Object o1, Object o2)
        {
            try {
                Class c = o1.getClass();
                Class[] paramTypes = {};
                Method m = c.getMethod(this.getter, paramTypes);
                
                Object[] params = {};
                T t1 = (T) o1;
                T t2 = (T) o2;
                Integer v1 = (Integer) m.invoke(o1, params);
                Integer v2 = (Integer) m.invoke(o2, params);
                return v1.compareTo(v2);
            }
            catch (Exception e) {}
            return -1;
        }

        public boolean equal(Object o)
        {
            return (compare(this, o)==0);
        }
    }

// ########## ArrayList Version ###########
    public Map<Y,ArrayList<T>> doSortA(String getIdMethod)
        throws Exception
    {
        Map<Y, ArrayList<T>> ret = new LinkedHashMap<Y, ArrayList<T>>();
        doSortA(getIdMethod, null, ret);
        return ret;
    }

    public Map<Y,ArrayList<T>> doSortA(String getIdMethod, Object arg)
        throws Exception
    {
        Map<Y, ArrayList<T>> ret = new LinkedHashMap<Y, ArrayList<T>>();
        doSortA(getIdMethod, arg, ret);
        return ret;
    }

    public Map<Y,T> doSortASingleton(String getIdMethod)
        throws Exception
    {
        Map<Y, ArrayList<T>> m = doSortA(getIdMethod);
        Iterator<Y> iter = m.keySet().iterator();
        Map<Y, T> ret = new LinkedHashMap<Y, T>();
        while (iter.hasNext()) {
            Y y = iter.next();
            ArrayList<T> a = m.get(y);
            ret.put(y, (a!=null)?a.get(0):null);
        }
        return ret;
    }

    static Object oo = new Object();
    public void doSortA(String getIdMethod, Object arg, Map<Y, ArrayList<T>> ret)
        throws Exception
    {
        if (list==null || list.size()==0)
            return;
        Class c = ((T)list.get(0)).getClass();
       
        Object[] params = null;
        Method m = null;
        if (arg==null) {
            Object[] tmp = {};
            params = tmp;
            Class[] paramTypes = {};
            m = c.getMethod(getIdMethod, paramTypes);
        }
        else {
            Object[] tmp = { arg };
            params = tmp;
            Class[] paramTypes = { oo.getClass() };
            m = c.getMethod(getIdMethod, paramTypes);
        }

        Iterator<T> iter = list.iterator();
        while (iter.hasNext()) {
            T t = iter.next();
            Y r = (Y) m.invoke(t, params);
            ArrayList<T> a = ret.get(r);
            if (a==null) {
                a = new ArrayList<T>();
                ret.put(r, a);
            }
            a.add(t);
        }
    }
    
    
    // Map<Y,ArrayList<T>>
    // Y:Object
    // T:Map<String,Object>
    
    public Map<Y, List<T>> doSortKeyA(String[] keys)
	    throws Exception
	{
    	Map<Y, List<T>> ret = new LinkedHashMap<Y, List<T>>();
	    if (list==null || list.size()==0)
	        return ret;
	    
	    for (int i=0; i<list.size(); i++) {
	    	T t = list.get(i);
	    	Object k = null;
	    	for (int j=0; j<keys.length; j++) {
	    		Y val = ((Map<String,Y>)t).get(keys[j]);
	    		if (k!=null)
	    			k = ((String)k) + "#" + val;
	    		else
	    			k = val;
	    	}
	        
	        List<T> a = (List<T>) ret.get((Y)k);
	        if (a==null) {
	            a = new ArrayList<T>();
	            ret.put((Y)k, a);
	        }
	        a.add(t);
	    }
	    return ret;
	}
    
	public Map<Y, T> doSortKeySingleton(String[] keys)
	    throws Exception
	{
		Map<Y, T> ret = new LinkedHashMap<Y, T>();
	    if (list==null || list.size()==0)
	        return ret;
	    
	    for (int i=0; i<list.size(); i++) {
	    	T t = list.get(i);
	    	Object k = null;
	    	for (int j=0; j<keys.length; j++) {
	    		Y val = ((Map<String,Y>)t).get(keys[j]);
	    		if (k!=null)
	    			k = ((String)k) + "#" + val;
	    		else
	    			k = val;
	    	}
	        ret.put((Y)k, t);
	    }
	    return ret;
	}
    
    /*
    public SortingMap<Y,T> getLookupMap(List<Object> list, String getter) 
    	throws Exception
    {
    	String ids = new ConsId(list).makeIds(getter);
    
    	Class c = T.getClass(); 
    	String clsname = c.getName();
    	
    	Object[] tmp = { arg };
        params = tmp;
        Class[] paramTypes = { String, String};
    	Mehtod m = Class.forName(clsname + "Mgr").getMethod("retrieveList", )
    	
    	List<T> results = T'Mgr(0).retrieveList("id in (" + ids + ")", "");
    	
    	return new SortingMap(results).doSortSingleton("getId");
    }
    */
}


