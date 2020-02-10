package com.tibco.cep.runtime.service.cluster.om;

import com.tibco.cep.kernel.core.rete.BeTransactionHook;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.runtime.session.RuleSession;

/*
* Author: Ashwin Jayaprakash / Date: 10/28/11 / Time: 4:34 PM
*/
public abstract class AbstractBeTransactionHook implements BeTransactionHook {
    protected AbstractBeTransactionHook() {
    }

    @Override
    public void cleanupTxnWorkArea(Object ruleSession) {
        if (ruleSession == null) {
            return;
        }

        ObjectManager om = ((RuleSession) ruleSession).getObjectManager();
        if (om instanceof DistributedCacheBasedStore) {
            ((DistributedCacheBasedStore) om).resetThreadLocalMaps();
        }
    }
}
