package com.tibco.cep.dashboard.plugin.beviews.biz;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQuery;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQueryExecutorFactory;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.mal.MALExternalURLUtils;
import com.tibco.cep.dashboard.plugin.beviews.mal.MALSourceElementCache;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.RequestProcessingException;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.QueryExecutor;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.mal.ElementNotFoundException;
import com.tibco.cep.dashboard.psvr.mal.MALException;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.psvr.variables.VariableContext;
import com.tibco.cep.dashboard.psvr.variables.VariableInterpreter;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;

public class LaunchExternalLinkAction extends BaseSessionCheckerAction {

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		BizResponse response = null;
		if (requestFromDashboardPage(request) == true) {
			response = generateLinkForDashboardPage(token, request);
		} else if (requestFromSearchPage(request) == true) {
			response = generateLinkForSearchPage(token, request);
		} else {
			response = handleError(getMessage("launchexternallink.nolink.generated"));
		}
		return response;
	}

	private boolean requestFromDashboardPage(BizSessionRequest request) {
		boolean ok = !StringUtil.isEmptyOrBlank(request.getParameter("componentid"));
		ok = ok && !StringUtil.isEmptyOrBlank(request.getParameter("seriesid"));
		ok = ok && !StringUtil.isEmptyOrBlank(request.getParameter("tupid"));
		ok = ok && !StringUtil.isEmptyOrBlank(request.getParameter("fieldname"));
		return ok;
	}

	private boolean requestFromSearchPage(BizSessionRequest request) {
		boolean ok = !StringUtil.isEmptyOrBlank(request.getParameter("typeid"));
		ok = ok && !StringUtil.isEmptyOrBlank(request.getParameter("tupid"));
		ok = ok && !StringUtil.isEmptyOrBlank(request.getParameter("fieldname"));
		return ok;
	}

	private BizResponse generateLinkForDashboardPage(SecurityToken token, BizSessionRequest request) {
		PresentationContext ctx = null;
		MALSourceElement sourceElement = null;
		String url = null;
		String componentID = request.getParameter("componentid");
		String seriesID = request.getParameter("seriesid");
		String tupleID = request.getParameter("tupid");
		String fieldName = request.getParameter("fieldname");
		MALSeriesConfig matchingSeriesConfig = null;
		try {
			if (StringUtil.isEmptyOrBlank(componentID) == true) {
				return handleError(getMessage("launchexternallink.invalid.componentid"));
			}
			if (StringUtil.isEmptyOrBlank(seriesID) == true) {
				return handleError(getMessage("launchexternallink.invalid.seriesid"));
			}
			if (StringUtil.isEmptyOrBlank(tupleID) == true) {
				return handleError(getMessage("launchexternallink.invalid.tupleid"));
			}
			if (StringUtil.isEmptyOrBlank(fieldName) == true) {
				return handleError(getMessage("launchexternallink.invalid.fieldname"));
			}

			TokenRoleProfile tokenRoleProfile = getTokenRoleProfile(token);
			ViewsConfigHelper viewsConfigHelper = tokenRoleProfile.getViewsConfigHelper();
			MALComponent component = viewsConfigHelper.getComponentById(componentID);
			if (component == null) {
				return handleError(getMessage("launchexternallink.nonexistent.component",getMessageGeneratorArgs(token,componentID)));
			}

			for (MALVisualization visualization : component.getVisualization()) {
				for (MALSeriesConfig seriesConfig : visualization.getSeriesConfig()) {
					if (seriesConfig.getId().equals(seriesID) == true) {
						matchingSeriesConfig = seriesConfig;
						break;
					}
				}
			}
			if (matchingSeriesConfig == null) {
				return handleError(getMessage("launchexternallink.nonexistent.series", getMessageGeneratorArgs(token,seriesID,component.toString())));
			}

			ctx = new PresentationContext(token, true);

			DataSourceHandler dataSourceHandler = ctx.getDataSourceHandler(matchingSeriesConfig);

			sourceElement = dataSourceHandler.getSourceElement();

			url = MALExternalURLUtils.getURLLink(sourceElement.getId(), fieldName);

			if (url == null) {
				return handleError(getMessage("launchexternallink.nonexistent.seriesbasedlink", getMessageGeneratorArgs(token,fieldName,URIHelper.getURI(matchingSeriesConfig))));
			}

			Tuple tuple = getTuple(tupleID, dataSourceHandler, ctx);

			return handleSuccess(generateLink(url, tuple, ctx));
		} catch (DataException e) {
			return handleError(getMessage("launchexternallink.tuple.fetch.failure", getMessageGeneratorArgs(token,e,tupleID)), e);
		} catch (RequestProcessingException e) {
			return handleError(getMessage("launchexternallink.tokenroleprofile.fetch.failure", getMessageGeneratorArgs(token,e)), e);
		} catch (MALException e) {
			return handleError(getMessage("launchexternallink.viewsconfig.fetch.failure", getMessageGeneratorArgs(token,e)), e);
		} catch (ElementNotFoundException e) {
			return handleError(getMessage("launchexternallink.viewsconfig.notfound.failure", getMessageGeneratorArgs(token,e)), e);
		} catch (VisualizationException e) {
			return handleError(getMessage("launchexternallink.datasourcehandler.fetch.failure", getMessageGeneratorArgs(token,e,URIHelper.getURI(matchingSeriesConfig))), e);
		} catch (ParseException e) {
			return handleError(getMessage("launchexternallink.url.parse.failure", getMessageGeneratorArgs(token,e, url, sourceElement.getName(), fieldName)), e);
		} finally {
			if (ctx != null){
				ctx.close();
			}
		}
	}

	private BizResponse generateLinkForSearchPage(SecurityToken token, BizSessionRequest request) {
		String typeid = request.getParameter("typeid");
		String tupleid = request.getParameter("tupid");
		String fieldName = request.getParameter("fieldname");
		MALSourceElement sourceElement = MALSourceElementCache.getInstance().getMALSourceElement(typeid);
		String url = MALExternalURLUtils.getURLLink(typeid, fieldName);
		if (url == null) {
			return handleError(getMessage("launchexternallink.nonexistent.entitybasedlink", getMessageGeneratorArgs(token,fieldName,sourceElement.getName())));
		}
		try {
			Tuple tuple = getTuple(request.getSession(),typeid,tupleid);
			return handleSuccess(generateLink(url, tuple, new PresentationContext(token)));
		} catch (DataException e) {
			return handleError(getMessage("launchexternallink.tuple.fetch.failure", getMessageGeneratorArgs(token,e,tupleid)), e);
		} catch (MALException e) {
			return handleError(getMessage("launchexternallink.viewsconfig.fetch.failure", getMessageGeneratorArgs(token,e)), e);
		} catch (ElementNotFoundException e) {
			return handleError(getMessage("launchexternallink.viewsconfig.notfound.failure", getMessageGeneratorArgs(token,e)), e);
		} catch (ParseException e) {
			return handleError(getMessage("launchexternallink.url.parse.failure", getMessageGeneratorArgs(token,e, url, sourceElement.getName(), fieldName)), e);
		}
	}

	private Tuple getTuple(String id, DataSourceHandler dataSourceHandler, PresentationContext ctx) throws DataException {
		List<Tuple> data = dataSourceHandler.getData(ctx);
		for (Tuple tuple : data) {
			if (tuple.getId().equals(id) == true) {
				return tuple;
			}
		}
		throw new UnsupportedOperationException("fetching tuple from db");
	}

	private Tuple getTuple(BizSession session, String typeid, String id) throws DataException {
		TupleSchema tupleSchema = TupleSchemaFactory.getInstance().getTupleSchema(typeid);
		TupleSchemaField idField = tupleSchema.getIDField();
		if (idField == null) {
			throw new DataException("No id field set in " + tupleSchema);
		}
		FieldValue fieldValue = new FieldValue(idField.getFieldDataType(), idField.getFieldDataType().valueOf(id));

		QuerySpec query = new QuerySpec(tupleSchema);
		query.addAndCondition(new QueryPredicate(tupleSchema, idField.getFieldName(), QueryPredicate.EQ, fieldValue));

		QueryExecutor queryExecutor = null;
		ResultSet resultSet = null;
		try {
			queryExecutor = ViewsQueryExecutorFactory.getInstance().createImplementation();
			resultSet = queryExecutor.executeQuery(new ViewsQuery(query));
			if (resultSet.next() == true) {
				return resultSet.getTuple();
			}
			return null;
		} catch (QueryException e) {
			throw new DataException(e);
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (QueryException e) {
					exceptionHandler.handleException("could not close resultset for "+query, e);
				}
			}
			if (queryExecutor != null) {
				queryExecutor.close();
			}
		}
	}

	private String generateLink(String urlTemplate, Tuple tuple, PresentationContext ctx) throws ParseException {
		VariableContext variableContext = new VariableContext(logger, ctx.getSecurityToken(), properties, tuple);
		String generatedLink = VariableInterpreter.getInstance().interpret(urlTemplate, variableContext, false);
		if (logger.isEnabledFor(Level.DEBUG) == true) {
			logger.log(Level.DEBUG, "Generated [" + generatedLink + "] from [" + urlTemplate + " ] using " + tuple);
		}
		String[] splits = generatedLink.split("\\?");
		if (splits.length == 2) {
			StringBuilder encodedQuery = new StringBuilder();
			String[] params = splits[1].split("&");
			for (int i = 0; i < params.length; i++) {
				String paramPair = params[i];
				String[] pair = paramPair.split("=");
				if (pair.length == 2){
					if (encodedQuery.length() > 0) {
						encodedQuery.append('&');
					}
					encodedQuery.append(pair[0]);
					encodedQuery.append('=');
					encodedQuery.append(encode(pair[1]));
				}
				else if (pair.length == 1) {
					encodedQuery.append(encode("&"));
					encodedQuery.append(pair[0]);
				}
			}
			generatedLink = splits[0]+"?"+encodedQuery;
			if (logger.isEnabledFor(Level.DEBUG) == true) {
				logger.log(Level.DEBUG, "Encoded link is [" + generatedLink + "]");
			}
		}
		return generatedLink;
	}

	@Override
	protected BizResponse handleInvalidSession(SecurityToken token, String sessionId, BizSessionRequest request) {
		if (requestFromSearchPage(request) == true){
			return handleError("Your session has timed out");
		}
		throw new UnsupportedOperationException();
	}


	private String encode(String value){
		try {
			return URLEncoder.encode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return value;
		}
	}

}
