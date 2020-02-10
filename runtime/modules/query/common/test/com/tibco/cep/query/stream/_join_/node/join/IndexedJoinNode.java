package com.tibco.cep.query.stream._join_.node.join;

import com.tibco.cep.query.stream._join_.index.Operator;
import com.tibco.cep.query.stream._join_.node.Node;
import com.tibco.cep.query.stream._join_.node.index.IndexExtractor;
import com.tibco.cep.query.stream._join_.node.xform.SourceToMapTransformer;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.query.stream.util.ReusableIterator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/*
 * Author: Ashwin Jayaprakash Date: Aug 7, 2008 Time: 4:16:12 PM
 */
public class IndexedJoinNode implements JoinNode {
    protected final IndexExtractor leftKeyExtractor;

    protected final IndexExtractor rightKeyExtractor;

    protected final Operator left2RightOperator;

    protected final Operator right2leftOperator;

    protected final FixedKeyHashMap<String, Tuple> leftAndRightMap;

    protected final SourceToMapTransformer<Tuple> leftRecordXformer;

    protected final SourceToMapTransformer<Tuple> rightRecordXformer;

    protected final JoinTupleProducer joinTupleProducer;

    protected final StopFilter stopFilter;

    protected final ArrayList<IndexedJoinBridgeNode> previousNodes;

    protected IndexedJoinBridgeNode previousLeft;

    protected IndexedJoinBridgeNode previousRight;

    protected Node<Tuple> next;

    /**
     * The same key cannot appear in both right and left sources.
     *
     * @param leftKeyExtractor
     * @param operator
     * @param rightKeyExtractor
     * @param leftRecordXformer
     * @param rightRecordXformer
     * @param joinTupleProducer
     * @param stopFilter         Can be <code>null</code>.
     */
    public IndexedJoinNode(IndexExtractor leftKeyExtractor,
                           Operator operator,
                           IndexExtractor rightKeyExtractor,
                           SourceToMapTransformer<Tuple> leftRecordXformer,
                           SourceToMapTransformer<Tuple> rightRecordXformer,
                           JoinTupleProducer joinTupleProducer,
                           StopFilter stopFilter) {
        this.leftKeyExtractor = leftKeyExtractor;
        this.rightKeyExtractor = rightKeyExtractor;

        this.left2RightOperator = Operator.findReverse(operator);
        this.right2leftOperator = operator;

        this.leftRecordXformer = leftRecordXformer;
        this.rightRecordXformer = rightRecordXformer;
        this.joinTupleProducer = joinTupleProducer;
        this.stopFilter = stopFilter;

        HashSet<String> leftAndRightKeys = new HashSet<String>();
        for (String key : leftRecordXformer.getKeys()) {
            leftAndRightKeys.add(key);
        }
        for (String key : rightRecordXformer.getKeys()) {
            leftAndRightKeys.add(key);
        }

        this.leftAndRightMap = new FixedKeyHashMap<String, Tuple>(leftAndRightKeys);

        this.leftRecordXformer.init(leftAndRightMap);
        this.rightRecordXformer.init(leftAndRightMap);

        this.joinTupleProducer.init(leftAndRightMap);

        this.previousNodes = new ArrayList<IndexedJoinBridgeNode>(2);
    }

    public SourceToMapTransformer<Tuple> getLeftRecordXformer() {
        return leftRecordXformer;
    }

    public SourceToMapTransformer<Tuple> getRightRecordXformer() {
        return rightRecordXformer;
    }

    public ExpressionEvaluator getLeftKeyExtractor() {
        return leftKeyExtractor;
    }

    public ExpressionEvaluator getRightKeyExtractor() {
        return rightKeyExtractor;
    }

    public Operator getLeft2RightOperator() {
        return left2RightOperator;
    }

    public Operator getRight2leftOperator() {
        return right2leftOperator;
    }

    public List<IndexedJoinBridgeNode> getPrevious() {
        return previousNodes;
    }

    public void setPreviousLeft(IndexedJoinBridgeNode previousLeft) {
        this.previousLeft = previousLeft;

        previousNodes.add(0, previousLeft);
    }

    public void setPreviousRight(IndexedJoinBridgeNode previousRight) {
        this.previousRight = previousRight;

        previousNodes.add(1, previousRight);
    }

    public Node<Tuple> getNext() {
        return next;
    }

    public void setNext(Node<Tuple> nextNode) {
        next = nextNode;
    }

