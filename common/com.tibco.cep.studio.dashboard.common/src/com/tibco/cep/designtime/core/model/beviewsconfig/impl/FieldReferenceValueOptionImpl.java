/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.FieldReferenceValueOption;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Field Reference Value Option</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.FieldReferenceValueOptionImpl#getField <em>Field</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FieldReferenceValueOptionImpl extends ValueOptionImpl implements FieldReferenceValueOption {
	/**
	 * The cached value of the '{@link #getField() <em>Field</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getField()
	 * @generated
	 * @ordered
	 */
	protected EObject field;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FieldReferenceValueOptionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getFieldReferenceValueOption();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getField() {
		if (field != null && field.eIsProxy()) {
			InternalEObject oldField = (InternalEObject)field;
			field = eResolveProxy(oldField);
			if (field != oldField) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BEViewsConfigurationPackage.FIELD_REFERENCE_VALUE_OPTION__FIELD, oldField, field));
			}
		}
		return field;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetField() {
		return field;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setField(EObject newField) {
		EObject oldField = field;
		field = newField;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.FIELD_REFERENCE_VALUE_OPTION__FIELD, oldField, field));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.FIELD_REFERENCE_VALUE_OPTION__FIELD:
				if (resolve) return getField();
				return basicGetField();
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
			case BEViewsConfigurationPackage.FIELD_REFERENCE_VALUE_OPTION__FIELD:
				setField((EObject)newValue);
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
			case BEViewsConfigurationPackage.FIELD_REFERENCE_VALUE_OPTION__FIELD:
				setField((EObject)null);
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
			case BEViewsConfigurationPackage.FIELD_REFERENCE_VALUE_OPTION__FIELD:
				return field != null;
		}
		return super.eIsSet(featureID);
	}

} //FieldReferenceValueOptionImpl
