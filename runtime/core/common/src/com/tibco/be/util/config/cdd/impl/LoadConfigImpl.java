/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.LoadConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import java.util.Map;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Load Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LoadConfigImpl#getMaxActive <em>Max Active</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LoadConfigImpl extends ArtifactConfigImpl implements LoadConfig {
	/**
	 * The cached value of the '{@link #getMaxActive() <em>Max Active</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxActive()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig maxActive;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LoadConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getLoadConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getMaxActive() {
		return maxActive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaxActive(OverrideConfig newMaxActive, NotificationChain msgs) {
		OverrideConfig oldMaxActive = maxActive;
		maxActive = newMaxActive;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_CONFIG__MAX_ACTIVE, oldMaxActive, newMaxActive);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxActive(OverrideConfig newMaxActive) {
		if (newMaxActive != maxActive) {
			NotificationChain msgs = null;
			if (maxActive != null)
				msgs = ((InternalEObject)maxActive).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_CONFIG__MAX_ACTIVE, null, msgs);
			if (newMaxActive != null)
				msgs = ((InternalEObject)newMaxActive).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LOAD_CONFIG__MAX_ACTIVE, null, msgs);
			msgs = basicSetMaxActive(newMaxActive, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LOAD_CONFIG__MAX_ACTIVE, newMaxActive, newMaxActive));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.LOAD_CONFIG__MAX_ACTIVE:
				return basicSetMaxActive(null, msgs);
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
			case CddPackage.LOAD_CONFIG__MAX_ACTIVE:
				return getMaxActive();
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
			case CddPackage.LOAD_CONFIG__MAX_ACTIVE:
				setMaxActive((OverrideConfig)newValue);
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
			case CddPackage.LOAD_CONFIG__MAX_ACTIVE:
				setMaxActive((OverrideConfig)null);
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
			case CddPackage.LOAD_CONFIG__MAX_ACTIVE:
				return maxActive != null;
		}
		return super.eIsSet(featureID);
	}


    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated NOT
     */
    public Map<Object, Object> toProperties() {
        final Map<Object, Object> p = super.toProperties();
        final String value = CddTools.getValueFromMixed(maxActive);
        if (null != value) {
            p.put("Agent.${AgentGroupName}.maxActive", CddTools.getValueFromMixed(maxActive));
        }
        return p;
    }

} //LoadConfigImpl
