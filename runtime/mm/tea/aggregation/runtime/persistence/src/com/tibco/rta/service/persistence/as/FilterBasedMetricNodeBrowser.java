package com.tibco.rta.service.persistence.as;

import com.tibco.as.space.browser.BrowserDef;
import com.tibco.as.space.browser.BrowserDef.BrowserType;
import com.tibco.as.space.browser.BrowserDef.DistributionScope;
import com.tibco.as.space.browser.BrowserDef.TimeScope;
import com.tibco.rta.log.Level;
import com.tibco.rta.query.FilterKeyQualifier;
import com.tibco.rta.query.MetricQualifier;
import com.tibco.rta.query.QueryByFilterDef;
import com.tibco.rta.query.filter.AndFilter;
import com.tibco.rta.query.filter.EqFilter;
import com.tibco.rta.query.filter.Filter;
import com.tibco.rta.query.filter.GEFilter;
import com.tibco.rta.query.filter.GtFilter;
import com.tibco.rta.query.filter.LEFilter;
import com.tibco.rta.query.filter.LogicalFilter;
import com.tibco.rta.query.filter.LtFilter;
import com.tibco.rta.query.filter.NEqFilter;
import com.tibco.rta.query.filter.NotFilter;
import com.tibco.rta.query.filter.OrFilter;
import com.tibco.rta.query.filter.RelationalFilter;

class FilterBasedMetricNodeBrowser extends AbstractMetricNodeBrowser {

    QueryByFilterDef queryDef;
    Filter filter;


    FilterBasedMetricNodeBrowser(QueryByFilterDef queryDef, ASPersistenceService pServ) throws Exception {
        super(pServ, queryDef.getSchemaName(), queryDef.getCubeName(), queryDef.getHierarchyName());
        this.queryDef = queryDef;

        filter = queryDef.getFilter();

        BrowserType browserType = BrowserType.GET;
        BrowserDef def = BrowserDef.create();
        def.setDistributionScope(DistributionScope.ALL);
        def.setTimeScope(TimeScope.SNAPSHOT);

        String query = filterToString(filter);

        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Making query %s ", query);
        }

        browser = space.browse(browserType, def, query);

    }


    public String filterToString(Filter filter) {
        StringBuilder sb = new StringBuilder();

        if (filter instanceof LogicalFilter) {
            LogicalFilter lf = (LogicalFilter) filter;
            String keyword = null;
            if (filter instanceof AndFilter) {
                keyword = " AND ";
            } else if (filter instanceof OrFilter) {
                keyword = " OR ";
            }

            Filter[] filters = lf.getFilters();

            if (filters.length == 0) {
                return "";
            } else if (filters.length == 1) {
                return " ( " + filterToString(filters[0]) + " ) ";
            } else {
                sb.append(" ( ");
                sb.append(filterToString(filters[0]));
                sb.append( " ) ");
                for (int i = 1; i < filters.length; i++) {
                    sb.append(keyword);
                    sb.append(" ( ");
                    sb.append(filterToString(filters[i]));
                    sb.append( " ) ");
                }
                return sb.toString();
            }

        } else if (filter instanceof RelationalFilter) {

            RelationalFilter rf = (RelationalFilter) filter;
            MetricQualifier mq = rf.getMetricQualifier();
            FilterKeyQualifier fq = rf.getKeyQualifier();
            String key = rf.getKey();
            Object value = rf.getValue();

            String operator = null;
            if (rf instanceof EqFilter) {
                operator = " = ";
            } else if (rf instanceof NEqFilter) {
                operator = " != ";
            } else if (rf instanceof GEFilter) {
                operator = " >= ";
            } else if (rf instanceof GtFilter) {
                operator = " > ";
            } else if (rf instanceof LEFilter) {
                operator = " <= ";
            } else if (rf instanceof LtFilter) {
                operator = " < ";
            }

            String escStr = "";

            if (mq != null) {
                if (mq.equals(MetricQualifier.DIMENSION_LEVEL)) {
                    escStr = "\"";
                    if (value != null) {
                        sb.append(" DIMENSION_LEVEL").append(operator).append(escStr).append(value).append(escStr);
                    } else {
                        sb.append(" DIMENSION_LEVEL is null ");
                    }
                } else {
                    //TODO??
                }
            } else if (fq != null) {
                if (fq.equals(FilterKeyQualifier.DIMENSION_NAME)) {
                    if (value instanceof String) {
                        escStr = "\"";
                    }
                } else if (fq.equals(FilterKeyQualifier.MEASUREMENT_NAME)) {
//                    key = key + ASPersistenceService.METRICVALUE_SUFFIX;
                    key = key;
                }

                if (value != null) {
                    sb.append(" ").append(key).append(operator).append(escStr).append(value).append(escStr);
                } else {
                    sb.append(" ").append(key).append(" is null ");
                }
            }

            return sb.toString();
        }
        else if(filter instanceof NotFilter) {
        	NotFilter notFilter = (NotFilter) filter;
            Filter bFilter = notFilter.getBaseFilter();

            if (bFilter == null) {
                return "";
            } else {
                return " NOT ( " + filterToString(bFilter) + " ) ";
            }
        }

        return sb.toString();
    }

}
