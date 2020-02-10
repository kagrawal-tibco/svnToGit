package com.tibco.cep.query.model.visitor;

import com.tibco.cep.query.model.ModelContext;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 8, 2007
 * Time: 9:16:38 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ContextOperator {
    void visit( ModelContext node ) throws Exception;
}
