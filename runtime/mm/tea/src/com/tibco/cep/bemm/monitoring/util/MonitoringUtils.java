package com.tibco.cep.bemm.monitoring.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.model.impl.AgentType;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.Attribute;
import com.tibco.cep.runtime.service.management.jmx.connectors.JMXConnUtil;
import com.tibco.rta.Fact;
import com.tibco.rta.MetricKey;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.impl.MetricKeyImpl;
import com.tibco.rta.runtime.model.MutableMetricNode;
import com.tibco.rta.service.om.ObjectManager;
import com.tibco.tea.agent.be.util.BEEntityDimensions;
import com.tibco.tea.agent.be.util.BETeaAgentProps;

public class MonitoringUtils {
	
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(MonitoringUtils.class);
	
	public static Object parseValue(Object value,String type) {
		if(value!=null)
		{	
			if("DOUBLE".equals(type))
			{	
				if(value instanceof Long)
					value=Double.parseDouble(((Long)value)+"");
				else
					value=Double.parseDouble((value)+"");
			}
			else if("INTEGER".equals(type))
			{
				value=Integer.parseInt(value+"");
			}
			else if("LONG".equals(type))
			{
				value=Long.parseLong(value+"");
			}
			else if("STRING".equals(type))
			{
				value=value+"";
			}
			else if("INTEGER".equals(type))
			{
				value=Integer.parseInt(value+"");
			}
			else if("LONG".equals(type))
			{
				value=Long.parseLong(value+"");
			}
			else if("STRING".equals(type))
			{
				value=value+"";
			}
			else if("BOOLEAN".equals(type))
			{
				value=Boolean.parseBoolean(value+"");
			}
			else if("CHAR".equals(type))
			{
				value=(value+"").charAt(0);
			}
			else if("SHORT".equals(type))
			{
				value=Short.parseShort(value+"");
			}
			else if("BYTE".equals(type))
			{
				value=Byte.parseByte(value+"");
			}
			else if("FLOAT".equals(type))
			{
				value=Float.parseFloat(value+"");
			}
		}
		return value;
	}
	
	public static Object parseValueAndGetDiff(Object value1, Object value2, String type) {
		Object value = null;
		if(value1.getClass() == value2.getClass())
		{
			if("DOUBLE".equals(type))
			{	
				if(value1 instanceof Long){
					value = Double.parseDouble(((Long)value1)+"") - (Double.parseDouble(((Long)value2)+""));
				}else{
					value = Double.parseDouble((value1)+"") - (Double.parseDouble((value2)+""));
				}
			}
			else if("INTEGER".equals(type))
			{
				value=Integer.parseInt(value1+"") - (Integer.parseInt(value2+""));
			}
			else if("LONG".equals(type))
			{
				value = Long.parseLong(value1+"") - (Long.parseLong(value2+""));
			}
			else if("SHORT".equals(type))
			{
				value=Short.parseShort(value1+"") - (Short.parseShort(value2+""));
			}
			else if("FLOAT".equals(type))
			{
				value=Float.parseFloat(value1+"") - (Float.parseFloat(value2+""));
			}else{
				try {
					LOGGER.log(Level.ERROR, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INVALID_DATA_TYPE_FOR_FINDING_DIFFERENCE));
				} catch (ObjectCreationException e) {
					e.printStackTrace();
				}
			}
			
			return deltaWithSign(value1, value2, value, type);
		}

