package com.tibco.cep.dashboard.psvr.biz.security;

import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Map;
import java.util.Properties;

import javax.security.auth.login.AccountNotFoundException;
import javax.security.auth.login.LoginException;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.ManagementUtils;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.psvr.biz.BaseAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.security.SecurityClient;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.net.mime.Base64Codec;

public class SignInAction extends BaseAction {

	private SecurityClient securityClient;
	private SetRoleAction setRoleActionDelegate;
	
	public SignInAction() {
		super();
		setRoleActionDelegate = new SetRoleAction();
	}

	@Override
	protected void init(String command, Properties properties, Map<String, String> configuration) throws Exception {
		super.init(command, properties, configuration);
		securityClient = (SecurityClient) ManagementUtils.getContext().lookup("security");
		setRoleActionDelegate.init(command, null, null);
	}

	@Override
	protected BizResponse doExecute(BizRequest request) {
		String username = request.getParameter("username");
		if (StringUtil.isEmptyOrBlank(username) == true){
			return handleError(getMessage("security.invalid.username"));
		}
		String password = request.getParameter("password");
		if (StringUtil.isEmptyOrBlank(password) == true){
			password = request.getParameter("rawpassword");
			if (StringUtil.isEmptyOrBlank(password) == false){
				password = Base64Codec.encodeBase64(password.getBytes());
			}
		}
		try {
			SecurityToken token = securityClient.login(username, password);
			Principal[] principals = token.getPrincipals();
			String role = request.getParameter("role");
			//if the user has supplied the role, try to set it
			if (StringUtil.isEmptyOrBlank(role) == false){
				BizResponse response = setRoleActionDelegate.doAuthenticatedExecute(token, request);
				if (response.getStatus() != BizResponse.SUCCESS_STATUS){
					return response;
				}
			}
			//we have only one principal, let's set as preferred role
			else if (principals.length == 1){
				request.addParameter("role", principals[0].getName());
				BizResponse response = setRoleActionDelegate.doAuthenticatedExecute(token, request);
				if (response.getStatus() != BizResponse.SUCCESS_STATUS){
					return response;
				}
			}
			return handleSuccess(token.toString());
		} catch (AccountNotFoundException e) {
			return handleError(getMessage("security.account.notfound",new MessageGeneratorArgs(e,username)),e);
		} catch (LoginException e) {
			return handleError(getMessage("security.general.failure",new MessageGeneratorArgs(e,username)),e);
		} catch (GeneralSecurityException e) {
			return handleError(getMessage("security.general.failure",new MessageGeneratorArgs(e,username)),e);
		}
	}

}
