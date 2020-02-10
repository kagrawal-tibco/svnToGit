package com.tibco.cep.mapper.xml.xdata.xpath.func;

import java.util.HashMap;
import java.util.Iterator;

import com.tibco.cep.mapper.xml.xdata.xpath.FunctionNamespace;

/**
 * A default implementation of {@link FunctionNamespace} which allows adding, etc.
 */
public class DefaultFunctionNamespace implements FunctionNamespace
{
    private HashMap m_functions = new HashMap(); // String->XFunction.
    private String m_loadErrorMessage;
    private String m_namespaceURI;
    private boolean m_builtIn;
    private String m_suggestedPrefix;
    private boolean m_isLocked;

    public DefaultFunctionNamespace(String namespaceURI)
    {
        m_namespaceURI = namespaceURI;
    }

    public XFunction get(String ncName)
    {
        return (XFunction) m_functions.get(ncName);
    }

    /**
     * Adds the function.<br>
     * Note that the namespace of the function name <b>must</b> match the namespace of this, otherwise an illegal
     * argument exception is thrown.
     * @param function The function, in this namespace.
     */
    public void add(XFunction function)
    {
        checkLocked();
        String fn = function.getName().getNamespaceURI();
        if ((fn==null && (m_namespaceURI!=null && m_namespaceURI.length()>0)) || (fn!=null && !fn.equals(m_namespaceURI)))
        {
            throw new IllegalArgumentException("Namespace mismatch for fn: " + function.getName() + ", attempt to add to: " + m_namespaceURI);
        }
        m_functions.put(function.getName().getLocalName(),function);
    }

    public Iterator getFunctions()
    {
        return m_functions.values().iterator();
    }

    public String getLoadErrorMessage()
    {
        return m_loadErrorMessage;
    }

    public void setLoadErrorMessage(String loadErrorMessage)
    {
        checkLocked();
        m_loadErrorMessage = loadErrorMessage;
    }

    public String getNamespaceURI()
    {
        return m_namespaceURI;
    }

    public String getSuggestedPrefix()
    {
        return m_suggestedPrefix;
    }

    public void setSuggestedPrefix(String suggestedPrefix)
    {
        checkLocked();
        m_suggestedPrefix = suggestedPrefix;
    }

    public boolean isBuiltIn()
    {
        return m_builtIn;
    }

    public void setBuiltIn(boolean builtIn)
    {
        checkLocked();
        m_builtIn = builtIn;
    }

    public void lock()
    {
        m_isLocked = true;
    }

    private void checkLocked()
    {
        if (m_isLocked)
        {
            throw new IllegalStateException("Locked");
        }
    }
}
