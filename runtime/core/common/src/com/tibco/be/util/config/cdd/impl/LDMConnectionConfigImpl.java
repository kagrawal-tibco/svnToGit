/**
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.LDMConnectionConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.SecurityOverrideConfig;

import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>LDM Connection Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LDMConnectionConfigImpl#getLdmUrl <em>Ldm Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LDMConnectionConfigImpl#getUserName <em>User Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LDMConnectionConfigImpl#getUserPassword <em>User Password</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LDMConnectionConfigImpl#getInitialSize <em>Initial Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LDMConnectionConfigImpl#getMinSize <em>Min Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LDMConnectionConfigImpl#getMaxSize <em>Max Size</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LDMConnectionConfigImpl extends EObjectImpl implements LDMConnectionConfig {
	/**
	 * The cached value of the '{@link #getLdmUrl() <em>Ldm Url</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLdmUrl()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig ldmUrl;

	/**
	 * The cached value of the '{@link #getUserName() <em>User Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserName()
	 * @generated
	 * @ordered
	 */
	protected SecurityOverrideConfig userName;

	/**
	 * The cached value of the '{@link #getUserPassword() <em>User Password</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserPassword()
	 * @generated
	 * @ordered
	 */
	protected SecurityOverrideConfig userPassword;

	/**
	 * The cached value of the '{@link #getInitialSize() <em>Initial Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialSize()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig initialSize;

	/**
	 * The cached value of the '{@link #getMinSize() <em>Min Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinSize()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig minSize;

	/**
	 * The cached value of the '{@link #getMaxSize() <em>Max Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxSize()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig maxSize;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LDMConnectionConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getLDMConnectionConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getLdmUrl() {
		return ldmUrl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLdmUrl(OverrideConfig newLdmUrl, NotificationChain msgs) {
		OverrideConfig oldLdmUrl = ldmUrl;
		ldmUrl = newLdmUrl;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LDM_CONNECTION_CONFIG__LDM_URL, oldLdmUrl, newLdmUrl);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLdmUrl(OverrideConfig newLdmUrl) {
		if (newLdmUrl != ldmUrl) {
			NotificationChain msgs = null;
			if (ldmUrl != null)
				msgs = ((InternalEObject)ldmUrl).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LDM_CONNECTION_CONFIG__LDM_URL, null, msgs);
			if (newLdmUrl != null)
				msgs = ((InternalEObject)newLdmUrl).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LDM_CONNECTION_CONFIG__LDM_URL, null, msgs);
			msgs = basicSetLdmUrl(newLdmUrl, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LDM_CONNECTION_CONFIG__LDM_URL, newLdmUrl, newLdmUrl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityOverrideConfig getUserName() {
		return userName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUserName(SecurityOverrideConfig newUserName, NotificationChain msgs) {
		SecurityOverrideConfig oldUserName = userName;
		userName = newUserName;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LDM_CONNECTION_CONFIG__USER_NAME, oldUserName, newUserName);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserName(SecurityOverrideConfig newUserName) {
		if (newUserName != userName) {
			NotificationChain msgs = null;
			if (userName != null)
				msgs = ((InternalEObject)userName).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LDM_CONNECTION_CONFIG__USER_NAME, null, msgs);
			if (newUserName != null)
				msgs = ((InternalEObject)newUserName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LDM_CONNECTION_CONFIG__USER_NAME, null, msgs);
			msgs = basicSetUserName(newUserName, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LDM_CONNECTION_CONFIG__USER_NAME, newUserName, newUserName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityOverrideConfig getUserPassword() {
		return userPassword;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUserPassword(SecurityOverrideConfig newUserPassword, NotificationChain msgs) {
		SecurityOverrideConfig oldUserPassword = userPassword;
		userPassword = newUserPassword;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LDM_CONNECTION_CONFIG__USER_PASSWORD, oldUserPassword, newUserPassword);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserPassword(SecurityOverrideConfig newUserPassword) {
		if (newUserPassword != userPassword) {
			NotificationChain msgs = null;
			if (userPassword != null)
				msgs = ((InternalEObject)userPassword).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LDM_CONNECTION_CONFIG__USER_PASSWORD, null, msgs);
			if (newUserPassword != null)
				msgs = ((InternalEObject)newUserPassword).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LDM_CONNECTION_CONFIG__USER_PASSWORD, null, msgs);
			msgs = basicSetUserPassword(newUserPassword, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LDM_CONNECTION_CONFIG__USER_PASSWORD, newUserPassword, newUserPassword));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getInitialSize() {
		return initialSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetInitialSize(OverrideConfig newInitialSize, NotificationChain msgs) {
		OverrideConfig oldInitialSize = initialSize;
		initialSize = newInitialSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LDM_CONNECTION_CONFIG__INITIAL_SIZE, oldInitialSize, newInitialSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialSize(OverrideConfig newInitialSize) {
		if (newInitialSize != initialSize) {
			NotificationChain msgs = null;
			if (initialSize != null)
				msgs = ((InternalEObject)initialSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LDM_CONNECTION_CONFIG__INITIAL_SIZE, null, msgs);
			if (newInitialSize != null)
				msgs = ((InternalEObject)newInitialSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LDM_CONNECTION_CONFIG__INITIAL_SIZE, null, msgs);
			msgs = basicSetInitialSize(newInitialSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LDM_CONNECTION_CONFIG__INITIAL_SIZE, newInitialSize, newInitialSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getMinSize() {
		return minSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMinSize(OverrideConfig newMinSize, NotificationChain msgs) {
		OverrideConfig oldMinSize = minSize;
		minSize = newMinSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LDM_CONNECTION_CONFIG__MIN_SIZE, oldMinSize, newMinSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinSize(OverrideConfig newMinSize) {
		if (newMinSize != minSize) {
			NotificationChain msgs = null;
			if (minSize != null)
				msgs = ((InternalEObject)minSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LDM_CONNECTION_CONFIG__MIN_SIZE, null, msgs);
			if (newMinSize != null)
				msgs = ((InternalEObject)newMinSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LDM_CONNECTION_CONFIG__MIN_SIZE, null, msgs);
			msgs = basicSetMinSize(newMinSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LDM_CONNECTION_CONFIG__MIN_SIZE, newMinSize, newMinSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getMaxSize() {
		return maxSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaxSize(OverrideConfig newMaxSize, NotificationChain msgs) {
		OverrideConfig oldMaxSize = maxSize;
		maxSize = newMaxSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LDM_CONNECTION_CONFIG__MAX_SIZE, oldMaxSize, newMaxSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxSize(OverrideConfig newMaxSize) {
		if (newMaxSize != maxSize) {
			NotificationChain msgs = null;
			if (maxSize != null)
				msgs = ((InternalEObject)maxSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LDM_CONNECTION_CONFIG__MAX_SIZE, null, msgs);
			if (newMaxSize != null)
				msgs = ((InternalEObject)newMaxSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LDM_CONNECTION_CONFIG__MAX_SIZE, null, msgs);
			msgs = basicSetMaxSize(newMaxSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LDM_CONNECTION_CONFIG__MAX_SIZE, newMaxSize, newMaxSize));
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
			case CddPackage.LDM_CONNECTION_CONFIG__LDM_URL:
				return basicSetLdmUrl(null, msgs);
			case CddPackage.LDM_CONNECTION_CONFIG__USER_NAME:
				return basicSetUserName(null, msgs);
			case CddPackage.LDM_CONNECTION_CONFIG__USER_PASSWORD:
				return basicSetUserPassword(null, msgs);
			case CddPackage.LDM_CONNECTION_CONFIG__INITIAL_SIZE:
				return basicSetInitialSize(null, msgs);
			case CddPackage.LDM_CONNECTION_CONFIG__MIN_SIZE:
				return basicSetMinSize(null, msgs);
			case CddPackage.LDM_CONNECTION_CONFIG__MAX_SIZE:
				return basicSetMaxSize(null, msgs);
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
			case CddPackage.LDM_CONNECTION_CONFIG__LDM_URL:
				return getLdmUrl();
			case CddPackage.LDM_CONNECTION_CONFIG__USER_NAME:
				return getUserName();
			case CddPackage.LDM_CONNECTION_CONFIG__USER_PASSWORD:
				return getUserPassword();
			case CddPackage.LDM_CONNECTION_CONFIG__INITIAL_SIZE:
				return getInitialSize();
			case CddPackage.LDM_CONNECTION_CONFIG__MIN_SIZE:
				return getMinSize();
			case CddPackage.LDM_CONNECTION_CONFIG__MAX_SIZE:
				return getMaxSize();
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
			case CddPackage.LDM_CONNECTION_CONFIG__LDM_URL:
				setLdmUrl((OverrideConfig)newValue);
				return;
			case CddPackage.LDM_CONNECTION_CONFIG__USER_NAME:
				setUserName((SecurityOverrideConfig)newValue);
				return;
			case CddPackage.LDM_CONNECTION_CONFIG__USER_PASSWORD:
				setUserPassword((SecurityOverrideConfig)newValue);
				return;
			case CddPackage.LDM_CONNECTION_CONFIG__INITIAL_SIZE:
				setInitialSize((OverrideConfig)newValue);
				return;
			case CddPackage.LDM_CONNECTION_CONFIG__MIN_SIZE:
				setMinSize((OverrideConfig)newValue);
				return;
			case CddPackage.LDM_CONNECTION_CONFIG__MAX_SIZE:
				setMaxSize((OverrideConfig)newValue);
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
			case CddPackage.LDM_CONNECTION_CONFIG__LDM_URL:
				setLdmUrl((OverrideConfig)null);
				return;
			case CddPackage.LDM_CONNECTION_CONFIG__USER_NAME:
				setUserName((SecurityOverrideConfig)null);
				return;
			case CddPackage.LDM_CONNECTION_CONFIG__USER_PASSWORD:
				setUserPassword((SecurityOverrideConfig)null);
				return;
			case CddPackage.LDM_CONNECTION_CONFIG__INITIAL_SIZE:
				setInitialSize((OverrideConfig)null);
				return;
			case CddPackage.LDM_CONNECTION_CONFIG__MIN_SIZE:
				setMinSize((OverrideConfig)null);
				return;
			case CddPackage.LDM_CONNECTION_CONFIG__MAX_SIZE:
				setMaxSize((OverrideConfig)null);
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
			case CddPackage.LDM_CONNECTION_CONFIG__LDM_URL:
				return ldmUrl != null;
			case CddPackage.LDM_CONNECTION_CONFIG__USER_NAME:
				return userName != null;
			case CddPackage.LDM_CONNECTION_CONFIG__USER_PASSWORD:
				return userPassword != null;
			case CddPackage.LDM_CONNECTION_CONFIG__INITIAL_SIZE:
				return initialSize != null;
			case CddPackage.LDM_CONNECTION_CONFIG__MIN_SIZE:
				return minSize != null;
			case CddPackage.LDM_CONNECTION_CONFIG__MAX_SIZE:
				return maxSize != null;
		}
		return super.eIsSet(featureID);
	}

} //LDMConnectionConfigImpl
