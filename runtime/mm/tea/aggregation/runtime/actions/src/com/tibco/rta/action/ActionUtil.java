package com.tibco.rta.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.MultiValueMetric;
import com.tibco.rta.RtaSession;
import com.tibco.rta.SingleValueMetric;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.model.rule.mutable.MutableActionDef;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.rule.Rule;
import com.tibco.tea.agent.be.util.BETeaAgentProps;

public class ActionUtil {
	
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(
			LoggerCategory.RTA_ACTIONS.getCategory());
	
	private static final String SIMPLE_DATE_PATTERN = "dd MMM yyyy hh:mm:ss SSS zzz";
	private static final int KB_CONSTANT = 1024;
	private static final int MB_CONSTANT = 1024 * 1024;
	private static final int GB_CONSTANT = 1024 * 1024 * 1024;

	public static final String RULE_NAME_TOKEN = "rule.name";
	public static final String RULE_ALERT_PRIORITY_TOKEN = "alert.priority";
	public static final String ALERT_TIMESTAMP_TOKEN = "alert.timestamp";
	public static final String RULE_CONDITION_TOKEN = "rule.condition";
	public static final String RULE_OWNER_NAME_TOKEN = "rule.owner.name";
	public static final String RULE_CONDITION_STATE_TOKEN = "alert.type";
	public static final String METRIC_KEY_TOKEN = "metric.key";
	public static final String METRIC_INFO_TOKEN = "metric.info";
	public static final String METRIC_VALUE_TOKEN = "metric.value";
	
	public static final String ENTITY_HEALTH= "entity.health";
	
	public static final String ACTION_ALERT_TEXT="alertText";
	public static final String ACTION_ALERT_DTLS="alertDtls";

	public static final String METRIC_KEY_DIM_REGEX = Pattern.quote("${metric.key.") + "(.*?)" + Pattern.quote("}");
	
	public static enum AlertLevel{
		high("High"),
		low("Low"),
		medium("Medium"),
		normal("Normal");
		
		private String alertLevelName;
		
		private AlertLevel(String name){
			alertLevelName=name;
		}
		
		public String getAlertLevelName(){
			return alertLevelName;
		}
	}

	public static String getCurrentTimeStamp() {
		return new SimpleDateFormat(SIMPLE_DATE_PATTERN).format(new Date());
	}

	public static Measurement getMeasurement(Metric metric) {
		MetricKey mKey = (MetricKey) metric.getKey();
		RtaSchema schema = ModelRegistry.INSTANCE.getRegistryEntry(mKey.getSchemaName());
		String measurementName = metric.getDescriptor().getMeasurementName();
		return schema.getMeasurement(measurementName);
	}

	public static DataType getMetricDataType(Metric metric) {
		return getMeasurement(metric).getMetricFunctionDescriptor().getMetricDataType();
	}

	public static Object getFormattedValue(Measurement measurement, Object metricValue) {
		if(measurement!= null && "Status".equals(measurement.getName()) && metricValue != null) {
			if(((Integer)metricValue) > 0) {
				return "Up";
			}
			else {
				return "Down";
			}
		}
		
		if (measurement == null || measurement.getUnitOfMeasurement() == null) {
			return metricValue;
		}
		

		String unit = measurement.getUnitOfMeasurement();
		if ("unit".equalsIgnoreCase(unit)) {
			return metricValue;
		} else if ("byte".equalsIgnoreCase(unit)) {
			DataType dataType = measurement.getMetricFunctionDescriptor().getMetricDataType();
			if (dataType == DataType.INTEGER) {
				return getStringOfIntValue((Integer) metricValue);
			} else if (dataType == DataType.LONG) {
				return getStringOfLongValue((Long) metricValue);
			} else if (dataType == DataType.DOUBLE) {
				return getStringOfDoubleValue((Double) metricValue);
			}
		} else if ("second".equalsIgnoreCase(unit)) {
			return metricValue + " seconds";
		}
		return metricValue;
	}

	private static String getStringOfIntValue(int value) {
		if (value >= GB_CONSTANT) {
			return (value / GB_CONSTANT) + " GB";
		} else if (value >= MB_CONSTANT) {
			return (value / MB_CONSTANT) + " MB";
		} else if (value >= KB_CONSTANT) {
			return (value / KB_CONSTANT) + " KB";
		} else {
			return value + " bytes";
		}
	}

