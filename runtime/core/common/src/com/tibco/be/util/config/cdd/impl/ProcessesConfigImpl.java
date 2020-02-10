/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.ProcessConfig;
import com.tibco.be.util.config.cdd.ProcessesConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Processes Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessesConfigImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessesConfigImpl#getRef <em>Ref</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessesConfigImpl#getUri <em>Uri</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProcessesConfigImpl extends UrisAndRefsConfigImpl implements ProcessesConfig {
	/**
	 * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroup()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap group;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProcessesConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getProcessesConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, CddPackage.PROCESSES_CONFIG__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProcessesConfig> getRef() {
		return getGroup().list(CddPackage.eINSTANCE.getProcessesConfig_Ref());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getUri() {
		return getGroup().list(CddPackage.eINSTANCE.getProcessesConfig_Uri());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<ProcessConfig> getAllProcesses() {
		final EList<ProcessConfig> all = new BasicEList<ProcessConfig>();		
		//This is not needed
		/*
	    
		all.addAll(this.getProcess());
	    
		final EList<ProcessesConfig> refs = this.getRef(); 
	    if (null != refs) {
	    	for (final ProcessesConfig ref : refs) {
	    		all.addAll(ref.getAllProcesses());
	    	}
	    }
	    
		 */
	    return all;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.PROCESSES_CONFIG__GROUP:
				return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
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
			case CddPackage.PROCESSES_CONFIG__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case CddPackage.PROCESSES_CONFIG__REF:
				return getRef();
			case CddPackage.PROCESSES_CONFIG__URI:
				return getUri();
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
			case CddPackage.PROCESSES_CONFIG__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case CddPackage.PROCESSES_CONFIG__REF:
				getRef().clear();
				getRef().addAll((Collection<? extends ProcessesConfig>)newValue);
				return;
			case CddPackage.PROCESSES_CONFIG__URI:
				getUri().clear();
				getUri().addAll((Collection<? extends String>)newValue);
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
			case CddPackage.PROCESSES_CONFIG__GROUP:
				getGroup().clear();
				return;
			case CddPackage.PROCESSES_CONFIG__REF:
				getRef().clear();
				return;
			case CddPackage.PROCESSES_CONFIG__URI:
				getUri().clear();
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
			case CddPackage.PROCESSES_CONFIG__GROUP:
				return group != null && !group.isEmpty();
			case CddPackage.PROCESSES_CONFIG__REF:
				return !getRef().isEmpty();
			case CddPackage.PROCESSES_CONFIG__URI:
				return !getUri().isEmpty();
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
		result.append(" (group: ");
		result.append(group);
		result.append(')');
		return result.toString();
	}

} //ProcessesConfigImpl
