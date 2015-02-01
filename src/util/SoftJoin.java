package util;

import java.util.*;
import java.lang.reflect.*;

public class SoftJoin<Y,T>
{
    ArrayList<T> list;
    String[] ids;
    int int_page = 1000000;
    int str_page = 150000; // 16bytes(mac,) multiply < 3000000

    /*
     * 可分段处理超过数据库 max_allowed_package 的量
     * 但是如果有超过一个 page 以上，query_spec 会有问题，目前还不支援
     */
    public SoftJoin(List<T> list, String getter, boolean isString)
    	throws Exception
    {
        this.list = (ArrayList) list;
    	if (!isString) {
    		int psize = (this.list.size()/int_page) + (((this.list.size()%int_page)>0)?1:0);
    		this.ids = new String[psize];
    		for (int i=0; i<psize; i++) {
    			int from = int_page * i;
    			int to = int_page*(i+1)-1;
    			if (to>this.list.size())
    				to = this.list.size()-1;
    			ids[i] = new ConsId(this.list.subList(from, to)).makeIds(getter);
    		}
    	}
    	else {
    		int psize = (this.list.size()/str_page) + (((this.list.size()%str_page)>0)?1:0);
    		this.ids = new String[psize];
    		for (int i=0; i<psize; i++) {
    			int from = str_page * i;
    			int to = str_page*(i+1)-1;
    			if (to>this.list.size())
    				to = this.list.size()-1;
    			List<T> sublist = this.list.subList(from, to);
    			String str = new ConsId(sublist).makeStringIds(getter);
    			this.ids[i] = str;
    		}
    	}
    }
    
    public List<Y> doJoin(dbo.Manager<Y> beanmgr, String field, String filter, String query_spec) 
		throws Exception
	{   
		if (ids.length>1 && query_spec.length()>0)
			throw new Exception("query_spec not support lengthy ids yet!");
		
		List<Y> ret = new ArrayList<Y>();
		for (int i=0; i<ids.length; i++) {
			System.out.print("j1#" + i);
			String q = field + " in (" + ids[i] + ")";
			if (filter!=null && filter.length()>0)
				q = filter + " and " + q;
			List<Y> obj = beanmgr.retrieveList(q, query_spec);
			System.out.print(" j2#" + i);
	    	ret.addAll(obj);
			System.out.print(" j3#" + i);
		}
		return ret;
	}

    public List<Y> doJoin(Y bean, String field, String query_spec, int tran_id) 
    	throws Exception
    {   
    	if (ids.length>1 && query_spec.length()>0)
    		throw new Exception("query_spec not support lengthy ids yet!");
    	
    	Class mgrclass = Class.forName(bean.getClass().getName() + "Mgr");
    	Class[] paramTypes = { int.class };
    	Constructor<Y> mgr_constructor = mgrclass.getConstructor(paramTypes);
    	Object mgr_object = mgr_constructor.newInstance(tran_id);
    	
    	Class[] paramTypes2 = { "".getClass(), "".getClass() };
    	Method m = mgrclass.getMethod("retrieveList", paramTypes2);
    	List<Y> ret = new ArrayList<Y>();
    	for (int i=0; i<ids.length; i++) {
    		System.out.print("j1#" + i);
        	Object[] params2 = { field + " in (" + ids[i] + ")", query_spec };
        	Object obj = m.invoke(mgr_object, params2); 
    		System.out.print(" j2#" + i);
        	ret.addAll((List<Y>)obj);
    		System.out.print(" j3#" + i);
    	}
    	return ret;
    }
}


