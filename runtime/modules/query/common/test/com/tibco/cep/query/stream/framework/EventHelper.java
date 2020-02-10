/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 11/8/2010
 */

package com.tibco.cep.query.stream.framework;

import com.tangosol.net.NamedCache;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.stream.aggregate.AggregateItemInfo;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.context.QueryContext;
import com.tibco.cep.query.stream.core.DefaultTupleValueExtractor;
import com.tibco.cep.query.stream.expression.TupleValueExtractor;
import com.tibco.cep.query.stream.group.GroupAggregateItemInfo;
import com.tibco.cep.query.stream.group.GroupItemInfo;
import com.tibco.cep.query.stream.impl.aggregate.*;
import com.tibco.cep.query.stream.impl.rete.HeavyReteEntity;
import com.tibco.cep.query.stream.impl.rete.ReteEntityInfo;
import com.tibco.cep.query.stream.rete.ReteEntityInfoImpl;
import com.tibco.cep.query.stream.tuple.AbstractTupleInfo;
import com.tibco.cep.query.stream.tuple.HeavyTuple;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.FixedKeys;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransaction;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/*
* Author: Karthikeyan Subramanian / Date: Feb 3, 2010 / Time: 12:37:41 PM
*/
public class EventHelper {

    // Class to type id mapping.
    protected final Map<String, Integer> classToTypeMap;
    private final String[] eventNames;
    private Class personEventClass;
    private Class windowEventClass;
    // Concept helper
    private ConceptHelper conceptHelper;
    // Rtc Transaction Helper
    private RtcTransactionHelper rtcTransactionHelper;
    // Txn Id
    private AtomicInteger txnId;
    // Agent Id
    private int agentId;
    // Master cache
    private NamedCache masterCache;
    // Maps
    private Map<Class, String[]> columnsMap = new HashMap<Class, String[]>();
    private Map<Class, Class[]> typesMap = new HashMap<Class, Class[]>();

    public EventHelper(
            String[] eventNames, Map<String, Integer> classToTypeMap, RtcTransactionHelper rtcTransactionHelper,
            ConceptHelper conceptHelper, AtomicInteger txnId, int agentId, NamedCache masterCache) {
        this.classToTypeMap = classToTypeMap;
        this.eventNames = eventNames;
        this.conceptHelper = conceptHelper;
        this.agentId = agentId;
        this.rtcTransactionHelper = rtcTransactionHelper;
        this.masterCache = masterCache;
        this.txnId = txnId;
        initClasses();
    }

    private void initClasses() {
        // Init event classes.
        try {
            windowEventClass = Class.forName("be.gen.Events.WindowEvent1");
            personEventClass = Class.forName("be.gen.PersonEvent");
        } catch (Exception ex) {
            // Ignore
        }
        // Person Event
        columnsMap.put(personEventClass, new String[]{"personEvent"});
        typesMap.put(personEventClass, new Class[]{personEventClass});
        // Window event.
        columnsMap.put(windowEventClass, new String[]{"event"});
        typesMap.put(windowEventClass, new Class[]{windowEventClass});
    }

    public List<Entity> addPersonEvents(String[] entries, int age, int start, int end) throws Exception {
        int typeId = classToTypeMap.get("be.gen.PersonEvent");
        LinkedList<Entity> createdEvents = new LinkedList<Entity>();
        NamedCache personCache = conceptHelper.getEntityCache(personEventClass);
        Constructor constructor = personEventClass.getConstructor(new Class[]{long.class,
                String.class});
        Method setterMethod = personEventClass.getDeclaredMethod("setProperty", new Class[]{
                String.class, Object.class});
        for (int i = start; i < end; i++) {
            Object event = constructor.newInstance(new Object[]{new Long(i),
                    i + ":" + personEventClass});

            setterMethod.invoke(event, new Object[]{"age", age});
            setterMethod.invoke(event, new Object[]{"firstName", "fn: " + i});
            setterMethod
                    .invoke(event, new Object[]{"lastName", entries[i % entries.length]});

            personCache.put(new Long(i), event);
            createdEvents.add((Entity) event);
            RtcTransaction txn = rtcTransactionHelper.addEvent(txnId.incrementAndGet(), agentId, typeId, event);
            byte[] serialize = rtcTransactionHelper.serializeTxn(txn);
            masterCache.put(txn.getTxnId(), serialize);
        }
        return createdEvents;
    }

