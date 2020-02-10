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
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.LineLayoutConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Line Layout Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LineLayoutConfigImpl#getEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LineLayoutConfigImpl#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.LineLayoutConfigImpl#getArg <em>Arg</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LineLayoutConfigImpl extends EObjectImpl implements LineLayoutConfig {
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
	 * The cached value of the '{@link #getClass_() <em>Class</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClass_()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig class_;

	/**
	 * The cached value of the '{@link #getArg() <em>Arg</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArg()
	 * @generated
	 * @ordered
	 */
	protected EList<OverrideConfig> arg;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LineLayoutConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getLineLayoutConfig();
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LINE_LAYOUT_CONFIG__ENABLED, oldEnabled, newEnabled);
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
				msgs = ((InternalEObject)enabled).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LINE_LAYOUT_CONFIG__ENABLED, null, msgs);
			if (newEnabled != null)
				msgs = ((InternalEObject)newEnabled).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LINE_LAYOUT_CONFIG__ENABLED, null, msgs);
			msgs = basicSetEnabled(newEnabled, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LINE_LAYOUT_CONFIG__ENABLED, newEnabled, newEnabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getClass_() {
		return class_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetClass(OverrideConfig newClass, NotificationChain msgs) {
		OverrideConfig oldClass = class_;
		class_ = newClass;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.LINE_LAYOUT_CONFIG__CLASS, oldClass, newClass);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClass(OverrideConfig newClass) {
		if (newClass != class_) {
			NotificationChain msgs = null;
			if (class_ != null)
				msgs = ((InternalEObject)class_).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.LINE_LAYOUT_CONFIG__CLASS, null, msgs);
			if (newClass != null)
				msgs = ((InternalEObject)newClass).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.LINE_LAYOUT_CONFIG__CLASS, null, msgs);
			msgs = basicSetClass(newClass, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.LINE_LAYOUT_CONFIG__CLASS, newClass, newClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<OverrideConfig> getArg() {
		if (arg == null) {
			arg = new EObjectContainmentEList<OverrideConfig>(OverrideConfig.class, this, CddPackage.LINE_LAYOUT_CONFIG__ARG);
		}
		return arg;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.LINE_LAYOUT_CONFIG__ENABLED:
				return basicSetEnabled(null, msgs);
			case CddPackage.LINE_LAYOUT_CONFIG__CLASS:
				return basicSetClass(null, msgs);
			case CddPackage.LINE_LAYOUT_CONFIG__ARG:
				return ((InternalEList<?>)getArg()).basicRemove(otherEnd, msgs);
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
			case CddPackage.LINE_LAYOUT_CONFIG__ENABLED:
				return getEnabled();
			case CddPackage.LINE_LAYOUT_CONFIG__CLASS:
				return getClass_();
			case CddPackage.LINE_LAYOUT_CONFIG__ARG:
				return getArg();
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
			case CddPackage.LINE_LAYOUT_CONFIG__ENABLED:
				setEnabled((OverrideConfig)newValue);
				return;
			case CddPackage.LINE_LAYOUT_CONFIG__CLASS:
				setClass((OverrideConfig)newValue);
				return;
			case CddPackage.LINE_LAYOUT_CONFIG__ARG:
				getArg().clear();
				getArg().addAll((Collection<? extends OverrideConfig>)newValue);
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
			case CddPackage.LINE_LAYOUT_CONFIG__ENABLED:
				setEnabled((OverrideConfig)null);
				return;
			case CddPackage.LINE_LAYOUT_CONFIG__CLASS:
				setClass((OverrideConfig)null);
				return;
			case CddPackage.LINE_LAYOUT_CONFIG__ARG:
				getArg().clear();
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
			case CddPackage.LINE_LAYOUT_CONFIG__ENABLED:
				return enabled != null;
			case CddPackage.LINE_LAYOUT_CONFIG__CLASS:
				return class_ != null;
			case CddPackage.LINE_LAYOUT_CONFIG__ARG:
				return arg != null && !arg.isEmpty();
		}
		return super.eIsSet(featureID);
	}
	
	/**
	 * @generated NOT
	 */
	@Override
	public Map<Object, Object> toProperties() {
		final Properties props = new Properties();
		
		CddTools.addEntryFromMixed(props, SystemProperty.TRACE_LAYOUT_ENABLED.getPropertyName(),
				this.getEnabled(), true, "false");

		CddTools.addEntryFromMixed(props, SystemProperty.TRACE_LAYOUT_CLASS_NAME.getPropertyName(),
				this.getClass_(), true);
		
		CddTools.addEntryFromJoinOfMixed(props, SystemProperty.TRACE_LAYOUT_CLASS_ARG.getPropertyName(),
				this.getArg(), " ", true);
		
		return props;
	}

} //LineLayoutConfigImpl
