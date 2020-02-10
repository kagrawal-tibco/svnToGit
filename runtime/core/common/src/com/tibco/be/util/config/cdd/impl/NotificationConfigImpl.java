/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.NotificationConfig;
import com.tibco.be.util.config.cdd.PropertyConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Notification Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.NotificationConfigImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.NotificationConfigImpl#getRange <em>Range</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.NotificationConfigImpl#getTolerance <em>Tolerance</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NotificationConfigImpl extends EObjectImpl implements NotificationConfig {
	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyConfig> property;

	/**
	 * The default value of the '{@link #getRange() <em>Range</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRange()
	 * @generated
	 * @ordered
	 */
	protected static final String RANGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRange() <em>Range</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRange()
	 * @generated
	 * @ordered
	 */
	protected String range = RANGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getTolerance() <em>Tolerance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTolerance()
	 * @generated
	 * @ordered
	 */
	protected static final String TOLERANCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTolerance() <em>Tolerance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTolerance()
	 * @generated
	 * @ordered
	 */
	protected String tolerance = TOLERANCE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NotificationConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getNotificationConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyConfig> getProperty() {
		if (property == null) {
			property = new EObjectContainmentEList<PropertyConfig>(PropertyConfig.class, this, CddPackage.NOTIFICATION_CONFIG__PROPERTY);
		}
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRange() {
		return range;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRange(String newRange) {
		String oldRange = range;
		range = newRange;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.NOTIFICATION_CONFIG__RANGE, oldRange, range));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTolerance() {
		return tolerance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTolerance(String newTolerance) {
		String oldTolerance = tolerance;
		tolerance = newTolerance;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.NOTIFICATION_CONFIG__TOLERANCE, oldTolerance, tolerance));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.NOTIFICATION_CONFIG__PROPERTY:
				return ((InternalEList<?>)getProperty()).basicRemove(otherEnd, msgs);
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
			case CddPackage.NOTIFICATION_CONFIG__PROPERTY:
				return getProperty();
			case CddPackage.NOTIFICATION_CONFIG__RANGE:
				return getRange();
			case CddPackage.NOTIFICATION_CONFIG__TOLERANCE:
				return getTolerance();
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
			case CddPackage.NOTIFICATION_CONFIG__PROPERTY:
				getProperty().clear();
				getProperty().addAll((Collection<? extends PropertyConfig>)newValue);
				return;
			case CddPackage.NOTIFICATION_CONFIG__RANGE:
				setRange((String)newValue);
				return;
			case CddPackage.NOTIFICATION_CONFIG__TOLERANCE:
				setTolerance((String)newValue);
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
			case CddPackage.NOTIFICATION_CONFIG__PROPERTY:
				getProperty().clear();
				return;
			case CddPackage.NOTIFICATION_CONFIG__RANGE:
				setRange(RANGE_EDEFAULT);
				return;
			case CddPackage.NOTIFICATION_CONFIG__TOLERANCE:
				setTolerance(TOLERANCE_EDEFAULT);
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
			case CddPackage.NOTIFICATION_CONFIG__PROPERTY:
				return property != null && !property.isEmpty();
			case CddPackage.NOTIFICATION_CONFIG__RANGE:
				return RANGE_EDEFAULT == null ? range != null : !RANGE_EDEFAULT.equals(range);
			case CddPackage.NOTIFICATION_CONFIG__TOLERANCE:
				return TOLERANCE_EDEFAULT == null ? tolerance != null : !TOLERANCE_EDEFAULT.equals(tolerance);
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
		result.append(" (range: ");
		result.append(range);
		result.append(", tolerance: ");
		result.append(tolerance);
		result.append(')');
		return result.toString();
	}

} //NotificationConfigImpl
