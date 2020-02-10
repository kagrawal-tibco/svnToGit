package com.tibco.cep.bpmn.ui.graph.model.command;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEAddConnectorCommand;
/**
 * @author majha
 *
 */
abstract public class AbstractConnectorAddCommand extends TSEAddConnectorCommand implements IGraphCommand<TSEConnector>{
	private static final long serialVersionUID = 2514013020931604947L;
	int commandType;
	protected ModelController modelController;
	protected ENamedElement extendedType;
	protected EClass modelType;
	protected String toolId;
	protected String attachedResourceUrl;

	public AbstractConnectorAddCommand(int type, ModelController controller, String resUrl, String toolId, EClass modelType, ENamedElement extType, TSENode node,
			double width, double height, double constantXOffset,
			double constantYOffset, double proportionalXOffset,
			double proportionalYOffset) {
		super(node, width, height, constantXOffset, constantYOffset,
				proportionalXOffset, proportionalYOffset);
		this.commandType = type;
		this.modelController = controller;
		this.modelType = modelType;
		this.extendedType = extType;
		this.toolId = toolId;
		attachedResourceUrl = resUrl;
	}

	@Override
	protected void doAction() throws Throwable {
		selectNewConnector(false);
		super.doAction();
		getConnector().setAttribute(BpmnUIConstants.NODE_ATTR_TYPE, modelType);		
		getConnector().setAttribute(BpmnUIConstants.NODE_ATTR_EXT_TYPE, extendedType);	
		getConnector().setAttribute(BpmnUIConstants.NODE_ATTR_NAME, "");	
		getConnector().setAttribute(BpmnUIConstants.NODE_ATTR_TOOL_ID, toolId);
		getConnector().setAttribute(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL, attachedResourceUrl);
		createEmfModel(false);
		
	}
	
	@Override
	protected void undoAction() throws Throwable {
		// TODO Auto-generated method stub
		super.undoAction();
		EObjectWrapper<EClass, EObject> userObject = EObjectWrapper.wrap((EObject)getConnector().getUserObject());
		removeElement(userObject);
	}
	
	@Override
	protected void redoAction() throws Throwable {
		super.redoAction();
		createEmfModel(true);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class type) {
		if(type == TSEAddConnectorCommand.class ) {
			return this;
		}  
		return null;
	}

	@Override
	public int getCommandType() {
		// TODO Auto-generated method stub
		return commandType;
	}

	@Override
	public ModelController getModelController() {
		// TODO Auto-generated method stub
		return modelController;
	}

	@Override
	public TSEConnector getNodeOrGraph() {
		// TODO Auto-generated method stub
		return (TSEConnector)getConnector();
	}
	
	abstract protected void createEmfModel(boolean isRedo);
	
	abstract protected EObject createElement();
	
	abstract protected void removeElement(EObjectWrapper<EClass, EObject> modelObject);
}
