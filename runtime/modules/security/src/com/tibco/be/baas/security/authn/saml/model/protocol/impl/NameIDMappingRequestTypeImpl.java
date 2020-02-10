/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType;
import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;
import com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.NameIDPolicyType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Name ID Mapping Request Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDMappingRequestTypeImpl#getBaseID <em>Base ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDMappingRequestTypeImpl#getNameID <em>Name ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDMappingRequestTypeImpl#getNameIDPolicy <em>Name ID Policy</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NameIDMappingRequestTypeImpl extends RequestAbstractTypeImpl implements NameIDMappingRequestType {
	/**
	 * The cached value of the '{@link #getBaseID() <em>Base ID</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseID()
	 * @generated
	 * @ordered
	 */
	protected BaseIDAbstractType baseID;

	/**
	 * The cached value of the '{@link #getNameID() <em>Name ID</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameID()
	 * @generated
	 * @ordered
	 */
	protected NameIDType nameID;

	/**
	 * The cached value of the '{@link #getNameIDPolicy() <em>Name ID Policy</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNameIDPolicy()
	 * @generated
	 * @ordered
	 */
	protected NameIDPolicyType nameIDPolicy;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NameIDMappingRequestTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.NAME_ID_MAPPING_REQUEST_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseIDAbstractType getBaseID() {
		return baseID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBaseID(BaseIDAbstractType newBaseID, NotificationChain msgs) {
		BaseIDAbstractType oldBaseID = baseID;
		baseID = newBaseID;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__BASE_ID, oldBaseID, newBaseID);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBaseID(BaseIDAbstractType newBaseID) {
		if (newBaseID != baseID) {
			NotificationChain msgs = null;
			if (baseID != null)
				msgs = ((InternalEObject)baseID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__BASE_ID, null, msgs);
			if (newBaseID != null)
				msgs = ((InternalEObject)newBaseID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__BASE_ID, null, msgs);
			msgs = basicSetBaseID(newBaseID, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__BASE_ID, newBaseID, newBaseID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameIDType getNameID() {
		return nameID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNameID(NameIDType newNameID, NotificationChain msgs) {
		NameIDType oldNameID = nameID;
		nameID = newNameID;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID, oldNameID, newNameID);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameID(NameIDType newNameID) {
		if (newNameID != nameID) {
			NotificationChain msgs = null;
			if (nameID != null)
				msgs = ((InternalEObject)nameID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID, null, msgs);
			if (newNameID != null)
				msgs = ((InternalEObject)newNameID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID, null, msgs);
			msgs = basicSetNameID(newNameID, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID, newNameID, newNameID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NameIDPolicyType getNameIDPolicy() {
		return nameIDPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNameIDPolicy(NameIDPolicyType newNameIDPolicy, NotificationChain msgs) {
		NameIDPolicyType oldNameIDPolicy = nameIDPolicy;
		nameIDPolicy = newNameIDPolicy;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID_POLICY, oldNameIDPolicy, newNameIDPolicy);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNameIDPolicy(NameIDPolicyType newNameIDPolicy) {
		if (newNameIDPolicy != nameIDPolicy) {
			NotificationChain msgs = null;
			if (nameIDPolicy != null)
				msgs = ((InternalEObject)nameIDPolicy).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID_POLICY, null, msgs);
			if (newNameIDPolicy != null)
				msgs = ((InternalEObject)newNameIDPolicy).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID_POLICY, null, msgs);
			msgs = basicSetNameIDPolicy(newNameIDPolicy, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID_POLICY, newNameIDPolicy, newNameIDPolicy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__BASE_ID:
				return basicSetBaseID(null, msgs);
			case ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID:
				return basicSetNameID(null, msgs);
			case ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID_POLICY:
				return basicSetNameIDPolicy(null, msgs);
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
			case ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__BASE_ID:
				return getBaseID();
			case ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID:
				return getNameID();
			case ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID_POLICY:
				return getNameIDPolicy();
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
			case ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__BASE_ID:
				setBaseID((BaseIDAbstractType)newValue);
				return;
			case ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID:
				setNameID((NameIDType)newValue);
				return;
			case ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID_POLICY:
				setNameIDPolicy((NameIDPolicyType)newValue);
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
			case ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__BASE_ID:
				setBaseID((BaseIDAbstractType)null);
				return;
			case ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID:
				setNameID((NameIDType)null);
				return;
			case ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID_POLICY:
				setNameIDPolicy((NameIDPolicyType)null);
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
			case ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__BASE_ID:
				return baseID != null;
			case ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID:
				return nameID != null;
			case ProtocolPackage.NAME_ID_MAPPING_REQUEST_TYPE__NAME_ID_POLICY:
				return nameIDPolicy != null;
		}
		return super.eIsSet(featureID);
	}

} //NameIDMappingRequestTypeImpl
