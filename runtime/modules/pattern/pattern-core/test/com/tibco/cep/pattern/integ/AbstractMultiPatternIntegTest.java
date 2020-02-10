package com.tibco.cep.pattern.integ;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.pattern.integ.admin.Admin;
import com.tibco.cep.pattern.objects.CancelRequest;
import com.tibco.cep.pattern.objects.EOD;
import com.tibco.cep.pattern.objects.FundReady;
import com.tibco.cep.pattern.objects.ReportRequest;
import com.tibco.cep.pattern.subscriber.exception.RoutingException;
import com.tibco.cep.pattern.subscriber.master.EventDescriptor;
import com.tibco.cep.pattern.subscriber.master.EventSource;
import com.tibco.cep.pattern.subscriber.master.Router;
import com.tibco.cep.pattern.util.callback.DriverTracker;

import static com.tibco.cep.pattern.impl.util.Helper.*;

/*
* Author: Ashwin Jayaprakash Date: Aug 27, 2009 Time: 6:11:49 PM
*/
public abstract class AbstractMultiPatternIntegTest {
    protected EventDescriptor<FundReady> fundReadyED;

    protected EventDescriptor<EOD> eodED;

    protected EventDescriptor<ReportRequest> reportRequestED;

    protected EventDescriptor<CancelRequest> cancelRequestED;

    //--------------

    protected EventSource<FundReady> frES;

    protected EventSource<EOD> eodES;

    protected EventSource<ReportRequest> rrES;

    protected EventSource<CancelRequest> crES;

    //--------------

    protected void setUpSources(Admin admin) throws LifecycleException {
        fundReadyED = $eventDescriptor(FundReady.class, "FundReady");
        admin.register(fundReadyED);

        eodED = $eventDescriptor(EOD.class, "EOD");
        admin.register(eodED);

        reportRequestED = $eventDescriptor(ReportRequest.class, "ReportRequest");
        admin.register(reportRequestED);

        cancelRequestED = $eventDescriptor(CancelRequest.class, "CancelRequest");
        admin.register(cancelRequestED);

        //-------------

        frES = $eventSource(fundReadyED);
        admin.deploy(frES);

        eodES = $eventSource(eodED);
        admin.deploy(eodES);

        rrES = $eventSource(reportRequestED);
        admin.deploy(rrES);

        crES = $eventSource(cancelRequestED);
        admin.deploy(crES);
    }

    @SuppressWarnings({"unchecked"})
    protected abstract DriverTracker setUpPattern(Admin admin)
            throws LifecycleException, RoutingException;

    protected void test(Admin admin, DriverTracker driverTracker)
            throws LifecycleException, RoutingException {
        Router router = admin.getRouter();

        //-------------

        final int TOTAL = 1000;

        sendData(router, driverTracker, TOTAL);

        //-------------

        verify(driverTracker, TOTAL);
    }

    protected void sendData(Router router, DriverTracker driverTracker, int TOTAL)
            throws RoutingException {
        long startTime = System.currentTimeMillis();

        for (int i = 1; i <= TOTAL; i++) {
            String accountId = "" + i;

            router.routeMessage(frES, new FundReady(accountId));
        }
        System.out.println("Done sending Funds.");

        while (driverTracker.getDriverTraces().size() < TOTAL) {
            System.out.println("Waiting for all Drivers/Funds to start...");

            try {
                Thread.sleep(50);
            }
            catch (InterruptedException e) {
            }
        }

        long timeTakenMillis = System.currentTimeMillis() - startTime;
        System.out.println(
                "Waited [" + (timeTakenMillis / 1000.0) + "] seconds for Drivers to start.");

        //-------------

        for (int i = 1; i <= TOTAL; i++) {
            String accountId = "" + i;

            router.routeMessage(rrES, new ReportRequest(accountId));
        }
        System.out.println("Done sending Reports.");

        try {
            System.out.println("Waiting for all Reports to be processed...");

            Thread.sleep(timeTakenMillis);
        }
        catch (InterruptedException e) {
        }

        //-------------

        for (int i = 1; i <= TOTAL; i++) {
            if (i % 2 == 0) {
                continue;
            }

            String accountId = "" + i;

            router.routeMessage(crES, new CancelRequest(accountId));
        }
        System.out.println("Done sending Cancellations.");

        try {
            System.out.println("Waiting for all Cancellations to be processed...");

            Thread.sleep(timeTakenMillis);
        }
        catch (InterruptedException e) {
        }

        //-------------

        router.routeMessage(eodES, new EOD($date(24, 9, 2704)));
        System.out.println("Done sending EOD.");

        try {
            System.out.println("Waiting for all EOD to be processed...");

            Thread.sleep(timeTakenMillis);
        }
        catch (InterruptedException e) {
        }
    }

    protected abstract void verify(DriverTracker driverTracker, int TOTAL);
}
