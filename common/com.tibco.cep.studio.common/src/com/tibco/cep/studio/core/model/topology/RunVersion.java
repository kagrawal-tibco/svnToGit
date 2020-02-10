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
 * A representation of the model object '<em><b>Run Version</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.RunVersion#getBeRuntime <em>Be Runtime</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getRunVersion()
 * @model extendedMetaData="name='run-version_._type' kind='elementOnly'"
 * @generated
 */
public interface RunVersion extends EObject {
	/**
	 * Returns the value of the '<em><b>Be Runtime</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Be Runtime</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Be Runtime</em>' containment reference.
	 * @see #setBeRuntime(BeRuntime)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getRunVersion_BeRuntime()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='be-runtime' namespace='##targetNamespace'"
	 * @generated
	 */
	BeRuntime getBeRuntime();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.RunVersion#getBeRuntime <em>Be Runtime</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Be Runtime</em>' containment reference.
	 * @see #getBeRuntime()
	 * @generated
	 */
	void setBeRuntime(BeRuntime value);

} // RunVersion
