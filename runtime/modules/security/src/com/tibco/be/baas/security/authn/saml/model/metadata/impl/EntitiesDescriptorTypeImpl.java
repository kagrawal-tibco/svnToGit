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
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.metadata.EntitiesDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.EntityDescriptorType;
import com.tibco.be.baas.security.authn.saml.model.metadata.ExtensionsType;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Entities Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntitiesDescriptorTypeImpl#getExtensions <em>Extensions</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntitiesDescriptorTypeImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntitiesDescriptorTypeImpl#getEntityDescriptor <em>Entity Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntitiesDescriptorTypeImpl#getEntitiesDescriptor <em>Entities Descriptor</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntitiesDescriptorTypeImpl#getCacheDuration <em>Cache Duration</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntitiesDescriptorTypeImpl#getID <em>ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntitiesDescriptorTypeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EntitiesDescriptorTypeImpl#getValidUntil <em>Valid Until</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EntitiesDescriptorTypeImpl extends EObjectImpl implements EntitiesDescriptorType {
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
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EntitiesDescriptorTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.ENTITIES_DESCRIPTOR_TYPE;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__EXTENSIONS, oldExtensions, newExtensions);
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
				msgs = ((InternalEObject)extensions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__EXTENSIONS, null, msgs);
			if (newExtensions != null)
				msgs = ((InternalEObject)newExtensions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__EXTENSIONS, null, msgs);
			msgs = basicSetExtensions(newExtensions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__EXTENSIONS, newExtensions, newExtensions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EntityDescriptorType> getEntityDescriptor() {
		return getGroup().list(MetadataPackage.Literals.ENTITIES_DESCRIPTOR_TYPE__ENTITY_DESCRIPTOR);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EntitiesDescriptorType> getEntitiesDescriptor() {
		return getGroup().list(MetadataPackage.Literals.ENTITIES_DESCRIPTOR_TYPE__ENTITIES_DESCRIPTOR);
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
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__CACHE_DURATION, oldCacheDuration, cacheDuration));
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
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__ID, oldID, iD));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__VALID_UNTIL, oldValidUntil, validUntil));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__EXTENSIONS:
				return basicSetExtensions(null, msgs);
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__GROUP:
				return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__ENTITY_DESCRIPTOR:
				return ((InternalEList<?>)getEntityDescriptor()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__ENTITIES_DESCRIPTOR:
				return ((InternalEList<?>)getEntitiesDescriptor()).basicRemove(otherEnd, msgs);
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
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__EXTENSIONS:
				return getExtensions();
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__ENTITY_DESCRIPTOR:
				return getEntityDescriptor();
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__ENTITIES_DESCRIPTOR:
				return getEntitiesDescriptor();
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__CACHE_DURATION:
				return getCacheDuration();
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__ID:
				return getID();
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__NAME:
				return getName();
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__VALID_UNTIL:
				return getValidUntil();
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
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__EXTENSIONS:
				setExtensions((ExtensionsType)newValue);
				return;
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__ENTITY_DESCRIPTOR:
				getEntityDescriptor().clear();
				getEntityDescriptor().addAll((Collection<? extends EntityDescriptorType>)newValue);
				return;
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__ENTITIES_DESCRIPTOR:
				getEntitiesDescriptor().clear();
				getEntitiesDescriptor().addAll((Collection<? extends EntitiesDescriptorType>)newValue);
				return;
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__CACHE_DURATION:
				setCacheDuration((Duration)newValue);
				return;
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__ID:
				setID((String)newValue);
				return;
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__NAME:
				setName((String)newValue);
				return;
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__VALID_UNTIL:
				setValidUntil((XMLGregorianCalendar)newValue);
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
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__EXTENSIONS:
				setExtensions((ExtensionsType)null);
				return;
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__GROUP:
				getGroup().clear();
				return;
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__ENTITY_DESCRIPTOR:
				getEntityDescriptor().clear();
				return;
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__ENTITIES_DESCRIPTOR:
				getEntitiesDescriptor().clear();
				return;
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__CACHE_DURATION:
				setCacheDuration(CACHE_DURATION_EDEFAULT);
				return;
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__ID:
				setID(ID_EDEFAULT);
				return;
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__VALID_UNTIL:
				setValidUntil(VALID_UNTIL_EDEFAULT);
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
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__EXTENSIONS:
				return extensions != null;
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__GROUP:
				return group != null && !group.isEmpty();
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__ENTITY_DESCRIPTOR:
				return !getEntityDescriptor().isEmpty();
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__ENTITIES_DESCRIPTOR:
				return !getEntitiesDescriptor().isEmpty();
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__CACHE_DURATION:
				return CACHE_DURATION_EDEFAULT == null ? cacheDuration != null : !CACHE_DURATION_EDEFAULT.equals(cacheDuration);
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__ID:
				return ID_EDEFAULT == null ? iD != null : !ID_EDEFAULT.equals(iD);
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MetadataPackage.ENTITIES_DESCRIPTOR_TYPE__VALID_UNTIL:
				return VALID_UNTIL_EDEFAULT == null ? validUntil != null : !VALID_UNTIL_EDEFAULT.equals(validUntil);
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
		result.append(", iD: ");
		result.append(iD);
		result.append(", name: ");
		result.append(name);
		result.append(", validUntil: ");
		result.append(validUntil);
		result.append(')');
		return result.toString();
	}

} //EntitiesDescriptorTypeImpl
