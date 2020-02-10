package com.tibco.rta.model.serialize.impl;

import com.tibco.rta.model.serialize.SerializationTarget;
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 17/10/12
 * Time: 2:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class XMLSerializationTarget implements SerializationTarget<Document> {

    private boolean isPrettyPrint;

    public XMLSerializationTarget(boolean isPrettyPrint) {
        this.isPrettyPrint = isPrettyPrint;
    }

    public XMLSerializationTarget() {
        this(false);
    }

    @Override
    public void persist(File file, Document rootDocument) throws Exception {
        //Write this out
        DOMSource domSource = new DOMSource(rootDocument);
        FileWriter writer = new FileWriter(file);
        StreamResult result = new StreamResult(writer); 
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        try {
            transformer.setOutputProperty(OutputKeys.INDENT, isPrettyPrint ? "yes" : "no");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(domSource, result);
            writer.flush();
        } finally {
            writer.close();
        }
    }


    /**
     * Persist the target to writer based on type of serialization.
     *
     * @param writer
     * @throws Exception
     */
    @Override
    public void persist(Writer writer, Document rootDocument) throws Exception {
        //Write this out
        DOMSource domSource = new DOMSource(rootDocument);
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        try {
        	transformer.setOutputProperty(OutputKeys.INDENT, isPrettyPrint ? "yes" : "no");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(domSource, result);
            writer.flush();
        } finally {
            writer.close();
        }
    }

    @Override
    public void persist(OutputStream outputStream, Document rootDocument) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
