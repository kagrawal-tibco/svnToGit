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
 * Represents an call-template xslt statement.
 */
public class ApplyTemplatesBinding extends ControlBinding
{
    public ApplyTemplatesBinding(BindingElementInfo info)
    {
        super(info);
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments)
    {
        // Type checking not fully done:
        TemplateReport ret = new TemplateReport(this,parent);
        ret.setInitialOutputType(expectedOutput);
        ret.setContext(context);

        TemplateReportSupport.initParsedFormula(ret,templateReportFormulaCache,templateReportArguments);
        if (ret.getFormulaType()!=null) {
            BindingTypeCheckUtilities.typeCheck(
                    BindingTypeCheckUtilities.TYPE_CHECK_FOR_EACH,
                    SMDT.REPEATING_ITEM,
                    ret.getFormulaType(),
                    ret.getXPathExpression().getTextRange(),
                    templateReportArguments.getXPathCheckArguments()
            );
        }

        ret.setChildContext(context);
        ret.setFollowingContext(context);
        ret.setOutputContext(outputContext);
        ret.setChildOutputContext(outputContext);

        int cc = getChildCount();
        for (int i=0;i<cc;i++)
        {
            Binding b = getChild(i);
            if (b instanceof SortBinding || b instanceof WithParamBinding)
            {
                // ok in context.
                b.createTemplateReport(ret,context,SMDT.PREVIOUS_ERROR,SMDT.PREVIOUS_ERROR, outputValidationLevel, templateReportFormulaCache,templateReportArguments);
            }
            else
            {
                // otherwise probably not (except comments, handled w/ true below)
                TemplateReportSupport.generateReportNoChildren(true,true,b,ret,templateReportFormulaCache,templateReportArguments);
            }
        }

        TemplateReportSupport.initIsRecursivelyErrorFree(ret);
        ret.setExpectedType(SMDT.PREVIOUS_ERROR); // can't understand. (yet)
        ret.setComputedType(SMDT.PREVIOUS_ERROR);
        ret.setRemainingOutputType(SMDT.PREVIOUS_ERROR); // can't understand.
        return ret;
    }

    private static final ExpandedName NAME = ReadFromXSLT.makeName("apply-templates");
    private static final ExpandedName ATTR_SELECT = ExpandedName.makeName("select");

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        super.issueNamespaceDeclarations(handler);
        handler.startElement(NAME,null,null);
        if (getFormula()!=null)
        {
            handler.attribute(ATTR_SELECT,getFormula(),null);
        }
        super.issueAdditionalAttributes(handler);

        for (int i=0;i<getChildCount();i++)
        {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(NAME,null,null);
    }

    public ExpandedName getName() {
        return NAME;
    }

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public Binding cloneShallow()
    {
        ApplyTemplatesBinding b = new ApplyTemplatesBinding(getElementInfo());
        b.setFormula(getFormula());
        return b;
    }
}
