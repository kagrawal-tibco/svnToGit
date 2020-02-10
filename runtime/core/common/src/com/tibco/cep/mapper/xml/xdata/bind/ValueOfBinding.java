/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.Map;
import java.util.Set;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprUtilities;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Represents an XSLT value-of operation.
 */
public class ValueOfBinding extends ControlBinding
{
    public ValueOfBinding(BindingElementInfo info)
    {
        super(info);
    }

    public ValueOfBinding(BindingElementInfo info,String formula)
    {
        super(info, formula);
    }

    /**
     * Clones this binding node.
     * @return Binding A binding node of this type.
     */
    public Binding cloneShallow()
    {
        ValueOfBinding vb = new ValueOfBinding(getElementInfo(),this.getFormula());
        return vb;
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        TemplateReport report = new TemplateReport(this,parent);
        ExprContext updatedContext = TemplateReportSupport.createContextForNamespaceDecls(this,context);
        report.setContext(updatedContext);
        report.setOutputContext(outputContext);
        report.setChildContext(updatedContext);
        report.setChildOutputContext(outputContext);
        report.setFollowingContext(context);
        report.setInitialOutputType(expectedOutput);

        TemplateReportSupport.initForOutputMatch(report,SMDT.STRING,false,templateReportArguments);
        TemplateReportSupport.initParsedFormula(report,templateReportFormulaCache,templateReportArguments);
        if (report.getExpectedType()!=null && report.getFormulaType()!=null) {
            ErrorMessageList eml = BindingTypeCheckUtilities.typeCheck(
                    BindingTypeCheckUtilities.TYPE_CHECK_VALUE, // do a value type-check.
                    report.getExpectedType(),
                    report.getFormulaType(),
                    report.getXPathExpression().getTextRange(),
                    templateReportArguments.getXPathCheckArguments()
                    );
            report.addFormulaErrors(eml);
        }

        TemplateReportSupport.traverseNoChildren(report,true,templateReportFormulaCache,templateReportArguments);
        TemplateReportSupport.initIsRecursivelyErrorFree(report);
        return report;
    }

    public void declareNamespaces(Set set)
    {
        set.add(ReadFromXSLT.XSLT_URI);
        super.declareNamespaces(set);
    }

    public boolean renamePrefixUsages(Map oldToNewPrefixMap)
    {
        String formula = getFormula();
        if (formula==null)
        {
            return false;
        }
        Expr parseFormula = Parser.parse(formula);
        Expr newFormula = ExprUtilities.renamePrefixes(parseFormula,oldToNewPrefixMap);
        String newStr = newFormula.toExactString();
        if (newStr.equals(formula))
        {
            return false;
        }
        setFormula(newStr);
        return true;
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException
    {
        super.issueNamespaceDeclarations(handler);
        handler.startElement(NAME,null,null);
        String f = getFormula();
        if (f!=null)
        {
            handler.attribute(SELECT_ATTR,f,null);
        }
        super.issueAdditionalAttributes(handler);
        for (int i=0;i<getChildCount();i++)
        {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(NAME,null,null);
    }

    private static final ExpandedName NAME = ReadFromXSLT.makeName("value-of");
    private static final ExpandedName SELECT_ATTR = ExpandedName.makeName("select");

    public ExpandedName getName() {
        return NAME;
    }
}
