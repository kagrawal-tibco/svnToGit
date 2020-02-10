/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.ArrayList;
import java.util.Set;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * An XSLT template.
 */
public class TemplateBinding extends AbstractBinding
{
    private String mTemplateName;
    private String mInputType;
    private String mOutputType;
    private String mLocalExcludePrefixes; // non XSLT; for inline bindings.

    public TemplateBinding(BindingElementInfo info, String name, String match)
    {
        super(info, match);
        mTemplateName = name;
    }

    public String getTemplateName()
    {
        return mTemplateName;
    }

    public void setTemplateName(String name)
    {
        mTemplateName = name;
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        TemplateReport ret = new TemplateReport(this,parent);
        ret.setContext(context);
        ret.setChildContext(context);
        ret.setFollowingContext(context);
        ret.setExpectedType(expectedOutput);
        ret.setOutputContext(outputContext);
        ret.setChildOutputContext(outputContext);
        ret.setInitialOutputType(expectedOutput);

        int startAt;
        if (templateReportArguments.isSkippingTemplateParams()) {
            startAt = BindingManipulationUtils.getTemplateFirstNonParameter(this);
        } else {
            startAt = 0;
        }
        SmSequenceType leftoverType = TemplateReportSupport.traverseChildren(ret,expectedOutput,startAt,templateReportFormulaCache, templateReportArguments);
        TemplateReportSupport.addMissingEndingTerms(ret,leftoverType,templateReportArguments);
        ret.setRemainingOutputType(SMDT.VOID);
        TemplateReportSupport.initIsRecursivelyErrorFree(ret);
        return ret;
    }

    public ExpandedName getName() {
        return TAG_TEMPLATE;
    }

    public Binding cloneShallow()
    {
        TemplateBinding ret = new TemplateBinding(getElementInfo(),mTemplateName, this.getFormula());
        ret.setInputType(getInputType());
        ret.setOutputType(getOutputType());
        ret.setLocalExcludePrefixes(getLocalExcludePrefixes());
        return ret;
    }

    /**
     * An xsl extension used in the inline mapper GUI to allow prefix exclusions on a per-template basis.
     * @return A string, possibly null.
     */
    public String getLocalExcludePrefixes()
    {
        return mLocalExcludePrefixes;
    }

    public void setLocalExcludePrefixes(String ep)
    {
        mLocalExcludePrefixes = ep;
    }

    public String getInputType() {
        return mInputType;
    }

    public void setInputType(String typeStr) {
        mInputType = typeStr;
    }

    public String getOutputType() {
        return mOutputType;
    }

    public void setOutputType(String outputType) {
        mOutputType = outputType;
    }

    private static final ExpandedName TAG_TEMPLATE = ReadFromXSLT.makeName("template");
    private static final ExpandedName ATTR_NAME = ExpandedName.makeName("name");
    private static final ExpandedName ATTR_MATCH = ExpandedName.makeName("match");
    private static final ExpandedName ATTR_INPUT = TibExtFunctions.makeName("input");
    private static final ExpandedName ATTR_OUTPUT = TibExtFunctions.makeName("output");

    public void declareNamespaces(Set set)
    {
        set.add(ReadFromXSLT.XSLT_URI);
        super.declareNamespaces(set);
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        super.issueNamespaceDeclarations(handler);
        handler.startElement(TAG_TEMPLATE,null,null);
        if (mTemplateName!=null)
        {
            handler.attribute(ATTR_NAME,mTemplateName,null);
        }
        if (getFormula()!=null) {
            handler.attribute(ATTR_MATCH,getFormula(),null);
        }
        if (mInputType!=null) {
            handler.attribute(ATTR_INPUT,mInputType,null);
        }
        if (mOutputType!=null) {
            handler.attribute(ATTR_OUTPUT,mOutputType,null);
        }
        super.issueAdditionalAttributes(handler);
        for (int i=0;i<getChildCount();i++) {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(TAG_TEMPLATE,null,null);
    }

    public ExpandedName[] getApplicationObjectAttributes()
    {
        ArrayList temp = new ArrayList();
        if (mInputType!=null)
        {
            temp.add(ATTR_INPUT);
        }
        if (mOutputType!=null)
        {
            temp.add(ATTR_OUTPUT);
        }
        if (getFormula()!=null)
        {
            //WCETODO add this.
        }
        return (ExpandedName[]) temp.toArray(new ExpandedName[temp.size()]);
    }

/* jtb: commented out -- not used & caused compilation error...
    public XmlTypedValue getApplicationObjectAttributeValue(ExpandedName name)
    {
        if (name.equals(ATTR_INPUT))
        {
            SmSequenceType t = XQueryTypesParser.parse(asXiNode(),
                                                       EmptyNamespaceProvider.getInstance(),
                                                       SMDT.NONE,
                                                       mInputType);
            return new XsTypeDeclaration(t);
        }
        return null;
    }
*/
}
