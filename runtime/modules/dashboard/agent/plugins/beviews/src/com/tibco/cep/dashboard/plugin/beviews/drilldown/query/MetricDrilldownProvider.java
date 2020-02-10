package com.tibco.cep.dashboard.plugin.beviews.drilldown.query;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.Tuple;
import com.tibco.cep.dashboard.common.data.TupleSchema;
import com.tibco.cep.dashboard.common.data.TupleSchemaField;
import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.dashboard.plugin.beviews.data.TupleSchemaFactory;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.model.NextInLine;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.DrilldownQuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QueryPredicate;
import com.tibco.cep.dashboard.plugin.beviews.drilldown.query.model.QuerySpec;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityCache;
import com.tibco.cep.dashboard.plugin.beviews.mal.EntityUtils;
import com.tibco.cep.dashboard.psvr.common.query.QueryException;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.kernel.service.logging.Logger;

public class MetricDrilldownProvider implements TypeSpecificDrilldownProvider {

	@Override
	public QuerySpec getDrillDownQuery(Logger logger, Tuple tuple, String typeId) {
		if (tuple == null) {
			return null;
		}
		if (StringUtil.isEmptyOrBlank(typeId) == true) {
			// we have a null or blank source type id , skip it
			return null;
		}
		// we will use the typeid since that is the DVM's type id
		TupleSchema tupleSchema = TupleSchemaFactory.getInstance().getTupleSchema(typeId);
		DrilldownQuerySpec querySpec = new DrilldownQuerySpec(tupleSchema);
		TupleSchemaField idField = tupleSchema.getIDField();
		// we will use the toInstanceID as @id, since Vincent's translates the @id to @parentid in context of a drilldown query
		querySpec.addAndCondition(new QueryPredicate(tupleSchema, idField.getFieldName(), QueryPredicate.EQ, new FieldValue(idField.getFieldDataType(), idField.getFieldDataType().valueOf(tuple.getId()))));
		return querySpec;
	}

	@Override
	public List<NextInLine> getNextInLines(Logger logger, Tuple tuple, Map<String, String> additionalParameters, DrilldownProvider drilldownProvider) throws QueryException {
		if (tuple == null) {
			return Collections.emptyList();
		}
		String toTypeId = tuple.getTypeId();
		String toInstanceId = tuple.getId();

		Entity entity = EntityCache.getInstance().getEntity(toTypeId);
		Entity nextInLineEntity = EntityUtils.getChild(entity);
		if (nextInLineEntity == null) {
			return Collections.emptyList();
		}

		boolean countRequired = true;
		if (additionalParameters != null && additionalParameters.containsKey("countrequired")) {
			countRequired = Boolean.valueOf(additionalParameters.get("countrequired"));
		}
		int count = -1;
		if (countRequired) {
			count = drilldownProvider.getInstanceCount(getDrillDownQuery(logger, tuple, nextInLineEntity.getGUID()), additionalParameters, null, false);
			if (count == 0) {
				return Collections.emptyList();
			}
		}
		// Anand fixed to use the alternate name instead of the real name - 10/22/04
		NextInLine nextInLine = new NextInLine(toTypeId, toInstanceId, tuple.getSchema().getTypeName() + " Source(s)", nextInLineEntity.getGUID(), count);
		return Arrays.asList(nextInLine);
	}

}
