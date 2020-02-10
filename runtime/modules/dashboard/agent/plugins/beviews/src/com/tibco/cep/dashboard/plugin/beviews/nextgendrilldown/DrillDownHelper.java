package com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown;

import java.util.Properties;

import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.EntityVisualizerProvider;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Logger;

public abstract class DrillDownHelper {

	protected Logger logger;

	protected ExceptionHandler exceptionHandler;

	protected MessageGenerator messageGenerator;

	protected Properties properties;

	protected EntityVisualizerProvider entityVisualizerProvider;

	protected DrillDownHelper(Logger logger, Properties properties, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		super();
		this.logger = LoggingService.getChildLogger(logger, "drilldown");
		this.properties = properties;
		this.exceptionHandler = new ExceptionHandler(this.logger);
		this.messageGenerator = messageGenerator;
		entityVisualizerProvider = EntityVisualizerProvider.getInstance();
	}

	protected MessageGeneratorArgs getMessageGeneratorArgs(DrillDownRequest request) {
		SecurityToken token = request.getToken();
		String rawPath = request.getPath() == null ? "" : request.getPath().getRawPath();
		return new MessageGeneratorArgs(token.toString(), token.getUserID(), token.getPreferredPrincipal(), token.getPrincipals(), null, rawPath);
	}

	protected MessageGeneratorArgs getMessageGeneratorArgs(DrillDownRequest request, Throwable t) {
		SecurityToken token = request.getToken();
		String rawPath = request.getPath() == null ? "" : request.getPath().getRawPath();
		return new MessageGeneratorArgs(token.toString(), token.getUserID(), token.getPreferredPrincipal(), token.getPrincipals(), t, rawPath);
	}

	protected MessageGeneratorArgs getMessageGeneratorArgs(DrillDownRequest request, Object... args) {
		SecurityToken token = request.getToken();
		Object[] extendedArgs = new Object[args.length + 1];
		extendedArgs[0] = request.getPath() == null ? "" : request.getPath().getRawPath();
		System.arraycopy(args, 0, extendedArgs, 1, args.length);
		return new MessageGeneratorArgs(token.toString(), token.getUserID(), token.getPreferredPrincipal(), token.getPrincipals(), null, extendedArgs);
	}

	protected MessageGeneratorArgs getMessageGeneratorArgs(DrillDownRequest request, Throwable t, Object... args) {
		SecurityToken token = request.getToken();
		Object[] extendedArgs = new Object[args.length + 1];
		extendedArgs[0] = request.getPath() == null ? "" : request.getPath().getRawPath();
		System.arraycopy(args, 0, extendedArgs, 1, args.length);
		return new MessageGeneratorArgs(token.toString(), token.getUserID(), token.getPreferredPrincipal(), token.getPrincipals(), t, extendedArgs);
	}

}