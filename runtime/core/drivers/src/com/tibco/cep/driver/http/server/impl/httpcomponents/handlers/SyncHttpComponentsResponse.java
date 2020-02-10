package com.tibco.cep.driver.http.server.impl.httpcomponents.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;

import com.tibco.cep.driver.http.server.HttpChannelServerResponse;

/**
 * This class reads a synchronous HTTP response and adapts to a generic http
 * response  
 * @author vjavere
 *
 */
public class SyncHttpComponentsResponse implements HttpChannelServerResponse {

	/**
	 * Content Producer for a Response Writer to write the content on the HTTP Connection
	 * as and when it is available
	 * @author vjavere
	 *
	 */
	public class StringContentProducer implements ContentProducer {
		public void writeTo(OutputStream out) throws IOException {
			OutputStreamWriter writer = new OutputStreamWriter(out);
			StringBuffer strBuf = strWriter.getBuffer();
			char [] respBytes = new char [strBuf.length()] ;
			strWriter.getBuffer().getChars(0, strBuf.length(), respBytes, 0);
			writer.write(respBytes,0,respBytes.length);
			writer.flush();
		}
		public void writeTo(OutputStreamWriter out) throws IOException {
			writeTo(out);
		}
		
	}

	private HttpResponse response;
	private OutputStream outStream;
	private StringWriter strWriter;
	private ContentProducer contentProducer;
	private long contentLength;
	
	private String contentType;

	/**
	 * Initialize a Synchronous HTTP Response
	 * Creates a new writer, associates it with a content producer for writing on the 
	 * HTTP Connection
	 * This writer is set on a HTTP response entity
	 * @param response
	 */
	public SyncHttpComponentsResponse(HttpResponse response) {
		this.response = response;
		contentProducer = new StringContentProducer();
		strWriter = new StringWriter();
		EntityTemplate reponseEntity = new EntityTemplate(contentProducer);
		response.setEntity(reponseEntity);
		
	}
	
	public void addHeader(String key, String value) {
		response.addHeader(key, value);
	}

	public long getContentLength() {
		return response.getEntity().getContentLength();
	}

	public String getContentType() {
		return response.getEntity().getContentType().getValue();
	}

	public OutputStream getOutputStream() throws IOException {
		return outStream;
	}

	public HttpResponse getResponse() {
		return response;
	}

	public Writer getWriter() throws IOException {
		return strWriter;
	}

	public void setHeader(String key, String value) {
		response.setHeader(key, value);
	}


	public void setStatus(int statusCode) {

		response.setStatusCode(statusCode);
	}

	public void setStatus(int statusCode, String statusPhrase) {
		response.setStatusLine(response.getProtocolVersion(), statusCode,
				statusPhrase);
	}

    /**
     * Sends the response back, this method applies only in case of asynchronous HTTP response wherein the
     * response needs to be triggered explicitly
     */
    public void sendResponse() {
        //To change body of implemented methods use File | Settings | File Templates.
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