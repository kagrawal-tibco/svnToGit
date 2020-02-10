/**
 * 
 */
package com.tibco.cep.driver.http.serializer;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;


/**
 * ENUM indicating the complete list of supported HTTP headers.
 * 
 * @author vpatil
 */
public enum HTTPHeaders {
	ACCEPT("Accept"),
	ACCEPT_CHARSET("Accept-Charset"),
	ACCEPT_ENCODING("Accept-Encoding"),
	ACCEPT_LANGUAGE("Accept-Language"),
	ACCEPT_RANGES("Accept-Ranges"),
	ACCESS_CONTROL_ALLOW_CREDENTIALS("Access-Control-Allow-Credentials"),
	ACCESS_CONTROL_ALLOW_HEADERS("Access-Control-Allow-Headers"),
	ACCESS_CONTROL_ALLOW_METHODS("Access-Control-Allow-Methods"),
	ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin"),
	ACCESS_CONTROL_EXPOSE_HEADERS("Access-Control-Expose-Headers"),
	ACCESS_CONTROL_MAX_AGE("Access-Control-Max-Age"),
	ACCESS_CONTROL_REQUEST_HEADERS("Access-Control-Request-Headers"),
	ACCESS_CONTROL_REQUEST_METHOD("Access-Control-Request-Method"),
	AGE("Age"),
	ALLOW("Allow"),
	AUTHORIZATION("Authorization"),
	CACHE_CONTROL("Cache-Control"),
	CONNECTION("Connection"),
	CONTENT_DISPOSITION("Content-Disposition"),
	CONTENT_ENCODING("Content-Encoding"),
	CONTENT_LANGUAGE("Ccontent-Language"),
	CONTENT_LENGTH("Content-Length"),
	CONTENT_LOCATION("Content-Location"),
	CONTENT_MD5("Content-MD5"),
	CONTENT_RANGE("Content-Range"),
	CONTENT_SECURITY_POLICY("Content-Security-Policy"),
	CONTENT_SECURITY_POLICY_REPORT_ONLY("Content-Security-Policy-Report-Only"),
	CONTENT_TYPE("Content-Type"),
	COOKIE("Cookie"),
	DATE("Date"),
	DNT("Dnt"),
	ETAG("ETag"),
	EXPECT("Expect"),
	EXPIRES("Expires"),
	FROM("From"),
	HOST("Host"),
	IF_MATCH("If-Match"),
	IF_MODIFIED_SINCE("If-Modified-Since"),
	IF_NONE_MATCH("If-None-Match"),
	IF_RANGE("If-Range"),
	IF_UNMODIFIED_SINCE("If-Unmodified-Since"),
	LAST_EVENT_ID("Last-Event-Id"),
	LAST_MODIFIED("Last-Modified"),
	LINK("Link"),
	LOCATION("Location"),
	MAX_FORWARDS("Max-Forwards"),
	ORIGIN("Origin"),
	P3P("P3P"),
	PRAGMA("Pragma"),
	PROXY_AUTHENTICATE("Proxy-Authenticate"),
	PROXY_AUTHORIZATION("Proxy-Authorization"),
	RANGE("Range"),
	REFERER("Rreferer"),
	REFRESH("Refresh"),
	RETRY_AFTER("Retry-After"),
	SERVER("Server"),
	SET_COOKIE("Set-Cookie"),
	SET_COOKIE2("Set-Cookie2"),
	TE("TE"),
	TIMING_ALLOW_ORIGIN("Timing-Allow-Origin"),
	TRAILER("Trailer"),
	TRANSFER_ENCODING("Transfer-Encoding"),
	UPGRADE("Upgrade"),
	USER_AGENT("User-Agent"),
	VARY("Vary"),
	VIA("Via"),
	WARNING("Warning"),
	WWW_AUTHENTICATE("WWW-Authenticate"),
	X_CONTENT_TYPE_OPTIONS("X-Content-Type-Options"),
	X_DO_NOT_TRACK("X-Do-Not-Track"),
	X_FORWARDED_FOR("X-Forwarded-For"),
	X_FORWARDED_PROTO("X-Forwarded-Proto"),
	X_FRAME_OPTIONS("X-Frame-Options"),
	X_POWERED_BY("X-Powered-By"),
	X_REQUESTED_WITH("X-Requested-With"),
	X_USER_IP("X-User-Ip"),
	X_XSS_PROTECTION("X-Xss-Protection"),
	CONTENT_TRANFER_ENCODING("Content-Transfer-Encoding");
	
	private String value;
	
	private static Set<String> supportedHttpHeaders;
	
	/**
	 * Default Constructor
	 * 
	 * @param value
	 */
	private HTTPHeaders(String value) {
		this.value = value;
	}
	
	/**
	 * Check if the Header name exists in the list of supported Http headers
	 * 
	 * @param headerName
	 * @return
	 */
	public static boolean contains(String headerName) {
		if (supportedHttpHeaders == null) {
			supportedHttpHeaders = new ConcurrentSkipListSet<String>();

			HTTPHeaders[] headerList = HTTPHeaders.values();
			for (HTTPHeaders header : headerList) {
				supportedHttpHeaders.add(header.value.toLowerCase());

			}
		}
		
		return supportedHttpHeaders.contains(headerName);
	}
	
	/**
	 * Fetch the Header value
	 * @return
	 */
	public String value() {
		return this.value;
	}
}
