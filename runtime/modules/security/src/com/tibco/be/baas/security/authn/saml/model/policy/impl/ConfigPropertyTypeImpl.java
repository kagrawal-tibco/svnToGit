/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.policy.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyType;
import com.tibco.be.baas.security.authn.saml.model.policy.ConfigPropertyValue;
import com.tibco.be.baas.security.authn.saml.model.policy.DataTypeType;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Config Property Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.ConfigPropertyTypeImpl#getDataType <em>Data Type</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.ConfigPropertyTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.policy.impl.ConfigPropertyTypeImpl#getPropertyValue <em>Property Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConfigPropertyTypeImpl extends EObjectImpl implements ConfigPropertyType {
	/**
	 * The default value of the '{@link #getDataType() <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected static final DataTypeType DATA_TYPE_EDEFAULT = DataTypeType.INTEGER;

	/**
	 * The cached value of the '{@link #getDataType() <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected DataTypeType dataType = DATA_TYPE_EDEFAULT;

	/**
	 * This is true if the Data Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean dataTypeESet;

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
	 * The cached value of the '{@link #getPropertyValue() <em>Property Value</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyValue()
	 * @generated
	 * @ordered
	 */
	protected EList<ConfigPropertyValue> propertyValue;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConfigPropertyTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PolicyPackage.Literals.CONFIG_PROPERTY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataTypeType getDataType() {
		return dataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataType(DataTypeType newDataType) {
		DataTypeType oldDataType = dataType;
		dataType = newDataType == null ? DATA_TYPE_EDEFAULT : newDataType;
		boolean oldDataTypeESet = dataTypeESet;
		dataTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PolicyPackage.CONFIG_PROPERTY_TYPE__DATA_TYPE, oldDataType, dataType, !oldDataTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDataType() {
		DataTypeType oldDataType = dataType;
		boolean oldDataTypeESet = dataTypeESet;
		dataType = DATA_TYPE_EDEFAULT;
		dataTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PolicyPackage.CONFIG_PROPERTY_TYPE__DATA_TYPE, oldDataType, DATA_TYPE_EDEFAULT, oldDataTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDataType() {
		return dataTypeESet;
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
			eNotify(new ENotificationImpl(this, Notification.SET, PolicyPackage.CONFIG_PROPERTY_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ConfigPropertyValue> getPropertyValue() {
		if (propertyValue == null) {
			propertyValue = new EObjectContainmentEList<ConfigPropertyValue>(ConfigPropertyValue.class, this, PolicyPackage.CONFIG_PROPERTY_TYPE__PROPERTY_VALUE);
		}
		return propertyValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case PolicyPackage.CONFIG_PROPERTY_TYPE__PROPERTY_VALUE:
				return ((InternalEList<?>)getPropertyValue()).basicRemove(otherEnd, msgs);
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
			case PolicyPackage.CONFIG_PROPERTY_TYPE__DATA_TYPE:
				return getDataType();
			case PolicyPackage.CONFIG_PROPERTY_TYPE__NAME:
				return getName();
			case PolicyPackage.CONFIG_PROPERTY_TYPE__PROPERTY_VALUE:
				return getPropertyValue();
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
			case PolicyPackage.CONFIG_PROPERTY_TYPE__DATA_TYPE:
				setDataType((DataTypeType)newValue);
				return;
			case PolicyPackage.CONFIG_PROPERTY_TYPE__NAME:
				setName((String)newValue);
				return;
			case PolicyPackage.CONFIG_PROPERTY_TYPE__PROPERTY_VALUE:
				getPropertyValue().clear();
				getPropertyValue().addAll((Collection<? extends ConfigPropertyValue>)newValue);
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
			case PolicyPackage.CONFIG_PROPERTY_TYPE__DATA_TYPE:
				unsetDataType();
				return;
			case PolicyPackage.CONFIG_PROPERTY_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case PolicyPackage.CONFIG_PROPERTY_TYPE__PROPERTY_VALUE:
				getPropertyValue().clear();
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
			case PolicyPackage.CONFIG_PROPERTY_TYPE__DATA_TYPE:
				return isSetDataType();
			case PolicyPackage.CONFIG_PROPERTY_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case PolicyPackage.CONFIG_PROPERTY_TYPE__PROPERTY_VALUE:
				return propertyValue != null && !propertyValue.isEmpty();
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
		result.append(" (dataType: ");
		if (dataTypeESet) result.append(dataType); else result.append("<unset>");
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //ConfigPropertyTypeImpl
