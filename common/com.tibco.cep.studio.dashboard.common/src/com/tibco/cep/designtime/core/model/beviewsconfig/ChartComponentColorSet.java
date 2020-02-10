/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Chart Component Color Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getSeriesColor <em>Series Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getGuideLineLabelFontColor <em>Guide Line Label Font Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getGuideLineValueLabelFontColor <em>Guide Line Value Label Font Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getDataPointLabelFontColor <em>Data Point Label Font Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getTopCapColor <em>Top Cap Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getPieEdgeColor <em>Pie Edge Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getPieDropShadowColor <em>Pie Drop Shadow Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getLineDropShadowColor <em>Line Drop Shadow Color</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartComponentColorSet()
 * @model extendedMetaData="name='ChartComponentColorSet' kind='elementOnly'"
 * @generated
 */
public interface ChartComponentColorSet extends ComponentColorSet {
	/**
	 * Returns the value of the '<em><b>Series Color</b></em>' reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Series Color</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Series Color</em>' reference list.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartComponentColorSet_SeriesColor()
	 * @model resolveProxies="false" required="true"
	 *        extendedMetaData="kind='element' name='seriesColor'"
	 * @generated
	 */
	EList<SeriesColor> getSeriesColor();

	/**
	 * Returns the value of the '<em><b>Guide Line Label Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guide Line Label Font Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guide Line Label Font Color</em>' attribute.
	 * @see #setGuideLineLabelFontColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartComponentColorSet_GuideLineLabelFontColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='guideLineLabelFontColor'"
	 * @generated
	 */
	String getGuideLineLabelFontColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getGuideLineLabelFontColor <em>Guide Line Label Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guide Line Label Font Color</em>' attribute.
	 * @see #getGuideLineLabelFontColor()
	 * @generated
	 */
	void setGuideLineLabelFontColor(String value);

	/**
	 * Returns the value of the '<em><b>Guide Line Value Label Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guide Line Value Label Font Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guide Line Value Label Font Color</em>' attribute.
	 * @see #setGuideLineValueLabelFontColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartComponentColorSet_GuideLineValueLabelFontColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='guideLineValueLabelFontColor'"
	 * @generated
	 */
	String getGuideLineValueLabelFontColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getGuideLineValueLabelFontColor <em>Guide Line Value Label Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guide Line Value Label Font Color</em>' attribute.
	 * @see #getGuideLineValueLabelFontColor()
	 * @generated
	 */
	void setGuideLineValueLabelFontColor(String value);

	/**
	 * Returns the value of the '<em><b>Data Point Label Font Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Point Label Font Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Point Label Font Color</em>' attribute.
	 * @see #setDataPointLabelFontColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartComponentColorSet_DataPointLabelFontColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='dataPointLabelFontColor'"
	 * @generated
	 */
	String getDataPointLabelFontColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getDataPointLabelFontColor <em>Data Point Label Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Point Label Font Color</em>' attribute.
	 * @see #getDataPointLabelFontColor()
	 * @generated
	 */
	void setDataPointLabelFontColor(String value);

	/**
	 * Returns the value of the '<em><b>Top Cap Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Top Cap Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Top Cap Color</em>' attribute.
	 * @see #setTopCapColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartComponentColorSet_TopCapColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='topCapColor'"
	 * @generated
	 */
	String getTopCapColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getTopCapColor <em>Top Cap Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Top Cap Color</em>' attribute.
	 * @see #getTopCapColor()
	 * @generated
	 */
	void setTopCapColor(String value);

	/**
	 * Returns the value of the '<em><b>Pie Edge Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pie Edge Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pie Edge Color</em>' attribute.
	 * @see #setPieEdgeColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartComponentColorSet_PieEdgeColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='pieEdgeColor'"
	 * @generated
	 */
	String getPieEdgeColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getPieEdgeColor <em>Pie Edge Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pie Edge Color</em>' attribute.
	 * @see #getPieEdgeColor()
	 * @generated
	 */
	void setPieEdgeColor(String value);

	/**
	 * Returns the value of the '<em><b>Pie Drop Shadow Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pie Drop Shadow Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pie Drop Shadow Color</em>' attribute.
	 * @see #setPieDropShadowColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartComponentColorSet_PieDropShadowColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='pieDropShadowColor'"
	 * @generated
	 */
	String getPieDropShadowColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getPieDropShadowColor <em>Pie Drop Shadow Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pie Drop Shadow Color</em>' attribute.
	 * @see #getPieDropShadowColor()
	 * @generated
	 */
	void setPieDropShadowColor(String value);

	/**
	 * Returns the value of the '<em><b>Line Drop Shadow Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line Drop Shadow Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Line Drop Shadow Color</em>' attribute.
	 * @see #setLineDropShadowColor(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getChartComponentColorSet_LineDropShadowColor()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='lineDropShadowColor'"
	 * @generated
	 */
	String getLineDropShadowColor();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet#getLineDropShadowColor <em>Line Drop Shadow Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line Drop Shadow Color</em>' attribute.
	 * @see #getLineDropShadowColor()
	 * @generated
	 */
	void setLineDropShadowColor(String value);

} // ChartComponentColorSet
