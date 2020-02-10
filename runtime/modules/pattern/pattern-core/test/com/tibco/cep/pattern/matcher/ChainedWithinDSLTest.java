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
import com.tibco.cep.pattern.util.callback.TrackableDriverCallbackCreator;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

import static com.tibco.cep.pattern.impl.util.Helper.*;

/*
* Author: Ashwin Jayaprakash Date: Jul 21, 2009 Time: 1:19:56 PM
*/
public class ChainedWithinDSLTest {
    public static void main(String[] args) throws LifecycleException {
        MatcherAdmin admin = new DefaultMatcherAdmin();
        admin.start();

        //----------------

        new ChainedWithinDSLTest().test(admin);

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
                .then(b)
                .thenWithin(5, TimeUnit.SECONDS, c)
                .thenWithin(5, TimeUnit.SECONDS, c)
                .thenWithin(5, TimeUnit.SECONDS, c)
                .then(d);

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

        $sleep(5000);
        System.out.println("New test...");
        test2(driverOwner, sA, sB, sC, sD);
        System.out.println();
    }

    private static void test1(DriverOwner driverOwner,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-1");

        Response response = null;

        A:
        {
            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        B:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        Within_5_C:
        {
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        Within_5_C:
        {
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        Within_5_C:
        {
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        D:
        {
            response = driverOwner.onInput(sD, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Complete));
        }
    }

    private static void test2(DriverOwner driverOwner,
                              Source sA, Source sB, Source sC, Source sD) {
        Id corrId = new DefaultId("demo-driver-2");

        Response response = null;

        A:
        {
            response = driverOwner.onInput(sA, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        B:
        {
            response = driverOwner.onInput(sB, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        Within_5_C:
        {
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Success));
        }

        $sleep(6500);

        Within_5_C:
        {
            response = driverOwner.onInput(sC, corrId, new DefaultInput());
            System.out.println("Response for driver [" + corrId + "] is: " + response);
            Assert.assertTrue((response instanceof Failure));
        }
    }
}