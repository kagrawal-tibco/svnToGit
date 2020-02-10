package com.tibco.cep.query.stream._join2_.api;

/*
* Author: Ashwin Jayaprakash Date: May 28, 2009 Time: 5:49:58 PM
*/
public interface Evaluator<I, O> {
    O evaluate(I input);
}
