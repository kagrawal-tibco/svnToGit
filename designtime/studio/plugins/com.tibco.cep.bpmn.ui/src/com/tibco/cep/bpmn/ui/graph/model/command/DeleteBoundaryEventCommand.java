package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * 
 * @author majha
 *
 */
public class  DeleteBoundaryEventCommand extends AbstractDeleteConnectorCommand{

	private static final long serialVersionUID = -1715071225376205863L;


	public DeleteBoundaryEventCommand(int commandType, ModelController controller, TSENode parentNode,
			TSEConnector connector) {
		super(commandType, controller, parentNode, connector);
	}


	@Override
	protected void removeElement(EObjectWrapper<EClass, EObject> parent, EObjectWrapper<EClass, EObject> modelObject,
			List<EObjectWrapper<EClass, EObject>> incoming,
			List<EObjectWrapper<EClass, EObject>> outgoing) {
		getModelController().removeBoundaryEvent(parent, modelObject, incoming, outgoing);
		
	}
	
	protected void addElement() {
		EObject userObject = (EObject)getNodeOrGraph().getUserObject();
		getModelController().addBoundaryEvent(parentObject, EObjectWrapper.wrap(userObject));
	}
	


}
