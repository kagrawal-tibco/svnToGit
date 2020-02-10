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
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.metadata.AffiliationDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.ExtensionsType;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Affiliation Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AffiliationDescriptorTypeImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AffiliationDescriptorTypeImpl#getAffiliateMember <em>Affiliate Member</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AffiliationDescriptorTypeImpl#getAffiliationOwnerID <em>Affiliation Owner ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AffiliationDescriptorTypeImpl#getCacheDuration <em>Cache Duration</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AffiliationDescriptorTypeImpl#getID <em>ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AffiliationDescriptorTypeImpl#getValidUntil <em>Valid Until</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AffiliationDescriptorTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AffiliationDescriptorTypeImpl extends EObjectImpl implements AffiliationDescriptorType {
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
	 * The cached value of the '{@link #getAffiliateMember() <em>Affiliate Member</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAffiliateMember()
	 * @generated
	 * @ordered
	 */
	protected EList<String> affiliateMember;

	/**
	 * The default value of the '{@link #getAffiliationOwnerID() <em>Affiliation Owner ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAffiliationOwnerID()
	 * @generated
	 * @ordered
	 */
	protected static final String AFFILIATION_OWNER_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAffiliationOwnerID() <em>Affiliation Owner ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAffiliationOwnerID()
	 * @generated
	 * @ordered
	 */
	protected String affiliationOwnerID = AFFILIATION_OWNER_ID_EDEFAULT;

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
	protected AffiliationDescriptorTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.AFFILIATION_DESCRIPTOR_TYPE;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__EXTENSIONS, oldExtensions, newExtensions);
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
				msgs = ((InternalEObject)extensions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__EXTENSIONS, null, msgs);
			if (newExtensions != null)
				msgs = ((InternalEObject)newExtensions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__EXTENSIONS, null, msgs);
			msgs = basicSetExtensions(newExtensions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__EXTENSIONS, newExtensions, newExtensions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getAffiliateMember() {
		if (affiliateMember == null) {
			affiliateMember = new EDataTypeEList<String>(String.class, this, MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__AFFILIATE_MEMBER);
		}
		return affiliateMember;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAffiliationOwnerID() {
		return affiliationOwnerID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAffiliationOwnerID(String newAffiliationOwnerID) {
		String oldAffiliationOwnerID = affiliationOwnerID;
		affiliationOwnerID = newAffiliationOwnerID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__AFFILIATION_OWNER_ID, oldAffiliationOwnerID, affiliationOwnerID));
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
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__CACHE_DURATION, oldCacheDuration, cacheDuration));
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
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__ID, oldID, iD));
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
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__VALID_UNTIL, oldValidUntil, validUntil));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__ANY_ATTRIBUTE);
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
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__EXTENSIONS:
				return basicSetExtensions(null, msgs);
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__EXTENSIONS:
				return getExtensions();
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__AFFILIATE_MEMBER:
				return getAffiliateMember();
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__AFFILIATION_OWNER_ID:
				return getAffiliationOwnerID();
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__CACHE_DURATION:
				return getCacheDuration();
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__ID:
				return getID();
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__VALID_UNTIL:
				return getValidUntil();
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__EXTENSIONS:
				setExtensions((ExtensionsType)newValue);
				return;
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__AFFILIATE_MEMBER:
				getAffiliateMember().clear();
				getAffiliateMember().addAll((Collection<? extends String>)newValue);
				return;
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__AFFILIATION_OWNER_ID:
				setAffiliationOwnerID((String)newValue);
				return;
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__CACHE_DURATION:
				setCacheDuration((Duration)newValue);
				return;
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__ID:
				setID((String)newValue);
				return;
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__VALID_UNTIL:
				setValidUntil((XMLGregorianCalendar)newValue);
				return;
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__EXTENSIONS:
				setExtensions((ExtensionsType)null);
				return;
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__AFFILIATE_MEMBER:
				getAffiliateMember().clear();
				return;
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__AFFILIATION_OWNER_ID:
				setAffiliationOwnerID(AFFILIATION_OWNER_ID_EDEFAULT);
				return;
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__CACHE_DURATION:
				setCacheDuration(CACHE_DURATION_EDEFAULT);
				return;
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__ID:
				setID(ID_EDEFAULT);
				return;
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__VALID_UNTIL:
				setValidUntil(VALID_UNTIL_EDEFAULT);
				return;
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__EXTENSIONS:
				return extensions != null;
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__AFFILIATE_MEMBER:
				return affiliateMember != null && !affiliateMember.isEmpty();
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__AFFILIATION_OWNER_ID:
				return AFFILIATION_OWNER_ID_EDEFAULT == null ? affiliationOwnerID != null : !AFFILIATION_OWNER_ID_EDEFAULT.equals(affiliationOwnerID);
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__CACHE_DURATION:
				return CACHE_DURATION_EDEFAULT == null ? cacheDuration != null : !CACHE_DURATION_EDEFAULT.equals(cacheDuration);
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__ID:
				return ID_EDEFAULT == null ? iD != null : !ID_EDEFAULT.equals(iD);
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__VALID_UNTIL:
				return VALID_UNTIL_EDEFAULT == null ? validUntil != null : !VALID_UNTIL_EDEFAULT.equals(validUntil);
			case MetadataPackage.AFFILIATION_DESCRIPTOR_TYPE__ANY_ATTRIBUTE:
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
		result.append(" (affiliateMember: ");
		result.append(affiliateMember);
		result.append(", affiliationOwnerID: ");
		result.append(affiliationOwnerID);
		result.append(", cacheDuration: ");
		result.append(cacheDuration);
		result.append(", iD: ");
		result.append(iD);
		result.append(", validUntil: ");
		result.append(validUntil);
		result.append(", anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //AffiliationDescriptorTypeImpl
