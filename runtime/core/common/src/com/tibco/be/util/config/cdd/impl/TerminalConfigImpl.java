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
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.be.util.config.cdd.TerminalConfig;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Terminal Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.TerminalConfigImpl#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.TerminalConfigImpl#getSysErrRedirect <em>Sys Err Redirect</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.TerminalConfigImpl#getSysOutRedirect <em>Sys Out Redirect</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.TerminalConfigImpl#getEncoding <em>Encoding</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TerminalConfigImpl extends EObjectImpl implements TerminalConfig {
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
	 * The cached value of the '{@link #getSysErrRedirect() <em>Sys Err Redirect</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSysErrRedirect()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig sysErrRedirect;

	/**
	 * The cached value of the '{@link #getSysOutRedirect() <em>Sys Out Redirect</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSysOutRedirect()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig sysOutRedirect;

	/**
	 * The cached value of the '{@link #getEncoding() <em>Encoding</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEncoding()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig encoding;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TerminalConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getTerminalConfig();
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.TERMINAL_CONFIG__ENABLED, oldEnabled, newEnabled);
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
				msgs = ((InternalEObject)enabled).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.TERMINAL_CONFIG__ENABLED, null, msgs);
			if (newEnabled != null)
				msgs = ((InternalEObject)newEnabled).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.TERMINAL_CONFIG__ENABLED, null, msgs);
			msgs = basicSetEnabled(newEnabled, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.TERMINAL_CONFIG__ENABLED, newEnabled, newEnabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getSysErrRedirect() {
		return sysErrRedirect;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSysErrRedirect(OverrideConfig newSysErrRedirect, NotificationChain msgs) {
		OverrideConfig oldSysErrRedirect = sysErrRedirect;
		sysErrRedirect = newSysErrRedirect;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.TERMINAL_CONFIG__SYS_ERR_REDIRECT, oldSysErrRedirect, newSysErrRedirect);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSysErrRedirect(OverrideConfig newSysErrRedirect) {
		if (newSysErrRedirect != sysErrRedirect) {
			NotificationChain msgs = null;
			if (sysErrRedirect != null)
				msgs = ((InternalEObject)sysErrRedirect).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.TERMINAL_CONFIG__SYS_ERR_REDIRECT, null, msgs);
			if (newSysErrRedirect != null)
				msgs = ((InternalEObject)newSysErrRedirect).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.TERMINAL_CONFIG__SYS_ERR_REDIRECT, null, msgs);
			msgs = basicSetSysErrRedirect(newSysErrRedirect, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.TERMINAL_CONFIG__SYS_ERR_REDIRECT, newSysErrRedirect, newSysErrRedirect));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getSysOutRedirect() {
		return sysOutRedirect;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSysOutRedirect(OverrideConfig newSysOutRedirect, NotificationChain msgs) {
		OverrideConfig oldSysOutRedirect = sysOutRedirect;
		sysOutRedirect = newSysOutRedirect;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.TERMINAL_CONFIG__SYS_OUT_REDIRECT, oldSysOutRedirect, newSysOutRedirect);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSysOutRedirect(OverrideConfig newSysOutRedirect) {
		if (newSysOutRedirect != sysOutRedirect) {
			NotificationChain msgs = null;
			if (sysOutRedirect != null)
				msgs = ((InternalEObject)sysOutRedirect).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.TERMINAL_CONFIG__SYS_OUT_REDIRECT, null, msgs);
			if (newSysOutRedirect != null)
				msgs = ((InternalEObject)newSysOutRedirect).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.TERMINAL_CONFIG__SYS_OUT_REDIRECT, null, msgs);
			msgs = basicSetSysOutRedirect(newSysOutRedirect, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.TERMINAL_CONFIG__SYS_OUT_REDIRECT, newSysOutRedirect, newSysOutRedirect));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getEncoding() {
		return encoding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEncoding(OverrideConfig newEncoding, NotificationChain msgs) {
		OverrideConfig oldEncoding = encoding;
		encoding = newEncoding;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.TERMINAL_CONFIG__ENCODING, oldEncoding, newEncoding);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEncoding(OverrideConfig newEncoding) {
		if (newEncoding != encoding) {
			NotificationChain msgs = null;
			if (encoding != null)
				msgs = ((InternalEObject)encoding).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.TERMINAL_CONFIG__ENCODING, null, msgs);
			if (newEncoding != null)
				msgs = ((InternalEObject)newEncoding).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.TERMINAL_CONFIG__ENCODING, null, msgs);
			msgs = basicSetEncoding(newEncoding, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.TERMINAL_CONFIG__ENCODING, newEncoding, newEncoding));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.TERMINAL_CONFIG__ENABLED:
				return basicSetEnabled(null, msgs);
			case CddPackage.TERMINAL_CONFIG__SYS_ERR_REDIRECT:
				return basicSetSysErrRedirect(null, msgs);
			case CddPackage.TERMINAL_CONFIG__SYS_OUT_REDIRECT:
				return basicSetSysOutRedirect(null, msgs);
			case CddPackage.TERMINAL_CONFIG__ENCODING:
				return basicSetEncoding(null, msgs);
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
			case CddPackage.TERMINAL_CONFIG__ENABLED:
				return getEnabled();
			case CddPackage.TERMINAL_CONFIG__SYS_ERR_REDIRECT:
				return getSysErrRedirect();
			case CddPackage.TERMINAL_CONFIG__SYS_OUT_REDIRECT:
				return getSysOutRedirect();
			case CddPackage.TERMINAL_CONFIG__ENCODING:
				return getEncoding();
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
			case CddPackage.TERMINAL_CONFIG__ENABLED:
				setEnabled((OverrideConfig)newValue);
				return;
			case CddPackage.TERMINAL_CONFIG__SYS_ERR_REDIRECT:
				setSysErrRedirect((OverrideConfig)newValue);
				return;
			case CddPackage.TERMINAL_CONFIG__SYS_OUT_REDIRECT:
				setSysOutRedirect((OverrideConfig)newValue);
				return;
			case CddPackage.TERMINAL_CONFIG__ENCODING:
				setEncoding((OverrideConfig)newValue);
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
			case CddPackage.TERMINAL_CONFIG__ENABLED:
				setEnabled((OverrideConfig)null);
				return;
			case CddPackage.TERMINAL_CONFIG__SYS_ERR_REDIRECT:
				setSysErrRedirect((OverrideConfig)null);
				return;
			case CddPackage.TERMINAL_CONFIG__SYS_OUT_REDIRECT:
				setSysOutRedirect((OverrideConfig)null);
				return;
			case CddPackage.TERMINAL_CONFIG__ENCODING:
				setEncoding((OverrideConfig)null);
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
			case CddPackage.TERMINAL_CONFIG__ENABLED:
				return enabled != null;
			case CddPackage.TERMINAL_CONFIG__SYS_ERR_REDIRECT:
				return sysErrRedirect != null;
			case CddPackage.TERMINAL_CONFIG__SYS_OUT_REDIRECT:
				return sysOutRedirect != null;
			case CddPackage.TERMINAL_CONFIG__ENCODING:
				return encoding != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public Map<Object, Object> toProperties() {
		final Properties props = new Properties();
		CddTools.addEntryFromMixed(props, SystemProperty.TRACE_TERM_ENABLED.getPropertyName(),
				this.getEnabled(), true, "false");
		CddTools.addEntryFromMixed(props, SystemProperty.TRACE_TERM_ENCODING.getPropertyName(),
				this.getEncoding(), true);
		CddTools.addEntryFromMixed(props, SystemProperty.TRACE_TERM_SYSERR_REDIRECT.getPropertyName(),
				this.getSysErrRedirect(), true, "true");
		CddTools.addEntryFromMixed(props, SystemProperty.TRACE_TERM_SYSOUT_REDIRECT.getPropertyName(),
				this.getSysOutRedirect(), true, "true");
		return props;
	}

} //TerminalConfigImpl
