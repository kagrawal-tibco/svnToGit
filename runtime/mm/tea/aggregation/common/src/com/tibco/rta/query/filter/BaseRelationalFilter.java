package com.tibco.rta.query.filter;



/**
 * A base filter for relational operations like equals, less than, greater than, etc
 * 
 */
public interface BaseRelationalFilter extends Filter {
	
	String getKey();

	Object getValue();
}
