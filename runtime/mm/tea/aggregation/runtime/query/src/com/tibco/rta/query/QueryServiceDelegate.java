package com.tibco.rta.query;

import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.session.ServerSession;
import com.tibco.rta.common.service.session.ServerSessionManager;
import com.tibco.rta.common.service.session.ServerSessionRegistry;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.service.query.QueryService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 7/2/13
 * Time: 10:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class QueryServiceDelegate {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_SERVICES_QUERY.getCategory());

    private QueryService delegatedService = null;

    public static final QueryServiceDelegate INSTANCE = new QueryServiceDelegate();

    private QueryServiceDelegate() {
        try {
            this.delegatedService = ServiceProviderManager.getInstance().getQueryService();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    public String registerQuery(QueryDef queryDef) throws Exception {
        return delegatedService.registerQuery(queryDef);
    }


    public String registerQuery(String publisherId, QueryDef queryDef) throws Exception {
        ServerSession serverSession = ServerSessionRegistry.INSTANCE.getServerSession(publisherId);
        if (serverSession == null) {
            String errorMessage = String.format("No server session found with id [%s]", publisherId);
            if (LOGGER.isEnabledFor(Level.WARN)) {
                LOGGER.log(Level.WARN, errorMessage);
            }
            throw new Exception(errorMessage);
        }
        ServerSessionManager.serverSessions.set(serverSession);
        serverSession.registerQuery(queryDef);
        return delegatedService.registerQuery(queryDef);
    }

    public QueryDef unregisterQuery(String publisherId, String queryName) throws Exception {
        ServerSession serverSession = ServerSessionRegistry.INSTANCE.getServerSession(publisherId);
        ServerSessionManager.serverSessions.set(serverSession);
        //Remove from session
        QueryDef tobeRemoved = delegatedService.unregisterQuery(queryName);
        if (tobeRemoved != null) {
            serverSession.unregisterQuery(tobeRemoved);
        }
        return tobeRemoved;
    }

    public boolean hasNext(String correlationId) throws Exception {
        return delegatedService.hasNext(correlationId);
    }

    public List<QueryResultTuple> getNext(String correlationId) throws Exception {
        return delegatedService.getNext(correlationId);
    }

    public void removeBrowserMapping(String publisherId, String correlationId) {
        ServerSession serverSession = ServerSessionRegistry.INSTANCE.getServerSession(publisherId);
        ServerSessionManager.serverSessions.set(serverSession);
        delegatedService.removeBrowserMapping(correlationId);
    }
}
