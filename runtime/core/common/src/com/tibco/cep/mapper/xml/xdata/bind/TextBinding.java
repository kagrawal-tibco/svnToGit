/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.NamespaceMapper;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.values.XsString;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

/**
 * Represents an XSLT value-of operation.
 */
public class TextBinding extends ControlBinding
{
    private boolean mIsFullTag;

    public TextBinding(BindingElementInfo ns)
    {
        super(ns);
    }

    public TextBinding(BindingElementInfo ns, String formula)
    {
        super(ns, formula);
    }

    /**
     * Generates an XSLT string from the given binding.
     * @param xslt Buffer which contains the generated XSLT.
     */
    public void toXSLTString(StringBuffer xslt, int indent, NamespaceMapper namespaceMap)
    {
        xslt.append(getFormula());
    }

    public TemplateReport createTemplateReport(TemplateReport parent,
                                               ExprContext context,
                                               SmSequenceType expectedOutput,
                                               SmSequenceType outputContext,
                                               int outputValidationLevel,
                                               TemplateReportFormulaCache templateReportFormulaCache,
                                               TemplateReportArguments templateReportArguments)
    {
        TemplateReport report = new TemplateReport(this,parent);
        report.setContext(context);
        report.setChildContext(context);
        report.setFollowingContext(context);
        report.setOutputContext(outputContext);
        report.setChildOutputContext(outputContext);
        report.setInitialOutputType(expectedOutput);

        // type check this later: String literalValue = getFormula();
//        report.setFormulaType(SmSequenceTypeFactory.create(XSDL.STRING,getFormula()));
        report.setFormulaType(SmSequenceTypeFactory.createAtomic(XSDL.STRING,
                                                                 new XsString(getFormula())));
        TemplateReportSupport.initForOutputMatch(report,SMDT.STRING,false,templateReportArguments);

        if (report.getExpectedType()!=null && report.getFormulaType()!=null) {
            ErrorMessageList eml = BindingTypeCheckUtilities.typeCheck(
                    BindingTypeCheckUtilities.TYPE_CHECK_VALUE, // do a value type-check.
                    report.getExpectedType(),
                    report.getFormulaType(),
                    new TextRange(0,getFormula().length()),
                    templateReportArguments.getXPathCheckArguments()
                    );
            report.addFormulaErrors(eml);
        }
        boolean allowComments = mIsFullTag;
        TemplateReportSupport.traverseNoChildren(report,allowComments,templateReportFormulaCache,templateReportArguments);

        TemplateReportSupport.initIsRecursivelyErrorFree(report);
        return report;
    }

    private static final ExpandedName NAME = ReadFromXSLT.makeName("text");

    public ExpandedName getName() {
        return NAME;
    }

    public boolean isFullTag() {
        return mIsFullTag;
    }

    public void setFullTag(boolean val) {
        mIsFullTag = val;
    }

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public com.tibco.cep.mapper.xml.xdata.bind.Binding cloneShallow()
    {
        TextBinding vb = new TextBinding(getElementInfo(),this.getFormula());
        vb.setFullTag(isFullTag());
        return vb;
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        // WCETODO literal versus non-literal.
        if (isFullTag()) {
            super.issueNamespaceDeclarations(handler);
            handler.startElement(NAME,null,null);
            super.issueAdditionalAttributes(handler);
            String t = getFormula();
            if (t==null) {
                t = "";
            }
            handler.text(t,false);
            handler.endElement(NAME,null,null);
        } else {
            String t = getFormula();
            if (t==null) {
                t = "";
            }
            handler.text(t,false);
        }
    }
}
