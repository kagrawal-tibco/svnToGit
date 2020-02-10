/**
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.CompositeIndexConfig;
import com.tibco.be.util.config.cdd.CompositeIndexesConfig;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composite Indexes Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CompositeIndexesConfigImpl#getCompositeIndex <em>Composite Index</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompositeIndexesConfigImpl extends EObjectImpl implements CompositeIndexesConfig {
	/**
	 * The cached value of the '{@link #getCompositeIndex() <em>Composite Index</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompositeIndex()
	 * @generated
	 * @ordered
	 */
	protected EList<CompositeIndexConfig> compositeIndex;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompositeIndexesConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getCompositeIndexesConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CompositeIndexConfig> getCompositeIndex() {
		if (compositeIndex == null) {
			compositeIndex = new EObjectContainmentEList<CompositeIndexConfig>(CompositeIndexConfig.class, this, CddPackage.COMPOSITE_INDEXES_CONFIG__COMPOSITE_INDEX);
		}
		return compositeIndex;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<Object, Object> toProperties() {
		return new java.util.Properties();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.COMPOSITE_INDEXES_CONFIG__COMPOSITE_INDEX:
				return ((InternalEList<?>)getCompositeIndex()).basicRemove(otherEnd, msgs);
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
			case CddPackage.COMPOSITE_INDEXES_CONFIG__COMPOSITE_INDEX:
				return getCompositeIndex();
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
			case CddPackage.COMPOSITE_INDEXES_CONFIG__COMPOSITE_INDEX:
				getCompositeIndex().clear();
				getCompositeIndex().addAll((Collection<? extends CompositeIndexConfig>)newValue);
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
			case CddPackage.COMPOSITE_INDEXES_CONFIG__COMPOSITE_INDEX:
				getCompositeIndex().clear();
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
			case CddPackage.COMPOSITE_INDEXES_CONFIG__COMPOSITE_INDEX:
				return compositeIndex != null && !compositeIndex.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //CompositeIndexesConfigImpl
