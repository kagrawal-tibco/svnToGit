/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.model.registry.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;

import com.tibco.cep.designtime.model.registry.ElementTypes;
import com.tibco.cep.designtime.model.registry.RegistryPackage;
import com.tibco.cep.designtime.model.registry.SupportedElementTypes;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Supported Element Types</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.SupportedElementTypesImpl#getElementType <em>Element Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SupportedElementTypesImpl extends EObjectImpl implements SupportedElementTypes {
	/**
	 * The cached value of the '{@link #getElementType() <em>Element Type</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementType()
	 * @generated
	 * @ordered
	 */
	protected EList<ElementTypes> elementType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SupportedElementTypesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RegistryPackage.Literals.SUPPORTED_ELEMENT_TYPES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ElementTypes> getElementType() {
		if (elementType == null) {
			elementType = new EDataTypeEList<ElementTypes>(ElementTypes.class, this, RegistryPackage.SUPPORTED_ELEMENT_TYPES__ELEMENT_TYPE);
		}
		return elementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RegistryPackage.SUPPORTED_ELEMENT_TYPES__ELEMENT_TYPE:
				return getElementType();
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
			case RegistryPackage.SUPPORTED_ELEMENT_TYPES__ELEMENT_TYPE:
				getElementType().clear();
				getElementType().addAll((Collection<? extends ElementTypes>)newValue);
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
			case RegistryPackage.SUPPORTED_ELEMENT_TYPES__ELEMENT_TYPE:
				getElementType().clear();
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
			case RegistryPackage.SUPPORTED_ELEMENT_TYPES__ELEMENT_TYPE:
				return elementType != null && !elementType.isEmpty();
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
		result.append(" (elementType: ");
		result.append(elementType);
		result.append(')');
		return result.toString();
	}

} //SupportedElementTypesImpl
