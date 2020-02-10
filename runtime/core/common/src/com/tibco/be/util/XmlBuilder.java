package com.tibco.be.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmNamespaceConsumer;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.helpers.NoNamespace;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: May 4, 2009
 * Time: 4:46:10 PM
 * To change this template use File | Settings | File Templates.
 */




public class XmlBuilder implements XmlContentHandler, SmNamespaceConsumer
{

    private XiNode m_currentNode;
    private XiNode document;

    private Locator m_locator;
    private Logger m_logger;
    Stack<XiNode> m_nodeStack;
    Map<String, String> pfxMap;
    SmNamespaceProvider smProvider;
    private List m_pendingNamespaces;

    public XmlBuilder(Logger logger)
    {
        this.m_logger = logger;
        this.m_nodeStack = new Stack<XiNode>();
        this.pfxMap = new HashMap<String, String>();
    }


    public void setNamespaceProvider(SmNamespaceProvider provider)
    {
        smProvider = provider;
    }

    public void setDocumentLocator(Locator locator)
    {
        m_locator = locator;
    }

    public void startDocument() throws SAXException
    {
        if (m_pendingNamespaces != null) {
            m_pendingNamespaces.clear();
        }

        if (m_locator != null)
        {
            m_currentNode = XiSupport.getXiFactory().createDocument(m_locator.getSystemId());
            m_nodeStack.push(m_currentNode);

            m_currentNode.setLineNumber(m_locator.getLineNumber());
            m_currentNode.setColumnNumber(m_locator.getColumnNumber());
        }
        else
        {
            m_currentNode = XiSupport.getXiFactory().createDocument();
            m_nodeStack.push(m_currentNode);
        }
        document = m_currentNode;
    }

    public void endDocument() throws SAXException
    {
        m_currentNode = m_nodeStack.pop();
    }



    public void prefixMapping(String prefix, String uri) throws SAXException
    {
        pfxMap.put(prefix, uri);


        XiNode namespace;

        if (null != uri) {
            namespace = XiSupport.getXiFactory().createNamespace(prefix, uri);
        }
        else {
            namespace = XiSupport.getXiFactory().createNamespace(prefix, NoNamespace.URI);
        }

        // performance... don't create the arrayList until we need it...
        if (m_pendingNamespaces == null)
            m_pendingNamespaces = new ArrayList();
        m_pendingNamespaces.add(namespace);
    }

    public void startElement(ExpandedName name, SmElement declaration, SmType type) throws SAXException
    {
        XiNode element = m_currentNode.appendElement(name);
        element.setType(type);
        element.setDeclaration(declaration);
        m_currentNode = element;
        m_nodeStack.push(element);

        if (m_pendingNamespaces != null && !m_pendingNamespaces.isEmpty()) {
            for (Iterator i = m_pendingNamespaces.iterator(); i.hasNext(); )
            {
                XiNode namespace = (XiNode)i.next();
                element.setNamespace(namespace);
            }
            m_pendingNamespaces.clear();
        }

    }

    public void endElement(ExpandedName name, SmElement declaration, SmType type) throws SAXException
    {
        m_nodeStack.pop();
        m_currentNode = m_nodeStack.peek();
    }

    public void attribute(ExpandedName name, XmlTypedValue typval, SmAttribute declaration) throws SAXException
    {
        m_currentNode.setAttributeTypedValue(name, typval, declaration);
    }

    public void attribute(ExpandedName name, String strval, SmAttribute declaration) throws SAXException
    {
        XiNode attribute = m_currentNode.setAttributeStringValue(name, strval);
        attribute.setDeclaration(declaration);
    }

    public void text(String value, boolean reserved) throws SAXException
    {
        m_currentNode.setStringValue(value);
    }

    public void text(XmlTypedValue value, boolean reserved) throws SAXException
    {
        m_currentNode.setTypedValue(value);
    }

    public void processingInstruction(String target, String data) throws SAXException
    {
    }

    public void comment(String value) throws SAXException
    {
    }

    public void ignorableWhitespace(String value, boolean reserved) throws SAXException
    {
    }

    public void skippedEntity(String name) throws SAXException
    {
    }

    /**
     * Returns the node that has been built.
     */
    public XiNode getNode()
    {
        return document;
    }

    public Map<String, String> getPrefixMapping() {
        return pfxMap;
    }

}

