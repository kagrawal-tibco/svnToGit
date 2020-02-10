package com.tibco.cep.bpmn.ui.graph.model.command;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;

import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.graphicaldrawing.TSENode;

public class PoolCommandFactory extends AbstractNodeCommandFactory {

	public PoolCommandFactory(IProject proj, ModelController controller, EClass type,
			ENamedElement extType) {
		super(proj, controller, type, extType);
	}
	
	@Override
	public IGraphCommand<TSENode> getInsertCommand(TSEGraph graph, TSENode node) {
		return new PoolInsertCommand(IGraphCommand.COMMAND_ADD,
				getModelController(), getModelType(), getExtendTyped(), graph,
				node);
	}

}
