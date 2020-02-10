package com.tibco.cep.driver.http.server.impl.servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.buf.MessageBytes;
import org.apache.tomcat.util.http.Parameters;

import com.tibco.cep.driver.http.HttpChannelConstants;
import com.tibco.cep.driver.http.HttpMethods;
import com.tibco.cep.driver.http.HttpUtils;
import com.tibco.cep.driver.http.serializer.HTTPHeaders;
import com.tibco.cep.driver.http.server.HttpChannelServerRequest;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * This class reads a HTTP Servlet request and adapts to a generic http
 * request
 *
 * @author vjavere
 */

public class HttpServerRequest implements HttpChannelServerRequest {

    private HttpServletRequest request;

    private Map<String, String[]> parametersMap;

    private Properties beProperties;

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(HttpServerRequest.class);

    public HttpServerRequest(HttpServletRequest request, Properties props) throws IOException {
        this.request = request;
        this.beProperties = props;
    }
    
    public Object getAttribute(String attributeName) {
        return request.getAttribute(attributeName);
    }

    public String getCharacterEncoding() {
        return request.getCharacterEncoding();
    }

    public int getContentLength() {
        return request.getContentLength();
    }

    public String getContentType() {
        return request.getContentType();
    }

    public String getHeader(String headerName) {
        return request.getHeader(headerName);

    }

    public Iterator getHeaderNames() {
        Enumeration enumHeaders = request.getHeaderNames();
        return Collections.list(enumHeaders).iterator();

    }


    public Iterator getHeaders(String headerName) {
        Enumeration enumHeaders = request.getHeaders(headerName);
        return Collections.list(enumHeaders).iterator();
    }

    public String getMethod() {
        String method = request.getMethod();
        if (method == null || method.trim().isEmpty()) {// why method should be null??
            if (request.getContentLength() <= 0)
                method = HttpMethods.METHOD_GET;
            else
                method = HttpMethods.METHOD_POST;
        }
        return method;
    }

    public String[] getParameterValues(String parameterName) {
        initializeParameterMap();
        String[] parameterValues;

        parameterValues = parametersMap.get(parameterName);

        if (parameterValues == null || parameterValues.length == 0) {
            try {
                parameterValues = request.getParameterValues(parameterName);
            } catch (Exception e) {
                LOGGER.log(Level.ERROR, "Error fetching parameter values", e);
            }
        }
        return parameterValues = (parameterValues == null) ? new String[] {} : parameterValues;
    }

    public String getPathInfo() {
        return request.getPathInfo();
    }

    public String getProtocol() {
        return request.getProtocol();
    }

    public String getQueryString() {
        return request.getQueryString();
    }

    public String getRemoteAddr() {
        return request.getRemoteAddr();
    }

    public String getRemoteHost() {
        return request.getRemoteHost();
    }


    public String getRequestURI() {
        return request.getRequestURI();
    }

    public String getScheme() {
        return request.getScheme();
    }

    public String getServerName() {
        return request.getServerName();
    }

    public int getServerPort() {
        return request.getServerPort();

    }

    public boolean isSecure() {
        return request.isSecure();
    }

    public InputStream getInputStream() throws IOException {
        InputStream baseInputStream = request.getInputStream();
        InputStream filteredInputStream = baseInputStream;
        int contentLength = request.getContentLength();
        String transferEncoding = request.getHeader(HTTPHeaders.TRANSFER_ENCODING.value());
        if (contentLength == -1 && "chunked".equalsIgnoreCase(transferEncoding)) {
        	byte[] filteredBytes = HttpUtils.readAndFilterByteStream(baseInputStream, contentLength);
        	filteredInputStream = new BufferedInputStream(new ByteArrayInputStream(filteredBytes));
        }
        return filteredInputStream;
    }

    private void initializeParameterMap() {
        if (parametersMap == null) {
            parametersMap = new HashMap<String, String[]>();
            String characterEncoding = getQueryStringEncoding();
            String queryString = getQueryString();

            if (queryString == null || queryString.trim().isEmpty() || characterEncoding == null)
                return;

            Parameters parameters = new Parameters();
            if (queryString != null && !queryString.trim().isEmpty()) {
                try {
                    MessageBytes messageBytes = MessageBytes.newInstance();
                    messageBytes.setString(queryString);
                    parameters.processParameters(messageBytes, Charset.forName(characterEncoding));
                } catch (Exception e) {
                    LOGGER.log(Level.ERROR, "Error processing parameters", e);
                }

            }

            Enumeration enum1 = parameters.getParameterNames();
            while (enum1.hasMoreElements()) {
                String name = (String) enum1.nextElement();
                String value[] = parameters.getParameterValues(name);
                parametersMap.put(name, value);
            }
        }
    }

    private String getQueryStringEncoding() {
        String encoding = "UTF-8";
        if (beProperties != null) {
            String uriEncoding = beProperties.getProperty(
                    HttpChannelConstants.URI_ENCODING, null);
            String useBodyEncodingForURI = beProperties.getProperty(
                    HttpChannelConstants.USE_BODY_ENCODING_FOR_URI, "false");
            Boolean valueOf = Boolean.valueOf(useBodyEncodingForURI);
            if (uriEncoding != null && !uriEncoding.isEmpty()) {
                encoding = uriEncoding;
            } else {
                if (valueOf) {
                    String characterEncoding = getCharacterEncoding();
                    if (characterEncoding != null)
                        encoding = characterEncoding;
                }
            }
        }
        return encoding;
    }

    @Override
    public void removeHeader(String headerName) {

    }
}
