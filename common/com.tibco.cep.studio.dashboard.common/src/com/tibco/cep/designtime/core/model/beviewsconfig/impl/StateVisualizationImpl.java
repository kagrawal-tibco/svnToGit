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
import com.tibco.cep.designtime.core.model.beviewsconfig.StateVisualization;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Visualization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.StateVisualizationImpl#getStateRefID <em>State Ref ID</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateVisualizationImpl extends OneDimVisualizationImpl implements StateVisualization {
	/**
	 * The default value of the '{@link #getStateRefID() <em>State Ref ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateRefID()
	 * @generated
	 * @ordered
	 */
	protected static final String STATE_REF_ID_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getStateRefID() <em>State Ref ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateRefID()
	 * @generated
	 * @ordered
	 */
	protected String stateRefID = STATE_REF_ID_EDEFAULT;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected StateVisualizationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getStateVisualization();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStateRefID() {
		return stateRefID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStateRefID(String newStateRefID) {
		String oldStateRefID = stateRefID;
		stateRefID = newStateRefID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.STATE_VISUALIZATION__STATE_REF_ID, oldStateRefID, stateRefID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.STATE_VISUALIZATION__STATE_REF_ID:
				return getStateRefID();
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
			case BEViewsConfigurationPackage.STATE_VISUALIZATION__STATE_REF_ID:
				setStateRefID((String)newValue);
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
			case BEViewsConfigurationPackage.STATE_VISUALIZATION__STATE_REF_ID:
				setStateRefID(STATE_REF_ID_EDEFAULT);
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
			case BEViewsConfigurationPackage.STATE_VISUALIZATION__STATE_REF_ID:
				return STATE_REF_ID_EDEFAULT == null ? stateRefID != null : !STATE_REF_ID_EDEFAULT.equals(stateRefID);
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
		result.append(" (stateRefID: ");
		result.append(stateRefID);
		result.append(')');
		return result.toString();
	}

} //StateVisualizationImpl
