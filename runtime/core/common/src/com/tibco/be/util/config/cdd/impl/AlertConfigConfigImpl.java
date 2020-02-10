/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.be.util.config.cdd.AlertConfigConfig;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.ConditionConfig;
import com.tibco.be.util.config.cdd.ProjectionConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Alert Config Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.AlertConfigConfigImpl#getCondition <em>Condition</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.AlertConfigConfigImpl#getProjection <em>Projection</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.AlertConfigConfigImpl#getAlertName <em>Alert Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AlertConfigConfigImpl extends EObjectImpl implements AlertConfigConfig {
	/**
	 * The cached value of the '{@link #getCondition() <em>Condition</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected ConditionConfig condition;

	/**
	 * The cached value of the '{@link #getProjection() <em>Projection</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProjection()
	 * @generated
	 * @ordered
	 */
	protected ProjectionConfig projection;

	/**
	 * The default value of the '{@link #getAlertName() <em>Alert Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlertName()
	 * @generated
	 * @ordered
	 */
	protected static final String ALERT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAlertName() <em>Alert Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlertName()
	 * @generated
	 * @ordered
	 */
	protected String alertName = ALERT_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AlertConfigConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getAlertConfigConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionConfig getCondition() {
		return condition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCondition(ConditionConfig newCondition, NotificationChain msgs) {
		ConditionConfig oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.ALERT_CONFIG_CONFIG__CONDITION, oldCondition, newCondition);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCondition(ConditionConfig newCondition) {
		if (newCondition != condition) {
			NotificationChain msgs = null;
			if (condition != null)
				msgs = ((InternalEObject)condition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.ALERT_CONFIG_CONFIG__CONDITION, null, msgs);
			if (newCondition != null)
				msgs = ((InternalEObject)newCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.ALERT_CONFIG_CONFIG__CONDITION, null, msgs);
			msgs = basicSetCondition(newCondition, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.ALERT_CONFIG_CONFIG__CONDITION, newCondition, newCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProjectionConfig getProjection() {
		return projection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProjection(ProjectionConfig newProjection, NotificationChain msgs) {
		ProjectionConfig oldProjection = projection;
		projection = newProjection;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.ALERT_CONFIG_CONFIG__PROJECTION, oldProjection, newProjection);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProjection(ProjectionConfig newProjection) {
		if (newProjection != projection) {
			NotificationChain msgs = null;
			if (projection != null)
				msgs = ((InternalEObject)projection).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.ALERT_CONFIG_CONFIG__PROJECTION, null, msgs);
			if (newProjection != null)
				msgs = ((InternalEObject)newProjection).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.ALERT_CONFIG_CONFIG__PROJECTION, null, msgs);
			msgs = basicSetProjection(newProjection, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.ALERT_CONFIG_CONFIG__PROJECTION, newProjection, newProjection));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAlertName() {
		return alertName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlertName(String newAlertName) {
		String oldAlertName = alertName;
		alertName = newAlertName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.ALERT_CONFIG_CONFIG__ALERT_NAME, oldAlertName, alertName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.ALERT_CONFIG_CONFIG__CONDITION:
				return basicSetCondition(null, msgs);
			case CddPackage.ALERT_CONFIG_CONFIG__PROJECTION:
				return basicSetProjection(null, msgs);
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
			case CddPackage.ALERT_CONFIG_CONFIG__CONDITION:
				return getCondition();
			case CddPackage.ALERT_CONFIG_CONFIG__PROJECTION:
				return getProjection();
			case CddPackage.ALERT_CONFIG_CONFIG__ALERT_NAME:
				return getAlertName();
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
			case CddPackage.ALERT_CONFIG_CONFIG__CONDITION:
				setCondition((ConditionConfig)newValue);
				return;
			case CddPackage.ALERT_CONFIG_CONFIG__PROJECTION:
				setProjection((ProjectionConfig)newValue);
				return;
			case CddPackage.ALERT_CONFIG_CONFIG__ALERT_NAME:
				setAlertName((String)newValue);
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
			case CddPackage.ALERT_CONFIG_CONFIG__CONDITION:
				setCondition((ConditionConfig)null);
				return;
			case CddPackage.ALERT_CONFIG_CONFIG__PROJECTION:
				setProjection((ProjectionConfig)null);
				return;
			case CddPackage.ALERT_CONFIG_CONFIG__ALERT_NAME:
				setAlertName(ALERT_NAME_EDEFAULT);
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
			case CddPackage.ALERT_CONFIG_CONFIG__CONDITION:
				return condition != null;
			case CddPackage.ALERT_CONFIG_CONFIG__PROJECTION:
				return projection != null;
			case CddPackage.ALERT_CONFIG_CONFIG__ALERT_NAME:
				return ALERT_NAME_EDEFAULT == null ? alertName != null : !ALERT_NAME_EDEFAULT.equals(alertName);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (alertName: ");
		result.append(alertName);
		result.append(')');
		return result.toString();
	}

} //AlertConfigConfigImpl
