package com.tibco.cep.runtime.service.management.agent.impl;

import javax.management.MBeanException;

/**
 * Created with IntelliJ IDEA.
 * User: hlouro
 * Date: 10/23/12
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class StatsMBeanException extends MBeanException {
    public StatsMBeanException(Exception e) {
        super(e);
    }

    public StatsMBeanException(Exception e, String message) {
        super(e, message);
    }
}
