/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 27/8/2010
 */

package com.tibco.cep.runtime.service.cluster.agent.tasks;

import java.util.Collection;
import java.util.LinkedList;

import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.PreprocessContext;

/**
 * Created by IntelliJ IDEA. User: apuneet Date: Apr 29, 2008 Time: 11:17:39 AM To change this
 * template use File | Settings | File Templates.
 */
abstract public class AgentActionManager implements IAgentActionManager
{
    public static boolean hasActionManager(RuleSession ruleSession) {
        PreprocessContext preContext = PreprocessContext.getContext();
        if (preContext == null) {
            if (ruleSession.getObjectManager() instanceof DistributedCacheBasedStore) {
                return true;
            }
        }

        return false;
    }

    public static void addAction(RuleSession ruleSession, AgentAction action) {
        PreprocessContext preContext = PreprocessContext.getContext();
        if (preContext == null) {
            if (ruleSession.getObjectManager() instanceof DistributedCacheBasedStore) {
                DistributedCacheBasedStore coherenceStore = ((DistributedCacheBasedStore) ruleSession.getObjectManager());
                InferenceAgent ia = (InferenceAgent) coherenceStore.getCacheAgent();
                ia.addAgentAction(action);

                return;
            }
        }

        throw new RuntimeException("addAction [" + action + "] not allowed in preprocessor");
    }
    
    public static void clearActions(RuleSession ruleSession) {
        PreprocessContext preContext = PreprocessContext.getContext();
        if (preContext == null) {
            if (ruleSession.getObjectManager() instanceof DistributedCacheBasedStore) {
                DistributedCacheBasedStore coherenceStore = ((DistributedCacheBasedStore) ruleSession.getObjectManager());
                InferenceAgent ia = (InferenceAgent) coherenceStore.getCacheAgent();
                ia.clearAgentActions();
                return;
            }
        }

        throw new RuntimeException("clearActions not allowed in preprocessor");
    }
}
