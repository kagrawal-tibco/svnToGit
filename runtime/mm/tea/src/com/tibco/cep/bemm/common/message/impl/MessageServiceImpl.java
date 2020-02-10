package com.tibco.cep.bemm.common.message.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Properties;

import com.tibco.cep.bemm.common.service.MessageService;
import com.tibco.cep.bemm.common.service.logging.Level;
import com.tibco.cep.bemm.common.service.logging.LogManagerFactory;
import com.tibco.cep.bemm.common.service.logging.Logger;
import com.tibco.cep.bemm.common.util.ConfigProperty;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;

/**
 * This service implements the functionality defined of the MessageService
 * 
 * @author dijadhav
 *
 */
public class MessageServiceImpl extends AbstractStartStopServiceImpl implements MessageService {

	// Map which holds the property key and value
	private final Properties properties = new Properties();
	private final Properties default_Properties = new Properties();

	// Logger
	private Logger LOGGER = LogManagerFactory.getLogManager().getLogger(MessageService.class);
	private String DEFAULT_MESSAGE_FILE = "messages_en_US.properties";

	public MessageServiceImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl#init(java.
	 * util.Properties)
	 */
	@Override
	public void init(Properties configuration) throws Exception {
		LOGGER.log(Level.DEBUG, "Initializing instance of MessageService");
		super.init(configuration);
		String messagesFile = configuration.getProperty(ConfigProperty.BE_TEA_AGENT_MESSAGES_FILE.getPropertyName(),
				ConfigProperty.BE_TEA_AGENT_MESSAGES_FILE.getDefaultValue());
		
		loadFromJar();//Load properties from the file in the jar. 
		try {
			load(messagesFile);//Override properties with those found in override file.
		} catch (Exception e) {
			LOGGER.log(Level.WARN, "Failed to load the messages from " + messagesFile
					+ ". Messages will be loaded from agents jar file");
		}
		LOGGER.log(Level.DEBUG, "Initialized instance of MessageService");
	}

	/**
	 * Load message properties file from jar
	 * 
	 */

	private void loadFromJar() {
		LOGGER.log(Level.INFO, "Loading messages file jar file");
		FileInputStream inStream = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		try {
			InputStream resourceAsStream = MessageServiceImpl.class.getResourceAsStream(DEFAULT_MESSAGE_FILE);
			isr = new InputStreamReader(resourceAsStream, "UTF8");
			br = new BufferedReader(isr);
			default_Properties.load(br);
		//	properties.load(resourceAsStream);
		} catch (IOException e) {
			LOGGER.log(Level.ERROR, e.getMessage());
		} finally {
			try {
				if (null != inStream)
					inStream.close();
			} catch (Exception e) {
			}
			try {
				if (null != isr)
					isr.close();
			} catch (Exception e) {
			}
			try {
				if (null != br)
					br.close();
			} catch (Exception e) {
			}
		}
		LOGGER.log(Level.INFO, "Loaded  messages file jar file");
	}

	/**
	 * Load message properties file
	 * 
	 * @param messagesFile
	 * @throws IOException
	 */
	private void load(String messagesFile) throws IOException {
		LOGGER.log(Level.INFO, "Loading messages file");
		FileInputStream inStream = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		try {

			inStream = new FileInputStream(messagesFile);
			isr = new InputStreamReader(inStream, "UTF8");
			br = new BufferedReader(isr);
			properties.load(br);
		} finally {
			try {
				if (null != inStream)
					inStream.close();
			} catch (Exception e) {
			}
			try {
				if (null != isr)
					isr.close();
			} catch (Exception e) {
			}
			try {
				if (null != br)
					br.close();
			} catch (Exception e) {
			}
		}
		LOGGER.log(Level.INFO, "Loaded  messages file");
	}

	@Override
	public String getMessage(String key, Object... args) {
		int index = -1;
		int noOfParameters = 0;
        String search = properties.getProperty(key);		
		if (null != search) {
			while ((index = search.indexOf("{", ++index)) > -1)
			   noOfParameters++;
	     }
	    if (noOfParameters != args.length)
			 return MessageFormat.format(default_Properties.getProperty(key), args);
		else{
			if(properties.containsKey(key))
			    return MessageFormat.format(properties.getProperty(key), args);	
			
			return MessageFormat.format(default_Properties.getProperty(key), args);
		}
	}
}
