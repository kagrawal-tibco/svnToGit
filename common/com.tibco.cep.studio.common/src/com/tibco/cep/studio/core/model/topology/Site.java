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
 * A representation of the model object '<em><b>Site</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.Site#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.Site#getClusters <em>Clusters</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.Site#getHostResources <em>Host Resources</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.Site#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getSite()
 * @model extendedMetaData="name='site' kind='elementOnly'"
 * @generated
 */
public interface Site extends EObject {
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
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getSite_Description()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='description' namespace='##targetNamespace'"
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.Site#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Clusters</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Clusters</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Clusters</em>' containment reference.
	 * @see #setClusters(Clusters)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getSite_Clusters()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='clusters' namespace='##targetNamespace'"
	 * @generated
	 */
	Clusters getClusters();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.Site#getClusters <em>Clusters</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Clusters</em>' containment reference.
	 * @see #getClusters()
	 * @generated
	 */
	void setClusters(Clusters value);

	/**
	 * Returns the value of the '<em><b>Host Resources</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Host Resources</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Host Resources</em>' containment reference.
	 * @see #setHostResources(HostResources)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getSite_HostResources()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='host-resources' namespace='##targetNamespace'"
	 * @generated
	 */
	HostResources getHostResources();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.Site#getHostResources <em>Host Resources</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Host Resources</em>' containment reference.
	 * @see #getHostResources()
	 * @generated
	 */
	void setHostResources(HostResources value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getSite_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='name'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.Site#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Site
