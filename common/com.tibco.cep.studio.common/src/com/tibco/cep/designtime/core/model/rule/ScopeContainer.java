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
 * A representation of the model object '<em><b>Scope Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.ScopeContainer#getSymbols <em>Symbols</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getScopeContainer()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface ScopeContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Symbols</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Symbols</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Symbols</em>' containment reference.
	 * @see #setSymbols(Symbols)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getScopeContainer_Symbols()
	 * @model containment="true"
	 * @generated
	 */
	Symbols getSymbols();
	
	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.ScopeContainer#getSymbols <em>Symbols</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Symbols</em>' containment reference.
	 * @see #getSymbols()
	 * @generated
	 */
	void setSymbols(Symbols value);

	/**
	 * @generated NOT
	 */
	Symbol getSymbol(String id);
} // ScopeContainer
