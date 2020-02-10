package com.tibco.cep.query.service;

import org.antlr.runtime.tree.CommonTree;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.model.QueryModel;

/*
 * User: pdhar
 * Date: Jul 8, 2007
 * Time: 9:03:42 PM
 */

public interface QueryModelFactory {

    QueryModel createModel(CommonTree ast) throws Exception;

    Class getEntityClass(String entityPath);

    QueryServiceProvider getQueryServiceProvider();

    Logger getLogger();
}
