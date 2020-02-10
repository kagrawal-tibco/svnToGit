/**
 * 
 */
package com.tibco.rta.query.filter.impl;

import javax.xml.bind.annotation.XmlRootElement;

import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.filter.NEqFilter;


/**
 * @author ssinghal
 *
 */

@XmlRootElement
public class NEqFilterImpl extends RelationalFilterImpl implements NEqFilter {

	private static final long serialVersionUID = -28137600133108822L;
	
	public NEqFilterImpl(){}
	
	public NEqFilterImpl(MetricQualifier qualifier, Object value) {
		super(qualifier, value);
	}
	
	public NEqFilterImpl(FilterKeyQualifier qualifier, String key, Object value) {
		super(qualifier,key, value);
	}
	
	public String toString() {
		return "NEQ:";
	}

}
