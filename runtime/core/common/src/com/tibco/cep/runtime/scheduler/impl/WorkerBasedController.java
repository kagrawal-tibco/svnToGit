package com.tibco.cep.runtime.scheduler.impl;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.PayloadValidationHelper;
import com.tibco.cep.runtime.channel.SerializationContext;
import com.tibco.cep.runtime.channel.impl.DefaultSerializationContext;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.impl.SimpleEventImpl;
import com.tibco.cep.runtime.scheduler.TaskController;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.basic.AsyncWorkerServiceWatcher;
import com.tibco.cep.runtime.service.om.ObjectBasedStore;
import com.tibco.cep.runtime.session.BEManagedThread;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionConfig;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.util.FQName;
import com.tibco.cep.runtime.util.ProcessInfo;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Feb 7, 2007
 * Time: 3:57:21 PM
 * To change this template use File | Settings | File Templates.
 */

@Deprecated
public class WorkerBasedController extends DefaultTaskController implements TaskController {
    /**
     * {@value}
     */
    public static final String PROPERTY_NUM_THREADS =
            "com.tibco.cep.runtime.scheduler.default.numThreads";

    /**
     * {@value}
     */
    public static final String PROPERTY_QUEUE_SIZE = "com.tibco.cep.runtime.scheduler.queueSize";

    public static final String DEFAULT_POOL_NAME = "$default.be.mt$";

    //protected RuleSession ruleSession;
    protected Map destinationToDispatcher;
    protected Map clientDispatacher;
    Map sessionToDestCtx;
    Dispatcher[] dispatchers;
    Logger logger;
    int activeWorkerTask;
    protected Dispatcher defaultDispatcher;
    boolean started=false;
    int threadCount = 0;
    protected FQName fqn;

    public WorkerBasedController(RuleSession session) {
        super(session);
        logger = session.getRuleServiceProvider().getLogger(WorkerBasedController.class);
        logger.log(Level.INFO,"Configuring WorkerBasedQueueController for RuleSession - " + session.getName());
        configure();
    }

    protected FQName getFQName() {
        if (fqn == null) {
            fqn = new FQName(/*todo getClusterName */ ProcessInfo.getProcessIdentifier(),
                    /*todo getAgentDetails*/ ruleSession.getName(), 
                    getClass().getSimpleName());
        }

        return fqn;
    }

    protected String getName() {
        return ruleSession.getName();
    }

    public double getJobRate() {
        double jobRate=super.getJobRate();
        for (int i=0;i < dispatchers.length; i++) {
            for (int j=0; j < dispatchers[i].threads.size(); j++) {
                BEManagedThread t=dispatchers[i].threads.get(j);
                jobRate += t.getJobRate();
            }
        }
        for (int j=0; j < defaultDispatcher.threads.size(); j++) {
            BEManagedThread t=defaultDispatcher.threads.get(j);
            jobRate += t.getJobRate();
        }
        return jobRate;
    }

    public long getNumJobsProcessed() {
        long numJobsProcessed=super.getNumJobsProcessed();
        for (int i=0;i < dispatchers.length; i++) {
            for (int j=0; j < dispatchers[i].threads.size(); j++) {
                BEManagedThread t=dispatchers[i].threads.get(j);
                numJobsProcessed += t.getNumJobs();
            }
        }
        for (int j=0; j < defaultDispatcher.threads.size(); j++) {
            BEManagedThread t=defaultDispatcher.threads.get(j);
            numJobsProcessed += t.getNumJobs();
        }
        return numJobsProcessed;
    }

