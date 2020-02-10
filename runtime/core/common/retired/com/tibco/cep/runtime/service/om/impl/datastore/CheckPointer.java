/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Aug 26, 2004
 * Time: 5:23:30 PM
 * To change this template use File | Settings | File Templates.
 */
package com.tibco.cep.runtime.service.om.impl.datastore;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.om.exception.OMException;
import com.tibco.cep.util.ResourceManager;

import java.util.Properties;

public class CheckPointer implements Runnable {

    private volatile boolean timeToStop = false;
    private int interval;
    private long lastCheckpointAt;
    private PersistentStore objectManager;
    private Thread cpThread;

    private Logger m_logger;

    public CheckPointer(PersistentStore om) {
        objectManager = om;
        m_logger = om.m_logger;
    }

    public void init() throws Exception {
        cpThread = new Thread(this);
        cpThread.setName(objectManager.getName()+".Checkpointer");
    }

    public void start() throws Exception {
        Properties beprops = objectManager.omConfig;
        interval = Integer.parseInt(beprops.getProperty("omCheckPtInterval", "0"));
        if (m_logger.isEnabledFor(Level.DEBUG))
            m_logger.log(Level.DEBUG,ResourceManager.getInstance().formatMessage("be.om.checkpointer.interval", new Integer(interval)));
        interval *= 1000;

        cpThread.start();
    }

    public void shutdown() throws Exception {
        timeToStop = true;
        cpThread.interrupt();
        cpThread.join();
    }

    public long getCheckpointInterval() {
        return (long) interval;
    }

    public void stop() throws Exception {
        timeToStop = true;
        cpThread.interrupt();
    }

    /**
     * Main checkpoint cpThread method.
     */
    public void run() {
        objectManager.session.setSession();
        timeToStop = false;

        while (!timeToStop) {
            synchronized (this) {
                try {
                    this.wait(interval);
                } catch (InterruptedException e) {
                        break;
                }
            }
            try {
                objectManager.checkpoint();
                lastCheckpointAt = System.currentTimeMillis();
            } catch (OMException e) {
                m_logger.log(Level.ERROR, "", e);
                // TODO: Should we exit if a checkpoint fails?
                //TODO if(((EngineContext) getContainer()).inShutdown)
                timeToStop = true;
                //else
//                System.exit(1);
            }
        }

        m_logger.log(Level.INFO,ResourceManager.getInstance().getMessage("be.om.checkpointer.terminate"));
    }

    public synchronized void forceCheckpoint() {
        this.notify();
    }

    public void updateStats(ObjectManagerStats omstats) {
        omstats.checkpointInterval = interval;
        omstats.lastCheckpointFinished = lastCheckpointAt;
    }

}
