package com.tibco.rta.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.runtime.model.MetricNodeEvent;
import com.tibco.rta.runtime.model.rule.AbstractActionImpl;
import com.tibco.rta.runtime.model.rule.Action;
import com.tibco.rta.runtime.model.rule.ActionHandlerContext;
import com.tibco.rta.runtime.model.rule.Rule;
import com.tibco.tea.agent.be.util.BEEntityDimensions;

public class NoAction implements ActionHandlerContext {
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(
			LoggerCategory.RTA_ACTIONS.getCategory());

	@Override
	public void init(Properties configuration) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return "Alert-Only";
	}

	@Override
	public Action getAction(Rule rule, ActionDef actionDef) {
		return new NoActionImpl(rule, actionDef);
	}
	
	class NoActionImpl extends AbstractActionImpl {
//		String alertText, alertDtls;
		public NoActionImpl(Rule rule, ActionDef actionDef) {
			super(rule, actionDef);
		}

		@Override
		public ActionHandlerContext getActionHandlerContext() {
			return NoAction.this;
		}

		@Override
		public void performAction(Rule rule, MetricNodeEvent node) throws Exception {
			Map<String,Object> actionAttributes=threadLocalOfAction.get();
			actionAttributes.clear();//remove previous values set by this thread.
			
			if (LOGGER.isEnabledFor(Level.INFO)) {
				LOGGER.log(Level.INFO, "No-Action triggered, RuleName: %s MetricNodeKey: %s", rule.getName(), node
						.getMetricNode().getKey());
			}
			try {
				String alertText = (String) getFunctionParamValue(MetricAttribute.ALERT_TEXT).getValue();

				//do token replacements.
				//Setting alert text
				alertText=ActionUtil.substituteAlertTextTokens((String)getFunctionParamValue(MetricAttribute.ALERT_TEXT).getValue(),rule,node.getMetricNode(),isSetAction(),actionDef,"");
				actionAttributes.put(ActionUtil.ACTION_ALERT_TEXT, alertText);
				
				String alertDtls = alertText;

				MetricKeyImpl key=(MetricKeyImpl)node.getMetricNode().getKey();
				if(key!=null){		
					String entity = key.getDimensionLevelName();
					String entityName = (String)(key.getDimensionValue(entity)==null?"":key.getDimensionValue(entity).toString());
					String appName = MonitoringUtils.getEntityName(key,BEEntityDimensions.app.name());

					alertDtls = String.format("{Entity name=%s},{Entity Type=%s},{Cluster Name=%s}", entityName, entity, appName);
				}
				actionAttributes.put(ActionUtil.ACTION_ALERT_DTLS, alertDtls);
				
			} catch (Exception e) {

			}
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

        @Override
        public String getAlertType() {
        	return "Alert-Only";
        }
    }

}
