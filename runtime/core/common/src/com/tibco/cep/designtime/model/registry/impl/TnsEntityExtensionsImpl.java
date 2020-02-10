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

import com.tibco.cep.designtime.model.registry.FileExtensionTypes;
import com.tibco.cep.designtime.model.registry.RegistryPackage;
import com.tibco.cep.designtime.model.registry.TnsEntityExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tns Entity Extensions</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.TnsEntityExtensionsImpl#getExtension <em>Extension</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TnsEntityExtensionsImpl extends EObjectImpl implements TnsEntityExtensions {
	/**
	 * The cached value of the '{@link #getExtension() <em>Extension</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtension()
	 * @generated
	 * @ordered
	 */
	protected EList<FileExtensionTypes> extension;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TnsEntityExtensionsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RegistryPackage.Literals.TNS_ENTITY_EXTENSIONS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FileExtensionTypes> getExtension() {
		if (extension == null) {
			extension = new EDataTypeEList<FileExtensionTypes>(FileExtensionTypes.class, this, RegistryPackage.TNS_ENTITY_EXTENSIONS__EXTENSION);
		}
		return extension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RegistryPackage.TNS_ENTITY_EXTENSIONS__EXTENSION:
				return getExtension();
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
			case RegistryPackage.TNS_ENTITY_EXTENSIONS__EXTENSION:
				getExtension().clear();
				getExtension().addAll((Collection<? extends FileExtensionTypes>)newValue);
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
			case RegistryPackage.TNS_ENTITY_EXTENSIONS__EXTENSION:
				getExtension().clear();
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
			case RegistryPackage.TNS_ENTITY_EXTENSIONS__EXTENSION:
				return extension != null && !extension.isEmpty();
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
		result.append(" (extension: ");
		result.append(extension);
		result.append(')');
		return result.toString();
	}

} //TnsEntityExtensionsImpl
