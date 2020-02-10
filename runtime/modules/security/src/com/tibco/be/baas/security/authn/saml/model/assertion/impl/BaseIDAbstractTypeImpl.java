/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.assertion.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.baas.security.authn.saml.model.assertion.AssertionPackage;
import com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Base ID Abstract Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.BaseIDAbstractTypeImpl#getNameQualifier <em>Name Qualifier</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.BaseIDAbstractTypeImpl#getSPNameQualifier <em>SP Name Qualifier</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class BaseIDAbstractTypeImpl extends EObjectImpl implements BaseIDAbstractType {
	/**
	 * The default value of the '{@link #getNameQualifier() <em>Name Qualifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameQualifier()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_QUALIFIER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNameQualifier() <em>Name Qualifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameQualifier()
	 * @generated
	 * @ordered
	 */
	protected String nameQualifier = NAME_QUALIFIER_EDEFAULT;

	/**
	 * The default value of the '{@link #getSPNameQualifier() <em>SP Name Qualifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSPNameQualifier()
	 * @generated
	 * @ordered
	 */
	protected static final String SP_NAME_QUALIFIER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSPNameQualifier() <em>SP Name Qualifier</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSPNameQualifier()
	 * @generated
	 * @ordered
	 */
	protected String sPNameQualifier = SP_NAME_QUALIFIER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BaseIDAbstractTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AssertionPackage.Literals.BASE_ID_ABSTRACT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNameQualifier() {
		return nameQualifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameQualifier(String newNameQualifier) {
		String oldNameQualifier = nameQualifier;
		nameQualifier = newNameQualifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.BASE_ID_ABSTRACT_TYPE__NAME_QUALIFIER, oldNameQualifier, nameQualifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSPNameQualifier() {
		return sPNameQualifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSPNameQualifier(String newSPNameQualifier) {
		String oldSPNameQualifier = sPNameQualifier;
		sPNameQualifier = newSPNameQualifier;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.BASE_ID_ABSTRACT_TYPE__SP_NAME_QUALIFIER, oldSPNameQualifier, sPNameQualifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AssertionPackage.BASE_ID_ABSTRACT_TYPE__NAME_QUALIFIER:
				return getNameQualifier();
			case AssertionPackage.BASE_ID_ABSTRACT_TYPE__SP_NAME_QUALIFIER:
				return getSPNameQualifier();
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
			case AssertionPackage.BASE_ID_ABSTRACT_TYPE__NAME_QUALIFIER:
				setNameQualifier((String)newValue);
				return;
			case AssertionPackage.BASE_ID_ABSTRACT_TYPE__SP_NAME_QUALIFIER:
				setSPNameQualifier((String)newValue);
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
			case AssertionPackage.BASE_ID_ABSTRACT_TYPE__NAME_QUALIFIER:
				setNameQualifier(NAME_QUALIFIER_EDEFAULT);
				return;
			case AssertionPackage.BASE_ID_ABSTRACT_TYPE__SP_NAME_QUALIFIER:
				setSPNameQualifier(SP_NAME_QUALIFIER_EDEFAULT);
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
			case AssertionPackage.BASE_ID_ABSTRACT_TYPE__NAME_QUALIFIER:
				return NAME_QUALIFIER_EDEFAULT == null ? nameQualifier != null : !NAME_QUALIFIER_EDEFAULT.equals(nameQualifier);
			case AssertionPackage.BASE_ID_ABSTRACT_TYPE__SP_NAME_QUALIFIER:
				return SP_NAME_QUALIFIER_EDEFAULT == null ? sPNameQualifier != null : !SP_NAME_QUALIFIER_EDEFAULT.equals(sPNameQualifier);
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
		result.append(" (nameQualifier: ");
		result.append(nameQualifier);
		result.append(", sPNameQualifier: ");
		result.append(sPNameQualifier);
		result.append(')');
		return result.toString();
	}

} //BaseIDAbstractTypeImpl
