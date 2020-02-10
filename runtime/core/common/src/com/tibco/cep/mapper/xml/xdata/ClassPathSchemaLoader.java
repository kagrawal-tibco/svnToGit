package com.tibco.cep.mapper.xml.xdata;

import java.io.IOException;
import java.io.InputStream;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.tibco.io.provider.ClassPathProvider;
import com.tibco.io.provider.StreamProvider;
import com.tibco.util.Debug;
import com.tibco.xml.schema.SmSchema;
import com.tibco.xml.schema.parse.SmParseSupport;

/**
 * Created by IntelliJ IDEA.
 * User: sabin
 * Date: Nov 5, 2004
 * Time: 5:41:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClassPathSchemaLoader implements ErrorHandler, EntityResolver {
    public void error(SAXParseException spe) {
        printSAXParseException(spe);
        throw new IllegalStateException();
    }

    public void fatalError(SAXParseException spe) {
        printSAXParseException(spe);
        throw new IllegalStateException();
    }

    public void warning(SAXParseException spe) {
    }

    public InputSource resolveEntity (String publicId, String systemId)
        throws SAXException, IOException {

        if (systemId.startsWith(CLASSPATH_PROTOCOL)) {
            systemId = systemId.substring(CLASSPATH_PROTOCOL.length());
        }
        InputSource source = loadSchema(getClass(), systemId);
        String absolutePath = CLASSPATH_PROTOCOL + systemId;
        source.setSystemId(absolutePath);

        return source;
    }

    protected SmSchema loadSchema(String schemaPath, String schemaName) {
        return loadSchema(schemaPath, schemaName, this, this, getClass());
    }

    private static SmSchema loadSchema(String schemaPath, String schemaName, ErrorHandler errorHandler,
                                       EntityResolver entityErsolver, Class relativeTo) {
        SmSchema schema = null;
        try {
            if (!schemaPath.startsWith("/")) {
                // the top-level schemas have always an absolute path
                schemaPath = "/" + schemaPath;
            }
            InputSource source = loadSchema(relativeTo, schemaPath);
            String absolutePath = CLASSPATH_PROTOCOL + schemaPath;
            source.setSystemId(absolutePath);
            schema = SmParseSupport.parseSchema(source, entityErsolver, errorHandler, SmParseSupport.FULL_CONSTRAINT_CHECKS);
        } catch (SAXException saxe) {
            Exception unwe = Debug.unwrapException(saxe);
            unwe.printStackTrace();
            throw new IllegalStateException(unwe.getMessage());
        } catch (IOException sme) {
            Exception unwe = Debug.unwrapException(sme);
            unwe.printStackTrace();
            throw new IllegalStateException(unwe.getMessage());
        }
        return schema;
    }

    private static InputSource loadSchema(Class relativeTo, String schemaPath)
            throws IOException {

        StreamProvider provider = new ClassPathProvider(relativeTo);
        InputStream stream = provider.getStreamAtPath(schemaPath);
        InputSource source = new InputSource(stream);
        return source;
    }

    static void printSAXParseException(SAXParseException spe) {
        if (spe.getSystemId() != null)
            System.err.print(spe.getSystemId());
        if (spe.getLineNumber() >= 0)
            System.err.print(":" + spe.getLineNumber());
        if (spe.getColumnNumber() >= 0)
            System.err.print(":" + spe.getColumnNumber());
        System.err.print(" - ");
        System.err.println(spe.getMessage());
        spe.printStackTrace(System.err);
    }

    private static String CLASSPATH_PROTOCOL = "classpath://";
}
