/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.scope.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.scope.RootScopeBlock;
import com.tibco.cep.studio.core.index.model.scope.ScopePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Root Scope Block</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.scope.impl.RootScopeBlockImpl#getDefinitionRef <em>Definition Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RootScopeBlockImpl extends ScopeBlockImpl implements RootScopeBlock {
	/**
	 * The cached value of the '{@link #getDefinitionRef() <em>Definition Ref</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinitionRef()
	 * @generated
	 * @ordered
	 */
	protected ElementReference definitionRef;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RootScopeBlockImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScopePackage.Literals.ROOT_SCOPE_BLOCK;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementReference getDefinitionRef() {
		return definitionRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDefinitionRef(ElementReference newDefinitionRef, NotificationChain msgs) {
		ElementReference oldDefinitionRef = definitionRef;
		definitionRef = newDefinitionRef;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ScopePackage.ROOT_SCOPE_BLOCK__DEFINITION_REF, oldDefinitionRef, newDefinitionRef);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefinitionRef(ElementReference newDefinitionRef) {
		if (newDefinitionRef != definitionRef) {
			NotificationChain msgs = null;
			if (definitionRef != null)
				msgs = ((InternalEObject)definitionRef).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ScopePackage.ROOT_SCOPE_BLOCK__DEFINITION_REF, null, msgs);
			if (newDefinitionRef != null)
				msgs = ((InternalEObject)newDefinitionRef).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ScopePackage.ROOT_SCOPE_BLOCK__DEFINITION_REF, null, msgs);
			msgs = basicSetDefinitionRef(newDefinitionRef, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ScopePackage.ROOT_SCOPE_BLOCK__DEFINITION_REF, newDefinitionRef, newDefinitionRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ScopePackage.ROOT_SCOPE_BLOCK__DEFINITION_REF:
				return basicSetDefinitionRef(null, msgs);
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
			case ScopePackage.ROOT_SCOPE_BLOCK__DEFINITION_REF:
				return getDefinitionRef();
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
			case ScopePackage.ROOT_SCOPE_BLOCK__DEFINITION_REF:
				setDefinitionRef((ElementReference)newValue);
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
			case ScopePackage.ROOT_SCOPE_BLOCK__DEFINITION_REF:
				setDefinitionRef((ElementReference)null);
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
			case ScopePackage.ROOT_SCOPE_BLOCK__DEFINITION_REF:
				return definitionRef != null;
		}
		return super.eIsSet(featureID);
	}

} //RootScopeBlockImpl
