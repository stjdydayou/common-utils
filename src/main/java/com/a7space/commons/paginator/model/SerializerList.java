package com.a7space.commons.paginator.model;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.alibaba.com.caucho.hessian.io.HessianHandle;


public class SerializerList<E> implements HessianHandle, Serializable {

    private List<E> data;
    
    /**
     * <p>Discription:[字段功能描述]</p>
     */
    private static final long serialVersionUID = -2564520377798675933L;

    public SerializerList(Collection<? extends E> c) {
        if (c == null)
            data = new ArrayList<E>();
        else
            data = new ArrayList<E>(c);
    }

    public SerializerList() {
        data = new ArrayList<E>();
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    private Object readResolve() throws ObjectStreamException{
        PageList list = new PageList(data);
        if (data.size() > 0) {
            if (data.get(0) instanceof Paginator) {
                Object o = data.get(0);
                data.remove(0);
                return new PageList(data, (Paginator) o);
            }
        }
        return new PageList(data);
    }
}
