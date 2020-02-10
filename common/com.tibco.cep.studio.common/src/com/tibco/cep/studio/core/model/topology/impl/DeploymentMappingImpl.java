/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.core.model.topology.DeploymentMapping;
import com.tibco.cep.studio.core.model.topology.TopologyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Deployment Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DeploymentMappingImpl#getDeploymentUnitRef <em>Deployment Unit Ref</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DeploymentMappingImpl#getHostRef <em>Host Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DeploymentMappingImpl extends EObjectImpl implements DeploymentMapping {
	/**
	 * The default value of the '{@link #getDeploymentUnitRef() <em>Deployment Unit Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeploymentUnitRef()
	 * @generated
	 * @ordered
	 */
	protected static final String DEPLOYMENT_UNIT_REF_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDeploymentUnitRef() <em>Deployment Unit Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeploymentUnitRef()
	 * @generated
	 * @ordered
	 */
	protected String deploymentUnitRef = DEPLOYMENT_UNIT_REF_EDEFAULT;

	/**
	 * The default value of the '{@link #getHostRef() <em>Host Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHostRef()
	 * @generated
	 * @ordered
	 */
	protected static final String HOST_REF_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHostRef() <em>Host Ref</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHostRef()
	 * @generated
	 * @ordered
	 */
	protected String hostRef = HOST_REF_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeploymentMappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TopologyPackage.Literals.DEPLOYMENT_MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDeploymentUnitRef() {
		return deploymentUnitRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeploymentUnitRef(String newDeploymentUnitRef) {
		String oldDeploymentUnitRef = deploymentUnitRef;
		deploymentUnitRef = newDeploymentUnitRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.DEPLOYMENT_MAPPING__DEPLOYMENT_UNIT_REF, oldDeploymentUnitRef, deploymentUnitRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHostRef() {
		return hostRef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHostRef(String newHostRef) {
		String oldHostRef = hostRef;
		hostRef = newHostRef;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.DEPLOYMENT_MAPPING__HOST_REF, oldHostRef, hostRef));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TopologyPackage.DEPLOYMENT_MAPPING__DEPLOYMENT_UNIT_REF:
				return getDeploymentUnitRef();
			case TopologyPackage.DEPLOYMENT_MAPPING__HOST_REF:
				return getHostRef();
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
			case TopologyPackage.DEPLOYMENT_MAPPING__DEPLOYMENT_UNIT_REF:
				setDeploymentUnitRef((String)newValue);
				return;
			case TopologyPackage.DEPLOYMENT_MAPPING__HOST_REF:
				setHostRef((String)newValue);
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
			case TopologyPackage.DEPLOYMENT_MAPPING__DEPLOYMENT_UNIT_REF:
				setDeploymentUnitRef(DEPLOYMENT_UNIT_REF_EDEFAULT);
				return;
			case TopologyPackage.DEPLOYMENT_MAPPING__HOST_REF:
				setHostRef(HOST_REF_EDEFAULT);
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
			case TopologyPackage.DEPLOYMENT_MAPPING__DEPLOYMENT_UNIT_REF:
				return DEPLOYMENT_UNIT_REF_EDEFAULT == null ? deploymentUnitRef != null : !DEPLOYMENT_UNIT_REF_EDEFAULT.equals(deploymentUnitRef);
			case TopologyPackage.DEPLOYMENT_MAPPING__HOST_REF:
				return HOST_REF_EDEFAULT == null ? hostRef != null : !HOST_REF_EDEFAULT.equals(hostRef);
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
		result.append(" (deploymentUnitRef: ");
		result.append(deploymentUnitRef);
		result.append(", hostRef: ");
		result.append(hostRef);
		result.append(')');
		return result.toString();
	}

} //DeploymentMappingImpl
