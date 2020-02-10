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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.LoadConfig;
import com.tibco.be.util.config.cdd.ProcessAgentClassConfig;
import com.tibco.be.util.config.cdd.ProcessEngineConfig;
import com.tibco.be.util.config.cdd.PropertyGroupConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Process Agent Class Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessAgentClassConfigImpl#getLoad <em>Load</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessAgentClassConfigImpl#getProcessEngine <em>Process Engine</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessAgentClassConfigImpl#getPropertyGroup <em>Property Group</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProcessAgentClassConfigImpl extends AgentClassConfigImpl implements ProcessAgentClassConfig {
	/**
	 * The cached value of the '{@link #getLoad() <em>Load</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLoad()
	 * @generated
	 * @ordered
	 */
	protected LoadConfig load;

	/**
	 * The cached value of the '{@link #getProcessEngine() <em>Process Engine</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProcessEngine()
	 * @generated
	 * @ordered
	 */
	protected EList<ProcessEngineConfig> processEngine;

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
	protected ProcessAgentClassConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getProcessAgentClassConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LoadConfig getLoad() {
		return load;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLoad(LoadConfig newLoad, NotificationChain msgs) {
		LoadConfig oldLoad = load;
		load = newLoad;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.PROCESS_AGENT_CLASS_CONFIG__LOAD, oldLoad, newLoad);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLoad(LoadConfig newLoad) {
		if (newLoad != load) {
			NotificationChain msgs = null;
			if (load != null)
				msgs = ((InternalEObject)load).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESS_AGENT_CLASS_CONFIG__LOAD, null, msgs);
			if (newLoad != null)
				msgs = ((InternalEObject)newLoad).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESS_AGENT_CLASS_CONFIG__LOAD, null, msgs);
			msgs = basicSetLoad(newLoad, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.PROCESS_AGENT_CLASS_CONFIG__LOAD, newLoad, newLoad));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ProcessEngineConfig> getProcessEngine() {
		if (processEngine == null) {
			processEngine = new EObjectContainmentEList<ProcessEngineConfig>(ProcessEngineConfig.class, this, CddPackage.PROCESS_AGENT_CLASS_CONFIG__PROCESS_ENGINE);
		}
		return processEngine;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.PROCESS_AGENT_CLASS_CONFIG__PROPERTY_GROUP, oldPropertyGroup, newPropertyGroup);
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
				msgs = ((InternalEObject)propertyGroup).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESS_AGENT_CLASS_CONFIG__PROPERTY_GROUP, null, msgs);
			if (newPropertyGroup != null)
				msgs = ((InternalEObject)newPropertyGroup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESS_AGENT_CLASS_CONFIG__PROPERTY_GROUP, null, msgs);
			msgs = basicSetPropertyGroup(newPropertyGroup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.PROCESS_AGENT_CLASS_CONFIG__PROPERTY_GROUP, newPropertyGroup, newPropertyGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.PROCESS_AGENT_CLASS_CONFIG__LOAD:
				return basicSetLoad(null, msgs);
			case CddPackage.PROCESS_AGENT_CLASS_CONFIG__PROCESS_ENGINE:
				return ((InternalEList<?>)getProcessEngine()).basicRemove(otherEnd, msgs);
			case CddPackage.PROCESS_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
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
			case CddPackage.PROCESS_AGENT_CLASS_CONFIG__LOAD:
				return getLoad();
			case CddPackage.PROCESS_AGENT_CLASS_CONFIG__PROCESS_ENGINE:
				return getProcessEngine();
			case CddPackage.PROCESS_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				return getPropertyGroup();
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
			case CddPackage.PROCESS_AGENT_CLASS_CONFIG__LOAD:
				setLoad((LoadConfig)newValue);
				return;
			case CddPackage.PROCESS_AGENT_CLASS_CONFIG__PROCESS_ENGINE:
				getProcessEngine().clear();
				getProcessEngine().addAll((Collection<? extends ProcessEngineConfig>)newValue);
				return;
			case CddPackage.PROCESS_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
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
			case CddPackage.PROCESS_AGENT_CLASS_CONFIG__LOAD:
				setLoad((LoadConfig)null);
				return;
			case CddPackage.PROCESS_AGENT_CLASS_CONFIG__PROCESS_ENGINE:
				getProcessEngine().clear();
				return;
			case CddPackage.PROCESS_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
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
			case CddPackage.PROCESS_AGENT_CLASS_CONFIG__LOAD:
				return load != null;
			case CddPackage.PROCESS_AGENT_CLASS_CONFIG__PROCESS_ENGINE:
				return processEngine != null && !processEngine.isEmpty();
			case CddPackage.PROCESS_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
				return propertyGroup != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public Map<Object, Object> toProperties() {
        final Properties p = new Properties();
//        if (null != this.load) {
//            p.putAll(this.load.toProperties());
//        }
        if (null != this.propertyGroup) {
            p.putAll(this.propertyGroup.toProperties());
        }
        
        for (ProcessEngineConfig processEngineConfig : this.processEngine) {
        	p.putAll(processEngineConfig.toProperties());
        }
        
        return p;
    }
	
} //ProcessAgentClassConfigImpl
