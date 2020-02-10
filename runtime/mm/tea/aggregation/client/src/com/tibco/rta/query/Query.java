package com.tibco.rta.query;

import com.tibco.rta.RtaException;

/**
 * The base client interface used to define a query
 * to be executed on the metric engine.
 */
public interface Query {

    /**
     * Return name
     * @return
     */
	String getName();


    /**
     * Define a query using partial or complete keys.
     * @return the query definition
     */
	QueryByKeyDef newQueryByKeyDef();

    /**
     * Define a query using filters
     * @return the query definition
     */
    QueryByFilterDef newQueryByFilterDef(String schemaName, String cubeName, String hierarchyName, String measurementName);

    /**
     * Return a browser over result set of a query.
     * <p>
     *     For snapshot query, browser can be used
     *     to iterate over the results, whereas for streaming
     *     queries the browser will be equivalent to an
     *     empty iterator.
     * </p>
     * @return
     * @throws Exception
     */
	<T extends MetricResultTuple> Browser<T> execute() throws RtaException;

    /**
     * Closes the query thus stopping any updates from server.
     */
	void close() throws RtaException;

    /**
     *
     * @return
     */
    QueryResultHandler getResultHandler();

    /**
     *
     * @param resultHandler
     */
   	void setResultHandler(QueryResultHandler resultHandler);

}