package com.tibco.rta.bemm.actions;

import java.util.Map;
import java.util.Properties;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;
import com.tibco.rta.Fact;
import com.tibco.rta.RtaSession;
import com.tibco.rta.action.ActionUtil;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.runtime.model.MetricNodeEvent;
import com.tibco.rta.runtime.model.rule.AbstractActionImpl;
import com.tibco.rta.runtime.model.rule.Action;
import com.tibco.rta.runtime.model.rule.ActionHandlerContext;
import com.tibco.rta.runtime.model.rule.Rule;
import com.tibco.tea.agent.be.util.BEEntityDimensions;
import com.tibco.tea.agent.be.util.BEEntityHealthStatus;
import com.tibco.tea.agent.be.util.BETeaAgentProps;

/*
 * @author vasharma
 */

public class SetHealthAction implements ActionHandlerContext  {

	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(SetHealthAction.class);


	@Override
	public String getName() {
		return "Set-Health-Action";
	}

	@Override
	public void init(Properties paramProperties) {	
	}

	@Override
	public void stop() {		
	}

	@Override
	public Action getAction(Rule rule, ActionDef actionDef) {
		return new SetHealthActionImpl(rule, actionDef);
	}

	class SetHealthActionImpl extends AbstractActionImpl {


		public SetHealthActionImpl(Rule rule, ActionDef actionDef) {
			super(rule, actionDef);
		}

		@Override
		public ActionHandlerContext getActionHandlerContext() {
			return SetHealthAction.this;
		}

		@Override
		public String getAlertType() {
			return "Set-Health-Action";
		}

		@Override
		public void performAction(Rule rule, MetricNodeEvent nodeEvent) throws Exception {
			Map<String,Object> actionAttributes=threadLocalOfAction.get();
			actionAttributes.clear();//remove previous values set by this thread.
			
			String healthParam = "";
			String healthEntity="";
			String entity = MonitoringUtils.getEntityFromRuleName(rule.getName());
			String healthValue = (String)getFunctionParamValue(MetricAttribute.ACTION_HEALTH_VALUE).getValue();
			String alertText;


			if(healthValue!=null && !healthValue.isEmpty())
			{
				String healthDisplayName=BEEntityHealthStatus.valueOf(healthValue)==null?"":BEEntityHealthStatus.valueOf(healthValue).getDisplayName();

				
				try {
					if(entity != null && !entity.isEmpty()) {
						@SuppressWarnings("unchecked")
						Map<String,Object> entityProps=(Map<String, Object>)BEMMServiceProviderManager.getInstance().getMetricRuleService().getEntityAttrMap(entity);
						if(entityProps!=null){
							healthParam=(String) entityProps.get("healthParam");
							healthEntity=(String) entityProps.get("healthEntity");
							if(nodeEvent.getMetricNode().getKey() instanceof MetricKeyImpl) {
								setEntityHealth((MetricKeyImpl)nodeEvent.getMetricNode().getKey(), entity, healthValue,healthEntity);
								RtaSession session = BEMMServiceProviderManager.getInstance().getAggregationService().getSession();
								if(session != null) {
									RtaSchema schema = session.getSchema(BETeaAgentProps.BEMM_FACT_SCHEMA);
									if(schema != null) {
										Fact fact = getSetHealthFact(BEMMServiceProviderManager.getInstance().getAggregationService().getDefaultSchema(), ((MetricKeyImpl)nodeEvent.getMetricNode().getKey()), healthParam, healthValue);
										session.publishFact(fact);
									}
								}
							}
						}
					}
					//Setting alert text
					alertText=ActionUtil.substituteAlertTextTokens((String)getFunctionParamValue(MetricAttribute.ALERT_TEXT).getValue(),rule,nodeEvent.getMetricNode(),isSetAction(),actionDef,healthDisplayName);
					actionAttributes.put(ActionUtil.ACTION_ALERT_TEXT, alertText);
					
					MetricKeyImpl key=(MetricKeyImpl)nodeEvent.getMetricNode().getKey();
					String alertDtls = alertText; //by default, in case key is null below.
					if(key!=null){	
						String entityName = (String)(key.getDimensionValue(entity)==null?"":key.getDimensionValue(entity).toString());
						String appName = MonitoringUtils.getEntityName(key,BEEntityDimensions.app.name());
						alertDtls = String.format("{Entity name=%s},{Entity Type=%s},{Cluster Name=%s}", entityName, entity, appName);
					}
					actionAttributes.put(ActionUtil.ACTION_ALERT_DTLS, alertDtls);
				}
				catch(Exception e) {
					SetHealthAction.LOGGER.log(Level.ERROR, "Exception occurred while sending Health Fact in SetHealthAction.",e);
				}
			}
			else{
				LOGGER.log(Level.ERROR, "Received health value for action  is empty/null.");
			}
		}

		private void setEntityHealth(MetricKeyImpl key, String entity, String healthValue, String healthEntity) throws ObjectCreationException {
			String appName = MonitoringUtils.getEntityName(key,BEEntityDimensions.app.name());
			if(BEEntityDimensions.app.name().equals(healthEntity))
				setHealthOfApplication(key, appName, healthValue);
			else if(BEEntityDimensions.instance.name().equals(healthEntity)){
				String instanceName=MonitoringUtils.getEntityName(key,BEEntityDimensions.instance.name());
				setHealthOfServiceInstance(key,instanceName,appName,healthValue);
			}
			else if(BEEntityDimensions.agent.name().equals(healthEntity)){
				String instanceName=MonitoringUtils.getEntityName(key,BEEntityDimensions.instance.name());
				setHealthOfServiceInstance(key, instanceName, appName, healthValue);
			}
		}

