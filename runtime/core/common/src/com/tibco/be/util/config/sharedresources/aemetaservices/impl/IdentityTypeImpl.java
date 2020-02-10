/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.aemetaservices.impl;

import com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage;
import com.tibco.be.util.config.sharedresources.aemetaservices.IdentityType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Identity Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.IdentityTypeImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.IdentityTypeImpl#getIsRef <em>Is Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IdentityTypeImpl extends EObjectImpl implements IdentityType {
	/**
     * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getMixed()
     * @generated
     * @ordered
     */
	protected FeatureMap mixed;

	/**
     * The default value of the '{@link #getIsRef() <em>Is Ref</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getIsRef()
     * @generated
     * @ordered
     */
	protected static final Object IS_REF_EDEFAULT = null;

	/**
     * The cached value of the '{@link #getIsRef() <em>Is Ref</em>}' attribute.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @see #getIsRef()
     * @generated
     * @ordered
     */
	protected Object isRef = IS_REF_EDEFAULT;

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected IdentityTypeImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected EClass eStaticClass() {
        return AemetaservicesPackage.Literals.IDENTITY_TYPE;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public FeatureMap getMixed() {
        if (mixed == null)
        {
            mixed = new BasicFeatureMap(this, AemetaservicesPackage.IDENTITY_TYPE__MIXED);
        }
        return mixed;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public Object getIsRef() {
        return isRef;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setIsRef(Object newIsRef) {
        Object oldIsRef = isRef;
        isRef = newIsRef;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, AemetaservicesPackage.IDENTITY_TYPE__IS_REF, oldIsRef, isRef));
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID)
        {
            case AemetaservicesPackage.IDENTITY_TYPE__MIXED:
                return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
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
        switch (featureID)
        {
            case AemetaservicesPackage.IDENTITY_TYPE__MIXED:
                if (coreType) return getMixed();
                return ((FeatureMap.Internal)getMixed()).getWrapper();
            case AemetaservicesPackage.IDENTITY_TYPE__IS_REF:
                return getIsRef();
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
        switch (featureID)
        {
            case AemetaservicesPackage.IDENTITY_TYPE__MIXED:
                ((FeatureMap.Internal)getMixed()).set(newValue);
                return;
            case AemetaservicesPackage.IDENTITY_TYPE__IS_REF:
                setIsRef(newValue);
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
        switch (featureID)
        {
            case AemetaservicesPackage.IDENTITY_TYPE__MIXED:
                getMixed().clear();
                return;
            case AemetaservicesPackage.IDENTITY_TYPE__IS_REF:
                setIsRef(IS_REF_EDEFAULT);
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
        switch (featureID)
        {
            case AemetaservicesPackage.IDENTITY_TYPE__MIXED:
                return mixed != null && !mixed.isEmpty();
            case AemetaservicesPackage.IDENTITY_TYPE__IS_REF:
                return IS_REF_EDEFAULT == null ? isRef != null : !IS_REF_EDEFAULT.equals(isRef);
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
        result.append(" (mixed: ");
        result.append(mixed);
        result.append(", isRef: ");
        result.append(isRef);
        result.append(')');
        return result.toString();
    }

} //IdentityTypeImpl
