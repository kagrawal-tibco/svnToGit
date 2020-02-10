/**
 * 
 */
package com.tibco.cep.studio.common.legacy.comparators;

import com.tibco.cep.decisionproject.ontology.Event;
import com.tibco.cep.studio.common.legacy.LegacyOntCompliance;
import com.tibco.cep.studio.common.util.Messages;

/**
 * @author aathalye
 *
 */
public class EventResourceComparator implements IResourceComparator<Event, com.tibco.cep.designtime.core.model.event.Event> {
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.common.legacy.comparators.IResourceComparator#compareTo(com.tibco.cep.decisionproject.ontology.AbstractResource, com.tibco.cep.designtime.core.model.Entity)
	 */
	public LegacyOntCompliance compareTo(Event oldResource,
			                             com.tibco.cep.designtime.core.model.event.Event newResource) {
		
		String oldSuperEPath = oldResource.getSuperEventPath();
		String newSuperEPath = newResource.getSuperEventPath();
		
		LegacyOntCompliance compliance = null;
		//Check super paths
		if (!oldSuperEPath.equals(newSuperEPath)) {
			String message = 
				Messages.getString("OntologyValidation.event.notIdentical",
						oldResource.getName(), 
						newResource.getName());
			String detail = 
				Messages.getString("OntologyValidation.event.notIdentical.superEvent.detail",
						oldResource.getName(), 
						oldSuperEPath,
						newResource.getName(),
						newSuperEPath
						);
			compliance = new LegacyOntCompliance(LegacyOntCompliance.FAILED);
			compliance.setMessage(message);
			compliance.setSource(oldResource);
			compliance.setDetail(detail);
			return compliance;
		}
		//Check folders
		String oldFolder = oldResource.getFolder();
		String newFolder = newResource.getFolder();
		
		if (!oldFolder.equals(newFolder)) {
			String message = 
				Messages.getString("OntologyValidation.event.notIdentical",
						oldResource.getName(), 
						newResource.getName());
			String detail = 
				Messages.getString("OntologyValidation.event.notIdentical.superEvent.detail",
						oldResource.getName(), 
						oldFolder,
						newResource.getName(),
						newFolder
						);
			compliance = new LegacyOntCompliance(LegacyOntCompliance.FAILED);
			compliance.setMessage(message);
			compliance.setDetail(detail);
			compliance.setSource(oldResource);
			return compliance;
		}
		//Check TTL Units
		int oldttlUnits = oldResource.getTtlUnits();
		int newttlUnits = newResource.getTtlUnits().getValue();
		
		if (!(oldttlUnits == newttlUnits)) {
			String message = 
				Messages.getString("OntologyValidation.event.notIdentical",
						oldResource.getName(), 
						newResource.getName());
			String detail = 
				Messages.getString("OntologyValidation.event.notIdentical.superEvent.detail",
						oldResource.getName(), 
						oldttlUnits,
						newResource.getName(),
						newttlUnits
						);
			compliance = new LegacyOntCompliance(LegacyOntCompliance.FAILED);
			compliance.setMessage(message);
			compliance.setSource(oldResource);
			compliance.setDetail(detail);
			return compliance;
		}
		
		//Check event type
		int oldEventType = oldResource.getType();
		int newEventType = newResource.getType().getValue();
		
		if (!(oldEventType == newEventType)) {
			String message = 
				Messages.getString("OntologyValidation.event.notIdentical",
						oldResource.getName(), 
						newResource.getName());
			String detail = 
				Messages.getString("OntologyValidation.event.notIdentical.superEvent.detail",
						oldResource.getName(), 
						oldEventType,
						newResource.getName(),
						newEventType
						);
			compliance = new LegacyOntCompliance(LegacyOntCompliance.FAILED);
			compliance.setMessage(message);
			compliance.setDetail(detail);
			compliance.setSource(oldResource);
			return compliance;
		}
		//Cannot put more checks since the old model does not store any more info
		return compliance = new LegacyOntCompliance(LegacyOntCompliance.OK);
	}
}
