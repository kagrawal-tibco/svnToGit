/**
 * 
 */
package com.tibco.cep.bemm.monitoring.metric.interceptor.impl;

import java.util.List;
import java.util.Map;

import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.monitoring.metric.interceptor.AbstractKeyBasedJmxInterceptor;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.Attribute;
import com.tibco.cep.bemm.monitoring.util.BeTeaAgentMonitorable;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;

/**
 * @author vasharma
 */
public class EventRateDeltaJmxInterceptor extends AbstractKeyBasedJmxInterceptor{
	
	@Override
	public void init(Agent agent, List<Attribute> attrList, Map<String, Object> genericMap ){
	}
	
	@Override
	public Attribute performAction(Attribute attr, Monitorable monitorable, List<Attribute> attrList, Map<String, Object> genericMap) {
		String deltaKey = prepareKey(monitorable, attr.getTargetMapping().getSource());
		Object deltaVal = 0;
		double interval=1;
		if(genericMap.get("entryPollInterval")!=null)
			interval=((int) genericMap.get("entryPollInterval"))/1000; //converting interval from millis to seconds 
		
		if(getValueMap().containsKey(deltaKey)){
			Object oldVal = getValueMap().get(deltaKey);
			deltaVal = MonitoringUtils.parseValueAndGetDiff(oldVal, attr.getValue(), attr.getTargetMapping().getDatatype());
		}else{
			deltaVal = MonitoringUtils.parseValue(deltaVal, attr.getTargetMapping().getDatatype());
		}
		getValueMap().put(deltaKey, attr.getValue());
		
		
		Double currentValue=(Double) deltaVal;
		Double eventRate=0d;
		if(interval>0){
			eventRate=(currentValue/interval); 
			attr.setValue(eventRate);
		}
		else
			attr.setValue(deltaVal);
		
		return attr;
	}

	@Override
	public Object performAction() {
		return null;
	}
	
	

}
