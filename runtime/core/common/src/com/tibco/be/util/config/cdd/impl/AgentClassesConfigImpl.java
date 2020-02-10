/**
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.AgentClassesConfig;
import com.tibco.be.util.config.cdd.CacheAgentClassConfig;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.DashboardAgentClassConfig;
import com.tibco.be.util.config.cdd.InferenceAgentClassConfig;
import com.tibco.be.util.config.cdd.LiveViewAgentClassConfig;
import com.tibco.be.util.config.cdd.MmAgentClassConfig;
import com.tibco.be.util.config.cdd.ProcessAgentClassConfig;
import com.tibco.be.util.config.cdd.QueryAgentClassConfig;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Agent Classes Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.AgentClassesConfigImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.AgentClassesConfigImpl#getCacheAgentConfig <em>Cache Agent Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.AgentClassesConfigImpl#getDashboardAgentConfig <em>Dashboard Agent Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.AgentClassesConfigImpl#getInferenceAgentConfig <em>Inference Agent Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.AgentClassesConfigImpl#getQueryAgentConfig <em>Query Agent Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.AgentClassesConfigImpl#getMmAgentConfig <em>Mm Agent Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.AgentClassesConfigImpl#getProcessAgentConfig <em>Process Agent Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.AgentClassesConfigImpl#getLiveViewAgentConfig <em>Live View Agent Config</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AgentClassesConfigImpl extends EObjectImpl implements AgentClassesConfig {
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
	protected AgentClassesConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getAgentClassesConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getGroup() {
		if (group == null) {
			group = new BasicFeatureMap(this, CddPackage.AGENT_CLASSES_CONFIG__GROUP);
		}
		return group;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<CacheAgentClassConfig> getCacheAgentConfig() {
		return getGroup().list(CddPackage.eINSTANCE.getAgentClassesConfig_CacheAgentConfig());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DashboardAgentClassConfig> getDashboardAgentConfig() {
		return getGroup().list(CddPackage.eINSTANCE.getAgentClassesConfig_DashboardAgentConfig());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<InferenceAgentClassConfig> getInferenceAgentConfig() {
		return getGroup().list(CddPackage.eINSTANCE.getAgentClassesConfig_InferenceAgentConfig());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<QueryAgentClassConfig> getQueryAgentConfig() {
		return getGroup().list(CddPackage.eINSTANCE.getAgentClassesConfig_QueryAgentConfig());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<MmAgentClassConfig> getMmAgentConfig() {
		return getGroup().list(CddPackage.eINSTANCE.getAgentClassesConfig_MmAgentConfig());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProcessAgentClassConfig> getProcessAgentConfig() {
		return getGroup().list(CddPackage.eINSTANCE.getAgentClassesConfig_ProcessAgentConfig());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<LiveViewAgentClassConfig> getLiveViewAgentConfig() {
		return getGroup().list(CddPackage.eINSTANCE.getAgentClassesConfig_LiveViewAgentConfig());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map<Object, Object> toProperties() {
		return new java.util.Properties();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.AGENT_CLASSES_CONFIG__GROUP:
				return ((InternalEList<?>)getGroup()).basicRemove(otherEnd, msgs);
			case CddPackage.AGENT_CLASSES_CONFIG__CACHE_AGENT_CONFIG:
				return ((InternalEList<?>)getCacheAgentConfig()).basicRemove(otherEnd, msgs);
			case CddPackage.AGENT_CLASSES_CONFIG__DASHBOARD_AGENT_CONFIG:
				return ((InternalEList<?>)getDashboardAgentConfig()).basicRemove(otherEnd, msgs);
			case CddPackage.AGENT_CLASSES_CONFIG__INFERENCE_AGENT_CONFIG:
				return ((InternalEList<?>)getInferenceAgentConfig()).basicRemove(otherEnd, msgs);
			case CddPackage.AGENT_CLASSES_CONFIG__QUERY_AGENT_CONFIG:
				return ((InternalEList<?>)getQueryAgentConfig()).basicRemove(otherEnd, msgs);
			case CddPackage.AGENT_CLASSES_CONFIG__MM_AGENT_CONFIG:
				return ((InternalEList<?>)getMmAgentConfig()).basicRemove(otherEnd, msgs);
			case CddPackage.AGENT_CLASSES_CONFIG__PROCESS_AGENT_CONFIG:
				return ((InternalEList<?>)getProcessAgentConfig()).basicRemove(otherEnd, msgs);
			case CddPackage.AGENT_CLASSES_CONFIG__LIVE_VIEW_AGENT_CONFIG:
				return ((InternalEList<?>)getLiveViewAgentConfig()).basicRemove(otherEnd, msgs);
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
			case CddPackage.AGENT_CLASSES_CONFIG__GROUP:
				if (coreType) return getGroup();
				return ((FeatureMap.Internal)getGroup()).getWrapper();
			case CddPackage.AGENT_CLASSES_CONFIG__CACHE_AGENT_CONFIG:
				return getCacheAgentConfig();
			case CddPackage.AGENT_CLASSES_CONFIG__DASHBOARD_AGENT_CONFIG:
				return getDashboardAgentConfig();
			case CddPackage.AGENT_CLASSES_CONFIG__INFERENCE_AGENT_CONFIG:
				return getInferenceAgentConfig();
			case CddPackage.AGENT_CLASSES_CONFIG__QUERY_AGENT_CONFIG:
				return getQueryAgentConfig();
			case CddPackage.AGENT_CLASSES_CONFIG__MM_AGENT_CONFIG:
				return getMmAgentConfig();
			case CddPackage.AGENT_CLASSES_CONFIG__PROCESS_AGENT_CONFIG:
				return getProcessAgentConfig();
			case CddPackage.AGENT_CLASSES_CONFIG__LIVE_VIEW_AGENT_CONFIG:
				return getLiveViewAgentConfig();
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
			case CddPackage.AGENT_CLASSES_CONFIG__GROUP:
				((FeatureMap.Internal)getGroup()).set(newValue);
				return;
			case CddPackage.AGENT_CLASSES_CONFIG__CACHE_AGENT_CONFIG:
				getCacheAgentConfig().clear();
				getCacheAgentConfig().addAll((Collection<? extends CacheAgentClassConfig>)newValue);
				return;
			case CddPackage.AGENT_CLASSES_CONFIG__DASHBOARD_AGENT_CONFIG:
				getDashboardAgentConfig().clear();
				getDashboardAgentConfig().addAll((Collection<? extends DashboardAgentClassConfig>)newValue);
				return;
			case CddPackage.AGENT_CLASSES_CONFIG__INFERENCE_AGENT_CONFIG:
				getInferenceAgentConfig().clear();
				getInferenceAgentConfig().addAll((Collection<? extends InferenceAgentClassConfig>)newValue);
				return;
			case CddPackage.AGENT_CLASSES_CONFIG__QUERY_AGENT_CONFIG:
				getQueryAgentConfig().clear();
				getQueryAgentConfig().addAll((Collection<? extends QueryAgentClassConfig>)newValue);
				return;
			case CddPackage.AGENT_CLASSES_CONFIG__MM_AGENT_CONFIG:
				getMmAgentConfig().clear();
				getMmAgentConfig().addAll((Collection<? extends MmAgentClassConfig>)newValue);
				return;
			case CddPackage.AGENT_CLASSES_CONFIG__PROCESS_AGENT_CONFIG:
				getProcessAgentConfig().clear();
				getProcessAgentConfig().addAll((Collection<? extends ProcessAgentClassConfig>)newValue);
				return;
			case CddPackage.AGENT_CLASSES_CONFIG__LIVE_VIEW_AGENT_CONFIG:
				getLiveViewAgentConfig().clear();
				getLiveViewAgentConfig().addAll((Collection<? extends LiveViewAgentClassConfig>)newValue);
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
			case CddPackage.AGENT_CLASSES_CONFIG__GROUP:
				getGroup().clear();
				return;
			case CddPackage.AGENT_CLASSES_CONFIG__CACHE_AGENT_CONFIG:
				getCacheAgentConfig().clear();
				return;
			case CddPackage.AGENT_CLASSES_CONFIG__DASHBOARD_AGENT_CONFIG:
				getDashboardAgentConfig().clear();
				return;
			case CddPackage.AGENT_CLASSES_CONFIG__INFERENCE_AGENT_CONFIG:
				getInferenceAgentConfig().clear();
				return;
			case CddPackage.AGENT_CLASSES_CONFIG__QUERY_AGENT_CONFIG:
				getQueryAgentConfig().clear();
				return;
			case CddPackage.AGENT_CLASSES_CONFIG__MM_AGENT_CONFIG:
				getMmAgentConfig().clear();
				return;
			case CddPackage.AGENT_CLASSES_CONFIG__PROCESS_AGENT_CONFIG:
				getProcessAgentConfig().clear();
				return;
			case CddPackage.AGENT_CLASSES_CONFIG__LIVE_VIEW_AGENT_CONFIG:
				getLiveViewAgentConfig().clear();
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
			case CddPackage.AGENT_CLASSES_CONFIG__GROUP:
				return group != null && !group.isEmpty();
			case CddPackage.AGENT_CLASSES_CONFIG__CACHE_AGENT_CONFIG:
				return !getCacheAgentConfig().isEmpty();
			case CddPackage.AGENT_CLASSES_CONFIG__DASHBOARD_AGENT_CONFIG:
				return !getDashboardAgentConfig().isEmpty();
			case CddPackage.AGENT_CLASSES_CONFIG__INFERENCE_AGENT_CONFIG:
				return !getInferenceAgentConfig().isEmpty();
			case CddPackage.AGENT_CLASSES_CONFIG__QUERY_AGENT_CONFIG:
				return !getQueryAgentConfig().isEmpty();
			case CddPackage.AGENT_CLASSES_CONFIG__MM_AGENT_CONFIG:
				return !getMmAgentConfig().isEmpty();
			case CddPackage.AGENT_CLASSES_CONFIG__PROCESS_AGENT_CONFIG:
				return !getProcessAgentConfig().isEmpty();
			case CddPackage.AGENT_CLASSES_CONFIG__LIVE_VIEW_AGENT_CONFIG:
				return !getLiveViewAgentConfig().isEmpty();
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

} //AgentClassesConfigImpl
