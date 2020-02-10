/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectEList;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponentColorSet;
import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesColor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Chart Component Color Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartComponentColorSetImpl#getSeriesColor <em>Series Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartComponentColorSetImpl#getGuideLineLabelFontColor <em>Guide Line Label Font Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartComponentColorSetImpl#getGuideLineValueLabelFontColor <em>Guide Line Value Label Font Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartComponentColorSetImpl#getDataPointLabelFontColor <em>Data Point Label Font Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartComponentColorSetImpl#getTopCapColor <em>Top Cap Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartComponentColorSetImpl#getPieEdgeColor <em>Pie Edge Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartComponentColorSetImpl#getPieDropShadowColor <em>Pie Drop Shadow Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartComponentColorSetImpl#getLineDropShadowColor <em>Line Drop Shadow Color</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChartComponentColorSetImpl extends ComponentColorSetImpl implements ChartComponentColorSet {
	/**
	 * The cached value of the '{@link #getSeriesColor() <em>Series Color</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeriesColor()
	 * @generated
	 * @ordered
	 */
	protected EList<SeriesColor> seriesColor;

	/**
	 * The default value of the '{@link #getGuideLineLabelFontColor() <em>Guide Line Label Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuideLineLabelFontColor()
	 * @generated
	 * @ordered
	 */
	protected static final String GUIDE_LINE_LABEL_FONT_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGuideLineLabelFontColor() <em>Guide Line Label Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuideLineLabelFontColor()
	 * @generated
	 * @ordered
	 */
	protected String guideLineLabelFontColor = GUIDE_LINE_LABEL_FONT_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getGuideLineValueLabelFontColor() <em>Guide Line Value Label Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuideLineValueLabelFontColor()
	 * @generated
	 * @ordered
	 */
	protected static final String GUIDE_LINE_VALUE_LABEL_FONT_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGuideLineValueLabelFontColor() <em>Guide Line Value Label Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGuideLineValueLabelFontColor()
	 * @generated
	 * @ordered
	 */
	protected String guideLineValueLabelFontColor = GUIDE_LINE_VALUE_LABEL_FONT_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getDataPointLabelFontColor() <em>Data Point Label Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataPointLabelFontColor()
	 * @generated
	 * @ordered
	 */
	protected static final String DATA_POINT_LABEL_FONT_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDataPointLabelFontColor() <em>Data Point Label Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataPointLabelFontColor()
	 * @generated
	 * @ordered
	 */
	protected String dataPointLabelFontColor = DATA_POINT_LABEL_FONT_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getTopCapColor() <em>Top Cap Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTopCapColor()
	 * @generated
	 * @ordered
	 */
	protected static final String TOP_CAP_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTopCapColor() <em>Top Cap Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTopCapColor()
	 * @generated
	 * @ordered
	 */
	protected String topCapColor = TOP_CAP_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getPieEdgeColor() <em>Pie Edge Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPieEdgeColor()
	 * @generated
	 * @ordered
	 */
	protected static final String PIE_EDGE_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPieEdgeColor() <em>Pie Edge Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPieEdgeColor()
	 * @generated
	 * @ordered
	 */
	protected String pieEdgeColor = PIE_EDGE_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getPieDropShadowColor() <em>Pie Drop Shadow Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPieDropShadowColor()
	 * @generated
	 * @ordered
	 */
	protected static final String PIE_DROP_SHADOW_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPieDropShadowColor() <em>Pie Drop Shadow Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPieDropShadowColor()
	 * @generated
	 * @ordered
	 */
	protected String pieDropShadowColor = PIE_DROP_SHADOW_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getLineDropShadowColor() <em>Line Drop Shadow Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineDropShadowColor()
	 * @generated
	 * @ordered
	 */
	protected static final String LINE_DROP_SHADOW_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLineDropShadowColor() <em>Line Drop Shadow Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLineDropShadowColor()
	 * @generated
	 * @ordered
	 */
	protected String lineDropShadowColor = LINE_DROP_SHADOW_COLOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChartComponentColorSetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getChartComponentColorSet();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SeriesColor> getSeriesColor() {
		if (seriesColor == null) {
			seriesColor = new EObjectEList<SeriesColor>(SeriesColor.class, this, BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__SERIES_COLOR);
		}
		return seriesColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGuideLineLabelFontColor() {
		return guideLineLabelFontColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGuideLineLabelFontColor(String newGuideLineLabelFontColor) {
		String oldGuideLineLabelFontColor = guideLineLabelFontColor;
		guideLineLabelFontColor = newGuideLineLabelFontColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__GUIDE_LINE_LABEL_FONT_COLOR, oldGuideLineLabelFontColor, guideLineLabelFontColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGuideLineValueLabelFontColor() {
		return guideLineValueLabelFontColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGuideLineValueLabelFontColor(String newGuideLineValueLabelFontColor) {
		String oldGuideLineValueLabelFontColor = guideLineValueLabelFontColor;
		guideLineValueLabelFontColor = newGuideLineValueLabelFontColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__GUIDE_LINE_VALUE_LABEL_FONT_COLOR, oldGuideLineValueLabelFontColor, guideLineValueLabelFontColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDataPointLabelFontColor() {
		return dataPointLabelFontColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataPointLabelFontColor(String newDataPointLabelFontColor) {
		String oldDataPointLabelFontColor = dataPointLabelFontColor;
		dataPointLabelFontColor = newDataPointLabelFontColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__DATA_POINT_LABEL_FONT_COLOR, oldDataPointLabelFontColor, dataPointLabelFontColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTopCapColor() {
		return topCapColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTopCapColor(String newTopCapColor) {
		String oldTopCapColor = topCapColor;
		topCapColor = newTopCapColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__TOP_CAP_COLOR, oldTopCapColor, topCapColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPieEdgeColor() {
		return pieEdgeColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPieEdgeColor(String newPieEdgeColor) {
		String oldPieEdgeColor = pieEdgeColor;
		pieEdgeColor = newPieEdgeColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__PIE_EDGE_COLOR, oldPieEdgeColor, pieEdgeColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPieDropShadowColor() {
		return pieDropShadowColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPieDropShadowColor(String newPieDropShadowColor) {
		String oldPieDropShadowColor = pieDropShadowColor;
		pieDropShadowColor = newPieDropShadowColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__PIE_DROP_SHADOW_COLOR, oldPieDropShadowColor, pieDropShadowColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLineDropShadowColor() {
		return lineDropShadowColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLineDropShadowColor(String newLineDropShadowColor) {
		String oldLineDropShadowColor = lineDropShadowColor;
		lineDropShadowColor = newLineDropShadowColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__LINE_DROP_SHADOW_COLOR, oldLineDropShadowColor, lineDropShadowColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__SERIES_COLOR:
				return getSeriesColor();
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__GUIDE_LINE_LABEL_FONT_COLOR:
				return getGuideLineLabelFontColor();
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__GUIDE_LINE_VALUE_LABEL_FONT_COLOR:
				return getGuideLineValueLabelFontColor();
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__DATA_POINT_LABEL_FONT_COLOR:
				return getDataPointLabelFontColor();
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__TOP_CAP_COLOR:
				return getTopCapColor();
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__PIE_EDGE_COLOR:
				return getPieEdgeColor();
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__PIE_DROP_SHADOW_COLOR:
				return getPieDropShadowColor();
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__LINE_DROP_SHADOW_COLOR:
				return getLineDropShadowColor();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__SERIES_COLOR:
				getSeriesColor().clear();
				getSeriesColor().addAll((Collection<? extends SeriesColor>)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__GUIDE_LINE_LABEL_FONT_COLOR:
				setGuideLineLabelFontColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__GUIDE_LINE_VALUE_LABEL_FONT_COLOR:
				setGuideLineValueLabelFontColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__DATA_POINT_LABEL_FONT_COLOR:
				setDataPointLabelFontColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__TOP_CAP_COLOR:
				setTopCapColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__PIE_EDGE_COLOR:
				setPieEdgeColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__PIE_DROP_SHADOW_COLOR:
				setPieDropShadowColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__LINE_DROP_SHADOW_COLOR:
				setLineDropShadowColor((String)newValue);
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
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__SERIES_COLOR:
				getSeriesColor().clear();
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__GUIDE_LINE_LABEL_FONT_COLOR:
				setGuideLineLabelFontColor(GUIDE_LINE_LABEL_FONT_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__GUIDE_LINE_VALUE_LABEL_FONT_COLOR:
				setGuideLineValueLabelFontColor(GUIDE_LINE_VALUE_LABEL_FONT_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__DATA_POINT_LABEL_FONT_COLOR:
				setDataPointLabelFontColor(DATA_POINT_LABEL_FONT_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__TOP_CAP_COLOR:
				setTopCapColor(TOP_CAP_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__PIE_EDGE_COLOR:
				setPieEdgeColor(PIE_EDGE_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__PIE_DROP_SHADOW_COLOR:
				setPieDropShadowColor(PIE_DROP_SHADOW_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__LINE_DROP_SHADOW_COLOR:
				setLineDropShadowColor(LINE_DROP_SHADOW_COLOR_EDEFAULT);
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
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__SERIES_COLOR:
				return seriesColor != null && !seriesColor.isEmpty();
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__GUIDE_LINE_LABEL_FONT_COLOR:
				return GUIDE_LINE_LABEL_FONT_COLOR_EDEFAULT == null ? guideLineLabelFontColor != null : !GUIDE_LINE_LABEL_FONT_COLOR_EDEFAULT.equals(guideLineLabelFontColor);
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__GUIDE_LINE_VALUE_LABEL_FONT_COLOR:
				return GUIDE_LINE_VALUE_LABEL_FONT_COLOR_EDEFAULT == null ? guideLineValueLabelFontColor != null : !GUIDE_LINE_VALUE_LABEL_FONT_COLOR_EDEFAULT.equals(guideLineValueLabelFontColor);
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__DATA_POINT_LABEL_FONT_COLOR:
				return DATA_POINT_LABEL_FONT_COLOR_EDEFAULT == null ? dataPointLabelFontColor != null : !DATA_POINT_LABEL_FONT_COLOR_EDEFAULT.equals(dataPointLabelFontColor);
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__TOP_CAP_COLOR:
				return TOP_CAP_COLOR_EDEFAULT == null ? topCapColor != null : !TOP_CAP_COLOR_EDEFAULT.equals(topCapColor);
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__PIE_EDGE_COLOR:
				return PIE_EDGE_COLOR_EDEFAULT == null ? pieEdgeColor != null : !PIE_EDGE_COLOR_EDEFAULT.equals(pieEdgeColor);
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__PIE_DROP_SHADOW_COLOR:
				return PIE_DROP_SHADOW_COLOR_EDEFAULT == null ? pieDropShadowColor != null : !PIE_DROP_SHADOW_COLOR_EDEFAULT.equals(pieDropShadowColor);
			case BEViewsConfigurationPackage.CHART_COMPONENT_COLOR_SET__LINE_DROP_SHADOW_COLOR:
				return LINE_DROP_SHADOW_COLOR_EDEFAULT == null ? lineDropShadowColor != null : !LINE_DROP_SHADOW_COLOR_EDEFAULT.equals(lineDropShadowColor);
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
		result.append(" (guideLineLabelFontColor: ");
		result.append(guideLineLabelFontColor);
		result.append(", guideLineValueLabelFontColor: ");
		result.append(guideLineValueLabelFontColor);
		result.append(", dataPointLabelFontColor: ");
		result.append(dataPointLabelFontColor);
		result.append(", topCapColor: ");
		result.append(topCapColor);
		result.append(", pieEdgeColor: ");
		result.append(pieEdgeColor);
		result.append(", pieDropShadowColor: ");
		result.append(pieDropShadowColor);
		result.append(", lineDropShadowColor: ");
		result.append(lineDropShadowColor);
		result.append(')');
		return result.toString();
	}

} //ChartComponentColorSetImpl
