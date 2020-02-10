package com.tibco.rta.client.utils;

import com.tibco.rta.RtaException;
import com.tibco.rta.model.Cube;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DimensionHierarchy;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.QueryDef;
import com.tibco.rta.query.filter.OrFilter;
import com.tibco.rta.query.impl.QueryFactory;

import java.util.ArrayList;
import java.util.Collection;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_IS_ALERT_HIERARCHY;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 28/3/14
 * Time: 11:02 AM
 * To change this template use File | Settings | File Templates.
 */
public final class SessionUtils {

    public static Collection<QueryDef> createClearAlertsQuerys(Collection<RtaSchema> schemas, Collection<String> alert_ids) throws RtaException {
        Collection<QueryDef> queryCollection = new ArrayList<QueryDef>();

        for (RtaSchema schema : schemas) {
            if (schema.isSystemSchema()) {
                for (Cube cube : schema.getCubes()) {
                    for (DimensionHierarchy dh : cube.getDimensionHierarchies()) {
                        if (dh.getProperty(ATTR_IS_ALERT_HIERARCHY) != null && dh.getProperty(ATTR_IS_ALERT_HIERARCHY).equalsIgnoreCase("true")) {
                            queryCollection.add(createAlertQuery(schema, cube, dh, alert_ids));
                        }
                    }
                }
            }
        }
        return queryCollection;
    }

    private static QueryDef createAlertQuery(RtaSchema schema, Cube cube, DimensionHierarchy dh, Collection<String> alert_ids) throws RtaException {
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
        throw new RtaException(err);

    }

    public static Double getUpdatedVersion(String version) {
        Double ver = Double.parseDouble(version);
        ver = ver + 1;
        return ver;
    }
}
