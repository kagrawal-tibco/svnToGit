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

import com.tibco.cep.studio.core.model.topology.DeployedFiles;
import com.tibco.cep.studio.core.model.topology.DeploymentUnit;
import com.tibco.cep.studio.core.model.topology.ProcessingUnitsConfig;
import com.tibco.cep.studio.core.model.topology.TopologyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Deployment Unit</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DeploymentUnitImpl#getDeployedFiles <em>Deployed Files</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DeploymentUnitImpl#getProcessingUnitsConfig <em>Processing Units Config</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DeploymentUnitImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.model.topology.impl.DeploymentUnitImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DeploymentUnitImpl extends EObjectImpl implements DeploymentUnit {
	/**
	 * The cached value of the '{@link #getDeployedFiles() <em>Deployed Files</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeployedFiles()
	 * @generated
	 * @ordered
	 */
	protected DeployedFiles deployedFiles;

	/**
	 * The cached value of the '{@link #getProcessingUnitsConfig() <em>Processing Units Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcessingUnitsConfig()
	 * @generated
	 * @ordered
	 */
	protected ProcessingUnitsConfig processingUnitsConfig;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DeploymentUnitImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TopologyPackage.Literals.DEPLOYMENT_UNIT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeployedFiles getDeployedFiles() {
		return deployedFiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDeployedFiles(DeployedFiles newDeployedFiles, NotificationChain msgs) {
		DeployedFiles oldDeployedFiles = deployedFiles;
		deployedFiles = newDeployedFiles;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TopologyPackage.DEPLOYMENT_UNIT__DEPLOYED_FILES, oldDeployedFiles, newDeployedFiles);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeployedFiles(DeployedFiles newDeployedFiles) {
		if (newDeployedFiles != deployedFiles) {
			NotificationChain msgs = null;
			if (deployedFiles != null)
				msgs = ((InternalEObject)deployedFiles).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.DEPLOYMENT_UNIT__DEPLOYED_FILES, null, msgs);
			if (newDeployedFiles != null)
				msgs = ((InternalEObject)newDeployedFiles).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.DEPLOYMENT_UNIT__DEPLOYED_FILES, null, msgs);
			msgs = basicSetDeployedFiles(newDeployedFiles, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.DEPLOYMENT_UNIT__DEPLOYED_FILES, newDeployedFiles, newDeployedFiles));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessingUnitsConfig getProcessingUnitsConfig() {
		return processingUnitsConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProcessingUnitsConfig(ProcessingUnitsConfig newProcessingUnitsConfig, NotificationChain msgs) {
		ProcessingUnitsConfig oldProcessingUnitsConfig = processingUnitsConfig;
		processingUnitsConfig = newProcessingUnitsConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, TopologyPackage.DEPLOYMENT_UNIT__PROCESSING_UNITS_CONFIG, oldProcessingUnitsConfig, newProcessingUnitsConfig);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcessingUnitsConfig(ProcessingUnitsConfig newProcessingUnitsConfig) {
		if (newProcessingUnitsConfig != processingUnitsConfig) {
			NotificationChain msgs = null;
			if (processingUnitsConfig != null)
				msgs = ((InternalEObject)processingUnitsConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.DEPLOYMENT_UNIT__PROCESSING_UNITS_CONFIG, null, msgs);
			if (newProcessingUnitsConfig != null)
				msgs = ((InternalEObject)newProcessingUnitsConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - TopologyPackage.DEPLOYMENT_UNIT__PROCESSING_UNITS_CONFIG, null, msgs);
			msgs = basicSetProcessingUnitsConfig(newProcessingUnitsConfig, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.DEPLOYMENT_UNIT__PROCESSING_UNITS_CONFIG, newProcessingUnitsConfig, newProcessingUnitsConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.DEPLOYMENT_UNIT__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, TopologyPackage.DEPLOYMENT_UNIT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TopologyPackage.DEPLOYMENT_UNIT__DEPLOYED_FILES:
				return basicSetDeployedFiles(null, msgs);
			case TopologyPackage.DEPLOYMENT_UNIT__PROCESSING_UNITS_CONFIG:
				return basicSetProcessingUnitsConfig(null, msgs);
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
			case TopologyPackage.DEPLOYMENT_UNIT__DEPLOYED_FILES:
				return getDeployedFiles();
			case TopologyPackage.DEPLOYMENT_UNIT__PROCESSING_UNITS_CONFIG:
				return getProcessingUnitsConfig();
			case TopologyPackage.DEPLOYMENT_UNIT__ID:
				return getId();
			case TopologyPackage.DEPLOYMENT_UNIT__NAME:
				return getName();
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
			case TopologyPackage.DEPLOYMENT_UNIT__DEPLOYED_FILES:
				setDeployedFiles((DeployedFiles)newValue);
				return;
			case TopologyPackage.DEPLOYMENT_UNIT__PROCESSING_UNITS_CONFIG:
				setProcessingUnitsConfig((ProcessingUnitsConfig)newValue);
				return;
			case TopologyPackage.DEPLOYMENT_UNIT__ID:
				setId((String)newValue);
				return;
			case TopologyPackage.DEPLOYMENT_UNIT__NAME:
				setName((String)newValue);
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
			case TopologyPackage.DEPLOYMENT_UNIT__DEPLOYED_FILES:
				setDeployedFiles((DeployedFiles)null);
				return;
			case TopologyPackage.DEPLOYMENT_UNIT__PROCESSING_UNITS_CONFIG:
				setProcessingUnitsConfig((ProcessingUnitsConfig)null);
				return;
			case TopologyPackage.DEPLOYMENT_UNIT__ID:
				setId(ID_EDEFAULT);
				return;
			case TopologyPackage.DEPLOYMENT_UNIT__NAME:
				setName(NAME_EDEFAULT);
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
			case TopologyPackage.DEPLOYMENT_UNIT__DEPLOYED_FILES:
				return deployedFiles != null;
			case TopologyPackage.DEPLOYMENT_UNIT__PROCESSING_UNITS_CONFIG:
				return processingUnitsConfig != null;
			case TopologyPackage.DEPLOYMENT_UNIT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case TopologyPackage.DEPLOYMENT_UNIT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(" (id: ");
		result.append(id);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //DeploymentUnitImpl
