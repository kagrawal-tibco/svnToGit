package com.tibco.cep.dashboard.psvr.biz.bootstrap;

import com.tibco.cep.dashboard.cep_dashboardVersion;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.management.MessageGeneratorArgs;
import com.tibco.cep.dashboard.psvr.biz.BaseAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.mal.MALGlobalElementsCache;
import com.tibco.cep.dashboard.psvr.mal.model.MALLogin;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.LoginCustomization;

public class GetLoginCustomization extends BaseAction {

	@Override
	protected BizResponse doExecute(BizRequest request) {
		try {
			String title = null;
			String imageURL = null;
			MALLogin login = MALGlobalElementsCache.getInstance().getLogin();
			if (login != null) {
				imageURL = login.getImageURL();
				title = login.getTitle();
			}
			LoginCustomization customization = new LoginCustomization();
			if (StringUtil.isEmptyOrBlank(title) == true) {
				title = "BEViews - " + cep_dashboardVersion.version;
			}
			customization.setTitle(title);
			if (StringUtil.isEmptyOrBlank(imageURL) == false) {
				//Modified By Anand to fix 10824 - 01/21/2011
				customization.setImageURL("../images" + imageURL);
			}
			String xml = OGLMarshaller.getInstance().marshall(null, customization);
			return handleSuccess(xml);
		} catch (OGLException e) {
			return handleError(getMessage("bizaction.marshalling.failure", new MessageGeneratorArgs(e, "login customization")), e);
		}
	}

}
