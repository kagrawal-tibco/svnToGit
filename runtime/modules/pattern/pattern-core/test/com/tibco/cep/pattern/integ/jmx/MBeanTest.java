package com.tibco.cep.pattern.integ.jmx;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.pattern.integ.admin.Admin;
import com.tibco.cep.pattern.integ.admin.Session;
import com.tibco.cep.pattern.integ.impl.admin.DefaultAdmin;
import com.tibco.cep.pattern.integ.impl.jmx.MBeanManager;
import com.tibco.cep.pattern.matcher.SimpleDriverCallback;
import com.tibco.cep.pattern.matcher.dsl.PatternDef;
import com.tibco.cep.pattern.matcher.model.InputDef;
import com.tibco.cep.pattern.objects.EOD;
import com.tibco.cep.pattern.objects.FundReady;
import com.tibco.cep.pattern.objects.ReportRequest;
import com.tibco.cep.pattern.subscriber.exception.RoutingException;
import com.tibco.cep.pattern.subscriber.master.EventDescriptor;
import com.tibco.cep.pattern.subscriber.master.EventSource;
import com.tibco.cep.pattern.subscriber.master.Router;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.tibco.cep.pattern.impl.util.Helper.*;

/*
* Author: Ashwin Jayaprakash Date: Oct 8, 2009 Time: 11:16:20 AM
*/
public class MBeanTest {
    protected EventDescriptor<FundReady> fundReadyED;

    protected EventDescriptor<EOD> eodED;

    protected EventDescriptor<ReportRequest> reportRequestED;

    //--------------

    protected EventSource<FundReady> frES;

    protected EventSource<EOD> eodES;

    protected EventSource<ReportRequest> rrES;

    //--------------

    public static void main(String[] args) throws Exception {
        Admin admin = new DefaultAdmin();
        admin.start();

        //--------------

        MBeanTest test = new MBeanTest();

        test.setUp(admin);

        test.test(admin);

        //--------------

        MBeanManager mBeanManager = new MBeanManager();
        mBeanManager.init(admin);

        //--------------

        admin.stop();
    }

    @SuppressWarnings({"unchecked"})
    public void test(Admin admin) throws LifecycleException, RoutingException, ExecutionException {
        Id id = $id("test-1");

        Session session = admin.create(id);

        //--------------

        session.definePatternSubscription()
                .listenTo(fundReadyED.getResourceId())
                .as("fr").use("accountId")

                .alsoListenTo(eodED.getResourceId())
                .as("eod").whereMatches("date", $date(24, 9, 2704))

                .alsoListenTo(reportRequestED.getResourceId())
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

        session.setDriverCallbackCreator(SimpleDriverCallback.CREATOR);

        session.captureSequence();

        admin.deploy(session);

        //--------------

        Router router = admin.getRouter();

        LinkedList<Future> routingJobs = new LinkedList<Future>();
        router.routeMessage(frES, new FundReady("A777"), routingJobs);
        int c = $waitForAll(routingJobs);

        router.routeMessage(eodES, new EOD($date(24, 9, 2704)), routingJobs);
        c = c + $waitForAll(routingJobs);

        router.routeMessage(rrES, new ReportRequest("A777"), routingJobs);
        c = c + $waitForAll(routingJobs);

        //-------------

        System.out.println("Total routings: " + c);

        try {
            Thread.sleep(1500);
        }
        catch (InterruptedException e) {
        }
    }

    public void setUp(Admin admin) throws LifecycleException {
        fundReadyED = $eventDescriptor(FundReady.class, "FundReady");
        admin.register(fundReadyED);

        eodED = $eventDescriptor(EOD.class, "EOD");
        admin.register(eodED);

        reportRequestED = $eventDescriptor(ReportRequest.class, "ReportRequest");
        admin.register(reportRequestED);

        //-------------

        frES = $eventSource(fundReadyED);
        admin.deploy(frES);

        eodES = $eventSource(eodED);
        admin.deploy(eodES);

        rrES = $eventSource(reportRequestED);
        admin.deploy(rrES);
    }
}