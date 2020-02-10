/**
 * 
 */
package com.jidesoft.decision.validation;

import com.jidesoft.decision.DecisionDataModel;
import com.jidesoft.decision.DecisionTablePane;
import com.tibco.cep.decision.table.model.dtmodel.Table;

/**
 * The context needed for validation based on field information changes.
 * @see IFieldValidationListener
 * @author aathalye
 *
 */
public class FieldValidationContext<T> {
		
	/**
	 * The validation object is question.
	 */
	private T validationObject;
	
	/**
	 * The UI pane is required.
	 */
	private DecisionTablePane decisionTablePane;
	
	private FieldValidationContextType validationContextType;
	
	/**
	 * Presently only 2 constants
	 * 
	 */
	public enum FieldValidationContextType {
		FIELD_NAME,
		FIELD_ALIAS;
	}
	
	private String changedFieldName;
	
	private String changedFieldAlias;

	public FieldValidationContext(T validationObject,
			                      DecisionTablePane decisionTablePane, 
			                      FieldValidationContextType validationContextType) {
		this.validationObject = validationObject;
		this.decisionTablePane = decisionTablePane;
		this.validationContextType = validationContextType;
	}
	
	

	public FieldValidationContext(T validationObject) {
		this.validationObject = validationObject;
	}


	/**
	 * @return the changedFieldName
	 */
	public final String getChangedFieldName() {
		return changedFieldName;
	}

	/**
	 * @param changedFieldName the changedFieldName to set
	 */
	public final void setChangedFieldName(String changedFieldName) {
		this.changedFieldName = changedFieldName;
	}

	/**
	 * @return the changedFieldAlias
	 */
	public final String getChangedFieldAlias() {
		return changedFieldAlias;
	}

	/**
	 * @param changedFieldAlias the changedFieldAlias to set
	 */
	public final void setChangedFieldAlias(String changedFieldAlias) {
		this.changedFieldAlias = changedFieldAlias;
	}

	/**
	 * @return the decisionField
	 */
	public final T getValidationObject() {
		return validationObject;
	}

	/**
	 * @return the validationContextType
	 */
	public final FieldValidationContextType getValidationContextType() {
		return validationContextType;
	}

	/**
	 * @return the tableEModel
	 */
	public final Table getTableEModel() {
		return decisionTablePane.getDecisionTableEditor().getDecisionTableModelManager().getTabelEModel();
	}
	

	/**
	 * @return the tableEModel
	 */
	public final DecisionDataModel getDecisionDataModel() {
		return decisionTablePane.getDecisionDataModel();
	}


	/**
	 * @return the project
	 */
	public final String getProject() {
		return decisionTablePane.getDecisionTableEditor().getProject().getName();
	}

	/**
	 * @param validationObject the validationObject to set
	 */
	public final void setValidationObject(T validationObject) {
		this.validationObject = validationObject;
	}


	/**
	 * @return Set the decisionTablePane
	 */
	public final void setDecisionTablePane(DecisionTablePane decisionTablePane) {
		this.decisionTablePane = decisionTablePane;
	}



	/**
	 * @return the decisionTablePane
	 */
	public final DecisionTablePane getDecisionTablePane() {
		return decisionTablePane;
	}
	
	
}