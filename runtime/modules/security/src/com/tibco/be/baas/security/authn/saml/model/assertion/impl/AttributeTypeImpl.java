/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;
import com.tibco.be.baas.security.authn.saml.model.assertion.AttributeType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AttributeTypeImpl#getAttributeValue <em>Attribute Value</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AttributeTypeImpl#getFriendlyName <em>Friendly Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AttributeTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AttributeTypeImpl#getNameFormat <em>Name Format</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.AttributeTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeTypeImpl extends EObjectImpl implements AttributeType {
	/**
	 * The cached value of the '{@link #getAttributeValue() <em>Attribute Value</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeValue()
	 * @generated
	 * @ordered
	 */
	protected EList<EObject> attributeValue;

	/**
	 * The default value of the '{@link #getFriendlyName() <em>Friendly Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFriendlyName()
	 * @generated
	 * @ordered
	 */
	protected static final String FRIENDLY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFriendlyName() <em>Friendly Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFriendlyName()
	 * @generated
	 * @ordered
	 */
	protected String friendlyName = FRIENDLY_NAME_EDEFAULT;

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
	 * The default value of the '{@link #getNameFormat() <em>Name Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameFormat()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_FORMAT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNameFormat() <em>Name Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameFormat()
	 * @generated
	 * @ordered
	 */
	protected String nameFormat = NAME_FORMAT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAnyAttribute() <em>Any Attribute</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnyAttribute()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap anyAttribute;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AssertionPackage.Literals.ATTRIBUTE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EObject> getAttributeValue() {
		if (attributeValue == null) {
			attributeValue = new EObjectContainmentEList<EObject>(EObject.class, this, AssertionPackage.ATTRIBUTE_TYPE__ATTRIBUTE_VALUE);
		}
		return attributeValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFriendlyName() {
		return friendlyName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFriendlyName(String newFriendlyName) {
		String oldFriendlyName = friendlyName;
		friendlyName = newFriendlyName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.ATTRIBUTE_TYPE__FRIENDLY_NAME, oldFriendlyName, friendlyName));
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
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.ATTRIBUTE_TYPE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNameFormat() {
		return nameFormat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameFormat(String newNameFormat) {
		String oldNameFormat = nameFormat;
		nameFormat = newNameFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.ATTRIBUTE_TYPE__NAME_FORMAT, oldNameFormat, nameFormat));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, AssertionPackage.ATTRIBUTE_TYPE__ANY_ATTRIBUTE);
		}
		return anyAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AssertionPackage.ATTRIBUTE_TYPE__ATTRIBUTE_VALUE:
				return ((InternalEList<?>)getAttributeValue()).basicRemove(otherEnd, msgs);
			case AssertionPackage.ATTRIBUTE_TYPE__ANY_ATTRIBUTE:
				return ((InternalEList<?>)getAnyAttribute()).basicRemove(otherEnd, msgs);
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
			case AssertionPackage.ATTRIBUTE_TYPE__ATTRIBUTE_VALUE:
				return getAttributeValue();
			case AssertionPackage.ATTRIBUTE_TYPE__FRIENDLY_NAME:
				return getFriendlyName();
			case AssertionPackage.ATTRIBUTE_TYPE__NAME:
				return getName();
			case AssertionPackage.ATTRIBUTE_TYPE__NAME_FORMAT:
				return getNameFormat();
			case AssertionPackage.ATTRIBUTE_TYPE__ANY_ATTRIBUTE:
				if (coreType) return getAnyAttribute();
				return ((FeatureMap.Internal)getAnyAttribute()).getWrapper();
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
			case AssertionPackage.ATTRIBUTE_TYPE__ATTRIBUTE_VALUE:
				getAttributeValue().clear();
				getAttributeValue().addAll((Collection<? extends EObject>)newValue);
				return;
			case AssertionPackage.ATTRIBUTE_TYPE__FRIENDLY_NAME:
				setFriendlyName((String)newValue);
				return;
			case AssertionPackage.ATTRIBUTE_TYPE__NAME:
				setName((String)newValue);
				return;
			case AssertionPackage.ATTRIBUTE_TYPE__NAME_FORMAT:
				setNameFormat((String)newValue);
				return;
			case AssertionPackage.ATTRIBUTE_TYPE__ANY_ATTRIBUTE:
				((FeatureMap.Internal)getAnyAttribute()).set(newValue);
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
			case AssertionPackage.ATTRIBUTE_TYPE__ATTRIBUTE_VALUE:
				getAttributeValue().clear();
				return;
			case AssertionPackage.ATTRIBUTE_TYPE__FRIENDLY_NAME:
				setFriendlyName(FRIENDLY_NAME_EDEFAULT);
				return;
			case AssertionPackage.ATTRIBUTE_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AssertionPackage.ATTRIBUTE_TYPE__NAME_FORMAT:
				setNameFormat(NAME_FORMAT_EDEFAULT);
				return;
			case AssertionPackage.ATTRIBUTE_TYPE__ANY_ATTRIBUTE:
				getAnyAttribute().clear();
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
			case AssertionPackage.ATTRIBUTE_TYPE__ATTRIBUTE_VALUE:
				return attributeValue != null && !attributeValue.isEmpty();
			case AssertionPackage.ATTRIBUTE_TYPE__FRIENDLY_NAME:
				return FRIENDLY_NAME_EDEFAULT == null ? friendlyName != null : !FRIENDLY_NAME_EDEFAULT.equals(friendlyName);
			case AssertionPackage.ATTRIBUTE_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AssertionPackage.ATTRIBUTE_TYPE__NAME_FORMAT:
				return NAME_FORMAT_EDEFAULT == null ? nameFormat != null : !NAME_FORMAT_EDEFAULT.equals(nameFormat);
			case AssertionPackage.ATTRIBUTE_TYPE__ANY_ATTRIBUTE:
				return anyAttribute != null && !anyAttribute.isEmpty();
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
		result.append(" (friendlyName: ");
		result.append(friendlyName);
		result.append(", name: ");
		result.append(name);
		result.append(", nameFormat: ");
		result.append(nameFormat);
		result.append(", anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //AttributeTypeImpl
