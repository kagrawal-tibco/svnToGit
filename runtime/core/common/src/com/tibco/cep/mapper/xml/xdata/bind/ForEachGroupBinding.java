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
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Represents an XSLT for-each-group operation.
 */
public class ForEachGroupBinding extends ControlBinding
{
    private String m_grouping;
    private int m_groupType;

    public static final int GROUP_TYPE_GROUP_BY = 0;
    public static final int GROUP_TYPE_GROUP_ADJACENT = 1;
    public static final int GROUP_TYPE_GROUP_STARTING_WITH = 2;
    public static final int GROUP_TYPE_GROUP_ENDING_WITH = 3;

    public ForEachGroupBinding(BindingElementInfo ns)
    {
        this(ns,null);
    }

    public ForEachGroupBinding(BindingElementInfo ns, String formula)
    {
        super(ns, formula);
    }

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
        // we could have not gone through this thing at all...
        ret.setRemainingOutputType(remainingType);

        TemplateReportSupport.bubbleOutMissingPrecedingTerms(ret);

        checkGrouping(ret);

        checkSortPlacement(ret);

        SmSequenceType matchedType = TemplateReportSupport.getMatchedChildTerm(ret);
        ret.setExpectedType(matchedType);
        ForEachBinding.checkForEachOutput(ret,templateReportArguments);

        // Make sure this is actually doing something:
        TemplateReportSupport.checkChildrenForContentAddWarning(ret);

        TemplateReportSupport.initIsRecursivelyErrorFree(ret);
        return ret;
    }

    /**
     * Checks the group-by, allows virtual override.
     * @param report
     */
    public void checkGrouping(TemplateReport report)
    {
        checkGroupingFormula(report,m_grouping);
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

    public static void checkGroupingFormula(TemplateReport report, String formula)
    {
    }

    private static ExpandedName NAME = ExpandedName.makeName(ReadFromXSLT.XSLT_URI,"for-each-group");

    private static ExpandedName GROUP_BY_ATTR = ExpandedName.makeName("group-by");
    private static ExpandedName SELECT_ATTR = ExpandedName.makeName("select");

    /**
     * Checks for sort positions.
     */
    private void checkSortPlacement(TemplateReport report)
    {
        int cc = report.getChildCount();
        boolean hasSeenNonSort = false;
        TemplateReport lastGoodSpot = null;
        for (int i=0;i<cc;i++) {
            TemplateReport b = report.getChild(i);
            if (b.getBinding() instanceof SortBinding) {
                if (hasSeenNonSort)
                {
                    ForEachBinding.addSortError(b,lastGoodSpot);
                }
                else
                {
                    lastGoodSpot = b;
                }
            } else {
                if (b.getBinding() instanceof CommentBinding) {
                    // ok, skip it.
                } else {
                    if (!isChildAllowedBeforeSort(b.getBinding()))
                    {
                        lastGoodSpot = b;
                        hasSeenNonSort = true;
                    }
                }
            }
        }
    }

    public boolean isChildAllowedBeforeSort(Binding b)
    {
        return false;
    }

    public void formatFragment(XmlContentHandler handler) throws SAXException {
        handler.startElement(NAME,null,null);
        String f = getFormula();
        if (f!=null)
        {
            handler.attribute(SELECT_ATTR,f,null);
        }
        if (m_grouping!=null)
        {
            handler.attribute(GROUP_BY_ATTR,m_grouping,null);
        }

        for (int i=0;i<getChildCount();i++) {
            getChild(i).formatFragment(handler);
        }
        handler.endElement(NAME,null,null);
    }

    public String getGrouping() {
        return m_grouping;
    }

    public void setGrouping(String groupBy) {
        m_grouping = groupBy;
    }

    public void setGroupType(int groupType)
    {
        m_groupType = groupType;
    }

    public int getGroupType()
    {
        return m_groupType;
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
        ForEachGroupBinding feg = new ForEachGroupBinding(getElementInfo(),this.getFormula());
        feg.setGrouping(getGrouping());
        feg.setGroupType(getGroupType());
        return feg;
    }
}
