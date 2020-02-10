/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.ClusterMemberConfig;
import com.tibco.be.util.config.cdd.RuleConfigConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Config Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.RuleConfigConfigImpl#getClusterMember <em>Cluster Member</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleConfigConfigImpl extends EObjectImpl implements RuleConfigConfig {
	/**
	 * The cached value of the '{@link #getClusterMember() <em>Cluster Member</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClusterMember()
	 * @generated
	 * @ordered
	 */
	protected EList<ClusterMemberConfig> clusterMember;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleConfigConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getRuleConfigConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ClusterMemberConfig> getClusterMember() {
		if (clusterMember == null) {
			clusterMember = new EObjectContainmentEList<ClusterMemberConfig>(ClusterMemberConfig.class, this, CddPackage.RULE_CONFIG_CONFIG__CLUSTER_MEMBER);
		}
		return clusterMember;
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
			case CddPackage.RULE_CONFIG_CONFIG__CLUSTER_MEMBER:
				return ((InternalEList<?>)getClusterMember()).basicRemove(otherEnd, msgs);
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
			case CddPackage.RULE_CONFIG_CONFIG__CLUSTER_MEMBER:
				return getClusterMember();
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
			case CddPackage.RULE_CONFIG_CONFIG__CLUSTER_MEMBER:
				getClusterMember().clear();
				getClusterMember().addAll((Collection<? extends ClusterMemberConfig>)newValue);
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
			case CddPackage.RULE_CONFIG_CONFIG__CLUSTER_MEMBER:
				getClusterMember().clear();
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
			case CddPackage.RULE_CONFIG_CONFIG__CLUSTER_MEMBER:
				return clusterMember != null && !clusterMember.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //RuleConfigConfigImpl
