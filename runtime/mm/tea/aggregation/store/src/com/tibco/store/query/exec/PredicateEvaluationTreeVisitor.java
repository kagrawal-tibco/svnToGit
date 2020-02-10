package com.tibco.store.query.exec;

import com.tibco.store.query.model.ResultStream;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 29/11/13
 * Time: 10:41 AM
 * <p/>
 * Hierarchical Visitor for visiting predicate tree nodes during traversal.
 *
 */
public interface PredicateEvaluationTreeVisitor {


    public <R extends ResultStream, P extends RelationalPredicateEvaluator> void visit(P relationalPredicateEvaluator, R... resultStreams);


    public <R extends ResultStream, P extends JoinPredicateEvaluator> void visit(P joinPredicateEvaluator, R... resultStreams);


    public <R extends ResultStream, P extends FilterPredicateEvaluator<?>> void visit(P filterPredicateEvaluator, R... resultStreams);


    public <R extends ResultStream, P extends BinaryPredicateEvaluator<?>> void visit(P binaryPredicateEvaluator, R... resultStreams);

    /**
     * Once tree is fully evaluated return the final result stream.
     *
     * @param <R>
     * @return
     */
    public <R extends ResultStream> R getResult();
}
