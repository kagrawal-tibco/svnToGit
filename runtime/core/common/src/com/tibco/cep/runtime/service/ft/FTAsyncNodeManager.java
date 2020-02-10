package com.tibco.cep.runtime.service.ft;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Apr 16, 2007
 * Time: 3:33:13 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FTAsyncNodeManager extends FTNodeManager,FTAsyncMsgCallback{
    public FTAsyncMsgCallback getAsyncCallback();
}
