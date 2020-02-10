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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Properties;

import com.tangosol.net.MemberEvent;
import com.tangosol.net.MemberListener;
import com.tangosol.util.ConverterCollections;
import com.tangosol.util.Filter;
import com.tangosol.util.MapEvent;
import com.tangosol.util.MapListener;
import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.ft.FTAsyncNodeManager;
import com.tibco.cep.runtime.service.ft.FTNode;
import com.tibco.cep.runtime.service.ft.FTNodeController;
import com.tibco.cep.runtime.service.ft.FTNodeManager;
import com.tibco.cep.runtime.service.ft.impl.FTNodeImpl;
import com.tibco.cep.runtime.service.ft.spi.FTNodeControl;
import com.tibco.cep.runtime.service.ft.spi.NodeInfo;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 26, 2007
 * Time: 10:49:15 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFTNodeManager implements FTAsyncNodeManager, Serializable {
    public static final String SHUTDOWN_NODE = "SHUTDOWN_NODE";
    protected transient MasterCache m_cache;
    protected transient String myNodeGUID;
    protected transient String myNodeCacheUid;
    protected transient int myNodeState;
    protected transient Logger m_logger;
    protected transient FTNodeController m_controller;
    protected transient FTNodeControl m_controlSpi;
    protected boolean deactivatedBySPI = false;
    protected transient String m_name;
    protected transient short m_priority;
    protected transient FTNode m_currentNode;
    protected transient String m_nodeName;
    protected transient long m_failbackWaitTime;
    protected transient Object m_failbackGuard = new Object();
    protected transient long m_failoverWaitTime;
    protected transient Object m_failoverGuard = new Object();
    protected transient BEProperties m_props;
//    protected transient HashMap clusterMap = new HashMap();
    protected transient HashMap disconnectedNodeMap = new HashMap();
    protected transient File m_primaryLockFile;
    protected transient FileLock m_primaryFileLock;
    protected transient boolean isLockingEnabled;
    protected transient boolean m_clusterLost = false;


    protected void initialize(Properties props, Logger logger, FTNodeController controller, String name) {
        m_logger = logger;
        m_controller = controller;
        m_name = name;
        if (props instanceof BEProperties) {
            m_props = (BEProperties) props;
        } else {
            m_props = new BEProperties(props);
        }
        m_controlSpi = getNodeControlSpi(props);
        m_priority = Short.valueOf(props.getProperty("be.ft.priority", Short.toString(Short.MAX_VALUE)).trim()).shortValue();
        m_nodeName = props.getProperty("be.ft.nodename", m_name).trim();
        if (null == m_nodeName || m_nodeName.length() == 0) {
            throw new RuntimeException("No nodename specified using property be.ft.nodename");
        }

        if (m_nodeName.length() > 30) { //CR:1-975GHR administrator node names are usually big and coherence cannot handle > 30 chars.
            logger.log(Level.INFO, "Node name " + m_nodeName + " is greater than 30 characters, using last 30 characters only");
            m_nodeName = m_nodeName.substring(m_nodeName.length() - 30);
        }

        try {
            logger.log(Level.INFO, "Initializing fault tolerant cache ...");
            System.getProperties().setProperty("tangosol.coherence.member", m_nodeName);
            m_cache = MasterCache.getMasterCache(this, m_props, null);
            if (m_cache == null) {
                throw new RuntimeException("Could not create fault tolerant cache");
            }
            logger.log(Level.INFO, "Successfully initialized cache.");
        } catch (Exception e) {
            m_logger.log(Level.ERROR, "Error initializing fault tolerant cache", e);
        }

        // default failback wait is 15 sec
        m_failbackWaitTime = Long.valueOf(props.getProperty("be.ft.failback.waitmilliseconds", "15000").trim()).longValue();
        m_failoverWaitTime = Long.valueOf(props.getProperty("be.ft.failover.waitmilliseconds", "15000").trim()).longValue();
        isLockingEnabled = Boolean.valueOf(props.getProperty("be.ft.locking.enabled", "false").trim()).booleanValue();
        final String clusterName = m_props.getString("be.ft.cluster.name", name).trim();
        if (isLockingEnabled) {
            final String primaryLockDir = m_props.getString("be.ft.cluster.lock.dir", System.getProperty("user.dir"));
            final File lockDir = new File(primaryLockDir);
            if (!lockDir.isDirectory()) {
                throw new RuntimeException("Property be.ft.cluster.lock.dir must point to a shared file system directory accessible to FT be-engine nodes");
            }
            this.m_primaryLockFile = new File(lockDir, "." + clusterName);
        }
//        final BEProperties ftprops = m_props.getBranch("be.ft."+clusterName);
//        for(Iterator it = ftprops.entrySet().iterator();it.hasNext();) {
//            Map.Entry e = (Map.Entry) it.next();
//            clusterMap.put(e.getKey(),Integer.valueOf((String) e.getValue()));
//        }
        if (m_cache != null) {
            if (m_logger.isEnabledFor(Level.INFO))
                m_logger.log(Level.INFO, name + " has joined cluster [" + m_cache + " ] with priority = " + m_priority);
        }


    }

    public void start() {
        if (m_cache == null) {
            throw new RuntimeException("Could not access fault tolerant cache");
        }
        myNodeGUID = m_nodeName;
        refreshNodeMap(getNodeMap(), true);
        m_currentNode = new FTNodeImpl(myNodeGUID,
                m_cache.getNodeCacheId(),
                m_cache.getName(),
                m_cache.getNodeCacheUid(),
                m_priority,
                m_cache.getTimeNodeCreated(),
                m_cache.getMachineId(),
                m_cache.getAddress(),
                m_cache.getPort(),
                m_nodeName,
                m_cache.getProcessName(),
                m_cache.getRackName(),
                m_cache.getRoleName(),
                m_cache.getSiteName());
        myNodeCacheUid = m_currentNode.getNodeCacheUid();
        m_cache.addMemberListener(getClusterMemberListener());
        m_cache.addMapListener(getNodeMapListener(), getNodeMapFilter(), false);
        if (!m_name.equals(SHUTDOWN_NODE)) {
            addNode(m_currentNode);
            m_logger.log(Level.INFO, "Starting NodeManager on node " + this.toString());
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////
    //MapListener interface

    protected MapListener getNodeMapListener() {
        return new NodeMapListener();
    }


    protected class NodeMapListener implements MapListener {
        public NodeMapListener() {
        }

        public void entryInserted(MapEvent mapEvent) {
        }

        public void entryUpdated(MapEvent mapEvent) {
        }

        public void entryDeleted(MapEvent mapEvent) {
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    // MemberListener interface

    protected MemberListener getClusterMemberListener() {
        return new ClusterMemberListener();
    }

    protected class ClusterMemberListener implements MemberListener {

        public void memberJoined(MemberEvent memberEvent) {
            logMemberEvent("MEMBER JOINED :", memberEvent);

        }

        public void memberLeaving(MemberEvent memberEvent) {
            logMemberEvent("MEMBER LEAVING :", memberEvent);

        }

        public void memberLeft(MemberEvent memberEvent) {
            logMemberEvent("MEMBER LEFT :", memberEvent);
        }

        public void logMemberEvent(String msg, MemberEvent memberEvent) {
            StringBuffer sb = new StringBuffer();
            sb.append(msg);
            sb.append(" uid = " + memberEvent.getMember().getUid().toString());
            sb.append(" id =" + memberEvent.getMember().getId());
            sb.append(" mid =" + memberEvent.getMember().getMachineId());
            sb.append(" timestamp =" + memberEvent.getMember().getTimestamp());
            sb.append(" address =" + memberEvent.getMember().getAddress());
            sb.append(" port =" + memberEvent.getMember().getPort());
            sb.append(" machineName =" + memberEvent.getMember().getMachineName());
            sb.append(" memberName =" + memberEvent.getMember().getMemberName());
            sb.append(" processName =" + memberEvent.getMember().getProcessName());
            sb.append(" rackName =" + memberEvent.getMember().getRackName());
            sb.append(" roleName =" + memberEvent.getMember().getRoleName());
            sb.append(" siteName =" + memberEvent.getMember().getSiteName());
            m_logger.log(Level.DEBUG, sb.toString());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    // NodeMapFilter

    protected Filter getNodeMapFilter() {
        return new NodeMapFilter();
    }

    protected class NodeMapFilter implements Filter, Serializable {

        public NodeMapFilter() {
        }

        public boolean evaluate(Object object) {
            Object obj = null;
            boolean result = false;
            try {
                ConverterCollections.ConverterMapEvent mapEvent = (ConverterCollections.ConverterMapEvent) object;
                switch (mapEvent.getId()) {
                    case MapEvent.ENTRY_INSERTED:
                        obj = mapEvent.getNewValue();
                        break;
                    case MapEvent.ENTRY_UPDATED:
                        obj = mapEvent.getNewValue();
                        break;
                    case MapEvent.ENTRY_DELETED:
                        obj = mapEvent.getOldValue();
                        break;
                }
                result = ((obj != null) && (obj instanceof FTNodeMap));
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    // FTNodeManager interface

    public Logger getLogger() {
        return m_logger;
    }

    public void setNodeController(FTNodeController _controller) {
        this.m_controller = _controller;
    }

    public FTNodeController getNodeController() {
        if (m_controller == null) {
            throw new RuntimeException("No Node Controller found");
        } else
            return m_controller;

    }

    public String getClusterName() {
        return m_cache.getName();
    }

    public String getNodeName() {
        return m_nodeName;
    }

    public String getMyNodeGUID() {
        return myNodeGUID;
    }

    protected FTNodeMap getNodeMap() {
        synchronized (m_cache) {
            FTNodeMap nodemap = null;
            try {
                m_cache.startTxn();
                Object obj = m_cache.get(FTNodeMap.NAME);
                if (obj == null) {
                    if (m_logger.isEnabledFor(Level.DEBUG))
                        m_logger.log(Level.DEBUG, "FTNodeManager:Creating new Node Map");
                    nodemap = new FTNodeMap();
                    m_cache.put(FTNodeMap.NAME, nodemap);
                } else {
                    if (obj instanceof FTNodeMap) {
                        nodemap = (FTNodeMap) obj;
                    }
                }
                m_currentNode = (FTNode) nodemap.get(myNodeGUID);
            } finally {
                m_cache.commitTxn();
            }

            return nodemap;
        }
    }

    public void addNode(FTNode node) {
        boolean failed = false;
        synchronized (m_cache) {
            try {
                m_cache.lock(MasterCache.WRITE_LOCK);
                FTNodeMap nodemap = getNodeMap();
                if (nodemap.get(node.getNodeGUID()) == null) {
                    m_cache.startTxn();
                    nodemap.lastModifierGUID = myNodeGUID;
                    nodemap.lastChangedNodeGUID = node.getNodeGUID();
                    nodemap.nodeActionPerformed = FTNodeMap.NODE_ADDED;
                    nodemap.put(node.getNodeGUID(), node);
                    m_cache.put(FTNodeMap.NAME, nodemap);
                } else {
                    failed = true;
                    throw new RuntimeException("Node " + node + " exists in the nodemap.");
                }
            } finally {
                if (!failed) {
                    m_cache.commitTxn();
                } else {
                    m_cache.rollbackTxn();
                }
                m_cache.unlock(MasterCache.WRITE_LOCK);
            }
        }
    }

    protected void addUpdateNode(FTNode node, boolean notifyMembers) {
        synchronized (m_cache) {
            try {
                m_cache.lock(MasterCache.WRITE_LOCK);
                FTNodeMap nodemap = getNodeMap();
                m_cache.startTxn();
                if (nodemap.get(node.getNodeGUID()) == null) {
                    nodemap.lastModifierGUID = myNodeGUID;
                    nodemap.lastChangedNodeGUID = node.getNodeGUID();
                    nodemap.nodeActionPerformed = notifyMembers ? FTNodeMap.NODE_UPDATED : FTNodeMap.NODE_REFRESHED;
                    nodemap.put(node.getNodeGUID(), node);
                    m_cache.put(FTNodeMap.NAME, nodemap);
                } else {
                    // make sure that the node can only add itself to the map
                    // no other node should add another node to the map
                    if (myNodeGUID.equals(node.getNodeGUID())) {
                        nodemap.lastModifierGUID = myNodeGUID;
                        nodemap.lastChangedNodeGUID = node.getNodeGUID();
                        nodemap.nodeActionPerformed = notifyMembers ? FTNodeMap.NODE_UPDATED : FTNodeMap.NODE_REFRESHED;
                        nodemap.put(node.getNodeGUID(), node);
                        m_cache.put(FTNodeMap.NAME, nodemap);
                    } else {
                        throw new RuntimeException(myNodeGUID + " is attempting to add another node " + node.getNodeGUID() + " to the nodemap.");
                    }
                }
            } finally {
                m_cache.commitTxn();
                m_cache.unlock(MasterCache.WRITE_LOCK);
            }
        }
    }

    public FTNode getCurrentNode() {
        synchronized (m_cache) {
            try {
                m_cache.lock(MasterCache.WRITE_LOCK);
                return (FTNode) getNodeMap().get(myNodeGUID);
            } finally {
                m_cache.unlock(MasterCache.WRITE_LOCK);
            }
        }
    }

    public FTNode getLastModifierNode() {
        synchronized (m_cache) {
            try {
                m_cache.lock(MasterCache.WRITE_LOCK);
                return (FTNode) getNodeMap().get(getNodeMap().lastModifierGUID);
            } finally {
                m_cache.unlock(MasterCache.WRITE_LOCK);
            }
        }
    }

    public FTNode getLastChangedNode() {
        synchronized (m_cache) {
            try {
                m_cache.lock(MasterCache.WRITE_LOCK);
                return (FTNode) getNodeMap().get(getNodeMap().lastChangedNodeGUID);
            } finally {
                m_cache.unlock(MasterCache.WRITE_LOCK);
            }
        }
    }


    public String getNodeGUIDfromUID(String uid) {
        String guid = null;
        FTNode[] nodes = getAllNodes();
        for (int i = 0; i < nodes.length; i++) {
            FTNode node = nodes[i];
            String nodeuid = node.getNodeCacheUid();
            if (nodeuid.equals(uid)) {
                guid = node.getNodeGUID();
                break;
            }
        }
        return guid;

    }


    public void cleanDeadNodes(FTNode node) {
        synchronized (m_cache) {
            if (node == null)
                return;
            try {
                m_cache.lock(MasterCache.WRITE_LOCK);
                FTNodeMap nodemap = getNodeMap();
                if (nodemap.get(node.getNodeGUID()) != null) {
                    m_cache.startTxn();
                    nodemap.lastModifierGUID = myNodeGUID;
                    nodemap.lastChangedNodeGUID = node.getNodeGUID();
                    nodemap.nodeActionPerformed = FTNodeMap.NODE_REFRESHED;
                    nodemap.remove(node.getNodeGUID());
                    m_cache.put(FTNodeMap.NAME, nodemap);
                }
            } finally {
                m_cache.commitTxn();
                m_cache.unlock(MasterCache.WRITE_LOCK);
                if (m_logger.isEnabledFor(Level.DEBUG))
                    m_logger.log(Level.DEBUG, "Removing Node: " + node);
            }
        }
    }

    public void removeNode(FTNode node, String uuid) {
        boolean rollbackTxn = false;
        synchronized (m_cache) {
            if (node == null)
                return;
            try {
                m_cache.lock(MasterCache.WRITE_LOCK);
                FTNodeMap nodemap = getNodeMap();
                if (nodemap.containsKey(node.getNodeGUID())) {
                    m_cache.startTxn();

                    if (node.getNodeGUID() == myNodeGUID && uuid != myNodeCacheUid) {
                        rollbackTxn = true;
                    } else {
                        nodemap.lastModifierGUID = myNodeGUID;
                        nodemap.lastChangedNodeGUID = node.getNodeGUID();
                        nodemap.nodeActionPerformed = FTNodeMap.NODE_DELETED;
                        nodemap.remove(node.getNodeGUID());
                        m_cache.put(FTNodeMap.NAME, nodemap);
                    }
                }
            } finally {
                if (!rollbackTxn) {
                    m_cache.commitTxn();
                    if (m_logger.isEnabledFor(Level.DEBUG))
                        m_logger.log(Level.DEBUG, "Removing Node: " + node);
                } else {
                    m_cache.rollbackTxn();
                }
                m_cache.unlock(MasterCache.WRITE_LOCK);
            }
        }
    }

    public int getNodeCount() {
        synchronized (m_cache) {
            try {
                m_cache.lock(MasterCache.WRITE_LOCK);
                return getNodeMap().size();
            } finally {
                m_cache.unlock(MasterCache.WRITE_LOCK);
            }
        }
    }

    protected void refreshNodeMap(FTNodeMap nodemap, boolean withTxn) {
        synchronized (m_cache) {
            StringBuffer sb = new StringBuffer();
            int numRemoved = 0;
            try {
                FTNode[] nodes = (FTNode[]) getAllNodes(nodemap);
                if (withTxn) {
                    m_cache.lock(MasterCache.WRITE_LOCK);
                    m_cache.startTxn();
                }
                for (int i = 0; i < nodes.length; i++) {
                    if (!m_cache.isAlive(nodes[i].getNodeCacheUid())) {
                        m_logger.log(Level.DEBUG, nodes[i] + " is not alive");
                        sb.append(nodes[i].toString() + " , ");
                        synchronized (disconnectedNodeMap) {
                            disconnectedNodeMap.put(nodes[i].getNodeGUID(), nodes[i]);
                        }
                        cleanDeadNodes(nodes[i]);
                    }
                }
            } finally {
                if (withTxn) {
                    m_cache.commitTxn();
                    m_cache.unlock(MasterCache.WRITE_LOCK);
                }
                if (m_logger.isEnabledFor(Level.DEBUG) && numRemoved > 0)
                    m_logger.log(Level.DEBUG, "Removing Inactive FT Nodes: " + sb.toString());
            }
        }
    }

    protected FTNode[] getAllNodes(FTNodeMap nodemap) {
        synchronized (m_cache) {
            if (nodemap != null)
                return (FTNode[]) nodemap.values().toArray(new FTNode[0]);
            try {
                m_cache.lock(MasterCache.WRITE_LOCK);
                return (FTNode[]) getNodeMap().values().toArray(new FTNode[0]);
            } finally {
                m_cache.unlock(MasterCache.WRITE_LOCK);
            }
        }
    }

    public FTNode[] getAllNodes() {
        return getAllNodes(null);
    }

    public boolean isPrimary(FTNode node) {
        return node.isPrimary();
    }

    public FTNode getPrimaryNode() {
        FTNode[] nodes = getAllNodes(null);
        for (int i = 0; i < nodes.length; i++) {
            if (isPrimary(nodes[i]))
                return nodes[i];
        }
        if (m_logger.isEnabledFor(Level.INFO))
            m_logger.log(Level.INFO, "FTNodeManager:No Primary node found!");
        return null;
    }

    public FTNode shutdown() {
        try {
            FTNode node = getCurrentNode();
            removeNode(node, node.getNodeCacheUid());
            return node;
        } catch (Exception e) {
            // 		   Mask the exceptions until Tangosol 3.4 ships out.
            //         http://forums.tangosol.com/thread.jspa?messageID=7728&#7728
            //    	   In the cluster-config element inside of our XML file we have the following code that
            //		   disables the shutdownhook  functionality:
            //
            //    		    <shutdown-listener>
            //    		      <enabled system-property="tangosol.coherence.shutdownhook">false</enabled>
            //    		    </shutdown-listener>
            //
            //
            //
            //    	   However, when I try to unload coherence code I see that the code fails to be
            //         downloaded since some coherence objects (e.g. SimpleElement) is being kept in
            //         memory since SafeCluster#ShutdownHook is referencing it (through SafeCluster).
            //         So I'm wondering how is it possible that even after having shutdownhook disabled
            //         I still have a Shutdownhook added to the Java system.
            //
            //    	   Coherence may potentially register two shutdown hooks: one for clustered services
            //         and another one for the local services.
            //
            //    	   For example, any cache created using the following cache configuration descriptor
            //         would be a part of of local cache service.
            //
            //    	   <!DOCTYPE cache-config SYSTEM "cache-config.dtd">
            //
            //    	   <cache-config>
            //    	     <caching-scheme-mapping>
            //    	       <cache-mapping>
            //    	         <cache-name>*</cache-name>
            //    	         <scheme-name>local</scheme-name>
            //    	       </cache-mapping>
            //    	     </caching-scheme-mapping>
            //
            //    	     <caching-schemes>
            //    	       <local-scheme>
            //    	         <scheme-name>local</scheme-name>
            //    	       </local-scheme>
            //    	     </caching-schemes>
            //    	   </cache-config>
            //
            //
            //
            //    	   The "shutdown-listener" element you referred to controls only the shutdown hook that is
            //         related to clustered services and is not a part of the SafeCluster class logic that
            //         controls only the "local" shutdown hook (SafeCluster#ShutdownHook), which at the moment
            //         is registered unconditionally.
            //
            //    	   There is an open improvement request (COH-556: Unregister ShutdownHook for local services
            //         during cluster shutdown) that is scheduled for the next Coherence 3.4 release.
        } finally {
            m_cache.release();
            m_cache = null;
        }
        return null;
    }

    public boolean isValidNode(String _guid) {
        return (getNodeMap().get(_guid) != null);
    }


    public abstract FTNode getNextPriorityNode(FTNodeMap nodemap);

    public abstract FTNode getNextPriorityNode();

    public abstract void activate(FTNode node);

    public abstract void deactivate(FTNode node);

    public abstract String getCurrentNodeName();

    public abstract FTNode shutdown(FTNode node);

    protected void rollbackNodeState(FTNode node) {
        synchronized (m_cache) {
            try {
                m_cache.lock(MasterCache.WRITE_LOCK);
                FTNodeMap nodemap = getNodeMap();
                if (nodemap.get(node.getNodeGUID()).equals(node)) {
                    m_cache.startTxn();
                    nodemap.lastModifierGUID = myNodeGUID;
                    nodemap.lastChangedNodeGUID = node.getNodeGUID();
                    nodemap.nodeActionPerformed = FTNodeMap.NODE_REFRESHED;

                    StringBuffer logState = new StringBuffer();
                    logState.append("NodeStateRollback:[ID:")
                            .append(node.getNodeCacheId())
                            .append(",Priority:")
                            .append(node.getPriority())
                            .append(",State:")
                            .append(FTNode.stateArray[node.getNodeState()])
                            .append(" -> ");
                    nodemap.put(node.getNodeGUID(), node);
                    node.resetPreviousNodeState();
                    setGlobalNodeState(nodemap, node, node.getNodeState());

                    // keep a copy of the state for exception purposes.
                    if (nodemap.lastChangedNodeGUID.equals(myNodeGUID))
                        myNodeState = node.getNodeState();
                    logState.append(FTNode.stateArray[node.getNodeState()])
                            .append("]");
                    if (m_logger.isEnabledFor(Level.DEBUG))
                        m_logger.log(Level.DEBUG, logState.toString());
                    m_cache.put(FTNodeMap.NAME, nodemap);
                } else {
                    m_logger.log(Level.ERROR, "FTNodeManager:Expected Node " + node + " does not exist while changing node state to " + FTNode.stateArray[node.getNodeState()]);
                    throw new RuntimeException("FTNodeManager:Expected Node " + node + " does not exist while changing node state to " + FTNode.stateArray[node.getNodeState()]);
                }
            } finally {
                m_cache.commitTxn();
                m_cache.unlock(MasterCache.WRITE_LOCK);
            }
        }
    }

    protected void setNodeState(FTNode node, int state, boolean refresh) {
        synchronized (m_cache) {
            try {
                m_cache.lock(MasterCache.WRITE_LOCK);
                FTNodeMap nodemap = getNodeMap();
                if (nodemap.get(node.getNodeGUID()).equals(node)) {
                    m_cache.startTxn();
                    nodemap.lastModifierGUID = myNodeGUID;
                    nodemap.lastChangedNodeGUID = node.getNodeGUID();
                    if (refresh) {
                        nodemap.nodeActionPerformed = FTNodeMap.NODE_REFRESHED;
                    } else {
                        nodemap.nodeActionPerformed = FTNodeMap.NODE_UPDATED;
                    }
                    StringBuffer logState = new StringBuffer();
                    logState.append("NodeStateChange:[ID:")
                            .append(node.getNodeCacheId())
                            .append(",Priority:")
                            .append(node.getPriority())
                            .append(",State:")
                            .append(FTNode.stateArray[node.getNodeState()])
                            .append(" -> ");
                    node.setNodeState(state);
                    nodemap.put(node.getNodeGUID(), node);
                    setGlobalNodeState(nodemap, node, state);

                    // keep a copy of the state for exception purposes.
                    if (nodemap.lastChangedNodeGUID.equals(myNodeGUID))
                        myNodeState = state;
                    logState.append(FTNode.stateArray[node.getNodeState()])
                            .append("]");
                    if (m_logger.isEnabledFor(Level.DEBUG))
                        m_logger.log(Level.DEBUG, logState.toString());
                    m_cache.put(FTNodeMap.NAME, nodemap);
                } else {
                    m_logger.log(Level.ERROR, "FTNodeManager:Expected Node " + node + " does not exist while changing node state to " + FTNode.stateArray[state]);
                    throw new RuntimeException("FTNodeManager:Expected Node " + node + " does not exist while changing node state to " + FTNode.stateArray[state]);
                }
            } finally {
                m_cache.commitTxn();
                m_cache.unlock(MasterCache.WRITE_LOCK);
            }
        }
    }

    protected abstract void setGlobalNodeState(FTNodeMap nodemap, FTNode node, int state);
    /*
    public abstract void memberJoined(MemberEvent memberEvent);

    public abstract void memberLeaving(MemberEvent memberEvent);

    public abstract void memberLeft(MemberEvent memberEvent);

    public abstract void entryInserted(MapEvent mapEvent);

    public abstract void entryUpdated(MapEvent mapEvent);

    public abstract void entryDeleted(MapEvent mapEvent);

    public abstract void notify(ChangeListener.ChangeEvent e);
    */

    protected abstract int getNodeInfoState(int ftNodeState);

    public BEProperties getProperties() {
        return m_props;
    }

    protected HashMap getDisconnectedNodeMap() {
        return disconnectedNodeMap;
    }

    public Object getFailbackGuard() {
        return m_failbackGuard;
    }

    public Object getFailoverGuard() {
        return m_failoverGuard;
    }

    public FileLock getPrimaryFileLock() {
        return m_primaryFileLock;
    }

    public File getPrimaryLockFile() {
        return m_primaryLockFile;
    }

    public String getPrimaryFileLockNode() {
        try {
            final FileInputStream lockfis = new FileInputStream(m_primaryLockFile);
            BufferedReader fr = new BufferedReader(new FileReader(m_primaryLockFile));
            String nodename = fr.readLine().trim();
            return nodename;

        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public boolean setPrimaryFileLock() {
        if (!isLockingEnabled) {
            return false;
        }
        if (isValidPrimaryFileLock()) {
            return false;
        }
        try {
            final FileOutputStream lockfos = new FileOutputStream(m_primaryLockFile);
            m_primaryFileLock = lockfos.getChannel().tryLock();
            PrintWriter fw = new PrintWriter(lockfos, true);
            fw.println(myNodeGUID);
            fw.flush();
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
        if (!isLockingEnabled) {
            return false;
        }
        if (!isValidPrimaryFileLock()) {
            return false;
        }
        try {
            m_primaryFileLock.release();
            return true;
        } catch (IOException e) {
            m_logger.log(Level.DEBUG, "Unable to release Lock file " + m_primaryLockFile.getAbsolutePath(), e);
            return false;
        }
    }

    public boolean isPrimaryFileLocked() {
        if (!isLockingEnabled) {
            return false;
        }
        if (m_primaryFileLock != null) {
            return m_primaryFileLock.isValid();
        } else {
            final FileOutputStream lockfos;
            try {
                lockfos = new FileOutputStream(m_primaryLockFile);
                m_primaryFileLock = lockfos.getChannel().tryLock();
                if (m_primaryFileLock != null) {
                    m_primaryFileLock.release();
                    m_primaryFileLock = null;
                    return false;
                } else
                    return true;
            } catch (FileNotFoundException e) {
                m_logger.log(Level.FATAL, "Lock file " + m_primaryLockFile.getAbsolutePath() + " not found", e);
                return false;
            } catch (IOException e) {
                m_logger.log(Level.DEBUG, "Unable to test Lock file " + m_primaryLockFile.getAbsolutePath(), e);
                return false;
            }
        }
    }

    public boolean isValidPrimaryFileLock() {
        if (!isLockingEnabled) {
            return false;
        }
        if (m_primaryFileLock == null) {
            return false;
        } else {
            return m_primaryFileLock.isValid();
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////
    // NodeControlSpi - gives the implementor the basic information to solve
    // cluster split-brain problems

    private FTNodeControl getNodeControlSpi(Properties props) {
        String ftNodeControlSpiClass = props.getProperty("be.ft.control.class");
        if (ftNodeControlSpiClass == null)
            return null;
        else {
            Class clazz = null;
            try {
                clazz = Class.forName(ftNodeControlSpiClass);
                Constructor constructor = clazz.getConstructor(new Class[]{FTNodeManager.class});
                return (FTNodeControl) constructor.newInstance(new Object[]{this});
            } catch (Exception e) {
                m_logger.log(Level.ERROR, "NodeControl Service Provider Implementation Class problem:" + ftNodeControlSpiClass, e);
                return null;
            }
        }
    }

    private NodeInfo[] getAllNodeInfo() {
        FTNode[] allFTNodes = getAllNodes();
        NodeInfo[] allNodeInfos = new NodeInfo[allFTNodes.length];
        for (int i = 0; i < allFTNodes.length; i++) {
            allNodeInfos[i] = createNodeInfo(allFTNodes[i]);

        }
        return allNodeInfos;
    }

    private NodeInfo createNodeInfo(FTNode _ftNode) {
        return new NodeInfoImpl(_ftNode, getNodeInfoState(_ftNode.getNodeState()));
    }

    protected void nodeControlSpiInit() {
        if (m_controlSpi != null) {
            m_controlSpi.init();
        }
    }

    protected boolean nodeControlSpiPreActivate() {
        if (m_controlSpi != null) {
            return m_controlSpi.preActivate(createNodeInfo(getCurrentNode()), getAllNodeInfo());
        } else
            return true;
    }

    protected void nodeControlSpiPostActivate() {
        if (m_controlSpi != null) {
            m_controlSpi.postActivate(createNodeInfo(getCurrentNode()));
        }
    }

    protected boolean nodeControlSpiPreDeActivate() {
        if (m_controlSpi != null) {
            return m_controlSpi.preDeactivate(createNodeInfo(getCurrentNode()), getAllNodeInfo());
        } else
            return true;
    }

    protected void nodeControlSpiPostDeActivate() {
        if (m_controlSpi != null) {
            m_controlSpi.postDeactivate(createNodeInfo(getCurrentNode()));
        }
    }

}
