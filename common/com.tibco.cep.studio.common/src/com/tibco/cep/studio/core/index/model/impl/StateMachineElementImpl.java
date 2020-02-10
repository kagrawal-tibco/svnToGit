/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.StateMachineElement;
import com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Machine Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.StateMachineElementImpl#getCompilableScopes <em>Compilable Scopes</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.StateMachineElementImpl#getIndexName <em>Index Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateMachineElementImpl extends EntityElementImpl implements StateMachineElement {
	/**
	 * The cached value of the '{@link #getCompilableScopes() <em>Compilable Scopes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompilableScopes()
	 * @generated
	 * @ordered
	 */
	protected EList<CompilableScopeEntry> compilableScopes;

	/**
	 * The default value of the '{@link #getIndexName() <em>Index Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndexName()
	 * @generated
	 * @ordered
	 */
	protected static final String INDEX_NAME_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getIndexName() <em>Index Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndexName()
	 * @generated
	 * @ordered
	 */
	protected String indexName = INDEX_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateMachineElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.STATE_MACHINE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CompilableScopeEntry> getCompilableScopes() {
		if (compilableScopes == null) {
			compilableScopes = new EObjectContainmentEList<CompilableScopeEntry>(CompilableScopeEntry.class, this, IndexPackage.STATE_MACHINE_ELEMENT__COMPILABLE_SCOPES);
		}
		return compilableScopes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIndexName() {
		return indexName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndexName(String newIndexName) {
		String oldIndexName = indexName;
		indexName = newIndexName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.STATE_MACHINE_ELEMENT__INDEX_NAME, oldIndexName, indexName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IndexPackage.STATE_MACHINE_ELEMENT__COMPILABLE_SCOPES:
				return ((InternalEList<?>)getCompilableScopes()).basicRemove(otherEnd, msgs);
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
			case IndexPackage.STATE_MACHINE_ELEMENT__COMPILABLE_SCOPES:
				return getCompilableScopes();
			case IndexPackage.STATE_MACHINE_ELEMENT__INDEX_NAME:
				return getIndexName();
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
			case IndexPackage.STATE_MACHINE_ELEMENT__COMPILABLE_SCOPES:
				getCompilableScopes().clear();
				getCompilableScopes().addAll((Collection<? extends CompilableScopeEntry>)newValue);
				return;
			case IndexPackage.STATE_MACHINE_ELEMENT__INDEX_NAME:
				setIndexName((String)newValue);
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
			case IndexPackage.STATE_MACHINE_ELEMENT__COMPILABLE_SCOPES:
				getCompilableScopes().clear();
				return;
			case IndexPackage.STATE_MACHINE_ELEMENT__INDEX_NAME:
				setIndexName(INDEX_NAME_EDEFAULT);
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
			case IndexPackage.STATE_MACHINE_ELEMENT__COMPILABLE_SCOPES:
				return compilableScopes != null && !compilableScopes.isEmpty();
			case IndexPackage.STATE_MACHINE_ELEMENT__INDEX_NAME:
				return INDEX_NAME_EDEFAULT == null ? indexName != null : !INDEX_NAME_EDEFAULT.equals(indexName);
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
		result.append(" (indexName: ");
		result.append(indexName);
		result.append(')');
		return result.toString();
	}

} //StateMachineElementImpl
