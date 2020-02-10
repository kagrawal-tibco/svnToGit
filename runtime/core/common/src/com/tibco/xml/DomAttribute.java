package com.tibco.xml;


import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Sep 28, 2008
 * Time: 8:31:32 AM
 * To change this template use File | Settings | File Templates.
 */

public class DomAttribute {

    public static String getStringValue(Node n, String name) {
        NamedNodeMap map = n.getAttributes();
        Node attrNode = map.getNamedItem(name);
        if (null == attrNode) return null;
        return attrNode.getNodeValue();
    }


    public static String getStringValue(Node n, String localName, String nameSpace) {
        NamedNodeMap map = n.getAttributes();
        Node attrNode = map.getNamedItemNS(localName, nameSpace);
        if (null == attrNode) return null;
        return attrNode.getNodeValue();
    }
}
