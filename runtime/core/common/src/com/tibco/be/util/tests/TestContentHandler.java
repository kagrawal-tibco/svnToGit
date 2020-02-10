package com.tibco.be.util.tests;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmType;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 2, 2004
 * Time: 12:10:33 PM
 * To change this template use Options | File Templates.
 */
 public class TestContentHandler implements XmlContentHandler {

     public void attribute(ExpandedName expandedName, String s, SmAttribute smAttribute) throws SAXException {
         System.out.println("attribute:-1" + expandedName + " value:" + s + "smAttribute:" +smAttribute);
     }

     public void attribute(ExpandedName expandedName, XmlTypedValue xmlTypedValue, SmAttribute smAttribute) throws SAXException {
         System.out.println("attribute:-2" + expandedName + " XmlTypedValue:" + xmlTypedValue + "smAttribute:" +smAttribute);
     }

     public void comment(String s) throws SAXException {
     }

     public void endDocument() throws SAXException {
         System.out.println("endDocument");
     }

     public void endElement(ExpandedName expandedName, SmElement smElement, SmType smType) throws SAXException {
         System.out.println("endElement:" + expandedName + " SmElement:"+smElement + "SmType:" +smType);
     }

     public void ignorableWhitespace(String s, boolean b) throws SAXException {
         System.out.println("ignoreWhiteSpace:" + s + " boolean:" + b);
     }

     public void prefixMapping(String s, String s1) throws SAXException {
         System.out.println("prefixMapping:" + s + " String s1:" + s1);
     }

     public void processingInstruction(String s, String s1) throws SAXException {
         System.out.println("pI:" + " v1 :=" +s + " v2:=" + s1);
     }

     public void setDocumentLocator(Locator locator) {
         System.out.println("setDocumentLocator:" + locator);
     }

     public void skippedEntity(String s) throws SAXException {
         System.out.println("skipped:" + s);
     }

     public void startDocument() throws SAXException {
         System.out.println("startDocument");
     }

     public void startElement(ExpandedName expandedName, SmElement smElement, SmType smType) throws SAXException {
         System.out.println("startElement:" + expandedName + " SmElement:"+smElement + "SmType:" +smType);
     }

     public void text(String s, boolean b) throws SAXException {
         System.out.println("text:" + s + "boolean" + b);
     }

     public void text(XmlTypedValue xmlTypedValue, boolean b) throws SAXException {
         System.out.println("text-2 XmlTypedValue:" + xmlTypedValue + " boolean" + b);

     }




 }

