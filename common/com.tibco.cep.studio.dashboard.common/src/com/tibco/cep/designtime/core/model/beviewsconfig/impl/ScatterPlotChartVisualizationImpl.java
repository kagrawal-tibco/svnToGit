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
import com.tibco.cep.designtime.core.model.beviewsconfig.PlotShapeEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.ScatterPlotChartVisualization;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Scatter Plot Chart Visualization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ScatterPlotChartVisualizationImpl#getPlotShape <em>Plot Shape</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ScatterPlotChartVisualizationImpl#getPlotShapeDimension <em>Plot Shape Dimension</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScatterPlotChartVisualizationImpl extends ChartVisualizationImpl implements ScatterPlotChartVisualization {
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
	protected ScatterPlotChartVisualizationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getScatterPlotChartVisualization();
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SCATTER_PLOT_CHART_VISUALIZATION__PLOT_SHAPE, oldPlotShape, plotShape, !oldPlotShapeESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.SCATTER_PLOT_CHART_VISUALIZATION__PLOT_SHAPE, oldPlotShape, PLOT_SHAPE_EDEFAULT, oldPlotShapeESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.SCATTER_PLOT_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION, oldPlotShapeDimension, plotShapeDimension, !oldPlotShapeDimensionESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.SCATTER_PLOT_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION, oldPlotShapeDimension, PLOT_SHAPE_DIMENSION_EDEFAULT, oldPlotShapeDimensionESet));
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
			case BEViewsConfigurationPackage.SCATTER_PLOT_CHART_VISUALIZATION__PLOT_SHAPE:
				return getPlotShape();
			case BEViewsConfigurationPackage.SCATTER_PLOT_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION:
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
			case BEViewsConfigurationPackage.SCATTER_PLOT_CHART_VISUALIZATION__PLOT_SHAPE:
				setPlotShape((PlotShapeEnum)newValue);
				return;
			case BEViewsConfigurationPackage.SCATTER_PLOT_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION:
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
			case BEViewsConfigurationPackage.SCATTER_PLOT_CHART_VISUALIZATION__PLOT_SHAPE:
				unsetPlotShape();
				return;
			case BEViewsConfigurationPackage.SCATTER_PLOT_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION:
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
			case BEViewsConfigurationPackage.SCATTER_PLOT_CHART_VISUALIZATION__PLOT_SHAPE:
				return isSetPlotShape();
			case BEViewsConfigurationPackage.SCATTER_PLOT_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION:
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
		result.append(" (plotShape: ");
		if (plotShapeESet) result.append(plotShape); else result.append("<unset>");
		result.append(", plotShapeDimension: ");
		if (plotShapeDimensionESet) result.append(plotShapeDimension); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //ScatterPlotChartVisualizationImpl
