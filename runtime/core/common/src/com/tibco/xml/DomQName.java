package com.tibco.xml;

import javax.xml.namespace.QName;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Feb 2, 2009
 * Time: 1:56:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class DomQName extends QName {

    private DomQName(String localPart) {
        super(localPart);
    }

    private DomQName(String namespaceURI, String localPart) {
        super(namespaceURI, localPart);
    }

    private DomQName(String namespaceURI, String localPart, String prefix) {
        super(namespaceURI, localPart, prefix);
    }

    public static DomQName makeName(String ns, String localName, String preFix) {
        return new DomQName(ns, localName, preFix);
    }

    public static DomQName makeName(String ns, String localName) {
        return new DomQName(ns, localName);
    }

    public static DomQName makeName(String localName) {
       return new DomQName(null,localName);
   }


}
