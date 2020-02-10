package com.tibco.cep.studio.core.index.jobs;

import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;

public class IndexJobResult<T extends EObject> extends Status {

	private T fJobData;
	
	public IndexJobResult(int severity, String pluginId, String message, T resultData) {
		super(severity, pluginId, message);
		this.fJobData = resultData;
		setException(null);
	}
	
	public IndexJobResult(int severity, String pluginId, String message, T resultData,Throwable t) {
		super(severity, pluginId, message);
		this.fJobData = resultData;
		setException(t);
	}

	public T getJobData() {
		return fJobData;
	}	
	

}
