/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.event.impl;

import java.util.ArrayList;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE;
import com.tibco.cep.designtime.core.model.event.EventPackage;
import com.tibco.cep.designtime.core.model.event.TimeEvent;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.designtime.core.model.validation.ValidationFactory;
import com.tibco.cep.studio.common.util.TimeOutUnitsUtils;
import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Time Event</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.TimeEventImpl#getScheduleType <em>Schedule Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.TimeEventImpl#getTimeEventCount <em>Time Event Count</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.TimeEventImpl#getInterval <em>Interval</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.impl.TimeEventImpl#getIntervalUnit <em>Interval Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TimeEventImpl extends EventImpl implements TimeEvent {
	/**
	 * The default value of the '{@link #getScheduleType() <em>Schedule Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScheduleType()
	 * @generated
	 * @ordered
	 */
	protected static final EVENT_SCHEDULE_TYPE SCHEDULE_TYPE_EDEFAULT = EVENT_SCHEDULE_TYPE.RULE_BASED;

	/**
	 * The cached value of the '{@link #getScheduleType() <em>Schedule Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScheduleType()
	 * @generated
	 * @ordered
	 */
	protected EVENT_SCHEDULE_TYPE scheduleType = SCHEDULE_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getTimeEventCount() <em>Time Event Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeEventCount()
	 * @generated
	 * @ordered
	 */
	protected static final String TIME_EVENT_COUNT_EDEFAULT = "0";

	/**
	 * The cached value of the '{@link #getTimeEventCount() <em>Time Event Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeEventCount()
	 * @generated
	 * @ordered
	 */
	protected String timeEventCount = TIME_EVENT_COUNT_EDEFAULT;

	/**
	 * The default value of the '{@link #getInterval() <em>Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterval()
	 * @generated
	 * @ordered
	 */
	protected static final String INTERVAL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInterval() <em>Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInterval()
	 * @generated
	 * @ordered
	 */
	protected String interval = INTERVAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getIntervalUnit() <em>Interval Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntervalUnit()
	 * @generated
	 * @ordered
	 */
	protected static final TIMEOUT_UNITS INTERVAL_UNIT_EDEFAULT = TIMEOUT_UNITS.MILLISECONDS;

	/**
	 * The cached value of the '{@link #getIntervalUnit() <em>Interval Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntervalUnit()
	 * @generated
	 * @ordered
	 */
	protected TIMEOUT_UNITS intervalUnit = INTERVAL_UNIT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TimeEventImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return EventPackage.Literals.TIME_EVENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EVENT_SCHEDULE_TYPE getScheduleType() {
		return scheduleType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScheduleType(EVENT_SCHEDULE_TYPE newScheduleType) {
		EVENT_SCHEDULE_TYPE oldScheduleType = scheduleType;
		scheduleType = newScheduleType == null ? SCHEDULE_TYPE_EDEFAULT : newScheduleType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.TIME_EVENT__SCHEDULE_TYPE, oldScheduleType, scheduleType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTimeEventCount() {
		return timeEventCount;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeEventCount(String newTimeEventCount) {
		String oldTimeEventCount = timeEventCount;
		timeEventCount = newTimeEventCount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.TIME_EVENT__TIME_EVENT_COUNT, oldTimeEventCount, timeEventCount));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInterval() {
		return interval;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInterval(String newInterval) {
		String oldInterval = interval;
		interval = newInterval;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.TIME_EVENT__INTERVAL, oldInterval, interval));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TIMEOUT_UNITS getIntervalUnit() {
		return intervalUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIntervalUnit(TIMEOUT_UNITS newIntervalUnit) {
		TIMEOUT_UNITS oldIntervalUnit = intervalUnit;
		intervalUnit = newIntervalUnit == null ? INTERVAL_UNIT_EDEFAULT : newIntervalUnit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, EventPackage.TIME_EVENT__INTERVAL_UNIT, oldIntervalUnit, intervalUnit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case EventPackage.TIME_EVENT__SCHEDULE_TYPE:
				return getScheduleType();
			case EventPackage.TIME_EVENT__TIME_EVENT_COUNT:
				return getTimeEventCount();
			case EventPackage.TIME_EVENT__INTERVAL:
				return getInterval();
			case EventPackage.TIME_EVENT__INTERVAL_UNIT:
				return getIntervalUnit();
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
			case EventPackage.TIME_EVENT__SCHEDULE_TYPE:
				setScheduleType((EVENT_SCHEDULE_TYPE)newValue);
				return;
			case EventPackage.TIME_EVENT__TIME_EVENT_COUNT:
				setTimeEventCount((String)newValue);
				return;
			case EventPackage.TIME_EVENT__INTERVAL:
				setInterval((String)newValue);
				return;
			case EventPackage.TIME_EVENT__INTERVAL_UNIT:
				setIntervalUnit((TIMEOUT_UNITS)newValue);
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
			case EventPackage.TIME_EVENT__SCHEDULE_TYPE:
				setScheduleType(SCHEDULE_TYPE_EDEFAULT);
				return;
			case EventPackage.TIME_EVENT__TIME_EVENT_COUNT:
				setTimeEventCount(TIME_EVENT_COUNT_EDEFAULT);
				return;
			case EventPackage.TIME_EVENT__INTERVAL:
				setInterval(INTERVAL_EDEFAULT);
				return;
			case EventPackage.TIME_EVENT__INTERVAL_UNIT:
				setIntervalUnit(INTERVAL_UNIT_EDEFAULT);
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
			case EventPackage.TIME_EVENT__SCHEDULE_TYPE:
				return scheduleType != SCHEDULE_TYPE_EDEFAULT;
			case EventPackage.TIME_EVENT__TIME_EVENT_COUNT:
				return TIME_EVENT_COUNT_EDEFAULT == null ? timeEventCount != null : !TIME_EVENT_COUNT_EDEFAULT.equals(timeEventCount); 
			case EventPackage.TIME_EVENT__INTERVAL:
				return INTERVAL_EDEFAULT == null ? interval != null : !INTERVAL_EDEFAULT.equals(interval);
			case EventPackage.TIME_EVENT__INTERVAL_UNIT:
				return intervalUnit != INTERVAL_UNIT_EDEFAULT;
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
		result.append(" (scheduleType: ");
		result.append(scheduleType);
		result.append(", timeEventCount: ");
		result.append(timeEventCount);
		result.append(", interval: ");
		result.append(interval);
		result.append(", intervalUnit: ");
		result.append(intervalUnit);
		result.append(')');
		return result.toString();
	}
	
	//Added by Anand - 01/17/2011 to fix BE-10395
	/**
	 * @generated not
	 */
	@Override
	public EList<ModelError> getModelErrors() {
		EList<ModelError> errorList = super.getModelErrors();
		if (getScheduleType().compareTo(EVENT_SCHEDULE_TYPE.REPEAT) == 0) {
			if (TimeOutUnitsUtils.isValid(getIntervalUnit()) == false) {
				ModelError error = ValidationFactory.eINSTANCE.createModelError();
				List<Object> args = new ArrayList<Object>();
				args.add(getFullPath());
				String formattedMsg = CommonValidationUtils.formatMessage("TimerEventResource.invalidTimeIntervalUnits", args);
				error.setMessage(formattedMsg);
				error.setSource(this);
				errorList.add(error);
			}
		}
		return errorList;
	}

} //TimeEventImpl
