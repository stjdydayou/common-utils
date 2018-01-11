package com.a7space.commons.paginator.model;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * 包含“分页”信息的List
 * 
 * <p>
 * 要得到总页数请使用 toPaginator().getTotalPages();
 * </p>
 * 
 * @author badqiu
 * @author miemiedev
 */
public class PageList<E> extends ArrayList<E> implements Serializable {

	private static final long serialVersionUID = 1412759446332294208L;

	private Paginator paginator;

	public PageList() {
	}

	public PageList(Collection<? extends E> c) {
		super(c);
	}

	public PageList(Collection<? extends E> c, Paginator p) {
		super(c);
		this.paginator = p;
	}

	public PageList(Paginator p) {
		this.paginator = p;
	}

	/**
	 * 得到分页器，通过Paginator可以得到总页数等值
	 * 
	 * @return
	 */
	public Paginator getPaginator() {
		return paginator;
	}

	public PageResult<E> getPageResult() {
		return new PageResult<E>(this);
	}

    /**
     * 创建新的序列化对象，并放入分页信息
     */
    private Object writeReplace() throws ObjectStreamException {
        SerializerList list = new SerializerList(this);
        if(paginator!=null)
            list.getData().add(0,paginator);
        return list;
    }

}
