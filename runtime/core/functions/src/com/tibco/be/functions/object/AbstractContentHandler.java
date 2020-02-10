package com.tibco.be.functions.object;

import java.util.HashMap;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmAttribute;

/**
 * User: ssubrama
 * Date: Sep 3, 2004
 * Time: 1:58:25 AM
 */
public abstract class AbstractContentHandler implements XmlContentHandler {

    HashMap pfxMapping = new HashMap();
    static ExpandedName ATTRTYPE_NM = ExpandedName.makeName("http://www.w3.org/2001/XMLSchema-instance", "type");


    public void attribute(ExpandedName expandedName, String s, SmAttribute smAttribute) throws SAXException {
    }


    public void comment(String s) throws SAXException {
    }


    public void endDocument() throws SAXException {
    }


    public void ignorableWhitespace(String s, boolean b) throws SAXException {
    }


    public void prefixMapping(String pfx, String value) throws SAXException {
        pfxMapping.put(pfx, value);
    }


    public void processingInstruction(String s, String s1) throws SAXException {
    }


    public void setDocumentLocator(Locator locator) {
    }


    public void skippedEntity(String s) throws SAXException {
    }


    public void startDocument() throws SAXException {
    }


    public void text(String s, boolean b) throws SAXException {
    }
}
