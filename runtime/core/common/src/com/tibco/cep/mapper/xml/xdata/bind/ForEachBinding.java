/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.Map;
import java.util.Set;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessageList;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprUtilities;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmParticle;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 * Represents an XSLT for-each operation.
 */
public class ForEachBinding extends ControlBinding
{
    public ForEachBinding(BindingElementInfo ns)
    {
        super(ns);
    }

    public ForEachBinding(BindingElementInfo ns, String formula)
    {
        super(ns, formula);
    }


    public Binding cloneShallow() {
        return new ForEachBinding(getElementInfo(),getFormula());
    }

    private static final ExpandedName NAME = ReadFromXSLT.makeName("for-each");

    public TemplateReport createTemplateReport(TemplateReport parent, ExprContext context, SmSequenceType expectedOutput, SmSequenceType outputContext, int outputValidationLevel, TemplateReportFormulaCache templateReportFormulaCache, TemplateReportArguments templateReportArguments) {
        TemplateReport ret = new TemplateReport(this,parent);
        ExprContext updatedContext = TemplateReportSupport.createContextForNamespaceDecls(this,context);
        ret.setContext(updatedContext);
        ret.setInitialOutputType(expectedOutput);

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

        SmSequenceType formulaType = ret.getFormulaType();
        SmSequenceType singularized = formulaType.prime();
        ExprContext childContext = updatedContext.createWithInput(singularized).createWithCurrent(singularized);
        ret.setChildContext(childContext);
        ret.setFollowingContext(context);
        ret.setOutputContext(outputContext);
        ret.setChildOutputContext(outputContext);
        SmSequenceType remainingType = TemplateReportSupport.traverseChildren(ret,expectedOutput,outputValidationLevel,templateReportFormulaCache,templateReportArguments);

        //validateRepeatingCardinality()
        SmSequenceType matchedType = TemplateReportSupport.getMatchedChildTerm(ret);
        ret.setExpectedType(matchedType);
        checkForEachOutput(ret,templateReportArguments);

        if (templateReportArguments.getCheckForMove())
        {
            TemplateReport first = TemplateReportSupport.findFirstOutputProducingChild(ret);
            TemplateReportSupport.bubbleOutMove(first, parent, ret);
        }

        // we could have not gone through this thing at all...
        ret.setRemainingOutputType(remainingType);

        TemplateReportSupport.bubbleOutMissingPrecedingTerms(ret);

        checkSortPlacement(ret);

        // Make sure this is actually doing something:
        TemplateReportSupport.checkChildrenForContentAddWarning(ret);

        TemplateReportSupport.initIsRecursivelyErrorFree(ret);
        return ret;
    }

    static void checkForEachOutput(TemplateReport ret, TemplateReportArguments args)
    {
        SmSequenceType matchedType = ret.getExpectedType();
        if (matchedType!=null)
        {
            SmSequenceType formulaType = ret.getFormulaType();
            // cardinality check:
            if (formulaType!=null)
            {
                //WCETODO make this code more common...
                SmCardinality ftq = formulaType.quantifier();
                SmCardinality mtq = matchedType.quantifier();
                if (ftq.getMaxOccurs()>mtq.getMaxOccurs())
                {
                    // Certain schemas (EDI for example), often have a construct like:
                    // (A | B | C | D)(1,3)
                    // where, in reality, you can only ever have 1 C (for example).
                    // So the following line tightens the requirement for this warning a little to acommodate those:
                    // (The check is disable-able w/ the lenient max occurs check flag.)
                    if (!args.getXPathCheckArguments().getLenientMaxOccursChecking() || (ftq.getMinOccurs()>mtq.getMaxOccurs() || (ftq.getMaxOccurs()==SmParticle.UNBOUNDED && mtq.getMaxOccurs()<=1)))
                    {
                        String msg = ResourceBundleManager.getMessage(MessageCode.CAN_GENERATE_MORE_THAN_EXPECTED,SmSequenceTypeSupport.getDisplayName(matchedType));
                        ErrorMessageList eml = new ErrorMessageList(new ErrorMessage(ErrorMessage.TYPE_WARNING,msg,ret.getXPathExpression().getTextRange()));
                        ret.addFormulaErrors(eml);
                    }
                }
            }
        }
    }

    /**
     * Checks for sort positions.
     */
    private static void checkSortPlacement(TemplateReport report)
    {
        int cc = report.getChildCount();
        boolean hasSeenNonSort = false;
        TemplateReport lastGoodSpot = null;
        for (int i=0;i<cc;i++) {
            TemplateReport b = report.getChild(i);
            if (b.getBinding() instanceof SortBinding) {
                if (hasSeenNonSort)
                {
                    addSortError(b,lastGoodSpot);
                }
                else
                {
                    lastGoodSpot = b;
                }
            } else {
                if (b.getBinding() instanceof CommentBinding) {
                    // ok, skip it.
                } else {
                    lastGoodSpot = b;
                    hasSeenNonSort = true;
                }
            }
        }
    }

    public static void addSortError(TemplateReport b, TemplateReport lastGoodSpot)
    {
        b.setStructuralError(ResourceBundleManager.getMessage(MessageCode.SORT_STATEMENTS_BEFORE_ANYTHING),lastGoodSpot);
        // because we're setting the structural error externally, we must manually update this flag:
        b.setIsRecursivelyErrorFree(false);
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

    public ExpandedName getName() {
        return NAME;
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        super.issueNamespaceDeclarations(handler);
        handler.startElement(NAME,null,null);
        if (getFormula()!=null)
        {
            handler.attribute(ATTR_SEL,getFormula(),null);
        }
        for (int i=0;i<getChildCount();i++)
        {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(NAME,null,null);
    }

    private static final ExpandedName ATTR_SEL = ExpandedName.makeName("select");
}
