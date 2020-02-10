package com.tibco.cep.deployment.ui;

import java.awt.BorderLayout;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JFrame;

import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmNamespace;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmSchema;
import com.tibco.xml.schema.channel.SchemaModelHandler;
import com.tibco.xml.schema.channel.SchemaModelProvider;
import com.tibco.xml.schema.parse.SmParseSupport;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Jun 17, 2009
 * Time: 12:20:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class DemoDeploymentConfigurator extends JFrame {

    private SmElement rootElement;

    public DemoDeploymentConfigurator(SmElement smElement, XiNode clusterDoc) {
        super("Deployment Configurator");
        SchemaAwareXmlEditor editor = new SchemaAwareXmlEditor(smElement, clusterDoc);
        getContentPane().setLayout(new BorderLayout());;
        getContentPane().add(editor, BorderLayout.CENTER);

        this.setSize(600, 600);
        pack();
        this.toFront();
        this.setVisible(true);

    }

    public SmElement getRootElement() {
        return rootElement;
    }

    public XiNode getDeployableUnit() {
        return null;
    }

//    public static void main(String[] args) {
//
//        try {
//
//            com.jidesoft.utils.Lm.verifyLicense("TIBCO Software Inc.", "TIBCO BusinessEvents", "piTabGB9Ky0BLyASnMlZd7:urra403H1");
//            LookAndFeelFactory.installDefaultLookAndFeel();
//            LookAndFeelFactory.installJideExtension(LookAndFeelFactory.VSNET_STYLE);
//
//
//            InputSource source = new InputSource(new FileInputStream("Q:\\be\\leo\\tools\\schemas\\config\\cluster-config-template.xml"));
//            SmNamespaceProvider smNsProvider = new DCConfigSchemaProvider();
//            XiNode templateDoc = XiParserFactory.newInstance().parse(source, smNsProvider);
//            XiNode clusterDoc = templateDoc.copy();
//            XiNode clusterNode = clusterDoc.getFirstChild();
//            DemoDeploymentConfigurator dd = new DemoDeploymentConfigurator(null, clusterNode);
//            dd.pack();
//            dd.show();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    static class EntityResolverAndErrorHandler implements EntityResolver, ErrorHandler  {
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            System.out.printf("SystemId = %s, PublicId=%s \n", systemId, publicId);
            URL url = new URL(systemId);
            InputStream istream = url.openStream();
            InputSource is = new InputSource(istream);

            is.setSystemId(systemId);

            return is;
        }

        public void warning(SAXParseException exception) throws SAXException {
            //exception.printStackTrace();
        }

        public void error(SAXParseException exception) throws SAXException {
            //exception.printStackTrace();
        }

        public void fatalError(SAXParseException exception) throws SAXException {
            //exception.printStackTrace();
        }
    }


    private static class DCConfigSchemaProvider implements SmNamespaceProvider, SchemaModelHandler, SchemaModelProvider {

        private HashMap<String, SmNamespace> namespaces;

        public DCConfigSchemaProvider() throws Exception{
            namespaces = new HashMap<String, SmNamespace>();

            FileInputStream fis = new FileInputStream("Q:\\be\\leo\\tools\\schemas\\config\\cluster-configuration.xsd");
            InputSource is = new InputSource(fis);

            is.setSystemId("Q:\\be\\leo\\tools\\schemas\\config\\cluster-configuration.xsd");
            EntityResolverAndErrorHandler ere = new EntityResolverAndErrorHandler();

            SmSchema schema = SmParseSupport.parseSchema(is, ere, ere, this, this, SmParseSupport.SCHEMA_4_SCHEMA_CHECKS );
            this.putSchema(schema.getNamespace(), schema);

        }
        public Iterator getNamespaces() {
            throw new UnsupportedOperationException( "nobody should be calling this" );
        }

        public SmNamespace getNamespace(String namespaceURI) {
            System.out.println("URI :" + namespaceURI);
            return namespaces.get(namespaceURI);
        }

        public void putSchema(String namespaceURI, SmSchema schema) {
            namespaces.put(namespaceURI, schema);
        }

        public SmSchema getSchema(String namespaceURI) {
            return (SmSchema) namespaces.get(namespaceURI);
        }
    }
}
