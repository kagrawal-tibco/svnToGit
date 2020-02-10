/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateEnumeration;
import com.tibco.cep.designtime.core.model.beviewsconfig.AlertIndicatorStateMap;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Alert Indicator State Enumeration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertIndicatorStateEnumerationImpl#getFieldName <em>Field Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.AlertIndicatorStateEnumerationImpl#getMapping <em>Mapping</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AlertIndicatorStateEnumerationImpl extends BEViewsElementImpl implements AlertIndicatorStateEnumeration {
	/**
	 * The default value of the '{@link #getFieldName() <em>Field Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFieldName()
	 * @generated
	 * @ordered
	 */
	protected static final String FIELD_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFieldName() <em>Field Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFieldName()
	 * @generated
	 * @ordered
	 */
	protected String fieldName = FIELD_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMapping() <em>Mapping</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMapping()
	 * @generated
	 * @ordered
	 */
	protected EList<AlertIndicatorStateMap> mapping;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AlertIndicatorStateEnumerationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getAlertIndicatorStateEnumeration();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFieldName(String newFieldName) {
		String oldFieldName = fieldName;
		fieldName = newFieldName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_ENUMERATION__FIELD_NAME, oldFieldName, fieldName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AlertIndicatorStateMap> getMapping() {
		if (mapping == null) {
			mapping = new EObjectContainmentEList<AlertIndicatorStateMap>(AlertIndicatorStateMap.class, this, BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_ENUMERATION__MAPPING);
		}
		return mapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_ENUMERATION__MAPPING:
				return ((InternalEList<?>)getMapping()).basicRemove(otherEnd, msgs);
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
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_ENUMERATION__FIELD_NAME:
				return getFieldName();
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_ENUMERATION__MAPPING:
				return getMapping();
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
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_ENUMERATION__FIELD_NAME:
				setFieldName((String)newValue);
				return;
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_ENUMERATION__MAPPING:
				getMapping().clear();
				getMapping().addAll((Collection<? extends AlertIndicatorStateMap>)newValue);
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
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_ENUMERATION__FIELD_NAME:
				setFieldName(FIELD_NAME_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_ENUMERATION__MAPPING:
				getMapping().clear();
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
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_ENUMERATION__FIELD_NAME:
				return FIELD_NAME_EDEFAULT == null ? fieldName != null : !FIELD_NAME_EDEFAULT.equals(fieldName);
			case BEViewsConfigurationPackage.ALERT_INDICATOR_STATE_ENUMERATION__MAPPING:
				return mapping != null && !mapping.isEmpty();
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
		result.append(" (fieldName: ");
		result.append(fieldName);
		result.append(')');
		return result.toString();
	}

} //AlertIndicatorStateEnumerationImpl
