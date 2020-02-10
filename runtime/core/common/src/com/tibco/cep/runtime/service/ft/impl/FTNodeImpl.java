package com.tibco.cep.runtime.service.ft.impl;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.Stack;

import com.tibco.cep.runtime.service.ft.FTNode;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Oct 1, 2006
 * Time: 12:48:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class FTNodeImpl implements FTNode, Serializable {
    static int stackSize = Integer.valueOf(System.getProperty("be.ft.node.history.size","5").trim()).intValue();

    private int nodeCacheId;
    private String nodeCacheUid;
    private String nodeGUID;
    private short priority;
    private int nodeState;
    private Stack nodeStateHistory;
    private long timeCreated;
    private InetAddress address;
    private int machineId;
    private String machineName;
    private int port;
    private boolean primary;
    private String clusterName;
    private String nodeName;
    private String processName;
    private String rackName;
    private String roleName;
    private String siteName;




    public FTNodeImpl(String _nodeGUID,
                      int _nodeId,
                      String _clusterName,
                      String _nodeUid,
                      short _priority,
                      long _timeCreated,
                      int _machineId,
                      InetAddress _address,
                      int _port,
                      String _nodeName,
                      String _processName,
                      String _rackName,
                      String _roleName,
                      String _siteName) {
        nodeGUID = _nodeGUID;
        nodeCacheId = _nodeId;
        nodeName = _nodeName;
        clusterName = _clusterName;
        nodeCacheUid = _nodeUid;
        priority = _priority;
        nodeState = FTNode.NODE_CREATED;
        nodeStateHistory = new Stack();
        pushNodeState(Integer.valueOf(nodeState));
        timeCreated = _timeCreated;
        machineId = _machineId;
        address = _address;
        port = _port;
        processName = _processName;
        rackName = _rackName;
        roleName = _roleName;
        siteName = _siteName;
    }

    private void pushNodeState(Integer nodestate) {
        nodeStateHistory.push(nodestate);
        if(nodeStateHistory.size() > stackSize) {
            int firstIndex =nodeStateHistory.lastIndexOf(nodeStateHistory.firstElement());
            nodeStateHistory.removeElementAt(firstIndex);
        }
    }

    public String getNodeName() {
        return nodeName;
    }

    public int getNodeCacheId() {
        return nodeCacheId;
    }

    public void setNodeCacheId(int nodeCacheId) {
        this.nodeCacheId = nodeCacheId;
    }

    public String getNodeCacheUid() {
        return nodeCacheUid;
    }

    public String getNodeGUID() {
        return nodeGUID;
    }

    public short getPriority() {
        return priority;
    }

    public int getNodeState() {
        return nodeState;
    }

    public void setNodeState(int state) {
        nodeState = state;
        pushNodeState(Integer.valueOf(state));

    }

    public int getLastNodeState() {
        return ((Integer)nodeStateHistory.peek()).intValue();
    }

    public int resetPreviousNodeState() {
        return ((Integer)nodeStateHistory.pop()).intValue();
    }

    public int[] getNodeStateHistory() {
        Integer[] history = (Integer[]) nodeStateHistory.toArray(new Integer[nodeStateHistory.size()]);
        int [] nodehistory = new int[history.length];
        int i =0;
        for(Iterator it = nodeStateHistory.iterator(); it.hasNext();){
            nodehistory[i]=((Integer)it.next()).intValue();
            i++;
        }
        return nodehistory;
    }

    private String getNodeStateHistoryStr() {
        int[] history = getNodeStateHistory();
        StringBuilder shistory = new StringBuilder();
        shistory.append("history{"+stackSize+"}=[");
        for(int i=history.length-1;i>0;i--) {
           shistory.append(FTNode.stateArray[history[i]]).append(",");
        }
        shistory.append("...]");
        return shistory.toString();
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getMachineId() {
        return machineId;
    }

	public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean _primary) {
        primary = _primary;
    }

    public String getClusterName() {
        return clusterName;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getProcessName() {
        return processName;
    }


    public String getRackName() {
        return rackName;
    }


    public String getRoleName() {
        return roleName;
    }


    public String getSiteName() {
        return siteName;
    }


    public boolean equals(Object obj) {
        if(obj instanceof FTNodeImpl) {
            FTNodeImpl node = (FTNodeImpl) obj;
            if(obj == this) return true;
            return node.nodeGUID.equals(this.nodeGUID);
        }
        return false;
    }


    public String toString() {
        //return "Node[ Id = " + nodeCacheId + "\r\n Uid = " + nodeGUID + "\r\n Priority = "+ priority + "\r\n Created at " + timeCreated + "\r\n State = " + FTNode.stateArray[nodeState]+" ]";
        //return ("Node[GUID="+nodeGUID+",ID="+nodeCacheId +",Priority="+priority +",State="+ FTNode.stateArray[nodeState]+","+getNodeStateHistoryStr()+"]");
        return ("Node[GUID="+nodeGUID+",ID="+nodeCacheId +",Priority="+priority +",State="+ FTNode.stateArray[nodeState]+"]");
    }

    public int hashCode() {
        return nodeGUID.hashCode();
    }
}
