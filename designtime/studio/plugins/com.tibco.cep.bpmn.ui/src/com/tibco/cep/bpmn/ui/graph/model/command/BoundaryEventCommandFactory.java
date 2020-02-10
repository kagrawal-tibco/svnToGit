package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.PropertiesType;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.PropertyNodeType;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.graph.model.factory.events.AbstractConnectorUIFactory;
import com.tibco.cep.bpmn.ui.graph.palette.PaletteLoader;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteGroupItem;
import com.tibco.cep.bpmn.ui.graph.palette.model.BpmnPaletteModel;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.TSCommand;
import com.tomsawyer.interactive.command.editing.TSEApplyBuilderCommand;

/**
 * 
 * @author majha
 *
 */
public  class BoundaryEventCommandFactory extends AbstractConnectorCommandFactory {

	private IProject project;

	public BoundaryEventCommandFactory(IProject project, ModelController controller,ENamedElement extType) {
		super(controller, extType);
		this.project = project;
	}

	public IGraphCommand<TSEConnector> getDeleteCommand(TSENode parentNode,TSEConnector connector) {
		return new DeleteBoundaryEventCommand(IGraphCommand.COMMAND_DELETE, getModelController(),parentNode,connector);
	}
	
	public IGraphCommand<TSEConnector> getUpdateCommand(TSEConnector connector,
			PropertiesType type, Map<String, Object> updateList) {
		TSCommand modelCmd = null;
		TSEApplyBuilderCommand uiCmd = null;
		switch (type) {
		case GENERAL_PROPERTIES:
			String toolId = (String)updateList.get(BpmnMetaModelExtensionConstants.E_ATTR_TOOL_ID);
			if (toolId != null && !toolId.trim().isEmpty()) {
				BpmnPaletteModel toolDefinition = PaletteLoader.getBpmnPaletteModel(project);
				BpmnPaletteGroupItem item = toolDefinition.getPaletteItemById(toolId);
				EClass nType = BpmnModelClass.BOUNDARY_EVENT;
				String name = (String) connector.getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
				String attachedResource = (String) connector.getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
//				String label = (String) node.getAttributeValue(BpmnUIConstants.NODE_ATTR_LABEL);
				ENamedElement extType = getExtendedNodeType(item);
				ExpandedName classSpec  = null;
				if (extType != null)
					classSpec = BpmnMetaModel.INSTANCE.getExpandedName(extType);
				AbstractConnectorUIFactory nodeBuilder = BpmnGraphUIFactory
						.getInstance(null).getConnectorUIFactory((TSENode)connector.getOwner(),name, attachedResource, toolId, nType,
								classSpec);

				if (nodeBuilder != null) {
					 uiCmd = new ConnectorApplyBuilderCommand(connector, 
							nodeBuilder, toolId, nType, extType);
				}
				PropertyNodeType fromType = new PropertyNodeType(BpmnModelClass.BOUNDARY_EVENT, (EClass)getExtendTyped());
				PropertyNodeType toType = new PropertyNodeType(BpmnModelClass.BOUNDARY_EVENT, (EClass)extType);
				modelCmd = new ConnectorTypeChangeCommand(IGraphCommand.COMMAND_UPDATE,
						getModelController(),connector, fromType, toType, updateList);
			}else{
				modelCmd = new BoundaryEventGeneralPropertiesUpdateCommand(
						IGraphCommand.COMMAND_UPDATE, getModelController(), getExtendTyped(), connector, updateList);
			}

			break;

		default:
			break;
		}
		
		ConnectorUpdateGroupCommand grpCmd = new ConnectorUpdateGroupCommand(IGraphCommand.COMMAND_UPDATE,
				getModelController(), getExtendTyped(),
				connector);

		if(uiCmd != null){
			grpCmd.add(uiCmd);
		}
		
		if (modelCmd != null)
			grpCmd.add(modelCmd);

		return grpCmd;
	}
		

	public IGraphCommand<TSEConnector> getInsertCommand(TSENode parent, String attachedRes, String toolId) {
		double width = 20.0;
		double height = 20.0;
		double constantXOffset = 0.0;
		double constantYOffset = 0.0;
		double proportionalYOffset = 0.5;
		double proportionalXOffset = 0.5;
//		List<TSEConnector> connList = parent.connectors();
//		double increment = 1.0 / (connList.size() + 1);
//		double i = 0;
//		for (TSEConnector conn : connList) {
//			i += increment;
//			conn.setProportionalXOffset(0.5 - i);
//		}
//		proportionalXOffset = 0.5
		return new BoundaryEventAddCommand(IGraphCommand.COMMAND_INSERT, getModelController(), attachedRes,toolId, getExtendTyped(), parent, width, height,
				constantXOffset, constantYOffset, proportionalXOffset,
				proportionalYOffset);
	}
		
	@Override
	public boolean handlesModelType(EClass mtype, ENamedElement extType) {
		if (extType != null) {
			if (BpmnModelClass.BOUNDARY_EVENT.isSuperTypeOf(mtype)
					&& getExtendTyped().equals(extType)) {
				return true;
			}
		}
		return false;
	}


	

}
