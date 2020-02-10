package com.tibco.cep.dashboard.psvr.biz;

import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.ManagementUtils;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.security.InvalidTokenException;
import com.tibco.cep.dashboard.security.SecurityClient;
import com.tibco.cep.dashboard.security.SecurityToken;

public abstract class BaseAuthenticatedAction extends BaseAction {
	
	protected SecurityClient securityClient;
	
	protected void init(String command, Properties properties, Map<String, String> configuration) throws Exception{
		super.init(command, properties, configuration);
		securityClient = (SecurityClient) ManagementUtils.getContext().lookup("security");
	}
	
	@Override
	protected final BizResponse doExecute(BizRequest request) {
		String stoken = request.getParameter(KnownParameterNames.TOKEN);
		if (StringUtil.isEmptyOrBlank(stoken) == true){
			return handleError(getMessage("bizaction.invalid.token"));
		}
		try {
			SecurityToken token = securityClient.convert(stoken);
			token.touched();
			return doAuthenticatedExecute(token, request);
		} catch (InvalidTokenException e) {
			return handleError(getMessage("bizaction.nonexistent.token", new MessageGeneratorArgs(e,stoken)),e);
		}
	}   
	
	protected abstract BizResponse doAuthenticatedExecute(SecurityToken token,BizRequest request);
	
    protected String getLoggableUserString(SecurityToken token){
        return "User[token="+token+",id="+token.getUserID()+",role="+token.getPreferredPrincipal()+"]";
    }	
    
    protected MessageGeneratorArgs getMessageGeneratorArgs(SecurityToken token){
    	return new MessageGeneratorArgs(token.toString(),token.getUserID(),token.getPreferredPrincipal(),token.getPrincipals(),null,(Object)null);
    }
    
    protected MessageGeneratorArgs getMessageGeneratorArgs(SecurityToken token,Throwable t){
    	return new MessageGeneratorArgs(token.toString(),token.getUserID(),token.getPreferredPrincipal(),token.getPrincipals(),t,(Object)null);
    }  
    
    protected MessageGeneratorArgs getMessageGeneratorArgs(SecurityToken token,Object... args){
    	return new MessageGeneratorArgs(token.toString(),token.getUserID(),token.getPreferredPrincipal(),token.getPrincipals(),null,args);
    }    
    
    protected MessageGeneratorArgs getMessageGeneratorArgs(SecurityToken token,Throwable t,Object... args){
    	return new MessageGeneratorArgs(token.toString(),token.getUserID(),token.getPreferredPrincipal(),token.getPrincipals(),t,args);
    }
    
    protected TokenRoleProfile getTokenRoleProfile(SecurityToken token) throws RequestProcessingException {
    	try {
			return TokenRoleProfile.getInstance(token);
		} catch (MALException e) {
			String message = getMessage("bizaction.view.loading.failure", getMessageGeneratorArgs(token));
			throw new RequestProcessingException(message,e);
		} catch (ElementNotFoundException e) {
			String message = getMessage("bizaction.view.notfound.failure", getMessageGeneratorArgs(token));
			throw new RequestProcessingException(message,e);
		}
    }

}