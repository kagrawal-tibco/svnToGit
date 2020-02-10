package com.tibco.rta.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.model.rule.mutable.MutableActionDef;
import com.tibco.rta.runtime.model.MetricNodeEvent;
import com.tibco.rta.runtime.model.rule.AbstractActionImpl;
import com.tibco.rta.runtime.model.rule.Action;
import com.tibco.rta.runtime.model.rule.ActionHandlerContext;
import com.tibco.rta.runtime.model.rule.Rule;
import com.tibco.tea.agent.be.util.BEEntityDimensions;

public class LogAction implements ActionHandlerContext {

	private static final Logger APP_LOGGER = LogManagerFactory.getLogManager().getLogger(
			LoggerCategory.RTA_ACTIONS.getCategory());
	private static org.apache.log4j.Logger LOG_ACTION_LOGGER;
	private static org.apache.log4j.Logger ALL_ALERT_LOGGER;
	private static boolean isXML = true;
	private static boolean isLogAllAlert = true;
	private static String alertLevel="Low";

	@Override
	public void init(Properties configuration) {

		isXML = !("TEXT".equalsIgnoreCase((String) ConfigProperty.RTA_LOG_ALERT_FILE_FORMAT.getValue(configuration)));
		if (APP_LOGGER.isEnabledFor(Level.INFO)) {
			APP_LOGGER.log(Level.INFO, "Initializing LogAction, Configured Alert log format: [%s]", isXML ? "XML" : "TEXT");
		}

		// Whether to log alert or not (All action like email, send to dashboard etc)
		isLogAllAlert = !("false".equalsIgnoreCase((String) ConfigProperty.RTA_LOG_ALERT.getValue(configuration)));
		if (APP_LOGGER.isEnabledFor(Level.INFO)) {
			APP_LOGGER.log(Level.INFO, "Log All Alert is configured [%s]", isLogAllAlert);
		}
		if (isLogAllAlert) {
			// all alert logger
			ALL_ALERT_LOGGER = org.apache.log4j.Logger.getLogger("AllAlertLogger");
			ALL_ALERT_LOGGER.setAdditivity(false);
			ALL_ALERT_LOGGER.setLevel(org.apache.log4j.Level.INFO);
			org.apache.log4j.Appender appender = ALL_ALERT_LOGGER.getAppender("AllAlertLogAppender");
			if (appender != null) {
				org.apache.log4j.RollingFileAppender rAppender = (org.apache.log4j.RollingFileAppender) appender;
				if (APP_LOGGER.isEnabledFor(Level.INFO)) {
					APP_LOGGER.log(Level.INFO, "Log All Alert File is configured [%s]", rAppender.getFile());
				}
			} else {
				APP_LOGGER.log(Level.WARN, "No Log File Appender 'AllAlertLogAppender' found for 'AllAlertLogger'!");
			}
		}

		// log action logger
		LOG_ACTION_LOGGER = org.apache.log4j.Logger.getLogger("LogActionLogger");
		LOG_ACTION_LOGGER.setAdditivity(false);
		LOG_ACTION_LOGGER.setLevel(org.apache.log4j.Level.INFO);
		org.apache.log4j.Appender logActionAppender = LOG_ACTION_LOGGER.getAppender("LogActionAppender");
		if (logActionAppender != null) {
			org.apache.log4j.RollingFileAppender rlogActionAppender = (org.apache.log4j.RollingFileAppender) logActionAppender;
			if (APP_LOGGER.isEnabledFor(Level.INFO)) {
				APP_LOGGER.log(Level.INFO, "Log-Action File is configured [%s]", rlogActionAppender.getFile());
			}
		} else {
			APP_LOGGER.log(Level.WARN, "No Log File Appender 'LogActionAppender' found for 'LogActionLogger'!");
		}
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
		return new LogActionImpl(rule, actionDef);
	}

	class LogActionImpl extends AbstractActionImpl {
		
		public LogActionImpl(Rule rule, ActionDef actionDef) {
			super(rule, actionDef);
		}

		@Override
		public ActionHandlerContext getActionHandlerContext() {
			return LogAction.this;
		}

