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
 * Represents an XSLT if operation.
 */
public class IfBinding extends ControlBinding
{
    public IfBinding(BindingElementInfo ns)
    {
        super(ns);
    }

    public IfBinding(BindingElementInfo ns, String formula)
    {
        super(ns, formula);
    }

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments)
    {
        TemplateReport ret = new TemplateReport(this,parent);
        ret.setContext(context);
        ret.setChildContext(context);
        ret.setFollowingContext(context);
        TemplateReportSupport.initParsedFormula(ret,templateReportFormulaCache,templateReportArguments,true); // true-> do boolean check.
        ret.setOutputContext(outputContext);
        ret.setChildOutputContext(outputContext);
        ret.setInitialOutputType(expectedOutput);

        SmSequenceType remaining = TemplateReportSupport.traverseChildren(ret,expectedOutput,outputValidationLevel,templateReportFormulaCache,templateReportArguments);

        if (templateReportArguments.getCheckForMove())
        {
            TemplateReport first = TemplateReportSupport.findFirstOutputProducingChild(ret);
            TemplateReportSupport.bubbleOutMove(first, parent, ret);
        }

        TemplateReportSupport.bubbleOutMissingPrecedingTerms(ret);

        ret.setExpectedType(TemplateReportSupport.getMatchedChildTerm(ret));

        // If the body of this 'if' eats a term, then assume its eaten;  (and can't appear after this if).
        // Also, for simplicity, performance, and ease-of-use, we need to have exactly 1 simple XType that represents the
        // remaining output state; attempting to have some sort of 'choice' propagated is really not possible.  We
        // assume that for an 'if' (and a choose), that the 'longest' path dictates how much output is produced.  In the
        // case of if, the longest path is always the 'if' body.
        ret.setRemainingOutputType(remaining);

        // not handled in all cases:
        SmSequenceType expectedOutputAfterMissingPreceding = TemplateReportSupport.skipMissingPreceding(expectedOutput,ret.getMissingPrecedingTerms());
        TemplateReportSupport.addMissingCases(ret,remaining,expectedOutputAfterMissingPreceding,true); // true-> add following this choose (some paths didn't generate)

        // Checks that there's actually <something> (anything) useful inside of this, otherwise issue a warning.
        TemplateReportSupport.checkChildrenForContentAddWarning(ret);

        TemplateReportSupport.initIsRecursivelyErrorFree(ret);
        return ret;
    }

    private static final ExpandedName NAME = ReadFromXSLT.makeName("if");
    private static final ExpandedName ATTR_TEST = ExpandedName.makeName("test");

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
            handler.attribute(ATTR_TEST,f,null);
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

    public Binding cloneShallow() {
        return new IfBinding(getElementInfo(),this.getFormula());
    }
}
