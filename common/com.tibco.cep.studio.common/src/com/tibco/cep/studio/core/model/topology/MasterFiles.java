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
 * A representation of the model object '<em><b>Master Files</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.MasterFiles#getCddMaster <em>Cdd Master</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.MasterFiles#getEarMaster <em>Ear Master</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getMasterFiles()
 * @model extendedMetaData="name='master-files_._type' kind='elementOnly'"
 * @generated
 */
public interface MasterFiles extends EObject {
	/**
	 * Returns the value of the '<em><b>Cdd Master</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cdd Master</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cdd Master</em>' attribute.
	 * @see #setCddMaster(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getMasterFiles_CddMaster()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='cdd-master' namespace='##targetNamespace'"
	 * @generated
	 */
	String getCddMaster();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.MasterFiles#getCddMaster <em>Cdd Master</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cdd Master</em>' attribute.
	 * @see #getCddMaster()
	 * @generated
	 */
	void setCddMaster(String value);

	/**
	 * Returns the value of the '<em><b>Ear Master</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ear Master</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ear Master</em>' attribute.
	 * @see #setEarMaster(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getMasterFiles_EarMaster()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='ear-master' namespace='##targetNamespace'"
	 * @generated
	 */
	String getEarMaster();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.MasterFiles#getEarMaster <em>Ear Master</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ear Master</em>' attribute.
	 * @see #getEarMaster()
	 * @generated
	 */
	void setEarMaster(String value);

} // MasterFiles
