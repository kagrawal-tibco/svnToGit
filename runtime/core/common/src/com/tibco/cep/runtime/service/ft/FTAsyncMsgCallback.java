package com.tibco.cep.runtime.service.ft;

import com.tibco.cep.runtime.service.ft.impl.FTNodeAsyncControllerMsgs;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Apr 16, 2007
 * Time: 12:04:34 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FTAsyncMsgCallback {
    public void ftCallback(FTNodeAsyncControllerMsgs msg) throws Exception;
}
