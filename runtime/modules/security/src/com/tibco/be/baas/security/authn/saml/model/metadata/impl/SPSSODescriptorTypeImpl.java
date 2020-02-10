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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.baas.security.authn.saml.model.metadata.AttributeConsumingServiceType;
import com.tibco.be.baas.security.authn.saml.model.metadata.IndexedEndpointType;
import com.tibco.be.baas.security.authn.saml.model.metadata.MetadataPackage;
import com.tibco.be.baas.security.authn.saml.model.metadata.SPSSODescriptorType;
import com.tibco.be.baas.security.authn.saml.model.policy.PolicyTemplateType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>SPSSO Descriptor Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.SPSSODescriptorTypeImpl#getAssertionConsumerService <em>Assertion Consumer Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.SPSSODescriptorTypeImpl#getAttributeConsumingService <em>Attribute Consuming Service</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.SPSSODescriptorTypeImpl#getAuthnPolicy <em>Authn Policy</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.SPSSODescriptorTypeImpl#isAuthnRequestsSigned <em>Authn Requests Signed</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.metadata.impl.SPSSODescriptorTypeImpl#isWantAssertionsSigned <em>Want Assertions Signed</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SPSSODescriptorTypeImpl extends SSODescriptorTypeImpl implements SPSSODescriptorType {
	/**
	 * The cached value of the '{@link #getAssertionConsumerService() <em>Assertion Consumer Service</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAssertionConsumerService()
	 * @generated
	 * @ordered
	 */
	protected EList<IndexedEndpointType> assertionConsumerService;

	/**
	 * The cached value of the '{@link #getAttributeConsumingService() <em>Attribute Consuming Service</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeConsumingService()
	 * @generated
	 * @ordered
	 */
	protected EList<AttributeConsumingServiceType> attributeConsumingService;

	/**
	 * The cached value of the '{@link #getAuthnPolicy() <em>Authn Policy</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthnPolicy()
	 * @generated
	 * @ordered
	 */
	protected EList<PolicyTemplateType> authnPolicy;

	/**
	 * The default value of the '{@link #isAuthnRequestsSigned() <em>Authn Requests Signed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAuthnRequestsSigned()
	 * @generated
	 * @ordered
	 */
	protected static final boolean AUTHN_REQUESTS_SIGNED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAuthnRequestsSigned() <em>Authn Requests Signed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAuthnRequestsSigned()
	 * @generated
	 * @ordered
	 */
	protected boolean authnRequestsSigned = AUTHN_REQUESTS_SIGNED_EDEFAULT;

	/**
	 * This is true if the Authn Requests Signed attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean authnRequestsSignedESet;

	/**
	 * The default value of the '{@link #isWantAssertionsSigned() <em>Want Assertions Signed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWantAssertionsSigned()
	 * @generated
	 * @ordered
	 */
	protected static final boolean WANT_ASSERTIONS_SIGNED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isWantAssertionsSigned() <em>Want Assertions Signed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isWantAssertionsSigned()
	 * @generated
	 * @ordered
	 */
	protected boolean wantAssertionsSigned = WANT_ASSERTIONS_SIGNED_EDEFAULT;

	/**
	 * This is true if the Want Assertions Signed attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean wantAssertionsSignedESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SPSSODescriptorTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MetadataPackage.Literals.SPSSO_DESCRIPTOR_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IndexedEndpointType> getAssertionConsumerService() {
		if (assertionConsumerService == null) {
			assertionConsumerService = new EObjectContainmentEList<IndexedEndpointType>(IndexedEndpointType.class, this, MetadataPackage.SPSSO_DESCRIPTOR_TYPE__ASSERTION_CONSUMER_SERVICE);
		}
		return assertionConsumerService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AttributeConsumingServiceType> getAttributeConsumingService() {
		if (attributeConsumingService == null) {
			attributeConsumingService = new EObjectContainmentEList<AttributeConsumingServiceType>(AttributeConsumingServiceType.class, this, MetadataPackage.SPSSO_DESCRIPTOR_TYPE__ATTRIBUTE_CONSUMING_SERVICE);
		}
		return attributeConsumingService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PolicyTemplateType> getAuthnPolicy() {
		if (authnPolicy == null) {
			authnPolicy = new EObjectContainmentEList<PolicyTemplateType>(PolicyTemplateType.class, this, MetadataPackage.SPSSO_DESCRIPTOR_TYPE__AUTHN_POLICY);
		}
		return authnPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAuthnRequestsSigned() {
		return authnRequestsSigned;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthnRequestsSigned(boolean newAuthnRequestsSigned) {
		boolean oldAuthnRequestsSigned = authnRequestsSigned;
		authnRequestsSigned = newAuthnRequestsSigned;
		boolean oldAuthnRequestsSignedESet = authnRequestsSignedESet;
		authnRequestsSignedESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.SPSSO_DESCRIPTOR_TYPE__AUTHN_REQUESTS_SIGNED, oldAuthnRequestsSigned, authnRequestsSigned, !oldAuthnRequestsSignedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAuthnRequestsSigned() {
		boolean oldAuthnRequestsSigned = authnRequestsSigned;
		boolean oldAuthnRequestsSignedESet = authnRequestsSignedESet;
		authnRequestsSigned = AUTHN_REQUESTS_SIGNED_EDEFAULT;
		authnRequestsSignedESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, MetadataPackage.SPSSO_DESCRIPTOR_TYPE__AUTHN_REQUESTS_SIGNED, oldAuthnRequestsSigned, AUTHN_REQUESTS_SIGNED_EDEFAULT, oldAuthnRequestsSignedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAuthnRequestsSigned() {
		return authnRequestsSignedESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isWantAssertionsSigned() {
		return wantAssertionsSigned;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWantAssertionsSigned(boolean newWantAssertionsSigned) {
		boolean oldWantAssertionsSigned = wantAssertionsSigned;
		wantAssertionsSigned = newWantAssertionsSigned;
		boolean oldWantAssertionsSignedESet = wantAssertionsSignedESet;
		wantAssertionsSignedESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MetadataPackage.SPSSO_DESCRIPTOR_TYPE__WANT_ASSERTIONS_SIGNED, oldWantAssertionsSigned, wantAssertionsSigned, !oldWantAssertionsSignedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetWantAssertionsSigned() {
		boolean oldWantAssertionsSigned = wantAssertionsSigned;
		boolean oldWantAssertionsSignedESet = wantAssertionsSignedESet;
		wantAssertionsSigned = WANT_ASSERTIONS_SIGNED_EDEFAULT;
		wantAssertionsSignedESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, MetadataPackage.SPSSO_DESCRIPTOR_TYPE__WANT_ASSERTIONS_SIGNED, oldWantAssertionsSigned, WANT_ASSERTIONS_SIGNED_EDEFAULT, oldWantAssertionsSignedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetWantAssertionsSigned() {
		return wantAssertionsSignedESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__ASSERTION_CONSUMER_SERVICE:
				return ((InternalEList<?>)getAssertionConsumerService()).basicRemove(otherEnd, msgs);
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__ATTRIBUTE_CONSUMING_SERVICE:
				return ((InternalEList<?>)getAttributeConsumingService()).basicRemove(otherEnd, msgs);
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__AUTHN_POLICY:
				return ((InternalEList<?>)getAuthnPolicy()).basicRemove(otherEnd, msgs);
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
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__ASSERTION_CONSUMER_SERVICE:
				return getAssertionConsumerService();
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__ATTRIBUTE_CONSUMING_SERVICE:
				return getAttributeConsumingService();
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__AUTHN_POLICY:
				return getAuthnPolicy();
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__AUTHN_REQUESTS_SIGNED:
				return isAuthnRequestsSigned() ? Boolean.TRUE : Boolean.FALSE;
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__WANT_ASSERTIONS_SIGNED:
				return isWantAssertionsSigned() ? Boolean.TRUE : Boolean.FALSE;
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
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__ASSERTION_CONSUMER_SERVICE:
				getAssertionConsumerService().clear();
				getAssertionConsumerService().addAll((Collection<? extends IndexedEndpointType>)newValue);
				return;
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__ATTRIBUTE_CONSUMING_SERVICE:
				getAttributeConsumingService().clear();
				getAttributeConsumingService().addAll((Collection<? extends AttributeConsumingServiceType>)newValue);
				return;
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__AUTHN_POLICY:
				getAuthnPolicy().clear();
				getAuthnPolicy().addAll((Collection<? extends PolicyTemplateType>)newValue);
				return;
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__AUTHN_REQUESTS_SIGNED:
				setAuthnRequestsSigned(((Boolean)newValue).booleanValue());
				return;
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__WANT_ASSERTIONS_SIGNED:
				setWantAssertionsSigned(((Boolean)newValue).booleanValue());
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
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__ASSERTION_CONSUMER_SERVICE:
				getAssertionConsumerService().clear();
				return;
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__ATTRIBUTE_CONSUMING_SERVICE:
				getAttributeConsumingService().clear();
				return;
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__AUTHN_POLICY:
				getAuthnPolicy().clear();
				return;
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__AUTHN_REQUESTS_SIGNED:
				unsetAuthnRequestsSigned();
				return;
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__WANT_ASSERTIONS_SIGNED:
				unsetWantAssertionsSigned();
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
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__ASSERTION_CONSUMER_SERVICE:
				return assertionConsumerService != null && !assertionConsumerService.isEmpty();
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__ATTRIBUTE_CONSUMING_SERVICE:
				return attributeConsumingService != null && !attributeConsumingService.isEmpty();
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__AUTHN_POLICY:
				return authnPolicy != null && !authnPolicy.isEmpty();
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__AUTHN_REQUESTS_SIGNED:
				return isSetAuthnRequestsSigned();
			case MetadataPackage.SPSSO_DESCRIPTOR_TYPE__WANT_ASSERTIONS_SIGNED:
				return isSetWantAssertionsSigned();
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
		result.append(" (authnRequestsSigned: ");
		if (authnRequestsSignedESet) result.append(authnRequestsSigned); else result.append("<unset>");
		result.append(", wantAssertionsSigned: ");
		if (wantAssertionsSignedESet) result.append(wantAssertionsSigned); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //SPSSODescriptorTypeImpl
