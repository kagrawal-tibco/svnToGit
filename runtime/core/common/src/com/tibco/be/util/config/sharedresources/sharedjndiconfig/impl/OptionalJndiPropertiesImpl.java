/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl;

import com.tibco.be.util.config.sharedresources.sharedjndiconfig.OptionalJndiProperties;
import com.tibco.be.util.config.sharedresources.sharedjndiconfig.Row;
import com.tibco.be.util.config.sharedresources.sharedjndiconfig.SharedjndiconfigPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Optional Jndi Properties</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.sharedjndiconfig.impl.OptionalJndiPropertiesImpl#getRow <em>Row</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OptionalJndiPropertiesImpl extends EObjectImpl implements OptionalJndiProperties {
	/**
	 * The cached value of the '{@link #getRow() <em>Row</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRow()
	 * @generated
	 * @ordered
	 */
	protected EList<Row> row;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OptionalJndiPropertiesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SharedjndiconfigPackage.Literals.OPTIONAL_JNDI_PROPERTIES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Row> getRow() {
		if (row == null) {
			row = new EObjectContainmentEList<Row>(Row.class, this, SharedjndiconfigPackage.OPTIONAL_JNDI_PROPERTIES__ROW);
		}
		return row;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SharedjndiconfigPackage.OPTIONAL_JNDI_PROPERTIES__ROW:
				return ((InternalEList<?>)getRow()).basicRemove(otherEnd, msgs);
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
			case SharedjndiconfigPackage.OPTIONAL_JNDI_PROPERTIES__ROW:
				return getRow();
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
			case SharedjndiconfigPackage.OPTIONAL_JNDI_PROPERTIES__ROW:
				getRow().clear();
				getRow().addAll((Collection<? extends Row>)newValue);
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
			case SharedjndiconfigPackage.OPTIONAL_JNDI_PROPERTIES__ROW:
				getRow().clear();
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
			case SharedjndiconfigPackage.OPTIONAL_JNDI_PROPERTIES__ROW:
				return row != null && !row.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //OptionalJndiPropertiesImpl
