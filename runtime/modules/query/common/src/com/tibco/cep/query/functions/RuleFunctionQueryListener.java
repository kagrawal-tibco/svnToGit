package com.tibco.cep.query.functions;

import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.query.api.QueryListener;
import com.tibco.cep.query.api.QueryListenerHandle;
import com.tibco.cep.query.api.Row;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * QueryListener implementation that forwards updates to a RuleFunction.
 */
class RuleFunctionQueryListener implements QueryListener {
    protected Object closure;

    protected RuleFunction ruleFunction;

    protected RuleSessionImpl ruleSession;

    protected QueryListenerHandle handle;

    protected String listenerName;

    public RuleFunctionQueryListener(
            String listenerName,
            RuleSession ruleSession,
            String callbackUri,
            Object closure) {

        if ((null == callbackUri) || (null == ruleSession)) {
            throw new IllegalArgumentException();
        }
        this.closure = closure;
        this.ruleSession = (RuleSessionImpl) ruleSession;
        this.listenerName = listenerName;
        this.ruleFunction = this.ruleSession.getRuleFunction(callbackUri);
    }

    public QueryListenerHandle getListenerHandle() {
        return this.handle;
    }

    public void onBatchEnd() {
        Object[] params = new Object[]{this.listenerName, true, false, null, this.closure};

        callRF(params);
    }

    public void onNewRow(Row row) {
        Object[] params =
                new Object[]{this.listenerName, false, false, row.getColumns(), this.closure};

        callRF(params);
    }


    public void onQueryEnd() {
        Object[] params = new Object[]{this.listenerName, false, true, null, this.closure};

        callRF(params);
    }

    public void onQueryStart(QueryListenerHandle handle) {
        this.handle = handle;
    }

    protected final void callRF(Object[] params) {
        RuleSession oldSession = RuleSessionManager.getCurrentRuleSession();
        try {
            RuleSessionManager.currentRuleSessions.set(ruleSession);

            ruleFunction.invoke(params);
        }
        finally {
            RuleSessionManager.currentRuleSessions.set(oldSession);
        }
    }
}
