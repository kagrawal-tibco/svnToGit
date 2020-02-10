package com.tibco.cep.query.client.console.swing.util;

import com.tibco.cep.query.client.console.swing.model.integ.nodes.NodeCache;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 *
 * @author ksubrama
 */
public class Registry {
    private static Registry SINGLETON = new Registry();
    private BEClassLoader beClassLoader;
    private QuerySession querySession;
    private RuleServiceProvider ruleServiceProvider;
    private NodeCache nodeCache;

    private Registry() {
    }

    public static Registry getRegistry() {
        return SINGLETON;
    }

    public BEClassLoader getBeClassLoader() {
        return beClassLoader;
    }

    public void setBeClassLoader(BEClassLoader beClassLoader) {
        this.beClassLoader = beClassLoader;
    }

    public void setQuerySession(QuerySession querySession) {
        this.querySession = querySession;
    }

    public void setRuleServiceProvider(RuleServiceProvider ruleServiceProvider) {
        this.ruleServiceProvider = ruleServiceProvider;
    }

    public void setNodeCache(NodeCache cache) {
        this.nodeCache = cache;
    }

    public RuleServiceProvider getRuleServiceProvider() {
        return ruleServiceProvider;
    }

    public QuerySession getQuerySession() {
        return querySession;
    }

    public NodeCache getNodeCache() {
        return nodeCache;
    }
}
