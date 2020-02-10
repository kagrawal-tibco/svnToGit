package com.tibco.cep.query.stream._join2_.impl;

import com.tibco.cep.query.stream._join2_.api.Combiner;
import com.tibco.cep.query.stream._join2_.api.Source;

/*
* Author: Ashwin Jayaprakash Date: Jun 1, 2009 Time: 8:22:39 PM
*/
public class SimpleCombiner implements Combiner<Object, Object> {
    protected final boolean leftToRight;

    protected final Source[] joinSources;

    /**
     * @param leftToRight If <code>false</code>, then right-to-left.
     * @param joinSources
     */
    public SimpleCombiner(boolean leftToRight, Source... joinSources) {
        this.leftToRight = leftToRight;
        this.joinSources = joinSources;
    }

    public Source[] getJoinSources() {
        return joinSources;
    }

    public Object[] combine(Object lhs, Object rhs) {
        if (leftToRight) {
            return new Object[]{lhs, rhs};
        }

        return new Object[]{rhs, lhs};
    }
}