    /**
     * Never used.
     *
     * @param globalContext
     * @param queryContext
     * @param t
     */
    public final void acceptNew(GlobalContext globalContext, QueryContext queryContext, Tuple t) {
        //Never used.
    }

    /**
     * Never used.
     *
     * @param globalContext
     * @param queryContext
     * @param t
     */
    public final void acceptDelete(GlobalContext globalContext, QueryContext queryContext,
                                   Tuple t) {
        //Never used.
    }

    protected void acceptLeft(GlobalContext globalContext, QueryContext queryContext, Tuple i) {
        leftRecordXformer.copyTransform(i, leftAndRightMap);

        Comparable key = leftKeyExtractor.evaluate(globalContext, queryContext, leftAndRightMap);

        Object rightResults =
                previousRight.fetch(globalContext, queryContext, left2RightOperator, key);

        if (stopFilter != null) {
            stopFilter.startSession(globalContext, queryContext);
        }
        handleLeftToRightResults(globalContext, queryContext, i, rightResults);
        if (stopFilter != null) {
            stopFilter.endSession(globalContext, queryContext);
        }

        leftAndRightMap.clear();
    }

    protected void acceptRight(GlobalContext globalContext, QueryContext queryContext, Tuple i) {
        rightRecordXformer.copyTransform(i, leftAndRightMap);

        Comparable key = rightKeyExtractor.evaluate(globalContext, queryContext, leftAndRightMap);

        Object leftResults =
                previousLeft.fetch(globalContext, queryContext, right2leftOperator, key);

        if (stopFilter != null) {
            stopFilter.startSession(globalContext, queryContext);
        }
        handleRightToLeftResults(globalContext, queryContext, leftResults, i);
        if (stopFilter != null) {
            stopFilter.endSession(globalContext, queryContext);
        }

        leftAndRightMap.clear();
    }

    /**
     * @param globalContext
     * @param queryContext
     * @param recordXformer
     * @param contributingTuple
     * @return The joined tuple.
     */
    private Tuple doJoin(GlobalContext globalContext, QueryContext queryContext,
                         SourceToMapTransformer<Tuple> recordXformer,
                         Tuple contributingTuple) {
        recordXformer.copyTransform(contributingTuple, leftAndRightMap);

        Tuple joinedResult = (Tuple) joinTupleProducer
                .evaluate(globalContext, queryContext, leftAndRightMap);

        next.acceptNew(globalContext, queryContext, joinedResult);

        return joinedResult;
    }

    /**
     * @return <code>false</code> by default.
     */
    protected boolean acceptLeftToRightIfRightIsNull() {
        return false;
    }

    /**
     * @return <code>false</code> by default.
     */
    protected boolean acceptRightToLeftIfLeftIsNull() {
        return false;
    }

    protected void handleLeftToRightResults(GlobalContext globalContext, QueryContext queryContext,
                                            Tuple left, Object right) {
        if (right instanceof ReusableIterator) {
            ReusableIterator<Tuple> rightResults = (ReusableIterator<Tuple>) right;

            for (rightResults.reset(); rightResults.hasNext();) {
                Tuple rightTuple = rightResults.next();

                Tuple joinedResult =
                        doJoin(globalContext, queryContext, rightRecordXformer, rightTuple);

                if (stopFilter != null &&
                        !stopFilter.continueJoins(globalContext, queryContext, joinedResult)) {
                    return;
                }
            }
        }
        else {
            if (right != null || acceptLeftToRightIfRightIsNull()) {
                Tuple rightTuple = (Tuple) right;

                doJoin(globalContext, queryContext, rightRecordXformer, rightTuple);
            }
        }
    }

    protected void handleRightToLeftResults(GlobalContext globalContext, QueryContext queryContext,
                                            Object left, Tuple right) {
        if (left instanceof ReusableIterator) {
            ReusableIterator<Tuple> leftResults = (ReusableIterator<Tuple>) left;

            for (leftResults.reset(); leftResults.hasNext();) {
                Tuple leftTuple = leftResults.next();

                Tuple joinedResult =
                        doJoin(globalContext, queryContext, leftRecordXformer, leftTuple);

                if (stopFilter != null &&
                        !stopFilter.continueJoins(globalContext, queryContext, joinedResult)) {
                    return;
                }
            }
        }
        else {
            if (left != null || acceptRightToLeftIfLeftIsNull()) {
                Tuple leftTuple = (Tuple) left;

                doJoin(globalContext, queryContext, leftRecordXformer, leftTuple);
            }
        }
    }
}
