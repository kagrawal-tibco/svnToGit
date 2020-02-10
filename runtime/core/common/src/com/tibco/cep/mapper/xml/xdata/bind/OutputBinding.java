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
 * The xsl:output tag.
 */
public class OutputBinding extends ControlBinding
{
    /**
     * Represents the read in namespaces.
     */
    private String m_outputMethod;

    public OutputBinding(BindingElementInfo info)
    {
        super(info);
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        super.issueNamespaceDeclarations(handler);
        handler.startElement(NAME,null,null);
        if (m_outputMethod!=null)
        {
            handler.attribute(ATTR_METHOD,m_outputMethod,null);
        }
        super.issueAdditionalAttributes(handler);
        for (int i=0;i<getChildCount();i++)
        {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(NAME,null,null);
    }

    public Binding cloneShallow()
    {
        OutputBinding sb = new OutputBinding(getElementInfo());
        sb.setOutputMethod(getOutputMethod());
        return sb;
    }

    public String getOutputMethod()
    {
        return m_outputMethod;
    }

    public void setOutputMethod(String outputMethod)
    {
        m_outputMethod = outputMethod;
    }

    private static final ExpandedName NAME = ReadFromXSLT.makeName("output");
    private static final ExpandedName ATTR_METHOD = ExpandedName.makeName("method");

    public ExpandedName getName()
    {
        return NAME;
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        // don't treat these as part of the report..., they can't appear in a template.
        TemplateReport tr = new TemplateReport(this,parent);
        tr.setContext(context);
        tr.setChildContext(context);
        tr.setFollowingContext(context);
        tr.setOutputContext(outputContext);
        tr.setChildOutputContext(outputContext);
        tr.setInitialOutputType(expectedOutput);
        tr.setRemainingOutputType(expectedOutput);
        tr.setStructuralError("The statement 'output' cannot appear here");
        return tr;
    }
}
