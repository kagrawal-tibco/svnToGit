/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjndiconfig;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Optional Jndi Properties</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.OptionalJndiProperties#getRow <em>Row</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#getOptionalJndiProperties()
 * @model extendedMetaData="name='OptionalJNDIProperties-type' kind='elementOnly'"
 * @generated
 */
public interface OptionalJndiProperties extends EObject {
	/**
	 * Returns the value of the '<em><b>Row</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Row</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Row</em>' containment reference list.
	 * @see com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage#getOptionalJndiProperties_Row()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='row' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Row> getRow();

} // OptionalJndiProperties
