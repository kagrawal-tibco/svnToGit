package com.tibco.cep.bpmn.model.designtime.ontology;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.model.process.ProcessModel;
import com.tibco.cep.designtime.model.process.TaskModel;
import com.tibco.cep.designtime.model.process.TransitionModel;

public class TransitionModelAdapter extends BaseModelAdapter implements TransitionModel {

	public TransitionModelAdapter(ProcessModel processModel, EObject element,
			Object[] params) {
		super(processModel,element,params);
	}

	@Override
	public <T> T cast(Class<T> typeOf) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskModel from() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaskModel to() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String condition() {
		// TODO Auto-generated method stub
		return null;
	}

}
