package com.tibco.cep.pattern.integ;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.pattern.integ.admin.Admin;
import com.tibco.cep.pattern.integ.admin.Session;
import com.tibco.cep.pattern.integ.impl.admin.DefaultAdmin;
import com.tibco.cep.pattern.matcher.dsl.PatternDef;
import com.tibco.cep.pattern.matcher.model.InputDef;
import com.tibco.cep.pattern.matcher.response.Complete;
import com.tibco.cep.pattern.matcher.response.Response;
import com.tibco.cep.pattern.subscriber.exception.RoutingException;
import com.tibco.cep.pattern.util.callback.CallTrace;
import com.tibco.cep.pattern.util.callback.DriverTrace;
import com.tibco.cep.pattern.util.callback.DriverTracker;
import com.tibco.cep.pattern.util.callback.TrackableDriverCallbackCreator;
import org.testng.Assert;

import java.util.Collection;
import java.util.LinkedList;

import static com.tibco.cep.pattern.impl.util.Helper.*;

/*
* Author: Ashwin Jayaprakash Date: Aug 27, 2009 Time: 6:11:49 PM
*/
public class MultiPatternIntegTest extends AbstractMultiPatternIntegTest {
    public static void main(String[] args) throws LifecycleException, RoutingException {
        Admin admin = new DefaultAdmin();
        admin.start();

        //--------------

        MultiPatternIntegTest test = new MultiPatternIntegTest();

        test.setUpSources(admin);

        DriverTracker driverTracker = test.setUpPattern(admin);

        test.test(admin, driverTracker);

        //--------------

        admin.stop();
    }

    @SuppressWarnings({"unchecked"})
    public DriverTracker setUpPattern(Admin admin) throws LifecycleException, RoutingException {
        Id id = $id("test-1");

        Session session = admin.create(id);

        //--------------

        session.definePatternSubscription()
                .listenTo($sourceId(fundReadyED))
                .as("fr").use("accountId")

                .alsoListenTo($sourceId(eodED))
                .as("eod").whereMatches("date", $date(24, 9, 2704))

                .alsoListenTo($sourceId(reportRequestED))
                .as("rr").use("accountId");

        //--------------

        InputDef frID = session.defineInput("fr");
        InputDef eodID = session.defineInput("eod");
        InputDef rrID = session.defineInput("rr");

        PatternDef mainPattern = session.createPattern()
                .startsWith(frID)
                .thenAll(session.createList(eodID).add(rrID));

        session.setPattern(mainPattern);

        //--------------

        TrackableDriverCallbackCreator callbackCreator = new TrackableDriverCallbackCreator();
        session.setDriverCallbackCreator(callbackCreator);

        admin.deploy(session);

        return callbackCreator;
    }

    protected void verify(DriverTracker driverTracker, int TOTAL) {
        try {
            System.out.println("Waiting for threads to complete....");
            Thread.sleep(2000);
            System.out.println("Woke up.");
        }
        catch (InterruptedException e) {
        }

        //-------------

        System.out.print("\n\n============================\n\n");

        Collection<DriverTrace> driverTraces = driverTracker.getDriverTraces();

        Assert.assertEquals(driverTraces.size(), TOTAL, "Actual number of DriverTraces is wrong.");

        System.out.println("DriverTracker recorded total: " + driverTraces.size());

        int successCount = 0;

        for (DriverTrace driverTrace : driverTraces) {
            LinkedList<CallTrace> callTraces = driverTrace.getCallTraces();

            Assert.assertTrue((callTraces.size() > 0), "DriverId [" + driverTrace.getDriverId() +
                    "] should've recorded at least 1 CallTrace but it is empty.");

            CallTrace lastCallTrace = callTraces.getLast();
            Response response = lastCallTrace.getResponse();

            System.out.println("   DriverId: " + driverTrace.getDriverId()
                    + ", Last status: " + response
                    + " (Pass: " + (response instanceof Complete) + ")"
                    + ", Completed: " + driverTrace.isStopped());

            if (response instanceof Complete) {
                successCount++;
            }
        }

        Assert.assertEquals(successCount, TOTAL, "Some Drivers did not complete successfully.");
    }
}