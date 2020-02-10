package com.tibco.rta.query.filter.impl;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.filter.OrFilter;

// TODO: Auto-generated Javadoc
/**
 * The Class OrFilter.
 */
@XmlAccessorType(XmlAccessType.NONE)
public class OrFilterImpl extends LogicalFilterImpl implements OrFilter {

	private static final long serialVersionUID = -8665641071715897615L;

	/**
	 * Instantiates a new or filter.
	 */
	public OrFilterImpl() {
		
	}
	
	public OrFilterImpl(Filter filter) {
		super(filter);
	}
	
	/**
	 * Instantiates a new or filter.
	 *
	 * @param f the f
	 */
	public OrFilterImpl (Filter[] filters) {
		super(filters);
	}
	
	/**
	 * Adds the filter.
	 *
	 * @param f the f
	 */
	public void addFilter (Filter filter){
		super.addFilter(filter);
	}
	
	public String toString() {
			String s =  "OR:";
			for (Filter f : filters) {
				s += f;
			}
			return s;
		}

}