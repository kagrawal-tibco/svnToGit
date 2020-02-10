package com.tibco.cep.studio.core.migration.helper;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/**
 * Use sax parsing to parse a .beproject file.
 * @author aathalye
 *
 */
public class BEProjectConfigFileParser {
	
	private SAXParser parser;
	
	/**
	 * Root directory of project
	 */
	private File parentDirectory;
	
	/**
	 * The .beproject file
	 */
	private File configFile;
	
	private IProgressMonitor progressMonitor;
	
	
	/**
	 * 
	 * @param configFile
	 */
	public BEProjectConfigFileParser(File parentDirectory, File configFile, IProgressMonitor progressMonitor) throws Exception {
		this.parentDirectory = parentDirectory;
		this.configFile = configFile;
		this.progressMonitor = progressMonitor;
		init(configFile);
	}

	private void init(final File configFile) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		parser = factory.newSAXParser();
		this.configFile = configFile;
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void parse() throws Exception {
		BEProjectConfigurationHandler handler = new BEProjectConfigurationHandler(parentDirectory, progressMonitor);
		XMLReader reader = parser.getXMLReader();
		reader.setErrorHandler(handler);
		reader.setContentHandler(handler);
		reader.parse(new InputSource(new FileInputStream(configFile)));
	}
}
