package com.tibco.cep.query.stream._join_.test;

import com.tibco.cep.query.stream._join_.impl.index.IndexedColumnExtractor;
import com.tibco.cep.query.stream._join_.impl.join.MultiWayNonMergingJTP;
import com.tibco.cep.query.stream._join_.impl.join.TwoWayNonMergingJTP;
import com.tibco.cep.query.stream._join_.impl.xform.MultiSourceToMapTransformer;
import com.tibco.cep.query.stream._join_.impl.xform.SingleSourceToMapTransformer;
import com.tibco.cep.query.stream._join_.index.DefaultNonUniqueRangeIndex;
import com.tibco.cep.query.stream._join_.index.Index;
import com.tibco.cep.query.stream._join_.index.Operator;
import com.tibco.cep.query.stream._join_.node.Node;
import com.tibco.cep.query.stream._join_.node.index.IndexExtractor;
import com.tibco.cep.query.stream._join_.node.index.IndexedStorageNode;
import com.tibco.cep.query.stream._join_.node.join.*;
import com.tibco.cep.query.stream._join_.node.xform.SourceToMapTransformer;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.join.JoinedTuple;
import com.tibco.cep.query.stream.misc.IdGenerator;
import com.tibco.cep.query.stream.tuple.LiteTuple;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.FixedKeyHashMap;
import org.testng.annotations.Test;

import java.util.Arrays;

/*
* Author: Ashwin Jayaprakash Date: Nov 20, 2008 Time: 4:16:47 PM
*/
public class NestedLoopJoin2Test {
    public static void main(String[] args) {
        new NestedLoopJoin2Test().test();
    }

    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        IdGenerator idGenerator = new IdGenerator();

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

        JoinTupleProducer joinProducerAB = new TwoWayNonMergingJTP(idGenerator, "a", "b");
        SourceToMapTransformer<Tuple> s2mJoinA = new SingleSourceToMapTransformer("a");
        SourceToMapTransformer<Tuple> s2mJoinB = new SingleSourceToMapTransformer("b");
        SourceToMapTransformer[] xformersAB = new SourceToMapTransformer[]{s2mJoinA, s2mJoinB};
        ExpressionEvaluator joinEvaluator = new JoinEvaluator("a", 0, "b", 0);
        NestedLoopJoinNode joinAB =
                new NestedLoopJoinNode(xformersAB, joinEvaluator, joinProducerAB, null);
        joinAB.setPreviousNodes(new NestedLoopJoinBridgeNode[]{bridgeA, bridgeB});
        bridgeA.setNext(joinAB);
        bridgeA.setPositionInJoin(0);
        bridgeB.setNext(joinAB);
        bridgeB.setPositionInJoin(1);

        //--------------

        IndexExtractor eeC = new IndexedColumnExtractor("c", 0);
        SourceToMapTransformer<Tuple> s2mC = new SingleSourceToMapTransformer("c");
        Index<Comparable, Tuple, Number> indexC =
                new DefaultNonUniqueRangeIndex<Comparable, Tuple, Number>();
        IndexedStorageNode isnC = new IndexedStorageNode(eeC, s2mC, indexC);
        NestedLoopJoinBridgeNode bridgeC = new NestedLoopJoinBridgeNode();
        isnC.setNext(bridgeC);
        bridgeC.setPrevious(isnC);

        IndexExtractor eeD = new IndexedColumnExtractor("d", 0);
        SourceToMapTransformer<Tuple> s2mD = new SingleSourceToMapTransformer("d");
        Index<Comparable, Tuple, Number> indexD =
                new DefaultNonUniqueRangeIndex<Comparable, Tuple, Number>();
        IndexedStorageNode isnD = new IndexedStorageNode(eeD, s2mD, indexD);
        NestedLoopJoinBridgeNode bridgeD = new NestedLoopJoinBridgeNode();
        isnD.setNext(bridgeD);
        bridgeD.setPrevious(isnD);

        JoinTupleProducer joinProducerCD = new TwoWayNonMergingJTP(idGenerator, "c", "d");
        SourceToMapTransformer<Tuple> s2mJoinC = new SingleSourceToMapTransformer("c");
        SourceToMapTransformer<Tuple> s2mJoinD = new SingleSourceToMapTransformer("d");
        SourceToMapTransformer[] xformersCD = new SourceToMapTransformer[]{s2mJoinC, s2mJoinD};
        ExpressionEvaluator joinEvaluatorCD = new JoinEvaluator("c", 0, "d", 0);
        NestedLoopJoinNode joinCD =
                new NestedLoopJoinNode(xformersCD, joinEvaluatorCD, joinProducerCD, null);
        joinCD.setPreviousNodes(new NestedLoopJoinBridgeNode[]{bridgeC, bridgeD});
        bridgeC.setNext(joinCD);
        bridgeC.setPositionInJoin(0);
        bridgeD.setNext(joinCD);
        bridgeD.setPositionInJoin(1);

        //--------------

