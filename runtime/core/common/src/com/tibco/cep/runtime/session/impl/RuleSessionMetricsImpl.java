package com.tibco.cep.runtime.session.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionMetrics;

/*
* Author: Suresh Subramani / Date: 1/17/12 / Time: 1:58 PM
*/
public class RuleSessionMetricsImpl implements RuleSessionMetrics{

    RuleSession session;
    ObjectManager objectManager;
    ConcurrentHashMap<Long, AtomicLong> inRtcMetric = new ConcurrentHashMap<Long, AtomicLong>();
    ConcurrentHashMap<Long, AtomicLong> postRtcMetric = new ConcurrentHashMap<Long, AtomicLong>();
    ConcurrentHashMap<Long, AtomicLong> preRtcMetric = new ConcurrentHashMap<Long, AtomicLong>();

    public RuleSessionMetricsImpl(RuleSession ruleSession)
    {
        this.session = ruleSession;
        this.objectManager = ruleSession.getObjectManager();
    }

    @Override
    public void setMetric(MetricType metricType, int typeId, long timems) {

        if (objectManager instanceof DistributedCacheBasedStore) {
            updateDistributedCacheBasedRuleSessionMetrics(metricType, typeId, timems);
        }
        else {
            updateInMemoryBasedRuleSessionMetrics(metricType, typeId, timems);
        }
    }

    private void updateDistributedCacheBasedRuleSessionMetrics(MetricType metricType, int typeId, long timems) {
        InferenceAgent ia = (InferenceAgent) ((DistributedCacheBasedStore) session.getObjectManager()).getCacheAgent();

        switch (metricType) {
            case PRERTC:
                ia.agentStats.incrementPreRTC(typeId, timems);
                break;
            case INRTC:
                ia.agentStats.incrementInRTC(typeId, timems);
                break;
            case POSTRTC:
                ia.agentStats.incrementPostRTC(typeId, timems);
                break;
        }
    }

    private void updateInMemoryBasedRuleSessionMetrics(MetricType metricType, long typeId, long timems) {
        //HUGO:Please complete this.
        switch(metricType) {
            case PRERTC:
            {
                AtomicLong aLong = preRtcMetric.get(typeId);
                if (aLong == null) {
                    aLong = preRtcMetric.putIfAbsent(typeId, new AtomicLong(timems));
                    if (aLong == null) return;

                }

            }
        }
    }
}
