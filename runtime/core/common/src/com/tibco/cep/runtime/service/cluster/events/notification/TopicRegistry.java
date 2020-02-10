/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.events.notification;

import java.util.Collection;

import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.Cluster;

/**
 * A registry interested notifications (typeId)s per CacheAgent.
 * The TypeIds are calculated based on the ruleset that the CacheAgent is interested in.
 * Also the TypeIds are matched to Cache+WM.
 */
public interface TopicRegistry {


    public void init(Cluster cluster) throws Exception;
    /**
     * Register a list of topic(Changes and deletes) for a given CacheAgent
     *
     * @param agent
     * @param topicsChange An int array containing typeId
     * @param topicsDelete An int array containing typeId
     */
    void register(CacheAgent agent, Collection<Integer> topicsChange, Collection<Integer> topicsDelete);

    void register(CacheAgent agent, int[] topicsChange, int[] topicsDelete);

    boolean isRegisteredForChange(int typeId);

    boolean isRegisteredForDelete(int typeId);

    public int[] encodeTopic(Collection<Integer> typeIds);

    void start() throws Exception;
    
    public int numRegistered();
}
