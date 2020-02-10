package com.tibco.cep.query.functions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.query.api.QueryConnection;
import com.tibco.cep.query.api.QueryException;
import com.tibco.cep.query.api.impl.local.QueryDriverImpl;
import com.tibco.cep.query.service.QuerySession;
import com.tibco.cep.query.service.impl.DelegatedQueryOM;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.impl.rete.integ.Manager;
import com.tibco.cep.query.stream.impl.rete.service.RegionManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

public class ConnectionCache {
    private static ConnectionCache singleton;

    private QueryDriverImpl driver;

    private ConcurrentHashMap<String, QueryConnection> urlToConnectionMap;

    private Properties cachedProperties;

    private ConnectionCache(Collection<QuerySession> querySessions,
                            Collection<String> regionManagerNames) {
        Manager manager = Registry.getInstance().getComponent(Manager.class);
        Map<String, RegionManager> regionManagers = manager.getRegionManagers();

        HashMap<String, QueryDriverImpl.Parameter> parameters =
                new HashMap<String, QueryDriverImpl.Parameter>();

        Iterator<QuerySession> sessionIter = querySessions.iterator();
        Iterator<String> rmNameIter = regionManagerNames.iterator();
        for (; sessionIter.hasNext() && rmNameIter.hasNext();) {
            QuerySession querySession = sessionIter.next();

            String regionManagerName = rmNameIter.next();
            RegionManager regionManager = regionManagers.get(regionManagerName);

            QueryDriverImpl.Parameter parameter =
                    new QueryDriverImpl.Parameter(regionManager, querySession);

            parameters.put(regionManagerName, parameter);
        }

        this.driver = new QueryDriverImpl(parameters);

        this.urlToConnectionMap = new ConcurrentHashMap<String, QueryConnection>();

        this.cachedProperties = new Properties();
    }

    private void doDiscard() {
        for (QueryConnection connection : urlToConnectionMap.values()) {
            connection.close();
        }

        urlToConnectionMap = null;
        driver = null;
    }

    /**
     * @param querySessions
     * @param regionManagerNames The order should correspond to the "querySessions" that were used
     *                           for the {@link com.tibco.cep.query.stream.impl.rete.service.RegionManager}s.
     *                           These will be used as the URLs in {@link #get(String)}.
     */
    public static void init(Collection<QuerySession> querySessions,
                            Collection<String> regionManagerNames) {
        if (singleton != null) {
            return;
        }

        singleton = new ConnectionCache(querySessions, regionManagerNames);
    }

    public static void discard() {
        singleton.doDiscard();
        singleton = null;
    }

    /**
     * {@link #init(java.util.Collection, java.util.Collection)} must be invoked the first time,
     * before this can be invoked.
     *
     * @return
     */
    public static ConnectionCache getInstance() {
        return singleton;
    }

    /**
     * Extracts the {@link com.tibco.cep.runtime.session.RuleSession} associated with the caller's
     * Thread and then gets the {@link com.tibco.cep.runtime.service.om.coherence.cluster.CacheAgent#getAgentName()}
     * as the URL.
     *
     * @return
     * @throws com.tibco.cep.query.api.QueryException
     *
     * @see #get(String)
     */
     public QueryConnection get() throws QueryException {
        final RuleSession ruleSession = RuleSessionManager.getCurrentRuleSession();
        final DelegatedQueryOM coherenceStore = ((DelegatedQueryOM) ruleSession.getObjectManager());

        return get(coherenceStore.getName());
    }

    /**
     * @param url
     * @return
     * @throws QueryException
     * @see #init(java.util.Collection, java.util.Collection)
     */
    public QueryConnection get(String url) throws QueryException {
        if (url == null) {
            throw new IllegalArgumentException();
        }

        QueryConnection connection = urlToConnectionMap.get(url);
        if (connection != null) {
            return connection;
        }

        //----------

        connection = driver.connect(url, cachedProperties);

        QueryConnection otherConn = urlToConnectionMap.putIfAbsent(url, connection);
        //Oops! We were beaten by another Thread. So, discard our instance.
        if (otherConn != null) {
            connection.close();

            connection = otherConn;
        }

        return connection;
    }
}
