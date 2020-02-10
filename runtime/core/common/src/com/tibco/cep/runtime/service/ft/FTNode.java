package com.tibco.cep.runtime.service.ft;

import java.net.InetAddress;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Sep 29, 2006
 * Time: 4:50:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FTNode {
    /* Important : The chronological order of the states are important , don't change
    ** without understanding where the order is used
    */
    public static final int NODE_SHUTDOWN = 0;
    public static final int NODE_CREATED              = NODE_SHUTDOWN +1;
    public static final int NODE_BEGIN_RECOVERY       = NODE_CREATED +1;
    public static final int NODE_END_RECOVERY         = NODE_BEGIN_RECOVERY +1;
    public static final int NODE_JOINED_CACHE         = NODE_END_RECOVERY +1;
    public static final int NODE_INACTIVE             = NODE_JOINED_CACHE +1;
    public static final int NODE_WAIT_FOR_ACTIVATION  = NODE_INACTIVE + 1;
    public static final int NODE_WAIT_BEFORE_START    = NODE_WAIT_FOR_ACTIVATION +1;
    public static final int NODE_START_CHANNELS       = NODE_WAIT_BEFORE_START +1;
    public static final int NODE_ACTIVE               = NODE_START_CHANNELS + 1;
    public static final int NODE_SUSPEND_RTC          = NODE_ACTIVE + 1;
    public static final int NODE_ACTIVATE_RTC         = NODE_SUSPEND_RTC + 1;
    public static final int NODE_STOP_CHANNELS        = NODE_ACTIVATE_RTC + 1;
    public static final int NODE_WAIT_RTC_COMPLETE    = NODE_STOP_CHANNELS + 1;
    public static final int NODE_HD_START             = NODE_WAIT_RTC_COMPLETE + 1;
    public static final int NODE_HD_STOP_CHANNELS     = NODE_HD_START + 1;
    public static final int NODE_WAIT_HD_RTC_COMPLETE = NODE_HD_STOP_CHANNELS + 1;
    public static final int NODE_HD_SYNC              = NODE_WAIT_HD_RTC_COMPLETE + 1;
    public static final int NODE_HD_UPDATED_SESSION   = NODE_HD_SYNC + 1;
    public static final int NODE_HD_WAIT_UPD_SESS_PRI = NODE_HD_UPDATED_SESSION + 1;



    public static final String[] stateArray = {
           "NODE_SHUTDOWN",
           "NODE_CREATED",
           "NODE_BEGIN_RECOVERY",
           "NODE_END_RECOVERY",
           "NODE_JOINED_CACHE",
           "NODE_INACTIVE",
           "NODE_WAIT_FOR_ACTIVATION",
           "NODE_WAIT_BEFORE_START",
           "NODE_START_CHANNELS",
           "NODE_ACTIVE",
           "NODE_SUSPEND_RTC",
           "NODE_ACTIVATE_RTC",
           "NODE_STOP_CHANNELS",
           "NODE_WAIT_RTC_COMPLETE",
           "NODE_HD_START",
           "NODE_HD_STOP_CHANNELS",
           "NODE_WAIT_HD_RTC_COMPLETE",
           "NODE_HD_SYNC",
           "NODE_HD_UPDATED_SESSION",
           "NODE_HD_WAIT_UPD_SESS_PRI"
    };

    public String getNodeName();

    public int getNodeCacheId();

    public String getNodeGUID();

    public String getNodeCacheUid();

    public short getPriority();

    public int getNodeState();

    public void setNodeState(int state);

    public int getLastNodeState();

    public int[] getNodeStateHistory();

    public long getTimeCreated();

    public InetAddress getAddress();

    public int getPort();

    public int getMachineId();    

    public boolean isPrimary();

    public void setPrimary(boolean _primary);

    public String getClusterName();

    public String getMachineName();

    public String getProcessName();

    public String getRackName();

    public String getRoleName();

    public String getSiteName();

    int resetPreviousNodeState();
}
