/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.SerializerConfig;
import com.tibco.cep.designtime.core.model.service.channel.SerializerInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Serializer Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.SerializerConfigImpl#isUserDefined <em>User Defined</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.SerializerConfigImpl#getSerializers <em>Serializers</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SerializerConfigImpl extends EObjectImpl implements SerializerConfig {
	/**
	 * The default value of the '{@link #isUserDefined() <em>User Defined</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUserDefined()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USER_DEFINED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUserDefined() <em>User Defined</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUserDefined()
	 * @generated
	 * @ordered
	 */
	protected boolean userDefined = USER_DEFINED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSerializers() <em>Serializers</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerializers()
	 * @generated
	 * @ordered
	 */
	protected EList<SerializerInfo> serializers;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SerializerConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChannelPackage.Literals.SERIALIZER_CONFIG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public SerializerInfo getDefaultSerializer() {
		SerializerInfo defaultSerializerInfo = null;
		if (serializers != null) {
			int counter = 0;
			for (SerializerInfo serializerInfo : serializers) {
				if (counter == 0) {
					defaultSerializerInfo = serializerInfo;
				}
				boolean defaultVal = serializerInfo.isDefault();
				if (defaultVal) {
					//Return the first occurence
					return defaultSerializerInfo = serializerInfo;
				}
				counter++;
			}
			//No one found with default = true. Return first
			return defaultSerializerInfo;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isUserDefined() {
		return userDefined;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserDefined(boolean newUserDefined) {
		boolean oldUserDefined = userDefined;
		userDefined = newUserDefined;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.SERIALIZER_CONFIG__USER_DEFINED, oldUserDefined, userDefined));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SerializerInfo> getSerializers() {
		if (serializers == null) {
			serializers = new EObjectResolvingEList<SerializerInfo>(SerializerInfo.class, this, ChannelPackage.SERIALIZER_CONFIG__SERIALIZERS);
		}
		return serializers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ChannelPackage.SERIALIZER_CONFIG__USER_DEFINED:
				return isUserDefined();
			case ChannelPackage.SERIALIZER_CONFIG__SERIALIZERS:
				return getSerializers();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ChannelPackage.SERIALIZER_CONFIG__USER_DEFINED:
				setUserDefined((Boolean)newValue);
				return;
			case ChannelPackage.SERIALIZER_CONFIG__SERIALIZERS:
				getSerializers().clear();
				getSerializers().addAll((Collection<? extends SerializerInfo>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ChannelPackage.SERIALIZER_CONFIG__USER_DEFINED:
				setUserDefined(USER_DEFINED_EDEFAULT);
				return;
			case ChannelPackage.SERIALIZER_CONFIG__SERIALIZERS:
				getSerializers().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ChannelPackage.SERIALIZER_CONFIG__USER_DEFINED:
				return userDefined != USER_DEFINED_EDEFAULT;
			case ChannelPackage.SERIALIZER_CONFIG__SERIALIZERS:
				return serializers != null && !serializers.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (userDefined: ");
		result.append(userDefined);
		result.append(')');
		return result.toString();
	}

} //SerializerConfigImpl
