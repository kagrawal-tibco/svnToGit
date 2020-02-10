package com.tibco.cep.bpmn.ui.graph.model.command;

import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSGraphObject;

public interface IGraphCommand <C extends TSGraphObject> {

	public static final int COMMAND_ADD = 1;
	public static final int COMMAND_UPDATE = 2;
	public static final int COMMAND_DELETE = 3;
	public static final int COMMAND_INSERT = 4;
	public static final int COMMAND_RECONNECT = 5;

	public ModelController getModelController();

	public C getNodeOrGraph();

	public int getCommandType();
	
	@SuppressWarnings("rawtypes")
	Object getAdapter(Class type);


}
