package com.tibco.cep.driver.http.server.impl.httpcomponents.handlers;

import static java.net.URLDecoder.decode;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.impl.nio.DefaultNHttpServerConnection;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.entity.BufferingNHttpEntity;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.Parameters;

import com.tibco.cep.driver.http.server.HttpChannelServerRequest;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * This class reads an asynchronous HTTP request and adapts to a generic http
 * request
 *
 * @author vjavere
 */
public class ASyncHttpComponentsRequest implements HttpChannelServerRequest {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(ASyncHttpComponentsRequest.class);

    private HttpRequest request;
    
    private byte[] data;
    
    private InputStream dataStream;

    private List<NameValuePair> nvPairs = new ArrayList<NameValuePair>();

    private Map<String, String[]> parametersMap = null;

    private HttpContext context;

    private String requestURI;

    private String queryString;

    private String hostName;

    private boolean secure;

    private String scheme;

    private String characterEncoding;

    private RequestUriEncoding uriEncodingHelper;

    /**
     * This method fetches the context, separates the request URI and query string
     * and retrieves the request body of an Asynchronous HTTP Request
     *
     * @param request
     * @param context
     * @param uriEncodingHelper
     * @throws IOException
     */
    public ASyncHttpComponentsRequest(HttpRequest request,
                                      HttpContext context,
                                      RequestUriEncoding uriEncodingHelper) throws java.io.IOException {
        this.request = request;
        this.context = context;
        this.uriEncodingHelper = uriEncodingHelper;
        separateUriAndQuery();

        if (request instanceof HttpEntityEnclosingRequest) {
            BufferingNHttpEntity entity = (BufferingNHttpEntity) ((HttpEntityEnclosingRequest) request).getEntity();
            characterEncoding = EntityUtils.getContentCharSet(entity);
            //Check whether request content has any encoding information else default to the JVM setting.
            characterEncoding = (characterEncoding == null) ? System.getProperty("file.encoding") : characterEncoding;
            data = EntityUtils.toByteArray(entity);
            dataStream = new ByteArrayInputStream(data);
            parseParams();
            //Reset the position counter back to 0
            //This is a safe call because the above stream supports marking.
            dataStream.reset();
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
            HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
            return (int) entity.getContentLength();
        }
        return 0;
    }

    public String getContentType() {
        if (request instanceof HttpEntityEnclosingRequest) {
            Header contentType = ((HttpEntityEnclosingRequest) request).getEntity().getContentType();
            if (contentType != null) {
                return contentType.getValue();
            }
        }
        return null;
    }

    public String getHeader(String headerName) {
        Header header = request.getFirstHeader(headerName);
        if (header != null)
            return header.getValue();
        else
            return null;
    }

    public Iterator<String> getHeaderNames() {
        HeaderIterator itr = request.headerIterator();
        Set<String> headerNames = new LinkedHashSet<String>();
        while (itr.hasNext()) {
            Header header = (Header) itr.next();
            headerNames.add(header.getName().toLowerCase());
        }
        return headerNames.iterator();
    }

    public Iterator getHeaders(String headerName) {
        Header[] headers = request.getHeaders(headerName);
        List<String> headerValues = new ArrayList<String>();
        for (int i = 0; i < headers.length; i++) {
            headerValues.add(headers[i].getValue());
        }
        return headerValues.iterator();

    }

    public InputStream getInputStream() throws IOException {
        return new BufferedInputStream(dataStream);
    }

    public String getMethod() {
        return request.getRequestLine().getMethod();
    }

    private void parseParams() {
        Scanner scanner = new Scanner(dataStream, characterEncoding).useDelimiter("&");
        parseParams(scanner);
    }

    /**
     * Look for patterns like <param>=<value>&<param>=<value>...
     * @param scanner
     */
    private void parseParams(Scanner scanner) {
        String name;
        String value;
        while (scanner.hasNext()) {
            String nameValue[] = scanner.next().split("=");
            //Continue here because this could be post data also and
            //not necessarily parameters.
            if (nameValue.length == 0 || nameValue.length > 2) {
                continue;
            }
            try {
                name = decode(nameValue[0], characterEncoding);
                value = null;
                if (nameValue.length == 2) {
                    value = decode(nameValue[1], characterEncoding);
                }
                if (name != null) {
                    nvPairs.add(new BasicNameValuePair(name, value));
                }
            } catch (UnsupportedEncodingException e) {
                LOGGER.log(Level.ERROR, e, null);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
	public void populateParameterMap() {
        if (parametersMap == null) {
            parametersMap = new HashMap<String, String[]>();
            final String queryString = getQueryString();
            //TODO Need to find an alternative to this API
            final Parameters parameters = new Parameters();
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
                if (getContentType() != null && getContentType().contains("application/x-www-form-urlencoded")) {
                    final ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(data.length);
                    byteArrayBuffer.append(data, 0, data.length);
                    //Use the encoding to parse parameters
                    parameters.setCharset(Charset.forName(characterEncoding));
                    parameters.processParameters(byteArrayBuffer.toByteArray(), 0, data.length);
                }
            }
            final Enumeration parameterNames = parameters.getParameterNames();
            String name = null;
            String[] value = null;
            while (parameterNames.hasMoreElements()) {
                name = (String) parameterNames.nextElement();
                value = parameters.getParameterValues(name);
                parametersMap.put(name, value);
            }
        }
    }

    public String[] getParameterValues(String parameterName) {
    	for (NameValuePair nvPair : nvPairs) {
            if (nvPair.getName().equals(parameterName)) {
                return new String[]{nvPair.getValue()};
            }
        }
    	//nvPairs will be empty if request is instanceof BasicHttpRequest
    	populateParameterMap();
        String[] value = parametersMap.get(parameterName);
        //Do not return null
        return (value != null) ? value : new String[]{};
    }
    public String getPathInfo() {
        return requestURI;
    }

    public String getProtocol() {
        return request.getRequestLine().getProtocolVersion().toString();
    }

    public String getQueryString() {
        return queryString;
    }

    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(dataStream));
    }

    public String getRemoteAddr() {
        DefaultNHttpServerConnection connection = (DefaultNHttpServerConnection) context
                .getAttribute(HttpCoreContext.HTTP_CONNECTION);
        return connection.getRemoteAddress().getHostAddress();
    }

    public String getRemoteHost() {
        DefaultNHttpServerConnection connection = (DefaultNHttpServerConnection) context
                .getAttribute(HttpCoreContext.HTTP_CONNECTION);
        return connection.getRemoteAddress().getHostAddress();
    }

    public HttpRequest getRequest() {
        return request;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getScheme() {
        return scheme;
    }

    public String getServerName() {
        return hostName;
    }

    public int getServerPort() {
        DefaultNHttpServerConnection connection = (DefaultNHttpServerConnection) context
                .getAttribute(HttpCoreContext.HTTP_CONNECTION);
        return connection.getLocalPort();
    }

    public boolean isSecure() {
        return secure;
    }

    private void separateUriAndQuery() throws UnsupportedEncodingException {

        String uri = uriEncodingHelper.getDecodedURI(request);
        int queryStringIndex = uri.indexOf('?');
        if (queryStringIndex != -1) {
            queryString = uri.substring(queryStringIndex + 1);
            requestURI = uri.substring(0, queryStringIndex);
        } else {
            requestURI = uri;
        }
    }


    public void setAttribute(String name, Object value) {
        context.setAttribute(name, value);
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
