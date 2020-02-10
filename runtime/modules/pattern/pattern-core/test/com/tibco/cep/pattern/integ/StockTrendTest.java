package com.tibco.cep.pattern.integ;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.pattern.integ.admin.Admin;
import com.tibco.cep.pattern.integ.admin.Session;
import com.tibco.cep.pattern.integ.impl.admin.DefaultAdmin;
import com.tibco.cep.pattern.matcher.dsl.PatternDef;
import com.tibco.cep.pattern.matcher.impl.master.ReflectionTransitionGuardSetCreator;
import com.tibco.cep.pattern.matcher.master.*;
import com.tibco.cep.pattern.matcher.model.InputDef;
import com.tibco.cep.pattern.matcher.response.*;
import com.tibco.cep.pattern.objects.StockTick;
import com.tibco.cep.pattern.subscriber.exception.RoutingException;
import com.tibco.cep.pattern.subscriber.master.EventDescriptor;
import com.tibco.cep.pattern.subscriber.master.EventSource;
import com.tibco.cep.pattern.subscriber.master.Router;
import com.tibco.cep.pattern.util.callback.CallTrace;
import com.tibco.cep.pattern.util.callback.DriverTrace;
import com.tibco.cep.pattern.util.callback.DriverTracker;
import com.tibco.cep.pattern.util.callback.TrackableDriverCallbackCreator;
import org.testng.Assert;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.tibco.cep.pattern.impl.util.Helper.*;

/*
* Author: Ashwin Jayaprakash Date: Aug 27, 2009 Time: 6:11:49 PM
*/
public class StockTrendTest {
    protected EventDescriptor<StockTick> stockTickED;

    //--------------

    protected EventSource<StockTick> stES;

    //--------------

    protected static ConcurrentHashMap<Long, StockTick> STOCK_TICKS =
            new ConcurrentHashMap<Long, StockTick>();

    public static void main(String[] args) throws LifecycleException, RoutingException {
        Admin admin = new DefaultAdmin();
        admin.start();

        //--------------

        StockTrendTest test = new StockTrendTest();

        test.setUpSources(admin);

        DriverTracker driverTracker = test.setUpPattern(admin);

        test.test(admin, driverTracker);

        //--------------

        admin.stop();
    }

    protected void setUpSources(Admin admin) throws LifecycleException {
        stockTickED = $eventDescriptor(StockTick.class, "StockTick");
        admin.register(stockTickED);

        //-------------

        stES = $eventSource(stockTickED);
        admin.deploy(stES);
    }

    @SuppressWarnings({"unchecked"})
    protected DriverTracker setUpPattern(Admin admin)
            throws LifecycleException, RoutingException {
        Id id = $id("test-1");

        Session session = admin.create(id);

        //--------------

        session.definePatternSubscription()
                .listenTo(stockTickED.getResourceId())
                .as("st").use("symbol");

        //--------------

        InputDef stID = session.defineInput("st");

        PatternDef mainPattern = session.createPattern()
                .startsWith(stID, 1, 3, $guardClosure("10Pts"));

        session.setPattern(mainPattern);

        //--------------

        TrackableDriverCallbackCreator callbackCreator = new TrackableDriverCallbackCreator();
        session.setDriverCallbackCreator(callbackCreator);

        ReflectionTransitionGuardSetCreator guardSetCreator = $guardSetCreator()
                .addClosureAndGuard($guardClosure("10Pts"), TenPointsTransitionGuard.class);
        session.setTransitionGuardSetCreator(guardSetCreator);

        admin.deploy(session);

        return callbackCreator;
    }

    protected void test(Admin admin, DriverTracker driverTracker)
            throws LifecycleException, RoutingException {
        Router router = admin.getRouter();

        //-------------

        sendData(router, driverTracker);

        //-------------

        verify(driverTracker);
    }

    protected void sendData(Router router, DriverTracker driverTracker)
            throws RoutingException {
        STOCK_TICKS.clear();

        route(router, "TIBX", 100.0, 10, 1);
        $sleep(1000);
        route(router, "TIBX", 102.0, 10, 2);
        $sleep(1000);

        route(router, "TIBX", 100.0, 10, 3);
        $sleep(1000);
        route(router, "TIBX", 90.0, 10, 4);
        $sleep(1000);
        route(router, "TIBX", 80.0, 10, 5);
        $sleep(1000);

        route(router, "TIBX", 70.0, 10, 6);
        $sleep(1000);
        route(router, "TIBX", 60.0, 10, 7);
        $sleep(1000);
        route(router, "TIBX", 50.0, 10, 8);
        $sleep(1000);

        route(router, "TIBX", 90.0, 10, 9);
        $sleep(1000);
        route(router, "TIBX", 120.0, 10, 10);
        $sleep(1000);

        route(router, "TIBX", 110.0, 10, 11);
        $sleep(1000);
        route(router, "TIBX", 100.0, 10, 12);
        $sleep(1000);
    }

