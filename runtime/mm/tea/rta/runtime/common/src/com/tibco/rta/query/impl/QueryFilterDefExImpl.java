package com.tibco.rta.query.impl;

import com.tibco.rta.query.QueryDefEx;
import com.tibco.rta.query.QueryResultHandler;
import com.tibco.rta.query.impl.QueryFilterDefImpl;

/**
 * @author vdhumal
 *
 */
public class QueryFilterDefExImpl extends QueryFilterDefImpl implements QueryDefEx {

	private static final long serialVersionUID = -7919805889043158776L;

	protected QueryResultHandler queryResultHandler = null;
	
	public QueryFilterDefExImpl(String schemaName, String cubeName, String hierarchyName, String measurementName) {
		super(schemaName, cubeName, hierarchyName, measurementName);
	}

	@Override
	public void setResultHandler(QueryResultHandler queryResultHandler) {
		this.queryResultHandler = queryResultHandler;		
	}

	@Override
	public QueryResultHandler getResultHandler() {
		return queryResultHandler;
	}

}
