package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 18, 2007
 * Time: 3:48:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FieldList extends Expression {

    Expression getExpression(int index);

    int getSize();
    
}
