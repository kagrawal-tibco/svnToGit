/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tibco.be.monitor.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.be.monitor.service.spi.DictionaryService;

/**
 *
 * @author ksubrama
 */
public class DictionaryServiceImpl implements DictionaryService {
    private static final Map<String, List<String>> groupMap =
            new HashMap<String, List<String>>();
    
    static {
        // Init bootstrap entries
        initJavaClasses();
        initJavaIO();
        initJavaNet();
        // Init all dictionary entries
        initThreadSync();
        initLockClasses();
        initTPClasses();
        initQClasses();
        initThreadClasses();
        initRMIClasses();
        initJUCClasses();
    }

    private static void initTPClasses() {
        initTPExecutor();
        initFutureTasks();
    }

    private static void initQClasses() {
        String[] qClasses = new String[] {
            "java.util.concurrent.*Queue"
        };
        groupMap.put("queue", Arrays.asList(qClasses));
    }

    private static void initThreadClasses() {
        String[] threadClasses = new String[] {
            "java.lang.Thread",
            "java.util.TimerThread"
        };
        groupMap.put("thread", Arrays.asList(threadClasses));
    }

    private static void initTPExecutor() {
        String[] tpExecClasses = new String[] {
            "java.util.concurrent.ThreadPoolExecutor",
            "java.util.concurrent.ThreadPoolExecutor$Worker",
            "java.util.concurrent.Executors$RunnableAdapter",            
        };
        groupMap.put("threadpool executor", Arrays.asList(tpExecClasses));
    }

    private static void initLockClasses() {
        String[] otherLockClasses = new String[] {
            "java.util.concurrent.locks"
        };
        groupMap.put("lock", Arrays.asList(otherLockClasses));
    }

    private static void initThreadSync() {
        String[] primitives = new String[] {
            "sun.misc.Unsafe.park",
            "java.lang.Object.wait",
            "java.lang.Thread.sleep"
        };
        groupMap.put("primitive", Arrays.asList(primitives));
    }

    private static void initRMIClasses() {
        String[] rmiClasses = new String[] {
            "javax.management.remote.rmi.*"
        };
        groupMap.put("rmi", Arrays.asList(rmiClasses));
    }

    private static void initFutureTasks() {
        String[] futureTasks = new String[] {
            "java.util.concurrent.FutureTask"
        };
        groupMap.put("future task", Arrays.asList(futureTasks));
    }

    private static void initJavaNet() {
        String[] netClasses = new String[] {
            "java.net"
        };
        groupMap.put("network", Arrays.asList(netClasses));
    }

    private static void initJavaClasses() {
        String[] javaClasses = new String[] {
            "java"
        };
        groupMap.put("java", Arrays.asList(javaClasses));
    }

    private static void initJavaIO() {
        String[] ioClasses = new String[] {
            "java.io"
        };
        groupMap.put("java-io", Arrays.asList(ioClasses));
    }

    private static void initJUCClasses() {
        String[] jucClasses = new String[] {
            "java.util.concurrent"
        };
        groupMap.put("juc", Arrays.asList(jucClasses));
    }

    @Override
    public Map<String, List<String>> getGroups() {
        return groupMap;
    }

}
