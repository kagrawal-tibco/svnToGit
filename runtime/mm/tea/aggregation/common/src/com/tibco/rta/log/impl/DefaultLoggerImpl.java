package com.tibco.rta.log.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import com.tibco.rta.log.Level;
import com.tibco.rta.log.Logger;


public class DefaultLoggerImpl implements Logger {

    static {
		try {
			Properties logProperties = new Properties();
			InputStream in;
			// Step 1: load log4j related system properties in command line			
			String configFile = System.getProperty("log4j.configuration");
			if (configFile != null && configFile.trim().length() > 0) {				
				in = new FileInputStream(configFile);
				System.out.println("Initializing Log with Config File: " + configFile);
			}
			else {
				// Step 2: try to get log4j.properties from classpath
				in = DefaultLoggerImpl.class.getClassLoader().getResourceAsStream("log4j.properties");
				if (in != null) {
					System.out.println("Log Config File (log4j.properties) found in classpath");
				}
			}
			if (in != null) {
				logProperties.load(in);
				PropertyConfigurator.configure(logProperties);
			} else {
				// Step 3: initialize with Log4j  BasicConfigurator
				System.out.println("Initializing Log4j with BasicConfigurator.");
				BasicConfigurator.configure();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	protected org.apache.log4j.Logger delegate;

	public DefaultLoggerImpl(String name) {
		final org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger.getRootLogger();
		if (rootLogger.getName().equals(name)) {
			delegate = rootLogger;
		} else {
			delegate = org.apache.log4j.Logger.getLogger(name);
		}
	}
	
	protected DefaultLoggerImpl() {
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
