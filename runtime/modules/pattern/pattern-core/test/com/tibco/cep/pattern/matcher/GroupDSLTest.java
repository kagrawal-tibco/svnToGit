package com.tibco.cep.pattern.matcher;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.impl.common.resource.DefaultId;
import com.tibco.cep.pattern.matcher.admin.MatcherAdmin;
import com.tibco.cep.pattern.matcher.dsl.PatternDef;
import com.tibco.cep.pattern.matcher.dsl.PatternDeploymentDef;
import com.tibco.cep.pattern.matcher.impl.admin.DefaultMatcherAdmin;
import com.tibco.cep.pattern.matcher.impl.model.DefaultInput;
import com.tibco.cep.pattern.matcher.master.DriverOwner;
import com.tibco.cep.pattern.matcher.master.Source;
import com.tibco.cep.pattern.matcher.model.InputDef;
import com.tibco.cep.pattern.matcher.response.Complete;
import com.tibco.cep.pattern.matcher.response.Response;
import com.tibco.cep.pattern.matcher.response.Success;
import com.tibco.cep.pattern.util.callback.CallTrace;
import com.tibco.cep.pattern.util.callback.DriverTrace;
import com.tibco.cep.pattern.util.callback.TrackableDriverCallbackCreator;
import org.testng.Assert;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static com.tibco.cep.pattern.impl.util.Helper.$id;

/*
* Author: Ashwin Jayaprakash Date: Jul 21, 2009 Time: 1:19:56 PM
*/
public class GroupDSLTest {
    public static void main(String[] args) throws LifecycleException {
        MatcherAdmin admin = new DefaultMatcherAdmin();
        admin.start();

        //----------------

        new GroupDSLTest().test(admin);

        //----------------

        admin.stop();
    }

    @SuppressWarnings({"unchecked"})
    public void test(MatcherAdmin admin) throws LifecycleException {
        PatternDeploymentDef deployment = admin.createDeployment($id("demo-1"));
        TrackableDriverCallbackCreator driverCallbackCreator = new TrackableDriverCallbackCreator();

        InputDef a = deployment.createInput("a");
        InputDef b = deployment.createInput("b");
        InputDef c = deployment.createInput("c");
        InputDef d = deployment.createInput("d");

        PatternDef mainPattern = deployment.createPattern()
                .startsWith(a)
                .thenAll(deployment.list(b).add(c))
                .thenAfter(5, TimeUnit.SECONDS)
                .then(d, 3, 5)
                .then(c)
                .thenAfter(5, TimeUnit.SECONDS);

        deployment
                .setPattern(mainPattern)
                .useDedicatedTimer(1)
                .useDriverCallbackCreator(driverCallbackCreator);

        DriverOwner driverOwner = admin.deploy(deployment);

        //----------------

        Source sA = a.getSource();
        Source sB = b.getSource();
        Source sC = c.getSource();
        Source sD = d.getSource();

        System.out.println("New test...");
        test1(driverOwner, driverCallbackCreator, sA, sB, sC, sD);
        System.out.println();
    }

    private static void test1(DriverOwner driverOwner,
                              TrackableDriverCallbackCreator driverCallbackCreator,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-1");

        Response response = driverOwner.onInput(sA, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        BothBAndC:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        After5:
        {
            try {
                System.out.println("Sleeping...");
                Thread.sleep(5100);
                System.out.println("Awoke...");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Repeat3To5Times:
        {
            response = driverOwner.onInput(sD, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sD, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sD, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            try {
                System.out.println("Sleeping...");
                Thread.sleep(7000);
                System.out.println("Awoke...");
            }
            catch (InterruptedException e) {
            }

            response = driverOwner.onInput(sD, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        JustC:
        {
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        AgainAfter5:
        {
            try {
                System.out.println("Sleeping...");
                Thread.sleep(5100);
                System.out.println("Awoke...");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        WaitForCompletion:
        {
            try {
                System.out.println("Waiting for completion...");
                Thread.sleep(2000);
                System.out.println("Awoke...");
            }
            catch (InterruptedException e) {
            }

            //---------------

            Collection<DriverTrace> driverTraces = driverCallbackCreator.getDriverTraces();
            DriverTrace driverTrace = driverTraces.iterator().next();

            Assert.assertTrue(driverTrace.isStopped(),
                    "Driver [" + driverTrace.getDriverId() + "] should've stopped by now.");

            CallTrace callTrace = driverTrace.getCallTraces().getLast();
            Response finalResponse = callTrace.getResponse();

            Assert.assertTrue((finalResponse instanceof Complete),
                    "Driver [" + driverTrace.getDriverId() + "] should've completed.");
        }
    }
}