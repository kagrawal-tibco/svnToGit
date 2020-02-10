package com.tibco.cep.studio.dashboard.ui.forms;

import org.eclipse.core.runtime.IStatus;

public interface ExceptionHandler {
	
	public String getPluginId();
	
	public IStatus createStatus(int severity, String message, Throwable exception);
	
	public void log(IStatus status);
	
	public void logAndAlert(String title, IStatus status);


}
