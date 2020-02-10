package com.tibco.cep.mapper.xml.xdata;

import java.util.ArrayList;

import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmType;

/**
 * A ContentHandler filter that changes the XML so that the root output element gets the no-namespace namespace.
 * This is specifically for one customer (DBS) which insisted on this, for whatever reason.
 */
public class RootPrefixChangingFilter implements XmlContentHandler
{
    private XmlContentHandler mTo;
    private boolean m_seenRootElement;

    private ArrayList m_rootPrefixes = new ArrayList();
    private ArrayList m_rootURIs = new ArrayList(); // parallel to m_rootPrefixes

    /**
     * Constructs the filter.
     * @param to The next step handler.
     */
    public RootPrefixChangingFilter(XmlContentHandler to)
    {
        mTo = to;
    }

    public void attribute(ExpandedName name, String value, SmAttribute declaration) throws SAXException
    {
        mTo.attribute(name,value,declaration);
    }

    public void attribute(ExpandedName name, XmlTypedValue value, SmAttribute declaration) throws SAXException
    {
        mTo.attribute(name,value,declaration);
    }

    public void comment(String value) throws SAXException
    {
        mTo.comment(value);
    }

    public void endDocument() throws SAXException
    {
        mTo.endDocument();
    }

    public void endElement(ExpandedName name, SmElement element, SmType override) throws SAXException
    {
        mTo.endElement(name,element,override);
    }

    public void ignorableWhitespace(String value, boolean reserved) throws SAXException
    {
        mTo.ignorableWhitespace(value,reserved);
    }

    public void prefixMapping(String prefix, String uri) throws SAXException
    {
        if (!m_seenRootElement)
        {
            // store these up.
            m_rootPrefixes.add(prefix);
            m_rootURIs.add(uri);
        }
        else
        {
            mTo.prefixMapping(prefix,uri);
        }
    }

    public void processingInstruction(String target, String data) throws SAXException
    {
        mTo.processingInstruction(target,data);
    }

    public void setDocumentLocator(Locator locator)
    {
        mTo.setDocumentLocator(locator);
    }

    public void skippedEntity(String name) throws SAXException
    {
        mTo.skippedEntity(name);
    }

    public void startDocument() throws SAXException
    {
        mTo.startDocument();
    }

    public void startElement(ExpandedName name, SmElement element, SmType override) throws SAXException
    {
        if (!m_seenRootElement)
        {
            boolean foundIt = false;
            m_seenRootElement = true;
            // issue namespaces:
            for (int i=0;i<m_rootPrefixes.size();i++)
            {
                String prefix = (String) m_rootPrefixes.get(i);
                String ns = (String) m_rootURIs.get(i);
                if (ns!=null && ns.equals(name.getNamespaceURI()) && !foundIt)
                {
                    // Use default ns for root.
                    mTo.prefixMapping("",name.getNamespaceURI());
                    foundIt = true;
                }
                else
                {
                    mTo.prefixMapping(prefix,ns);
                }
            }
            if (!foundIt)
            {
                // Wasn't already issued, so issue it now:
                mTo.prefixMapping("",name.getNamespaceURI());
            }
        }
        mTo.startElement(name,element,override);
    }

    public void text(String value, boolean reserved) throws SAXException
    {
        mTo.text(value,reserved);
    }

    public void text(XmlTypedValue value, boolean reserved) throws SAXException
    {
        mTo.text(value,reserved);
    }
}

