package com.tibco.cep.query.model;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 15, 2007
 * Time: 11:42:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface NullLiteral extends Literal {
    public static Object NULL_VALUE = new Object() {
        public String toString() {
            return "NULL";
        }
    };
    
}
