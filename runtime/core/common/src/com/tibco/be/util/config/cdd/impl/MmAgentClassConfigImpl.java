/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.be.util.config.cdd.AlertConfigSetConfig;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.InferenceAgentClassConfig;
import com.tibco.be.util.config.cdd.MmActionConfigSetConfig;
import com.tibco.be.util.config.cdd.MmAgentClassConfig;
import com.tibco.be.util.config.cdd.PropertyGroupConfig;
import com.tibco.be.util.config.cdd.QueryAgentClassConfig;
import com.tibco.be.util.config.cdd.RuleConfigConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mm Agent Class Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmAgentClassConfigImpl#getMmInferenceAgentClass <em>Mm Inference Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmAgentClassConfigImpl#getMmQueryAgentClass <em>Mm Query Agent Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmAgentClassConfigImpl#getAlertConfigSet <em>Alert Config Set</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmAgentClassConfigImpl#getRuleConfig <em>Rule Config</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmAgentClassConfigImpl#getMmActionConfigSet <em>Mm Action Config Set</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.MmAgentClassConfigImpl#getPropertyGroup <em>Property Group</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MmAgentClassConfigImpl extends AgentClassConfigImpl implements MmAgentClassConfig {
	/**
	 * The cached value of the '{@link #getMmInferenceAgentClass() <em>Mm Inference Agent Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMmInferenceAgentClass()
	 * @generated
	 * @ordered
	 */
	protected InferenceAgentClassConfig mmInferenceAgentClass;

	/**
	 * The cached value of the '{@link #getMmQueryAgentClass() <em>Mm Query Agent Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMmQueryAgentClass()
	 * @generated
	 * @ordered
	 */
	protected QueryAgentClassConfig mmQueryAgentClass;

	/**
	 * The cached value of the '{@link #getAlertConfigSet() <em>Alert Config Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlertConfigSet()
	 * @generated
	 * @ordered
	 */
	protected AlertConfigSetConfig alertConfigSet;

	/**
	 * The cached value of the '{@link #getRuleConfig() <em>Rule Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleConfig()
	 * @generated
	 * @ordered
	 */
	protected RuleConfigConfig ruleConfig;

	/**
	 * The cached value of the '{@link #getMmActionConfigSet() <em>Mm Action Config Set</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMmActionConfigSet()
	 * @generated
	 * @ordered
	 */
	protected MmActionConfigSetConfig mmActionConfigSet;

	/**
	 * The cached value of the '{@link #getPropertyGroup() <em>Property Group</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyGroup()
	 * @generated
	 * @ordered
	 */
	protected PropertyGroupConfig propertyGroup;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MmAgentClassConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getMmAgentClassConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InferenceAgentClassConfig getMmInferenceAgentClass() {
		return mmInferenceAgentClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmInferenceAgentClass(InferenceAgentClassConfig newMmInferenceAgentClass) {
		InferenceAgentClassConfig oldMmInferenceAgentClass = mmInferenceAgentClass;
		mmInferenceAgentClass = newMmInferenceAgentClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.MM_AGENT_CLASS_CONFIG__MM_INFERENCE_AGENT_CLASS, oldMmInferenceAgentClass, mmInferenceAgentClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QueryAgentClassConfig getMmQueryAgentClass() {
		return mmQueryAgentClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmQueryAgentClass(QueryAgentClassConfig newMmQueryAgentClass) {
		QueryAgentClassConfig oldMmQueryAgentClass = mmQueryAgentClass;
		mmQueryAgentClass = newMmQueryAgentClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.MM_AGENT_CLASS_CONFIG__MM_QUERY_AGENT_CLASS, oldMmQueryAgentClass, mmQueryAgentClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlertConfigSetConfig getAlertConfigSet() {
		return alertConfigSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAlertConfigSet(AlertConfigSetConfig newAlertConfigSet, NotificationChain msgs) {
		AlertConfigSetConfig oldAlertConfigSet = alertConfigSet;
		alertConfigSet = newAlertConfigSet;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.MM_AGENT_CLASS_CONFIG__ALERT_CONFIG_SET, oldAlertConfigSet, newAlertConfigSet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlertConfigSet(AlertConfigSetConfig newAlertConfigSet) {
		if (newAlertConfigSet != alertConfigSet) {
			NotificationChain msgs = null;
			if (alertConfigSet != null)
				msgs = ((InternalEObject)alertConfigSet).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.MM_AGENT_CLASS_CONFIG__ALERT_CONFIG_SET, null, msgs);
			if (newAlertConfigSet != null)
				msgs = ((InternalEObject)newAlertConfigSet).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.MM_AGENT_CLASS_CONFIG__ALERT_CONFIG_SET, null, msgs);
			msgs = basicSetAlertConfigSet(newAlertConfigSet, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.MM_AGENT_CLASS_CONFIG__ALERT_CONFIG_SET, newAlertConfigSet, newAlertConfigSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleConfigConfig getRuleConfig() {
		return ruleConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRuleConfig(RuleConfigConfig newRuleConfig, NotificationChain msgs) {
		RuleConfigConfig oldRuleConfig = ruleConfig;
		ruleConfig = newRuleConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.MM_AGENT_CLASS_CONFIG__RULE_CONFIG, oldRuleConfig, newRuleConfig);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuleConfig(RuleConfigConfig newRuleConfig) {
		if (newRuleConfig != ruleConfig) {
			NotificationChain msgs = null;
			if (ruleConfig != null)
				msgs = ((InternalEObject)ruleConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.MM_AGENT_CLASS_CONFIG__RULE_CONFIG, null, msgs);
			if (newRuleConfig != null)
				msgs = ((InternalEObject)newRuleConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.MM_AGENT_CLASS_CONFIG__RULE_CONFIG, null, msgs);
			msgs = basicSetRuleConfig(newRuleConfig, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.MM_AGENT_CLASS_CONFIG__RULE_CONFIG, newRuleConfig, newRuleConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MmActionConfigSetConfig getMmActionConfigSet() {
		return mmActionConfigSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMmActionConfigSet(MmActionConfigSetConfig newMmActionConfigSet, NotificationChain msgs) {
		MmActionConfigSetConfig oldMmActionConfigSet = mmActionConfigSet;
		mmActionConfigSet = newMmActionConfigSet;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.MM_AGENT_CLASS_CONFIG__MM_ACTION_CONFIG_SET, oldMmActionConfigSet, newMmActionConfigSet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMmActionConfigSet(MmActionConfigSetConfig newMmActionConfigSet) {
		if (newMmActionConfigSet != mmActionConfigSet) {
			NotificationChain msgs = null;
			if (mmActionConfigSet != null)
				msgs = ((InternalEObject)mmActionConfigSet).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.MM_AGENT_CLASS_CONFIG__MM_ACTION_CONFIG_SET, null, msgs);
			if (newMmActionConfigSet != null)
				msgs = ((InternalEObject)newMmActionConfigSet).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.MM_AGENT_CLASS_CONFIG__MM_ACTION_CONFIG_SET, null, msgs);
			msgs = basicSetMmActionConfigSet(newMmActionConfigSet, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.MM_AGENT_CLASS_CONFIG__MM_ACTION_CONFIG_SET, newMmActionConfigSet, newMmActionConfigSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyGroupConfig getPropertyGroup() {
		return propertyGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPropertyGroup(PropertyGroupConfig newPropertyGroup, NotificationChain msgs) {
		PropertyGroupConfig oldPropertyGroup = propertyGroup;
		propertyGroup = newPropertyGroup;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.MM_AGENT_CLASS_CONFIG__PROPERTY_GROUP, oldPropertyGroup, newPropertyGroup);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyGroup(PropertyGroupConfig newPropertyGroup) {
		if (newPropertyGroup != propertyGroup) {
			NotificationChain msgs = null;
			if (propertyGroup != null)
				msgs = ((InternalEObject)propertyGroup).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.MM_AGENT_CLASS_CONFIG__PROPERTY_GROUP, null, msgs);
			if (newPropertyGroup != null)
				msgs = ((InternalEObject)newPropertyGroup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.MM_AGENT_CLASS_CONFIG__PROPERTY_GROUP, null, msgs);
			msgs = basicSetPropertyGroup(newPropertyGroup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.MM_AGENT_CLASS_CONFIG__PROPERTY_GROUP, newPropertyGroup, newPropertyGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.MM_AGENT_CLASS_CONFIG__ALERT_CONFIG_SET:
				return basicSetAlertConfigSet(null, msgs);
			case CddPackage.MM_AGENT_CLASS_CONFIG__RULE_CONFIG:
				return basicSetRuleConfig(null, msgs);
			case CddPackage.MM_AGENT_CLASS_CONFIG__MM_ACTION_CONFIG_SET:
				return basicSetMmActionConfigSet(null, msgs);
			case CddPackage.MM_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				return basicSetPropertyGroup(null, msgs);
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
			case CddPackage.MM_AGENT_CLASS_CONFIG__MM_INFERENCE_AGENT_CLASS:
				return getMmInferenceAgentClass();
			case CddPackage.MM_AGENT_CLASS_CONFIG__MM_QUERY_AGENT_CLASS:
				return getMmQueryAgentClass();
			case CddPackage.MM_AGENT_CLASS_CONFIG__ALERT_CONFIG_SET:
				return getAlertConfigSet();
			case CddPackage.MM_AGENT_CLASS_CONFIG__RULE_CONFIG:
				return getRuleConfig();
			case CddPackage.MM_AGENT_CLASS_CONFIG__MM_ACTION_CONFIG_SET:
				return getMmActionConfigSet();
			case CddPackage.MM_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				return getPropertyGroup();
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
			case CddPackage.MM_AGENT_CLASS_CONFIG__MM_INFERENCE_AGENT_CLASS:
				setMmInferenceAgentClass((InferenceAgentClassConfig)newValue);
				return;
			case CddPackage.MM_AGENT_CLASS_CONFIG__MM_QUERY_AGENT_CLASS:
				setMmQueryAgentClass((QueryAgentClassConfig)newValue);
				return;
			case CddPackage.MM_AGENT_CLASS_CONFIG__ALERT_CONFIG_SET:
				setAlertConfigSet((AlertConfigSetConfig)newValue);
				return;
			case CddPackage.MM_AGENT_CLASS_CONFIG__RULE_CONFIG:
				setRuleConfig((RuleConfigConfig)newValue);
				return;
			case CddPackage.MM_AGENT_CLASS_CONFIG__MM_ACTION_CONFIG_SET:
				setMmActionConfigSet((MmActionConfigSetConfig)newValue);
				return;
			case CddPackage.MM_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				setPropertyGroup((PropertyGroupConfig)newValue);
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
			case CddPackage.MM_AGENT_CLASS_CONFIG__MM_INFERENCE_AGENT_CLASS:
				setMmInferenceAgentClass((InferenceAgentClassConfig)null);
				return;
			case CddPackage.MM_AGENT_CLASS_CONFIG__MM_QUERY_AGENT_CLASS:
				setMmQueryAgentClass((QueryAgentClassConfig)null);
				return;
			case CddPackage.MM_AGENT_CLASS_CONFIG__ALERT_CONFIG_SET:
				setAlertConfigSet((AlertConfigSetConfig)null);
				return;
			case CddPackage.MM_AGENT_CLASS_CONFIG__RULE_CONFIG:
				setRuleConfig((RuleConfigConfig)null);
				return;
			case CddPackage.MM_AGENT_CLASS_CONFIG__MM_ACTION_CONFIG_SET:
				setMmActionConfigSet((MmActionConfigSetConfig)null);
				return;
			case CddPackage.MM_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				setPropertyGroup((PropertyGroupConfig)null);
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
			case CddPackage.MM_AGENT_CLASS_CONFIG__MM_INFERENCE_AGENT_CLASS:
				return mmInferenceAgentClass != null;
			case CddPackage.MM_AGENT_CLASS_CONFIG__MM_QUERY_AGENT_CLASS:
				return mmQueryAgentClass != null;
			case CddPackage.MM_AGENT_CLASS_CONFIG__ALERT_CONFIG_SET:
				return alertConfigSet != null;
			case CddPackage.MM_AGENT_CLASS_CONFIG__RULE_CONFIG:
				return ruleConfig != null;
			case CddPackage.MM_AGENT_CLASS_CONFIG__MM_ACTION_CONFIG_SET:
				return mmActionConfigSet != null;
			case CddPackage.MM_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				return propertyGroup != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Map<Object, Object> toProperties() {
        final Properties p = (Properties) super.toProperties();

        final InferenceAgentClassConfig inferenceAgent = this.getMmInferenceAgentClass();
        if (null != this.mmInferenceAgentClass) {
    		p.putAll(inferenceAgent.toProperties());
        }
        
        final QueryAgentClassConfig queryAgent = this.getMmQueryAgentClass();
        if (null != queryAgent) {
		    p.putAll(queryAgent.toProperties());
        }

        // Properties
        if (null != this.propertyGroup) {
            p.putAll(this.propertyGroup.toProperties());
        }

        return p;
	}
	
} //MmAgentClassConfigImpl
