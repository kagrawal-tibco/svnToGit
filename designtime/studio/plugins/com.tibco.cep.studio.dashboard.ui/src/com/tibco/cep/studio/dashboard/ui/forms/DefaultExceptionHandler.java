package com.tibco.cep.studio.dashboard.ui.forms;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;

import com.tibco.cep.studio.dashboard.core.DashboardCorePlugIn;

public class DefaultExceptionHandler implements ExceptionHandler {
	
	private String pluginId;
	private Bundle bundle;
	private ILog log;

	public DefaultExceptionHandler(){
		this(DashboardCorePlugIn.PLUGIN_ID);
	}
	
	public DefaultExceptionHandler(String pluginId){
		this.pluginId = pluginId;
		bundle = Platform.getBundle(pluginId);
		log = Platform.getLog(bundle);
	}
	
	@Override
	public String getPluginId() {
		return pluginId;
	}
	
	@Override
	public IStatus createStatus(int severity, String message, Throwable exception) {
		return new Status(severity, pluginId, message, exception);
	}

	@Override
	public void log(IStatus status) {
		log.log(status);
	}
	
	@Override
	public void logAndAlert(String title, IStatus status) {
		log.log(status);
		switch (status.getSeverity()){
			case IStatus.OK:
			case IStatus.INFO:
				MessageDialog.openInformation(Display.getCurrent().getActiveShell(), title, status.getMessage());
				break;
			case IStatus.ERROR:
				MessageDialog.openError(Display.getCurrent().getActiveShell(), title, status.getMessage());
				break;
			case IStatus.WARNING:
				MessageDialog.openWarning(Display.getCurrent().getActiveShell(), title, status.getMessage());
				break;
		}
	}



}
