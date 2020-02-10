package com.tibco.cep.query.service.impl;


import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.service.QueryModelFactory;
import com.tibco.cep.query.service.QueryServiceProvider;
import com.tibco.cep.runtime.model.TypeManager;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Jul 8, 2007
 * Time: 1:10:06 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractQueryModelFactory implements QueryModelFactory {

    protected QueryServiceProvider queryServiceProvider;
    protected Logger logger;

     public AbstractQueryModelFactory(QueryServiceProvider qsp) {
         this.queryServiceProvider = qsp;
         this.logger = qsp.getLogger(this.getClass());
    }

    public QueryServiceProvider getQueryServiceProvider() {
        return this.queryServiceProvider;
    }

    public Logger getLogger() {
        return this.logger;
    }

     public  Class getEntityClass(String entityPath) {
        final TypeManager tm = this.queryServiceProvider.getProjectContext().getRuleServiceProvider().getTypeManager();
        TypeManager.TypeDescriptor td = tm.getTypeDescriptor(entityPath);
        Class clazz = td.getImplClass();
        return clazz;
    }

}
