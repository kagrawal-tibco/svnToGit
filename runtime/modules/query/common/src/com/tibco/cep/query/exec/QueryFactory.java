package com.tibco.cep.query.exec;

import com.tibco.cep.query.model.QueryModel;
import com.tibco.cep.query.service.Query;

/**
 * Created by IntelliJ IDEA.
 * User: nprade
 * Date: Nov 5, 2007
 * Time: 4:02:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface QueryFactory {


    public Query createQuery(QueryModel model) throws Exception;


}
