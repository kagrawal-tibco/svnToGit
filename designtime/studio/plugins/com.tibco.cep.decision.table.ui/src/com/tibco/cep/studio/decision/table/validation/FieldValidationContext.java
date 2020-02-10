/**
 * 
 */
package com.tibco.cep.studio.decision.table.validation;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableTypes;
import com.tibco.cep.studio.decision.table.editor.DecisionTableDesignViewer;

/**
 * The context needed for validation based on field information changes.
 * @see IFieldValidationListener
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
	private DecisionTableDesignViewer formViewer;
	
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

	private TableTypes tableType;
	
	public FieldValidationContext(T validationObject,
			                      DecisionTableDesignViewer formViewer, 
			                      FieldValidationContextType validationContextType,
			                      TableTypes tableType) {
		this.validationObject = validationObject;
		this.formViewer = formViewer;
		this.validationContextType = validationContextType;
		this.tableType= tableType;
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
		return formViewer.getTable();
	}
	

	/**
	 * @return the tableType
	 */
	public final TableTypes getTableType() {
		return tableType;
	}


	/**
	 * @return the project
	 */
	public final String getProject() {
		return formViewer.getTable().getOwnerProjectName();
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
	public final void setDecisionTableSWTFormViewer(DecisionTableDesignViewer formViewer) {
		this.formViewer = formViewer;
	}



	/**
	 * @return the decisionTablePane
	 */
	public final DecisionTableDesignViewer getDecisionTableSWTFormViewer() {
		return formViewer;
	}
	
	
}