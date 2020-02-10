package com.tibco.be.util.tests;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.xml.sax.InputSource;

import com.tibco.be.util.XSTemplateSerializer;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiSerializer;
import com.tibco.xml.datamodel.nodes.XmlCursor4XiNode;
import com.tibco.xml.processor.XmlContentParser;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 2, 2004
 * Time: 12:12:36 PM
 * To change this template use Options | File Templates.
 */

public class TestHelper {

    public static String getXSLTTemplateFromFile(String transformFile) throws Exception {

        String s = readAsString(transformFile);
        ArrayList list = new ArrayList();
        String template = XSTemplateSerializer.deSerialize(s, list, null);
        return template;

    }

    public static String readAsString(String fileName) throws Exception {
        FileInputStream fis = new FileInputStream(fileName);
        int len = fis.available();
        byte[] buf = new byte[len];
        len = fis.read(buf);
        String s = new String(buf, 0, len-1);
        return s;
    }
    
    public static InputSource getInputSourceFromFilename(String filename) throws MalformedURLException
    {

        File file = new File(filename);
        String sourceURL = file.toURL().toExternalForm();
        InputSource source = new InputSource(sourceURL);
        source.setSystemId(sourceURL);
        return source;
    }

    public static XiNode getDocumentAsXiNode(String filename)
    {
        try
        {
            XmlCursor4XiNode cursor = new XmlCursor4XiNode();
            XmlContentParser p = new XmlContentParser(cursor);
            InputSource source = getInputSourceFromFilename(filename);
            p.parse(source);
            XiNode node = cursor.getNode();
            System.out.println(XiSerializer.serialize(node));
            return node.getFirstChild();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }



}
