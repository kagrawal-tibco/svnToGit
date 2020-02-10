/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Scatter Plot Chart Visualization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 *             The extension of chart visualization to handle generation of line charts
 *             
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScatterPlotChartVisualization#getPlotShape <em>Plot Shape</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScatterPlotChartVisualization#getPlotShapeDimension <em>Plot Shape Dimension</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getScatterPlotChartVisualization()
 * @model extendedMetaData="name='ScatterPlotChartVisualization' kind='elementOnly'"
 * @generated
 */
public interface ScatterPlotChartVisualization extends ChartVisualization {
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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getScatterPlotChartVisualization_PlotShape()
	 * @model default="circle" unsettable="true"
	 *        extendedMetaData="kind='element' name='plotShape'"
	 * @generated
	 */
	PlotShapeEnum getPlotShape();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScatterPlotChartVisualization#getPlotShape <em>Plot Shape</em>}' attribute.
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
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScatterPlotChartVisualization#getPlotShape <em>Plot Shape</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPlotShape()
	 * @see #getPlotShape()
	 * @see #setPlotShape(PlotShapeEnum)
	 * @generated
	 */
	void unsetPlotShape();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScatterPlotChartVisualization#getPlotShape <em>Plot Shape</em>}' attribute is set.
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
	 * The default value is <code>"5"</code>.
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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getScatterPlotChartVisualization_PlotShapeDimension()
	 * @model default="5" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='element' name='plotShapeDimension'"
	 * @generated
	 */
	int getPlotShapeDimension();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScatterPlotChartVisualization#getPlotShapeDimension <em>Plot Shape Dimension</em>}' attribute.
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
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScatterPlotChartVisualization#getPlotShapeDimension <em>Plot Shape Dimension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPlotShapeDimension()
	 * @see #getPlotShapeDimension()
	 * @see #setPlotShapeDimension(int)
	 * @generated
	 */
	void unsetPlotShapeDimension();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.ScatterPlotChartVisualization#getPlotShapeDimension <em>Plot Shape Dimension</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Plot Shape Dimension</em>' attribute is set.
	 * @see #unsetPlotShapeDimension()
	 * @see #getPlotShapeDimension()
	 * @see #setPlotShapeDimension(int)
	 * @generated
	 */
	boolean isSetPlotShapeDimension();

} // ScatterPlotChartVisualization
