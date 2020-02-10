package com.tibco.cep.query.stream.framework;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
 * Author: Ashwin Jayaprakash Date: Mar 10, 2008 Time: 5:45:33 PM
 */

public class SimpleLogger extends Logger implements com.tibco.cep.kernel.service.logging.Logger {
    @Override
    public void log(LogLevel level, String message) {
        System.err.println(message);
    }

    public void log(LogLevel level, ResourceId resourceId, String message) {
        log(level, resourceId + " :: " + message);
    }

    @Override
    public void log(LogLevel level, Throwable throwable) {
        throwable.printStackTrace(System.err);
    }

    public void log(LogLevel level, ResourceId resourceId, Throwable throwable) {
        log(level, resourceId + " ::", throwable);
    }

    @Override
    public void log(LogLevel level, String message, Throwable throwable) {
        System.err.println(message);
        throwable.printStackTrace(System.err);
    }

    public void log(LogLevel level, ResourceId resourceId, String message,
                    Throwable throwable) {
        log(level, resourceId + " :: " + message, throwable);
    }

    // ---------

    public void debug(boolean on) {
    }

    public void error(boolean on) {
    }

    public void fatal(boolean on) {
    }

    public void info(boolean on) {
    }

    public boolean isDebug() {
        return false;
    }

    public boolean isError() {
        return true;
    }

    public boolean isFatal() {
        return true;
    }

    public boolean isInfo() {
        return true;
    }

    public boolean isUser() {
        return true;
    }

    public boolean isWarn() {
        return true;
    }

    public void logDebug(String msg) {
        System.err.println(msg);
    }

    public void logDebug(String msg, Throwable throwable) {
        System.err.println(msg);
        throwable.printStackTrace(System.err);
    }

    public void logDebug(String component, String msg, Throwable throwable) {
        System.err.println(msg);
        throwable.printStackTrace(System.err);
    }

    public void logError(String msg) {
        System.err.println(msg);
    }

    public void logError(String msg, Throwable throwable) {
        System.err.println(msg);
        throwable.printStackTrace(System.err);
    }

    public void logError(String component, String msg, Throwable throwable) {
        System.err.println(msg);
        throwable.printStackTrace(System.err);
    }

    public void logFatal(String msg) {
        System.err.println(msg);
    }

    public void logFatal(String msg, Throwable throwable) {
        System.err.println(msg);
        throwable.printStackTrace(System.err);
    }

    public void logFatal(String component, String msg, Throwable throwable) {
        System.err.println(msg);
        throwable.printStackTrace(System.err);
    }

    public void logInfo(String msg) {
        System.err.println(msg);
    }

    public void logInfo(String msg, Throwable throwable) {
        System.err.println(msg);
    }

    public void logInfo(String component, String msg, Throwable throwable) {
        System.err.println(msg);
    }

    public void logUser(String msg) {
        System.err.println(msg);
    }

    public void logUser(String msg, Throwable throwable) {
        System.err.println(msg);
    }

    public void logUser(String component, String msg, Throwable throwable) {
        System.err.println(msg);
    }

    public void logWarn(String msg) {
        System.err.println(msg);
    }

    public void logWarn(String msg, Throwable throwable) {
        System.err.println(msg);
        throwable.printStackTrace(System.err);
    }

    public void logWarn(String component, String msg, Throwable throwable) {
        System.err.println(msg);
        throwable.printStackTrace(System.err);
    }

    public void user(boolean on) {
    }

    public void warn(boolean on) {
    }

    public void close() {
    }

    @Override
    public Level getLevel() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isEnabledFor(Level level) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void log(Level level, String msg) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void log(Level level, String format, Object... args) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void log(Level level, String format, Throwable thrown, Object... args) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void log(Level level, Throwable thrown, String msg) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void log(Level level, Throwable thrown, String format, Object... args) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setLevel(Level level) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}