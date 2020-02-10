package com.tibco.cep.dashboard.psvr.biz.dashboard;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.biz.BaseAuthenticatedAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.RequestProcessingException;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.security.SecurityToken;

public class SetCurrentPageAction extends BaseAuthenticatedAction {

	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		try {
			String pageid = request.getParameter("pageid");
			if (StringUtil.isEmptyOrBlank(pageid) == true){
				return handleError(getMessage("setcurrentpage.invalid.pageid"));
			}
			ViewsConfigHelper viewsConfigHelper = getTokenRoleProfile(token).getViewsConfigHelper();
			MALPage page = viewsConfigHelper.getPageByID(pageid);
			if (page == null){
				return handleError(getMessage("setcurrentpage.nonexistent.pageid",getMessageGeneratorArgs(token,pageid)));
			}
			viewsConfigHelper.setCurrentPageID(pageid);
			return handleSuccess("");
		} catch (RequestProcessingException e) {
			return handleError(getMessage("bizaction.tokenprofile.retrieval.failure", getMessageGeneratorArgs(token, e)), e);
		}
	}

}
