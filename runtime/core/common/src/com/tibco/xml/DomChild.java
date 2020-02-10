package com.tibco.xml;

import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Sep 28, 2008
 * Time: 8:21:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class DomChild {

    public static NodeList getChildren(Node df) {
        NodeList nl = df.getChildNodes();
        return nl;
    }

    public static NamedNodeMap getAttributes(Node df) {
        NamedNodeMap attrs = df.getAttributes();
        return attrs;
    }

    public static Iterator<Node> getIterator(Node node) {
        return getIterator(node, null, XMLConstants.NULL_NS_URI, (short) 0);
    }

    public static Iterator<Node> getIterator(Node node,short type) {
        return getIterator(node, null, XMLConstants.NULL_NS_URI, type);
    }

    public static Iterator<Node> getIterator (Node node, QName qn) {
        return getIterator(node,qn.getLocalPart(),qn.getNamespaceURI(), (short) 0);
    }

    public static Iterator<Node> getIterator (Node node, String qName) {
        return getIterator(node, qName, XMLConstants.NULL_NS_URI, (short) 0);
    }

    public static Iterator<Node> getIterator(Node node, String qName, String namespace, short type) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        NodeList nl = getChildren(node);
        for (int i=0; i < nl.getLength(); i++) {
            Node n = nl.item(i);
            boolean isType = ((type == 0) || (type == n.getNodeType()));

            if (qName == null && isType) {
                nodes.add(n);
                continue;
            }
            if(qName != null) {
                boolean isQName = qName.equals(n.getNodeName());
                if (null == namespace) {
                    if (isQName && isType) {
                        nodes.add(n);
                        continue;
                    }
                }
                else {
                    boolean isNs = namespace == null || namespace.equals(XMLConstants.NULL_NS_URI) || n.getNamespaceURI() == null || namespace.equals(n.getNamespaceURI());
                    if (isQName && isNs && isType) {
                        nodes.add(n);
                        continue;
                    }
                }
            }
        }
        return nodes.iterator();
    }



    public static Node getChildElement(Node df, String qName) {
        return getChild(df, qName, XMLConstants.NULL_NS_URI,Node.ELEMENT_NODE);
    }
    public static Node getChildAttribute(Node df, String qName) {
        return getChild(df, qName, XMLConstants.NULL_NS_URI,Node.ATTRIBUTE_NODE);
    }

    public static Node getChildElement(Node df, QName qn) {
        return getChild(df,qn.getLocalPart(),qn.getNamespaceURI(),Node.ELEMENT_NODE);
    }

    public static Node getChildAttribute(Node df, QName qn) {
        return getChild(df,qn.getLocalPart(),qn.getNamespaceURI(),Node.ATTRIBUTE_NODE);
    }

    public static Node getChild(Node df, QName qn,short type) {
        return getChild(df,qn.getLocalPart(),qn.getNamespaceURI(),type);
    }

    
    public static Node getChild(Node df, String qName, String namespace,short type) {

        if (qName == null) return null;

        if(type == Node.ELEMENT_NODE) {

            NodeList children = getChildren(df);
            int length = children.getLength();
            for (int i=0; i < length; i++) {
                Node n = children.item(i);

                boolean isQName = qName.equals(n.getNodeName());
                if (null == namespace || namespace.equals(XMLConstants.NULL_NS_URI)) {
                    if (isQName) return n;
                }
                boolean isNs = namespace == null || namespace.equals(XMLConstants.NULL_NS_URI) || n.getNamespaceURI() == null || namespace.equals(n.getNamespaceURI());

                boolean isType = n.getNodeType() == type;
                if (isQName && isNs && isType) return n;
            }
        } else if( type == Node.ATTRIBUTE_NODE) {
            NamedNodeMap children = getAttributes(df);
            if(children == null)
                return null;
            int length = children.getLength();
            for (int i=0; i < length; i++) {
                Node n = children.item(i);

                boolean isQName = qName.equals(n.getNodeName());
                if (null == namespace || namespace.equals(XMLConstants.NULL_NS_URI)) {
                    if (isQName) return n;
                }
                boolean isNs = namespace == null || namespace.equals(XMLConstants.NULL_NS_URI) || n.getNamespaceURI() == null || namespace.equals(n.getNamespaceURI());

                boolean isType = n.getNodeType() == type;
                if (isQName && isNs && isType) return n;
            }
        }
        return null;

    }

    public static String getChildElementStringValue(Node df, String qName, String ns) {
        Node node = getChild(df, qName, ns,Node.ELEMENT_NODE);
        if (node == null) return null;
        Node textNode = node.getFirstChild();
        if (null == textNode) return null;
        return textNode.getNodeValue();
    }

    public static String getChildAttributeStringValue(Node df, String qName) {
        return getChildAttributeStringValue(df,qName,XMLConstants.NULL_NS_URI);
    }

    public static String getChildAttributeStringValue(Node df, String qName, String ns) {
        Node node = getChild(df, qName, ns,Node.ATTRIBUTE_NODE);
        if (node == null) return null;
        Node textNode = node.getFirstChild();
        if (null == textNode) return null;
        return textNode.getNodeValue();
    }

    public static String getChildStringValue(Node df, String qName, String ns,short type) {
        Node node = getChild(df, qName, ns, type);
        if (node == null) return null;
        Node textNode = node.getFirstChild();
        if (null == textNode) return null;
        return textNode.getNodeValue();
    }
}
