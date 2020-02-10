package com.tibco.rta.query.impl;

import com.tibco.rta.query.filter.AndFilter;
import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.filter.NotFilter;
import com.tibco.rta.query.filter.OrFilter;
import com.tibco.rta.query.filter.impl.AndFilterImpl;
import com.tibco.rta.query.filter.impl.NotFilterImpl;
import com.tibco.rta.query.filter.impl.OrFilterImpl;

public class BaseQueryFactory {

	protected BaseQueryFactory () {
		
	}
	
	public NotFilter newNotFilter(Filter filter) {
		return new NotFilterImpl(filter);	
	}
	
	public NotFilter newNotFilter() {
		return new NotFilterImpl();	
	}
	
	public AndFilter newAndFilter() {
		return new AndFilterImpl();
	}
	
	public AndFilter newAndFilter(Filter... filters) {
		return new AndFilterImpl(filters);
	}

	public OrFilter newOrFilter() {
		return new OrFilterImpl();
	}
	
	public OrFilter newOrFilter(Filter filter) {
		return new OrFilterImpl(filter);
	}
	
	public OrFilter newOrFilter(Filter[] filters) {
		return new OrFilterImpl(filters);
	}
	
}
