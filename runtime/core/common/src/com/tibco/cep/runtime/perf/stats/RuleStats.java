package com.tibco.cep.runtime.perf.stats;

public class RuleStats {

    private String uri;

    private long invocationCount;

//	private double successRate;

    private double averageConditionProcessingTime;

//	private double averageActionProcessingTime;

    private double averageProcessingTime;

    public String getURI() {
        return uri;
    }

    public void setURI(String uri) {
        this.uri = uri;
    }

    public long getInvocationCount() {
        return invocationCount;
    }

    public void setInvocationCount(long invocationCount) {
        this.invocationCount = invocationCount;
    }

//	public double getSuccessRate() {
//		return successRate;
//	}
//
//	public void setSuccessRate(double successRate) {
//		this.successRate = successRate;
//	}

    public double getAverageConditionProcessingTime() {
        return averageConditionProcessingTime;
    }

    public void setAverageConditionProcessingTime(double averageConditionProcessingTime) {
        this.averageConditionProcessingTime = averageConditionProcessingTime;
    }

//	public double getAverageActionProcessingTime() {
//		return averageActionProcessingTime;
//	}
//
//	public void setAverageActionProcessingTime(double averageActionProcessingTime) {
//		this.averageActionProcessingTime = averageActionProcessingTime;
//	}

    public double getAverageProcessingTime() {
        return averageProcessingTime;
    }

    public void setAverageProcessingTime(double averageProcessingTime) {
        this.averageProcessingTime = averageProcessingTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(RuleStats.class.getSimpleName());
        sb.append("[uri/signature=");
        sb.append(uri);
        sb.append(",invocationcount=");
        sb.append(invocationCount);
        sb.append(",avgproctime=");
        sb.append(averageProcessingTime);
        sb.append(",avgconditionproctime=");
        sb.append(averageConditionProcessingTime);
        sb.append("]");
        return sb.toString();
    }
}
