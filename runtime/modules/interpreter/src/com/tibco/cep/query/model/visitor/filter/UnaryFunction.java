package com.tibco.cep.query.model.visitor.filter;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 9, 2007
 * Time: 8:58:21 AM
 * To change this template use File | Settings | File Templates.
 */
public interface UnaryFunction extends Serializable {

    Object eval( Object arg );
}
