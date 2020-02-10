/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Range Plot Chart Visualization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *             The extension of chart visualization to handle generation of range plot charts
 *             
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getPlotShape <em>Plot Shape</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getPlotShapeDimension <em>Plot Shape Dimension</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getWhiskerThickness <em>Whisker Thickness</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getWhiskerWidth <em>Whisker Width</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getOrientation <em>Orientation</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getRangePlotChartVisualization()
 * @model extendedMetaData="name='RangePlotChartVisualization' kind='elementOnly'"
 * @generated
 */
public interface RangePlotChartVisualization extends ChartVisualization {
	/**
	 * Returns the value of the '<em><b>Plot Shape</b></em>' attribute.
	 * The default value is <code>"circle"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.PlotShapeEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plot Shape</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plot Shape</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PlotShapeEnum
	 * @see #isSetPlotShape()
	 * @see #unsetPlotShape()
	 * @see #setPlotShape(PlotShapeEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getRangePlotChartVisualization_PlotShape()
	 * @model default="circle" unsettable="true"
	 *        extendedMetaData="kind='element' name='plotShape'"
	 * @generated
	 */
	PlotShapeEnum getPlotShape();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getPlotShape <em>Plot Shape</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Plot Shape</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.PlotShapeEnum
	 * @see #isSetPlotShape()
	 * @see #unsetPlotShape()
	 * @see #getPlotShape()
	 * @generated
	 */
	void setPlotShape(PlotShapeEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getPlotShape <em>Plot Shape</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPlotShape()
	 * @see #getPlotShape()
	 * @see #setPlotShape(PlotShapeEnum)
	 * @generated
	 */
	void unsetPlotShape();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getPlotShape <em>Plot Shape</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Plot Shape</em>' attribute is set.
	 * @see #unsetPlotShape()
	 * @see #getPlotShape()
	 * @see #setPlotShape(PlotShapeEnum)
	 * @generated
	 */
	boolean isSetPlotShape();

	/**
	 * Returns the value of the '<em><b>Plot Shape Dimension</b></em>' attribute.
	 * The default value is <code>"3"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plot Shape Dimension</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plot Shape Dimension</em>' attribute.
	 * @see #isSetPlotShapeDimension()
	 * @see #unsetPlotShapeDimension()
	 * @see #setPlotShapeDimension(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getRangePlotChartVisualization_PlotShapeDimension()
	 * @model default="3" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='element' name='plotShapeDimension'"
	 * @generated
	 */
	int getPlotShapeDimension();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getPlotShapeDimension <em>Plot Shape Dimension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Plot Shape Dimension</em>' attribute.
	 * @see #isSetPlotShapeDimension()
	 * @see #unsetPlotShapeDimension()
	 * @see #getPlotShapeDimension()
	 * @generated
	 */
	void setPlotShapeDimension(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getPlotShapeDimension <em>Plot Shape Dimension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPlotShapeDimension()
	 * @see #getPlotShapeDimension()
	 * @see #setPlotShapeDimension(int)
	 * @generated
	 */
	void unsetPlotShapeDimension();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getPlotShapeDimension <em>Plot Shape Dimension</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Plot Shape Dimension</em>' attribute is set.
	 * @see #unsetPlotShapeDimension()
	 * @see #getPlotShapeDimension()
	 * @see #setPlotShapeDimension(int)
	 * @generated
	 */
	boolean isSetPlotShapeDimension();

	/**
	 * Returns the value of the '<em><b>Whisker Thickness</b></em>' attribute.
	 * The default value is <code>"2"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Whisker Thickness</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Whisker Thickness</em>' attribute.
	 * @see #isSetWhiskerThickness()
	 * @see #unsetWhiskerThickness()
	 * @see #setWhiskerThickness(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getRangePlotChartVisualization_WhiskerThickness()
	 * @model default="2" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='element' name='whiskerThickness'"
	 * @generated
	 */
	int getWhiskerThickness();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getWhiskerThickness <em>Whisker Thickness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Whisker Thickness</em>' attribute.
	 * @see #isSetWhiskerThickness()
	 * @see #unsetWhiskerThickness()
	 * @see #getWhiskerThickness()
	 * @generated
	 */
	void setWhiskerThickness(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getWhiskerThickness <em>Whisker Thickness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetWhiskerThickness()
	 * @see #getWhiskerThickness()
	 * @see #setWhiskerThickness(int)
	 * @generated
	 */
	void unsetWhiskerThickness();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getWhiskerThickness <em>Whisker Thickness</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Whisker Thickness</em>' attribute is set.
	 * @see #unsetWhiskerThickness()
	 * @see #getWhiskerThickness()
	 * @see #setWhiskerThickness(int)
	 * @generated
	 */
	boolean isSetWhiskerThickness();

	/**
	 * Returns the value of the '<em><b>Whisker Width</b></em>' attribute.
	 * The default value is <code>"5"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Whisker Width</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Whisker Width</em>' attribute.
	 * @see #isSetWhiskerWidth()
	 * @see #unsetWhiskerWidth()
	 * @see #setWhiskerWidth(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getRangePlotChartVisualization_WhiskerWidth()
	 * @model default="5" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='element' name='whiskerWidth'"
	 * @generated
	 */
	int getWhiskerWidth();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getWhiskerWidth <em>Whisker Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Whisker Width</em>' attribute.
	 * @see #isSetWhiskerWidth()
	 * @see #unsetWhiskerWidth()
	 * @see #getWhiskerWidth()
	 * @generated
	 */
	void setWhiskerWidth(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getWhiskerWidth <em>Whisker Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetWhiskerWidth()
	 * @see #getWhiskerWidth()
	 * @see #setWhiskerWidth(int)
	 * @generated
	 */
	void unsetWhiskerWidth();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getWhiskerWidth <em>Whisker Width</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Whisker Width</em>' attribute is set.
	 * @see #unsetWhiskerWidth()
	 * @see #getWhiskerWidth()
	 * @see #setWhiskerWidth(int)
	 * @generated
	 */
	boolean isSetWhiskerWidth();

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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getRangePlotChartVisualization_Orientation()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='orientation'"
	 * @generated
	 */
	OrientationEnum getOrientation();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getOrientation <em>Orientation</em>}' attribute.
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
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getOrientation <em>Orientation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetOrientation()
	 * @see #getOrientation()
	 * @see #setOrientation(OrientationEnum)
	 * @generated
	 */
	void unsetOrientation();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization#getOrientation <em>Orientation</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Orientation</em>' attribute is set.
	 * @see #unsetOrientation()
	 * @see #getOrientation()
	 * @see #setOrientation(OrientationEnum)
	 * @generated
	 */
	boolean isSetOrientation();

} // RangePlotChartVisualization
