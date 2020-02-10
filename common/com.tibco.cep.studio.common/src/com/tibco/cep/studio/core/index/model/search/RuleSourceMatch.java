/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.search;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule Source Match</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.RuleSourceMatch#getOffset <em>Offset</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.RuleSourceMatch#getLength <em>Length</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.search.RuleSourceMatch#getContainingRule <em>Containing Rule</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage#getRuleSourceMatch()
 * @model
 * @generated
 */
public interface RuleSourceMatch extends EObject {
	/**
	 * Returns the value of the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Offset</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Offset</em>' attribute.
	 * @see #setOffset(int)
	 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage#getRuleSourceMatch_Offset()
	 * @model
	 * @generated
	 */
	int getOffset();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.search.RuleSourceMatch#getOffset <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Offset</em>' attribute.
	 * @see #getOffset()
	 * @generated
	 */
	void setOffset(int value);

	/**
	 * Returns the value of the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Length</em>' attribute.
	 * @see #setLength(int)
	 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage#getRuleSourceMatch_Length()
	 * @model
	 * @generated
	 */
	int getLength();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.search.RuleSourceMatch#getLength <em>Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Length</em>' attribute.
	 * @see #getLength()
	 * @generated
	 */
	void setLength(int value);

	/**
	 * Returns the value of the '<em><b>Containing Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Containing Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Containing Rule</em>' reference.
	 * @see #setContainingRule(EObject)
	 * @see com.tibco.cep.studio.core.index.model.search.SearchPackage#getRuleSourceMatch_ContainingRule()
	 * @model
	 * @generated
	 */
	EObject getContainingRule();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.search.RuleSourceMatch#getContainingRule <em>Containing Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Containing Rule</em>' reference.
	 * @see #getContainingRule()
	 * @generated
	 */
	void setContainingRule(EObject value);

} // RuleSourceMatch
