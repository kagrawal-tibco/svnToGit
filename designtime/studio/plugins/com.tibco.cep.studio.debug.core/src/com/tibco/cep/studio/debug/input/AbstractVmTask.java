package com.tibco.cep.studio.debug.input;

import org.eclipse.debug.core.model.IDebugTarget;

abstract public class AbstractVmTask implements VmTask {
	
	IDebugTarget debugTarget;

	public AbstractVmTask(IDebugTarget target) {
		debugTarget = target;
	}

	@Override
	public IDebugTarget getDebugTarget() {
		return debugTarget;
	}
	
}
