package com.tibco.cep.runtime.perf.stats.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.perf.stats.RuleStats;
import com.tibco.cep.runtime.perf.stats.StatsAggregator;
import com.tibco.cep.runtime.util.FQName;

public class EnginePerfStatsAggregator implements StatsAggregator {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(EnginePerfStatsAggregator.class);

    static int MATCHING_TERM_LOCATION = 4;

    private static String MATCHING_TERM_VALUE = "InferenceAgent";

    private long rtcCount;

    private double avgRTCTime;

    private ConcurrentHashMap<String, RuleStats> ruleStatsMap;

    public EnginePerfStatsAggregator() {
        rtcCount = 0;
        avgRTCTime = 0;
        ruleStatsMap = new ConcurrentHashMap<String, RuleStats>();
    }

    public long getTotalRTCs() {
        return rtcCount;
    }
    
    protected void setTotalRTCs(long rtcCount){
    	this.rtcCount = rtcCount;
    }

    public double getAverageRTCTime() {
        return avgRTCTime;
    }

    protected void setAverageRTCTime(double avgRTCTime){
    	this.avgRTCTime = avgRTCTime;
    }
    
    public Collection<RuleStats> getRuleStats() {
        return new LinkedList<RuleStats>(ruleStatsMap.values());
    }
    
    

    @Override
    public boolean willAccept(FQName name) {
        String[] names = name.getComponentNames();
        //do we have enough names ?
        if (names.length > MATCHING_TERM_LOCATION) {
            //yes, we do !! is the matching term matching ?
            if (names[MATCHING_TERM_LOCATION].equals(MATCHING_TERM_VALUE) == true) {
                //yes it does, do call type and call identifier match
                String callType = names.length > MATCHING_TERM_LOCATION ? names[MATCHING_TERM_LOCATION + 1] : null;
                String callIdentifier = names.length > MATCHING_TERM_LOCATION + 2 ? names[MATCHING_TERM_LOCATION + 2] : null;
                if (callType.indexOf("Rule") != -1 || ("General".equals(callType) == true && "Rete".equals(callIdentifier) == true)) {
                    //yes we are dealing with a rule stat or a rete general stat, accept it
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void aggregate(long time, FQName name, Data data) {
        String[] names = name.getComponentNames();
        String callType = names.length > MATCHING_TERM_LOCATION ? names[MATCHING_TERM_LOCATION + 1] : null;
        String callIdentifier = names.length > MATCHING_TERM_LOCATION + 2 ? names[MATCHING_TERM_LOCATION + 2] : null;
        if (callType == null || callIdentifier == null) {
        	return;
        }
        if ("Rule".equals(callType) == true) {
        	RuleStats stats = ruleStatsMap.get(callIdentifier);
            if (stats == null) {
                stats = new RuleStats();
                stats.setURI(callIdentifier);
                ruleStatsMap.put(callIdentifier, stats);
            }
            double currAvg = stats.getAverageProcessingTime();
            double newAvg = ((Double) data.getColumns()[3] + currAvg)/2;
            stats.setAverageProcessingTime(newAvg);
            stats.setInvocationCount(stats.getInvocationCount()+1);
            if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
                LOGGER.log(Level.DEBUG, "%s::%s -> %s",getClass().getSimpleName(), callType, stats.toString());
            }
        }
        else if (("RuleFilterCondition".equals(callType) == true) || ("RuleJoinCondition".equals(callType) == true)) {
        	RuleStats stats = ruleStatsMap.get(callIdentifier);
            if (stats == null) {
                stats = new RuleStats();
                stats.setURI(callIdentifier);
                ruleStatsMap.put(callIdentifier, stats);
            }
            double currAvg = stats.getAverageConditionProcessingTime();
            double newAvg = ((Double) data.getColumns()[3] + currAvg)/2;
            stats.setAverageConditionProcessingTime(newAvg);
//            stats.setInvocationCount(stats.getInvocationCount()+1);
            if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
            	LOGGER.log(Level.DEBUG, "%s::%s -> %s",getClass().getSimpleName(), callType, stats.toString());
            }
        }
        else if ("General".equals(callType) == true && "Rete".equals(callIdentifier) == true) {
            rtcCount = rtcCount + (Long)data.getColumns()[2];
            avgRTCTime = (avgRTCTime + ((Double) data.getColumns()[3]))/2;
            if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
                LOGGER.log(Level.DEBUG, "%s::No Of RTCs %d with average proc time of %f", getClass().getSimpleName(), rtcCount, avgRTCTime);
            }
        }
    }

    @Override
    public void close() {
        ruleStatsMap.clear();
    }

}