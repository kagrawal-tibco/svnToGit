package com.tibco.cep.kernel.service;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Oct 6, 2006
 * Time: 2:08:12 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ExceptionHandler {
    public void handleException(Exception ex, String message);
    public void handleRuleException(Exception ex, String message, String ruleUri, Object[] ruleScope);
}
