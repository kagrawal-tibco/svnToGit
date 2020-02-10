package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.PropertiesType;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;

public class SequenceFlowCommandFactory extends AbstractEdgeCommandFactory {

	public SequenceFlowCommandFactory(ModelController controller,
			EClass modelType, ENamedElement extType) {
		super(controller, modelType, extType);
	}

	@Override
	public boolean handlesModelType(EClass mtype, ENamedElement extType) {
		return BpmnModelClass.SEQUENCE_FLOW.isSuperTypeOf(mtype);
	}
	
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public IGraphCommand<TSEEdge> getAddCommand(TSENode sourceNode,
			TSENode targetNode, List bendList, boolean connectedToConnector) {
		super.getAddCommand(sourceNode, targetNode, bendList, connectedToConnector);
		
		return new SequenceCreateCommand(IGraphCommand.COMMAND_ADD,sourceNode,targetNode,bendList,connectedToConnector,getModelController());
	}
	
	public IGraphCommand<TSEEdge> getDeleteCommand(TSEEdge edge, boolean connectedToConnector) {
		
		return new SequenceDeleteCommand(IGraphCommand.COMMAND_DELETE,edge, connectedToConnector,getModelController());
	}
	
	public IGraphCommand<TSEEdge> getInsertCommand(TSEEdge edge) {
		
		return new SequenceInsertCommand(IGraphCommand.COMMAND_INSERT,edge,getModelController());
	}
	
	@SuppressWarnings("rawtypes")
	public IGraphCommand<TSEEdge> getReconnectCommand(TSEEdge paramTSEEdge, TSENode paramTSENode,TSEConnector paramTSEConnector, boolean paramBoolean,
			List paramList1, List paramList2) {
		// TODO Auto-generated method stub
		return new SequenceReconnectCommand(getModelController(),paramTSEEdge,  paramTSENode, paramTSEConnector, paramBoolean, paramList1,paramList2);
	}
	
	@Override
	public IGraphCommand<TSEEdge> getUpdateCommand(TSGraphObject edge,PropertiesType type, Map<String, Object> updateList) {
		IGraphCommand<TSEEdge> cmd ;
		if(! (edge instanceof TSEEdge))
			return null;
		switch (type){
		case GENERAL_PROPERTIES:
			cmd =  new SequenceGeneralPropertiesUpdateCommand(
					IGraphCommand.COMMAND_UPDATE,
					getModelController(),
					BpmnModelClass.SEQUENCE_FLOW,
					(TSEEdge)edge,
					updateList);
			break;
		default:
			cmd =  new SequenceUpdateCommand(
					IGraphCommand.COMMAND_UPDATE,
					getModelController(),
					BpmnModelClass.SEQUENCE_FLOW,
					(TSEEdge)edge,
					updateList);
		}
		return cmd;
	}

}
