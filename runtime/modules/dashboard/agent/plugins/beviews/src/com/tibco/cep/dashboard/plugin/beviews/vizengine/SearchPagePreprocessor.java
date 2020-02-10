package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.plugin.beviews.search.BizSessionSearchStore;
import com.tibco.cep.dashboard.plugin.beviews.search.IncompatibleQueryException;
import com.tibco.cep.dashboard.psvr.biz.BizProperties;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizSession;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.RequestProcessingException;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.dashboard.psvr.data.DataException;
import com.tibco.cep.dashboard.psvr.data.DataSourceHandler;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;
import com.tibco.cep.dashboard.psvr.mal.MALReferenceResolver;
import com.tibco.cep.dashboard.psvr.mal.URIHelper;
import com.tibco.cep.dashboard.psvr.mal.ViewsConfigHelper;
import com.tibco.cep.dashboard.psvr.mal.managers.MALQueryManagerComponentManager;
import com.tibco.cep.dashboard.psvr.mal.model.MALComponent;
import com.tibco.cep.dashboard.psvr.mal.model.MALPage;
import com.tibco.cep.dashboard.psvr.mal.model.MALPanel;
import com.tibco.cep.dashboard.psvr.mal.model.MALPartition;
import com.tibco.cep.dashboard.psvr.mal.model.MALSeriesConfig;
import com.tibco.cep.dashboard.psvr.mal.model.MALSourceElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALVisualization;
import com.tibco.cep.dashboard.psvr.ogl.model.ActionConfigType;
import com.tibco.cep.dashboard.psvr.ogl.model.Parameter;
import com.tibco.cep.dashboard.psvr.ogl.model.Variable;
import com.tibco.cep.dashboard.psvr.ogl.model.types.CommandType;
import com.tibco.cep.dashboard.psvr.ogl.model.types.DataType;
import com.tibco.cep.dashboard.psvr.ogl.model.types.VariableType;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.dashboard.psvr.user.TokenRoleProfile;
import com.tibco.cep.dashboard.psvr.vizengine.PagePreProcessor;
import com.tibco.cep.dashboard.psvr.vizengine.VisualizationException;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigGenerator;
import com.tibco.cep.dashboard.psvr.vizengine.actions.ActionConfigUtils;
import com.tibco.cep.designtime.core.model.Entity;

public class SearchPagePreprocessor extends PagePreProcessor {

	private ActionConfigType sessionKeepingActionConfig;

	@Override
	protected void init() {
		boolean enableSessionKeeping = (Boolean) BizProperties.ENABLE_SESSION_KEEPING.getValue(properties);
		if (enableSessionKeeping == true) {
			// create the raw keep session alive action
			String[][] params = new String[][] { new String[] { "command", "keepsessionalive" } };
			String[][] dynparams = new String[][] { new String[] { "sessionid", ActionConfigGenerator.SESSION_ID } };
			ActionConfigType launchInternalLinkActionConfig = ActionConfigUtils.createActionConfig("", CommandType.LAUNCHINTERNALLINK, false, null, dynparams, params, null);
			// embed the raw action in the reucurring action
			sessionKeepingActionConfig = ActionConfigUtils.createActionConfigSet("", false, Arrays.asList(launchInternalLinkActionConfig));
			// set command type
			sessionKeepingActionConfig.setCommand(CommandType.RECURRING);
			// set the period based on the session timeout value
			long sessionTimeOut = (Long) BizProperties.SESSION_TIMEOUT_KEY.getValue(properties);
			// reduce the session timeout by 10%
			sessionTimeOut = sessionTimeOut - (long) (sessionTimeOut * 0.10);
			// set the session timeout value as a config param
			Parameter periodParam = new Parameter();
			periodParam.setName("period");
			periodParam.setContent(String.valueOf(sessionTimeOut));
			sessionKeepingActionConfig.addConfigParam(periodParam);
		}
	}

	@Override
	public PreProcessorResults preprocess(MALPage page, BizSessionRequest request, PresentationContext ctx) throws RequestProcessingException {
		try {
			// create session
			BizSession session = request.getSession(true);
			// create the drill down query
			QuerySpec[] querySpecs = createDrillDownQueries(request, ctx);
			//update the biz session search store
			BizSessionSearchStore searchStore = BizSessionSearchStore.getInstance(session);
			searchStore.setQuerySpecs(querySpecs == null ? new ArrayList<QuerySpec>(0) : Arrays.asList(querySpecs));
			if (pageHasQueryManager(page) == true) {
				//create the query manager model only if we have a query manager in the page
				searchStore.createQueryManagerModel();
			}
			PreProcessorResults results = new PreProcessorResults();
			// create token variable
			results.addVariable(getVariable("stoken", DataType.STRING, session.getTokenId(), VariableType.HIDDEN));
			// create session id variable
			results.addVariable(getVariable("sessionid", DataType.STRING, session.getId(), VariableType.HIDDEN));
			// add session keeping action if needed
			if (sessionKeepingActionConfig != null) {
				results.addAction(sessionKeepingActionConfig);
			}
			return results;
		} catch (IncompatibleQueryException e) {
			String message = getMessage("searchpage.incomptible.querymodel", ctx.getMessageGeneratorArgs(e, new Object[0]));
			throw new RequestProcessingException(message,e);
		}
	}

	private boolean pageHasQueryManager(MALPage page) {
		for (MALPartition partition : page.getPartition()) {
			for (MALPanel panel : partition.getPanel()) {
				for (MALComponent component : panel.getComponent()) {
					if (component.getDefinitionType().equals(MALQueryManagerComponentManager.DEFINITION_TYPE) == true) {
						return true;
					}
				}
			}
		}
		return false;
	}

