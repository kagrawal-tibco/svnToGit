package com.tibco.cep.loadbalancer.impl.membership.file;

import static com.tibco.cep.util.Helper.$configProperty;
import static com.tibco.cep.util.Helper.$eval;
import static com.tibco.cep.util.Helper.$logger;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.impl.service.DefaultThreadFactory;
import com.tibco.cep.loadbalancer.endpoint.Endpoint;
import com.tibco.cep.loadbalancer.endpoint.EndpointContainer;
import com.tibco.cep.loadbalancer.impl.membership.MembershipInfo;
import com.tibco.cep.loadbalancer.membership.MembershipChangeListener;
import com.tibco.cep.loadbalancer.membership.MembershipChangeProvider;
import com.tibco.cep.loadbalancer.util.Helper;
import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash / Date: Mar 24, 2010 / Time: 10:58:19 AM
*/

@LogCategory("loadbalancer.core.membership.file")
public abstract class AbstractFileMembershipChangeProvider<E extends Endpoint, C extends EndpointContainer<E>>
        implements MembershipChangeProvider<E, C> {
    /**
     * {@value}.
     */
    public static final long DEFAULT_POLL_INTERVAL_MILLIS = 2500;

    /**
     * {@value}.
     */
    public static final String PROPERTY_POLL_DIR = "${this.id}.file.polldir";

    protected Id id;

    protected File pollDirectory;

    protected MembershipChangeListener<E, C> changeListener;

    protected ReentrantLock reentrantLock;

    protected ScheduledExecutorService executorService;

    protected HashMap<File, ScannedFileInfo<E, C>> scannedFiles;

    protected Logger logger;

    protected ResourceProvider resourceProvider;

    public AbstractFileMembershipChangeProvider() {
        this.reentrantLock = new ReentrantLock();
    }

    public AbstractFileMembershipChangeProvider(Id id) {
        this.reentrantLock = new ReentrantLock();

        this.id = id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    @Override
    public Id getId() {
        return id;
    }

    public File getPollDirectory() {
        return pollDirectory;
    }

    public void setPollDirectory(File pollDirectory) {
        this.pollDirectory = pollDirectory;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void start(ResourceProvider resourceProvider, MembershipChangeListener<E, C> changeListener)
            throws LifecycleException {
        this.logger = $logger(resourceProvider, getClass());

        this.resourceProvider = resourceProvider;

        this.changeListener = changeListener;

        this.scannedFiles = new HashMap<File, ScannedFileInfo<E, C>>();

        String executerServiceName = getClass().getSimpleName() + "." + Helper.$hashMD5(id);
        this.executorService = Executors.newScheduledThreadPool(1, new DefaultThreadFactory(executerServiceName));
        this.executorService
                .scheduleAtFixedRate(new FilePoller(), DEFAULT_POLL_INTERVAL_MILLIS, DEFAULT_POLL_INTERVAL_MILLIS,
                        TimeUnit.MILLISECONDS);

        String pollDirFlag = $eval(resourceProvider, PROPERTY_POLL_DIR, "this", this).toString();
        String pollDirString = $configProperty(resourceProvider, pollDirFlag);
        if (pollDirString == null) {
            String msg = String.format("The polling directory [%s] must be provided for the membership listener [%s]",
                    pollDirFlag, getId());

            throw new LifecycleException(new IllegalArgumentException(msg));
        }
        this.pollDirectory = new File(pollDirString);

        this.logger.log(Level.INFO,
                String.format("[%s] started polling Members in [%s]", getId(), pollDirectory.getAbsolutePath()));
    }

    @Override
    public void refresh() throws LifecycleException {
        reentrantLock.lock();
        try {
            logger.log(Level.INFO, String.format("[%s] received a forced refresh", getId()));

            scannedFiles.clear();
        }
        finally {
            reentrantLock.unlock();
        }
    }

    protected void fileNew(File file, ScannedFileInfo<E, C> scannedFileInfo) {
        logger.log(Level.INFO, String.format("[%s] found a new Member [%s] in [%s]", getId(), file.getName(),
                pollDirectory.getAbsolutePath()));

        MembershipInfo<E, C> membershipInfo = readFromFile(file);

        changeListener.hasJoined(membershipInfo.createContainer(resourceProvider));

        scannedFileInfo.setMembershipInfo(membershipInfo);
    }

    protected void fileChanged(File file, ScannedFileInfo<E, C> existingScannedFileInfo) {
        MembershipInfo<E, C> membershipInfo = readFromFile(file);
        MembershipInfo<E, C> existingMembershipInfo = existingScannedFileInfo.getMembershipInfo();

        if (existingMembershipInfo.getVersion().equals(membershipInfo.getVersion())) {
            logger.log(Level.INFO,
                    String.format("[%s] found a Member [%s] in [%s] that has not changed",
                            getId(), file.getName(), pollDirectory.getAbsolutePath()));

            changeListener.isOk(membershipInfo.getContainerId());
        }
        else {
            logger.log(Level.INFO,
                    String.format("[%s] found a Member [%s] in [%s] that has changed",
                            getId(), file.getName(), pollDirectory.getAbsolutePath()));

            //Old one leaves.
            changeListener.hasLeft(existingMembershipInfo.getContainerId());

            //New one joins.
            changeListener.hasJoined(membershipInfo.createContainer(resourceProvider));

            existingScannedFileInfo.setMembershipInfo(membershipInfo);
        }
    }

    protected void fileDeleted(File file, ScannedFileInfo<E, C> scannedFileInfo) {
        logger.log(Level.INFO,
                String.format("[%s] is removing a Member [%s] no longer present in [%s]",
                        getId(), file.getName(), pollDirectory.getAbsolutePath()));

        changeListener.hasLeft(scannedFileInfo.getMembershipInfo().getContainerId());
    }

    protected abstract MembershipInfo<E, C> readFromFile(File file);

    @Override
    public void stop() throws LifecycleException {
        executorService.shutdownNow();

        reentrantLock.lock();
        try {
            scannedFiles.clear();
        }
        finally {
            reentrantLock.unlock();
        }

        logger.log(Level.INFO,
                String.format("[%s] stopped polling Members in [%s]", getId(), pollDirectory.getAbsolutePath()));

        executorService = null;
        changeListener = null;
        scannedFiles = null;

        logger.log(Level.INFO, String.format("[%s] discarded", getId()));
    }

    //---------------

    protected class FilePoller implements Runnable {
        public FilePoller() {
        }

        @Override
        public void run() {
            try {
                reentrantLock.lock();
                try {
                    poll();
                }
                finally {
                    reentrantLock.unlock();
                }
            }
            catch (Throwable t) {
                logger.log(Level.SEVERE,
                        String.format("[%s] encountered an error while polling [%s]", getId(),
                                pollDirectory.getAbsolutePath()), t);
            }
        }

        protected void poll() {
            File[] files = pollDirectory.listFiles();

            HashSet<File> currentSet = new HashSet<File>();

            //New or modified files.
            for (File file : files) {
                if (file.isDirectory()) {
                    continue;
                }

                ScannedFileInfo<E, C> scannedFileInfo = scannedFiles.get(file);
                long lastMod = file.lastModified();

                if (scannedFileInfo == null) {
                    scannedFileInfo = new ScannedFileInfo<E, C>(lastMod);

                    fileNew(file, scannedFileInfo);

                    scannedFiles.put(file, scannedFileInfo);
                }
                else if (scannedFileInfo.getLastModifiedTime() < lastMod) {
                    scannedFileInfo.setLastModifiedTime(lastMod);

                    fileChanged(file, scannedFileInfo);
                }

                currentSet.add(file);
            }

            //---------------

            //Deleted files.
            for (Iterator<Entry<File, ScannedFileInfo<E, C>>> iterator = scannedFiles.entrySet().iterator();
                 iterator.hasNext();) {
                Entry<File, ScannedFileInfo<E, C>> entry = iterator.next();
                File file = entry.getKey();

                if (currentSet.contains(file) == false) {
                    ScannedFileInfo<E, C> scannedFileInfo = entry.getValue();

                    fileDeleted(file, scannedFileInfo);

                    iterator.remove();
                }
            }

            currentSet.clear();
        }
    }
}
