package com.tibco.cep.query.stream._join2_.api;

/*
* Author: Ashwin Jayaprakash Date: May 28, 2009 Time: 7:22:48 PM
*/
public interface Joiner<L extends View, R extends View> {
    Combiner getCombiner();

    void attemptJoin(L lhs, R rhs);
}
