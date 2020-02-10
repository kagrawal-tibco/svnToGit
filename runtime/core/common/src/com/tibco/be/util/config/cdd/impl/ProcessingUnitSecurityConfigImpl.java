/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Map;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.ProcessingUnitSecurityConfig;
import com.tibco.be.util.config.cdd.SecurityController;
import com.tibco.be.util.config.cdd.SecurityOverrideConfig;
import com.tibco.be.util.config.cdd.SecurityRequester;
import com.tibco.cep.runtime.util.SystemProperty;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Processing Unit Security Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessingUnitSecurityConfigImpl#getRole <em>Role</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessingUnitSecurityConfigImpl#getController <em>Controller</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProcessingUnitSecurityConfigImpl#getRequester <em>Requester</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProcessingUnitSecurityConfigImpl extends SecurityConfigImpl implements ProcessingUnitSecurityConfig {
	/**
	 * The cached value of the '{@link #getRole() <em>Role</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRole()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig role;

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
	protected ProcessingUnitSecurityConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getProcessingUnitSecurityConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getRole() {
		return role;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRole(OverrideConfig newRole, NotificationChain msgs) {
		OverrideConfig oldRole = role;
		role = newRole;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__ROLE, oldRole, newRole);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRole(OverrideConfig newRole) {
		if (newRole != role) {
			NotificationChain msgs = null;
			if (role != null)
				msgs = ((InternalEObject)role).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__ROLE, null, msgs);
			if (newRole != null)
				msgs = ((InternalEObject)newRole).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__ROLE, null, msgs);
			msgs = basicSetRole(newRole, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__ROLE, newRole, newRole));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__CONTROLLER, oldController, newController);
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
				msgs = ((InternalEObject)controller).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__CONTROLLER, null, msgs);
			if (newController != null)
				msgs = ((InternalEObject)newController).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__CONTROLLER, null, msgs);
			msgs = basicSetController(newController, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__CONTROLLER, newController, newController));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__REQUESTER, oldRequester, newRequester);
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
				msgs = ((InternalEObject)requester).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__REQUESTER, null, msgs);
			if (newRequester != null)
				msgs = ((InternalEObject)newRequester).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__REQUESTER, null, msgs);
			msgs = basicSetRequester(newRequester, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__REQUESTER, newRequester, newRequester));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__ROLE:
				return basicSetRole(null, msgs);
			case CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__CONTROLLER:
				return basicSetController(null, msgs);
			case CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__REQUESTER:
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
			case CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__ROLE:
				return getRole();
			case CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__CONTROLLER:
				return getController();
			case CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__REQUESTER:
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
			case CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__ROLE:
				setRole((OverrideConfig)newValue);
				return;
			case CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__CONTROLLER:
				setController((SecurityController)newValue);
				return;
			case CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__REQUESTER:
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
			case CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__ROLE:
				setRole((OverrideConfig)null);
				return;
			case CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__CONTROLLER:
				setController((SecurityController)null);
				return;
			case CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__REQUESTER:
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
			case CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__ROLE:
				return role != null;
			case CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__CONTROLLER:
				return controller != null;
			case CddPackage.PROCESSING_UNIT_SECURITY_CONFIG__REQUESTER:
				return requester != null;
		}
		return super.eIsSet(featureID);
	}

	/**
     * @generated NOT
     */
	@Override
    public Map<Object, Object> toProperties() {
        Map<Object, Object> properties = super.toProperties();
        String defaultValue = "";

        //Role
        CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_MODE_ROLE.getPropertyName(), this.role, true, defaultValue);

        //Controller settings
        if (this.controller != null) {
	    	SecurityOverrideConfig policyFile = this.controller.getPolicyFile();
	        if (policyFile != null && policyFile.isOverride()) 
	        	CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_POLICY_FILE.getPropertyName(), policyFile, true, defaultValue);
	        
	        SecurityOverrideConfig cntrlIdentityPassword = this.controller.getIdentityPassword();
	        if (cntrlIdentityPassword != null && cntrlIdentityPassword.isOverride()) 
	        	CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_CONTROLLER_IDENTITY.getPropertyName(), cntrlIdentityPassword, false, defaultValue);	        
        }
        
        //Requester settings
    	if (this.requester != null) {
	        SecurityOverrideConfig tokenFile = this.requester.getTokenFile();
			if (tokenFile != null && tokenFile.isOverride()) 
				CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_TOKEN_FILE.getPropertyName(), tokenFile, true, defaultValue);
			
			SecurityOverrideConfig reqIdentityPassword = this.requester.getIdentityPassword();
			if (reqIdentityPassword != null && reqIdentityPassword.isOverride()) 
				CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_TOKEN_IDENTITY.getPropertyName(), reqIdentityPassword, false, defaultValue);
	
			SecurityOverrideConfig certificateKeyFile = this.requester.getCertificateKeyFile();
			if (certificateKeyFile != null && certificateKeyFile.isOverride()) 
				CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_REQUESTER_CERTKEYFILE.getPropertyName(), certificateKeyFile, true, defaultValue);
	
			SecurityOverrideConfig domainName = this.requester.getDomainName();
			if (domainName != null && domainName.isOverride()) 
				CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_REQUESTER_DOMAINNAME.getPropertyName(), domainName, true, defaultValue);    		
	
			SecurityOverrideConfig userName = this.requester.getUserName();
			if (userName != null && userName.isOverride()) 
				CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_REQUESTER_USERNAME.getPropertyName(), userName, true, defaultValue);
			
			SecurityOverrideConfig userPassword = this.requester.getUserPassword();
			if (userPassword != null && userPassword.isOverride()) 
				CddTools.addEntryFromMixed(properties, SystemProperty.AS_SECURITY_REQUESTER_PASSWORD.getPropertyName(), userPassword, false, defaultValue);
    	}
    	
    	return properties;
    }	
} //ProcessingUnitSecurityConfigImpl
