/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.Argument;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Argument</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.ArgumentImpl#getArgumentEntry <em>Argument Entry</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ArgumentImpl extends EObjectImpl implements Argument {
	/**
	 * The cached value of the '{@link #getArgumentEntry() <em>Argument Entry</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArgumentEntry()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractResource> argumentEntry;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArgumentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OntologyPackage.Literals.ARGUMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated not
	 */
	public EList<AbstractResource> getArgumentEntry() {
		if (argumentEntry == null) {
			//argumentEntry = new EObjectContainmentEList<AbstractResource>(AbstractResource.class, this, OntologyPackage.ARGUMENT__ARGUMENT_ENTRY);
			argumentEntry = new EDataTypeEList<AbstractResource>(AbstractResource.class, this, OntologyPackage.ARGUMENT__ARGUMENT_ENTRY);
		}
		return argumentEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OntologyPackage.ARGUMENT__ARGUMENT_ENTRY:
				return ((InternalEList<?>)getArgumentEntry()).basicRemove(otherEnd, msgs);
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
			case OntologyPackage.ARGUMENT__ARGUMENT_ENTRY:
				return getArgumentEntry();
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
			case OntologyPackage.ARGUMENT__ARGUMENT_ENTRY:
				getArgumentEntry().clear();
				getArgumentEntry().addAll((Collection<? extends AbstractResource>)newValue);
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
			case OntologyPackage.ARGUMENT__ARGUMENT_ENTRY:
				getArgumentEntry().clear();
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
			case OntologyPackage.ARGUMENT__ARGUMENT_ENTRY:
				return argumentEntry != null && !argumentEntry.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ArgumentImpl
