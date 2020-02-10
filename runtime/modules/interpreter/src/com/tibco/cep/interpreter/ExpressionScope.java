package com.tibco.cep.interpreter;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * User: nprade
 * Date: 1/23/12
 * Time: 6:17 PM
 */
public class ExpressionScope
    extends LinkedHashMap<String, Class> {


    public ExpressionScope() {
    }


    public ExpressionScope(int initialCapacity) {
        super(initialCapacity);
    }


    public ExpressionScope(
            int initialCapacity,
            float loadFactor) {
        super(initialCapacity, loadFactor);
    }


    public ExpressionScope(
            int initialCapacity,
            float loadFactor,
            boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }


    public ExpressionScope(Map<? extends String, ? extends Class> m) {
        super(m);
    }

}
