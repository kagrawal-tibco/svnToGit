package com.tibco.cep.driver.http.server.impl.httpcomponents.handlers;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.nio.DefaultNHttpServerConnection;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.Parameters;

import com.tibco.cep.driver.http.server.HttpChannelServerRequest;

/**
 * This class reads an synchronous HTTP request and adapts to a generic http
 * request  
 * @author vjavere
 *
 */
public class SyncHttpComponentsRequest implements HttpChannelServerRequest {
	private HttpRequest request;

	private byte[] data; // post data

	private Map<String, String[]> parametersMap;

	private HttpContext context;

	private String requestUri;

	private String queryString;

	private String hostName;

	private boolean secure;

	private String scheme;

	private String characterEncoding;

	private RequestUriEncoding uriEncodingHelper;

	/**
	 * This method fetches the context, separates the request URI and query string   
	 * and retrieves the request body of a Synchronous HTTP Request 
	 * @param request
	 * @param context
	 * @param uriEncodingHelper
	 * @throws IOException
	 */
	public SyncHttpComponentsRequest(HttpRequest request, HttpContext context,
			RequestUriEncoding uriEncodingHelper) throws IOException {
		this.request = request;
		this.context = context;
		this.uriEncodingHelper = uriEncodingHelper;

		separateUriAndQuery();
		if (request instanceof HttpEntityEnclosingRequest) {
        	BasicHttpEntity entity = (BasicHttpEntity)((HttpEntityEnclosingRequest) request).getEntity();
			data = EntityUtils.toByteArray(entity);
			if (entity.getContentEncoding() != null) {
				characterEncoding = entity.getContentEncoding().getValue();
			}
        	entity.consumeContent();

		}

	}

	public Object getAttribute(String attributeName) {
		return context.getAttribute(attributeName);
	}

	public String getCharacterEncoding() {

		return characterEncoding;
	}

	public int getContentLength() {
		if (request instanceof HttpEntityEnclosingRequest) {
			HttpEntity entity = ((HttpEntityEnclosingRequest) request)
					.getEntity();

			return (int) entity.getContentLength();
		}
		return 0;
	}

	public String getContentType() {
		if (request instanceof HttpEntityEnclosingRequest) {
			Header contentType = ((HttpEntityEnclosingRequest) request).getEntity()
					                .getContentType();
            if (contentType != null) {
                return contentType.getValue();
            }
        }
		return null;
	}

	public String getHeader(String headerName) {
		Header header = request.getFirstHeader(headerName);
		if(header != null)
			return header.getValue();
		else
			return null;
	}

	public Iterator getHeaderNames() {
		Iterator itr = request.headerIterator();
		HashSet headerNames = new HashSet();
		while (itr.hasNext()) {
			Header header = (Header) itr.next();
			headerNames.add(header.getName().toLowerCase());
		}
		return headerNames.iterator();
	}

	public Iterator getHeaders(String headerName) {
		Header[] headers = request.getHeaders(headerName);
		List headerValues = new ArrayList();
		for (int i = 0; i < headers.length; i++) {
			headerValues.add(headers[i].getValue());
		}
		return headerValues.iterator();

	}

	public InputStream getInputStream() throws IOException {

		return new BufferedInputStream((new ByteArrayInputStream(data)));

	}

	public String getMethod() {
		return request.getRequestLine().getMethod();
	}
	
	public Map getParameterMap() {

		if (parametersMap == null) {
			parametersMap = new HashMap<String, String[]>();
			String queryString = getQueryString();
			Parameters parameters = new Parameters();
			//parameters.setEncoding(HttpConstants.DEFAULT_ENCODING_SERVER_SIDE); // need to find out an alternative for this
            if (queryString != null) {
                try {
                    byte[] bytes = queryString.getBytes("UTF-8");
                    parameters.processParameters(bytes, 0, bytes.length);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
			if (data != null && data.length > 0) {
				if (getContentType()!= null && getContentType().contains("application/x-www-form-urlencoded")) {
					ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(
							data.length);
					byteArrayBuffer.append(data, 0, data.length);
					byte[] tempdata = byteArrayBuffer.toByteArray();

					parameters.processParameters(tempdata, 0, data.length);
				}
			}
			Enumeration enum1 = parameters.getParameterNames();
			while (enum1.hasMoreElements()) {
				String name = (String) enum1.nextElement();
				String value[] = parameters.getParameterValues(name);
				parametersMap.put(name, value);
			}
		}
		return parametersMap;
	}

	public String[] getParameterValues(String parameterName) {
		getParameterMap();
		return parametersMap.get(parameterName);
	}

	public String getPathInfo() {
		return requestUri;
	}

	public String getProtocol() {
		return request.getRequestLine().getProtocolVersion().toString();
	}

	public String getQueryString() {
		return queryString;
	}

	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

	public String getRemoteAddr() {
		DefaultNHttpServerConnection connection = (DefaultNHttpServerConnection) context
				.getAttribute(ExecutionContext.HTTP_CONNECTION);
		return connection.getRemoteAddress().getHostAddress();
	}

	public String getRemoteHost() {
		DefaultNHttpServerConnection connection = (DefaultNHttpServerConnection) context
				.getAttribute(ExecutionContext.HTTP_CONNECTION);
		return connection.getRemoteAddress().getHostAddress();
	}

	public HttpRequest getRequest() {
		return request;
	}

	public String getRequestURI() {
		return requestUri;
	}

	public String getScheme() {
		return scheme;
	}

	public String getServerName() {

		return hostName;
	}

	public int getServerPort() {
		DefaultNHttpServerConnection connection = (DefaultNHttpServerConnection) context
				.getAttribute(ExecutionContext.HTTP_CONNECTION);
		return connection.getLocalPort();
	}

	public boolean isSecure() {
		return secure;
	}

	/**
	 * Separates URI and query String in the request line
	 * @throws UnsupportedEncodingException
	 */
	private void separateUriAndQuery() throws UnsupportedEncodingException {
		
		String uri = uriEncodingHelper.getDecodedURI(request);		
		int queryStringIndex = uri.indexOf('?');
		if (queryStringIndex != -1) {
			queryString = uri.substring(queryStringIndex + 1);
			requestUri = uri.substring(0, queryStringIndex);
		} else {
			requestUri = uri;
		}
		

	}

	public void setCharacterEncoding(String encoding) {
		characterEncoding = encoding;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	public void setServerName(String name) {
		this.hostName = name;
	}

    /**
     * Remove header from request with this name.
     *
     * @param headerName
     */
    @Override
    public void removeHeader(String headerName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