	private static String getStringOfLongValue(long value) {
		if (value >= GB_CONSTANT) {
			return (value / GB_CONSTANT) + " GB";
		} else if (value >= MB_CONSTANT) {
			return (value / MB_CONSTANT) + " MB";
		} else if (value >= KB_CONSTANT) {
			return (value / KB_CONSTANT) + " KB";
		} else {
			return value + " bytes";
		}
	}

	private static String getStringOfDoubleValue(double value) {
		if (value >= GB_CONSTANT) {
			return (value / GB_CONSTANT) + " GB";
		} else if (value >= MB_CONSTANT) {
			return (value / MB_CONSTANT) + " MB";
		} else if (value >= KB_CONSTANT) {
			return (value / KB_CONSTANT) + " KB";
		} else {
			return value + " bytes";
		}
	}
	
	
	public static String substituteTokens(String body, Rule rule, MetricNode node, boolean isSet, ActionDef actionDef, boolean wrapHtml) {
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, String.format("Before token replacement, MessageContent=[%s]", body));
		}
		
		if (body == null) {
			return "";
		}
		if (wrapHtml) {
			if(!(body.startsWith("<html>") || body.startsWith("<HTML>"))){
				body = "<html><body>" + body + "</body></html>";
			}	
		}
		body = replaceRuleName(body,getExpression(RULE_NAME_TOKEN), rule.getName());
		
		if (actionDef.getAlertLevel() == null) {
			((MutableActionDef)actionDef).setAlertLevel("Low");
		}
		body = body.replace(getExpression(RULE_ALERT_PRIORITY_TOKEN), actionDef.getAlertLevel());
		body = body.replace(getExpression(ALERT_TIMESTAMP_TOKEN), ActionUtil.getCurrentTimeStamp());
		if (isSet) {
			body = body.replace(getExpression(RULE_CONDITION_STATE_TOKEN), "set");
			//body = body.replace(getExpression(RULE_CONDITION_TOKEN), rule.getRuleDef().getSetCondition().toString());
		} else {
			body = body.replace(getExpression(RULE_CONDITION_STATE_TOKEN), "clear");
			//body = body.replace(getExpression(RULE_CONDITION_TOKEN), rule.getRuleDef().getClearCondition().toString());
		}
		String ruleOwnerName = (rule.getRuleDef().getUserName() != null) ? rule.getRuleDef().getUserName() : "";
		body = body.replace(getExpression(RULE_OWNER_NAME_TOKEN), ruleOwnerName);
		//body = body.replace(getExpression(METRIC_KEY_TOKEN), node.getKey().toString());
		//body = body.replace(getExpression(METRIC_INFO_TOKEN), ActionUtil.getMetricInfoString(node, wrapHtml));
		//body = replaceMetricValueToken(body, node);
		
		//body = replaceMetricKeyDimensionTokens(body, node);
		
		body=replaceBEDimValuesTokens(body,node);
		
		body=replaceMetricValuesTokens(body,node,rule);
		
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, String.format("After token replacement, MessageContent=[%s]", body));
		}
		return body;
		
	}
	
	private static String replaceMetricValuesTokens(String alertText, MetricNode node, Rule rule)  {
		
		MetricKey metricKey = (MetricKey) node.getKey();
		try {
			RtaSession session = BEMMServiceProviderManager.getInstance().getAggregationService().getSession();
			
			if(session!=null) {
				
				DimensionHierarchy hierarchy=session.getSchema(BETeaAgentProps.BEMM_FACT_SCHEMA).getCube(BETeaAgentProps.CUBE_BEMM).getDimensionHierarchy(metricKey.getDimensionHierarchyName());
				for(Measurement measurement : hierarchy.getMeasurements()){
					String alertToken=measurement.getProperty(BETeaAgentProps.BEMM_RULE_ALERT_TOKEN);
					if(alertToken!=null && !alertToken.isEmpty()){
						String metricName=measurement.getName();
						if (!alertText.contains(getExpression(alertToken))) {
							continue;
						}
						Metric metric = node.getMetric(metricName);
						
						if (!metric.isMultiValued()) {
							SingleValueMetric svMetric = (SingleValueMetric) metric;
							alertText = alertText.replace(getExpression(alertToken), "" 
									+ ActionUtil.getFormattedValue(measurement, svMetric.getValue()));
						} else {
							MultiValueMetric mvm = (MultiValueMetric) metric;
							List<?> objVals = mvm.getValues();
							StringBuilder msgBldr = new StringBuilder();
							String prefix = "";
							for (Object obj : objVals) {
								msgBldr.append(prefix);
			                    msgBldr.append(obj);
								prefix = ",";
							}
							alertText = alertText.replace(getExpression(alertToken), msgBldr.toString());
						}
					}
				}
			}
		} 
		catch (Exception e) {
			LOGGER.log(Level.DEBUG, String.format("Exception occurred while token replacement for metric values =[%s]", alertText),e);
		}		
		return alertText;
	}

	private static String replaceMetricKeyDimensionTokens(String body, MetricNode node) {
		Set<String> metricKeyTokens = new HashSet<String>();
		
		Matcher m = Pattern.compile(METRIC_KEY_DIM_REGEX).matcher(body);
		
		while(m.find()) {
			metricKeyTokens.add(m.group());			
		}
		
		for(String key: metricKeyTokens) {			
			String dimensionName = key.substring(13, key.length()-1);
			MetricKey metricKey = (MetricKey) node.getKey();
			Object dimensionValue = metricKey.getDimensionValue(dimensionName);
			if(dimensionValue != null) {
				body = body.replace(key, (String) dimensionValue);
			}
		}
		return body;
	}

	private static String replaceMetricValueToken(String body, MetricNode node) {
		for (String metricName : node.getMetricNames()) {
			if (!body.contains(getExpression(METRIC_VALUE_TOKEN + "." + metricName))) {
				continue;
			}
			Metric metric = node.getMetric(metricName);
			Measurement measurement = ActionUtil.getMeasurement(metric);
			if (!metric.isMultiValued()) {
				SingleValueMetric svMetric = (SingleValueMetric) metric;
				body = body.replace(getExpression(METRIC_VALUE_TOKEN + "." + metricName), "" 
						+ ActionUtil.getFormattedValue(measurement, svMetric.getValue()));
			} else {
				MultiValueMetric mvm = (MultiValueMetric) metric;
				List<?> objVals = mvm.getValues();
				StringBuilder msgBldr = new StringBuilder();
				String prefix = "";
				for (Object obj : objVals) {
					msgBldr.append(prefix);
                    msgBldr.append(obj);
					prefix = ",";
				}
				body = body.replace(getExpression(METRIC_VALUE_TOKEN + "." + metricName), msgBldr.toString());
			}
		}
		return body;
	}
	
	public static String getMetricInfoString(MetricNode node, boolean isHtml) {
		if (isHtml) {
			StringBuilder bldr = new StringBuilder(
					"<table><font face=\"Calibri\" size=\"3\">");
			bldr.append("<tr > <th>Metric Name</th> <th>Value</th> </tr>");
			for (String metricName : node.getMetricNames()) {
				bldr.append("<tr>");
				bldr.append("<td>").append(metricName).append("</td>");
				Metric metric = node.getMetric(metricName);
				Measurement measurement = ActionUtil.getMeasurement(metric);
				if (!metric.isMultiValued()) {
					SingleValueMetric svMetric = (SingleValueMetric) metric;
					bldr.append("<td align=\"center\">")
							.append(ActionUtil.getFormattedValue(measurement,
									svMetric.getValue())).append("</td>");
				} else {
					MultiValueMetric mvm = (MultiValueMetric) metric;
					List<?> objVals = mvm.getValues();
					StringBuilder msgBldr = new StringBuilder();
					String prefix = "";
					for (Object obj : objVals) {
						msgBldr.append(prefix);
						msgBldr.append(obj);
						prefix = ",";
					}
					bldr.append("<td align=\"center\">")
							.append(msgBldr.toString()).append("</td>");
				}
				bldr.append("</tr>");
			}
			bldr.append("</font></table>");
			return bldr.toString();
		} else {
			StringBuilder bldr = new StringBuilder("{");
			for (String metricName : node.getMetricNames()) {
				bldr.append(metricName).append("=");
				Metric metric = node.getMetric(metricName);
				Measurement measurement = ActionUtil.getMeasurement(metric);
				if (!metric.isMultiValued()) {
					SingleValueMetric svMetric = (SingleValueMetric) metric;
							bldr.append(ActionUtil.getFormattedValue(measurement,
									svMetric.getValue()));
				} else {
					MultiValueMetric mvm = (MultiValueMetric) metric;
					List<?> objVals = mvm.getValues();
					StringBuilder msgBldr = new StringBuilder();
					String prefix = "";
					for (Object obj : objVals) {
						msgBldr.append(prefix);
						msgBldr.append(obj);
						prefix = ",";
					}
				}
				bldr.append(", ");
			}
			bldr.append("}");
			return bldr.toString();
		}
	}
	
	public static String substituteAlertTextTokens(String alertText, Rule rule, MetricNode node, boolean isSet, ActionDef actionDef, String healthValue) {
		
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, String.format("Alert text before token replacement, MessageContent=[%s]", alertText));
		}
		
		if (alertText == null || alertText.isEmpty()) {
			return "";
		}

		alertText = replaceRuleName(alertText,getExpression(RULE_NAME_TOKEN), rule.getName());
		
		if (actionDef.getAlertLevel() == null) {
			((MutableActionDef)actionDef).setAlertLevel("Low");
		}
		alertText = alertText.replace(getExpression(RULE_ALERT_PRIORITY_TOKEN), actionDef.getAlertLevel());
		alertText = alertText.replace(getExpression(ALERT_TIMESTAMP_TOKEN), ActionUtil.getCurrentTimeStamp());
		if (isSet) {
			alertText = alertText.replace(getExpression(RULE_CONDITION_STATE_TOKEN), "set");
			//alertText = alertText.replace(getExpression(RULE_CONDITION_TOKEN), rule.getRuleDef().getSetCondition().toString());
		} else {
			alertText = alertText.replace(getExpression(RULE_CONDITION_STATE_TOKEN), "clear");
			//alertText = alertText.replace(getExpression(RULE_CONDITION_TOKEN), rule.getRuleDef().getClearCondition().toString());
		}
		String ruleOwnerName = (rule.getRuleDef().getUserName() != null) ? rule.getRuleDef().getUserName() : "";
		alertText = alertText.replace(getExpression(RULE_OWNER_NAME_TOKEN), ruleOwnerName);
		//alertText = alertText.replace(getExpression(METRIC_KEY_TOKEN), node.getKey().toString());

		//alertText = replaceMetricValueToken(alertText, node);
		//alertText = replaceMetricKeyDimensionTokens(alertText, node);
		
		alertText=replaceBEDimValuesTokens(alertText,node);
		
		alertText=replaceMetricValuesTokens(alertText,node,rule);
		
		//Substituting health value
		alertText = alertText.replace(getExpression(ENTITY_HEALTH), healthValue);
		
		
		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, String.format("After token replacement, MessageContent=[%s]", alertText));
		}
		
		return alertText;
	}
	

	private static String replaceRuleName(String alertText, String expression,String ruleName) {
		String filteredRuleName="";
		
		if(ruleName!=null&&!ruleName.isEmpty()) {	
			int index=ruleName.lastIndexOf(BETeaAgentProps.BE_RULE_APP_NAME_SEPERATOR);
			
			if(index>0) {
				filteredRuleName = ruleName.substring(index+1,ruleName.length());
			}
		}
		alertText = alertText.replace(getExpression(RULE_NAME_TOKEN),filteredRuleName);
		return alertText;
	}
	
	public static String getRuleName(String ruleName) {
		String filteredRuleName="";
		
		if(ruleName!=null&&!ruleName.isEmpty()) {	
			int index=ruleName.lastIndexOf(BETeaAgentProps.BE_RULE_APP_NAME_SEPERATOR);
			
			if(index>0) {
				filteredRuleName = ruleName.substring(index+1,ruleName.length());
			}
		}
		return filteredRuleName;
	}

	private static String replaceBEDimValuesTokens(String alertText,MetricNode node) {
		MetricKey metricKey = (MetricKey) node.getKey();
		try {
			RtaSession session = BEMMServiceProviderManager.getInstance().getAggregationService().getSession();
			
			if(session!=null) {
				
				DimensionHierarchy hierarchy=session.getSchema(BETeaAgentProps.BEMM_FACT_SCHEMA).getCube(BETeaAgentProps.CUBE_BEMM).getDimensionHierarchy(metricKey.getDimensionHierarchyName());
				for(Dimension dim:hierarchy.getDimensions()){
					String alertToken=dim.getProperty(BETeaAgentProps.BEMM_RULE_ALERT_TOKEN);
					if(alertToken!=null && !alertToken.isEmpty()){
						if (!alertText.contains(getExpression(alertToken))) {
							continue;
						}
						String tokenValue=(String) metricKey.getDimensionValue(dim.getName());
						alertText=alertText.replace(getExpression(alertToken),tokenValue);
					}
					
				}				
			}
		} 
		catch (Exception e) {
			LOGGER.log(Level.DEBUG, String.format("Exception occurred while token replacement for metric values =[%s]", alertText),e);
		}		
		return alertText;

		
		
		
		
	}

	public static String getExpression(String variable) {
		return "${" + variable + "}";
	}
}
