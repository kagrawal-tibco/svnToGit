package com.tibco.cep.query.stream._join_.test;

import com.tibco.cep.query.stream._join_.impl.index.IndexedColumnExtractor;
import com.tibco.cep.query.stream._join_.impl.join.MultiWayMergingJTP;
import com.tibco.cep.query.stream._join_.impl.join.TwoWayMergingJTP;
import com.tibco.cep.query.stream._join_.impl.xform.SingleSourceToMapTransformer;
import com.tibco.cep.query.stream._join_.index.DefaultUniqueIndex;
import com.tibco.cep.query.stream._join_.index.Index;
import com.tibco.cep.query.stream._join_.index.Operator;
import com.tibco.cep.query.stream._join_.node.Node;
import com.tibco.cep.query.stream._join_.node.index.IndexExtractor;
import com.tibco.cep.query.stream._join_.node.index.IndexedStorageNode;
import com.tibco.cep.query.stream._join_.node.join.IndexedJoinBridgeNode;
import com.tibco.cep.query.stream._join_.node.join.IndexedJoinNode;
import com.tibco.cep.query.stream._join_.node.join.JoinTupleProducer;
import com.tibco.cep.query.stream._join_.node.xform.SourceToMapTransformer;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.misc.IdGenerator;
import com.tibco.cep.query.stream.tuple.LiteTuple;
import com.tibco.cep.query.stream.tuple.Tuple;
import org.testng.annotations.Test;

/*
* Author: Ashwin Jayaprakash Date: Oct 21, 2008 Time: 11:26:56 AM
*/
public class EqualsGraphTest {
    public static void main(String[] args) throws Exception {
        new EqualsGraphTest().test();
    }

    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() throws Exception {
        IndexExtractor eeA = new IndexedColumnExtractor("a", 0);
        SourceToMapTransformer<Tuple> s2mA = new SingleSourceToMapTransformer("a");
        Index<Comparable, Tuple, Number> indexA =
                new DefaultUniqueIndex<Comparable, Tuple, Number>();
        IndexedStorageNode isnA = new IndexedStorageNode(eeA, s2mA, indexA);
        IndexedJoinBridgeNode bridgeA = new IndexedJoinBridgeNode(true);
        isnA.setNext(bridgeA);
        bridgeA.setPrevious(isnA);

        IndexExtractor eeB = new IndexedColumnExtractor("b", 0);
        SourceToMapTransformer<Tuple> s2mB = new SingleSourceToMapTransformer("b");
        Index<Comparable, Tuple, Number> indexB =
                new DefaultUniqueIndex<Comparable, Tuple, Number>();
        IndexedStorageNode isnB = new IndexedStorageNode(eeB, s2mB, indexB);
        IndexedJoinBridgeNode bridgeB = new IndexedJoinBridgeNode(false);
        isnB.setNext(bridgeB);
        bridgeB.setPrevious(isnB);

        IdGenerator idGenerator = new IdGenerator();
        JoinTupleProducer joinProducer = new TwoWayMergingJTP(idGenerator,
                new MultiWayMergingJTP.TupleInput("a", 0),
                new MultiWayMergingJTP.TupleInput("b", 0));
        SourceToMapTransformer<Tuple> s2mJoinA = new SingleSourceToMapTransformer("a");
        SourceToMapTransformer<Tuple> s2mJoinB = new SingleSourceToMapTransformer("b");
        IndexedJoinNode join = new IndexedJoinNode(eeA, Operator.EQUALS, eeB,
                s2mJoinA, s2mJoinB, joinProducer, null);
        join.setPreviousLeft(bridgeA);
        bridgeA.setNext(join);
        join.setPreviousRight(bridgeB);
        bridgeB.setNext(join);

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
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"Simpson", "Homer"});

        LiteTuple right1 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"Simpson"});
        LiteTuple right2 =
                new LiteTuple(idGenerator.generateNewId(), new Object[]{"Duck"});


        isnA.acceptNew(null, null, left1);
        isnA.acceptNew(null, null, left2);
        isnA.acceptNew(null, null, left3);

        isnB.acceptNew(null, null, right1);
        isnB.acceptNew(null, null, right2);

        isnA.acceptNew(null, null, left4);
        isnA.acceptNew(null, null, left5);
    }

    //---------------

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
