package com.tibco.rta.action;

import java.util.Properties;

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

public class SendToDashboard implements ActionHandlerContext {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_ACTIONS.getCategory());

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Action getAction(Rule rule, ActionDef actionDef) {
		// TODO Auto-generated method stub
		return new SendToDashboardActionImpl(rule, actionDef);
	}
	
	class SendToDashboardActionImpl extends AbstractActionImpl {
		
		String alertText;
		
		public SendToDashboardActionImpl(Rule rule, ActionDef actionDef) {
			super(rule, actionDef);
		}

		@Override
		public ActionHandlerContext getActionHandlerContext() {
			return SendToDashboard.this;
		}

		@Override
		public void performAction(Rule rule, MetricNodeEvent nodeEvent)
				throws Exception {
			boolean isSet = isSetAction();			
			if (LOGGER.isEnabledFor(Level.INFO)) {
				String description = String.format("SendToDashboardAction triggered, Rule: [%s] %s condition passed for MetricNode Key[%s]",
						rule.getName(), isSet ? "SET" : "CLEAR", nodeEvent.getMetricNode().getKey().toString());
				LOGGER.log(Level.INFO, description);
			}
            // log Alert.
         	LogAction.logAlert(isSet, rule, nodeEvent,alertText);

			alertText = String.format("Alert for rule [%s] for [%s] condition", rule.getRuleDef().getName(), isSetAction() ? "SET" : "CLEAR");
			
		}

		@Override
		public String getAlertText() {
			return alertText;
		}

		@Override
		public String getAlertDetails() {
			return alertText;
		}

        @Override
        public String getAlertType() {
            return "SendToDashboard";
        }
    }
}