package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.ArrayList;
import java.util.Iterator;

import com.tibco.util.coll.Iterators;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlNodeKind;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.appl.ApplicationObjectXiNode;
import com.tibco.xml.datamodel.appl.XmlApplicationObject;

/**
 * Helper base class for XSLT instructions.
 */
public abstract class AbstractBinding extends Binding implements XmlApplicationObject
{
    private static final XmlApplicationObject[] EMPTY_AO_ARRAY = new XmlApplicationObject[0];

    public AbstractBinding(BindingElementInfo info)
    {
        super(info);
    }

    public AbstractBinding(BindingElementInfo info, String formula)
    {
        super(info,formula);
    }

    public AbstractBinding()
    {
        super(BindingElementInfo.EMPTY_INFO);
    }

    /**
     * Take care of this.
     * @return The xi node representation of this object.
     */
    public XiNode asXiNode()
    {
        return new ApplicationObjectXiNode(this);
    }

    public XmlApplicationObject[] getApplicationObjectChildren()
    {
        if (getChildCount()==0)
        {
            return EMPTY_AO_ARRAY;
        }
        XmlApplicationObject[] r = new XmlApplicationObject[getChildCount()];
        for (int i=0;i<r.length;i++)
        {
            r[i] = (AbstractBinding) getChild(i);
        }
        return r;
    }

    public XmlApplicationObject getApplicationObjectParent()
    {
        return (AbstractBinding) getParent();
    }

    public XmlNodeKind getApplicationObjectNodeKind()
    {
        return XmlNodeKind.ELEMENT;
    }

    public ExpandedName getApplicationObjectName()
    {
        return getName();
    }

    public XmlTypedValue getApplicationObjectTypedValue()
    {
        return null;
    }

    public void setApplicationObjectTypedValue(XmlTypedValue value)
    {
        throw new IllegalArgumentException("Cannot set typed value here.");
    }

    public Iterator getApplicationObjectLocalPrefixes()
    {
        BindingElementInfo.NamespaceDeclaration[] nd = getElementInfo().getNamespaceDeclarations();
        if (nd.length==0)
        {
            return Iterators.EMPTY;
        }
        ArrayList l = new ArrayList();
        for (int i=0;i<nd.length;i++)
        {
            l.add(nd[i].getPrefix());
        }
        return l.iterator();
    }

    public String getApplicationObjectNamespaceForPrefix(String prefix)
    {
        BindingElementInfo.NamespaceDeclaration[] nd = getElementInfo().getNamespaceDeclarations();
        for (int i=0;i<nd.length;i++)
        {
            if (nd[i].getPrefix().equals(prefix))
            {
                return nd[i].getNamespace();
            }
        }
        // undefined; so just return null.
        return null;
    }

    public ExpandedName[] getApplicationObjectAttributes()
    {
        return new ExpandedName[0];//FIXME
    }

    public XmlTypedValue getApplicationObjectAttributeValue(ExpandedName name)
    {
        return null;
    }

    public void setApplicationObjectAttributeValue(ExpandedName name, XmlTypedValue value)
    {
    }

    public void removeApplicationObjectNamespace(String prefix)
    {
        BindingElementInfo info = getElementInfo();
        BindingElementInfo.NamespaceDeclaration[] nd = info.getNamespaceDeclarations();
        int replaceIndex = -1;
        for (int i=0;i<nd.length;i++)
        {
            BindingElementInfo.NamespaceDeclaration n = nd[i];
            if (n.getPrefix().equals(prefix))
            {
                replaceIndex = i;
                break;
            }
        }
        if (replaceIndex==-1)
        {
            return;
        }
        BindingElementInfo.NamespaceDeclaration[] newd = new BindingElementInfo.NamespaceDeclaration[nd.length -1];
        int j=0;
        for (int i=0;i<nd.length;i++)
        {
            if (i!=replaceIndex)
            {
                newd[j++] = nd[i];
            }
        }
        BindingElementInfo newinfo = getElementInfo().createWithNamespaceDeclarations(newd);
        setElementInfo(newinfo);
    }

    public void setApplicationObjectNamespace(String prefix, String namespace)
    {
        BindingElementInfo info = getElementInfo();
        BindingElementInfo.NamespaceDeclaration[] nd = info.getNamespaceDeclarations();
        int replaceIndex = -1;
        for (int i=0;i<nd.length;i++)
        {
            BindingElementInfo.NamespaceDeclaration n = nd[i];
            if (n.getPrefix().equals(prefix))
            {
                replaceIndex = i;
                break;
            }
        }

        BindingElementInfo.NamespaceDeclaration[] newd = new BindingElementInfo.NamespaceDeclaration[replaceIndex<0 ? nd.length + 1 : nd.length];
        for (int i=0;i<nd.length;i++)
        {
            newd[i] = nd[i];
        }
        BindingElementInfo.NamespaceDeclaration newdecl = new BindingElementInfo.NamespaceDeclaration(prefix,namespace);
        if (replaceIndex<0)
        {
            newd[nd.length] = newdecl;
        }
        else
        {
            newd[replaceIndex] = newdecl;
        }
        BindingElementInfo newinfo = getElementInfo().createWithNamespaceDeclarations(newd);
        setElementInfo(newinfo);
    }
}
