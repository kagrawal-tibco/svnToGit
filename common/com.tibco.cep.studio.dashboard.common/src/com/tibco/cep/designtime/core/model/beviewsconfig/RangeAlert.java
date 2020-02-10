/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Range Alert</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert#getLowerValue <em>Lower Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert#getUpperValue <em>Upper Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getRangeAlert()
 * @model
 * @generated
 */
public interface RangeAlert extends Alert {
	/**
	 * Returns the value of the '<em><b>Lower Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lower Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lower Value</em>' attribute.
	 * @see #isSetLowerValue()
	 * @see #unsetLowerValue()
	 * @see #setLowerValue(double)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getRangeAlert_LowerValue()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 * @generated
	 */
	double getLowerValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert#getLowerValue <em>Lower Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower Value</em>' attribute.
	 * @see #isSetLowerValue()
	 * @see #unsetLowerValue()
	 * @see #getLowerValue()
	 * @generated
	 */
	void setLowerValue(double value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert#getLowerValue <em>Lower Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLowerValue()
	 * @see #getLowerValue()
	 * @see #setLowerValue(double)
	 * @generated
	 */
	void unsetLowerValue();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert#getLowerValue <em>Lower Value</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Lower Value</em>' attribute is set.
	 * @see #unsetLowerValue()
	 * @see #getLowerValue()
	 * @see #setLowerValue(double)
	 * @generated
	 */
	boolean isSetLowerValue();

	/**
	 * Returns the value of the '<em><b>Upper Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Upper Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper Value</em>' attribute.
	 * @see #isSetUpperValue()
	 * @see #unsetUpperValue()
	 * @see #setUpperValue(double)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getRangeAlert_UpperValue()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 * @generated
	 */
	double getUpperValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert#getUpperValue <em>Upper Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper Value</em>' attribute.
	 * @see #isSetUpperValue()
	 * @see #unsetUpperValue()
	 * @see #getUpperValue()
	 * @generated
	 */
	void setUpperValue(double value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert#getUpperValue <em>Upper Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetUpperValue()
	 * @see #getUpperValue()
	 * @see #setUpperValue(double)
	 * @generated
	 */
	void unsetUpperValue();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangeAlert#getUpperValue <em>Upper Value</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Upper Value</em>' attribute is set.
	 * @see #unsetUpperValue()
	 * @see #getUpperValue()
	 * @see #setUpperValue(double)
	 * @generated
	 */
	boolean isSetUpperValue();

} // RangeAlert
