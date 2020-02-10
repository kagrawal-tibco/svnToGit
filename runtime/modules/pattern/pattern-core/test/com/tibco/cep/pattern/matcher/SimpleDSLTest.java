package com.tibco.cep.pattern.matcher;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.impl.common.resource.DefaultId;
import com.tibco.cep.pattern.matcher.admin.MatcherAdmin;
import com.tibco.cep.pattern.matcher.dsl.PatternDef;
import com.tibco.cep.pattern.matcher.dsl.PatternDeploymentDef;
import com.tibco.cep.pattern.matcher.impl.admin.DefaultMatcherAdmin;
import com.tibco.cep.pattern.matcher.impl.model.DefaultInput;
import com.tibco.cep.pattern.matcher.master.DriverCallbackCreator;
import com.tibco.cep.pattern.matcher.master.DriverOwner;
import com.tibco.cep.pattern.matcher.master.Source;
import com.tibco.cep.pattern.matcher.model.InputDef;
import com.tibco.cep.pattern.matcher.response.Complete;
import com.tibco.cep.pattern.matcher.response.Failure;
import com.tibco.cep.pattern.matcher.response.Response;
import com.tibco.cep.pattern.matcher.response.Success;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

import static com.tibco.cep.pattern.impl.util.Helper.$id;

/*
* Author: Ashwin Jayaprakash Date: Jul 21, 2009 Time: 1:19:56 PM
*/
public class SimpleDSLTest {
    public static void main(String[] args) throws LifecycleException {
        MatcherAdmin admin = new DefaultMatcherAdmin();
        admin.start();

        //----------------

        new SimpleDSLTest().test(admin);

        //----------------

        admin.stop();
    }

    @SuppressWarnings({"unchecked"})
    public void test(MatcherAdmin admin) throws LifecycleException {
        PatternDeploymentDef deployment = admin.createDeployment($id("demo-1"));
        DriverCallbackCreator driverCallbackCreator = SimpleDriverCallback.CREATOR;

        InputDef a = deployment.createInput("a");
        InputDef b = deployment.createInput("b");
        InputDef c = deployment.createInput("c");
        InputDef d = deployment.createInput("d");
        InputDef e = deployment.createInput("e");
        InputDef f = deployment.createInput("f");
        InputDef g = deployment.createInput("g");

        PatternDef endSubPattern = deployment.createPattern()
                .startsWith(d)
                .then(e, 2, 5)
                .then(f)
                .thenWithin(10, TimeUnit.SECONDS, g);

        PatternDef mainPattern = deployment.createPattern()
                .startsWith(a)
                .then(b)
                .thenAnyOne(deployment.list(a).add(b))
                .thenAfter(5, TimeUnit.SECONDS)
                .then(c)
                .then(endSubPattern);

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
        Source sE = e.getSource();
        Source sF = f.getSource();
        Source sG = g.getSource();

        System.out.println("New test...");
        test1(driverOwner, sA, sB, sC, sD, sE, sF, sG);
        System.out.println();

        System.out.println("New test...");
        test2(driverOwner, sA, sB, sC, sD, sE, sF, sG);
        System.out.println();

        System.out.println("New test...");
        test3(driverOwner, sA, sB, sC, sD, sE, sF, sG);
        System.out.println();

        System.out.println("New test...");
        test4(driverOwner, sA, sB, sC, sD, sE, sF, sG);
        System.out.println();
    }

    private static void test1(DriverOwner driverOwner,
                              Source sA, Source sB, Source sC, Source sD,
                              Source sE, Source sF, Source sG) {
        Id corrId = new DefaultId("demo-driver-1");

        Response response = driverOwner.onInput(sA, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        response = driverOwner.onInput(sB, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        AOrB:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        After5:
        {
            try {
                System.out.println("Sleeping..");
                Thread.sleep(6500);
                System.out.println("Awoke..");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        response = driverOwner.onInput(sC, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        response = driverOwner.onInput(sD, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        RepeatE2To5:
        {
            response = driverOwner.onInput(sE, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sE, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sE, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        response = driverOwner.onInput(sF, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        Within10:
        {
            response = driverOwner.onInput(sG, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Complete));
        }
    }

    private static void test2(DriverOwner driverOwner,
                              Source sA, Source sB, Source sC, Source sD,
                              Source sE, Source sF, Source sG) {
        Id corrId = new DefaultId("demo-driver-2");

        Response response = driverOwner.onInput(sA, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        response = driverOwner.onInput(sB, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        AOrB:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        After5:
        {
            //No waiting.
        }

        response = driverOwner.onInput(sC, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Failure));
    }

    private static void test3(DriverOwner driverOwner,
                              Source sA, Source sB, Source sC, Source sD,
                              Source sE, Source sF, Source sG) {
        Id corrId = new DefaultId("demo-driver-3");

        Response response = driverOwner.onInput(sA, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        response = driverOwner.onInput(sB, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        AOrB:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        After5:
        {
            try {
                System.out.println("Sleeping..");
                Thread.sleep(6500);
                System.out.println("Awoke..");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        response = driverOwner.onInput(sC, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        response = driverOwner.onInput(sD, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        RepeatE2To5:
        {
            //Just once.

            response = driverOwner.onInput(sE, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        response = driverOwner.onInput(sF, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Failure));
    }

    private static void test4(DriverOwner driverOwner,
                              Source sA, Source sB, Source sC, Source sD,
                              Source sE, Source sF, Source sG) {
        Id corrId = new DefaultId("demo-driver-4");

        Response response = driverOwner.onInput(sA, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        response = driverOwner.onInput(sB, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        AOrB:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        After5:
        {
            try {
                System.out.println("Sleeping..");
                Thread.sleep(6500);
                System.out.println("Awoke..");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        response = driverOwner.onInput(sC, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        response = driverOwner.onInput(sD, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        RepeatE2To5:
        {
            response = driverOwner.onInput(sE, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sE, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));

            response = driverOwner.onInput(sE, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        response = driverOwner.onInput(sF, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        Within10:
        {
            //Delay.
            try {
                System.out.println("Sleeping..");
                Thread.sleep(13000);
                System.err.println("Awoke..You should've seen a TimeOut error by now.");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            response = driverOwner.onInput(sG, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Failure));
        }
    }
}
