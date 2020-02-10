/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.studio.core.model.topology.ProcessingUnitConfig;
import com.tibco.cep.studio.core.model.topology.ProcessingUnitsConfig;
import com.tibco.cep.studio.core.model.topology.TopologyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Processing Units Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.ProcessingUnitsConfigImpl#getProcessingUnitConfig <em>Processing Unit Config</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProcessingUnitsConfigImpl extends EObjectImpl implements ProcessingUnitsConfig {
	/**
	 * The cached value of the '{@link #getProcessingUnitConfig() <em>Processing Unit Config</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcessingUnitConfig()
	 * @generated
	 * @ordered
	 */
	protected EList<ProcessingUnitConfig> processingUnitConfig;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProcessingUnitsConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TopologyPackage.Literals.PROCESSING_UNITS_CONFIG;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProcessingUnitConfig> getProcessingUnitConfig() {
		if (processingUnitConfig == null) {
			processingUnitConfig = new EObjectContainmentEList<ProcessingUnitConfig>(ProcessingUnitConfig.class, this, TopologyPackage.PROCESSING_UNITS_CONFIG__PROCESSING_UNIT_CONFIG);
		}
		return processingUnitConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TopologyPackage.PROCESSING_UNITS_CONFIG__PROCESSING_UNIT_CONFIG:
				return ((InternalEList<?>)getProcessingUnitConfig()).basicRemove(otherEnd, msgs);
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
			case TopologyPackage.PROCESSING_UNITS_CONFIG__PROCESSING_UNIT_CONFIG:
				return getProcessingUnitConfig();
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
			case TopologyPackage.PROCESSING_UNITS_CONFIG__PROCESSING_UNIT_CONFIG:
				getProcessingUnitConfig().clear();
				getProcessingUnitConfig().addAll((Collection<? extends ProcessingUnitConfig>)newValue);
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
			case TopologyPackage.PROCESSING_UNITS_CONFIG__PROCESSING_UNIT_CONFIG:
				getProcessingUnitConfig().clear();
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
			case TopologyPackage.PROCESSING_UNITS_CONFIG__PROCESSING_UNIT_CONFIG:
				return processingUnitConfig != null && !processingUnitConfig.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProcessingUnitsConfigImpl
