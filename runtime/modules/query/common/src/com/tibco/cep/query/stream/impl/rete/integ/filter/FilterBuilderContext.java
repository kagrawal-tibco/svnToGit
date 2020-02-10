package com.tibco.cep.query.stream.impl.rete.integ.filter;

import com.tibco.cep.query.stream.expression.ExpressionEvaluator;

/*
* Author: Karthikeyan Subramanian / Date: Apr 5, 2010 / Time: 3:39:40 PM
*/
public interface FilterBuilderContext<F> {
    
    void buildFilters(ExpressionEvaluator evaluator);

    F getFilter();
}
