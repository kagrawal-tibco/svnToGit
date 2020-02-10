/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata.impl;

import java.util.Collection;

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

import com.tibco.be.baas.security.authn.saml.model.metadata.AdditionalMetadataLocationType;
import com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.AttributeAuthorityDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.AuthnAuthorityDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.ContactType;
import com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.ExtensionsType;
import com.tibco.be.baas.security.authn.saml.model.metadata.IDPSSODescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;
import com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType;
import com.tibco.be.baas.security.authn.saml.model.metadata.PDPDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.RoleDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Entity Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl#getRoleDescriptor <em>Role Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl#getIDPSSODescriptor <em>IDPSSO Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl#getSPSSODescriptor <em>SPSSO Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl#getAuthnAuthorityDescriptor <em>Authn Authority Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl#getAttributeAuthorityDescriptor <em>Attribute Authority Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl#getPDPDescriptor <em>PDP Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl#getAffiliationDescriptor <em>Affiliation Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl#getOrganization <em>Organization</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl#getContactPerson <em>Contact Person</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl#getAdditionalMetadataLocation <em>Additional Metadata Location</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl#getCacheDuration <em>Cache Duration</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl#getEntityID <em>Entity ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl#getID <em>ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl#getValidUntil <em>Valid Until</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntityDescriptorTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EntityDescriptorTypeImpl extends EObjectImpl implements EntityDescriptorType {
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
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap group;

	/**
	 * The cached value of the '{@link #getAffiliationDescriptor() <em>Affiliation Descriptor</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAffiliationDescriptor()
	 * @generated
	 * @ordered
	 */
	protected AffiliationDescriptorType affiliationDescriptor;

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
	 * The cached value of the '{@link #getAdditionalMetadataLocation() <em>Additional Metadata Location</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdditionalMetadataLocation()
	 * @generated
	 * @ordered
	 */
	protected EList<AdditionalMetadataLocationType> additionalMetadataLocation;

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
	 * The default value of the '{@link #getEntityID() <em>Entity ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityID()
	 * @generated
	 * @ordered
	 */
	protected static final String ENTITY_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEntityID() <em>Entity ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityID()
	 * @generated
	 * @ordered
	 */
	protected String entityID = ENTITY_ID_EDEFAULT;

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
	protected EntityDescriptorTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.ENTITY_DESCRIPTOR_TYPE;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MetadataPackage.ENTITY_DESCRIPTOR_TYPE__EXTENSIONS, oldExtensions, newExtensions);
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
				msgs = ((InternalEObject)extensions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.ENTITY_DESCRIPTOR_TYPE__EXTENSIONS, null, msgs);
			if (newExtensions != null)
				msgs = ((InternalEObject)newExtensions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.ENTITY_DESCRIPTOR_TYPE__EXTENSIONS, null, msgs);
			msgs = basicSetExtensions(newExtensions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ENTITY_DESCRIPTOR_TYPE__EXTENSIONS, newExtensions, newExtensions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, MetadataPackage.ENTITY_DESCRIPTOR_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RoleDescriptorType> getRoleDescriptor() {
		return getGroup().list(MetadataPackage.Literals.ENTITY_DESCRIPTOR_TYPE__ROLE_DESCRIPTOR);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IDPSSODescriptorType> getIDPSSODescriptor() {
		return getGroup().list(MetadataPackage.Literals.ENTITY_DESCRIPTOR_TYPE__IDPSSO_DESCRIPTOR);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SPSSODescriptorType> getSPSSODescriptor() {
		return getGroup().list(MetadataPackage.Literals.ENTITY_DESCRIPTOR_TYPE__SPSSO_DESCRIPTOR);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AuthnAuthorityDescriptorType> getAuthnAuthorityDescriptor() {
		return getGroup().list(MetadataPackage.Literals.ENTITY_DESCRIPTOR_TYPE__AUTHN_AUTHORITY_DESCRIPTOR);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AttributeAuthorityDescriptorType> getAttributeAuthorityDescriptor() {
		return getGroup().list(MetadataPackage.Literals.ENTITY_DESCRIPTOR_TYPE__ATTRIBUTE_AUTHORITY_DESCRIPTOR);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PDPDescriptorType> getPDPDescriptor() {
		return getGroup().list(MetadataPackage.Literals.ENTITY_DESCRIPTOR_TYPE__PDP_DESCRIPTOR);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AffiliationDescriptorType getAffiliationDescriptor() {
		return affiliationDescriptor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAffiliationDescriptor(AffiliationDescriptorType newAffiliationDescriptor, NotificationChain msgs) {
		AffiliationDescriptorType oldAffiliationDescriptor = affiliationDescriptor;
		affiliationDescriptor = newAffiliationDescriptor;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MetadataPackage.ENTITY_DESCRIPTOR_TYPE__AFFILIATION_DESCRIPTOR, oldAffiliationDescriptor, newAffiliationDescriptor);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAffiliationDescriptor(AffiliationDescriptorType newAffiliationDescriptor) {
		if (newAffiliationDescriptor != affiliationDescriptor) {
			NotificationChain msgs = null;
			if (affiliationDescriptor != null)
				msgs = ((InternalEObject)affiliationDescriptor).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.ENTITY_DESCRIPTOR_TYPE__AFFILIATION_DESCRIPTOR, null, msgs);
			if (newAffiliationDescriptor != null)
				msgs = ((InternalEObject)newAffiliationDescriptor).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.ENTITY_DESCRIPTOR_TYPE__AFFILIATION_DESCRIPTOR, null, msgs);
			msgs = basicSetAffiliationDescriptor(newAffiliationDescriptor, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ENTITY_DESCRIPTOR_TYPE__AFFILIATION_DESCRIPTOR, newAffiliationDescriptor, newAffiliationDescriptor));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ORGANIZATION, oldOrganization, newOrganization);
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
				msgs = ((InternalEObject)organization).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ORGANIZATION, null, msgs);
			if (newOrganization != null)
				msgs = ((InternalEObject)newOrganization).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ORGANIZATION, null, msgs);
			msgs = basicSetOrganization(newOrganization, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ORGANIZATION, newOrganization, newOrganization));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ContactType> getContactPerson() {
		if (contactPerson == null) {
			contactPerson = new EObjectContainmentEList<ContactType>(ContactType.class, this, MetadataPackage.ENTITY_DESCRIPTOR_TYPE__CONTACT_PERSON);
		}
		return contactPerson;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AdditionalMetadataLocationType> getAdditionalMetadataLocation() {
		if (additionalMetadataLocation == null) {
			additionalMetadataLocation = new EObjectContainmentEList<AdditionalMetadataLocationType>(AdditionalMetadataLocationType.class, this, MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ADDITIONAL_METADATA_LOCATION);
		}
		return additionalMetadataLocation;
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
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ENTITY_DESCRIPTOR_TYPE__CACHE_DURATION, oldCacheDuration, cacheDuration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEntityID() {
		return entityID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEntityID(String newEntityID) {
		String oldEntityID = entityID;
		entityID = newEntityID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ENTITY_ID, oldEntityID, entityID));
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
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ID, oldID, iD));
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
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ENTITY_DESCRIPTOR_TYPE__VALID_UNTIL, oldValidUntil, validUntil));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ANY_ATTRIBUTE);
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
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__EXTENSIONS:
				return basicSetExtensions(null, msgs);
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__GROUP:
				return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ROLE_DESCRIPTOR:
				return ((InternalEList<?>)getRoleDescriptor()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__IDPSSO_DESCRIPTOR:
				return ((InternalEList<?>)getIDPSSODescriptor()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__SPSSO_DESCRIPTOR:
				return ((InternalEList<?>)getSPSSODescriptor()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__AUTHN_AUTHORITY_DESCRIPTOR:
				return ((InternalEList<?>)getAuthnAuthorityDescriptor()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ATTRIBUTE_AUTHORITY_DESCRIPTOR:
				return ((InternalEList<?>)getAttributeAuthorityDescriptor()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__PDP_DESCRIPTOR:
				return ((InternalEList<?>)getPDPDescriptor()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__AFFILIATION_DESCRIPTOR:
				return basicSetAffiliationDescriptor(null, msgs);
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ORGANIZATION:
				return basicSetOrganization(null, msgs);
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__CONTACT_PERSON:
				return ((InternalEList<?>)getContactPerson()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ADDITIONAL_METADATA_LOCATION:
				return ((InternalEList<?>)getAdditionalMetadataLocation()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__EXTENSIONS:
				return getExtensions();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ROLE_DESCRIPTOR:
				return getRoleDescriptor();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__IDPSSO_DESCRIPTOR:
				return getIDPSSODescriptor();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__SPSSO_DESCRIPTOR:
				return getSPSSODescriptor();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__AUTHN_AUTHORITY_DESCRIPTOR:
				return getAuthnAuthorityDescriptor();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ATTRIBUTE_AUTHORITY_DESCRIPTOR:
				return getAttributeAuthorityDescriptor();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__PDP_DESCRIPTOR:
				return getPDPDescriptor();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__AFFILIATION_DESCRIPTOR:
				return getAffiliationDescriptor();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ORGANIZATION:
				return getOrganization();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__CONTACT_PERSON:
				return getContactPerson();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ADDITIONAL_METADATA_LOCATION:
				return getAdditionalMetadataLocation();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__CACHE_DURATION:
				return getCacheDuration();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ENTITY_ID:
				return getEntityID();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ID:
				return getID();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__VALID_UNTIL:
				return getValidUntil();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__EXTENSIONS:
				setExtensions((ExtensionsType)newValue);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ROLE_DESCRIPTOR:
				getRoleDescriptor().clear();
				getRoleDescriptor().addAll((Collection<? extends RoleDescriptorType>)newValue);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__IDPSSO_DESCRIPTOR:
				getIDPSSODescriptor().clear();
				getIDPSSODescriptor().addAll((Collection<? extends IDPSSODescriptorType>)newValue);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__SPSSO_DESCRIPTOR:
				getSPSSODescriptor().clear();
				getSPSSODescriptor().addAll((Collection<? extends SPSSODescriptorType>)newValue);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__AUTHN_AUTHORITY_DESCRIPTOR:
				getAuthnAuthorityDescriptor().clear();
				getAuthnAuthorityDescriptor().addAll((Collection<? extends AuthnAuthorityDescriptorType>)newValue);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ATTRIBUTE_AUTHORITY_DESCRIPTOR:
				getAttributeAuthorityDescriptor().clear();
				getAttributeAuthorityDescriptor().addAll((Collection<? extends AttributeAuthorityDescriptorType>)newValue);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__PDP_DESCRIPTOR:
				getPDPDescriptor().clear();
				getPDPDescriptor().addAll((Collection<? extends PDPDescriptorType>)newValue);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__AFFILIATION_DESCRIPTOR:
				setAffiliationDescriptor((AffiliationDescriptorType)newValue);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ORGANIZATION:
				setOrganization((OrganizationType)newValue);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__CONTACT_PERSON:
				getContactPerson().clear();
				getContactPerson().addAll((Collection<? extends ContactType>)newValue);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ADDITIONAL_METADATA_LOCATION:
				getAdditionalMetadataLocation().clear();
				getAdditionalMetadataLocation().addAll((Collection<? extends AdditionalMetadataLocationType>)newValue);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__CACHE_DURATION:
				setCacheDuration((Duration)newValue);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ENTITY_ID:
				setEntityID((String)newValue);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ID:
				setID((String)newValue);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__VALID_UNTIL:
				setValidUntil((XMLGregorianCalendar)newValue);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__EXTENSIONS:
				setExtensions((ExtensionsType)null);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__GROUP:
				getGroup().clear();
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ROLE_DESCRIPTOR:
				getRoleDescriptor().clear();
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__IDPSSO_DESCRIPTOR:
				getIDPSSODescriptor().clear();
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__SPSSO_DESCRIPTOR:
				getSPSSODescriptor().clear();
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__AUTHN_AUTHORITY_DESCRIPTOR:
				getAuthnAuthorityDescriptor().clear();
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ATTRIBUTE_AUTHORITY_DESCRIPTOR:
				getAttributeAuthorityDescriptor().clear();
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__PDP_DESCRIPTOR:
				getPDPDescriptor().clear();
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__AFFILIATION_DESCRIPTOR:
				setAffiliationDescriptor((AffiliationDescriptorType)null);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ORGANIZATION:
				setOrganization((OrganizationType)null);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__CONTACT_PERSON:
				getContactPerson().clear();
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ADDITIONAL_METADATA_LOCATION:
				getAdditionalMetadataLocation().clear();
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__CACHE_DURATION:
				setCacheDuration(CACHE_DURATION_EDEFAULT);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ENTITY_ID:
				setEntityID(ENTITY_ID_EDEFAULT);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ID:
				setID(ID_EDEFAULT);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__VALID_UNTIL:
				setValidUntil(VALID_UNTIL_EDEFAULT);
				return;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__EXTENSIONS:
				return extensions != null;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ROLE_DESCRIPTOR:
				return !getRoleDescriptor().isEmpty();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__IDPSSO_DESCRIPTOR:
				return !getIDPSSODescriptor().isEmpty();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__SPSSO_DESCRIPTOR:
				return !getSPSSODescriptor().isEmpty();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__AUTHN_AUTHORITY_DESCRIPTOR:
				return !getAuthnAuthorityDescriptor().isEmpty();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ATTRIBUTE_AUTHORITY_DESCRIPTOR:
				return !getAttributeAuthorityDescriptor().isEmpty();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__PDP_DESCRIPTOR:
				return !getPDPDescriptor().isEmpty();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__AFFILIATION_DESCRIPTOR:
				return affiliationDescriptor != null;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ORGANIZATION:
				return organization != null;
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__CONTACT_PERSON:
				return contactPerson != null && !contactPerson.isEmpty();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ADDITIONAL_METADATA_LOCATION:
				return additionalMetadataLocation != null && !additionalMetadataLocation.isEmpty();
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__CACHE_DURATION:
				return CACHE_DURATION_EDEFAULT == null ? cacheDuration != null : !CACHE_DURATION_EDEFAULT.equals(cacheDuration);
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ENTITY_ID:
				return ENTITY_ID_EDEFAULT == null ? entityID != null : !ENTITY_ID_EDEFAULT.equals(entityID);
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ID:
				return ID_EDEFAULT == null ? iD != null : !ID_EDEFAULT.equals(iD);
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__VALID_UNTIL:
				return VALID_UNTIL_EDEFAULT == null ? validUntil != null : !VALID_UNTIL_EDEFAULT.equals(validUntil);
			case MetadataPackage.ENTITY_DESCRIPTOR_TYPE__ANY_ATTRIBUTE:
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
		result.append(" (group: ");
		result.append(group);
		result.append(", cacheDuration: ");
		result.append(cacheDuration);
		result.append(", entityID: ");
		result.append(entityID);
		result.append(", iD: ");
		result.append(iD);
		result.append(", validUntil: ");
		result.append(validUntil);
		result.append(", anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //EntityDescriptorTypeImpl
