/**
 * 
 */
package com.jidesoft.decision.validation.impl;

import java.util.List;

import com.jidesoft.decision.DecisionDataModel;
import com.jidesoft.decision.DecisionField;
import com.jidesoft.decision.validation.FieldValidationContext;
import com.jidesoft.decision.validation.FieldValidationResult;
import com.jidesoft.decision.validation.IFieldValidationListener;
import com.tibco.cep.decision.table.language.DTLanguageUtil;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;

/**
 * Check for duplicates while renaming.
 * @author aathalye
 *
 */
public class FieldNameDuplicationValidationListener implements IFieldValidationListener<MatchingValidationEntity> {
	
	/* (non-Javadoc)
	 * @see com.jidesoft.decision.validation.IFieldValidationListener#validate(com.jidesoft.decision.validation.FieldValidationContext)
	 */
	@Override
	public FieldValidationResult validate(FieldValidationContext<MatchingValidationEntity> fieldValidationContext) {
		//1.) Validate for duplicate name (discount spaces)
		MatchingValidationEntity matchingValidationEntity = fieldValidationContext.getValidationObject();
		DecisionDataModel decisionDataModel = fieldValidationContext.getDecisionDataModel();
		String changedFieldName = fieldValidationContext.getChangedFieldName();
		return prepareResult(matchingValidationEntity, decisionDataModel, changedFieldName);
	}
	
	/**
	 * 
	 * @param decisionDataModel
	 * @param matchingColumn
	 * @param changedFieldName
	 * @return
	 */
	private FieldValidationResult prepareResult(MatchingValidationEntity matchingValidationEntity,
			                                    DecisionDataModel decisionDataModel,
			                                    String changedFieldName) {
		FieldValidationResult fieldValidationResult = null;
		//Get area of the field and check whether the name exists in that area
		//Canonicalize field name to remove extraneous spaces
		changedFieldName = DTLanguageUtil.canonicalizeExpression(changedFieldName);
		ColumnType columnType = matchingValidationEntity.getBackendColumn().getColumnType();
		List<DecisionField> decisionFields = 
			(columnType == ColumnType.CONDITION || columnType == ColumnType.CUSTOM_CONDITION) ? decisionDataModel.getConditionFields() : decisionDataModel.getActionFields();
		changedFieldName = changedFieldName.replaceAll(" ", "");	
		for (DecisionField field : decisionFields) {
			String fieldName = field.getName().replaceAll(" ", "");
			if (changedFieldName.equals(fieldName)) {
				//Exists so it is a duplicate case.
				fieldValidationResult = new FieldValidationResult(FieldValidationResult.DUPLICATE_CODE, "Field Name " + changedFieldName + " already exists");
				return fieldValidationResult;
			}
		}
		fieldValidationResult = new FieldValidationResult(FieldValidationResult.SUCCESS_CODE);
		return fieldValidationResult;
	}
}
