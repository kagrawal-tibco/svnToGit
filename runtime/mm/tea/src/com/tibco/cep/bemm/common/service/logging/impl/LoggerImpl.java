package com.tibco.cep.bemm.common.service.logging.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.ConfigProperty;

/**
 * @author vdhumal
 *
 */
public class LoggerImpl implements Logger {

	static {
		try {
			Properties logProperties = new Properties();
			InputStream in;
			// Step 1: load log4j related system properties in command line
			String configFile = System.getProperty("log4j.configuration");
			if (configFile != null && configFile.trim().length() > 0) {
				in = new FileInputStream(configFile);
				System.out.println("Initializing Log with Config File: " + configFile);
			} else {
				// Step 2: try to get log4j.properties from classpath
				in = LoggerImpl.class.getClassLoader().getResourceAsStream("log4j.properties");
				if (in != null) {
					System.out.println("Log Config File (log4j.properties) found in classpath");
				}
			}
			if (in != null) {
				logProperties.load(in);
				PropertyConfigurator.configure(logProperties);
			} else {
				// Step 3: initialize with Log4j BasicConfigurator
				System.out.println("Initializing Log4j with BasicConfigurator.");
				BasicConfigurator.configure();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected org.apache.log4j.Logger delegate;

	/**
	 * @param name
	 */
	protected LoggerImpl(String name) {
		final org.apache.log4j.Logger rootLogger = org.apache.log4j.Logger.getRootLogger();
		if (rootLogger.getName().equals(name)) {
			this.delegate = rootLogger;
		} else {
			this.delegate = org.apache.log4j.Logger.getLogger(name);
		}
		String loglevel = System.getProperty(ConfigProperty.BE_TEA_AGENT_LOG_LEVEL.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_LOG_LEVEL.getDefaultValue());

		//Set log level
		if (null != loglevel && !loglevel.trim().isEmpty() && null != Level.valueOf(loglevel)) {
			setLevel(Level.valueOf(loglevel));
		} 
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
			this.delegate.log(delegateLevel, msg);
		}
	}

	public void log(Level level, String format, Object... arguments) {
		final org.apache.log4j.Level delegateLevel = org.apache.log4j.Level.toLevel(level.toInt());
		if (this.delegate.isEnabledFor(delegateLevel)) {
			this.delegate.log(delegateLevel, String.format(format, arguments));
		}
	}

	public void log(Level level, String format, Throwable thrown, Object... arguments) {
		final org.apache.log4j.Level delegateLevel = org.apache.log4j.Level.toLevel(level.toInt());
		if (this.delegate.isEnabledFor(delegateLevel)) {
			this.delegate.log(delegateLevel, String.format(format, arguments), thrown);
		}
	}

	public void log(Level level, Throwable thrown, String msg) {
		final org.apache.log4j.Level delegateLevel = org.apache.log4j.Level.toLevel(level.toInt());
		if (this.delegate.isEnabledFor(delegateLevel)) {
			this.delegate.log(delegateLevel, msg, thrown);
		}
	}

	public void log(Level level, Throwable thrown, String format, Object... arguments) {
		final org.apache.log4j.Level delegateLevel = org.apache.log4j.Level.toLevel(level.toInt());
		if (this.delegate.isEnabledFor(delegateLevel)) {
			this.delegate.log(delegateLevel, String.format(format, arguments), thrown);
		}
	}

	public void setLevel(Level level) {
		this.delegate.setLevel(org.apache.log4j.Level.toLevel(level.toInt()));
	}

}
