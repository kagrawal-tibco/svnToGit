/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Action Context Symbol</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.ActionContextSymbol#getActionType <em>Action Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getActionContextSymbol()
 * @model
 * @generated
 */
public interface ActionContextSymbol extends Symbol {
	/**
	 * Returns the value of the '<em><b>Action Type</b></em>' attribute.
	 * The default value is <code>"create"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.rule.ActionType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.rule.ActionType
	 * @see #setActionType(ActionType)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getActionContextSymbol_ActionType()
	 * @model default="create"
	 * @generated
	 */
	ActionType getActionType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.ActionContextSymbol#getActionType <em>Action Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.rule.ActionType
	 * @see #getActionType()
	 * @generated
	 */
	void setActionType(ActionType value);

} // ActionContextSymbol
