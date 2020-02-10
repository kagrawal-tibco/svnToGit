/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Field Reference Value Option</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.FieldReferenceValueOption#getField <em>Field</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getFieldReferenceValueOption()
 * @model
 * @generated
 */
public interface FieldReferenceValueOption extends ValueOption {
	/**
	 * Returns the value of the '<em><b>Field</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Field</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Field</em>' reference.
	 * @see #setField(EObject)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getFieldReferenceValueOption_Field()
	 * @model
	 * @generated
	 */
	EObject getField();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.FieldReferenceValueOption#getField <em>Field</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Field</em>' reference.
	 * @see #getField()
	 * @generated
	 */
	void setField(EObject value);

} // FieldReferenceValueOption
