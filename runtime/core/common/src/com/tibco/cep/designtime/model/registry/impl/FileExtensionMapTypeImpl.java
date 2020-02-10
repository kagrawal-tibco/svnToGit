/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.model.registry.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.model.registry.FileExtensionMapItemType;
import com.tibco.cep.designtime.model.registry.FileExtensionMapType;
import com.tibco.cep.designtime.model.registry.RegistryPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>File Extension Map Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.FileExtensionMapTypeImpl#getItem <em>Item</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FileExtensionMapTypeImpl extends EObjectImpl implements FileExtensionMapType {
	/**
	 * The cached value of the '{@link #getItem() <em>Item</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getItem()
	 * @generated
	 * @ordered
	 */
	protected EList<FileExtensionMapItemType> item;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FileExtensionMapTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RegistryPackage.Literals.FILE_EXTENSION_MAP_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FileExtensionMapItemType> getItem() {
		if (item == null) {
			item = new EObjectContainmentEList<FileExtensionMapItemType>(FileExtensionMapItemType.class, this, RegistryPackage.FILE_EXTENSION_MAP_TYPE__ITEM);
		}
		return item;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RegistryPackage.FILE_EXTENSION_MAP_TYPE__ITEM:
				return ((InternalEList<?>)getItem()).basicRemove(otherEnd, msgs);
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
			case RegistryPackage.FILE_EXTENSION_MAP_TYPE__ITEM:
				return getItem();
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
			case RegistryPackage.FILE_EXTENSION_MAP_TYPE__ITEM:
				getItem().clear();
				getItem().addAll((Collection<? extends FileExtensionMapItemType>)newValue);
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
			case RegistryPackage.FILE_EXTENSION_MAP_TYPE__ITEM:
				getItem().clear();
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
			case RegistryPackage.FILE_EXTENSION_MAP_TYPE__ITEM:
				return item != null && !item.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //FileExtensionMapTypeImpl
