/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.Map;
import java.util.Set;

import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprUtilities;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;
import com.tibco.io.xml.XMLStringUtilities;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.data.primitive.QName;

/**
 * The base class for {@link ElementBinding} and {@link AttributeBinding}.
 */
public abstract class DataComponentBinding extends TemplateContentBinding
{
    protected ExpandedName m_name;
    protected boolean m_isExplicitXsd;
    protected String m_explicitNamespaceAVT; // Only if m_isExplicitXsd, optional.
    protected String m_explicitNameAVT = ""; // Only if m_isExplicitXsd, cannot be null.

    /**
     * Constructor.
     */
    public DataComponentBinding(BindingElementInfo info, ExpandedName name)
    {
        super(info);
        if (name==null)
        {
            // fail fast.
            throw new NullPointerException();
        }
        m_name = name;
        m_isExplicitXsd = false;
    }

    public DataComponentBinding(BindingElementInfo info, String optionalNsAVT, String nameAVT) {
        super(info);
        m_isExplicitXsd = true;
        m_explicitNamespaceAVT = optionalNsAVT;
        m_explicitNameAVT = nameAVT;
    }

    /**
     * Sets the name used when this is a <b>not</b> a {@link #isExplicitXslRepresentation}.
     * @param name The non null name.
     */
    public final void setLiteralName(ExpandedName name)
    {
        m_name = name;
        if (name==null)
        {
            throw new NullPointerException("Null name");
        }
    }

    public final void setExplicitNamespaceAVT(String namespace)
    {
        m_explicitNamespaceAVT = namespace;
    }

    public final String getExplicitNamespaceAVT()
    {
        return m_explicitNamespaceAVT;
    }

    public final void setExplicitNameAVT(String name)
    {
        m_explicitNameAVT = name;
        if (name==null)
        {
            throw new NullPointerException();
        }
    }

    public final String getExplicitNameAVT()
    {
        return m_explicitNameAVT;
    }

    protected static ExpandedName WILDCARD_NAME = ExpandedName.makeName("*","*");

    public ExpandedName computeComponentExpandedName() throws PrefixToNamespaceResolver.PrefixNotFoundException
    {
        return computeComponentExpandedName(asXiNode());
    }

    /**
     * Computes the best name for this attribute; where ambiguous, uses *
     * @return
     */
    public ExpandedName computeComponentExpandedName(PrefixToNamespaceResolver resolver) throws PrefixToNamespaceResolver.PrefixNotFoundException
    {
        if (!m_isExplicitXsd)
        {
            // so easy.
            return getName();
        }
        else
        {
            if (m_explicitNamespaceAVT!=null)
            {
                String ns = AVTUtilities.isAVTString(m_explicitNamespaceAVT) ? "*" : m_explicitNamespaceAVT;
                String ln = AVTUtilities.isAVTString(m_explicitNameAVT) ? "*" : m_explicitNameAVT;
                return ExpandedName.makeName(ns,ln);
            }
            else
            {
                if (!AVTUtilities.isAVTString(m_explicitNameAVT))
                {
                    QName qn = new QName(m_explicitNameAVT);
                    String ns = resolver.getNamespaceURIForPrefix(qn.getPrefix());
                    return ExpandedName.makeName(ns,qn.getLocalName());
                }
                else
                {
                    // can't determine it.
                    return WILDCARD_NAME;
                }
            }
        }
    }

    /**
     * Is this an 'xsl:attribute/xsl:element' or a literal result attribute/element.
     * @return True if this is an xsl attribute/element.
     */
    public final boolean isExplicitXslRepresentation()
    {
        return m_isExplicitXsd;
    }

    public final void setExplicitXslRepresentation(boolean explicit)
    {
        m_isExplicitXsd = explicit;
    }

    public void declareNamespaces(Set set)
    {
        if (!m_isExplicitXsd && !isNameMalformed())
        {
            set.add(getName().getNamespaceURI());
        }
        else
        {
            set.add(ReadFromXSLT.XSLT_URI);
        }
        super.declareNamespaces(set);
    }

    protected final boolean isNameMalformed()
    {
        if (!isExplicitXslRepresentation())
        {
            return !XMLStringUtilities.isNCName(m_name.getLocalName());
        }
        return false;
    }

    /**
     * Indicates if this element name or namespace is specified using an attribute value template.
     */
    public boolean hasAVTName() {
        if (!m_isExplicitXsd)
        {
            // not done that way.
            return false;
        }
        if (AVTUtilities.isAVTString(m_explicitNameAVT))
        {
            return true;
        }
        if (m_explicitNamespaceAVT!=null && AVTUtilities.isAVTString(m_explicitNamespaceAVT))
        {
            return true;
        }
        return false;
    }

    public boolean renamePrefixUsages(Map oldToNewPrefixMap)
    {
        if (!m_isExplicitXsd)
        {
            String ln = m_explicitNameAVT;
            if (!AVTUtilities.isAVTString(ln) && m_explicitNamespaceAVT==null)
            {
                // if namespace isn't null, then it's not using a qname.
                QName qn = new QName(ln);
                String n = (String) oldToNewPrefixMap.get(qn.getPrefix());
                if (n!=null)
                {
                    QName nqn = new QName(n,qn.getLocalName());
                    m_explicitNameAVT = nqn.toString();
                    return true;
                }
            }
            else
            {
                Expr e = AVTUtilities.parseAsExpr(ln);
                String nstr = AVTUtilities.renderAsAVT(ExprUtilities.renamePrefixes(e,oldToNewPrefixMap));
                boolean any = false;
                if (!nstr.equals(ln))
                {
                    any = true;
                    m_explicitNameAVT = nstr;
                }
                if (m_explicitNamespaceAVT!=null)
                {
                    Expr e2 = AVTUtilities.parseAsExpr(m_explicitNamespaceAVT);
                    String nstr2 = AVTUtilities.renderAsAVT(ExprUtilities.renamePrefixes(e2,oldToNewPrefixMap));
                    if (!nstr2.equals(m_explicitNamespaceAVT))
                    {
                        m_explicitNamespaceAVT = nstr2;
                        any = true;
                    }
                }
                return any;
            }
        }
        return false;
    }


    /**
     * Used to be able to load/save even invalid names.
     */
    protected static final ExpandedName MALFORMED_ATTR = ExpandedName.makeName(TibExtFunctions.NAMESPACE,"malformed");
}
