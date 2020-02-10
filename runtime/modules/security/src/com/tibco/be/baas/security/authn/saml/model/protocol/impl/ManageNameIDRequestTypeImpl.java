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
import com.tibco.be.baas.security.authn.saml.model.protocol.ManageNameIDRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;
import com.tibco.be.baas.security.authn.saml.model.protocol.TerminateType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Manage Name ID Request Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ManageNameIDRequestTypeImpl#getNameID <em>Name ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ManageNameIDRequestTypeImpl#getNewID <em>New ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ManageNameIDRequestTypeImpl#getTerminate <em>Terminate</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ManageNameIDRequestTypeImpl extends RequestAbstractTypeImpl implements ManageNameIDRequestType {
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
	 * The default value of the '{@link #getNewID() <em>New ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewID()
	 * @generated
	 * @ordered
	 */
	protected static final String NEW_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNewID() <em>New ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNewID()
	 * @generated
	 * @ordered
	 */
	protected String newID = NEW_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTerminate() <em>Terminate</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTerminate()
	 * @generated
	 * @ordered
	 */
	protected TerminateType terminate;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ManageNameIDRequestTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.MANAGE_NAME_ID_REQUEST_TYPE;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__NAME_ID, oldNameID, newNameID);
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
				msgs = ((InternalEObject)nameID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__NAME_ID, null, msgs);
			if (newNameID != null)
				msgs = ((InternalEObject)newNameID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__NAME_ID, null, msgs);
			msgs = basicSetNameID(newNameID, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__NAME_ID, newNameID, newNameID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNewID() {
		return newID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewID(String newNewID) {
		String oldNewID = newID;
		newID = newNewID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__NEW_ID, oldNewID, newID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TerminateType getTerminate() {
		return terminate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTerminate(TerminateType newTerminate, NotificationChain msgs) {
		TerminateType oldTerminate = terminate;
		terminate = newTerminate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__TERMINATE, oldTerminate, newTerminate);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTerminate(TerminateType newTerminate) {
		if (newTerminate != terminate) {
			NotificationChain msgs = null;
			if (terminate != null)
				msgs = ((InternalEObject)terminate).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__TERMINATE, null, msgs);
			if (newTerminate != null)
				msgs = ((InternalEObject)newTerminate).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__TERMINATE, null, msgs);
			msgs = basicSetTerminate(newTerminate, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__TERMINATE, newTerminate, newTerminate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__NAME_ID:
				return basicSetNameID(null, msgs);
			case ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__TERMINATE:
				return basicSetTerminate(null, msgs);
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
			case ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__NAME_ID:
				return getNameID();
			case ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__NEW_ID:
				return getNewID();
			case ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__TERMINATE:
				return getTerminate();
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
			case ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__NAME_ID:
				setNameID((NameIDType)newValue);
				return;
			case ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__NEW_ID:
				setNewID((String)newValue);
				return;
			case ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__TERMINATE:
				setTerminate((TerminateType)newValue);
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
			case ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__NAME_ID:
				setNameID((NameIDType)null);
				return;
			case ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__NEW_ID:
				setNewID(NEW_ID_EDEFAULT);
				return;
			case ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__TERMINATE:
				setTerminate((TerminateType)null);
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
			case ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__NAME_ID:
				return nameID != null;
			case ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__NEW_ID:
				return NEW_ID_EDEFAULT == null ? newID != null : !NEW_ID_EDEFAULT.equals(newID);
			case ProtocolPackage.MANAGE_NAME_ID_REQUEST_TYPE__TERMINATE:
				return terminate != null;
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
		result.append(" (newID: ");
		result.append(newID);
		result.append(')');
		return result.toString();
	}

} //ManageNameIDRequestTypeImpl
