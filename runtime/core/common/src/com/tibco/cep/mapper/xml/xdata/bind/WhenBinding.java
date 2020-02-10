/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.Map;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprUtilities;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;

/**
 * Represents an XSLT when operation.
 * (Not a control binding since it MUST be inside a {@link ChooseBinding}).
 */
public class WhenBinding extends AbstractBinding
{
    public WhenBinding(BindingElementInfo info, String test)
    {
        super(info, test);
    }

    /**
     * Returns the formated name for toString() (from BindingReport).
     * @return String name to show in toString.
     */
//    public String getReportFormatName()
//    {
//        return "when";
//    }
    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        TemplateReport ret = new TemplateReport(this,parent);
        ret.setContext(context);
        ret.setFollowingContext(context);
        TemplateReportSupport.initParsedFormula(ret,templateReportFormulaCache,templateReportArguments,true); // true-> do boolean check.
        ret.setChildContext(context);
        ret.setOutputContext(outputContext);
        ret.setChildOutputContext(outputContext);
        ret.setInitialOutputType(expectedOutput);
        if (parent==null || !(parent.getBinding() instanceof ChooseBinding))
        {
            ret.setStructuralError("The 'when' statement must appear directly inside a 'choose'.");
        }

        SmSequenceType remainingType = TemplateReportSupport.traverseChildren(ret,expectedOutput,outputValidationLevel,templateReportFormulaCache,templateReportArguments);
        ret.setRemainingOutputType(remainingType);

        ret.setExpectedType(TemplateReportSupport.getMatchedChildTerm(ret));

        TemplateReportSupport.initIsRecursivelyErrorFree(ret);
        return ret;
    }

    private static final ExpandedName NAME = ReadFromXSLT.makeName("when");
    private static final ExpandedName ATTR_TEST = ExpandedName.makeName("test");
    public ExpandedName getName() {
        return NAME;
    }

    public boolean renamePrefixUsages(Map oldToNewPrefixMap)
    {
        String formula = getFormula();
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
            handler.attribute(ATTR_TEST,f,null);
        }
        super.issueAdditionalAttributes(handler);
        for (int i=0;i<getChildCount();i++) {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(NAME,null,null);
    }

    public com.tibco.cep.mapper.xml.xdata.bind.Binding cloneShallow() {
        return new WhenBinding(getElementInfo(),this.getFormula());
    }
}
