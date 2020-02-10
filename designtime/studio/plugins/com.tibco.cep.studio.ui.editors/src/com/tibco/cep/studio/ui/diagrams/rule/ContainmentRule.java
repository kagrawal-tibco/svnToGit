package com.tibco.cep.studio.ui.diagrams.rule;

import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.impl.ConceptImpl;
import com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.diagrams.EntityNodeData;
import com.tibco.cep.studio.ui.diagrams.EntityNodeItem;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.swing.editing.tool.TSEBuildEdgeTool;

/**
 * 
 * @author smarathe
 *
 */
public class ContainmentRule extends ConceptDiagramRule {

	ConceptDiagramRuleSet ruleset;
	TSEBuildEdgeTool tool;

	public ContainmentRule(ConceptDiagramRuleSet ruleset, TSEBuildEdgeTool tool) {
		super();
		this.ruleset = ruleset;
		this.tool = tool;
	}

	@Override
	public ConceptDiagramRuleSet getRuleset() {
		return this.ruleset;
	}

	@Override
	public boolean isAllowed() {

		String edgeType = DiagramUtils.getEdgeType(tool.getEdge());
		if(edgeType.equals(DiagramUtils.CONTAINEMT_EDGE_TYPE)) {

			TSENode srcTSNode = this.ruleset.getSourceNode();
			TSENode tgtTSNode = this.ruleset.getTargetNode();

			ConceptImpl srcConcept = ((ConceptImpl)((EntityNodeItem)((EntityNodeData)srcTSNode.getUserObject()).getEntity()).getUserObject());
			ConceptImpl tgtConcept = ((ConceptImpl)((EntityNodeItem)((EntityNodeData)tgtTSNode.getUserObject()).getEntity()).getUserObject());
			if(isConceptContained(tgtConcept)) {
				return false;
			} else if(isContainerContained(srcConcept, tgtConcept)){
				return false;
			} else if(srcConcept == tgtConcept){
				return false;
			} else {
				return true;
			}

		} else {
			return true;
		}

	}

	private boolean isContainerContained(ConceptImpl srcConcept,
			ConceptImpl tgtConcept) {
		EList<PropertyDefinition> propertyList = tgtConcept.getAllProperties();
		for(PropertyDefinition property:propertyList) {
			if(property.getType() == PROPERTY_TYPES.CONCEPT) {
				if(property.getConceptTypePath().equals(srcConcept.getFullPath())) {
					return true;
				}
			}
		}
		EList<Concept> subConcepts = tgtConcept.getSubConcepts(); 
		for(Concept concept:subConcepts) {
			if(isContainerContained(srcConcept, (ConceptImpl)concept)) {
				return true;
			}
		}
		return false;
	}

	private boolean isConceptContained(ConceptImpl srcConcept) {
		String projectName = srcConcept.getOwnerProjectName();
		List<Entity> conceptList = IndexUtils.getAllEntities(projectName, ELEMENT_TYPES.CONCEPT);
		for(Entity entity:conceptList) {
			ConceptImpl concept = (ConceptImpl)entity;
			List<PropertyDefinition> propertyList = concept.getAllProperties();
			for(PropertyDefinition property:propertyList) {
				PropertyDefinitionImpl propertyImpl = (PropertyDefinitionImpl) property;
				if(propertyImpl.getType() == PROPERTY_TYPES.CONCEPT) {
					if(propertyImpl.getConceptTypePath().equals(srcConcept.getFullPath())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	

}
