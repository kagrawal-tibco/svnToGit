/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.studio.core.index.model.EventElement;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Event Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.EventElementImpl#getExpiryActionScopeEntry <em>Expiry Action Scope Entry</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EventElementImpl extends EntityElementImpl implements EventElement {
	/**
	 * The cached value of the '{@link #getExpiryActionScopeEntry() <em>Expiry Action Scope Entry</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExpiryActionScopeEntry()
	 * @generated
	 * @ordered
	 */
	protected CompilableScopeEntry expiryActionScopeEntry;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EventElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.EVENT_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompilableScopeEntry getExpiryActionScopeEntry() {
		return expiryActionScopeEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpiryActionScopeEntry(CompilableScopeEntry newExpiryActionScopeEntry, NotificationChain msgs) {
		CompilableScopeEntry oldExpiryActionScopeEntry = expiryActionScopeEntry;
		expiryActionScopeEntry = newExpiryActionScopeEntry;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IndexPackage.EVENT_ELEMENT__EXPIRY_ACTION_SCOPE_ENTRY, oldExpiryActionScopeEntry, newExpiryActionScopeEntry);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpiryActionScopeEntry(CompilableScopeEntry newExpiryActionScopeEntry) {
		if (newExpiryActionScopeEntry != expiryActionScopeEntry) {
			NotificationChain msgs = null;
			if (expiryActionScopeEntry != null)
				msgs = ((InternalEObject)expiryActionScopeEntry).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IndexPackage.EVENT_ELEMENT__EXPIRY_ACTION_SCOPE_ENTRY, null, msgs);
			if (newExpiryActionScopeEntry != null)
				msgs = ((InternalEObject)newExpiryActionScopeEntry).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IndexPackage.EVENT_ELEMENT__EXPIRY_ACTION_SCOPE_ENTRY, null, msgs);
			msgs = basicSetExpiryActionScopeEntry(newExpiryActionScopeEntry, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.EVENT_ELEMENT__EXPIRY_ACTION_SCOPE_ENTRY, newExpiryActionScopeEntry, newExpiryActionScopeEntry));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IndexPackage.EVENT_ELEMENT__EXPIRY_ACTION_SCOPE_ENTRY:
				return basicSetExpiryActionScopeEntry(null, msgs);
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
			case IndexPackage.EVENT_ELEMENT__EXPIRY_ACTION_SCOPE_ENTRY:
				return getExpiryActionScopeEntry();
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
			case IndexPackage.EVENT_ELEMENT__EXPIRY_ACTION_SCOPE_ENTRY:
				setExpiryActionScopeEntry((CompilableScopeEntry)newValue);
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
			case IndexPackage.EVENT_ELEMENT__EXPIRY_ACTION_SCOPE_ENTRY:
				setExpiryActionScopeEntry((CompilableScopeEntry)null);
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
			case IndexPackage.EVENT_ELEMENT__EXPIRY_ACTION_SCOPE_ENTRY:
				return expiryActionScopeEntry != null;
		}
		return super.eIsSet(featureID);
	}

} //EventElementImpl
