package com.tibco.cep.bpmn.model.designtime.ontology;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.designtime.model.process.TaskModel;
import com.tibco.cep.designtime.model.process.TransitionModel;

public class TaskModelAdapter extends BaseModelAdapter implements TaskModel {


	public TaskModelAdapter(ProcessModel processModel, EObject element,
			Object[] params) {
		super(processModel,element,params);
	}

	@Override
	public TransitionModel[] getOutgoingTransitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransitionModel[] getIncomingTransitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInputMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOutputMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T cast(Class<T> typeOf) {
		// TODO Auto-generated method stub
		return null;
	}

}
