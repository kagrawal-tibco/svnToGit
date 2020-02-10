package com.tibco.cep.runtime.management.impl.adapter.parent;


import java.util.Properties;
import java.util.Timer;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.management.impl.adapter.RemoteClusterSpecialPropKeys;
import com.tibco.cep.runtime.management.impl.adapter.util.ProxyCoherenceCacheTable;
import com.tibco.cep.runtime.management.impl.adapter.util.ProxyCoherenceManagementTable;
import com.tibco.cep.runtime.management.impl.adapter.util.ProxyCoherenceMetricTable;
import com.tibco.cep.runtime.service.Service;

/*
* Author: Ashwin Jayaprakash Date: Apr 21, 2009 Time: 10:45:14 AM
*/
public class RemoteProcessMaster implements Service {
    protected volatile Timer timer;

    protected volatile String clusterName;

    protected volatile String masterCddPath;
    
    protected volatile RemoteProcessInfo currentRemoteProcessInfo;

    protected volatile ProxyCoherenceManagementTable proxyCoherenceManagementTable;

    protected volatile ProxyCoherenceMetricTable proxyCoherenceMetricTable;
    protected volatile ProxyCoherenceCacheTable proxyCoherenceCacheTable;


    public RemoteProcessMaster(String clusterName, String masterCddPath) {
        this.clusterName = clusterName;
        this.masterCddPath = masterCddPath;
    }

    public void init(Configuration configuration, Object... otherArgs) throws Exception {
    }

    public void start() throws Exception {
        //Start nothing by default. Only if this is an MM server.
    }

    public String getId() {
        return getClass().getName();
    }

    /**
     * @return The remote-process-info obtained from the
     * {@link #connect(java.util.Properties, com.tibco.cep.kernel.service.logging.Logger)} operation.
     * Can be <code>null</code>, if a
     *         connection was not made before.
     */
    public RemoteProcessInfo getCurrentRemoteProcessInfo() {
        return currentRemoteProcessInfo;
    }

    /**
     * If the old one already exists, then it will be discarded and a new connection will be
     * attempted.
     *
     * @param properties
     * @param logger
     * @return
     * @throws Exception
     */
    synchronized RemoteProcessInfo connect(Properties properties, Logger logger)
            throws Exception {
        if (currentRemoteProcessInfo != null) {
            try {
                currentRemoteProcessInfo.discard();
            }
            catch (Exception e) {
                logger.log(Level.WARN, e, "Error occurred while discarding the old remote-process-info for cluster '%s'.", clusterName);
            }

            currentRemoteProcessInfo = null;
        }

        //--------------

        currentRemoteProcessInfo = doConnect(properties, logger);

        RemoteProcessPingerTask pingerTask = currentRemoteProcessInfo.getPingerTask();

        if (timer == null) {
            timer = new Timer("RemoteProcess.Timer.Pinger."+clusterName, true);
        }
        timer.schedule(pingerTask, pingerTask.getPingDelayMillis(),
                pingerTask.getPingDelayMillis());

        return currentRemoteProcessInfo;
    }

    RemoteProcessInfo doConnect(Properties properties, Logger logger) throws Exception {
        RemoteProcessBuilder processBuilder =
                new RemoteProcessBuilder(properties, logger, masterCddPath, clusterName);

        processBuilder.firstStep();

        try {
            processBuilder.attemptBrokerConnection();

            logger.log(Level.INFO, "Remote process for cluster '%s' is already running.", clusterName);
            
            final String killOldBroker = properties.getProperty(RemoteClusterSpecialPropKeys.BROKER_PROCESS_KILL.getKey());

            if(killOldBroker == null || killOldBroker.equals("false") || killOldBroker.isEmpty())
            	return processBuilder.finalStep();
            else{
            	logger.log(Level.INFO, "Trying to shutdown remote process for cluster '%s'.", clusterName);
            	processBuilder.shutdownBroker(0);
            }
        } catch (Exception e) {
            logger.log(Level.DEBUG, e, "Initial connection test failed for cluster '%s'.", clusterName);
        }

        //-----------

        processBuilder.launchChildProcess();

        logger.log(Level.INFO, "Launched remote process for cluster '%s'. Waiting for it to start.", clusterName);
        Thread.sleep(5000);
        logger.log(Level.INFO, "Attempting connection to remote process for cluster '%s'.", clusterName);

        //-----------

        Exception exception = null;
        int i = 0;
        long totalTimeWaited = 0;
        for (; totalTimeWaited < com.tibco.cep.runtime.management.impl.adapter.RemoteProcessManager
                .PING_DELAY_MILLIS; i++) {
            long sleepTime = 1000 * (i + 1);
            Thread.sleep(sleepTime);
            totalTimeWaited = totalTimeWaited + sleepTime;

            try {
                processBuilder.testChildProcessAlive();
            }
            catch (Exception e) {
                throw e;
            }

            try {
                processBuilder.attemptBrokerConnection();

                return processBuilder.finalStep();
            }
            catch (Exception e) {
                logger.log(Level.DEBUG, e, "Connection test failed for cluster '%s'", clusterName);

                exception = e;
            }
        }

        throw new RuntimeException(
                "Unable to connect to the remote process even after ["
                        + i + "] attempts over a [" + (totalTimeWaited / 1000) + "] second period.",
                exception);
    }

    public void stop() throws Exception {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        if (currentRemoteProcessInfo != null) {
            currentRemoteProcessInfo.discard();
            currentRemoteProcessInfo = null;
        }
    }

    /**
     * @return The name of the cluster for which this process was created
     */
    public String getClusterName() {
        return clusterName;
    }
    
    public void createProxyTables() {
        final RemoteCoherenceClusterAdapter adapter = currentRemoteProcessInfo.getAdapter();

        proxyCoherenceCacheTable = new ProxyCoherenceCacheTable(adapter.getCacheTable());
        proxyCoherenceMetricTable = new ProxyCoherenceMetricTable(adapter.getMetricDataTable());
        proxyCoherenceManagementTable = new ProxyCoherenceManagementTable(adapter.getManagementTable());
    }

    public void discardProxyTables(String listenerName) {
        proxyCoherenceCacheTable.discard();

        proxyCoherenceMetricTable.unregisterListener(listenerName, null);
        proxyCoherenceMetricTable.discard();

        proxyCoherenceManagementTable.discard();
    }

    public ProxyCoherenceManagementTable getProxyCoherenceManagementTable() {
        return proxyCoherenceManagementTable;
    }

    public ProxyCoherenceMetricTable getProxyCoherenceMetricTable() {
        return proxyCoherenceMetricTable;
    }

    public ProxyCoherenceCacheTable getProxyCoherenceCacheTable() {
        return proxyCoherenceCacheTable;
    }
}
