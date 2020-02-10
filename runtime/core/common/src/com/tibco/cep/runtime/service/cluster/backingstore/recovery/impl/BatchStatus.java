package com.tibco.cep.runtime.service.cluster.backingstore.recovery.impl;

import java.io.Serializable;

public enum BatchStatus implements Serializable {
	
	READY(1, "Ready"),
	ASSIGNED(2, "Assigned"),
	SUCCESS(4, "Success"),
	FAILED(5, "Failed"),
	RETRY(6, "Ready");
	
	private int status = 1;
	private String statusText = "";
	
	BatchStatus(int status, String statusText) {
		this.status = status;
		this.statusText = statusText;
	}
	
	public int value(){
		return this.status;
	}
	
	public String toString(){
		return statusText;
	}
}