		try {
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.CHECK_DATA_STRUCURE_ERROR));
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}
		return null; // Will never come here. If it does then something wrong in map storing keys.
	}
	
	//TODO - see if we optimize this
	private static Object deltaWithSign(Object value1, Object value2, Object value, String type) {
		
		if("DOUBLE".equals(type)){
			if( ((double)value1<0) && ((double)value2<0) ){ //both are -ve
				if((double)value2 > (double)value1){ //+ve
					value = Math.abs((double)value);
				}else{ //-ve
					value = -1 * Math.abs((double)value);
				}
			}else{ //take sign of second number
				int sign = (int) Math.signum((double)value2);
				value = sign * Math.abs((double)value);
			}
		}else if("INTEGER".equals(type)){
			if( ((int)value1<0) && ((int)value2<0) ){ //both are -ve
				if((int)value2 > (int)value1){ //+ve
					value = Math.abs((int)value);
				}else{ //-ve
					value = -1 * Math.abs((int)value);
				}
			}else{ //take sign of second number
				int sign = (int) Math.signum((int)value2);
				value = sign * Math.abs((int)value);
			}
		}
		else if("LONG".equals(type)){
			if( ((long)value1<0) && ((long)value2<0) ){ //both are -ve
				if((long)value2>(long)value1){ //+ve
					value = Math.abs((long)value);
				}else{ //-ve
					value = -1 * Math.abs((long)value);
				}
			}else{ //take sign of second number
				int sign = (int) Math.signum((long)value2);
				value = sign * Math.abs((long)value);
			}
		}
		else if("SHORT".equals(type)){
			if( ((short)value1<0) && ((short)value2<0) ){ //both are -ve
				if((short)value2>(short)value1){ //+ve
					value = Math.abs((short)value);
				}else{ //-ve
					value = -1 * Math.abs((short)value);
				}
			}else{ //take sign of second number
				int sign = (int) Math.signum((short)value2);
				value = sign * Math.abs((short)value);
			}
		}
		else if("FLOAT".equals(type)){
			if( ((float)value1<0) && ((float)value2<0) ){ //both are -ve
				if((float)value2>(float)value1){ //+ve
					value = Math.abs((float)value);
				}else{ //-ve
					value = -1 * Math.abs((float)value);
				}
			}else{ //take sign of second number
				int sign = (int) Math.signum((float)value2);
				value = sign * Math.abs((float)value);
			}
		}
			
		return value;
		
	}
	
	//returns java type
	public static String getJavaType(String type) {
		type = type.toUpperCase();

		if (type.equals("STRING"))
			type = String.class.getName();
		else if (type.equals("BOOLEAN"))
			type = Boolean.TYPE.getName();
		else if (type.equals("INTEGER") || type.equals("INT"))
			type = Integer.TYPE.getName();
		else if (type.equals("CHAR"))
			type = Character.TYPE.getName();
		else if (type.equals("LONG"))
			type = Long.TYPE.getName();
		else if (type.equals("SHORT"))
			type = Short.TYPE.getName();
		else if (type.equals("BYTE"))
			type = Byte.TYPE.getName();
		else if (type.equals("DOUBLE"))
			type = Double.TYPE.getName();
		else if (type.equals("FLOAT"))
			type = Float.TYPE.getName();
		else if (type.equals("VOID"))
			type = Void.TYPE.getName();
		return type;
	}
	
	
	public static Object getValueForType(String paramValue, String paramType) {
		paramType = paramType.toUpperCase();
		Object value = null;
		if (paramType.equals("STRING"))
			value = paramValue;
		else if (paramType.equals("BOOLEAN"))
			value = Boolean.parseBoolean(paramValue);
		else if (paramType.equals("INTEGER") || paramType.equals("INT"))
			value = Integer.parseInt(paramValue);
		else if (paramType.equals("CHAR"))
			value = paramValue.toCharArray()[0];
		else if (paramType.equals("LONG"))
			value = Long.parseLong(paramValue);
		else if (paramType.equals("SHORT"))
			value = Short.parseShort(paramValue);
		else if (paramType.equals("BYTE"))
			value = Byte.parseByte(paramValue);
		else if (paramType.equals("DOUBLE"))
			value = Double.parseDouble(paramValue);
		else if (paramType.equals("FLOAT"))
			value = Float.parseFloat(paramValue);
		return value;
	}
	
	public static Fact getFact(List<Attribute> factAttr) throws Exception {
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
	
	/*
	 * Goes through the list of entities fetched from the Schema applicable for measurement/dimension
	 * and searches for the provided entityName to be present in the list.
	 * If found return true , else false
	 * True signifies the current measurement/dimension is applicable for writing rules on the entity 
	 * 
	 */
	public static boolean isEntityApplicable(String entity,String entityNames) {
		String[] entityList=entityNames.split(",");
		if(entity!=null && !entity.isEmpty())
		for(String entry:entityList ){
			if(entity.equals(entry)){
				return true;
			}
		}
		return false;
	}
	
	
	public static MutableMetricNode getMetricNode(MetricKey metricKey) throws Exception
	{
		ObjectManager om = ServiceProviderManager.getInstance().getObjectManager();
		MutableMetricNode node=(MutableMetricNode) om.getNode(metricKey);
		return node;
	}
	
	public static String getEntityName(MetricKeyImpl key, String entity) {
		if(BEEntityDimensions.app.name().equals(entity))
			return (String)key.getDimensionValue(BEEntityDimensions.app.name());
		if(BEEntityDimensions.instance.name().equals(entity))
			return (String)key.getDimensionValue(BEEntityDimensions.instance.name());
		if(BEEntityDimensions.agent.name().equals(entity))
			return (String)key.getDimensionValue(BEEntityDimensions.agent.name());
		if(BEEntityDimensions.inference.name().equals(entity))
			return (String)key.getDimensionValue(BEEntityDimensions.agent.name());
		if(BEEntityDimensions.cache.name().equals(entity))
			return (String)key.getDimensionValue(BEEntityDimensions.agent.name());
		else
			return "";
	}
	public static Object getEntityFromNodeNode(String nodeString, boolean parseForType) {
		if(nodeString!=null && !nodeString.isEmpty()){
			if(parseForType){
				int i=nodeString.indexOf("/");
				if(i>-1){
					String node=nodeString.substring(0,i);
					return node;
				}
			}
			else {
				int i=nodeString.lastIndexOf("/");
				if(i>-1){
					String node=nodeString.substring(i+1,nodeString.length());
					return node;
				}
			}
		}
		return nodeString;
	}

	public static String getEntityFromRuleName(String name) {
		
		if(name!=null&&!name.isEmpty()){
			
			String[] ruleProps=name.split("\\"+BETeaAgentProps.BE_RULE_APP_NAME_SEPERATOR);
			
			if(ruleProps!=null&&ruleProps.length>1)
				return ruleProps[1];
		}
		
		return name;
	}
	
	public static String getRuleName(String name) {
		
		if(name!=null&&!name.isEmpty()){
			
			String[] ruleProps=name.split("\\"+BETeaAgentProps.BE_RULE_APP_NAME_SEPERATOR);
			
			if(ruleProps!=null&&ruleProps.length>=3)
				return ruleProps[2];
		}
		
		return name;
	}
	
    public static String getAppFromRuleName(String name) {
		
		if(name!=null&&!name.isEmpty()){
			
			String[] ruleProps=name.split("\\"+BETeaAgentProps.BE_RULE_APP_NAME_SEPERATOR);
			
			if(ruleProps!=null&&ruleProps.length>=0)
				return ruleProps[0];
		}
		
		return name;
	}
	
	
	public static String substituteTokens(String source, Monitorable monitorable) {

		if(monitorable instanceof Agent){
			Agent agent=(Agent)monitorable;
			
			source = source.replace(getExpression("agentId"), agent.getAgentId()+"");
			source = source.replace(getExpression("agentName"), agent.getAgentName());		
			
		}
		return source;
		
	}
	
	public static String getExpression(String variable) {
		return "${" + variable + "}";
	}
	
	public static boolean isValidAgentType(String test) {

	    for (AgentType agentType : AgentType.values()) {
	        if (agentType.getType().equals(test)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public static String getJmxServiceUrl(String host, int jmxPort) {
		return JMXConnUtil.buildJXMUrlStr(host, jmxPort);
	}
	
	public static Object getAttributeValue(String sourceName, List<Attribute> list) {
		
		for(Attribute attr: list){
			if(attr.getTargetMapping()!=null&&attr.getTargetMapping().getSource().equals(sourceName))
				return attr.getValue();
		}
		
		return null;
	}
	
	
	public static Attribute getAttribute(String sourceName, List<Attribute> list) {
		
		for(Attribute attr: list){
			if(attr.getTargetMapping()!=null&&attr.getTargetMapping().getSource().equals(sourceName))
				return attr;
		}
		
		return null;
	}

	public static void removeAttribute(Attribute removeAttr, List<Attribute> attributes) {
		
		for(Attribute attr: attributes){
			if(attr.getName().equals(removeAttr.getName())){
				attributes.remove(attr);
				return;
			}
		}
	}
	
	 public static Object clone (Object object) {
	    	
	    	if (object instanceof Serializable) {
	    		Object obj = null;
	    		byte[] bytes = null;
	    		
	    		try { //Serialize
	    			ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    			ObjectOutputStream oos = new ObjectOutputStream(bos);
	    			oos.writeObject(object);
	    			bytes = bos.toByteArray();
	    			oos.close();
	    			bos.close();
	    		}catch (Exception e){
	    			e.printStackTrace(); 
	    		}
	    		
	    		try{ //DeSerialize
	    			InputStream is = new ByteArrayInputStream(bytes);
	    			ObjectInputStream ois = new ObjectInputStream(is);
	    			obj = ois.readObject();
	    			ois.close();
	    			is.close();
	    		}catch (Exception e){
	    			e.printStackTrace(); 
	    		}
	    		return obj;
	    	}
	    	return null;
	    }
	
	
}
