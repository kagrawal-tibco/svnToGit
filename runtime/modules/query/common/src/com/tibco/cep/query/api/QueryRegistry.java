package com.tibco.cep.query.api;

public interface QueryRegistry {

    
    void bindListener(String statementName, String resultsName, QueryListener listener);


    QueryResultSet bindResultSet(String statementName, String resultsName);


    QueryStatement findStatement(String statementName);


    QueryConnection getConnection();


    void registerStatement(String name, QueryStatement statement);


    void shareResults(String statementName, String resultsName);


    void unbindListener(String statementName, String resultName, QueryListener listener);


    QueryResultSet unbindResultSet(String statementName, String resultName);


    void unregisterStatement(String name);


    void unshareResults(String statementName, String resultsName);

    
}
 
