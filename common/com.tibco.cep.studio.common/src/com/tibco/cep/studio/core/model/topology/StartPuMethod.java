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
 * A representation of the model object '<em><b>Start Pu Method</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.StartPuMethod#getHawk <em>Hawk</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.StartPuMethod#getSsh <em>Ssh</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.StartPuMethod#getPstools <em>Pstools</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getStartPuMethod()
 * @model extendedMetaData="name='start-pu-method_._type' kind='elementOnly'"
 * @generated
 */
public interface StartPuMethod extends EObject {
	/**
	 * Returns the value of the '<em><b>Hawk</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hawk</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hawk</em>' containment reference.
	 * @see #setHawk(Hawk)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getStartPuMethod_Hawk()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='hawk' namespace='##targetNamespace'"
	 * @generated
	 */
	Hawk getHawk();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.StartPuMethod#getHawk <em>Hawk</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hawk</em>' containment reference.
	 * @see #getHawk()
	 * @generated
	 */
	void setHawk(Hawk value);

	/**
	 * Returns the value of the '<em><b>Ssh</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ssh</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ssh</em>' containment reference.
	 * @see #setSsh(Ssh)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getStartPuMethod_Ssh()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ssh' namespace='##targetNamespace'"
	 * @generated
	 */
	Ssh getSsh();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.StartPuMethod#getSsh <em>Ssh</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ssh</em>' containment reference.
	 * @see #getSsh()
	 * @generated
	 */
	void setSsh(Ssh value);

	/**
	 * Returns the value of the '<em><b>Pstools</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pstools</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pstools</em>' containment reference.
	 * @see #setPstools(Pstools)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getStartPuMethod_Pstools()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='pstools' namespace='##targetNamespace'"
	 * @generated
	 */
	Pstools getPstools();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.StartPuMethod#getPstools <em>Pstools</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pstools</em>' containment reference.
	 * @see #getPstools()
	 * @generated
	 */
	void setPstools(Pstools value);

} // StartPuMethod
