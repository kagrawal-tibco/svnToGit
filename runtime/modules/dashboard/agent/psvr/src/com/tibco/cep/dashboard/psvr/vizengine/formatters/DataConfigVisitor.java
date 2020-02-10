package com.tibco.cep.dashboard.psvr.vizengine.formatters;

import com.tibco.cep.dashboard.psvr.alerts.AlertEvaluator;

public interface DataConfigVisitor {
	
	public AlertEvaluator getAlertEvaluator();
	
	public OutputFieldValueProcessor getFieldValueProcessor();

}
