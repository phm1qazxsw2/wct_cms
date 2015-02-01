package util;

import java.lang.reflect.*;
import java.util.*;

public class ConsId<Y,T>
{
    private Map<Y,Y> excludes = null;
    private List<T> list = null;

    public ConsId(List<T> list)
    {
        this.list = list;
    }
    
    public ConsId(ArrayList<T> list)
    {
        this.list = list;
    }

    public void setExcludes(Map<Y,Y> excludes) 
    {
        this.excludes = excludes;
    }

    public static final String NOTFOUND = "-9999999";
    public static final String NOTFOUND_STR = "'@#$!#'";
    // return NOTFOUND if no id is found. the outer program hopefully will not 
    // search anything when idstring is NOTFOUND
    public String makeIds(String getIdMethod)
        throws Exception
    {
        StringBuffer sb = new StringBuffer();
        if (this.list==null || this.list.size()==0) 
            return NOTFOUND;
        Class c = this.list.get(0).getClass();
        Class[] paramTypes = {};
        Method m = c.getMethod(getIdMethod, paramTypes);
        HashMap tmp = new HashMap();

        Object[] params = {};
        Iterator<T> iter = list.iterator();
        while (iter.hasNext()) {
            T t = iter.next();
            Y i = (Y) m.invoke(t, params);
            if (tmp.get(i)!=null)
                continue;
            if (excludes!=null && excludes.get(i)!=null)
                continue;
            if (sb.length()>0) sb.append(',');
            sb.append(i);
            tmp.put(i, i);
        }
        if (sb.length()==0)
            return NOTFOUND;
        return sb.toString();
    }

    public String makeStringIds(String getIdMethod)
        throws Exception
    {
        StringBuffer sb = new StringBuffer();
        if (this.list==null || this.list.size()==0)
            return NOTFOUND_STR; 
        Class c = list.get(0).getClass();
        Class[] paramTypes = {};
        Method m = c.getMethod(getIdMethod, paramTypes);
        HashMap tmp = new HashMap();

        Object[] params = {};
        Iterator<T> iter = list.iterator();
        while (iter.hasNext()) {
            T t = iter.next();
            Y i = (Y) m.invoke(t, params);
            if (tmp.get(i)!=null)
                continue;
            if (excludes!=null && excludes.get(i)!=null)
                continue;
            if (sb.length()>0) sb.append(',');
            sb.append('\'');
            sb.append(i);
            sb.append('\'');
            tmp.put(i, i);
        }
        if (sb.length()==0)
            return NOTFOUND_STR;
        return sb.toString();
    }
}

