package com.tibco.cep.query.model.visitor.filter;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 9, 2007
 * Time: 8:59:01 AM
 * To change this template use File | Settings | File Templates.
 */

public interface UnaryProcedure extends Serializable {

    void run(Object arg) throws Exception;

}
