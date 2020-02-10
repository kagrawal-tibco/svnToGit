package com.tibco.cep.runtime.util;

import com.tibco.cep.query.stream.framework.SimpleLogger;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
* Author: Ashwin Jayaprakash Date: May 12, 2009 Time: 11:43:13 AM
*/
public class CustomBlockingRejectionHandlerTest {
    public static void main(String[] args) {
        LinkedBlockingQueue<Runnable> q = new LinkedBlockingQueue<Runnable>(5);

        ThreadPoolExecutor tpe = new ThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS,
                q, new CustomBlockingRejectionHandler("$pool", new SimpleLogger(), 13));

        tpe.execute(new Job());
        tpe.execute(new Job());
        tpe.execute(new Job());
        tpe.execute(new Job());
        tpe.execute(new Job());

        tpe.execute(new Job());
        tpe.execute(new Job());
        tpe.execute(new Job());
        tpe.execute(new Job());
        tpe.execute(new Job());

        tpe.execute(new Job());
        tpe.execute(new Job());

        System.out.println("One more extra job.");

        tpe.execute(new Job());
        tpe.execute(new Job());
        tpe.execute(new Job());

        System.out.println("Done.");
    }

    protected static class Job implements Runnable {
        public void run() {
            try {
                System.out.println(Thread.currentThread() + " sleeping");

                Thread.sleep(Integer.MAX_VALUE);
            }
            catch (InterruptedException e) {
            }
        }
    }
}
