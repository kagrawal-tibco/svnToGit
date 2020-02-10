/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Map;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.CacheManagerSecurityConfig;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.SecurityController;
import com.tibco.be.util.config.cdd.SecurityRequester;
import com.tibco.cep.runtime.util.SystemProperty;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cache Manager Security Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerSecurityConfigImpl#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerSecurityConfigImpl#getController <em>Controller</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.CacheManagerSecurityConfigImpl#getRequester <em>Requester</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CacheManagerSecurityConfigImpl extends SecurityConfigImpl implements CacheManagerSecurityConfig {
	/**
	 * The cached value of the '{@link #getEnabled() <em>Enabled</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnabled()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig enabled;

	/**
	 * The cached value of the '{@link #getController() <em>Controller</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getController()
	 * @generated
	 * @ordered
	 */
	protected SecurityController controller;

	/**
	 * The cached value of the '{@link #getRequester() <em>Requester</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequester()
	 * @generated
	 * @ordered
	 */
	protected SecurityRequester requester;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CacheManagerSecurityConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getCacheManagerSecurityConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getEnabled() {
		return enabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnabled(OverrideConfig newEnabled, NotificationChain msgs) {
		OverrideConfig oldEnabled = enabled;
		enabled = newEnabled;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_SECURITY_CONFIG__ENABLED, oldEnabled, newEnabled);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnabled(OverrideConfig newEnabled) {
		if (newEnabled != enabled) {
			NotificationChain msgs = null;
			if (enabled != null)
				msgs = ((InternalEObject)enabled).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_SECURITY_CONFIG__ENABLED, null, msgs);
			if (newEnabled != null)
				msgs = ((InternalEObject)newEnabled).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_SECURITY_CONFIG__ENABLED, null, msgs);
			msgs = basicSetEnabled(newEnabled, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_SECURITY_CONFIG__ENABLED, newEnabled, newEnabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityController getController() {
		return controller;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetController(SecurityController newController, NotificationChain msgs) {
		SecurityController oldController = controller;
		controller = newController;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_SECURITY_CONFIG__CONTROLLER, oldController, newController);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setController(SecurityController newController) {
		if (newController != controller) {
			NotificationChain msgs = null;
			if (controller != null)
				msgs = ((InternalEObject)controller).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_SECURITY_CONFIG__CONTROLLER, null, msgs);
			if (newController != null)
				msgs = ((InternalEObject)newController).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_SECURITY_CONFIG__CONTROLLER, null, msgs);
			msgs = basicSetController(newController, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_SECURITY_CONFIG__CONTROLLER, newController, newController));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityRequester getRequester() {
		return requester;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRequester(SecurityRequester newRequester, NotificationChain msgs) {
		SecurityRequester oldRequester = requester;
		requester = newRequester;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_SECURITY_CONFIG__REQUESTER, oldRequester, newRequester);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequester(SecurityRequester newRequester) {
		if (newRequester != requester) {
			NotificationChain msgs = null;
			if (requester != null)
				msgs = ((InternalEObject)requester).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_SECURITY_CONFIG__REQUESTER, null, msgs);
			if (newRequester != null)
				msgs = ((InternalEObject)newRequester).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.CACHE_MANAGER_SECURITY_CONFIG__REQUESTER, null, msgs);
			msgs = basicSetRequester(newRequester, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.CACHE_MANAGER_SECURITY_CONFIG__REQUESTER, newRequester, newRequester));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.CACHE_MANAGER_SECURITY_CONFIG__ENABLED:
				return basicSetEnabled(null, msgs);
			case CddPackage.CACHE_MANAGER_SECURITY_CONFIG__CONTROLLER:
				return basicSetController(null, msgs);
			case CddPackage.CACHE_MANAGER_SECURITY_CONFIG__REQUESTER:
				return basicSetRequester(null, msgs);
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
			case CddPackage.CACHE_MANAGER_SECURITY_CONFIG__ENABLED:
				return getEnabled();
			case CddPackage.CACHE_MANAGER_SECURITY_CONFIG__CONTROLLER:
				return getController();
			case CddPackage.CACHE_MANAGER_SECURITY_CONFIG__REQUESTER:
				return getRequester();
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
			case CddPackage.CACHE_MANAGER_SECURITY_CONFIG__ENABLED:
				setEnabled((OverrideConfig)newValue);
				return;
			case CddPackage.CACHE_MANAGER_SECURITY_CONFIG__CONTROLLER:
				setController((SecurityController)newValue);
				return;
			case CddPackage.CACHE_MANAGER_SECURITY_CONFIG__REQUESTER:
				setRequester((SecurityRequester)newValue);
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
			case CddPackage.CACHE_MANAGER_SECURITY_CONFIG__ENABLED:
				setEnabled((OverrideConfig)null);
				return;
			case CddPackage.CACHE_MANAGER_SECURITY_CONFIG__CONTROLLER:
				setController((SecurityController)null);
				return;
			case CddPackage.CACHE_MANAGER_SECURITY_CONFIG__REQUESTER:
				setRequester((SecurityRequester)null);
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
			case CddPackage.CACHE_MANAGER_SECURITY_CONFIG__ENABLED:
				return enabled != null;
			case CddPackage.CACHE_MANAGER_SECURITY_CONFIG__CONTROLLER:
				return controller != null;
			case CddPackage.CACHE_MANAGER_SECURITY_CONFIG__REQUESTER:
				return requester != null;
		}
		return super.eIsSet(featureID);
	}

    /**
     * @generated NOT
     */
	@Override
    public Map<Object, Object> toProperties() {
    	String defaultValue = "";
        Map<Object, Object> properties = super.toProperties();
        
        CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_ENABLE.getPropertyName(), this.getEnabled(), true, "false");
        
    	if (this.controller != null) {
	        CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_POLICY_FILE.getPropertyName(), this.controller.getPolicyFile(), true, defaultValue);
	        CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_CONTROLLER_IDENTITY.getPropertyName(), this.controller.getIdentityPassword(), false, defaultValue);
    	}
    	
    	if (this.requester != null) {
    		CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_TOKEN_FILE.getPropertyName(), this.requester.getTokenFile(), true, defaultValue);
    		CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_TOKEN_IDENTITY.getPropertyName(), this.requester.getIdentityPassword(), false, defaultValue);
    		CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_REQUESTER_CERTKEYFILE.getPropertyName(), this.requester.getCertificateKeyFile(), true, defaultValue);
    		CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_REQUESTER_DOMAINNAME.getPropertyName(), this.requester.getDomainName(), true, defaultValue);    		
    		CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_REQUESTER_USERNAME.getPropertyName(), this.requester.getUserName(), true, defaultValue);
    		CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_REQUESTER_PASSWORD.getPropertyName(), this.requester.getUserPassword(), false, defaultValue);    		
    	}
        return properties;
    }
	
} //CacheManagerSecurityConfigImpl
