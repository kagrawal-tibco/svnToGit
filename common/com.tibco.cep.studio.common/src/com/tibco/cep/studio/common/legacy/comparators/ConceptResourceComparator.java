/**
 * 
 */
package com.tibco.cep.studio.common.legacy.comparators;

import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.studio.common.legacy.LegacyOntCompliance;
import com.tibco.cep.studio.common.util.Messages;

/**
 * @author aathalye
 *
 */
public class ConceptResourceComparator implements IResourceComparator<Concept, com.tibco.cep.designtime.core.model.element.Concept> {

	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.common.legacy.comparators.IResourceComparator#compareTo(com.tibco.cep.decisionproject.ontology.AbstractResource, com.tibco.cep.designtime.core.model.Entity)
	 */
	public LegacyOntCompliance compareTo(Concept oldResource,
			com.tibco.cep.designtime.core.model.element.Concept newResource) {
		//TODO Put check for no null
		LegacyOntCompliance compliance = null;
		String oldSuperCPath = oldResource.getSuperConceptPath();
		String newSuperCPath = newResource.getSuperConceptPath();
		newSuperCPath = (newSuperCPath == null) ? "" : newSuperCPath;
		if (!oldSuperCPath.equals(newSuperCPath)) {
			String message = 
				Messages.getString("OntologyValidation.concept.notIdentical",
						oldResource.getName(), 
						newResource.getName());
			String detail = 
				Messages.getString("OntologyValidation.concept.notIdentical.superConcept.detail",
						oldResource.getName(), 
						oldSuperCPath,
						newResource.getName(),
						newSuperCPath
						);
			compliance = new LegacyOntCompliance(LegacyOntCompliance.FAILED);
			compliance.setMessage(message);
			compliance.setSource(oldResource);
			compliance.setDetail(detail);
			return compliance;
		}
		String oldFolder = oldResource.getFolder();
		String newFolder = newResource.getFolder();
		if (!oldFolder.equals(newFolder)) {
			String message = 
				Messages.getString("OntologyValidation.concept.notIdentical",
						oldResource.getName(), 
						newResource.getName());
			String detail = 
				Messages.getString("OntologyValidation.concept.notIdentical.folder.detail",
						oldResource.getName(), 
						oldFolder,
						newResource.getName(),
						newFolder
						);
			compliance = new LegacyOntCompliance(LegacyOntCompliance.FAILED);
			compliance.setMessage(message);
			compliance.setSource(oldResource);
			compliance.setDetail(detail);
			return compliance;
		}
		return compliance = new LegacyOntCompliance(LegacyOntCompliance.OK);
	}
	
}
