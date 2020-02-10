package com.tibco.be.util.config.topology;


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

import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmSchema;
import com.tibco.xml.schema.channel.SchemaModelHandler;
import com.tibco.xml.schema.channel.SchemaModelProvider;
import com.tibco.xml.schema.parse.SmParseSupport;

public class TopoSchemaProvider implements SmNamespaceProvider,
		SchemaModelHandler, SchemaModelProvider {

	private final HashMap<String, SmNamespace> namespaces;

	public TopoSchemaProvider()  throws SAXException, IOException {
		namespaces = new HashMap<String, SmNamespace>();
        final URL schemaUrl = TopoSchemaProvider.class.getResource("/BEMMTopology.xsd");
		final InputSource is = new InputSource(schemaUrl.openStream());
		is.setSystemId(schemaUrl.getPath());
		//String schemaFile = "Q:\\be\\trunk\\leo\\em\\src\\BEMMTopology.xsd";
		//InputStream istream = new FileInputStream(new File(schemaFile));
		//final InputSource is = new InputSource(istream);
        //is.setSystemId(schemaFile);

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
