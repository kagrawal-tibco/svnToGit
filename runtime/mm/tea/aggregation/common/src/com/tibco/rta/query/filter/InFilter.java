package com.tibco.rta.query.filter;

import java.util.List;


/**
 * A filter that represents an "in" condition.
 */
public interface InFilter extends RelationalFilter {
	
	void addToSet(Object... values);
	
	List<Object> getInSet();

}