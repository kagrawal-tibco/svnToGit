package com.tibco.cep.query.stream._join2_.example;

import com.tibco.cep.query.stream._join2_.api.*;
import com.tibco.cep.query.stream._join2_.example.domain.OptimizedShipment;
import com.tibco.cep.query.stream._join2_.example.domain.Person;
import com.tibco.cep.query.stream._join2_.example.domain.Shipment;
import com.tibco.cep.query.stream._join2_.impl.*;
import com.tibco.cep.query.stream._join2_.impl.AbstractMultiCombiner.LeftArrayRightSimpleCombiner;
import com.tibco.cep.query.stream._join2_.impl.AbstractMultiCombiner.LeftSimpleRightArrayCombiner;
import com.tibco.cep.query.stream._join2_.util.DateMaker;

import java.util.Collection;
import java.util.GregorianCalendar;

/*
* Author: Ashwin Jayaprakash Date: Jun 1, 2009 Time: 5:14:30 PM
*/
public class ThreeWayJoinTest extends TwoWayEquiJoinTest {
    IndexedSource personAndShipmentJoinSource;

    SimpleSource optimizedShipmentSource;

    AbstractJoinResultTracker personAndShipmentJoinHandler;

    SimpleNestedLoopJoiner personShipAndOptimizedShipNLJ;

    IndexedNestedLoopJoiner optimizedShipAndPersonShipNLJ;

    @SuppressWarnings({"unchecked"})
    public ThreeWayJoinTest() {
        super();

        //-----------

        MultiSourceMVELExtractor personAndShipmentPrimaryKeyExtractor =
                new MultiSourceMVELExtractor("person.id + '-' + shipment.id", "person", "shipment");

        this.personAndShipmentJoinHandler = new AbstractJoinResultTracker() {
            public void batchStart() {
                personAndShipmentJoinSource.batchStart();
            }

            protected void onJoinEpilogue(Object[] joinMembers, Object[] primaryKeysOfJoinMembers,
                                          Object joinResultPrimaryKey) {
                personAndShipmentJoinSource.add(joinResultPrimaryKey, joinMembers);
            }

            public boolean continueBatch() {
                return true;
            }

            public void batchEnd() {
                personAndShipmentJoinSource.batchEnd();
            }

            //------------

            protected void sourceBatchStart(Source source, int sourcePosition) {
            }

            protected void sourceBatchEndEpilogue(Source source, int sourcePosition,
                                                  Collection deletedResultPrimaryKeysInBatch) {
            }
        };

        this.personAndShipmentJoinHandler
                .setResultPrimaryKeyExtractor(personAndShipmentPrimaryKeyExtractor)
                .setJoinSources(new Source[]{personSource, shipmentSource});

        this.personAndShipmentNLJ.setJoinHandler(personAndShipmentJoinHandler);
        this.shipmentAndPersonNLJ.setJoinHandler(personAndShipmentJoinHandler);

        //-----------

        MultiSourceMVELExtractor personAndShipmentSearchKeyExtractor =
                new MultiSourceMVELExtractor("shipment.deliveryDate", "person", "shipment");

        this.personAndShipmentJoinSource = new IndexedSource()
                .setFilterPassedStore(new SimpleIndexedStore())
                .setPrimaryKeyExtractor(personAndShipmentPrimaryKeyExtractor)
                .setSecondaryKeyExtractor(personAndShipmentSearchKeyExtractor);

        this.optimizedShipmentSource = new SimpleSource()
                .setFilterPassedStore(new SimpleStore())
                .setPrimaryKeyExtractor(
                        new SingleSourceMVELExtractor("optimizedShipment.id", "optimizedShipment"));

        //-----------

        this.personShipAndOptimizedShipNLJ = new SimpleNestedLoopJoiner()
                .setCombiner(new LeftArrayRightSimpleCombiner(true, personAndShipmentJoinSource,
                        optimizedShipmentSource))
                .setFilter(new MultiSourceMVELFilter(
                        "shipment.deliveryDate == optimizedShipment.deliveryDate",
                        "person", "shipment", "optimizedShipment"));

        this.optimizedShipAndPersonShipNLJ = new IndexedNestedLoopJoiner()
                .setCombiner(new LeftSimpleRightArrayCombiner(false, optimizedShipmentSource,
                        personAndShipmentJoinSource))
                .setLeftSearchKeyExtractor(
                        new SingleSourceMVELExtractor("optimizedShipment.deliveryDate",
                                "optimizedShipment"))
                .setRightValueRetriever(
                        new SimpleIndexRetriever<Comparable, Object>(Operator.Equal));

        //-----------

        JoinHandler finalJoinHandler = new JoinHandler() {
            public void batchStart() {
                System.out.println("---------");
            }

            public void onJoin(Object[] joinMembers) {
                System.out.println("Join result: " + joinMembers[0] + ", " + joinMembers[1] + ", " +
                        joinMembers[2]);
            }

            public boolean continueBatch() {
                return true;
            }

            public void batchEnd() {
            }
        };

        this.personShipAndOptimizedShipNLJ.setJoinHandler(finalJoinHandler);
        this.optimizedShipAndPersonShipNLJ.setJoinHandler(finalJoinHandler);
    }

