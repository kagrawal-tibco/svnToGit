/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.metadata.ContactType;
import com.tibco.be.baas.security.authn.saml.model.metadata.ContactTypeType;
import com.tibco.be.baas.security.authn.saml.model.metadata.ExtensionsType;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Contact Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.ContactTypeImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.ContactTypeImpl#getCompany <em>Company</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.ContactTypeImpl#getGivenName <em>Given Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.ContactTypeImpl#getSurName <em>Sur Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.ContactTypeImpl#getEmailAddress <em>Email Address</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.ContactTypeImpl#getTelephoneNumber <em>Telephone Number</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.ContactTypeImpl#getContactType <em>Contact Type</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.ContactTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ContactTypeImpl extends EObjectImpl implements ContactType {
	/**
	 * The cached value of the '{@link #getExtensions() <em>Extensions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtensions()
	 * @generated
	 * @ordered
	 */
	protected ExtensionsType extensions;

	/**
	 * The default value of the '{@link #getCompany() <em>Company</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompany()
	 * @generated
	 * @ordered
	 */
	protected static final String COMPANY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCompany() <em>Company</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompany()
	 * @generated
	 * @ordered
	 */
	protected String company = COMPANY_EDEFAULT;

	/**
	 * The default value of the '{@link #getGivenName() <em>Given Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGivenName()
	 * @generated
	 * @ordered
	 */
	protected static final String GIVEN_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGivenName() <em>Given Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGivenName()
	 * @generated
	 * @ordered
	 */
	protected String givenName = GIVEN_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSurName() <em>Sur Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSurName()
	 * @generated
	 * @ordered
	 */
	protected static final String SUR_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSurName() <em>Sur Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSurName()
	 * @generated
	 * @ordered
	 */
	protected String surName = SUR_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEmailAddress() <em>Email Address</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmailAddress()
	 * @generated
	 * @ordered
	 */
	protected EList<String> emailAddress;

	/**
	 * The cached value of the '{@link #getTelephoneNumber() <em>Telephone Number</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTelephoneNumber()
	 * @generated
	 * @ordered
	 */
	protected EList<String> telephoneNumber;

	/**
	 * The default value of the '{@link #getContactType() <em>Contact Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContactType()
	 * @generated
	 * @ordered
	 */
	protected static final ContactTypeType CONTACT_TYPE_EDEFAULT = ContactTypeType.TECHNICAL;

	/**
	 * The cached value of the '{@link #getContactType() <em>Contact Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContactType()
	 * @generated
	 * @ordered
	 */
	protected ContactTypeType contactType = CONTACT_TYPE_EDEFAULT;

	/**
	 * This is true if the Contact Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean contactTypeESet;

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
	protected ContactTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.CONTACT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExtensionsType getExtensions() {
		return extensions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExtensions(ExtensionsType newExtensions, NotificationChain msgs) {
		ExtensionsType oldExtensions = extensions;
		extensions = newExtensions;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MetadataPackage.CONTACT_TYPE__EXTENSIONS, oldExtensions, newExtensions);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtensions(ExtensionsType newExtensions) {
		if (newExtensions != extensions) {
			NotificationChain msgs = null;
			if (extensions != null)
				msgs = ((InternalEObject)extensions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.CONTACT_TYPE__EXTENSIONS, null, msgs);
			if (newExtensions != null)
				msgs = ((InternalEObject)newExtensions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.CONTACT_TYPE__EXTENSIONS, null, msgs);
			msgs = basicSetExtensions(newExtensions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.CONTACT_TYPE__EXTENSIONS, newExtensions, newExtensions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompany(String newCompany) {
		String oldCompany = company;
		company = newCompany;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.CONTACT_TYPE__COMPANY, oldCompany, company));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGivenName() {
		return givenName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGivenName(String newGivenName) {
		String oldGivenName = givenName;
		givenName = newGivenName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.CONTACT_TYPE__GIVEN_NAME, oldGivenName, givenName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSurName() {
		return surName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSurName(String newSurName) {
		String oldSurName = surName;
		surName = newSurName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.CONTACT_TYPE__SUR_NAME, oldSurName, surName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getEmailAddress() {
		if (emailAddress == null) {
			emailAddress = new EDataTypeEList<String>(String.class, this, MetadataPackage.CONTACT_TYPE__EMAIL_ADDRESS);
		}
		return emailAddress;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getTelephoneNumber() {
		if (telephoneNumber == null) {
			telephoneNumber = new EDataTypeEList<String>(String.class, this, MetadataPackage.CONTACT_TYPE__TELEPHONE_NUMBER);
		}
		return telephoneNumber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ContactTypeType getContactType() {
		return contactType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContactType(ContactTypeType newContactType) {
		ContactTypeType oldContactType = contactType;
		contactType = newContactType == null ? CONTACT_TYPE_EDEFAULT : newContactType;
		boolean oldContactTypeESet = contactTypeESet;
		contactTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.CONTACT_TYPE__CONTACT_TYPE, oldContactType, contactType, !oldContactTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetContactType() {
		ContactTypeType oldContactType = contactType;
		boolean oldContactTypeESet = contactTypeESet;
		contactType = CONTACT_TYPE_EDEFAULT;
		contactTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, MetadataPackage.CONTACT_TYPE__CONTACT_TYPE, oldContactType, CONTACT_TYPE_EDEFAULT, oldContactTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetContactType() {
		return contactTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, MetadataPackage.CONTACT_TYPE__ANY_ATTRIBUTE);
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
			case MetadataPackage.CONTACT_TYPE__EXTENSIONS:
				return basicSetExtensions(null, msgs);
			case MetadataPackage.CONTACT_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.CONTACT_TYPE__EXTENSIONS:
				return getExtensions();
			case MetadataPackage.CONTACT_TYPE__COMPANY:
				return getCompany();
			case MetadataPackage.CONTACT_TYPE__GIVEN_NAME:
				return getGivenName();
			case MetadataPackage.CONTACT_TYPE__SUR_NAME:
				return getSurName();
			case MetadataPackage.CONTACT_TYPE__EMAIL_ADDRESS:
				return getEmailAddress();
			case MetadataPackage.CONTACT_TYPE__TELEPHONE_NUMBER:
				return getTelephoneNumber();
			case MetadataPackage.CONTACT_TYPE__CONTACT_TYPE:
				return getContactType();
			case MetadataPackage.CONTACT_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.CONTACT_TYPE__EXTENSIONS:
				setExtensions((ExtensionsType)newValue);
				return;
			case MetadataPackage.CONTACT_TYPE__COMPANY:
				setCompany((String)newValue);
				return;
			case MetadataPackage.CONTACT_TYPE__GIVEN_NAME:
				setGivenName((String)newValue);
				return;
			case MetadataPackage.CONTACT_TYPE__SUR_NAME:
				setSurName((String)newValue);
				return;
			case MetadataPackage.CONTACT_TYPE__EMAIL_ADDRESS:
				getEmailAddress().clear();
				getEmailAddress().addAll((Collection<? extends String>)newValue);
				return;
			case MetadataPackage.CONTACT_TYPE__TELEPHONE_NUMBER:
				getTelephoneNumber().clear();
				getTelephoneNumber().addAll((Collection<? extends String>)newValue);
				return;
			case MetadataPackage.CONTACT_TYPE__CONTACT_TYPE:
				setContactType((ContactTypeType)newValue);
				return;
			case MetadataPackage.CONTACT_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.CONTACT_TYPE__EXTENSIONS:
				setExtensions((ExtensionsType)null);
				return;
			case MetadataPackage.CONTACT_TYPE__COMPANY:
				setCompany(COMPANY_EDEFAULT);
				return;
			case MetadataPackage.CONTACT_TYPE__GIVEN_NAME:
				setGivenName(GIVEN_NAME_EDEFAULT);
				return;
			case MetadataPackage.CONTACT_TYPE__SUR_NAME:
				setSurName(SUR_NAME_EDEFAULT);
				return;
			case MetadataPackage.CONTACT_TYPE__EMAIL_ADDRESS:
				getEmailAddress().clear();
				return;
			case MetadataPackage.CONTACT_TYPE__TELEPHONE_NUMBER:
				getTelephoneNumber().clear();
				return;
			case MetadataPackage.CONTACT_TYPE__CONTACT_TYPE:
				unsetContactType();
				return;
			case MetadataPackage.CONTACT_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.CONTACT_TYPE__EXTENSIONS:
				return extensions != null;
			case MetadataPackage.CONTACT_TYPE__COMPANY:
				return COMPANY_EDEFAULT == null ? company != null : !COMPANY_EDEFAULT.equals(company);
			case MetadataPackage.CONTACT_TYPE__GIVEN_NAME:
				return GIVEN_NAME_EDEFAULT == null ? givenName != null : !GIVEN_NAME_EDEFAULT.equals(givenName);
			case MetadataPackage.CONTACT_TYPE__SUR_NAME:
				return SUR_NAME_EDEFAULT == null ? surName != null : !SUR_NAME_EDEFAULT.equals(surName);
			case MetadataPackage.CONTACT_TYPE__EMAIL_ADDRESS:
				return emailAddress != null && !emailAddress.isEmpty();
			case MetadataPackage.CONTACT_TYPE__TELEPHONE_NUMBER:
				return telephoneNumber != null && !telephoneNumber.isEmpty();
			case MetadataPackage.CONTACT_TYPE__CONTACT_TYPE:
				return isSetContactType();
			case MetadataPackage.CONTACT_TYPE__ANY_ATTRIBUTE:
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
		result.append(" (company: ");
		result.append(company);
		result.append(", givenName: ");
		result.append(givenName);
		result.append(", surName: ");
		result.append(surName);
		result.append(", emailAddress: ");
		result.append(emailAddress);
		result.append(", telephoneNumber: ");
		result.append(telephoneNumber);
		result.append(", contactType: ");
		if (contactTypeESet) result.append(contactType); else result.append("<unset>");
		result.append(", anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //ContactTypeImpl
