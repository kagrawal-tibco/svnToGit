/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Shared Process Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.SharedProcessElement#getSharedProcess <em>Shared Process</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getSharedProcessElement()
 * @model
 * @generated
 */
public interface SharedProcessElement extends SharedElement, ProcessElement {
	/**
	 * Returns the value of the '<em><b>Shared Process</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shared Process</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shared Process</em>' reference.
	 * @see #setSharedProcess(EObject)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getSharedProcessElement_SharedProcess()
	 * @model
	 * @generated
	 */
	EObject getSharedProcess();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.SharedProcessElement#getSharedProcess <em>Shared Process</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shared Process</em>' reference.
	 * @see #getSharedProcess()
	 * @generated
	 */
	void setSharedProcess(EObject value);

} // SharedProcessElement
