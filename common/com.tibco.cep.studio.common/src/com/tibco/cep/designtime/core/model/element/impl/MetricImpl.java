/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.element.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.METRIC_TYPE;
import com.tibco.cep.designtime.core.model.TIME_UNITS;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metric</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.MetricImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.MetricImpl#getWindowType <em>Window Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.MetricImpl#getWindowSize <em>Window Size</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.MetricImpl#getRecurringTimeType <em>Recurring Time Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.MetricImpl#getRecurringFrequency <em>Recurring Frequency</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.MetricImpl#getStartTime <em>Start Time</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.MetricImpl#getShortName <em>Short Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.MetricImpl#isPersistent <em>Persistent</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.MetricImpl#isTransactionalRolling <em>Transactional Rolling</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.MetricImpl#isEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.MetricImpl#getRetentionTimeType <em>Retention Time Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.MetricImpl#getRetentionTimeSize <em>Retention Time Size</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.MetricImpl#getUserDefinedFields <em>User Defined Fields</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MetricImpl extends ConceptImpl implements Metric {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final METRIC_TYPE TYPE_EDEFAULT = METRIC_TYPE.REGULAR;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected METRIC_TYPE type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getWindowType() <em>Window Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWindowType()
	 * @generated
	 * @ordered
	 */
	protected static final TIME_UNITS WINDOW_TYPE_EDEFAULT = TIME_UNITS.MILLISECONDS;

	/**
	 * The cached value of the '{@link #getWindowType() <em>Window Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWindowType()
	 * @generated
	 * @ordered
	 */
	protected TIME_UNITS windowType = WINDOW_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getWindowSize() <em>Window Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWindowSize()
	 * @generated
	 * @ordered
	 */
	protected static final long WINDOW_SIZE_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getWindowSize() <em>Window Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWindowSize()
	 * @generated
	 * @ordered
	 */
	protected long windowSize = WINDOW_SIZE_EDEFAULT;

	/**
	 * The default value of the '{@link #getRecurringTimeType() <em>Recurring Time Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecurringTimeType()
	 * @generated
	 * @ordered
	 */
	protected static final TIME_UNITS RECURRING_TIME_TYPE_EDEFAULT = TIME_UNITS.MILLISECONDS;

	/**
	 * The cached value of the '{@link #getRecurringTimeType() <em>Recurring Time Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecurringTimeType()
	 * @generated
	 * @ordered
	 */
	protected TIME_UNITS recurringTimeType = RECURRING_TIME_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getRecurringFrequency() <em>Recurring Frequency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecurringFrequency()
	 * @generated
	 * @ordered
	 */
	protected static final long RECURRING_FREQUENCY_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getRecurringFrequency() <em>Recurring Frequency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRecurringFrequency()
	 * @generated
	 * @ordered
	 */
	protected long recurringFrequency = RECURRING_FREQUENCY_EDEFAULT;

	/**
	 * The default value of the '{@link #getStartTime() <em>Start Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartTime()
	 * @generated
	 * @ordered
	 */
	protected static final long START_TIME_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getStartTime() <em>Start Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartTime()
	 * @generated
	 * @ordered
	 */
	protected long startTime = START_TIME_EDEFAULT;

	/**
	 * The default value of the '{@link #getShortName() <em>Short Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShortName()
	 * @generated
	 * @ordered
	 */
	protected static final String SHORT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getShortName() <em>Short Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShortName()
	 * @generated
	 * @ordered
	 */
	protected String shortName = SHORT_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isPersistent() <em>Persistent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPersistent()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PERSISTENT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPersistent() <em>Persistent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPersistent()
	 * @generated
	 * @ordered
	 */
	protected boolean persistent = PERSISTENT_EDEFAULT;

	/**
	 * The default value of the '{@link #isTransactionalRolling() <em>Transactional Rolling</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTransactionalRolling()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TRANSACTIONAL_ROLLING_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTransactionalRolling() <em>Transactional Rolling</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTransactionalRolling()
	 * @generated
	 * @ordered
	 */
	protected boolean transactionalRolling = TRANSACTIONAL_ROLLING_EDEFAULT;

	/**
	 * The default value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ENABLED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isEnabled() <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEnabled()
	 * @generated
	 * @ordered
	 */
	protected boolean enabled = ENABLED_EDEFAULT;

	/**
	 * The default value of the '{@link #getRetentionTimeType() <em>Retention Time Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRetentionTimeType()
	 * @generated
	 * @ordered
	 */
	protected static final TIME_UNITS RETENTION_TIME_TYPE_EDEFAULT = TIME_UNITS.MILLISECONDS;

	/**
	 * The cached value of the '{@link #getRetentionTimeType() <em>Retention Time Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRetentionTimeType()
	 * @generated
	 * @ordered
	 */
	protected TIME_UNITS retentionTimeType = RETENTION_TIME_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getRetentionTimeSize() <em>Retention Time Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRetentionTimeSize()
	 * @generated
	 * @ordered
	 */
	protected static final long RETENTION_TIME_SIZE_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getRetentionTimeSize() <em>Retention Time Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRetentionTimeSize()
	 * @generated
	 * @ordered
	 */
	protected long retentionTimeSize = RETENTION_TIME_SIZE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getUserDefinedFields() <em>User Defined Fields</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUserDefinedFields()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyDefinition> userDefinedFields;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetricImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ElementPackage.Literals.METRIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public METRIC_TYPE getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(METRIC_TYPE newType) {
		METRIC_TYPE oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.METRIC__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TIME_UNITS getWindowType() {
		return windowType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWindowType(TIME_UNITS newWindowType) {
		TIME_UNITS oldWindowType = windowType;
		windowType = newWindowType == null ? WINDOW_TYPE_EDEFAULT : newWindowType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.METRIC__WINDOW_TYPE, oldWindowType, windowType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getWindowSize() {
		return windowSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWindowSize(long newWindowSize) {
		long oldWindowSize = windowSize;
		windowSize = newWindowSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.METRIC__WINDOW_SIZE, oldWindowSize, windowSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TIME_UNITS getRecurringTimeType() {
		return recurringTimeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRecurringTimeType(TIME_UNITS newRecurringTimeType) {
		TIME_UNITS oldRecurringTimeType = recurringTimeType;
		recurringTimeType = newRecurringTimeType == null ? RECURRING_TIME_TYPE_EDEFAULT : newRecurringTimeType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.METRIC__RECURRING_TIME_TYPE, oldRecurringTimeType, recurringTimeType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getRecurringFrequency() {
		return recurringFrequency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRecurringFrequency(long newRecurringFrequency) {
		long oldRecurringFrequency = recurringFrequency;
		recurringFrequency = newRecurringFrequency;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.METRIC__RECURRING_FREQUENCY, oldRecurringFrequency, recurringFrequency));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartTime(long newStartTime) {
		long oldStartTime = startTime;
		startTime = newStartTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.METRIC__START_TIME, oldStartTime, startTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShortName(String newShortName) {
		String oldShortName = shortName;
		shortName = newShortName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.METRIC__SHORT_NAME, oldShortName, shortName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPersistent() {
		return persistent;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public boolean isMetric() {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPersistent(boolean newPersistent) {
		boolean oldPersistent = persistent;
		persistent = newPersistent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.METRIC__PERSISTENT, oldPersistent, persistent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTransactionalRolling() {
		return transactionalRolling;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransactionalRolling(boolean newTransactionalRolling) {
		boolean oldTransactionalRolling = transactionalRolling;
		transactionalRolling = newTransactionalRolling;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.METRIC__TRANSACTIONAL_ROLLING, oldTransactionalRolling, transactionalRolling));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnabled(boolean newEnabled) {
		boolean oldEnabled = enabled;
		enabled = newEnabled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.METRIC__ENABLED, oldEnabled, enabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TIME_UNITS getRetentionTimeType() {
		return retentionTimeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRetentionTimeType(TIME_UNITS newRetentionTimeType) {
		TIME_UNITS oldRetentionTimeType = retentionTimeType;
		retentionTimeType = newRetentionTimeType == null ? RETENTION_TIME_TYPE_EDEFAULT : newRetentionTimeType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.METRIC__RETENTION_TIME_TYPE, oldRetentionTimeType, retentionTimeType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getRetentionTimeSize() {
		return retentionTimeSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRetentionTimeSize(long newRetentionTimeSize) {
		long oldRetentionTimeSize = retentionTimeSize;
		retentionTimeSize = newRetentionTimeSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.METRIC__RETENTION_TIME_SIZE, oldRetentionTimeSize, retentionTimeSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyDefinition> getUserDefinedFields() {
		if (userDefinedFields == null) {
			userDefinedFields = new EObjectContainmentEList<PropertyDefinition>(PropertyDefinition.class, this, ElementPackage.METRIC__USER_DEFINED_FIELDS);
		}
		return userDefinedFields;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ElementPackage.METRIC__USER_DEFINED_FIELDS:
				return ((InternalEList<?>)getUserDefinedFields()).basicRemove(otherEnd, msgs);
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
			case ElementPackage.METRIC__TYPE:
				return getType();
			case ElementPackage.METRIC__WINDOW_TYPE:
				return getWindowType();
			case ElementPackage.METRIC__WINDOW_SIZE:
				return getWindowSize();
			case ElementPackage.METRIC__RECURRING_TIME_TYPE:
				return getRecurringTimeType();
			case ElementPackage.METRIC__RECURRING_FREQUENCY:
				return getRecurringFrequency();
			case ElementPackage.METRIC__START_TIME:
				return getStartTime();
			case ElementPackage.METRIC__SHORT_NAME:
				return getShortName();
			case ElementPackage.METRIC__PERSISTENT:
				return isPersistent();
			case ElementPackage.METRIC__TRANSACTIONAL_ROLLING:
				return isTransactionalRolling();
			case ElementPackage.METRIC__ENABLED:
				return isEnabled();
			case ElementPackage.METRIC__RETENTION_TIME_TYPE:
				return getRetentionTimeType();
			case ElementPackage.METRIC__RETENTION_TIME_SIZE:
				return getRetentionTimeSize();
			case ElementPackage.METRIC__USER_DEFINED_FIELDS:
				return getUserDefinedFields();
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
			case ElementPackage.METRIC__TYPE:
				setType((METRIC_TYPE)newValue);
				return;
			case ElementPackage.METRIC__WINDOW_TYPE:
				setWindowType((TIME_UNITS)newValue);
				return;
			case ElementPackage.METRIC__WINDOW_SIZE:
				setWindowSize((Long)newValue);
				return;
			case ElementPackage.METRIC__RECURRING_TIME_TYPE:
				setRecurringTimeType((TIME_UNITS)newValue);
				return;
			case ElementPackage.METRIC__RECURRING_FREQUENCY:
				setRecurringFrequency((Long)newValue);
				return;
			case ElementPackage.METRIC__START_TIME:
				setStartTime((Long)newValue);
				return;
			case ElementPackage.METRIC__SHORT_NAME:
				setShortName((String)newValue);
				return;
			case ElementPackage.METRIC__PERSISTENT:
				setPersistent((Boolean)newValue);
				return;
			case ElementPackage.METRIC__TRANSACTIONAL_ROLLING:
				setTransactionalRolling((Boolean)newValue);
				return;
			case ElementPackage.METRIC__ENABLED:
				setEnabled((Boolean)newValue);
				return;
			case ElementPackage.METRIC__RETENTION_TIME_TYPE:
				setRetentionTimeType((TIME_UNITS)newValue);
				return;
			case ElementPackage.METRIC__RETENTION_TIME_SIZE:
				setRetentionTimeSize((Long)newValue);
				return;
			case ElementPackage.METRIC__USER_DEFINED_FIELDS:
				getUserDefinedFields().clear();
				getUserDefinedFields().addAll((Collection<? extends PropertyDefinition>)newValue);
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
			case ElementPackage.METRIC__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case ElementPackage.METRIC__WINDOW_TYPE:
				setWindowType(WINDOW_TYPE_EDEFAULT);
				return;
			case ElementPackage.METRIC__WINDOW_SIZE:
				setWindowSize(WINDOW_SIZE_EDEFAULT);
				return;
			case ElementPackage.METRIC__RECURRING_TIME_TYPE:
				setRecurringTimeType(RECURRING_TIME_TYPE_EDEFAULT);
				return;
			case ElementPackage.METRIC__RECURRING_FREQUENCY:
				setRecurringFrequency(RECURRING_FREQUENCY_EDEFAULT);
				return;
			case ElementPackage.METRIC__START_TIME:
				setStartTime(START_TIME_EDEFAULT);
				return;
			case ElementPackage.METRIC__SHORT_NAME:
				setShortName(SHORT_NAME_EDEFAULT);
				return;
			case ElementPackage.METRIC__PERSISTENT:
				setPersistent(PERSISTENT_EDEFAULT);
				return;
			case ElementPackage.METRIC__TRANSACTIONAL_ROLLING:
				setTransactionalRolling(TRANSACTIONAL_ROLLING_EDEFAULT);
				return;
			case ElementPackage.METRIC__ENABLED:
				setEnabled(ENABLED_EDEFAULT);
				return;
			case ElementPackage.METRIC__RETENTION_TIME_TYPE:
				setRetentionTimeType(RETENTION_TIME_TYPE_EDEFAULT);
				return;
			case ElementPackage.METRIC__RETENTION_TIME_SIZE:
				setRetentionTimeSize(RETENTION_TIME_SIZE_EDEFAULT);
				return;
			case ElementPackage.METRIC__USER_DEFINED_FIELDS:
				getUserDefinedFields().clear();
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
			case ElementPackage.METRIC__TYPE:
				return type != TYPE_EDEFAULT;
			case ElementPackage.METRIC__WINDOW_TYPE:
				return windowType != WINDOW_TYPE_EDEFAULT;
			case ElementPackage.METRIC__WINDOW_SIZE:
				return windowSize != WINDOW_SIZE_EDEFAULT;
			case ElementPackage.METRIC__RECURRING_TIME_TYPE:
				return recurringTimeType != RECURRING_TIME_TYPE_EDEFAULT;
			case ElementPackage.METRIC__RECURRING_FREQUENCY:
				return recurringFrequency != RECURRING_FREQUENCY_EDEFAULT;
			case ElementPackage.METRIC__START_TIME:
				return startTime != START_TIME_EDEFAULT;
			case ElementPackage.METRIC__SHORT_NAME:
				return SHORT_NAME_EDEFAULT == null ? shortName != null : !SHORT_NAME_EDEFAULT.equals(shortName);
			case ElementPackage.METRIC__PERSISTENT:
				return persistent != PERSISTENT_EDEFAULT;
			case ElementPackage.METRIC__TRANSACTIONAL_ROLLING:
				return transactionalRolling != TRANSACTIONAL_ROLLING_EDEFAULT;
			case ElementPackage.METRIC__ENABLED:
				return enabled != ENABLED_EDEFAULT;
			case ElementPackage.METRIC__RETENTION_TIME_TYPE:
				return retentionTimeType != RETENTION_TIME_TYPE_EDEFAULT;
			case ElementPackage.METRIC__RETENTION_TIME_SIZE:
				return retentionTimeSize != RETENTION_TIME_SIZE_EDEFAULT;
			case ElementPackage.METRIC__USER_DEFINED_FIELDS:
				return userDefinedFields != null && !userDefinedFields.isEmpty();
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
		result.append(" (type: ");
		result.append(type);
		result.append(", windowType: ");
		result.append(windowType);
		result.append(", windowSize: ");
		result.append(windowSize);
		result.append(", recurringTimeType: ");
		result.append(recurringTimeType);
		result.append(", recurringFrequency: ");
		result.append(recurringFrequency);
		result.append(", startTime: ");
		result.append(startTime);
		result.append(", shortName: ");
		result.append(shortName);
		result.append(", persistent: ");
		result.append(persistent);
		result.append(", transactionalRolling: ");
		result.append(transactionalRolling);
		result.append(", enabled: ");
		result.append(enabled);
		result.append(", retentionTimeType: ");
		result.append(retentionTimeType);
		result.append(", retentionTimeSize: ");
		result.append(retentionTimeSize);
		result.append(')');
		return result.toString();
	}

} //MetricImpl
