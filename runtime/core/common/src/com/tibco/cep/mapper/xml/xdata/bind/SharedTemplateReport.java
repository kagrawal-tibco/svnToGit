package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.ArrayList;

import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * This is the part of the binding report that applies across ALL of the bindings.
 * Currently it only stores shared variable & magic function (i.e. current-group, etc.) information.
 */
public final class SharedTemplateReport
{
    private ArrayList mVariables = new ArrayList();
    private SmSequenceType m_currentGroup = SMDT.VOID;

    public SharedTemplateReport() {
    }

    public void addVariable(VariableDefinition varDef) {
        mVariables.add(varDef);
    }

    public VariableDefinition[] getVariables() {
        return (VariableDefinition[]) mVariables.toArray(new VariableDefinition[0]);
    }

    public void setCurrentGroup(SmSequenceType type)
    {
        if (type==null)
        {
            throw new NullPointerException();
        }
        m_currentGroup = type;
    }

    public SmSequenceType getCurrentGroup()
    {
        return m_currentGroup;
    }
}

