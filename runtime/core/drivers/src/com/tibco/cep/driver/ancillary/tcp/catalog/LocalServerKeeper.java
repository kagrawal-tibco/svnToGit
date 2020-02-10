package com.tibco.cep.driver.ancillary.tcp.catalog;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.tibco.cep.driver.ancillary.api.Session;
import com.tibco.cep.driver.ancillary.api.SessionManager;
import com.tibco.cep.driver.ancillary.tcp.server.TCPServer;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/*
* Author: Ashwin Jayaprakash Date: Mar 20, 2009 Time: 1:31:41 PM
*/
public class LocalServerKeeper implements SessionManager.SessionListener {
    protected TCPServer tcpServer;

    protected ConcurrentHashMap<String, Session> allSessions;

    protected RuleSessionImpl ruleSession;

    protected ExecutorService generalExecutorService;

    protected volatile RuleFunction sessionListenerRuleFunction;

    protected volatile SessionManager.SessionListener secondaryListener;

    public void init(TCPServer tcpServer, RuleSessionImpl ruleSession) {
        this.tcpServer = tcpServer;
        this.tcpServer.setListener(this);

        this.allSessions = new ConcurrentHashMap<String, Session>();
        this.ruleSession = ruleSession;
        this.generalExecutorService = tcpServer.getGeneralExecutorService();
    }

    public void start(RuleFunction sessionListenerRuleFunction,
                      SessionManager.SessionListener secondaryListener) {
        this.sessionListenerRuleFunction = sessionListenerRuleFunction;
        this.secondaryListener = secondaryListener;
    }

    public RuleSession getRuleSession() {
        return ruleSession;
    }

    public RuleFunction getSessionListenerRuleFunction() {
        return sessionListenerRuleFunction;
    }

    public TCPServer getTcpServer() {
        return tcpServer;
    }

    public ConcurrentMap<String, Session> getAllSessions() {
        return allSessions;
    }

    public void onNewSession(Session session) throws Exception {
        final String sessionId = session.getId();

        allSessions.put(sessionId, session);
        secondaryListener.onNewSession(session);

        Future job = generalExecutorService.submit(
                new Runnable() {
                    public void run() {
                        try {
                            ruleSession.invokeFunction(sessionListenerRuleFunction,
                                    new Object[]{tcpServer.getId(), sessionId}, false);
                        }
                        catch (Throwable t) {
                            Logger logger = ruleSession.getRuleServiceProvider().getLogger(this.getClass());

                            String s = "Error occurred in the Session Listener RuleFunction [%s]." +
                                    " The session [%s] must be cleared soon to avoid a resource leak.";
                            s = String.format(s, sessionListenerRuleFunction.getSignature(),
                                    sessionId);

                            logger.log(Level.ERROR, t, s);
                        }
                    }
                });
    }

    public void removeSession(Session session) {
        secondaryListener.removeSession(session);

        allSessions.remove(session.getId());
    }

    public void discard() {
        allSessions.clear();
    }
}
