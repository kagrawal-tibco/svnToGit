package com.tibco.cep.query.model.visitor.filter;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Sep 9, 2007
 * Time: 8:57:34 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Function extends java.io.Serializable {
    Object eval();
}
