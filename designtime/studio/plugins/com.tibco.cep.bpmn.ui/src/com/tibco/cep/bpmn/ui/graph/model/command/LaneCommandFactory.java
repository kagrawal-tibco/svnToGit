package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.PropertiesType;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

public class LaneCommandFactory extends AbstractNodeCommandFactory {

	public LaneCommandFactory(IProject proj, ModelController controller, EClass type,
			ENamedElement extType) {
		super(proj, controller, type, extType);
	}

	@Override
	public IGraphCommand<TSENode> getInsertCommand(TSEGraph graph, TSENode node) {
		return new LaneInsertCommand(IGraphCommand.COMMAND_ADD,
				getModelController(), getModelType(), getExtendTyped(), graph,
				node);
	}

	@Override
	public IGraphCommand<TSENode> getAddCommand(TSEGraph graph, double x,
			double y) {
		return new LaneCreateCommand(IGraphCommand.COMMAND_ADD,
				getModelController(), getModelType(), getExtendTyped(), graph,
				x, y);
	}

	@Override
	public IGraphCommand<TSENode> getDeleteCommand(TSEGraph graph, TSENode node) {
		return new LaneDeleteCommand(IGraphCommand.COMMAND_DELETE,
				getModelController(), getModelType(), getExtendTyped(), node);
	}

	@Override
	public IGraphCommand<TSENode> getUpdateCommand(TSGraphObject grpObj,
			PropertiesType type, Map<String, Object> updateList) {
		if (!(grpObj instanceof TSENode))
			return null;

		TSENode node = (TSENode) grpObj;
		AbstractUpdateNodeCommand modelCmd = null;
		EClass nodeType = (EClass) node
				.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);

		switch (type) {
		case GENERAL_PROPERTIES: {
			modelCmd = new LaneGeneralPropertiesUpdateCommand(
					IGraphCommand.COMMAND_UPDATE, getModelController(),
					nodeType, getExtendTyped(), (TSENode) node, updateList);

			}
			break;

		default:
			break;
		}

		return modelCmd;
	}

}
