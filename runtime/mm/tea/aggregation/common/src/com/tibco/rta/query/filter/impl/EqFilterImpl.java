package com.tibco.rta.query.filter.impl;


import javax.xml.bind.annotation.XmlRootElement;

import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.filter.EqFilter;

// TODO: Auto-generated Javadoc
/**
 * The Class EqFilter.
 */
@XmlRootElement
public class EqFilterImpl extends RelationalFilterImpl implements EqFilter {
	
	private static final long serialVersionUID = 9160128275847230960L;

	/**
	 * Instantiates a new eq filter.
	 */
	public EqFilterImpl() {}
	
	/**
	 * Instantiates a new eq filter.
	 *
	 * @param qualifier the qualifier
	 * @param value the value
	 */
	public EqFilterImpl(MetricQualifier qualifier, Object value) {
		super(qualifier, value);
	}
	
	public EqFilterImpl(FilterKeyQualifier qualifier, String key, Object value) {
		super(qualifier,key, value);
	}
	
	public String toString() {
		return "EQ:";
	}
}