    public List<Entity> addPersonEvents(String[] entries, int start, int end) throws Exception {
        int typeId = classToTypeMap.get("be.gen.PersonEvent");
        LinkedList<Entity> createdEvents = new LinkedList<Entity>();
        NamedCache personCache = conceptHelper.getEntityCache(personEventClass);
        Constructor constructor = personEventClass.getConstructor(new Class[]{long.class,
                String.class});
        Method setterMethod = personEventClass.getDeclaredMethod("setProperty", new Class[]{
                String.class, Object.class});
        for (int i = start; i < end; i++) {
            Object event = constructor.newInstance(new Object[]{new Long(i),
                    i + ":" + personEventClass});

            setterMethod.invoke(event, new Object[]{"age", i});
            setterMethod.invoke(event, new Object[]{"firstName", "fn: " + i});
            setterMethod
                    .invoke(event, new Object[]{"lastName", entries[i % entries.length]});

            personCache.put(new Long(i), event);
            createdEvents.add((Entity) event);
            RtcTransaction txn = rtcTransactionHelper.addEvent(txnId.incrementAndGet(), agentId, typeId, event);
            byte[] serialize = rtcTransactionHelper.serializeTxn(txn);
            masterCache.put(txn.getTxnId(), serialize);
        }
        return createdEvents;
    }

    public List<Entity> addPersonEvents() throws Exception {
        return addPersonEvents(new String[]{"Clarke", "Baxter", "Asimov", "Brin"}, 25, 0, 50);
    }

    public List<Entity> addPersonEvents(int start, int end) throws Exception {
        return addPersonEvents(new String[]{"Clarke", "Baxter", "Asimov", "Brin"}, 25, start, end);
    }

    public List<Entity> addPersonEvents(String[] entries, int age) throws Exception {
        return addPersonEvents(entries, age, 0, 50);
    }

    public List<Entity> addWindowEvents() throws Exception {
        int typeId = classToTypeMap.get("be.gen.Events.WindowEvent1");
        LinkedList<Entity> createdEvents = new LinkedList<Entity>();

        NamedCache eventCache = conceptHelper.getEntityCache(windowEventClass);

        Constructor constructor = windowEventClass
                .getConstructor(new Class[]{long.class, String.class});
        Method setterMethod = windowEventClass.getDeclaredMethod("setProperty", new Class[]{
                String.class, Object.class});
        String[] lastNames = {"Clarke", "Baxter", "Asimov", "Brin", "Le Guin"};
        for (int i = 0; i < 50; i++) {
            Object event = constructor
                    .newInstance(new Object[]{new Long(i), i + ":" + windowEventClass});
            setterMethod.invoke(event, new Object[]{"prop1", lastNames[i % lastNames.length]});
            setterMethod.invoke(event, new Object[]{"prop2", ((25 + i) % lastNames.length)});
            setterMethod.invoke(event, new Object[]{"prop3", i});

            eventCache.put(new Long(i), event);
            createdEvents.add((Entity) event);

            RtcTransaction txn = rtcTransactionHelper.addEvent(txnId.incrementAndGet(), agentId, typeId, event);
            byte[] serialize = rtcTransactionHelper.serializeTxn(txn);

            masterCache.put(txn.getTxnId(), serialize);
        }
        return createdEvents;
    }

