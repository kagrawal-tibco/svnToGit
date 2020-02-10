package com.tibco.cep.bemm.monitoring.metric.probe.accumulator.jmx;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.management.AttributeList;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.TabularDataSupport;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.Mapping;
import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.MultiMapping;
import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.Property;
import com.tibco.cep.bemm.monitoring.metric.config.collectorconfig.TargetMapping;
import com.tibco.cep.bemm.monitoring.metric.interceptor.TeaJmxInterceptor;
import com.tibco.cep.bemm.monitoring.metric.interceptor.TeaJmxInterceptorFactory;
import com.tibco.cep.bemm.monitoring.metric.probe.AccumulatorConstants;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.AbstractAccumulator;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.Attribute;
import com.tibco.cep.bemm.monitoring.util.BeTeaAgentMonitorable;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;
import com.tibco.rta.Fact;

public abstract class AbstractJmxAccumulator extends AbstractAccumulator {
	
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(AbstractJmxAccumulator.class);
	private MessageService messageService;
	AbstractJmxAccumulator(){
		try {
			messageService = BEMMServiceProviderManager.getInstance().getMessageService();
		} catch (ObjectCreationException e) {
			e.printStackTrace();
		}	
	}
	
	protected List<Attribute> fetchFactAttributes(List<Mapping> outputMapperList,JmxBeanManager beanManager, Map.Entry<Monitorable, Object> entity, List<Attribute> attrList) throws IOException {
		String entityType = entity.getKey().getType().value();
		boolean emptyAttListFlag = false;
		for (Mapping mapper : outputMapperList) {
			/*
			 *NOTE: if entity attribute is present in the mapping and is same as the entity type(Refer the enum AgentType) 
			 *of current entity(i.e for which the collection is being done)
			 *Note : the types are applicable only for the agent types as the collection is done at agent level only
			 */
			if(mapper.getEntity()!=null)
				if(!(entityType.equals(mapper.getEntity())))
					continue;
			
			String source = mapper.getSource();
			source=MonitoringUtils.substituteTokens(source,entity.getKey());

			if (LOGGER.isEnabledFor(Level.DEBUG)) {
				LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.FETCHING_OBJECT,mapper.getSource()));
			}
			String mappingType=mapper.getType();
			List<TargetMapping> targetMappingList = mapper.getTargetMapping();
			Map<String, Attribute> attrMap=new HashMap<String, Attribute>();

