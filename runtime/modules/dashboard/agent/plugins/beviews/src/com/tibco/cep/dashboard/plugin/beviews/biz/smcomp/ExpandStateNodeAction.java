package com.tibco.cep.dashboard.plugin.beviews.biz.smcomp;

import java.io.IOException;
import java.util.Iterator;

import com.tibco.cep.dashboard.psvr.biz.BaseAuthenticatedAction;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLMarshaller;
import com.tibco.cep.dashboard.psvr.ogl.OGLUnmarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.ProcessModel;
import com.tibco.cep.dashboard.psvr.ogl.model.VisualizationModel;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationEngine;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.security.SecurityToken;


public class ExpandStateNodeAction extends BaseAuthenticatedAction {

	@Override
	protected BizResponse doAuthenticatedExecute(SecurityToken token, BizRequest request) {
		ProcessModel pModel = null;
		String componentid = "";
		String modelXML = "";
		PresentationContext ctx = null;
		try{
			modelXML = request.getParameter("processmodel");
			pModel = (ProcessModel) OGLUnmarshaller.getInstance().unmarshall(ProcessModel.class, modelXML);
			componentid = pModel.getComponentID();
			ctx  = new PresentationContext(token);
			MALComponent component = ctx.getViewsConfigHelper().getComponentById(componentid);
			if (component == null) {
				component = ctx.getTokenRoleProfile().getComponentGallery().searchComponent(componentid);
				if (component == null) {
					return handleError(getMessage("expandstatenode.nonexistent.componentid", getMessageGeneratorArgs(token, componentid)));
				}
			}
			for (Iterator<String> paramIter = request.getParameterNames(); paramIter.hasNext(); ) {
				String paramName = paramIter.next();
				if (paramName.equals("processmodel")) {
					ctx.addAttribute(paramName, pModel);
				}
				else {
					ctx.addAttribute(paramName, request.getParameterValues(paramName));
				}
			}
			VisualizationModel model = VisualizationEngine.getInstance().getComponentVisualizationModelWithConfig(component, null, ctx);
			return handleSuccess(OGLMarshaller.getInstance().marshall(token, model));
		} catch (DataException e) {
			return handleError(getMessage("expandstatenode.config.generation.failure", getMessageGeneratorArgs(token, e, componentid)), e);
		} catch (MALException e) {
			return handleError(getMessage("bizaction.element.retrieval.failure", getMessageGeneratorArgs(token, e, componentid)), e);
		} catch (ElementNotFoundException e) {
			return handleError(getMessage("bizaction.element.notfound.failure", getMessageGeneratorArgs(token, e, componentid)), e);
		} catch (VisualizationException e) {
			return handleError(getMessage("expandstatenode.config.generation.failure", getMessageGeneratorArgs(token, e, componentid)), e);
		} catch (IOException e) {
			return handleError(getMessage("expandstatenode.config.generation.failure", getMessageGeneratorArgs(token, e, componentid)), e);
		} catch (PluginException e) {
			return handleError(getMessage("expandstatenode.config.generation.failure", getMessageGeneratorArgs(token, e, componentid)), e);
		} catch (OGLException e) {
			return handleError(getMessage("bizaction.marshalling.failure", getMessageGeneratorArgs(token, e, "component config")), e);
		} finally {
			if (ctx != null) {
				ctx.close();
			}
		}
	}

}
