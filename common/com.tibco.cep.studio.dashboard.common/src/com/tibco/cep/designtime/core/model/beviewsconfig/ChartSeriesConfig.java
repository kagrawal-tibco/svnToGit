/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Chart Series Config</b></em>'.
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
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartSeriesConfig#getAnchor <em>Anchor</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartSeriesConfig#getValueLabelStyle <em>Value Label Style</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartSeriesConfig()
 * @model extendedMetaData="name='ChartSeriesConfig' kind='elementOnly'"
 * @generated
 */
public interface ChartSeriesConfig extends TwoDimSeriesConfig {
	/**
	 * Returns the value of the '<em><b>Anchor</b></em>' attribute.
	 * The default value is <code>"Q1"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesAnchorEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Anchor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Anchor</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesAnchorEnum
	 * @see #isSetAnchor()
	 * @see #unsetAnchor()
	 * @see #setAnchor(SeriesAnchorEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartSeriesConfig_Anchor()
	 * @model default="Q1" unsettable="true"
	 *        extendedMetaData="kind='element' name='anchor'"
	 * @generated
	 */
	SeriesAnchorEnum getAnchor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartSeriesConfig#getAnchor <em>Anchor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Anchor</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.SeriesAnchorEnum
	 * @see #isSetAnchor()
	 * @see #unsetAnchor()
	 * @see #getAnchor()
	 * @generated
	 */
	void setAnchor(SeriesAnchorEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartSeriesConfig#getAnchor <em>Anchor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAnchor()
	 * @see #getAnchor()
	 * @see #setAnchor(SeriesAnchorEnum)
	 * @generated
	 */
	void unsetAnchor();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartSeriesConfig#getAnchor <em>Anchor</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Anchor</em>' attribute is set.
	 * @see #unsetAnchor()
	 * @see #getAnchor()
	 * @see #setAnchor(SeriesAnchorEnum)
	 * @generated
	 */
	boolean isSetAnchor();

	/**
	 * Returns the value of the '<em><b>Value Label Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Label Style</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Label Style</em>' containment reference.
	 * @see #setValueLabelStyle(ValueLabelStyle)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartSeriesConfig_ValueLabelStyle()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='valueLabelStyle'"
	 * @generated
	 */
	ValueLabelStyle getValueLabelStyle();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartSeriesConfig#getValueLabelStyle <em>Value Label Style</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Label Style</em>' containment reference.
	 * @see #getValueLabelStyle()
	 * @generated
	 */
	void setValueLabelStyle(ValueLabelStyle value);

} // ChartSeriesConfig
