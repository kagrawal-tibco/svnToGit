package com.tibco.cep.bemm.monitoring.metric.rule;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.model.Application;
import com.tibco.rta.common.service.StartStopService;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.rule.impl.RuleDefImpl;
import com.tibco.tea.agent.be.util.BEEntityHealthStatus;

public interface BEEntityMetricRuleService extends StartStopService {
	
	/**
	 * Initializes rule service
	 * @return
	 * @throws Exception
	 */
	public void init(Properties configuration) throws Exception;
	
	/**
	 * Deletes an existing rule
	 * @return String
	 * @throws Exception
	 */
	public String delete(List<String> ruleNames) throws Exception;
	
	/**
	 * returns all rules associated with an app
	 * @param loggedInUser 
	 * @param isAdmin 
	 * @return ArrayList<RuleDef>
	 * @throws Exception
	 */
	public ArrayList<RuleDef> getAppRules(String appName, String loggedInUser, boolean isAdmin) throws Exception;
	
	/**
	 * Creates a rule
	 * @return String
	 * @throws Exception
	 */
	public String createRule(RuleDef rule) throws Exception;
	
	/**
	 * Updates an existing rule
	 * @return ArrayList<RuleDef>
	 * @throws Exception
	 */
	public String updateRule(RuleDef rule) throws Exception;
	
	/**
	 * The rules are loaded as disabled from memory/rule store.This method will enable them.
	 * @return void 
	 * @throws Exception
	 */
	public void enableLoadedRules() throws Exception;
	
	/**
	 * Gets action descriptors from memory
	 * @return List 
	 * @throws Exception
	 */
	public List<ActionFunctionDescriptor> getActionDescriptors() throws Exception;

	/**
	 * Prepares a rule before creating in memory
	 * @param  ruleDef
	 * @param  appName
	 * @param  entity 
	 * @param  loggedInUser 
	 * @param isRuleAdmin 
	 * @return RuleDef 
	 * @throws Exception
	 */
	public RuleDef prepareRule(RuleDefImpl ruleDef, String appName , String entity, String loggedInUser,boolean isUpdate, boolean isRuleAdmin) throws Exception;
	
	
	/**
	 * Load pre-configured rules and creates them for specified application
	 * @param  application
	 * @throws FileNotFoundException 
	 * @throws Exception 
	 */
	public void createPreconfiguredRules(Application application) throws Exception;
	
	/**
	 * Update rules state to enable/disable
	 * @param  ruleNames
	 * @param  isEnable
	 * @throws Exception 
	 */
	public String updateRulesState(List<String> ruleNames, boolean isEnable) throws Exception;

	
	/**
	 * Provides attributes applicable to all entities for rules
	 * @return Map<String, Object>
	 */
	Map<String, Object> getAllEntitiesAttrMap();
	
	/**
	 * Provides attributes applicable to specified entity for rules
	 * @return Object
	 */
	public Object getEntityAttrMap(String entityName);
	
	/**
	 * Provides Health levels
	 * @return Map<String,String>
	 */
	public Map<String,String> getHealthMap();
	
	/**
	 * Performs cleanup operation for rules after deployment delete
	 * @return void
	 * @throws FileNotFoundException 
	 * @throws Exception 
	 * @throws ObjectCreationException 
	 */
	public void deleteAppRuleConifg(String name) throws Exception;
	
}
