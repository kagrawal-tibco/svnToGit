/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.id.impl;

import com.tibco.be.util.config.sharedresources.id.IdPackage;
import com.tibco.be.util.config.sharedresources.id.Identity;
import com.tibco.be.util.config.sharedresources.id.Repository;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Repository</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.impl.RepositoryImpl#getIdentity <em>Identity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RepositoryImpl extends EObjectImpl implements Repository {
	/**
	 * The cached value of the '{@link #getIdentity() <em>Identity</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIdentity()
	 * @generated
	 * @ordered
	 */
	protected Identity identity;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RepositoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IdPackage.Literals.REPOSITORY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Identity getIdentity() {
		return identity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIdentity(Identity newIdentity, NotificationChain msgs) {
		Identity oldIdentity = identity;
		identity = newIdentity;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IdPackage.REPOSITORY__IDENTITY, oldIdentity, newIdentity);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIdentity(Identity newIdentity) {
		if (newIdentity != identity) {
			NotificationChain msgs = null;
			if (identity != null)
				msgs = ((InternalEObject)identity).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IdPackage.REPOSITORY__IDENTITY, null, msgs);
			if (newIdentity != null)
				msgs = ((InternalEObject)newIdentity).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IdPackage.REPOSITORY__IDENTITY, null, msgs);
			msgs = basicSetIdentity(newIdentity, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IdPackage.REPOSITORY__IDENTITY, newIdentity, newIdentity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IdPackage.REPOSITORY__IDENTITY:
				return basicSetIdentity(null, msgs);
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
			case IdPackage.REPOSITORY__IDENTITY:
				return getIdentity();
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
			case IdPackage.REPOSITORY__IDENTITY:
				setIdentity((Identity)newValue);
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
			case IdPackage.REPOSITORY__IDENTITY:
				setIdentity((Identity)null);
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
			case IdPackage.REPOSITORY__IDENTITY:
				return identity != null;
		}
		return super.eIsSet(featureID);
	}

} //RepositoryImpl
