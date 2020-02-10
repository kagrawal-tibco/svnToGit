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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType;
import com.tibco.be.baas.security.authn.saml.model.metadata.LocalizedNameType;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;
import com.tibco.be.baas.security.authn.saml.model.metadata.RequestedAttributeType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Consuming Service Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeConsumingServiceTypeImpl#getServiceName <em>Service Name</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeConsumingServiceTypeImpl#getServiceDescription <em>Service Description</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeConsumingServiceTypeImpl#getRequestedAttribute <em>Requested Attribute</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeConsumingServiceTypeImpl#getIndex <em>Index</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.AttributeConsumingServiceTypeImpl#isIsDefault <em>Is Default</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AttributeConsumingServiceTypeImpl extends EObjectImpl implements AttributeConsumingServiceType {
	/**
	 * The cached value of the '{@link #getServiceName() <em>Service Name</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServiceName()
	 * @generated
	 * @ordered
	 */
	protected EList<LocalizedNameType> serviceName;

	/**
	 * The cached value of the '{@link #getServiceDescription() <em>Service Description</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getServiceDescription()
	 * @generated
	 * @ordered
	 */
	protected EList<LocalizedNameType> serviceDescription;

	/**
	 * The cached value of the '{@link #getRequestedAttribute() <em>Requested Attribute</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequestedAttribute()
	 * @generated
	 * @ordered
	 */
	protected EList<RequestedAttributeType> requestedAttribute;

	/**
	 * The default value of the '{@link #getIndex() <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected static final int INDEX_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getIndex() <em>Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndex()
	 * @generated
	 * @ordered
	 */
	protected int index = INDEX_EDEFAULT;

	/**
	 * This is true if the Index attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean indexESet;

	/**
	 * The default value of the '{@link #isIsDefault() <em>Is Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsDefault()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_DEFAULT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isIsDefault() <em>Is Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsDefault()
	 * @generated
	 * @ordered
	 */
	protected boolean isDefault = IS_DEFAULT_EDEFAULT;

	/**
	 * This is true if the Is Default attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean isDefaultESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeConsumingServiceTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.ATTRIBUTE_CONSUMING_SERVICE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LocalizedNameType> getServiceName() {
		if (serviceName == null) {
			serviceName = new EObjectContainmentEList<LocalizedNameType>(LocalizedNameType.class, this, MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_NAME);
		}
		return serviceName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LocalizedNameType> getServiceDescription() {
		if (serviceDescription == null) {
			serviceDescription = new EObjectContainmentEList<LocalizedNameType>(LocalizedNameType.class, this, MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_DESCRIPTION);
		}
		return serviceDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RequestedAttributeType> getRequestedAttribute() {
		if (requestedAttribute == null) {
			requestedAttribute = new EObjectContainmentEList<RequestedAttributeType>(RequestedAttributeType.class, this, MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__REQUESTED_ATTRIBUTE);
		}
		return requestedAttribute;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndex(int newIndex) {
		int oldIndex = index;
		index = newIndex;
		boolean oldIndexESet = indexESet;
		indexESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__INDEX, oldIndex, index, !oldIndexESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetIndex() {
		int oldIndex = index;
		boolean oldIndexESet = indexESet;
		index = INDEX_EDEFAULT;
		indexESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__INDEX, oldIndex, INDEX_EDEFAULT, oldIndexESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetIndex() {
		return indexESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsDefault() {
		return isDefault;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsDefault(boolean newIsDefault) {
		boolean oldIsDefault = isDefault;
		isDefault = newIsDefault;
		boolean oldIsDefaultESet = isDefaultESet;
		isDefaultESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__IS_DEFAULT, oldIsDefault, isDefault, !oldIsDefaultESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetIsDefault() {
		boolean oldIsDefault = isDefault;
		boolean oldIsDefaultESet = isDefaultESet;
		isDefault = IS_DEFAULT_EDEFAULT;
		isDefaultESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__IS_DEFAULT, oldIsDefault, IS_DEFAULT_EDEFAULT, oldIsDefaultESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetIsDefault() {
		return isDefaultESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_NAME:
				return ((InternalEList<?>)getServiceName()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_DESCRIPTION:
				return ((InternalEList<?>)getServiceDescription()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__REQUESTED_ATTRIBUTE:
				return ((InternalEList<?>)getRequestedAttribute()).basicRemove(otherEnd, msgs);
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
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_NAME:
				return getServiceName();
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_DESCRIPTION:
				return getServiceDescription();
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__REQUESTED_ATTRIBUTE:
				return getRequestedAttribute();
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__INDEX:
				return new Integer(getIndex());
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__IS_DEFAULT:
				return isIsDefault() ? Boolean.TRUE : Boolean.FALSE;
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
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_NAME:
				getServiceName().clear();
				getServiceName().addAll((Collection<? extends LocalizedNameType>)newValue);
				return;
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_DESCRIPTION:
				getServiceDescription().clear();
				getServiceDescription().addAll((Collection<? extends LocalizedNameType>)newValue);
				return;
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__REQUESTED_ATTRIBUTE:
				getRequestedAttribute().clear();
				getRequestedAttribute().addAll((Collection<? extends RequestedAttributeType>)newValue);
				return;
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__INDEX:
				setIndex(((Integer)newValue).intValue());
				return;
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__IS_DEFAULT:
				setIsDefault(((Boolean)newValue).booleanValue());
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
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_NAME:
				getServiceName().clear();
				return;
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_DESCRIPTION:
				getServiceDescription().clear();
				return;
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__REQUESTED_ATTRIBUTE:
				getRequestedAttribute().clear();
				return;
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__INDEX:
				unsetIndex();
				return;
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__IS_DEFAULT:
				unsetIsDefault();
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
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_NAME:
				return serviceName != null && !serviceName.isEmpty();
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__SERVICE_DESCRIPTION:
				return serviceDescription != null && !serviceDescription.isEmpty();
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__REQUESTED_ATTRIBUTE:
				return requestedAttribute != null && !requestedAttribute.isEmpty();
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__INDEX:
				return isSetIndex();
			case MetadataPackage.ATTRIBUTE_CONSUMING_SERVICE_TYPE__IS_DEFAULT:
				return isSetIsDefault();
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
		result.append(" (index: ");
		if (indexESet) result.append(index); else result.append("<unset>");
		result.append(", isDefault: ");
		if (isDefaultESet) result.append(isDefault); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //AttributeConsumingServiceTypeImpl
