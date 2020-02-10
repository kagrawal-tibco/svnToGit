/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 11/8/2010
 */

package com.tibco.cep.query.benchmark.query.sender;

import com.tangosol.net.NamedCache;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.cluster.txn.RtcTransaction;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/*
* Author: Ashwin Jayaprakash Date: Aug 26, 2008 Time: 7:27:26 PM
*/
public class MTSender implements Runnable {
    protected static final int EVENT_ID_BURN_NUMBER = 10000;

    protected final AtomicLong eventIdRepo;

    protected final ConcurrentLinkedQueue<Long> deleteQueue;

    protected final NamedCache masterCache;

    protected final NamedCache eventCache;

    protected final String name;

    protected final AtomicBoolean stopFlag;

    public MTSender(AtomicLong eventIdRepo, ConcurrentLinkedQueue<Long> deleteQueue,
                    NamedCache masterCache, NamedCache eventCache, String name,
                    AtomicBoolean stopFlag) {
        this.eventIdRepo = eventIdRepo;
        this.deleteQueue = deleteQueue;
        this.masterCache = masterCache;
        this.eventCache = eventCache;
        this.name = name;
        this.stopFlag = stopFlag;
    }

    protected byte[] serializeTxn(RtcTransaction txn) {
        ByteArrayOutputStream bufStream = new ByteArrayOutputStream(32 * 4);
        DataOutput buf = new DataOutputStream(bufStream);
        Iterator writeIterator = txn.writeToCahceOps(buf);

        while (writeIterator.hasNext()) {
            writeIterator.next();
        }

        return bufStream.toByteArray();
    }

    public void run() {
        long batchEndEventId = eventIdRepo.addAndGet(EVENT_ID_BURN_NUMBER);
        long batchStartEventId = batchEndEventId - EVENT_ID_BURN_NUMBER + 1;

        try {
            int cardSwipeTypeId = 1011;
            Class cardSwipeEventClass = Class.forName("be.gen.ObjectDefinitions.CardSwipeEvent");
            String[] lastNames = {"AA", "BB", "CC", "DD", "EE"};
            int maxCustomers = 100000;

            Constructor constructor =
                    cardSwipeEventClass.getConstructor(new Class[]{long.class, String.class});

            int count = 0;
            Long previousTxnId = null;
            for (long eventId = batchStartEventId; ; eventId++, count++) {
                String extId = "swipe:" + name + ":" + count;
                String firstName = extId;
                String lastName = lastNames[count % lastNames.length];
                String customerId = (count % maxCustomers) + "";
                String cardId = customerId;
                Calendar currTime = GregorianCalendar.getInstance();
                currTime.setTimeInMillis(System.currentTimeMillis());
                String senderId = name;

                Long eventIdObj = new Long(eventId);
                Object event = constructor
                        .newInstance(new Object[]{eventIdObj, eventId + ":" + cardSwipeEventClass});

                SimpleEvent se = (SimpleEvent) event;

                se.setProperty("firstName", firstName);
                se.setProperty("lastName", lastName);
                se.setProperty("customerId", customerId);
                se.setProperty("cardId", cardId);
                se.setProperty("occurrenceTime", currTime);
                se.setProperty("senderId", senderId);

                eventCache.put(eventIdObj, event);

                RtcTransaction txn = new RtcTransaction(null);
                txn.recordNewEvent((Event) event, cardSwipeTypeId, 1);
                byte[] serialize = serializeTxn(txn);

                if (previousTxnId != null) {
                    deleteQueue.add(previousTxnId);
                }

                Long txnId = txn.getTxnId();
                masterCache.put(txnId, serialize);
                previousTxnId = txnId;

                if ((count & 127) == 0) {
                    System.err.println(name + " inserted so far: " + count
                            + "[" + batchStartEventId + " - " + batchEndEventId + "]");

                    if (stopFlag.get()) {
                        System.err.println(name + " shutting down.");

                        break;
                    }
                }

                //-----------

                if (eventId == batchEndEventId) {
                    batchEndEventId = eventIdRepo.addAndGet(EVENT_ID_BURN_NUMBER);
                    batchStartEventId = batchEndEventId - EVENT_ID_BURN_NUMBER + 1;

                    eventId = batchStartEventId;
                }
            }
        }
        catch (Exception e) {
            System.err.println("Error in:" + name);
            e.printStackTrace(System.err);
        }
    }
}
