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
import com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimSeriesConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Two Dim Series Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TwoDimSeriesConfigImpl#getCategoryDataConfig <em>Category Data Config</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TwoDimSeriesConfigImpl extends OneDimSeriesConfigImpl implements TwoDimSeriesConfig {
	/**
	 * The cached value of the '{@link #getCategoryDataConfig() <em>Category Data Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategoryDataConfig()
	 * @generated
	 * @ordered
	 */
	protected DataConfig categoryDataConfig;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TwoDimSeriesConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getTwoDimSeriesConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataConfig getCategoryDataConfig() {
		return categoryDataConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCategoryDataConfig(DataConfig newCategoryDataConfig, NotificationChain msgs) {
		DataConfig oldCategoryDataConfig = categoryDataConfig;
		categoryDataConfig = newCategoryDataConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TWO_DIM_SERIES_CONFIG__CATEGORY_DATA_CONFIG, oldCategoryDataConfig, newCategoryDataConfig);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCategoryDataConfig(DataConfig newCategoryDataConfig) {
		if (newCategoryDataConfig != categoryDataConfig) {
			NotificationChain msgs = null;
			if (categoryDataConfig != null)
				msgs = ((InternalEObject)categoryDataConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.TWO_DIM_SERIES_CONFIG__CATEGORY_DATA_CONFIG, null, msgs);
			if (newCategoryDataConfig != null)
				msgs = ((InternalEObject)newCategoryDataConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.TWO_DIM_SERIES_CONFIG__CATEGORY_DATA_CONFIG, null, msgs);
			msgs = basicSetCategoryDataConfig(newCategoryDataConfig, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TWO_DIM_SERIES_CONFIG__CATEGORY_DATA_CONFIG, newCategoryDataConfig, newCategoryDataConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.TWO_DIM_SERIES_CONFIG__CATEGORY_DATA_CONFIG:
				return basicSetCategoryDataConfig(null, msgs);
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
			case BEViewsConfigurationPackage.TWO_DIM_SERIES_CONFIG__CATEGORY_DATA_CONFIG:
				return getCategoryDataConfig();
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
			case BEViewsConfigurationPackage.TWO_DIM_SERIES_CONFIG__CATEGORY_DATA_CONFIG:
				setCategoryDataConfig((DataConfig)newValue);
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
			case BEViewsConfigurationPackage.TWO_DIM_SERIES_CONFIG__CATEGORY_DATA_CONFIG:
				setCategoryDataConfig((DataConfig)null);
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
			case BEViewsConfigurationPackage.TWO_DIM_SERIES_CONFIG__CATEGORY_DATA_CONFIG:
				return categoryDataConfig != null;
		}
		return super.eIsSet(featureID);
	}

} //TwoDimSeriesConfigImpl
