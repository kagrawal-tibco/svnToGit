package com.tibco.rta.query.filter.impl;

import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.filter.GtFilter;
// TODO: Auto-generated Javadoc
/**
 * The Class GtFilter.
 */
public class GtFilterImpl extends RelationalFilterImpl implements GtFilter {

	private static final long serialVersionUID = -3649870227330013576L;

	/**
	 * Instantiates a new gt filter.
	 */
	public GtFilterImpl() {}
	
	/**
	 * Instantiates a new gt filter.
	 *
	 * @param extractor the extractor
	 * @param value the value
	 */
	public GtFilterImpl(FilterKeyQualifier qualifier, String key, Object value) {
		super(qualifier,key, value);
	}
	
	public GtFilterImpl(MetricQualifier qualifier, Object value) {
		super(qualifier, value);
	}

	
	public String toString() {
		return "GT:";
	}
}