    public LinkedHashMap<String, GroupAggregateItemInfo> getItemInfos(Class entityClass) {
        LinkedHashMap<String, GroupAggregateItemInfo> itemInfos =
                new LinkedHashMap<String, GroupAggregateItemInfo>();
        if (entityClass.equals(personEventClass)) {
            itemInfos.put("lastName", new GroupAggregateItemInfo(new GroupItemInfo(
                    new DefaultTupleValueExtractor() {
                        @Override
                        public Object extract(
                                GlobalContext globalContext, QueryContext queryContext,
                                Tuple tuple) {
                            SimpleEvent event = (SimpleEvent) tuple.getColumn(0);
                            Object result = null;
                            try {
                                result = event.getProperty("lastName");
                            }
                            catch (NoSuchFieldException e) {
                                e.printStackTrace(System.err);
                            }
                            return result;
                        }
                    })));
            itemInfos.put("count", new GroupAggregateItemInfo(new AggregateItemInfo(
                    CountAggregator.CREATOR, new DefaultTupleValueExtractor() {
                        @Override
                        public Object extract(
                                GlobalContext globalContext, QueryContext queryContext,
                                Tuple tuple) {
                            SimpleEvent event = (SimpleEvent) tuple.getColumn(0);
                            Object result = null;
                            try {
                                result = event.getProperty("age");
                            }
                            catch (NoSuchFieldException e) {
                                e.printStackTrace(System.err);
                            }
                            return result;
                        }
                    })));
            itemInfos.put("sum", new GroupAggregateItemInfo(new AggregateItemInfo(
                    IntegerSumAggregator.CREATOR, new DefaultTupleValueExtractor() {
                        @Override
                        public Object extract(
                                GlobalContext globalContext, QueryContext queryContext,
                                Tuple tuple) {
                            SimpleEvent event = (SimpleEvent) tuple.getColumn(0);
                            Object result = null;
                            try {
                                result = event.getProperty("age");
                            }
                            catch (NoSuchFieldException e) {
                                e.printStackTrace(System.err);
                            }
                            return result;
                        }
                    })));
            itemInfos.put("avg", new GroupAggregateItemInfo(new AggregateItemInfo(
                    AverageAggregator.CREATOR, new DefaultTupleValueExtractor() {
                        @Override
                        public Object extract(
                                GlobalContext globalContext, QueryContext queryContext,
                                Tuple tuple) {
                            SimpleEvent event = (SimpleEvent) tuple.getColumn(0);
                            Object result = null;
                            try {
                                result = event.getProperty("age");
                            }
                            catch (NoSuchFieldException e) {
                                e.printStackTrace(System.err);
                            }
                            return result;
                        }
                    })));
            itemInfos.put("min", new GroupAggregateItemInfo(new AggregateItemInfo(
                    NullsLargestMinAggregator.CREATOR, new DefaultTupleValueExtractor() {
                        @Override
                        public Object extract(
                                GlobalContext globalContext, QueryContext queryContext,
                                Tuple tuple) {
                            SimpleEvent event = (SimpleEvent) tuple.getColumn(0);
                            Object result = null;
                            try {
                                result = event.getProperty("age");
                            }
                            catch (NoSuchFieldException e) {
                                e.printStackTrace(System.err);
                            }
                            return result;
                        }
                    })));
            itemInfos.put("max", new GroupAggregateItemInfo(new AggregateItemInfo(
                    NullsSmallestMaxAggregator.CREATOR, new DefaultTupleValueExtractor() {
                        @Override
                        public Object extract(
                                GlobalContext globalContext, QueryContext queryContext,
                                Tuple tuple) {
                            SimpleEvent event = (SimpleEvent) tuple.getColumn(0);
                            Object result = null;
                            try {
                                result = event.getProperty("age");
                            }
                            catch (NoSuchFieldException e) {
                                e.printStackTrace(System.err);
                            }
                            return result;
                        }
                    })));
        } else if (entityClass.equals(windowEventClass)) {
            itemInfos.put("prop1", new GroupAggregateItemInfo(new GroupItemInfo(
                    new DefaultTupleValueExtractor() {
                        @Override
                        public Object extract(
                                GlobalContext globalContext, QueryContext queryContext,
                                Tuple tuple) {
                            Object retVal = null;
                            try {
                                retVal = ((SimpleEvent) tuple.getColumn(0)).getProperty("prop1");
                            }
                            catch (NoSuchFieldException e) {
                                e.printStackTrace(System.err);
                            }
                            return retVal;
                        }
                    })));
            itemInfos.put("count", new GroupAggregateItemInfo(new AggregateItemInfo(
                    CountAggregator.CREATOR, new DefaultTupleValueExtractor() {
                        @Override
                        public Object extract(
                                GlobalContext globalContext, QueryContext queryContext,
                                Tuple tuple) {
                            Object retVal = null;
                            try {
                                retVal = ((SimpleEvent) tuple.getColumn(0)).getProperty("prop2");
                            }
                            catch (NoSuchFieldException e) {
                                e.printStackTrace(System.err);
                            }
                            return retVal;
                        }
                    })));
            itemInfos.put("max", new GroupAggregateItemInfo(new AggregateItemInfo(
                    NullsSmallestMaxAggregator.CREATOR, new DefaultTupleValueExtractor() {
                        @Override
                        public Object extract(
                                GlobalContext globalContext, QueryContext queryContext,
                                Tuple tuple) {
                            Object retVal = null;
                            try {
                                retVal = ((SimpleEvent) tuple.getColumn(0)).getProperty("prop3");
                            }
                            catch (NoSuchFieldException e) {
                                e.printStackTrace(System.err);
                            }
                            return retVal;
                        }
                    })));
        }
        return itemInfos;
    }

