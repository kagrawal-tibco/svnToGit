package com.tibco.rta.query;

/**
 * Interface to be used to get notifications
 * of query results in case of streaming queries.
 */
public interface QueryResultHandler {

    /**
     * Get notification of result of a streaming query
     * @param queryResultTuple streaming query result.
     */
	void onData(QueryResultTuple queryResultTuple);

    /**
     * Notification of any exception or error.
     * @param errorContext error context.
     */
	void onError(Object errorContext);
	
}