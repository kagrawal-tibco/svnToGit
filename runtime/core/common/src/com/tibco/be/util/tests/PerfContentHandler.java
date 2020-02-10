package com.tibco.be.util.tests;

import java.util.HashMap;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.tibco.be.util.StudioTraxSupport;
import com.tibco.be.util.TemplatesArgumentPair;
import com.tibco.be.util.TraxSupport;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmType;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 2, 2004
 * Time: 12:05:32 PM
 * To change this template use Options | File Templates.
 */


public class PerfContentHandler implements XmlContentHandler {

    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    

    LinkedListMap map;
    HashMap pfxMap = new HashMap();

    public void attribute(ExpandedName expandedName, String s, SmAttribute smAttribute) throws SAXException {
        map.put(expandedName, s);
    }

    public void attribute(ExpandedName expandedName, XmlTypedValue xmlTypedValue, SmAttribute smAttribute) throws SAXException {
        map.put(expandedName, xmlTypedValue);
    }

    public void comment(String s) throws SAXException {
    }

    public void endDocument() throws SAXException {
//                System.out.println("endDocument");
        System.out.println(map);
    }

    public void endElement(ExpandedName expandedName, SmElement smElement, SmType smType) throws SAXException {
//                System.out.println("endElement:" + expandedName + " SmElement:"+smElement + "SmType:" +smType);
        map = map.prev;
    }

    public void ignorableWhitespace(String s, boolean b) throws SAXException {

    }

    public void prefixMapping(String s, String s1) throws SAXException {
        pfxMap.put(s,s1);
    }

    public void processingInstruction(String s, String s1) throws SAXException {

    }

    public void setDocumentLocator(Locator locator) {

    }

    public void skippedEntity(String s) throws SAXException {

    }

    public void startDocument() throws SAXException {
        map = new LinkedListMap();
    }

    public void startElement(ExpandedName expandedName, SmElement smElement, SmType smType) throws SAXException {
        LinkedListMap amap = new LinkedListMap();
        map.put(expandedName, amap);
        amap.prev = map;
        map = amap;

    }

    public void text(String s, boolean b) throws SAXException {
        map.put("value",s);
    }

    public void text(XmlTypedValue xmlTypedValue, boolean b) throws SAXException {
        map.put("value",xmlTypedValue);

    }



    static class LinkedListMap extends HashMap {
        LinkedListMap prev;
        LinkedListMap next;

    }

    public static void main(String args[]) {

        try {

            String transformFile = args[0];
            String inputXmlFile = args[1];

            XiNode[] nodes = new XiNode[1];
            nodes[0] = TestHelper.getDocumentAsXiNode(inputXmlFile);

            String template = TestHelper.getXSLTTemplateFromFile(transformFile);

            long startTm = System.currentTimeMillis();
            TemplatesArgumentPair tap = StudioTraxSupport.getTemplates("myKey", template);

            int n=5;
            for (int i=0; i<n; i++) {
                TraxSupport.doTransform(tap.getTemplates(), nodes, new PerfContentHandler());
                System.out.println("*******************" + i + "************");
            }

            long endTm = System.currentTimeMillis();
            System.out.println("Start Time:" + startTm);
            System.out.println("End Time:" + endTm);
            System.out.println("Time taken for " + n + " transforms:" + (endTm - startTm));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}