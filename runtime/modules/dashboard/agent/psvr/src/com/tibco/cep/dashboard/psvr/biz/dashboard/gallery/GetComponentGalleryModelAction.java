package com.tibco.cep.dashboard.psvr.biz.dashboard.gallery;

import com.tibco.cep.dashboard.psvr.biz.BaseAuthenticatedAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.RequestProcessingException;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.security.SecurityToken;

/**
 * @author apatil
 *
 */
public class GetComponentGalleryModelAction extends BaseAuthenticatedAction {
	
	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		try {
			TokenRoleProfile profile = getTokenRoleProfile(token);
			String galleryXML = profile.getComponentGalleryGenerator().generate();
			return handleSuccess(galleryXML);
		} catch (RequestProcessingException e) {
			return handleError(getMessage("bizaction.tokenprofile.retrieval.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (OGLException e) {
			return handleError(getMessage("bizaction.marshalling.failure", getMessageGeneratorArgs(token, e, "component gallery")), e);
		}
	}	

}