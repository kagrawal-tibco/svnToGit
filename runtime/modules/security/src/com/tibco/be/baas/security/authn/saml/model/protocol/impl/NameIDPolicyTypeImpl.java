/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Name ID Policy Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDPolicyTypeImpl#isAllowCreate <em>Allow Create</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDPolicyTypeImpl#getFormat <em>Format</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDPolicyTypeImpl#getSPNameQualifier <em>SP Name Qualifier</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NameIDPolicyTypeImpl extends EObjectImpl implements NameIDPolicyType {
	/**
	 * The default value of the '{@link #isAllowCreate() <em>Allow Create</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAllowCreate()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ALLOW_CREATE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAllowCreate() <em>Allow Create</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAllowCreate()
	 * @generated
	 * @ordered
	 */
	protected boolean allowCreate = ALLOW_CREATE_EDEFAULT;

	/**
	 * This is true if the Allow Create attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean allowCreateESet;

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
	protected NameIDPolicyTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.NAME_ID_POLICY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAllowCreate() {
		return allowCreate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAllowCreate(boolean newAllowCreate) {
		boolean oldAllowCreate = allowCreate;
		allowCreate = newAllowCreate;
		boolean oldAllowCreateESet = allowCreateESet;
		allowCreateESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.NAME_ID_POLICY_TYPE__ALLOW_CREATE, oldAllowCreate, allowCreate, !oldAllowCreateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAllowCreate() {
		boolean oldAllowCreate = allowCreate;
		boolean oldAllowCreateESet = allowCreateESet;
		allowCreate = ALLOW_CREATE_EDEFAULT;
		allowCreateESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ProtocolPackage.NAME_ID_POLICY_TYPE__ALLOW_CREATE, oldAllowCreate, ALLOW_CREATE_EDEFAULT, oldAllowCreateESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAllowCreate() {
		return allowCreateESet;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.NAME_ID_POLICY_TYPE__FORMAT, oldFormat, format));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.NAME_ID_POLICY_TYPE__SP_NAME_QUALIFIER, oldSPNameQualifier, sPNameQualifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ProtocolPackage.NAME_ID_POLICY_TYPE__ALLOW_CREATE:
				return isAllowCreate() ? Boolean.TRUE : Boolean.FALSE;
			case ProtocolPackage.NAME_ID_POLICY_TYPE__FORMAT:
				return getFormat();
			case ProtocolPackage.NAME_ID_POLICY_TYPE__SP_NAME_QUALIFIER:
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
			case ProtocolPackage.NAME_ID_POLICY_TYPE__ALLOW_CREATE:
				setAllowCreate(((Boolean)newValue).booleanValue());
				return;
			case ProtocolPackage.NAME_ID_POLICY_TYPE__FORMAT:
				setFormat((String)newValue);
				return;
			case ProtocolPackage.NAME_ID_POLICY_TYPE__SP_NAME_QUALIFIER:
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
			case ProtocolPackage.NAME_ID_POLICY_TYPE__ALLOW_CREATE:
				unsetAllowCreate();
				return;
			case ProtocolPackage.NAME_ID_POLICY_TYPE__FORMAT:
				setFormat(FORMAT_EDEFAULT);
				return;
			case ProtocolPackage.NAME_ID_POLICY_TYPE__SP_NAME_QUALIFIER:
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
			case ProtocolPackage.NAME_ID_POLICY_TYPE__ALLOW_CREATE:
				return isSetAllowCreate();
			case ProtocolPackage.NAME_ID_POLICY_TYPE__FORMAT:
				return FORMAT_EDEFAULT == null ? format != null : !FORMAT_EDEFAULT.equals(format);
			case ProtocolPackage.NAME_ID_POLICY_TYPE__SP_NAME_QUALIFIER:
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
		result.append(" (allowCreate: ");
		if (allowCreateESet) result.append(allowCreate); else result.append("<unset>");
		result.append(", format: ");
		result.append(format);
		result.append(", sPNameQualifier: ");
		result.append(sPNameQualifier);
		result.append(')');
		return result.toString();
	}

} //NameIDPolicyTypeImpl
