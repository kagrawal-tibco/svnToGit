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

import com.tibco.be.util.config.cdd.CacheAgentClassConfig;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.PropertyGroupConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cache Agent Class Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheAgentClassConfigImpl#getPropertyGroup <em>Property Group</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CacheAgentClassConfigImpl extends AgentClassConfigImpl implements CacheAgentClassConfig {
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
	protected CacheAgentClassConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getCacheAgentClassConfig();
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_AGENT_CLASS_CONFIG__PROPERTY_GROUP, oldPropertyGroup, newPropertyGroup);
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
				msgs = ((InternalEObject)propertyGroup).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_AGENT_CLASS_CONFIG__PROPERTY_GROUP, null, msgs);
			if (newPropertyGroup != null)
				msgs = ((InternalEObject)newPropertyGroup).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_AGENT_CLASS_CONFIG__PROPERTY_GROUP, null, msgs);
			msgs = basicSetPropertyGroup(newPropertyGroup, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_AGENT_CLASS_CONFIG__PROPERTY_GROUP, newPropertyGroup, newPropertyGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.CACHE_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
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
			case CddPackage.CACHE_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
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
			case CddPackage.CACHE_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
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
			case CddPackage.CACHE_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
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
			case CddPackage.CACHE_AGENT_CLASS_CONFIG__PROPERTY_GROUP:
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
        return p;
    }

	
	
} //CacheAgentClassConfigImpl
