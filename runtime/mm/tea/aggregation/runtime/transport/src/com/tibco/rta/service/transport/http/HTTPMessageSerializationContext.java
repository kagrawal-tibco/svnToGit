package com.tibco.rta.service.transport.http;

import com.tibco.rta.service.transport.MessageSerializationContext;

import javax.servlet.AsyncContext;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/11/12
 * Time: 5:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class HTTPMessageSerializationContext implements MessageSerializationContext {

    private AsyncContext asyncContext;

    public HTTPMessageSerializationContext(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    public AsyncContext getAsyncContext() {
        return asyncContext;
    }
}
