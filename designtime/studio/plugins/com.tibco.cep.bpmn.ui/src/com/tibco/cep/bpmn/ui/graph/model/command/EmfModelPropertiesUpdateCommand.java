package com.tibco.cep.bpmn.ui.graph.model.command;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.graph.model.controller.ModelController;
import com.tomsawyer.interactive.command.TSCommand;

/**
 * 
 * @author majha
 *
 */
public class EmfModelPropertiesUpdateCommand extends TSCommand {
	private static final long serialVersionUID = -7439560190865294671L;
	
	protected Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> updateList;
	private ModelController modelController;
	protected Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> oldProperties;

	public EmfModelPropertiesUpdateCommand(ModelController controller,Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> updateList) {
		this.modelController = controller;
		this.updateList = updateList;
	}
	
	protected void doAction() throws Throwable {
		oldProperties = updateModel(updateList);
	}

	protected void undoAction() throws Throwable {
		updateModel(oldProperties);
	}
	
	@Override
	protected void redoAction() throws Throwable {
		updateModel(updateList);
	}
	

	
	protected Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> updateModel(Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> model) {
		Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> props = new HashMap<EObjectWrapper<EClass,EObject>, Map<String,Object>>();
		Set<EObjectWrapper<EClass, EObject>> keySet = model.keySet();
		for (EObjectWrapper<EClass, EObject> eObjectWrapper : keySet) {
			Map<String, Object> map = model.get(eObjectWrapper);
			Map<String, Object> updateEmfModel = modelController.updateEmfModel(eObjectWrapper,map);
			props.put(eObjectWrapper, updateEmfModel);
		}

		return props;
	}
	


}
