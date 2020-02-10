package com.tibco.cep.studio.wizard.as.services.spi;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.tibco.cep.studio.wizard.as.ASPlugin;
import com.tibco.cep.studio.wizard.as.internal.utils.Messages;

abstract public class AStageParticipant implements IStageParticipant {

	private IStatus status = new Status(IStatus.ERROR, ASPlugin._PLUGIN_ID, Messages.getString("Status.ori_error_status")); //$NON-NLS-1$

	@Override
	public void setStatus(IStatus status) {
		this.status = status;
	}

	@Override
	public IStatus getStatus() {
		return status;
	}

}
