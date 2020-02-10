package com.tibco.cep.kernel.service.impl;

import com.tibco.cep.kernel.service.ExceptionHandler;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Oct 6, 2006
 * Time: 2:22:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultExceptionHandler implements ExceptionHandler {
    protected Logger m_logger;
    public DefaultExceptionHandler(Logger logger) {
        m_logger = logger;
    }
    public void handleException(Exception exp, String message) {
        m_logger.log(Level.ERROR, exp, message);
    }
    
    public void handleRuleException(Exception exp, String message, String ruleUri, Object[] ruleScope) {
        handleException(exp, message);
    }
}
