/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.element;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.METRIC_TYPE;
import com.tibco.cep.designtime.core.model.TIME_UNITS;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metric</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Metric#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Metric#getWindowType <em>Window Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Metric#getWindowSize <em>Window Size</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Metric#getRecurringTimeType <em>Recurring Time Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Metric#getRecurringFrequency <em>Recurring Frequency</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Metric#getStartTime <em>Start Time</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Metric#getShortName <em>Short Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Metric#isPersistent <em>Persistent</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Metric#isTransactionalRolling <em>Transactional Rolling</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Metric#isEnabled <em>Enabled</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Metric#getRetentionTimeType <em>Retention Time Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Metric#getRetentionTimeSize <em>Retention Time Size</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.Metric#getUserDefinedFields <em>User Defined Fields</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getMetric()
 * @model
 * @generated
 */
public interface Metric extends Concept {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.METRIC_TYPE}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.METRIC_TYPE
	 * @see #setType(METRIC_TYPE)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getMetric_Type()
	 * @model
	 * @generated
	 */
	METRIC_TYPE getType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Metric#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.METRIC_TYPE
	 * @see #getType()
	 * @generated
	 */
	void setType(METRIC_TYPE value);

	/**
	 * Returns the value of the '<em><b>Window Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.TIME_UNITS}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Window Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Window Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.TIME_UNITS
	 * @see #setWindowType(TIME_UNITS)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getMetric_WindowType()
	 * @model
	 * @generated
	 */
	TIME_UNITS getWindowType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Metric#getWindowType <em>Window Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Window Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.TIME_UNITS
	 * @see #getWindowType()
	 * @generated
	 */
	void setWindowType(TIME_UNITS value);

	/**
	 * Returns the value of the '<em><b>Window Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Window Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Window Size</em>' attribute.
	 * @see #setWindowSize(long)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getMetric_WindowSize()
	 * @model
	 * @generated
	 */
	long getWindowSize();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Metric#getWindowSize <em>Window Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Window Size</em>' attribute.
	 * @see #getWindowSize()
	 * @generated
	 */
	void setWindowSize(long value);

	/**
	 * Returns the value of the '<em><b>Recurring Time Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.TIME_UNITS}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recurring Time Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recurring Time Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.TIME_UNITS
	 * @see #setRecurringTimeType(TIME_UNITS)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getMetric_RecurringTimeType()
	 * @model
	 * @generated
	 */
	TIME_UNITS getRecurringTimeType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Metric#getRecurringTimeType <em>Recurring Time Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recurring Time Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.TIME_UNITS
	 * @see #getRecurringTimeType()
	 * @generated
	 */
	void setRecurringTimeType(TIME_UNITS value);

	/**
	 * Returns the value of the '<em><b>Recurring Frequency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recurring Frequency</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recurring Frequency</em>' attribute.
	 * @see #setRecurringFrequency(long)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getMetric_RecurringFrequency()
	 * @model
	 * @generated
	 */
	long getRecurringFrequency();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Metric#getRecurringFrequency <em>Recurring Frequency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recurring Frequency</em>' attribute.
	 * @see #getRecurringFrequency()
	 * @generated
	 */
	void setRecurringFrequency(long value);

	/**
	 * Returns the value of the '<em><b>Start Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Time</em>' attribute.
	 * @see #setStartTime(long)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getMetric_StartTime()
	 * @model
	 * @generated
	 */
	long getStartTime();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Metric#getStartTime <em>Start Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Time</em>' attribute.
	 * @see #getStartTime()
	 * @generated
	 */
	void setStartTime(long value);

	/**
	 * Returns the value of the '<em><b>Short Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Short Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Short Name</em>' attribute.
	 * @see #setShortName(String)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getMetric_ShortName()
	 * @model
	 * @generated
	 */
	String getShortName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Metric#getShortName <em>Short Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Short Name</em>' attribute.
	 * @see #getShortName()
	 * @generated
	 */
	void setShortName(String value);

	/**
	 * Returns the value of the '<em><b>Persistent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Persistent</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Persistent</em>' attribute.
	 * @see #setPersistent(boolean)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getMetric_Persistent()
	 * @model
	 * @generated
	 */
	boolean isPersistent();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Metric#isPersistent <em>Persistent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Persistent</em>' attribute.
	 * @see #isPersistent()
	 * @generated
	 */
	void setPersistent(boolean value);

	/**
	 * Returns the value of the '<em><b>Transactional Rolling</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transactional Rolling</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transactional Rolling</em>' attribute.
	 * @see #setTransactionalRolling(boolean)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getMetric_TransactionalRolling()
	 * @model
	 * @generated
	 */
	boolean isTransactionalRolling();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Metric#isTransactionalRolling <em>Transactional Rolling</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transactional Rolling</em>' attribute.
	 * @see #isTransactionalRolling()
	 * @generated
	 */
	void setTransactionalRolling(boolean value);

	/**
	 * Returns the value of the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enabled</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enabled</em>' attribute.
	 * @see #setEnabled(boolean)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getMetric_Enabled()
	 * @model
	 * @generated
	 */
	boolean isEnabled();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Metric#isEnabled <em>Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enabled</em>' attribute.
	 * @see #isEnabled()
	 * @generated
	 */
	void setEnabled(boolean value);

	/**
	 * Returns the value of the '<em><b>Retention Time Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.TIME_UNITS}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Retention Time Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Retention Time Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.TIME_UNITS
	 * @see #setRetentionTimeType(TIME_UNITS)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getMetric_RetentionTimeType()
	 * @model
	 * @generated
	 */
	TIME_UNITS getRetentionTimeType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Metric#getRetentionTimeType <em>Retention Time Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Retention Time Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.TIME_UNITS
	 * @see #getRetentionTimeType()
	 * @generated
	 */
	void setRetentionTimeType(TIME_UNITS value);

	/**
	 * Returns the value of the '<em><b>Retention Time Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Retention Time Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Retention Time Size</em>' attribute.
	 * @see #setRetentionTimeSize(long)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getMetric_RetentionTimeSize()
	 * @model
	 * @generated
	 */
	long getRetentionTimeSize();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.Metric#getRetentionTimeSize <em>Retention Time Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Retention Time Size</em>' attribute.
	 * @see #getRetentionTimeSize()
	 * @generated
	 */
	void setRetentionTimeSize(long value);

	/**
	 * Returns the value of the '<em><b>User Defined Fields</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.element.PropertyDefinition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Defined Fields</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Defined Fields</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getMetric_UserDefinedFields()
	 * @model containment="true"
	 * @generated
	 */
	EList<PropertyDefinition> getUserDefinedFields();

} // Metric