    public void deleteAndReinsertEvents(int typeId, List<Entity> events) throws Exception {
        int x = events.size();

        RtcTransaction txn = rtcTransactionHelper.createNewRtcTransaction();

        Iterator<Entity> entityIterator = events.iterator();
        for (; entityIterator.hasNext();) {
            Entity entity = entityIterator.next();
            rtcTransactionHelper.deleteEvent(txn, entity, typeId);

            x--;
            if (x == 1) {
                break;
            }
        }

        //todo Event cannot really be modified. This is just a simulation.
        //todo Partition Windows other than "SimpleWindow" cannot handle Updates.
        Entity entity = entityIterator.next();
        rtcTransactionHelper.addEvent(txn, agentId, typeId, entity);

        byte[] serialize = rtcTransactionHelper.serializeTxn(txn);
        masterCache.put(txn.getTxnId(), serialize);
    }

    public void removeEntities(List<Entity> entities, int typeId) throws Exception {
        RtcTransaction txn = rtcTransactionHelper.createNewRtcTransaction();
        for (Entity entity : entities) {
            rtcTransactionHelper.deleteEvent(txn, entity, typeId);
        }
        byte[] serialize = rtcTransactionHelper.serializeTxn(txn);
        masterCache.put(txn.getTxnId(), serialize);
    }

    public List<Comparator<Object>> getPersonEventComparators() {
        List<Comparator<Object>> comparators = new ArrayList<Comparator<Object>>(2);
        comparators.add(new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                if (o1 == FixedKeys.NULL) {
                    if (o2 == FixedKeys.NULL) {
                        return 0;
                    }

                    // Nulls first.
                    return -1;
                } else if (o2 == FixedKeys.NULL) {
                    return 1;
                }

                int thisVal = (Integer) o1;
                int anotherVal = (Integer) o2;

                return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
            }
        });
        comparators.add(new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                if (o1 == FixedKeys.NULL) {
                    if (o2 == FixedKeys.NULL) {
                        return 0;
                    }

                    // Nulls first.
                    return -1;
                } else if (o2 == FixedKeys.NULL) {
                    return 1;
                }

                String thisVal = (String) o1;
                String anotherVal = (String) o2;

                return thisVal.compareTo(anotherVal);
            }
        });
        return comparators;
    }

    public TupleValueExtractor[] getPersonEventTupleValueExtractors() {
        TupleValueExtractor[] extractors = new DefaultTupleValueExtractor[2];
        extractors[0] = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(
                    GlobalContext globalContext, QueryContext queryContext,
                    Tuple tuple) {
                // Count column.
                return tuple.getColumn(1);
            }
        };
        extractors[1] = new DefaultTupleValueExtractor() {
            @Override
            public Object extract(
                    GlobalContext globalContext, QueryContext queryContext,
                    Tuple tuple) {
                // Name column.
                return tuple.getColumn(0);
            }
        };
        return extractors;
    }

    public ReteEntityInfo getReteEntityInfo(Class klass) {
        if (klass.equals(personEventClass)) {
            return new ReteEntityInfoImpl(PersonEventTuple.class, columnsMap.get(klass), typesMap.get(klass));
        } else if (klass.equals(windowEventClass)) {
            return new ReteEntityInfoImpl(EventTuple.class, columnsMap.get(klass), typesMap.get(klass));
        }
        return null;
    }

    public TupleInfo getTupleInfo(Class klass) {
        if (klass.equals(personEventClass)) {
            return new AbstractTupleInfo(OutputTuple.class, new String[]{
                    "lastName", "count", "sum", "avg", "min", "max"}, new Class[]{String.class,
                    Integer.class, Integer.class, Double.class, Integer.class, Integer.class}) {
                public Tuple createTuple(Number id) {
                    return new OutputTuple(id);
                }
            };
        } else if (klass.equals(windowEventClass)) {
            return new AbstractTupleInfo(OutputTuple.class, new String[]{"prop1", "count", "max"},
                    new Class[]{String.class, Integer.class, Double.class}) {
                public Tuple createTuple(Number id) {
                    return new OutputTuple(id);
                }
            };
        }
        return null;
    }


    public static class PersonEventTuple extends HeavyReteEntity {

        public PersonEventTuple(Number id) {
            super(id);
        }
    }

    public static class EventTuple extends HeavyReteEntity {

        public EventTuple(Number id) {
            super(id);
        }
    }

    public static class OutputTuple extends HeavyTuple {

        public OutputTuple(Number id) {
            super(id);
        }

        public OutputTuple(Number id, Object[] columns) {
            super(id, columns);
        }
    }
}
