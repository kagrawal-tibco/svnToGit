package com.tibco.cep.studio.ui.property.page.classpath;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;

import com.tibco.cep.studio.ui.StudioUIPlugin;

public class VariableStatusInfo implements IStatus {

	public static final IStatus OK_STATUS= new VariableStatusInfo();

	private String fStatusMessage;
	private int fSeverity;

	public VariableStatusInfo() {
		this(OK, null);
	}

	public VariableStatusInfo(int severity, String message) {
		fStatusMessage= message;
		fSeverity= severity;
	}

	public boolean isOK() {
		return fSeverity == IStatus.OK;
	}

	public boolean isWarning() {
		return fSeverity == IStatus.WARNING;
	}

	public boolean isInfo() {
		return fSeverity == IStatus.INFO;
	}

	public boolean isError() {
		return fSeverity == IStatus.ERROR;
	}

	public String getMessage() {
		return fStatusMessage;
	}

	public void setError(String errorMessage) {
		Assert.isNotNull(errorMessage);
		fStatusMessage= errorMessage;
		fSeverity= IStatus.ERROR;
	}

	public void setWarning(String warningMessage) {
		Assert.isNotNull(warningMessage);
		fStatusMessage= warningMessage;
		fSeverity= IStatus.WARNING;
	}

	public void setInfo(String infoMessage) {
		Assert.isNotNull(infoMessage);
		fStatusMessage= infoMessage;
		fSeverity= IStatus.INFO;
	}

	public void setOK() {
		fStatusMessage= null;
		fSeverity= IStatus.OK;
	}

	/*
	 * @see IStatus#matches(int)
	 */
	public boolean matches(int severityMask) {
		return (fSeverity & severityMask) != 0;
	}

	/**
	 * Returns always <code>false</code>.
	 * @see IStatus#isMultiStatus()
	 */
	public boolean isMultiStatus() {
		return false;
	}

	/*
	 * @see IStatus#getSeverity()
	 */
	public int getSeverity() {
		return fSeverity;
	}

	/*
	 * @see IStatus#getPlugin()
	 */
	public String getPlugin() {
		return StudioUIPlugin.PLUGIN_ID;
	}

	public Throwable getException() {
		return null;
	}

	public int getCode() {
		return fSeverity;
	}

	public IStatus[] getChildren() {
		return new IStatus[0];
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("StatusInfo "); //$NON-NLS-1$
		if (fSeverity == OK) {
			buf.append("OK"); //$NON-NLS-1$
		} else if (fSeverity == ERROR) {
			buf.append("ERROR"); //$NON-NLS-1$
		} else if (fSeverity == WARNING) {
			buf.append("WARNING"); //$NON-NLS-1$
		} else if (fSeverity == INFO) {
			buf.append("INFO"); //$NON-NLS-1$
		} else {
			buf.append("severity="); //$NON-NLS-1$
			buf.append(fSeverity);
		}
		buf.append(": "); //$NON-NLS-1$
		buf.append(fStatusMessage);
		return buf.toString();
	}
}
