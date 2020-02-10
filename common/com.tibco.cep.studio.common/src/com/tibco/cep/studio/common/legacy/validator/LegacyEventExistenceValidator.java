/**
 * 
 */
package com.tibco.cep.studio.common.legacy.validator;

import static com.tibco.cep.studio.common.legacy.LegacyOntCompliance.FAILED;

import java.util.List;

import org.eclipse.emf.common.util.URI;

import com.tibco.cep.decisionproject.ontology.Event;
import com.tibco.cep.decisionproject.ontology.Property;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.common.legacy.LegacyOntCompliance;
import com.tibco.cep.studio.common.legacy.comparators.EventResourceComparator;
import com.tibco.cep.studio.common.legacy.comparators.IResourceComparator;
import com.tibco.cep.studio.common.legacy.comparators.PropertyResourceComparator;
import com.tibco.cep.studio.common.util.Messages;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * @author aathalye
 *
 */
public class LegacyEventExistenceValidator 
				extends DefaultLegacyResourceAbstractValidator<Event, com.tibco.cep.designtime.core.model.event.Event>
                implements ILegacyResourceExistenceValidator<Event> {
	
	public LegacyEventExistenceValidator(String dpPath, String rootPath) {
		super(dpPath, rootPath);
	}
	
	public void act(Event abstractResource) {
		// TODO Auto-generated method stub
		
	}


	public void validate(Event abstractResource, List<LegacyOntCompliance> compliances) {
		//Get the full path of this event
		String folder = abstractResource.getFolder();
		String name = abstractResource.getName();
		String path = folder + name;
		
		//Append this to the base path
		String extension = 
			(abstractResource.getType() == 0) ? CommonIndexUtils.getFileExtension(ELEMENT_TYPES.SIMPLE_EVENT)
					: CommonIndexUtils.getFileExtension(ELEMENT_TYPES.TIME_EVENT);
			
		String newPath = buildFilePath(path, extension);
		try {
			URI uri = URI.createFileURI(newPath);
			com.tibco.cep.designtime.core.model.event.Event newResource = 
				validateExistence(uri);
			if (newResource != null) {
				//This means there is a concept with same name
				//Now compare other things
				IResourceComparator<Event, 
				                    com.tibco.cep.designtime.core.model.event.Event>
				     eventComparator = new EventResourceComparator();
				LegacyOntCompliance eventCompliance = 
					eventComparator.compareTo(abstractResource, newResource);
				if (eventCompliance.getStatus() == LegacyOntCompliance.OK) {
					//Check for properties of each
					validateChildren(abstractResource, newResource, compliances);
				} else {
					if (!compliances.contains(eventCompliance)) {
						compliances.add(eventCompliance);
					}
				}
			}
		} catch (Exception e) {
			LegacyOntCompliance comp = new LegacyOntCompliance(FAILED);
			comp.setSource(abstractResource);
			comp.setMessage(Messages.getString("OntologyValidation.event.notFound",
			        path));
			comp.setDetail(Messages.getString("OntologyValidation.event.notFound.detail", 
			        path, abstractResource.getType(), 
			        dpPath,
			        rootPath));
			if (!compliances.contains(comp)) {
				compliances.add(comp);
			}
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.common.legacy.validator.DefaultLegacyResourceAbstractValidator#validateChildren(com.tibco.cep.decisionproject.ontology.AbstractResource, com.tibco.cep.designtime.core.model.Entity, java.util.List)
	 */
	protected void validateChildren(Event oldResource ,
			com.tibco.cep.designtime.core.model.event.Event newResource,
			List<LegacyOntCompliance> compliances) {
		List<Property> userProperties = oldResource.getUserProperty();
		IResourceComparator<Property, PropertyDefinition> propComparator =
			   new PropertyResourceComparator<Event, com.tibco.cep.designtime.core.model.event.Event>(oldResource, newResource);
		
		for (Property userProperty : userProperties) {
			//Search for this property in the new events children
			PropertyDefinition propertyDefinition = 
				newResource.getPropertyDefinition(userProperty.getName(), true);
			if (propertyDefinition != null) {
				LegacyOntCompliance compliance = 
					propComparator.compareTo(userProperty, propertyDefinition);
				if (!(compliance.getStatus() == LegacyOntCompliance.OK)) {
					if (!compliances.contains(compliance)) {
						compliances.add(compliance);
					}
				}
			} else {
				LegacyOntCompliance failure = new LegacyOntCompliance(FAILED);
				failure.setSource(userProperty);
				failure.setMessage(Messages.getString("OntologyValidation.property.notFound",
						userProperty.getName()));
				failure.setDetail(Messages.getString("OntologyValidation.property.notFound.detail", 
						userProperty.getName(), 
						oldResource.getName(),
						dpPath,
						newResource.getName(), 
						rootPath));
				if (!compliances.contains(failure)) {
					compliances.add(failure);
				}
			}
		}
		//That means there were no children
		if (newResource.getProperties().size() == 0 && userProperties.size() == 0) {
			
		}
	}
}
