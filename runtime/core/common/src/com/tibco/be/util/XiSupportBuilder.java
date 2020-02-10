package com.tibco.be.util;

import java.util.ArrayList;
import java.util.Iterator;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.nodes.Element;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmNamespaceConsumer;
import com.tibco.xml.schema.SmNamespaceProvider;
import com.tibco.xml.schema.SmType;
import com.tibco.xml.schema.helpers.NoNamespace;



public class XiSupportBuilder implements XmlContentHandler, SmNamespaceConsumer
{
//    private final XiFactory m_factory;

    private SmNamespaceProvider m_schemas;

    private XiNode m_currentNode;

    private Locator m_locator;

    private ArrayList m_pendingNamespaces;


    public XiSupportBuilder()
    {
//        this(XiFactoryFactory.newInstance());
//        this(XiSupport.getXiFactory());
    }

//    public XiSupportBuilder(XiFactory factory)
//    {
//        if (null == factory)
//        {
//            RuntimeException e = new IllegalArgumentException("factory is null");
//
//            e.printStackTrace();
//
//            throw e;
//        }
//
////        m_factory = factory;
//    }



    public void setNamespaceProvider(SmNamespaceProvider provider)
    {
        m_schemas = provider;
    }

    public void setDocumentLocator(Locator locator)
    {
        m_locator = locator;
    }

    public void startDocument() throws SAXException
    {
        if (m_locator != null)
        {
            m_currentNode = XiSupport.getXiFactory().createDocument(m_locator.getSystemId());

            m_currentNode.setLineNumber(m_locator.getLineNumber());
            m_currentNode.setColumnNumber(m_locator.getColumnNumber());
        }
        else
        {
            m_currentNode = XiSupport.getXiFactory().createDocument();
        }
    }

    public void endDocument() throws SAXException
    {
        if (null != m_schemas){
        }
    }

    public void startFragment() throws SAXException
    {
        if (m_locator != null)
        {
            m_currentNode = XiSupport.getXiFactory().createFragment(m_locator.getSystemId());

            m_currentNode.setLineNumber(m_locator.getLineNumber());
            m_currentNode.setColumnNumber(m_locator.getColumnNumber());
        }
        else
        {
            m_currentNode = XiSupport.getXiFactory().createFragment();
        }
    }

    public void endFragment() throws SAXException
    {
    }

    public void prefixMapping(String prefix, String uri) throws SAXException
    {
        XiNode namespace;

        if (null != uri)
        {
            namespace = XiSupport.getXiFactory().createNamespace(prefix, uri);
        }
        else
        {
            namespace = XiSupport.getXiFactory().createNamespace(prefix, NoNamespace.URI);
        }

        // performance... don't create the arrayList until we need it...
        if (m_pendingNamespaces == null)
            m_pendingNamespaces = new ArrayList();
        m_pendingNamespaces.add(namespace);
    }

    public void startElement(ExpandedName name, SmElement declaration, SmType type) throws SAXException
    {
        // The current node is almost always an Element object so we can typecast
        // here, which allows the JRE to inline the appendElement call... and then
        // typecast the returned value so the JRE can inline the setDeclaration and
        // setType calls... performance...
        Element element;
        if (m_currentNode instanceof Element)
        {
            Element currentElement = (Element)m_currentNode;
            element = (Element)currentElement.appendElement(name);
        }
        else
        {
            element = (Element)m_currentNode.appendElement(name);
        }

        if (null != declaration)
        {
            element.setDeclaration(declaration);
        }

        if (null != type)
        {
            element.setType(type);
        }

        if (m_locator != null)
        {
            element.setLineNumber(m_locator.getLineNumber());
            element.setColumnNumber(m_locator.getColumnNumber());
        }

        if (m_pendingNamespaces != null && !m_pendingNamespaces.isEmpty())
        {
            Iterator i = m_pendingNamespaces.iterator();
            while (i.hasNext())
            {
                XiNode namespace = (XiNode)i.next();
                element.setNamespace(namespace);
            }
            m_pendingNamespaces.clear();
        }

        m_currentNode = element;
    }

    public void endElement(ExpandedName name, SmElement declaration, SmType type) throws SAXException
    {
        // The current node is almost always an Element object so we can typecast
        // here, which allows the JRE to inline the getParentNode call... performance...
        if (m_currentNode instanceof Element)
        {
            Element currentElement = (Element)m_currentNode;
            m_currentNode = currentElement.getParentNode();
        }
        else
        {
            m_currentNode = m_currentNode.getParentNode();
        }
    }

    public void attribute(ExpandedName name, XmlTypedValue typval, SmAttribute declaration) throws SAXException
    {
        XiNode attribute = m_currentNode.setAttributeTypedValue(name, typval, declaration);

        if (m_locator != null)
        {
            attribute.setLineNumber(m_locator.getLineNumber());
            attribute.setColumnNumber(m_locator.getColumnNumber());
        }
    }

    public void attribute(ExpandedName name, String strval, SmAttribute declaration) throws SAXException
    {
        XiNode attribute = m_currentNode.setAttributeStringValue(name, strval);

        if (m_locator != null)
        {
            attribute.setLineNumber(m_locator.getLineNumber());
            attribute.setColumnNumber(m_locator.getColumnNumber());
        }
    }

    public void text(String value, boolean reserved) throws SAXException
    {
        // Note: Line and column numbering is not supported on text nodes.

        m_currentNode.appendText(value);
    }

    public void text(XmlTypedValue value, boolean reserved) throws SAXException
    {
        // The current node should always be an Element object so we can typecast
        // here, which allows the JRE to inline the appendText call.... or in this
        // case, hard code the call, instead of doing a virtual call...

        // Note: Line and column numbering is not supported on text nodes.
        if (m_currentNode instanceof Element)
        {
            Element currentElement = (Element)m_currentNode;
            currentElement.appendText(value);
        }
        else
        {
            m_currentNode.appendText(value);
        }
    }

    public void processingInstruction(String target, String data) throws SAXException
    {
        XiNode pi = XiSupport.getXiFactory().createProcessingInstruction(target, data);

        if (null != m_locator)
        {
            pi.setLineNumber(m_locator.getLineNumber());
            pi.setColumnNumber(m_locator.getColumnNumber());
        }

        m_currentNode.appendChild(pi);
    }

    public void comment(String value) throws SAXException
    {
        XiNode comment = XiSupport.getXiFactory().createComment(value);

        if (null != m_locator)
        {
            comment.setLineNumber(m_locator.getLineNumber());
            comment.setColumnNumber(m_locator.getColumnNumber());
        }

        m_currentNode.appendChild(comment);
    }

    public void ignorableWhitespace(String value, boolean reserved) throws SAXException
    {
        //we just drop them
    }

    public void skippedEntity(String name) throws SAXException
    {
    }

    /**
     * Returns the node that has been built.
     */
    public XiNode getNode()
    {
        return m_currentNode;
    }

    /**
     * Makes the builder ready for another run.
     */
    public void reset()
    {
        m_currentNode = null;
        m_locator = null;
        if (m_pendingNamespaces != null)
            m_pendingNamespaces.clear();
    }
}
