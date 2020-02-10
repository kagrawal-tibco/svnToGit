package com.tibco.cep.query.stream._join_.test;

import com.tibco.cep.query.stream._join_.impl.index.IndexedColumnExtractor;
import com.tibco.cep.query.stream._join_.impl.join.MultiWayMergingJTP;
import com.tibco.cep.query.stream._join_.impl.join.TwoWayMergingJTP;
import com.tibco.cep.query.stream._join_.impl.xform.SingleSourceToMapTransformer;
import com.tibco.cep.query.stream._join_.index.DefaultNonUniqueRangeIndex;
import com.tibco.cep.query.stream._join_.index.Index;
import com.tibco.cep.query.stream._join_.node.Node;
import com.tibco.cep.query.stream._join_.node.index.IndexExtractor;
import com.tibco.cep.query.stream._join_.node.index.IndexedStorageNode;
import com.tibco.cep.query.stream._join_.node.join.JoinTupleProducer;
import com.tibco.cep.query.stream._join_.node.join.NestedLoopJoinBridgeNode;
import com.tibco.cep.query.stream._join_.node.join.NestedLoopJoinNode;
import com.tibco.cep.query.stream._join_.node.xform.SourceToMapTransformer;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.misc.IdGenerator;
import com.tibco.cep.query.stream.tuple.LiteTuple;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import org.testng.annotations.Test;

/*
* Author: Ashwin Jayaprakash Date: Nov 20, 2008 Time: 4:16:47 PM
*/
public class NestedLoopJoinTest {
    public static void main(String[] args) {
        new NestedLoopJoinTest().test();
    }

    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        IndexExtractor eeA = new IndexedColumnExtractor("a", 0);
        SourceToMapTransformer<Tuple> s2mA = new SingleSourceToMapTransformer("a");
        Index<Comparable, Tuple, Number> indexA =
                new DefaultNonUniqueRangeIndex<Comparable, Tuple, Number>();
        IndexedStorageNode isnA = new IndexedStorageNode(eeA, s2mA, indexA);
        NestedLoopJoinBridgeNode bridgeA = new NestedLoopJoinBridgeNode();
        isnA.setNext(bridgeA);
        bridgeA.setPrevious(isnA);

        IndexExtractor eeB = new IndexedColumnExtractor("b", 0);
        SourceToMapTransformer<Tuple> s2mB = new SingleSourceToMapTransformer("b");
        Index<Comparable, Tuple, Number> indexB =
                new DefaultNonUniqueRangeIndex<Comparable, Tuple, Number>();
        IndexedStorageNode isnB = new IndexedStorageNode(eeB, s2mB, indexB);
        NestedLoopJoinBridgeNode bridgeB = new NestedLoopJoinBridgeNode();
        isnB.setNext(bridgeB);
        bridgeB.setPrevious(isnB);

        IdGenerator idGenerator = new IdGenerator();
        JoinTupleProducer joinProducer = new TwoWayMergingJTP(idGenerator,
                new MultiWayMergingJTP.TupleInput("a", 0),
                new MultiWayMergingJTP.TupleInput("b", 0));
        SourceToMapTransformer<Tuple> s2mJoinA = new SingleSourceToMapTransformer("a");
        SourceToMapTransformer<Tuple> s2mJoinB = new SingleSourceToMapTransformer("b");
        SourceToMapTransformer[] xformers = new SourceToMapTransformer[]{s2mJoinA, s2mJoinB};
        ExpressionEvaluator joinEvaluator = new JoinEvaluator("a", 0, "b", 0);
        NestedLoopJoinNode join =
                new NestedLoopJoinNode(xformers, joinEvaluator, joinProducer, null);
        join.setPreviousNodes(new NestedLoopJoinBridgeNode[]{bridgeA, bridgeB});
        bridgeA.setNext(join);
        bridgeA.setPositionInJoin(0);
        bridgeB.setNext(join);
        bridgeB.setPositionInJoin(1);

        EndNode endNode = new EndNode();
        endNode.setPrevious(join);
        join.setNext(endNode);

        //--------------

        LiteTuple left1 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"Mouse", "Mickey"});
        LiteTuple left2 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"Duck", "Donald"});
        LiteTuple left3 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"McDuck", "Scrooge"});
        LiteTuple left4 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"Simpson", "Bart"});
        LiteTuple left5 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"Andretti", "Mario"});

        LiteTuple right1 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"Simpson"});
        LiteTuple right2 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"Duck"});
        LiteTuple right3 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"Bheema"});


        isnA.acceptNew(null, null, left1);
        isnA.acceptNew(null, null, left2);
        isnA.acceptNew(null, null, left3);

        isnB.acceptNew(null, null, right1);
        isnB.acceptNew(null, null, right2);
        isnB.acceptNew(null, null, right3);

        isnA.acceptNew(null, null, left4);
        isnA.acceptNew(null, null, left5);
    }

    //---------------

    protected static class JoinEvaluator implements ExpressionEvaluator {
        protected final String leftTupleKey;

        protected final int leftTupleColumn;

        protected final String rightTupleKey;

        protected final int rightTupleColumn;

        public JoinEvaluator(String leftTupleKey, int leftTupleColumn, String rightTupleKey,
                             int rightTupleColumn) {
            this.leftTupleKey = leftTupleKey;
            this.leftTupleColumn = leftTupleColumn;
            this.rightTupleKey = rightTupleKey;
            this.rightTupleColumn = rightTupleColumn;
        }

        public Object evaluate(GlobalContext globalContext, QueryContext queryContext,
                               FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return evaluateBoolean(globalContext, queryContext, aliasAndTuples);
        }

        public boolean evaluateBoolean(GlobalContext globalContext, QueryContext queryContext,
                                       FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            Tuple left = aliasAndTuples.get(leftTupleKey);
            String leftVal = (String) left.getColumn(leftTupleColumn);

            Tuple right = aliasAndTuples.get(rightTupleKey);
            String rightVal = (String) right.getColumn(rightTupleColumn);

            return leftVal.compareTo(rightVal) == 0;
        }

        public int evaluateInteger(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return 0;
        }

        public long evaluateLong(GlobalContext globalContext, QueryContext queryContext,
                                 FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return 0;
        }

        public float evaluateFloat(GlobalContext globalContext, QueryContext queryContext,
                                   FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return 0;
        }

        public double evaluateDouble(GlobalContext globalContext, QueryContext queryContext,
                                     FixedKeyHashMap<String, ? extends Tuple> aliasAndTuples) {
            return 0;
        }
    }

    protected static class EndNode implements Node<Tuple> {
        protected Node<Tuple> previous;

        public Node<Tuple> getPrevious() {
            return previous;
        }

        public void setPrevious(Node<Tuple> previous) {
            this.previous = previous;
        }

        public Node<Tuple> getNext() {
            return null;
        }

        public void acceptNew(GlobalContext globalContext, QueryContext queryContext, Tuple input) {
            System.out.println("Result: " + input.getColumn(0) + ", " + input.getColumn(1));
        }

        public void acceptDelete(GlobalContext globalContext, QueryContext queryContext,
                                 Tuple input) {
        }
    }
}
