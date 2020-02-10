package com.tibco.be.rms.driver.http.test;

import com.tibco.cep.mgmtserver.rms.MethodBuilder;
import com.tibco.cep.mgmtserver.rms.utils.RequestHeaders;
import com.tibco.cep.mgmtserver.rms.builder.impl.GetMethodBuilder;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.CountDownLatch;


/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Sep 17, 2008
 * Time: 12:21:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class HTTPChannelTest {

    static String url = "http://localhost:5000/Transports/Channels/HTTPChannel/PerfTestDestination";

    public static void main(String[] r) {
        int numThreads = Integer.parseInt(r[0]);
        test(numThreads);
    }

    private static void test(int numberOfThreads) {
        // Create a method instance.
        MethodBuilder<GetMethod> builder = new GetMethodBuilder();
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < numberOfThreads; i++) {
            service.execute(new Requestor(builder, latch));
        }
        long t2 = System.currentTimeMillis();
        try {
            //Wait till all threads complete their work
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(t2 - t1);
        service.shutdown();
    }

    static class Requestor implements Runnable {

        private MethodBuilder<GetMethod> methodBuilder;

        private CountDownLatch latch;

        Requestor(MethodBuilder<GetMethod> methodBuilder, CountDownLatch latch) {
            this.methodBuilder = methodBuilder;
            this.latch = latch;
        }
        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p/>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        public synchronized void run() {
            execute(methodBuilder);
            //Decrement the latch
            latch.countDown();
        }
    }

    static void execute(MethodBuilder<GetMethod> methodBuilder) {
        GetMethod method = null;
        try {
            HttpClient client = new HttpClient();
            RequestHeaders reqHeaders = new RequestHeaders();
            reqHeaders.setUsername("test");
            method = methodBuilder.buildMethod(url,
                "PerfEvent", "www.tibco.com/be/ontology/Test/PerfEvent",
                reqHeaders, null);


            // Execute the method.
            int statusCode = client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                // This should not come for get
            }

        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {           // Release the connection.
            method.releaseConnection();
        }
    }
}
