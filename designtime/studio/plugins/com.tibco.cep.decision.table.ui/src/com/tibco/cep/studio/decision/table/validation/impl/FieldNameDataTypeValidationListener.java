/**
 * 
 */
package com.tibco.cep.studio.decision.table.validation.impl;

import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.decision.table.validation.FieldValidationContext;
import com.tibco.cep.studio.decision.table.validation.FieldValidationResult;
import com.tibco.cep.studio.decision.table.validation.FieldValidationUtils;
import com.tibco.cep.studio.decision.table.validation.IFieldValidationListener;

/**
 * Perform data type validation post rename to make sure
 * integrity of the table is not broken.
 * @author aathalye
 *
 */
public class FieldNameDataTypeValidationListener implements IFieldValidationListener<MatchingValidationEntity> {
	
	private IFieldValidationListener<MatchingValidationEntity> fieldNameDuplicationValidationListener;
	

	/* (non-Javadoc)
	 * @see com.jidesoft.decision.validation.IFieldValidationListener#validate(com.jidesoft.decision.validation.FieldValidationContext)
	 */
	@Override
	public FieldValidationResult validate(FieldValidationContext<MatchingValidationEntity> fieldValidationContext) {
		//Validate data type of the new field such that the data type
		//should be same as the one before if the column has any value 
		//in it.
		MatchingValidationEntity matchingValidationEntity = fieldValidationContext.getValidationObject();
		Table tableEModel = fieldValidationContext.getTableEModel();
		
		String changedFieldName = fieldValidationContext.getChangedFieldName();
		FieldValidationResult fieldValidationResult = 
			validateDataType(matchingValidationEntity, fieldValidationContext.getTableType(), tableEModel, changedFieldName);
		if (fieldValidationResult.getResultCode() == FieldValidationResult.SUCCESS_CODE) {
			fieldNameDuplicationValidationListener = new FieldNameDuplicationValidationListener();
			fieldValidationResult = fieldNameDuplicationValidationListener.validate(fieldValidationContext);
		}
		return fieldValidationResult;
	}
	
	/**
	 * 
	 * @param matchingValidationEntity
	 * @param decisionDataModel
	 * @param tableRuleSet
	 * @param changedFieldName
	 * @return
	 */
	private FieldValidationResult validateDataType(MatchingValidationEntity matchingValidationEntity,
			                                       TableTypes tableType,
			                      				   Table tableEModel,
			                      				   String changedFieldName) {
		FieldValidationResult fieldValidationResult = null;
		Column backendColumn = matchingValidationEntity.getBackendColumn();
		ColumnType columnType = backendColumn.getColumnType();
		//Do not validate data type for custom column
		if (columnType == ColumnType.CUSTOM_CONDITION || columnType == ColumnType.CUSTOM_ACTION) {
			//Do not validate
			fieldValidationResult = new FieldValidationResult(FieldValidationResult.SUCCESS_CODE);
			return fieldValidationResult;
		}
		
		//Search any tablerulevariable having this id as column id
		boolean cellExistsForColumn = 
			FieldValidationUtils.hasCellWithColumnId(tableEModel,
					                                 tableType,
					                                 backendColumn.getId(),
					                                 columnType == ColumnType.CONDITION || columnType == ColumnType.CUSTOM_CONDITION);
		//If cell exists the data type needs to be verified.
		if (!cellExistsForColumn) {
			//Do not validate
			fieldValidationResult = new FieldValidationResult(FieldValidationResult.SUCCESS_CODE);
		} else {
			//Validate datatype
			boolean isSameDataType = isSameDataType(matchingValidationEntity);
			fieldValidationResult = (isSameDataType) 
					? new FieldValidationResult(FieldValidationResult.SUCCESS_CODE)
			        : new FieldValidationResult(FieldValidationResult.INVALID_CODE, "Data Type validation failed");
		}
		return fieldValidationResult;
	}
	
	/**
	 * 
	 * @param matchingValidationEntity
	 * @param matchingColumn
	 * @return
	 */
	private boolean isSameDataType(MatchingValidationEntity matchingValidationEntity) {
		ArgumentProperty argumentProperty = matchingValidationEntity.getMatchingArgumentProperty();
		//Check if it has a matching property
		PropertyDefinition propertyDefinition = matchingValidationEntity.getMatchedPropertyDefinition();
		//Get data type
		PROPERTY_TYPES propertyType = null;
		if (propertyDefinition != null) {
			propertyType = propertyDefinition.getType();
		} else {
			//The data type is on the arg property path
			String argPropertyPath = argumentProperty.getPath();
			propertyType = PROPERTY_TYPES.get(argPropertyPath);
			if (propertyType == null) {
				return false;
			}
		}
		if (propertyType.getValue() == matchingValidationEntity.getBackendColumn().getPropertyType()) {
			return true;
		}
		return false;
	}
}
