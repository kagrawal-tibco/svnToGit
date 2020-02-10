/**
 * 
 */
package com.tibco.be.functions.channel.http;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

import com.tibco.cep.driver.http.client.HTTPConnectionInfo;

/**
 * Functions to create/update HTTP ConnectionInfo object, which contain various attributes/properties used during when the actual connection is established
 * 
 * @author vpatil
 */

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "HTTP.ConnectionInfo",
        synopsis = "HTTP Connection Info")
public class HTTPConnectionInfoFunctions {
	
	@com.tibco.be.model.functions.BEFunction (
        name = "createHTTPConnectionInfo",
        signature = "Object createHTTPConnectionInfo(boolean isSecure)",
        params = {
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "isSecure", type = "boolean", desc = "Is Secure communication"),
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "HTTP ConnectionInfo Object"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a HTTPConnectionInfo object.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object createHTTPConnectionInfo(boolean isSecure) {
		return new HTTPConnectionInfo(isSecure);
	}
	
	@com.tibco.be.model.functions.BEFunction (
        name = "setProxy",
        signature = "Object setProxy(Object connectionInfo, String proxyHost, int proxyPort)",
        params = {
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "connectionInfo", type = "Object", desc = "HTTP Connection Info Object"),
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "proxyHost", type = "String", desc = "Proxy Host"),
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "proxyPort", type = "int", desc = "Proxy Port")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "HTTP ConnectionInfo Object"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Update HTTPConnectionInfo Object with proxy details.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object setProxy(Object connectionInfo, String proxyHost, int proxyPort) {
		HTTPConnectionInfo httpConnInfo = null;
		
		if (connectionInfo instanceof HTTPConnectionInfo) {
			httpConnInfo = (HTTPConnectionInfo) connectionInfo;
			httpConnInfo.setProxy(proxyHost, proxyPort);
		} else {
			throw new IllegalArgumentException("connectionInfo is not an instanceof HTTPConnectionInfo object");
		}
		
		return httpConnInfo;
	}
	
	@com.tibco.be.model.functions.BEFunction (
        name = "disableExpectContinueHeader",
        signature = "Object disableExpectContinueHeader(Object connectionInfo, boolean expectContinueHeaderDisabled)",
        params = {
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "connectionInfo", type = "Object", desc = "HTTP Connection Info Object"),
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "disableExpectContinueHeader", type = "boolean", desc = "True to disable use. False by default")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "HTTP ConnectionInfo Object"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Configure HTTP Connection to enable/disable Expect-Continue Header.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object disableExpectContinueHeader(Object connectionInfo, boolean disableExpectContinueHeader) {
		HTTPConnectionInfo httpConnInfo = null;
		
		if (connectionInfo instanceof HTTPConnectionInfo) {
			httpConnInfo = (HTTPConnectionInfo) connectionInfo;
			httpConnInfo.setExpectContinueHeaderDisabled(disableExpectContinueHeader);
		} else {
			throw new IllegalArgumentException("connectionInfo is not an instanceof HTTPConnectionInfo object");
		}
		
		return httpConnInfo;
	}

	@com.tibco.be.model.functions.BEFunction (
        name = "setSecureInfo",
        signature = "Object setSecureInfo(Object connectionInfo, String sslProtocol, Object clientIdKeyStore, String clientIdPassword, Object trustedCertsKeystore, String trustedCertsPassword, boolean verifyHostName)",
        params = {
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "connectionInfo", type = "Object", desc = "HTTP Connection Info Object"),
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "sslProtocol", type = "String", desc = "SSL Protocol to use. Valid values TLSv1.1/TLSv1.2/TLSv1.3. Defaults to TLS(TLSv1.1) if not specified."),
        		@com.tibco.be.model.functions.FunctionParamDescriptor(name = "clientIdKeystore", type = "Object", desc = "Keystore Object for client identity, not required for 1 way SSL"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "clientIdPassword", type = "String", desc = "Password for client id keystore, not required for 1 way SSL"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "trustedCertsKeystore", type = "Object", desc = "Keystore Object for trusted certificates"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "trustedCertsPassword", type = "String", desc = "Password for trusted certificates keystore"),
                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "verifyHostName", type = "boolean", desc = "flag for checking if a hostname matches the names stored inside the server's certificate")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "HTTP ConnectionInfo Object"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Update HTTPConnectionInfo Object with secure/SSL usage details.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
	public static Object setSecureInfo(Object connectionInfo, String sslProtocol, Object clientIdKeyStore, String clientIdPassword, Object trustedCertsKeystore, String trustedCertsPassword, boolean verifyHostName) {
		HTTPConnectionInfo httpConnInfo = null;
		
		if (connectionInfo instanceof HTTPConnectionInfo) {
			httpConnInfo = (HTTPConnectionInfo) connectionInfo;
			httpConnInfo.setSecureInfo(sslProtocol, clientIdKeyStore, clientIdPassword, trustedCertsKeystore, trustedCertsPassword, verifyHostName);
		} else {
			throw new IllegalArgumentException("connectionInfo is not an instanceof HTTPConnectionInfo object");
		}
		
		return httpConnInfo;
	}
	
	@com.tibco.be.model.functions.BEFunction(
        name = "disableCookie",
        signature = "void disableCookie(Object connectionInfo, boolean disableCookie)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "connectionInfo", type = "Object", desc = "HTTP Connection Info Object"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "disableCookie", type = "boolean", desc = "True to disable. False by default.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = "void"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Configures HTTP Connection to enable/disable cookies. Disable will ignore cookies both ways i.e. accepting from the server or sending back to the server.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void disableCookie(Object connectionInfo, boolean disableCookie) {
		HTTPConnectionInfo httpConnInfo = null;
		
		if (connectionInfo instanceof HTTPConnectionInfo) {
			httpConnInfo = (HTTPConnectionInfo) connectionInfo;
			httpConnInfo.setCookiesDisabled(disableCookie);
		} else {
			throw new IllegalArgumentException("connectionInfo is not an instanceof HTTPConnectionInfo object");
		}
    }
	
	@com.tibco.be.model.functions.BEFunction(
        name = "setHttpMethod",
        signature = "void setHttpMethod(Object connectionInfo, String httpMethod)",
        params = {
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "connectionInfo", type = "Object", desc = "HTTP Connection Info Object"),
        	@com.tibco.be.model.functions.FunctionParamDescriptor(name = "httpMethod", type = "String", desc = "Http methods i.e. GET/POST/PUT/DELETE/TRACE/HEAD/OPTIONS/PATCH")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = "void"),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Configures HTTP Connection to use specified HTTP method during invocation.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static void setHttpMethod(Object connectionInfo, String httpMethod) {
		HTTPConnectionInfo httpConnInfo = null;
		
		if (connectionInfo instanceof HTTPConnectionInfo) {
			httpConnInfo = (HTTPConnectionInfo) connectionInfo;
			if (httpMethod != null && !httpMethod.isEmpty())
				httpConnInfo.setHttpMethod(httpMethod.toUpperCase());
			else 
				throw new IllegalArgumentException("httpMethod is null/empty.");
		} else {
			throw new IllegalArgumentException("connectionInfo is not an instanceof HTTPConnectionInfo object");
		}
    }
}
