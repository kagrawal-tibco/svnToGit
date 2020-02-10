package com.tibco.store.query.exec.impl;

import com.tibco.store.query.exec.AbstractCostBasedPredicateEvaluator;
import com.tibco.store.query.exec.BinaryPredicateEvaluator;
import com.tibco.store.query.exec.CostBasedPredicateEvaluator;
import com.tibco.store.query.exec.PredicateEvaluator;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 15/12/13
 * Time: 12:23 PM
 *
 * TODO Needs some proper refactoring.
 */
public class CostBasedBinaryPredicateEvaluator extends AbstractCostBasedPredicateEvaluator<BinaryPredicateEvaluator<CostBasedPredicateEvaluator>> implements CostBasedPredicateEvaluator, BinaryPredicateEvaluator<CostBasedPredicateEvaluator> {

    protected PredicateEvaluatorContainer<CostBasedPredicateEvaluator> predicateEvaluatorContainer;

    public CostBasedBinaryPredicateEvaluator(BinaryPredicateEvaluator<CostBasedPredicateEvaluator> wrappedPredicateEvaluator) {
        super(wrappedPredicateEvaluator);
        predicateEvaluatorContainer = new PredicateEvaluatorContainer<CostBasedPredicateEvaluator>();
    }

    @Override
    public void computeCost() {
        Number leftCost;
        Number rightCost;

        leftCost = predicateEvaluatorContainer.getLeftChild().getEvaluationCost();
        //TODO Handle data type for number properly
        rightCost = predicateEvaluatorContainer.getRightChild().getEvaluationCost();

        cost = leftCost.doubleValue() + rightCost.doubleValue();
    }


    @Override
    public boolean addChildPredicateEvaluator(PredicateEvaluator childPredicateEvaluator) {
        boolean added = predicateEvaluatorContainer.addChildPredicateEvaluator(childPredicateEvaluator);
        if (added) {
            //Also set its parent
            if (childPredicateEvaluator instanceof CostBasedPredicateEvaluator) {
                ((CostBasedPredicateEvaluator)childPredicateEvaluator).setParentPredicateEvaluator(this);
            }
            //Compute cost of this evaluator
            if (isFull()) {
                computeCost();
            }
        }
        return added;
    }

    /**
     * Return true is both left and right child are non-null.
     * @return
     */
    @Override
    public boolean isFull() {
        return predicateEvaluatorContainer.isFull();
    }

    @Override
    public CostBasedPredicateEvaluator getLeftChild() {
        return predicateEvaluatorContainer.getLeftChild();
    }

    @Override
    public CostBasedPredicateEvaluator getRightChild() {
        return predicateEvaluatorContainer.getRightChild();
    }
}
