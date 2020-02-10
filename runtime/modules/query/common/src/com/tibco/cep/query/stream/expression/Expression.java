package com.tibco.cep.query.stream.expression;

import java.util.Map;

import com.tibco.cep.query.stream.tuple.Tuple;

/*
* Author: Ashwin Jayaprakash Date: Apr 6, 2008 Time: 12:05:53 AM
*/
public interface Expression {
    /**
     * @return The Stream aliases and their types used in this expression.
     */
    public Map<String, Class<? extends Tuple>> getAliasAndTypes();
}
