package com.tibco.cep.mapper.xml.xdata.xpath;

import com.tibco.xml.datamodel.XiNode;

/**
 * Replacing EvalContext
 */
public final class DataContext {
    private VariableList mVariables;
    private XiNode mDocument;

    public DataContext(VariableList variables) {
        mVariables = variables;
    }

    public DataContext(XiNode document) {
        mDocument = document;
    }

    public XiNode getDocument() {
        return mDocument;
    }

    public VariableList getVariables() {
        return mVariables;
    }
}

