package com.tibco.rta.query.filter.impl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.filter.NotFilter;

/**
 * The Class NotFilter.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class NotFilterImpl implements NotFilter {

	private static final long serialVersionUID = -877818958677873204L;
	
	protected Filter filter;
	
	protected Filter parent;

	/**
	 * Instantiates a new not filter.
	 */
	public NotFilterImpl() {}
	
	/**
	 * Instantiates a new not filter.
	 *
	 * @param filter the filter
	 */
	public NotFilterImpl(Filter filter) {
		this.filter = filter;
	}

	@Override
	public Filter getParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public void setParent(Filter parent) {
		this.parent = parent;
		
	}

	@XmlElement
	@Override
	public Filter getBaseFilter() {
		return filter;
	}
	
	private void setBaseFilter(Filter baseFilter) {
		filter = baseFilter;
	}

}