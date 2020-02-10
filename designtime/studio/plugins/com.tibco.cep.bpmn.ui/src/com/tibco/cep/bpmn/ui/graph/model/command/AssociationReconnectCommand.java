package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSENode;
import com.tomsawyer.interactive.command.editing.TSEReconnectEdgeCommand;

public class AssociationReconnectCommand extends TSEReconnectEdgeCommand implements IGraphCommand<TSEEdge> {

	private static final long serialVersionUID = -8145901392714960789L;
	private ModelController modelController;
	private EObjectWrapper<EClass, EObject> oldNode;

	@SuppressWarnings("rawtypes")
	public AssociationReconnectCommand(ModelController modelController, TSEEdge paramTSEEdge, TSENode paramTSENode, TSEConnector paramTSEConnector,
			boolean paramBoolean, List paramList1, List paramList2) {
		super(paramTSEEdge, paramTSENode, paramTSEConnector, paramBoolean, paramList1, paramList2);
		this.modelController = modelController;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class type) {
		if (type == TSEReconnectEdgeCommand.class) {
			return this;
		}
		return null;
	}

	@Override
	public int getCommandType() {
		return IGraphCommand.COMMAND_UPDATE;
	}

	@Override
	public ModelController getModelController() {
		return modelController;
	}

	@Override
	public TSEEdge getNodeOrGraph() {
		return getEdge();
	}

	@Override
	protected void doAction() throws Throwable {
		super.doAction();
		createEmfModel();
	}

	@Override
	public void redoAction() throws Throwable {
		super.redoAction();
		createEmfModel();
	}

	@Override
	protected void undoCleanup() {
		super.undoCleanup();
	}

	@Override
	protected void undoAction() throws Throwable {
		// TODO Auto-generated method stub
		super.undoAction();
		final EObjectWrapper<EClass, EObject> edge = EObjectWrapper.wrap((EObject) getEdge().getUserObject());
		final EObjectWrapper<EClass, EObject> node = EObjectWrapper.wrap((EObject) getNewNode().getUserObject());

		if (mustReconnectSource()) {
			node.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING, edge.getEInstance());
			edge.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF, oldNode.getEInstance());
			oldNode.addToListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING, edge.getEInstance());
		} else {
			node.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING, edge.getEInstance());
			edge.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF, oldNode.getEInstance());
			oldNode.addToListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING, edge.getEInstance());
		}
	}

	private void createEmfModel() {
		final EObjectWrapper<EClass, EObject> edge = EObjectWrapper.wrap((EObject) getEdge().getUserObject());
		final EObjectWrapper<EClass, EObject> node = EObjectWrapper.wrap((EObject) getNewNode().getUserObject());

		if (mustReconnectSource()) {
			oldNode = EObjectWrapper.wrap((EObject) edge.getAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF));
			oldNode.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING, edge.getEInstance());
			edge.setAttribute(BpmnMetaModelConstants.E_ATTR_SOURCE_REF, node.getEInstance());
			node.addToListAttribute(BpmnMetaModelConstants.E_ATTR_OUTGOING, edge.getEInstance());
		} else {
			oldNode = EObjectWrapper.wrap((EObject) edge.getAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF));
			oldNode.removeFromListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING, edge.getEInstance());
			edge.setAttribute(BpmnMetaModelConstants.E_ATTR_TARGET_REF, node.getEInstance());
			node.addToListAttribute(BpmnMetaModelConstants.E_ATTR_INCOMING, edge.getEInstance());
		}
	}

	@Override
	protected void finalize() throws Throwable {
		modelController = null;
		oldNode = null;
		super.finalize();
	}
}
