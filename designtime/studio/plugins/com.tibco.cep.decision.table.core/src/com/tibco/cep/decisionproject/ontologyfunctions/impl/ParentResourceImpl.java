/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontologyfunctions.impl;

import java.util.Iterator;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.decision.table.model.dtmodel.ResourceType;
import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.decisionproject.ontology.Event;
import com.tibco.cep.decisionproject.ontology.Property;
import com.tibco.cep.decisionproject.ontology.Rule;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.ontology.RuleSet;
import com.tibco.cep.decisionproject.ontologyfunctions.AbstractResource;
import com.tibco.cep.decisionproject.ontologyfunctions.Folder;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage;
import com.tibco.cep.decisionproject.ontologyfunctions.ParentResource;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parent Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
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
		return OntologyFunctionsPackage.Literals.PARENT_RESOURCE;
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

	private ResourceType getResourceType(AbstractResource resource){		
		if (resource instanceof Concept) return ResourceType.CONCEPT;
		if (resource instanceof Event) return ResourceType.EVENT;
		if (resource instanceof Folder) return ResourceType.FOLDER;
		if (resource instanceof Property) return ResourceType.PROPERTY;
		if (resource instanceof RuleFunction) return ResourceType.RULE_FUNCTION;
		if (resource instanceof RuleSet) return ResourceType.RULESET;
		if (resource instanceof Rule) return ResourceType.RULE;
		return null;
	}
} //ParentResourceImpl
