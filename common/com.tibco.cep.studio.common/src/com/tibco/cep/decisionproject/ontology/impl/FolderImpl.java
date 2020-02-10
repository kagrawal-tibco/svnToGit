/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.impl;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.Folder;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Folder</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.FolderImpl#getResource <em>Resource</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FolderImpl extends ParentResourceImpl implements Folder {
	/**
	 * The cached value of the '{@link #getResource() <em>Resource</em>}' reference list.
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
	 * @generated NOT
	 */
	protected FolderImpl() {
		super();
		
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OntologyPackage.Literals.FOLDER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AbstractResource> getResource() {
		if (resource == null) {
			resource = new EObjectResolvingEList<AbstractResource>(AbstractResource.class, this, OntologyPackage.FOLDER__RESOURCE);
		}
		return resource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OntologyPackage.FOLDER__RESOURCE:
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
			case OntologyPackage.FOLDER__RESOURCE:
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
			case OntologyPackage.FOLDER__RESOURCE:
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
			case OntologyPackage.FOLDER__RESOURCE:
				return resource != null && !resource.isEmpty();
		}
		return super.eIsSet(featureID);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decisionproject.ontology.ParentResource#addChild(com.tibco.cep.decisionproject.ontology.AbstractResource)
	 */
	public boolean addChild(AbstractResource abstractResource) {
		if (resource == null){
			resource = new EObjectResolvingEList<AbstractResource>(AbstractResource.class, this, OntologyPackage.FOLDER__RESOURCE);
		}
	
		return resource.add(abstractResource);
	
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.decisionproject.model.impl.ParentResourceImpl#getChildren()
	 */
	@Override
	public Iterator<AbstractResource> getChildren() {
		return getResource().iterator();
	}

} //FolderImpl
