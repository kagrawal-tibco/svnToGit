package com.tibco.cep.bemm.monitoring.metric.rule.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.xml.sax.InputSource;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.cep.bemm.monitoring.metric.config.attrmapconfig.AttrMapping;
import com.tibco.cep.bemm.monitoring.metric.config.attrmapconfig.Entity;
import com.tibco.cep.bemm.monitoring.metric.config.attrmapconfig.RuleEntityAttrMap;
import com.tibco.cep.bemm.monitoring.metric.rule.BEEntityMetricRuleService;
import com.tibco.cep.bemm.monitoring.metric.rule.RuleExistsException;
import com.tibco.cep.bemm.monitoring.util.MonitoringUtils;
import com.tibco.rta.RtaException;
import com.tibco.rta.RtaSession;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.Measurement;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.InvokeConstraint;
import com.tibco.rta.model.rule.InvokeConstraint.Constraint;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.rule.RuleFactory;
import com.tibco.rta.model.rule.impl.RuleDefImpl;
import com.tibco.rta.model.rule.mutable.MutableActionDef;
import com.tibco.rta.model.rule.mutable.MutableRuleDef;
import com.tibco.rta.model.serialize.impl.SerializationUtils;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.filter.AndFilter;
import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.impl.QueryFactory;
import com.tibco.rta.util.ServiceConstants;
import com.tibco.tea.agent.be.util.BEEntityHealthStatus;
import com.tibco.tea.agent.be.util.BETeaAgentProps;

