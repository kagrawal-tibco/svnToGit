package com.tibco.cep.studio.ui.diagrams.rule;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.impl.ConceptImpl;
import com.tibco.cep.diagramming.utils.DiagramUtils;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.diagrams.ConceptDiagramManager;
import com.tibco.cep.studio.ui.diagrams.EntityNodeData;
import com.tibco.cep.studio.ui.diagrams.EntityNodeItem;
import com.tibco.cep.studio.ui.diagrams.tools.ConceptReconnectEdgeTool;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.swing.editing.tool.TSEBuildEdgeTool;
/**
 * 
 * @author smarathe
 *
 */
public class InheritanceRule extends ConceptDiagramRule {

	ConceptDiagramRuleSet ruleset;
	TSEBuildEdgeTool tool;
	ConceptDiagramManager diagramManager;
	private boolean flag = true;

	public InheritanceRule(ConceptDiagramRuleSet conceptDiagramRuleSet,
			TSEBuildEdgeTool tool) {
		super();
		this.ruleset = conceptDiagramRuleSet;
		this.tool = tool;

	}

	@Override
	public ConceptDiagramRuleSet getRuleset() {

		return this.ruleset;
	}

	@Override
	public boolean isAllowed() {
		String edgeType = DiagramUtils.getEdgeType(tool.getEdge());
		if(edgeType.equals(DiagramUtils.INHERITANCE_EDGE_TYPE)) {
			
		
			ConceptImpl srcConcept = null, tgtConcept = null;

			TSENode srcTSNode = this.ruleset.getSourceNode();
			TSENode tgtTSNode = this.ruleset.getTargetNode();
			if(!(srcTSNode.getUserObject() instanceof SharedEntityElement) ){
				Object srcNode = ((EntityNodeItem)((EntityNodeData)srcTSNode.getUserObject()).getEntity()).getUserObject();
				Object tgtNode = null;
				
				if(srcNode instanceof ConceptImpl) {
					srcConcept = ((ConceptImpl)srcNode);
				}
				
				if(tool instanceof ConceptReconnectEdgeTool) {
					srcConcept.setSuperConceptPath("");
				}
			if((tgtTSNode.getUserObject() instanceof SharedEntityElement)){
				return true;

			} else {
				tgtNode = ((EntityNodeItem)((EntityNodeData)tgtTSNode.getUserObject()).getEntity()).getUserObject();
			}
			
			if(tgtNode instanceof ConceptImpl) {
				tgtConcept = ((ConceptImpl)tgtNode);
			}
			
			if(srcConcept == tgtConcept) {
				return false;
			} else if(containsInheritanceCycleLoop(srcConcept, tgtConcept)) {
				return false;
			} else if(isContained(srcConcept, tgtConcept)) {
				return false;
			} else if(isContained(tgtConcept, srcConcept)) {
				return false;
			}else if(srcConcept.getSuperConceptPath() == null || srcConcept.getSuperConceptPath().equals("")){
				return true;
			} else {
				return false;
			}
		  }else{
			  return false;
		  }
		} else {
			return true;
		}

	}

	private boolean isContained(ConceptImpl srcConcept, ConceptImpl tgtConcept) {
		EList<PropertyDefinition> propertyList = tgtConcept.getAllProperties();
		for(PropertyDefinition property:propertyList) {
			if(property.getType() == PROPERTY_TYPES.CONCEPT) {
				if(property.getConceptTypePath().equals(srcConcept.getFullPath())) {
					return true;
				} else {
					ConceptImpl concept = (ConceptImpl) IndexUtils.getEntity(srcConcept.getOwnerProjectName(), property.getConceptTypePath());
					return isContained(srcConcept, concept);
				}
			} 
		}
		return false;
	}

	private boolean containsInheritanceCycleLoop(ConceptImpl srcConcept,
			ConceptImpl tgtConcept) {
		if (srcConcept == tgtConcept) {
			return true;
		} else 	if(tgtConcept.getSuperConceptPath() == null || tgtConcept.getSuperConceptPath().equals("")) {
			return false;
		}  else {
			ConceptImpl superConcept = (ConceptImpl) tgtConcept.getSuperConcept();
			return containsInheritanceCycleLoop(srcConcept, superConcept);
		}
	}

}
