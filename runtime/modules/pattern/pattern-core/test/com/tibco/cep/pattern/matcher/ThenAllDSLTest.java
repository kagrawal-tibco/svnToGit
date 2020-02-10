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

import static com.tibco.cep.pattern.impl.util.Helper.$id;

/*
* Author: Ashwin Jayaprakash Date: Jul 21, 2009 Time: 1:19:56 PM
*/
public class ThenAllDSLTest {
    public static void main(String[] args) throws LifecycleException {
        MatcherAdmin admin = new DefaultMatcherAdmin();
        admin.start();

        //----------------

        new ThenAllDSLTest().test(admin);

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

        PatternDef mainPattern = deployment.createPattern()
                .startsWith(a)
                .thenAll(deployment.list(b).add(c).add(d));

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
        test1(driverOwner, sA, sB, sC, sD);
        System.out.println();

        System.out.println("New test...");
        test2(driverOwner, sA, sB, sC, sD);
        System.out.println();

        System.out.println("New test...");
        test3(driverOwner, sA, sB, sC, sD);
        System.out.println();

        System.out.println("New test...");
        test4(driverOwner, sA, sB, sC, sD);
        System.out.println();
    }

    private static void test1(DriverOwner driverOwner,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-1");

        Response response = driverOwner.onInput(sA, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        response = driverOwner.onInput(sB, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        response = driverOwner.onInput(sC, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        response = driverOwner.onInput(sD, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Complete));
    }

    private static void test2(DriverOwner driverOwner,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-2");

        Response response = driverOwner.onInput(sA, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        response = driverOwner.onInput(sC, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        response = driverOwner.onInput(sD, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        response = driverOwner.onInput(sB, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Complete));
    }

    private static void test3(DriverOwner driverOwner,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-3");

        Response response = driverOwner.onInput(sA, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        response = driverOwner.onInput(sC, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        response = driverOwner.onInput(sD, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        response = driverOwner.onInput(sC, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Failure));
    }

    private static void test4(DriverOwner driverOwner,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-4");

        Response response = driverOwner.onInput(sA, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Success));

        response = driverOwner.onInput(sA, corrId, new DefaultInput());
        System.out.println("Response for driver [" + corrId + "] is: " + response);
        Assert.assertTrue((response instanceof Failure));
    }
}