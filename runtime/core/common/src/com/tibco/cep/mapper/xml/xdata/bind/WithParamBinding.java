/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;

/**
 * Represents an call-template xslt statement.
 */
public class WithParamBinding extends ControlBinding
{
    private String mVarQName;

    public WithParamBinding(BindingElementInfo info, String varQName) {
        super(info,"with-param");
        mVarQName = varQName;
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        throw new RuntimeException("NYI");
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        throw new RuntimeException("NYI");
    }

    private static final ExpandedName NAME = ExpandedName.makeName(ReadFromXSLT.XSLT_URI,"with-param");

    public ExpandedName getName() {
        return NAME;
    }

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public com.tibco.cep.mapper.xml.xdata.bind.Binding cloneShallow()
    {
        return new WithParamBinding(getElementInfo(),mVarQName);
    }

    public String getParamName()
    {
        return mVarQName;
    }


}
