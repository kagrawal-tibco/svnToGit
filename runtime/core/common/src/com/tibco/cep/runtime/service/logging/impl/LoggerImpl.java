package com.tibco.cep.runtime.service.logging.impl;


import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

/*
* User: Nicolas Prade
* Date: Oct 27, 2009
* Time: 7:58:01 PM
*/


public class LoggerImpl
        implements Logger {


    protected org.apache.log4j.Logger delegate;


    protected LoggerImpl(String name) {
        final org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger.getRootLogger();
        if (rootLogger.getName().equals(name)) {
            this.delegate = rootLogger;
        } else {
            this.delegate = org.apache.log4j.Logger.getLogger(name);
        }
    }


     private static String addComponent(String component, String msg) {
        if (null == component) {
            final RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (null != session) {
                return "[" + session.getLogComponent() + "] " + msg;
            }
        } else {
            return "[" + component + "] " + msg;
        }
        return msg;
    }


    public void close() {
        this.delegate.removeAllAppenders();
    }


    public String getName() {
        return this.delegate.getName();
    }


    public Level getLevel() {
        return Level.valueOf(this.delegate.getEffectiveLevel().toInt());
    }


    public boolean isEnabledFor(Level level) {
        return this.delegate.isEnabledFor(org.apache.log4j.Level.toLevel(level.toInt()));
    }


    public void log(Level level, String msg) {
        final org.apache.log4j.Level delegateLevel = org.apache.log4j.Level.toLevel(level.toInt());
        if (this.delegate.isEnabledFor(delegateLevel)) {
            this.delegate.log(delegateLevel, addComponent(null, msg));
        }
    }


    public void log(Level level, String format, Object... arguments) {
    	if (arguments == null || arguments.length == 0) {
    		log(level, format);
    		return;
    	}
        final org.apache.log4j.Level delegateLevel = org.apache.log4j.Level.toLevel(level.toInt());
        if (this.delegate.isEnabledFor(delegateLevel)) {
            this.delegate.log(delegateLevel, addComponent(null, String.format(format, arguments)));
        }
    }


    public void log(Level level, String format, Throwable thrown, Object... arguments) {
    	if (arguments == null || arguments.length == 0) {
    		log(level, thrown, format);
    		return;
    	}
        final org.apache.log4j.Level delegateLevel = org.apache.log4j.Level.toLevel(level.toInt());
        if (this.delegate.isEnabledFor(delegateLevel)) {
            this.delegate.log(delegateLevel, addComponent(null, String.format(format, arguments)), thrown);
        }
    }


    public void log(Level level, Throwable thrown, String msg) {
        final org.apache.log4j.Level delegateLevel = org.apache.log4j.Level.toLevel(level.toInt());
        if (this.delegate.isEnabledFor(delegateLevel)) {
            this.delegate.log(delegateLevel, addComponent(null, msg), thrown);
        }
    }


    public void log(Level level, Throwable thrown, String format, Object... arguments) {
    	if (arguments == null || arguments.length == 0) {
    		log(level, thrown, format);
    		return;
    	}
        final org.apache.log4j.Level delegateLevel = org.apache.log4j.Level.toLevel(level.toInt());
        if (this.delegate.isEnabledFor(delegateLevel)) {
            this.delegate.log(delegateLevel, addComponent(null, String.format(format, arguments)), thrown);
        }
    }


    public void setLevel(Level level) {
        this.delegate.setLevel(org.apache.log4j.Level.toLevel(level.toInt()));
    }
}
