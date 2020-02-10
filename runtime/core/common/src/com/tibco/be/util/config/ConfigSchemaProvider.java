package com.tibco.be.util.config;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmSchema;
import com.tibco.xml.schema.channel.SchemaModelHandler;
import com.tibco.xml.schema.channel.SchemaModelProvider;
import com.tibco.xml.schema.parse.SmParseSupport;
/**
 * 
 * @author pdhar
 *
 */
public class ConfigSchemaProvider implements SmNamespaceProvider,
		SchemaModelHandler, SchemaModelProvider {

	private final HashMap<String, SmNamespace> namespaces;

	public ConfigSchemaProvider() throws SAXException, IOException {
		namespaces = new HashMap<String, SmNamespace>();

        InputSource is;
        URL schemaUrl = ClusterConfig.class.getResource("/cdd-5.6.xsd");
        if (null != schemaUrl) {
            is = new InputSource(schemaUrl.openStream());
        } else {
            is = new InputSource(ClusterConfig.class.getResourceAsStream("/cdd-5.6.xsd"));
            schemaUrl = ClusterConfig.class.getProtectionDomain().getCodeSource().getLocation();
        }
        is.setSystemId(schemaUrl.getPath());

		final EntityResolver entResolver = new EntityResolver() {
			public InputSource resolveEntity(final String publicId, final String systemId)
					throws SAXException, IOException {
				System.out.printf("SystemId = %s, PublicId=%s \n", systemId,
						publicId);
				final URL url = new URL(systemId);
				final InputStream istream = url.openStream();
				final InputSource is = new InputSource(istream);

				is.setSystemId(systemId);

				return is;
			}

		};

		final ErrorHandler errHandler = new ErrorHandler() {
			@Override
			public void error(final SAXParseException exception) throws SAXException {
				// TODO Auto-generated method stub

			}

			@Override
			public void fatalError(final SAXParseException exception)
					throws SAXException {
				// TODO Auto-generated method stub

			}

			@Override
			public void warning(final SAXParseException exception)
					throws SAXException {
				// TODO Auto-generated method stub

			}
		};
		
		final SmSchema schema = SmParseSupport.parseSchema(is, entResolver,
				errHandler, this, this, SmParseSupport.SCHEMA_4_SCHEMA_CHECKS);
		this.putSchema(schema.getNamespace(), schema);
	}

	
	@Override
	public Iterator getNamespaces() {
		return namespaces.values().iterator();
	}
	
	@Override
	public SmNamespace getNamespace(String namespaceURI) {
//        System.out.println("URI: " + namespaceURI);
        return namespaces.get(namespaceURI);
    }
	
	@Override
    public void putSchema(String namespaceURI, SmSchema schema) {
        namespaces.put(namespaceURI, schema);
    }
	
	@Override
    public SmSchema getSchema(String namespaceURI) {
        return (SmSchema) namespaces.get(namespaceURI);
    }

	

}
