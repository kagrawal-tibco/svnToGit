/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataPlottingEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.LineChartVisualization;
import com.tibco.cep.designtime.core.model.beviewsconfig.PlotShapeEnum;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Line Chart Visualization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.LineChartVisualizationImpl#getLineThickness <em>Line Thickness</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.LineChartVisualizationImpl#getLineSmoothness <em>Line Smoothness</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.LineChartVisualizationImpl#getDataPlotting <em>Data Plotting</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.LineChartVisualizationImpl#getPlotShape <em>Plot Shape</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.LineChartVisualizationImpl#getPlotShapeDimension <em>Plot Shape Dimension</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LineChartVisualizationImpl extends ChartVisualizationImpl implements LineChartVisualization {
	/**
	 * The default value of the '{@link #getLineThickness() <em>Line Thickness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineThickness()
	 * @generated
	 * @ordered
	 */
	protected static final int LINE_THICKNESS_EDEFAULT = 3;

	/**
	 * The cached value of the '{@link #getLineThickness() <em>Line Thickness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineThickness()
	 * @generated
	 * @ordered
	 */
	protected int lineThickness = LINE_THICKNESS_EDEFAULT;

	/**
	 * This is true if the Line Thickness attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean lineThicknessESet;

	/**
	 * The default value of the '{@link #getLineSmoothness() <em>Line Smoothness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineSmoothness()
	 * @generated
	 * @ordered
	 */
	protected static final String LINE_SMOOTHNESS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLineSmoothness() <em>Line Smoothness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineSmoothness()
	 * @generated
	 * @ordered
	 */
	protected String lineSmoothness = LINE_SMOOTHNESS_EDEFAULT;

	/**
	 * The default value of the '{@link #getDataPlotting() <em>Data Plotting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataPlotting()
	 * @generated
	 * @ordered
	 */
	protected static final DataPlottingEnum DATA_PLOTTING_EDEFAULT = DataPlottingEnum.ALL;

	/**
	 * The cached value of the '{@link #getDataPlotting() <em>Data Plotting</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataPlotting()
	 * @generated
	 * @ordered
	 */
	protected DataPlottingEnum dataPlotting = DATA_PLOTTING_EDEFAULT;

	/**
	 * This is true if the Data Plotting attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean dataPlottingESet;

	/**
	 * The default value of the '{@link #getPlotShape() <em>Plot Shape</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlotShape()
	 * @generated
	 * @ordered
	 */
	protected static final PlotShapeEnum PLOT_SHAPE_EDEFAULT = PlotShapeEnum.CIRCLE;

	/**
	 * The cached value of the '{@link #getPlotShape() <em>Plot Shape</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlotShape()
	 * @generated
	 * @ordered
	 */
	protected PlotShapeEnum plotShape = PLOT_SHAPE_EDEFAULT;

	/**
	 * This is true if the Plot Shape attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean plotShapeESet;

	/**
	 * The default value of the '{@link #getPlotShapeDimension() <em>Plot Shape Dimension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlotShapeDimension()
	 * @generated
	 * @ordered
	 */
	protected static final int PLOT_SHAPE_DIMENSION_EDEFAULT = 5;

	/**
	 * The cached value of the '{@link #getPlotShapeDimension() <em>Plot Shape Dimension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlotShapeDimension()
	 * @generated
	 * @ordered
	 */
	protected int plotShapeDimension = PLOT_SHAPE_DIMENSION_EDEFAULT;

	/**
	 * This is true if the Plot Shape Dimension attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean plotShapeDimensionESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LineChartVisualizationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getLineChartVisualization();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLineThickness() {
		return lineThickness;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLineThickness(int newLineThickness) {
		int oldLineThickness = lineThickness;
		lineThickness = newLineThickness;
		boolean oldLineThicknessESet = lineThicknessESet;
		lineThicknessESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__LINE_THICKNESS, oldLineThickness, lineThickness, !oldLineThicknessESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLineThickness() {
		int oldLineThickness = lineThickness;
		boolean oldLineThicknessESet = lineThicknessESet;
		lineThickness = LINE_THICKNESS_EDEFAULT;
		lineThicknessESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__LINE_THICKNESS, oldLineThickness, LINE_THICKNESS_EDEFAULT, oldLineThicknessESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLineThickness() {
		return lineThicknessESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLineSmoothness() {
		return lineSmoothness;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLineSmoothness(String newLineSmoothness) {
		String oldLineSmoothness = lineSmoothness;
		lineSmoothness = newLineSmoothness;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__LINE_SMOOTHNESS, oldLineSmoothness, lineSmoothness));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataPlottingEnum getDataPlotting() {
		return dataPlotting;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataPlotting(DataPlottingEnum newDataPlotting) {
		DataPlottingEnum oldDataPlotting = dataPlotting;
		dataPlotting = newDataPlotting == null ? DATA_PLOTTING_EDEFAULT : newDataPlotting;
		boolean oldDataPlottingESet = dataPlottingESet;
		dataPlottingESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__DATA_PLOTTING, oldDataPlotting, dataPlotting, !oldDataPlottingESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDataPlotting() {
		DataPlottingEnum oldDataPlotting = dataPlotting;
		boolean oldDataPlottingESet = dataPlottingESet;
		dataPlotting = DATA_PLOTTING_EDEFAULT;
		dataPlottingESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__DATA_PLOTTING, oldDataPlotting, DATA_PLOTTING_EDEFAULT, oldDataPlottingESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDataPlotting() {
		return dataPlottingESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlotShapeEnum getPlotShape() {
		return plotShape;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPlotShape(PlotShapeEnum newPlotShape) {
		PlotShapeEnum oldPlotShape = plotShape;
		plotShape = newPlotShape == null ? PLOT_SHAPE_EDEFAULT : newPlotShape;
		boolean oldPlotShapeESet = plotShapeESet;
		plotShapeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__PLOT_SHAPE, oldPlotShape, plotShape, !oldPlotShapeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetPlotShape() {
		PlotShapeEnum oldPlotShape = plotShape;
		boolean oldPlotShapeESet = plotShapeESet;
		plotShape = PLOT_SHAPE_EDEFAULT;
		plotShapeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__PLOT_SHAPE, oldPlotShape, PLOT_SHAPE_EDEFAULT, oldPlotShapeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetPlotShape() {
		return plotShapeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPlotShapeDimension() {
		return plotShapeDimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPlotShapeDimension(int newPlotShapeDimension) {
		int oldPlotShapeDimension = plotShapeDimension;
		plotShapeDimension = newPlotShapeDimension;
		boolean oldPlotShapeDimensionESet = plotShapeDimensionESet;
		plotShapeDimensionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION, oldPlotShapeDimension, plotShapeDimension, !oldPlotShapeDimensionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetPlotShapeDimension() {
		int oldPlotShapeDimension = plotShapeDimension;
		boolean oldPlotShapeDimensionESet = plotShapeDimensionESet;
		plotShapeDimension = PLOT_SHAPE_DIMENSION_EDEFAULT;
		plotShapeDimensionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION, oldPlotShapeDimension, PLOT_SHAPE_DIMENSION_EDEFAULT, oldPlotShapeDimensionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetPlotShapeDimension() {
		return plotShapeDimensionESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__LINE_THICKNESS:
				return getLineThickness();
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__LINE_SMOOTHNESS:
				return getLineSmoothness();
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__DATA_PLOTTING:
				return getDataPlotting();
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__PLOT_SHAPE:
				return getPlotShape();
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION:
				return getPlotShapeDimension();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__LINE_THICKNESS:
				setLineThickness((Integer)newValue);
				return;
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__LINE_SMOOTHNESS:
				setLineSmoothness((String)newValue);
				return;
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__DATA_PLOTTING:
				setDataPlotting((DataPlottingEnum)newValue);
				return;
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__PLOT_SHAPE:
				setPlotShape((PlotShapeEnum)newValue);
				return;
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION:
				setPlotShapeDimension((Integer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__LINE_THICKNESS:
				unsetLineThickness();
				return;
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__LINE_SMOOTHNESS:
				setLineSmoothness(LINE_SMOOTHNESS_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__DATA_PLOTTING:
				unsetDataPlotting();
				return;
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__PLOT_SHAPE:
				unsetPlotShape();
				return;
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION:
				unsetPlotShapeDimension();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__LINE_THICKNESS:
				return isSetLineThickness();
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__LINE_SMOOTHNESS:
				return LINE_SMOOTHNESS_EDEFAULT == null ? lineSmoothness != null : !LINE_SMOOTHNESS_EDEFAULT.equals(lineSmoothness);
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__DATA_PLOTTING:
				return isSetDataPlotting();
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__PLOT_SHAPE:
				return isSetPlotShape();
			case BEViewsConfigurationPackage.LINE_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION:
				return isSetPlotShapeDimension();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (lineThickness: ");
		if (lineThicknessESet) result.append(lineThickness); else result.append("<unset>");
		result.append(", lineSmoothness: ");
		result.append(lineSmoothness);
		result.append(", dataPlotting: ");
		if (dataPlottingESet) result.append(dataPlotting); else result.append("<unset>");
		result.append(", plotShape: ");
		if (plotShapeESet) result.append(plotShape); else result.append("<unset>");
		result.append(", plotShapeDimension: ");
		if (plotShapeDimensionESet) result.append(plotShapeDimension); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //LineChartVisualizationImpl
