package com.tibco.store.query.exec;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 15/12/13
 * Time: 12:22 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CostBasedPredicateEvaluator extends PredicateEvaluator {

    public Number getEvaluationCost();

    /**
     * TODO Should we really expose this?
     */
    public void computeCost();

    public void setParentPredicateEvaluator(CostBasedPredicateEvaluator parentPredicateEvaluator);
}
