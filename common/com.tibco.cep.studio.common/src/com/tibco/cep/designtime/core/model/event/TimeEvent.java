/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.event;

import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Time Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.TimeEvent#getScheduleType <em>Schedule Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.TimeEvent#getTimeEventCount <em>Time Event Count</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.TimeEvent#getInterval <em>Interval</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.TimeEvent#getIntervalUnit <em>Interval Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getTimeEvent()
 * @model
 * @generated
 */
public interface TimeEvent extends Event {
	/**
	 * Returns the value of the '<em><b>Schedule Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schedule Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schedule Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE
	 * @see #setScheduleType(EVENT_SCHEDULE_TYPE)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getTimeEvent_ScheduleType()
	 * @model required="true"
	 * @generated
	 */
	EVENT_SCHEDULE_TYPE getScheduleType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.TimeEvent#getScheduleType <em>Schedule Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schedule Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE
	 * @see #getScheduleType()
	 * @generated
	 */
	void setScheduleType(EVENT_SCHEDULE_TYPE value);

	/**
	 * Returns the value of the '<em><b>Time Event Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time Event Count</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Event Count</em>' attribute.
	 * @see #setTimeEventCount(long)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getTimeEvent_TimeEventCount()
	 * @model
	 * @generated
	 */
	String getTimeEventCount();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.TimeEvent#getTimeEventCount <em>Time Event Count</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Event Count</em>' attribute.
	 * @see #getTimeEventCount()
	 * @generated
	 */
	void setTimeEventCount(String value);

	/**
	 * Returns the value of the '<em><b>Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interval</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interval</em>' attribute.
	 * @see #setInterval(String)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getTimeEvent_Interval()
	 * @model
	 * @generated
	 */
	String getInterval();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.TimeEvent#getInterval <em>Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interval</em>' attribute.
	 * @see #getInterval()
	 * @generated
	 */
	void setInterval(String value);

	/**
	 * Returns the value of the '<em><b>Interval Unit</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.TIMEOUT_UNITS}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Interval Unit</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Interval Unit</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.TIMEOUT_UNITS
	 * @see #setIntervalUnit(TIMEOUT_UNITS)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getTimeEvent_IntervalUnit()
	 * @model
	 * @generated
	 */
	TIMEOUT_UNITS getIntervalUnit();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.TimeEvent#getIntervalUnit <em>Interval Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Interval Unit</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.TIMEOUT_UNITS
	 * @see #getIntervalUnit()
	 * @generated
	 */
	void setIntervalUnit(TIMEOUT_UNITS value);

} // TimeEvent
