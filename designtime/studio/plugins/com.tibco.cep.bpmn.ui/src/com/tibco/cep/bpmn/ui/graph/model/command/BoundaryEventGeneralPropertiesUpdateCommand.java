package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graph.TSGraphObject;
import com.tomsawyer.graphicaldrawing.TSEConnector;
import com.tomsawyer.interactive.command.TSCommand;

/**
 * @author majha
 *
 */
public class BoundaryEventGeneralPropertiesUpdateCommand extends TSCommand implements IGraphCommand<TSEConnector> {

	private static final long serialVersionUID = -6534242318360804564L;
	int commandType;
	protected ModelController modelController;
	private ENamedElement extendedType;
	private EClass modelType;
	private TSEConnector connector;
	protected Map<String, Object> updateList;

	protected EObjectWrapper<EClass, EObject> elementWrapper;
	protected Map<String, Object> oldProperties;

	public BoundaryEventGeneralPropertiesUpdateCommand(int type,ModelController controller, ENamedElement extType,TSEConnector connector, Map<String, Object> updateList) {
		this.commandType = type;
		this.modelController = controller;
		this.modelType = BpmnModelClass.BOUNDARY_EVENT;
		this.extendedType = extType;
		this.connector = connector;
		this.updateList = updateList;
		oldProperties = new HashMap<String, Object>();
		EObject userObject = (EObject) connector.getUserObject();
		elementWrapper = EObjectWrapper.wrap(userObject);
	}

	protected void doAction() throws Throwable {
		String desc = (String) updateList.get(BpmnMetaModelConstants.E_ATTR_TEXT);
		if (desc != null) {
			EList<EObject> listAttribute = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
			if(listAttribute.size() == 0 ){
				getModelController().createDocumentation(elementWrapper, desc);
			}
		}

		oldProperties = updateModel(updateList);
	}

	protected void undoAction() throws Throwable {
		updateModel(oldProperties);
	}
	
	@Override
	protected void redoAction() throws Throwable {
		updateModel(updateList);
	}
	
	protected Map<String, Object> updateModel(Map<String, Object> model) {
		Map<String, Object> props = new HashMap<String, Object>();
		String name = (String) model.get(BpmnMetaModelConstants.E_ATTR_ID);
		String label = (String) model.get(BpmnMetaModelConstants.E_ATTR_NAME);
		String desc = (String) model.get(BpmnMetaModelConstants.E_ATTR_TEXT);
		
		if(name == null && model.containsKey(BpmnMetaModelConstants.E_ATTR_NAME))
			name = "";
//
//		if(label == null && model.containsKey(BpmnMetaModelConstants.E_ATTR_LABEL))
//			label = "";
		
		if(desc == null && model.containsKey(BpmnMetaModelConstants.E_ATTR_TEXT))
			desc = "";

		TSEConnector node = getNodeOrGraph();

		if (name != null)
			node.setAttribute(BpmnUIConstants.NODE_ATTR_NAME, name);

		if (label != null) {
//			node.setAttribute(BpmnUIConstants.NODE_ATTR_LABEL, label);
		}

		if (desc != null) {
			node.setAttribute(BpmnUIConstants.NODE_ATTR_TASK_DESCRIPTION, desc);
			EList<EObject> listAttribute = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
			EObject docObj = listAttribute.get(0);
			props.putAll(getModelController().updateEmfModel(
						EObjectWrapper.wrap(docObj), model));
		}

		props.putAll(getModelController().updateEmfModel(elementWrapper, model));

		return props;
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
		return connector;
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
}
