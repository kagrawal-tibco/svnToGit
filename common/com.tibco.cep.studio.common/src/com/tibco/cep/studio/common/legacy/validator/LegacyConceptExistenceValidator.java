/**
 * 
 */
package com.tibco.cep.studio.common.legacy.validator;

import static com.tibco.cep.studio.common.legacy.LegacyOntCompliance.FAILED;

import java.util.List;

import org.eclipse.emf.common.util.URI;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.decisionproject.ontology.Property;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.common.legacy.LegacyOntCompliance;
import com.tibco.cep.studio.common.legacy.comparators.ConceptResourceComparator;
import com.tibco.cep.studio.common.legacy.comparators.IResourceComparator;
import com.tibco.cep.studio.common.legacy.comparators.PropertyResourceComparator;
import com.tibco.cep.studio.common.util.Messages;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * @author aathalye
 *
 */
public class LegacyConceptExistenceValidator 
        extends DefaultLegacyResourceAbstractValidator<Concept, com.tibco.cep.designtime.core.model.element.Concept> implements
		ILegacyResourceExistenceValidator<Concept> {
	
		
	public LegacyConceptExistenceValidator(String dpPath, String rootPath) {
		super(dpPath, rootPath);
	}
	
	public void act(Concept abstractResource) {
		//Do nothing
	}

	
	public void validate(Concept abstractResource, List<LegacyOntCompliance> compliances) {
		//Get the full path of this concept
		String folder = abstractResource.getFolder();
		String name = abstractResource.getName();
		String path = folder + name;
		
		//Append this to the base path
		//Check for concept/scorecard
		String extension = 
			(abstractResource.isScoreCard() == false) ? CommonIndexUtils.getFileExtension(ELEMENT_TYPES.CONCEPT)
					: CommonIndexUtils.getFileExtension(ELEMENT_TYPES.SCORECARD);
			
		String newPath = buildFilePath(path, extension);
		try { 
			URI uri = URI.createFileURI(newPath);
			com.tibco.cep.designtime.core.model.element.Concept newResource = 
				validateExistence(uri);
			if (newResource != null) {
				//This means there is a concept with same name
				//Now compare other things
				IResourceComparator<Concept, 
				                    com.tibco.cep.designtime.core.model.element.Concept>
				     conceptComparator = new ConceptResourceComparator();
				LegacyOntCompliance conceptCompliance = 
					conceptComparator.compareTo(abstractResource, newResource);
				if (conceptCompliance.getStatus() == LegacyOntCompliance.OK) {
					//Check for properties of each
					validateChildren(abstractResource, newResource, compliances);
				} else {
					if (!compliances.contains(conceptCompliance)) {
						compliances.add(conceptCompliance);
					}
				}
			}
		} catch (Exception e) {
			LegacyOntCompliance comp = new LegacyOntCompliance(FAILED);
			comp.setSource(abstractResource);
			comp.setMessage(Messages.getString("OntologyValidation.concept.notFound",
					        path));
			comp.setDetail(Messages.getString("OntologyValidation.concept.notFound.detail", 
					        path, dpPath, rootPath));
			if (!compliances.contains(comp)) {
				compliances.add(comp);
			}
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.common.legacy.validator.DefaultLegacyResourceAbstractValidator#validateChildren(com.tibco.cep.decisionproject.ontology.AbstractResource, com.tibco.cep.designtime.core.model.Entity, java.util.List)
	 */
	protected void validateChildren(Concept oldResource ,
			com.tibco.cep.designtime.core.model.element.Concept newResource,
			List<LegacyOntCompliance> compliances) {
		//Iterator<? extends AbstractResource> children = oldResource.getChildren();
		List<AbstractResource> properties = oldResource.getProperty();
		IResourceComparator<Property, PropertyDefinition> propComparator =
			   new PropertyResourceComparator<Concept, com.tibco.cep.designtime.core.model.element.Concept>(oldResource, newResource);
		
		for (AbstractResource oldAbstractResource : properties) {
			//Search for this property in the new concepts children
			if (oldAbstractResource instanceof Property) {
				Property oldProperty = (Property)oldAbstractResource;
				PropertyDefinition propertyDefinition = 
					newResource.getPropertyDefinition(oldProperty.getName(), false);
				if (propertyDefinition != null) {
					LegacyOntCompliance compliance = propComparator.compareTo(oldProperty, propertyDefinition);
					if (!(compliance.getStatus() == LegacyOntCompliance.OK)) {
						if (!compliances.contains(compliance)) {
							compliances.add(compliance);
						}
					}
				} else {
					LegacyOntCompliance failure = new LegacyOntCompliance(FAILED);
					failure.setSource(oldProperty);
					failure.setMessage(Messages.getString("OntologyValidation.property.notFound",
							oldProperty.getName()));
					failure.setDetail(Messages.getString("OntologyValidation.property.notFound.detail", 
							oldProperty.getName(), 
							oldResource.getName(),
							dpPath,
							newResource.getName(), 
							rootPath));
					if (!compliances.contains(failure)) {
						compliances.add(failure);
					}
				}
			}
		
		}
		//That means there were no children
		if (newResource.getProperties().size() == 0 && properties.size() == 0) {
			//areIdentical = true;
		}
	}
}
