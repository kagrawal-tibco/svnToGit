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

import com.tibco.cep.studio.core.model.topology.DeploymentUnit;
import com.tibco.cep.studio.core.model.topology.DeploymentUnits;
import com.tibco.cep.studio.core.model.topology.TopologyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Deployment Units</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DeploymentUnitsImpl#getDeploymentUnit <em>Deployment Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DeploymentUnitsImpl extends EObjectImpl implements DeploymentUnits {
	/**
	 * The cached value of the '{@link #getDeploymentUnit() <em>Deployment Unit</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeploymentUnit()
	 * @generated
	 * @ordered
	 */
	protected EList<DeploymentUnit> deploymentUnit;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeploymentUnitsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TopologyPackage.Literals.DEPLOYMENT_UNITS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DeploymentUnit> getDeploymentUnit() {
		if (deploymentUnit == null) {
			deploymentUnit = new EObjectContainmentEList<DeploymentUnit>(DeploymentUnit.class, this, TopologyPackage.DEPLOYMENT_UNITS__DEPLOYMENT_UNIT);
		}
		return deploymentUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TopologyPackage.DEPLOYMENT_UNITS__DEPLOYMENT_UNIT:
				return ((InternalEList<?>)getDeploymentUnit()).basicRemove(otherEnd, msgs);
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
			case TopologyPackage.DEPLOYMENT_UNITS__DEPLOYMENT_UNIT:
				return getDeploymentUnit();
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
			case TopologyPackage.DEPLOYMENT_UNITS__DEPLOYMENT_UNIT:
				getDeploymentUnit().clear();
				getDeploymentUnit().addAll((Collection<? extends DeploymentUnit>)newValue);
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
			case TopologyPackage.DEPLOYMENT_UNITS__DEPLOYMENT_UNIT:
				getDeploymentUnit().clear();
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
			case TopologyPackage.DEPLOYMENT_UNITS__DEPLOYMENT_UNIT:
				return deploymentUnit != null && !deploymentUnit.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DeploymentUnitsImpl
