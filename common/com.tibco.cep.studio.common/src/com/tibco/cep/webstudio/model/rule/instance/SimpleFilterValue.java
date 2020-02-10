/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.webstudio.model.rule.instance;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Filter Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.webstudio.model.rule.instance.SimpleFilterValue#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public interface SimpleFilterValue extends FilterValue {
	
	public final int VALUE_FEATURE_ID = 0;

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.webstudio.model.rule.instance.SimpleFilterValue#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // SimpleFilterValue
