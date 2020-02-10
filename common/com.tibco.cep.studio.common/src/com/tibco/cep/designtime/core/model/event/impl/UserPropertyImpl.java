/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.event.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.UserProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.UserPropertyImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.UserPropertyImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.UserPropertyImpl#getExtendedProperties <em>Extended Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UserPropertyImpl extends EObjectImpl implements UserProperty {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final PROPERTY_TYPES TYPE_EDEFAULT = PROPERTY_TYPES.STRING;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected PROPERTY_TYPES type = TYPE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getExtendedProperties() <em>Extended Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtendedProperties()
	 * @generated
	 * @ordered
	 */
	protected PropertyMap extendedProperties;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UserPropertyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EventPackage.Literals.USER_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.USER_PROPERTY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PROPERTY_TYPES getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(PROPERTY_TYPES newType) {
		PROPERTY_TYPES oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.USER_PROPERTY__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyMap getExtendedProperties() {
		return extendedProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExtendedProperties(PropertyMap newExtendedProperties, NotificationChain msgs) {
		PropertyMap oldExtendedProperties = extendedProperties;
		extendedProperties = newExtendedProperties;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, EventPackage.USER_PROPERTY__EXTENDED_PROPERTIES, oldExtendedProperties, newExtendedProperties);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtendedProperties(PropertyMap newExtendedProperties) {
		if (newExtendedProperties != extendedProperties) {
			NotificationChain msgs = null;
			if (extendedProperties != null)
				msgs = ((InternalEObject)extendedProperties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - EventPackage.USER_PROPERTY__EXTENDED_PROPERTIES, null, msgs);
			if (newExtendedProperties != null)
				msgs = ((InternalEObject)newExtendedProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - EventPackage.USER_PROPERTY__EXTENDED_PROPERTIES, null, msgs);
			msgs = basicSetExtendedProperties(newExtendedProperties, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.USER_PROPERTY__EXTENDED_PROPERTIES, newExtendedProperties, newExtendedProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case EventPackage.USER_PROPERTY__EXTENDED_PROPERTIES:
				return basicSetExtendedProperties(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EventPackage.USER_PROPERTY__NAME:
				return getName();
			case EventPackage.USER_PROPERTY__TYPE:
				return getType();
			case EventPackage.USER_PROPERTY__EXTENDED_PROPERTIES:
				return getExtendedProperties();
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
			case EventPackage.USER_PROPERTY__NAME:
				setName((String)newValue);
				return;
			case EventPackage.USER_PROPERTY__TYPE:
				setType((PROPERTY_TYPES)newValue);
				return;
			case EventPackage.USER_PROPERTY__EXTENDED_PROPERTIES:
				setExtendedProperties((PropertyMap)newValue);
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
			case EventPackage.USER_PROPERTY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case EventPackage.USER_PROPERTY__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case EventPackage.USER_PROPERTY__EXTENDED_PROPERTIES:
				setExtendedProperties((PropertyMap)null);
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
			case EventPackage.USER_PROPERTY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case EventPackage.USER_PROPERTY__TYPE:
				return type != TYPE_EDEFAULT;
			case EventPackage.USER_PROPERTY__EXTENDED_PROPERTIES:
				return extendedProperties != null;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

} //UserPropertyImpl
