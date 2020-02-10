/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.SerializerInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Serializer Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.SerializerInfoImpl#getSerializerType <em>Serializer Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.SerializerInfoImpl#getSerializerClass <em>Serializer Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.SerializerInfoImpl#isDefault <em>Default</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SerializerInfoImpl extends EObjectImpl implements SerializerInfo {
	/**
	 * The default value of the '{@link #getSerializerType() <em>Serializer Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerializerType()
	 * @generated
	 * @ordered
	 */
	protected static final String SERIALIZER_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSerializerType() <em>Serializer Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerializerType()
	 * @generated
	 * @ordered
	 */
	protected String serializerType = SERIALIZER_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSerializerClass() <em>Serializer Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerializerClass()
	 * @generated
	 * @ordered
	 */
	protected static final String SERIALIZER_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSerializerClass() <em>Serializer Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerializerClass()
	 * @generated
	 * @ordered
	 */
	protected String serializerClass = SERIALIZER_CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #isDefault() <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDefault()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DEFAULT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDefault() <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDefault()
	 * @generated
	 * @ordered
	 */
	protected boolean default_ = DEFAULT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SerializerInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChannelPackage.Literals.SERIALIZER_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSerializerType() {
		return serializerType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSerializerType(String newSerializerType) {
		String oldSerializerType = serializerType;
		serializerType = newSerializerType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.SERIALIZER_INFO__SERIALIZER_TYPE, oldSerializerType, serializerType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSerializerClass() {
		return serializerClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSerializerClass(String newSerializerClass) {
		String oldSerializerClass = serializerClass;
		serializerClass = newSerializerClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.SERIALIZER_INFO__SERIALIZER_CLASS, oldSerializerClass, serializerClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDefault() {
		return default_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefault(boolean newDefault) {
		boolean oldDefault = default_;
		default_ = newDefault;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.SERIALIZER_INFO__DEFAULT, oldDefault, default_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ChannelPackage.SERIALIZER_INFO__SERIALIZER_TYPE:
				return getSerializerType();
			case ChannelPackage.SERIALIZER_INFO__SERIALIZER_CLASS:
				return getSerializerClass();
			case ChannelPackage.SERIALIZER_INFO__DEFAULT:
				return isDefault();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ChannelPackage.SERIALIZER_INFO__SERIALIZER_TYPE:
				setSerializerType((String)newValue);
				return;
			case ChannelPackage.SERIALIZER_INFO__SERIALIZER_CLASS:
				setSerializerClass((String)newValue);
				return;
			case ChannelPackage.SERIALIZER_INFO__DEFAULT:
				setDefault((Boolean)newValue);
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
			case ChannelPackage.SERIALIZER_INFO__SERIALIZER_TYPE:
				setSerializerType(SERIALIZER_TYPE_EDEFAULT);
				return;
			case ChannelPackage.SERIALIZER_INFO__SERIALIZER_CLASS:
				setSerializerClass(SERIALIZER_CLASS_EDEFAULT);
				return;
			case ChannelPackage.SERIALIZER_INFO__DEFAULT:
				setDefault(DEFAULT_EDEFAULT);
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
			case ChannelPackage.SERIALIZER_INFO__SERIALIZER_TYPE:
				return SERIALIZER_TYPE_EDEFAULT == null ? serializerType != null : !SERIALIZER_TYPE_EDEFAULT.equals(serializerType);
			case ChannelPackage.SERIALIZER_INFO__SERIALIZER_CLASS:
				return SERIALIZER_CLASS_EDEFAULT == null ? serializerClass != null : !SERIALIZER_CLASS_EDEFAULT.equals(serializerClass);
			case ChannelPackage.SERIALIZER_INFO__DEFAULT:
				return default_ != DEFAULT_EDEFAULT;
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
		result.append(" (serializerType: ");
		result.append(serializerType);
		result.append(", serializerClass: ");
		result.append(serializerClass);
		result.append(", default: ");
		result.append(default_);
		result.append(')');
		return result.toString();
	}

} //SerializerInfoImpl
