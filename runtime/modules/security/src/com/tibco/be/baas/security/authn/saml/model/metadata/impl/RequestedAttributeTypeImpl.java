/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.be.baas.security.authn.saml.model.assertion.impl.AttributeTypeImpl;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;
import com.tibco.be.baas.security.authn.saml.model.metadata.RequestedAttributeType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Requested Attribute Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.RequestedAttributeTypeImpl#isIsRequired <em>Is Required</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RequestedAttributeTypeImpl extends AttributeTypeImpl implements RequestedAttributeType {
	/**
	 * The default value of the '{@link #isIsRequired() <em>Is Required</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsRequired()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_REQUIRED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsRequired() <em>Is Required</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsRequired()
	 * @generated
	 * @ordered
	 */
	protected boolean isRequired = IS_REQUIRED_EDEFAULT;

	/**
	 * This is true if the Is Required attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean isRequiredESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RequestedAttributeTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.REQUESTED_ATTRIBUTE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsRequired() {
		return isRequired;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsRequired(boolean newIsRequired) {
		boolean oldIsRequired = isRequired;
		isRequired = newIsRequired;
		boolean oldIsRequiredESet = isRequiredESet;
		isRequiredESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.REQUESTED_ATTRIBUTE_TYPE__IS_REQUIRED, oldIsRequired, isRequired, !oldIsRequiredESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetIsRequired() {
		boolean oldIsRequired = isRequired;
		boolean oldIsRequiredESet = isRequiredESet;
		isRequired = IS_REQUIRED_EDEFAULT;
		isRequiredESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, MetadataPackage.REQUESTED_ATTRIBUTE_TYPE__IS_REQUIRED, oldIsRequired, IS_REQUIRED_EDEFAULT, oldIsRequiredESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetIsRequired() {
		return isRequiredESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MetadataPackage.REQUESTED_ATTRIBUTE_TYPE__IS_REQUIRED:
				return isIsRequired() ? Boolean.TRUE : Boolean.FALSE;
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
			case MetadataPackage.REQUESTED_ATTRIBUTE_TYPE__IS_REQUIRED:
				setIsRequired(((Boolean)newValue).booleanValue());
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
			case MetadataPackage.REQUESTED_ATTRIBUTE_TYPE__IS_REQUIRED:
				unsetIsRequired();
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
			case MetadataPackage.REQUESTED_ATTRIBUTE_TYPE__IS_REQUIRED:
				return isSetIsRequired();
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
		result.append(" (isRequired: ");
		if (isRequiredESet) result.append(isRequired); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //RequestedAttributeTypeImpl
