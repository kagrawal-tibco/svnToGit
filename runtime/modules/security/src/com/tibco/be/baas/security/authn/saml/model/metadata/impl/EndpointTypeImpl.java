/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.metadata.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.metadata.EndpointType;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Endpoint Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EndpointTypeImpl#getAny <em>Any</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EndpointTypeImpl#getBinding <em>Binding</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EndpointTypeImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EndpointTypeImpl#getResponseLocation <em>Response Location</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.EndpointTypeImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EndpointTypeImpl extends EObjectImpl implements EndpointType {
	/**
	 * The cached value of the '{@link #getAny() <em>Any</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAny()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap any;

	/**
	 * The default value of the '{@link #getBinding() <em>Binding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBinding()
	 * @generated
	 * @ordered
	 */
	protected static final String BINDING_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getBinding() <em>Binding</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBinding()
	 * @generated
	 * @ordered
	 */
	protected String binding = BINDING_EDEFAULT;

	/**
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected static final String LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected String location = LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getResponseLocation() <em>Response Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResponseLocation()
	 * @generated
	 * @ordered
	 */
	protected static final String RESPONSE_LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResponseLocation() <em>Response Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResponseLocation()
	 * @generated
	 * @ordered
	 */
	protected String responseLocation = RESPONSE_LOCATION_EDEFAULT;

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
	protected EndpointTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.ENDPOINT_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAny() {
		if (any == null) {
			any = new BasicFeatureMap(this, MetadataPackage.ENDPOINT_TYPE__ANY);
		}
		return any;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBinding() {
		return binding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBinding(String newBinding) {
		String oldBinding = binding;
		binding = newBinding;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ENDPOINT_TYPE__BINDING, oldBinding, binding));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocation(String newLocation) {
		String oldLocation = location;
		location = newLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ENDPOINT_TYPE__LOCATION, oldLocation, location));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getResponseLocation() {
		return responseLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResponseLocation(String newResponseLocation) {
		String oldResponseLocation = responseLocation;
		responseLocation = newResponseLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.ENDPOINT_TYPE__RESPONSE_LOCATION, oldResponseLocation, responseLocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getAnyAttribute() {
		if (anyAttribute == null) {
			anyAttribute = new BasicFeatureMap(this, MetadataPackage.ENDPOINT_TYPE__ANY_ATTRIBUTE);
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
			case MetadataPackage.ENDPOINT_TYPE__ANY:
				return ((InternalEList<?>)getAny()).basicRemove(otherEnd, msgs);
			case MetadataPackage.ENDPOINT_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.ENDPOINT_TYPE__ANY:
				if (coreType) return getAny();
				return ((FeatureMap.Internal)getAny()).getWrapper();
			case MetadataPackage.ENDPOINT_TYPE__BINDING:
				return getBinding();
			case MetadataPackage.ENDPOINT_TYPE__LOCATION:
				return getLocation();
			case MetadataPackage.ENDPOINT_TYPE__RESPONSE_LOCATION:
				return getResponseLocation();
			case MetadataPackage.ENDPOINT_TYPE__ANY_ATTRIBUTE:
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
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MetadataPackage.ENDPOINT_TYPE__ANY:
				((FeatureMap.Internal)getAny()).set(newValue);
				return;
			case MetadataPackage.ENDPOINT_TYPE__BINDING:
				setBinding((String)newValue);
				return;
			case MetadataPackage.ENDPOINT_TYPE__LOCATION:
				setLocation((String)newValue);
				return;
			case MetadataPackage.ENDPOINT_TYPE__RESPONSE_LOCATION:
				setResponseLocation((String)newValue);
				return;
			case MetadataPackage.ENDPOINT_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.ENDPOINT_TYPE__ANY:
				getAny().clear();
				return;
			case MetadataPackage.ENDPOINT_TYPE__BINDING:
				setBinding(BINDING_EDEFAULT);
				return;
			case MetadataPackage.ENDPOINT_TYPE__LOCATION:
				setLocation(LOCATION_EDEFAULT);
				return;
			case MetadataPackage.ENDPOINT_TYPE__RESPONSE_LOCATION:
				setResponseLocation(RESPONSE_LOCATION_EDEFAULT);
				return;
			case MetadataPackage.ENDPOINT_TYPE__ANY_ATTRIBUTE:
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
			case MetadataPackage.ENDPOINT_TYPE__ANY:
				return any != null && !any.isEmpty();
			case MetadataPackage.ENDPOINT_TYPE__BINDING:
				return BINDING_EDEFAULT == null ? binding != null : !BINDING_EDEFAULT.equals(binding);
			case MetadataPackage.ENDPOINT_TYPE__LOCATION:
				return LOCATION_EDEFAULT == null ? location != null : !LOCATION_EDEFAULT.equals(location);
			case MetadataPackage.ENDPOINT_TYPE__RESPONSE_LOCATION:
				return RESPONSE_LOCATION_EDEFAULT == null ? responseLocation != null : !RESPONSE_LOCATION_EDEFAULT.equals(responseLocation);
			case MetadataPackage.ENDPOINT_TYPE__ANY_ATTRIBUTE:
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
		result.append(" (any: ");
		result.append(any);
		result.append(", binding: ");
		result.append(binding);
		result.append(", location: ");
		result.append(location);
		result.append(", responseLocation: ");
		result.append(responseLocation);
		result.append(", anyAttribute: ");
		result.append(anyAttribute);
		result.append(')');
		return result.toString();
	}

} //EndpointTypeImpl