    @Override
    public void test() {
        Person p1 = new Person();
        p1.setAge(10);
        p1.setFirstName("A1");
        p1.setLastName("B1");
        p1.setId(1);

        Person p2 = new Person();
        p2.setAge(60);
        p2.setFirstName("A2");
        p2.setLastName("B2");
        p2.setId(2);

        Person p3 = new Person();
        p3.setAge(12);
        p3.setFirstName("A2");
        p3.setLastName("B2");
        p3.setId(3);

        Person p4 = new Person();
        p4.setAge(80);
        p4.setFirstName("A2");
        p4.setLastName("B2");
        p4.setId(4);

        Person p5 = new Person();
        p5.setAge(29);
        p5.setFirstName("A2");
        p5.setLastName("B2");
        p5.setId(5);

        GregorianCalendar calendar = new GregorianCalendar();

        Shipment s1 = new Shipment();
        s1.setCustomerFirstName("A2");
        s1.setCustomerLastName("B2");
        s1.setDeliveryDate(DateMaker.makeDate(calendar, 2009, 1, 2));
        s1.setId(1);

        Shipment s2 = new Shipment();
        s2.setCustomerFirstName("A1");
        s2.setCustomerLastName("B1");
        s2.setDeliveryDate(DateMaker.makeDate(calendar, 2009, 1, 2));
        s2.setId(2);

        Shipment s3 = new Shipment();
        s3.setCustomerFirstName("A2");
        s3.setCustomerLastName("B1");
        s3.setDeliveryDate(DateMaker.makeDate(calendar, 2009, 1, 2));
        s3.setId(3);

        Shipment s4 = new Shipment();
        s4.setCustomerFirstName("A2");
        s4.setCustomerLastName("B2");
        s4.setDeliveryDate(DateMaker.makeDate(calendar, 2009, 1, 2));
        s4.setId(4);

        OptimizedShipment o1 = new OptimizedShipment();
        o1.setId(1);
        o1.setDeliveryDate(DateMaker.makeDate(calendar, 2009, 1, 2));

        OptimizedShipment o2 = new OptimizedShipment();
        o2.setId(2);
        o2.setDeliveryDate(DateMaker.makeDate(calendar, 2010, 3, 3));

        //-----------

        personSource.batchStart();
        personSource.add(p1);
        personSource.add(p2);
        personSource.add(p3);
        personSource.add(p4);
        personSource.add(p5);
        IndexedView personView = personSource.batchEnd();

        shipmentSource.batchStart();
        shipmentSource.add(s1);
        shipmentSource.add(s2);
        shipmentSource.add(s3);
        shipmentSource.add(s4);
        IndexedView shipmentView = shipmentSource.batchEnd();

        personAndShipmentNLJ.attemptJoin(personView, shipmentView);

        optimizedShipmentSource.batchStart();
        optimizedShipmentSource.add(o1);
        optimizedShipmentSource.add(o2);
        View optimizedShipmentView = optimizedShipmentSource.batchEnd();

        IndexedView personAndShipmentJoinView =
                personAndShipmentJoinSource.getIndexedFilterPassedStore();

        personShipAndOptimizedShipNLJ.attemptJoin(personAndShipmentJoinView, optimizedShipmentView);

        //-----------

        System.out.println();

        OptimizedShipment o3 = new OptimizedShipment();
        o3.setId(3);
        o3.setDeliveryDate(DateMaker.makeDate(calendar, 2009, 1, 2));

        OptimizedShipment o4 = new OptimizedShipment();
        o4.setId(4);
        o4.setDeliveryDate(DateMaker.makeDate(calendar, 2009, 12, 2));

        optimizedShipmentSource.batchStart();
        optimizedShipmentSource.add(o3);
        optimizedShipmentSource.add(o4);
        optimizedShipmentView = optimizedShipmentSource.batchEnd();

        optimizedShipAndPersonShipNLJ.attemptJoin(optimizedShipmentView, personAndShipmentJoinView);
    }

    public static void main(String[] args) {
        new ThreeWayJoinTest().test();
    }
}