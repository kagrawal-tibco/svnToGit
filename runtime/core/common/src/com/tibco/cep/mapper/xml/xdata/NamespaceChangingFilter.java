package com.tibco.cep.mapper.xml.xdata;

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tibco.sax.AttributesImpl;

/**
 * A SAX filter which performs URL->URL namespace updates.
 */
public class NamespaceChangingFilter extends DefaultHandler {
    private ContentHandler mTo;
    private HashMap mFromToNamespaceMap;
    // Re-used attribute table, updated attributes set here.
    private AttributesImpl mAttributes  = new AttributesImpl();

    /**
     * Constructs the filter.
     * @param map Map of oldnamespace (String) to new namespace (String).
     * @param to The next step handler.
     */
    public NamespaceChangingFilter(HashMap map, ContentHandler to) {
        mTo = to;
        mFromToNamespaceMap = map;
    }

    public void startPrefixMapping(String prefix, String ns) throws SAXException {
        mTo.startPrefixMapping(prefix,updateNamespace(ns));
    }

    public void characters(char[] chars, int i, int i1) throws SAXException {
        mTo.characters(chars,i,i1);
    }

    public void startElement(String ns, String ln, String qn, Attributes attributes) throws SAXException {
        updateAttributeNamespaces(attributes);
        mTo.startElement(updateNamespace(ns), ln, qn, mAttributes);
    }

    public void endElement(String ns, String ln, String qn) throws SAXException {
        mTo.endElement(updateNamespace(ns), ln, qn);
    }

    public void setDocumentLocator(Locator locator) {
        mTo.setDocumentLocator(locator);
    }

    public void endPrefixMapping(String s) throws SAXException {
        mTo.endPrefixMapping(s);
    }

    public void ignorableWhitespace(char[] chars, int i, int i1) throws SAXException {
        mTo.ignorableWhitespace(chars,i,i1);
    }

    public void processingInstruction(String s, String s1) throws SAXException {
        mTo.processingInstruction(s,s1);
    }

    public void skippedEntity(String s) throws SAXException {
        mTo.skippedEntity(s);
    }

    public void startDocument() throws SAXException {
        mTo.startDocument();
    }

    public void endDocument() throws SAXException {
        mTo.endDocument();
    }

    private String updateNamespace(String ns) {
        if (ns==null) {
            return null;
        }
        String nns = (String) mFromToNamespaceMap.get(ns);
        if (nns!=null) {
            return nns;
        } else {
            return ns;
        }
    }

    private void updateAttributeNamespaces(Attributes attrs) {
        mAttributes.clear();
        int ct = attrs.getLength();
        for (int i=0;i<ct;i++) {
            mAttributes.addAttribute(
                    updateNamespace(attrs.getURI(i)),
                    attrs.getLocalName(i),
                    attrs.getQName(i),
                    attrs.getType(i),
                    attrs.getValue(i)
            );
        }
    }
}

