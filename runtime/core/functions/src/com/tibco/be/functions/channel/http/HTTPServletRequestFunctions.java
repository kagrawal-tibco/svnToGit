package com.tibco.be.functions.channel.http;

import static com.tibco.be.model.functions.FunctionDomain.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
		category = "HTTP.Servlet.Request",
		synopsis = "Functions to operate on HTTP servlet request objects.")
public class HTTPServletRequestFunctions {
	
	private static final int WAIT_TIME_FOR_SLOW_BROWSER = 600;
	private static final int MAX_FETCH_RETRIES = 5;
	
	private static final String BROWSER_MSIE = "msie";
	private static final String BROWSER_FIREFOX = "firefox";
	private static final String BROWSER_CHROME = "chrome";
	private static final String BROWSER_SAFARI = "safari";
	
	public final static String TRANSFER_ENCODING_HEADER = "Transfer-Encoding";
    public final static String TRANSFER_ENCODING_CHUNKED = "chunked";

	@com.tibco.be.model.functions.BEFunction(
		name = "startAsync",
		signature = "Object startAsync(Object servletRequest)",
		synopsis = "Starts asynchronous processing of servlet requests.",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "servletRequest", type = "Object", desc = "Servlet Request object")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Async Context"),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Starts asynchronous processing of servlet requests.",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static Object startAsync(Object servletRequest) {
		if (!(servletRequest instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("Context parameter should be of type HttpServletRequest");
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
		return httpServletRequest.startAsync();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getRequestURI",
		signature = "String getRequestURI(Object request)",
		synopsis = "Get servlet request URI.",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "Object", desc = "The servlet request URI including query parameters if any.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The value of the URI."),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Get servlet request URI.",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static String getRequestURI(Object request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("request parameter should be of type HttpServletRequest");
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		return httpServletRequest.getRequestURI();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getMethod",
		signature = "String getMethod(Object request)",
		synopsis = "Gets the servlet request method.",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "Object", desc = "The servlet request object.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The value of the method."),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Gets the servlet request method.",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static String getRequestMethod(Object request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("request parameter should be of type HttpServletRequest");
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		return httpServletRequest.getMethod();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getRequestHeader",
		signature = "String getRequestHeader(Object request, String headerName)",
		synopsis = "Gets a servlet request header value.",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "Object", desc = "The servlet request object."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "headerName", type = "String", desc = "The name of the request header.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The value of the header."),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Gets a servlet request header value.",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static String getRequestHeader(Object request, String headerName) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("request parameter should be of type HttpServletRequest");
		}
		if (headerName == null) {
			throw new IllegalArgumentException("Header name cannot be null");
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		return httpServletRequest.getHeader(headerName);
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getRequestHeaders",
		signature = "Object getRequestHeaders(Object request)",
		synopsis = "Get servlet request header names.",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "Object", desc = "The servlet request object.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The collection of header names."),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Get servlet request header names.",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static Object getRequestHeaders(Object request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("request parameter should be of type HttpServletRequest");
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;

		List<String> headerNameList = new ArrayList<String>();

		Enumeration<String> headers = httpServletRequest.getHeaderNames();
		while (headers.hasMoreElements()) {
			headerNameList.add(headers.nextElement());
		}

		return headerNameList.toArray(new String[headerNameList.size()]);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getSession",
		signature = "Object getSession(Object request)",
		synopsis = "Get Http Session",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "Object", desc = "The servlet request object.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Http Session"),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Get Http Session",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static Object getSession(Object request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("request parameter should be of type HttpServletRequest");
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		
		return httpServletRequest.getSession();
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getSessionId",
		signature = "String getSessionId(Object session)",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "session", type = "Object", desc = "Http Session object.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Session Id"),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Get Http Session Id",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static String getSessionId(Object session) {
		if (!(session instanceof HttpSession)) {
			throw new IllegalArgumentException("session parameter should be of type HttpSession");
		}
		
		HttpSession httpSession = (HttpSession) session;
		return httpSession.getId();
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setSessionTimeout",
		signature = "void setSessionTimeout(Object session, int timeout)",
		synopsis = "Set Http Session timeout",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "session", type = "Object", desc = "Http Session object."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "timeout", type = "int", desc = "Timeout")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Set Http Session timeout",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static void setSessionTimeout(Object session, int timeout) {
		if (!(session instanceof HttpSession)) {
			throw new IllegalArgumentException("session parameter should be of type HttpSession");
		}
		
		HttpSession httpSession = (HttpSession) session;
		httpSession.setMaxInactiveInterval(timeout);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "setSessionAttribute",
		signature = "void setSessionAttribute(Object session, String key, Object value)",
		synopsis = "Set Http Session timeout",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "session", type = "Object", desc = "Http Session object."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "Attribute name/key"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "Attribute value")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Set Http Session timeout",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static void setSessionAttribute(Object session, String key, Object value) {
		if (!(session instanceof HttpSession)) {
			throw new IllegalArgumentException("session parameter should be of type HttpSession");
		}
		
		HttpSession httpSession = (HttpSession) session;
		httpSession.setAttribute(key, value);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getSessionAttributeNames",
		signature = "String[] getSessionAttributeNames(Object session)",
		synopsis = "Get Http Session attribute names",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "session", type = "Object", desc = "Http Session object.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "Session Attribute Names"),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Get Http Session attribute names",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static String[] getSessionAttributeNames(Object session) {
		if (!(session instanceof HttpSession)) {
			throw new IllegalArgumentException("session parameter should be of type HttpSession");
		}
		
		HttpSession httpSession = (HttpSession) session;
		List<String> attributeNameList = new ArrayList<String>();
		
		Enumeration<String> sessionAttributes = httpSession.getAttributeNames();
		while (sessionAttributes.hasMoreElements()) {
			attributeNameList.add(sessionAttributes.nextElement());
		}

		return attributeNameList.toArray(new String[attributeNameList.size()]);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getSessionAttribute",
		signature = "Object getSessionAttribute(Object session, String key)",
		synopsis = "Get Http Session attribute",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "session", type = "Object", desc = "Http Session object."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "Attribute name/key")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Attribute value"),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Get Http Session attribute",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static Object getSessionAttribute(Object session, String key) {
		if (!(session instanceof HttpSession)) {
			throw new IllegalArgumentException("session parameter should be of type HttpSession");
		}
		
		HttpSession httpSession = (HttpSession) session;
		return httpSession.getAttribute(key);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "removeSessionAttribute",
		signature = "void removeSessionAttribute(Object session, String key)",
		synopsis = "Remove Http Session attribute",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "session", type = "Object", desc = "Http Session object."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "key", type = "String", desc = "Attribute name/key")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Remove Http Session attribute",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static void removeSessionAttribute(Object session, String key) {
		if (!(session instanceof HttpSession)) {
			throw new IllegalArgumentException("session parameter should be of type HttpSession");
		}
		
		HttpSession httpSession = (HttpSession) session;
		httpSession.removeAttribute(key);
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "invalidateSession",
		signature = "void invalidateSession(Object session)",
		synopsis = "Invalidate Http sesion",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "session", type = "Object", desc = "Http Session object.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Invalidate Http sesion",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static void invalidateSession(Object session) {
		if (!(session instanceof HttpSession)) {
			throw new IllegalArgumentException("session parameter should be of type HttpSession");
		}
		
		HttpSession httpSession = (HttpSession) session;
		httpSession.invalidate();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getRequestorAddress",
		signature = "String getRequestorAddress(Object request)",
		synopsis = "Gets the address of the remote requestor.",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "Object", desc = "The servlet request object.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The value of the remote requestor address."),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Gets the address of the remote requestor.",
		cautions = "",
		fndomain = {ACTION, CONDITION, BUI},
		example = ""
	)
	public static String getRequestorAddress(Object request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("Request parameter should be of type HttpServletRequest");
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		return httpServletRequest.getRemoteAddr();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getRequestParameter",
		signature = "String getRequestParameter(Object request, String paramName)",
		synopsis = "Gets a servlet request parameter value.",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "Object", desc = "The servlet request object."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "paramName", type = "String", desc = "The name of the request parameter.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The value of the parameter."),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Gets a servlet request parameter value.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static String getRequestParameter(Object request, String paramName) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("request parameter should be of type HttpServletRequest");
		}
		if (paramName == null) {
			throw new IllegalArgumentException("Parameter name cannot be null");
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		return httpServletRequest.getParameter(paramName);
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getRequestParameterValues",
		signature = "String[] getRequestParameterValues(Object request, String paramName)",
		synopsis = "Gets the values of all the servlet request parameters.",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "Object", desc = "The servlet request object."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "paramName", type = "String", desc = "The name of the request parameter.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "The values of the parameter."),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Gets the values of all the servlet request parameters.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static String[] getRequestParameterValues(Object request, String paramName) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("request parameter should be of type HttpServletRequest");
		}
		if (paramName == null) {
			throw new IllegalArgumentException("Parameter name cannot be null");
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		return httpServletRequest.getParameterValues(paramName);
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getRequestParameters",
		signature = "String getRequestParameters(Object request)",
		synopsis = "Gets the names of all the servlet request parameters.",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "Object", desc = "The servlet request object.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "The names of all the parameters."),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Gets the names of all the servlet request parameters.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static String[] getRequestParameters(Object request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("request parameter should be of type HttpServletRequest");
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		
		List<String> parameters = new ArrayList<String>();
		
		String queryString = httpServletRequest.getQueryString();
		if (queryString != null && !queryString.isEmpty()) {
			String[] parameterPairs = queryString.split("&");
			for (String parameter : parameterPairs) {
				String[] keyValue = parameter.split("=");
				if (keyValue.length > 0 && !parameters.contains(keyValue[0])) {
					parameters.add(keyValue[0]);
				}
			}
		} else {
			Enumeration<String> params = httpServletRequest.getParameterNames();
			while (params.hasMoreElements()) {
				parameters.add(params.nextElement());
			}
		}
		return parameters.toArray(new String[parameters.size()]);
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getMultipartContent",
		signature = "Object getMultipartContent(Object request)",
		synopsis = "Gets the complete Multipart Content as a Map.",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "Object", desc = "The servlet request object.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Byte Array of the part contents."),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Gets the complete Multipart Content as a Map.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)    
	public static Object getMultipartContent(Object request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("request parameter should be of type HttpServletRequest");
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		Map<String, Object> multipartContent = new HashMap<String, Object>();
		try {	
			if (ServletFileUpload.isMultipartContent(httpServletRequest)) {
				ServletFileUpload upload = new ServletFileUpload();
				FileItemIterator iter = upload.getItemIterator(httpServletRequest);
				while (iter.hasNext()) {
					FileItemStream item = iter.next();
					String fieldName = item.getFieldName();
					InputStream is = item.openStream();
					ByteArrayOutputStream buffer = new ByteArrayOutputStream();
					int nRead = 0;
					byte[] byteBuff = new byte[4096];
					while ((nRead = is.read(byteBuff, 0, byteBuff.length)) != -1) {
						buffer.write(byteBuff, 0, nRead);
					}
					buffer.flush();
					if (item.isFormField()) {
						multipartContent.put(fieldName, buffer.toString("UTF-8"));
					} else {
						multipartContent.put(fieldName, buffer.toByteArray());
					}	
				}
			}

			return multipartContent;			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}   	
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getPartContent",
		signature = "Object getPartContent(Object multipartContent, String partName)",
		synopsis = "Gets the specified part of a Multipart content.",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "multipartContent", type = "Object", desc = "Multipart content."),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "partName", type = "String", desc = "The Part name.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Part content."),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Gets the specified part of a Multipart content.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)        
	public static Object getPartContent(Object multipartContent, String partName) {
		if (!(multipartContent instanceof Map)) {
			throw new IllegalArgumentException("request parameter should be of type Map");
		}
		Map multiPartFormDataMap = (Map) multipartContent; 
		return multiPartFormDataMap.get(partName);
	}


    @com.tibco.be.model.functions.BEFunction(
            name = "getProtocol",
            signature = "String getProtocol(Object request)",
            synopsis = "Gets the protocol used by the servlet request.",
            params = {
                    @com.tibco.be.model.functions.FunctionParamDescriptor(
                            name = "request", type = "Object", desc = "The servlet request.")
            },
            freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(
                    name = "", type = "String", desc = "The name and version of the protocol the request uses."),
            version = "5.1",
            see = "",
            mapper = @com.tibco.be.model.functions.BEMapper(),
            description = "Gets the protocol used by the servlet request.",
            cautions = "",
            fndomain = {ACTION, CONDITION, BUI},
            example = ""
    )
    public static String getProtocol(
            Object request)
    {
        if (!(request instanceof HttpServletRequest)) {
            throw new IllegalArgumentException("request parameter should be of type HttpServletRequest");
        }
        return ((HttpServletRequest)request).getProtocol();
    }


	@com.tibco.be.model.functions.BEFunction(
		name = "getRequestCookies",
		signature = "Object[] getRequestCookies(Object request)",
		synopsis = "Get servlet request cookies",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "Object", desc = "The servlet request object.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object[]", desc = "All cookies"),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Get servlet request cookies",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static Object[] getRequestCookies(Object request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("request parameter should be of type HttpServletRequest");
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		Cookie[] requestCookies = httpServletRequest.getCookies();
		Object[] cookies = null;
		//Added Check for NULL reference
		if (requestCookies != null) {
			cookies = new Object[requestCookies.length];        
			System.arraycopy(requestCookies, 0, cookies, 0, requestCookies.length);
		}
		return cookies;
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getRequestContent",
		signature = "byte[] getRequestContent(Object request)",
		synopsis = "Gets the servlet request post data.",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "Object", desc = "The servlet request object.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "byte[]", desc = "The post content."),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Gets the servlet request post data.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static Object getRequestContent(Object request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("request parameter should be of type HttpServletRequest");
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		
		InputStream inputStream = null;
		try {
			inputStream = httpServletRequest.getInputStream();
			if (TRANSFER_ENCODING_CHUNKED.equals(getRequestHeader(httpServletRequest, TRANSFER_ENCODING_HEADER))) {
				return readChunkedInputStream(inputStream);
			}

			//Return string content
			return readInputStream(inputStream, httpServletRequest.getContentLength());
		} catch (IOException e) {
			if (e instanceof SocketTimeoutException) {
				try {
					return retryPostDataFetch(inputStream, httpServletRequest.getContentLength(), 1);
				} catch (IOException ioException) {
					throw new RuntimeException(ioException);
				}
			}
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Retry for POST data, since browsers such as IE and Chrome at times are slow when sending large data
	 * 
	 * @param inputStream
	 * @param contentLength
	 * @param retryCount
	 * @return
	 * @throws IOException
	 */
	private static Object retryPostDataFetch(InputStream inputStream, int contentLength, int retryCount) throws IOException {
		try {
			Thread.sleep(WAIT_TIME_FOR_SLOW_BROWSER);
		} catch (InterruptedException interruptedException) {
			throw new RuntimeException(interruptedException);
		}
		
		try {
			// try once again
			return readInputStream(inputStream, contentLength);
		} catch (IOException ioException) {
			if (ioException instanceof SocketTimeoutException && retryCount < MAX_FETCH_RETRIES) {
				return retryPostDataFetch(inputStream, contentLength, ++retryCount);
			}
			throw new RuntimeException(ioException);
		}
	}
	
	@com.tibco.be.model.functions.BEFunction(
		name = "getRequestBrowserName",
		signature = "String getRequestBrowserName(Object request)",
		synopsis = "Gets the browser name from where the request is generated.",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "Object", desc = "The servlet request object.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Browser name from where the request originated, i.e. MSIE/FIREFOX/CHROME/SAFARI"),
		version = "5.1.2",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Gets the browser name from where the request is generated.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static String getRequestBrowserName(Object request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("request parameter should be of type HttpServletRequest");
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		String userAgent = getRequestHeader(httpServletRequest, "user-agent").toLowerCase();
		
		String browserName= null;
		if (userAgent.contains(BROWSER_MSIE)) {
			browserName = BROWSER_MSIE;
		} else if (userAgent.contains(BROWSER_FIREFOX)) {
			browserName = BROWSER_FIREFOX;
		} else if (userAgent.contains(BROWSER_CHROME)) {
			browserName = BROWSER_CHROME;
		} else if (userAgent.contains(BROWSER_SAFARI)) {
			browserName = BROWSER_SAFARI;
		}
		if (browserName != null) browserName = browserName.toUpperCase();
		
		return browserName;
	}

	/**
	 * Read data from incoming client request
	 * 
	 * @param inputStream
	 * @param contentLength
	 * @return
	 * @throws IOException 
	 */
	private static byte[] readInputStream(InputStream inputStream, int contentLength) throws IOException {
		if (inputStream != null) {
			if (contentLength <= 0) {
				return new byte[0];
			}
			ReadableByteChannel channel = Channels.newChannel(inputStream);
			byte[] postData = new byte[contentLength];
			ByteBuffer buf = ByteBuffer.allocateDirect(contentLength);
			int numRead = 0;
			int counter = 0;
			while (numRead >= 0) {
				((Buffer)buf).rewind();
				//Read bytes from the channel
				numRead = channel.read(buf);
				((Buffer)buf).rewind();
				for (int i = 0; i < numRead; i++) {
					postData[counter++] = buf.get();
				}
			}
			return postData;
		}
		return null;
	}
	
	/**
	 * Read chunked data
	 * 
	 * @param chunkedInputStream
	 * @return
	 * @throws IOException
	 */
	private static byte[] readChunkedInputStream(InputStream chunkedInputStream) throws IOException {
		ReadableByteChannel channel = Channels.newChannel(chunkedInputStream);
		
		// content length is not known upfront, hence a initial size
		int bufferLength = 2048;
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteBuffer byteBuffer = ByteBuffer.allocate(bufferLength);
		
		int numRead = 0;
		while (numRead >= 0) {
			((Buffer)byteBuffer).rewind();
			//Read bytes from the channel
			numRead = channel.read(byteBuffer);
			((Buffer)byteBuffer).rewind();

			if (numRead > 0) {
				byte[] dataBytes = byteBuffer.array();
				baos.write(dataBytes, 0, numRead);
			}

			((Buffer)byteBuffer).clear();
		}
		
		return baos.toByteArray();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getCookieValue",
		signature = "String getCookieValue(Object[] allCookies, String cookieName)",
		synopsis = "Gets the cookie vlaue for the given cookie name",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "allCookies", type = "Object[]", desc = "All request cookies"),
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "cookieName", type = "String", desc = "The name of the cookie whose value is desired.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String",
		desc = "Get servlet request cookie value."),
		version = "5.1",
		see = "getRequestCookies",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Gets the cookie value for the given cookie name",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)
	public static String getCookieValue(Object[] allCookies, String cookieName) {
		for (Object cookie : allCookies) {
			Cookie requestCookie = (Cookie)cookie;
			if (requestCookie.getName().equals(cookieName)) {
				return requestCookie.getValue();
			}
		}
		return null;
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getServerName",
		signature = "String getServerName(Object request)",
		synopsis = "Gets the server name used in the request.",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "Object", desc = "The servlet request object.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The name of the web server."),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Gets the server name used in the request.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)    
	public static String getServerName(Object request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("Request parameter should be of type HttpServletRequest");
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		return httpServletRequest.getServerName();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getServerPort",
		signature = "int getServerPort(Object request)",
		synopsis = "Gets the port number.",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "Object", desc = "The servlet request object.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "int", desc = "The http port of the web server."),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Gets the port number.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)    
	public static int getServerPort(Object request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("Request parameter should be of type HttpServletRequest");
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		return httpServletRequest.getLocalPort();
	}

	@com.tibco.be.model.functions.BEFunction(
		name = "getServerScheme",
		signature = "String getServerScheme(Object request)",
		synopsis = "Gets the protocol name used in the request.",
		params = {
				@com.tibco.be.model.functions.FunctionParamDescriptor(name = "request", type = "Object", desc = "The servlet request object.")
		},
		freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "The protocol of the web server."),
		version = "5.1",
		see = "",
		mapper = @com.tibco.be.model.functions.BEMapper(),
		description = "Gets the protocol name used in the request.",
		cautions = "",
		fndomain = {ACTION},
		example = ""
	)    
	public static String getServerScheme(Object request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException("Request parameter should be of type HttpServletRequest");
		}
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		return httpServletRequest.getScheme();
	}
}
