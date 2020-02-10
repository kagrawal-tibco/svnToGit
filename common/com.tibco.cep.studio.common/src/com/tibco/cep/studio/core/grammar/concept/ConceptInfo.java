package com.tibco.cep.studio.core.grammar.concept;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.grammar.EntityInfo;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class ConceptInfo extends EntityInfo {
	
	public ConceptInfo(Concept concept) {
		super(concept);
	}

	private Concept getConcept() {
		return (Concept) getEntity();
	}
	
	public boolean isAutoStartStateMachine() {
		return getConcept().isAutoStartStateMachine();
	}
	
	public List<Property> getAttributes() { 
		Collection<PropertyDefinition> properties = getConcept().getAttributeDefinitions();
		List<Property> propWrappers = new ArrayList<ConceptInfo.Property>();
		for (PropertyDefinition propDef : properties) {
			propWrappers.add(new Property(propDef));
		}
		return propWrappers; 
	}
	
	public List<Property> getProperties() { 
		EList<PropertyDefinition> properties = getConcept().getProperties();
		List<Property> propWrappers = new ArrayList<ConceptInfo.Property>();
		for (PropertyDefinition propDef : properties) {
			propWrappers.add(new Property(propDef));
		}
		return propWrappers; 
	}
	
	public List<String> getStateMachines() {
		List<String> newPaths = new ArrayList<String>();
		EList<String> stateMachinePaths = getConcept().getStateMachinePaths();
		for (String smPath : stateMachinePaths) {
			newPaths.add(ModelUtils.convertPathToPackage(smPath));
		}
		return newPaths;
	}
	
	public boolean isMetadata() {
		return getConcept().getExtendedProperties() != null 
				&& getConcept().getExtendedProperties().getProperties().size() > 0;
	}
	
	public String getParent() { 
		String superPath = getConcept().getSuperConceptPath();
		if (superPath == null || superPath.length() == 0) {
			return null;
		}

		return ModelUtils.convertPathToPackage(superPath); 
	}

}
