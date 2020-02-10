package com.tibco.cep.bpmn.ui.graph.model.command;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.factory.events.AbstractConnectorUIFactory;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.interactive.command.editing.TSEApplyBuilderCommand;

/**
 * 
 * @author majha
 *
 */
public class ConnectorApplyBuilderCommand extends TSEApplyBuilderCommand {

	private static final long serialVersionUID = -8345947739040601899L;
	
	private EClass currentNodeType ;
	@SuppressWarnings("unused")
	private EClass oldNodeType ;
	private ENamedElement currentExtType ;
	@SuppressWarnings("unused")
	private ENamedElement oldExtType ;
	private String currentName;
	@SuppressWarnings("unused")
	private String oldName;
	private String toolId;
	@SuppressWarnings("unused")
	private String oldToolId;
	
	public ConnectorApplyBuilderCommand(TSEConnector paramTSENode,
			AbstractConnectorUIFactory paramTSNodeBuilder,String toolId, EClass ntype, ENamedElement eType) {
		super(paramTSENode, paramTSNodeBuilder);
		currentNodeType = ntype;
		currentExtType = eType;
		String name = paramTSNodeBuilder.getConnectorName();
		currentName = (name == null ? "" :name);
		this.toolId = toolId;
	}
	
	@Override
	protected void doAction() throws Throwable {
		super.doAction();
		oldNodeType = (EClass)getConnector().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		getConnector().setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, currentNodeType);	
		oldExtType = (ENamedElement)getConnector().getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
		getConnector().setAttribute(BpmnUIConstants.NODE_ATTR_EXT_TYPE, currentExtType);
		oldName =(String)getConnector().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
		getConnector().setAttribute(BpmnUIConstants.NODE_ATTR_NAME, currentName);
		oldToolId =(String)getConnector().getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		getConnector().setAttribute(BpmnUIConstants.NODE_ATTR_TOOL_ID, toolId);
		((AbstractConnectorUIFactory)getNewObjectBuilder()).decorateNode(getConnector());
		((AbstractConnectorUIFactory)getNewObjectBuilder()).layoutNode(getConnector());
	}
	
//	@Override
//	protected void undoAction() throws Throwable {
//		getNode().setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, oldNodeType);		
//		getNode().setAttribute(BpmnUIConstants.NODE_ATTR_EXT_TYPE, oldExtType);
//		getNode().setAttribute(BpmnUIConstants.NODE_ATTR_NAME, oldName);
//		getNode().setAttribute(BpmnUIConstants.NODE_ATTR_TOOL_ID, oldToolId);
//	}
	


}
