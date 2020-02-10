/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 27/8/2010
 */

package com.tibco.cep.query.stream.rete;

import com.tangosol.net.NamedCache;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.core.Source;
import com.tibco.cep.query.stream.framework.AbstractCacheTest;
import com.tibco.cep.query.stream.framework.TestGroupNames;
import com.tibco.cep.query.stream.impl.rete.*;
import com.tibco.cep.query.stream.impl.rete.integ.Manager;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.impl.rete.query.ReteQueryImpl;
import com.tibco.cep.query.stream.impl.rete.service.RegionManager;
import com.tibco.cep.query.stream.impl.rete.service.ReteEntityDispatcher;
import com.tibco.cep.query.stream.io.StreamedSink;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomIntSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomStringSimple;
import com.tibco.cep.runtime.service.cluster.om.RtcChangeStatus;
import com.tibco.cep.runtime.service.cluster.system.impl.ObjectTupleImpl;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransaction;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 6:27:33 PM
 */

public class ReteCQClassHierarchyTest extends AbstractCacheTest {

    protected Class[] allCustomerClasses;
    private int customerId, goldCustomerId, silverCustomerId, gcSecMemberId;

    @Test(groups = {TestGroupNames.RUNTIME})
    public void test() {
        customerId = classToTypeMap.get("be.gen.Concepts.Customer");
        goldCustomerId = classToTypeMap.get("be.gen.Concepts.GoldCustomer");
        silverCustomerId = classToTypeMap.get("be.gen.Concepts.SilverCustomer");
        gcSecMemberId = classToTypeMap.get("be.gen.Concepts.GoldCustomerSecondaryMember");
        ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
        try {
            ClassLoader loader = getEntityClassLoader();
            Thread.currentThread().setContextClassLoader(loader);

            runTest();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            Thread.currentThread().setContextClassLoader(oldCL);
        }
    }

