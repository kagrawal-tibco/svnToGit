package com.tibco.cep.impl.common.log;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.tibco.cep.util.Flags;

/*
* Author: Ashwin Jayaprakash / Date: Dec 16, 2009 / Time: 6:18:23 PM
*/

public class DelegatingLogger extends Logger {
    /**
     * {@value}.
     */
    public static final Level DEFAULT_JDK_LEVEL = Level.INFO;

    /**
     * {@value}.
     */
    public static final com.tibco.cep.kernel.service.logging.Level DEFAULT_DELEGATE_LEVEL =
            com.tibco.cep.kernel.service.logging.Level.INFO;

    protected com.tibco.cep.kernel.service.logging.Logger delegate;

    public DelegatingLogger(com.tibco.cep.kernel.service.logging.Logger delegate) {
        super(delegate.getName(), null);

        this.delegate = delegate;

        com.tibco.cep.kernel.service.logging.Level delegateLevel = delegate.getLevel();
        Level level = convertDelegateToJdk(delegateLevel);
        super.setLevel(level);
    }

    //-------------

    public static Level convertDelegateToJdk(
            com.tibco.cep.kernel.service.logging.Level delegateLevel) {
        if (Flags.DEBUG) {
            return Level.FINE;
        }

        if (delegateLevel == com.tibco.cep.kernel.service.logging.Level.ERROR) {
            return Level.SEVERE;
        }
        else if (delegateLevel == com.tibco.cep.kernel.service.logging.Level.WARN) {
            return Level.WARNING;
        }
        else if (delegateLevel == com.tibco.cep.kernel.service.logging.Level.INFO) {
            return Level.INFO;
        }
        else if (delegateLevel == com.tibco.cep.kernel.service.logging.Level.DEBUG) {
            return Level.FINE;
        }
        else if (delegateLevel == com.tibco.cep.kernel.service.logging.Level.ALL) {
            return Level.ALL;
        }
        else if (delegateLevel == com.tibco.cep.kernel.service.logging.Level.OFF) {
            return Level.OFF;
        }

        return DEFAULT_JDK_LEVEL;
    }

    public static com.tibco.cep.kernel.service.logging.Level convertJdkToDelegate(Level jdkLevel) {
        switch (jdkLevel.intValue()) {
            case 1000 /*Level.SEVERE*/:
                return com.tibco.cep.kernel.service.logging.Level.ERROR;

            case 900 /*Level.WARNING*/:
                return com.tibco.cep.kernel.service.logging.Level.WARN;

            case 800/*Level.INFO*/:
                return com.tibco.cep.kernel.service.logging.Level.INFO;

            case 700 /*Level.CONFIG*/:
            case 500 /*Level.FINE*/:
            case 400 /*Level.FINER*/:
            case 300 /*Level.FINEST*/: {
                if (Flags.DEBUG) {
                    return com.tibco.cep.kernel.service.logging.Level.INFO;
                }

                return com.tibco.cep.kernel.service.logging.Level.DEBUG;
            }

            case Integer.MIN_VALUE /*Level.ALL*/:
                return com.tibco.cep.kernel.service.logging.Level.ALL;

            case Integer.MAX_VALUE/*Level.OFF*/:
                return com.tibco.cep.kernel.service.logging.Level.OFF;

            default:
                return DEFAULT_DELEGATE_LEVEL;
        }
    }

    //-------------

    @Override
    public boolean isLoggable(Level level) {
        com.tibco.cep.kernel.service.logging.Level delegateLevel = convertJdkToDelegate(level);

        return delegate.isEnabledFor(delegateLevel);
    }

    @Override
    public void setLevel(Level newLevel) throws SecurityException {
        //When JVM shuts down, the JDK loggers are reset by the VM and this param is null then.
        if (newLevel == null) {
            newLevel = DEFAULT_JDK_LEVEL;
        }

        com.tibco.cep.kernel.service.logging.Level delegateLevel = convertJdkToDelegate(newLevel);
        delegate.setLevel(delegateLevel);

        //Just so that the conversions and back conversions are correct.
        Level allowedLevel = convertDelegateToJdk(delegateLevel);
        super.setLevel(allowedLevel);
    }

    @Override
    public void log(LogRecord record) {
        Level level = record.getLevel();
        com.tibco.cep.kernel.service.logging.Level delegateLevel = convertJdkToDelegate(level);

        Throwable throwable = record.getThrown();
        if (throwable == null) {
            delegate.log(delegateLevel, record.getMessage());
        }
        else {
            delegate.log(delegateLevel, throwable, record.getMessage());
        }
    }
}
