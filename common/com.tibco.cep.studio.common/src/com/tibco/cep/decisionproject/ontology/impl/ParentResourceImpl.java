/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.impl;

import java.util.Iterator;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.decision.table.model.dtmodel.ResourceType;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.ParentResource;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parent Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
/**
 * @author aathalye
 *
 */
public abstract class ParentResourceImpl extends AbstractResourceImpl implements ParentResource {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParentResourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OntologyPackage.Literals.PARENT_RESOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public abstract Iterator<? extends AbstractResource> getChildren();
		
	
	
	/**
	 * @generated NOT
	 * @see com.tibco.cep.decisionproject.model.ParentResource#findChild(java.lang.String)
	 */
	public AbstractResource findChild(String name,ResourceType resourceType) {
		Iterator<? extends AbstractResource> children = getChildren();
		if (children != null) {
			while (children.hasNext()) {
				AbstractResource abs = children.next();
				ResourceType resType = getResourceType(abs);
				if (resType != null && resType == resourceType){				
					if (abs.getName().intern() == name.intern()) {
						return abs;
					}
				}
			}
		}
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decisionproject.ontology.ParentResource#findChild(java.lang.String)
	 */
	public AbstractResource findChild(String name) {
		Iterator<? extends AbstractResource> children = getChildren();
		if (children != null) {
			while (children.hasNext()) {
				AbstractResource abs = children.next();
				if (abs.getName().intern() == name.intern()) {
					return abs;
				}
			}
		}
		return null;
	}

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decisionproject.ontology.ParentResource#getChildIndex(com.tibco.cep.decisionproject.ontology.AbstractResource, com.tibco.cep.decision.table.model.dtmodel.ResourceType)
	 */
	public int getChildIndex(AbstractResource abstractResource) {
		Iterator<? extends AbstractResource> children = getChildren();
		ResourceType reqResType = getResourceType(abstractResource);
		String name = abstractResource.getName();
		if (children != null) {
			int counter = 0;
			while (children.hasNext()) {
				AbstractResource abs = children.next();
				ResourceType resType = getResourceType(abs);
				if (resType != null && resType.equals(reqResType)){				
					if (abs.getName().intern() == name.intern()) {
						return counter;
					}
				}
				counter++;
			}
		}
		return -1;
	}
	
	
} //ParentResourceImpl