    @Override
    protected RegionManagerInterceptor provideRegionManagerInterceptor(
            RegionManager rm) {
        try {
            Class customerClass = Class.forName("be.gen.Concepts.Customer");
            Class customerClass2 = Class.forName("be.gen.Concepts.GoldCustomer");
            Class customerClass3 = Class.forName("be.gen.Concepts.SilverCustomer");
            Class customerClass4 = Class.forName("be.gen.Concepts.GoldCustomerSecondaryMember");

            allCustomerClasses =
                    new Class[]{customerClass, customerClass2, customerClass3, customerClass4};
            hackAndInterceptRSP();
            BEClassLoader testBECL = new HackedBEClassLoader(allCustomerClasses,
                    getClass().getClassLoader(), 9090, rsp);

            ReteEntityClassHierarchyHelper hierarchyHelper =
                    new ReteEntityClassHierarchyHelper(testBECL);
            return new Interceptor(rm, hierarchyHelper);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    protected void runTest() throws Exception {
        final Class customerClass = Class.forName("be.gen.Concepts.Customer");
        final Class customerClass2 = Class.forName("be.gen.Concepts.GoldCustomer");
        final Class customerClass3 = Class.forName("be.gen.Concepts.SilverCustomer");
        final Class customerClass4 = Class.forName("be.gen.Concepts.GoldCustomerSecondaryMember");

        // ----------

        AtomicInteger idGen = new AtomicInteger(-1);

        doTest(idGen, customerClass,
                new Class[]{customerClass, customerClass2, customerClass3, customerClass4},
                new int[]{customerId, goldCustomerId, silverCustomerId, gcSecMemberId});

        System.err.println("======== New set ========");

        doTest(idGen, customerClass2, new Class[]{customerClass2, customerClass4}, new int[]{goldCustomerId, gcSecMemberId});
    }

    @Override
    public ReteQuery getReteQuery(String resourceId, Source[] sources) {
        return new ReteQueryImpl(master.getAgentService().getName(), new ResourceId(resourceId),
                (ReteEntitySource[]) sources, sink, false, null);
    }

    private void doTest(
            AtomicInteger idGen, Class rootClass, final Class[] allCustomerClasses,
            int[] typeIds)
            throws Exception {
        String[] columnNames = new String[]{"customer"};
        Class[] columnTypes = new Class[]{rootClass};
        final ReteEntityInfo customerReteEntityInfo =
                new ReteEntityInfoImpl(CustomerTuple.class, columnNames,
                        columnTypes);
        ReteEntitySource customerSource = new ReteEntitySourceImpl(new ResourceId(
                "CustomerSource"), customerReteEntityInfo, rootClass);
        sources.clear();
        sources.put(customerSource.getResourceId().getId(), customerSource);

        // ----------

        sink = new StreamedSink(customerSource.getInternalStream(), new ResourceId("Sink"));

        ReteQuery query = getReteQuery("AllCustomersQry", new ReteEntitySource[]{customerSource});

        ReteEntityDispatcher dispatcher = regionManager.getDispatcher();

        try {
            query.init(repo, null);
            query.start();

            dispatcher.registerQuery(query);

            testHierarchy(idGen, allCustomerClasses, typeIds);
        }
        finally {
            dispatcher.unregisterAndStopQuery(query);

            tearDownHierarchyHelper();
        }
    }

    private void tearDownHierarchyHelper() {
        String regionName = regionManager.getRegionName();

        Registry.getInstance().getComponent(Manager.class).getRegionManagers()
                .put(regionName, regionManager);
    }

    private void testHierarchy(
            AtomicInteger idGen, Class[] allCustomerClasses,
            int[] typeIds) throws Exception {
        final int firstId = idGen.get() + 1;

        List<Entity> entities = doAdds(idGen, allCustomerClasses, typeIds);

        Thread.sleep(4 * 1000);
        List<Object[]> expectedResults = new ArrayList<Object[]>();
        LinkedIdentityHashMap<Tuple, Object[]> received =
                collectAndMatchStreamedSink(expectedResults,
                        10);

        ArrayList<Tuple> lastSet = new ArrayList<Tuple>(received.keySet());

        Assert.assertEquals(lastSet.size(), allCustomerClasses.length,
                "Not all Customers were returned.");

        HashMap<Integer, Class> expectedCustomers = new HashMap<Integer, Class>();
        for (int i = 0; i < allCustomerClasses.length; i++) {
            int index = i + firstId;

            expectedCustomers.put(index, allCustomerClasses[i]);
        }

        for (Tuple tuple : lastSet) {
            Concept customer = (Concept) tuple.getColumn(0);

            Long l = customer.getId();
            Class c = expectedCustomers.remove(l.intValue());

            int index = l.intValue() - firstId;
            Assert.assertEquals(c, allCustomerClasses[index]);
        }

        Assert.assertTrue(expectedCustomers.isEmpty(), "Not all Customers were received.");
    }

    protected List<Entity> doAdds(AtomicInteger idGen, Class[] customerClasses, int[] typeIds)
            throws Exception {
        LinkedList<Entity> createdEvents = new LinkedList<Entity>();
        Map<Long, ObjectTupleImpl> objTableEntries = new HashMap<Long, ObjectTupleImpl>();
        RtcTransaction txn = rtcTransactionHelper.createNewRtcTransaction();
        for (int i = 0; i < customerClasses.length; i++) {
            Class customerClass = customerClasses[i];
            int id = typeIds[i];
            NamedCache customerCache = conceptHelper.getEntityCache(customerClass);

            Constructor customerConstructor = customerClass.getConstructor(new Class[]{long.class,
                    String.class});

            int custId = idGen.incrementAndGet();
            Object customer = customerConstructor.newInstance(new Object[]{new Long(custId),
                    custId + ":" + customerClass});

            Property ageProp = ((Concept) customer).getProperty("age");
            conceptHelper.hackConcept((ConceptImpl) (ageProp).getParent());
            ((PropertyAtomIntSimple) ageProp).setInt(30 - idGen.get());

            Property nameProp = ((Concept) customer).getProperty("name");
            conceptHelper.hackConcept((ConceptImpl) (nameProp).getParent());
            ((PropertyAtomStringSimple) nameProp).setString(customerClass.getName() + ":" + custId);

            customerCache.put(new Long(custId), customer);
            objTableEntries.put((long) custId, new ObjectTupleImpl(custId, ((Concept) customer).getExtId(), id));
            createdEvents.add((Entity) customer);
            rtcTransactionHelper.addConcept(txn, (Concept) customer, id, new RtcChangeStatus((byte) 0, new int[]{}));
        }
        conceptHelper.updateObjectTable(objTableEntries, txn);
        return createdEvents;
    }

    //---------------

    public static class CustomerTuple extends HeavyReteEntity {

        public CustomerTuple(Number id) {
            super(id);
        }
    }

    protected static class Interceptor extends RegionManagerInterceptor {

        protected ReteEntityClassHierarchyHelper heirarchyHelper;

        public Interceptor(
                RegionManager defaultRM,
                ReteEntityClassHierarchyHelper heirarchyHelper) {
            super(defaultRM);

            this.heirarchyHelper = heirarchyHelper;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("getHierarchyHelper")) {
                return heirarchyHelper;
            }

            return method.invoke(defaultRM, args);
        }
    }

    private static class HackedBEClassLoader extends BEClassLoader {

        private Class[] classes;

        private HackedBEClassLoader(Class[] classes, ClassLoader classLoader,
                                    int jdiPort, RuleServiceProvider serviceProvider) {
            super(classLoader, jdiPort, serviceProvider);
            this.classes = classes;

        }

        @Override
        public Collection getClasses() {
            LinkedList list = new LinkedList();
            for (Class customerClass : classes) {
                list.add(customerClass);
            }
            return list;
        }
    }
}