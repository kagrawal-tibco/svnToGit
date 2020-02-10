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
import com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.PlotShapeEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.RangePlotChartVisualization;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Range Plot Chart Visualization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.RangePlotChartVisualizationImpl#getPlotShape <em>Plot Shape</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.RangePlotChartVisualizationImpl#getPlotShapeDimension <em>Plot Shape Dimension</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.RangePlotChartVisualizationImpl#getWhiskerThickness <em>Whisker Thickness</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.RangePlotChartVisualizationImpl#getWhiskerWidth <em>Whisker Width</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.RangePlotChartVisualizationImpl#getOrientation <em>Orientation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RangePlotChartVisualizationImpl extends ChartVisualizationImpl implements RangePlotChartVisualization {
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
	protected static final int PLOT_SHAPE_DIMENSION_EDEFAULT = 3;

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
	 * The default value of the '{@link #getWhiskerThickness() <em>Whisker Thickness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWhiskerThickness()
	 * @generated
	 * @ordered
	 */
	protected static final int WHISKER_THICKNESS_EDEFAULT = 2;

	/**
	 * The cached value of the '{@link #getWhiskerThickness() <em>Whisker Thickness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWhiskerThickness()
	 * @generated
	 * @ordered
	 */
	protected int whiskerThickness = WHISKER_THICKNESS_EDEFAULT;

	/**
	 * This is true if the Whisker Thickness attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean whiskerThicknessESet;

	/**
	 * The default value of the '{@link #getWhiskerWidth() <em>Whisker Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWhiskerWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int WHISKER_WIDTH_EDEFAULT = 5;

	/**
	 * The cached value of the '{@link #getWhiskerWidth() <em>Whisker Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWhiskerWidth()
	 * @generated
	 * @ordered
	 */
	protected int whiskerWidth = WHISKER_WIDTH_EDEFAULT;

	/**
	 * This is true if the Whisker Width attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean whiskerWidthESet;

	/**
	 * The default value of the '{@link #getOrientation() <em>Orientation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrientation()
	 * @generated
	 * @ordered
	 */
	protected static final OrientationEnum ORIENTATION_EDEFAULT = OrientationEnum.VERTICAL;

	/**
	 * The cached value of the '{@link #getOrientation() <em>Orientation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrientation()
	 * @generated
	 * @ordered
	 */
	protected OrientationEnum orientation = ORIENTATION_EDEFAULT;

	/**
	 * This is true if the Orientation attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean orientationESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RangePlotChartVisualizationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getRangePlotChartVisualization();
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__PLOT_SHAPE, oldPlotShape, plotShape, !oldPlotShapeESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__PLOT_SHAPE, oldPlotShape, PLOT_SHAPE_EDEFAULT, oldPlotShapeESet));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION, oldPlotShapeDimension, plotShapeDimension, !oldPlotShapeDimensionESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION, oldPlotShapeDimension, PLOT_SHAPE_DIMENSION_EDEFAULT, oldPlotShapeDimensionESet));
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
	public int getWhiskerThickness() {
		return whiskerThickness;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWhiskerThickness(int newWhiskerThickness) {
		int oldWhiskerThickness = whiskerThickness;
		whiskerThickness = newWhiskerThickness;
		boolean oldWhiskerThicknessESet = whiskerThicknessESet;
		whiskerThicknessESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__WHISKER_THICKNESS, oldWhiskerThickness, whiskerThickness, !oldWhiskerThicknessESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetWhiskerThickness() {
		int oldWhiskerThickness = whiskerThickness;
		boolean oldWhiskerThicknessESet = whiskerThicknessESet;
		whiskerThickness = WHISKER_THICKNESS_EDEFAULT;
		whiskerThicknessESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__WHISKER_THICKNESS, oldWhiskerThickness, WHISKER_THICKNESS_EDEFAULT, oldWhiskerThicknessESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetWhiskerThickness() {
		return whiskerThicknessESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getWhiskerWidth() {
		return whiskerWidth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWhiskerWidth(int newWhiskerWidth) {
		int oldWhiskerWidth = whiskerWidth;
		whiskerWidth = newWhiskerWidth;
		boolean oldWhiskerWidthESet = whiskerWidthESet;
		whiskerWidthESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__WHISKER_WIDTH, oldWhiskerWidth, whiskerWidth, !oldWhiskerWidthESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetWhiskerWidth() {
		int oldWhiskerWidth = whiskerWidth;
		boolean oldWhiskerWidthESet = whiskerWidthESet;
		whiskerWidth = WHISKER_WIDTH_EDEFAULT;
		whiskerWidthESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__WHISKER_WIDTH, oldWhiskerWidth, WHISKER_WIDTH_EDEFAULT, oldWhiskerWidthESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetWhiskerWidth() {
		return whiskerWidthESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrientationEnum getOrientation() {
		return orientation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrientation(OrientationEnum newOrientation) {
		OrientationEnum oldOrientation = orientation;
		orientation = newOrientation == null ? ORIENTATION_EDEFAULT : newOrientation;
		boolean oldOrientationESet = orientationESet;
		orientationESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__ORIENTATION, oldOrientation, orientation, !oldOrientationESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetOrientation() {
		OrientationEnum oldOrientation = orientation;
		boolean oldOrientationESet = orientationESet;
		orientation = ORIENTATION_EDEFAULT;
		orientationESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__ORIENTATION, oldOrientation, ORIENTATION_EDEFAULT, oldOrientationESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetOrientation() {
		return orientationESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__PLOT_SHAPE:
				return getPlotShape();
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION:
				return getPlotShapeDimension();
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__WHISKER_THICKNESS:
				return getWhiskerThickness();
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__WHISKER_WIDTH:
				return getWhiskerWidth();
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__ORIENTATION:
				return getOrientation();
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
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__PLOT_SHAPE:
				setPlotShape((PlotShapeEnum)newValue);
				return;
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION:
				setPlotShapeDimension((Integer)newValue);
				return;
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__WHISKER_THICKNESS:
				setWhiskerThickness((Integer)newValue);
				return;
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__WHISKER_WIDTH:
				setWhiskerWidth((Integer)newValue);
				return;
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__ORIENTATION:
				setOrientation((OrientationEnum)newValue);
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
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__PLOT_SHAPE:
				unsetPlotShape();
				return;
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION:
				unsetPlotShapeDimension();
				return;
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__WHISKER_THICKNESS:
				unsetWhiskerThickness();
				return;
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__WHISKER_WIDTH:
				unsetWhiskerWidth();
				return;
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__ORIENTATION:
				unsetOrientation();
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
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__PLOT_SHAPE:
				return isSetPlotShape();
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__PLOT_SHAPE_DIMENSION:
				return isSetPlotShapeDimension();
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__WHISKER_THICKNESS:
				return isSetWhiskerThickness();
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__WHISKER_WIDTH:
				return isSetWhiskerWidth();
			case BEViewsConfigurationPackage.RANGE_PLOT_CHART_VISUALIZATION__ORIENTATION:
				return isSetOrientation();
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
		result.append(", whiskerThickness: ");
		if (whiskerThicknessESet) result.append(whiskerThickness); else result.append("<unset>");
		result.append(", whiskerWidth: ");
		if (whiskerWidthESet) result.append(whiskerWidth); else result.append("<unset>");
		result.append(", orientation: ");
		if (orientationESet) result.append(orientation); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //RangePlotChartVisualizationImpl
