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

import static com.tibco.cep.pattern.impl.util.Helper.$id;
import static com.tibco.cep.pattern.impl.util.Helper.$pattern;

/*
* Author: Ashwin Jayaprakash Date: Jul 21, 2009 Time: 1:19:56 PM
*/
public class RepeatSubDSLTest {
    public static void main(String[] args) throws LifecycleException {
        MatcherAdmin admin = new DefaultMatcherAdmin();
        admin.start();

        //----------------

        new RepeatSubDSLTest().test(admin);

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
                .startsWith(a)
                .then($pattern(deployment).startsWith(b, 0, 2).then(c, 0, 1))
                        /*
                        todo Validate - The alternative path (d) must not have b or c - that would
                        be an ambiguity.
                        */
                .then(d)
                        /*
                        todo Validate - The next item in a {0, x} sequence must not have the same
                        elements as the first one.

                        ( (a|b) -> (b -> c) ) is ambiguous.

                        ( ((a){2} -> (b){0, 1}) -> (b -> c) ) is ambiguous.                                                
                        */
                .then($pattern(deployment).startsWith(a, 0, 2).then(b, 0, 1), 0, 2)
                .then(c);

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

        JustA:
        {
            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        B0To2ThenC0To1:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        JustD:
        {
            response = driverOwner.onInput(sD, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        RepeatTwice_A0To2ThenB0To1:
        {
            //First: AAB

            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            //--------------

            //Second: AB

            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        JustC:
        {
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
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

            DriverTrace driverTrace = driverCallbackCreator.getDriverTrace(corrId);

            Assert.assertTrue(driverTrace.isStopped(),
                    "Driver [" + driverTrace.getDriverId() + "] should've stopped by now.");

            CallTrace callTrace = driverTrace.getCallTraces().getLast();
            Response finalResponse = callTrace.getResponse();

            Assert.assertTrue((finalResponse instanceof Complete),
                    "Driver [" + driverTrace.getDriverId() + "] should've completed.");
        }
    }

    private static void test2(DriverOwner driverOwner,
                              TrackableDriverCallbackCreator driverCallbackCreator,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-2");

        Response response = null;

        JustA:
        {
            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        B0To2ThenC0To1:
        {
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        JustD:
        {
            response = driverOwner.onInput(sD, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        RepeatTwice_A0To2ThenB0To1:
        {
            //First: B

            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            //--------------

            //Second: B

            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        JustC:
        {
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
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

            DriverTrace driverTrace = driverCallbackCreator.getDriverTrace(corrId);

            Assert.assertTrue(driverTrace.isStopped(),
                    "Driver [" + driverTrace.getDriverId() + "] should've stopped by now.");

            CallTrace callTrace = driverTrace.getCallTraces().getLast();
            Response finalResponse = callTrace.getResponse();

            Assert.assertTrue((finalResponse instanceof Complete),
                    "Driver [" + driverTrace.getDriverId() + "] should've completed.");
        }
    }

    private static void test3(DriverOwner driverOwner,
                              TrackableDriverCallbackCreator driverCallbackCreator,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-3");

        Response response = null;

        JustA:
        {
            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        B0To2ThenC0To1:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        JustD:
        {
            response = driverOwner.onInput(sD, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        RepeatTwice_A0To2ThenB0To1:
        {
            //First: B

            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        JustC:
        {
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
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

            DriverTrace driverTrace = driverCallbackCreator.getDriverTrace(corrId);

            Assert.assertTrue(driverTrace.isStopped(),
                    "Driver [" + driverTrace.getDriverId() + "] should've stopped by now.");

            CallTrace callTrace = driverTrace.getCallTraces().getLast();
            Response finalResponse = callTrace.getResponse();

            Assert.assertTrue((finalResponse instanceof Complete),
                    "Driver [" + driverTrace.getDriverId() + "] should've completed.");
        }
    }

    private static void test4(DriverOwner driverOwner,
                              TrackableDriverCallbackCreator driverCallbackCreator,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-4");

        Response response = null;

        JustA:
        {
            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        B0To2ThenC0To1:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        JustD:
        {
            response = driverOwner.onInput(sD, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        RepeatTwice_A0To2ThenB0To1:
        {
            //First: B

            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            //Second: A

            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        JustC:
        {
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
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

            DriverTrace driverTrace = driverCallbackCreator.getDriverTrace(corrId);

            Assert.assertTrue(driverTrace.isStopped(),
                    "Driver [" + driverTrace.getDriverId() + "] should've stopped by now.");

            CallTrace callTrace = driverTrace.getCallTraces().getLast();
            Response finalResponse = callTrace.getResponse();

            Assert.assertTrue((finalResponse instanceof Complete),
                    "Driver [" + driverTrace.getDriverId() + "] should've completed.");
        }
    }

    private static void test5(DriverOwner driverOwner,
                              TrackableDriverCallbackCreator driverCallbackCreator,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-5");

        Response response = null;

        JustA:
        {
            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        B0To2ThenC0To1:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        JustD:
        {
            response = driverOwner.onInput(sD, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        RepeatTwice_A0To2ThenB0To1:
        {

        }

        JustC:
        {
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
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

            DriverTrace driverTrace = driverCallbackCreator.getDriverTrace(corrId);

            Assert.assertTrue(driverTrace.isStopped(),
                    "Driver [" + driverTrace.getDriverId() + "] should've stopped by now.");

            CallTrace callTrace = driverTrace.getCallTraces().getLast();
            Response finalResponse = callTrace.getResponse();

            Assert.assertTrue((finalResponse instanceof Complete),
                    "Driver [" + driverTrace.getDriverId() + "] should've completed.");
        }
    }

    private static void test6(DriverOwner driverOwner,
                              TrackableDriverCallbackCreator driverCallbackCreator,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-6");

        Response response = null;

        JustA:
        {
            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        B0To2ThenC0To1:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            //Wrong input - B instead of C.
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Failure));
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

            DriverTrace driverTrace = driverCallbackCreator.getDriverTrace(corrId);

            Assert.assertTrue(driverTrace.isStopped(),
                    "Driver [" + driverTrace.getDriverId() + "] should've stopped by now.");

            CallTrace callTrace = driverTrace.getCallTraces().getLast();
            Response finalResponse = callTrace.getResponse();

            Assert.assertFalse((finalResponse instanceof Complete),
                    "Driver [" + driverTrace.getDriverId() + "] should not've completed.");
        }
    }

    private static void test7(DriverOwner driverOwner,
                              TrackableDriverCallbackCreator driverCallbackCreator,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-7");

        Response response = null;

        JustA:
        {
            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        B0To2ThenC0To1:
        {
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        JustD:
        {
            response = driverOwner.onInput(sD, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        RepeatTwice_A0To2ThenB0To1:
        {
            //First: B

            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            //--------------

            //Second: AAB

            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        JustC:
        {
            //Wrong input
            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Failure));
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

            DriverTrace driverTrace = driverCallbackCreator.getDriverTrace(corrId);

            Assert.assertTrue(driverTrace.isStopped(),
                    "Driver [" + driverTrace.getDriverId() + "] should've stopped by now.");

            CallTrace callTrace = driverTrace.getCallTraces().getLast();
            Response finalResponse = callTrace.getResponse();

            Assert.assertFalse((finalResponse instanceof Complete),
                    "Driver [" + driverTrace.getDriverId() + "] should not've completed.");
        }
    }
}