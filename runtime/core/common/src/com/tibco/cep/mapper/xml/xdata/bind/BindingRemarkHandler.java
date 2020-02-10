package com.tibco.cep.mapper.xml.xdata.bind;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.tibco.xml.channel.remark.XmlRemarkHandler;
import com.tibco.xml.data.context.XmlContext;
import com.tibco.xml.data.context.helpers.XPathHelper;
import com.tibco.xml.xquery.ExprException;

/**
 * {@link XmlRemarkHandler} implementation that collects validation errors.
 */
public class BindingRemarkHandler implements XmlRemarkHandler, ErrorListener {

    private List errorList; // lazily allocated.

    public BindingRemarkHandler() {
    }

    public boolean hasErrors() {
        return errorList!=null && errorList.size() > 0;
    }

    /**
     * Call after the validation run is completed.
     * @throws SAXException if any errors were reported by the validator.
     */
    public void assertNoErrors() throws SAXException {
        if (errorList!=null && errorList.size() > 0) {
            StringBuffer msg = new StringBuffer();
            Iterator errors = errorList.iterator();
            while (errors.hasNext()) {
                msg.append((String) errors.next());
            }
            throw new SAXException(msg.toString());
        }
    }

    /**
     * Get the stack trace as a string
     */
    protected static String getStackTrace(Throwable ex) {
        final CharArrayWriter caw = new CharArrayWriter();
        ex.printStackTrace(new PrintWriter(caw));
        caw.flush();
        caw.close();
        return caw.toString();
    }

    protected void addError(Throwable exception) {
        addError(exception, exception);  
    }

    /**
     * Given a transformer exception, call the addError method with
     * the right arguments.
     */
    protected void addError(Throwable parentException, Throwable exception) {
        if (exception == null) {
            // TODO: the string here should be localized, but in practice the
            // exception is never null
            //addError("transformation error: ", exception, null, null);
            addError('\n'+parentException.getClass().getName() + ": ", parentException, null, null);
        } else {
            if (exception instanceof ExprException) {
                // recurse into this wrapper
                addError(exception, ((ExprException)exception).getException());
            } else
            if (exception instanceof InvocationTargetException) {
                // recurse into this wrapper
                addError(exception, ((InvocationTargetException) exception).getTargetException());
            } else
            if (exception instanceof TransformerException) {
                // recurse into this wrapper
                addError(exception, ((TransformerException) exception).getException());
            } else {
                addError('\n'+exception.getClass().getName() + ": ", exception, null, null);
            }
        }
    }

    /**
     * Add an error to the collection
     * @param mainMsg the main message about the error, e.g. "data validation error"
     * @param t the exception associated with the error
     * @param code the code string for the error, may be null
     * @param context the data context for the error, may be null
     */
    protected void addError(String mainMsg, Throwable t, String code, XmlContext context) {
        StringBuffer errMsg = new StringBuffer(mainMsg);

        if (t == null) {
            errMsg.append("null");
        } else {
            errMsg.append(t.getLocalizedMessage());
        }

        if (code != null && code.length() > 0) {
            errMsg.append("   (");
            errMsg.append(code);
            errMsg.append(')');
        }
        if (context != null) {
            XmlContext root = context.getRoot();
            if (root != null) {
               String xpath = XPathHelper.calculateXPath(root,
                                                         context,
                                                         XPathHelper.FORMAT_ABBREVIATE,
                                                         true);

                errMsg.append(" at ");
                errMsg.append(xpath);
            }
        }
        if (t != null) {
            String trace = getStackTrace(t);
            errMsg.append('\n');
            errMsg.append(trace);
        }

        if (errorList==null)
        {
            errorList = new ArrayList();
        }
        errorList.add(errMsg.toString());
    }

    public void error(XmlContext context, Locator locator, Exception e, String code) {
        addError("validation error: ", e, code, context);
    }

    public void fatalError(XmlContext context, Locator locator, Exception e, String code) {
        addError("validation error: ", e, code, context);
    }

    public void info(XmlContext context, Locator locator, Exception e, String code) throws SAXException {
    }

    public void remark(XmlContext context, Locator locator, Exception e, String code) throws SAXException {
    }

    public void warning(XmlContext context, Locator locator, Exception e, String code) throws SAXException {
    }

    public void error(TransformerException exception) {
        addError(exception);
    }

    public void fatalError(TransformerException exception) {
        addError(exception);
    }

    public void warning(TransformerException exception) {
    }
}
