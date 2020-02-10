package com.tibco.cep.bpmn.ui.graph.model.command;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.AbstractNodeUIFactory;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEApplyBuilderCommand;

/**
 * 
 * @author majha
 *
 */
public class NodeApplyBuilderCommand extends TSEApplyBuilderCommand {

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
	
	public NodeApplyBuilderCommand(TSENode paramTSENode,
			AbstractNodeUIFactory paramTSNodeBuilder,String toolId, EClass ntype, ENamedElement eType) {
		super(paramTSENode, paramTSNodeBuilder);
		currentNodeType = ntype;
		currentExtType = eType;
		String name = paramTSNodeBuilder.getNodeName();
		currentName = (name == null ? "" :name);
		this.toolId = toolId;
	}
	
	@Override
	protected void doAction() throws Throwable {
		super.doAction();
		oldNodeType = (EClass)getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TYPE);
		getNode().setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, currentNodeType);	
		oldExtType = (ENamedElement)getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_EXT_TYPE);
		getNode().setAttribute(BpmnUIConstants.NODE_ATTR_EXT_TYPE, currentExtType);
		oldName =(String)getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
		getNode().setAttribute(BpmnUIConstants.NODE_ATTR_NAME, currentName);
		oldToolId =(String)getNode().getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		getNode().setAttribute(BpmnUIConstants.NODE_ATTR_TOOL_ID, toolId);
		((AbstractNodeUIFactory)getNewObjectBuilder()).decorateNode(getNode());
		((AbstractNodeUIFactory)getNewObjectBuilder()).layoutNode(getNode());
	}
	
//	@Override
//	protected void undoAction() throws Throwable {
//		getNode().setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, oldNodeType);		
//		getNode().setAttribute(BpmnUIConstants.NODE_ATTR_EXT_TYPE, oldExtType);
//		getNode().setAttribute(BpmnUIConstants.NODE_ATTR_NAME, oldName);
//		getNode().setAttribute(BpmnUIConstants.NODE_ATTR_TOOL_ID, oldToolId);
//	}
	


}
