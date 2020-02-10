package com.tibco.cep.dashboard.psvr.user;

import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import com.tibco.cep.dashboard.logging.LoggingService;
import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.ManagementException;
import com.tibco.cep.dashboard.management.ManagementUtils;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.management.Service;
import com.tibco.cep.dashboard.security.SecurityClient;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.dashboard.security.SecurityTokenListener;
import com.tibco.cep.kernel.service.logging.Level;

/**
 * @author apatil
 *
 */
public class UserService extends Service {
    
    private SecurityClient securityClient;

	private SecurityTokenListenerImpl listener;
	
    public UserService() {
		super("user", "User Service");
	}
	
	@Override
	protected void doInit() throws ManagementException {
		TokenRoleProfile.LOGGER = LoggingService.getChildLogger(logger, "tokenroleprofile");
		TokenRoleProfile.EXCEPTION_HANDLER = new ExceptionHandler(TokenRoleProfile.LOGGER);
		TokenRoleProfile.MESSAGE_GENERATOR = new MessageGenerator(name, exceptionHandler);
		TokenRoleProfile.PROCESSOR = new TokenRoleProfileProcessor(TokenRoleProfile.LOGGER,TokenRoleProfile.EXCEPTION_HANDLER,TokenRoleProfile.MESSAGE_GENERATOR);
	} 	

	@Override
	protected void doStart() throws ManagementException {
		try {
			securityClient = (SecurityClient) ManagementUtils.getContext().lookup("security");
		} catch (NamingException e) {
			throw new ManagementException(e);
		}
		listener = new SecurityTokenListenerImpl();
		securityClient.addSecurityTokenListener(listener);
	}
	
	@Override
	protected boolean doStop() {
		List<SecurityToken> tokens = new LinkedList<SecurityToken>(TokenRoleProfile.tokenToInstanceMap.keySet());
		for (SecurityToken securityToken : tokens) {
			TokenRoleProfile[] profiles = TokenRoleProfile.getInstances(securityToken);
			for (TokenRoleProfile profile : profiles) {
				profile.destroy();
			}
			TokenRoleProfile.tokenToInstanceMap.remove(securityToken);
		}
		tokens.clear();
		if (listener != null && securityClient != null){
			securityClient.removeSecurityTokenListener(listener);
		}
		listener = null;
		securityClient = null;
		return true;
	}

	class SecurityTokenListenerImpl implements SecurityTokenListener {

		@Override
		public void tokenCreated(SecurityToken token) {
			//do nothing;
		}

		@Override
		public void tokenDeleted(SecurityToken token) {
			TokenRoleProfile[] profiles = TokenRoleProfile.getInstances(token);
			if (profiles != null) {
	    		if (logger.isEnabledFor(Level.DEBUG) == true){
	    			logger.log(Level.DEBUG,"Shutting down "+profiles.length+" profile(s) for "+token);
	    		}
				for (TokenRoleProfile tokenProfile : profiles) {
					tokenProfile.destroy();
				}
				TokenRoleProfile.tokenToInstanceMap.remove(token);
			}
		}

		@Override
		public void tokenExpired(SecurityToken token) {
			tokenDeleted(token);
		}

	}

  

}