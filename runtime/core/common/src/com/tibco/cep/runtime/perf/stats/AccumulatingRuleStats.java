package com.tibco.cep.runtime.perf.stats;

import java.util.concurrent.atomic.AtomicLong;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * A RuleStats alternative that collects total execution time and invocation counts, and uses it to calculate average.
 * @author moshaikh
 */
public final class AccumulatingRuleStats extends RuleStats {
	
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(AccumulatingRuleStats.class);

    private String uri;

    private AtomicLong invocationCount;

    private double averageConditionProcessingTime;

    private AtomicLong totalExecutionTime;
    
    public AccumulatingRuleStats(String uri) {
    	this.uri = uri;
    	this.invocationCount = new AtomicLong();
    	this.totalExecutionTime = new AtomicLong();
    }

    public String getURI() {
        return uri;
    }

    public long getInvocationCount() {
        return invocationCount.get();
    }

    public double getAverageProcessingTime() {
        return (double)totalExecutionTime.get()/invocationCount.get();
    }
    
    public void recordExecution(double executionTime) {
    	if(executionTime==0){
    		return;
    	}
		totalExecutionTime.addAndGet((long)executionTime);
		invocationCount.incrementAndGet();
	}
    
    public String toString() {
        StringBuilder sb = new StringBuilder(AccumulatingRuleStats.class.getSimpleName());
        sb.append("[uri/signature=");
        sb.append(uri);
        sb.append(",invocationcount=");
        sb.append(invocationCount);
        sb.append(",avgproctime=");
        sb.append(getAverageProcessingTime());
        sb.append(",avgconditionproctime=");
        sb.append(averageConditionProcessingTime);
        sb.append("]");
        return sb.toString();
    }
}
