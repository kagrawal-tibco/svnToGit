/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.id;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.Repository#getIdentity <em>Identity</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getRepository()
 * @model extendedMetaData="name='repository-type' kind='elementOnly'"
 * @generated
 */
public interface Repository extends EObject {
	/**
	 * Returns the value of the '<em><b>Identity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Identity</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Identity</em>' containment reference.
	 * @see #setIdentity(Identity)
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getRepository_Identity()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='identity'"
	 * @generated
	 */
	Identity getIdentity();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.id.Repository#getIdentity <em>Identity</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Identity</em>' containment reference.
	 * @see #getIdentity()
	 * @generated
	 */
	void setIdentity(Identity value);

} // Repository
