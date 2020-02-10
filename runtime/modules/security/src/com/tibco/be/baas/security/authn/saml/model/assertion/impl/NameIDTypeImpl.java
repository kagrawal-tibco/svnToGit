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
import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Name ID Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.NameIDTypeImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.NameIDTypeImpl#getFormat <em>Format</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.NameIDTypeImpl#getNameQualifier <em>Name Qualifier</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.NameIDTypeImpl#getSPNameQualifier <em>SP Name Qualifier</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.NameIDTypeImpl#getSPProvidedID <em>SP Provided ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NameIDTypeImpl extends EObjectImpl implements NameIDType {
	/**
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected String value = VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getFormat() <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormat()
	 * @generated
	 * @ordered
	 */
	protected static final String FORMAT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFormat() <em>Format</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFormat()
	 * @generated
	 * @ordered
	 */
	protected String format = FORMAT_EDEFAULT;

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
	 * The default value of the '{@link #getSPProvidedID() <em>SP Provided ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSPProvidedID()
	 * @generated
	 * @ordered
	 */
	protected static final String SP_PROVIDED_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSPProvidedID() <em>SP Provided ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSPProvidedID()
	 * @generated
	 * @ordered
	 */
	protected String sPProvidedID = SP_PROVIDED_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NameIDTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AssertionPackage.Literals.NAME_ID_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(String newValue) {
		String oldValue = value;
		value = newValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.NAME_ID_TYPE__VALUE, oldValue, value));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFormat(String newFormat) {
		String oldFormat = format;
		format = newFormat;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.NAME_ID_TYPE__FORMAT, oldFormat, format));
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
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.NAME_ID_TYPE__NAME_QUALIFIER, oldNameQualifier, nameQualifier));
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
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.NAME_ID_TYPE__SP_NAME_QUALIFIER, oldSPNameQualifier, sPNameQualifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSPProvidedID() {
		return sPProvidedID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSPProvidedID(String newSPProvidedID) {
		String oldSPProvidedID = sPProvidedID;
		sPProvidedID = newSPProvidedID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.NAME_ID_TYPE__SP_PROVIDED_ID, oldSPProvidedID, sPProvidedID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AssertionPackage.NAME_ID_TYPE__VALUE:
				return getValue();
			case AssertionPackage.NAME_ID_TYPE__FORMAT:
				return getFormat();
			case AssertionPackage.NAME_ID_TYPE__NAME_QUALIFIER:
				return getNameQualifier();
			case AssertionPackage.NAME_ID_TYPE__SP_NAME_QUALIFIER:
				return getSPNameQualifier();
			case AssertionPackage.NAME_ID_TYPE__SP_PROVIDED_ID:
				return getSPProvidedID();
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
			case AssertionPackage.NAME_ID_TYPE__VALUE:
				setValue((String)newValue);
				return;
			case AssertionPackage.NAME_ID_TYPE__FORMAT:
				setFormat((String)newValue);
				return;
			case AssertionPackage.NAME_ID_TYPE__NAME_QUALIFIER:
				setNameQualifier((String)newValue);
				return;
			case AssertionPackage.NAME_ID_TYPE__SP_NAME_QUALIFIER:
				setSPNameQualifier((String)newValue);
				return;
			case AssertionPackage.NAME_ID_TYPE__SP_PROVIDED_ID:
				setSPProvidedID((String)newValue);
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
			case AssertionPackage.NAME_ID_TYPE__VALUE:
				setValue(VALUE_EDEFAULT);
				return;
			case AssertionPackage.NAME_ID_TYPE__FORMAT:
				setFormat(FORMAT_EDEFAULT);
				return;
			case AssertionPackage.NAME_ID_TYPE__NAME_QUALIFIER:
				setNameQualifier(NAME_QUALIFIER_EDEFAULT);
				return;
			case AssertionPackage.NAME_ID_TYPE__SP_NAME_QUALIFIER:
				setSPNameQualifier(SP_NAME_QUALIFIER_EDEFAULT);
				return;
			case AssertionPackage.NAME_ID_TYPE__SP_PROVIDED_ID:
				setSPProvidedID(SP_PROVIDED_ID_EDEFAULT);
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
			case AssertionPackage.NAME_ID_TYPE__VALUE:
				return VALUE_EDEFAULT == null ? value != null : !VALUE_EDEFAULT.equals(value);
			case AssertionPackage.NAME_ID_TYPE__FORMAT:
				return FORMAT_EDEFAULT == null ? format != null : !FORMAT_EDEFAULT.equals(format);
			case AssertionPackage.NAME_ID_TYPE__NAME_QUALIFIER:
				return NAME_QUALIFIER_EDEFAULT == null ? nameQualifier != null : !NAME_QUALIFIER_EDEFAULT.equals(nameQualifier);
			case AssertionPackage.NAME_ID_TYPE__SP_NAME_QUALIFIER:
				return SP_NAME_QUALIFIER_EDEFAULT == null ? sPNameQualifier != null : !SP_NAME_QUALIFIER_EDEFAULT.equals(sPNameQualifier);
			case AssertionPackage.NAME_ID_TYPE__SP_PROVIDED_ID:
				return SP_PROVIDED_ID_EDEFAULT == null ? sPProvidedID != null : !SP_PROVIDED_ID_EDEFAULT.equals(sPProvidedID);
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
		result.append(" (value: ");
		result.append(value);
		result.append(", format: ");
		result.append(format);
		result.append(", nameQualifier: ");
		result.append(nameQualifier);
		result.append(", sPNameQualifier: ");
		result.append(sPNameQualifier);
		result.append(", sPProvidedID: ");
		result.append(sPProvidedID);
		result.append(')');
		return result.toString();
	}

} //NameIDTypeImpl
