package com.tibco.store.query.exec;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/12/13
 * Time: 10:57 AM
 * To change this template use File | Settings | File Templates.
 */
public interface BinaryPredicateEvaluator<P extends PredicateEvaluator> extends PredicateEvaluator {

    public P getLeftChild();

    public P getRightChild();
}
