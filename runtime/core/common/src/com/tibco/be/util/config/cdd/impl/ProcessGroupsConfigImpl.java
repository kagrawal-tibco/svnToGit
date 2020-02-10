/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.ProcessGroupsConfig;
import com.tibco.be.util.config.cdd.ProcessesConfig;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Process Groups Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessGroupsConfigImpl#getProcesses <em>Processes</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProcessGroupsConfigImpl extends ArtifactConfigImpl implements ProcessGroupsConfig {
	/**
	 * The cached value of the '{@link #getProcesses() <em>Processes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcesses()
	 * @generated
	 * @ordered
	 */
	protected EList<ProcessesConfig> processes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProcessGroupsConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getProcessGroupsConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProcessesConfig> getProcesses() {
		if (processes == null) {
			processes = new EObjectContainmentEList<ProcessesConfig>(ProcessesConfig.class, this, CddPackage.PROCESS_GROUPS_CONFIG__PROCESSES);
		}
		return processes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.PROCESS_GROUPS_CONFIG__PROCESSES:
				return ((InternalEList<?>)getProcesses()).basicRemove(otherEnd, msgs);
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
			case CddPackage.PROCESS_GROUPS_CONFIG__PROCESSES:
				return getProcesses();
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
			case CddPackage.PROCESS_GROUPS_CONFIG__PROCESSES:
				getProcesses().clear();
				getProcesses().addAll((Collection<? extends ProcessesConfig>)newValue);
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
			case CddPackage.PROCESS_GROUPS_CONFIG__PROCESSES:
				getProcesses().clear();
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
			case CddPackage.PROCESS_GROUPS_CONFIG__PROCESSES:
				return processes != null && !processes.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProcessGroupsConfigImpl
