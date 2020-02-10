package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.index.BpmnIndexUtils;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.ontology.BpmnIndex;
import com.tibco.cep.bpmn.model.designtime.ontology.impl.DefaultBpmnIndex;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSGraph;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author pdhar
 *
 */
public class CallActivityInsertCommand extends FlowElementInsertCommand {

	private static final long serialVersionUID = 6598540339585421205L;
	@SuppressWarnings("unused")
	private EClass nodeType;
	private String nodeName;
	@SuppressWarnings("unused")
	private String nodeLabel;
	private TSGraph rootGraph;
	private TSEGraph currentGraph;
	private EObjectWrapper<EClass, EObject> process;
	private EObjectWrapper<EClass, EObject> lane;
	private IProject project;

	
	
	public CallActivityInsertCommand(int type, ModelController controller,
			EClass modelType, ENamedElement extType, TSEGraph graph,
			TSENode node, IProject proj) {
		super(type, controller, modelType, extType, graph, node);
		this.project = proj;
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
		
		
	}
	
	protected Object parseAttachedResource(String resourcePath) {
		resourcePath = resourcePath.replace("\\", "/");
		EObject resource = null;
		@SuppressWarnings("unused")
		boolean found = false;
		Collection<EObject> procs = getBpmnOntology().getAllProcesses();
		for(EObject proc:procs) {
			EObjectWrapper<EClass, EObject> processWrapper = EObjectWrapper.wrap(proc);
			String folder = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_FOLDER);
			String name = processWrapper.getAttribute(BpmnMetaModelConstants.E_ATTR_NAME);
			String fullpath = null;
			
			if(folder.endsWith(Character.toString(IPath.SEPARATOR)))
				fullpath = folder+name;
			else
				fullpath = folder+IPath.SEPARATOR+name;
			
			if (fullpath.equals(resourcePath)) {
				resource = processWrapper.getEInstance();
				found = true;
				break;
			}
		}
		
		return resource;
	}
	
	private BpmnIndex getBpmnOntology() {
		EObject index = BpmnIndexUtils.getIndex(project);
		return new DefaultBpmnIndex(index);
	}

	

	protected void doAction() throws Throwable {
		super.doAction();

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

	@Override
	protected EObject createElement() {
		String toolId = (String) getNode().getAttributeValue(
				BpmnUIConstants.NODE_ATTR_TOOL_ID);
		EObjectWrapper<EClass, EObject> modelObject = getModelController()
				.createActivity(nodeName, toolId, getModelType(), process, lane);

		return modelObject.getEInstance();
	}
	
	@Override
	protected void removeElement(EObjectWrapper<EClass, EObject> modelObject) {
		getModelController().removeActivity(modelObject,process,lane,incomingEdge,outgoingEdge);
		
	}
}
