package com.tibco.cep.query.client.console.swing.util;

import com.tibco.cep.runtime.session.RuleSessionManager;

/**
 *
 * @author ksubrama
 */
public class SessionUtil {

    public SessionUtil() {
    }

    public static void initSession() {
        RuleSessionManager.currentRuleSessions.set(
                Registry.getRegistry().getQuerySession().getRuleSession());
    }
}
