package com.jidesoft.decision.validation.impl;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.tibco.cep.decision.table.language.ArrayParseUtil;
import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.ResourceType;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.utils.IndexUtils;

public class FieldValidationUtils {
	
	private static final Pattern ARRAY_BRACKETS_PATTERN = Pattern.compile("\\[\\]");
	
	/**
	 * 
	 * @param project
	 * @param tableEModel
	 * @param tableType
	 * @param fieldId
	 * @param changedFieldName
	 * @return
	 */
	public static MatchingValidationEntity getMatchingEntity(String project, 
			                                                 Table tableEModel,
			                                                 TableTypes tableType,
			                                                 String fieldId,
			                                                 String changedFieldName) {
		MatchingValidationEntity matchEntity = null;
		List<Argument> scopeArguments = tableEModel.getArgument();
		//Get matching column
		Column matchingColumn = getMatchingColumn(tableEModel, tableType, fieldId);
		
		//Check whether new name is array access name
		boolean isArrayAccessForNewName = ArrayParseUtil.isArrayAccessColumn(changedFieldName);
		for (Argument scopeArgument : scopeArguments) {
			if (matchEntity == null) {
				ArgumentProperty argumentProperty = scopeArgument.getProperty();
				String path = argumentProperty.getPath();
				ResourceType type = argumentProperty.getResourceType();
				switch (type) {
					case CONCEPT :
						Concept concept = IndexUtils.getConcept(project, path);
						matchEntity = matchProperty(matchingColumn,
								                    argumentProperty, 
								                	concept.getAllPropertyDefinitions(),
								                	changedFieldName,
								                	isArrayAccessForNewName);
						
						break;
					case EVENT :
						Event event = IndexUtils.getSimpleEvent(project, path);
						matchEntity = matchProperty(matchingColumn,
								                    argumentProperty, 
				                					event.getAllUserProperties(),
				                					changedFieldName,
				                					isArrayAccessForNewName);
						break;
					case UNDEFINED :
						//Check if the argument exists as primitive type
						String argAlias = argumentProperty.getAlias();
						boolean matchFound = false;
						if (isArrayAccessForNewName) {
							matchFound = matchPropertyName(argAlias, changedFieldName);
						} else {
							if (argAlias.equalsIgnoreCase(changedFieldName)) {
								matchFound = true;
							}
						}
						if (matchFound) {
							matchEntity = new MatchingValidationEntity(matchingColumn, argumentProperty);
						}
				}
			}
		}
		//If still null (custom one)
		if (matchEntity == null) {
			matchEntity = new MatchingValidationEntity(matchingColumn);
		}
		return matchEntity;
	}
	
	/**
	 * @param matchingColumn
	 * @param entityName
	 * @param propertyDefinitions
	 * @param changedFieldName
	 * @param isArrayAccessForNewName
	 * @return
	 */
	private static MatchingValidationEntity matchProperty(Column matchingColumn,
			                                              ArgumentProperty argumentProperty,
			                             		          List<PropertyDefinition> propertyDefinitions,
			                             		          String changedFieldName,
			                             		          boolean isArrayAccessForNewName) {
		PropertyDefinition matchingPropertyDefinition = null;
		MatchingValidationEntity matchingEntity = null;
		//Check if new name is substitution name
		boolean isSubstitution = DecisionTableUtil.isVarString(changedFieldName, matchingColumn.getColumnType());
		String entityAlias = argumentProperty.getAlias();
		for (PropertyDefinition prop : propertyDefinitions) {
			String propName = entityAlias.concat("." + prop.getName());
			if (isArrayAccessForNewName) {
				boolean matchFound = matchPropertyName(propName, changedFieldName);
				if (matchFound) {
					matchingPropertyDefinition = prop;
				}
			} else if (isSubstitution) {
				//Check whether any alias above is part of the string
				if (changedFieldName.startsWith(propName)) {
					matchingPropertyDefinition = prop;
				}
			} else {
				if (propName.equalsIgnoreCase(changedFieldName)) {
					matchingPropertyDefinition = prop;
				}
			}
			if (matchingPropertyDefinition != null) {
				matchingEntity = new MatchingValidationEntity(matchingColumn, argumentProperty, matchingPropertyDefinition);
				break;
			}
		}
		return matchingEntity;
	}
	
	/**
	 * To be used only for array properties
	 * @param propName
	 * @param newName
	 * @return
	 */
	private static boolean matchPropertyName(String propName, String newName) {
		Scanner scanner = new Scanner(newName).useDelimiter(ARRAY_BRACKETS_PATTERN);
		while (scanner.hasNext()) {
			//Hopefully only one
			String nexToken = scanner.next();
			if (propName.equalsIgnoreCase(nexToken)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param tableEModel
	 * @param tableType
	 * @param columnId
	 * @return
	 */
	public static Column getMatchingColumn(Table tableEModel, TableTypes tableType, String columnId) {
		TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();
		Columns columnsModel = tableRuleSet.getColumns();
		return columnsModel.search(columnId);
	}
	
	/**
	 * Check whether there is a cell containing a non empty expression
	 * for this column id.
	 * @param tableEModel
	 * @param tableType
	 * @param columnId
	 * @param isCondition
	 * @return
	 */
	public static boolean hasCellWithColumnId(Table tableEModel, TableTypes tableType, String columnId, boolean isCondition) {
		boolean cellExists = false;
		List<TableRule> rules = 
			(tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable().getRule() : tableEModel.getExceptionTable().getRule();
		outerloop:
		for (TableRule tableRule : rules) {
			List<TableRuleVariable> tableRuleVariables = (isCondition) ? tableRule.getCondition() : tableRule.getAction();
			for (TableRuleVariable tableRuleVariable : tableRuleVariables) {
				String expression = tableRuleVariable.getExpr();
				//Also check whether the expression is not empty
				if (tableRuleVariable.getColId().equals(columnId) && 
						expression != null && expression.length() > 0) {
					//We found a cell in this column.
					cellExists = true;
					break outerloop;
				}
			}
		}
		return cellExists;
	}
}
