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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.metadata.ExtensionsType;
import com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedNameType;
import com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedURIType;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;
import com.tibco.be.baas.security.authn.saml.model.metadata.OrganizationType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Organization Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.OrganizationTypeImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.OrganizationTypeImpl#getOrganizationName <em>Organization Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.OrganizationTypeImpl#getOrganizationDisplayName <em>Organization Display Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.OrganizationTypeImpl#getOrganizationURL <em>Organization URL</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.OrganizationTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrganizationTypeImpl extends EObjectImpl implements OrganizationType {
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
	 * The cached value of the '{@link #getOrganizationName() <em>Organization Name</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrganizationName()
	 * @generated
	 * @ordered
	 */
	protected EList<LocalizedNameType> organizationName;

	/**
	 * The cached value of the '{@link #getOrganizationDisplayName() <em>Organization Display Name</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrganizationDisplayName()
	 * @generated
	 * @ordered
	 */
	protected EList<LocalizedNameType> organizationDisplayName;

	/**
	 * The cached value of the '{@link #getOrganizationURL() <em>Organization URL</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrganizationURL()
	 * @generated
	 * @ordered
	 */
	protected EList<LocalizedURIType> organizationURL;

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
	protected OrganizationTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.ORGANIZATION_TYPE;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MetadataPackage.ORGANIZATION_TYPE__EXTENSIONS, oldExtensions, newExtensions);
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
				msgs = ((InternalEObject)extensions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.ORGANIZATION_TYPE__EXTENSIONS, null, msgs);
			if (newExtensions != null)
				msgs = ((InternalEObject)newExtensions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.ORGANIZATION_TYPE__EXTENSIONS, null, msgs);
			msgs = basicSetExtensions(newExtensions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ORGANIZATION_TYPE__EXTENSIONS, newExtensions, newExtensions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LocalizedNameType> getOrganizationName() {
		if (organizationName == null) {
			organizationName = new EObjectContainmentEList<LocalizedNameType>(LocalizedNameType.class, this, MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_NAME);
		}
		return organizationName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LocalizedNameType> getOrganizationDisplayName() {
		if (organizationDisplayName == null) {
			organizationDisplayName = new EObjectContainmentEList<LocalizedNameType>(LocalizedNameType.class, this, MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_DISPLAY_NAME);
		}
		return organizationDisplayName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LocalizedURIType> getOrganizationURL() {
		if (organizationURL == null) {
			organizationURL = new EObjectContainmentEList<LocalizedURIType>(LocalizedURIType.class, this, MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_URL);
		}
		return organizationURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, MetadataPackage.ORGANIZATION_TYPE__ANY_ATTRIBUTE);
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
			case MetadataPackage.ORGANIZATION_TYPE__EXTENSIONS:
				return basicSetExtensions(null, msgs);
			case MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_NAME:
				return ((InternalEList<?>)getOrganizationName()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_DISPLAY_NAME:
				return ((InternalEList<?>)getOrganizationDisplayName()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_URL:
				return ((InternalEList<?>)getOrganizationURL()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ORGANIZATION_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.ORGANIZATION_TYPE__EXTENSIONS:
				return getExtensions();
			case MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_NAME:
				return getOrganizationName();
			case MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_DISPLAY_NAME:
				return getOrganizationDisplayName();
			case MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_URL:
				return getOrganizationURL();
			case MetadataPackage.ORGANIZATION_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.ORGANIZATION_TYPE__EXTENSIONS:
				setExtensions((ExtensionsType)newValue);
				return;
			case MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_NAME:
				getOrganizationName().clear();
				getOrganizationName().addAll((Collection<? extends LocalizedNameType>)newValue);
				return;
			case MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_DISPLAY_NAME:
				getOrganizationDisplayName().clear();
				getOrganizationDisplayName().addAll((Collection<? extends LocalizedNameType>)newValue);
				return;
			case MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_URL:
				getOrganizationURL().clear();
				getOrganizationURL().addAll((Collection<? extends LocalizedURIType>)newValue);
				return;
			case MetadataPackage.ORGANIZATION_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.ORGANIZATION_TYPE__EXTENSIONS:
				setExtensions((ExtensionsType)null);
				return;
			case MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_NAME:
				getOrganizationName().clear();
				return;
			case MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_DISPLAY_NAME:
				getOrganizationDisplayName().clear();
				return;
			case MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_URL:
				getOrganizationURL().clear();
				return;
			case MetadataPackage.ORGANIZATION_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.ORGANIZATION_TYPE__EXTENSIONS:
				return extensions != null;
			case MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_NAME:
				return organizationName != null && !organizationName.isEmpty();
			case MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_DISPLAY_NAME:
				return organizationDisplayName != null && !organizationDisplayName.isEmpty();
			case MetadataPackage.ORGANIZATION_TYPE__ORGANIZATION_URL:
				return organizationURL != null && !organizationURL.isEmpty();
			case MetadataPackage.ORGANIZATION_TYPE__ANY_ATTRIBUTE:
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
		result.append(" (anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //OrganizationTypeImpl
