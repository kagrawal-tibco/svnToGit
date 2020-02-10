/**
 * 
 */
package com.tibco.cep.studio.decision.table.validation.impl;

import java.util.ArrayList;

import com.tibco.cep.decision.table.language.DTLanguageUtil;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.decision.table.editor.DecisionTableDesignViewer;
import com.tibco.cep.studio.decision.table.validation.FieldValidationContext;
import com.tibco.cep.studio.decision.table.validation.FieldValidationResult;
import com.tibco.cep.studio.decision.table.validation.IFieldValidationListener;

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
		DecisionTableDesignViewer formViewer = fieldValidationContext.getDecisionTableSWTFormViewer();
		String changedFieldName = fieldValidationContext.getChangedFieldName();
		return prepareResult(matchingValidationEntity, formViewer, fieldValidationContext.getTableType(), changedFieldName);
	}
	
	/**
	 * 
	 * @param decisionDataModel
	 * @param matchingColumn
	 * @param changedFieldName
	 * @return
	 */
	private FieldValidationResult prepareResult(MatchingValidationEntity matchingValidationEntity,
			                                    DecisionTableDesignViewer formViewer,
			                                    TableTypes tableType,
			                                    String changedFieldName) {
		FieldValidationResult fieldValidationResult = null;
		//Get area of the field and check whether the name exists in that area
		//Canonicalize field name to remove extraneous spaces
		changedFieldName = DTLanguageUtil.canonicalizeExpression(changedFieldName);
		//TODO Condition column can have same name as Action column?
		changedFieldName = changedFieldName.replaceAll(" ", "");
		ArrayList<String> columnNames = formViewer.getColumnNames(tableType);
		for (String string : columnNames) {
			String fieldName = string.replaceAll(" ", "");
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
