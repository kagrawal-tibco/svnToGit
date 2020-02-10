package com.tibco.cep.dashboard.psvr.vizengine.formatters;

import java.util.Map;

import com.tibco.cep.dashboard.common.data.FieldValue;

public interface OutputFieldValueProcessor {

	public static enum KEY {DISPLAY,TOOLTIP};
	
    public Map<KEY, Object> process(String fieldName, FieldValue value);
    
}