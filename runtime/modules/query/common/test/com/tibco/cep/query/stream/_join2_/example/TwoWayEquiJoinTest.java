package com.tibco.cep.query.stream._join2_.example;

import com.tibco.cep.query.stream._join2_.api.IndexedView;
import com.tibco.cep.query.stream._join2_.api.JoinHandler;
import com.tibco.cep.query.stream._join2_.api.Operator;
import com.tibco.cep.query.stream._join2_.example.domain.Person;
import com.tibco.cep.query.stream._join2_.example.domain.Shipment;
import com.tibco.cep.query.stream._join2_.impl.*;

/*
* Author: Ashwin Jayaprakash Date: Jun 1, 2009 Time: 5:14:30 PM
*/
public class TwoWayEquiJoinTest {
    IndexedSource personSource;

    IndexedSource shipmentSource;

    IndexedNestedLoopJoiner personAndShipmentNLJ;

    IndexedNestedLoopJoiner shipmentAndPersonNLJ;

    @SuppressWarnings({"unchecked"})
    public TwoWayEquiJoinTest() {
        SingleSourceMVELExtractor leftSearchKeyExtractor =
                new SingleSourceMVELExtractor("person.lastName + person.firstName", "person");

        this.personSource = new IndexedSource()
                .setFilter(new SingleSourceMVELFilter("person.age > 45", "person"))
                .setPrimaryKeyExtractor(new SingleSourceMVELExtractor("person.id", "person"))
                .setSecondaryKeyExtractor(leftSearchKeyExtractor)
                .setFilterPassedStore(new SimpleIndexedStore())
                .setFilterFailedStore(new SimpleStore());

        SingleSourceMVELExtractor rightSearchKeyExtractor = new SingleSourceMVELExtractor(
                "shipment.customerLastName + shipment.customerFirstName",
                "shipment");

        this.shipmentSource = new IndexedSource()
                .setFilterPassedStore(new SimpleIndexedStore())
                .setPrimaryKeyExtractor(new SingleSourceMVELExtractor("shipment.id", "shipment"))
                .setSecondaryKeyExtractor(rightSearchKeyExtractor);

        //-----------

        this.personAndShipmentNLJ =
                new IndexedNestedLoopJoiner()
                        .setCombiner(new SimpleCombiner(true, personSource, shipmentSource))
                        .setLeftSearchKeyExtractor(leftSearchKeyExtractor)
                        .setRightValueRetriever(
                                new SimpleIndexRetriever<Comparable, Object>(Operator.Equal));

        this.shipmentAndPersonNLJ =
                new IndexedNestedLoopJoiner()
                        .setCombiner(new SimpleCombiner(false, shipmentSource, personSource))
                        .setLeftSearchKeyExtractor(rightSearchKeyExtractor)
                        .setRightValueRetriever(
                                new SimpleIndexRetriever<Comparable, Object>(Operator.Equal));

        //-----------

        JoinHandler joinHandler = new JoinHandler() {
            public void batchStart() {
                System.out.println("---------");
            }

            public void onJoin(Object[] joinMembers) {
                System.out.println("Join result: " + joinMembers[0] + ", " + joinMembers[1]);
            }

            public boolean continueBatch() {
                return true;
            }

            public void batchEnd() {
            }
        };

        this.personAndShipmentNLJ.setJoinHandler(joinHandler);
        this.shipmentAndPersonNLJ.setJoinHandler(joinHandler);
    }

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

        Shipment s1 = new Shipment();
        s1.setCustomerFirstName("A2");
        s1.setCustomerLastName("B2");
        s1.setId(1);

        Shipment s2 = new Shipment();
        s2.setCustomerFirstName("A1");
        s2.setCustomerLastName("B1");
        s2.setId(2);

        Shipment s3 = new Shipment();
        s3.setCustomerFirstName("A2");
        s3.setCustomerLastName("B1");
        s3.setId(3);

        Shipment s4 = new Shipment();
        s4.setCustomerFirstName("A2");
        s4.setCustomerLastName("B2");
        s4.setId(4);

        //-----------

        personSource.batchStart();
        personSource.add(p1);
        personSource.add(p2);
        personSource.add(p3);
        personSource.add(p4);
        personSource.add(p5);
        IndexedView leftIndexedView = personSource.batchEnd();

        shipmentSource.batchStart();
        shipmentSource.add(s1);
        shipmentSource.add(s2);
        shipmentSource.add(s3);
        shipmentSource.add(s4);
        IndexedView rightIndexedView = shipmentSource.batchEnd();

        personAndShipmentNLJ.attemptJoin(leftIndexedView, rightIndexedView);

        //-----------

        System.out.println();

        personSource.batchStart();
        personSource.remove(p2);
        leftIndexedView = personSource.batchEnd();

        shipmentAndPersonNLJ.attemptJoin(rightIndexedView, leftIndexedView);
    }

    public static void main(String[] args) {
        new TwoWayEquiJoinTest().test();
    }
}
