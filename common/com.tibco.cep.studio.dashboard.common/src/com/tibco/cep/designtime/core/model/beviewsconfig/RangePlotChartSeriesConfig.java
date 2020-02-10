/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Range Plot Chart Series Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *             
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesConfig#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getRangePlotChartSeriesConfig()
 * @model extendedMetaData="name='RangePlotChartSeriesConfig' kind='elementOnly'"
 * @generated
 */
public interface RangePlotChartSeriesConfig extends ChartSeriesConfig {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesTypeEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesTypeEnum
	 * @see #isSetType()
	 * @see #unsetType()
	 * @see #setType(RangePlotChartSeriesTypeEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getRangePlotChartSeriesConfig_Type()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='type'"
	 * @generated
	 */
	RangePlotChartSeriesTypeEnum getType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesConfig#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesTypeEnum
	 * @see #isSetType()
	 * @see #unsetType()
	 * @see #getType()
	 * @generated
	 */
	void setType(RangePlotChartSeriesTypeEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesConfig#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetType()
	 * @see #getType()
	 * @see #setType(RangePlotChartSeriesTypeEnum)
	 * @generated
	 */
	void unsetType();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartSeriesConfig#getType <em>Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Type</em>' attribute is set.
	 * @see #unsetType()
	 * @see #getType()
	 * @see #setType(RangePlotChartSeriesTypeEnum)
	 * @generated
	 */
	boolean isSetType();

} // RangePlotChartSeriesConfig
