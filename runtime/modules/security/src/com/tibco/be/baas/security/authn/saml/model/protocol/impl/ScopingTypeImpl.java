/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol.impl;

import java.math.BigInteger;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;

import com.tibco.be.baas.security.authn.saml.model.protocol.IDPListType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;
import com.tibco.be.baas.security.authn.saml.model.protocol.ScopingType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Scoping Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ScopingTypeImpl#getIDPList <em>IDP List</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ScopingTypeImpl#getRequesterID <em>Requester ID</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.ScopingTypeImpl#getProxyCount <em>Proxy Count</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScopingTypeImpl extends EObjectImpl implements ScopingType {
	/**
	 * The cached value of the '{@link #getIDPList() <em>IDP List</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIDPList()
	 * @generated
	 * @ordered
	 */
	protected IDPListType iDPList;

	/**
	 * The cached value of the '{@link #getRequesterID() <em>Requester ID</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequesterID()
	 * @generated
	 * @ordered
	 */
	protected EList<String> requesterID;

	/**
	 * The default value of the '{@link #getProxyCount() <em>Proxy Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProxyCount()
	 * @generated
	 * @ordered
	 */
	protected static final BigInteger PROXY_COUNT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProxyCount() <em>Proxy Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProxyCount()
	 * @generated
	 * @ordered
	 */
	protected BigInteger proxyCount = PROXY_COUNT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ScopingTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.SCOPING_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IDPListType getIDPList() {
		return iDPList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIDPList(IDPListType newIDPList, NotificationChain msgs) {
		IDPListType oldIDPList = iDPList;
		iDPList = newIDPList;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ProtocolPackage.SCOPING_TYPE__IDP_LIST, oldIDPList, newIDPList);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIDPList(IDPListType newIDPList) {
		if (newIDPList != iDPList) {
			NotificationChain msgs = null;
			if (iDPList != null)
				msgs = ((InternalEObject)iDPList).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.SCOPING_TYPE__IDP_LIST, null, msgs);
			if (newIDPList != null)
				msgs = ((InternalEObject)newIDPList).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ProtocolPackage.SCOPING_TYPE__IDP_LIST, null, msgs);
			msgs = basicSetIDPList(newIDPList, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.SCOPING_TYPE__IDP_LIST, newIDPList, newIDPList));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getRequesterID() {
		if (requesterID == null) {
			requesterID = new EDataTypeEList<String>(String.class, this, ProtocolPackage.SCOPING_TYPE__REQUESTER_ID);
		}
		return requesterID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigInteger getProxyCount() {
		return proxyCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProxyCount(BigInteger newProxyCount) {
		BigInteger oldProxyCount = proxyCount;
		proxyCount = newProxyCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.SCOPING_TYPE__PROXY_COUNT, oldProxyCount, proxyCount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProtocolPackage.SCOPING_TYPE__IDP_LIST:
				return basicSetIDPList(null, msgs);
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
			case ProtocolPackage.SCOPING_TYPE__IDP_LIST:
				return getIDPList();
			case ProtocolPackage.SCOPING_TYPE__REQUESTER_ID:
				return getRequesterID();
			case ProtocolPackage.SCOPING_TYPE__PROXY_COUNT:
				return getProxyCount();
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
			case ProtocolPackage.SCOPING_TYPE__IDP_LIST:
				setIDPList((IDPListType)newValue);
				return;
			case ProtocolPackage.SCOPING_TYPE__REQUESTER_ID:
				getRequesterID().clear();
				getRequesterID().addAll((Collection<? extends String>)newValue);
				return;
			case ProtocolPackage.SCOPING_TYPE__PROXY_COUNT:
				setProxyCount((BigInteger)newValue);
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
			case ProtocolPackage.SCOPING_TYPE__IDP_LIST:
				setIDPList((IDPListType)null);
				return;
			case ProtocolPackage.SCOPING_TYPE__REQUESTER_ID:
				getRequesterID().clear();
				return;
			case ProtocolPackage.SCOPING_TYPE__PROXY_COUNT:
				setProxyCount(PROXY_COUNT_EDEFAULT);
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
			case ProtocolPackage.SCOPING_TYPE__IDP_LIST:
				return iDPList != null;
			case ProtocolPackage.SCOPING_TYPE__REQUESTER_ID:
				return requesterID != null && !requesterID.isEmpty();
			case ProtocolPackage.SCOPING_TYPE__PROXY_COUNT:
				return PROXY_COUNT_EDEFAULT == null ? proxyCount != null : !PROXY_COUNT_EDEFAULT.equals(proxyCount);
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
		result.append(" (requesterID: ");
		result.append(requesterID);
		result.append(", proxyCount: ");
		result.append(proxyCount);
		result.append(')');
		return result.toString();
	}

} //ScopingTypeImpl
