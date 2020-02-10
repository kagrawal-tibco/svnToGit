package com.tibco.be.functions.channel.http;

import static com.tibco.be.model.functions.FunctionDomain.*;

import javax.servlet.AsyncContext;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Standard",
        category = "HTTP.Servlet",
        synopsis = "Functions to get HTTP servlet request and response objects.")
public class HTTPServletFunctions {

    
    @com.tibco.be.model.functions.BEFunction(
        name = "getServletRequest",
        synopsis = "Get servlet request from the context object.",
        signature = "Object getServletRequest(Object context)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "context", type = "Object", desc = "The context (AsyncContext) associated with a servlet request.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The <code>HttpServletRequest</code> instance."),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get servlet request from the context object.",
        cautions = "",
        fndomain = {ACTION},
        example = ""
    )
    public static Object getServletRequest(Object context) {
        if (!(context instanceof AsyncContext)) {
            throw new IllegalArgumentException("Context parameter should be of type AsyncContext");
        }
        AsyncContext asyncContext = (AsyncContext)context;
        return asyncContext.getRequest();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "getServletResponse",
        synopsis = "Get servlet response from the context object.",
        signature = "Object getServletResponse(Object context)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "context", type = "Object", desc = "The context (AsyncContext) associated with a servlet request.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "The <code>HttpServletResponse</code> instance."),
        version = "5.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Get servlet response from the context object.",
        cautions = "",
        fndomain = {ACTION, CONDITION, BUI},
        example = ""
    )
    public static Object getServletResponse(Object context) {
        if (!(context instanceof AsyncContext)) {
            throw new IllegalArgumentException("Context parameter should be of type AsyncContext");
        }
        AsyncContext asyncContext = (AsyncContext)context;
        return asyncContext.getResponse();
    }
    
}
