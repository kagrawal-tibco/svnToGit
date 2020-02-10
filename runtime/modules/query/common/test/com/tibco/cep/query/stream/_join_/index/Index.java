package com.tibco.cep.query.stream._join_.index;

import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;

/*
* Author: Ashwin Jayaprakash Date: Aug 4, 2008 Time: 4:21:03 PM
*/
public interface Index<K extends Comparable, V, N> {
    boolean supportsOperator(Operator operator);

    void record(K key, V value, N idOfValue);

    void erase(K key, N idOfValue);

    /**
     * @param globalContext
     * @param queryContext
     * @param operator
     * @param key           Will be <code>null</code> for {@link com.tibco.cep.query.stream._join_.index.Operator#ALL}.
     * @return A single value {@link V} or {@link com.tibco.cep.query.stream.util.ReusableIterator}
     *         of {@link V}. No <code>null</code>s are returned unless the mapping itself contains a
     *         <code>null</code>.
     * @throws UnsupportedOperationException if the wrong operator is used - {@link
     *                                       #supportsOperator(Operator)}.
     */
    Object fetch(GlobalContext globalContext, QueryContext queryContext, Operator operator, K key);
}
