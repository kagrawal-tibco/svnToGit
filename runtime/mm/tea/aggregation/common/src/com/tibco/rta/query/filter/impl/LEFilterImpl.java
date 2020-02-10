package com.tibco.rta.query.filter.impl;

import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.filter.LEFilter;
// TODO: Auto-generated Javadoc
/**
 * The Class LEFilter.
 */
public class LEFilterImpl extends RelationalFilterImpl implements LEFilter {

	private static final long serialVersionUID = -7807579188832947084L;

	/**
	 * Instantiates a new lE filter.
	 */
	public LEFilterImpl() {}
	
	/**
	 * Instantiates a new lE filter.
	 *
	 * @param extractor the extractor
	 * @param value the value
	 */
	public LEFilterImpl(FilterKeyQualifier qualifier, String key, Object value) {
		super(qualifier,key, value);
	}
	
	public LEFilterImpl(MetricQualifier qualifier, Object value) {
		super(qualifier, value);
	}

	
	public String toString() {
		return "LE:";
	}
}