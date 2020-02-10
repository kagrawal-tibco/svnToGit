package com.tibco.cep.webstudio.server.logging;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.google.gwt.logging.server.StackTraceDeobfuscator;
import com.google.gwt.logging.shared.RemoteLoggingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Servlet class to receive and log the logMessages submitted by clients.
 * 
 * @author moshaikh
 * 
 */
@SuppressWarnings("serial")
public class WebStudioRemoteLoggingService extends RemoteServiceServlet
		implements RemoteLoggingService {

	private static final Logger logger = Logger.getLogger(WebStudioRemoteLoggingService.class.getName());
	private static final String LOG4J_CONFIGURAION_FILE = "log4j.isc.config.xml";
	private static StackTraceDeobfuscator deobfuscator = null;

	@Override
	public void init() throws ServletException {
		super.init();
		configureLog4j();
	    String symbolMapsDir = getServletConfig().getInitParameter("symbolMapsDir");
		initDeobfuscator(symbolMapsDir);
	}

	/**
	 * Loads the configuration file and configures log4j.
	 */
	private static void configureLog4j() {
		try {
			URL log4jConfig = WebStudioRemoteLoggingService.class
					.getClassLoader().getResource(LOG4J_CONFIGURAION_FILE);
			DOMConfigurator.configure(log4jConfig);
			logger.info("Log4j initialised.");
		} catch (Exception ex) {
			logger.error(
					"Failed to configure log4j using configuration file - "
							+ LOG4J_CONFIGURAION_FILE, ex);
		}
	}

	/**
	 * Logs a messages for the passed LogRecord.
	 */
	public String logOnServer(LogRecord lr) {
		if (lr == null || lr.getLoggerName() == null) {
			return null;
		}
		Logger clientLogger = Logger.getLogger(lr.getLoggerName());
		if (deobfuscator != null && lr.getThrown() != null) {
			String strongName = getPermutationStrongName();
			lr = deobfuscate(lr, strongName);
			clientLogger.error(lr.getMessage(), lr.getThrown());
			return null;
		}
		if (lr.getLevel() == Level.INFO) {
			clientLogger.info(lr.getMessage());
		} else if (lr.getLevel() == Level.SEVERE) {
			clientLogger.error(lr.getMessage());
		} else if (lr.getLevel() == Level.WARNING) {
			clientLogger.warn(lr.getMessage());
		} else {
			clientLogger.debug(lr.getMessage());
		}
		return null;
	}
	
	/**
	 * Initialises the deobfuscator with the defined symbol maps directory.
	 * @param symbolMapsDir
	 */
	private void initDeobfuscator(String symbolMapsDir) {
		logger.info("Initialising deobfuscator, symbolMapsDir: " + symbolMapsDir);
		try {
			deobfuscator = new WebStudioDeobfuscator(symbolMapsDir, getServletContext());
			logger.info("Created WebStudioDeobfuscator.");
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * Converts the symbols in stackTrace to actual names.
	 * @param lr
	 * @param strongName
	 * @return
	 */
	private LogRecord deobfuscate(LogRecord lr, String strongName) {
		if (lr.getThrown() != null && lr.getThrown().getStackTrace() != null) {
			StackTraceElement[] st = deobfuscator.deobfuscateStackTrace(lr.getThrown().getStackTrace(), strongName);
			lr.getThrown().setStackTrace(st);
    	}
		else {
			lr = deobfuscator.deobfuscateLogRecord(lr, strongName);
		}
		return lr;
	}
}
