package com.tibco.rta.service.cluster.event;

import java.util.EventObject;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 25/7/13
 * Time: 10:17 AM
 * To change this template use File | Settings | File Templates.
 */
public class GMPLifecycleEvent extends EventObject {

    /**
     * Manager is in starting state.
     */
    public static final int MEMBER_JOIN_EVENT = 1 << 1;

    /**
     * Manager is in accept mode.
     */
    public static final int MEMBER_LEFT_EVENT = 1 << 2;

    /**
     * Manager up but not accepting tasks.
     */
    public static final int QUORUM_COMPLETE_EVENT = 1 << 3;

    /**
     * Manager closed because the owning session closed.
     */
    public static final int MEMBER_ELECTED = 1 << 4;

    public GMPLifecycleEvent(Object source) {
        super(source);
    }
}
