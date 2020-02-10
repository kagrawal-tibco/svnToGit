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
 * A representation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.Node#getDescription <em>Description</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getNode()
 * @model extendedMetaData="name='node-type' kind='elementOnly'"
 * @generated
 */
public interface Node extends EObject {
	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.tibco.be.util.config.sharedresources.id.IdPackage#getNode_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='description'"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.sharedresources.id.Node#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

} // Node
