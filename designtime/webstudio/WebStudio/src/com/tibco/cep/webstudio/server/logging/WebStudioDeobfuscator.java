package com.tibco.cep.webstudio.server.logging;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.google.gwt.logging.server.StackTraceDeobfuscator;

/**
 * By default StackTraceDeobfuscator loads symbolMaps from files in fileSystem.
 * This class overrides this functionality to load sumbolMaps from within the war.
 * 
 * @author moshaikh
 * 
 */
public class WebStudioDeobfuscator extends StackTraceDeobfuscator {

	private String symbolMapsDir;
	private ServletContext servletContext;

	private static final Logger logger = Logger.getLogger(WebStudioDeobfuscator.class
			.getName());

	public WebStudioDeobfuscator(String symbolMapsDirectory, ServletContext servletContext) throws Exception {
		super(symbolMapsDirectory);
		if (validateSymbolMapsDir(symbolMapsDirectory, servletContext)) {
			this.symbolMapsDir = symbolMapsDirectory;
			this.servletContext = servletContext;
		}
		else {
			throw new Exception("Invalid directory, cannot create WebStudioDeobfuscator.");
		}
	}

	/**
	 * Reads the symbolMap file from the WebStudio war.
	 */
	@Override
	protected InputStream getSymbolMapInputStream(String permutationStrongName)
			throws IOException {
		String path = symbolMapsDir + "/" + permutationStrongName + ".symbolMap";
		InputStream is = servletContext.getResourceAsStream(path);
		logger.info("Loading symbolMap from - " + path);
		return is;
	}
	
	private boolean validateSymbolMapsDir(String symbolMapsDir, ServletContext servletContext) {
		URL url = null;
		try {
			url = servletContext.getResource(symbolMapsDir);
			logger.info("Successfully located symbolMaps directory - " + symbolMapsDir);
		} catch (MalformedURLException e) {
			logger.error("Failed to locate symbolMaps directory - " + symbolMapsDir, e);
		}
		return url != null;
	}
}
