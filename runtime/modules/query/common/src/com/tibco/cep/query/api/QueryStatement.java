package com.tibco.cep.query.api;

import com.tibco.cep.query.service.QueryFeatures;
import com.tibco.cep.query.stream.cache.SharedObjectSource;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;


public interface QueryStatement {


    /**
     * Closes this statement.
     */
    void close();


    /**
     * Executes the query and returns a {@link com.tibco.cep.query.api.QueryResultSet}.
     *
     * @param policy QueryPolicy describing the policies to use when executing the query.
     * @return QueryResultSet from where the query results can be read.
     * @throws QueryException is anything goes wrong.
     */
    QueryResultSet executeQuery(QueryPolicy policy) throws QueryException;

    QueryResultSet executeQuery(QueryPolicy policy, QueryFeatures queryFeatures) throws QueryException;

    /**
     * Executes the query and binds a {@link com.tibco.cep.query.api.QueryListener} to its
     * results.
     *
     * @param policy   QueryPolicy describing the policies to use when executing the query.
     * @param listener QueryListener which can read the query results.
     * @throws QueryException is anything goes wrong.
     * @return QueryListenerHandle that can be used to manage the listener.
     */
    QueryListenerHandle executeQuery(QueryPolicy policy, QueryListener listener) throws QueryException;

    QueryListenerHandle executeQuery(QueryPolicy policy, QueryListener listener, QueryFeatures queryFeatures) throws QueryException;

    /**
     * Gets the QueryConnection owning this object, if it is known, else null.
     *
     * @return QueryConnection owning this object, if it is known, else null.
     */
    QueryConnection getConnection();


    /**
     * Sets the value of the binding variable of given name.
     *
     * @param parameterName  String name of the binding variable.
     * @param o              Object value to set the binding variable to.
     */
    void setObject(String parameterName, Object o);

    
    /**
     * Clears the parameter objects that were set using {@link #setObject(String, Object)} before
     * invoking a new {@link QueryResultSet}.
     */
    void clearParameters();


    /**
     * Gets the source text of the query.
     * @return String source text of the query.
     */
    String getText();

    /**
     * Return the Fake Shared object source for the query statement
     * @return SharedObjectSource fake implementation
     */
    SharedObjectSource getFakeSharedObjectSource();

    /**
     * Returns the array of rete query objects that are cached.
     * @return ReteQuery[] cached queries
     */
    ReteQuery[] getCachedReteQueries();
}
 
