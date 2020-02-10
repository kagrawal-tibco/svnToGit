package com.tibco.cep.runtime.scheduler.impl;



import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.channel.impl.DefaultSerializationContext;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.scheduler.TaskController;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionConfig;
import com.tibco.cep.runtime.session.impl.PreprocessContext;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;



/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Feb 6, 2007
 * Time: 11:49:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class AsymmetricFairQueueController implements TaskController {

    RuleSession session;
    HashMap workers = new HashMap();
    final Object guard = new Object();
    FQWorker current;
    Logger logger;

    Set idleWorkers = new HashSet();


    public AsymmetricFairQueueController(RuleSession session) {
        this.session = session;
        logger = session.getRuleServiceProvider().getLogger(AsymmetricFairQueueController.class);
        logger.log(Level.INFO,"Configuring AsymmetricFairQueueController for RuleSession - " + session.getName());
        RuleSessionConfig.InputDestinationConfig[] config = session.getConfig().getInputDestinations();
        FQWorker prev = null;
        for (int i=0; i < config.length; i++) {
            String destURI = config[i].getURI();
            RuleFunction rf = config[i].getPreprocessor();
            int numWorker = config[i].getNumWorker();
            int qSize = config[i].getQueueSize();
            int qWeight = config[i].getWeight();

            if ((qWeight <= 0) || (qSize <= 0)) {
                throw new  RuntimeException("Input Destination[" + destURI + "] - configured with QWeight or QSize less than equal zero.");
            }
            FQWorker worker = new FQWorker(destURI, qSize, qWeight);
            if (i==0) {
                current = worker;
            }
            if (prev != null) {
                prev.setNext(worker);
            }
            prev = worker;

            workers.put(destURI, worker);
        }
        if (prev != null)
            prev.setNext(current); //loop back to the first one.

    }

    public double getJobRate() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getNumJobsProcessed() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void suspend() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int threadCount() {
        return 1;
    }

    public void start() throws Exception {
        Iterator fqWorkers = workers.values().iterator();
        while (fqWorkers.hasNext()) {
            FQWorker worker = (FQWorker) fqWorkers.next();
            worker.start();
            logger.log(Level.INFO,"Starting worker for destination [" + worker.getName() + "] with Queue Size = " + worker.queueSize + " and weight = " + worker.batchCount );

        }
    }

    public void shutdown() {
        Iterator fqWorkers = workers.values().iterator();
        while (fqWorkers.hasNext()) {
            FQWorker worker = (FQWorker) fqWorkers.next();
            worker.stopThread();
        }
    }

    public boolean isRunning() {
        throw new RuntimeException("NOT IMPLEMENTED");
    }    

    public void processEvent(Channel.Destination dest, SimpleEvent event, EventContext ctx) throws Exception {

        WorkItem wi = new WorkItem(ctx, dest, event);
        FQWorker worker = (FQWorker) workers.get(dest.getURI());
        worker.enqueue(wi);

    }


    public void processTask(String dispatcherName, Runnable task) throws Exception {
        throw new Exception ("Not yet Implemented");
    }

    public void fairAssert(FQWorker worker) throws Exception {
        synchronized(guard) {
            while ((current != null) &&(worker != current)) {
                try {
                    guard.wait(15);
                    if (idleWorkers.contains(current)) { // the current guard belongs to an idle worker who is waiting on take() call
                        current = null; //reset the token for anybody to grab
                        guard.notifyAll();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            setWorkerActive(worker);
            worker.postProcess();

            if (current != null) {
                current = current.next;
            }
            else {
                current = worker.next;
            }
            guard.notifyAll();
        }
    }

    public void relinquishSpinLock(FQWorker worker) {
        if (current == worker) {
            synchronized(guard) {
                current = worker.next;
                guard.notifyAll();
                setWorkerIdle(worker);
            }
        }
    }

    public synchronized void setWorkerIdle(FQWorker worker) {
        idleWorkers.add(worker);
        if (isIdle()) {
            current = null;
        }
    }

    public synchronized boolean isIdle() {
        if (idleWorkers.size() == workers.size()) return true;
        return false;
    }

    public synchronized void setWorkerActive(FQWorker worker) {
        idleWorkers.remove(worker);
    }



    class FQWorker extends Thread {
        BlockingQueue queue;
        int batchCount;
        boolean shutdown = false;
        int queueSize;
        FQWorker next;
        PreprocessContext context;
        boolean bRelinquished = false;

        FQWorker(String name, int queueSize, int batchCount) {
            super(name);
            if (queueSize > 0) queue = new LinkedBlockingQueue(queueSize-1);
                        else queue = new LinkedBlockingQueue();


            this.queueSize = queueSize;
            this.batchCount = batchCount;

        }


        void enqueue(Object obj) throws InterruptedException {
            queue.put(obj);
        }

        public void run() {



            while (!shutdown) {
                try {
                    if(context == null) context = PreprocessContext.newContext((RuleSessionImpl) session);

                    PreprocessContext.setContext(context);
                    if (preProcess()) {
                        AsymmetricFairQueueController.this.fairAssert(this);
                    }
                    else {
                        AsymmetricFairQueueController.this.relinquishSpinLock(this);
                        bRelinquished = true;
                    }
                    PreprocessContext.cleanContext();
                    context = null;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

        boolean preProcess() throws Exception {
            boolean byPass = true;

            boolean processed = false;

            for (int i=0; i < batchCount; i++)
            {
                WorkItem wi = null;
                if (AsymmetricFairQueueController.this.isIdle()) {
                    wi = (WorkItem) queue.take();
                }
                else {
                    wi = (WorkItem) queue.poll(0, TimeUnit.MILLISECONDS);
                }

                if (wi == null) {
                    break;
                }
                else {
                    processed = true;
                    wi.processWork(context);

                }


            }

            //PreprocessContext.setContext(null);
            //context = null;
            return processed;
        }

        PreprocessContext getPreprocessContext() {
            return context;
        }

        void postProcess() throws Exception {
            PreprocessContext.setContext(context);
            ((RuleSessionImpl) session).preprocessPostSchedule(context);  //call post schedule
            PreprocessContext.cleanContext();  //clean the context

        }

        void setNext(FQWorker next) {
            this.next = next;
        }

        synchronized void stopThread() {
            shutdown = true;
        }


    }

    class WorkItem {

        Channel.Destination dest;
        SimpleEvent event;
        EventContext ctx;


        public WorkItem(EventContext ctx, Channel.Destination dest, SimpleEvent event) {
            this.ctx = ctx;
            this.dest = dest;
            this.event = event;

        }

        public void processWork(PreprocessContext preContext) throws Exception{
            SerializationContext sci = new DefaultSerializationContext(AsymmetricFairQueueController.this.session, dest);
            if (event == null) { //try deserializing the event
                if (dest.getEventSerializer() != null) {
                    event = dest.getEventSerializer().deserialize(ctx.getMessage(), sci);
                    if (event != null) {
                        event.setContext(ctx);
                        ((SimpleEventImpl)event).setDestinationURI(dest.getURI());
                    }
                }
            }

            if (event != null) {
                RuleFunction preprocessor = sci.getDeployedDestinationConfig().getPreprocessor();
                if (preprocessor != null) {
                    ((RuleSessionImpl) session).preprocessPreSchedule(preprocessor, event);
                } else {
                    preContext.add(event);
                }
            }



        }


    }


}
