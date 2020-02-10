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
import com.tibco.cep.designtime.core.model.beviewsconfig.OneDimVisualization;
import com.tibco.cep.designtime.core.model.beviewsconfig.ValueGuidelineConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>One Dim Visualization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.OneDimVisualizationImpl#getValueGuidelineConfig <em>Value Guideline Config</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OneDimVisualizationImpl extends VisualizationImpl implements OneDimVisualization {
	/**
	 * The cached value of the '{@link #getValueGuidelineConfig() <em>Value Guideline Config</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueGuidelineConfig()
	 * @generated
	 * @ordered
	 */
	protected ValueGuidelineConfig valueGuidelineConfig;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OneDimVisualizationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getOneDimVisualization();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueGuidelineConfig getValueGuidelineConfig() {
		return valueGuidelineConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValueGuidelineConfig(ValueGuidelineConfig newValueGuidelineConfig, NotificationChain msgs) {
		ValueGuidelineConfig oldValueGuidelineConfig = valueGuidelineConfig;
		valueGuidelineConfig = newValueGuidelineConfig;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.ONE_DIM_VISUALIZATION__VALUE_GUIDELINE_CONFIG, oldValueGuidelineConfig, newValueGuidelineConfig);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValueGuidelineConfig(ValueGuidelineConfig newValueGuidelineConfig) {
		if (newValueGuidelineConfig != valueGuidelineConfig) {
			NotificationChain msgs = null;
			if (valueGuidelineConfig != null)
				msgs = ((InternalEObject)valueGuidelineConfig).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.ONE_DIM_VISUALIZATION__VALUE_GUIDELINE_CONFIG, null, msgs);
			if (newValueGuidelineConfig != null)
				msgs = ((InternalEObject)newValueGuidelineConfig).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.ONE_DIM_VISUALIZATION__VALUE_GUIDELINE_CONFIG, null, msgs);
			msgs = basicSetValueGuidelineConfig(newValueGuidelineConfig, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.ONE_DIM_VISUALIZATION__VALUE_GUIDELINE_CONFIG, newValueGuidelineConfig, newValueGuidelineConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.ONE_DIM_VISUALIZATION__VALUE_GUIDELINE_CONFIG:
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
			case BEViewsConfigurationPackage.ONE_DIM_VISUALIZATION__VALUE_GUIDELINE_CONFIG:
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
			case BEViewsConfigurationPackage.ONE_DIM_VISUALIZATION__VALUE_GUIDELINE_CONFIG:
				setValueGuidelineConfig((ValueGuidelineConfig)newValue);
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
			case BEViewsConfigurationPackage.ONE_DIM_VISUALIZATION__VALUE_GUIDELINE_CONFIG:
				setValueGuidelineConfig((ValueGuidelineConfig)null);
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
			case BEViewsConfigurationPackage.ONE_DIM_VISUALIZATION__VALUE_GUIDELINE_CONFIG:
				return valueGuidelineConfig != null;
		}
		return super.eIsSet(featureID);
	}

} //OneDimVisualizationImpl
