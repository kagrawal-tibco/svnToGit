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
import com.tibco.be.baas.security.authn.saml.model.assertion.SubjectLocalityType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subject Locality Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectLocalityTypeImpl#getAddress <em>Address</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.assertion.impl.SubjectLocalityTypeImpl#getDNSName <em>DNS Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SubjectLocalityTypeImpl extends EObjectImpl implements SubjectLocalityType {
	/**
	 * The default value of the '{@link #getAddress() <em>Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddress()
	 * @generated
	 * @ordered
	 */
	protected static final String ADDRESS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAddress() <em>Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAddress()
	 * @generated
	 * @ordered
	 */
	protected String address = ADDRESS_EDEFAULT;

	/**
	 * The default value of the '{@link #getDNSName() <em>DNS Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDNSName()
	 * @generated
	 * @ordered
	 */
	protected static final String DNS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDNSName() <em>DNS Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDNSName()
	 * @generated
	 * @ordered
	 */
	protected String dNSName = DNS_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubjectLocalityTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AssertionPackage.Literals.SUBJECT_LOCALITY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddress(String newAddress) {
		String oldAddress = address;
		address = newAddress;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_LOCALITY_TYPE__ADDRESS, oldAddress, address));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDNSName() {
		return dNSName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDNSName(String newDNSName) {
		String oldDNSName = dNSName;
		dNSName = newDNSName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AssertionPackage.SUBJECT_LOCALITY_TYPE__DNS_NAME, oldDNSName, dNSName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AssertionPackage.SUBJECT_LOCALITY_TYPE__ADDRESS:
				return getAddress();
			case AssertionPackage.SUBJECT_LOCALITY_TYPE__DNS_NAME:
				return getDNSName();
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
			case AssertionPackage.SUBJECT_LOCALITY_TYPE__ADDRESS:
				setAddress((String)newValue);
				return;
			case AssertionPackage.SUBJECT_LOCALITY_TYPE__DNS_NAME:
				setDNSName((String)newValue);
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
			case AssertionPackage.SUBJECT_LOCALITY_TYPE__ADDRESS:
				setAddress(ADDRESS_EDEFAULT);
				return;
			case AssertionPackage.SUBJECT_LOCALITY_TYPE__DNS_NAME:
				setDNSName(DNS_NAME_EDEFAULT);
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
			case AssertionPackage.SUBJECT_LOCALITY_TYPE__ADDRESS:
				return ADDRESS_EDEFAULT == null ? address != null : !ADDRESS_EDEFAULT.equals(address);
			case AssertionPackage.SUBJECT_LOCALITY_TYPE__DNS_NAME:
				return DNS_NAME_EDEFAULT == null ? dNSName != null : !DNS_NAME_EDEFAULT.equals(dNSName);
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
		result.append(" (address: ");
		result.append(address);
		result.append(", dNSName: ");
		result.append(dNSName);
		result.append(')');
		return result.toString();
	}

} //SubjectLocalityTypeImpl
