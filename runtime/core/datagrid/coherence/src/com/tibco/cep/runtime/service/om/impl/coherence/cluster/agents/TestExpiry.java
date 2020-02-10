/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 13/8/2010
 */

package com.tibco.cep.runtime.service.om.impl.coherence.cluster.agents;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.net.cache.CacheEvent;
import com.tangosol.util.MapEvent;
import com.tangosol.util.MapListener;
import com.tangosol.util.MultiplexingMapListener;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 21, 2008
 * Time: 1:37:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestExpiry {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    
    
    public static void main(String args[]) {
        try {
            NamedCache test = CacheFactory.getCache("dist-hello");
            test.addMapListener(new MapListen());
            for (int i = 0; i < 20; i++) {
                test.put(new Integer(i), new Integer(i), 1);
                Thread.sleep(1000);
            }
            Thread.sleep(1000);
            java.util.Iterator allKeys = test.keySet().iterator();
            while (allKeys.hasNext()) {
                System.out.println("Key = " + allKeys.next());
            }
            Thread.currentThread().join();
        } catch (Exception ex) {

        }
    }

    static class EventPrinter extends MultiplexingMapListener {
        public void onMapEvent(MapEvent evt) {
            if (evt instanceof CacheEvent && ((CacheEvent) evt).isSynthetic()) {
                System.out.println("Synthetic " + evt);
                out(evt);
            } else {
                System.out.println("Generic " + evt);
            }
        }
    }

    static class MapListen implements MapListener {
        public void entryDeleted(MapEvent event) {
            System.out.println("entry deleted " + event.toString());
        }

        public void entryInserted(MapEvent event) {
            System.out.println("entry inserted " + event.toString());
        }

        public void entryUpdated(MapEvent event) {
            System.out.println("entry updated " + event.toString());
        }
    }
}
