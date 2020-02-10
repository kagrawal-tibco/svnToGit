/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ssh</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.Ssh#getPort <em>Port</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getSsh()
 * @model extendedMetaData="name='ssh_._type' kind='empty'"
 * @generated
 */
public interface Ssh extends EObject {
	/**
	 * Returns the value of the '<em><b>Port</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Port</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Port</em>' attribute.
	 * @see #setPort(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getSsh_Port()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='port'"
	 * @generated
	 */
	String getPort();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.Ssh#getPort <em>Port</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Port</em>' attribute.
	 * @see #getPort()
	 * @generated
	 */
	void setPort(String value);

} // Ssh
