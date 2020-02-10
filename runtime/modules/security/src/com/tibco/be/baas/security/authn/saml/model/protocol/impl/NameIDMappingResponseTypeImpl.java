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

import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;
import com.tibco.be.baas.security.authn.saml.model.protocol.NameIDMappingResponseType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Name ID Mapping Response Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.NameIDMappingResponseTypeImpl#getNameID <em>Name ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NameIDMappingResponseTypeImpl extends StatusResponseTypeImpl implements NameIDMappingResponseType {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NameIDMappingResponseTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.NAME_ID_MAPPING_RESPONSE_TYPE;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.NAME_ID_MAPPING_RESPONSE_TYPE__NAME_ID, oldNameID, newNameID);
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
				msgs = ((InternalEObject)nameID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.NAME_ID_MAPPING_RESPONSE_TYPE__NAME_ID, null, msgs);
			if (newNameID != null)
				msgs = ((InternalEObject)newNameID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.NAME_ID_MAPPING_RESPONSE_TYPE__NAME_ID, null, msgs);
			msgs = basicSetNameID(newNameID, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.NAME_ID_MAPPING_RESPONSE_TYPE__NAME_ID, newNameID, newNameID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProtocolPackage.NAME_ID_MAPPING_RESPONSE_TYPE__NAME_ID:
				return basicSetNameID(null, msgs);
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
			case ProtocolPackage.NAME_ID_MAPPING_RESPONSE_TYPE__NAME_ID:
				return getNameID();
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
			case ProtocolPackage.NAME_ID_MAPPING_RESPONSE_TYPE__NAME_ID:
				setNameID((NameIDType)newValue);
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
			case ProtocolPackage.NAME_ID_MAPPING_RESPONSE_TYPE__NAME_ID:
				setNameID((NameIDType)null);
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
			case ProtocolPackage.NAME_ID_MAPPING_RESPONSE_TYPE__NAME_ID:
				return nameID != null;
		}
		return super.eIsSet(featureID);
	}

} //NameIDMappingResponseTypeImpl
