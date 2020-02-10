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
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * Represents an XSLT copy operation.
 */
public class CopyBinding extends ControlBinding
{
    // disabled.public String mGeneratesOutput;

    public CopyBinding(BindingElementInfo info, String formula)
    {
        super(info, formula);
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments)
    {
        TemplateReport ret = new TemplateReport(this,parent);
        ExprContext updatedContext = TemplateReportSupport.createContextForNamespaceDecls(this,context);
        ret.setContext(updatedContext);
        ret.setOutputContext(outputContext);
        ret.setChildContext(updatedContext);
        SmSequenceType copyType = context.getInput();
        ret.setInitialOutputType(expectedOutput);
        TemplateReportSupport.initForOutputMatch(ret, copyType, false, templateReportArguments);
        ret.setChildOutputContext(copyType);
        ret.setFollowingContext(updatedContext);

        SmSequenceType contentType;
        if (copyType.getParticleTerm()==null)
        {
            // If we don't resolve to anything, we don't actually know what it is, so we should just treat it as a
            // previous error for the purposes of things inside the element:
            contentType = SMDT.PREVIOUS_ERROR;
        }
        else
        {
            contentType = SmSequenceTypeSupport.createAttributeAndChildAndTypedValueSequence(copyType);
        }
        ret.setChildOutputContext(contentType);
        ret.setComputedType(copyType);

        TemplateReportSupport.traverseChildren(ret,contentType,outputValidationLevel,templateReportFormulaCache,templateReportArguments);

        TemplateReportSupport.initIsRecursivelyErrorFree(ret);
        return ret;
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        handler.startElement(NAME,null,null);
        String f = getFormula();
        if (f==null) f = "";
        handler.attribute(SELECT_ATTR,f,null);
        for (int i=0;i<getChildCount();i++)
        {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(NAME,null,null);
    }

    private static final ExpandedName NAME = ReadFromXSLT.makeName("copy");
    private static final ExpandedName SELECT_ATTR = ExpandedName.makeName("select");

    public ExpandedName getName()
    {
        return NAME;
    }

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public Binding cloneShallow()
    {
        return new CopyBinding(getElementInfo(),this.getFormula());
    }
}
