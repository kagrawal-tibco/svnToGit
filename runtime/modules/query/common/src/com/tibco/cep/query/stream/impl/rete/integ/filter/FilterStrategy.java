package com.tibco.cep.query.stream.impl.rete.integ.filter;

import java.util.Collection;

import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;

/*
* Author: Karthikeyan Subramanian / Date: Apr 5, 2010 / Time: 3:37:24 PM
*/
public interface FilterStrategy<F> {

    void resolveBindVariables(QueryContext queryContext) throws Exception;

    /**
     * Build comparison filters - equals, not equals, greater than, lesser than
     * greater than equals, lesser than equals, In, Between, etc
     * @param expressionEvaluator
     * @return filter
     */
    F buildComparisonFilter(ExpressionEvaluator expressionEvaluator);

    /**
     * Build logical filters - and, or, not
     * @param expressionEvaluator
     * @return filter
     */
    F buildLogicalFilter(ExpressionEvaluator expressionEvaluator);

    /**
     * Build Miscellaneous Filters - Like, etc
     * @param expressionEvaluator
     * @return filter
     */
    F buildMiscellaneousFilter(ExpressionEvaluator expressionEvaluator);

    /**
     * Bundle all the filters into a single filter.
     * @param innerFilters
     * @return bundled filters
     */
    F bundleFilters(Collection<F> innerFilters);
}
