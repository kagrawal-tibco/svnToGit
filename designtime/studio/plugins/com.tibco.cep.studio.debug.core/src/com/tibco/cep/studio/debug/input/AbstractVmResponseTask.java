package com.tibco.cep.studio.debug.input;

import org.eclipse.debug.core.model.IDebugTarget;


public abstract class AbstractVmResponseTask extends AbstractVmTask implements VmResponseTask {
	
	Object response;

	public AbstractVmResponseTask(IDebugTarget target) {
		super(target);
	}
	public Object getResponse() {
		return response;
	}
	
	@Override
	public boolean hasResponse() {
		return response != null;
	}

	
	public void setResponse(Object response) {
		this.response = response;
	}

}
