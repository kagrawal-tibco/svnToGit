package com.tibco.cep.bemm.monitoring.metric.interceptor.impl;

import java.util.List;
import java.util.Map;

import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.cep.bemm.monitoring.metric.interceptor.AbstractKeyBasedJmxInterceptor;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.Attribute;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;
import com.tibco.tea.agent.be.util.BETeaAgentProps;


/**
 * @author vasharma
 *
 */
public class RuleNameJmxInterceptor extends AbstractKeyBasedJmxInterceptor{
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(RuleNameJmxInterceptor.class);

	
	@Override
	public void init(Agent agent, List<Attribute> attrList, Map<String, Object> genericMap ){}
	
	@Override
	public Attribute performAction(Attribute attr, Monitorable monitorable, List<Attribute> attrList, Map<String, Object> genericMap) {
		String delimitedRuleName=(String) attr.getValue();

		if(MetricAttribute.BE_TEA_AGENT_RULE_NAME.equals(attr.getTargetMapping().getTarget())){
			attr.setValue(MonitoringUtils.getRuleName(delimitedRuleName));
		}
		else if(MetricAttribute.BE_TEA_AGENT_RULE_ENTITY.equals(attr.getTargetMapping().getTarget())){
			attr.setValue(MonitoringUtils.getEntityFromRuleName(delimitedRuleName));
		}
		else if(MetricAttribute.BE_TEA_AGENT_RULE_APP.equals(attr.getTargetMapping().getTarget())){
			attr.setValue(MonitoringUtils.getAppFromRuleName(delimitedRuleName));
		}

		printMap(attr);
		return attr;

	}
	
	private void printMap(Attribute attr){
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, "[Attr Name :"+attr.getName()+"]");
			LOGGER.log(Level.DEBUG, "[Attr Val  :"+attr.getValue()+"]");
		}
	}

	@Override
	public Object performAction() {
		return null;
	}
	
	

}
