/**
 * 
 */
package com.tibco.cep.studio.core.adapters;

import java.util.Collection;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;

/**
 * @author aathalye
 *
 */
public class EventPropertyDefinitionAdapter extends EntityAdapter<PropertyDefinition> implements
		EventPropertyDefinition {
	
	private Event ownerEvent;
	
	public EventPropertyDefinitionAdapter(PropertyDefinition adapted, 
			                              Event owner,
			                              Ontology emfOntology) {
		super(adapted, emfOntology);
		this.ownerEvent = owner;
	}
		
	@Override
	protected PropertyDefinition getAdapted() {
		return adapted;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.event.EventPropertyDefinition#getAttributeDefinition(java.lang.String)
	 */
	
	public EventPropertyDefinition getAttributeDefinition(String arg0) {
		throw new UnsupportedOperationException("May not be needed");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.event.EventPropertyDefinition#getAttributeDefinitions()
	 */
	
	public Collection<?> getAttributeDefinitions() {
		throw new UnsupportedOperationException("May not be needed");
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.event.EventPropertyDefinition#getOwner()
	 */
	
	public Event getOwner() {
		return ownerEvent;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.event.EventPropertyDefinition#getPropertyName()
	 */
	
	public String getPropertyName() {
		return adapted.getName();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.model.event.EventPropertyDefinition#getType()
	 */
	
	public RDFPrimitiveTerm getType() {
		//Find the type of the wrapped property
		PROPERTY_TYPES property_types = adapted.getType();
		return getRDFPrimitiveTerm(property_types);
	}
	
	private RDFPrimitiveTerm getRDFPrimitiveTerm(PROPERTY_TYPES type) {
		switch (type) {
			case STRING: {
				return (RDFPrimitiveTerm)RDFTypes.STRING;
			}
			case BOOLEAN: {
				return (RDFPrimitiveTerm)RDFTypes.BOOLEAN;
			}
			case DATE_TIME: {
				return (RDFPrimitiveTerm)RDFTypes.DATETIME;
			}
			case DOUBLE: {
				return (RDFPrimitiveTerm)RDFTypes.DOUBLE;
			}
			case LONG: {
				return (RDFPrimitiveTerm)RDFTypes.LONG;
			}
			case INTEGER: {
				return (RDFPrimitiveTerm)RDFTypes.INTEGER;
			}
			default: {
				return (RDFPrimitiveTerm)RDFTypes.STRING;
			}
		}
	}
}
