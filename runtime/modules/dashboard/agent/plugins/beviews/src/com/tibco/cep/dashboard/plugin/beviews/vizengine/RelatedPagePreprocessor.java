package com.tibco.cep.dashboard.plugin.beviews.vizengine;

import java.util.HashMap;
import java.util.Map;

import com.tibco.be.functions.object.ObjectHelper;
import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.FieldValueArray;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQuery;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQueryExecutorFactory;
import com.tibco.cep.dashboard.plugin.beviews.data.ConceptTupleSchema;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.psvr.biz.BizRequest;
import com.tibco.cep.dashboard.psvr.biz.RequestProcessingException;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.QueryExecutor;
import com.tibco.cep.dashboard.psvr.common.query.ResultSet;
import com.tibco.cep.dashboard.psvr.runtime.PresentationContext;
import com.tibco.cep.runtime.model.element.Concept;


public class RelatedPagePreprocessor extends SearchPagePreprocessor {

	@Override
	protected void init() {
		super.init();
	}

	@Override
	protected QuerySpec[] createDrillDownQueries(BizRequest request, PresentationContext pCtx) throws RequestProcessingException {
		// type id
		String typeid = request.getParameter("typeid");
		boolean invalidTypeID = StringUtil.isEmptyOrBlank(typeid);
		// instance id
		String instanceid = request.getParameter("tupid");
		boolean invalidinstanceID = StringUtil.isEmptyOrBlank(instanceid);
		// if all the id's are invalid , then return null
		if (invalidTypeID == true && invalidinstanceID == true) {
			return null;
		}
		// we have a min of id's validate each
		if (invalidTypeID == true) {
			throw new RequestProcessingException(getMessage("searchpage.invalid.typeid"));
		}
		if (invalidinstanceID == true) {
			throw new RequestProcessingException(getMessage("searchpage.invalid.instanceid"));
		}

		//get tuple matching the typeid and instance id
		Tuple tuple = getTuple(typeid, instanceid, pCtx);

		if (tuple != null) {
			//extract users (can be parents or referrers)
			String[] userIDs = extractUsers(tuple);

			if (userIDs != null) {
				return convertToQueries(userIDs);
			}
		}

		return null;

	}

	private Tuple getTuple(String typeid, String instanceid, PresentationContext pCtx) throws RequestProcessingException {
		TupleSchema tupleSchema = TupleSchemaFactory.getInstance().getTupleSchema(typeid);

		QuerySpec query = new QuerySpec(tupleSchema);
		TupleSchemaField idField = tupleSchema.getIDField();
		if (idField == null) {
			throw new RequestProcessingException(getMessage("searchpage.nonexistent.idfield", pCtx.getMessageGeneratorArgs(null, typeid)));
		}
		FieldValue fieldValue = new FieldValue(idField.getFieldDataType(), idField.getFieldDataType().valueOf(instanceid));
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
			throw new RequestProcessingException(getMessage("relatedpage.basetuple.fetch.failure"), e);
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

	private String[] extractUsers(Tuple tuple) {
		TupleSchema schema = tuple.getSchema();
		if (schema instanceof ConceptTupleSchema) {
			ConceptTupleSchema conceptTupleSchema = (ConceptTupleSchema) schema;
			TupleSchemaField parentIdField = conceptTupleSchema.getParentIdField();
			String[] userIds = null;
			if (parentIdField.isArray() == true) {
				FieldValueArray fieldValueArray = tuple.getFieldArrayValueByName(parentIdField.getFieldName());
				if (fieldValueArray.isNull() == false) {
					Comparable<?>[] values = fieldValueArray.getValues();
					userIds = new String[values.length];
					for (int i = 0; i < values.length; i++) {
						userIds[i] = String.valueOf(values[i]);
					}
				}
			}
			else {
				FieldValue fieldValue = tuple.getFieldValueByName(parentIdField.getFieldName());
				if (fieldValue.isNull() == false) {
					userIds = new String[]{String.valueOf(fieldValue)};
				}
			}
			return userIds;
		}
		return null;
	}

	private QuerySpec[] convertToQueries(String[] ids){
		Map<String, QuerySpec> querySpecMap = new HashMap<String, QuerySpec>();
		for (String id : ids) {
			//we need to call ObjectHelper.getById to get the concept
			Concept concept = ObjectHelper.getById(Long.parseLong(id));
			TupleSchema schema = TupleSchemaFactory.getInstance().getTupleSchema(concept.getExpandedName());
			QuerySpec query = querySpecMap.get(schema.getTypeID());
			if (query == null) {
				query = new QuerySpec(schema);
				querySpecMap.put(schema.getTypeID(), query);
			}
			TupleSchemaField idField = schema.getIDField();
			FieldValue idValue = new FieldValue(idField.getFieldDataType(),idField.getFieldDataType().valueOf(id));
			query.addOrCondition(new QueryPredicate(schema, idField.getFieldName(), QueryPredicate.EQ, idValue));
		}
		return querySpecMap.values().toArray(new QuerySpec[querySpecMap.size()]);
	}

}
