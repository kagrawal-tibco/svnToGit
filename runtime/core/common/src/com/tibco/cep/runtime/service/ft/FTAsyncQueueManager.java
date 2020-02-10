package com.tibco.cep.runtime.service.ft;

import com.tibco.cep.runtime.service.ft.impl.FTNodeAsyncControllerMsgs;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Mar 25, 2007
 * Time: 9:40:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FTAsyncQueueManager {

    void asyncMessage(FTNodeAsyncControllerMsgs msg);

    Object syncMessage(FTNodeAsyncControllerMsgs msg) throws Exception;

    public void start();

    public void stop();

    public void addNodeController(String name, FTNodeController  controller);

    public FTNodeController removeNodeController(String name);

    public void notifyControllers(FTNodeAsyncControllerMsgs msg) throws Exception;

    public FTNodeController[] getNodeControllers();

    void setAsyncCallback(FTAsyncMsgCallback callback);
}
