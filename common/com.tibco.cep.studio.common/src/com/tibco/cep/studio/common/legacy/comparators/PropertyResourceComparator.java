/**
 * 
 */
package com.tibco.cep.studio.common.legacy.comparators;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.Property;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.common.legacy.LegacyOntCompliance;
import com.tibco.cep.studio.common.util.Messages;

/**
 * @author aathalye
 *
 */
public class PropertyResourceComparator<A extends AbstractResource, E extends Entity> implements IResourceComparator<Property, PropertyDefinition> {
	
	private A oldContainer;
	
	private E newContainer;
	
	public PropertyResourceComparator(A oldContainer, E newContainer) {
		this.oldContainer = oldContainer;
		this.newContainer = newContainer;
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.common.legacy.comparators.IResourceComparator#compareTo(com.tibco.cep.decisionproject.ontology.AbstractResource, com.tibco.cep.designtime.core.model.Entity)
	 */
	public LegacyOntCompliance compareTo(Property oldResource,
			                             PropertyDefinition newResource) {
		int oldDataType = oldResource.getDataType();
		int newDataType = newResource.getType().getValue();
		LegacyOntCompliance compliance = null;
		if (!(oldDataType == newDataType)) {
			String message = 
				Messages.getString("OntologyValidation.property.notIdentical",
						oldResource.getName(), 
						newResource.getName());
			String detail = 
				Messages.getString("OntologyValidation.property.notIdentical.dataType.detail",
						oldResource.getName(), 
						oldContainer.getName(),
						oldDataType,
						newResource.getName(),
						newContainer.getName(),
						newDataType
						);
			compliance = new LegacyOntCompliance(LegacyOntCompliance.FAILED);
			compliance.setMessage(message);
			compliance.setSource(oldResource);
			compliance.setDetail(detail);
			return compliance;
		}
		//Check for multi value
		boolean isMultipleOld = oldResource.isMultiple();
		boolean isMultipleNew = newResource.isArray();
		if (!(isMultipleOld == isMultipleNew)) {
			String message = 
				Messages.getString("OntologyValidation.property.notIdentical",
						oldResource.getName(), 
						newResource.getName());
			String detail = 
				Messages.getString("OntologyValidation.property.notIdentical.multiplicity.detail",
						oldResource.getName(), 
						oldContainer.getName(),
						isMultipleOld,
						newResource.getName(),
						newContainer.getName(),
						isMultipleNew
						);
			compliance = new LegacyOntCompliance(LegacyOntCompliance.FAILED);
			compliance.setMessage(message);
			compliance.setSource(oldResource);
			compliance.setDetail(detail);
			return compliance;
		}
		//Check history size
		int oldHistorySize = oldResource.getHistorySize();
		int newHistorySize = newResource.getHistorySize();
		if (!(oldHistorySize == newHistorySize)) {
			String message = 
				Messages.getString("OntologyValidation.property.notIdentical",
						oldResource.getName(), 
						newResource.getName());
			String detail = 
				Messages.getString("OntologyValidation.property.notIdentical.historySize.detail",
						oldResource.getName(), 
						oldContainer.getName(),
						oldHistorySize,
						newResource.getName(),
						newContainer.getName(),
						newHistorySize
						);
			compliance = new LegacyOntCompliance(LegacyOntCompliance.FAILED);
			compliance.setMessage(message);
			compliance.setSource(oldResource);
			compliance.setDetail(detail);
			return compliance;
		}
		//Check History policy
		int oldHistoryPolicy = oldResource.getHistoryPolicy();
		int newHistoryPolicy = newResource.getHistoryPolicy();
		if (!(oldHistoryPolicy == newHistoryPolicy)) {
			String message = 
				Messages.getString("OntologyValidation.property.notIdentical",
						oldResource.getName(), 
						newResource.getName());
			String detail = 
				Messages.getString("OntologyValidation.property.notIdentical.historyPolicy.detail",
						oldResource.getName(), 
						oldContainer.getName(),
						oldHistoryPolicy,
						newResource.getName(),
						newContainer.getName(),
						newHistoryPolicy
						);
			compliance = new LegacyOntCompliance(LegacyOntCompliance.FAILED);
			compliance.setMessage(message);
			compliance.setSource(oldResource);
			compliance.setDetail(detail);
			return compliance;
		}
		//Not sure if the old ones contain default value
		return compliance = new LegacyOntCompliance(LegacyOntCompliance.OK);
	}
}
