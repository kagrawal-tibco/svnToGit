package com.tibco.cep.runtime.management.impl.adapter.parent;

import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;

import com.tibco.cep.runtime.management.MetricTable;
import com.tibco.cep.runtime.management.impl.adapter.RemoteCoherenceMetricDataListener;
import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Mar 18, 2009 Time: 1:29:39 PM
*/
public class RemoteCoherenceMetricDataListenerImpl implements RemoteCoherenceMetricDataListener {
    protected String name;

    protected MetricTable.DataListener localListener;

    protected ExecutorService workerThreads;

    public RemoteCoherenceMetricDataListenerImpl(String name, MetricTable.DataListener localListener,
                                           ExecutorService workerThreads) {
        this.name = name;
        this.localListener = localListener;
        this.workerThreads = workerThreads;
    }

    public String getName() throws RemoteException {
        return name;
    }

    public MetricTable.DataListener getLocalListener() {
        return localListener;
    }

    public void onNew(FQName key, Data data) throws RemoteException {
        workerThreads.submit(new AsyncListenerNotifier(localListener, key, data));
    }

    //-----------

    protected static class AsyncListenerNotifier implements Runnable {
        protected MetricTable.DataListener localListener;

        protected FQName key;

        protected Data data;

        public AsyncListenerNotifier(MetricTable.DataListener localListener, FQName key,
                                     Data data) {
            this.localListener = localListener;
            this.key = key;
            this.data = data;
        }

        public void run() {
            localListener.onNew(key, data);
        }
    }
}
