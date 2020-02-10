/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.AgentsConfig;
import com.tibco.be.util.config.cdd.CddPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Agents Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.AgentsConfigImpl#getAgent <em>Agent</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AgentsConfigImpl extends EObjectImpl implements AgentsConfig {
	/**
	 * The cached value of the '{@link #getAgent() <em>Agent</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAgent()
	 * @generated
	 * @ordered
	 */
	protected EList<AgentConfig> agent;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AgentsConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getAgentsConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AgentConfig> getAgent() {
		if (agent == null) {
			agent = new EObjectContainmentEList<AgentConfig>(AgentConfig.class, this, CddPackage.AGENTS_CONFIG__AGENT);
		}
		return agent;
	}

	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Map<Object, Object> toProperties() {
	    final Properties p = new Properties();
	    for (final AgentConfig agent : this.getAgent()) {
	    		p.putAll(agent.toProperties());
	    }
	    return p;
	}

	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.AGENTS_CONFIG__AGENT:
				return ((InternalEList<?>)getAgent()).basicRemove(otherEnd, msgs);
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
			case CddPackage.AGENTS_CONFIG__AGENT:
				return getAgent();
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
			case CddPackage.AGENTS_CONFIG__AGENT:
				getAgent().clear();
				getAgent().addAll((Collection<? extends AgentConfig>)newValue);
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
			case CddPackage.AGENTS_CONFIG__AGENT:
				getAgent().clear();
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
			case CddPackage.AGENTS_CONFIG__AGENT:
				return agent != null && !agent.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AgentsConfigImpl
