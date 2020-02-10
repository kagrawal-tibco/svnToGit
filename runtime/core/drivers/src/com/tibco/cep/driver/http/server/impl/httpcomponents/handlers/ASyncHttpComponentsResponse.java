package com.tibco.cep.driver.http.server.impl.httpcomponents.handlers;

import static com.tibco.cep.driver.http.HttpChannelConstants.UTF8_ENCODING;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.nio.entity.NByteArrayEntity;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.nio.protocol.NHttpResponseTrigger;

import com.tibco.cep.driver.http.server.HttpChannelServerResponse;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * This class reads an asynchronous HTTP response and adapts to a generic http
 * response
 *
 * @author vjavere
 */
public class ASyncHttpComponentsResponse implements HttpChannelServerResponse {

    private static final Logger LOGGER = com.tibco.cep.kernel.service.logging.LogManagerFactory.getLogManager().getLogger(ASyncHttpComponentsResponse.class);

    private HttpResponse response;

    private StringWriter strWriter;

    private ByteArrayOutputStream byteArrayOutputStream;

    // Callback handle for the response trigger for submitting the response
    private NHttpResponseTrigger callBackHandle;

    /**
     * Initializes the HTTP response, the trigger for sending the callback response and
     * a response writer
     */
    public ASyncHttpComponentsResponse(HttpResponse response, NHttpResponseTrigger callBackHandle) {
        this.response = response;
        this.callBackHandle = callBackHandle;
        strWriter = new StringWriter();
        byteArrayOutputStream = new ByteArrayOutputStream();
    }

    public void addHeader(String key, String value) {
        response.addHeader(key, value);
    }

    public Writer getWriter() throws IOException {
        return strWriter;
    }

    public OutputStream getOutputStream() throws IOException {
        return byteArrayOutputStream;
    }

    public void sendResponse() {
        sendResponseForWriter();
        sendResponseForOutputStream();
        callBackHandle.submitResponse(response);
    }

    /**
     * Associates the NIO based String Entity with the response and sends it back on the HTTP
     * Connection. This is called in the writer's close() method, when the response is finished writing
     */
    private void sendResponseForWriter() {
        try {
            if (strWriter.getBuffer() != null && strWriter.getBuffer().length() > 0) {
                NStringEntity responseEntity = new NStringEntity(strWriter.getBuffer().toString(), UTF8_ENCODING);
                response.setEntity(responseEntity);
                strWriter.flush();
            }
        } catch (Exception ex) {
            LOGGER.log(com.tibco.cep.kernel.service.logging.Level.ERROR, ex, null);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Associates the NIO based Byte Array Entity with the response and sends it back on the HTTP
     * Connection. This is called in sendResponse() method, when the response is finished writing
     */
    private void sendResponseForOutputStream() {
        try {
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            if (byteArray != null && byteArray.length > 0) {
                NByteArrayEntity byteArrayEntity = new NByteArrayEntity(byteArray);
                response.setEntity(byteArrayEntity);
            }
        } catch (Exception ex) {
            LOGGER.log(com.tibco.cep.kernel.service.logging.Level.ERROR, ex, null);
            throw new RuntimeException(ex);
        }
    }


    public void setHeader(String key, String value) {
        response.setHeader(key, value);
    }

    public void setStatus(int statusCode) {
        response.setStatusCode(statusCode);
    }

    public void setStatus(int statusCode, String statusPhrase) {
        response.setStatusLine(response.getProtocolVersion(), statusCode, statusPhrase);
    }

    /**
     * sets the entity on the response
     *
     * @param entity
     */
    public void setEntity(HttpEntity entity) {
        response.setEntity(entity);
    }

	@Override
	public void setContentLength(int length) {
		//do nothing
		//Modified by Anand on 03/04/2011 to fix BE-8262 
	}

	@Override
	public void setContentType(String contentType) {
		//do nothing
		//Modified by Anand on 03/04/2011 to fix BE-8262
	}
    
}