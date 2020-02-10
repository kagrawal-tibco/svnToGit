package com.tibco.rta.service.query;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_IS_ALERT_HIERARCHY;

import java.util.ArrayList;
import java.util.Collection;

import com.tibco.rta.Metric;
import com.tibco.rta.MetricKey;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricEventType;
import com.tibco.rta.query.MetricResultTuple;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.filter.OrFilter;
import com.tibco.rta.query.impl.MetricResultTupleImpl;
import com.tibco.rta.query.impl.QueryFactory;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.runtime.model.MetricNodeEvent;
import com.tibco.rta.service.admin.AdminService;

public class QueryUtils {
	
    public static MetricResultTuple convertMetricNodeToMetricResultTuple(MetricNode metricNode) {
        return convertMetricNodeToMetricResultTuple(metricNode, MetricEventType.UPDATE);
	}
	
    public static MetricResultTuple convertMetricNodeToMetricResultTuple(MetricNode metricNode, MetricEventType metricNodeEventType) {
		MetricResultTupleImpl rs = new MetricResultTupleImpl();

        if (metricNode != null) {
            rs.setCreatedTime(metricNode.getCreatedTime());
            rs.setUpdatedTime(metricNode.getLastModifiedTime());
            rs.setMetricKey((MetricKey) metricNode.getKey());
			for (String metricName : metricNode.getMetricNames()) {
				Metric<?> metric = metricNode.getMetric(metricName);
				rs.addMetric(metricName, metric);
                //TODO change this so that descriptor can be moved to metric node?
                rs.setMetricValueDescriptor(metric.getDescriptor());
			}
		}
		rs.setMetricEventType(metricNodeEventType);
		return rs;
	}
	
    public static MetricResultTuple convertMetricNodeToMetricResultTuple(MetricNodeEvent metricNodeEvent) {
        return convertMetricNodeToMetricResultTuple(metricNodeEvent.getMetricNode(), metricNodeEvent.getEventType());
	}
	
	public static Collection<QueryDef> createClearAlertsQuerys(Collection<String> alert_ids) throws Exception {
		AdminService adminService = ServiceProviderManager.getInstance().getAdminService();
		Collection<QueryDef> queryCollection = new ArrayList<QueryDef>();
		
		for (RtaSchema schema: adminService.getAllSchemas()) {
			if (schema.isSystemSchema()) {
				for(Cube cube: schema.getCubes()) {
					for(DimensionHierarchy dh : cube.getDimensionHierarchies()) {
						if (dh.getProperty(ATTR_IS_ALERT_HIERARCHY) != null && dh.getProperty(ATTR_IS_ALERT_HIERARCHY).equalsIgnoreCase("true")) {
							queryCollection.add(createAlertQuery(schema, cube, dh, alert_ids));
						}
					}
				}
			}
		}
		
		return queryCollection;
	}

	private static QueryDef createAlertQuery(RtaSchema schema, Cube cube, DimensionHierarchy dh, Collection<String> alert_ids) throws Exception {		
		QueryFactory queryFac = QueryFactory.INSTANCE;

		QueryByFilterDef queryDef = queryFac.newQueryByFilterDef(schema.getName(), cube.getName(), dh.getName(), null);
		Dimension d = dh.getDimension("alert_id");
		if (d != null) {			
			OrFilter orFilter = queryFac.newOrFilter();
			
			for (String alertID : alert_ids) {
				orFilter.addFilter(queryFac.newEqFilter(FilterKeyQualifier.DIMENSION_NAME, d.getName(), alertID));				
			}
			queryDef.setFilter(orFilter);
			return queryDef;
		}

		String err = String.format("No [alert_id] dimension present in hierarchy [%s] of Schema [%s]", dh.getName(), schema.getName());
		throw new Exception(err);

	}

}
