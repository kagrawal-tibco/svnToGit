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

/**
 * The xsl:comment xslt tag.
 */
public class XslCommentBinding extends TemplateContentBinding
{
    public XslCommentBinding(BindingElementInfo info) {
        super(info);
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments)
    {
        // harmless comment (generation):
        TemplateReport tr = new TemplateReport(this,parent);
        tr.setContext(context);
        tr.setInitialOutputType(expectedOutput);
        tr.setFollowingContext(context);
        tr.setChildContext(context);
        tr.setOutputContext(outputContext);
        tr.setChildOutputContext(outputContext);
        tr.setRemainingOutputType(expectedOutput);

        TemplateReportSupport.traverseChildren(tr,SMDT.STRING,outputValidationLevel,templateReportFormulaCache,templateReportArguments);

        TemplateReportSupport.initIsRecursivelyErrorFree(tr);

        return tr;
    }

    private static final ExpandedName NAME = ExpandedName.makeName(ReadFromXSLT.XSLT_URI,"comment");
    public ExpandedName getName() {
        return NAME;
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException
    {
        super.issueNamespaceDeclarations(handler);
        handler.startElement(NAME,null,null);
        super.issueAdditionalAttributes(handler);
        for (int i=0;i<getChildCount();i++)
        {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(NAME,null,null);
    }

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public Binding cloneShallow() {
        return new XslCommentBinding(getElementInfo());
    }
}
