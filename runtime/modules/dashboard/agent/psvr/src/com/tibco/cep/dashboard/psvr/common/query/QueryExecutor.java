package com.tibco.cep.dashboard.psvr.common.query;

public interface QueryExecutor {

	/**
	 * Execute the query and count the number of tuples in the result set.
	 * This is for seeing how large a potential result set could be before
	 * fetching all the tuples.  This call won't change the current result
	 * set if there's any.
	 *
	 * @param query         The query to execute.
	 * @return the row count of the result set.
	 */
	public int countQuery(Query query) throws QueryException;

	/**
	 * Execute the query and return a result set.
	 *
	 * @param query         The query spec to execute.
	 * @param ctx           The execution context for running the query.
	 *                      This is needed for evaluating the query against runtime property and parameter substitution.
	 * @return the result set cursor
	 */
	public ResultSet executeQuery(Query query) throws QueryException;

	/**
	 * Closes this instance of query executor. All open curosrs should be closed
	 */
	public void close();

}