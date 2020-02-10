package com.tibco.be.rms.util;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: Sep 1, 2008
 * Time: 12:21:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class LazyLoadingImplParser {

    private static SAXParser parser;

    static {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newSAXParser();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private File file;

    private ArtifactAttributesReader handler;

    public LazyLoadingImplParser(final String file) throws Exception {
        init(file);
    }

    private void init(final String filePath) throws Exception {
        file = new File(filePath);
    }

    public void parse() throws Exception {
        XMLReader reader = parser.getXMLReader();
        String fileName = file.getName();
        String extension = fileName.substring(fileName.indexOf('.') + 1, fileName.length());
        handler = LazyLoadingDeserializerFactory.INSTANCE.getDeserializer(extension);
        if (handler instanceof DefaultHandler2) {
            reader.setContentHandler((DefaultHandler2)handler);
        }
        try {
            InputStream is = new FileInputStream(file);
            reader.parse(new InputSource(is));
        } catch (ParsingCompletedException pe) {
            return;
        }
    }

    public ArtifactAttributesReader getAttributesReader() {
        return handler;
    }
}
