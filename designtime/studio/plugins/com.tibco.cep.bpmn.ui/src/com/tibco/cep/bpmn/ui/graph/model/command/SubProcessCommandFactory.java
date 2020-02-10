package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.PropertiesType;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSGraphMember;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

public class SubProcessCommandFactory extends AbstractNodeCommandFactory {

	private BpmnDiagramManager diagramManager;

	public SubProcessCommandFactory(IProject proj, ModelController controller,EClass type,ENamedElement extType, BpmnDiagramManager manager) {
		super(proj, controller,type,extType);
		this.diagramManager = manager;
	}
	
	@Override
	public IGraphCommand<TSENode> getAddCommand(TSEGraph graph, double x,
			double y) {
		return new SubProcessCreateCommand(IGraphCommand.COMMAND_ADD,getModelController(),getModelType(),getExtendTyped(),graph, x, y);
	}
	
	
	@Override
	public IGraphCommand<TSENode> getInsertCommand(TSEGraph graph, TSENode node) {
		return new SubProcessInsertCommand(IGraphCommand.COMMAND_ADD,
				getModelController(), getModelType(), getExtendTyped(), graph,
				node,this.diagramManager );
	}
	
	public IGraphCommand<TSENode> getDeleteCommand(TSEGraph graph, TSENode node) {
		return new TaskDeleteCommand(IGraphCommand.COMMAND_DELETE,
				getModelController(), getModelType(), getExtendTyped(), node);
	}
	
	@Override
	public IGraphCommand<TSENode> getUpdateCommand(TSGraphObject grpObj,
			PropertiesType type, Map<String, Object> updateList) {
		TSENode node = null;
		if (grpObj instanceof TSENode)
			node = (TSENode) grpObj;
		else if (grpObj instanceof TSEGraph) {
			TSGraphMember parent = ((TSEGraph) grpObj).getParent();
			if (parent != null && parent instanceof TSENode)
				node = (TSENode) parent;
		}

		if (node != null) {
			AbstractUpdateNodeCommand modelCmd = null;
			EClass nodeType = (EClass) node
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);

			switch (type) {
			case GENERAL_PROPERTIES: {
				modelCmd = new SubProcessGeneralPropertiesUpdateCommand(
						IGraphCommand.COMMAND_UPDATE, getModelController(),
						nodeType, getExtendTyped(), (TSENode) node, updateList);

			}
				break;

			default:
				break;
			}

			return modelCmd;
		}

		return null;

	}

}
