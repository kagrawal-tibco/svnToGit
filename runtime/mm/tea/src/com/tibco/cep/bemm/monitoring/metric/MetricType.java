package com.tibco.cep.bemm.monitoring.metric;


public enum MetricType {
	
    CPU_STATS("cpu"),
    MEMORY_STATS("memory"),
    THREAD_STATS("thread"),
    GC_STATS("gc"),
    SWAP("swap"),
    IO_STATS("io"),
    NETWORK_STATS("network"),
    VM_STATS("vm");

    private final String metricType; 
    
    MetricType(String metricType) {
    	this.metricType = metricType;
    }
    
    public String value() {
        return metricType;
    }

    public static MetricType fromValue(String v) {
        return valueOf(v);
    }

}
