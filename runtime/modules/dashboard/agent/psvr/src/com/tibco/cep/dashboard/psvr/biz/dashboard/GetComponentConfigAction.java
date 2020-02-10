package com.tibco.cep.dashboard.psvr.biz.dashboard;

import java.io.IOException;
import java.util.Iterator;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.biz.BaseAuthenticatedAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.EditingPresentationContext;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationEngine;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.security.SecurityToken;

public class GetComponentConfigAction extends BaseAuthenticatedAction {

	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		String componentid = request.getParameter("componentid");
		if (StringUtil.isEmptyOrBlank(componentid) == true) {
			return handleError(getMessage("getcomponentconfig.invalid.componentid"));
		}
		PresentationContext ctx = null;
		MALComponent component = null;
		String mode = request.getParameter("mode");
		try {
			if ("edit".equals(mode) == true) {
				ctx = new EditingPresentationContext(token);
				component = ctx.getTokenRoleProfile().getTransaction().getComponent();
			} else {
				ctx = new PresentationContext(token, !("limited".equals(mode)));
				component = ctx.getViewsConfigHelper().getComponentById(componentid);
				if (component == null) {
					component = ctx.getTokenRoleProfile().getComponentGallery().searchComponent(componentid);
				}
			}
			if (component == null) {
				return handleError(getMessage("getcomponentconfig.nonexistent.componentid", getMessageGeneratorArgs(token, componentid)));
			}
			for (Iterator<String> paramIter = request.getParameterNames(); paramIter.hasNext(); ) {
				String paramName = paramIter.next();
				ctx.addAttribute(paramName, request.getParameterValues(paramName));
			}
			VisualizationModel compConfig = VisualizationEngine.getInstance().getComponentVisualizationModelWithConfig(component, null, ctx);
			return handleSuccess(OGLMarshaller.getInstance().marshall(token, compConfig));
		} catch (DataException e) {
			return handleError(getMessage("getcomponentconfig.config.generation.failure", getMessageGeneratorArgs(token, e, componentid)), e);
		} catch (MALException e) {
			return handleError(getMessage("bizaction.view.loading.failure", getMessageGeneratorArgs(token, e, componentid)), e);
		} catch (ElementNotFoundException e) {
			return handleError(getMessage("bizaction.view.notfound.failure", getMessageGeneratorArgs(token, e, componentid)), e);
		} catch (VisualizationException e) {
			return handleError(getMessage("getcomponentconfig.config.generation.failure", getMessageGeneratorArgs(token, e, componentid)), e);
		} catch (IOException e) {
			return handleError(getMessage("getcomponentconfig.config.generation.failure", getMessageGeneratorArgs(token, e, componentid)), e);
		} catch (PluginException e) {
			return handleError(getMessage("getcomponentconfig.config.generation.failure", getMessageGeneratorArgs(token, e, componentid)), e);
		} catch (OGLException e) {
			return handleError(getMessage("bizaction.marshalling.failure", getMessageGeneratorArgs(token, e, "component config")), e);
		} finally {
			if (ctx != null) {
				ctx.close();
			}
		}
	}

}
