/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Serializer Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.SerializerConfig#isUserDefined <em>User Defined</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.SerializerConfig#getSerializers <em>Serializers</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getSerializerConfig()
 * @model
 * @generated
 */
public interface SerializerConfig extends EObject {
	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Serializer</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	SerializerInfo getDefaultSerializer();

	/**
	 * Returns the value of the '<em><b>User Defined</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Defined</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Defined</em>' attribute.
	 * @see #setUserDefined(boolean)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getSerializerConfig_UserDefined()
	 * @model
	 * @generated
	 */
	boolean isUserDefined();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.SerializerConfig#isUserDefined <em>User Defined</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Defined</em>' attribute.
	 * @see #isUserDefined()
	 * @generated
	 */
	void setUserDefined(boolean value);

	/**
	 * Returns the value of the '<em><b>Serializers</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.service.channel.SerializerInfo}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Serializers</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Serializers</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getSerializerConfig_Serializers()
	 * @model
	 * @generated
	 */
	EList<SerializerInfo> getSerializers();

} // SerializerConfig