	protected QuerySpec[] createDrillDownQueries(BizRequest request, PresentationContext pCtx) throws RequestProcessingException {
		// component id
		String componentid = request.getParameter("componentid");
		// series id
		String seriesid = request.getParameter("seriesid");
		// tuple id
		String tupleid = request.getParameter("tupid");
		// type name
		String typename = request.getParameter("typename");
		// if all the id's are invalid , then return null
		if (StringUtil.isEmptyOrBlank(componentid) == false && StringUtil.isEmptyOrBlank(seriesid) == false && StringUtil.isEmptyOrBlank(tupleid) == false) {
			return createDrillDownQueries(componentid, seriesid, tupleid, pCtx);
		}
		if (StringUtil.isEmptyOrBlank(typename) == false) {
			return createDrillDownQueries(typename, request, pCtx);
		}
		return null;
	}

	private QuerySpec[] createDrillDownQueries(String componentid, String seriesid, String tupleid, PresentationContext pCtx) throws RequestProcessingException {
		// we have a min of id's validate each
		if (StringUtil.isEmptyOrBlank(componentid) == true) {
			throw new RequestProcessingException(getMessage("searchpage.invalid.componentid"));
		}
		if (StringUtil.isEmptyOrBlank(seriesid) == true) {
			throw new RequestProcessingException(getMessage("searchpage.invalid.seriesid"));
		}
		if (StringUtil.isEmptyOrBlank(tupleid) == true) {
			throw new RequestProcessingException(getMessage("searchpage.invalid.tupleid"));
		}
		TokenRoleProfile tokenRoleProfile = pCtx.getTokenRoleProfile();
		ViewsConfigHelper viewsConfigHelper = tokenRoleProfile.getViewsConfigHelper();
		MALComponent component = viewsConfigHelper.getComponentById(componentid);
		if (component == null) {
			throw new RequestProcessingException(getMessage("searchpage.nonexistent.component", pCtx.getMessageGeneratorArgs(null, componentid)));
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
			throw new RequestProcessingException(getMessage("searchpage.nonexistent.series", pCtx.getMessageGeneratorArgs(null, seriesid, component.toString())));
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
			throw new RequestProcessingException(getMessage("searchpage.datasourcehandler.retrieval.failure", pCtx.getMessageGeneratorArgs(null, URIHelper.getURI(matchingSeriesConfig))));
		} catch (DataException e) {
			throw new RequestProcessingException(getMessage("searchpage.tuple.retrieval.failure", pCtx.getMessageGeneratorArgs(null, URIHelper.getURI(matchingSeriesConfig), tupleid)));
		}

		if (selectedTuple == null) {
			throw new RequestProcessingException(getMessage("searchpage.nonexistent.tupleid", pCtx.getMessageGeneratorArgs(null, URIHelper.getURI(matchingSeriesConfig), tupleid)));
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
				throw new RequestProcessingException(getMessage("searchpage.nonexistent.idfield", pCtx.getMessageGeneratorArgs(null, URIHelper.getURI(matchingSeriesConfig))));
			}
			FieldValue fieldValue = new FieldValue(idField.getFieldDataType(), idField.getFieldDataType().valueOf(tupleid));
			querySpec.addAndCondition(new QueryPredicate(tupleSchema, idField.getFieldID(), QueryPredicate.EQ, fieldValue));

		} else {
			for (MALFieldMetaInfo fieldMetaInfo : queryFields) {
				String fieldName = fieldMetaInfo.getName();
				FieldValue fieldValue = selectedTuple.getFieldValueByName(fieldName);
				if (fieldValue.isNull() == true) {
					querySpec.addAndCondition(new QueryPredicate(tupleSchema, fieldName, QueryPredicate.IS_NULL, fieldValue));
				}
				else {
					querySpec.addAndCondition(new QueryPredicate(tupleSchema, fieldName, QueryPredicate.EQ, fieldValue));
				}
			}
		}
		return new QuerySpec[]{querySpec};
	}

	private QuerySpec[] createDrillDownQueries(String typeName, BizRequest request, PresentationContext pCtx) throws RequestProcessingException {
		//find a type by the type name
		List<Entity> entities = EntityCache.getInstance().searchByName(typeName);
		if (entities.size() != 1){
			throw new RequestProcessingException("Found more than one entity for "+typeName);
		}
		TupleSchema tupleSchema = TupleSchemaFactory.getInstance().getTupleSchema(entities.get(0));
		QuerySpec querySpec = new QuerySpec(tupleSchema);
		int fieldCount = tupleSchema.getFieldCount();
		for (int i = 0; i < fieldCount; i++) {
			TupleSchemaField field = tupleSchema.getFieldByPosition(i);
			String value = request.getParameter(field.getFieldName());
			if (StringUtil.isEmptyOrBlank(value) == false) {
				FieldValue fieldValue = new FieldValue(field.getFieldDataType(), field.getFieldDataType().valueOf(value));
				querySpec.addAndCondition(new QueryPredicate(tupleSchema, field.getFieldName(), QueryPredicate.EQ, fieldValue));
			}
		}
		return new QuerySpec[]{querySpec};
	}

	private Variable getVariable(String name, DataType dataType, String value, VariableType type) {
		Variable var = new Variable();
		var.setName(name);
		var.setDatatype(dataType);
		var.setContent(value);
		var.setType(type);
		return var;
	}

	@Override
	protected void shutdown() throws NonFatalException {
		sessionKeepingActionConfig = null;
	}

}