    private void route(Router router, String symbol, double price, long volume, long uniqueId)
            throws RoutingException {
        StockTick stockTick = new StockTick(symbol, price, volume, uniqueId);

        STOCK_TICKS.put(uniqueId, stockTick);

        router.routeMessage(stES, stockTick);
    }

    protected void verify(DriverTracker driverTracker) {
        System.out.println("Sleeping...");
        try {
            Thread.sleep(5000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Awoke.");

        System.out.println("Everything but the last 1 should've completed by now");
        System.out.println("=====================================");

        //-------------

        Collection<Id> ids = driverTracker.getDriverCorrelationIds();
        Assert.assertEquals(ids.size(), 1, "There should've been just 1 set of DriverIds.");

        Id id = ids.iterator().next();
        List<DriverTrace> driverTraces = driverTracker.getDriverTraces(id);
        Assert.assertEquals(driverTraces.size(), 5,
                "There should've been 5 DriverTraces for the DriverId [" + id + "].");

        DriverTrace driverTrace = driverTraces.get(0);
        checkDriverTrace(driverTrace, 0, true, Failure.class);

        driverTrace = driverTraces.get(1);
        checkDriverTrace(driverTrace, 1, true, Complete.class);

        driverTrace = driverTraces.get(2);
        checkDriverTrace(driverTrace, 2, true, Complete.class);

        driverTrace = driverTraces.get(3);
        checkDriverTrace(driverTrace, 3, true, Failure.class);

        driverTrace = driverTraces.get(4);
        checkDriverTrace(driverTrace, 4, false, Success.class);
    }

    private void checkDriverTrace(DriverTrace driverTrace, int index, boolean expectStopped,
                                  Class<? extends Response> expectedLastStatus) {
        LinkedList<CallTrace> callTraces = driverTrace.getCallTraces();

        CallTrace lastCallTrace = callTraces.getLast();
        Response response = lastCallTrace.getResponse();

        System.out.println("   DriverId: " + driverTrace.getDriverId()
                + ", End status: " + response.getClass().getName() + ""
                + ", Completed: " + driverTrace.isStopped());

        Assert.assertEquals(driverTrace.isStopped(), expectStopped,
                "Driver stop status is wrong: " + index);

        Assert.assertTrue((expectedLastStatus.isInstance(response)),
                "Driver last status of [" + index + "] is wrong [" + response + "]. Expected ["
                        + expectedLastStatus + "].");
    }

    //-------------

    protected static class TenPointsTransitionGuard implements TransitionGuard {
        protected double previousPrice = Double.MIN_VALUE;

        protected DriverView driverView;

        public TenPointsTransitionGuard() {
        }

        public void init(ResourceProvider resourceProvider, DriverView driverView,
                         TransitionGuardSet guardSet) {
            this.driverView = driverView;
        }

        public void onInput(Source source, Input input) {
            Object key = input.getKey();
            StockTick sentData = STOCK_TICKS.get(key);
            double currentPrice = sentData.getPrice();

            //-------------

            if (previousPrice == Double.MIN_VALUE) {
                previousPrice = currentPrice;

                System.out.println("New transition >> Source [" + source + "], [Input: " + input
                        + "], [Current value: " + currentPrice + "]");

                return;
            }

            //-------------

            double diff = (previousPrice - currentPrice);

            if (diff != 10.0) {
                Exception e = new Exception(
                        "Transition >> Source [" + source + "], [Input: " + input
                                + "], [Previous value: " + previousPrice
                                + "], [Current value: " + currentPrice + "]");

                Failure failure = new UnexpectedOccurrence(input, source, e);
                driverView.flag(failure);
            }
            else {
                System.out.println("Transition >> Source [" + source + "], [Input: " + input
                        + "], [Previous value: " + previousPrice
                        + "], [Current value: " + currentPrice + "]");

                previousPrice = currentPrice;
            }
        }
    }
}