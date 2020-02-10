package com.tibco.cep.runtime.service.cluster.backingstore.recovery;

public enum RecoveryStatus {

    LOADING_DONE(1 ,"Completed Recovery"),
    PARTIAL_LOADING_DONE(2, "Completed Partial Recovery"),
    LOADING_ERROR(3, "Recovery Error"),
    SCHEDULE_FOR_LOADING(4, "Registered For Recovery"),
    LOADING_IN_PROGRESS(5, "Started Recovery");
    
    private int status;
    private String summary;
    
    RecoveryStatus(int status, String summary) {
    	this.status = status;
    	this.summary = summary;
    }
    
    public int intValue() {
    	return this.status;
    }
    
    public String summary() {
    	return this.summary;
    }
}
