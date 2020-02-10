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

package com.tibco.cep.runtime.service.cluster.events.notification;

import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.txn.RtcKey;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransaction;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 23, 2008
 * Time: 12:07:37 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EntityMediator {
    boolean isListenerEnabled();

    boolean isPublisherEnabled();

    void startMediator(int excludeAgentId) throws Exception;

    void publish(RtcKey key, RtcTransaction txn, InferenceAgent agent) throws Exception;

    void shutdownMediation();

    void resumeMediation();

    void suspendMediation();

    boolean isMediationRunning();

    void activate();
}
