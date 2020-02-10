package com.tibco.cep.bemm.monitoring.metric.probe.accumulator;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.AccumulatorPlugin;
import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.Mapping;
import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.MultiMapping;
import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.Property;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.jmx.JmxBeanManager;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.jmx.JmxException;
import com.tibco.cep.bemm.monitoring.util.BeTeaAgentMonitorable;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;
import com.tibco.rta.Fact;


abstract public class AbstractAccumulator {

	
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(AbstractAccumulator.class);

	abstract public void init(AccumulatorPlugin accumulatorConfig) throws Exception;

	abstract public void stop();
	
	abstract public void publishFacts(List<Mapping> outputMapperList,List<MultiMapping> list, Map<Monitorable,Object> monitorableEntitiesRegistry, String schemaName);	
	
	protected JmxBeanManager getBeanManager(
			Monitorable monitorable) throws IOException, JmxException {
		String jmxServiceUrl="";
		String[] credentials=new String[]{"",""};
		if(monitorable!=null) {
			if(monitorable instanceof Agent){
				ServiceInstance instance=((Agent)monitorable).getInstance();
				jmxServiceUrl=MonitoringUtils.getJmxServiceUrl(instance.getHost().getHostIp(), instance.getJmxPort());
				String jmxUsername=instance.getJmxUserName();
				String jmxPassword=instance.getJmxPassword();
				String decodedPassword = ManagementUtil.getDecodedPwd(jmxPassword);
				credentials=new String[]{jmxUsername,decodedPassword};
			}
			else if(monitorable instanceof BeTeaAgentMonitorable){
				BeTeaAgentMonitorable beTeaAgent=((BeTeaAgentMonitorable)monitorable);
				jmxServiceUrl=MonitoringUtils.getJmxServiceUrl(beTeaAgent.getHostIp(), beTeaAgent.getJmxPort());
				String jmxUsername=beTeaAgent.getJmxUserName();
				String jmxPassword=beTeaAgent.getJmxPassword();
				String decodedPassword = ManagementUtil.getDecodedPwd(jmxPassword);
				credentials=new String[]{jmxUsername,decodedPassword};
			}
		}

		return new JmxBeanManager(jmxServiceUrl,credentials);
	}
	

	protected void publishFact(Fact fact) throws ObjectCreationException, Exception{
		if(fact == null){
			return;
		}
		BEMMServiceProviderManager.getInstance().getAggregationService().getSession().publishFact(fact);
	}

	protected Fact getFact(List<Attribute> factAttr) throws Exception {
		Fact fact = BEMMServiceProviderManager.getInstance().getAggregationService().createFact();
		if (fact == null) {
			return null;
		}
		for (Attribute attribute : factAttr){
			try {
				fact.setAttribute(attribute.getName(),attribute.getValue());
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.PREPARING_FACT_ERROR),e);
				return null;
			}
		}
		return fact;
	}
	
	protected Fact getFact(List<Attribute> factAttr,String schemaName) throws Exception {
		Fact fact = BEMMServiceProviderManager.getInstance().getAggregationService().createFact(schemaName);
		if (fact == null) {
			return null;
		}
		for (Attribute attribute : factAttr){
			try {
				fact.setAttribute(attribute.getName(),attribute.getValue());
			} catch (Exception e) {
				LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.PREPARING_FACT_ERROR),e);
				return null;
			}
		}
		return fact;
	}
	
	public String getPropertyValue(String propertyName, List<Property> propList)
	{
		for(Property prop : propList) {
			if(prop!=null)
				if(prop.getName().equals(propertyName))
					return prop.getValue();
		}
		return "";
	}
	
	

}
