package com.tibco.cep.pattern.subscriber;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.impl.common.resource.DefaultId;
import com.tibco.cep.pattern.impl.util.ReflectionEventDescriptor;
import com.tibco.cep.pattern.impl.util.SimpleEventSource;
import com.tibco.cep.pattern.objects.EOD;
import com.tibco.cep.pattern.objects.FundReady;
import com.tibco.cep.pattern.objects.ReportRequest;
import com.tibco.cep.pattern.subscriber.admin.AdvancedSubscriberAdmin;
import com.tibco.cep.pattern.subscriber.dsl.SubscriptionDef;
import com.tibco.cep.pattern.subscriber.dsl.SubscriptionDeploymentDef;
import com.tibco.cep.pattern.subscriber.exception.RoutingException;
import com.tibco.cep.pattern.subscriber.impl.admin.DefaultAdvancedSubscriberAdmin;
import com.tibco.cep.pattern.subscriber.master.*;

import static com.tibco.cep.pattern.impl.util.Helper.$date;
import static com.tibco.cep.pattern.impl.util.Helper.$id;

/*
* Author: Ashwin Jayaprakash Date: Aug 12, 2009 Time: 4:03:21 PM
*/
public class SimpleSubscribeTest {
    public static void main(String[] args) throws LifecycleException, RoutingException {
        AdvancedSubscriberAdmin admin = new DefaultAdvancedSubscriberAdmin();
        admin.start();

        //-------------

        new SimpleSubscribeTest().testA(admin);

        //----------------

        admin.stop();
    }

    public void testA(AdvancedSubscriberAdmin admin) throws LifecycleException, RoutingException {
        EventDescriptor<FundReady> fundReadyED =
                new ReflectionEventDescriptor<FundReady>(FundReady.class,
                        new DefaultId("FundReady"));
        admin.register(fundReadyED);

        EventDescriptor<EOD> eodED =
                new ReflectionEventDescriptor<EOD>(EOD.class, new DefaultId("EOD"));
        admin.register(eodED);

        EventDescriptor<ReportRequest> reportRequestED =
                new ReflectionEventDescriptor<ReportRequest>(ReportRequest.class,
                        new DefaultId("ReportRequest"));
        admin.register(reportRequestED);

        //-------------

        EventSource<FundReady> frES = new SimpleEventSource<FundReady>(fundReadyED);
        admin.deploy(frES);

        EventSource<EOD> eodES = new SimpleEventSource<EOD>(eodED);
        admin.deploy(eodES);

        EventSource<ReportRequest> rrES = new SimpleEventSource<ReportRequest>(reportRequestED);
        admin.deploy(rrES);

        //-------------

        SubscriptionDeploymentDef deployment = admin.createDeployment($id("test-1"));

        SubscriptionDef subscription = deployment.create()
                .listenTo(fundReadyED.getResourceId())
                .as("fr").use("accountId")
                .forwardTo($listener("fr"))

                .alsoListenTo(eodED.getResourceId())
                .as("eod").whereMatches("date", $date(24, 9, 2704))
                .forwardTo($listener("eod"))

                .alsoListenTo(reportRequestED.getResourceId())
                .as("rr").use("accountId")
                .forwardTo($listener("rr"));

        deployment.setSubscription(subscription);

        SubscriptionCaretaker caretaker = admin.deploy(deployment);
        Id subscriptionId = caretaker.getId();

        //-------------

        Router router = admin.getRouter();

        router.routeMessage(frES, new FundReady("A777"));
        router.routeMessage(eodES, new EOD($date(24, 9, 2704)));
        router.routeMessage(rrES, new ReportRequest("A777"));

        //-------------

        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
        }
    }

    public static SubscriptionListener $listener(String alias) {
        return new SimpleSubscriptionListener(alias);
    }
}
