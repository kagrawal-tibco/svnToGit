package com.tibco.cep.query.stream._retired_;

import java.util.Collection;

import com.tibco.cep.query.stream.cache.SharedObjectManager;
import com.tibco.cep.query.stream.impl.rete.input.QObjectManager;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.util.IdGenerator;

/*
 * Author: Ashwin Jayaprakash Date: Nov 15, 2007 Time: 6:20:15 PM
 */

public class InitialDataFeeder {
    protected final ReteQuery query;

    protected final boolean syncOrAsync;

    protected SharedObjectManager som;

    protected QObjectManager qom;

    protected FindMatchesRule[] findMatchesRules;

    public InitialDataFeeder(ReteQuery query, boolean syncOrAsync, SharedObjectManager som,
            QObjectManager qom) {
        this.query = query;
        this.syncOrAsync = syncOrAsync;
        this.som = som;
        this.qom = qom;
    }

    public ReteQuery getQuery() {
        return query;
    }

    public boolean isSyncOrAsync() {
        return syncOrAsync;
    }

    public FindMatchesRule[] createFindMatchesRules() throws Exception {
        Collection<String> classes = query.getReteEntityClassNames();
        findMatchesRules = new FindMatchesRule[classes.size()];
        int i = 0;
        for (String classNames : classes) {
            String ruleName = query.getName() + "$" + classNames;
            long trigger = IdGenerator.generateNewId();

            if (syncOrAsync) {
                findMatchesRules[i] = new SyncFindMatchesRule(query, ruleName, classNames, trigger,
                        som, qom);
            }
            else {
                findMatchesRules[i] = new AsyncFindMatchesRule(query, ruleName, classNames,
                        trigger, som, qom, 100);
            }

            findMatchesRules[i].init();

            i++;
        }

        return findMatchesRules;
    }

    public void end() throws Exception {
        for (FindMatchesRule findMatchesRule : findMatchesRules) {
            findMatchesRule.end();
        }
    }
}
