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

import com.tibco.be.baas.security.authn.saml.model.protocol.AuthnQueryType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;
import com.tibco.be.baas.security.authn.saml.model.protocol.RequestedAuthnContextType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Authn Query Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnQueryTypeImpl#getRequestedAuthnContext <em>Requested Authn Context</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.AuthnQueryTypeImpl#getSessionIndex <em>Session Index</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AuthnQueryTypeImpl extends SubjectQueryAbstractTypeImpl implements AuthnQueryType {
	/**
	 * The cached value of the '{@link #getRequestedAuthnContext() <em>Requested Authn Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequestedAuthnContext()
	 * @generated
	 * @ordered
	 */
	protected RequestedAuthnContextType requestedAuthnContext;

	/**
	 * The default value of the '{@link #getSessionIndex() <em>Session Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSessionIndex()
	 * @generated
	 * @ordered
	 */
	protected static final String SESSION_INDEX_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSessionIndex() <em>Session Index</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSessionIndex()
	 * @generated
	 * @ordered
	 */
	protected String sessionIndex = SESSION_INDEX_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AuthnQueryTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.AUTHN_QUERY_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequestedAuthnContextType getRequestedAuthnContext() {
		return requestedAuthnContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRequestedAuthnContext(RequestedAuthnContextType newRequestedAuthnContext, NotificationChain msgs) {
		RequestedAuthnContextType oldRequestedAuthnContext = requestedAuthnContext;
		requestedAuthnContext = newRequestedAuthnContext;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_QUERY_TYPE__REQUESTED_AUTHN_CONTEXT, oldRequestedAuthnContext, newRequestedAuthnContext);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequestedAuthnContext(RequestedAuthnContextType newRequestedAuthnContext) {
		if (newRequestedAuthnContext != requestedAuthnContext) {
			NotificationChain msgs = null;
			if (requestedAuthnContext != null)
				msgs = ((InternalEObject)requestedAuthnContext).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.AUTHN_QUERY_TYPE__REQUESTED_AUTHN_CONTEXT, null, msgs);
			if (newRequestedAuthnContext != null)
				msgs = ((InternalEObject)newRequestedAuthnContext).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.AUTHN_QUERY_TYPE__REQUESTED_AUTHN_CONTEXT, null, msgs);
			msgs = basicSetRequestedAuthnContext(newRequestedAuthnContext, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_QUERY_TYPE__REQUESTED_AUTHN_CONTEXT, newRequestedAuthnContext, newRequestedAuthnContext));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSessionIndex() {
		return sessionIndex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSessionIndex(String newSessionIndex) {
		String oldSessionIndex = sessionIndex;
		sessionIndex = newSessionIndex;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.AUTHN_QUERY_TYPE__SESSION_INDEX, oldSessionIndex, sessionIndex));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProtocolPackage.AUTHN_QUERY_TYPE__REQUESTED_AUTHN_CONTEXT:
				return basicSetRequestedAuthnContext(null, msgs);
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
			case ProtocolPackage.AUTHN_QUERY_TYPE__REQUESTED_AUTHN_CONTEXT:
				return getRequestedAuthnContext();
			case ProtocolPackage.AUTHN_QUERY_TYPE__SESSION_INDEX:
				return getSessionIndex();
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
			case ProtocolPackage.AUTHN_QUERY_TYPE__REQUESTED_AUTHN_CONTEXT:
				setRequestedAuthnContext((RequestedAuthnContextType)newValue);
				return;
			case ProtocolPackage.AUTHN_QUERY_TYPE__SESSION_INDEX:
				setSessionIndex((String)newValue);
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
			case ProtocolPackage.AUTHN_QUERY_TYPE__REQUESTED_AUTHN_CONTEXT:
				setRequestedAuthnContext((RequestedAuthnContextType)null);
				return;
			case ProtocolPackage.AUTHN_QUERY_TYPE__SESSION_INDEX:
				setSessionIndex(SESSION_INDEX_EDEFAULT);
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
			case ProtocolPackage.AUTHN_QUERY_TYPE__REQUESTED_AUTHN_CONTEXT:
				return requestedAuthnContext != null;
			case ProtocolPackage.AUTHN_QUERY_TYPE__SESSION_INDEX:
				return SESSION_INDEX_EDEFAULT == null ? sessionIndex != null : !SESSION_INDEX_EDEFAULT.equals(sessionIndex);
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
		result.append(" (sessionIndex: ");
		result.append(sessionIndex);
		result.append(')');
		return result.toString();
	}

} //AuthnQueryTypeImpl
