/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.OneDimSeriesConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>One Dim Series Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.OneDimSeriesConfigImpl#getValueDataConfig <em>Value Data Config</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OneDimSeriesConfigImpl extends SeriesConfigImpl implements OneDimSeriesConfig {
	/**
	 * The cached value of the '{@link #getValueDataConfig() <em>Value Data Config</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValueDataConfig()
	 * @generated
	 * @ordered
	 */
	protected EList<DataConfig> valueDataConfig;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OneDimSeriesConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getOneDimSeriesConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DataConfig> getValueDataConfig() {
		if (valueDataConfig == null) {
			valueDataConfig = new EObjectContainmentEList<DataConfig>(DataConfig.class, this, BEViewsConfigurationPackage.ONE_DIM_SERIES_CONFIG__VALUE_DATA_CONFIG);
		}
		return valueDataConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.ONE_DIM_SERIES_CONFIG__VALUE_DATA_CONFIG:
				return ((InternalEList<?>)getValueDataConfig()).basicRemove(otherEnd, msgs);
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
			case BEViewsConfigurationPackage.ONE_DIM_SERIES_CONFIG__VALUE_DATA_CONFIG:
				return getValueDataConfig();
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
			case BEViewsConfigurationPackage.ONE_DIM_SERIES_CONFIG__VALUE_DATA_CONFIG:
				getValueDataConfig().clear();
				getValueDataConfig().addAll((Collection<? extends DataConfig>)newValue);
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
			case BEViewsConfigurationPackage.ONE_DIM_SERIES_CONFIG__VALUE_DATA_CONFIG:
				getValueDataConfig().clear();
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
			case BEViewsConfigurationPackage.ONE_DIM_SERIES_CONFIG__VALUE_DATA_CONFIG:
				return valueDataConfig != null && !valueDataConfig.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //OneDimSeriesConfigImpl
