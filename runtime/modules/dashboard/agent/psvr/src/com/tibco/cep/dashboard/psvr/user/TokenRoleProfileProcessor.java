package com.tibco.cep.dashboard.psvr.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALTransactionException;
import com.tibco.cep.dashboard.psvr.plugin.Builder;
import com.tibco.cep.dashboard.psvr.plugin.BuilderResult;
import com.tibco.cep.dashboard.psvr.plugin.PlugInUtils;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

public class TokenRoleProfileProcessor {

	protected Logger logger;

	protected ExceptionHandler exceptionHandler;

	protected MessageGenerator messageGenerator;

	TokenRoleProfileProcessor(Logger logger, ExceptionHandler exceptionHandler, MessageGenerator messageGenerator) {
		super();
		this.logger = logger;
		this.exceptionHandler = exceptionHandler;
		this.messageGenerator = messageGenerator;
	}

	void process(SecurityToken token, TokenRoleProfile roleProfile) {
		Collection<Builder> builders = PlugInUtils.getBuilders();
		try {
			roleProfile.getMALSession().beginTransaction();
		} catch (MALTransactionException e) {
			throw new RuntimeException(e);
		}
		HashMap<BuilderResult.SEVERITY, List<BuilderResult>> results = new HashMap<BuilderResult.SEVERITY, List<BuilderResult>>();
		for (Builder builder : builders) {
			try {
				if (logger.isEnabledFor(Level.DEBUG) == true){
					logger.log(Level.DEBUG,"Triggering a build of profile for "+token+"[userid="+token.getUserID()+",role="+token.getPreferredPrincipal()+"] using "+builder.getDescriptiveName());
				}
				List<BuilderResult> localresults = builder.build(roleProfile);
				for (BuilderResult builderResult : localresults) {
					List<BuilderResult> specificResults = results.get(builderResult.getSeverity());
					if (specificResults == null) {
						specificResults = new LinkedList<BuilderResult>();
						results.put(builderResult.getSeverity(), specificResults);
					}
					specificResults.add(builderResult);
				}
			} catch (MALException e) {
				MessageGeneratorArgs args = new MessageGeneratorArgs(token.toString(), token.getUserID(), token.getPreferredPrincipal(), token.getPrincipals(), e, builder.getDescriptiveName());
				String message = messageGenerator.getMessage("tokenroleprofile.process.builder.failure", args);
				exceptionHandler.handleException(message, e, Level.WARN);
			}
		}
		try {
			roleProfile.getMALSession().getTransaction().rollBack();
		} catch (MALTransactionException e) {
			throw new RuntimeException(e);
		}
		roleProfile.getMALSession().getTransaction().close();
		if (results.containsKey(BuilderResult.SEVERITY.ERROR) == true){
			for (BuilderResult builderResult : results.get(BuilderResult.SEVERITY.ERROR)) {
				logger.log(Level.ERROR, builderResult.getMessage());
			}
			throw new RuntimeException("Role Profile for "+token+" failed processing, see log for more details");
		}
		if (results.containsKey(BuilderResult.SEVERITY.WARNING) == true){
			for (BuilderResult builderResult : results.get(BuilderResult.SEVERITY.WARNING)) {
				logger.log(Level.WARN, builderResult.getMessage());
			}
		}	
		if (results.containsKey(BuilderResult.SEVERITY.INFO) == true){
			for (BuilderResult builderResult : results.get(BuilderResult.SEVERITY.INFO)) {
				logger.log(Level.INFO, builderResult.getMessage());
			}
		}		
	}

}
