package com.tibco.cep.mapper.xml.xdata;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.tibco.cep.mapper.xml.xdata.xpath.FunctionNamespace;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolver;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibcoExtendedFunctionNamespace;
import com.tibco.cep.mapper.xml.xdata.xpath.func.XPath10FunctionNamespace;
import com.tibco.cep.mapper.xml.xdata.xpath.func.XPath20FunctionNamespace;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.xquery.Expr;
import com.tibco.xml.xquery.FunctionGroup;

/**
 * A set of extension functions. This class will act as a virtual singleton. There should
 * be only one instance of this Object per RepoAgent.
 */
public class XPathFunctionResolver implements FunctionResolver
{
    private FunctionNamespace m_defaultNs;
    private Map m_builtInNamespaces = new HashMap();  // String (ns) -> FunctionNamespace
    private FunctionResolver m_externalFunctions; // may be null.

    private FunctionGroup m_functionGroup = new FunctionGroup()
    {
        // Only check external functions, not built in ones:
        public Expr getFunctionCallExpr(ExpandedName expandedName, Expr[] exprs)
        {
            if (m_externalFunctions==null)
            {
                return null;
            }
            return m_externalFunctions.getAsFunctionGroup().getFunctionCallExpr(expandedName,exprs);
        }

        public boolean isFunctionAvailable(ExpandedName expandedName, int i)
        {
            if (m_externalFunctions==null)
            {
                return false;
            }
            return m_externalFunctions.getAsFunctionGroup().isFunctionAvailable(expandedName,i);
        }
    };

    /**
     * We will not load any external functions in this case.
     */
    public XPathFunctionResolver()
    {
        this(null);
    }

    public XPathFunctionResolver(FunctionResolver javaCache)
    {
        addBuiltIn();
        m_externalFunctions = javaCache;
    }

    private void addBuiltIn()
    {
        addBuiltIn(XPath10FunctionNamespace.INSTANCE);
        addBuiltIn(XPath20FunctionNamespace.INSTANCE);
        addBuiltIn(TibcoExtendedFunctionNamespace.INSTANCE);
        m_defaultNs = XPath10FunctionNamespace.INSTANCE;
    }

    private void addBuiltIn(FunctionNamespace fn)
    {
        m_builtInNamespaces.put(fn.getNamespaceURI(),fn);
    }

    public Iterator getNamespaces()
    {
        ArrayList al = new ArrayList();
        al.addAll(m_builtInNamespaces.values());
        if (m_externalFunctions!=null)
        {
            Iterator i2 = m_externalFunctions.getNamespaces();
            while (i2.hasNext())
            {
                al.add(i2.next());
            }
        }
        return al.iterator();
    }

    public FunctionNamespace getNamespace(String namespace)
    {
        if (namespace==null || namespace.length()==0)
        {
            return m_defaultNs;
        }
        FunctionNamespace fn = (FunctionNamespace) m_builtInNamespaces.get(namespace);
        if (fn!=null)
        {
            return fn;
        }
        if (m_externalFunctions!=null)
        {
            return m_externalFunctions.getNamespace(namespace);
        }
        return null;
    }

    public FunctionGroup getAsFunctionGroup()
    {
        return m_functionGroup;
    }
}

