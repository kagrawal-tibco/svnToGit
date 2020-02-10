/**
 * 
 */
package com.tibco.cep.bemm.monitoring.metric.interceptor;

import java.util.List;
import java.util.Map;

import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.Attribute;

/**
 * @author ssinghal
 *
 */
public interface TeaJmxInterceptor {
	
	public void init(Agent key, List<Attribute> attrList, Map<String, Object> genericMap);
	
	public Object performAction();
	
	public Object performAction(Attribute attr, Monitorable key, List<Attribute> attrList, Map<String, Object> genericMap);

}
