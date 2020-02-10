package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tibco.cep.bpmn.ui.PropertiesType;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;

public abstract class AbstractEdgeCommandFactory implements ICommandFactory {
	protected ModelController modelController;
	protected EClass modelType;
	protected ENamedElement extendTyped;

	public AbstractEdgeCommandFactory(ModelController controller, EClass modelType, ENamedElement extType) {
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public IGraphCommand<TSEEdge> getCommand(int type, Object... args) {
		switch (type) {
		case IGraphCommand.COMMAND_ADD:
			boolean connectedToConnector = args.length == 4 ? (Boolean) args[3] : false;
			return getAddCommand((TSENode) args[0], (TSENode) args[1], (List) args[2], connectedToConnector);
		case IGraphCommand.COMMAND_UPDATE:
			return getUpdateCommand((TSGraphObject) args[0], (PropertiesType) args[1], (Map<String, Object>) args[2]);
		case IGraphCommand.COMMAND_DELETE:
			connectedToConnector = args.length == 2 ? (Boolean) args[1] : false;
			return getDeleteCommand((TSEEdge) args[0], connectedToConnector);
		case IGraphCommand.COMMAND_INSERT:
			return getInsertCommand((TSEEdge) args[0]);
		case IGraphCommand.COMMAND_RECONNECT:
			return getReconnectCommand((TSEEdge) args[0], (TSENode) args[1], (TSEConnector) args[2], (Boolean) args[3], (List<?>) args[4], (List<?>) args[5]);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public IGraphCommand<TSEEdge> getAddCommand(TSENode sourceNode, TSENode targetNode, List bendList, boolean connectedToConnector) {
		// TODO Auto-generated method stub
		return null;
	}

	public IGraphCommand<TSEEdge> getUpdateCommand(TSGraphObject edge, PropertiesType type, Map<String, Object> updateList) {
		// TODO Auto-generated method stub
		return null;
	}

	public IGraphCommand<TSEEdge> getDeleteCommand(TSEEdge edge, boolean connectedToConnector) {
		// TODO Auto-generated method stub
		return null;
	}

	public IGraphCommand<TSEEdge> getInsertCommand(TSEEdge edge) {
		// TODO Auto-generated method stub
		return null;
	}

	public IGraphCommand<TSEEdge> getReconnectCommand(TSEEdge paramTSEEdge, TSENode paramTSENode, TSEConnector paramTSEConnector, boolean paramBoolean,
			List<?> paramList1, List<?> paramList2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean handlesModelType(EClass mtype, ENamedElement extType) {
		if (getModelType().equals(mtype) && ((extType == null) || (getExtendTyped().equals(extType)))) {
			return true;
		}
		return false;
	}

}
