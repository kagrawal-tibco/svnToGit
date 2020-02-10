package com.tibco.cep.studio.wizard.as.services.spi;

import org.eclipse.core.runtime.IStatus;

import com.tibco.cep.studio.wizard.as.commons.utils.IContext;

public interface IStageParticipant {

	/**
	 * 
	 * @param context
	 * @return if continue
	 */
	boolean participate(int stage, IContext context);

	void setStatus(IStatus status);

	IStatus getStatus();

}
