/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.webstudio.model.rule.instance;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Multi Filter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.webstudio.model.rule.instance.MultiFilter#getMatchType <em>Match Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public interface MultiFilter extends Clause {
	
	public final int MATCH_TYPE_FEATURE_ID = 1;

	/**
	 * Returns the value of the '<em><b>Match Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Match Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Match Type</em>' attribute.
	 * @see #setMatchType(String)
	 * @generated
	 */
	String getMatchType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.webstudio.model.rule.instance.MultiFilter#getMatchType <em>Match Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Match Type</em>' attribute.
	 * @see #getMatchType()
	 * @generated
	 */
	void setMatchType(String value);

} // MultiFilter
