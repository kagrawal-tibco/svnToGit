/**
 * 
 */
package com.tibco.cep.security.authz.core.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.tibco.cep.security.authz.core.ACL;
import com.tibco.cep.security.authz.exceptions.AuthzConfigurationException;
import com.tibco.cep.security.authz.utils.IOUtils;

/**
 * @author aathalye
 *
 */
public class ACLConfigurationParser2 {
	
	private InputStream is;
	
	private ACL acl;
	
	private SAXParser parser;
	
	
	//private static PluginLoggerImpl TRACE = LoggerRegistry.getLogger(PluginIds.PLUGIN_SECURITY);
			
	protected ACLConfigurationParser2(final String configFile) throws AuthzConfigurationException {
		try {
			init(configFile);
		} catch (Exception e) {
			throw new AuthzConfigurationException("Policy file for authorization not found", e);
		}
	}
	
	public ACLConfigurationParser2(final InputStream stream) throws AuthzConfigurationException {
		try {
			init(stream);
		} catch (Exception e) {
			throw new AuthzConfigurationException("Policy file for authorization not found", e);
		}
	}
	
	private void init(final String configFile) throws Exception {
		is = new FileInputStream(configFile);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		IOUtils.writeBytes(is, bos);
		byte[] bytes = bos.toByteArray();
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		is = bis;
		init(is);
	}
	
	private void init(final InputStream stream) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		//factory.setValidating(true);
		//factory.setNamespaceAware(true);
		parser = factory.newSAXParser();
		is = stream;
		acl = new ACLImpl();
	}
	
	public ACL parse() throws Exception {
		ACLConfigurationHandler handler = new ACLConfigurationHandler();
		XMLReader reader = parser.getXMLReader();
		reader.setErrorHandler(handler);
		reader.setContentHandler(handler);
		if (is != null) {
			if (is.markSupported())
				//Reset the buffer position
				is.reset();
		}
		reader.parse(new InputSource(is));
		acl.setResources(handler.getResources());
		acl.setACLEntries(handler.getACLEntries());
		return acl;
	}
}
