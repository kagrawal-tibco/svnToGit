package com.tibco.store.query.exec.impl;

import com.tibco.store.query.exec.CostBasedPredicateEvaluator;
import com.tibco.store.query.exec.FilterPredicateEvaluator;
import com.tibco.store.query.exec.util.PredicateConstants;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 16/12/13
 * Time: 11:02 AM
 *
 *
 */
public class CostBasedFilterPredicateEvaluator extends CostBasedBinaryPredicateEvaluator implements CostBasedPredicateEvaluator, FilterPredicateEvaluator<CostBasedPredicateEvaluator> {

    public CostBasedFilterPredicateEvaluator(SimpleFilterPredicateEvaluator<CostBasedPredicateEvaluator> wrappedPredicateEvaluator) {
        super(wrappedPredicateEvaluator);
    }

    @Override
    public void computeCost() {
        Number leftCost;
        Number rightCost;

        CostBasedPredicateEvaluator leftChild = predicateEvaluatorContainer.getLeftChild();
        CostBasedPredicateEvaluator rightChild = predicateEvaluatorContainer.getRightChild();

        leftCost = (leftChild != null) ? leftChild.getEvaluationCost() : PredicateConstants.DEFAULT_COST;
        //TODO Handle data type for number properly
        rightCost = (rightChild != null) ? rightChild.getEvaluationCost() : PredicateConstants.DEFAULT_COST;

        compareAndSwap(leftCost, rightCost);
        //Maximum of the 2 is the actual cost
        cost = Math.min(leftCost.intValue(), rightCost.intValue());

        //Percolate cost to parent
        percolateCostUp();
    }

    private void compareAndSwap(Number leftCost, Number rightCost) {
        if (leftCost.doubleValue() > rightCost.doubleValue()) {
            //Swap
            CostBasedPredicateEvaluator leftChild = predicateEvaluatorContainer.getLeftChild();
            CostBasedPredicateEvaluator rightChild = predicateEvaluatorContainer.getRightChild();
            predicateEvaluatorContainer.clear();

            predicateEvaluatorContainer.addChildPredicateEvaluator(rightChild);
            predicateEvaluatorContainer.addChildPredicateEvaluator(leftChild);
        }
    }

    private void percolateCostUp() {
        if (parentPredicateEvaluator != null) {
            parentPredicateEvaluator.computeCost();
        }
    }
}
