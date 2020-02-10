package com.tibco.rta.action;

import java.util.Properties;

import com.tibco.rta.RtaCommand.Command;
import com.tibco.rta.impl.RtaCommandImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.runtime.action.catalog.ActionCatalog;
import com.tibco.rta.runtime.model.MetricNodeEvent;
import com.tibco.rta.runtime.model.rule.AbstractActionImpl;
import com.tibco.rta.runtime.model.rule.Action;
import com.tibco.rta.runtime.model.rule.ActionHandlerContext;
import com.tibco.rta.runtime.model.rule.Rule;
import com.tibco.rta.service.query.QueryUtils;

public class SendToNamedSessionAction implements ActionHandlerContext {

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
		return new SendToNamedSessionActionImpl(rule, actionDef);
	}
	
	class SendToNamedSessionActionImpl extends AbstractActionImpl {

		String alertText;
		
		public SendToNamedSessionActionImpl(Rule rule, ActionDef actionDef) {
			super(rule, actionDef);
		}

		@Override
		public ActionHandlerContext getActionHandlerContext() {
			return SendToNamedSessionAction.this;
		}

		@Override
		public void performAction(Rule rule, MetricNodeEvent nodeEvent) throws Exception {
			boolean isSet = isSetAction();
			String description = String.format(" %s condition passed: %s", isSet ? "SET" : "CLEAR", nodeEvent
                    .getMetricNode().getKey());
			if (LOGGER.isEnabledFor(Level.INFO)) {
				LOGGER.log(Level.INFO, "SendToNamedSessionAction triggered, Rule:" + rule.getName() + description);
			}

			// log Alert.
			LogAction.logAlert(isSet, rule, nodeEvent,alertText);
			
			RtaCommandImpl command = new RtaCommandImpl(Command.CUSTOM_COMMAND);
			command.setProperty("Description", description);
            command.setProperty("MetricTuple", QueryUtils.convertMetricNodeToMetricResultTuple(nodeEvent.getMetricNode()));

			String sessionName = (String) getFunctionParamValue("session-name").getValue();
			ActionCatalog.sendToSession(sessionName, command);
			alertText = "Alert For Named session" + sessionName;
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
            return "SendToNamedSession";
        }

	}
}
