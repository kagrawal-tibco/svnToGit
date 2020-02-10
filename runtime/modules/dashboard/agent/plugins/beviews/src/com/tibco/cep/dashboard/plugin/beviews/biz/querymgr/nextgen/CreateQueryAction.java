package com.tibco.cep.dashboard.plugin.beviews.biz.querymgr.nextgen;

import java.util.Arrays;

import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryCondition;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.EntityVisualizer;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.visengine.EntityVisualizerProvider;
import com.tibco.cep.dashboard.plugin.beviews.querymgr.DataTypeHandler;
import com.tibco.cep.dashboard.plugin.beviews.querymgr.DataTypeHandlerFactory;
import com.tibco.cep.dashboard.plugin.beviews.search.BizSessionSearchStore;
import com.tibco.cep.dashboard.plugin.beviews.search.IncompatibleQueryException;
import com.tibco.cep.dashboard.plugin.beviews.search.QuerySpecModel;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.BizResponse;
import com.tibco.cep.dashboard.psvr.biz.BizSessionRequest;
import com.tibco.cep.dashboard.psvr.biz.RequestProcessingException;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.ogl.OGLException;
import com.tibco.cep.dashboard.psvr.ogl.OGLUnmarshaller;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.QueryManagerEntity;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.QueryManagerEntityField;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.QueryManagerEntityFieldCondition;
import com.tibco.cep.dashboard.psvr.ogl.model.edm.QueryManagerModel;
import com.tibco.cep.dashboard.security.SecurityToken;
import com.tibco.cep.kernel.service.logging.Level;

public class CreateQueryAction extends BaseQueryManagerAction {

	@Override
	protected BizResponse doExecute(SecurityToken token, BizSessionRequest request) {
		try {
			//create the query spec using the incoming xml
			QuerySpec querySpec = createQuery(token,request);
			//get the search store
			BizSessionSearchStore searchStore = BizSessionSearchStore.getInstance(request.getSession());
			//get the query manager model
			QuerySpecModel querySpecMdl = searchStore.getQueryManagerModel();
			if (querySpecMdl == null) {
				//we don't have a query manager model , create a new one
				searchStore.setQuerySpecs(Arrays.asList(querySpec));
				searchStore.createQueryManagerModel();
			}
			else {
				//reset the query spec model
				querySpecMdl.setQuerySpec(querySpec);
				//reset the query spec in the session for getdrilldowndata to work
				searchStore.setQuerySpecs(Arrays.asList(querySpec));
			}
			return handleSuccess("");
		} catch (RequestProcessingException e) {
			return handleError(getMessage("createquery.general.processing.failure", getMessageGeneratorArgs(token,e)), e);
		} catch (IncompatibleQueryException e) {
			return handleError(getMessage("createquery.general.processing.failure", getMessageGeneratorArgs(token,e)), e);
		} catch (QueryException e) {
			return handleError(getMessage("createquery.general.processing.failure", getMessageGeneratorArgs(token,e)), e);
		}
	}

    private QueryManagerModel parseQueryManagerModel(SecurityToken token, BizRequest request) throws RequestProcessingException {
        String queryMdlXML = request.getParameter("querymgrmodel");
        if (StringUtil.isEmptyOrBlank(queryMdlXML) == true) {
            throw new RequestProcessingException(getMessage("createquery.invalid.querymodelxml"));
        }
        queryMdlXML = queryMdlXML.replace("&lt;", "<").replace("&gt;", ">");
        QueryManagerModel queryManagerModel = null;
        try {
        	if (logger.isEnabledFor(Level.DEBUG) == true) {
        		logger.log(Level.DEBUG, "Received [" + queryMdlXML + "]");
        	}
            queryManagerModel = (QueryManagerModel) OGLUnmarshaller.getInstance().unmarshall(QueryManagerModel.class, queryMdlXML);
        } catch (OGLException e) {
            String exMsg = exceptionHandler.getMessage(e);
            exMsg = getMessage("createquery.querymodelxml.unmarshall.failure", getMessageGeneratorArgs(token,exMsg));
            throw new RequestProcessingException(exMsg, e);
        }
        return queryManagerModel;
    }

    protected QuerySpec createQuery(SecurityToken token, BizRequest request) throws RequestProcessingException, QueryException {
		//unmarshall the incoming query model
		QueryManagerModel queryManagerModel = parseQueryManagerModel(token, request);
		//make sure that the query manager model is valid
		//selected entity id
		String typeid = queryManagerModel.getSelectedEntityID();
		if(StringUtil.isEmptyOrBlank(typeid) == true){
			throw new RequestProcessingException(getMessage("createquery.invalid.typeid"));
		}
//		//entity presence checks
//		if (queryManagerModel.getQueryManagerEntityCount() == 0){
//			throw new RequestProcessingException(getMessage("createquery.nonexistent.entity"));
//		}
//		if (queryManagerModel.getQueryManagerEntityCount() != 1){
//			throw new RequestProcessingException(getMessage("createquery.toomany.entity"));
//		}
		TupleSchema tupleSchema = TupleSchemaFactory.getInstance().getTupleSchema(typeid);
		//create the query spec
		QuerySpec querySpec = new QuerySpec(tupleSchema);
		if (queryManagerModel.getQueryManagerEntityCount() == 1) {
			QueryManagerEntity queryManagerEntity = queryManagerModel.getQueryManagerEntity(0);
			//now add all the incoming conditions
			EntityVisualizer entityVisualizer = EntityVisualizerProvider.getInstance().getEntityVisualizerById(typeid);
			for (QueryManagerEntityField field : queryManagerEntity.getQueryManagerEntityField()) {
				QueryManagerEntityFieldCondition condition = field.getCondition();
				//the field name in front end is the display name
				String displayName = field.getName();
				//get the actual field name
				String name = entityVisualizer.getName(displayName);
				// condition == null implies no condition set for the field (reset condition)
				if(condition != null) {
					//the operator
					String highLevelOperator = condition.getOperator();
					//the values
					String[] values = condition.getValue();
					//get the field datatype using tuple schema
					TupleSchemaField tupleSchemaField = tupleSchema.getFieldByName(name);
					DataTypeHandler dataTypeHandler = DataTypeHandlerFactory.getInstance().getDataTypeHandler(tupleSchemaField.getFieldDataType());
					//use the datatype handler to create the query condition
					QueryCondition queryCondition = dataTypeHandler.createQueryCondition(logger, tupleSchema, name, highLevelOperator, values);
		        	if (logger.isEnabledFor(Level.DEBUG) == true) {
		        		logger.log(Level.DEBUG, "Adding [" + queryCondition + "] for "+entityVisualizer.getName());
		        	}
					//set the condition in the query spec model
		        	querySpec.addAndCondition(queryCondition);
				}
			}
		}
		return querySpec;
    }

}
