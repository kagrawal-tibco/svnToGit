package com.tibco.store.query.exec;

import com.tibco.store.query.exec.util.PredicateConstants;
import com.tibco.store.query.model.Predicate;
import com.tibco.store.query.model.ResultStream;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/12/13
 * Time: 11:06 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractCostBasedPredicateEvaluator<P extends PredicateEvaluator> implements CostBasedPredicateEvaluator {

    protected P wrappedPredicateEvaluator;

    protected CostBasedPredicateEvaluator parentPredicateEvaluator;

    protected Number cost;

    protected AbstractCostBasedPredicateEvaluator(P wrappedPredicateEvaluator) {
        this.wrappedPredicateEvaluator = wrappedPredicateEvaluator;
        cost = PredicateConstants.DEFAULT_COST;
    }


    @Override
    public <R extends ResultStream> R eval(R... inputResultStreams) {
        return wrappedPredicateEvaluator.eval(inputResultStreams);
    }

    @Override
    public boolean addChildPredicateEvaluator(PredicateEvaluator childPredicateEvaluator) {
        return wrappedPredicateEvaluator.addChildPredicateEvaluator(childPredicateEvaluator);
    }

    @Override
    public boolean isFull() {
        return wrappedPredicateEvaluator.isFull();
    }

    @Override
    public Number getEvaluationCost() {
        return cost;
    }


    public void setParentPredicateEvaluator(CostBasedPredicateEvaluator parentPredicateEvaluator) {
        this.parentPredicateEvaluator = parentPredicateEvaluator;
    }

    @Override
    public <P extends Predicate> P getWrappedPredicate() {
        return wrappedPredicateEvaluator.getWrappedPredicate();
    }

    @Override
    public int getNumOperands() {
        return wrappedPredicateEvaluator.getNumOperands();
    }
}
