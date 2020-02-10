package com.tibco.be.functions.channel.http;

import static com.tibco.be.model.functions.FunctionDomain.*;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;
import com.tibco.cep.runtime.service.cluster.agent.tasks.AgentAction;
import com.tibco.cep.runtime.service.cluster.agent.tasks.AgentActionManager;
import com.tibco.cep.runtime.service.cluster.om.DistributedCacheBasedStore;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.PreprocessContext;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "HTTP.Servlet.Response",
        synopsis = "Functions to operate on HTTP servlet response objects.")
public class HTTPServletResponseFunctions {
	
	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(HTTPServletFunctions.class);

	@com.tibco.be.model.functions.BEFunction(
        name = "sendRedirectTo",
        synopsis = "Responds by redirecting the client to a different URL.",
        signature = "void sendRedirectTo(Object context, String redirectURL)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "context", type = "Object", desc = "The context (AsyncContext) associated with a servlet request."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "redirectURL", type = "String", desc = "The redirection URL.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Responds by redirecting the client to a different URL.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void sendRedirectTo(Object context, String redirectURL) {
        if (!(context instanceof AsyncContext)) {
            throw new IllegalArgumentException("context parameter should be of type AsyncContext");
        }
        if (redirectURL == null) {
            throw new IllegalArgumentException("Redirect URL cannot be null");
        }
        AsyncContext asyncContext = (AsyncContext)context;
        HttpServletResponse httpServletResponse = (HttpServletResponse)asyncContext.getResponse();
        try {
            httpServletResponse.sendRedirect(redirectURL);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        asyncContext.complete();
    }
		
    @com.tibco.be.model.functions.BEFunction(
        name = "setResponseContent",
        synopsis = "Sets the String or byte[] response content in the servlet response, and sends the response.",
        signature = "void setResponseContent(Object context, Object content, boolean commitResponse)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "context", type = "Object", desc = "The context (AsyncContext) associated with a servlet request/response."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "content", type = "Object", desc = "The String/byte[] response content."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "commitResponse", type = "boolean", desc = "Whether response should be immediately committed or not.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the String or byte[] response content in the servlet response, and sends the response.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setResponseContent(Object context, Object content, boolean commitResponse) {
        if (!(context instanceof AsyncContext)) {
            throw new IllegalArgumentException("context parameter should be of type AsyncContext");
        }
        if (content == null) {
            throw new IllegalArgumentException("Content cannot be null");
        }
        AsyncContext asyncContext = (AsyncContext)context;
        RuleSession ruleSession = RuleSessionManager.getCurrentRuleSession();
      
        // Post RTC if done via Rule
        if (AgentActionManager.hasActionManager(ruleSession)) {
        	 AgentActionManager.addAction(ruleSession, new SendResponseAction(asyncContext, content, commitResponse));
        	 
        } else { //Send directly if in-memory or via Pre-processor
          sendResponse(asyncContext, content, commitResponse);
        }
    }

    /**
     * Send response back to client.
     * 
     * @param asyncContext
     * @param content
     * @param commitResponse
     */
    private static void sendResponse(final AsyncContext asyncContext, Object content, boolean commitResponse) {
        HttpServletResponse httpServletResponse = (HttpServletResponse)asyncContext.getResponse();
        try {
            LOGGER.log(Level.DEBUG, "Writing out response");
            if (content instanceof String) {
            	String stringContent = (String) content;
            	
            	httpServletResponse.getWriter().write(stringContent);
            	httpServletResponse.flushBuffer();
            } else if (content instanceof byte[]) {
            	byte[] byteContent = (byte[]) content;
            	
            	ServletOutputStream outStream = httpServletResponse.getOutputStream();
            	outStream.write(byteContent);
                outStream.flush();
            	outStream.close();
            }
            if (commitResponse) {
                asyncContext.complete();
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "setResponseStatusCode",
        synopsis = "Sets the status code in the servlet response.",
        signature = "void setResponseStatusCode(Object context, int statusCode)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "response", type = "Object", desc = "The servlet response."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "statusCode", type = "int", desc = "The status code.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets the status code in the servlet response.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setResponseStatusCode(Object response, int statusCode) {
        if (!(response instanceof HttpServletResponse)) {
            throw new IllegalArgumentException("response parameter should be of type HTTPServletResponse");
        }
        if (statusCode < 0) {
            throw new IllegalArgumentException("Status code cannot be negative");
        }
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        httpServletResponse.setStatus(statusCode);
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "setResponseHeader",
        synopsis = "Sets a response header.",
        signature = "void setResponseHeader(Object response, String headerName, String headerValue)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "response", type = "Object", desc = "The servlet response."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "headerName", type = "String", desc = "The header name."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "headerValue", type = "String", desc = "Value of the header.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Sets a response header.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setResponseHeader(Object response, String headerName, String headerValue) {
        if (!(response instanceof HttpServletResponse)) {
            throw new IllegalArgumentException("response parameter should be of type HTTPServletResponse");
        }
        if (headerName == null) {
            throw new IllegalArgumentException("Header name cannot be null");
        }
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        httpServletResponse.setHeader(headerName, headerValue);
    }
    
    @com.tibco.be.model.functions.BEFunction(
        name = "getCookie",
        synopsis = "Creates a cookie object to be set on the response.",
        signature = "Object getCookie(String cookieName, String cookieValue, String domain, String path, int expiry)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cookieName", type = "String", desc = "The name of the cookie."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cookieValue", type = "String", desc = "The cookie value."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "domain", type = "String", desc = "Optional domain for the cookie."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "path", type = "String", desc = "Optional path for the cookie."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "expiry", type = "int", desc = "Max age for the cookie in seconds.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "cookie", desc = "object"),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Creates a cookie object to be set on the response.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static Object getCookie(String cookieName,
                                   String cookieValue, 
                                   String domain,
                                   String path,
                                   int expiry) {
        if (cookieName == null) {
            throw new IllegalArgumentException("Cookie name cannot be null");
        }
        if (cookieValue == null) {
            throw new IllegalArgumentException("Cookie value cannot be null");
        }
        
        Cookie cookie = new Cookie(cookieName, cookieValue);
        if (domain != null) {
            cookie.setDomain(domain);
        }
        cookie.setVersion(1);
        cookie.setPath(path);
        cookie.setMaxAge(expiry);
        return cookie;
    }
            
    @com.tibco.be.model.functions.BEFunction(
        name = "setCookie",
        synopsis = "Adds a cookie object to the response.",
        signature = "void setCookie(Object response, Object cookie)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "response", type = "Object", desc = "The servlet response."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "cookie", type = "Object", desc = "The cookie object."),
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "5.1",
        see = "getCookie",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Adds a cookie object to the response.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static void setCookie(Object response, Object cookie) {
        if (!(response instanceof HttpServletResponse)) {
            throw new IllegalArgumentException("response parameter should be of type HTTPServletResponse");
        }
        if (!(cookie instanceof Cookie)) {
            throw new IllegalArgumentException("Cookie parameter should belong to servlet cookie type");
        }
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        try {
            httpServletResponse.addCookie((Cookie)cookie);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }


    /**
     *
     */
    static class SendResponseAction implements AgentAction {
       
       private AsyncContext asyncContext;
       
       private Object content;

       private boolean commitResponse;
       
       public SendResponseAction(final AsyncContext asyncContext, Object content, boolean commitResponse) {
           this.asyncContext = asyncContext;
           this.content = content;
           this.commitResponse = commitResponse;
       }

       public void run(CacheAgent cacheAgent) throws Exception{
           if (cacheAgent instanceof InferenceAgent) {
               sendResponse(asyncContext, content, commitResponse);
           }
       }
   	}
}
