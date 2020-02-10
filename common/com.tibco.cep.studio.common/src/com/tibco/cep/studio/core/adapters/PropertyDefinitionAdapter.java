package com.tibco.cep.studio.core.adapters;

import java.util.Collection;
import java.util.Set;

import com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.process.ProcessModel;


public class PropertyDefinitionAdapter extends EntityAdapter<com.tibco.cep.designtime.core.model.element.PropertyDefinition> implements
		PropertyDefinition {
	protected static final String INTERNAL_REPRESENTATION_SEPARATOR_STRING = ":";
	protected boolean _isMetricTrackingAuditField = false;
	
	public PropertyDefinitionAdapter(com.tibco.cep.designtime.core.model.element.PropertyDefinition adapted ,
			                         Ontology emfOntology) {
		super(adapted, emfOntology);
	}

	public PropertyDefinitionAdapter(com.tibco.cep.designtime.core.model.element.PropertyDefinition adapted ,
			                         Ontology emfOntology, Boolean isMetricTrackingAuditField) {
		super(adapted, emfOntology);
		_isMetricTrackingAuditField = isMetricTrackingAuditField.booleanValue();
	}

	@Override
	protected com.tibco.cep.designtime.core.model.element.PropertyDefinition getAdapted() {
		return adapted;
	}

	public PropertyDefinition getAttributeDefinition(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<PropertyDefinition> getAttributeDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

	public Concept getConceptType() {
//		return getOwner();
		String path = adapted.getConceptTypePath();
		return getOntology().getConcept(path);
	}

	public String getConceptTypePath() {
		return adapted.getConceptTypePath();
	}

	public String getDefaultValue() {
		return adapted.getDefaultValue();
	}

	public Collection<?> getDisjointSet() {
		return adapted.getDisjointSet();
	}

	public Collection<?> getEquivalentProperties() {
		return adapted.getEquivalentProperties();
	}

	public int getHistoryPolicy() {
		return adapted.getHistoryPolicy();
	}

	public int getHistorySize() {
		return adapted.getHistorySize();
	}


	public Concept getOwner() {
		String path = adapted.getOwnerPath();
		Entity e = getOntology().getEntity(path);
		if(e instanceof Concept) return (Concept)e;
		else if(e instanceof ProcessModel) return ((ProcessModel)e).cast(Concept.class);
		return null;
	}

	public PropertyDefinition getParent() {
		return new PropertyDefinitionAdapter(adapted.getParent(),getOntology());
	}

	public int getType() {
		return adapted.getType().getValue();
	}

	public boolean isA(PropertyDefinition propertyDefinition) {
		return false;
	}

	public boolean isArray() {
		return adapted.isArray();
	}

	public boolean isDisjointFrom(PropertyDefinition pd) {
		if (pd == null || pd == this) {
            return false;
        }

        String internalRep = toInternalRepresentation(pd);

        return getDisjointSet().contains(internalRep);
	}
	
	protected String toInternalRepresentation(PropertyDefinition pd) {
        return pd.getOwner().getFullPath() + INTERNAL_REPRESENTATION_SEPARATOR_STRING + pd.getName();
    }

	public boolean isSameAs(PropertyDefinition pd) {
		if (pd == null) {
            return false;
        }
        if (pd == this) {
            return true;
        }
        String internalRep = toInternalRepresentation(pd);
		return getEquivalentProperties().contains(internalRep);
	}

	public boolean isTransitive() {
		return adapted.isTransitive();
	}
	
	@Override
	public int getOrder() {
		return adapted.getOrder();
	}

	public boolean isGroupByField() {
		return adapted.isGroupByField();		
	}
	
	public METRIC_AGGR_TYPE getAggregationType() {
		return adapted.getAggregationType();
	}
	
	public boolean isMetricTrackingAuditField() {
		return _isMetricTrackingAuditField;
	}
	
	public long getGroupByPosition() {
		return adapted.getGroupByPosition();
	}
	
	public String getName() {
		if (_isMetricTrackingAuditField) {
			return "I_" + super.getName();
		}
		else {
			return super.getName();
		}
	}
	
	@Override
	public Set getInstances() {
		// TODO Auto-generated method stub
		return null;
	}
}
