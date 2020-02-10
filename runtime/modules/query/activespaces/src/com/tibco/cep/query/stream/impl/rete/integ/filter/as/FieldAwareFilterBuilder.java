package com.tibco.cep.query.stream.impl.rete.integ.filter.as;

import java.util.Properties;

import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.impl.expression.SimpleGlobalContext;
import com.tibco.cep.query.stream.impl.rete.expression.ReteEntityFilter;
import com.tibco.cep.query.stream.impl.rete.integ.filter.FilterBuilder;
import com.tibco.cep.runtime.service.dao.impl.tibas.ASConstants;
import com.tibco.cep.runtime.service.om.api.EntityDao;
import com.tibco.cep.runtime.service.om.api.Filter;
import com.tibco.cep.runtime.service.om.api.query.SqlFilter;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash / Date: 6/13/11 / Time: 4:32 PM
*/
public class FieldAwareFilterBuilder extends FilterBuilder {
    public FieldAwareFilterBuilder(ReteEntityFilter[] reteFilters, Class entityClass, EntityDao entityDao,
                                   SimpleGlobalContext simpleGlobalContext, QueryContext queryContext) {
        super(reteFilters, entityClass, entityDao, simpleGlobalContext, queryContext);
    }

    @Override
    protected Filter doBuild() {
        Properties properties = System.getProperties();
        String codecExplicitStr = properties.getProperty(SystemProperty.PROP_TUPLE_EXPLICIT.getPropertyName(), Boolean.FALSE.toString());
        boolean codecExplicit = Boolean.parseBoolean(codecExplicitStr);

        if (codecExplicit) {
            SqlFilter sqlFilter = AstToExplicitFieldQueryConvertor
                    .tryConvert(simpleGlobalContext, queryContext, entityClass, reteFilters[0] /*todo Collapse to 1*/);

            if (sqlFilter != null) {
                return sqlFilter;
            }
        }

        return super.doBuild();
    }
}
