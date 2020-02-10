/**
 * 
 */
package com.tibco.cep.studio.decision.table.utils;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.studio.decision.table.generator.IDGenerator;
import com.tibco.cep.studio.decision.table.generator.RuleIDGenerator;

/**
 * @author aathalye
 *
 */
public class RuleIDGeneratorManager {
	
	public static RuleIDGeneratorManager INSTANCE = new RuleIDGeneratorManager();
	
	private RuleIDGeneratorManager() {}
	
	private static Map<RuleIDGeneratorKey, IDGenerator<String>> idGeneratorMap = new HashMap<RuleIDGeneratorKey, IDGenerator<String>>();
	
	public static IDGenerator<String> getIDGenerator(String project, String tablePath) {
		RuleIDGeneratorKey key = new RuleIDGeneratorKey(project, tablePath);
		if (idGeneratorMap.containsKey(key)) {
			return idGeneratorMap.get(key);
		} else {
			IDGenerator<String> idGenerator = new RuleIDGenerator();
			idGeneratorMap.put(key, idGenerator);
			return idGenerator;
		}
	}
	
	/**
	 * Set id for a newly created {@link DecisionRule}
	 * <p>
	 * <b><i>
	 * Do not use this for setting ids for {@link DecisionRule}s
	 * created from existing backend rules.
	 * </i></b>
	 * </p>
	 * <p>
	 * Since {@link TableRuleVariable} is used to set value of
	 * {@link DecisionEntry}, upon addition/duplication the id
	 * of this <code>TableRuleVariable</code> also has to be set to the
	 * latest rule id.
	 * </p>
	 * @param project
	 * @param tablePath
	 * @param decisionRule
	 */
	public void setID(String project, String tablePath, TableRule decisionRule) {
		IDGenerator<String> idGenerator = getIDGenerator(project, tablePath);
		String newId = idGenerator.getNextID();
		decisionRule.setId(newId);
		for ( TableRuleVariable trv : decisionRule.getCondition()) {
				String colId = trv.getColId();
				String newTrvId = newId + "_" + colId;
				trv.setId(newTrvId);
		}
		for (TableRuleVariable trv : decisionRule.getAction()) {
				String colId = trv.getColId();
				String newTrvId = newId + "_" + colId;
				trv.setId(newTrvId);
		}
	}
	
	/**
	 * Set the id in the idgenerator.
	 * <p>
	 * The rule id is passed from outside from say UI rule
	 * created out of backend rule.
	 * </p>
	 * @param project
	 * @param tablePath
	 * @param ruleId
	 */
	public void steadyIncrement(String project, String tablePath, int ruleId) {
		IDGenerator<String> idGenerator = getIDGenerator(project, tablePath);
		//Find the current id
		String currentID = idGenerator.getCurrentID();
		//Find the max of the current id and the passed id
		Integer currentIDInt = Integer.parseInt(currentID);
		int maxId = Math.max(ruleId, currentIDInt);
		maxId = maxId + 1;
		idGenerator.setID(Integer.toString(maxId));
	}
	
	/**
	 * Reset the id generator for this table in the specified project
	 * when the editor is disposed.
	 * @param project
	 * @param tablePath
	 */
	public void resetIDGenerator(String project, String tablePath) {
		IDGenerator<String> idGenerator = getIDGenerator(project, tablePath);
		if (idGenerator != null) {
			idGenerator.reset();
		}
	}
	
	public void setPreviousID(String project, String tablePath) {
		IDGenerator<String> idGenerator = getIDGenerator(project, tablePath);
		if (idGenerator != null) {
			idGenerator.restorePreviousID();
		}
	}
}
