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
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.DriverTypeInfo;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Driver Type Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverTypeInfoImpl#getDriverTypeName <em>Driver Type Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DriverTypeInfoImpl#getSharedResourceExtension <em>Shared Resource Extension</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DriverTypeInfoImpl extends EObjectImpl implements DriverTypeInfo {
	/**
	 * The default value of the '{@link #getDriverTypeName() <em>Driver Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDriverTypeName()
	 * @generated
	 * @ordered
	 */
	protected static final String DRIVER_TYPE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDriverTypeName() <em>Driver Type Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDriverTypeName()
	 * @generated
	 * @ordered
	 */
	protected String driverTypeName = DRIVER_TYPE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSharedResourceExtension() <em>Shared Resource Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSharedResourceExtension()
	 * @generated
	 * @ordered
	 */
	protected static final String SHARED_RESOURCE_EXTENSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSharedResourceExtension() <em>Shared Resource Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSharedResourceExtension()
	 * @generated
	 * @ordered
	 */
	protected String sharedResourceExtension = SHARED_RESOURCE_EXTENSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DriverTypeInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChannelPackage.Literals.DRIVER_TYPE_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDriverTypeName() {
		return driverTypeName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDriverTypeName(String newDriverTypeName) {
		String oldDriverTypeName = driverTypeName;
		driverTypeName = newDriverTypeName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_TYPE_INFO__DRIVER_TYPE_NAME, oldDriverTypeName, driverTypeName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSharedResourceExtension() {
		return sharedResourceExtension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSharedResourceExtension(String newSharedResourceExtension) {
		String oldSharedResourceExtension = sharedResourceExtension;
		sharedResourceExtension = newSharedResourceExtension;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DRIVER_TYPE_INFO__SHARED_RESOURCE_EXTENSION, oldSharedResourceExtension, sharedResourceExtension));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getName() {
		return getDriverTypeName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setName(String driverTypeName) {
		setDriverTypeName(driverTypeName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ChannelPackage.DRIVER_TYPE_INFO__DRIVER_TYPE_NAME:
				return getDriverTypeName();
			case ChannelPackage.DRIVER_TYPE_INFO__SHARED_RESOURCE_EXTENSION:
				return getSharedResourceExtension();
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
			case ChannelPackage.DRIVER_TYPE_INFO__DRIVER_TYPE_NAME:
				setDriverTypeName((String)newValue);
				return;
			case ChannelPackage.DRIVER_TYPE_INFO__SHARED_RESOURCE_EXTENSION:
				setSharedResourceExtension((String)newValue);
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
			case ChannelPackage.DRIVER_TYPE_INFO__DRIVER_TYPE_NAME:
				setDriverTypeName(DRIVER_TYPE_NAME_EDEFAULT);
				return;
			case ChannelPackage.DRIVER_TYPE_INFO__SHARED_RESOURCE_EXTENSION:
				setSharedResourceExtension(SHARED_RESOURCE_EXTENSION_EDEFAULT);
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
			case ChannelPackage.DRIVER_TYPE_INFO__DRIVER_TYPE_NAME:
				return DRIVER_TYPE_NAME_EDEFAULT == null ? driverTypeName != null : !DRIVER_TYPE_NAME_EDEFAULT.equals(driverTypeName);
			case ChannelPackage.DRIVER_TYPE_INFO__SHARED_RESOURCE_EXTENSION:
				return SHARED_RESOURCE_EXTENSION_EDEFAULT == null ? sharedResourceExtension != null : !SHARED_RESOURCE_EXTENSION_EDEFAULT.equals(sharedResourceExtension);
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
		result.append(" (driverTypeName: ");
		result.append(driverTypeName);
		result.append(", sharedResourceExtension: ");
		result.append(sharedResourceExtension);
		result.append(')');
		return result.toString();
	}
	
	@Override
	public boolean equals(Object otherObject) {
		if (otherObject == this) {
			return true;
		}
		if (!(otherObject instanceof DRIVER_TYPE)) {
			return false;
		}
		DRIVER_TYPE other = (DRIVER_TYPE)otherObject;
		//Check names for equality
		String otherName = other.getName();
		if (!otherName.equals(driverTypeName)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return driverTypeName.hashCode();
	}

} //DriverTypeInfoImpl
