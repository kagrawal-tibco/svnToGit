/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata.impl;

import java.util.Collection;
import java.util.List;

import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.metadata.ContactType;
import com.tibco.be.baas.security.authn.saml.model.metadata.ExtensionsType;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;
import com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType;
import com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Role Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.RoleDescriptorTypeImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.RoleDescriptorTypeImpl#getOrganization <em>Organization</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.RoleDescriptorTypeImpl#getContactPerson <em>Contact Person</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.RoleDescriptorTypeImpl#getCacheDuration <em>Cache Duration</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.RoleDescriptorTypeImpl#getErrorURL <em>Error URL</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.RoleDescriptorTypeImpl#getID <em>ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.RoleDescriptorTypeImpl#getProtocolSupportEnumeration <em>Protocol Support Enumeration</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.RoleDescriptorTypeImpl#getValidUntil <em>Valid Until</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.RoleDescriptorTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class RoleDescriptorTypeImpl extends EObjectImpl implements RoleDescriptorType {
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
	 * The cached value of the '{@link #getOrganization() <em>Organization</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrganization()
	 * @generated
	 * @ordered
	 */
	protected OrganizationType organization;

	/**
	 * The cached value of the '{@link #getContactPerson() <em>Contact Person</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContactPerson()
	 * @generated
	 * @ordered
	 */
	protected EList<ContactType> contactPerson;

	/**
	 * The default value of the '{@link #getCacheDuration() <em>Cache Duration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheDuration()
	 * @generated
	 * @ordered
	 */
	protected static final Duration CACHE_DURATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCacheDuration() <em>Cache Duration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheDuration()
	 * @generated
	 * @ordered
	 */
	protected Duration cacheDuration = CACHE_DURATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getErrorURL() <em>Error URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getErrorURL()
	 * @generated
	 * @ordered
	 */
	protected static final String ERROR_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getErrorURL() <em>Error URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getErrorURL()
	 * @generated
	 * @ordered
	 */
	protected String errorURL = ERROR_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getID() <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getID()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getID() <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getID()
	 * @generated
	 * @ordered
	 */
	protected String iD = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getProtocolSupportEnumeration() <em>Protocol Support Enumeration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProtocolSupportEnumeration()
	 * @generated
	 * @ordered
	 */
	protected static final List<String> PROTOCOL_SUPPORT_ENUMERATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProtocolSupportEnumeration() <em>Protocol Support Enumeration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProtocolSupportEnumeration()
	 * @generated
	 * @ordered
	 */
	protected List<String> protocolSupportEnumeration = PROTOCOL_SUPPORT_ENUMERATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getValidUntil() <em>Valid Until</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidUntil()
	 * @generated
	 * @ordered
	 */
	protected static final XMLGregorianCalendar VALID_UNTIL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValidUntil() <em>Valid Until</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidUntil()
	 * @generated
	 * @ordered
	 */
	protected XMLGregorianCalendar validUntil = VALID_UNTIL_EDEFAULT;

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
	protected RoleDescriptorTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.ROLE_DESCRIPTOR_TYPE;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MetadataPackage.ROLE_DESCRIPTOR_TYPE__EXTENSIONS, oldExtensions, newExtensions);
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
				msgs = ((InternalEObject)extensions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.ROLE_DESCRIPTOR_TYPE__EXTENSIONS, null, msgs);
			if (newExtensions != null)
				msgs = ((InternalEObject)newExtensions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.ROLE_DESCRIPTOR_TYPE__EXTENSIONS, null, msgs);
			msgs = basicSetExtensions(newExtensions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ROLE_DESCRIPTOR_TYPE__EXTENSIONS, newExtensions, newExtensions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrganizationType getOrganization() {
		return organization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOrganization(OrganizationType newOrganization, NotificationChain msgs) {
		OrganizationType oldOrganization = organization;
		organization = newOrganization;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MetadataPackage.ROLE_DESCRIPTOR_TYPE__ORGANIZATION, oldOrganization, newOrganization);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrganization(OrganizationType newOrganization) {
		if (newOrganization != organization) {
			NotificationChain msgs = null;
			if (organization != null)
				msgs = ((InternalEObject)organization).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.ROLE_DESCRIPTOR_TYPE__ORGANIZATION, null, msgs);
			if (newOrganization != null)
				msgs = ((InternalEObject)newOrganization).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.ROLE_DESCRIPTOR_TYPE__ORGANIZATION, null, msgs);
			msgs = basicSetOrganization(newOrganization, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ROLE_DESCRIPTOR_TYPE__ORGANIZATION, newOrganization, newOrganization));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ContactType> getContactPerson() {
		if (contactPerson == null) {
			contactPerson = new EObjectContainmentEList<ContactType>(ContactType.class, this, MetadataPackage.ROLE_DESCRIPTOR_TYPE__CONTACT_PERSON);
		}
		return contactPerson;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Duration getCacheDuration() {
		return cacheDuration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheDuration(Duration newCacheDuration) {
		Duration oldCacheDuration = cacheDuration;
		cacheDuration = newCacheDuration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ROLE_DESCRIPTOR_TYPE__CACHE_DURATION, oldCacheDuration, cacheDuration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getErrorURL() {
		return errorURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setErrorURL(String newErrorURL) {
		String oldErrorURL = errorURL;
		errorURL = newErrorURL;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ROLE_DESCRIPTOR_TYPE__ERROR_URL, oldErrorURL, errorURL));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getID() {
		return iD;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setID(String newID) {
		String oldID = iD;
		iD = newID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ROLE_DESCRIPTOR_TYPE__ID, oldID, iD));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public List<String> getProtocolSupportEnumeration() {
		return protocolSupportEnumeration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProtocolSupportEnumeration(List<String> newProtocolSupportEnumeration) {
		List<String> oldProtocolSupportEnumeration = protocolSupportEnumeration;
		protocolSupportEnumeration = newProtocolSupportEnumeration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ROLE_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION, oldProtocolSupportEnumeration, protocolSupportEnumeration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XMLGregorianCalendar getValidUntil() {
		return validUntil;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValidUntil(XMLGregorianCalendar newValidUntil) {
		XMLGregorianCalendar oldValidUntil = validUntil;
		validUntil = newValidUntil;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ROLE_DESCRIPTOR_TYPE__VALID_UNTIL, oldValidUntil, validUntil));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, MetadataPackage.ROLE_DESCRIPTOR_TYPE__ANY_ATTRIBUTE);
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
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__EXTENSIONS:
				return basicSetExtensions(null, msgs);
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ORGANIZATION:
				return basicSetOrganization(null, msgs);
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__CONTACT_PERSON:
				return ((InternalEList<?>)getContactPerson()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__EXTENSIONS:
				return getExtensions();
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ORGANIZATION:
				return getOrganization();
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__CONTACT_PERSON:
				return getContactPerson();
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__CACHE_DURATION:
				return getCacheDuration();
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ERROR_URL:
				return getErrorURL();
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ID:
				return getID();
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION:
				return getProtocolSupportEnumeration();
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__VALID_UNTIL:
				return getValidUntil();
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__EXTENSIONS:
				setExtensions((ExtensionsType)newValue);
				return;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ORGANIZATION:
				setOrganization((OrganizationType)newValue);
				return;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__CONTACT_PERSON:
				getContactPerson().clear();
				getContactPerson().addAll((Collection<? extends ContactType>)newValue);
				return;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__CACHE_DURATION:
				setCacheDuration((Duration)newValue);
				return;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ERROR_URL:
				setErrorURL((String)newValue);
				return;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ID:
				setID((String)newValue);
				return;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION:
				setProtocolSupportEnumeration((List<String>)newValue);
				return;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__VALID_UNTIL:
				setValidUntil((XMLGregorianCalendar)newValue);
				return;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__EXTENSIONS:
				setExtensions((ExtensionsType)null);
				return;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ORGANIZATION:
				setOrganization((OrganizationType)null);
				return;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__CONTACT_PERSON:
				getContactPerson().clear();
				return;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__CACHE_DURATION:
				setCacheDuration(CACHE_DURATION_EDEFAULT);
				return;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ERROR_URL:
				setErrorURL(ERROR_URL_EDEFAULT);
				return;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ID:
				setID(ID_EDEFAULT);
				return;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION:
				setProtocolSupportEnumeration(PROTOCOL_SUPPORT_ENUMERATION_EDEFAULT);
				return;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__VALID_UNTIL:
				setValidUntil(VALID_UNTIL_EDEFAULT);
				return;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__EXTENSIONS:
				return extensions != null;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ORGANIZATION:
				return organization != null;
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__CONTACT_PERSON:
				return contactPerson != null && !contactPerson.isEmpty();
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__CACHE_DURATION:
				return CACHE_DURATION_EDEFAULT == null ? cacheDuration != null : !CACHE_DURATION_EDEFAULT.equals(cacheDuration);
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ERROR_URL:
				return ERROR_URL_EDEFAULT == null ? errorURL != null : !ERROR_URL_EDEFAULT.equals(errorURL);
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ID:
				return ID_EDEFAULT == null ? iD != null : !ID_EDEFAULT.equals(iD);
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__PROTOCOL_SUPPORT_ENUMERATION:
				return PROTOCOL_SUPPORT_ENUMERATION_EDEFAULT == null ? protocolSupportEnumeration != null : !PROTOCOL_SUPPORT_ENUMERATION_EDEFAULT.equals(protocolSupportEnumeration);
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__VALID_UNTIL:
				return VALID_UNTIL_EDEFAULT == null ? validUntil != null : !VALID_UNTIL_EDEFAULT.equals(validUntil);
			case MetadataPackage.ROLE_DESCRIPTOR_TYPE__ANY_ATTRIBUTE:
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
		result.append(" (cacheDuration: ");
		result.append(cacheDuration);
		result.append(", errorURL: ");
		result.append(errorURL);
		result.append(", iD: ");
		result.append(iD);
		result.append(", protocolSupportEnumeration: ");
		result.append(protocolSupportEnumeration);
		result.append(", validUntil: ");
		result.append(validUntil);
		result.append(", anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //RoleDescriptorTypeImpl
