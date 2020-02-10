/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.ProjectionConfig;
import com.tibco.be.util.config.cdd.SetPropertyConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Projection Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.ProjectionConfigImpl#getSetProperty <em>Set Property</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProjectionConfigImpl extends EObjectImpl implements ProjectionConfig {
	/**
	 * The cached value of the '{@link #getSetProperty() <em>Set Property</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSetProperty()
	 * @generated
	 * @ordered
	 */
	protected EList<SetPropertyConfig> setProperty;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProjectionConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getProjectionConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SetPropertyConfig> getSetProperty() {
		if (setProperty == null) {
			setProperty = new EObjectContainmentEList<SetPropertyConfig>(SetPropertyConfig.class, this, CddPackage.PROJECTION_CONFIG__SET_PROPERTY);
		}
		return setProperty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.PROJECTION_CONFIG__SET_PROPERTY:
				return ((InternalEList<?>)getSetProperty()).basicRemove(otherEnd, msgs);
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
			case CddPackage.PROJECTION_CONFIG__SET_PROPERTY:
				return getSetProperty();
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
			case CddPackage.PROJECTION_CONFIG__SET_PROPERTY:
				getSetProperty().clear();
				getSetProperty().addAll((Collection<? extends SetPropertyConfig>)newValue);
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
			case CddPackage.PROJECTION_CONFIG__SET_PROPERTY:
				getSetProperty().clear();
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
			case CddPackage.PROJECTION_CONFIG__SET_PROPERTY:
				return setProperty != null && !setProperty.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ProjectionConfigImpl
