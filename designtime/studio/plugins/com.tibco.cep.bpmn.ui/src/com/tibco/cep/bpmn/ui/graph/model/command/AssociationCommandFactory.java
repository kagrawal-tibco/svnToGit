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

public class AssociationCommandFactory extends AbstractEdgeCommandFactory {

	public AssociationCommandFactory(ModelController controller, EClass modelType, ENamedElement extType) {
		super(controller, modelType, extType);
	}

	@Override
	public boolean handlesModelType(EClass mtype, ENamedElement extType) {
		return BpmnModelClass.ASSOCIATION.isSuperTypeOf(mtype);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public IGraphCommand<TSEEdge> getAddCommand(TSENode sourceNode, TSENode targetNode, List bendList, boolean connectorConnector) {
		super.getAddCommand(sourceNode, targetNode, bendList, connectorConnector);

		return new AssociationCreateCommand(IGraphCommand.COMMAND_ADD, sourceNode, targetNode, bendList, getModelController());
	}

	public IGraphCommand<TSEEdge> getDeleteCommand(TSEEdge edge, boolean connectorConnector) {

		return new AssociationDeleteCommand(IGraphCommand.COMMAND_DELETE, edge, getModelController());
	}

	public IGraphCommand<TSEEdge> getInsertCommand(TSEEdge edge) {

		return new AssociationInsertCommand(IGraphCommand.COMMAND_INSERT, edge, getModelController());
	}

	@SuppressWarnings("rawtypes")
	public IGraphCommand<TSEEdge> getReconnectCommand(TSEEdge paramTSEEdge, TSENode paramTSENode, TSEConnector paramTSEConnector, boolean paramBoolean,
			List paramList1, List paramList2) {
		// TODO Auto-generated method stub
		return new AssociationReconnectCommand(getModelController(), paramTSEEdge, paramTSENode, paramTSEConnector, paramBoolean, paramList1, paramList2);
	}

	@Override
	public IGraphCommand<TSEEdge> getUpdateCommand(TSGraphObject edge, PropertiesType type, Map<String, Object> updateList) {
		IGraphCommand<TSEEdge> cmd;
		if (!(edge instanceof TSEEdge))
			return null;
		switch (type) {
		case GENERAL_PROPERTIES:
			cmd = new AssociationGeneralPropertiesUpdateCommand(IGraphCommand.COMMAND_UPDATE, getModelController(), BpmnModelClass.ASSOCIATION, (TSEEdge) edge,
					updateList);
			break;
		default:
			cmd = new AssociationUpdateCommand(IGraphCommand.COMMAND_UPDATE, getModelController(), BpmnModelClass.ASSOCIATION, (TSEEdge) edge, updateList);
		}
		return cmd;
	}

}
