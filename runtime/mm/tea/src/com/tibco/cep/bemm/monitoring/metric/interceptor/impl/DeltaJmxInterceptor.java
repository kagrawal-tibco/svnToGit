/**
 * 
 */
package com.tibco.cep.bemm.monitoring.metric.interceptor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.monitoring.metric.interceptor.AbstractKeyBasedJmxInterceptor;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.Attribute;
import com.tibco.cep.bemm.monitoring.util.BeTeaAgentMonitorable;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;

/**
 * @author ssinghal
 *
 */
public class DeltaJmxInterceptor extends AbstractKeyBasedJmxInterceptor{

	/*Agent agent;
	List<Attribute> attrList=new ArrayList<Attribute>();*/
	
	@Override
	public void init(Agent agent, List<Attribute> attrList, Map<String, Object> genericMap ){
		/*this.agent = agent;
		this.attrList = attrList;*/
	}
	
	@Override
	public Attribute performAction(Attribute attr, Monitorable monitorable, List<Attribute> attrList, Map<String, Object> genericMap) {
		String appender =  getAppender(attrList) + "." +attr.getTargetMapping().getSource();
		String deltaKey = prepareKey(monitorable, appender);
		Object deltaVal = 0;
		
		if(getValueMap().containsKey(deltaKey)){
			Object oldVal = getValueMap().get(deltaKey);
			deltaVal = MonitoringUtils.parseValueAndGetDiff(oldVal, attr.getValue(), attr.getTargetMapping().getDatatype());
		}else{
			deltaVal = MonitoringUtils.parseValue(deltaVal, attr.getTargetMapping().getDatatype());
		}
		getValueMap().put(deltaKey, attr.getValue());
		attr.setValue(deltaVal);
		return attr;
	}

	private String getAppender(List<Attribute> attrList) {
		for(Attribute att : attrList)
		{
			if(att.getName().equals("destination")){
				return (String)att.getValue();
			}
		}
		return "";
	}

	@Override
	public Object performAction() {
		return null;
	}
	
	

}
