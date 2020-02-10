package com.tibco.cep.studio.dashboard.core.util;


/**
 * @
 *
 */
public class Assertion {

    public static void isNull(Object obj) {
        if(null == obj) {
            throw new IllegalArgumentException("A null is encountered where an object is expected");
        }
    }
}
