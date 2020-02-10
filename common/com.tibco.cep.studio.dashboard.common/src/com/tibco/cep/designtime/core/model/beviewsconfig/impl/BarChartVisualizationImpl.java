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
import com.tibco.cep.designtime.core.model.beviewsconfig.BarChartVisualization;
import com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Bar Chart Visualization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.BarChartVisualizationImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.BarChartVisualizationImpl#getTopCapThickness <em>Top Cap Thickness</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.BarChartVisualizationImpl#getOverlapPercentage <em>Overlap Percentage</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.BarChartVisualizationImpl#getOrientation <em>Orientation</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BarChartVisualizationImpl extends ChartVisualizationImpl implements BarChartVisualization {
	/**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int WIDTH_EDEFAULT = 10;

	/**
	 * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected int width = WIDTH_EDEFAULT;

	/**
	 * This is true if the Width attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean widthESet;

	/**
	 * The default value of the '{@link #getTopCapThickness() <em>Top Cap Thickness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTopCapThickness()
	 * @generated
	 * @ordered
	 */
	protected static final double TOP_CAP_THICKNESS_EDEFAULT = 0.5;

	/**
	 * The cached value of the '{@link #getTopCapThickness() <em>Top Cap Thickness</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTopCapThickness()
	 * @generated
	 * @ordered
	 */
	protected double topCapThickness = TOP_CAP_THICKNESS_EDEFAULT;

	/**
	 * This is true if the Top Cap Thickness attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean topCapThicknessESet;

	/**
	 * The default value of the '{@link #getOverlapPercentage() <em>Overlap Percentage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOverlapPercentage()
	 * @generated
	 * @ordered
	 */
	protected static final int OVERLAP_PERCENTAGE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOverlapPercentage() <em>Overlap Percentage</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOverlapPercentage()
	 * @generated
	 * @ordered
	 */
	protected int overlapPercentage = OVERLAP_PERCENTAGE_EDEFAULT;

	/**
	 * This is true if the Overlap Percentage attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean overlapPercentageESet;

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
	protected BarChartVisualizationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getBarChartVisualization();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidth(int newWidth) {
		int oldWidth = width;
		width = newWidth;
		boolean oldWidthESet = widthESet;
		widthESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__WIDTH, oldWidth, width, !oldWidthESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetWidth() {
		int oldWidth = width;
		boolean oldWidthESet = widthESet;
		width = WIDTH_EDEFAULT;
		widthESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__WIDTH, oldWidth, WIDTH_EDEFAULT, oldWidthESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetWidth() {
		return widthESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getTopCapThickness() {
		return topCapThickness;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTopCapThickness(double newTopCapThickness) {
		double oldTopCapThickness = topCapThickness;
		topCapThickness = newTopCapThickness;
		boolean oldTopCapThicknessESet = topCapThicknessESet;
		topCapThicknessESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__TOP_CAP_THICKNESS, oldTopCapThickness, topCapThickness, !oldTopCapThicknessESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTopCapThickness() {
		double oldTopCapThickness = topCapThickness;
		boolean oldTopCapThicknessESet = topCapThicknessESet;
		topCapThickness = TOP_CAP_THICKNESS_EDEFAULT;
		topCapThicknessESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__TOP_CAP_THICKNESS, oldTopCapThickness, TOP_CAP_THICKNESS_EDEFAULT, oldTopCapThicknessESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTopCapThickness() {
		return topCapThicknessESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOverlapPercentage() {
		return overlapPercentage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOverlapPercentage(int newOverlapPercentage) {
		int oldOverlapPercentage = overlapPercentage;
		overlapPercentage = newOverlapPercentage;
		boolean oldOverlapPercentageESet = overlapPercentageESet;
		overlapPercentageESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__OVERLAP_PERCENTAGE, oldOverlapPercentage, overlapPercentage, !oldOverlapPercentageESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetOverlapPercentage() {
		int oldOverlapPercentage = overlapPercentage;
		boolean oldOverlapPercentageESet = overlapPercentageESet;
		overlapPercentage = OVERLAP_PERCENTAGE_EDEFAULT;
		overlapPercentageESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__OVERLAP_PERCENTAGE, oldOverlapPercentage, OVERLAP_PERCENTAGE_EDEFAULT, oldOverlapPercentageESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetOverlapPercentage() {
		return overlapPercentageESet;
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__ORIENTATION, oldOrientation, orientation, !oldOrientationESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__ORIENTATION, oldOrientation, ORIENTATION_EDEFAULT, oldOrientationESet));
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
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__WIDTH:
				return getWidth();
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__TOP_CAP_THICKNESS:
				return getTopCapThickness();
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__OVERLAP_PERCENTAGE:
				return getOverlapPercentage();
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__ORIENTATION:
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
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__WIDTH:
				setWidth((Integer)newValue);
				return;
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__TOP_CAP_THICKNESS:
				setTopCapThickness((Double)newValue);
				return;
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__OVERLAP_PERCENTAGE:
				setOverlapPercentage((Integer)newValue);
				return;
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__ORIENTATION:
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
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__WIDTH:
				unsetWidth();
				return;
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__TOP_CAP_THICKNESS:
				unsetTopCapThickness();
				return;
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__OVERLAP_PERCENTAGE:
				unsetOverlapPercentage();
				return;
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__ORIENTATION:
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
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__WIDTH:
				return isSetWidth();
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__TOP_CAP_THICKNESS:
				return isSetTopCapThickness();
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__OVERLAP_PERCENTAGE:
				return isSetOverlapPercentage();
			case BEViewsConfigurationPackage.BAR_CHART_VISUALIZATION__ORIENTATION:
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
		result.append(" (width: ");
		if (widthESet) result.append(width); else result.append("<unset>");
		result.append(", topCapThickness: ");
		if (topCapThicknessESet) result.append(topCapThickness); else result.append("<unset>");
		result.append(", overlapPercentage: ");
		if (overlapPercentageESet) result.append(overlapPercentage); else result.append("<unset>");
		result.append(", orientation: ");
		if (orientationESet) result.append(orientation); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //BarChartVisualizationImpl
