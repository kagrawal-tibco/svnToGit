package com.tibco.rta.query.filter.impl;


import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.filter.LikeFilter;

// TODO: Auto-generated Javadoc
/**
 * The Class LikeFilterImpl.
 */
public class LikeFilterImpl extends RelationalFilterImpl implements LikeFilter {
	
	private static final long serialVersionUID = 9160128275847230960L;

	/**
	 * Instantiates a new Like filter.
	 */
	public LikeFilterImpl() {}
	
	/**
	 * Instantiates a new Like filter.
	 *
	 * @param qualifier the qualifier
	 * @param value the value
	 */
	public LikeFilterImpl(MetricQualifier qualifier, String valueRegEx) {
		super(qualifier, valueRegEx);
	}
	
	public LikeFilterImpl(FilterKeyQualifier qualifier, String key, String valueRegEx) {
		super(qualifier,key, valueRegEx);
	}
	
	public String toString() {
		return "LIKE:";
	}
}