package com.tibco.cep.bpmn.ui.graph.model.factory.common;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.extension.ExtensionHelper;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.Messages;
import com.tibco.cep.bpmn.ui.editor.BpmnLayoutManager;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tibco.cep.diagramming.ui.NoteNodeUI;
import com.tomsawyer.drawing.geometry.shared.TSPolygonShape;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSEGraphManager;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.graphicaldrawing.TSESolidObject;
import com.tomsawyer.graphicaldrawing.ui.simple.TSEObjectUI;

public class TextAnnotationNodeUIFactory extends AbstractNodeUIFactory {

	private static final long serialVersionUID = -1649204342472082871L;

	public TextAnnotationNodeUIFactory(String name,String referredBEResource, String toolId,
			BpmnLayoutManager layoutManager) {
		super(name, referredBEResource, toolId, layoutManager, BpmnModelClass.TEXT_ANNOTATION);
		if(getNodeName() == null) {
			setNodeName(Messages.getString("title.textannotation")); //$NON-NLS-1$
		}
	}
	
	@Override
	public TSEObjectUI initGraphUI(Object ...args) {
		NoteNodeUI ui = new NoteNodeUI();
		return ui;
	}
	
	@Override
	public TSENode addNode(TSEGraph graph) {
		// XYZ
		TSENode node = super.addNode(graph);
		TSEGraphManager graphManager = (TSEGraphManager) graph.getOwnerGraphManager();
		if (graphManager.getNodeBuilder() != null)
			graphManager.getNodeBuilder().setNodeUI(getNodeUI());
		return node;
	}

	@Override
	public void decorateNode(TSENode node) {
		node.setName(Messages.getString("PALLETTE_ENTRY_TOOL_NOTE_TITLE"));
		node.setSize(60, 25);
		node.setShape(TSPolygonShape.fromString("[ 6 (0, 0) (100, 0) (100, 75) (75, 75) (75, 100) (0, 100) ]"));
//		node.setResizability(TSESolidObject.RESIZABILITY_TIGHT_FIT);
		node.setUI(getNodeUI());
		// XYZ?
		node.discardAllLabels();
	}

	@Override
	public void layoutNode(TSENode node) {
		EObject userObject = (EObject)node.getUserObject();
		if(userObject != null && userObject.eClass().equals(BpmnModelClass.TEXT_ANNOTATION)){
			EObjectWrapper<EClass, EObject> valueWrapper = ExtensionHelper.getAddDataExtensionValueWrapper(userObject);
			if(valueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LENGTH)&&
					valueWrapper.containsAttribute(BpmnMetaModelExtensionConstants.E_ATTR_BREADTH)){
				Double length = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_LENGTH);
				Double breadth = valueWrapper.getAttribute(BpmnMetaModelExtensionConstants.E_ATTR_BREADTH);
				if(length != null && breadth != null){
					node.setSize(length, breadth);
			}
		}
		

	}
	
	}
}
