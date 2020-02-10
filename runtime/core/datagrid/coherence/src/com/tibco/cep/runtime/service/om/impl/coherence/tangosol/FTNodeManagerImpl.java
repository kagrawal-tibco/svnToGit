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

import java.util.Properties;

import com.tangosol.net.MemberEvent;
import com.tangosol.net.MemberListener;
import com.tangosol.util.MapEvent;
import com.tangosol.util.MapListener;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.ChangeListener;
import com.tibco.cep.runtime.service.ft.FTAsyncMsgCallback;
import com.tibco.cep.runtime.service.ft.FTNode;
import com.tibco.cep.runtime.service.ft.FTNodeAsyncMsgId;
import com.tibco.cep.runtime.service.ft.FTNodeController;
import com.tibco.cep.runtime.service.ft.impl.FTNodeAsyncControllerImpl;
import com.tibco.cep.runtime.service.ft.impl.FTNodeAsyncControllerMsgs;
import com.tibco.cep.runtime.service.ft.spi.NodeInfo;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Sep 29, 2006
 * Time: 6:11:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class FTNodeManagerImpl extends AbstractFTNodeManager implements ChangeListener {

    public FTNodeManagerImpl(Properties props, Logger logger, FTNodeController controller, String name) {
        initialize(props, logger, controller, name);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Asynchronous Callback interface start

    public void ftCallback(FTNodeAsyncControllerMsgs msg) throws Exception {
        FTNode myNode = (FTNode) getNodeMap().get(myNodeGUID);
        FTNode nextPriorityNode = null;


        switch (msg.getId()) {
            case FTNodeAsyncMsgId.MSGID_ASYNC_INIT_ALL: {
                if (m_logger.isEnabledFor(Level.DEBUG))
                    m_logger.log(Level.DEBUG, "FTCallback:" + msg);
                setNodeState(myNode, FTNode.NODE_END_RECOVERY, false);
            }
            break;
            case FTNodeAsyncMsgId.MSGID_ASYNC_WAIT_FOR_RTC: {
                if (m_logger.isEnabledFor(Level.DEBUG))
                    m_logger.log(Level.DEBUG, "FTCallback:" + msg);
                setNodeState(myNode, FTNode.NODE_INACTIVE, false);
                nodeControlSpiPostDeActivate();
                nextPriorityNode = getNextPriorityNode(getNodeMap());
                // RTC is completed and this node should signal the waiting next high priority node to
                // start channels and become active
                if (nextPriorityNode.getNodeState() == FTNode.NODE_WAIT_FOR_ACTIVATION) {
                    if (m_logger.isEnabledFor(Level.INFO))
                        m_logger.log(Level.INFO, m_name + " is activating the primary node in cluster:" + m_cache.getName());
                    //setNodeState(nextPriorityNode, FTNode.NODE_START_CHANNELS);
                    setNodeState(nextPriorityNode, FTNode.NODE_WAIT_BEFORE_START, false);
                }
            }
            break;
            case FTNodeAsyncMsgId.MSGID_ASYNC_SUSPEND_RTC: {
                if (m_logger.isEnabledFor(Level.DEBUG))
                    m_logger.log(Level.DEBUG, "FTCallback:" + msg);
                FTNode allNodes[] = getAllNodes(getNodeMap());
                for (int j = 0; j < allNodes.length; j++) {
                    if (allNodes[j].getNodeState() == FTNode.NODE_CREATED) {
                        if (m_logger.isEnabledFor(Level.INFO))
                            m_logger.log(Level.INFO, m_name + " is signalling node: " + allNodes[j] + " to begin recovery");
                        setNodeState(allNodes[j], FTNode.NODE_BEGIN_RECOVERY, false);
                    }
                }
            }
            break;
            case FTNodeAsyncMsgId.MSGID_ASYNC_START_CHANNELS: {
                if (m_logger.isEnabledFor(Level.DEBUG))
                    m_logger.log(Level.DEBUG, "FTCallback:" + msg);
                setNodeState(myNode, FTNode.NODE_ACTIVE, false);
            }
            break;
            case FTNodeAsyncMsgId.MSGID_ASYNC_STOP_CHANNELS: {
                if (m_logger.isEnabledFor(Level.DEBUG))
                    m_logger.log(Level.DEBUG, "FTCallback:" + msg);
                setNodeState(myNode, FTNode.NODE_WAIT_RTC_COMPLETE, false);
            }
            break;
            case FTNodeAsyncMsgId.MSGID_ASYNC_WAIT_BEFORE_START: {
                if (m_logger.isEnabledFor(Level.DEBUG))
                    m_logger.log(Level.DEBUG, "FTCallback:" + msg);
                if (myNode.getNodeState() == FTNode.NODE_INACTIVE) {
                    // Failover happens here from secondary to primary
                    // therefore the secondary should wait for be.ft.failover.waitmilliseconds
                    // this will hold the coherence thread and might disrupt the cache
                    // the solution is to wait asynchronusly before start using a new state
                    synchronized (m_failoverGuard) {
                        m_logger.log(Level.DEBUG, "Waiting for " + m_failoverWaitTime + " milliseconds");
                        m_failoverGuard.wait(m_failoverWaitTime);
                    }

                } else if (myNode.getNodeState() == FTNode.NODE_WAIT_FOR_ACTIVATION) {
                    // Primary is waiting for activation , secondary should yield control
                    // therefore the secondary should wait for be.ft.failover.waitmilliseconds
                    // Failback happens here
                    synchronized (m_failbackGuard) {
                        m_logger.log(Level.DEBUG, "Waiting for " + m_failoverWaitTime + " milliseconds");
                        m_failbackGuard.wait(m_failbackWaitTime);
                    }

                } else if (myNode.getNodeState() == FTNode.NODE_WAIT_BEFORE_START) {
                    // Primary is waiting for activation , secondary should yield control
                    // therefore the secondary should wait for be.ft.failover.waitmilliseconds
                    // Failback happens here
                    synchronized (m_failbackGuard) {
                        m_failbackGuard.wait(m_failbackWaitTime);
                    }
                }
                setNodeState(myNode, FTNode.NODE_START_CHANNELS, false);
            }
            break;
            default:
                break;
        }
    }

    public FTAsyncMsgCallback getAsyncCallback() {
        return this;
    }
    // Asynchronous Callback interface end

    public FTNode getNextPriorityNode(FTNodeMap nodemap) {
        FTNode[] nodes = getAllNodes(nodemap);
        if (nodes.length == 1) {
            return nodes[0];
        }
        short highPriority = Short.MAX_VALUE;
        FTNode priorityNode = null;
        for (int i = 0; i < nodes.length; i++) {
            FTNode node = nodes[i];
            short priority = node.getPriority();
            if (priority < highPriority) {
                highPriority = priority;
                priorityNode = node;
            } else if (priority == highPriority) {
                if (node.getTimeCreated() < priorityNode.getTimeCreated()) {
                    priorityNode = node;
                } else if (node.getTimeCreated() == priorityNode.getTimeCreated()) {
                    if (node.getNodeCacheId() < priorityNode.getNodeCacheId()) {
                        priorityNode = node;
                    }

                }
            }
        }
        return priorityNode;
    }

    public FTNode getNextPriorityNode() {
        return getNextPriorityNode(null);
    }

    public void activate(FTNode node) {
        setNodeState(node, FTNode.NODE_ACTIVE, false);
    }

    public void deactivate(FTNode node) {
        setNodeState(node, FTNode.NODE_INACTIVE, false);
    }


    public String getCurrentNodeName() {
        //return (m_name+"@"+m_cache.getName()+":"+getCurrentNode().getNodeId());
        return getCurrentNode().getNodeName();
    }

    public String toString() {
        return getCurrentNodeName() + "[" + m_cache.getName() + ":" + getCurrentNode().getNodeCacheId() + "]";
    }


    public FTNode shutdown(FTNode node) {
        setNodeState(node, FTNode.NODE_SHUTDOWN, false);
        return node;
    }


    protected void setGlobalNodeState(FTNodeMap nodemap, FTNode node, int state) {
        if (state == FTNode.NODE_ACTIVE) {
            nodemap.activeNodeGUID = node.getNodeGUID();
            node.setPrimary(true);
            if (myNodeGUID.equals(node.getNodeGUID())) {
                setPrimaryFileLock();
            }
        } else {
            if (myNodeGUID.equals(node.getNodeGUID())) {
                releasePrimaryFileLock();
            }
        }
        if (state == FTNode.NODE_INACTIVE)
            node.setPrimary(false);
        if (state == FTNode.NODE_SUSPEND_RTC && nodemap.lockNodeGUID == null)
            nodemap.lockNodeGUID = myNodeGUID;
        if (state == FTNode.NODE_ACTIVATE_RTC && nodemap.lockNodeGUID != null)
            nodemap.lockNodeGUID = null;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    // MemberListener interface

    protected MemberListener getClusterMemberListener() {
        return new FTClusterMemberListener();
    }

    protected class FTClusterMemberListener extends ClusterMemberListener {
        public void memberJoined(MemberEvent memberEvent) {
            super.memberJoined(memberEvent);
            String guid = memberEvent.getMember().getMemberName();
            if (null == guid || guid.length() == 0) {
                return;
            }
            if (!isLockingEnabled) {
                return;
            }
            if (guid.equals(myNodeGUID)) {
                // this node is joining the cluster back
                // check if the cluster has been lost earlier.
                // just add the node and send refresh notification to
                // other members.
                if (m_clusterLost) {
                    addUpdateNode(m_currentNode, false);
                } else {
                    throw new RuntimeException("Cannot add node " + myNodeGUID + " to the node map without a cluster disconnection.");
                }
            } else {
                // another node is joining the cluster
                // check if the node is in disconnected nodemap
                // and then remove it from the map.
                synchronized (disconnectedNodeMap) {
                    if (disconnectedNodeMap.containsKey(guid)) {
                        disconnectedNodeMap.remove(guid);
                    }
                }
            }
        }

        public void memberLeaving(MemberEvent memberEvent) {
            super.memberLeaving(memberEvent);
        }

        public void memberLeft(MemberEvent memberEvent) {
            super.memberLeft(memberEvent);
            String guid = memberEvent.getMember().getMemberName();
            String uuid = memberEvent.getMember().getUid().toString();
            if (null == guid || guid.length() == 0) {
                return;
            }
            if (!isLockingEnabled) {
                removeNode((FTNode) getNodeMap().get(guid), uuid);
                return;
            }
            if (guid.equals(myNodeGUID)) {
                // if this node is leaving the cluster
                FTNode primaryNode = getPrimaryNode();
                if (myNodeGUID.equals(primaryNode.getNodeGUID())) {
                    // if this node is primary and it has lost the cluster
                    // then it should keep running and hold the lock.
                    if (m_primaryFileLock != null) {
                        if (isValidPrimaryFileLock()) {
                            m_logger.log(Level.INFO, "Primary Node is holding the exclusive file lock.");
                            // this should prevent the secondary nodes from getting a false notification
                            // whether this node has crashed. this primary node should now wait for auto
                            // cluster restart.

                        } else {
                            m_logger.log(Level.ERROR, "Primary Node file lock is present but not in locked state.");
                        }
                    } else {
                        m_logger.log(Level.ERROR, "Primary Node should hold the exclusive file lock.");
                    }
                    // set a boolean flag that cluster has been lost
                    m_clusterLost = true;

                } else {
                    // this node is secondary and it has lost the cluster
                    // it should wait for auto cluster restart. During this
                    // time other nodes may remove this node from their local
                    // copy of the nodemap.This node should not change its own
                    // node state and should not add itself to the local
                    // disconnected nodemap

                    // set a boolean flag that cluster has been lost
                    m_clusterLost = true;

                }

            } else {
                // if another node is leaving the cluster
                FTNode primaryNode = getPrimaryNode();
                String primaryGUID = null;
                if (null != primaryNode) {
                    primaryGUID = primaryNode.getNodeGUID();
                } else {
                    FTNodeMap nodemap = getNodeMap();
                    primaryGUID = nodemap.activeNodeGUID;
                }
                if (primaryGUID.equals(guid)) {
                    // primary node left the cluster
                    if (isPrimaryFileLocked()) {
                        // Primary is still running
                        //  no need to elect a primary.
                    } else {
                        // primary node is really gone
                        // time to elect primary. remove the lost primary.
                        synchronized (disconnectedNodeMap) {
                            if (!disconnectedNodeMap.containsKey(guid)) {
                                disconnectedNodeMap.put(guid, getNodeMap().get(guid));
                            }
                        }
                        removeNode((FTNode) getNodeMap().get(guid), uuid);
                    }
                } else {
                    // secondary node has left the cluster
                    // it is safe to remove the node from the nodemap
                    synchronized (disconnectedNodeMap) {
                        if (!disconnectedNodeMap.containsKey(guid)) {
                            disconnectedNodeMap.put(guid, getNodeMap().get(guid));
                        }
                    }
                    removeNode((FTNode) getNodeMap().get(guid), uuid);
                }
            }
        }
    }

    // NodeMap events start

    protected MapListener getNodeMapListener() {
        return new FTNodeMapListener();
    }

    protected class FTNodeMapListener extends NodeMapListener {

        public FTNodeMapListener() {
        }

        public void entryInserted(MapEvent mapEvent) {
            // do nothing
        }

        public void entryUpdated(MapEvent mapEvent) {
            try {
                FTNodeMap nodemap = (FTNodeMap) mapEvent.getNewValue();
                FTNode myNode = (FTNode) nodemap.get(myNodeGUID);
                FTNode nextPriorityNode;
                boolean bypassRTClock = Boolean.valueOf(System.getProperty("be.ft.bypass.rtc.lock", "true")).booleanValue();
                if (m_logger.isEnabledFor(Level.DEBUG))
                    m_logger.log(Level.DEBUG, "entryUpdated:" + nodemap);
                // this is to stop handling notifications that start coming
                // before this node event joined the nodemap
                if (myNode == null)
                    return;
                switch (nodemap.nodeActionPerformed) {
                    case FTNodeMap.NODE_ADDED: {
                        if (nodemap.lastChangedNodeGUID.equals(myNodeGUID)) {
                            nodeControlSpiInit();
                            if (myNode.getNodeState() == FTNode.NODE_CREATED) {
                                getNodeController().nodeStarted();
                                if (nodemap.size() == 1) {
                                    setNodeState(myNode, FTNode.NODE_BEGIN_RECOVERY, false);

                                } else if (nodemap.size() > 1) {
                                    FTNode primaryNode = getPrimaryNode();

                                    if (primaryNode != null) {
                                        if (m_logger.isEnabledFor(Level.DEBUG)) {
                                            m_logger.log(Level.DEBUG, m_name + " is Signalling primary node id:" + primaryNode.getNodeCacheId() + " to suspend RTC.");
                                        }
                                        // check primary node state to see if another secondary
                                        // node already started the RTC locking process
                                        if (primaryNode.getNodeState() == FTNode.NODE_SUSPEND_RTC) {
                                            // it means another secondary has started recovery so this
                                            // secondary node should also start recover
                                            setNodeState(myNode, FTNode.NODE_BEGIN_RECOVERY, false);
                                        }
                                        // if the primary node is activating from RTC lock then this
                                        // secondary should wait for the primary to become active again
                                        // it should update its node state to  NODE_CREATED
                                        if (primaryNode.getNodeState() == FTNode.NODE_ACTIVATE_RTC) {

                                        }
                                        // if primary is active or activating then start the RTC locking process
                                        if (primaryNode.getNodeState() == FTNode.NODE_ACTIVE ||
                                                primaryNode.getNodeState() == FTNode.NODE_ACTIVATE_RTC) {
                                            // if primary node found then ask it to suspend rtc
                                            if (!bypassRTClock) {
                                                // if primary node found then ask it to suspend rtc
                                                setNodeState(primaryNode, FTNode.NODE_SUSPEND_RTC, false);
                                            } else {
                                                // bypassed RTC suspend hence this new node should go to recovery
                                                setNodeState(myNode, FTNode.NODE_BEGIN_RECOVERY, false);
                                            }
                                        }
                                    } else {
                                        // it is likely that the primary is still starting and
                                        // it wont be known as primary until it is active.
                                        // therefore join the cache and the next high priority node
                                        // will become primary

                                        setNodeState(myNode, FTNode.NODE_JOINED_CACHE, false);
                                    }
                                }
                            }
                        } else {
                            // Another node added
                            if (m_logger.isEnabledFor(Level.INFO)) {
                                m_logger.log(Level.INFO, "New Node Added : " + getLastChangedNode());
                            }
                        }
                    }
                    break;
                    case FTNodeMap.NODE_UPDATED: {
                        // The node updated was my node
                        if (nodemap.lastChangedNodeGUID.equals(myNodeGUID)) {
                            switch (myNode.getNodeState()) {
                                case FTNode.NODE_CREATED:
                                    // it means that the primary is in state NODE_ACTIVATE_RTC but not active
                                    // and a secondary node who joined did not start recovery.
                                    FTNode primaryNode = getPrimaryNode();
                                    if (primaryNode != null) {
                                        if (primaryNode.getNodeState() == FTNode.NODE_ACTIVE) {
                                            if (!bypassRTClock) {
                                                // if primary node found then ask it to suspend rtc
                                                setNodeState(primaryNode, FTNode.NODE_SUSPEND_RTC, false);
                                            } else {
                                                FTNode allNodes[] = getAllNodes(nodemap);
                                                for (int j = 0; j < allNodes.length; j++) {
                                                    if (allNodes[j].getNodeState() == FTNode.NODE_CREATED) {
                                                        if (m_logger.isEnabledFor(Level.INFO))
                                                            m_logger.log(Level.INFO, m_name + " is signalling node: " + allNodes[j] + " to begin recovery");
                                                        setNodeState(allNodes[j], FTNode.NODE_BEGIN_RECOVERY, false);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    break;
                                case FTNode.NODE_BEGIN_RECOVERY:
                                    if (m_logger.isEnabledFor(Level.INFO))
                                        m_logger.log(Level.INFO, m_name + " is starting recovery from cache");
                                    setNodeState(myNode, FTNode.NODE_JOINED_CACHE, false);
                                    break;
                                case FTNode.NODE_END_RECOVERY:
                                    if (m_logger.isEnabledFor(Level.INFO))
                                        m_logger.log(Level.INFO, m_name + " has finished recovery from cache");
                                    if (nodemap.size() > 1) {
                                        boolean activatedOnce = false;
                                        primaryNode = getPrimaryNode();
                                        // There is a primary node and it is suspended so it must be activated
                                        // because recovery is complete
                                        if (primaryNode != null && primaryNode.getNodeState() == FTNode.NODE_SUSPEND_RTC) {
                                            setNodeState(primaryNode, FTNode.NODE_ACTIVATE_RTC, false);
                                            activatedOnce = true;
                                        }
                                        // if many secondary nodes join very quickly it is likely that one of them
                                        // notify the primary to join state NODE_ACTIVATE_RTC where it will tend
                                        // to stay if not nudged forward only once by each new node
                                        if (activatedOnce == false && primaryNode != null && primaryNode.getNodeState() == FTNode.NODE_ACTIVATE_RTC)
                                            setNodeState(primaryNode, FTNode.NODE_ACTIVATE_RTC, false);
                                    }
                                    if (nodemap.size() == 1) {
                                        // If I am the only node then start channels
                                        setNodeState(myNode, FTNode.NODE_START_CHANNELS, false);
                                    } else {
                                        nextPriorityNode = getNextPriorityNode(nodemap);
                                        if (nextPriorityNode.getNodeGUID().equals(myNodeGUID)) {
                                            // Check if there is another primary node already active
                                            // only then set this high priority node to wait for activation
                                            primaryNode = getPrimaryNode();
                                            if (primaryNode == null) {
                                                // if there is no primary node then this high  priority node should become primary
                                                setNodeState(myNode, FTNode.NODE_START_CHANNELS, false);
                                            }
                                            // There is a primary active or activating node and this higher priority node should become primary in failback.
                                            // Therefore wait for activation
                                            if (primaryNode != null &&
                                                    (primaryNode.getNodeState() == FTNode.NODE_ACTIVATE_RTC ||
                                                            primaryNode.getNodeState() == FTNode.NODE_SUSPEND_RTC ||
                                                            primaryNode.getNodeState() == FTNode.NODE_ACTIVE)) {
                                                if (m_logger.isEnabledFor(Level.INFO))
                                                    m_logger.log(Level.INFO, m_name + " is waiting for activation to become primary node in cluster:" + m_cache.getName());
                                                setNodeState(myNode, FTNode.NODE_WAIT_FOR_ACTIVATION, false);
                                            }

                                        } else {
                                            // TODO Dont start listeners
                                            if (m_logger.isEnabledFor(Level.DEBUG))
                                                m_logger.log(Level.DEBUG, "FTNodeManager:Becoming secondary! Channels should not start");
                                            setNodeState(myNode, FTNode.NODE_INACTIVE, false);
                                        }

                                    }
                                    break;
                                case FTNode.NODE_JOINED_CACHE:
                                    getNodeController().initAll(); // initialize but dont start
                                    if (!(getNodeController() instanceof FTNodeAsyncControllerImpl))
                                        setNodeState(myNode, FTNode.NODE_END_RECOVERY, false);
                                    break;
                                case FTNode.NODE_SUSPEND_RTC:
                                    // Am I the Active Node
                                    if (isPrimary(myNode)) {
                                        if (m_logger.isEnabledFor(Level.INFO))
                                            m_logger.log(Level.INFO, m_name + " is suspending RTC cycles for new node recovery");
                                        //SuspendRTC till the new node recovers completely
                                        getNodeController().suspendRTC();
                                        m_logger.log(Level.DEBUG, "FTNodeManager:Locking WM");
                                        //setNodeState(getLastModifierNode(),FTNode.NODE_BEGIN_RECOVERY);
                                        if (!(getNodeController() instanceof FTNodeAsyncControllerImpl)) {
                                            FTNode allNodes[] = getAllNodes(nodemap);
                                            for (int j = 0; j < allNodes.length; j++) {
                                                if (allNodes[j].getNodeState() == FTNode.NODE_CREATED) {
                                                    if (m_logger.isEnabledFor(Level.INFO))
                                                        m_logger.log(Level.INFO, m_name + " is signalling node: " + allNodes[j] + " to begin recovery");
                                                    setNodeState(allNodes[j], FTNode.NODE_BEGIN_RECOVERY, false);
                                                }
                                            }
                                        }
                                    }
                                    break;
                                case FTNode.NODE_ACTIVATE_RTC:
                                    boolean secondaryRecoveryCompleted = true;
                                    // Check all nodes except this primary node if they have started recovery or joined the cache
                                    // to see their incomplete recovery status
                                    FTNode allNodes[] = getAllNodes(nodemap);
                                    for (int j = 0; j < allNodes.length; j++) {
                                        if (!myNode.getNodeGUID().equals((allNodes[j].getNodeGUID())) &&
                                                (allNodes[j].getNodeState() == FTNode.NODE_BEGIN_RECOVERY ||
                                                        allNodes[j].getNodeState() == FTNode.NODE_JOINED_CACHE)) {
                                            secondaryRecoveryCompleted = false;
                                            if (m_logger.isEnabledFor(Level.DEBUG) && !secondaryRecoveryCompleted)
                                                m_logger.log(Level.DEBUG, "FTNodeManager:Node " + allNodes[j] + " recovery is incomplete");
                                            break;
                                        }
                                    }
                                    if (m_logger.isEnabledFor(Level.DEBUG) && !secondaryRecoveryCompleted)
                                        m_logger.log(Level.DEBUG, "FTNodeManager:All secondary nodes have not completed recovery");
                                    if (secondaryRecoveryCompleted) {
                                        if (m_logger.isEnabledFor(Level.DEBUG))
                                            m_logger.log(Level.DEBUG, "FTNodeManager:All secondary nodes completed recovery");
                                        if (m_logger.isEnabledFor(Level.INFO))
                                            m_logger.log(Level.INFO, m_name + " is activating RTC cycles after new node recovery");
                                        getNodeController().activateRTC();
                                        m_logger.log(Level.DEBUG, "FTNodeManager:ULocking WM");

                                        setNodeState(myNode, FTNode.NODE_ACTIVE, false);
                                    }
                                    break;
                                case FTNode.NODE_STOP_CHANNELS:
                                    if (nodeControlSpiPreDeActivate()) {
                                        // TODO Call RSP to Stop channels
                                        if (m_logger.isEnabledFor(Level.DEBUG))
                                            m_logger.log(Level.DEBUG, "FTNodeManager:We have to stop channels here!");
                                        if (m_logger.isEnabledFor(Level.INFO))
                                            m_logger.log(Level.INFO, m_name + " is stopping channels in cluster:" + m_cache.getName());
                                        getNodeController().stopChannels();
                                        if (!(getNodeController() instanceof FTNodeAsyncControllerImpl))
                                            setNodeState(myNode, FTNode.NODE_WAIT_RTC_COMPLETE, false);
                                    } else {
                                        if (m_logger.isEnabledFor(Level.INFO))
                                            m_logger.log(Level.INFO, m_name + " is being forced to become primary  by Node Control SPI");
                                        //setNodeState(myNode, FTNode.NODE_ACTIVE, false);
                                        rollbackNodeState(myNode);
                                    }
                                    break;
                                case FTNode.NODE_WAIT_RTC_COMPLETE:
                                    //TODO Call RSP to wait for rule cycles
                                    if (m_logger.isEnabledFor(Level.DEBUG))
                                        m_logger.log(Level.DEBUG, "FTNodeManager:We have to wait here for rule cycles to complete");
                                    if (m_logger.isEnabledFor(Level.INFO))
                                        m_logger.log(Level.INFO, m_name + "is finishing run to complete cycles in cluster:" + m_cache.getName());
                                    getNodeController().waitForRuleCycles();
                                    if (!(getNodeController() instanceof FTNodeAsyncControllerImpl)) {
                                        setNodeState(myNode, FTNode.NODE_INACTIVE, false);
                                        nodeControlSpiPostDeActivate();
                                        nextPriorityNode = getNextPriorityNode(nodemap);
                                        // RTC is completed and this node should signal the waiting next high priority node to
                                        // start channels and become active
                                        if (nextPriorityNode.getNodeState() == FTNode.NODE_WAIT_FOR_ACTIVATION) {
                                            if (m_logger.isEnabledFor(Level.INFO))
                                                m_logger.log(Level.INFO, m_name + " is activating the primary node in cluster:" + m_cache.getName());
                                            setNodeState(nextPriorityNode, FTNode.NODE_START_CHANNELS, false);
                                        }
                                    }
                                    break;
                                case FTNode.NODE_WAIT_FOR_ACTIVATION: {
                                    if (m_logger.isEnabledFor(Level.DEBUG))
                                        m_logger.log(Level.DEBUG, "FTNodeManager:Node Waiting for Activation");
                                    getNodeController().waitForActivation();
                                }
                                break;
                                case FTNode.NODE_START_CHANNELS:
                                    if (m_logger.isEnabledFor(Level.INFO))
                                        m_logger.log(Level.INFO, m_name + " is starting channels in cluster:" + m_cache.getName());
                                    // TODO Call RSP to start channels
                                    if (nodeControlSpiPreActivate()) {
                                        if (m_logger.isEnabledFor(Level.DEBUG))
                                            if (nodemap.lastModifierGUID.equals(myNode.getNodeGUID()))
                                                m_logger.log(Level.DEBUG, "FTNodeManager:Self notified: We have to start channels here!");
                                            else
                                                m_logger.log(Level.DEBUG, "FTNodeManager:Remote notified: We have to start channels here!");
                                        getNodeController().startChannels();
                                        if (!(getNodeController() instanceof FTNodeAsyncControllerImpl)) {
                                            setNodeState(myNode, FTNode.NODE_ACTIVE, false);
                                        }
                                    } else {
                                        deactivatedBySPI = true;
                                        if (m_logger.isEnabledFor(Level.INFO))
                                            m_logger.log(Level.INFO, m_name + " is being forced to become secondary by Node Control SPI");
                                        //setNodeState(myNode, FTNode.NODE_INACTIVE, false);
                                        rollbackNodeState(myNode);
                                    }
                                    break;
                                case FTNode.NODE_INACTIVE:
                                    if (deactivatedBySPI) {
                                        deactivatedBySPI = false;
                                        break;
                                    }
                                    nextPriorityNode = getNextPriorityNode(nodemap);
                                    if (nextPriorityNode.getNodeGUID().equals(myNodeGUID)) {
                                        if (getNodeController() instanceof FTNodeAsyncControllerImpl) {
                                            setNodeState(myNode, FTNode.NODE_WAIT_BEFORE_START, false);
                                        } else {
                                            setNodeState(myNode, FTNode.NODE_START_CHANNELS, false);
                                        }
                                    } else {
                                        getNodeController().setInactive();
                                    }
                                    if (m_logger.isEnabledFor(Level.INFO)) {

                                        FTNode[] cnodes = getAllNodes();
                                        m_logger.log(Level.INFO, "Total number of nodes in cluster " + m_cache.getName() + " = " + cnodes.length);
                                        for (int i = 0; i < cnodes.length; i++) {
                                            m_logger.log(Level.INFO, "[" + i + "]" + cnodes[i]);
                                        }
                                        m_logger.log(Level.INFO, m_name + " is a secondary node in cluster:" + m_cache.getName());
                                    }
                                    break;
                                case FTNode.NODE_ACTIVE:
                                    nextPriorityNode = getNextPriorityNode(nodemap);
                                    nodeControlSpiPostActivate();
                                    // before becoming active check if the next priority node is some other node and
                                    // that node is waiting for activation
                                    if (!nextPriorityNode.getNodeGUID().equals(myNodeGUID)) {
                                        if (nextPriorityNode.getNodeState() == FTNode.NODE_WAIT_FOR_ACTIVATION) {
                                            setNodeState(myNode, FTNode.NODE_STOP_CHANNELS, false);
                                        }
                                    }
                                    if (m_logger.isEnabledFor(Level.INFO)) {

                                        FTNode[] cnodes = getAllNodes();
                                        m_logger.log(Level.INFO, "Total number of nodes in cluster " + m_cache.getName() + " = " + cnodes.length);
                                        for (int i = 0; i < cnodes.length; i++) {
                                            m_logger.log(Level.INFO, "[" + i + "]" + cnodes[i]);
                                        }
                                        m_logger.log(Level.INFO, m_name + " is the primary node in cluster:" + m_cache.getName());
                                    }
                                    break;
                                case FTNode.NODE_SHUTDOWN:
                                    if (m_logger.isEnabledFor(Level.INFO))
                                        m_logger.log(Level.INFO, m_name + " is shutting down in cluster:" + m_cache.getName());
                                    getNodeController().shutdown();
                                    break;
                                case FTNode.NODE_WAIT_BEFORE_START: {
                                    if (getNodeController() instanceof FTNodeAsyncControllerImpl) {
                                        getNodeController().waitBeforeStart();
                                    }
                                }
                                break;
                            } // end switch
                        } else {   // Node updated was some other node
                            if (myNode != null && myNode.getNodeState() == FTNode.NODE_ACTIVE) {
                                // If this node is the active node and some other node state got updated
                                // the always check if the next priority node is waiting for activation
                                // if it is waiting then this node should stop channels.
                                nextPriorityNode = getNextPriorityNode(nodemap);
                                if (!nextPriorityNode.getNodeGUID().equals(myNodeGUID)) {
                                    if (nextPriorityNode.getNodeState() == FTNode.NODE_WAIT_FOR_ACTIVATION) {
                                        setNodeState(myNode, FTNode.NODE_STOP_CHANNELS, false);
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case FTNodeMap.NODE_DELETED: {
                        // The primary node died so elect a new primary
                        if (nodemap.lastChangedNodeGUID.equals(nodemap.activeNodeGUID)) {
                            if (m_logger.isEnabledFor(Level.INFO))
                                m_logger.log(Level.INFO, "Primary node died in cluster:" + m_cache.getName());
                            if (myNode.getNodeState() == FTNode.NODE_BEGIN_RECOVERY) {
                                // If I have started recovery I need to know if I
                                // am the only node left after the primary node got deleted
                                if (getNodeMap().size() == 1) {
                                    throw new RuntimeException("FTNodeManager:The primary node from which the cache recovery was being made died");
                                }
                                // There are other node caches available so recovery possible

                            }

                            nextPriorityNode = getNextPriorityNode(nodemap);
                            if (nextPriorityNode.getNodeGUID().equals(myNodeGUID)) {
                                myNode = nextPriorityNode;
                                if (myNode.getNodeState() == FTNode.NODE_INACTIVE) {
                                    // Failover happens here from secondary to primary
                                    // therefore the secondary should wait for be.ft.failover.waitmilliseconds
                                    // this will hold the coherence thread and might disrupt the cache
                                    // the solution is to wait asynchronusly before start using a new state
                                    if (getNodeController() instanceof FTNodeAsyncControllerImpl) {
                                        setNodeState(myNode, FTNode.NODE_WAIT_BEFORE_START, false);
                                    } else {
                                        setNodeState(myNode, FTNode.NODE_START_CHANNELS, false);
                                    }

                                } else if (myNode.getNodeState() == FTNode.NODE_WAIT_FOR_ACTIVATION) {
                                    // Primary is waiting for activation , secondary should yield control
                                    // therefore the secondary should wait for be.ft.failover.waitmilliseconds
                                    // Failback happens here
                                    if (getNodeController() instanceof FTNodeAsyncControllerImpl) {
                                        setNodeState(myNode, FTNode.NODE_WAIT_BEFORE_START, false);
                                    } else {
                                        setNodeState(myNode, FTNode.NODE_START_CHANNELS, false);
                                    }
                                }
                            }
                        } else {  // a secondary node died
                            // check if the dead secondary node locked the primary in the recovery notification
                            if (isPrimary(myNode) && nodemap.lastChangedNodeGUID.equals(nodemap.lockNodeGUID)) {
                                if (myNode.getNodeState() == FTNode.NODE_SUSPEND_RTC) {
                                    // I need to unlock the WM  since the secondary which locked my died
                                    m_logger.log(Level.DEBUG, "FTNodeManager:Unlocking WM since the locking secondary node died");
                                    setNodeState(myNode, FTNode.NODE_ACTIVATE_RTC, false);
                                }

                            }
                            // The secondary died leaving the next priority node in wait
                            nextPriorityNode = getNextPriorityNode(nodemap);
                            if (myNode.equals(nextPriorityNode) && myNode.getNodeState() == FTNode.NODE_WAIT_FOR_ACTIVATION) {
                                if (getNodeController() instanceof FTNodeAsyncControllerImpl) {
                                    setNodeState(myNode, FTNode.NODE_WAIT_BEFORE_START, false);
                                } else {
                                    setNodeState(myNode, FTNode.NODE_START_CHANNELS, false);
                                }
                            }

                        }
                    }
                    break;
                    case FTNodeMap.NODE_REFRESHED: {
                        m_logger.log(Level.DEBUG, "Node Refreshed :" + nodemap.get(nodemap.lastChangedNodeGUID));
                    }
                    break;
                }

            } catch (Exception e) {
                try {
                    if (myNodeGUID == null || myNodeState < FTNode.NODE_JOINED_CACHE) {
                        e.printStackTrace();
                        m_logger.log(Level.FATAL, "Fatal Error :" + e.getMessage());
                        System.exit(-1);
                    }
                    if (myNodeGUID != null && myNodeState >= FTNode.NODE_JOINED_CACHE) {
                        e.printStackTrace();
                        m_logger.log(Level.ERROR, "Engine " + m_name + "[Cluster:" + getClusterName() + "Node:" + getCurrentNode().getNodeCacheId() + "] recieved an Exception and it is shutting down.", e);
                        getNodeController().shutdown();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    m_logger.log(Level.FATAL, "Engine " + m_name + "[Cluster:" + getClusterName() + "Node:" + getCurrentNode().getNodeCacheId() + "] encountered a Fatal error and exiting", e1);
                    System.exit(-1);
                }
            }
        }


        public void entryDeleted(MapEvent mapEvent) {
            return;
        }

    }

    // NodeMap events end

    protected int getNodeInfoState(int ftNodeState) {
        switch (ftNodeState) {
            case FTNode.NODE_ACTIVE:
            case FTNode.NODE_SUSPEND_RTC:
            case FTNode.NODE_ACTIVATE_RTC:
                return NodeInfo.NODE_ACTIVE;

            case FTNode.NODE_WAIT_RTC_COMPLETE:
            case FTNode.NODE_STOP_CHANNELS:
                return NodeInfo.NODE_DEACTIVATING;

            case FTNode.NODE_CREATED:
            case FTNode.NODE_BEGIN_RECOVERY:
            case FTNode.NODE_END_RECOVERY:
            case FTNode.NODE_INACTIVE:
            case FTNode.NODE_JOINED_CACHE:
            case FTNode.NODE_WAIT_FOR_ACTIVATION:
                return NodeInfo.NODE_INACTIVE;

            case FTNode.NODE_START_CHANNELS:
                return NodeInfo.NODE_ACTIVATING;

            default:
                throw new RuntimeException("Invalid FTNodeState =" + ftNodeState);
        }
    }

    public void notify(ChangeEvent e) {
        //TODO: implement
    }


}
