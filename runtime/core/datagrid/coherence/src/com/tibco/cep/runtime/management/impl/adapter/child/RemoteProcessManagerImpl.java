package com.tibco.cep.runtime.management.impl.adapter.child;

import com.tibco.cep.runtime.management.impl.adapter.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

/*
* Author: Ashwin Jayaprakash Date: Mar 25, 2009 Time: 1:58:52 PM
*/
public class RemoteProcessManagerImpl implements RemoteProcessManager, ExceptionCollector {
    /**
     * {@value}.
     */
    public static final int MAX_RECENT_EXCEPTIONS = 10;

    /**
     * {@value}.
     */
    public static final int MAX_PING_MISSES = 5;

    protected volatile RemoteCoherenceManagementTableImpl managementTable;

    protected volatile RemoteCoherenceMetricTableImpl metricTable;

    protected volatile RemoteCoherenceCacheTableImpl cacheTable;

    protected volatile boolean initSuccess;

    protected final PingTracker pingTracker;

    protected final LinkedBlockingQueue<Throwable> recentExceptions;

    protected final InternalMissedPingListener missedPingListener;

    protected final EnumMap<RemoteClusterSpecialPropKeys, String> specialProperties;

    protected String clusterName;

    /**
     * This will start the {@link com.tibco.cep.runtime.management.impl.adapter.child.PingTracker}.
     *
     * @throws java.rmi.RemoteException
     */
    public RemoteProcessManagerImpl() throws RemoteException {
        System.out.println("Attempting pre-init.");

        this.missedPingListener = new InternalMissedPingListener();

        this.pingTracker = new PingTracker(this.missedPingListener);
        this.pingTracker.start();

        this.recentExceptions = new LinkedBlockingQueue<Throwable>();

        this.cacheTable = new RemoteCoherenceCacheTableImpl();
        this.metricTable = new RemoteCoherenceMetricTableImpl();
        this.managementTable = new RemoteCoherenceManagementTableImpl();

        //-------------

        this.specialProperties = new EnumMap<RemoteClusterSpecialPropKeys, String>(
                RemoteClusterSpecialPropKeys.class);

        String logFilePath = null;

        Properties sysProps = System.getProperties();
        for (RemoteClusterSpecialPropKeys specialPropKey : RemoteClusterSpecialPropKeys.values()) {
            String specialPropValue =
                    sysProps.getProperty(specialPropKey.getKey(), specialPropKey.getDefaultValue());
        	//System.out.println("--------TEST-------"+specialPropKey.getKey()+": "+specialPropValue);

            if (specialPropValue != null) {
                this.specialProperties.put(specialPropKey, specialPropValue);
                if (specialPropKey.equals(RemoteClusterSpecialPropKeys.CLUSTER)) {
                    this.clusterName = specialPropValue;
                } else if (specialPropKey.equals(RemoteClusterSpecialPropKeys.BROKER_LOG_FILE)) {
                    logFilePath = specialPropValue;
                }
            }
        }
        
        //System.out.println("******TEST******The System properties are: "+System.getProperties());

        try {
            File logFile = setupSysLog(logFilePath);
            this.specialProperties
                    .put(RemoteClusterSpecialPropKeys.BROKER_LOG_FILE, logFile.getAbsolutePath());
        }
        catch (IOException e) {
            throw new RemoteException("Error occurred during pre-init", e);
        }

        System.out.println("Succeeded pre-init.");
    }

    private File setupSysLog(String logFilePath) throws IOException {
        File logFile = null;
        if (logFilePath == null) {
            logFile = File.createTempFile("remote_cluster_broker_", ".log");
        }
        else {
            logFile = new File(logFilePath);
        }

        File parentFile = logFile.getParentFile();
        parentFile.mkdirs();
        if (logFile.exists() == false) {
            logFile.createNewFile();
        }

        if (logFile.exists() == false || logFile.isDirectory() || logFile.canWrite() == false) {
            String s = String.format("Unable to create/use the file [%s] for logging.",
                    logFile.getAbsolutePath());

            throw new IOException(s);
        }
        if(logFilePath == null)
        	logFile.deleteOnExit();

        FileOutputStream fos = new FileOutputStream(logFile);
        PrintStream ps = new PrintStream(fos);

        System.out.println("Switching output to: " + logFile.getAbsolutePath());
        System.setOut(ps);
        System.setErr(ps);
        System.out.println("Switched output to log file.");

        return logFile;
    }

