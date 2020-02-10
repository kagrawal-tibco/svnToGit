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
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.FilesConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Files Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.FilesConfigImpl#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.FilesConfigImpl#getDir <em>Dir</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.FilesConfigImpl#getMaxNumber <em>Max Number</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.FilesConfigImpl#getMaxSize <em>Max Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.FilesConfigImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.FilesConfigImpl#getAppend <em>Append</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FilesConfigImpl extends EObjectImpl implements FilesConfig {
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
	 * The cached value of the '{@link #getDir() <em>Dir</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDir()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig dir;

	/**
	 * The cached value of the '{@link #getMaxNumber() <em>Max Number</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxNumber()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig maxNumber;

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
	 * The cached value of the '{@link #getName() <em>Name</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig name;

	/**
	 * The cached value of the '{@link #getAppend() <em>Append</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAppend()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig append;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FilesConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getFilesConfig();
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.FILES_CONFIG__ENABLED, oldEnabled, newEnabled);
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
				msgs = ((InternalEObject)enabled).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.FILES_CONFIG__ENABLED, null, msgs);
			if (newEnabled != null)
				msgs = ((InternalEObject)newEnabled).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.FILES_CONFIG__ENABLED, null, msgs);
			msgs = basicSetEnabled(newEnabled, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.FILES_CONFIG__ENABLED, newEnabled, newEnabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getDir() {
		return dir;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDir(OverrideConfig newDir, NotificationChain msgs) {
		OverrideConfig oldDir = dir;
		dir = newDir;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.FILES_CONFIG__DIR, oldDir, newDir);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDir(OverrideConfig newDir) {
		if (newDir != dir) {
			NotificationChain msgs = null;
			if (dir != null)
				msgs = ((InternalEObject)dir).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.FILES_CONFIG__DIR, null, msgs);
			if (newDir != null)
				msgs = ((InternalEObject)newDir).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.FILES_CONFIG__DIR, null, msgs);
			msgs = basicSetDir(newDir, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.FILES_CONFIG__DIR, newDir, newDir));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getMaxNumber() {
		return maxNumber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMaxNumber(OverrideConfig newMaxNumber, NotificationChain msgs) {
		OverrideConfig oldMaxNumber = maxNumber;
		maxNumber = newMaxNumber;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.FILES_CONFIG__MAX_NUMBER, oldMaxNumber, newMaxNumber);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxNumber(OverrideConfig newMaxNumber) {
		if (newMaxNumber != maxNumber) {
			NotificationChain msgs = null;
			if (maxNumber != null)
				msgs = ((InternalEObject)maxNumber).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.FILES_CONFIG__MAX_NUMBER, null, msgs);
			if (newMaxNumber != null)
				msgs = ((InternalEObject)newMaxNumber).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.FILES_CONFIG__MAX_NUMBER, null, msgs);
			msgs = basicSetMaxNumber(newMaxNumber, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.FILES_CONFIG__MAX_NUMBER, newMaxNumber, newMaxNumber));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.FILES_CONFIG__MAX_SIZE, oldMaxSize, newMaxSize);
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
				msgs = ((InternalEObject)maxSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.FILES_CONFIG__MAX_SIZE, null, msgs);
			if (newMaxSize != null)
				msgs = ((InternalEObject)newMaxSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.FILES_CONFIG__MAX_SIZE, null, msgs);
			msgs = basicSetMaxSize(newMaxSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.FILES_CONFIG__MAX_SIZE, newMaxSize, newMaxSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetName(OverrideConfig newName, NotificationChain msgs) {
		OverrideConfig oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.FILES_CONFIG__NAME, oldName, newName);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(OverrideConfig newName) {
		if (newName != name) {
			NotificationChain msgs = null;
			if (name != null)
				msgs = ((InternalEObject)name).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.FILES_CONFIG__NAME, null, msgs);
			if (newName != null)
				msgs = ((InternalEObject)newName).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.FILES_CONFIG__NAME, null, msgs);
			msgs = basicSetName(newName, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.FILES_CONFIG__NAME, newName, newName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getAppend() {
		return append;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAppend(OverrideConfig newAppend, NotificationChain msgs) {
		OverrideConfig oldAppend = append;
		append = newAppend;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.FILES_CONFIG__APPEND, oldAppend, newAppend);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAppend(OverrideConfig newAppend) {
		if (newAppend != append) {
			NotificationChain msgs = null;
			if (append != null)
				msgs = ((InternalEObject)append).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.FILES_CONFIG__APPEND, null, msgs);
			if (newAppend != null)
				msgs = ((InternalEObject)newAppend).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.FILES_CONFIG__APPEND, null, msgs);
			msgs = basicSetAppend(newAppend, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.FILES_CONFIG__APPEND, newAppend, newAppend));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.FILES_CONFIG__ENABLED:
				return basicSetEnabled(null, msgs);
			case CddPackage.FILES_CONFIG__DIR:
				return basicSetDir(null, msgs);
			case CddPackage.FILES_CONFIG__MAX_NUMBER:
				return basicSetMaxNumber(null, msgs);
			case CddPackage.FILES_CONFIG__MAX_SIZE:
				return basicSetMaxSize(null, msgs);
			case CddPackage.FILES_CONFIG__NAME:
				return basicSetName(null, msgs);
			case CddPackage.FILES_CONFIG__APPEND:
				return basicSetAppend(null, msgs);
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
			case CddPackage.FILES_CONFIG__ENABLED:
				return getEnabled();
			case CddPackage.FILES_CONFIG__DIR:
				return getDir();
			case CddPackage.FILES_CONFIG__MAX_NUMBER:
				return getMaxNumber();
			case CddPackage.FILES_CONFIG__MAX_SIZE:
				return getMaxSize();
			case CddPackage.FILES_CONFIG__NAME:
				return getName();
			case CddPackage.FILES_CONFIG__APPEND:
				return getAppend();
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
			case CddPackage.FILES_CONFIG__ENABLED:
				setEnabled((OverrideConfig)newValue);
				return;
			case CddPackage.FILES_CONFIG__DIR:
				setDir((OverrideConfig)newValue);
				return;
			case CddPackage.FILES_CONFIG__MAX_NUMBER:
				setMaxNumber((OverrideConfig)newValue);
				return;
			case CddPackage.FILES_CONFIG__MAX_SIZE:
				setMaxSize((OverrideConfig)newValue);
				return;
			case CddPackage.FILES_CONFIG__NAME:
				setName((OverrideConfig)newValue);
				return;
			case CddPackage.FILES_CONFIG__APPEND:
				setAppend((OverrideConfig)newValue);
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
			case CddPackage.FILES_CONFIG__ENABLED:
				setEnabled((OverrideConfig)null);
				return;
			case CddPackage.FILES_CONFIG__DIR:
				setDir((OverrideConfig)null);
				return;
			case CddPackage.FILES_CONFIG__MAX_NUMBER:
				setMaxNumber((OverrideConfig)null);
				return;
			case CddPackage.FILES_CONFIG__MAX_SIZE:
				setMaxSize((OverrideConfig)null);
				return;
			case CddPackage.FILES_CONFIG__NAME:
				setName((OverrideConfig)null);
				return;
			case CddPackage.FILES_CONFIG__APPEND:
				setAppend((OverrideConfig)null);
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
			case CddPackage.FILES_CONFIG__ENABLED:
				return enabled != null;
			case CddPackage.FILES_CONFIG__DIR:
				return dir != null;
			case CddPackage.FILES_CONFIG__MAX_NUMBER:
				return maxNumber != null;
			case CddPackage.FILES_CONFIG__MAX_SIZE:
				return maxSize != null;
			case CddPackage.FILES_CONFIG__NAME:
				return name != null;
			case CddPackage.FILES_CONFIG__APPEND:
				return append != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public Map<Object, Object> toProperties() {
		final Properties props = new Properties();

		CddTools.addEntryFromMixed(props,
				SystemProperty.TRACE_FILE_APPEND.getPropertyName(),
				this.getAppend(), true, "true");

		CddTools.addEntryFromMixed(props,
				SystemProperty.TRACE_FILE_DIR.getPropertyName(),
				this.getDir(), true);

		CddTools.addEntryFromMixed(props,
				SystemProperty.TRACE_FILE_ENABLED.getPropertyName(),
				this.getEnabled(), true);

		CddTools.addEntryFromMixed(props,
				SystemProperty.TRACE_FILE_MAX_NUM.getPropertyName(),
				this.getMaxNumber(), true, "10");

		CddTools.addEntryFromMixed(props,
				SystemProperty.TRACE_FILE_MAX_SIZE.getPropertyName(),
				this.getMaxSize(), true, "1000000");

		CddTools.addEntryFromMixed(props,
				SystemProperty.TRACE_FILE_NAME.getPropertyName(),
				this.getName(), true);
		
		return props;
	}	
	
} //FilesConfigImpl
