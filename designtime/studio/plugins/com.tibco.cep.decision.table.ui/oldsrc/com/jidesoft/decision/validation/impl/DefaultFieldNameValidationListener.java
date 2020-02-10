/**
 * 
 */
package com.jidesoft.decision.validation.impl;

import com.jidesoft.decision.DecisionField;
import com.jidesoft.decision.validation.FieldValidationContext;
import com.jidesoft.decision.validation.FieldValidationResult;
import com.jidesoft.decision.validation.IFieldValidationListener;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;

/**
 * @author aathalye
 *
 */
public class DefaultFieldNameValidationListener implements IFieldValidationListener<DecisionField> {
	
	private static final String CLASS = DefaultFieldNameValidationListener.class.getName();
	
	private IFieldValidationListener<MatchingValidationEntity> dataTypeFieldValidationListener;

	/* (non-Javadoc)
	 * @see com.jidesoft.decision.validation.IFieldValidationListener#validate(com.jidesoft.decision.validation.FieldValidationContext)
	 */
	@Override
	public FieldValidationResult validate(FieldValidationContext<DecisionField> fieldValidationContext) {
		FieldValidationResult fieldValidationResult = null;
		//Validate field name change
		//1.) Validate name change such that new name is a valid property name.
		DecisionField decisionField = fieldValidationContext.getValidationObject();
		Table tableEModel = fieldValidationContext.getTableEModel();
		String project = fieldValidationContext.getProject();
		String changedFieldName = fieldValidationContext.getChangedFieldName();
		DecisionTableUIPlugin.debug(CLASS, "New Field Name {0}", changedFieldName);
		TableTypes tableType = fieldValidationContext.getDecisionDataModel().getTableType();
		//Check if the existing column is custom type or not
		Column matchingColumn = FieldValidationUtils.getMatchingColumn(tableEModel, tableType, decisionField.getId());
		DecisionTableUIPlugin.debug(CLASS, "Backend column affected {0}", matchingColumn);
		MatchingValidationEntity matchingValidationEntity = prepareEntity(project, tableEModel, tableType, decisionField.getId(), changedFieldName);
		if (matchingValidationEntity != null) {
			//No matching property was found.
			if (matchingValidationEntity.getMatchedPropertyDefinition() == null) {
				if (matchingColumn != null && 
						(matchingColumn.getColumnType() == ColumnType.CONDITION || 
								matchingColumn.getColumnType() == ColumnType.ACTION)) {	
					fieldValidationResult = 
						new FieldValidationResult(FieldValidationResult.INVALID_CODE, "The name " + changedFieldName + " is not a valid property name");
					return fieldValidationResult;
				}
			}
			//Check data type
			this.dataTypeFieldValidationListener = new FieldNameDataTypeValidationListener();
			fieldValidationResult = dataTypeFieldValidationListener.validate(prepareValidationContext(fieldValidationContext, matchingValidationEntity));
		} else {
			if (matchingColumn != null && 
					(matchingColumn.getColumnType() == ColumnType.CUSTOM_CONDITION 
							|| matchingColumn.getColumnType() == ColumnType.CUSTOM_ACTION)) {
				DecisionTableUIPlugin.debug(CLASS, "Checking data type for column type {0}", matchingColumn.getColumnType());
				//Check data type
				this.dataTypeFieldValidationListener = new FieldNameDataTypeValidationListener();
				fieldValidationResult = dataTypeFieldValidationListener.validate(prepareValidationContext(fieldValidationContext, matchingValidationEntity));
			} else {
				fieldValidationResult = 
					new FieldValidationResult(FieldValidationResult.INVALID_CODE, "The name " + changedFieldName + " is not a valid property name");
			}
		}
		return fieldValidationResult;
	}
	
	/**
	 * 
	 * @param project
	 * @param tableEModel
	 * @param tableType
	 * @param fieldId
	 * @param changedFieldName
	 * @return
	 */
	private MatchingValidationEntity prepareEntity(String project,
			                                       Table tableEModel,
			                                       TableTypes tableType,
			                                       String fieldId,
			                                       String changedFieldName) {
		
		//Check whether the name is valid for any argument.
		MatchingValidationEntity matchingEntity = 
			FieldValidationUtils.getMatchingEntity(project, tableEModel, tableType, fieldId, changedFieldName);
		return matchingEntity;
	}
	
	/**
	 * 
	 * @param receivedContext
	 * @param matchingEntity
	 * @return
	 */
	private FieldValidationContext<MatchingValidationEntity> prepareValidationContext(FieldValidationContext<DecisionField> receivedContext, MatchingValidationEntity matchingEntity) {
		FieldValidationContext<MatchingValidationEntity> newValidationContext = new FieldValidationContext<MatchingValidationEntity>(matchingEntity);
		newValidationContext.setChangedFieldAlias(receivedContext.getChangedFieldAlias());
		newValidationContext.setChangedFieldName(receivedContext.getChangedFieldName());
		newValidationContext.setDecisionTablePane(receivedContext.getDecisionTablePane());
		return newValidationContext;
	}
}
