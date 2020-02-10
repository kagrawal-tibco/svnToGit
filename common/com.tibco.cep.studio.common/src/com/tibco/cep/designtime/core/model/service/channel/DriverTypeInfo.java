/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Driver Type Info</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverTypeInfo#getDriverTypeName <em>Driver Type Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverTypeInfo#getSharedResourceExtension <em>Shared Resource Extension</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverTypeInfo()
 * @model
 * @generated
 */
public interface DriverTypeInfo extends DRIVER_TYPE {
	/**
	 * Returns the value of the '<em><b>Driver Type Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Driver Type Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Driver Type Name</em>' attribute.
	 * @see #setDriverTypeName(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverTypeInfo_DriverTypeName()
	 * @model
	 * @generated
	 */
	String getDriverTypeName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.DriverTypeInfo#getDriverTypeName <em>Driver Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Driver Type Name</em>' attribute.
	 * @see #getDriverTypeName()
	 * @generated
	 */
	void setDriverTypeName(String value);

	/**
	 * Returns the value of the '<em><b>Shared Resource Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shared Resource Extension</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shared Resource Extension</em>' attribute.
	 * @see #setSharedResourceExtension(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverTypeInfo_SharedResourceExtension()
	 * @model
	 * @generated
	 */
	String getSharedResourceExtension();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.DriverTypeInfo#getSharedResourceExtension <em>Shared Resource Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shared Resource Extension</em>' attribute.
	 * @see #getSharedResourceExtension()
	 * @generated
	 */
	void setSharedResourceExtension(String value);

} // DriverTypeInfo
