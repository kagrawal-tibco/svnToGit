/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Object Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.ObjectProperty#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.ModelPackage#getObjectProperty()
 * @model
 * @generated
 */
public interface ObjectProperty extends Entity {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(EObject)
	 * @see com.tibco.cep.designtime.core.model.ModelPackage#getObjectProperty_Value()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EObject getValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.ObjectProperty#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(EObject value);

} // ObjectProperty
