/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontologyfunctions.impl;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.decisionproject.ontologyfunctions.AbstractResource;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctions;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ontology Functions</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontologyfunctions.impl.OntologyFunctionsImpl#getResource <em>Resource</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OntologyFunctionsImpl extends ParentResourceImpl implements OntologyFunctions {
	/**
	 * 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResource()
	 * @generated not
	 * @ordered
	 */
	public boolean addChild(AbstractResource child) {
		if (resource == null) {
			resource = new EObjectContainmentEList<AbstractResource>(AbstractResource.class, this, OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS__RESOURCE);
		}
		
		return resource.add(child);
	}

	/**
	 * 
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResource()
	 * @generated not
	 * @ordered
	 */
	@Override
	public Iterator<? extends AbstractResource> getChildren() {
		return getResource().iterator();
		
	}

	/**
	 * The cached value of the '{@link #getResource() <em>Resource</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResource()
	 * @generated
	 * @ordered
	 */
	protected EList<AbstractResource> resource;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OntologyFunctionsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OntologyFunctionsPackage.Literals.ONTOLOGY_FUNCTIONS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AbstractResource> getResource() {
		if (resource == null) {
			resource = new EObjectContainmentEList<AbstractResource>(AbstractResource.class, this, OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS__RESOURCE);
		}
		return resource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS__RESOURCE:
				return ((InternalEList<?>)getResource()).basicRemove(otherEnd, msgs);
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
			case OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS__RESOURCE:
				return getResource();
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
			case OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS__RESOURCE:
				getResource().clear();
				getResource().addAll((Collection<? extends AbstractResource>)newValue);
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
			case OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS__RESOURCE:
				getResource().clear();
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
			case OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS__RESOURCE:
				return resource != null && !resource.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //OntologyFunctionsImpl
