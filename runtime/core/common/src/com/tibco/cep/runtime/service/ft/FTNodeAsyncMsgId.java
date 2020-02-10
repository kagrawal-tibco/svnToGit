package com.tibco.cep.runtime.service.ft;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 25, 2007
 * Time: 9:15:40 PM
 * To change this template use File | Settings | File Templates.
 */
//////////////////////////////////////////////////////////////////////////////////////////
// FTNodeAsyncMsgs
public interface FTNodeAsyncMsgId {
    public static final int MSGID_ASYNC_NODE_STARTED    = 0;
    public static final int MSGID_ASYNC_INIT_ALL          = 1;
    public static final int MSGID_ASYNC_INIT_CHANNELS     = 2;
    public static final int MSGID_ASYNC_SET_INACTIVE      = 3;
    public static final int MSGID_ASYNC_START_CHANNELS    = 4;
    public static final int MSGID_ASYNC_STOP_CHANNELS     = 5;
    public static final int MSGID_ASYNC_WAIT_FOR_RTC      = 6;
    public static final int MSGID_ASYNC_STOP_NODE         = 7;
    public static final int MSGID_ASYNC_INIT_RULESESSION  = 8;
    public static final int MSGID_ASYNC_SUSPEND_RTC       = 9;
    public static final int MSGID_ASYNC_ACTIVATE_RTC      = 10;
    public static final int MSGID_ASYNC_SHUTDOWN          = 11;
    public static final int MSGID_SYNC_GETDIGEST          = 12;
    public static final int MSGID_WAIT_FOR_ACTIVATION     = 13;
    public static final int MSGID_ASYNC_WAIT_BEFORE_START = 14;
}
