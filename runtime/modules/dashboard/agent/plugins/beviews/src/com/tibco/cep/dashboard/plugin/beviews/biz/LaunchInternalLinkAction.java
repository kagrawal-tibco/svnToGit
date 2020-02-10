package com.tibco.cep.dashboard.plugin.beviews.biz;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.psvr.biz.BaseSessionedAction;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.MALReferenceResolver;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;

public class LaunchInternalLinkAction extends BaseSessionedAction {

	@Override
	protected BizResponse doSessionedExecute(SecurityToken token, BizSessionRequest request) {
		String componentid = request.getParameter("componentid");
		if (StringUtil.isEmptyOrBlank(componentid) == true){
			return handleError("launchinternallink.invalid.componentid");
		}
		String seriesid = request.getParameter("seriesid");
		if (StringUtil.isEmptyOrBlank(seriesid) == true){
			return handleError("launchinternallink.invalid.seriesid");
		}

		PresentationContext pCtx = null;
		try {
			pCtx = new PresentationContext(token);
		} catch (MALException e) {
			return handleError(getMessage("launchinternallink.viewsconfig.fetch.failure", getMessageGeneratorArgs(token, e)), e);
		} catch (ElementNotFoundException e) {
			return handleError(getMessage("launchinternallink.viewsconfig.notfound.failure", getMessageGeneratorArgs(token, e)), e);
		}

		TokenRoleProfile tokenRoleProfile = pCtx.getTokenRoleProfile();

		ViewsConfigHelper viewsConfigHelper = tokenRoleProfile.getViewsConfigHelper();
		MALComponent component = viewsConfigHelper.getComponentById(componentid);
		if (component == null){
			return handleError(getMessage("launchinternallink.nonexistent.component",getMessageGeneratorArgs(token,componentid)));
		}

		MALSeriesConfig matchingSeriesConfig = null;
		for (MALVisualization visualization : component.getVisualization()) {
			for (MALSeriesConfig seriesConfig : visualization.getSeriesConfig()) {
				if (seriesConfig.getId().equals(seriesid) == true){
					matchingSeriesConfig = seriesConfig;
					break;
				}
			}
		}
		if (matchingSeriesConfig == null){
			return handleError(getMessage("launchinternallink.nonexistent.series",getMessageGeneratorArgs(token,seriesid,component.toString())));
		}

		MALFieldMetaInfo[] queryFields = MALReferenceResolver.resolveFieldReferences(token, matchingSeriesConfig.getActionRule().getDrillableFields());
		if (queryFields == null || queryFields.length == 0){
			try {
				DataSourceHandler dataSourceHandler = pCtx.getDataSourceHandler(matchingSeriesConfig);
				MALSourceElement sourceElement = dataSourceHandler.getSourceElement();
				List<MALFieldMetaInfo> groupByFields = new LinkedList<MALFieldMetaInfo>();
				for (MALFieldMetaInfo field : sourceElement.getFields()) {
					if (field.isGroupBy() == true){
						groupByFields.add(field);
					}
				}
				queryFields = groupByFields.toArray(new MALFieldMetaInfo[groupByFields.size()]);
			} catch (VisualizationException e) {
				String message = getMessage("launchinternallink.datasourcehandler.retrieval.failure",getMessageGeneratorArgs(token,URIHelper.getURI(matchingSeriesConfig)));
				exceptionHandler.handleException(message, e, Level.WARN);
			}
		}

		if (queryFields == null || queryFields.length == 0){
			//PORT String tupleid = request.getParameter("tupid");
			//PORT create a drill down information object containing the source element & tupleid
		}
		else {
			//PORT create a drill down information object containing the source element & group by fields
		}

		BizSession session = request.getSession(true);
		//PORT attach the drill down information object to a session using sessionid ?

		//PORT export page loader as a property
		StringBuilder builder = new StringBuilder("pageloader.html?");
		appendParameterTo(builder,"ssid",session.getId());
		String type = request.getParameter("type");
		if (StringUtil.isEmptyOrBlank(type) == false){
			appendParameterTo(builder,"&type",type);
		}
		return handleSuccess(builder.toString());
	}

	private void appendParameterTo(StringBuilder buffer, String name, String value) {
		buffer.append(name);
		buffer.append("=");
		try {
			buffer.append(URLEncoder.encode(value, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			buffer.append(value);
		}
	}

}
