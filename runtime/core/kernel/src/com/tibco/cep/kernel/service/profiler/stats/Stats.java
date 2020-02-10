package com.tibco.cep.kernel.service.profiler.stats;

import java.io.IOException;
import java.text.DecimalFormat;

import com.tibco.cep.kernel.helper.Format;

/**
 * Created by IntelliJ IDEA.
 * User: kpang
 * Date: Mar 19, 2008
 * Time: 8:23:21 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Stats {

    public static final String BRK = System.getProperty("line.separator", "\n");
    public static final String STARTUP_ACTION_CLASS_NAME_ENDING = ".BE$$Actions$Startup";
    public static final String SHUTDOWN_ACTION_CLASS_NAME_ENDING = ".BE$$Actions$Shutdown";
    public static final String ACTIVATE_ACTION_CLASS_NAME = "com.tibco.cep.runtime.session.impl.RuleSessionImpl$ActivateAction";
    public static final String STATETIMEOUT_EVENT_CLASS_NAME = "com.tibco.cep.runtime.model.element.impl.StateMachineConceptImpl$StateTimeoutEvent";
    public static final String CODEGEN_PREFIX = Format.CODEGEN_PREFIX;
    public static final char RULE_SEPARATOR_CHAR = '.';
    public static final String DELIMITER= "\t";
    public static final String TIMESTAMP_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS";

    protected static final DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    abstract public void reset();

    abstract public void write(StringBuilder sb, String delim) throws IOException;
}
