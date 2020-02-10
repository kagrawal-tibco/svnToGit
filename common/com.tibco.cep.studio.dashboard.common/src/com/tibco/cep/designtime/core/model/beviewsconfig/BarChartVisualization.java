/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bar Chart Visualization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *             The extension of chart visualization to handle generation of bar charts
 *             
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getWidth <em>Width</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getTopCapThickness <em>Top Cap Thickness</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getOverlapPercentage <em>Overlap Percentage</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getOrientation <em>Orientation</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getBarChartVisualization()
 * @model extendedMetaData="name='BarChartVisualization' kind='elementOnly'"
 * @generated
 */
public interface BarChartVisualization extends ChartVisualization {
	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * The default value is <code>"10"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Width</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #isSetWidth()
	 * @see #unsetWidth()
	 * @see #setWidth(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getBarChartVisualization_Width()
	 * @model default="10" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='element' name='width'"
	 * @generated
	 */
	int getWidth();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #isSetWidth()
	 * @see #unsetWidth()
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetWidth()
	 * @see #getWidth()
	 * @see #setWidth(int)
	 * @generated
	 */
	void unsetWidth();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getWidth <em>Width</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Width</em>' attribute is set.
	 * @see #unsetWidth()
	 * @see #getWidth()
	 * @see #setWidth(int)
	 * @generated
	 */
	boolean isSetWidth();

	/**
	 * Returns the value of the '<em><b>Top Cap Thickness</b></em>' attribute.
	 * The default value is <code>"0.5"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Top Cap Thickness</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Top Cap Thickness</em>' attribute.
	 * @see #isSetTopCapThickness()
	 * @see #unsetTopCapThickness()
	 * @see #setTopCapThickness(double)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getBarChartVisualization_TopCapThickness()
	 * @model default="0.5" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='element' name='topCapThickness'"
	 * @generated
	 */
	double getTopCapThickness();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getTopCapThickness <em>Top Cap Thickness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Top Cap Thickness</em>' attribute.
	 * @see #isSetTopCapThickness()
	 * @see #unsetTopCapThickness()
	 * @see #getTopCapThickness()
	 * @generated
	 */
	void setTopCapThickness(double value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getTopCapThickness <em>Top Cap Thickness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTopCapThickness()
	 * @see #getTopCapThickness()
	 * @see #setTopCapThickness(double)
	 * @generated
	 */
	void unsetTopCapThickness();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getTopCapThickness <em>Top Cap Thickness</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Top Cap Thickness</em>' attribute is set.
	 * @see #unsetTopCapThickness()
	 * @see #getTopCapThickness()
	 * @see #setTopCapThickness(double)
	 * @generated
	 */
	boolean isSetTopCapThickness();

	/**
	 * Returns the value of the '<em><b>Overlap Percentage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Overlap Percentage</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Overlap Percentage</em>' attribute.
	 * @see #isSetOverlapPercentage()
	 * @see #unsetOverlapPercentage()
	 * @see #setOverlapPercentage(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getBarChartVisualization_OverlapPercentage()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='element' name='overlapPercentage'"
	 * @generated
	 */
	int getOverlapPercentage();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getOverlapPercentage <em>Overlap Percentage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Overlap Percentage</em>' attribute.
	 * @see #isSetOverlapPercentage()
	 * @see #unsetOverlapPercentage()
	 * @see #getOverlapPercentage()
	 * @generated
	 */
	void setOverlapPercentage(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getOverlapPercentage <em>Overlap Percentage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOverlapPercentage()
	 * @see #getOverlapPercentage()
	 * @see #setOverlapPercentage(int)
	 * @generated
	 */
	void unsetOverlapPercentage();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getOverlapPercentage <em>Overlap Percentage</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Overlap Percentage</em>' attribute is set.
	 * @see #unsetOverlapPercentage()
	 * @see #getOverlapPercentage()
	 * @see #setOverlapPercentage(int)
	 * @generated
	 */
	boolean isSetOverlapPercentage();

	/**
	 * Returns the value of the '<em><b>Orientation</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Orientation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Orientation</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum
	 * @see #isSetOrientation()
	 * @see #unsetOrientation()
	 * @see #setOrientation(OrientationEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getBarChartVisualization_Orientation()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='orientation'"
	 * @generated
	 */
	OrientationEnum getOrientation();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getOrientation <em>Orientation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Orientation</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum
	 * @see #isSetOrientation()
	 * @see #unsetOrientation()
	 * @see #getOrientation()
	 * @generated
	 */
	void setOrientation(OrientationEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getOrientation <em>Orientation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOrientation()
	 * @see #getOrientation()
	 * @see #setOrientation(OrientationEnum)
	 * @generated
	 */
	void unsetOrientation();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization#getOrientation <em>Orientation</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Orientation</em>' attribute is set.
	 * @see #unsetOrientation()
	 * @see #getOrientation()
	 * @see #setOrientation(OrientationEnum)
	 * @generated
	 */
	boolean isSetOrientation();

} // BarChartVisualization