        IndexExtractor eeBWithA = new IndexedColumnExtractor("b", 0);
        SourceToMapTransformer<Tuple> s2mBWithA = new MultiSourceToMapTransformer("a", "b");
        Index<Comparable, Tuple, Number> indexBWithA =
                new DefaultNonUniqueRangeIndex<Comparable, Tuple, Number>();
        IndexedStorageNode isnBWithA = new IndexedStorageNode(eeBWithA, s2mBWithA, indexBWithA);
        IndexedJoinBridgeNode bridgeBWithA = new IndexedJoinBridgeNode(true);
        joinAB.setNext(isnBWithA);
        isnBWithA.setPrevious(joinAB);
        isnBWithA.setNext(bridgeBWithA);
        bridgeBWithA.setPrevious(isnBWithA);

        IndexExtractor eeCWithD = new IndexedColumnExtractor("c", 0);
        SourceToMapTransformer<Tuple> s2mCWithD = new MultiSourceToMapTransformer("c", "d");
        Index<Comparable, Tuple, Number> indexCWithD =
                new DefaultNonUniqueRangeIndex<Comparable, Tuple, Number>();
        IndexedStorageNode isnCWithD = new IndexedStorageNode(eeCWithD, s2mCWithD, indexCWithD);
        IndexedJoinBridgeNode bridgeCWithD = new IndexedJoinBridgeNode(false);
        joinCD.setNext(isnCWithD);
        isnCWithD.setPrevious(joinCD);
        isnCWithD.setNext(bridgeCWithD);
        bridgeCWithD.setPrevious(isnCWithD);

        JoinTupleProducer joinProducerABCD =
                new MultiWayNonMergingJTP(idGenerator, "a", "b", "c", "d");
        SourceToMapTransformer<Tuple> s2mJoinBWithA = new MultiSourceToMapTransformer("a", "b");
        SourceToMapTransformer<Tuple> s2mJoinCWithD = new MultiSourceToMapTransformer("c", "d");
        IndexedJoinNode joinABCD = new IndexedJoinNode(eeBWithA, Operator.EQUALS, eeCWithD,
                s2mJoinBWithA, s2mJoinCWithD, joinProducerABCD, null);
        joinABCD.setPreviousLeft(bridgeBWithA);
        bridgeBWithA.setNext(joinABCD);
        joinABCD.setPreviousRight(bridgeCWithD);
        bridgeCWithD.setNext(joinABCD);

        //--------------

        EndNode endNode = new EndNode();
        endNode.setPrevious(joinABCD);
        joinABCD.setNext(endNode);

        //--------------

        LiteTuple a1 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"Igor", "Prince"});
        LiteTuple a2 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"Duck", "Donald"});
        LiteTuple a3 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"McDuck", "Scrooge"});
        LiteTuple a4 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"Simpson", "Bart"});
        LiteTuple a5 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"Andretti", "Mario"});
        LiteTuple a6 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"Igor", "Vlad"});

        LiteTuple b1 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"Simpson", "Homer"});
        LiteTuple b2 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"Igor", "Princess"});

        isnA.acceptNew(null, null, a1);
        isnA.acceptNew(null, null, a2);
        isnA.acceptNew(null, null, a3);

        isnB.acceptNew(null, null, b1);
        isnB.acceptNew(null, null, b2);

        isnA.acceptNew(null, null, a4);
        isnA.acceptNew(null, null, a5);
        isnA.acceptNew(null, null, a6);

        LiteTuple c1 = new LiteTuple(idGenerator.generateNewId(), new Object[]{"Simpson"});
        LiteTuple c2 = new LiteTuple(idGenerator.generateNewId(), new Object[]{"Igor"});
        LiteTuple c3 = new LiteTuple(idGenerator.generateNewId(), new Object[]{"Arjuna"});

        LiteTuple d1 = new LiteTuple(idGenerator.generateNewId(), new Object[]{"Krishna", "Radha"});
        LiteTuple d2 = new LiteTuple(idGenerator.generateNewId(), new Object[]{"Igor", "Baby"});
        LiteTuple d3 = new LiteTuple(idGenerator.generateNewId(), new Object[]{"Simpson", "Marge"});
        LiteTuple d4 = new LiteTuple(idGenerator.generateNewId(), new Object[]{"Simpson", "Lisa"});

        isnC.acceptNew(null, null, c1);
        isnC.acceptNew(null, null, c2);
        isnC.acceptNew(null, null, c3);

        isnD.acceptNew(null, null, d1);
        isnD.acceptNew(null, null, d2);
        isnD.acceptNew(null, null, d3);
        isnD.acceptNew(null, null, d4);
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
            String s = "";

            JoinedTuple resultTuple = (JoinedTuple) input;
            Object[] columns = resultTuple.getRawColumns();
            for (int i = 0; i < columns.length; i++) {
                Object column = columns[i];
                Tuple tuple = (Tuple) column;

                Object[] values = tuple.getRawColumns();
                s = s + String.format("Column-%s: %-25s ", i, Arrays.asList(values));
            }

            System.out.println(s);
        }

        public void acceptDelete(GlobalContext globalContext, QueryContext queryContext,
                                 Tuple input) {
        }
    }
}