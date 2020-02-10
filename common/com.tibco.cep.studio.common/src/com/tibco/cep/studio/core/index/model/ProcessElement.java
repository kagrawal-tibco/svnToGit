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
 * A representation of the model object '<em><b>Process Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.ProcessElement#getProcess <em>Process</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getProcessElement()
 * @model
 * @generated
 */
public interface ProcessElement extends EntityElement {
	/**
	 * Returns the value of the '<em><b>Process</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Process</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Process</em>' containment reference.
	 * @see #setProcess(EObject)
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#getProcessElement_Process()
	 * @model containment="true"
	 * @generated
	 */
	EObject getProcess();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.index.model.ProcessElement#getProcess <em>Process</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Process</em>' containment reference.
	 * @see #getProcess()
	 * @generated
	 */
	void setProcess(EObject value);

} // ProcessElement