public class BEEntityMetricRuleServiceImpl  extends AbstractStartStopServiceImpl implements BEEntityMetricRuleService {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BEEntityMetricRuleService.class);
	private MessageService messageService=null;
	private String 		   rulesConfigFileLoc   	="";
	private String         attrMapConfigFile    	="";
	Map<String, Object>    entityMap            	=new HashMap<String, Object>();
	private boolean 	   enablePreconfiguredRules =false; 
	
	private static final String ATTR_MAP_JAXB_PKG = "com.tibco.cep.bemm.monitoring.metric.config.attrmapconfig";
	
	@Override
	public void init(Properties configuration) throws Exception {
		super.init(configuration);
		rulesConfigFileLoc			=(String) ConfigProperty.BE_TEA_AGENT_METRICS_RULES_CONFIG_FILE.getValue(configuration);
		messageService 				= BEMMServiceProviderManager.getInstance().getMessageService();
		attrMapConfigFile 			= (String) ConfigProperty.BE_TEA_AGENT_METRICS_RULES_ATTR_MAP_FILE.getValue(configuration);
		enablePreconfiguredRules	=Boolean.parseBoolean((String)ConfigProperty.BE_TEA_AGENT_DEFAULT_RULES_ENABLE.getValue(configuration));
	}
	
	/**
	 * should be called after aggregation service start.
	 * 
	 */
	@Override
	public void start() throws ObjectCreationException, Exception {
		RtaSession session=BEMMServiceProviderManager.getInstance()
				.getAggregationService().getSession();

		File file = new File(attrMapConfigFile);
		
		JAXBContext jaxbContext = JAXBContext.newInstance(ATTR_MAP_JAXB_PKG, this.getClass().getClassLoader());

		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		if(file!=null){
			RuleEntityAttrMap entityAttrsMap=(RuleEntityAttrMap)jaxbUnmarshaller.unmarshal(file);
			populateEntityMap(entityAttrsMap);
		}

		//Creating pre-configured rules for Loaded application
		if(enablePreconfiguredRules){
			createPreconfiguredRulesForAllApps();
		}
	}

	@Override
	public String delete(List<String> ruleNames) throws Exception {
		int errorCount=0;
		for(String ruleName:ruleNames){
			try {	

				if (ruleName != null && !ruleName.isEmpty()) {
					RtaSession session=BEMMServiceProviderManager.getInstance()
							.getAggregationService().getSession();

					if(session!=null) {
						session.deleteRule(ruleName);
					}
				}	
			} catch (RtaException e) {
				LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.DELETE_RULE_FAILED, ruleName), e);
				errorCount++;
			}
		}
		if(errorCount==ruleNames.size())
			return messageService.getMessage(MessageKey.RULE_DELETE_ERROR_MESSAGE);
		
		return messageService.getMessage(MessageKey.RULE_DELETE_SUCCESS_MESSAGE);
	}

	@Override
	public ArrayList<RuleDef> getAppRules(String appName, String loggedInUser ,boolean isAdmin) throws Exception {
		String ruleAppName = "";
		ArrayList<RuleDef> appRules = new ArrayList<RuleDef>();

		try {
			RtaSession session = BEMMServiceProviderManager.getInstance()
					.getAggregationService().getSession();
			if(session!=null) {

				for (RuleDef ruleDef : session.getAllRuleDefs()) {
					int index = ruleDef.getName().indexOf(
							BETeaAgentProps.BE_RULE_APP_NAME_SEPERATOR);

					if (index > 0) {
						ruleAppName = ruleDef.getName().substring(0, index);
					}
					if (appName != null && !appName.isEmpty()) {
						if (appName.equals(ruleAppName)) {
							if(!isAdmin && loggedInUser!=null && loggedInUser.equals(ruleDef.getUserName()))
								appRules.add(ruleDef);
							else if(isAdmin)
								appRules.add(ruleDef);
						}
					}
				}
			}
		} catch (RtaException e) {
			LOGGER.log(
					Level.ERROR,
					messageService.getMessage(MessageKey.GETTING_RULES_FOR_APP_ERROR,
					appName),e);
		}
		return appRules;
	}

	@Override
	public String createRule(RuleDef ruleDef) throws Exception {
		if (ruleDef != null) {
			try {
				RtaSession session = BEMMServiceProviderManager.getInstance()
						.getAggregationService().getSession();

				if(session!=null)
					session.createRule(ruleDef);

			}catch (RuleExistsException e) {
				LOGGER.log(Level.DEBUG, "Rule :"+MonitoringUtils.getRuleName(ruleDef.getName())+" for selected entity already exists", e);
				throw new RuleExistsException(messageService.getMessage(MessageKey.RULE_EXISTS_ERROR_MESSAGE,MonitoringUtils.getRuleName(ruleDef.getName())));
			}
			catch (RtaException e) {
				LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.CREATING_RULE_ERROR), e);
				return messageService.getMessage(MessageKey.RULE_CREATE_ERROR_MESSAGE);
			}
		}
		return messageService.getMessage(MessageKey.RULE_CREATE_SUCCESS_MESSAGE);
	}

	@Override
	public String updateRule(RuleDef ruleDef) throws Exception {
		if (ruleDef != null) {
			try {
				RtaSession session = BEMMServiceProviderManager.getInstance()
						.getAggregationService().getSession();

				if(session!=null)
					session.updateRule(ruleDef);

			} catch (RtaException e) {
				LOGGER.log(Level.ERROR, messageService.getMessage(MessageKey.UPDATING_RULE_ERROR),e);
				return messageService.getMessage(MessageKey.RULE_UPDATE_ERROR_MESSAGE);
			}
		}
		return messageService.getMessage(MessageKey.RULE_UPDATE_SUCCESS_MESSAGE);
	}
	
	@Override
	public void enableLoadedRules() throws Exception {

		RtaSession session = BEMMServiceProviderManager.getInstance()
				.getAggregationService().getSession();

		if(session!=null) {
			
			if(session!=null) {
				for (RuleDef ruleDef : session.getAllRuleDefs()) {
					if (ruleDef instanceof MutableRuleDef){
						String isEnabled=((MutableRuleDef) ruleDef).getProperty("isEnabled");
						if(isEnabled!=null&& !isEnabled.isEmpty() && isEnabled.equals("true")){
							((MutableRuleDef) ruleDef).setEnabled(true);
							session.updateRule(ruleDef);
						}
					}
				}
			}
		}
	}

	@Override
	public List<ActionFunctionDescriptor> getActionDescriptors() throws Exception {

		List<ActionFunctionDescriptor> list=new ArrayList<ActionFunctionDescriptor>();

		RtaSession session = BEMMServiceProviderManager.getInstance().getAggregationService().getSession();

		if(session!=null)
			list =session.getAllActionFunctionDescriptors();

		return list;
	}
	
	@Override
	public RuleDef prepareRule(RuleDefImpl ruleDef, String appName, String entity ,String loggedInUser,boolean isUpdate,boolean isAdmin) throws Exception
	{	

		QueryByFilterDef setConditionInput=(QueryByFilterDef) ruleDef.getSetCondition();
		QueryByFilterDef clearConditionInput=(QueryByFilterDef) ruleDef.getClearCondition();
		
		//entity info 
		@SuppressWarnings("unchecked")
		Map<String,Object> entityProps=(Map<String, Object>) entityMap.get(entity);
		String schema=(String) entityProps.get("schema");
		String hierarchy=(String) entityProps.get("hierarchy");
		String cube=(String) entityProps.get("cube");
		String agentType=(String) entityProps.get("agentType");
		String dimLevel=(String) entityProps.get("dimLevel");
		
		
		//-----------------SETTING CONDITION----------------
		QueryFactory qfac = QueryFactory.INSTANCE;
		QueryByFilterDef setCondition = qfac.newQueryByFilterDef(schema, cube, hierarchy,"");
		
		QueryByFilterDef clearCondition = qfac.newQueryByFilterDef(schema, cube,hierarchy,"");
		
		Filter appFilter = QueryFactory.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, MetricAttribute.DIM_APP,appName);
		
		Filter dimensionFilter =QueryFactory.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL,dimLevel);
		
		AndFilter andFilterSet = QueryFactory.INSTANCE.newAndFilter();
		AndFilter andFilterSetPreConditions = QueryFactory.INSTANCE.newAndFilter();

		AndFilter andFilterClear = QueryFactory.INSTANCE.newAndFilter();
		AndFilter andFilterClearPreConditions = QueryFactory.INSTANCE.newAndFilter();
		
		
		//Adding Default dimension level and app level filters
		if(!"na".equals(agentType) && MonitoringUtils.isValidAgentType(agentType)){
			Filter agentTypeFilter=QueryFactory.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME,MetricAttribute.AGENT_TYPE,agentType);
			andFilterSetPreConditions.addFilter(appFilter,dimensionFilter,agentTypeFilter);
			andFilterClearPreConditions.addFilter(appFilter,dimensionFilter,agentTypeFilter);
		}
		else {
			andFilterSetPreConditions.addFilter(appFilter,dimensionFilter);
			andFilterClearPreConditions.addFilter(appFilter,dimensionFilter);
		}
		
		andFilterSet.addFilter(andFilterSetPreConditions,setConditionInput.getFilter());
		andFilterClear.addFilter(andFilterClearPreConditions,clearConditionInput.getFilter());
		
		setCondition.setFilter(andFilterSet);
		clearCondition.setFilter(andFilterClear);
		
		
		RuleFactory factory = new RuleFactory();
		MutableRuleDef preparedRule = factory.newRuleDef(appName+BETeaAgentProps.BE_RULE_APP_NAME_SEPERATOR+entity+BETeaAgentProps.BE_RULE_APP_NAME_SEPERATOR+ruleDef.getName());
		preparedRule.setSetCondition(setCondition);
		preparedRule.setClearCondition(clearCondition);
		
		InvokeConstraint invokeConstraint = factory.newInvokeConstraint(Constraint.ONCE_ONLY);
		
		//-----------------SETTING ACTIONS----------------
		for(ActionDef action:ruleDef.getSetActionDefs()){
			MutableActionDef actionDef=factory.newSetActionDef(preparedRule, action.getActionFunctionDescriptor(), invokeConstraint);
			actionDef.setAlertLevel(action.getAlertLevel());
			actionDef.setName(action.getName());
		}
		
		for(ActionDef action:ruleDef.getClearActionDefs()){
			MutableActionDef actionDef=factory.newClearActionDef(preparedRule, action.getActionFunctionDescriptor(), invokeConstraint);
			actionDef.setAlertLevel(action.getAlertLevel());
			actionDef.setName(action.getName());
		}
		
		//Checking if update rule , if true setting the created date
		if(isUpdate)
			preparedRule.setCreatedDate(ruleDef.getCreatedDate());
		
		//Setting a property with entity in Rule.This will denote , for which entity level the rule is written for.
		preparedRule.setProperty(BETeaAgentProps.BEMM_RULE_ENTITY_PROP,entity);
		preparedRule.setProperty(BETeaAgentProps.BEMM_RULE_IS_ADMIN,isAdmin+"");
		
		//Set Username of user who created the rule
		preparedRule.setUserName(loggedInUser);
		
		//set description for created rule
		preparedRule.setDescription(ruleDef.getDescription());
		
		//set the rule enabled/disabled
		preparedRule.setEnabled(ruleDef.isEnabled());
		
		//set the rule state
		preparedRule.setSetOnce(ruleDef.isSetOnce());
		
		return preparedRule;
	}
	
	
	public void createPreconfiguredRulesForAllApps() throws Exception {
		
		Map<String, Application> applications = BEMMServiceProviderManager.getInstance().getBEApplicationsManagementService().getApplications();
		
		for(Entry<String, Application> entry:applications.entrySet()){
			createPreconfiguredRules(entry.getValue());
		}
		
	}
	
	@Override
	public void createPreconfiguredRules(Application application) throws Exception {
		
		if(!enablePreconfiguredRules)
			return;
		
		File rulesConfigFile=new File(rulesConfigFileLoc);
		if(rulesConfigFile!=null && rulesConfigFile.getName()!=null && rulesConfigFile.getName().endsWith(BETeaAgentProps.BE_DATASTORE_RULE_EXTENSION)){
			InputSource source=new InputSource(new FileInputStream(rulesConfigFile));
			List<RuleDef> rules=(List<RuleDef>) SerializationUtils.deserializeRules(source);
			
			for(RuleDef rule : rules){
				RuleDef preparedRule=preparePreconfiguredRule((MutableRuleDef)rule,application);
				if(preparedRule!=null)
					createRule(preparedRule);
			}
		}
	}

	private RuleDef preparePreconfiguredRule(MutableRuleDef ruleDef, Application application) {
		
		MutableRuleDef preparedRule=null;
		String entity=ruleDef.getProperty(BETeaAgentProps.BEMM_RULE_ENTITY_PROP);

		QueryByFilterDef setConditionInput=(QueryByFilterDef) ruleDef.getSetCondition();
		QueryByFilterDef clearConditionInput=(QueryByFilterDef) ruleDef.getClearCondition();

		//entity info
		@SuppressWarnings("unchecked")
		Map<String,Object> entityProps=(Map<String, Object>) entityMap.get(entity);
		if(entityProps!=null){

			String schema=(String) entityProps.get("schema");
			String hierarchy=(String) entityProps.get("hierarchy");
			String cube=(String) entityProps.get("cube");
			String agentType=(String) entityProps.get("agentType");
			String dimLevel=(String) entityProps.get("dimLevel");

			//-----------------SETTING CONDITION----------------
			QueryFactory qfac = QueryFactory.INSTANCE;
			QueryByFilterDef setCondition = qfac.newQueryByFilterDef(schema,cube, hierarchy,"");

			QueryByFilterDef clearCondition = qfac.newQueryByFilterDef(schema, cube, hierarchy,"");


			Filter appFilter = QueryFactory.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, MetricAttribute.DIM_APP,application.getName());

			Filter dimensionFilter =QueryFactory.INSTANCE.newEqFilter(MetricQualifier.DIMENSION_LEVEL,dimLevel);

			AndFilter andFilterSet = QueryFactory.INSTANCE.newAndFilter();
			AndFilter andFilterSetPreConditions = QueryFactory.INSTANCE.newAndFilter();

			AndFilter andFilterClear = QueryFactory.INSTANCE.newAndFilter();
			AndFilter andFilterClearPreConditions = QueryFactory.INSTANCE.newAndFilter();
			
			//Adding Default dimension level and app level filters
			if(!"na".equals(agentType) && MonitoringUtils.isValidAgentType(agentType)){
				Filter agentTypeFilter=QueryFactory.INSTANCE.newEqFilter(FilterKeyQualifier.DIMENSION_NAME,MetricAttribute.AGENT_TYPE,agentType);
				andFilterSetPreConditions.addFilter(appFilter,dimensionFilter,agentTypeFilter);
				andFilterClearPreConditions.addFilter(appFilter,dimensionFilter,agentTypeFilter);
			}
			else {
				andFilterSetPreConditions.addFilter(appFilter,dimensionFilter);
				andFilterClearPreConditions.addFilter(appFilter,dimensionFilter);
			}
			
			andFilterSet.addFilter(andFilterSetPreConditions,setConditionInput.getFilter());
			andFilterClear.addFilter(andFilterClearPreConditions,clearConditionInput.getFilter());
			
			setCondition.setFilter(andFilterSet);
			clearCondition.setFilter(andFilterClear);


			RuleFactory factory = new RuleFactory();
			preparedRule = factory.newRuleDef(application.getName()+BETeaAgentProps.BE_RULE_APP_NAME_SEPERATOR+entity+BETeaAgentProps.BE_RULE_APP_NAME_SEPERATOR+ruleDef.getName());
			preparedRule.setSetCondition(setCondition);
			preparedRule.setClearCondition(clearCondition);

			InvokeConstraint invokeConstraint = factory.newInvokeConstraint(Constraint.ONCE_ONLY);

			//-----------------SETTING ACTIONS----------------
			for(ActionDef action:ruleDef.getSetActionDefs()){
				MutableActionDef actionDef=factory.newSetActionDef(preparedRule, action.getActionFunctionDescriptor(), invokeConstraint);
				actionDef.setAlertLevel(action.getAlertLevel());
				actionDef.setName(action.getName());
			}

			for(ActionDef action:ruleDef.getClearActionDefs()){
				MutableActionDef actionDef=factory.newClearActionDef(preparedRule, action.getActionFunctionDescriptor(), invokeConstraint);
				actionDef.setAlertLevel(action.getAlertLevel());
				actionDef.setName(action.getName());
			}

			//Setting a property with entity in Rule.This will denote , for which entity level the rule is written for.
			preparedRule.setProperty(BETeaAgentProps.BEMM_RULE_ENTITY_PROP,entity);
			preparedRule.setProperty(BETeaAgentProps.BEMM_RULE_IS_ADMIN,"true");

			//Set Username of user who created the rule
			preparedRule.setUserName(ruleDef.getUserName());

			//set description for created rule
			preparedRule.setDescription(ruleDef.getDescription());

			//set the rule enabled/disabled
			preparedRule.setEnabled(ruleDef.isEnabled());
		}
		return preparedRule;
	}
	
	@Override
	public String updateRulesState(List<String> ruleNames, boolean isEnable) throws Exception {


		RtaSession session=BEMMServiceProviderManager.getInstance().getAggregationService().getSession();

		if(session!=null) {
			for(String ruleName : ruleNames){
				RuleDef ruleDef=session.getRule(ruleName);
				if (ruleDef instanceof MutableRuleDef)
					((MutableRuleDef) ruleDef).setEnabled(isEnable);
					((MutableRuleDef) ruleDef).setProperty("isEnabled",isEnable+"");
					session.updateRule(ruleDef);
			}
		}
		return messageService.getMessage(MessageKey.RULE_STATUS_UPDATE_SUCCESS_MESSAGE);
	}
	

	private void populateEntityMap(RuleEntityAttrMap entityAttrsMap) throws Exception {

		RtaSession session=BEMMServiceProviderManager.getInstance()
				.getAggregationService().getSession();
		if(session!=null ) {
			RtaSchema schema =session.getSchema(BETeaAgentProps.BEMM_FACT_SCHEMA);
			if(schema!=null) {
				for(Cube cube : schema.getCubes()) {
					for(Entity entity:entityAttrsMap.getEntity()){
						HashSet<Map<String,String>> attributes=new HashSet<Map<String,String>>();
						DimensionHierarchy hierarchy=cube.getDimensionHierarchy(entity.getHierarchy());
						Map<String,Object> props=new HashMap<String,Object>();
						
						attributes=getMeasurementAttributesForEntity(hierarchy,entity,attributes);						
						attributes=getDimensionAttributesForEntity(hierarchy,entity,attributes);
						props.put("index",entity.getIndex());
						props.put("schema",entity.getSchema());
						props.put("cube",entity.getCube());
						props.put("hierarchy",entity.getHierarchy());
						props.put("dimLevel",entity.getDimLevel());
						props.put("attributes", attributes);
						props.put("displayName", entity.getDisplayName());
						props.put("healthParam", entity.getHealthParam());
						props.put("healthEntity", entity.getHealthEntity());
						props.put("actions",entity.getActions().getAction());
						//props.put("alertText",entity.getAlertText());
						
						//This property will be used to determine that the entity at dim level Agent but qualified by type of agent: cache or inference
						props.put("agentType", MonitoringUtils.isValidAgentType(entity.getName())?entity.getName():"na");
						
						entityMap.put(entity.getName(), props);
					}
				}
			}
		}
	}
	
	private HashSet<Map<String, String>> getMeasurementAttributesForEntity(DimensionHierarchy hierarchy, Entity entity,HashSet<Map<String, String>> attributes) {
		
			for(AttrMapping mapping :entity.getMappings().getMapping()){
				if("measurement".equals(mapping.getType())){
					Measurement measurement=hierarchy.getMeasurement(mapping.getName());
					if(measurement!=null){
						Map<String,String> measurementProps=new HashMap<String,String>();
						
						measurementProps.put(BETeaAgentProps.BEMM_RULE_STORAGE_TYPE,measurement.getProperty(BETeaAgentProps.BEMM_RULE_STORAGE_TYPE));
						measurementProps.put(BETeaAgentProps.BEMM_RULE_ATTR_NAME, mapping.getName());
						measurementProps.put(BETeaAgentProps.BEMM_RULE_DISPLAY_NAME,mapping.getDisplayName());
						measurementProps.put(BETeaAgentProps.BEMM_RULE_ATTR_TYPE,BETeaAgentProps.BEMM_RULE_MEASUREMENT_NAME);
						measurementProps.put(BETeaAgentProps.BEMM_RULE_ALERT_TOKEN,measurement.getProperty(BETeaAgentProps.BEMM_RULE_ALERT_TOKEN));
						measurementProps.put("index",mapping.getIndex()+"");
						
						if(mapping.getDisplayType()!=null && !mapping.getDisplayType().isEmpty()){
							measurementProps.put(BETeaAgentProps.BEMM_RULE_DISPLAY_TYPE,mapping.getDisplayType());
						}
						else{
							measurementProps.put(BETeaAgentProps.BEMM_RULE_DISPLAY_TYPE,measurement.getProperty(BETeaAgentProps.BEMM_RULE_STORAGE_TYPE));
						}
							
						attributes.add(measurementProps);
					}
				}
				
			}
		
		return attributes;
	}

	private HashSet<Map<String, String>> getDimensionAttributesForEntity(DimensionHierarchy hierarchy, Entity entity,HashSet<Map<String, String>> attributes) {
		
		int dimeLevelNo=hierarchy.getLevel(entity.getDimLevel());
		for(AttrMapping mapping :entity.getMappings().getMapping()){
			if("dimension".equals(mapping.getType())){
				Dimension dim=hierarchy.getDimension(mapping.getName());
				if(dim!=null){
					if(dimeLevelNo>=hierarchy.getLevel(dim.getName())){
						Map<String,String> dimensionProps=new HashMap<String,String>();

						dimensionProps.put(BETeaAgentProps.BEMM_RULE_STORAGE_TYPE, dim.getAssociatedAttribute().getDataType().toString());
						dimensionProps.put(BETeaAgentProps.BEMM_RULE_ATTR_NAME, mapping.getName());
						dimensionProps.put(BETeaAgentProps.BEMM_RULE_DISPLAY_NAME,mapping.getDisplayName());
						dimensionProps.put(BETeaAgentProps.BEMM_RULE_ATTR_TYPE,BETeaAgentProps.BEMM_RULE_DIMENSION_NAME);
						
						if(mapping.getDisplayType()!=null && !mapping.getDisplayType().isEmpty()){
							dimensionProps.put(BETeaAgentProps.BEMM_RULE_DISPLAY_TYPE,mapping.getDisplayType());
						}
						else{
							dimensionProps.put(BETeaAgentProps.BEMM_RULE_DISPLAY_TYPE,dim.getAssociatedAttribute().getDataType().toString());
						}
						
						dimensionProps.put("index",mapping.getIndex()+"");

						attributes.add(dimensionProps);
					}
				}
			}
		}
		return attributes;
	}

	@Override
	public Map<String, Object> getAllEntitiesAttrMap() {
		return entityMap;
	}
	
	@Override
	public Object getEntityAttrMap(String entityName) {
		if(entityMap!=null)
			return entityMap.get(entityName);
		else
			return null;
	}

	@Override
	public Map<String, String> getHealthMap() {
		LinkedHashMap<String,String> healthMap=new LinkedHashMap<String,String>();
		for(BEEntityHealthStatus val:BEEntityHealthStatus.values()){
			healthMap.put(val.getLevel()+"$"+val.getHealthStatus(),val.getDisplayName());
		}
		return healthMap;
	}

	@Override
	public void deleteAppRuleConifg(String name) throws Exception  {

		RtaSession session=BEMMServiceProviderManager.getInstance().getAggregationService().getSession();
		List<String> ruleNames=new ArrayList<String>();
		if(session!=null){
			List<RuleDef> rules=session.getAllRuleDefs();
			for(RuleDef rule :rules){
				if(rule.getName()!=null&&!rule.getName().isEmpty()){
					String split[]=rule.getName().split("\\"+BETeaAgentProps.BE_RULE_APP_NAME_SEPERATOR);
					if(name.equals(split[0])){
						ruleNames.add(rule.getName());
					}
				}
			}
			delete(ruleNames);
		}
		try{
			Properties configuration=BEMMServiceProviderManager.getInstance().getConfiguration();
			if(configuration!=null){
				String ruleRepo=configuration.getProperty(ServiceConstants.RULES_DATASTORE);
				if(ruleRepo!=null && !ruleRepo.isEmpty()){
					File file=new File(ruleRepo+File.separator+name);
					if(file!=null&&file.exists())
						ManagementUtil.delete(file);
				}

			}
		}
		catch(FileNotFoundException e){
			LOGGER.log(Level.DEBUG, messageService.getMessage(MessageKey.RULE_REPO_APP_NOT_FOUND, name));
		}


	}
}	