package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.ArrayList;
import java.util.Iterator;

import com.tibco.cep.mapper.xml.xdata.DefaultNamespaceMapper;
import com.tibco.xml.data.primitive.AtomSequence;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.data.primitive.SerializationContext;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.data.primitive.XmlUberType;
import com.tibco.xml.data.primitive.values.AbstractXmlAtomicValue;
import com.tibco.xml.data.primitive.values.XsString;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.SmType;

/**
 * An XType atomic value.
 */
public class XsTypeDeclaration extends AbstractXmlAtomicValue
{
    private SmSequenceType m_type;

    public XsTypeDeclaration(SmSequenceType t)
    {
        m_type = t;
    }

    public String castAsString()
    {
        return null;
    }

    public XmlUberType getUberType()
    {
        return null;
    }

    public boolean isEqualOrDerived(ExpandedName expandedName)
    {
        return false;
    }

    public ExpandedName getTypeName()
    {
        return null;
    }

    public boolean isUnknownType()
    {
        return false;
    }

    public XmlTypedValue getRequiredInScopeNamespaces(SerializationContext serializationContext)
    {
        DefaultNamespaceMapper nm = new DefaultNamespaceMapper();
        m_type.registerAllNamespaces(nm);
        Iterator i = nm.getNamespaceIterator();
        if (!i.hasNext())
        {
            return AtomSequence.EMPTY;
        }
        String f = (String) i.next();
        XmlAtomicValue v = new XsString(f);
        if (!i.hasNext())
        {
            return new XsString(f);// going to URL is probably a really bad idear.
        }
        ArrayList ret = new ArrayList();
        ret.add(v);
        while (i.hasNext())
        {
            String ns = (String) i.next();
            ret.add(new XsString(ns));
        }
        return new AtomSequence((XmlAtomicValue[])ret.toArray(new XmlAtomicValue[ret.size()]));
    }

    public boolean containsNewLineChar()
    {
        return false;
    }

    public String getSerializedForm(NamespaceToPrefixResolver namespaceToPrefixResolver)
    {
        return null;
    }

    public String getLegacySerializedForm(SerializationContext sc)
    {
        return null;
    }

    public String getSerializedForm(SerializationContext serializationContext)
    {
        return null;
    }

    public SmType getType()
    {
        return null;
    }

    public boolean isWhitespace()
    {
        return false;
    }
}

