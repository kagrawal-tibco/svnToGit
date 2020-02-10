package com.tibco.cep.query.client.console.swing.control.query;

import com.tibco.cep.query.client.console.swing.model.listener.AsyncQueryListenerImpl;
import com.tibco.cep.query.client.console.swing.model.QueryResultModel;
import com.tibco.cep.query.client.console.swing.ui.RightPanel;
import com.tibco.cep.query.client.console.swing.util.QueryUtil;
import com.tibco.cep.query.client.console.swing.util.SessionUtil;
import com.tibco.cep.query.functions.CallbackFunctions;
import com.tibco.cep.query.functions.QueryFunctions;
import com.tibco.cep.query.functions.ResultSetFunctions;
import com.tibco.cep.query.functions.StatementFunctions;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author ksubrama
 */
public class Query {
    private static final AtomicLong index = new AtomicLong(0);
    private static final Random random = new Random(System.currentTimeMillis());
    private static Set<Query> querySet = new HashSet<Query>();
    private static final byte[] lock = new byte[0];

    public static void closeAllQueries() {
        synchronized(lock) {
            if(querySet != null) {
                for (Query query : querySet) {
                    query.forceShutdown();
                }
                querySet.clear();
                querySet = null;
            }
        }
    }

    private final String query, queryName, statementName, resultsetName, listenerName;
    private QueryResultModel resultModel;
    private AsyncQueryListenerImpl listener;
    
    public Query(String query) throws Exception {
        this.query = query;
        long currentIndex = index.incrementAndGet();
        long randomLong = random.nextLong();
        queryName = "Query-" + randomLong + "-" + currentIndex;
        statementName = "Statement-" + randomLong + "-" + currentIndex;
        resultsetName = "Resultset-" + randomLong + "-" + currentIndex;
        listenerName = "Listener-" + randomLong + "-" + currentIndex;
        createQuery();
        resultModel = new QueryResultModel(QueryUtil.getColumnNames(queryName));
        synchronized(lock) {
            if(querySet != null) {
                querySet.add(this);
            }
        }
    }

    public QueryResultModel getResultModel() {
        return resultModel;
    }
    
    private void createQuery() throws Exception {
        SessionUtil.initSession();
        if(QueryFunctions.exists(queryName) == false) {
            QueryFunctions.create(queryName, query);
        }
        if(StatementFunctions.isOpen(statementName) == false) {
            StatementFunctions.open(queryName, statementName);
        }
    }

    public boolean executeQuery(QueryType type, RightPanel rightPanel) throws Exception {
        createQuery();
        switch (type) {
            case CONTINUOUS:
                resultModel.removeAllRows();
                handleContinuousQuery(rightPanel);
                return false;
            case SNAPSHOT:
                resultModel.removeAllRows();
                handleSnapshotQuery();
                return true;
            case SNAPSHOT_THEN_CONTINUOUS:
                resultModel.removeAllRows();
                handleSnapshotThenContinuousQuery(rightPanel);
                return false;
        }
        return true;
    }

    public void closeQuery() {
        synchronized(lock) {
            if(querySet != null) {
                if(querySet.contains(this) == false) {
                    return;
                }
                forceShutdown();
                querySet.remove(this);
            }
        }        
    }

    public void destroyQuery() {
        if(listener != null) {
            listener.stopQuery();
        }
        if(CallbackFunctions.exists(listenerName) == true) {
            CallbackFunctions.delete(listenerName);
        }
        ResultSetFunctions.close(resultsetName);
        StatementFunctions.close(statementName);
        QueryFunctions.delete(queryName);
    }

    private void handleSnapshotQuery() {
        try {
            StatementFunctions.execute(statementName, resultsetName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleContinuousQuery(RightPanel rightPanel) {
        if(CallbackFunctions.exists(listenerName) == true) {
            CallbackFunctions.delete(listenerName);
        }
        listener = new AsyncQueryListenerImpl(this, rightPanel);
        try {
            StatementFunctions.executeWithCallback_Internal(statementName, listenerName,
                    listener, true, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleSnapshotThenContinuousQuery(RightPanel rightPanel) {
        if(CallbackFunctions.exists(listenerName) == true) {
            CallbackFunctions.delete(listenerName);
        }
        listener = new AsyncQueryListenerImpl(this, rightPanel);
        try {
            StatementFunctions.executeSSThenContinuousWithCallback(statementName,
                    listenerName, listener, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return queryName;
    }

    public String getResultsetName() {
        return resultsetName;
    }

    public String getStatementName() {
        return statementName;
    }

    public String getListenerName() {
        return listenerName;
    }

    private void forceShutdown() {
        SessionUtil.initSession();
        if(listener != null) {
            listener.stopQuery();
        }
        if(CallbackFunctions.exists(listenerName) == true) {
            CallbackFunctions.delete(listenerName);
        }
        ResultSetFunctions.close(resultsetName);
        StatementFunctions.close(statementName);
    }
}
