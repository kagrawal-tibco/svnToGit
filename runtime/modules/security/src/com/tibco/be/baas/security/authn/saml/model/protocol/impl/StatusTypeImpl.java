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
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusCodeType;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusDetailType;
import com.tibco.be.baas.security.authn.saml.model.protocol.StatusType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Status Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusTypeImpl#getStatusCode <em>Status Code</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusTypeImpl#getStatusMessage <em>Status Message</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.StatusTypeImpl#getStatusDetail <em>Status Detail</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StatusTypeImpl extends EObjectImpl implements StatusType {
	/**
	 * The cached value of the '{@link #getStatusCode() <em>Status Code</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatusCode()
	 * @generated
	 * @ordered
	 */
	protected StatusCodeType statusCode;

	/**
	 * The default value of the '{@link #getStatusMessage() <em>Status Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatusMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String STATUS_MESSAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStatusMessage() <em>Status Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatusMessage()
	 * @generated
	 * @ordered
	 */
	protected String statusMessage = STATUS_MESSAGE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStatusDetail() <em>Status Detail</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatusDetail()
	 * @generated
	 * @ordered
	 */
	protected StatusDetailType statusDetail;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StatusTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.STATUS_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusCodeType getStatusCode() {
		return statusCode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStatusCode(StatusCodeType newStatusCode, NotificationChain msgs) {
		StatusCodeType oldStatusCode = statusCode;
		statusCode = newStatusCode;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.STATUS_TYPE__STATUS_CODE, oldStatusCode, newStatusCode);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatusCode(StatusCodeType newStatusCode) {
		if (newStatusCode != statusCode) {
			NotificationChain msgs = null;
			if (statusCode != null)
				msgs = ((InternalEObject)statusCode).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.STATUS_TYPE__STATUS_CODE, null, msgs);
			if (newStatusCode != null)
				msgs = ((InternalEObject)newStatusCode).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.STATUS_TYPE__STATUS_CODE, null, msgs);
			msgs = basicSetStatusCode(newStatusCode, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.STATUS_TYPE__STATUS_CODE, newStatusCode, newStatusCode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatusMessage(String newStatusMessage) {
		String oldStatusMessage = statusMessage;
		statusMessage = newStatusMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.STATUS_TYPE__STATUS_MESSAGE, oldStatusMessage, statusMessage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StatusDetailType getStatusDetail() {
		return statusDetail;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStatusDetail(StatusDetailType newStatusDetail, NotificationChain msgs) {
		StatusDetailType oldStatusDetail = statusDetail;
		statusDetail = newStatusDetail;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.STATUS_TYPE__STATUS_DETAIL, oldStatusDetail, newStatusDetail);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatusDetail(StatusDetailType newStatusDetail) {
		if (newStatusDetail != statusDetail) {
			NotificationChain msgs = null;
			if (statusDetail != null)
				msgs = ((InternalEObject)statusDetail).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.STATUS_TYPE__STATUS_DETAIL, null, msgs);
			if (newStatusDetail != null)
				msgs = ((InternalEObject)newStatusDetail).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.STATUS_TYPE__STATUS_DETAIL, null, msgs);
			msgs = basicSetStatusDetail(newStatusDetail, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.STATUS_TYPE__STATUS_DETAIL, newStatusDetail, newStatusDetail));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProtocolPackage.STATUS_TYPE__STATUS_CODE:
				return basicSetStatusCode(null, msgs);
			case ProtocolPackage.STATUS_TYPE__STATUS_DETAIL:
				return basicSetStatusDetail(null, msgs);
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
			case ProtocolPackage.STATUS_TYPE__STATUS_CODE:
				return getStatusCode();
			case ProtocolPackage.STATUS_TYPE__STATUS_MESSAGE:
				return getStatusMessage();
			case ProtocolPackage.STATUS_TYPE__STATUS_DETAIL:
				return getStatusDetail();
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
			case ProtocolPackage.STATUS_TYPE__STATUS_CODE:
				setStatusCode((StatusCodeType)newValue);
				return;
			case ProtocolPackage.STATUS_TYPE__STATUS_MESSAGE:
				setStatusMessage((String)newValue);
				return;
			case ProtocolPackage.STATUS_TYPE__STATUS_DETAIL:
				setStatusDetail((StatusDetailType)newValue);
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
			case ProtocolPackage.STATUS_TYPE__STATUS_CODE:
				setStatusCode((StatusCodeType)null);
				return;
			case ProtocolPackage.STATUS_TYPE__STATUS_MESSAGE:
				setStatusMessage(STATUS_MESSAGE_EDEFAULT);
				return;
			case ProtocolPackage.STATUS_TYPE__STATUS_DETAIL:
				setStatusDetail((StatusDetailType)null);
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
			case ProtocolPackage.STATUS_TYPE__STATUS_CODE:
				return statusCode != null;
			case ProtocolPackage.STATUS_TYPE__STATUS_MESSAGE:
				return STATUS_MESSAGE_EDEFAULT == null ? statusMessage != null : !STATUS_MESSAGE_EDEFAULT.equals(statusMessage);
			case ProtocolPackage.STATUS_TYPE__STATUS_DETAIL:
				return statusDetail != null;
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
		result.append(" (statusMessage: ");
		result.append(statusMessage);
		result.append(')');
		return result.toString();
	}

} //StatusTypeImpl
