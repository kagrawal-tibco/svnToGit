package com.tibco.rta.query.filter;

/**
 * A filter that represents a "not" condition
 */
public interface NotFilter extends Filter {
	
	Filter getBaseFilter();

}