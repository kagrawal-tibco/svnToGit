/**
 * 
 */
package com.tibco.cep.bpmn.model.designtime.ontology;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.process.ProcessConcept;
import com.tibco.cep.designtime.model.process.SubProcessConcept;
import com.tibco.cep.designtime.model.process.ProcessModel.BASE_ATTRIBUTES;
import com.tibco.cep.studio.core.adapters.ConceptAdapter;
import com.tibco.cep.studio.core.adapters.PropertyDefinitionAdapter;

/**
 * @author pdhar
 *
 */
public class ProcessConceptAdapter extends ConceptAdapter implements ProcessConcept {
	

	/**
	 * @param adapted
	 * @param emfOntology
	 */
	public ProcessConceptAdapter(Concept adapted, Ontology emfOntology,Object ...params) {
		super(adapted, emfOntology);
	}
	
	@Override
	public PropertyDefinition getAttributeDefinition(String attributeName) {
		if(BASE_ATTRIBUTES.parent.name().equals(attributeName)) {
			return new ParentPDAdapter(adapted.getAttributeDefinition(attributeName),getOntology());
		}
		return new PropertyDefinitionAdapter(adapted.getAttributeDefinition(attributeName),getOntology());
	}
	
	@Override
	public Collection<PropertyDefinition> getAttributeDefinitions() {
		List<PropertyDefinition> pds = new ArrayList<PropertyDefinition>();
		for(com.tibco.cep.designtime.core.model.element.PropertyDefinition a: adapted.getAttributeDefinitions()) {
			pds.add(new PropertyDefinitionAdapter(a,getOntology()));
		}
		return pds;
	}

	private static class ParentPDAdapter extends PropertyDefinitionAdapter {

		public ParentPDAdapter(
				com.tibco.cep.designtime.core.model.element.PropertyDefinition adapted,
				Ontology emfOntology) {
			super(adapted, emfOntology);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public int getType() {
			return BASE_ATTRIBUTES.parent.getRdfTypeId();
		}
	}
}
