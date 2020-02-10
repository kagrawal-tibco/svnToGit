package com.tibco.store.query.exec.impl.invm;

import com.tibco.store.query.exec.BinaryPredicateEvaluator;
import com.tibco.store.query.exec.FilterPredicateEvaluator;
import com.tibco.store.query.exec.JoinPredicateEvaluator;
import com.tibco.store.query.exec.PredicateEvaluationTreeVisitor;
import com.tibco.store.query.exec.PredicateEvaluator;
import com.tibco.store.query.exec.RelationalPredicateEvaluator;
import com.tibco.store.query.model.ResultStream;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 10/12/13
 * Time: 11:15 AM
 * <p/>
 * The binary tree is visited in post-order form whereas only root at chain
 * is visited and the chain itself visits its links.
 */
public class DefaultPredicateEvaluationTreeVisitor implements PredicateEvaluationTreeVisitor {

    /**
     * Add results of every evaluation to the output stack.
     */
    protected SimpleEvaluationOutputStack<ResultStream> evaluationOutputStack = new SimpleEvaluationOutputStack<ResultStream>();


    @Override
    public <R extends ResultStream, P extends RelationalPredicateEvaluator> void visit(P relationalPredicateEvaluator, R... resultStreams) {
        //If it comes here it would be either because this was at the root
        //or because it was child of binary predicate evaluator. In both
        //cases the result would be pushed to the output stack.
        push(relationalPredicateEvaluator.eval(resultStreams));
    }

    @Override
    public <R extends ResultStream, P extends JoinPredicateEvaluator> void visit(P joinPredicateEvaluator, R... resultStreams) {
        push(joinPredicateEvaluator.eval(resultStreams));
    }

    @Override
    public <R extends ResultStream, P extends FilterPredicateEvaluator<?>> void visit(P filterPredicateEvaluator, R... resultStreams) {
        //Visit left child
        visitChild(filterPredicateEvaluator.getLeftChild(), resultStreams);
        //Now before visiting right child pop off top operand because that will be passed to next child
        ResultStream topResultStream = evaluationOutputStack.pop();
        //Visit right child
        visitChild(filterPredicateEvaluator.getRightChild(), topResultStream);
    }

    @Override
    public <R extends ResultStream, P extends BinaryPredicateEvaluator<?>> void visit(P binaryPredicateEvaluator, R... resultStreams) {
        //Visit left child
        visitChild(binaryPredicateEvaluator.getLeftChild(), resultStreams);
        //Visit right child
        visitChild(binaryPredicateEvaluator.getRightChild(), resultStreams);
        //Visit root
        visitBinaryPredicateEvaluator(binaryPredicateEvaluator);
    }

    protected <P extends PredicateEvaluator, R extends ResultStream> void visitChild(P childPredicateEvaluator, R... resultStreams) {
        if (childPredicateEvaluator != null) {
            if (childPredicateEvaluator instanceof RelationalPredicateEvaluator) {
                visit((RelationalPredicateEvaluator) childPredicateEvaluator, resultStreams);
            } else if (childPredicateEvaluator instanceof FilterPredicateEvaluator) {
                visit((FilterPredicateEvaluator) childPredicateEvaluator, resultStreams);
            } else {
                visit((BinaryPredicateEvaluator) childPredicateEvaluator, resultStreams);
            }
        }
    }

    private <P extends BinaryPredicateEvaluator<?>> void visitBinaryPredicateEvaluator(P binaryPredicateEvaluator) {
        ResultStream[] topStreams = getTopOperands(binaryPredicateEvaluator.getNumOperands());
        //Perform join/union or any other op
        ResultStream returnStream = binaryPredicateEvaluator.eval(topStreams);

        push(returnStream);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R extends ResultStream> R getResult() {
        return (R) evaluationOutputStack.pop();
    }

    protected void push(ResultStream resultStream) {
        //Add it to stack
        evaluationOutputStack.push(resultStream);
    }

    private ResultStream[] getTopOperands(int numberOfOperands) {
        Collection<ResultStream> topOperands = evaluationOutputStack.popTopEntries(numberOfOperands);
        return topOperands.toArray(new ResultStream[topOperands.size()]);
    }
}
