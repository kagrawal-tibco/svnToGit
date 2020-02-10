package com.tibco.be.functions.xpath;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Mar 28, 2005
 * Time: 3:59:20 PM
 * To change this template use File | Settings | File Templates.
 */
import java.util.HashMap;
import java.util.Map;

import com.tibco.xml.channel.variable.VariableContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlSequence;


public final class XPathDefaultVarContext implements VariableContext
{
    /**
     * The storage for variable bindings.
     */
    private Map m_variables = null;

    public XPathDefaultVarContext()
    {
    }

    public XmlSequence getVariableValue(ExpandedName name)
    {
        if (null == name)
        {
            throw new IllegalArgumentException("name is null");
        }
        if (null != m_variables)
        {
            return (XmlSequence)m_variables.get(name);
        }
        else
        {
            return null;
        }
    }

    public void setVariableValue(ExpandedName name, XmlSequence value)
    {
        if (null == name)
        {
            throw new IllegalArgumentException("name is null");
        }
        if (null != value)
        {
            if (null == m_variables)
            {
                m_variables = new HashMap();
            }
            m_variables.put(name, value);
        }
        else
        {
            if (null != m_variables)
            {
                m_variables.remove(name);
            }
        }
    }

    public boolean containsKey(ExpandedName name)
    {
        if (null == name)
        {
            throw new IllegalArgumentException("name is null");
        }
        if (null != m_variables)
        {
            return m_variables.containsKey(name);
        }
        else
        {
            return false;
        }
    }
}

