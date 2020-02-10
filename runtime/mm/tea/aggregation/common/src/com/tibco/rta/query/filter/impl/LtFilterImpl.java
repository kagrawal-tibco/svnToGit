package com.tibco.rta.query.filter.impl;

import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.filter.LtFilter;
// TODO: Auto-generated Javadoc
/**
 * The Class LtFilter.
 */
public class LtFilterImpl extends RelationalFilterImpl implements LtFilter {
	
	private static final long serialVersionUID = -3029592210412219663L;

	/**
	 * Instantiates a new lt filter.
	 */
	public LtFilterImpl() {}
	
	/**
	 * Instantiates a new lt filter.
	 *
	 * @param extractor the extractor
	 * @param value the value
	 */
	public LtFilterImpl(FilterKeyQualifier qualifier, String key, Object value) {
		super(qualifier,key, value);
	}
	
	public LtFilterImpl(MetricQualifier qualifier, Object value) {
		super(qualifier, value);
	}

	
	public String toString() {
		return "LT:";
	}
}