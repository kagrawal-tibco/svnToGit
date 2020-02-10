/**
 * 
 */
package com.tibco.cep.bemm.runtime.service.management.process;

import java.util.regex.Pattern;

import javax.management.openmbean.TabularDataSupport;

import com.tibco.cep.bemm.common.exception.ObjectCreationException;
import com.tibco.cep.bemm.common.message.impl.MessageKey;
import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;

/**
 * @author dijadhav
 *
 */
public class BETeaAgent implements BETeaAgentMBean {
	/**
	 * Logger instance
	 */
	private static Logger LOGGER = LogManagerFactory.getLogManager().getLogger(BETeaAgentMBean.class);

	@Override
	public TabularDataSupport getLoggerNamesWithLevels() {

		try {
			final String INVOKED_METHOD = "getLoggerNamesWithLevels";
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INVOKED_METHOD, INVOKED_METHOD));
			MBeanTabularDataHandler tabularDataHandler = new MBeanTabularDataHandler(LOGGER);
			tabularDataHandler.setTabularData(INVOKED_METHOD);
			Logger[] loggers = LogManagerFactory.getLogManager().getLoggers();
			for (Logger logger : loggers) {
				Object[] values = new Object[tabularDataHandler.getNumItems()];
				values[0] = logger.getName();
				values[1] = logger.getLevel().toString();
				tabularDataHandler.put(values);
			}
			return tabularDataHandler.getTabularData(INVOKED_METHOD);
		} catch (Exception e) {
			LOGGER.log(Level.ERROR, e, e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	@Override
	public void setLogLevel(String loggerName, String level) {
		final String INVOKED_METHOD = "setLevel";
		try {
			LOGGER.log(Level.DEBUG, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.ACTIVATING_LEVEL_FOR_LOGGERNAME, level ,loggerName));

			if (loggerName == null || loggerName.trim().length() == 0) {
				throw new IllegalArgumentException(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INVALID_LOG_NAME_PATTERN));
			}
			if (level == null || level.trim().length() == 0) {
				throw new IllegalArgumentException(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INVALID_LEVEL));
			}
			Level levelObj = Level.valueOf(level);
			if (levelObj == null) {
				try {
					levelObj = Level.valueOf(new Integer(level));
				} catch (NumberFormatException ignore) {
				}
				if (levelObj == null) {
					throw new IllegalArgumentException(BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.UNKNOWN_LEVEL) + "[" + level + "]");
				}
			}
			// are we dealing with the global wild card?
			if (loggerName.equals("*") == true) {
				// yes, we are
				// register the level
				// update all existing loggers
				for (Logger logger : LogManagerFactory.getLogManager().getLoggers()) {
					logger.setLevel(levelObj);
				}
			} else {
				// no we have a specific level or level pattern
				// escape the logNamePattern and force it to lower case
				loggerName = loggerName.replaceAll("\\.", "\\\\.");
				loggerName = loggerName.replaceAll("\\*", ".*").toLowerCase();
				Pattern pattern = Pattern.compile(loggerName);
				// update all existing loggers
				for (Logger logger : LogManagerFactory.getLogManager().getLoggers()) {
					if (pattern.matcher(logger.getName().toLowerCase()).matches() == true) {
						logger.setLevel(levelObj);
					}
				}
			}

		} catch (IllegalArgumentException iae) {
			LOGGER.log(Level.INFO, iae.getMessage());
		}catch (ObjectCreationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			try {
				LOGGER.log(Level.ERROR, e, BEMMServiceProviderManager.getInstance().getMessageService().getMessage(MessageKey.INVOKING_METHOD_ERROR,INVOKED_METHOD));
			} catch (ObjectCreationException e1) {
				e1.printStackTrace();
			}
			throw e;
		}
	}
}
