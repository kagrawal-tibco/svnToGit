package com.tibco.cep.query.stream.monitor;

import java.util.Properties;

import com.tibco.cep.query.stream.core.Component;

/*
 * Author: Ashwin Jayaprakash Date: Oct 15, 2007 Time: 11:26:56 AM
 */

public abstract class Logger implements Component {
    protected LogLevel logLevel;

    protected ResourceId resourceId;

    public Logger() {
        this.resourceId = new ResourceId(getClass().getName());
        this.logLevel = LogLevel.WARNING;
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public void init(Properties properties) throws Exception {
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
    }

    public void discard() throws Exception {
        resourceId.discard();
        resourceId = null;
    }

    // ---------

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public boolean isAllowed(LogLevel level) {
        return logLevel.isAllowed(level);
    }

    public abstract void log(LogLevel level, String message);

    public abstract void log(LogLevel level, ResourceId resourceId, String message);

    public abstract void log(LogLevel level, Throwable throwable);

    public abstract void log(LogLevel level, ResourceId resourceId, Throwable throwable);

    public abstract void log(LogLevel level, String message, Throwable throwable);

    public abstract void log(LogLevel level, ResourceId resourceId, String message,
                             Throwable throwable);

    // ---------

    public static enum LogLevel {
        DEBUG(0xF), INFO(0xF >> 1), WARNING(0xF >> 2), ERROR(0xF >> 3);

        private final int mask;

        LogLevel(int mask) {
            this.mask = mask;
        }

        boolean isAllowed(LogLevel otherLevel) {
            return (mask == (mask | otherLevel.mask));
        }
    }
}
