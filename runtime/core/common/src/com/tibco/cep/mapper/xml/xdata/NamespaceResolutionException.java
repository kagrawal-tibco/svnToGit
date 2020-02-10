// copyright 2001 TIBCO Software Inc

package com.tibco.cep.mapper.xml.xdata;

import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

/**
 * When XMLIO can't find namespace. (binding runner needs to recognize this). Maybe move, standardize.
 */
public final class NamespaceResolutionException extends SAXParseException {
    private String mURI;

    public NamespaceResolutionException(String namespaceURI, Locator locator) {
        super("Could not resolve namespace: " + namespaceURI,locator);
        mURI = namespaceURI;
    }

    public NamespaceResolutionException(String namespaceURI, Locator locator, Exception exception) {
        super("Could not resolve namespace: " + namespaceURI, locator, exception);
        mURI = namespaceURI;
    }

    public String getURI() {
        return mURI;
    }
}
