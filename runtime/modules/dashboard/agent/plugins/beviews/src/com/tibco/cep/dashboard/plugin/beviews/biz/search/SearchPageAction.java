package com.tibco.cep.dashboard.plugin.beviews.biz.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.biz.BaseSessionCheckerAction;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.runtime.ComponentContentProvider;
import com.tibco.cep.dashboard.plugin.beviews.runtime.ComponentContentProviderFactory;
import com.tibco.cep.dashboard.plugin.beviews.runtime.VelocityViewHelper;
import com.tibco.cep.dashboard.plugin.beviews.search.BizSessionSearchStore;
import com.tibco.cep.dashboard.plugin.beviews.search.IncompatibleQueryException;
import com.tibco.cep.dashboard.psvr.biz.BizProperties;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.KnownParameterNames;
import com.tibco.cep.dashboard.psvr.biz.RequestProcessingException;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.MALReferenceResolver;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.managers.MALQueryManagerComponentManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALSearchPageManager;
import com.tibco.cep.dashboard.psvr.mal.managers.MALSearchViewComponentManager;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALPanel;
import com.tibco.cep.dashboard.psvr.mal.model.MALPartition;
import com.tibco.cep.dashboard.psvr.mal.model.MALQueryManagerComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.plugin.PluginException;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.security.SecurityToken;

public class SearchPageAction extends BaseSessionCheckerAction {

	private Boolean enableSessionKeeping;

