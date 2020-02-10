package com.tibco.cep.dashboard.psvr.biz.security;

import java.security.Principal;

import com.tibco.cep.dashboard.psvr.biz.BaseAuthenticatedAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.security.SecurityToken;

public class VerifyTokenAction extends BaseAuthenticatedAction {

	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
        Principal[] principals = token.getPrincipals();
        Principal principal = token.getPreferredPrincipal();
        StringBuilder sb = new StringBuilder("<tokeninfo>");
        sb.append("<username>"+token.getUserID()+"</username>");
        if (principal != null) {
            sb.append("<preferredrole>" + principal.getName() + "</preferredrole>");
        }
        sb.append("<roles>");
        if (principals != null) {
        	for (int i = 0; i < principals.length; ++i) {
                String principalName = principals[i].getName();
                sb.append("<role name=\"" + principalName + "\"/>");
            }
        }            
        sb.append("</roles>");
        sb.append("</tokeninfo>");
        return handleSuccess(sb.toString());
	}

}