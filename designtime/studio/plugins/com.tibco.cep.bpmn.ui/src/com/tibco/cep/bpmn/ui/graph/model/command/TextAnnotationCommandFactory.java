package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.PropertiesType;
import com.tibco.cep.bpmn.ui.editor.BpmnDiagramManager;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.PropertyNodeType;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.graph.palette.PaletteLoader;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.TSCommand;
import com.tomsawyer.interactive.command.editing.TSEApplyBuilderCommand;

public class TextAnnotationCommandFactory extends AbstractNodeCommandFactory {

	private BpmnDiagramManager diagramManager;
	public TextAnnotationCommandFactory(IProject proj,ModelController controller,
			EClass type, ENamedElement extType, BpmnDiagramManager manager) {
		super(proj, controller, type, extType);
		this.diagramManager=manager;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public IGraphCommand<TSENode> getAddCommand(TSEGraph graph, double x,
			double y) {
		return new GatewayCreateCommand(IGraphCommand.COMMAND_ADD,getModelController(),getModelType(),getExtendTyped(),graph, x, y);
	}
	
	@Override
	public IGraphCommand<TSENode> getInsertCommand(TSEGraph graph, TSENode node) {
		return new TextAnnotationInsertCommand(IGraphCommand.COMMAND_ADD,
				getModelController(), getModelType(), getExtendTyped(), graph,
				node,diagramManager);
	}
	
	@Override
	public IGraphCommand<TSENode> getDeleteCommand(TSEGraph graph, TSENode node) {
		return new TextAnnotationDeleteCommand(IGraphCommand.COMMAND_DELETE,
				getModelController(), getModelType(), getExtendTyped(),node);
	}

	@Override
	public IGraphCommand<TSENode> getUpdateCommand(TSGraphObject grpObj, PropertiesType type, Map<String, Object> updateList) {
		if(! (grpObj instanceof TSENode))
			return null;
		
		TSENode node = (TSENode)grpObj;
		TSEApplyBuilderCommand uiCmd = null;
		TSCommand modelCmd = null;
		EClass nodeType = (EClass)node.getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		
		switch (type) {
		case GENERAL_PROPERTIES:
		{
			String toolId = (String)updateList.get(BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);
			String resUrl = (String) node
					.getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
			if (toolId != null && !toolId.trim().isEmpty()) {
				BpmnPaletteModel toolDefinition = PaletteLoader.getBpmnPaletteModel(project);
				BpmnPaletteGroupItem item = toolDefinition.getPaletteItemById(toolId);
				EClass nType = getNodeType(item);
				String name = (String) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
//				String label = (String) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
				ENamedElement extType = (ENamedElement)updateList.get(BpmnUIConstants.NODE_ATTR_EXT_TYPE);

				ExpandedName classSpec = null;
				if(extType != null)
					classSpec = BpmnMetaModel.INSTANCE.getExpandedName(extType);
				AbstractNodeUIFactory nodeBuilder = BpmnGraphUIFactory
						.getInstance(null).getNodeUIFactory(name,resUrl,toolId, nType,
								classSpec);

				if (nodeBuilder != null) {
					uiCmd = new NodeApplyBuilderCommand((TSENode) node,
							nodeBuilder, toolId, nType, extType);
				}
				PropertyNodeType fromType = new PropertyNodeType(nodeType, (EClass)getExtendTyped());
				PropertyNodeType toType = new PropertyNodeType(nType, (EClass)extType);
				modelCmd = new NodeTypeChangeCommand(IGraphCommand.COMMAND_UPDATE,
						getModelController(),(TSENode)node, fromType, toType, updateList);
			}else{
				modelCmd = new TextAnnotationGeneralPropertiesUpdateCommand(IGraphCommand.COMMAND_UPDATE,
						getModelController(), nodeType, getExtendTyped(),
						(TSENode)node, updateList);
			}
		}
			break;

		default:
			break;
		}
		
		NodeUpdateGroupCommand grpCmd = new NodeUpdateGroupCommand(IGraphCommand.COMMAND_UPDATE,
				getModelController(),nodeType, getExtendTyped(),
				(TSENode)node);

		if(uiCmd != null){
			grpCmd.add(uiCmd);
		}
		
		if (modelCmd != null)
			grpCmd.add(modelCmd);
		
		return grpCmd;
	} 

}
