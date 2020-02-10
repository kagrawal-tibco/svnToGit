package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.interactive.command.TSCommand;
import com.tomsawyer.interactive.command.TSGroupCommand;

/**
 * @author majha
 *
 */
public class ConnectorUpdateGroupCommand extends TSGroupCommand implements IGraphCommand<TSEConnector> {

	private static final long serialVersionUID = 190832044212796294L;
	private int commandType;
	private ModelController modelController;
	private EClass modelType;
	private ENamedElement extendedType;
	private TSEConnector node;


	public ConnectorUpdateGroupCommand(int type,ModelController controller,ENamedElement extType,TSEConnector node) {
		super();
		this.commandType = type;
		this.modelController = controller;
		this.modelType = BpmnModelClass.BOUNDARY_EVENT;
		this.extendedType = extType;
		this.node = node;
	}
	
	public ModelController getModelController() {
		return modelController;
	}


	public int getCommandType() {
		return commandType;
	}
	
	public EClass getModelType() {
		return modelType;
	}
	
	
	public ENamedElement getExtendedType() {
		return extendedType;
	}
	
	
	
	public List<TSGraphObject> getAffectedObjects() {
		List<TSGraphObject> objects = new LinkedList<TSGraphObject>();
		objects.add(getNode());
		return objects;
	}
	
	private TSEConnector getNode() {
		// TODO Auto-generated method stub
		return node;
	}

	@Override
	public TSEConnector getNodeOrGraph() {
		return getNode();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class type) {
		if(type == TSCommand.class) {
			return this;
		}  
		return null;
	}
	
	protected void finalize() throws Throwable {
		modelController = null;
		node = null;
		modelType = null;
		extendedType = null;
		super.finalize();
	}
}
