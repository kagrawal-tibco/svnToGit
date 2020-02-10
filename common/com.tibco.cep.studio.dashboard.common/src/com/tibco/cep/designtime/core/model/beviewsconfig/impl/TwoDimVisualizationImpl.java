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
import com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.TwoDimVisualization;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Two Dim Visualization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TwoDimVisualizationImpl#getCategoryGuidelineConfig <em>Category Guideline Config</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TwoDimVisualizationImpl extends OneDimVisualizationImpl implements TwoDimVisualization {
	/**
	 * The cached value of the '{@link #getCategoryGuidelineConfig() <em>Category Guideline Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategoryGuidelineConfig()
	 * @generated
	 * @ordered
	 */
	protected CategoryGuidelineConfig categoryGuidelineConfig;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TwoDimVisualizationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getTwoDimVisualization();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CategoryGuidelineConfig getCategoryGuidelineConfig() {
		return categoryGuidelineConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCategoryGuidelineConfig(CategoryGuidelineConfig newCategoryGuidelineConfig, NotificationChain msgs) {
		CategoryGuidelineConfig oldCategoryGuidelineConfig = categoryGuidelineConfig;
		categoryGuidelineConfig = newCategoryGuidelineConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TWO_DIM_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG, oldCategoryGuidelineConfig, newCategoryGuidelineConfig);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCategoryGuidelineConfig(CategoryGuidelineConfig newCategoryGuidelineConfig) {
		if (newCategoryGuidelineConfig != categoryGuidelineConfig) {
			NotificationChain msgs = null;
			if (categoryGuidelineConfig != null)
				msgs = ((InternalEObject)categoryGuidelineConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.TWO_DIM_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG, null, msgs);
			if (newCategoryGuidelineConfig != null)
				msgs = ((InternalEObject)newCategoryGuidelineConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.TWO_DIM_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG, null, msgs);
			msgs = basicSetCategoryGuidelineConfig(newCategoryGuidelineConfig, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TWO_DIM_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG, newCategoryGuidelineConfig, newCategoryGuidelineConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.TWO_DIM_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG:
				return basicSetCategoryGuidelineConfig(null, msgs);
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
			case BEViewsConfigurationPackage.TWO_DIM_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG:
				return getCategoryGuidelineConfig();
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
			case BEViewsConfigurationPackage.TWO_DIM_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG:
				setCategoryGuidelineConfig((CategoryGuidelineConfig)newValue);
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
			case BEViewsConfigurationPackage.TWO_DIM_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG:
				setCategoryGuidelineConfig((CategoryGuidelineConfig)null);
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
			case BEViewsConfigurationPackage.TWO_DIM_VISUALIZATION__CATEGORY_GUIDELINE_CONFIG:
				return categoryGuidelineConfig != null;
		}
		return super.eIsSet(featureID);
	}

} //TwoDimVisualizationImpl
