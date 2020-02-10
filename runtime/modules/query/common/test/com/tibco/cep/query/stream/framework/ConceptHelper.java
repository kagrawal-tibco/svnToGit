/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.query.stream.framework;

import com.tangosol.net.NamedCache;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.stream.impl.rete.HeavyReteEntity;
import com.tibco.cep.query.stream.join.HeavyJoinedTuple;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomIntSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomStringSimple;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.cluster.system.impl.ObjectTupleImpl;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransaction;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
* Author: Karthikeyan Subramanian / Date: Feb 3, 2010 / Time: 12:57:35 PM
*/
public class ConceptHelper {
    protected HashMap<Class, NamedCache> entityCaches;
    private CustomMaster master;
    private Map<String, Integer> classToTypeMap;
    // Master cache
    private NamedCache masterCache;
    // Cache cluster
    private CacheCluster cacheCluster;
    // Classes
    private Class customerClass;
    private Class addressClass;
    private Class orderClass;
    // Rtc transaction helper
    private RtcTransactionHelper rtcTransactionHelper;
    // Agent id
    private int agentId;

    public ConceptHelper(CustomMaster master, RtcTransactionHelper rtcTransactionHelper, NamedCache masterCache, int agentId,
                         CacheCluster hackedCacheCluster, HashMap<Class, NamedCache> entityCaches, Map<String, Integer> classToTypeMap) {
        this.master = master;
        this.masterCache = masterCache;
        this.entityCaches = entityCaches;
        this.classToTypeMap = classToTypeMap;
        this.agentId = agentId;
        this.rtcTransactionHelper = rtcTransactionHelper;
        this.cacheCluster = hackedCacheCluster;
        initClasses();
    }

    private void initClasses() {
        try {
            addressClass = Class.forName("be.gen.Concepts.Address");
            customerClass = Class.forName("be.gen.Concepts.Customer");
            orderClass = Class.forName("be.gen.Concepts.Order");
        } catch (Exception ex) {
            // Ignore
        }
    }

    public NamedCache getEntityCache(Class entityClass) {
        NamedCache nc = entityCaches.get(entityClass);

        if (nc == null) {
            nc = master.getAgentService().getEntityCache(entityClass);

            entityCaches.put(entityClass, nc);
        }

        return nc;
    }

    public void hackConcept(ConceptImpl concept) throws Exception {
        Field field = ConceptImpl.class.getDeclaredField("checkSession");
        field.setAccessible(true);
        field.set(concept, false);
    }

    public List<Entity> addCustomerAndAddress(int size) throws Exception {
        int customerId = classToTypeMap.get("be.gen.Concepts.Customer");
        int addressId = classToTypeMap.get("be.gen.Concepts.Address");
        LinkedList<Entity> createdEvents = new LinkedList<Entity>();

        NamedCache customerCache = getEntityCache(customerClass);
        NamedCache addressCache = getEntityCache(addressClass);
        Map<Long, ObjectTupleImpl> objTableEntries = new HashMap<Long, ObjectTupleImpl>();

        int idGen = -1;
        RtcTransaction txn = rtcTransactionHelper.createNewRtcTransaction();
        for (int i = 0; i < size; i++) {
            idGen++;
            int custId = idGen;
            Object customer = addCustomer(custId, 30 - i);

            idGen++;
            int address1Id = idGen;
            Object address1 = addAddress(address1Id, "USA");

            idGen++;
            int address2Id = idGen;
            Object address2 = addAddress(address2Id, "Timbuctoo");

            addAddressToCustomer(customer, address1, address2);

            objTableEntries.put((long) address1Id, new ObjectTupleImpl(address1Id, ((Concept) address1).getExtId(), addressId));
            objTableEntries.put((long) address2Id, new ObjectTupleImpl(address2Id, ((Concept) address2).getExtId(), addressId));

            // ----------

            customerCache.put(new Long(custId), customer);
            createdEvents.add((Entity) customer);

            objTableEntries.put((long) custId, new ObjectTupleImpl(custId, ((Concept) customer).getExtId(), customerId));

            addressCache.put(new Long(address1Id), address1);
            addressCache.put(new Long(address2Id), address2);

            rtcTransactionHelper.addConcept(txn, (Concept) customer, customerId);
        }
        updateObjectTable(objTableEntries, txn);
        return createdEvents;
    }

    public void updateObjectTable(Map<Long, ObjectTupleImpl> objTableEntries,
                                  RtcTransaction txn) throws Exception {
        byte[] serialize = rtcTransactionHelper.serializeTxn(txn);
        ObjectTable objectTable = cacheCluster.getObjectTableCache();
        HashMap<Long, byte[]> serializedEntityTuples = new HashMap<Long, byte[]>();
        for (Map.Entry<Long, ObjectTupleImpl> entityTupleEntry : objTableEntries.entrySet()) {
            byte[] bytes = ObjectTupleImpl.serialize(entityTupleEntry.getValue());

            serializedEntityTuples.put(entityTupleEntry.getKey(), bytes);
        }
        objectTable.putAll(serializedEntityTuples);
        masterCache.put(txn.getTxnId(), serialize);
    }

