/**
 * 
 */
package com.tibco.cep.studio.common.legacy.adapters;

import java.util.List;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.decisionproject.ontology.OntologyFactory;
import com.tibco.cep.decisionproject.ontology.Property;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;

/**
 * @author aathalye
 *
 */
public class ConceptModelTransformer
		implements
		IModelTransformer<Concept, com.tibco.cep.designtime.core.model.element.Concept> {

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.common.legacy.adapters.IModelTransformer#transform(com.tibco.cep.decisionproject.ontology.AbstractResource, com.tibco.cep.designtime.core.model.Entity)
	 */
	
	public com.tibco.cep.designtime.core.model.element.Concept transform(
			Concept transformFrom,
			com.tibco.cep.designtime.core.model.element.Concept transformTo) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.common.legacy.adapters.IModelTransformer#transform(com.tibco.cep.designtime.core.model.Entity, com.tibco.cep.decisionproject.ontology.AbstractResource)
	 */
	
	public Concept transform(
			com.tibco.cep.designtime.core.model.element.Concept transformFrom,
			Concept transformTo) {
		if (transformFrom != null && transformTo != null) {
			transformTo.setName(transformFrom.getName());
			transformTo.setFolder(transformFrom.getFolder());
			transformTo.setOwnerProjectName(transformFrom.getOwnerProjectName());
			//transform each property
			List<PropertyDefinition> fromProperties = transformFrom.getAllProperties();
			transform(fromProperties, transformTo.getProperty());
			return transformTo;
		}
		return null;
	}
	
	private void transform(List<PropertyDefinition> fromProperties, 
			               List<AbstractResource> toProperties) {
		for (PropertyDefinition pDef : fromProperties) {
			Property toProperty = OntologyFactory.eINSTANCE.createProperty();
			toProperty.setName(pDef.getName());
			toProperty.setPropertyResourceType(pDef.getConceptTypePath());
			toProperty.setDataType(pDef.getType().getValue());
			toProperty.setOwnerProjectName(pDef.getOwnerProjectName());
			toProperties.add(toProperty);
		}
	}

}
