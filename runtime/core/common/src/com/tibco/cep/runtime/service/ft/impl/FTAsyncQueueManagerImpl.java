package com.tibco.cep.runtime.service.ft.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.ft.FTAsyncMsgCallback;
import com.tibco.cep.runtime.service.ft.FTAsyncQueueManager;
import com.tibco.cep.runtime.service.ft.FTNodeAsyncMsgId;
import com.tibco.cep.runtime.service.ft.FTNodeController;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.impl.RuleServiceProviderImpl;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 25, 2007
 * Time: 10:01:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class FTAsyncQueueManagerImpl extends Thread implements FTAsyncQueueManager,Runnable {
    private BlockingQueue m_controllerQueue = new LinkedBlockingQueue();
    private Map m_controllers;

    private RuleServiceProvider m_rsp;
    private Logger m_logger;
    private boolean bStop;
    private final Object stopGuard = new Object();
    private boolean exceptionShutdown = false;
    private FTAsyncMsgCallback asyncCallback;


    public FTAsyncQueueManagerImpl(RuleServiceProvider rsp) {
        super("FTAsyncQueueManager:"+ rsp.getName());
        m_rsp = rsp;
        m_logger = m_rsp.getLogger(FTAsyncQueueManagerImpl.class);
        m_controllers = new HashMap();

       // m_thread.start();
    }

    /**
     * Adds a named node controller for recieving FT events
     * @param name
     * @param controller
     */
    public void addNodeController(String name, FTNodeController controller) {
        m_controllers.put(name,controller);
    }

    /**
     * notify all registered node controllers about the controller message
     * @param msg
     * @throws Exception
     */
    public void notifyControllers(FTNodeAsyncControllerMsgs msg) throws Exception {
       for(Iterator it = m_controllers.values().iterator();it.hasNext();) {
           FTNodeController controller = (FTNodeController) it.next();
           dispatchMessage(controller, msg);
       }
       // if there is a call back then return the call
       if(this.asyncCallback != null ) {
                this.asyncCallback.ftCallback(msg);
       }
    }

    /**
     * removes a node controller
     * @param name
     * @return FTNodeController
     */
    public FTNodeController removeNodeController(String name) {
        return (FTNodeController) m_controllers.remove(name);
    }

    /**
     * returns the list of node controllers
     * @return  FTNodeController[]
     */
    public FTNodeController[] getNodeControllers() {
        return (FTNodeController[]) m_controllers.values().toArray(new FTNodeController[0]);
    }

    /**
     * sets the asynchronous call back
     * @param callback
     */
    public void setAsyncCallback(FTAsyncMsgCallback callback) {
        this.asyncCallback = callback;
    }

    /**
     * stops the message loop
     */
    public void stopThread() {
        if(exceptionShutdown)
            return;
        synchronized(stopGuard) {
            this.bStop = true;
            try {
                stopGuard.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

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
    public void run() {
        boolean bExit = false;
        FTNodeAsyncControllerMsgs msg = null;
        while (!bStop) {
            try {
                msg =(FTNodeAsyncControllerMsgs) m_controllerQueue.poll(2000, TimeUnit.MILLISECONDS);
                if (msg != null) {
                    if(m_logger.isEnabledFor(Level.DEBUG))
                        m_logger.log(Level.DEBUG, "[FTAsyncQueueManagerImpl]"+msg.toString());
                        notifyControllers(msg);
                }
            }
            catch (InterruptedException ie) {
                if (bStop) break;
                else {
                    m_logger.log(Level.ERROR, "FTAsyncQueueManager interrupted",ie);
                }
            }
            catch (Exception e) {
                exceptionShutdown = true;
                ((RuleServiceProviderImpl)m_rsp).hawkShutdown();
                break;
            }
        }
        synchronized(stopGuard) {
            //((RuleServiceProviderImpl)m_rsp).shutdown(false);
            stopGuard.notifyAll();
        }
    }

    private void dispatchMessage(FTNodeController controller, FTNodeAsyncControllerMsgs msg) throws Exception {
        try {
            switch(msg.getId()) {
                case FTNodeAsyncMsgId.MSGID_ASYNC_INIT_ALL:
                    controller.initAll();
                    break;
                case FTNodeAsyncMsgId.MSGID_ASYNC_SUSPEND_RTC:
                    controller.suspendRTC();
                    break;
                case FTNodeAsyncMsgId.MSGID_ASYNC_ACTIVATE_RTC:
                    controller.activateRTC();
                    break;
                case FTNodeAsyncMsgId.MSGID_ASYNC_INIT_CHANNELS:
                    controller.initChannels();
                    break;
                case FTNodeAsyncMsgId.MSGID_ASYNC_INIT_RULESESSION:
                    controller.initRuleSessions();
                    break;
                case FTNodeAsyncMsgId.MSGID_ASYNC_NODE_STARTED:
                    controller.nodeStarted();
                    break;
                case FTNodeAsyncMsgId.MSGID_ASYNC_SET_INACTIVE:
                    controller.setInactive();
                    break;
                case FTNodeAsyncMsgId.MSGID_ASYNC_SHUTDOWN:
                    controller.shutdown();
                    break;
                case FTNodeAsyncMsgId.MSGID_ASYNC_START_CHANNELS:
                    controller.startChannels();
                    break;
                case FTNodeAsyncMsgId.MSGID_ASYNC_STOP_CHANNELS:
                    controller.stopChannels();
                    break;
                case FTNodeAsyncMsgId.MSGID_ASYNC_STOP_NODE:
                    controller.stopNode();
                    break;

                case FTNodeAsyncMsgId.MSGID_ASYNC_WAIT_FOR_RTC:
                    controller.waitForRuleCycles();
                    break;
                case FTNodeAsyncMsgId.MSGID_WAIT_FOR_ACTIVATION:
                    controller.waitForActivation();
                    break;
                case FTNodeAsyncMsgId.MSGID_ASYNC_WAIT_BEFORE_START:
                    controller.waitBeforeStart();
                    break;
                default:
                    break;
            }
//            if(msg.getCallback() != null ) {
//                FTAsyncMsgCallback cb = msg.getCallback();
//                cb.ftCallback(msg);
//            }
        } catch (Exception e) {
             m_logger.log(Level.ERROR,"Error dispatching msg:"+msg,e);
             throw e;
        }
    }

    public synchronized void asyncMessage(FTNodeAsyncControllerMsgs msg) {
        try {
            m_controllerQueue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Object syncMessage(FTNodeAsyncControllerMsgs msg) throws Exception {
        switch(msg.getId()) {
            case FTNodeAsyncMsgId.MSGID_SYNC_GETDIGEST:
                    return new byte[0];
//            case FTNodeAsyncMsgId.MSGID_ASYNC_SUSPEND_RTC:
//                    if(m_rsp instanceof RuleServiceProviderImpl)
//                        ((RuleServiceProviderImpl)m_rsp).suspendRTC();
//                    return null;
            default:
                    return null;
        }
    }
}
