/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Line Chart Visualization</b></em>'.
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
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getLineThickness <em>Line Thickness</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getLineSmoothness <em>Line Smoothness</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getDataPlotting <em>Data Plotting</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getPlotShape <em>Plot Shape</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getPlotShapeDimension <em>Plot Shape Dimension</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getLineChartVisualization()
 * @model extendedMetaData="name='LineChartVisualization' kind='elementOnly'"
 * @generated
 */
public interface LineChartVisualization extends ChartVisualization {
	/**
	 * Returns the value of the '<em><b>Line Thickness</b></em>' attribute.
	 * The default value is <code>"3"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line Thickness</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Line Thickness</em>' attribute.
	 * @see #isSetLineThickness()
	 * @see #unsetLineThickness()
	 * @see #setLineThickness(int)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getLineChartVisualization_LineThickness()
	 * @model default="3" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='element' name='lineThickness'"
	 * @generated
	 */
	int getLineThickness();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getLineThickness <em>Line Thickness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line Thickness</em>' attribute.
	 * @see #isSetLineThickness()
	 * @see #unsetLineThickness()
	 * @see #getLineThickness()
	 * @generated
	 */
	void setLineThickness(int value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getLineThickness <em>Line Thickness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLineThickness()
	 * @see #getLineThickness()
	 * @see #setLineThickness(int)
	 * @generated
	 */
	void unsetLineThickness();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getLineThickness <em>Line Thickness</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Line Thickness</em>' attribute is set.
	 * @see #unsetLineThickness()
	 * @see #getLineThickness()
	 * @see #setLineThickness(int)
	 * @generated
	 */
	boolean isSetLineThickness();

	/**
	 * Returns the value of the '<em><b>Line Smoothness</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Line Smoothness</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Line Smoothness</em>' attribute.
	 * @see #setLineSmoothness(String)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getLineChartVisualization_LineSmoothness()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='lineSmoothness'"
	 * @generated
	 */
	String getLineSmoothness();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getLineSmoothness <em>Line Smoothness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Line Smoothness</em>' attribute.
	 * @see #getLineSmoothness()
	 * @generated
	 */
	void setLineSmoothness(String value);

	/**
	 * Returns the value of the '<em><b>Data Plotting</b></em>' attribute.
	 * The default value is <code>"all"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.beviewsconfig.DataPlottingEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Plotting</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Plotting</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataPlottingEnum
	 * @see #isSetDataPlotting()
	 * @see #unsetDataPlotting()
	 * @see #setDataPlotting(DataPlottingEnum)
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getLineChartVisualization_DataPlotting()
	 * @model default="all" unsettable="true"
	 *        extendedMetaData="kind='element' name='dataPlotting'"
	 * @generated
	 */
	DataPlottingEnum getDataPlotting();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getDataPlotting <em>Data Plotting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Plotting</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.DataPlottingEnum
	 * @see #isSetDataPlotting()
	 * @see #unsetDataPlotting()
	 * @see #getDataPlotting()
	 * @generated
	 */
	void setDataPlotting(DataPlottingEnum value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getDataPlotting <em>Data Plotting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDataPlotting()
	 * @see #getDataPlotting()
	 * @see #setDataPlotting(DataPlottingEnum)
	 * @generated
	 */
	void unsetDataPlotting();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getDataPlotting <em>Data Plotting</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Data Plotting</em>' attribute is set.
	 * @see #unsetDataPlotting()
	 * @see #getDataPlotting()
	 * @see #setDataPlotting(DataPlottingEnum)
	 * @generated
	 */
	boolean isSetDataPlotting();

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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getLineChartVisualization_PlotShape()
	 * @model default="circle" unsettable="true"
	 *        extendedMetaData="kind='element' name='plotShape'"
	 * @generated
	 */
	PlotShapeEnum getPlotShape();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getPlotShape <em>Plot Shape</em>}' attribute.
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
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getPlotShape <em>Plot Shape</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPlotShape()
	 * @see #getPlotShape()
	 * @see #setPlotShape(PlotShapeEnum)
	 * @generated
	 */
	void unsetPlotShape();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getPlotShape <em>Plot Shape</em>}' attribute is set.
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
	 * @see com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage#getLineChartVisualization_PlotShapeDimension()
	 * @model default="5" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='element' name='plotShapeDimension'"
	 * @generated
	 */
	int getPlotShapeDimension();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getPlotShapeDimension <em>Plot Shape Dimension</em>}' attribute.
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
	 * Unsets the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getPlotShapeDimension <em>Plot Shape Dimension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPlotShapeDimension()
	 * @see #getPlotShapeDimension()
	 * @see #setPlotShapeDimension(int)
	 * @generated
	 */
	void unsetPlotShapeDimension();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization#getPlotShapeDimension <em>Plot Shape Dimension</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Plot Shape Dimension</em>' attribute is set.
	 * @see #unsetPlotShapeDimension()
	 * @see #getPlotShapeDimension()
	 * @see #setPlotShapeDimension(int)
	 * @generated
	 */
	boolean isSetPlotShapeDimension();

} // LineChartVisualization
