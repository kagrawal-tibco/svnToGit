package com.tibco.cep.pattern.integ;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.pattern.integ.admin.Admin;
import com.tibco.cep.pattern.integ.admin.Session;
import com.tibco.cep.pattern.integ.impl.admin.DefaultAdmin;
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
* Author: Ashwin Jayaprakash Date: Aug 27, 2009 Time: 6:11:49 PM
*/
public class SubPatternIntegTest {
    protected EventDescriptor<FundReady> fundReadyED;

    protected EventDescriptor<EOD> eodED;

    protected EventDescriptor<ReportRequest> reportRequestED;

    //--------------

    protected EventSource<FundReady> frES;

    protected EventSource<EOD> eodES;

    protected EventSource<ReportRequest> rrES;

    //--------------

    public static void main(String[] args)
            throws LifecycleException, RoutingException, ExecutionException {
        Admin admin = new DefaultAdmin();
        admin.start();

        //--------------

        SubPatternIntegTest test = new SubPatternIntegTest();

        test.setUp(admin);

        test.test(admin);

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

        PatternDef subPattern1 = session.createPattern().startsWith(eodID).then(rrID);
        PatternDef subPattern2 = session.createPattern().startsWith(rrID);

        PatternDef mainPattern = session.createPattern()
                .startsWith(frID)
                .thenAll(session.createList(subPattern1).add(subPattern2));

        session.setPattern(mainPattern);

        //--------------

        session.setDriverCallbackCreator(SimpleDriverCallback.CREATOR);

        admin.deploy(session);

        //--------------

        Router router = admin.getRouter();

        LinkedList<Future> routingJobs = new LinkedList<Future>();

        //--------------

        System.out.println("New test...");

        router.routeMessage(frES, new FundReady("A777"), routingJobs);
        int c = $waitForAll(routingJobs);

        router.routeMessage(eodES, new EOD($date(24, 9, 2704)), routingJobs);
        c += $waitForAll(routingJobs);

        router.routeMessage(rrES, new ReportRequest("A777"), routingJobs);
        c += $waitForAll(routingJobs);

        router.routeMessage(rrES, new ReportRequest("A777"), routingJobs);
        c += $waitForAll(routingJobs);

        System.out.println("Total routings: " + c);

        //--------------

        System.out.println("New test...");

        router.routeMessage(frES, new FundReady("A777"), routingJobs);
        c = $waitForAll(routingJobs);

        router.routeMessage(rrES, new ReportRequest("A777"), routingJobs);
        c += $waitForAll(routingJobs);

        router.routeMessage(eodES, new EOD($date(24, 9, 2704)), routingJobs);
        c += $waitForAll(routingJobs);

        router.routeMessage(rrES, new ReportRequest("A777"), routingJobs);
        c += $waitForAll(routingJobs);

        System.out.println("Total routings: " + c);
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