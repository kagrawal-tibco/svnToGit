package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.ReplacementXPathFilter;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

public abstract class ComparisonExpr extends OperatorExpr
{
    protected ComparisonExpr(Expr left, Expr right, TextRange range, String trailingWhitespace) {
        super(left,right,range, trailingWhitespace);
    }

    private void evalNodeSetToPrimitive(SmSequenceType nodeSet, Expr forExpr, SmSequenceType primitive, Expr primExpr, EvalTypeInfo info)
    {
        if (SmSequenceTypeSupport.isPreviousError(primitive))
        {
            return;
        }
        SmCardinality card = nodeSet.quantifier();
        if (card.getMaxOccurs()>1 && card.isKnown()) {
            // add warning now... todo, though, need a recommended way to fix this:
            addMultipleComparisonWarning(info);
        }
        if (SmSequenceTypeSupport.isPrimitiveBoolean(primitive))
        {
            addComparedAsBooleanWarning(info,forExpr,primExpr);
        }
    }

    private void evalStringToPrimitive(Expr forExpr, SmSequenceType primitive, Expr primExpr, EvalTypeInfo info)
    {
        if (SmSequenceTypeSupport.isPreviousError(primitive))
        {
            return;
        }
        if (SmSequenceTypeSupport.isPrimitiveBoolean(primitive))
        {
            addComparedAsBooleanWarning(info,forExpr,primExpr);
        }
    }

    public final SmSequenceType evalOpType(SmSequenceType lhs, SmSequenceType rhs, ExprContext context, EvalTypeInfo info) {
        if (SmSequenceTypeSupport.isNodeSet(lhs))
        {
            if (SmSequenceTypeSupport.isNodeSet(rhs))
            {
                SmCardinality lhsc = lhs.quantifier();
                SmCardinality rhsc = rhs.quantifier();
                // tough case, string values... just pass through ok for now.
                // (maybe a warning, this is pretty dubious)
                if (lhsc.getMaxOccurs()>1 && lhsc.isKnown())
                {
                    addMultipleComparisonWarning(info);
                }
                if (rhsc.getMaxOccurs()>1 && rhsc.isKnown())
                {
                    addMultipleComparisonWarning(info);
                }
            }
            else
            {
                evalNodeSetToPrimitive(lhs, mLeft, rhs, mRight, info);
            }
        } else {
            if (SmSequenceTypeSupport.isNodeSet(rhs))
            {
                evalNodeSetToPrimitive(rhs, mRight, lhs, mLeft, info);
            }
            else
            {
                if (SmSequenceTypeSupport.isString(lhs)) // should be isPrimitiveString
                {
                    evalStringToPrimitive(mLeft,rhs,mRight,info);
                }
            }
        }
        // add further checks... warnings for dubious stuff.
        return info.recordReturnType(this,SMDT.BOOLEAN);
    }

    private void addMultipleComparisonWarning(EvalTypeInfo info) {
        // Since there's no workaround for now, turn this off:
        //info.addError(new ErrorMessage(ErrorMessage.TYPE_WARNING,"Comparing against a set of values; will be true if the comparison against any one of the values in the set is true",getTextRange()));
    }

    private void addComparedAsBooleanWarning(EvalTypeInfo info, Expr forExpr, Expr boolExpr)
    {
        String msg = ResourceBundleManager.getMessage(MessageCode.COMPARED_AS_BOOLEAN);
        ErrorMessage em = new ErrorMessage(ErrorMessage.TYPE_WARNING,msg,forExpr.getTextRange());
        String fixedResult;
        String te = boolExpr.toExactString().trim();
        // These are all xpaths, not localized:
        if (te.equals("true()"))
        {
            fixedResult = "\"true\"";
        }
        else
        {
            if (te.equals("false()"))
            {
                fixedResult = "\"false\"";
            }
            else
            {
                fixedResult = "string(" + te + ")";
            }
        }

        em.setFilter(new ReplacementXPathFilter(boolExpr,Parser.parse(fixedResult)));
        info.addError(em);
    }
}
