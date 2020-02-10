/**
 * 
 */
package com.tibco.cep.studio.decision.table.model.update.listener.impl;

import com.tibco.cep.decision.table.model.controller.ColumnModelController;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.decision.table.model.update.listener.ITableModelChangeRequestEvent;
import com.tibco.cep.studio.decision.table.model.update.listener.ITableModelChangeRequestListener;
import com.tibco.cep.studio.decision.table.model.update.listener.TableModelColumnUpdateRequestEvent;
import com.tibco.cep.studio.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.studio.decision.table.validation.FieldValidationUtils;
import com.tibco.cep.studio.decision.table.validation.impl.MatchingValidationEntity;

/**
 * @author aathalye
 *
 */
public class TableModelColumnNameUpdateRequestListener implements ITableModelChangeRequestListener {
	
	private Column affectedColumn;
	
	/**
	 * Updated to column model. Harmless to keep here.
	 * Don't care much about project name.
	 */
	private ColumnModelController columnModelController = new ColumnModelController(null);
	
	/**
	 * 
	 */
	public <T extends ITableModelChangeRequestEvent> void doChange(T changeRequestEvent) {
		if (!(changeRequestEvent instanceof TableModelColumnUpdateRequestEvent)) {
			return;
		}
		TableModelColumnUpdateRequestEvent tableModelColumnUpdateRequestEvent = (TableModelColumnUpdateRequestEvent)changeRequestEvent;
		String projectName = tableModelColumnUpdateRequestEvent.getProjectName();
		TableTypes tableType = tableModelColumnUpdateRequestEvent.getTableType();
		Table tableEModel = tableModelColumnUpdateRequestEvent.getTableEModel();
		//Source is the field in question.
		Column col = (Column)tableModelColumnUpdateRequestEvent.getSource();
		//Get event type
		TableModelColumnUpdateRequestEvent.TableModelColumnUpdateRequestEventType eventType = tableModelColumnUpdateRequestEvent.getTableColumnUpdateRequestEventType();
		//Find the right table rule set.
		TableRuleSet tableRuleSet = (tableType == TableTypes.DECISION_TABLE) ? tableEModel.getDecisionTable() : tableEModel.getExceptionTable();
		//Get all columns
		Columns allColumns = tableRuleSet.getColumns();
		//Search a column with the id
		affectedColumn = allColumns.search(col.getId());
		ColumnType columnType = affectedColumn.getColumnType();
		switch (eventType) {
		
			case NAME :
				columnModelController.setColumnName(affectedColumn, tableModelColumnUpdateRequestEvent.getChangedName());
				if (columnType == ColumnType.CONDITION || columnType == ColumnType.ACTION) {
					//Not required for custom ones.
					columnModelController.
						setPropertyPath(affectedColumn, getNewPropertyPath(projectName, tableEModel, tableType, tableModelColumnUpdateRequestEvent.getChangedName()));
				}
				//Check for substitution
				boolean isVarString = (columnType == ColumnType.CONDITION || columnType == ColumnType.CUSTOM_CONDITION) ? 
							DecisionTableUtil.isConditionVarString(affectedColumn.getName()) : DecisionTableUtil.isActionVarString(affectedColumn.getName());
				columnModelController.setSubstitution(affectedColumn, isVarString);	
				break;
				
			case ALIAS :
				String alias = tableModelColumnUpdateRequestEvent.getChangedAlias();
				columnModelController.setColumnAlias(affectedColumn, alias);
				break;
				
			case DEFAULTCELLTEXT :
				String defaultCellText = tableModelColumnUpdateRequestEvent.getChangedDefaultCellText();
				columnModelController.setColumnDefaultCellText(affectedColumn, defaultCellText);
				break;
		}
	}
	
	/**
	 * 
	 * @param projectName
	 * @param tableEModel
	 * @param tableType
	 * @param changedName
	 * @return
	 */
	protected String getNewPropertyPath(String projectName, 
			                          Table tableEModel,
			                          TableTypes tableType,
			                          String changedName) {
		StringBuilder stringBuilder = new StringBuilder();
		//This will contain the right argument property.
		MatchingValidationEntity matchingEntity = 
			FieldValidationUtils.getMatchingEntity(projectName, tableEModel, tableType, affectedColumn.getId(), changedName);
		if (matchingEntity != null) {
			ArgumentProperty argumentProperty = matchingEntity.getMatchingArgumentProperty();
			//Get path
			String argumentPropertyPath = argumentProperty.getPath();
			stringBuilder.append(argumentPropertyPath);
			//Check if there is a property
			PropertyDefinition propertyDefinition = matchingEntity.getMatchedPropertyDefinition();
			if (propertyDefinition != null) {
				stringBuilder.append("/");
				String propertyName = propertyDefinition.getName();
				stringBuilder.append(propertyName);
			}
		}
		return stringBuilder.toString();
	}
	
	/**
	 * Return the column object affected as a result of execution of this.
	 * @return
	 */
	public Column getAffectedColumn() {
		return affectedColumn;
	}
}
