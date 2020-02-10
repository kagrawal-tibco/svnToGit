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
import com.tibco.cep.pattern.matcher.response.Failure;
import com.tibco.cep.pattern.matcher.response.Response;
import com.tibco.cep.pattern.matcher.response.Success;
import com.tibco.cep.pattern.util.callback.CallTrace;
import com.tibco.cep.pattern.util.callback.DriverTrace;
import com.tibco.cep.pattern.util.callback.TrackableDriverCallbackCreator;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

import static com.tibco.cep.pattern.impl.util.Helper.*;

/*
* Author: Ashwin Jayaprakash Date: Jul 21, 2009 Time: 1:19:56 PM
*/
public class StartRepeatAndWithinSubDSLTest {
    public static void main(String[] args) throws LifecycleException {
        MatcherAdmin admin = new DefaultMatcherAdmin();
        admin.start();

        //----------------

        new StartRepeatAndWithinSubDSLTest().test(admin);

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

        PatternDef mainPattern = $pattern(deployment)
                .startsWith(a, 0, 3)
                .then(b)
                .thenWithin(5, TimeUnit.SECONDS,
                        $pattern(deployment).startsWith(c, 0, 2).then(d, 0, 1))
                .then(a);

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

        System.out.println("New test...");
        test2(driverOwner, driverCallbackCreator, sA, sB, sC, sD);
        System.out.println();

        System.out.println("New test...");
        test3(driverOwner, driverCallbackCreator, sA, sB, sC, sD);
        System.out.println();

        System.out.println("New test...");
        test4(driverOwner, driverCallbackCreator, sA, sB, sC, sD);
        System.out.println();

        System.out.println("New test...");
        test5(driverOwner, driverCallbackCreator, sA, sB, sC, sD);
        System.out.println();

        System.out.println("New test...");
        test6(driverOwner, driverCallbackCreator, sA, sB, sC, sD);
        System.out.println();

        System.out.println("New test...");
        test7(driverOwner, driverCallbackCreator, sA, sB, sC, sD);
        System.out.println();
    }

    private static void test1(DriverOwner driverOwner,
                              TrackableDriverCallbackCreator driverCallbackCreator,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-1");

        Response response = null;

        RepeatA0To3Times:
        {
            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        JustB:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        Within5CAndD:
        {
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sD, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        JustA:
        {
            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Complete));
        }
    }

    private static void test2(DriverOwner driverOwner,
                              TrackableDriverCallbackCreator driverCallbackCreator,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-2");

        Response response = null;

        RepeatA0To3Times:
        {
        }

        JustB:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        Within5CAndD:
        {
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sD, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        JustA:
        {
            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Complete));
        }
    }

    private static void test3(DriverOwner driverOwner,
                              TrackableDriverCallbackCreator driverCallbackCreator,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-3");

        Response response = null;

        RepeatA0To3Times:
        {
        }

        JustB:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        Within5CAndD:
        {
            response = driverOwner.onInput(sD, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        JustA:
        {
            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Complete));
        }
    }


    private static void test4(DriverOwner driverOwner,
                              TrackableDriverCallbackCreator driverCallbackCreator,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-4");

        Response response = null;

        RepeatA0To3Times:
        {
        }

        JustB:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        Within5CAndD:
        {
        }

        JustA:
        {
            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Complete));
        }
    }

    private static void test5(DriverOwner driverOwner,
                              TrackableDriverCallbackCreator driverCallbackCreator,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-5");

        Response response = null;

        RepeatA0To3Times:
        {
        }

        JustB:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        Within5CAndD:
        {
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            $sleep(8000);
        }

        JustA:
        {
            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Complete));
        }
    }

    private static void test6(DriverOwner driverOwner,
                              TrackableDriverCallbackCreator driverCallbackCreator,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-6");

        Response response = null;

        RepeatA0To3Times:
        {
        }

        JustB:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        Within5CAndD:
        {
        }

        WaitForCompletion:
        {
            DriverTrace driverTrace = driverCallbackCreator.getDriverTrace(corrId);

            Assert.assertFalse(driverTrace.isStopped(),
                    "Driver [" + driverTrace.getDriverId() + "] should not've stopped by now.");

            CallTrace callTrace = driverTrace.getCallTraces().getLast();
            Response finalResponse = callTrace.getResponse();

            Assert.assertTrue((finalResponse instanceof Success),
                    "Driver [" + driverTrace.getDriverId() + "] should not've completed.");
        }
    }

    private static void test7(DriverOwner driverOwner,
                              TrackableDriverCallbackCreator driverCallbackCreator,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-7");

        Response response = null;

        RepeatA0To3Times:
        {
        }

        JustB:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        Within5CAndD:
        {
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            $sleep(8000);

            //Too late.
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Failure));
        }
    }
}