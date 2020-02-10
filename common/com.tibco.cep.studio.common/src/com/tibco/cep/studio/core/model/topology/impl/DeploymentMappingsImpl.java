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

import com.tibco.cep.studio.core.model.topology.DeploymentMapping;
import com.tibco.cep.studio.core.model.topology.DeploymentMappings;
import com.tibco.cep.studio.core.model.topology.TopologyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Deployment Mappings</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DeploymentMappingsImpl#getDeploymentMapping <em>Deployment Mapping</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DeploymentMappingsImpl extends EObjectImpl implements DeploymentMappings {
	/**
	 * The cached value of the '{@link #getDeploymentMapping() <em>Deployment Mapping</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeploymentMapping()
	 * @generated
	 * @ordered
	 */
	protected EList<DeploymentMapping> deploymentMapping;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeploymentMappingsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TopologyPackage.Literals.DEPLOYMENT_MAPPINGS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DeploymentMapping> getDeploymentMapping() {
		if (deploymentMapping == null) {
			deploymentMapping = new EObjectContainmentEList<DeploymentMapping>(DeploymentMapping.class, this, TopologyPackage.DEPLOYMENT_MAPPINGS__DEPLOYMENT_MAPPING);
		}
		return deploymentMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TopologyPackage.DEPLOYMENT_MAPPINGS__DEPLOYMENT_MAPPING:
				return ((InternalEList<?>)getDeploymentMapping()).basicRemove(otherEnd, msgs);
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
			case TopologyPackage.DEPLOYMENT_MAPPINGS__DEPLOYMENT_MAPPING:
				return getDeploymentMapping();
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
			case TopologyPackage.DEPLOYMENT_MAPPINGS__DEPLOYMENT_MAPPING:
				getDeploymentMapping().clear();
				getDeploymentMapping().addAll((Collection<? extends DeploymentMapping>)newValue);
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
			case TopologyPackage.DEPLOYMENT_MAPPINGS__DEPLOYMENT_MAPPING:
				getDeploymentMapping().clear();
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
			case TopologyPackage.DEPLOYMENT_MAPPINGS__DEPLOYMENT_MAPPING:
				return deploymentMapping != null && !deploymentMapping.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DeploymentMappingsImpl
