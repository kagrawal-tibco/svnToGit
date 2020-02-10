package com.tibco.rta.query.filter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.tibco.rta.model.serialize.jaxb.adapter.LogicalFilterAdapter;

/**
 * 
 * A base filter for logical operations AND and OR.
 *
 */
@XmlJavaTypeAdapter(LogicalFilterAdapter.class)
public interface LogicalFilter extends Filter {
	/**
	 * Adds the filter.
	 *
	 * @param f the f
	 */
	void addFilter (Filter... f);
	
	Filter[] getFilters();
	
}
