package com.tibco.rta.query;

import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.QueryResultHandler;

/**
 * @author vdhumal
 *
 */
public interface QueryDefEx extends QueryDef {

	/**
	 * @param queryResultHandler
	 */
	void setResultHandler(QueryResultHandler queryResultHandler);  
	
	/**
	 * @return QueryResultHandler
	 */
	QueryResultHandler getResultHandler();
	
}
