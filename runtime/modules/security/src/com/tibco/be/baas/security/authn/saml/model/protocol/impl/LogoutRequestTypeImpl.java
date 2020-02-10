/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol.impl;

import java.util.Collection;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;

import com.tibco.be.baas.security.authn.saml.model.assertion.BaseIDAbstractType;
import com.tibco.be.baas.security.authn.saml.model.assertion.NameIDType;
import com.tibco.be.baas.security.authn.saml.model.protocol.LogoutRequestType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Logout Request Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.LogoutRequestTypeImpl#getBaseID <em>Base ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.LogoutRequestTypeImpl#getNameID <em>Name ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.LogoutRequestTypeImpl#getSessionIndex <em>Session Index</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.LogoutRequestTypeImpl#getNotOnOrAfter <em>Not On Or After</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.LogoutRequestTypeImpl#getReason <em>Reason</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LogoutRequestTypeImpl extends RequestAbstractTypeImpl implements LogoutRequestType {
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
	 * The cached value of the '{@link #getSessionIndex() <em>Session Index</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSessionIndex()
	 * @generated
	 * @ordered
	 */
	protected EList<String> sessionIndex;

	/**
	 * The default value of the '{@link #getNotOnOrAfter() <em>Not On Or After</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotOnOrAfter()
	 * @generated
	 * @ordered
	 */
	protected static final XMLGregorianCalendar NOT_ON_OR_AFTER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNotOnOrAfter() <em>Not On Or After</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNotOnOrAfter()
	 * @generated
	 * @ordered
	 */
	protected XMLGregorianCalendar notOnOrAfter = NOT_ON_OR_AFTER_EDEFAULT;

	/**
	 * The default value of the '{@link #getReason() <em>Reason</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReason()
	 * @generated
	 * @ordered
	 */
	protected static final String REASON_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReason() <em>Reason</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReason()
	 * @generated
	 * @ordered
	 */
	protected String reason = REASON_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LogoutRequestTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.LOGOUT_REQUEST_TYPE;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.LOGOUT_REQUEST_TYPE__BASE_ID, oldBaseID, newBaseID);
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
				msgs = ((InternalEObject)baseID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.LOGOUT_REQUEST_TYPE__BASE_ID, null, msgs);
			if (newBaseID != null)
				msgs = ((InternalEObject)newBaseID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.LOGOUT_REQUEST_TYPE__BASE_ID, null, msgs);
			msgs = basicSetBaseID(newBaseID, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.LOGOUT_REQUEST_TYPE__BASE_ID, newBaseID, newBaseID));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.LOGOUT_REQUEST_TYPE__NAME_ID, oldNameID, newNameID);
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
				msgs = ((InternalEObject)nameID).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.LOGOUT_REQUEST_TYPE__NAME_ID, null, msgs);
			if (newNameID != null)
				msgs = ((InternalEObject)newNameID).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.LOGOUT_REQUEST_TYPE__NAME_ID, null, msgs);
			msgs = basicSetNameID(newNameID, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.LOGOUT_REQUEST_TYPE__NAME_ID, newNameID, newNameID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getSessionIndex() {
		if (sessionIndex == null) {
			sessionIndex = new EDataTypeEList<String>(String.class, this, ProtocolPackage.LOGOUT_REQUEST_TYPE__SESSION_INDEX);
		}
		return sessionIndex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XMLGregorianCalendar getNotOnOrAfter() {
		return notOnOrAfter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNotOnOrAfter(XMLGregorianCalendar newNotOnOrAfter) {
		XMLGregorianCalendar oldNotOnOrAfter = notOnOrAfter;
		notOnOrAfter = newNotOnOrAfter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.LOGOUT_REQUEST_TYPE__NOT_ON_OR_AFTER, oldNotOnOrAfter, notOnOrAfter));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReason(String newReason) {
		String oldReason = reason;
		reason = newReason;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.LOGOUT_REQUEST_TYPE__REASON, oldReason, reason));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__BASE_ID:
				return basicSetBaseID(null, msgs);
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__NAME_ID:
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
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__BASE_ID:
				return getBaseID();
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__NAME_ID:
				return getNameID();
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__SESSION_INDEX:
				return getSessionIndex();
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__NOT_ON_OR_AFTER:
				return getNotOnOrAfter();
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__REASON:
				return getReason();
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
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__BASE_ID:
				setBaseID((BaseIDAbstractType)newValue);
				return;
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__NAME_ID:
				setNameID((NameIDType)newValue);
				return;
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__SESSION_INDEX:
				getSessionIndex().clear();
				getSessionIndex().addAll((Collection<? extends String>)newValue);
				return;
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__NOT_ON_OR_AFTER:
				setNotOnOrAfter((XMLGregorianCalendar)newValue);
				return;
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__REASON:
				setReason((String)newValue);
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
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__BASE_ID:
				setBaseID((BaseIDAbstractType)null);
				return;
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__NAME_ID:
				setNameID((NameIDType)null);
				return;
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__SESSION_INDEX:
				getSessionIndex().clear();
				return;
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__NOT_ON_OR_AFTER:
				setNotOnOrAfter(NOT_ON_OR_AFTER_EDEFAULT);
				return;
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__REASON:
				setReason(REASON_EDEFAULT);
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
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__BASE_ID:
				return baseID != null;
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__NAME_ID:
				return nameID != null;
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__SESSION_INDEX:
				return sessionIndex != null && !sessionIndex.isEmpty();
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__NOT_ON_OR_AFTER:
				return NOT_ON_OR_AFTER_EDEFAULT == null ? notOnOrAfter != null : !NOT_ON_OR_AFTER_EDEFAULT.equals(notOnOrAfter);
			case ProtocolPackage.LOGOUT_REQUEST_TYPE__REASON:
				return REASON_EDEFAULT == null ? reason != null : !REASON_EDEFAULT.equals(reason);
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
		result.append(", notOnOrAfter: ");
		result.append(notOnOrAfter);
		result.append(", reason: ");
		result.append(reason);
		result.append(')');
		return result.toString();
	}

} //LogoutRequestTypeImpl
