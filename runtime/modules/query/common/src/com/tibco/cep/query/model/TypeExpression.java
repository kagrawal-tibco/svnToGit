package com.tibco.cep.query.model;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Jul 8, 2008
* Time: 5:02:55 PM
*/
public interface TypeExpression extends Expression {

    /**
     * Gets the TypeInfo of the instances of the type represented by this expression.
     * @return TypeInfo of the instances of the type represented by this expression.
     */
    TypeInfo getInstancesTypeInfo();    

}
