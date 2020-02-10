package com.tibco.cep.query.stream.impl.util;


import java.util.Properties;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
 * Author: Ashwin Jayaprakash Date: Mar 10, 2008 Time: 5:45:33 PM
 */

public class DelegatedLogger
        extends Logger
        implements com.tibco.cep.kernel.service.logging.Logger {

    protected com.tibco.cep.kernel.service.logging.Logger delegate;


    @Override
    public void init(Properties properties) throws Exception {
        super.init(properties);

        DelegatedLoggerInput input =
                (DelegatedLoggerInput) properties.get(DelegatedLoggerInput.KEY_INPUT);
        delegate = input.getDelegate();
    }

    @Override
    public void stop() throws Exception {
        //Do nothing. Somebody could still be using this - like CacheListener which shutsdown async.
    }

    public com.tibco.cep.kernel.service.logging.Logger getDelegate() {
        return delegate;
    }

    // ---------

    @Override
    public void log(LogLevel level, String message) {
        this.delegate.log(logLevelToLevel(level), message);
    }

    public void log(LogLevel level, ResourceId resourceId, String message) {
        log(level, resourceId + " :: " + message);
    }

    @Override
    public void log(LogLevel level, Throwable throwable) {
        this.log(level, "", throwable);
    }

    public void log(LogLevel level, ResourceId resourceId, Throwable throwable) {
        log(level, resourceId + " ::", throwable);
    }

    @Override
    public void log(LogLevel level, String message, Throwable throwable) {
        this.delegate.log(logLevelToLevel(level), throwable,  message);
    }

    public void log(LogLevel level, ResourceId resourceId, String message,
                    Throwable throwable) {
        log(level, resourceId + " :: " + message, throwable);
    }


    public Level getLevel() {
        return this.delegate.getLevel();
    }

    
    public String getName() {
        return this.getClass().getName();
    }


    public boolean isEnabledFor(Level level) {
        return this.delegate.isEnabledFor(level);
    }


    public void log(Level level, String format, Object... args) {
        this.delegate.log(level, format, args);
    }


    public void log(Level level, String msg) {
        this.delegate.log(level, msg);
    }


    public void log(Level level, Throwable thrown, String format, Object... args) {
        this.delegate.log(level, thrown, format, args);
    }


    public void log(Level level, String format, Throwable thrown, Object... args) {
        this.delegate.log(level, thrown, format, args);
    }


    public void log(Level level, Throwable thrown, String msg) {
        this.delegate.log(level, thrown, msg);
    }


    public void setLevel(Level level) {
        this.delegate.setLevel(level);
    }


    public void close() {
        delegate.close();
    }

    private static Level logLevelToLevel(LogLevel logLevel) {
        switch (logLevel) {
            case DEBUG:
                return Level.DEBUG;
            case INFO:
                return Level.INFO;
            case WARNING:
                return Level.WARN;
            case ERROR:
                return Level.ERROR;
            default:
                return Level.INFO;
        }
    }

    //------------

    public static class DelegatedLoggerInput {
        /**
         * {@value}
         */
        public static final String KEY_INPUT = DelegatedLoggerInput.class.getName();

        protected final com.tibco.cep.kernel.service.logging.Logger delegate;

        public DelegatedLoggerInput(com.tibco.cep.kernel.service.logging.Logger delegate) {
            this.delegate = delegate;
        }

        public com.tibco.cep.kernel.service.logging.Logger getDelegate() {
            return delegate;
        }
    }
}
