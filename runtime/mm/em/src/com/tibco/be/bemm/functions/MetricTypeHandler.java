package com.tibco.be.bemm.functions;

import java.io.IOException;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.SimpleEvent;

public abstract class MetricTypeHandler {

	protected String type;
	protected String monitoredEntityName;
	protected long delay;
	protected Logger logger;
	
	public MetricTypeHandler() {
		delay = 30 * 1000; //by default the delay is 30 seconds
	}
	
	void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	void setMonitoredEntityName(String monitoredEntityName){
		this.monitoredEntityName = monitoredEntityName;
	}
	
	void setDelay(long delay){
		this.delay = delay;
	}
	
	String getType() {
		return type;
	}	
	
	protected abstract void init() throws IOException;	

	protected abstract SimpleEvent[] populate(EventCreator eventCreator) throws IOException;
	
	protected double limit(double d,int decimalPlaces){
		double i = Math.pow(10, decimalPlaces);
		return Math.round(d*i)/i;
	}

}