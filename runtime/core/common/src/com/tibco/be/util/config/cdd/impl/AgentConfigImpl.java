/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Properties;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.ConfigTools;
import com.tibco.be.util.config.cdd.AgentClassConfig;
import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.OverrideConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Agent Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.AgentConfigImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.AgentConfigImpl#getRef <em>Ref</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.AgentConfigImpl#getKey <em>Key</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.AgentConfigImpl#getPriority <em>Priority</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AgentConfigImpl extends ArtifactConfigImpl implements AgentConfig {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AgentConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getAgentConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, CddPackage.AGENT_CONFIG__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgentClassConfig getRef() {
		return (AgentClassConfig)getMixed().get(CddPackage.eINSTANCE.getAgentConfig_Ref(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRef(AgentClassConfig newRef) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getAgentConfig_Ref(), newRef);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getKey() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getAgentConfig_Key(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetKey(OverrideConfig newKey, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getAgentConfig_Key(), newKey, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKey(OverrideConfig newKey) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getAgentConfig_Key(), newKey);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPriority() {
		return (OverrideConfig)getMixed().get(CddPackage.eINSTANCE.getAgentConfig_Priority(), true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPriority(OverrideConfig newPriority, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(CddPackage.eINSTANCE.getAgentConfig_Priority(), newPriority, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPriority(OverrideConfig newPriority) {
		((FeatureMap.Internal)getMixed()).set(CddPackage.eINSTANCE.getAgentConfig_Priority(), newPriority);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.AGENT_CONFIG__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case CddPackage.AGENT_CONFIG__KEY:
				return basicSetKey(null, msgs);
			case CddPackage.AGENT_CONFIG__PRIORITY:
				return basicSetPriority(null, msgs);
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
			case CddPackage.AGENT_CONFIG__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case CddPackage.AGENT_CONFIG__REF:
				return getRef();
			case CddPackage.AGENT_CONFIG__KEY:
				return getKey();
			case CddPackage.AGENT_CONFIG__PRIORITY:
				return getPriority();
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
			case CddPackage.AGENT_CONFIG__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case CddPackage.AGENT_CONFIG__REF:
				setRef((AgentClassConfig)newValue);
				return;
			case CddPackage.AGENT_CONFIG__KEY:
				setKey((OverrideConfig)newValue);
				return;
			case CddPackage.AGENT_CONFIG__PRIORITY:
				setPriority((OverrideConfig)newValue);
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
			case CddPackage.AGENT_CONFIG__MIXED:
				getMixed().clear();
				return;
			case CddPackage.AGENT_CONFIG__REF:
				setRef((AgentClassConfig)null);
				return;
			case CddPackage.AGENT_CONFIG__KEY:
				setKey((OverrideConfig)null);
				return;
			case CddPackage.AGENT_CONFIG__PRIORITY:
				setPriority((OverrideConfig)null);
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
			case CddPackage.AGENT_CONFIG__MIXED:
				return mixed != null && !mixed.isEmpty();
			case CddPackage.AGENT_CONFIG__REF:
				return getRef() != null;
			case CddPackage.AGENT_CONFIG__KEY:
				return getKey() != null;
			case CddPackage.AGENT_CONFIG__PRIORITY:
				return getPriority() != null;
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
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

	
    public Properties toProperties() {
        final Properties p = new Properties();
        
        final AgentClassConfig agentClass = this.getRef();        
        final String ref = agentClass.getId();
        
        final String priority = CddTools.getValueFromMixed(this.getPriority());        
        if (null != priority) {
        	p.put("Agent." + ref + ".priority", priority);
        }

        final String key = CddTools.getValueFromMixed(this.getKey());        
        if (null != key) {
        	p.put("Agent." + ref + ".key", key);
        }

        if (null != agentClass) {
            p.putAll(ConfigTools.replaceInKeys(agentClass.toProperties(), "${AgentGroupName}", ref));
        }
        //todo: for other props see CacheAgentConfiguration.getCacheConfig()
        return p;
    }
    
} //AgentConfigImpl