    public Object addCustomer(int custId, int age) throws Exception {
        Constructor customerConstructor = customerClass.getConstructor(new Class[]{long.class,
                String.class});
        Object customer = customerConstructor.newInstance(new Object[]{new Long(custId),
                custId + ":" + customerClass});

        Property ageProp = ((Concept) customer).getProperty("age");
        hackConcept((ConceptImpl) (ageProp).getParent());
        ((PropertyAtomIntSimple) ageProp).setInt(age);

        Property nameProp = ((Concept) customer).getProperty("name");
        hackConcept((ConceptImpl) (nameProp).getParent());
        ((PropertyAtomStringSimple) nameProp).setString("fn: " + custId);
        return customer;
    }

    public Object addAddress(int addressId, String country) throws Exception {
        Constructor addressConstructor = addressClass.getConstructor(new Class[]{long.class,
                String.class});
        Object address = addressConstructor.newInstance(new Object[]{new Long(addressId),
                addressId + ":" + addressClass});
        Property countryProp = ((Concept) address).getProperty("country");
        hackConcept((ConceptImpl) (countryProp).getParent());
        ((PropertyAtomStringSimple) countryProp).setString(country);
        return address;
    }

    public Object addOrder(int orderId, int custId) throws Exception {
        Constructor orderConstructor = orderClass.getConstructor(new Class[]{long.class,
                String.class});
        Object order = orderConstructor.newInstance(new Object[]{new Long(orderId),
                orderId + ":" + orderClass});
        Property customerIdProp = ((Concept) order).getProperty("customerId");
        hackConcept((ConceptImpl) (customerIdProp).getParent());
        ((PropertyAtomLong) customerIdProp).setLong(custId);
        return order;
    }

    public void addAddressToCustomer(Object customer, Object... addresses) throws Exception {
        Object addressRef = ((Concept) customer).getProperty("addresses");
        PropertyArrayContainedConcept addressConcepts =
                (PropertyArrayContainedConcept) addressRef;
        for (Object address : addresses) {
            addressConcepts.add(address);
        }
    }

    public List<Entity> addCustomerAndOrder(int size) throws Exception {
        int orderId = classToTypeMap.get("be.gen.Concepts.Order");
        LinkedList<Entity> createdEvents = new LinkedList<Entity>();
        Map<Long, ObjectTupleImpl> objTableEntries = new HashMap<Long, ObjectTupleImpl>();
        int customerId = classToTypeMap.get("be.gen.Concepts.Customer");
        NamedCache customerCache = getEntityCache(customerClass);
        NamedCache orderCache = getEntityCache(orderClass);
        RtcTransaction txn = rtcTransactionHelper.createNewRtcTransaction();
        int idGen = -1;
        for (int i = 0; i < size; i++) {

            idGen++;
            int custId = idGen;
            Object customer = addCustomer(custId, 30 - i);

            customerCache.put(new Long(custId), customer);
            createdEvents.add((Entity) customer);
            // Now make object table entries.
            objTableEntries.put((long) custId, new ObjectTupleImpl(custId, ((Concept) customer).getExtId(), customerId));
            rtcTransactionHelper.addConcept(txn, (Concept) customer, customerId);

            // ----------

            idGen++;
            int ordId = idGen;
            Object order = addOrder(ordId, custId);

            orderCache.put(new Long(ordId), order);
            createdEvents.add((Entity) order);
            // Now make object table entries.
            objTableEntries.put((long) ordId, new ObjectTupleImpl(ordId, ((Concept) order).getExtId(), orderId));
            rtcTransactionHelper.addConcept(txn, (Concept) order, orderId);
        }
        updateObjectTable(objTableEntries, txn);

        return createdEvents;
    }

    public void deleteAndModifyCustomer(List<Entity> events)
            throws Exception {
        NamedCache customerCache = getEntityCache(customerClass);
        int customerId = classToTypeMap.get("be.gen.Concepts.Customer");

        int i = 0;

        for (Entity entity : events) {
            long custId = entity.getId();
            RtcTransaction txn = rtcTransactionHelper.createNewRtcTransaction();
            // Delete the first one.
            if (i == 0) {
                Long id = new Long(custId);

                customerCache.remove(id);
                rtcTransactionHelper.deleteConcept(txn, (Concept) entity, customerId);
                byte[] serialize = rtcTransactionHelper.serializeTxn(txn);

                masterCache.put(txn.getTxnId(), serialize);
            } else {
                Property ageProp = ((Concept) entity).getProperty("age");
                int oldAge = ((PropertyAtomIntSimple) ageProp).getInt();
                ((PropertyAtomIntSimple) ageProp).setInt(oldAge * -1);

                customerCache.put(new Long(custId), entity);
                rtcTransactionHelper.modifyConcept(txn, (Concept) entity, customerId);
                byte[] serialize = rtcTransactionHelper.serializeTxn(txn);
                cacheCluster.getObjectTableCache().removeAllFromObjectIdTable(txn.getObjectTable_delete());
                masterCache.put(txn.getTxnId(), serialize);
            }
            i++;
        }
    }

    public static class CustomerTuple extends HeavyReteEntity {
        public CustomerTuple(Number id) {
            super(id);
        }
    }

    public static class CustomerAndOrderTuple extends HeavyJoinedTuple {
        public CustomerAndOrderTuple(Number id) {
            super(id);
        }

        public CustomerAndOrderTuple(Long id, Object[] columns) {
            super(id, columns);
        }
    }
}
