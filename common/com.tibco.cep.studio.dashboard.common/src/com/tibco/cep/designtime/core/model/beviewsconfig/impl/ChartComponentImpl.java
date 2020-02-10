/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryGuidelineConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.ChartComponent;
import com.tibco.cep.designtime.core.model.beviewsconfig.ChartValueGuidelineConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.LegendFormat;
import com.tibco.cep.designtime.core.model.beviewsconfig.PlotAreaFormat;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Chart Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartComponentImpl#getPlotArea <em>Plot Area</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartComponentImpl#getLegend <em>Legend</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartComponentImpl#getCategoryGuidelineConfig <em>Category Guideline Config</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartComponentImpl#getValueGuidelineConfig <em>Value Guideline Config</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChartComponentImpl extends ComponentImpl implements ChartComponent {
	/**
	 * The cached value of the '{@link #getPlotArea() <em>Plot Area</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlotArea()
	 * @generated
	 * @ordered
	 */
	protected PlotAreaFormat plotArea;

	/**
	 * The cached value of the '{@link #getLegend() <em>Legend</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLegend()
	 * @generated
	 * @ordered
	 */
	protected LegendFormat legend;

	/**
	 * The cached value of the '{@link #getCategoryGuidelineConfig() <em>Category Guideline Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategoryGuidelineConfig()
	 * @generated
	 * @ordered
	 */
	protected ChartCategoryGuidelineConfig categoryGuidelineConfig;

	/**
	 * The cached value of the '{@link #getValueGuidelineConfig() <em>Value Guideline Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueGuidelineConfig()
	 * @generated
	 * @ordered
	 */
	protected ChartValueGuidelineConfig valueGuidelineConfig;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChartComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getChartComponent();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlotAreaFormat getPlotArea() {
		return plotArea;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPlotArea(PlotAreaFormat newPlotArea, NotificationChain msgs) {
		PlotAreaFormat oldPlotArea = plotArea;
		plotArea = newPlotArea;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_COMPONENT__PLOT_AREA, oldPlotArea, newPlotArea);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPlotArea(PlotAreaFormat newPlotArea) {
		if (newPlotArea != plotArea) {
			NotificationChain msgs = null;
			if (plotArea != null)
				msgs = ((InternalEObject)plotArea).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CHART_COMPONENT__PLOT_AREA, null, msgs);
			if (newPlotArea != null)
				msgs = ((InternalEObject)newPlotArea).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CHART_COMPONENT__PLOT_AREA, null, msgs);
			msgs = basicSetPlotArea(newPlotArea, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_COMPONENT__PLOT_AREA, newPlotArea, newPlotArea));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LegendFormat getLegend() {
		return legend;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLegend(LegendFormat newLegend, NotificationChain msgs) {
		LegendFormat oldLegend = legend;
		legend = newLegend;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_COMPONENT__LEGEND, oldLegend, newLegend);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLegend(LegendFormat newLegend) {
		if (newLegend != legend) {
			NotificationChain msgs = null;
			if (legend != null)
				msgs = ((InternalEObject)legend).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CHART_COMPONENT__LEGEND, null, msgs);
			if (newLegend != null)
				msgs = ((InternalEObject)newLegend).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CHART_COMPONENT__LEGEND, null, msgs);
			msgs = basicSetLegend(newLegend, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_COMPONENT__LEGEND, newLegend, newLegend));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChartCategoryGuidelineConfig getCategoryGuidelineConfig() {
		return categoryGuidelineConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCategoryGuidelineConfig(ChartCategoryGuidelineConfig newCategoryGuidelineConfig, NotificationChain msgs) {
		ChartCategoryGuidelineConfig oldCategoryGuidelineConfig = categoryGuidelineConfig;
		categoryGuidelineConfig = newCategoryGuidelineConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_COMPONENT__CATEGORY_GUIDELINE_CONFIG, oldCategoryGuidelineConfig, newCategoryGuidelineConfig);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCategoryGuidelineConfig(ChartCategoryGuidelineConfig newCategoryGuidelineConfig) {
		if (newCategoryGuidelineConfig != categoryGuidelineConfig) {
			NotificationChain msgs = null;
			if (categoryGuidelineConfig != null)
				msgs = ((InternalEObject)categoryGuidelineConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CHART_COMPONENT__CATEGORY_GUIDELINE_CONFIG, null, msgs);
			if (newCategoryGuidelineConfig != null)
				msgs = ((InternalEObject)newCategoryGuidelineConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CHART_COMPONENT__CATEGORY_GUIDELINE_CONFIG, null, msgs);
			msgs = basicSetCategoryGuidelineConfig(newCategoryGuidelineConfig, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_COMPONENT__CATEGORY_GUIDELINE_CONFIG, newCategoryGuidelineConfig, newCategoryGuidelineConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChartValueGuidelineConfig getValueGuidelineConfig() {
		return valueGuidelineConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValueGuidelineConfig(ChartValueGuidelineConfig newValueGuidelineConfig, NotificationChain msgs) {
		ChartValueGuidelineConfig oldValueGuidelineConfig = valueGuidelineConfig;
		valueGuidelineConfig = newValueGuidelineConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_COMPONENT__VALUE_GUIDELINE_CONFIG, oldValueGuidelineConfig, newValueGuidelineConfig);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValueGuidelineConfig(ChartValueGuidelineConfig newValueGuidelineConfig) {
		if (newValueGuidelineConfig != valueGuidelineConfig) {
			NotificationChain msgs = null;
			if (valueGuidelineConfig != null)
				msgs = ((InternalEObject)valueGuidelineConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CHART_COMPONENT__VALUE_GUIDELINE_CONFIG, null, msgs);
			if (newValueGuidelineConfig != null)
				msgs = ((InternalEObject)newValueGuidelineConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CHART_COMPONENT__VALUE_GUIDELINE_CONFIG, null, msgs);
			msgs = basicSetValueGuidelineConfig(newValueGuidelineConfig, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_COMPONENT__VALUE_GUIDELINE_CONFIG, newValueGuidelineConfig, newValueGuidelineConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.CHART_COMPONENT__PLOT_AREA:
				return basicSetPlotArea(null, msgs);
			case BEViewsConfigurationPackage.CHART_COMPONENT__LEGEND:
				return basicSetLegend(null, msgs);
			case BEViewsConfigurationPackage.CHART_COMPONENT__CATEGORY_GUIDELINE_CONFIG:
				return basicSetCategoryGuidelineConfig(null, msgs);
			case BEViewsConfigurationPackage.CHART_COMPONENT__VALUE_GUIDELINE_CONFIG:
				return basicSetValueGuidelineConfig(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.CHART_COMPONENT__PLOT_AREA:
				return getPlotArea();
			case BEViewsConfigurationPackage.CHART_COMPONENT__LEGEND:
				return getLegend();
			case BEViewsConfigurationPackage.CHART_COMPONENT__CATEGORY_GUIDELINE_CONFIG:
				return getCategoryGuidelineConfig();
			case BEViewsConfigurationPackage.CHART_COMPONENT__VALUE_GUIDELINE_CONFIG:
				return getValueGuidelineConfig();
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
			case BEViewsConfigurationPackage.CHART_COMPONENT__PLOT_AREA:
				setPlotArea((PlotAreaFormat)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT__LEGEND:
				setLegend((LegendFormat)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT__CATEGORY_GUIDELINE_CONFIG:
				setCategoryGuidelineConfig((ChartCategoryGuidelineConfig)newValue);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT__VALUE_GUIDELINE_CONFIG:
				setValueGuidelineConfig((ChartValueGuidelineConfig)newValue);
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
			case BEViewsConfigurationPackage.CHART_COMPONENT__PLOT_AREA:
				setPlotArea((PlotAreaFormat)null);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT__LEGEND:
				setLegend((LegendFormat)null);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT__CATEGORY_GUIDELINE_CONFIG:
				setCategoryGuidelineConfig((ChartCategoryGuidelineConfig)null);
				return;
			case BEViewsConfigurationPackage.CHART_COMPONENT__VALUE_GUIDELINE_CONFIG:
				setValueGuidelineConfig((ChartValueGuidelineConfig)null);
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
			case BEViewsConfigurationPackage.CHART_COMPONENT__PLOT_AREA:
				return plotArea != null;
			case BEViewsConfigurationPackage.CHART_COMPONENT__LEGEND:
				return legend != null;
			case BEViewsConfigurationPackage.CHART_COMPONENT__CATEGORY_GUIDELINE_CONFIG:
				return categoryGuidelineConfig != null;
			case BEViewsConfigurationPackage.CHART_COMPONENT__VALUE_GUIDELINE_CONFIG:
				return valueGuidelineConfig != null;
		}
		return super.eIsSet(featureID);
	}

} //ChartComponentImpl
