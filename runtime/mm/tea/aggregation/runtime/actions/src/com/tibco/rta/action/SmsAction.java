package com.tibco.rta.action;

import com.tibco.rta.RtaCommand;
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

import java.util.Properties;

public class SmsAction implements ActionHandlerContext {

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
		return new SmsActionImpl(rule, actionDef);
	}
	
	class SmsActionImpl extends AbstractActionImpl {

		public SmsActionImpl(Rule rule, ActionDef actionDef) {
			super(rule, actionDef);
		}

		@Override
		public ActionHandlerContext getActionHandlerContext() {
			return SmsAction.this;
		}

		@Override
		public void performAction(Rule rule, MetricNodeEvent node)
				throws Exception {
			String isSetStr = isSetAction() ? "SET" : "CLEAR";
            if (LOGGER.isEnabledFor(Level.INFO)) {
			    LOGGER.log(Level.INFO, "SMS Action (%s) triggered for node: %s", isSetStr, node.getMetricNode().getKey());
            }
            // log Alert.
         	LogAction.logAlert(isSetAction(), rule, node,"");
            
            RtaCommandImpl rtac = new RtaCommandImpl(RtaCommand.Command.CUSTOM_COMMAND);
            rtac.setProperty("smsTo", "12345678");
            rtac.setProperty("message", String.format("Clear Condition satisfied for node %s", QueryUtils.convertMetricNodeToMetricResultTuple(node.getMetricNode())));
            ActionCatalog.sendToAllSessions(rtac);
		}

		@Override
		public String getAlertText() {
			return "";
		}

		@Override
		public String getAlertDetails() {
			return "";
		}

        @Override
        public String getAlertType() {
            return "SMS";
        }
    }

}
