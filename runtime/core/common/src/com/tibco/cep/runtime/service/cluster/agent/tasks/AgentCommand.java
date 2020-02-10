/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.agent.tasks;

import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Jan 23, 2009
 * Time: 9:36:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AgentCommand {
    void execute(InferenceAgent cacheAgent);
}
