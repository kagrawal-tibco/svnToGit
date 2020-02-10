/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.designtimelib.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLib;
import com.tibco.cep.designtime.core.model.designtimelib.DesignTimeLibEntry;
import com.tibco.cep.designtime.core.model.designtimelib.DesigntimelibPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Design Time Lib</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.designtimelib.impl.DesignTimeLibImpl#getDesignTimeLibEntry <em>Design Time Lib Entry</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DesignTimeLibImpl extends EObjectImpl implements DesignTimeLib {
	/**
	 * The cached value of the '{@link #getDesignTimeLibEntry() <em>Design Time Lib Entry</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDesignTimeLibEntry()
	 * @generated
	 * @ordered
	 */
	protected EList<DesignTimeLibEntry> designTimeLibEntry;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DesignTimeLibImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DesigntimelibPackage.Literals.DESIGN_TIME_LIB;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DesignTimeLibEntry> getDesignTimeLibEntry() {
		if (designTimeLibEntry == null) {
			designTimeLibEntry = new EObjectContainmentEList<DesignTimeLibEntry>(DesignTimeLibEntry.class, this, DesigntimelibPackage.DESIGN_TIME_LIB__DESIGN_TIME_LIB_ENTRY);
		}
		return designTimeLibEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DesigntimelibPackage.DESIGN_TIME_LIB__DESIGN_TIME_LIB_ENTRY:
				return ((InternalEList<?>)getDesignTimeLibEntry()).basicRemove(otherEnd, msgs);
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
			case DesigntimelibPackage.DESIGN_TIME_LIB__DESIGN_TIME_LIB_ENTRY:
				return getDesignTimeLibEntry();
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
			case DesigntimelibPackage.DESIGN_TIME_LIB__DESIGN_TIME_LIB_ENTRY:
				getDesignTimeLibEntry().clear();
				getDesignTimeLibEntry().addAll((Collection<? extends DesignTimeLibEntry>)newValue);
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
			case DesigntimelibPackage.DESIGN_TIME_LIB__DESIGN_TIME_LIB_ENTRY:
				getDesignTimeLibEntry().clear();
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
			case DesigntimelibPackage.DESIGN_TIME_LIB__DESIGN_TIME_LIB_ENTRY:
				return designTimeLibEntry != null && !designTimeLibEntry.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DesignTimeLibImpl
