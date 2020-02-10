package com.tibco.cep.runtime.scheduler.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
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
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Feb 15, 2007
 * Time: 8:38:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class SymmetricFairQueueController implements TaskController {

    RuleSession session;
    HashMap queues;
    Logger logger;
    ArrayList workers = new ArrayList();
    int numworkers;
    TaskQueue defaultQueue;

    public SymmetricFairQueueController(RuleSession session) {

        this.session = session;
        this.logger = session.getRuleServiceProvider().getLogger(SymmetricFairQueueController.class);
        this.queues = new HashMap();

        RuleSessionConfig.InputDestinationConfig[] config = session.getConfig().getInputDestinations();
        Properties beprops =  session.getRuleServiceProvider().getProperties();
        String name = session.getName();
        numworkers = Integer.parseInt(beprops.getProperty("com.tibco.cep.runtime.scheduler." + name + ".numworkers", "1"));
        logger.log(Level.INFO, "Configuring SymmetricFairQueueController for RuleSession - " + session.getName());
        for (int i=0; i < config.length; i++) {
            String destURI = config[i].getURI();
            RuleFunction rf = config[i].getPreprocessor();
            int qSize =Integer.parseInt(beprops.getProperty("com.tibco.cep.runtime.scheduler." + name+"."+destURI + ".queuesize", "5"));
            int qWeight = Integer.parseInt(beprops.getProperty("com.tibco.cep.runtime.scheduler." + name+"."+ destURI + ".queueweight", "1"));
//            int qSize = config[i].getQueueSize();
//            int qWeight = config[i].getWeight();

            if ((qWeight <= 0) || (qSize <= 0)) {
                throw new  RuntimeException("Input Destination[" + destURI + "] - configured with QWeight or QSize less than equal zero.");
            }
            TaskQueue queue = new TaskQueue (destURI, qWeight, qSize, rf);

            queues.put(destURI, queue);
        }

        ArrayList list = new ArrayList(queues.values());
        for (int i=0; i < numworkers; i++) {
            FQWorker worker = new FQWorker (name + "." + i, list);
            workers.add(worker);
        }
        defaultQueue = (TaskQueue) list.get(0);

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
        return numworkers;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void processEvent(Channel.Destination dest, SimpleEvent event, EventContext ctx) throws Exception {
        TaskQueue queue = (TaskQueue) queues.get(dest.getURI());
        queue.enqueue(new TaskItem(dest,event, ctx));
    }

    public void processTask(String dispatcherName, Runnable task) throws Exception {
        defaultQueue.enqueue(task);
    }

    public void shutdown() {
        for (int i=0; i < numworkers; i++) {
            FQWorker worker = (FQWorker) workers.get(i);
            worker.interrupt();
        }
    }

    public void start() throws Exception {

        for (int i=0; i < numworkers; i++) {
            FQWorker worker = (FQWorker) workers.get(i);
            worker.start();
        }
    }

    public boolean isRunning() {
        throw new RuntimeException("NOT IMPLEMENTED");
    }

    class TaskQueue {
        BlockingQueue queue;
        int queueWeight;
        int queueSize;
        String destURI;
        RuleFunction rf;

        public TaskQueue (String destURI, int queueWeight, int queueSize, RuleFunction rf) {
            this.destURI = destURI;
            this.queueWeight = queueWeight;
            this.queueSize = queueSize;
            this.rf = rf;
            if (queueSize > 0) queue = new LinkedBlockingQueue(queueSize-1);
            else queue = new LinkedBlockingQueue();

        }

        void enqueue(Object obj) throws InterruptedException {
            queue.put(obj);
        }

        void processFairly(ArrayList list) throws Exception {


            for (int i=0; i < queueWeight; i++) {
                TaskItem item = (TaskItem) queue.poll(0, TimeUnit.MILLISECONDS);
                if (item == null) {
                    break; //some intelligent logic can be applied
                }
                list.add(item);
                //item.preProcess(list);
            }
        }





    }

    class FQWorker extends Thread {

        List taskQueues;
        PreprocessContext context;
        FQWorker (String name, List taskQueues) {
            super(name);
            this.taskQueues = taskQueues;
        }

        public void run() {

            int numQueues = taskQueues.size();
            boolean bInterrupted = false;
            while (!bInterrupted) {
                try {
                    //if(context == null) context = PreprocessContext.newContext((RuleSessionImpl) session);
                    //PreprocessContext.setContext(context);

                    ArrayList list = new ArrayList();
                    for (int i=0; i < numQueues; i++) {
                        ((RuleServiceProviderImpl) session.getRuleServiceProvider()).ensureRSP();
                        TaskQueue queue = (TaskQueue) taskQueues.get(i);
                        queue.processFairly(list);
                    }
                    for (int i=0; i<list.size(); i++) {
                        Object o = list.get(i);
                        if (o instanceof TaskItem) {
                            TaskItem ti = (TaskItem) o;
                            ti.execute();
                        }
                        else if (o instanceof Runnable) {
                            ((Runnable)o).run();
                        }
                        else {
                            logger.log(Level.ERROR, "Invalid task object - %s", o);
                        }
                        //session.assertObject(list.get(i), true);
                    }

                    //PreprocessContext.cleanContext();
                    //context = null;
                }
                catch (InterruptedException ie) {
                    bInterrupted = true;
                }
                catch (Exception e) {
                    logger.log(Level.ERROR, e, e.getMessage());
                }
            }
        }

        void postProcess() throws Exception {
            PreprocessContext.setContext(context);
            ((RuleSessionImpl) session).preprocessPostSchedule(context);  //call post schedule
            PreprocessContext.cleanContext();  //clean the context

        }


    }

    class TaskItem {

        Channel.Destination dest;
        SimpleEvent event;
        EventContext ctx;

        TaskItem(Channel.Destination dest, SimpleEvent event, EventContext ctx) {
            this.dest = dest;
            this.event = event;
            this.ctx = ctx;
        }

        public void preProcess(ArrayList list) throws Exception{
            SerializationContext sci = new DefaultSerializationContext(SymmetricFairQueueController.this.session, dest);
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
                list.add(event);
            }
        }

        public void execute() {
            SerializationContext sci = new DefaultSerializationContext(session, dest);

            try {
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
                        ((RuleSessionImpl) session).preprocessPassthru(preprocessor, event);
                    } else {
                        session.assertObject(event, true);
                    }
                }
            }
            catch(Exception ex) {
                logger.log(Level.ERROR, ex, ex.getMessage());
            }
        }


    }
}
