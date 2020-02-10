/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.model.topology.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.core.model.topology.Cluster;
import com.tibco.cep.studio.core.model.topology.DeploymentMappings;
import com.tibco.cep.studio.core.model.topology.DeploymentUnits;
import com.tibco.cep.studio.core.model.topology.MasterFiles;
import com.tibco.cep.studio.core.model.topology.RunVersion;
import com.tibco.cep.studio.core.model.topology.TopologyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cluster</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.ClusterImpl#getMasterFiles <em>Master Files</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.ClusterImpl#getRunVersion <em>Run Version</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.ClusterImpl#getDeploymentUnits <em>Deployment Units</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.ClusterImpl#getDeploymentMappings <em>Deployment Mappings</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.ClusterImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.ClusterImpl#getProjectCdd <em>Project Cdd</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ClusterImpl extends EObjectImpl implements Cluster {
	/**
	 * The cached value of the '{@link #getMasterFiles() <em>Master Files</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMasterFiles()
	 * @generated
	 * @ordered
	 */
	protected MasterFiles masterFiles;

	/**
	 * The cached value of the '{@link #getRunVersion() <em>Run Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRunVersion()
	 * @generated
	 * @ordered
	 */
	protected RunVersion runVersion;

	/**
	 * The cached value of the '{@link #getDeploymentUnits() <em>Deployment Units</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeploymentUnits()
	 * @generated
	 * @ordered
	 */
	protected DeploymentUnits deploymentUnits;

	/**
	 * The cached value of the '{@link #getDeploymentMappings() <em>Deployment Mappings</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeploymentMappings()
	 * @generated
	 * @ordered
	 */
	protected DeploymentMappings deploymentMappings;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getProjectCdd() <em>Project Cdd</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectCdd()
	 * @generated
	 * @ordered
	 */
	protected static final String PROJECT_CDD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProjectCdd() <em>Project Cdd</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjectCdd()
	 * @generated
	 * @ordered
	 */
	protected String projectCdd = PROJECT_CDD_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClusterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TopologyPackage.Literals.CLUSTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MasterFiles getMasterFiles() {
		return masterFiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMasterFiles(MasterFiles newMasterFiles, NotificationChain msgs) {
		MasterFiles oldMasterFiles = masterFiles;
		masterFiles = newMasterFiles;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TopologyPackage.CLUSTER__MASTER_FILES, oldMasterFiles, newMasterFiles);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMasterFiles(MasterFiles newMasterFiles) {
		if (newMasterFiles != masterFiles) {
			NotificationChain msgs = null;
			if (masterFiles != null)
				msgs = ((InternalEObject)masterFiles).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.CLUSTER__MASTER_FILES, null, msgs);
			if (newMasterFiles != null)
				msgs = ((InternalEObject)newMasterFiles).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.CLUSTER__MASTER_FILES, null, msgs);
			msgs = basicSetMasterFiles(newMasterFiles, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.CLUSTER__MASTER_FILES, newMasterFiles, newMasterFiles));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RunVersion getRunVersion() {
		return runVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRunVersion(RunVersion newRunVersion, NotificationChain msgs) {
		RunVersion oldRunVersion = runVersion;
		runVersion = newRunVersion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TopologyPackage.CLUSTER__RUN_VERSION, oldRunVersion, newRunVersion);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRunVersion(RunVersion newRunVersion) {
		if (newRunVersion != runVersion) {
			NotificationChain msgs = null;
			if (runVersion != null)
				msgs = ((InternalEObject)runVersion).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.CLUSTER__RUN_VERSION, null, msgs);
			if (newRunVersion != null)
				msgs = ((InternalEObject)newRunVersion).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.CLUSTER__RUN_VERSION, null, msgs);
			msgs = basicSetRunVersion(newRunVersion, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.CLUSTER__RUN_VERSION, newRunVersion, newRunVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentUnits getDeploymentUnits() {
		return deploymentUnits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeploymentUnits(DeploymentUnits newDeploymentUnits, NotificationChain msgs) {
		DeploymentUnits oldDeploymentUnits = deploymentUnits;
		deploymentUnits = newDeploymentUnits;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TopologyPackage.CLUSTER__DEPLOYMENT_UNITS, oldDeploymentUnits, newDeploymentUnits);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeploymentUnits(DeploymentUnits newDeploymentUnits) {
		if (newDeploymentUnits != deploymentUnits) {
			NotificationChain msgs = null;
			if (deploymentUnits != null)
				msgs = ((InternalEObject)deploymentUnits).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.CLUSTER__DEPLOYMENT_UNITS, null, msgs);
			if (newDeploymentUnits != null)
				msgs = ((InternalEObject)newDeploymentUnits).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.CLUSTER__DEPLOYMENT_UNITS, null, msgs);
			msgs = basicSetDeploymentUnits(newDeploymentUnits, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.CLUSTER__DEPLOYMENT_UNITS, newDeploymentUnits, newDeploymentUnits));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentMappings getDeploymentMappings() {
		return deploymentMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeploymentMappings(DeploymentMappings newDeploymentMappings, NotificationChain msgs) {
		DeploymentMappings oldDeploymentMappings = deploymentMappings;
		deploymentMappings = newDeploymentMappings;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TopologyPackage.CLUSTER__DEPLOYMENT_MAPPINGS, oldDeploymentMappings, newDeploymentMappings);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeploymentMappings(DeploymentMappings newDeploymentMappings) {
		if (newDeploymentMappings != deploymentMappings) {
			NotificationChain msgs = null;
			if (deploymentMappings != null)
				msgs = ((InternalEObject)deploymentMappings).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.CLUSTER__DEPLOYMENT_MAPPINGS, null, msgs);
			if (newDeploymentMappings != null)
				msgs = ((InternalEObject)newDeploymentMappings).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.CLUSTER__DEPLOYMENT_MAPPINGS, null, msgs);
			msgs = basicSetDeploymentMappings(newDeploymentMappings, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.CLUSTER__DEPLOYMENT_MAPPINGS, newDeploymentMappings, newDeploymentMappings));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.CLUSTER__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProjectCdd() {
		return projectCdd;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjectCdd(String newProjectCdd) {
		String oldProjectCdd = projectCdd;
		projectCdd = newProjectCdd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.CLUSTER__PROJECT_CDD, oldProjectCdd, projectCdd));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TopologyPackage.CLUSTER__MASTER_FILES:
				return basicSetMasterFiles(null, msgs);
			case TopologyPackage.CLUSTER__RUN_VERSION:
				return basicSetRunVersion(null, msgs);
			case TopologyPackage.CLUSTER__DEPLOYMENT_UNITS:
				return basicSetDeploymentUnits(null, msgs);
			case TopologyPackage.CLUSTER__DEPLOYMENT_MAPPINGS:
				return basicSetDeploymentMappings(null, msgs);
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
			case TopologyPackage.CLUSTER__MASTER_FILES:
				return getMasterFiles();
			case TopologyPackage.CLUSTER__RUN_VERSION:
				return getRunVersion();
			case TopologyPackage.CLUSTER__DEPLOYMENT_UNITS:
				return getDeploymentUnits();
			case TopologyPackage.CLUSTER__DEPLOYMENT_MAPPINGS:
				return getDeploymentMappings();
			case TopologyPackage.CLUSTER__NAME:
				return getName();
			case TopologyPackage.CLUSTER__PROJECT_CDD:
				return getProjectCdd();
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
			case TopologyPackage.CLUSTER__MASTER_FILES:
				setMasterFiles((MasterFiles)newValue);
				return;
			case TopologyPackage.CLUSTER__RUN_VERSION:
				setRunVersion((RunVersion)newValue);
				return;
			case TopologyPackage.CLUSTER__DEPLOYMENT_UNITS:
				setDeploymentUnits((DeploymentUnits)newValue);
				return;
			case TopologyPackage.CLUSTER__DEPLOYMENT_MAPPINGS:
				setDeploymentMappings((DeploymentMappings)newValue);
				return;
			case TopologyPackage.CLUSTER__NAME:
				setName((String)newValue);
				return;
			case TopologyPackage.CLUSTER__PROJECT_CDD:
				setProjectCdd((String)newValue);
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
			case TopologyPackage.CLUSTER__MASTER_FILES:
				setMasterFiles((MasterFiles)null);
				return;
			case TopologyPackage.CLUSTER__RUN_VERSION:
				setRunVersion((RunVersion)null);
				return;
			case TopologyPackage.CLUSTER__DEPLOYMENT_UNITS:
				setDeploymentUnits((DeploymentUnits)null);
				return;
			case TopologyPackage.CLUSTER__DEPLOYMENT_MAPPINGS:
				setDeploymentMappings((DeploymentMappings)null);
				return;
			case TopologyPackage.CLUSTER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TopologyPackage.CLUSTER__PROJECT_CDD:
				setProjectCdd(PROJECT_CDD_EDEFAULT);
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
			case TopologyPackage.CLUSTER__MASTER_FILES:
				return masterFiles != null;
			case TopologyPackage.CLUSTER__RUN_VERSION:
				return runVersion != null;
			case TopologyPackage.CLUSTER__DEPLOYMENT_UNITS:
				return deploymentUnits != null;
			case TopologyPackage.CLUSTER__DEPLOYMENT_MAPPINGS:
				return deploymentMappings != null;
			case TopologyPackage.CLUSTER__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TopologyPackage.CLUSTER__PROJECT_CDD:
				return PROJECT_CDD_EDEFAULT == null ? projectCdd != null : !PROJECT_CDD_EDEFAULT.equals(projectCdd);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", projectCdd: ");
		result.append(projectCdd);
		result.append(')');
		return result.toString();
	}

} //ClusterImpl
