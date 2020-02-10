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
 * A representation of the model object '<em><b>Host Resource</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.HostResource#getHostname <em>Hostname</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.HostResource#getIp <em>Ip</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.HostResource#getUserCredentials <em>User Credentials</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.HostResource#getOsType <em>Os Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.HostResource#getSoftware <em>Software</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.HostResource#getStartPuMethod <em>Start Pu Method</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.HostResource#getId <em>Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getHostResource()
 * @model extendedMetaData="name='host-resource_._type' kind='elementOnly'"
 * @generated
 */
public interface HostResource extends EObject {
	/**
	 * Returns the value of the '<em><b>Hostname</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Hostname</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hostname</em>' attribute.
	 * @see #setHostname(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getHostResource_Hostname()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='hostname' namespace='##targetNamespace'"
	 * @generated
	 */
	String getHostname();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.HostResource#getHostname <em>Hostname</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hostname</em>' attribute.
	 * @see #getHostname()
	 * @generated
	 */
	void setHostname(String value);

	/**
	 * Returns the value of the '<em><b>Ip</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ip</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ip</em>' attribute.
	 * @see #setIp(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getHostResource_Ip()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='ip' namespace='##targetNamespace'"
	 * @generated
	 */
	String getIp();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.HostResource#getIp <em>Ip</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ip</em>' attribute.
	 * @see #getIp()
	 * @generated
	 */
	void setIp(String value);

	/**
	 * Returns the value of the '<em><b>User Credentials</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Credentials</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Credentials</em>' containment reference.
	 * @see #setUserCredentials(UserCredentials)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getHostResource_UserCredentials()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='user-credentials' namespace='##targetNamespace'"
	 * @generated
	 */
	UserCredentials getUserCredentials();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.HostResource#getUserCredentials <em>User Credentials</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Credentials</em>' containment reference.
	 * @see #getUserCredentials()
	 * @generated
	 */
	void setUserCredentials(UserCredentials value);

	/**
	 * Returns the value of the '<em><b>Os Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Os Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Os Type</em>' attribute.
	 * @see #setOsType(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getHostResource_OsType()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='os-type' namespace='##targetNamespace'"
	 * @generated
	 */
	String getOsType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.HostResource#getOsType <em>Os Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Os Type</em>' attribute.
	 * @see #getOsType()
	 * @generated
	 */
	void setOsType(String value);

	/**
	 * Returns the value of the '<em><b>Software</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Software</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Software</em>' containment reference.
	 * @see #setSoftware(Software)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getHostResource_Software()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='software' namespace='##targetNamespace'"
	 * @generated
	 */
	Software getSoftware();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.HostResource#getSoftware <em>Software</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Software</em>' containment reference.
	 * @see #getSoftware()
	 * @generated
	 */
	void setSoftware(Software value);

	/**
	 * Returns the value of the '<em><b>Start Pu Method</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Pu Method</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Pu Method</em>' containment reference.
	 * @see #setStartPuMethod(StartPuMethod)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getHostResource_StartPuMethod()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='start-pu-method' namespace='##targetNamespace'"
	 * @generated
	 */
	StartPuMethod getStartPuMethod();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.HostResource#getStartPuMethod <em>Start Pu Method</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Pu Method</em>' containment reference.
	 * @see #getStartPuMethod()
	 * @generated
	 */
	void setStartPuMethod(StartPuMethod value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.tibco.cep.studio.core.model.topology.TopologyPackage#getHostResource_Id()
	 * @model id="true" dataType="org.eclipse.emf.ecore.xml.type.ID"
	 *        extendedMetaData="kind='attribute' name='id'"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.model.topology.HostResource#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

} // HostResource
