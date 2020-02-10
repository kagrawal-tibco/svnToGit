package com.tibco.cep.query.stream._join2_.api;

/*
* Author: Ashwin Jayaprakash Date: May 28, 2009 Time: 5:40:24 PM
*/
public interface Combiner<L, R> {
    Source[] getJoinSources();

    Object[] combine(L lhs, R rhs);
}
