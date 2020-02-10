package com.tibco.rta.service.persistence.as;

import com.tibco.as.space.browser.BrowserDef;
import com.tibco.as.space.browser.BrowserDef.BrowserType;
import com.tibco.as.space.browser.BrowserDef.DistributionScope;
import com.tibco.as.space.browser.BrowserDef.TimeScope;
import com.tibco.rta.MetricKey;
import com.tibco.rta.log.Level;
import com.tibco.rta.query.QueryByKeyDef;
import com.tibco.rta.runtime.model.MetricNode;

class KeyBasedMetricNodeBrowser extends AbstractMetricNodeBrowser implements com.tibco.rta.query.Browser<MetricNode> {

    QueryByKeyDef queryDef;
    MetricKey mKey;

    public KeyBasedMetricNodeBrowser(QueryByKeyDef queryDef, ASPersistenceService pServ) throws Exception {

        super(pServ, queryDef.getQueryKey().getSchemaName(),
                queryDef.getQueryKey().getCubeName(),
                queryDef.getQueryKey().getDimensionHierarchyName());
        this.queryDef = queryDef;
        mKey = queryDef.getQueryKey();


        BrowserType browserType = BrowserType.GET;
        BrowserDef def = BrowserDef.create();
        def.setDistributionScope(DistributionScope.ALL);
        def.setTimeScope(TimeScope.SNAPSHOT);

        String query;

        StringBuilder strBldr = new StringBuilder();
//        strBldr.append(ASPersistenceService.DIMHR_NAME);
//        strBldr.append(" = \"");
//        strBldr.append(hierarchyName);
//        strBldr.append("\"");

        if (mKey.getDimensionLevelName() != null) {
//            strBldr.append(" AND ");
            strBldr.append(ASPersistenceService.DIMENSION_LEVEL_NAME_FIELD);
            strBldr.append(" = ");
            strBldr.append("\"");
            strBldr.append(mKey.getDimensionLevelName());
            strBldr.append("\"");
        }

        for (String dimName : mKey.getDimensionNames()) {
            Object value = mKey.getDimensionValue(dimName);
            if (value instanceof String) {
                strBldr.append(" and ");
                strBldr.append(dimName);
                strBldr.append(" = \"");
                strBldr.append(value);
                strBldr.append("\"");
            } else if (value != null) {
                strBldr.append(" and ");
                strBldr.append(dimName);
                strBldr.append(" = ");
                strBldr.append(value);
            } else {
                strBldr.append(" and ");
                strBldr.append(dimName);
                strBldr.append(" is null");
            }
        }
        query = strBldr.toString();
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Making query : %s", query);
        }
        browser = space.browse(browserType, def, query);
    }
}
