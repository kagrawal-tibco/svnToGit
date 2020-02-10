package com.tibco.rta.query.filter.impl;

import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.filter.GEFilter;

// TODO: Auto-generated Javadoc
/**
 * The Class GEFilter.
 */
public class GEFilterImpl extends RelationalFilterImpl implements GEFilter {

	private static final long serialVersionUID = -7957255810803398465L;

	/**
	 * Instantiates a new gE filter.
	 */
	public GEFilterImpl() {}
	
	/**
	 * Instantiates a new gE filter.
	 *
	 * @param extractor the extractor
	 * @param value the value
	 */
	public GEFilterImpl(FilterKeyQualifier keyQualifier, String key, Object value) {
		super(keyQualifier,key, value);
	}
	
	public GEFilterImpl(MetricQualifier qualifier, Object value) {
		super(qualifier, value);
	}

	public String toString() {
		return "GE:";
	}
}