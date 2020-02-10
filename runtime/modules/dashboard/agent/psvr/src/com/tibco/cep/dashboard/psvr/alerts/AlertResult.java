package com.tibco.cep.dashboard.psvr.alerts;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.dashboard.common.utils.StringUtil;

public class AlertResult {
	
	private boolean success;
	
	private String message;
	
	private Throwable cause;
	
	private Map<String, String> resultsMap;
	
	public AlertResult(String message,Throwable cause){
		resultsMap = new HashMap<String, String>();
		success = false;
		this.message = message;
		this.cause = cause;
		
	}
	
	public AlertResult(){
		resultsMap = new HashMap<String, String>();
		success = true;
	}
	
	public final boolean isSuccess() {
		return success;
	}

	public final String getMessage() {
		return message;
	}

	public final Throwable getCause() {
		return cause;
	}

	public final String getValue(String key) {
		String value = (String) resultsMap.get(key);
		if (StringUtil.isEmptyOrBlank(value) == true) {
			return null;
		}
		return value;
	}

	public final void setValue(String key,String value) {
		if (StringUtil.isEmptyOrBlank(key) == true){
			throw new IllegalArgumentException("Key cannot be empty or blank");
		}
		resultsMap.put(key,value);
	}	
	
}