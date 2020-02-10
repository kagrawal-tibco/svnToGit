/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.validation;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model Error</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.validation.ModelError#getMessage <em>Message</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.validation.ModelError#isWarning <em>Warning</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.validation.ModelError#getSource <em>Source</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.validation.ValidationPackage#getModelError()
 * @model
 * @generated
 */
public interface ModelError extends EObject {
	/**
	 * Returns the value of the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message</em>' attribute.
	 * @see #setMessage(String)
	 * @see com.tibco.cep.designtime.core.model.validation.ValidationPackage#getModelError_Message()
	 * @model
	 * @generated
	 */
	String getMessage();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.validation.ModelError#getMessage <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message</em>' attribute.
	 * @see #getMessage()
	 * @generated
	 */
	void setMessage(String value);

	/**
	 * Returns the value of the '<em><b>Warning</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Warning</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Warning</em>' attribute.
	 * @see #setWarning(boolean)
	 * @see com.tibco.cep.designtime.core.model.validation.ValidationPackage#getModelError_Warning()
	 * @model
	 * @generated
	 */
	boolean isWarning();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.validation.ModelError#isWarning <em>Warning</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Warning</em>' attribute.
	 * @see #isWarning()
	 * @generated
	 */
	void setWarning(boolean value);

	/**
	 * Returns the value of the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' reference.
	 * @see #setSource(EObject)
	 * @see com.tibco.cep.designtime.core.model.validation.ValidationPackage#getModelError_Source()
	 * @model
	 * @generated
	 */
	EObject getSource();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.validation.ModelError#getSource <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' reference.
	 * @see #getSource()
	 * @generated
	 */
	void setSource(EObject value);

} // ModelError
