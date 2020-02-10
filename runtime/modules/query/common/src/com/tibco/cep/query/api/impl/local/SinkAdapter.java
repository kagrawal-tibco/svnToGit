package com.tibco.cep.query.api.impl.local;

import java.util.concurrent.atomic.AtomicBoolean;

import com.tibco.cep.query.api.QueryResultSet;
import com.tibco.cep.query.api.QueryStatement;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
* Author: Ashwin Jayaprakash Date: Mar 21, 2008 Time: 7:26:38 PM
*/
public abstract class SinkAdapter implements QueryResultSet {
    protected AtomicBoolean stop;

    protected TupleInfo tupleInfo;

    protected QueryStatement statement;

    protected QueryResultSetManager resultSetManager;

    protected ReteQuery reteQuery;

    protected SinkAdapter(ReteQuery reteQuery, QueryStatement statement,
                          QueryResultSetManager resultSetManager) {
        this.stop = new AtomicBoolean(false);
        this.statement = statement;
        this.reteQuery = reteQuery;
        this.resultSetManager = resultSetManager;
        this.tupleInfo = reteQuery.getSink().getOutputInfo();
    }

    public QueryStatement getStatement() {
        return statement;
    }

    public int findColumn(String name) {
        return tupleInfo.getColumnPosition(name);
    }

    public ReteQuery getReteQuery() {
        return reteQuery;
    }

    public void close() throws Exception {
        resultSetManager.unregisterAndStopQuery(reteQuery);
    }
}
