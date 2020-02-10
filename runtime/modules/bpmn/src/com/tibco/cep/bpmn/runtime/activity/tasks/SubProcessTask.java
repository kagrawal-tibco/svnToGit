package com.tibco.cep.bpmn.runtime.activity.tasks;

import com.tibco.cep.designtime.model.process.ProcessModel;

/**
 * @author pdhar
 * 
 */
public class SubProcessTask extends CallActivityTask {



	public SubProcessTask() {
		// TODO Auto-generated constructor stub
	}
	
	
	protected void initCalledProcessModel() {
		ProcessModel pmodel = getInitContext().getProcessModel();
		calledProcessModel = pmodel.getSubProcessById(getName());  //Get the subprocess of this name.

	}
	
	
	

	





	



}