    protected void configure() {
        RuleSessionConfig.InputDestinationConfig[] dests = ruleSession.getConfig().getInputDestinations();
        destinationToDispatcher = new HashMap();
        sessionToDestCtx = new HashMap();

        //------------------

        Properties properties = ruleSession.getRuleServiceProvider().getProperties();

        String s = properties.getProperty(PROPERTY_NUM_THREADS, "10").trim();
        int defworkers = Integer.parseInt(s);

        s = properties.getProperty(PROPERTY_QUEUE_SIZE, "1024");
        int queueSize = Integer.parseInt(s);

        defaultDispatcher = new Dispatcher(DEFAULT_POOL_NAME, defworkers, queueSize);

        logger.log(Level.INFO,"Created default thread pool [" + defaultDispatcher.getDestinationName() + "] with " +
                defworkers + " threads and queue size of " + queueSize);

        //------------------

        threadCount += defworkers;

        for(int i = 0; i < dests.length; i++) {
            int numWorker = dests[i].getNumWorker();
            queueSize = dests[i].getQueueSize();
            RuleSessionConfig.ThreadingModel threadingModel = dests[i].getThreadingModel();
            if (numWorker == 0 && RuleSessionConfig.ThreadingModel.CALLER != threadingModel) {
                destinationToDispatcher.put(dests[i].getURI(),defaultDispatcher);
            }
            else if (numWorker > 0) {
                Dispatcher dis = new Dispatcher(dests[i].getURI(), numWorker, queueSize);
                destinationToDispatcher.put(dests[i].getURI(),dis);
                threadCount += numWorker;

            }
            else { // No dispatcher, use the client's the thread
                //destinationToDispatcher.put(dests[i].getURI(),null);
            }
        }
        //int bebwworkers = Integer.parseInt(ruleSession.getRuleServiceProvider().getProperties().getProperty("com.tibco.be.bw.callback.numThreads", "4"));
        //queueSize = Integer.parseInt(ruleSession.getRuleServiceProvider().getProperties().getProperty("com.tibco.be.bw.callback.queueSize", "0"));
        //if (bebwworkers > 0)
        //    destinationToDispatcher.put("$bebw.callback$", new Dispatcher("$bebw.callback$", bebwworkers, queueSize)); //put the be.bw bridge too.

        //threadCount += bebwworkers +  defworkers;

        // add default dispatcher to the map for bebw callback rulefunction
        destinationToDispatcher.put(defaultDispatcher.getName(), defaultDispatcher);

        dispatchers = (Dispatcher[]) destinationToDispatcher.values().toArray(new Dispatcher[0]);
        activeWorkerTask = 0;
    }

    synchronized public void start() {
        if (!started) {
            for(int i = 0; i < dispatchers.length; i++) {
                Dispatcher dp = dispatchers[i];
                if ((dp != null) && (dp != defaultDispatcher)) {
                    dp.start();
                }
            }
            defaultDispatcher.start();
            started=true;
        }
    }

    public int threadCount() {
        return threadCount;
    }

    synchronized public void shutdown() {
        for(int i = 0; i < dispatchers.length; i++) {
            Dispatcher dp = dispatchers[i];
            if ((dp != null) && (dp != defaultDispatcher)) {
                dp.shutdown();
            }

        }
        defaultDispatcher.shutdown();
        started=false;
    }

    synchronized void incrActiveWorkerTask() {
        activeWorkerTask++;
    }

    protected synchronized void decrActiveWorkerTask() {
        activeWorkerTask--;
    }

