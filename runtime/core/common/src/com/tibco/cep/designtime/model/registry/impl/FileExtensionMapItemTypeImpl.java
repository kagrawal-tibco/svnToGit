/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.model.registry.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.designtime.model.registry.FileExtensionMapItemType;
import com.tibco.cep.designtime.model.registry.FileExtensionTypes;
import com.tibco.cep.designtime.model.registry.RegistryPackage;
import com.tibco.cep.designtime.model.registry.SupportedElementTypes;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>File Extension Map Item Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.FileExtensionMapItemTypeImpl#getExtension <em>Extension</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.FileExtensionMapItemTypeImpl#getElementType <em>Element Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FileExtensionMapItemTypeImpl extends EObjectImpl implements FileExtensionMapItemType {
	/**
	 * The default value of the '{@link #getExtension() <em>Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtension()
	 * @generated
	 * @ordered
	 */
	protected static final FileExtensionTypes EXTENSION_EDEFAULT = FileExtensionTypes.RULE_EXTENSION;

	/**
	 * The cached value of the '{@link #getExtension() <em>Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtension()
	 * @generated
	 * @ordered
	 */
	protected FileExtensionTypes extension = EXTENSION_EDEFAULT;

	/**
	 * This is true if the Extension attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean extensionESet;

	/**
	 * The cached value of the '{@link #getElementType() <em>Element Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementType()
	 * @generated
	 * @ordered
	 */
	protected SupportedElementTypes elementType;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FileExtensionMapItemTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RegistryPackage.Literals.FILE_EXTENSION_MAP_ITEM_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FileExtensionTypes getExtension() {
		return extension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtension(FileExtensionTypes newExtension) {
		FileExtensionTypes oldExtension = extension;
		extension = newExtension == null ? EXTENSION_EDEFAULT : newExtension;
		boolean oldExtensionESet = extensionESet;
		extensionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegistryPackage.FILE_EXTENSION_MAP_ITEM_TYPE__EXTENSION, oldExtension, extension, !oldExtensionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetExtension() {
		FileExtensionTypes oldExtension = extension;
		boolean oldExtensionESet = extensionESet;
		extension = EXTENSION_EDEFAULT;
		extensionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, RegistryPackage.FILE_EXTENSION_MAP_ITEM_TYPE__EXTENSION, oldExtension, EXTENSION_EDEFAULT, oldExtensionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetExtension() {
		return extensionESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SupportedElementTypes getElementType() {
		return elementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetElementType(SupportedElementTypes newElementType, NotificationChain msgs) {
		SupportedElementTypes oldElementType = elementType;
		elementType = newElementType;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RegistryPackage.FILE_EXTENSION_MAP_ITEM_TYPE__ELEMENT_TYPE, oldElementType, newElementType);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setElementType(SupportedElementTypes newElementType) {
		if (newElementType != elementType) {
			NotificationChain msgs = null;
			if (elementType != null)
				msgs = ((InternalEObject)elementType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RegistryPackage.FILE_EXTENSION_MAP_ITEM_TYPE__ELEMENT_TYPE, null, msgs);
			if (newElementType != null)
				msgs = ((InternalEObject)newElementType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RegistryPackage.FILE_EXTENSION_MAP_ITEM_TYPE__ELEMENT_TYPE, null, msgs);
			msgs = basicSetElementType(newElementType, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegistryPackage.FILE_EXTENSION_MAP_ITEM_TYPE__ELEMENT_TYPE, newElementType, newElementType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RegistryPackage.FILE_EXTENSION_MAP_ITEM_TYPE__ELEMENT_TYPE:
				return basicSetElementType(null, msgs);
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
			case RegistryPackage.FILE_EXTENSION_MAP_ITEM_TYPE__EXTENSION:
				return getExtension();
			case RegistryPackage.FILE_EXTENSION_MAP_ITEM_TYPE__ELEMENT_TYPE:
				return getElementType();
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
			case RegistryPackage.FILE_EXTENSION_MAP_ITEM_TYPE__EXTENSION:
				setExtension((FileExtensionTypes)newValue);
				return;
			case RegistryPackage.FILE_EXTENSION_MAP_ITEM_TYPE__ELEMENT_TYPE:
				setElementType((SupportedElementTypes)newValue);
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
			case RegistryPackage.FILE_EXTENSION_MAP_ITEM_TYPE__EXTENSION:
				unsetExtension();
				return;
			case RegistryPackage.FILE_EXTENSION_MAP_ITEM_TYPE__ELEMENT_TYPE:
				setElementType((SupportedElementTypes)null);
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
			case RegistryPackage.FILE_EXTENSION_MAP_ITEM_TYPE__EXTENSION:
				return isSetExtension();
			case RegistryPackage.FILE_EXTENSION_MAP_ITEM_TYPE__ELEMENT_TYPE:
				return elementType != null;
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
		if (extensionESet) result.append(extension); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //FileExtensionMapItemTypeImpl