		private void setHealthOfApplication(MetricKeyImpl key, String entityName, String healthValue) throws ObjectCreationException {
			if(entityName != null && !entityName.isEmpty()) {
				Application application = BEMMServiceProviderManager.getInstance().getBEApplicationsManagementService().getApplicationByName(entityName);
				if(application != null)
					application.setHealthStatus(healthValue);
			}
		}


		private void setHealthOfServiceInstance(MetricKeyImpl key, String entityName, String appName, String healthValue) throws ObjectCreationException {
			if(entityName != null && !entityName.isEmpty())
			{
				Application application = BEMMServiceProviderManager.getInstance().getBEApplicationsManagementService().getApplicationByName(appName);
				if(application != null) {
					for(Host host : application.getHosts()) {
						for(ServiceInstance instance : host.getInstances()) {
							if(instance.getName() != null && !instance.getName().isEmpty() && instance.getName().equals(entityName)) {
								instance.setHealthStatus(healthValue);
								return;
							}
						}
					}
				}
			}
		}

		/*private void setHealthOfAgent(MetricKeyImpl key, String entityName, String appName, String healthValue) throws ObjectCreationException {
			if(entityName != null && !entityName.isEmpty())
			{
				Application application = BEMMServiceProviderManager.getInstance().getBEApplicationsManagementService().getApplicationByName(appName);
				if(application != null) {
					for(Host host : application.getHosts()) {
						for(ServiceInstance instance : host.getInstances()) {
							for(Agent agent : instance.getAgents()) {
								if(agent.getName() != null && !agent.getName().isEmpty() && agent.getName().equals(entityName)) {
									agent.setHealthStatus(healthValue);
									return;
								}
							}
						}
					}
				}
			}
		}*/
		
		private int getInstanceIsRunning(String instanceName, String appName){
			try{
				Application application = BEMMServiceProviderManager.getInstance().getBEApplicationsManagementService().getApplicationByName(appName);
				if(application != null) {
					for(Host host : application.getHosts()) {
						for(ServiceInstance instance : host.getInstances()) {
							if(instance.getName() != null && !instance.getName().isEmpty() && instance.getName().equals(instanceName)) {
								return instance.isRunning()==true?1:0;
							}
						}
					}
				}
			}catch(Exception e){
				return 0;
			}
			return 0;
		}

		//Prepares a fact for setting health
		private Fact getSetHealthFact (RtaSchema schema,MetricKeyImpl key, String healthParam,String healthValue) throws ObjectCreationException, Exception {

			Fact fact = BEMMServiceProviderManager.getInstance().getAggregationService().createFact();

			fact = getDimensionAttributes(key,fact);
			
			String instanceName=(String) (fact.getAttribute(MetricAttribute.PU_INSTANCE)!=null?fact.getAttribute(MetricAttribute.PU_INSTANCE):"");
			String appName=(String) (fact.getAttribute(MetricAttribute.CLUSTER)!=null?fact.getAttribute(MetricAttribute.CLUSTER):"");
			
			int isRunning=getInstanceIsRunning(instanceName,appName);
			
			//Setting health attribute
			fact.setAttribute(MetricAttribute.TIMESTAMP,System.currentTimeMillis());
			fact.setAttribute(MetricAttribute.PU_INSTANCE_ISACTIVE,isRunning);
			fact.setAttribute(healthParam,healthValue);
			
			return fact;
		}

		private Fact getDimensionAttributes(MetricKeyImpl key, Fact fact) throws Exception {

			String appName=(String)key.getDimensionValue(BEEntityDimensions.app.name());
			String instanceName=(String)key.getDimensionValue(BEEntityDimensions.instance.name());
			String agentName=(String)key.getDimensionValue(BEEntityDimensions.agent.name());
			
			//Setting dummy app value
			fact.setAttribute(MetricAttribute.CLUSTER_DUMMY,MetricAttribute.CLUSTER_DUMMY_VALUE);
			
			if(appName!=null&&!appName.isEmpty()) {
				fact.setAttribute(MetricAttribute.CLUSTER, appName);
			}
			else {
				//This case should never happen.App Name must be present in every record
				fact.setAttribute(MetricAttribute.CLUSTER, MetricAttribute.DUMMY_ATTR);
			}
			if(instanceName!=null&&!instanceName.isEmpty()) {
				fact.setAttribute(MetricAttribute.PU_INSTANCE, instanceName);
			}
			else {
				fact.setAttribute(MetricAttribute.PU_INSTANCE,MetricAttribute.DUMMY_ATTR);
			}
			if(agentName!=null&&!agentName.isEmpty()) {
				fact.setAttribute(MetricAttribute.AGENT_NAME, agentName);
			}
			else {
				fact.setAttribute(MetricAttribute.AGENT_NAME, MetricAttribute.DUMMY_ATTR);
			}

			return fact;
		}

		@Override
		public String getAlertText() {
			String alertText = (String) threadLocalOfAction.get().get(ActionUtil.ACTION_ALERT_TEXT);
			return alertText;
		}

		@Override
		public String getAlertDetails() {
			String alertDtls = (String) threadLocalOfAction.get().get(ActionUtil.ACTION_ALERT_DTLS);
			return alertDtls;
		}


	}
}

