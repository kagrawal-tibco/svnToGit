package com.tibco.cep.driver.http;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Apr 3, 2008
 * Time: 6:52:48 PM
 * To change this template use File | Settings | File Templates.
 */
public interface HttpMethods {
    public static String METHOD_DELETE = "DELETE";
    public static String METHOD_HEAD = "HEAD";
    public static String METHOD_GET = "GET";
    public static String METHOD_OPTIONS = "OPTIONS";
    public static String METHOD_POST = "POST";
    public static String METHOD_PUT = "PUT";
    public static String METHOD_TRACE = "TRACE";
    public static String METHOD_PATCH = "PATCH";
    public static String PROTOCOL_HTTP = "http";
    public static String PROTOCOL_HTTPS = "https";
    public static String PROTOCOL_REST = "rest";
    public static String PROTOCOL_SOAP = "soap";

    public static final String DEFAULT_CONTENT_TYPE = "text/xml; charset=UTF-8";
    public static int DEFAULT_RETRY_COUNT = 3;
}
