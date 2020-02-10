package com.tibco.cep.dashboard.psvr.vizengine.formatters;

import com.tibco.cep.dashboard.psvr.alerts.AlertEvaluator;

public class DefaultDataConfigVisitor implements DataConfigVisitor {
	
	private AlertEvaluator alertEvaluator;
	
	private OutputFieldValueProcessor outputFieldValueProcessor;
	
	public DefaultDataConfigVisitor(){
		this(null,null);
	}
	
	public DefaultDataConfigVisitor(AlertEvaluator alertEvaluator){
		this(alertEvaluator, null);
	}

	public DefaultDataConfigVisitor(OutputFieldValueProcessor outputFieldValueProcessor){
		this(null,outputFieldValueProcessor);
	}
	
	public DefaultDataConfigVisitor(AlertEvaluator alertEvaluator, OutputFieldValueProcessor outputFieldValueProcessor){
		this.alertEvaluator = alertEvaluator;
		this.outputFieldValueProcessor = outputFieldValueProcessor;
	}

	@Override
	public AlertEvaluator getAlertEvaluator() {
		return alertEvaluator;
	}

	@Override
	public OutputFieldValueProcessor getFieldValueProcessor() {
		return outputFieldValueProcessor;
	}

}
