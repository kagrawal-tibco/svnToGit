/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 11/8/2010
 */

package com.tibco.cep.runtime.service.om.impl.coherence.tangosol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.ft.FTNodeManager;
import com.tibco.cep.runtime.service.ft.spi.FTNodeControl;
import com.tibco.cep.runtime.service.ft.spi.NodeInfo;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 11, 2008
 * Time: 3:58:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultNodeControlImpl implements FTNodeControl {
    private FTNodeManagerImpl nodemgr;
    private boolean initialized = false;
    private FileLock m_primaryFileLock;
    private File m_primaryLockFile;
    private Logger m_logger;

    public DefaultNodeControlImpl(FTNodeManager nodemgr) {
        this.nodemgr = (FTNodeManagerImpl) nodemgr;
        this.m_logger = nodemgr.getLogger();

    }

    public void init() {
        if (!initialized) {
            final String clusterName = this.nodemgr.getProperties().getString("be.ft.cluster.name", this.nodemgr.getNodeName()).trim();
            final String primaryLockDir = this.nodemgr.getProperties().getString("be.ft.cluster.lock.dir", System.getProperty("user.dir"));
            final File lockDir = new File(primaryLockDir);
            if (!lockDir.isDirectory()) {
                throw new RuntimeException("Property be.ft.cluster.lock.dir must point to a shared file system directory accessible to FT be-engine nodes");
            }
            this.m_primaryLockFile = new File(lockDir, "." + clusterName);
        }
    }

    public void postActivate(NodeInfo thisNode) {

    }

    public void postDeactivate(NodeInfo thisNode) {

    }

    public boolean preActivate(NodeInfo thisNode, NodeInfo[] allNodes) {
        if (setPrimaryFileLock()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean preDeactivate(NodeInfo thisNode, NodeInfo[] allNodes) {
        if (releasePrimaryFileLock()) {
            return true;
        } else {
            return false;
        }
    }

    public void shutdown() {

    }

    public FileLock getPrimaryFileLock() {
        return m_primaryFileLock;
    }

    public File getPrimaryLockFile() {
        return m_primaryLockFile;
    }

    public boolean setPrimaryFileLock() {
        if (isValidPrimaryFileLock()) {
            return false;
        }
        try {
            final FileOutputStream lockfos = new FileOutputStream(m_primaryLockFile);
            m_primaryFileLock = lockfos.getChannel().tryLock();
            lockfos.write(nodemgr.getMyNodeGUID().getBytes());
            return true;
        } catch (FileNotFoundException e) {
            m_primaryFileLock = null;
            m_logger.log(Level.FATAL, "Lock file " + m_primaryLockFile.getAbsolutePath() + " not found", e);
            return false;
        } catch (IOException e) {
            m_primaryFileLock = null;
            m_logger.log(Level.DEBUG, "Unable to set Lock file " + m_primaryLockFile.getAbsolutePath(), e);
            return false;
        }
    }

    public boolean releasePrimaryFileLock() {
        if (!isValidPrimaryFileLock()) {
            return false;
        }
        try {
            m_primaryFileLock.release();
            return true;
        } catch (IOException e) {
            m_logger.log(Level.ERROR, "Unable to release Lock file " + m_primaryLockFile.getAbsolutePath(), e);
            return false;
        }
    }

    public boolean isValidPrimaryFileLock() {
        if (m_primaryFileLock == null) {
            return false;
        } else {
            return m_primaryFileLock.isValid();
        }
    }
}
