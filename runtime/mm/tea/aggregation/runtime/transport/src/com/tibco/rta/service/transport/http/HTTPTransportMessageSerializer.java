package com.tibco.rta.service.transport.http;

import com.tibco.rta.service.transport.Message;
import com.tibco.rta.service.transport.MessageSerializer;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/11/12
 * Time: 5:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class HTTPTransportMessageSerializer implements MessageSerializer<HTTPMessageSerializationContext> {

   /**
     * @param toSerialize
     * @param context
     * @return
     */
    @Override
    public Object serialize(Message toSerialize, HTTPMessageSerializationContext context, boolean commit) throws Exception {
        AsyncContext asyncContext = context.getAsyncContext();
        HttpServletResponse servletResponse = (HttpServletResponse)asyncContext.getResponse();
        //properties can go as headers and payload as post
        Properties properties = toSerialize.getMessageProperties();
        Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String propertyName = (String)propertyNames.nextElement();
            //TODO Not sure whether this is safe
            servletResponse.addHeader(propertyName, (String)properties.get(propertyName));
        }
        //Check for payload
        Object payload = toSerialize.getPayload();
        if ((payload instanceof String) && !((String) payload).isEmpty()) {
            Writer writer = servletResponse.getWriter();
            writer.write((String) payload);
        } else if ((payload instanceof byte[])) {
            OutputStream outputStream = servletResponse.getOutputStream();
            outputStream.write((byte[]) payload);
        }
        servletResponse.flushBuffer();
        //complete
        if (commit) {
            asyncContext.complete();
        }
        return null;
    }
}
