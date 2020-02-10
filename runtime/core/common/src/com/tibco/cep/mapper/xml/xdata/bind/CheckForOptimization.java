package com.tibco.cep.mapper.xml.xdata.bind;

import java.util.HashSet;
import java.util.Set;

import org.xml.sax.SAXException;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.bind.fix.BindingFixerChange;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprUtilities;
import com.tibco.cep.mapper.xml.xdata.xpath.FunctionResolver;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.ReplacementXPathFilter;
import com.tibco.xml.channel.infoset.XmlContentHandler;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.NamespaceToPrefixResolver;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.schema.helpers.NoNamespace;

/**
 * Does a check for mapper optimization, specifically, expressions that can be moved outside of the current context.
 */
public final class CheckForOptimization
{
    public static final void checkForOptimization(TemplateReport report)
    {
        Expr e = report.getXPathExpression();
        if (e==null)
        {
            // forget it.
            return;
        }
        checkForPredicate(report,e);
    }

    private static final void checkForPredicate(TemplateReport r, Expr e)
    {
        if (e.getExprTypeCode()==ExprTypeCode.EXPR_PREDICATE)
        {
            checkForPredicateOptimization(r,e.getChildren()[1]);
        }
        Expr[] c = e.getChildren();
        for (int i=0;i<c.length;i++)
        {
            checkForPredicate(r,c[i]);
        }
    }

    private static final void checkForPredicateOptimization(TemplateReport report, final Expr predicateExpr)
    {
        if (ExprUtilities.isSimpleConstant(predicateExpr) || ExprUtilities.isVariableReference(predicateExpr) || ExprUtilities.isNoArgFunction(predicateExpr))
        {
            // forget it, can't do much more.
            return;
        }
        FunctionResolver fr = report.getContext().getXPathFunctionSet();
        PrefixToNamespaceResolver r = report.getContext().getNamespaceMapper();
        boolean isIndep;
        try
        {
            isIndep = ExprUtilities.isIndependentOfDotAndPosition(predicateExpr,fr,r);
        }
        catch (PrefixToNamespaceResolver.PrefixNotFoundException n)
        {
            // ignore.
            return; // forget it.
        }
        if (!isIndep)
        {
            // nope, nothing to do here, recursively scan further:
            Expr[] ch = predicateExpr.getChildren();
            for (int i=0;i<ch.length;i++)
            {
                checkForPredicateOptimization(report,ch[i]);
            }
            return;
        }
        if (report.getBinding().getParent()==null)
        {
            return; // just in case.
        }
        TemplateReportExtendedError ee = new TemplateReportExtendedError()
        {
            public boolean canFix(TemplateReport onReport)
            {
                return true;
            }

            public void fix(TemplateReport templateReport)
            {
                String vn = findUniqueVariableName(templateReport.getBinding(),templateReport.getXPathExpression());
                SetVariableBinding svb = new SetVariableBinding(BindingElementInfo.EMPTY_INFO,
                                                                ExpandedName.makeName(NoNamespace.URI, vn),
                                                                predicateExpr.toExactString());

                TemplateReport insertAtReport = findNearestAreaForVariables(templateReport);
                Binding b = insertAtReport.getBinding();
                int ioc = b.getParent().getIndexOfChild(b);
                b.getParent().addChild(ioc,svb);
                String ws = getTrailingWhitespace(predicateExpr.toExactString());
                Expr v = Expr.create(ExprTypeCode.EXPR_VARIABLE_REFERENCE,new Expr[0],vn,ws,"");
                ReplacementXPathFilter rf = new ReplacementXPathFilter(predicateExpr.getTextRange(),v);
                Expr e2 = rf.filter(templateReport.getXPathExpression(),templateReport.getContext().getNamespaceMapper());
                templateReport.setXPathExpression(e2); // so if there are more than 1 fix that it works.
                templateReport.getBinding().setFormula(e2.toExactString());
            }

            public void formatFragment(XmlContentHandler handler, NamespaceToPrefixResolver resolver) throws SAXException
            {
            }

            public String formatMessage(TemplateReport templateReport)
            {
                return ResourceBundleManager.getMessage(MessageCode.OPTIMIZE_MOVE_OUT_OF_PREDICATE,""+predicateExpr);
            }

            public int getCategory()
            {
                return BindingFixerChange.CATEGORY_OPTIMIZATION;
            }

            public void registerNamespaces(NamespaceContextRegistry nsContextRegistry)
            {
            }
        };
        report.addExtendedError(ee);
    }

    /**
     * Finds the nearest report where variables can safely be added as a sibling (i.e. not in a choose or call-template)
     * @param report The report.
     * @return The report.
     */
    private static TemplateReport findNearestAreaForVariables(TemplateReport report)
    {
        //WCETODO make this a method somewhere?
        Binding p = report.getBinding().getParent();
        if (p instanceof ChooseBinding || p instanceof CallTemplateBinding)
        {
            return findNearestAreaForVariables(report.getParent());
        }
        return report;
    }

    private static String getTrailingWhitespace(String str)
    {
        StringBuffer sb = new StringBuffer();
        for (int i=str.length()-1;i>=0;i--)
        {
            char c = str.charAt(i);
            if (Character.isWhitespace(c))
            {
                sb.append(c);
            }
            else
            {
                break;
            }
        }
        return sb.toString();
    }

    private static String findUniqueVariableName(Binding checkBinding, Expr checkFormula)
    {
        HashSet set = new HashSet();
        TemplateBinding tb = BindingManipulationUtils.getContainingTemplateBinding(checkBinding);
        findAllVariablesAbove(set,tb); // avoid duplictes of params, etc.
        findAllVariablesBelow(set,tb); // globally avoid duplicates in the template (overkill, but nicer for debugging, etc.)
        ExprUtilities.getDeclaredVariables(checkFormula,set);
        return findUniqueVariableName(set,"var");
    }

    private static void findAllVariablesAbove(Set taken, Binding at)
    {
        if (at==null)
        {
            return;
        }
        for (int i=0;i<at.getChildCount();i++)
        {
            Binding c = at.getChild(i);
            if (c instanceof ParamBinding)
            {
                taken.add(((ParamBinding)c).getParamName());
            }
            else
            {
                if (c instanceof SetVariableBinding)
                {
                    taken.add(((SetVariableBinding)c).getVariableName());
                }
            }
        }
        findAllVariablesAbove(taken,at.getParent());
    }

    private static void findAllVariablesBelow(Set taken, Binding at)
    {
        if (at==null)
        {
            return;
        }
        for (int i=0;i<at.getChildCount();i++)
        {
            Binding c = at.getChild(i);
            if (c instanceof ParamBinding)
            {
                taken.add(((ParamBinding)c).getParamName());
            }
            else
            {
                if (c instanceof SetVariableBinding)
                {
                    taken.add(((SetVariableBinding)c).getVariableName());
                }
            }
            findAllVariablesBelow(taken,c);
        }
    }

    private static String findUniqueVariableName(Set taken, String name)
    {
        for (int i=1;;i++)
        {
            String t = name + ((i==1) ? "" : "" + i);
            if (!taken.contains(t))
            {
                return t;
            }
        }
    }
}

