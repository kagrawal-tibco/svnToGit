package com.tibco.store.query.exec.impl;

import com.tibco.store.query.exec.CostBasedPredicateEvaluator;
import com.tibco.store.query.exec.PredicateEvaluator;
import com.tibco.store.query.exec.RelationalPredicateEvaluator;
import com.tibco.store.query.model.LogicalPredicate;
import com.tibco.store.query.model.RelationalPredicate;
import com.tibco.store.query.model.impl.AndPredicate;
import com.tibco.store.query.model.impl.OrPredicate;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/12/13
 * Time: 7:53 PM
 *
 * Slight changes from default while visiting cost based predicate tree.
 */
public class CostBasedPredicateTreeVisitor extends SimplePredicateTreeVisitor {


    @Override
    public void visit(RelationalPredicate relationalPredicate) {
        RelationalPredicateEvaluator relationalPredicateEvaluator = new SimpleRelationalPredicateEvaluator(relationalPredicate);

        processPredicateEvaluator(new CostBasedRelationalPredicateEvaluator(relationalPredicateEvaluator));
    }

    @Override
    protected <L extends LogicalPredicate> void processLogicalPredicate(L logicalPredicate) {
        PredicateEvaluator predicateEvaluator = null;
        if (logicalPredicate instanceof AndPredicate) {
            SimpleFilterPredicateEvaluator<CostBasedPredicateEvaluator> realPredicateEvaluator = new SimpleFilterPredicateEvaluator<CostBasedPredicateEvaluator>(logicalPredicate);
            predicateEvaluator = new CostBasedFilterPredicateEvaluator(realPredicateEvaluator);
        } else if (logicalPredicate instanceof OrPredicate) {
            SimpleBinaryPredicateEvaluator<CostBasedPredicateEvaluator> realPredicateEvaluator = new SimpleBinaryPredicateEvaluator<CostBasedPredicateEvaluator>(logicalPredicate);
            predicateEvaluator = new CostBasedBinaryPredicateEvaluator(realPredicateEvaluator);
        }
        processPredicateEvaluator(predicateEvaluator);
    }
}