    public EnumMap<RemoteClusterSpecialPropKeys, String> getPropsIfAlreadyInited()
            throws RemoteException {
        if (initSuccess) {
            return specialProperties;
        }

        return null;
    }

    public void init() throws RemoteException {
        if (initSuccess) {
            return;
        }

        synchronized (this) {
            if (initSuccess == false) {
                System.out.println("Attempting to initialize.");

                try {
                    cacheTable.init(clusterName, this);
                    metricTable.init(clusterName, this);
                    managementTable.init(clusterName, this);
                }
                catch (RuntimeException e) {
                    e.printStackTrace();

                    throw e;
                }

                initSuccess = true;

                System.out.println("Initialization succeeded.");
            }
        }
    }

    /**
     * Available after {@link #init()}.
     *
     * @return
     */
    public RemoteCoherenceManagementTableImpl getManagementTableLocal() {
        return managementTable;
    }

    /**
     * Available after {@link #init()}.
     *
     * @return
     */
    public RemoteCoherenceMetricTableImpl getMetricTableLocal() {
        return metricTable;
    }

    /**
     * Available after {@link #init()}.
     *
     * @return
     */
    public RemoteCoherenceCacheTableImpl getCacheTableLocal() {
        return cacheTable;
    }

    /**
     * Available after {@link #init()}.
     *
     * @return
     */
    public RemoteCoherenceManagementTable getManagementTable() {
        return managementTable;
    }

    /**
     * Available after {@link #init()}.
     *
     * @return
     */
    public RemoteCoherenceMetricTable getMetricTable() {
        return metricTable;
    }

    /**
     * Available after {@link #init()}.
     *
     * @return
     */
    public RemoteCoherenceCacheTable getCacheTable() {
        return cacheTable;
    }

    public void ping() throws RemoteException {
        pingTracker.ping();

        missedPingListener.resetMissedPingCount();
    }

    public Collection<Throwable> fetchRecentExceptions() throws RemoteException {
        return new LinkedList<Throwable>(recentExceptions);
    }

    public void collect(Throwable throwable) {
        while (recentExceptions.size() > MAX_RECENT_EXCEPTIONS) {
            recentExceptions.poll();
        }

        recentExceptions.offer(throwable);
    }

    public void shutdown(int status) throws RemoteException {
        System.out.println("Shutdown requested with status code: " + status);

        try {
            try {
                pingTracker.stopTrackerThread();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            try {
                managementTable.discard();
                managementTable = null;
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            try {
                metricTable.discard();
                metricTable = null;
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            try {
                cacheTable.discard();
                cacheTable = null;
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            specialProperties.clear();
        }
        finally {
            System.exit(status);
        }
    }

    //------------

    protected class InternalMissedPingListener implements PingTracker.MissedPingListener {
        protected volatile int missedPingCount;

        protected void resetMissedPingCount() {
            missedPingCount = 0;
        }

        public void onMissedPing(long lastReceivedTimeMillis) {
            missedPingCount++;

            if (missedPingCount > MAX_PING_MISSES) {
                long seconds = (System.currentTimeMillis() - lastReceivedTimeMillis) / 1000;

                System.err.println("Ping messages from the parent process" +
                        " have not been received in more than [" + seconds +
                        "] seconds. This process will attempt to shutdown now.");

                try {
                    RemoteProcessManagerImpl.this.shutdown(1);
                }
                catch (RemoteException e) {
                    //Ignore.
                }
            }
        }
    }
}