    synchronized public boolean isRunning() {
        boolean isRunning = false;
        for(int i = 0; i < dispatchers.length; i++) {
            if (dispatchers[i].isRunning()) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    public void resume() {
        for(int i = 0; i < dispatchers.length; i++) {
            Dispatcher dp = dispatchers[i];
            if ((dp != null) && (dp != defaultDispatcher)) {
                dp.resume();
            }

        }
        defaultDispatcher.resume();
    }

    public void suspend() {
        for(int i = 0; i < dispatchers.length; i++) {
            Dispatcher dp = dispatchers[i];
            if ((dp != null) && (dp != defaultDispatcher)) {
                dp.suspend();
            }

        }
        defaultDispatcher.suspend();
    }

    public void processEvent(Channel.Destination dest, SimpleEvent event, EventContext ctx) throws Exception {
        incrActiveWorkerTask();
        Dispatcher dispatcher = (Dispatcher) destinationToDispatcher.get(dest.getURI());

        if (null == dispatcher) {
            super.processEvent(dest, event, ctx);
            return;
        }

        SerializationContext sci = (SerializationContext) sessionToDestCtx.get(dest);
        if (sci == null){
            sci = new DefaultSerializationContext(ruleSession, dest);
            if (sessionToDestCtx.size() < 128) {
                synchronized(sessionToDestCtx){
                    sessionToDestCtx.put(dest, sci);
                }
            }
        }
        dispatcher.put(new WorkerTask(dest, event, ctx, sci));
    }

    public void processTask(String dispatcherName, Runnable task) throws Exception {
        Dispatcher dispatcher = (Dispatcher) destinationToDispatcher.get(dispatcherName);
        if (dispatcher != null) {
            //System.err.println("Scheduling Task = " + task);
            dispatcher.put(task);
            return;
        }
        super.processTask(dispatcherName, task);

    }

    protected void dispatch(Dispatcher dispatcher, Channel.Destination dest, SimpleEvent event, EventContext ctx, SerializationContext sci ) throws Exception{
        if(dispatcher == null)
            defaultDispatcher.put(new WorkerTask(dest,event,ctx,sci));
            // executeTask(event, ctx, dest, sci);
        else
            dispatcher.put(new WorkerTask(dest, event, ctx, sci));
    }

    protected void executeTask(SimpleEvent event, EventContext context, Channel.Destination dest, SerializationContext sci) {
        try {
            if (event == null) { //try deserializing the event
                if (dest.getEventSerializer() != null) {
                    event = dest.getEventSerializer().deserialize(context.getMessage(), sci);
                    if (event != null) {
                        event.setContext(context);
                        ((SimpleEventImpl)event).setDestinationURI(dest.getURI());
                        if (PayloadValidationHelper.ENABLED) {
                            PayloadValidationHelper.validate(event, sci);
                        }
                    }
                }
            }

            if (event != null) {
                RuleFunction preprocessor = sci.getDeployedDestinationConfig().getPreprocessor();
                if (preprocessor != null) {
                    ((RuleSessionImpl) ruleSession).preprocessPassthru(preprocessor, event);
                } else {
                    ruleSession.assertObject(event, true);
                }
            }
        }
        catch(Exception ex) {
            logger.log(Level.ERROR, ex, ex.getMessage());
        }
        finally {
            decrActiveWorkerTask();
        }
    }


    class WorkerTask implements Runnable
    {
        SimpleEvent         m_event;
        EventContext        m_ctx;
        Channel.Destination m_dest;
        SerializationContext m_sci;

        WorkerTask(Channel.Destination dest, SimpleEvent event, EventContext ctx, SerializationContext sci) {
            m_dest  = dest;
            m_event = event;
            m_ctx = ctx;
            m_sci = sci;
        }

        public void run() {
            executeTask(m_event, m_ctx, m_dest, m_sci);
        }
    }

    public interface DispatcherMBean {
        public String getDestinationName();
        public int getThreadCount();
        public void setThreadCount(int threadCount);
        int getQueueSize();
        int getQueueCapacity();
        boolean isSuspended();
        boolean isStarted();

        void suspend();

        void resume();
    }

    public class Dispatcher  implements DispatcherMBean, AsyncWorkerServiceWatcher.AsyncWorkerService{
        private  ArrayList <BEManagedThread> threads;
        private BlockingQueue inputQueue;
        boolean started=false;
        ThreadLocal m_postActions = new ThreadLocal();
        String destinationName;
        final Object lock = new Object();
        boolean suspended=false;
        int queueSize;
        int numWorkers=0;
        protected FQName fqn;

        public Dispatcher(String destinationName, int numWorker, int queueSize) {
            //queueSize =  Integer.parseInt(ruleSession.getRuleServiceProvider().getProperties().getProperty("com.tibco.cep.runtime.scheduler.queuesize", "-1"));
            this.queueSize = queueSize;
            this.numWorkers=numWorker;
            if (queueSize <= 0 ) {
                this.inputQueue = new LinkedBlockingQueue(Integer.MAX_VALUE);
            } else {
                this.inputQueue=new LinkedBlockingQueue(queueSize);
            }
            this.destinationName=destinationName;

            ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
            AsyncWorkerServiceWatcher workManagerWatcher = registry.getWorkManagerWatcher();
            if (workManagerWatcher != null) {
                workManagerWatcher.register(this);
            }
        }

        private void initThreads() {
            if (threads == null) {
                threads = new ArrayList();
                for (int i = 0; i < numWorkers; i++) {
                    BEManagedThread thread= new BEManagedThread(destinationName + ".Worker." + i, ruleSession.getRuleServiceProvider(), inputQueue);
                    thread.setChannelThreadType();
                    threads.add(thread);
                    //threads[i].setName(name + ".Worker." + i);
                }
            }
        }

        private void registerMBean() {
            try {
                String objectName="com.tibco.be:service=Agent,type=destinations,name=" +  WorkerBasedController.this.getName() + "$" + destinationName;
                MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
                ObjectName name = new ObjectName(objectName);
                mbs.registerMBean(this, name);
            } catch (Exception ex) {
                //ex.printStackTrace();
            }
        }

        //-----------

        public FQName getName() {
            return fqn;
        }

        public int getNumMaxThreads() {
            int maxAlive = 0;

            synchronized (lock) {
                if (threads != null) {
                    for (BEManagedThread thread : threads) {
                        if (thread.getState() != Thread.State.TERMINATED) {
                            maxAlive++;
                        }
                    }
                }
            }

            return maxAlive;
        }

        public int getNumActiveThreads() {
            int active = 0;

            synchronized (lock) {
                if (threads != null) {
                    for (BEManagedThread thread : threads) {
                        if (thread.getState() == Thread.State.RUNNABLE) {
                            active++;
                        }
                    }
                }
            }

            return active;
        }

        public int getJobQueueCapacity() {
            return inputQueue.remainingCapacity() + inputQueue.size();
        }

        public int getJobQueueSize() {
            return inputQueue.size();
        }

        public boolean isActive() {
            return isStarted();
        }

        //-----------

        public int getQueueSize() {
            return inputQueue.size();
        }

        public int getQueueCapacity() {
            return inputQueue.remainingCapacity();
        }

        public String getDestinationName() {
            return destinationName;
        }

        public int getThreadCount() {
            if (threads != null)
                return threads.size();
            else
                return 0;
        }

        public boolean isStarted() {
            return started;
        }

        public boolean isSuspended() {
            return suspended;
        }

        public  void setThreadCount(int threadCount) {
            synchronized(lock) {
                initThreads();
                if (threadCount > threads.size()) {
                    for (int i=threads.size(); i < (threadCount);i++) {
                        BEManagedThread thread= new BEManagedThread(destinationName + ".Worker." + i, ruleSession.getRuleServiceProvider(), inputQueue);
                        thread.setChannelThreadType();
                        threads.add(thread);
                        if (started) {
                            thread.start();
                        }
                    }
                }
            }
        }

        public void start() {
            synchronized(lock) {
                if (!started) {
                    FQName parentFQN =  WorkerBasedController.this.getFQName();
                    this.fqn = new FQName(parentFQN, destinationName);

                    initThreads();
                    for (int i = 0; i < threads.size(); i++) {
                        BEManagedThread thread= (BEManagedThread) threads.get(i);
                        //System.err.println("Starting Thread " + thread);
                        thread.start();
                    }
                    started=true;
                    if (ruleSession.getObjectManager() instanceof ObjectBasedStore)
                        registerMBean();
                }
            }
        }

        public boolean isRunning() {
            if (threads != null) {
                for (int i = 0; i < threads.size(); i++) {
                    BEManagedThread thread= (BEManagedThread) threads.get(i);
                    if (thread.isRunning()) {
                        return true;
                    }
                }
            }
            return false;
        }

        public void shutdown() {
            synchronized(lock) {
                if (started) {
                    if (threads != null) {
                        for (int i = 0; i < threads.size(); i++) {
                            BEManagedThread thread= (BEManagedThread) threads.get(i);
                            logger.log(Level.DEBUG,"Shutting down controller thread:"+thread.toString());
                            thread.shutdown();
                        }
                    }
                    threads=null;
                    started=false;
                }
            }

            ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
            AsyncWorkerServiceWatcher workManagerWatcher = registry.getWorkManagerWatcher();
            if (workManagerWatcher != null) {
                workManagerWatcher.unregister(this);
            }
        }

        public void put(Runnable task) throws InterruptedException {
            inputQueue.put(task);
        }

        public void suspend() {
            synchronized(lock) {
                if (threads != null) {
                    for (int i = 0; i < threads.size(); i++) {
                        BEManagedThread thread= (BEManagedThread) threads.get(i);
                        logger.log(Level.DEBUG,"Suspending controller thread:"+thread.toString());
                        thread.suspendThread();
                    }
                }
                suspended=true;
            }
        }

        public void resume() {
            synchronized(lock) {
                if (threads != null) {
                    for (int i = 0; i < threads.size(); i++) {
                        BEManagedThread thread= (BEManagedThread) threads.get(i);
                        logger.log(Level.DEBUG,"Resuming controller thread:"+thread.toString());
                        thread.resumeThread();
                    }
                }
                suspended=false;
            }
        }
    }
}
