package com.tibco.cep.studio.ui.views;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;

import com.tibco.cep.studio.ui.StudioUIPlugin;

public class StatusInfo implements IStatus {
	
	public static final IStatus STATUS_OK= new StatusInfo();
	
	private String msg;
	private int sev;

	public StatusInfo() {
		this(null,OK);
	}

	/**
	 * @param msg
	 * @param sev
	 */
	public StatusInfo(String msg, int sev) {
		this.msg = msg;
		this.sev = sev;
	}



	@Override
	public IStatus[] getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Throwable getException() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMessage() {
		return msg;
	}

	@Override
	public String getPlugin() {
		return StudioUIPlugin.PLUGIN_ID;
	}

	@Override
	public int getSeverity() {
		return sev;
	}
	
	/**
	 * 	
	 * @param errorMessage
	 */
	public void setError(String errorMessage) {
		Assert.isNotNull(errorMessage);
		msg = errorMessage;
		sev = IStatus.ERROR;
	}

	/**
	 * 		
	 * @param warningMessage
	 */
	public void setWarning(String warningMessage) {
		Assert.isNotNull(warningMessage);
		msg = warningMessage;
		sev = IStatus.WARNING;
	}

	/**
	 * 	
	 * @param infoMessage
	 */
	public void setInfo(String infoMessage) {
		Assert.isNotNull(infoMessage);
		msg = infoMessage;
		sev = IStatus.INFO;
	}	

	@Override
	public boolean isMultiStatus() {
		return false;
	}

	@Override
	public boolean isOK() {
		return sev == IStatus.OK;
	}
	
	
	public boolean isWarning() {
		return sev == IStatus.WARNING;
	}

	/**
	 *  Returns if the status' severity is INFO.
	 */	
	public boolean isInfo() {
		return sev == IStatus.INFO;
	}	

	/**
	 *  Returns if the status' severity is ERROR.
	 */	
	public boolean isError() {
		return sev == IStatus.ERROR;
	}
	
	

	@Override
	public boolean matches(int severityMask) {
		return (sev & severityMask) != 0;
	}

}
