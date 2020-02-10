package com.tibco.cep.query.functions;

import java.util.LinkedList;

import com.tibco.cep.query.api.Row;
import com.tibco.cep.runtime.session.RuleSession;

/**
 * QueryListener implementation that forwards updates to a RuleFunction.
 */
class RuleFunctionBatchedQueryListener extends RuleFunctionQueryListener {
    protected final LinkedList<Object[]> accumulatedRowsInBatch;

    public RuleFunctionBatchedQueryListener(String listenerName, RuleSession ruleSession,
                                            String callbackUri, Object closure) {
        super(listenerName, ruleSession, callbackUri, closure);

        this.accumulatedRowsInBatch = new LinkedList<Object[]>();
    }

    @Override
    public void onNewRow(Row row) {
        accumulatedRowsInBatch.add(row.getColumns());
    }

    @Override
    public void onBatchEnd() {
        int arraySize = accumulatedRowsInBatch.size();
        Object[][] allRows = accumulatedRowsInBatch.toArray(new Object[arraySize][]);
        accumulatedRowsInBatch.clear();

        Object[] params = new Object[]{this.listenerName, true, false, allRows, this.closure};

        callRF(params);
    }
}