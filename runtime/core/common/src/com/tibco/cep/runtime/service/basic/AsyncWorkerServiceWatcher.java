package com.tibco.cep.runtime.service.basic;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.Notification;

import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Oct 20, 2008 Time: 11:23:48 AM
*/
public class AsyncWorkerServiceWatcher implements JVMHeapWatcher.JVMHeapWarningListener {
    /*
    * Only the keys are relevant. Values are dummy. Used as a Set.
     */
    protected final ConcurrentHashMap<AsyncWorkerService, Object> registeredWorkerServices;

    protected JVMHeapWatcher heapWatcher;

    public AsyncWorkerServiceWatcher(JVMHeapWatcher heapWatcher) {
        this.registeredWorkerServices = new ConcurrentHashMap<AsyncWorkerService, Object>();
        this.heapWatcher = heapWatcher;
    }

    public void init() {
        heapWatcher.addListener(this);
    }


    public void register(AsyncWorkerService service) {
        registeredWorkerServices.put(service, service);

    }

    public void unregister(AsyncWorkerService service) {
        registeredWorkerServices.remove(service);
    }

    public void stop() {
        registeredWorkerServices.clear();

        heapWatcher.removeListener(this);
        heapWatcher = null;
    }

    //--------------

    public Collection<AsyncWorkerService> getRegisteredWorkerServices() {
        return registeredWorkerServices.keySet();
    }

    /**
     * @return <code>null</code> if there were no stats to capture.
     */
    public StringBuilder collectStats() {
        return captureStats();
    }

    public void onWarning(Notification notification, String poolName, long maxBytes,
                          long usedBytes) {
        StringBuilder builder = captureStats();
        if (builder != null) {
            System.err.println(builder);
        }
    }

    /**
     * @return <code>null</code> if there was no data to capture.
     */
    protected StringBuilder captureStats() {
        StringBuilder builder = new StringBuilder();

        if (registeredWorkerServices.isEmpty()) {
            return null;
        }

        //-----------

        int counter = 0;

        builder.append(
                "\n========================== AsyncWorkerService stats ==========================");

        for (Iterator<AsyncWorkerService> iterator = registeredWorkerServices.keySet().iterator();
             iterator.hasNext();) {
            AsyncWorkerService workerService = iterator.next();

            if (workerService.isActive() == false) {
                continue;
            }

            iterator.remove();

            FQName name = workerService.getName();
            int maxThreads = workerService.getNumMaxThreads();
            int activeThreads = workerService.getNumActiveThreads();
            int qCapacity = workerService.getJobQueueCapacity();
            int qSize = workerService.getJobQueueSize();


            builder.append("\n  Name                           : ").append(name);
            builder.append("\n  Total num of threads           : ").append(maxThreads);
            builder.append("\n  Current num of active threads  : ").append(activeThreads);
            builder.append("\n  Total Job queue capacity       : ").append(qCapacity);
            builder.append("\n  Current Job queue size         : ").append(qSize);
            builder.append("\n");

            counter++;
        }

        builder.append("~--------~--------~--------~--------~--------~--------~--------~--------~");

        if (counter > 0) {
            return builder;
        }

        return null;
    }

    //-----------

    public static interface AsyncWorkerService {
        /**
         * If not active, then none of the other methods will be invoked.
         *
         * @return
         */
        boolean isActive();

        FQName getName();

        int getNumMaxThreads();

        int getNumActiveThreads();

        int getJobQueueCapacity();

        int getJobQueueSize();
    }
}
