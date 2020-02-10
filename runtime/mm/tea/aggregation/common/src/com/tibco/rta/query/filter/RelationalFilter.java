package com.tibco.rta.query.filter;


import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.tibco.rta.model.serialize.jaxb.adapter.RelationalFilterAdapter;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;

/**
 * A base filter for relational operations like equals, less than, greater than, etc
 * 
 */
@XmlJavaTypeAdapter(RelationalFilterAdapter.class)
public interface RelationalFilter extends BaseRelationalFilter {
	
	MetricQualifier getMetricQualifier();
	
	FilterKeyQualifier getKeyQualifier();
}
