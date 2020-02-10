package com.tibco.cep.query.api;


public interface QueryConnection {


    /**
     * Closes the connection.
     */
    void close();


    /**
     * Prepares a new statement.
     * @param queryText String text of the query.
     * @return QueryStatement prepared.
     * @throws QueryException upon problem.
     */
    QueryStatement prepareStatement(String queryText) throws QueryException;


}
 
