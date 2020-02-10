package com.tibco.cep.runtime.service.ft.impl;

import com.tibco.cep.runtime.service.ft.FTAsyncQueueManager;
import com.tibco.cep.runtime.service.ft.FTNodeController;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 25, 2007
 * Time: 9:18:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class FTNodeAsyncControllerImpl implements FTNodeController {
    FTAsyncQueueManager m_qmgr;

    public FTNodeAsyncControllerImpl(FTAsyncQueueManager qmgr) {
        m_qmgr = qmgr;
    }

    public String getControllerName() {
        return getClass().getName();
    }

    public void nodeStarted() throws Exception {
        m_qmgr.asyncMessage(FTNodeAsyncControllerMsgs.MSG_ASYNC_NODE_STARTED);
    }

    public void initAll() throws Exception {
        m_qmgr.asyncMessage(FTNodeAsyncControllerMsgs.MSG_ASYNC_INIT_ALL);
    }

    public void initChannels() throws Exception {
        m_qmgr.asyncMessage(FTNodeAsyncControllerMsgs.MSG_ASYNC_INIT_CHANNELS);
    }

    public void setInactive() throws Exception {
        m_qmgr.asyncMessage(FTNodeAsyncControllerMsgs.MSG_ASYNC_SET_INACTIVE);
    }

    public void waitForActivation() throws Exception {
        m_qmgr.asyncMessage(FTNodeAsyncControllerMsgs.MSG_ASYNC_WAIT_FOR_ACTIVATION);
    }

    public void startChannels() throws Exception {
        m_qmgr.asyncMessage(FTNodeAsyncControllerMsgs.MSG_ASYNC_START_CHANNELS);
    }

    public void stopChannels() throws Exception {
        m_qmgr.asyncMessage(FTNodeAsyncControllerMsgs.MSG_ASYNC_STOP_CHANNELS);
    }

    public void waitForRuleCycles() throws Exception {
        m_qmgr.asyncMessage(FTNodeAsyncControllerMsgs.MSG_ASYNC_WAIT_FOR_RTC);
    }

    public void stopNode() throws Exception {
        m_qmgr.asyncMessage(FTNodeAsyncControllerMsgs.MSG_ASYNC_STOP_NODE);
    }

    public void initRuleSessions() throws Exception {
        m_qmgr.asyncMessage(FTNodeAsyncControllerMsgs.MSG_ASYNC_INIT_RULESESSION);
    }

    public void suspendRTC() throws Exception {
        m_qmgr.asyncMessage(FTNodeAsyncControllerMsgs.MSG_ASYNC_SUSPEND_RTC);
    }

    public void activateRTC() throws Exception {
        m_qmgr.asyncMessage(FTNodeAsyncControllerMsgs.MSG_ASYNC_ACTIVATE_RTC);
    }

    public byte[] getNodeArchiveDigest() throws Exception {
        //return new byte[0];
        return (byte[]) m_qmgr.syncMessage(FTNodeAsyncControllerMsgs.MSG_SYNC_GETDIGEST);
    }

    public void shutdown() throws Exception {
        m_qmgr.asyncMessage(FTNodeAsyncControllerMsgs.MSG_ASYNC_SHUTDOWN);
    }

    public void waitBeforeStart() throws Exception {
        m_qmgr.asyncMessage(FTNodeAsyncControllerMsgs.MSG_ASYNC_WAIT_BEFORE_START);
    }
}

