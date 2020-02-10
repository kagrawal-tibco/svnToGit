package com.tibco.cep.runtime.model.exception.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.tibco.cep.runtime.model.exception.BEException;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 31, 2006
 * Time: 2:53:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class BEExceptionImpl extends RuntimeException implements BEException {
    private final String errorType;
    private String stackTrace = null;
    public BEExceptionImpl(String errorType, String message) {
        super(message);
        this.errorType = errorType;
    }
    
    public BEExceptionImpl(String errorType, String message, BEException cause) {
        super(message, cause == null ? null : cause.toException());
        this.errorType = errorType;
    }
    
    private BEExceptionImpl(Throwable toWrap) {
        super(_message(toWrap), _cause(toWrap));
        this.errorType = toWrap == null ? null : toWrap.getClass().getName();
        if(toWrap != null) setStackTrace(toWrap.getStackTrace());
    }
    
    private static String _message(Throwable toWrap) {
        if(toWrap == null) return "";
        String message = toWrap.getMessage();
        return toWrap.getClass().getName() + ": " + (message == null ? "<no message>" : message);
    }
    
    private static Throwable _cause(Throwable toWrap) {
        BEException cause = wrapThrowable(toWrap.getCause());
        if(cause == null) return null;
        return cause.toException();
    }
    
    public static BEException wrapThrowable(Throwable toWrap) {
        if(toWrap == null) return null;
        if(toWrap instanceof BEException) {
            return (BEException)toWrap;
        }
        return new BEExceptionImpl(toWrap); 
    }

    public static RuntimeException wrapThrowableAsRE(Throwable toWrap) {
        if(toWrap == null) return null;
        return wrapThrowable(toWrap).toException();
    }
    
    public String getErrorType() {
        return errorType;
    }

    public RuntimeException toException() {
        return this;
    }

    public BEException get_Cause() {
        return (BEException)getCause();
    }

    public String get_StackTrace() {
        if(stackTrace == null) {
            stackTrace = makeStackTraceString(this);
        }
        return stackTrace;
    }
    public static String makeStackTraceString(Exception exp) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exp.printStackTrace(pw);
        pw.close();
        return sw.toString();
    }
    
    public void toXiNode(XiNode node) {
        if(get_Cause() != null) {
            get_Cause().toXiNode(node.appendElement(ExpandedName.makeName(com.tibco.cep.designtime.model.BEException.ATTRIBUTE_CAUSE)));
        }
        if(getErrorType() != null){
            node.setAttributeStringValue(ExpandedName.makeName(com.tibco.cep.designtime.model.BEException.ATTRIBUTE_ERROR_TYPE), getErrorType());
        }
        if(getMessage() != null){
            node.setAttributeStringValue(ExpandedName.makeName(com.tibco.cep.designtime.model.BEException.ATTRIBUTE_MESSAGE), getMessage());
        }
        if(get_StackTrace() != null){
            node.setAttributeStringValue(ExpandedName.makeName(com.tibco.cep.designtime.model.BEException.ATTRIBUTE_STACK_TRACE), get_StackTrace());
        }
    }
}