package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tibco.cep.bpmn.ui.PropertiesType;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;


/**
 * @author pdhar
 *
 * @param <C>
 */
public abstract class AbstractGraphCommandFactory 
		implements ICommandFactory{

	protected ModelController modelController;
	protected EClass modelType;
	protected ENamedElement extendTyped;

	public AbstractGraphCommandFactory(ModelController controller,EClass modelType,ENamedElement extType) {
		this.modelController = controller;
		this.modelType = modelType;
		this.extendTyped = extType;
	}
	
	public ModelController getModelController() {
		return modelController;
	}
	
	public EClass getModelType() {
		return modelType;
	}
	
	public ENamedElement getExtendTyped() {
		return extendTyped;
	}
	
	@SuppressWarnings("unchecked")
	public IGraphCommand<TSEGraph> getCommand(int type, Object ... args) {
		switch(type) {
		case IGraphCommand.COMMAND_ADD:
			return getAddCommand((TSEGraph)args[0],(Double)args[1],(Double)args[2]);
		case IGraphCommand.COMMAND_UPDATE:
			return getUpdateCommand((TSGraphObject)args[0],(PropertiesType)args[1],(Map<String, Object>)args[2]);
		case IGraphCommand.COMMAND_DELETE:
			return getDeleteCommand((TSEGraph)args[0],(TSENode)args[1]);
		case IGraphCommand.COMMAND_INSERT:
			return getInsertCommand((TSEGraph)args[0],(TSENode)args[1]);
		}
		return null;
	}

	public IGraphCommand<TSEGraph> getInsertCommand(TSEGraph graph, TSENode node) {
		// TODO Auto-generated method stub
		return null;
	}

	public IGraphCommand<TSEGraph> getDeleteCommand(TSEGraph graph, TSENode node) {
		// TODO Auto-generated method stub
		return null;
	}

	public IGraphCommand<TSEGraph> getUpdateCommand(TSGraphObject node, PropertiesType type, Map<String, Object> updateList) {
		// TODO Auto-generated method stub
		return null;
	}

	public IGraphCommand<TSEGraph> getAddCommand(TSEGraph graph, double x,
			double y) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public boolean handlesModelType(EClass mtype, ENamedElement extType) {
		if (getModelType().equals(mtype) && ((extType == null ) || (getExtendTyped().equals(extType)))){
			return true;
		}
		return false;
	}
	
	
}