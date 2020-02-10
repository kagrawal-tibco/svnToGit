/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.agent.mm;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 18, 2008
 * Time: 10:43:37 AM
 * To change this template use File | Settings | File Templates.
 */
public interface InferenceAgentEntityStatsMBean {
    public String getCacheMode();

    long getNumRecovered();

    long getNumAssertedFromChannel();

    long getNumAssertedFromAgents();

    double getAvgTimePreRTC();

    double getAvgTimeInRTC();

    double getAvgTimePostRTC();

    long getNumHitsInL1Cache();

    long getNumMissesInL1Cache();

    long getNumModifiedFromChannel();

    long getNumModifiedFromAgents();

    long getNumRetractedFromChannel();

    long getNumRetractedFromAgents();
}
