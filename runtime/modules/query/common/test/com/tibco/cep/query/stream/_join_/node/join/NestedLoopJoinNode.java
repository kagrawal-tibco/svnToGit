package com.tibco.cep.query.stream._join_.node.join;

import com.tibco.cep.query.stream._join_.node.Node;
import com.tibco.cep.query.stream._join_.node.xform.SourceToMapTransformer;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import com.tibco.cep.query.stream.util.ReusableIterator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/*
 * Author: Ashwin Jayaprakash Date: Aug 7, 2008 Time: 4:16:12 PM
 */

public class NestedLoopJoinNode implements JoinNode {
    protected final SourceToMapTransformer[] recordXformers;

    protected final ExpressionEvaluator joinEvaluator;

    protected final JoinTupleProducer joinTupleProducer;

    protected final StopFilter stopFilter;

    protected final DummyIterator dummyIterator;

    protected NestedLoopJoinBridgeNode[] previousNodes;

    protected Node<Tuple> next;

    private ReusableIterator[] nestedJoinIters;

    private FixedKeyHashMap<String, Tuple> map;

    protected final ArrayList<NestedLoopJoinBridgeNode> previousNodeList;

    /**
     * @param recordXformers
     * @param joinEvaluator
     * @param joinTupleProducer
     * @param stopFilter        Can be <code>null</code>.
     */
    public NestedLoopJoinNode(SourceToMapTransformer[] recordXformers,
                              ExpressionEvaluator joinEvaluator,
                              JoinTupleProducer joinTupleProducer, StopFilter stopFilter) {
        this.recordXformers = recordXformers;
        this.joinEvaluator = joinEvaluator;
        this.joinTupleProducer = joinTupleProducer;
        this.stopFilter = stopFilter;
        this.dummyIterator = new DummyIterator();

        LinkedList<String> allKeys = new LinkedList<String>();
        for (SourceToMapTransformer xformer : recordXformers) {
            for (String key : xformer.getKeys()) {
                allKeys.add(key);
            }
        }
        this.map = new FixedKeyHashMap<String, Tuple>(allKeys);

        for (SourceToMapTransformer xformer : this.recordXformers) {
            xformer.init(map);
        }

        this.joinTupleProducer.init(map);

        this.previousNodeList = new ArrayList<NestedLoopJoinBridgeNode>(2);
    }

    public SourceToMapTransformer[] getRecordXformers() {
        return recordXformers;
    }

    public List<NestedLoopJoinBridgeNode> getPrevious() {
        return previousNodeList;
    }

    /**
     * @param previousNodes Left to right, from 0 to end. Same order as the {@link
     *                      com.tibco.cep.query.stream._join_.node.xform.SourceToMapTransformer}
     *                      elements in {@link #NestedLoopJoinNode(com.tibco.cep.query.stream._join_.node.xform.SourceToMapTransformer[],
     *                      com.tibco.cep.query.stream.expression.ExpressionEvaluator,
     *                      JoinTupleProducer, StopFilter)}.
     */
    public void setPreviousNodes(NestedLoopJoinBridgeNode[] previousNodes) {
        this.previousNodes = previousNodes;

        for (NestedLoopJoinBridgeNode previousNode : previousNodes) {
            this.previousNodeList.add(previousNode);
        }

        this.nestedJoinIters = new ReusableIterator[previousNodes.length];
    }

    public Node<Tuple> getNext() {
        return next;
    }

    public void setNext(Node<Tuple> next) {
        this.next = next;
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

    protected void acceptNew(GlobalContext globalContext, QueryContext queryContext, Tuple input,
                             int joinPosition) {
        final ReusableIterator[] localNestedJoinIters = nestedJoinIters;

        for (int i = 0; i < localNestedJoinIters.length; i++) {
            if (i == joinPosition) {
                dummyIterator.setTuple(input);
                localNestedJoinIters[i] = dummyIterator;
            }
            else {
                localNestedJoinIters[i] = previousNodes[i].fetchAll(globalContext, queryContext);
            }
        }

        map.clear();

        if (stopFilter != null) {
            stopFilter.startSession(globalContext, queryContext);
        }

        nonRecursiveJoin(globalContext, queryContext);

        if (stopFilter != null) {
            stopFilter.endSession(globalContext, queryContext);
        }

        dummyIterator.clear();
        for (int i = 0; i < localNestedJoinIters.length; i++) {
            localNestedJoinIters[i] = null;
        }
    }

    protected void nonRecursiveJoin(GlobalContext globalContext, QueryContext queryContext) {
        final ReusableIterator[] allIters = nestedJoinIters;
        final SourceToMapTransformer[] allXformers = recordXformers;
        final FixedKeyHashMap<String, Tuple> localMap = map;
        final int lastPosition = allIters.length - 1;
        final ExpressionEvaluator localJoinEvaluator = joinEvaluator;
        final JoinTupleProducer localJoinTupleProducer = joinTupleProducer;

        int currentPosition = 0;

        //Reset the left-most/root only once in the beginning.
        allIters[currentPosition].reset();

        nestedLoopJoin:
        for (; ;) {
            //This is the end of the recursion.
            if (currentPosition == lastPosition) {
                ReusableIterator lastOne = allIters[currentPosition];
                for (lastOne.reset(); lastOne.hasNext();) {
                    Object object = lastOne.next();
                    allXformers[currentPosition].copyTransform(object, localMap);

                    boolean joinTrue = localJoinEvaluator
                            .evaluateBoolean(globalContext, queryContext, localMap);
                    if (joinTrue) {
                        Tuple resultTuple = localJoinTupleProducer
                                .evaluate(globalContext, queryContext, localMap);

                        next.acceptNew(globalContext, queryContext, resultTuple);

                        if (stopFilter != null &&
                                stopFilter
                                        .continueJoins(globalContext, queryContext, resultTuple) ==
                                        false) {
                            break;
                        }
                    }
                }

                //Unwind stack.
                currentPosition--;
            }

            //------------

            ReusableIterator intermediateOne = allIters[currentPosition];

            //We do not reset the left-most/root.
            if (currentPosition > 0) {
                intermediateOne.reset();
            }

            for (; intermediateOne.hasNext();) {
                Object object = intermediateOne.next();
                allXformers[currentPosition].copyTransform(object, localMap);

                //Push on stack.
                currentPosition++;
                continue nestedLoopJoin;
            }

            if (intermediateOne.hasNext() == false) {
                //The end of the outermost loop. Exit.
                if (currentPosition == 0) {
                    break;
                }

                //Unwind stack.
                currentPosition--;
            }
        }

        localMap.clear();
    }

    protected void acceptDelete(GlobalContext globalContext, QueryContext queryContext, Tuple input,
                                int joinPosition) {
        //todo
    }

    //-------------

    protected static class DummyIterator implements ReusableIterator<Tuple> {

        protected Tuple tuple;

        protected boolean end;

        public Tuple getTuple() {
            return tuple;
        }

        public void setTuple(Tuple tuple) {
            this.tuple = tuple;
            end = false;
        }

        protected void clear() {
            tuple = null;
            end = true;
        }

        public void reset() {
            end = false;
        }

        public boolean hasNext() {
            return !end;
        }

        public Tuple next() {
            if (!end) {
                end = true;

                return tuple;
            }

            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
