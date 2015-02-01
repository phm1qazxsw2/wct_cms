package util;

import java.util.*;

public class CacheMap<X,Y> 
{
    private int size = 0;
    private Map<X, Y> cache = null;
    
    public CacheMap(int size) {
    	this.size = size;
    	cache = new LinkedHashMap<X, Y>(size);
    }
    
    public void put(X a, Y b) {
    	long t1 = new Date().getTime();
    	Y v = cache.get(a);
    	if (v!=null) {
    		cache.put(a, b);
    		return;
    	}
    	// 如果超过大小，就把第一个移调
    	if (cache.size()>=size) {
	    	Iterator<X> iter = cache.keySet().iterator();
	    	Object o = cache.remove(iter.next());
	    	System.out.println("## squeez room for " + o.getClass().getName());
    	}
    	cache.put(a, b);
    	long t2 = new Date().getTime();
    	if ((t2-t1)>5) {
    		System.out.println("## cache too slow: " + (t2-t1) + " size=" + cache.size());
    	}
    }
    
    public Y get(X a) {
    	return cache.get(a);
    }
}
