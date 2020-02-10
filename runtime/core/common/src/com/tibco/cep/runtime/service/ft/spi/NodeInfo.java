package com.tibco.cep.runtime.service.ft.spi;

import java.net.InetAddress;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Oct 4, 2006
 * Time: 4:42:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface NodeInfo {

    public static final int NODE_ACTIVATING = 1;
    public static final int NODE_ACTIVE = 2;
    public static final int NODE_DEACTIVATING = 3;
    public static final int NODE_INACTIVE = 4;

    int getState();

    String getNodeGUID();

    int getNodeCacheId();

    String getNodeCacheUid();

    int getMachineId();

    InetAddress getAddress();

    int getPort();

    long getJoinedTimestamp();

    int getPriority();
}
