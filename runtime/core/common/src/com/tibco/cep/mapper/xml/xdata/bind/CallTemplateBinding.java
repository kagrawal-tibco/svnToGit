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
public class CallTemplateBinding extends ControlBinding
{
    private String mTemplateName;

    public CallTemplateBinding(BindingElementInfo info, String templateName)
    {
        super(info,"[call-template]");
        mTemplateName = templateName;
    }

    public void setTemplateName(String name) {
        mTemplateName = name;
    }

    public String getTemplateName()
    {
        return mTemplateName;
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        TemplateReport ret = new TemplateReport(this,parent);
        ret.setInitialOutputType(expectedOutput);
        ret.setContext(context);
        ret.setChildContext(context);
        ret.setFollowingContext(context);
        ret.setOutputContext(outputContext);
        ret.setChildOutputContext(outputContext);

        if (mTemplateName==null)
        {
            mTemplateName = "";
        }
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

        /* Stylesheet resolve not supported yet.
        StylesheetResolver tr = context.getStylesheetResolver();*/
        TemplateSignatureReport tb = null;//tr.getTemplateByName(ExpandedName.makeName(mTemplateName));
        if (tb!=null)
        {
            // We found the template:
            SmSequenceType templateOutput = tb.getOutput();
            TemplateReportSupport.initForOutputMatch(ret, templateOutput, false, templateReportArguments);
            TemplateReportSupport.initIsRecursivelyErrorFree(ret);
            ret.setComputedType(templateOutput);
            return ret;
        }
        else
        {
            // template not found; mark as error.
            ret.setStructuralError("Template '" + mTemplateName + "' not found.");
            ret.setRemainingOutputType(SMDT.PREVIOUS_ERROR);
            TemplateReportSupport.initIsRecursivelyErrorFree(ret);
            return ret;
        }
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        super.issueNamespaceDeclarations(handler);
        handler.startElement(NAME,null,null);
        super.issueAdditionalAttributes(handler);
        if (mTemplateName!=null)
        {
            handler.attribute(ATTR_NAME,mTemplateName,null);
        }
        for (int i=0;i<getChildCount();i++) {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(NAME,null,null);
    }

    private static final ExpandedName ATTR_NAME = ExpandedName.makeName("name");
    private static final ExpandedName NAME = ExpandedName.makeName(ReadFromXSLT.XSLT_URI,"call-template");

    public ExpandedName getName() {
        return NAME;
    }

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public Binding cloneShallow()
    {
        CallTemplateBinding ctb = new CallTemplateBinding(getElementInfo(),mTemplateName);
        return ctb;
    }
}
