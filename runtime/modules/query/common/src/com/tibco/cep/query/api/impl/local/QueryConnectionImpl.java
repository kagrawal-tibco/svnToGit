package com.tibco.cep.query.api.impl.local;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.query.api.QueryConnection;
import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.api.QueryStatement;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.query.stream.impl.rete.service.RegionManager;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Mar 19, 2008
* Time: 8:35:57 PM
*/
public class QueryConnectionImpl implements QueryConnection, QueryStatementManager {
    protected QuerySession querySession;

    protected RegionManager regionManager;

    //Map being used as a Set.
    protected ConcurrentHashMap<QueryStatement, /*Dummy value.*/ Object> statements;

    public QueryConnectionImpl(QuerySession querySession, RegionManager regionManager) {
        this.querySession = querySession;
        this.regionManager = regionManager;
        this.statements = new ConcurrentHashMap<QueryStatement, Object>();
    }

    public QueryStatement prepareStatement(String queryText) throws QueryException {
        QueryStatement statement = new QueryStatementImpl(this, this /*Callback.*/,
                querySession, regionManager, queryText);

        statements.put(statement, 0 /*Dummy.*/);

        return statement;
    }


    public QueryStatement prepareStatement(PlanGenerator generator) {
        QueryStatement statement = null;
        if(generator instanceof DeletePlanGeneratorImpl) {
            statement = new DeleteQueryStatementImpl(this, this, querySession, regionManager, (DeletePlanGeneratorImpl)generator);
        } else {
            statement =
                    new QueryStatementImpl(this, this, querySession, this.regionManager, generator);
        }

        this.statements.put(statement, 0/*Dummy.*/);

        return statement;
    }


    public void unregisterStatement(QueryStatementImpl statement) {
        statements.remove(statement);
    }

    public void close() {
        for (Iterator<QueryStatement> iterator = statements.keySet().iterator();
             iterator.hasNext();) {
            QueryStatement statement = iterator.next();
            iterator.remove();

            //Calls unregisterStatement(..).
            statement.close();
        }
        statements = null;

        querySession = null;
        regionManager = null;
    }
}