			try
			{
				if(mappingType.equals(AccumulatorConstants.MAPPING_TYPE_METHOD)){	
					attrMap = getAttributeListForOperations(beanManager,source,targetMappingList,entity.getKey());
				}
				else{
					attrMap = getAttributeListForAttributes(beanManager,source,targetMappingList);
				}
			}
			catch(JmxException e){
					LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.POPULATING_MBEAN_ATTRIBUTES_ERROR,source),e);
			}
			
			//Use this map to pass additional properties/entities to interceptor
			Map<String, Object> genericMap = new HashMap<String, Object>();
			 
			for(Entry<String, Attribute> entry:attrMap.entrySet()){
				if(entry.getValue().getTargetMapping().getInterceptorName()!=null&&!entry.getValue().getTargetMapping().getInterceptorName().isEmpty()){

					TeaJmxInterceptor inceptor;
					try {
						inceptor = TeaJmxInterceptorFactory.getInstance(entry.getValue().getTargetMapping().getInterceptorName(),false);
						//inceptor.init((Agent)entity.getKey(), new ArrayList<Attribute>(attrMap.values()), null);
						if(entry.getValue()!=null){
							Attribute attr = (Attribute) inceptor.performAction(entry.getValue(),entity.getKey(), new ArrayList<Attribute>(attrMap.values()), genericMap);
							if(attr.getValue()!=null){
								entry.setValue(attr);
							}else{
								attrMap.remove(attr.getName());
							}
						}
					} 
					catch (Exception e) {
							LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.GETTING_INTERCEPTOR_ERROR,entry.getValue().getTargetMapping().getInterceptorName()),e);
					}
				}
			}
			
			attrList.addAll(new ArrayList<Attribute>(attrMap.values()));
		}
		
		if(emptyAttListFlag == true){
			attrList.clear();
		}
		return attrList;
	}
	
	
	protected Map<String, Attribute> getAttributeListForAttributes(JmxBeanManager beanManager, String mbeanName, List<TargetMapping> mappings) throws JmxException, IOException {

		HashSet<String> attributeNames = new HashSet<String>();
		Map<String,Attribute> factAttrMap=new HashMap<String,Attribute>();

		for(TargetMapping mapping:mappings){
			String attrName=mapping.getSource();
			if(mapping.getSource()!=null&&mapping.getSource().indexOf(".")>0){
				String[] name = attrName.split("\\.");
				attrName=name[0];
			}
			attributeNames.add(attrName);

			Attribute factAttribute=new Attribute();

			//Initialing fact attribute with default values
			Object defaultAttrVal=mapping.getDefaultValue();
			if(defaultAttrVal!=null){
				defaultAttrVal=MonitoringUtils.parseValue(defaultAttrVal, mapping.getDatatype());
			}
			factAttribute.setName(mapping.getTarget());
			factAttribute.setValue(defaultAttrVal);
			factAttribute.setTargetMapping(mapping);
			factAttrMap.put(mapping.getTarget(),factAttribute);
		}
		AttributeList list= beanManager.getAttributes(mbeanName, attributeNames.toArray(new String[attributeNames.size()]));

		for(TargetMapping mapping:mappings){
			for( javax.management.Attribute attr:list.asList()){
				Attribute factAttribute=factAttrMap.get(mapping.getTarget());
				if(factAttribute!=null){
					if(mapping.getSource().equals(attr.getName())){
						Object attrVal=attr.getValue();
						attrVal=getParsedValue(attrVal,mapping.getDatatype(),mapping.getDefaultValue());
						factAttribute.setValue(attrVal);

					}
					else if(mapping.getSource()!=null&&mapping.getSource().indexOf(".")>0){
						String[] name = mapping.getSource().split("\\.");
						if(name[0].equals(attr.getName()) && name.length>1){
							if(attr.getValue() instanceof CompositeData){
								CompositeData data = (CompositeData)attr.getValue();
								Object attrVal=getParsedValue(data.get(name[1]),mapping.getDatatype(),mapping.getDefaultValue());
								factAttribute.setValue(attrVal);
							}
						}
					}
				}
			}
		}
		return factAttrMap;
	}



	protected Object getParsedValue(Object attrVal, String datatype, String defaultValue) {

		if(attrVal==null&&!AccumulatorConstants.NA_STR.equals(defaultValue.toString()))
			attrVal = defaultValue;

		if(attrVal!=null&&datatype!=null&&!"".equals(datatype)){
			attrVal=MonitoringUtils.parseValue(attrVal,datatype);
		}
		return attrVal;
	}

	protected Map<String, Attribute> getAttributeListForOperations(JmxBeanManager beanManager, String mbeanName, List<TargetMapping> mappings, Monitorable entity) throws JmxException, IOException {

		HashSet<String> operationNames = new HashSet<String>();
		HashMap<String,Object[]> operationParameters = new HashMap<String,Object[]>();
		HashMap<String,String[]> operationSignatures = new HashMap<String,String[]>();
		
		Map<String,Attribute> factAttrMap=new HashMap<String,Attribute>();

		for(TargetMapping mapping:mappings){
			String attrName=mapping.getSource();
			if(mapping.getSource()!=null&&mapping.getSource().indexOf(".")>0){
				String[] name = attrName.split("\\.");
				attrName=name[0];
			}
			
			Attribute factAttribute=new Attribute();

			//Initialing fact attribute with default values
			Object defaultAttrVal=mapping.getDefaultValue();
			if(defaultAttrVal!=null){
				defaultAttrVal=MonitoringUtils.parseValue(defaultAttrVal, mapping.getDatatype());
			}
			factAttribute.setName(mapping.getTarget());
			factAttribute.setValue(defaultAttrVal);
			factAttribute.setTargetMapping(mapping);
			factAttrMap.put(mapping.getTarget(),factAttribute);
			Object[] parameters = new Object[]{};
			String[] signature = new String[]{};
			
			if(mapping.getArg().size()>=0){
				List<Property> argList=mapping.getArg();
				parameters = new Object[argList.size()];
				signature = new String[argList.size()];

				for (int i=0;i<argList.size();i++) {
					Property arg=argList.get(i);
					String paramType = arg.getType();
					String javaType = MonitoringUtils.getJavaType(paramType);
					String paramValue = arg.getValue();
					paramValue=MonitoringUtils.substituteTokens(paramValue, entity);
					parameters[i] = MonitoringUtils.getValueForType(paramValue, paramType);
					signature[i] = javaType;
				}
			}
			
			operationNames.add(attrName);
			operationParameters.put(attrName, parameters);
			operationSignatures.put(attrName, signature);
			
		}

		Map<String,Object> resultMap= beanManager.invokeAll(mbeanName, operationNames.toArray(new String[operationNames.size()]),operationParameters,operationSignatures);

		for(TargetMapping mapping:mappings){
			for( Map.Entry<String,Object> entry :resultMap.entrySet()){
				Attribute factAttribute=factAttrMap.get(mapping.getTarget());

				if(factAttribute!=null){
					if(mapping.getSource().equals(entry.getKey())){
						Object attrVal=entry.getValue();

						attrVal=getParsedValue(attrVal,mapping.getDatatype(),mapping.getDefaultValue());
						factAttribute.setValue(attrVal);
					}
					else if(mapping.getSource()!=null&&mapping.getSource().indexOf(".")>0){
						String[] name = mapping.getSource().split("\\.");
						if(name[0].equals(entry.getKey()) && name.length>1)
							if(entry.getValue()==null){
								Object attrVal=getParsedValue(null,mapping.getDatatype(),mapping.getDefaultValue());
								factAttribute.setValue(attrVal);
							}
							else if(entry.getValue() instanceof TabularDataSupport){
								TabularDataSupport data = (TabularDataSupport)entry.getValue();

								if(name[1].equalsIgnoreCase("length")){

									Object attrVal=getParsedValue(data.size(),mapping.getDatatype(),mapping.getDefaultValue());
									factAttribute.setValue(attrVal);
								}else{
									for (Iterator iter = ((TabularDataSupport) entry.getValue()).values().iterator(); iter.hasNext();) {
										CompositeDataSupport currentRow = (CompositeDataSupport) iter.next();

										Object attrVal=getParsedValue(currentRow.get(name[1]),mapping.getDatatype(),mapping.getDefaultValue());
										factAttribute.setValue(attrVal);
										break;
									}
								}
							}else if(entry.getValue() instanceof Object[]){
								Object[] data = (Object[])entry.getValue();

								if(name[1].equalsIgnoreCase("length")){

									Object attrVal=getParsedValue(data.length,mapping.getDatatype(),mapping.getDefaultValue());
									factAttribute.setValue(attrVal);
								}
							}else if(entry.getValue() instanceof List<?>){
								List<?> data = (List<?>)entry.getValue();

								if(name[1].equalsIgnoreCase("length")){
									factAttribute.setValue(data.size());

								}
							}
							else if(entry.getValue() instanceof CompositeData){
								CompositeData data = (CompositeData)entry.getValue();
								factAttribute.setValue(data.get(name[1]));
							}
					}
				}
			}
		}
		return factAttrMap;
	}
	
	protected List<Attribute> getInterceptedValues(List<Attribute> attributes, MultiMapping multiMap, Monitorable monitorable) {
		
		//Use this map to pass additional properties/entities to interceptor
		Map<String, Object> genericMap = new HashMap<String, Object>();
		
		boolean emptyAttListFlag = false;
		
		for(TargetMapping mapping:multiMap.getTargetMapping()){

			if(mapping.getInterceptorName()!=null&&!mapping.getInterceptorName().isEmpty()){

				TeaJmxInterceptor inceptor;
				try {
					inceptor = TeaJmxInterceptorFactory.getInstance(mapping.getInterceptorName(),false);
					//inceptor.init((Agent)entity.getKey(), attributes, null);
					if(MonitoringUtils.getAttribute(mapping.getSource(),attributes)!=null){
						Attribute attr = (Attribute) inceptor.performAction(MonitoringUtils.getAttribute(mapping.getSource(),attributes),monitorable, attributes, genericMap);
						MonitoringUtils.removeAttribute(attr,attributes);
						if(attr.getValue()!=null){
							attributes.add(attr);
						}
					}
				} 
				catch (Exception e) {
					e.printStackTrace();
					LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.GETTING_INTERCEPTOR_ERROR, mapping.getInterceptorName()),e);
				}
			}
		}
		if(emptyAttListFlag){
			attributes.clear();
		}
		return attributes;
	}
	
	public List<List<Attribute>> fetchMultiMapFactAttributes(MultiMapping multiMap,Entry<Monitorable, Object> entity,JmxBeanManager beanManager) throws IOException
	{
		String source=multiMap.getSource();
		source=MonitoringUtils.substituteTokens(source, entity.getKey());
		
		String name=multiMap.getName();

		if (LOGGER.isEnabledFor(Level.DEBUG)) {
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.FETCHING_OBJECTNAME_ATTRIBUTE, multiMap.getSource(), name));
		}	
		Object[] parameters = new Object[]{};
		String[] signature = new String[]{};

		if(multiMap.getArg().size()>0){
			List<Property> argList=multiMap.getArg();
			parameters = new Object[argList.size()];
			signature = new String[argList.size()];

			for (int i=0;i<argList.size();i++) {
				Property arg=argList.get(i);
				String paramType = arg.getType();
				String javaType = MonitoringUtils.getJavaType(paramType);
				String paramValue = arg.getValue();
				paramValue = MonitoringUtils.substituteTokens(paramValue, entity.getKey());
				parameters[i] = MonitoringUtils.getValueForType(paramValue, paramType);
				signature[i] = javaType;
			}

		}
		Object value=null;
		try{
			if(AccumulatorConstants.MAPPING_TYPE_ATTRIBUTE.equals(multiMap.getType())){
				AttributeList list=beanManager.getAttributes(source,new String[]{name});
				if(list!=null && !list.isEmpty()){
					javax.management.Attribute mbeanValue=(javax.management.Attribute)list.get(0);
					value=mbeanValue.getValue();
				}
			}
			else{
				value=beanManager.invoke(source,name,parameters,signature);
			}
		}
		catch(JmxException e){
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.INVOKING_MBEAN_OPERATION_ERROR,source,name),e);
		}

		List<List<Attribute>> attributes = new ArrayList<List<Attribute>>();

		if(value!=null&&value instanceof TabularDataSupport){
			for (Iterator iter = ((TabularDataSupport) value).values().iterator(); iter.hasNext();) {
				List<Attribute> attrRecord = new ArrayList<Attribute>();
				CompositeDataSupport currentRow = (CompositeDataSupport) iter.next();
				for(TargetMapping mapping:multiMap.getTargetMapping()){

					Attribute attr=new Attribute();
					attr.setTargetMapping(mapping);
					Object attrVal=currentRow.get(mapping.getSource());

					if(attrVal==null&&!AccumulatorConstants.NA_STR.equals(mapping.getDefaultValue().toString()))
						attrVal = mapping.getDefaultValue();

					if(attrVal!=null&&mapping.getDatatype()!=null&&!"".equals(mapping.getDatatype().trim())){
						attrVal=MonitoringUtils.parseValue(attrVal,mapping.getDatatype());
					}

					attr.setName(mapping.getTarget());
					attr.setValue(attrVal);
					attrRecord.add(attr);
				}
				attributes.add(attrRecord);
			}
		}
		else if(value!=null&&value instanceof CompositeData[]) {
			for(CompositeData val : (CompositeData[])value){
				CompositeData currentRow = (CompositeData) val;
				List<Attribute> attrRecord = new ArrayList<Attribute>();
				for(TargetMapping mapping:multiMap.getTargetMapping()) {

					Attribute attr=new Attribute();
					attr.setTargetMapping(mapping);
					Object attrVal=currentRow.get(mapping.getSource());

					if(attrVal==null&&!AccumulatorConstants.NA_STR.equals(mapping.getDefaultValue().toString()))
						attrVal = mapping.getDefaultValue();

					if(attrVal!=null&&mapping.getDatatype()!=null&&!"".equals(mapping.getDatatype().trim())){
						attrVal=MonitoringUtils.parseValue(attrVal,mapping.getDatatype());
					}

					attr.setName(mapping.getTarget());
					attr.setValue(attrVal);
					attrRecord.add(attr);
				}
				attributes.add(attrRecord);
			}
		}
		else if(value!=null&&value instanceof CompositeDataSupport[]) {
			for(CompositeDataSupport val : (CompositeDataSupport[])value){
				CompositeDataSupport currentRow = (CompositeDataSupport) val;
				List<Attribute> attrRecord = new ArrayList<Attribute>();
				for(TargetMapping mapping:multiMap.getTargetMapping()) {

					Attribute attr=new Attribute();
					attr.setTargetMapping(mapping);
					Object attrVal=currentRow.get(mapping.getSource());

					if(attrVal==null&&!AccumulatorConstants.NA_STR.equals(mapping.getDefaultValue().toString()))
						attrVal = mapping.getDefaultValue();

					if(attrVal!=null&&mapping.getDatatype()!=null&&!"".equals(mapping.getDatatype().trim())){
						attrVal=MonitoringUtils.parseValue(attrVal,mapping.getDatatype());
					}

					attr.setName(mapping.getTarget());
					attr.setValue(attrVal);
					attrRecord.add(attr);
				}
				attributes.add(attrRecord);
			}
		}
		else if(value!=null&&value instanceof CompositeDataSupport) {

			CompositeDataSupport currentRow = (CompositeDataSupport) value;
			List<Attribute> attrRecord = new ArrayList<Attribute>();
			for(TargetMapping mapping:multiMap.getTargetMapping()) {

				Attribute attr=new Attribute();
				attr.setTargetMapping(mapping);
				Object attrVal=currentRow.get(mapping.getSource());

				if(attrVal==null&&!AccumulatorConstants.NA_STR.equals(mapping.getDefaultValue().toString()))
					attrVal = mapping.getDefaultValue();

				if(attrVal!=null&&mapping.getDatatype()!=null&&!"".equals(mapping.getDatatype().trim())){
					attrVal=MonitoringUtils.parseValue(attrVal,mapping.getDatatype());
				}

				attr.setName(mapping.getTarget());
				attr.setValue(attrVal);
				attrRecord.add(attr);
			}
			attributes.add(attrRecord);
		}
		return attributes;
	}
	
	public void publishMultiMapFacts(List<MultiMapping> multiMappingList, Entry<Monitorable, Object> entity,JmxBeanManager beanManager, String schemaName) throws  IOException {
		String entityType = entity.getKey().getType().value();
		for(MultiMapping multiMap:multiMappingList){
			if(multiMap.getEntity()!=null)
				if(!(multiMap.getEntity().contains(entityType)))
					continue;
			List<List<Attribute>> records=fetchMultiMapFactAttributes(multiMap,entity,beanManager);
			if(records.size()>0){
				for(List<Attribute> attributes:records){
				attributes=getInterceptedValues(attributes,multiMap,entity.getKey());
				publish(attributes,entity.getKey(),schemaName);
				}
			}
		}
	}
	public void publishSingleMapFact(List<Mapping> outputMapperList, Entry<Monitorable, Object> entity, JmxBeanManager beanManager, String schemaName) throws IOException {
		List<Attribute> attrList = new ArrayList<Attribute>();
		fetchFactAttributes(outputMapperList, beanManager,entity, attrList);
		publish(attrList,entity.getKey(),schemaName);
	}
	
	public void publish(List<Attribute> attrList,Monitorable entity,String schemaName){
		if(attrList!=null && attrList.size()>0){
			List<Attribute> factAttr=null;

			if(entity instanceof Agent){
				if(((Agent)entity).getInstance().isRunning()){
					factAttr = ((Agent) entity).getBasicFactAttributes();
					factAttr.addAll(attrList);			
				}
			}
			else if(entity instanceof BeTeaAgentMonitorable){
				if(((BeTeaAgentMonitorable)entity).getStatus().equals("running")){
					factAttr = ((BeTeaAgentMonitorable) entity).getBasicFactAttributes();
					factAttr.addAll(attrList);
				}
			}
			if(factAttr!=null){
				Fact fact = null;
				try {
					fact = getFact(factAttr,schemaName);
					publishFact(fact);
				} catch (Exception e) {
					LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.PUBLISHING_FACT_ERROR), e);
				}
			}
		}
	}
}
