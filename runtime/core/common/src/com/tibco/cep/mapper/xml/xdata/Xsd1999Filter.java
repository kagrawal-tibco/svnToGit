package com.tibco.cep.mapper.xml.xdata;

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import com.tibco.sax.AttributesImpl;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.flavor.XSDL;

/**
 * A SAX filter that converts a '1999' XSD instance into a 'normal' XSD instance.<br>
 * (We're going to parsey like it's 1999)
 * This involves updating namespaces mostly.
 */
public final class Xsd1999Filter extends NamespaceChangingFilter {
    private AttributesImpl mNewAttributes  = new AttributesImpl();

    private static final String XSI1999_NAMESPACE = "http://www.w3.org/1999/XMLSchema-instance";
    private static final String XSD1999_NAMESPACE = "http://www.w3.org/1999/XMLSchema";

    public Xsd1999Filter(ContentHandler handler) {
        super(NAMESPACE_MAP,handler);
    }

    private static final HashMap NAMESPACE_MAP = new HashMap();
    static {
        NAMESPACE_MAP.put(XSI1999_NAMESPACE,XSDL.INSTANCE_NAMESPACE);
        NAMESPACE_MAP.put(XSD1999_NAMESPACE,XSDL.NAMESPACE);
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes attributes) throws SAXException {
        replaceAttrs(attributes);
        super.startElement(namespaceURI,localName,qName,mNewAttributes);
    }

    private void replaceAttrs(Attributes attributes) {
        mNewAttributes.clear();
        for (int i=0;i<attributes.getLength();i++) {
            if ("null".equals(attributes.getLocalName(i)) && XSI1999_NAMESPACE.equals(attributes.getURI(i))) {
                String val = attributes.getValue(i).trim();
                String nval;
                if (val.equals("1")) {
                    nval = "true";
                } else {
                    if (val.equals("0")) {
                        nval = "false";
                    } else {
                        nval = val;
                    }
                }
                QName qn = new QName(attributes.getQName(i));
                mNewAttributes.addAttribute(
                        XSI1999_NAMESPACE,
                        "nil",
                        qn.getPrefix() + ":nil",
                        attributes.getType(i),
                        nval
                );
            } else {
                mNewAttributes.addAttribute(
                        attributes.getURI(i),
                        attributes.getLocalName(i),
                        attributes.getQName(i),
                        attributes.getType(i),
                        attributes.getValue(i)
                );
            }
        }
    }
}

