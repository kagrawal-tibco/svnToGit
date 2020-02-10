/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Action Context</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.ActionContext#getActionContextSymbols <em>Action Context Symbols</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getActionContext()
 * @model
 * @generated
 */
public interface ActionContext extends EObject {
	/**
	 * Returns the value of the '<em><b>Action Context Symbols</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action Context Symbols</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action Context Symbols</em>' containment reference.
	 * @see #setActionContextSymbols(Symbols)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getActionContext_ActionContextSymbols()
	 * @model containment="true"
	 * @generated
	 */
	Symbols getActionContextSymbols();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.ActionContext#getActionContextSymbols <em>Action Context Symbols</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action Context Symbols</em>' containment reference.
	 * @see #getActionContextSymbols()
	 * @generated
	 */
	void setActionContextSymbols(Symbols value);

} // ActionContext
