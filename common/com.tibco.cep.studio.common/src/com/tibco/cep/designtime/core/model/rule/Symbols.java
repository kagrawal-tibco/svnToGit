/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Symbols</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Symbols#getSymbolList <em>Symbol List</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getSymbols()
 * @model
 * @generated
 */
public interface Symbols extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Symbol Map</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	Map<String, Symbol> getSymbolMap();

	/**
	 * Returns the value of the '<em><b>Symbol List</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.rule.Symbol}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Symbol List</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getSymbols_SymbolList()
	 * @model containment="true"
	 * @generated
	 */
	EList<Symbol> getSymbolList();

} // Symbols
