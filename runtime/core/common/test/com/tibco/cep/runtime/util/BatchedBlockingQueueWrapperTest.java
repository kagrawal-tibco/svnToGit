package com.tibco.cep.runtime.util;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/*
* Author: Ashwin Jayaprakash Date: May 12, 2009 Time: 4:43:33 PM
*/
public class BatchedBlockingQueueWrapperTest {
    public static void main(String[] args) {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<Integer>();
        BatchedOneWayBlockingQueue<Integer> wrapper =
                new BatchedOneWayBlockingQueue<Integer>(q, 5);

        //------------

        for (int i = 0; i < 47; i++) {
            q.add(i);
        }

        while (wrapper.isEmpty() == false) {
            Collection<Integer> items = wrapper.poll();

            System.out.println(">> " + items);
        }

        System.out.println("Done");

        //------------

        for (int i = 0; i < 47; i++) {
            q.add(i);
        }

        while (wrapper.isEmpty() == false) {
            Collection<Integer> items = null;

            try {
                items = wrapper.poll(1, TimeUnit.SECONDS);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(">> " + items);
        }

        System.out.println("Done");

        //------------

        for (int i = 0; i < 47; i++) {
            q.add(i);
        }

        while (wrapper.isEmpty() == false) {
            Collection<Integer> items = null;

            try {
                items = wrapper.take();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(">> " + items);
        }

        System.out.println("Done");
    }
}
