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
import com.tibco.cep.designtime.core.model.beviewsconfig.ChartCategoryDataConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.ChartVisualization;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Chart Visualization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ChartVisualizationImpl#getSharedCategoryDataConfig <em>Shared Category Data Config</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ChartVisualizationImpl extends TwoDimVisualizationImpl implements ChartVisualization {
	/**
	 * The cached value of the '{@link #getSharedCategoryDataConfig() <em>Shared Category Data Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSharedCategoryDataConfig()
	 * @generated
	 * @ordered
	 */
	protected ChartCategoryDataConfig sharedCategoryDataConfig;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChartVisualizationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getChartVisualization();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ChartCategoryDataConfig getSharedCategoryDataConfig() {
		return sharedCategoryDataConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSharedCategoryDataConfig(ChartCategoryDataConfig newSharedCategoryDataConfig, NotificationChain msgs) {
		ChartCategoryDataConfig oldSharedCategoryDataConfig = sharedCategoryDataConfig;
		sharedCategoryDataConfig = newSharedCategoryDataConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG, oldSharedCategoryDataConfig, newSharedCategoryDataConfig);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSharedCategoryDataConfig(ChartCategoryDataConfig newSharedCategoryDataConfig) {
		if (newSharedCategoryDataConfig != sharedCategoryDataConfig) {
			NotificationChain msgs = null;
			if (sharedCategoryDataConfig != null)
				msgs = ((InternalEObject)sharedCategoryDataConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG, null, msgs);
			if (newSharedCategoryDataConfig != null)
				msgs = ((InternalEObject)newSharedCategoryDataConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG, null, msgs);
			msgs = basicSetSharedCategoryDataConfig(newSharedCategoryDataConfig, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG, newSharedCategoryDataConfig, newSharedCategoryDataConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG:
				return basicSetSharedCategoryDataConfig(null, msgs);
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
			case BEViewsConfigurationPackage.CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG:
				return getSharedCategoryDataConfig();
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
			case BEViewsConfigurationPackage.CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG:
				setSharedCategoryDataConfig((ChartCategoryDataConfig)newValue);
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
			case BEViewsConfigurationPackage.CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG:
				setSharedCategoryDataConfig((ChartCategoryDataConfig)null);
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
			case BEViewsConfigurationPackage.CHART_VISUALIZATION__SHARED_CATEGORY_DATA_CONFIG:
				return sharedCategoryDataConfig != null;
		}
		return super.eIsSet(featureID);
	}

} //ChartVisualizationImpl
