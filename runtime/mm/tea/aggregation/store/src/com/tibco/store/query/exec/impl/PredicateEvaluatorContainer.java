package com.tibco.store.query.exec.impl;

import com.tibco.store.query.exec.PredicateEvaluator;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/12/13
 * Time: 11:40 AM
 *
 * Indirection meant to hold children of binary evaluators.
 */
public class PredicateEvaluatorContainer<P extends PredicateEvaluator> {

    private P leftChild;

    private P rightChild;

    public P getLeftChild() {
        return leftChild;
    }

    public P getRightChild() {
        return rightChild;
    }

    public boolean isFull() {
        return leftChild != null && rightChild != null;
    }

    @SuppressWarnings("unchecked")
    public boolean addChildPredicateEvaluator(PredicateEvaluator childPredicateEvaluator) {
        if (isFull()) {
            return false;
        }
        //See if left child is empty and add there followed by right child
        if (leftChild == null) {
            leftChild = (P) childPredicateEvaluator;
        } else if (rightChild == null) {
            rightChild = (P) childPredicateEvaluator;
        } else {
            throw new IllegalArgumentException("Predicate evaluator cannot have any more children");
        }
        return true;
    }

    public void clear() {
        leftChild = null;
        rightChild = null;
    }
}
