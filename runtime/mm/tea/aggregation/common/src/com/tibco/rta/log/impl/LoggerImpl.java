package com.tibco.rta.log.impl;

import com.tibco.rta.log.Level;
import com.tibco.rta.log.Logger;


public class LoggerImpl implements Logger {

	protected org.apache.log4j.Logger delegate;

	public LoggerImpl(String name) {
		final org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger.getRootLogger();
		if (rootLogger.getName().equals(name)) {
			delegate = rootLogger;
		} else {
			delegate = org.apache.log4j.Logger.getLogger(name);
		}
	}
	
	protected LoggerImpl() {
		delegate = org.apache.log4j.Logger.getRootLogger();
	}

	private static String addComponent(String component, String msg) {
		if (null == component) {
		} else {
			return "[" + component + "] " + msg;
		}
		return msg;
	}

	public void close() {
		delegate.removeAllAppenders();
	}

	public String getName() {
		return delegate.getName();
	}

	public Level getLevel() {
		return Level.valueOf(delegate.getEffectiveLevel().toInt());
	}

	public boolean isEnabledFor(Level level) {
		return delegate.isEnabledFor(org.apache.log4j.Level.toLevel(level
				.toInt()));
	}

	public void log(Level level, String msg) {
		final org.apache.log4j.Level delegateLevel = org.apache.log4j.Level
				.toLevel(level.toInt());
		if (delegate.isEnabledFor(delegateLevel)) {
			delegate.log(delegateLevel, addComponent(null, msg));
		}
	}

	public void log(Level level, String format, Object... arguments) {
		final org.apache.log4j.Level delegateLevel = org.apache.log4j.Level
				.toLevel(level.toInt());
		if (delegate.isEnabledFor(delegateLevel)) {
			delegate.log(delegateLevel,
					addComponent(null, String.format(format, arguments)));
		}
	}

	public void log(Level level, String format, Throwable thrown,
			Object... arguments) {
		final org.apache.log4j.Level delegateLevel = org.apache.log4j.Level
				.toLevel(level.toInt());
		if (delegate.isEnabledFor(delegateLevel)) {
			delegate.log(delegateLevel,
					addComponent(null, String.format(format, arguments)),
					thrown);
		}
	}

	public void log(Level level, Throwable thrown, String msg) {
		final org.apache.log4j.Level delegateLevel = org.apache.log4j.Level
				.toLevel(level.toInt());
		if (delegate.isEnabledFor(delegateLevel)) {
			delegate.log(delegateLevel, addComponent(null, msg), thrown);
		}
	}

	public void log(Level level, Throwable thrown, String format,
			Object... arguments) {
		final org.apache.log4j.Level delegateLevel = org.apache.log4j.Level
				.toLevel(level.toInt());
		if (delegate.isEnabledFor(delegateLevel)) {
			delegate.log(delegateLevel,
					addComponent(null, String.format(format, arguments)),
					thrown);
		}
	}

	public void setLevel(Level level) {
		delegate.setLevel(org.apache.log4j.Level.toLevel(level.toInt()));
	}

}
