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
 * A representation of the model object '<em><b>Resource Descriptions</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.ResourceDescriptions#getNode <em>Node</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getResourceDescriptions()
 * @model extendedMetaData="name='resourceDescriptions-type' kind='elementOnly'"
 * @generated
 */
public interface ResourceDescriptions extends EObject {
	/**
	 * Returns the value of the '<em><b>Node</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Node</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Node</em>' containment reference.
	 * @see #setNode(Node)
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getResourceDescriptions_Node()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='node'"
	 * @generated
	 */
	Node getNode();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.id.ResourceDescriptions#getNode <em>Node</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Node</em>' containment reference.
	 * @see #getNode()
	 * @generated
	 */
	void setNode(Node value);

} // ResourceDescriptions