		@Override
		public void performAction(Rule rule, MetricNodeEvent node) throws Exception {
			Map<String,Object> actionAttributes=threadLocalOfAction.get();
			actionAttributes.clear();//remove previous values set by this thread.
			
			if (APP_LOGGER.isEnabledFor(Level.INFO)) {
				APP_LOGGER.log(Level.INFO, "LogAction triggered for RuleName: %s  MetricNode Key: %s", rule.getName(),
						node.getMetricNode().getKey().toString());
			}
			MetricKeyImpl key=(MetricKeyImpl)node.getMetricNode().getKey();
			//Setting alert text
			String alertText=ActionUtil.substituteAlertTextTokens((String)getFunctionParamValue(MetricAttribute.ALERT_TEXT).getValue(),rule,node.getMetricNode(),isSetAction(),actionDef,"");
			actionAttributes.put(ActionUtil.ACTION_ALERT_TEXT, alertText);
			
			String alertDtls = alertText;
			if(key!=null){		
				String entity = key.getDimensionLevelName();
				String entityName = (String)(key.getDimensionValue(entity)==null?"":key.getDimensionValue(entity).toString());
				String appName = MonitoringUtils.getEntityName(key,BEEntityDimensions.app.name());
				
				alertDtls = String.format("{Entity name=%s},{Entity Type=%s},{Cluster Name=%s}", entityName, entity, appName);
			}
			actionAttributes.put(ActionUtil.ACTION_ALERT_DTLS, alertDtls);
			
			if (this.getAlertLevel() == null) {
				((MutableActionDef)actionDef).setAlertLevel("Low");
			}
			
			alertLevel=actionDef.getAlertLevel();
			
			if(alertLevel!=null&&alertLevel.length()>1){
				alertLevel=alertLevel.substring(0, 1).toUpperCase() + alertLevel.substring(1);
			}
			
			
			logAction(LOG_ACTION_LOGGER, isSetAction(), rule, node,alertText);
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
            return "Log-Action";
        }
    }
	
	/**
	 * To log All Alert/actions like email, send to dashboard etc.
	 * @param alertText 
	 * @param logActionImpl 
	 */
	public static void logAlert(boolean isSet, Rule rule, MetricNodeEvent node, String alertText) {
		if (isLogAllAlert) {
			if (APP_LOGGER.isEnabledFor(Level.DEBUG)) {
				APP_LOGGER.log(Level.DEBUG, "Logging alert to All Alert file, RuleName=%s IsSet=%s", rule.getName(), isSet);
			}
			logAction(ALL_ALERT_LOGGER, isSet, rule, node,alertText);
		}
	}
	
	
	private static String logAction(org.apache.log4j.Logger logger, boolean isSet, Rule rule, MetricNodeEvent node, String alertText) {
		String str = null;
		if (isXML) {
			str = logXmlAlert(logger, isSet, rule, node,alertText);
		} else {
			str = logTextAlert(logger, isSet, rule, node,alertText);
		}
		return str;
	}

	private static String logXmlAlert(org.apache.log4j.Logger logger, Boolean isSetAction, Rule rule, MetricNodeEvent node, String alertText) {
		
		StringBuilder alertBuff = new StringBuilder();
		alertBuff.append("<alert>");
		alertBuff.append("\n");
		alertBuff.append(String.format("<rule-name>%s</rule-name>", ActionUtil.getRuleName(rule.getName())));
		alertBuff.append("\n");
		String ownerName = (rule.getRuleDef().getUserName() != null) ? rule.getRuleDef().getUserName() : "";
		alertBuff.append(String.format("<user-name>%s</user-name>", ownerName));
		alertBuff.append("\n");
		alertBuff.append(String.format("<timestamp>%s</timestamp>", ActionUtil.getCurrentTimeStamp()));
		alertBuff.append("\n");
		alertBuff.append(String.format("<alert-level>%s</alert-level>", alertLevel));
		alertBuff.append("\n");
		if (isSetAction) {
			alertBuff.append("<alert-type>Set</alert-type>");
		} else {
			alertBuff.append("<alert-type>Clear</alert-type>");
		}
		alertBuff.append("\n");
		alertBuff.append(String.format("<alert-text>%s</alert-text>", alertText));
		alertBuff.append("\n");
		alertBuff.append("</alert>");
		alertBuff.append("\n");
		String str = alertBuff.toString();
		logger.info(str);
		return str;
	}

	private static String logTextAlert(org.apache.log4j.Logger logger, Boolean isSetAction, Rule rule, MetricNodeEvent node, String alertText) {
		StringBuilder alertBuff = new StringBuilder();
		alertBuff.append("##");
		alertBuff.append("\n");
		alertBuff.append(String.format("Rule Name   : %s", ActionUtil.getRuleName(rule.getName())));
		alertBuff.append("\n");
		String ownerName = (rule.getRuleDef().getUserName() != null) ? rule.getRuleDef().getUserName() : "";
		alertBuff.append(String.format("User Name   : %s", ownerName));
		alertBuff.append("\n");
		alertBuff.append(String.format("Timestamp   : %s", ActionUtil.getCurrentTimeStamp()));
		alertBuff.append("\n");
		alertBuff.append(String.format("Alert Level : %s", alertLevel));
		alertBuff.append("\n");
		if (isSetAction) {
			alertBuff.append("Alert Type  : Set");
		} else {
			alertBuff.append("Alert Type  : Clear");
		}
		alertBuff.append("\n");
		alertBuff.append(String.format("Alert Text  : %s", alertText));
		alertBuff.append("\n");
		alertBuff.append("##");
		alertBuff.append("\n");
		String str = alertBuff.toString();
		logger.info(str);
		return str;
	}

}
