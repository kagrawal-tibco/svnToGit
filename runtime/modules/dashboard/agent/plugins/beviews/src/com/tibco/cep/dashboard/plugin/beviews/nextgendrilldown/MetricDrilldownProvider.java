package com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.common.query.ViewsQuery;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.DrilldownQuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityUtils;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.dashboard.psvr.common.query.QueryExecutor;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.kernel.service.logging.Logger;

class MetricDrilldownProvider implements TypeSpecificDrilldownProvider {

	@Override
	public QuerySpec getDrillDownQuery(Logger logger, Entity entity, String entityInstanceId, String nextInLineTypeId, QueryExecutor queryExecutor) throws QueryException {
		if (entity == null || StringUtil.isEmptyOrBlank(entityInstanceId) == true) {
			return null;
		}
		if (StringUtil.isEmptyOrBlank(nextInLineTypeId) == true) {
			// we have a null or blank source type id , skip it
			return null;
		}
		// we will use the nextInLineTypeId since that is the DVM's type id
		TupleSchema tupleSchema = TupleSchemaFactory.getInstance().getTupleSchema(nextInLineTypeId);
		DrilldownQuerySpec querySpec = new DrilldownQuerySpec(tupleSchema);
		TupleSchemaField idField = tupleSchema.getIDField();
		// we will use the toInstanceID as @id, since Vincent's translates the @id to @parentid in context of a drill down query
		querySpec.addAndCondition(new QueryPredicate(tupleSchema, idField.getFieldName(), QueryPredicate.EQ, new FieldValue(idField.getFieldDataType(), idField.getFieldDataType().valueOf(entityInstanceId))));
		return querySpec;
	}

	@Override
	public List<Tuple> getNextInLines(Logger logger, Entity entity, String entityInstanceId, QueryExecutor queryExecutor) throws QueryException {
		if (entity == null || StringUtil.isEmptyOrBlank(entityInstanceId) == true) {
			return Collections.emptyList();
		}
		Entity nextInLineEntity = EntityUtils.getChild(entity);
		if (nextInLineEntity == null) {
			return Collections.emptyList();
		}
		int count = queryExecutor.countQuery(new ViewsQuery(getDrillDownQuery(logger, entity, entityInstanceId, nextInLineEntity.getGUID(), queryExecutor)));
		NextInLineTuple nextInLineTuple = new NextInLineTuple(nextInLineEntity.getGUID(), count);
		ArrayList<Tuple> tuples = new ArrayList<Tuple>();
		tuples.add(nextInLineTuple);
		return tuples;
	}

}
