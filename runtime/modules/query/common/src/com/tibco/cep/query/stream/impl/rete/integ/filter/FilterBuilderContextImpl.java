package com.tibco.cep.query.stream.impl.rete.integ.filter;

import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.query.stream.expression.ExpressionEvaluator;

/*
* Author: Karthikeyan Subramanian / Date: Apr 5, 2010 / Time: 4:34:48 PM
*/
public class FilterBuilderContextImpl<F> implements FilterBuilderContext<F> {

    protected F filter = null;
    protected FilterStrategy<F> strategy;
    protected ExpressionEvaluator root;

    public FilterBuilderContextImpl(FilterStrategy<F> strategy) {
        this.strategy = strategy;
    }

    @Override
    public void buildFilters(ExpressionEvaluator evaluator) {
        root = evaluator;
        filter = buildLogicalEvaluatorFilter(evaluator);
        if(filter == null) {
            filter = buildRelationalEvaluatorFilter(evaluator);
        }
        if(filter == null) {
            filter = buildMiscellaneousFilter(evaluator);
        }
    }

    public F getFilter() {
        List<F> filters = new LinkedList<F>();
        filters.add(filter);
        return strategy.bundleFilters(filters);
    }

    private F buildLogicalEvaluatorFilter(ExpressionEvaluator evaluator) {
        return strategy.buildLogicalFilter(evaluator);
    }

    private F buildRelationalEvaluatorFilter(ExpressionEvaluator evaluator) {
        return strategy.buildComparisonFilter(evaluator);
    }

    private F buildMiscellaneousFilter(ExpressionEvaluator evaluator) {
        return strategy.buildMiscellaneousFilter(evaluator);
    }
}
