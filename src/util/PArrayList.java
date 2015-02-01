package util;

import java.util.*;

public class PArrayList<T> extends ArrayList<T>
{
    public PArrayList() {}

    public PArrayList(List<T> a) {
        super((a==null)?new ArrayList():a);
    }

    public PArrayList(T a) {
        super();
        if (a==null)
        	return;
        this.add(a);
    }

    public PArrayList(Vector<T> v) {
        for (int i=0; v!=null&&i<v.size(); i++)
            this.add(v.get(i));
    }

    public PArrayList<T> concate(List<T> a) {
        for (int i=0; a!=null&&i<a.size(); i++)
            this.add(a.get(i));
        return this;
    }
     
    public PArrayList<T> insert(T a) {
        this.add(a);
        return this;
    }
}