	@Override
	protected void init(String command, Properties properties, Map<String, String> configuration) throws Exception {
		super.init(command, properties, configuration);
		enableSessionKeeping = (Boolean)BizProperties.ENABLE_SESSION_KEEPING.getValue(properties);
	}

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		try {
			PresentationContext pCtx = new PresentationContext(token);
			TokenRoleProfile profile = pCtx.getTokenRoleProfile();
			MALPage searchPage = profile.getViewsConfigHelper().getPageByType(MALSearchPageManager.DEFINITION_TYPE);
			if (searchPage == null) {
				// we have no search page, this should not occur
				return VelocityViewHelper.getInstance().prepareRespone(request, "nosearchpage");
			}
			// we have a search page
			MALPanel queryManagerPnl = null;
			MALPanel drillDownPnl = null;
			for (MALPartition partition : searchPage.getPartition()) {
				for (MALPanel panel : partition.getPanel()) {
					if (panel.getComponentCount() == 1) {
						if (panel.getComponent(0).getDefinitionType().equals(MALQueryManagerComponentManager.DEFINITION_TYPE) == true) {
							queryManagerPnl = panel;
						} else if (panel.getComponent(0).getDefinitionType().equals(MALSearchViewComponentManager.DEFINITION_TYPE) == true) {
							drillDownPnl = panel;
						}
					}
				}
			}
			if (drillDownPnl == null) {
				return VelocityViewHelper.getInstance().prepareRespone(request, "invalidsearchpage");
			}
			// create session
			BizSession session = request.getSession(true);
			// add to request
			request.setAttribute(KnownParameterNames.SESSION_ID, session.getId());
			// prep request & session for content provide usage
			createSearchPageEnv(request, session, pCtx);
			String drilldownContent = getContent(drillDownPnl.getComponent(0), request, token);
			String template = "drilldown";
			if (queryManagerPnl != null) {
				String queryManagerContent = getContent(queryManagerPnl.getComponent(0), request, token);
				String span = queryManagerPnl.getSpan();
				request.setAttribute("querymanagercontent", queryManagerContent);
				request.setAttribute("span", span);
				template = "drilldownwithquerymgr";
			}
			request.setAttribute("drilldowncontent", drilldownContent);
			MALQueryManagerComponent queryManagerComponent = new MALQueryManagerComponent();
			queryManagerComponent.setDefinitionType(MALQueryManagerComponentManager.DEFINITION_TYPE);
			String queryManagerContent = getContent(queryManagerComponent, request, token);
			request.setAttribute("querymanagercontent", queryManagerContent);
			template = VelocityViewHelper.getInstance().getViewTemplatePath("search", template);
			return VelocityViewHelper.getInstance().prepareResponseUsingTemplatePath(request, template);
		} catch (Exception e) {
			String message = e.getMessage();
			if (message == null){
				message = "An error occured while processing the request";
			}
			request.setAttribute("errormessage", message);
			exceptionHandler.handleException(e);
		}
		return VelocityViewHelper.getInstance().prepareRespone(request, "error");
	}

	@SuppressWarnings("unchecked")
	private void createSearchPageEnv(BizSessionRequest request, BizSession session, PresentationContext pCtx) throws RequestProcessingException, IncompatibleQueryException {
		QuerySpec querySpec = createDrillDownQuery(request, pCtx);
		BizSessionSearchStore.getInstance(session).setQuerySpecs(querySpec == null ? new ArrayList<QuerySpec>(0) : Arrays.asList(querySpec));
		BizSessionSearchStore.getInstance(session).createQueryManagerModel();
		List<String> commands = (List<String>) request.getAttribute("runCommands");
		if (commands == null){
			commands = new LinkedList<String>();
			request.setAttribute("runCommands", commands);
		}
		if (enableSessionKeeping == true) {
			commands.add("startSessionKeeper();");
			long sessionTimeoutValue = (Long) BizProperties.SESSION_TIMEOUT_KEY.getValue(properties);
			sessionTimeoutValue = sessionTimeoutValue/2;
			request.setAttribute("sessionkeeperinterval", sessionTimeoutValue);
		}
	}

	private String getContent(MALComponent component, BizSessionRequest request, SecurityToken securityToken) throws PluginException {
		ComponentContentProvider provider = ComponentContentProviderFactory.getInstance().getProvider(component);
		if (provider != null) {
			provider.setSecurityToken(securityToken);
			provider.setProperties(properties);
			provider.setLogger(logger);
			provider.setExceptionHandler(exceptionHandler);
			provider.setMessageGenerator(messageGenerator);
			return provider.getComponentContent(request);
		}
		return null;
	}

	@Override
	protected BizResponse handleInvalidSession(SecurityToken token, String sessionId, BizSessionRequest request) {
		return VelocityViewHelper.getInstance().prepareRespone(request, "invalidsession");
	}

	private QuerySpec createDrillDownQuery(BizRequest request, PresentationContext pCtx) throws RequestProcessingException {
		//component id
		String componentid = request.getParameter("componentid");
		boolean invalidCompID = StringUtil.isEmptyOrBlank(componentid);
		//series id
		String seriesid = request.getParameter("seriesid");
		boolean invalidSeriesID = StringUtil.isEmptyOrBlank(seriesid);
		//tuple id
		String tupleid = request.getParameter("tupid");
		boolean invalidTupleID = StringUtil.isEmptyOrBlank(tupleid);
		//if all the id's are invalid , then return null
		if (invalidCompID == true && invalidSeriesID == true && invalidTupleID == true) {
			return null;
		}
		//we have a min of id's validate each
		if (invalidCompID == true) {
			throw new RequestProcessingException(getMessage("searchpage.invalid.componentid"));
		}
		if (invalidSeriesID == true) {
			throw new RequestProcessingException(getMessage("searchpage.invalid.seriesid"));
		}
		if (invalidTupleID == true) {
			throw new RequestProcessingException(getMessage("searchpage.invalid.tupleid"));
		}
		TokenRoleProfile tokenRoleProfile = pCtx.getTokenRoleProfile();
		ViewsConfigHelper viewsConfigHelper = tokenRoleProfile.getViewsConfigHelper();
		MALComponent component = viewsConfigHelper.getComponentById(componentid);
		if (component == null) {
			throw new RequestProcessingException(getMessage("searchpage.nonexistent.component", getMessageGeneratorArgs(pCtx.getSecurityToken(), componentid)));
		}

		MALSeriesConfig matchingSeriesConfig = null;
		for (MALVisualization visualization : component.getVisualization()) {
			for (MALSeriesConfig seriesConfig : visualization.getSeriesConfig()) {
				if (seriesConfig.getId().equals(seriesid) == true) {
					matchingSeriesConfig = seriesConfig;
					break;
				}
			}
		}
		if (matchingSeriesConfig == null) {
			throw new RequestProcessingException(getMessage("searchpage.nonexistent.series", getMessageGeneratorArgs(pCtx.getSecurityToken(), seriesid, component.toString())));
		}

		DataSourceHandler dataSourceHandler = null;
		Tuple selectedTuple = null;
		try {
			dataSourceHandler = pCtx.getDataSourceHandler(matchingSeriesConfig);
			List<Tuple> data = dataSourceHandler.getData(pCtx);
			for (Tuple tuple : data) {
				if (tuple.getId().equals(tupleid) == true) {
					selectedTuple = tuple;
					break;
				}
			}
		} catch (VisualizationException ex) {
			throw new RequestProcessingException(getMessage("searchpage.datasourcehandler.retrieval.failure", getMessageGeneratorArgs(pCtx.getSecurityToken(), URIHelper.getURI(matchingSeriesConfig))));
		} catch (DataException e) {
			throw new RequestProcessingException(getMessage("searchpage.tuple.retrieval.failure", getMessageGeneratorArgs(pCtx.getSecurityToken(), URIHelper.getURI(matchingSeriesConfig), tupleid)));
		}

		if (selectedTuple == null) {
			throw new RequestProcessingException(getMessage("searchpage.nonexistent.tupleid", getMessageGeneratorArgs(pCtx.getSecurityToken(), URIHelper.getURI(matchingSeriesConfig), tupleid)));
		}

		MALSourceElement sourceElement = dataSourceHandler.getSourceElement();

		MALFieldMetaInfo[] queryFields = MALReferenceResolver.resolveFieldReferences(pCtx.getSecurityToken(), matchingSeriesConfig.getActionRule().getDrillableFields());
		if (queryFields == null || queryFields.length == 0) {
			List<MALFieldMetaInfo> groupByFields = new LinkedList<MALFieldMetaInfo>();
			for (MALFieldMetaInfo field : sourceElement.getFields()) {
				if (field.isGroupBy() == true) {
					groupByFields.add(field);
				}
			}
			queryFields = groupByFields.toArray(new MALFieldMetaInfo[groupByFields.size()]);
		}

		TupleSchema tupleSchema = TupleSchemaFactory.getInstance().getTupleSchema(sourceElement.getId());

		QuerySpec querySpec = new QuerySpec(tupleSchema);
		if (queryFields == null || queryFields.length == 0) {
			TupleSchemaField idField = tupleSchema.getIDField();
			if (idField == null) {
				throw new RequestProcessingException(getMessage("searchpage.nonexistent.idfield", getMessageGeneratorArgs(pCtx.getSecurityToken(), URIHelper.getURI(matchingSeriesConfig))));
			}
			FieldValue fieldValue = new FieldValue(idField.getFieldDataType(), idField.getFieldDataType().valueOf(tupleid));
			querySpec.addAndCondition(new QueryPredicate(tupleSchema, idField.getFieldID(), QueryPredicate.EQ, fieldValue));

		} else {
			for (MALFieldMetaInfo fieldMetaInfo : queryFields) {
				String fieldName = fieldMetaInfo.getName();
				FieldValue fieldValue = selectedTuple.getFieldValueByName(fieldName);
				querySpec.addAndCondition(new QueryPredicate(tupleSchema, fieldName, QueryPredicate.EQ, fieldValue));
			}
		}
		return querySpec;
	}


}