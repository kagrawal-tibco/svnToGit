package com.tibco.cep.bpmn.ui.graph.model.command;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEGraph;

/**
 * @author pdhar
 *
 */
public class SubProcessCreateCommand extends AbstractCreateNodeCommand {
	private static final long serialVersionUID = -1314295592903350586L;
	@SuppressWarnings("unused")
	private EClass nodeType;
	private String nodeName;
//	private String nodeLabel;
	private TSGraph rootGraph;
	private TSEGraph currentGraph;
	private EObjectWrapper<EClass, EObject> process;
	private EObjectWrapper<EClass, EObject> lane;


	
	public SubProcessCreateCommand(int commandAdd, ModelController modelController,
			EClass modelType, ENamedElement extType, TSEGraph graph, double x, double y) {
		super(commandAdd,modelController,modelType,extType,graph,x,y);
	}

	protected void doAction() throws Throwable {
		super.doAction();
		rootGraph = ((TSEGraph)getNode().getOwnerGraph()).getGreatestAncestor();
		currentGraph = (TSEGraph)getNode().getOwnerGraph();
		
		nodeType = (EClass) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		nodeName = (String) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
//		nodeLabel = (String) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
		
		process = EObjectWrapper.wrap((EObject)rootGraph.getUserObject());
		lane = EObjectWrapper.wrap((EObject)currentGraph.getUserObject());
		
		EObjectWrapper<EClass, EObject> modelObject = getModelController().createActivity(nodeName,"",getModelType(),process,lane);
		getNode().setUserObject(modelObject.getEInstance());
		
		TSEGraph childGraph = (TSEGraph) getNode().getChildGraph();
		if(childGraph != null) {
			childGraph.setUserObject(modelObject.getEInstance());
		}
	}

	protected void undoAction() throws Throwable {
		super.undoAction();
		
	}

	protected void finalize() throws Throwable {
		nodeType = null;
		nodeName = null;
//		nodeLabel = null;
		rootGraph = null;
		currentGraph = null;
		process = null;
		lane = null;
		super.finalize();
	}
	

}
