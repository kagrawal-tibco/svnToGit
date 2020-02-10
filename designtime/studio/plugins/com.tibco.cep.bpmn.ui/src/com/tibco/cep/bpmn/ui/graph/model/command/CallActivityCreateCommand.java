package com.tibco.cep.bpmn.ui.graph.model.command;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEGraph;

/**
 * @author pdhar
 *
 */
public class CallActivityCreateCommand extends AbstractCreateNodeCommand {
	private static final long serialVersionUID = -1314295592903350586L;
	@SuppressWarnings("unused")
	private EClass nodeType;
	private String nodeName;
	@SuppressWarnings("unused")
	private String nodeLabel;
	private TSGraph rootGraph;
	private TSEGraph currentGraph;
	private EObjectWrapper<EClass, EObject> process;
	private EObjectWrapper<EClass, EObject> lane;


	
	public CallActivityCreateCommand(int commandAdd, ModelController modelController,
			EClass modelType, ENamedElement extType, TSEGraph graph, double x, double y) {
		super(commandAdd,modelController,modelType,extType,graph,x,y);
	}

	protected void doAction() throws Throwable {
		super.doAction();
		currentGraph = (TSEGraph)getNode().getOwnerGraph();
		lane = EObjectWrapper.wrap((EObject)currentGraph.getUserObject());
		
		if(BpmnModelClass.SUB_PROCESS.isSuperTypeOf(lane.getEClassType())){
			rootGraph = currentGraph;
			process = lane;
		}
		else{
			rootGraph = ((TSEGraph)getNode().getOwnerGraph()).getGreatestAncestor();
			process = EObjectWrapper.wrap((EObject)rootGraph.getUserObject());
		}
		
		nodeType = (EClass) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		nodeName = (String) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
//		nodeLabel = (String) getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
		
		
		EObjectWrapper<EClass, EObject> modelObject = 
			getModelController().createActivity(nodeName,"",getModelType(),process,lane);
		getNode().setUserObject(modelObject.getEInstance());
		
	}

	protected void undoAction() throws Throwable {
		super.undoAction();
		
	}

	protected void finalize() throws Throwable {
		nodeType = null;
		nodeName = null;
		rootGraph = null;
		currentGraph = null;
		process = null;
		lane = null;
		super.finalize();
	}
	

}
