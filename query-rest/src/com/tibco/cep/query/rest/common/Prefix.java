package com.tibco.cep.query.rest.common;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 2/13/14
 * Time: 9:03 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Prefix {
    String GREATER_THAN = ">";//"%3E";//    : >
    String GREATER_THAN_EQUAL = ">=";//"%3E%3D";//: >=
    String LESS_THAN = "<";//"%3C"; //    : <
    String LESS_THAN_EQUAL = "<=";//"%3C%3D"; // : <=
    String ESCAPE_CHAR = "";//"%5C";
    String EQUALS = "=";
}
