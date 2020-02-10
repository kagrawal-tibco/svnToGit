package com.tibco.cep.query.stream.expression;

import java.util.Map;

import com.tibco.cep.query.stream.tuple.Tuple;

/*
 * Author: Ashwin Jayaprakash Date: Oct 26, 2007 Time: 11:19:33 AM
 */

public abstract class AbstractExpression implements Expression {
    protected final Map<String, Class<? extends Tuple>> aliasAndTypes;

    /**
     * @param aliasAndTypes Stream alias and the Tuple types.
     */
    public AbstractExpression(Map<String, Class<? extends Tuple>> aliasAndTypes) {
        this.aliasAndTypes = aliasAndTypes;
    }

    public Map<String, Class<? extends Tuple>> getAliasAndTypes() {
        return aliasAndTypes;
    }
}
