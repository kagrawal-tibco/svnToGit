package com.tibco.rta.query.impl;

import com.tibco.rta.query.QueryDefEx;
import com.tibco.rta.query.QueryResultHandler;
import com.tibco.rta.query.impl.QueryKeyDefImpl;

/**
 * @author vdhumal
 *
 */
public class QueryKeyDefExImpl extends QueryKeyDefImpl implements QueryDefEx {

	private static final long serialVersionUID = 7090233808336450656L;

	private QueryResultHandler queryResultHandler = null;
	
	@Override
	public void setResultHandler(QueryResultHandler queryResultHandler) {
		this.queryResultHandler = queryResultHandler;
	}

	@Override
	public QueryResultHandler getResultHandler() {
		return queryResultHandler;
	}

}
