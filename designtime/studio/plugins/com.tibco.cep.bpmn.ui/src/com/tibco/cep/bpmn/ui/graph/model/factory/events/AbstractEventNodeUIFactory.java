package com.tibco.cep.bpmn.ui.graph.model.factory.events;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tomsawyer.drawing.geometry.shared.TSOvalShape;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSENodeLabel;
import com.tomsawyer.graphicaldrawing.TSESolidObject;

/**
 * @author pdhar
 *
 */
public abstract class AbstractEventNodeUIFactory extends AbstractNodeUIFactory {
	
	private EClass eventDefinitionType;
	private static final long serialVersionUID = 6613014327051157566L;
	
	public AbstractEventNodeUIFactory(String name,String referredBEResource, String toolId,BpmnLayoutManager layoutManager, EClass nodeType, EClass eventDefType) {
		super(name,referredBEResource, toolId, layoutManager, nodeType, eventDefType );
		this.eventDefinitionType = eventDefType;
	}
	
	@Override
	public TSENode addNode(TSEGraph graph) {
		// XYZ
		TSENode node = super.addNode(graph);
		node.setAttribute(BpmnUIConstants.NODE_ATTR_EXT_TYPE, eventDefinitionType);
		return node;
	}

	
	
	public EClass getEventDefinitionType() {
		return eventDefinitionType;
	}
	
	@Override
	public void decorateNode(TSENode node) {
		node.setResizability(TSESolidObject.RESIZABILITY_LOCKED);
		// node.setSize(node.getWidth() * 0.65, node.getHeight() * 0.65);
		node.setSize(60, 60);
		node.setShape(TSOvalShape.getInstance());
		node.setUI(getNodeUI());
		String name = getNodeName(); 
		name = (name == null? "": name);
		node.setName(name);
	}
	
	
	
	
	public void layoutNode(TSENode node) {
		if (getLayoutManager() != null && node.labels().size() > 0) {
			getLayoutManager().setNodeLabelOptions((TSENodeLabel) node.labels().get(0));
		}
	}
	

}
