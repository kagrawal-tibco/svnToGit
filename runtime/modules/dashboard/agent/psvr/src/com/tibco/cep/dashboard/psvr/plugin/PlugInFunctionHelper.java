package com.tibco.cep.dashboard.psvr.plugin;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.kernel.service.logging.Logger;

public abstract class PlugInFunctionHelper {

	protected PlugIn plugIn;
	protected Logger logger;
	protected ExceptionHandler exceptionHandler;
	protected MessageGenerator messageGenerator;

	protected PlugInFunctionHelper(PlugIn plugIn) {
		this.plugIn = plugIn;
		this.logger = this.plugIn.getLogger();
		this.exceptionHandler = this.plugIn.getExceptionHandler();
		this.messageGenerator = this.plugIn.getMessageGenerator();
	}

	public String getDescriptiveName() {
		return plugIn.getDescriptiveName();
	}

}