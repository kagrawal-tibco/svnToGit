/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.palette.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.common.palette.EmfType;
import com.tibco.cep.studio.common.palette.PalettePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Emf Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.EmfTypeImpl#getEmfType <em>Emf Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.EmfTypeImpl#getExtendedType <em>Extended Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class EmfTypeImpl extends EObjectImpl implements EmfType {
	/**
	 * The default value of the '{@link #getEmfType() <em>Emf Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmfType()
	 * @generated
	 * @ordered
	 */
	protected static final String EMF_TYPE_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getEmfType() <em>Emf Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEmfType()
	 * @generated
	 * @ordered
	 */
	protected String emfType = EMF_TYPE_EDEFAULT;

	/**
	 * This is true if the Emf Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean emfTypeESet;

	/**
	 * The default value of the '{@link #getExtendedType() <em>Extended Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtendedType()
	 * @generated
	 * @ordered
	 */
	protected static final String EXTENDED_TYPE_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getExtendedType() <em>Extended Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtendedType()
	 * @generated
	 * @ordered
	 */
	protected String extendedType = EXTENDED_TYPE_EDEFAULT;

	/**
	 * This is true if the Extended Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean extendedTypeESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EmfTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PalettePackage.Literals.EMF_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEmfType() {
		return emfType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEmfType(String newEmfType) {
		String oldEmfType = emfType;
		emfType = newEmfType;
		boolean oldEmfTypeESet = emfTypeESet;
		emfTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.EMF_TYPE__EMF_TYPE, oldEmfType, emfType, !oldEmfTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetEmfType() {
		String oldEmfType = emfType;
		boolean oldEmfTypeESet = emfTypeESet;
		emfType = EMF_TYPE_EDEFAULT;
		emfTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PalettePackage.EMF_TYPE__EMF_TYPE, oldEmfType, EMF_TYPE_EDEFAULT, oldEmfTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetEmfType() {
		return emfTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExtendedType() {
		return extendedType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtendedType(String newExtendedType) {
		String oldExtendedType = extendedType;
		extendedType = newExtendedType;
		boolean oldExtendedTypeESet = extendedTypeESet;
		extendedTypeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.EMF_TYPE__EXTENDED_TYPE, oldExtendedType, extendedType, !oldExtendedTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetExtendedType() {
		String oldExtendedType = extendedType;
		boolean oldExtendedTypeESet = extendedTypeESet;
		extendedType = EXTENDED_TYPE_EDEFAULT;
		extendedTypeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PalettePackage.EMF_TYPE__EXTENDED_TYPE, oldExtendedType, EXTENDED_TYPE_EDEFAULT, oldExtendedTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetExtendedType() {
		return extendedTypeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PalettePackage.EMF_TYPE__EMF_TYPE:
				return getEmfType();
			case PalettePackage.EMF_TYPE__EXTENDED_TYPE:
				return getExtendedType();
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
			case PalettePackage.EMF_TYPE__EMF_TYPE:
				setEmfType((String)newValue);
				return;
			case PalettePackage.EMF_TYPE__EXTENDED_TYPE:
				setExtendedType((String)newValue);
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
			case PalettePackage.EMF_TYPE__EMF_TYPE:
				unsetEmfType();
				return;
			case PalettePackage.EMF_TYPE__EXTENDED_TYPE:
				unsetExtendedType();
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
			case PalettePackage.EMF_TYPE__EMF_TYPE:
				return isSetEmfType();
			case PalettePackage.EMF_TYPE__EXTENDED_TYPE:
				return isSetExtendedType();
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
		result.append(" (emfType: ");
		if (emfTypeESet) result.append(emfType); else result.append("<unset>");
		result.append(", extendedType: ");
		if (extendedTypeESet) result.append(extendedType); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //EmfTypeImpl
