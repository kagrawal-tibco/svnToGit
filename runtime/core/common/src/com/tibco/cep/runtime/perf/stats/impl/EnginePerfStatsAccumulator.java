package com.tibco.cep.runtime.perf.stats.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.perf.stats.RuleStats;
import com.tibco.cep.runtime.perf.stats.AccumulatingRuleStats;
import com.tibco.cep.runtime.util.FQName;

/**
 * A stats aggregator that accumulates the numbers from the starting..
 * 
 * @author moshaikh
 */
public class EnginePerfStatsAccumulator extends EnginePerfStatsAggregator {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(EnginePerfStatsAccumulator.class);

	private ConcurrentHashMap<String, AccumulatingRuleStats> ruleStatsMap;

	public EnginePerfStatsAccumulator() {
		ruleStatsMap = new ConcurrentHashMap<String, AccumulatingRuleStats>();
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
			String uri = callIdentifier;
			double processingTime = (Double) data.getColumns()[3];
			AccumulatingRuleStats ruleStats2 = getOrCreateRuleStats(uri);
			ruleStats2.recordExecution(processingTime);
		}
		else if (("RuleFilterCondition".equals(callType) == true) || ("RuleJoinCondition".equals(callType) == true)) {
			AccumulatingRuleStats stats = ruleStatsMap.get(callIdentifier);
            if (stats == null) {
                stats = new AccumulatingRuleStats(callIdentifier);
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
        	setTotalRTCs(getTotalRTCs() +  (Long)data.getColumns()[2]);
        	setAverageRTCTime((getAverageRTCTime() + ((Double) data.getColumns()[3]))/2);
        	
            if (LOGGER.isEnabledFor(Level.DEBUG) == true) {
                LOGGER.log(Level.DEBUG, "%s::No Of RTCs %d with average proc time of %f", getClass().getSimpleName(), getTotalRTCs(), getAverageRTCTime());
            }
        }
	}

	@Override
	public Collection<RuleStats> getRuleStats() {
		return new LinkedList<RuleStats>(ruleStatsMap.values());
	}

	private AccumulatingRuleStats getOrCreateRuleStats(String ruleUri) {
		AccumulatingRuleStats stats = ruleStatsMap.get(ruleUri);
		if (stats == null) {
			stats = ruleStatsMap.putIfAbsent(ruleUri, new AccumulatingRuleStats(ruleUri));
		}
		return stats != null ? stats : ruleStatsMap.get(ruleUri);
	}
}