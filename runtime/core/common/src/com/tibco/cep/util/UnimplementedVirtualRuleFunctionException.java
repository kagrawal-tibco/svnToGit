package com.tibco.cep.util;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Apr 16, 2008
 * Time: 7:33:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class UnimplementedVirtualRuleFunctionException extends RuntimeException {
    public UnimplementedVirtualRuleFunctionException(String functionName) {
        super(functionName + " is an unimplemented virtual rule function and cannot be invoked until implemented");
    }
}
