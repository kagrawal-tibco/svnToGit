/**
 * 
 */
package com.tibco.cep.bemm.monitoring.metric.interceptor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.Attribute;
import com.tibco.cep.bemm.monitoring.util.BeTeaAgentMonitorable;


/**
 * @author ssinghal
 *
 */
public abstract class AbstractKeyBasedJmxInterceptor implements TeaJmxInterceptor{
	
	private Map<String, Object> valueMap = new ConcurrentHashMap<String, Object>();

	public Map<String, Object> getValueMap() {
		return valueMap;
	}

	protected String prepareKey(Monitorable monitorable, String appender) {
		StringBuffer deltaKey = new StringBuffer();
		if(monitorable instanceof Agent){
			Agent agent=(Agent)monitorable;
			deltaKey.append(agent.getInstance().getHost().getApplication().getName() + ".");
			deltaKey.append(agent.getInstance().getName() + ".");
			deltaKey.append(agent.getInstance().getPuId() + ".");
			deltaKey.append(agent.getAgentType().getType() + ".");
			deltaKey.append(agent.getAgentName() + ".");
			deltaKey.append(appender);
		}
		else if(monitorable instanceof BeTeaAgentMonitorable){
			BeTeaAgentMonitorable agent=(BeTeaAgentMonitorable)monitorable;
			deltaKey.append(agent.getName() + ".");
			deltaKey.append(appender);
		}
		return deltaKey.toString();
	}
	
}
