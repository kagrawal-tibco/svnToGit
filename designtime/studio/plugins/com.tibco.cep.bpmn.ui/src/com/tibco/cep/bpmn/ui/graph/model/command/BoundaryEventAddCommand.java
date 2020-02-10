package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModel;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.BpmnGraphUIFactory;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tibco.cep.bpmn.ui.graph.model.factory.events.AbstractConnectorUIFactory;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.graphicaldrawing.TSENode;

/**
 * @author majha
 *
 */
public class BoundaryEventAddCommand extends AbstractConnectorAddCommand{

	private static final long serialVersionUID = 3316361261022717652L;
	private String nodeName;
	private EObjectWrapper<EClass, EObject> parentObject;

	public BoundaryEventAddCommand(int type, ModelController controller,String resUrl, String toolId, ENamedElement extType, TSENode node,
			double width, double height, double constantXOffset,
			double constantYOffset, double proportionalXOffset,
			double proportionalYOffset) {
		super(type, controller,  resUrl, toolId,BpmnModelClass.BOUNDARY_EVENT, extType, node, width, height, constantXOffset, constantYOffset,
				proportionalXOffset, proportionalYOffset);
	}

	
	protected void createEmfModel(boolean isRedo){
		nodeName = (String) getConnector().getAttributeValue(BpmnUIConstants.NODE_ATTR_NAME);
		toolId = (String)getConnector().getAttributeValue(BpmnUIConstants.NODE_ATTR_TOOL_ID);
		String attachedResource = (String) getConnector().getAttributeValue(BpmnUIConstants.NODE_ATTR_BE_RESOURCE_URL);
		
		TSENode parentNode = getNode();
		parentObject = EObjectWrapper.wrap((EObject)parentNode.getUserObject());

		EObject createElement = createElement();
		getConnector().setUserObject(createElement);
		
		if (!isRedo) {
			ExpandedName classSpec =  BpmnMetaModel.INSTANCE
					.getExpandedName(extendedType);
			AbstractConnectorUIFactory nodeUIFactory = BpmnGraphUIFactory
					.getInstance(null).getConnectorUIFactory(getNode(),nodeName,attachedResource, toolId,
							modelType, classSpec);
			nodeUIFactory.decorateNode((TSEConnector)getConnector());
			nodeUIFactory.layoutNode((TSEConnector)getConnector());
		}
	}
	
	protected EObject createElement() {
		EObjectWrapper<EClass, EObject> modelObject = getModelController().addBoundaryEvent(parentObject, (EClass)extendedType, toolId);
		return modelObject.getEInstance();
	}
	
	protected void removeElement(EObjectWrapper<EClass, EObject> modelObject) {
		getModelController().removeBoundaryEvent(parentObject, modelObject, new ArrayList<EObjectWrapper<EClass,EObject>>(), new ArrayList<EObjectWrapper<EClass,EObject>>());
	}
}
