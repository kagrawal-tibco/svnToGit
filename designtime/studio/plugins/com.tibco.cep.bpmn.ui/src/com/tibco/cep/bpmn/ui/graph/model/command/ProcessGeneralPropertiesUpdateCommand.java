package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelConstants;
import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnModelClass;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIConstants;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.graphicaldrawing.TSEGraph;
import com.tomsawyer.interactive.command.TSCommand;

/**
 * 
 * @author majha
 *
 */
public class ProcessGeneralPropertiesUpdateCommand extends TSCommand implements
		IGraphCommand<TSEGraph> {

	private static final long serialVersionUID = -49488066285301008L;
	private TSEGraph graph;
	private Map<String, Object> updateList;
	private ModelController modelController;
	private int commandType;

	protected EObjectWrapper<EClass, EObject> elementWrapper;
	protected Map<String, Object> oldProperties;

	public ProcessGeneralPropertiesUpdateCommand(int type, ModelController controller, TSEGraph graph,  Map<String, Object> updateList) {
		this.commandType = type;
		this.graph = graph;
		this.modelController = controller;
		this.updateList = updateList;
	}
	
	protected void doAction() throws Throwable {
		EObject userObject = (EObject) graph.getUserObject();
		elementWrapper = EObjectWrapper.wrap(userObject);
		
		String processType = (String) updateList
				.remove(BpmnMetaModelConstants.E_ATTR_PROCESS_TYPE);
		String desc = (String) updateList
				.get(BpmnMetaModelConstants.E_ATTR_TEXT);
		
		if (desc != null) {
			EList<EObject> listAttribute = elementWrapper.getListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);
			if(listAttribute.size() == 0 ){
				getModelController().createDocumentation(elementWrapper, desc);
			}
		}

		if (processType != null) {
			EEnumLiteral enumLiteral = null;
			if (processType.equals(BpmnUIConstants.PROCESS_TYPE_PUBLIC))
				enumLiteral = BpmnModelClass.ENUM_PROCESS_TYPE_PUBLIC;
			else
				enumLiteral = BpmnModelClass.ENUM_PROCESS_TYPE_EXECUTABLE;

			updateList.put(BpmnMetaModelConstants.E_ATTR_PROCESS_TYPE,
					enumLiteral);
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
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class type) {
		if(type == TSCommand.class) {
			return this;
		}   
		return null;
	}

	@Override
	public int getCommandType() {
		return this.commandType;
	}

	@Override
	public ModelController getModelController() {
		return this.modelController;
	}

	@Override
	public TSEGraph getNodeOrGraph() {
		return this.graph;
	}
	
	protected Map<String, Object> updateModel(Map<String, Object> model) {
		Map<String, Object> props = new HashMap<String, Object>();
		String name = (String) model
				.get(BpmnMetaModelConstants.E_ATTR_NAME);
				
		String desc = (String) model
				.get(BpmnMetaModelConstants.E_ATTR_TEXT);
		
		if(name == null && model.containsKey(BpmnMetaModelConstants.E_ATTR_NAME))
			name = "";
		
		if(desc == null && model.containsKey(BpmnMetaModelConstants.E_ATTR_TEXT))
			desc = "";
		
		if (name != null)
			graph.setAttribute(BpmnUIConstants.NODE_ATTR_NAME, name);

		if (desc != null) {
			graph
					.setAttribute(BpmnUIConstants.NODE_ATTR_TASK_DESCRIPTION,
							desc);
			EList<EObject> listAttribute = elementWrapper
					.getListAttribute(BpmnMetaModelConstants.E_ATTR_DOCUMENTATION);

			EObject docObj = listAttribute.get(0);
			props.putAll(getModelController().updateEmfModel(
					EObjectWrapper.wrap(docObj), model));
		}
		props.putAll(getModelController().updateEmfModel(elementWrapper,
				model));

		return props;
	}
	
	protected void finalize() throws Throwable {
		graph = null;
		modelController = null;
		updateList.clear();
		updateList = null;
		super.finalize();
	}

}
