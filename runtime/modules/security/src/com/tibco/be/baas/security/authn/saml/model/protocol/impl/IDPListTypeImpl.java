/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.baas.security.authn.saml.model.protocol.impl;

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

import com.tibco.be.baas.security.authn.saml.model.protocol.IDPEntryType;
import com.tibco.be.baas.security.authn.saml.model.protocol.IDPListType;
import com.tibco.be.baas.security.authn.saml.model.protocol.ProtocolPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>IDP List Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.IDPListTypeImpl#getIDPEntry <em>IDP Entry</em>}</li>
 *   <li>{@link com.tibco.be.baas.security.authn.saml.model.protocol.impl.IDPListTypeImpl#getGetComplete <em>Get Complete</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IDPListTypeImpl extends EObjectImpl implements IDPListType {
	/**
	 * The cached value of the '{@link #getIDPEntry() <em>IDP Entry</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIDPEntry()
	 * @generated
	 * @ordered
	 */
	protected EList<IDPEntryType> iDPEntry;

	/**
	 * The default value of the '{@link #getGetComplete() <em>Get Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGetComplete()
	 * @generated
	 * @ordered
	 */
	protected static final String GET_COMPLETE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGetComplete() <em>Get Complete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGetComplete()
	 * @generated
	 * @ordered
	 */
	protected String getComplete = GET_COMPLETE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IDPListTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProtocolPackage.Literals.IDP_LIST_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IDPEntryType> getIDPEntry() {
		if (iDPEntry == null) {
			iDPEntry = new EObjectContainmentEList<IDPEntryType>(IDPEntryType.class, this, ProtocolPackage.IDP_LIST_TYPE__IDP_ENTRY);
		}
		return iDPEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGetComplete() {
		return getComplete;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGetComplete(String newGetComplete) {
		String oldGetComplete = getComplete;
		getComplete = newGetComplete;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ProtocolPackage.IDP_LIST_TYPE__GET_COMPLETE, oldGetComplete, getComplete));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ProtocolPackage.IDP_LIST_TYPE__IDP_ENTRY:
				return ((InternalEList<?>)getIDPEntry()).basicRemove(otherEnd, msgs);
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
			case ProtocolPackage.IDP_LIST_TYPE__IDP_ENTRY:
				return getIDPEntry();
			case ProtocolPackage.IDP_LIST_TYPE__GET_COMPLETE:
				return getGetComplete();
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
			case ProtocolPackage.IDP_LIST_TYPE__IDP_ENTRY:
				getIDPEntry().clear();
				getIDPEntry().addAll((Collection<? extends IDPEntryType>)newValue);
				return;
			case ProtocolPackage.IDP_LIST_TYPE__GET_COMPLETE:
				setGetComplete((String)newValue);
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
			case ProtocolPackage.IDP_LIST_TYPE__IDP_ENTRY:
				getIDPEntry().clear();
				return;
			case ProtocolPackage.IDP_LIST_TYPE__GET_COMPLETE:
				setGetComplete(GET_COMPLETE_EDEFAULT);
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
			case ProtocolPackage.IDP_LIST_TYPE__IDP_ENTRY:
				return iDPEntry != null && !iDPEntry.isEmpty();
			case ProtocolPackage.IDP_LIST_TYPE__GET_COMPLETE:
				return GET_COMPLETE_EDEFAULT == null ? getComplete != null : !GET_COMPLETE_EDEFAULT.equals(getComplete);
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
		result.append(" (getComplete: ");
		result.append(getComplete);
		result.append(')');
		return result.toString();
	}

} //IDPListTypeImpl
