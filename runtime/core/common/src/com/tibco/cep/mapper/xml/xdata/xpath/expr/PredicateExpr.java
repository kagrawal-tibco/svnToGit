package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.Parser;
import com.tibco.cep.mapper.xml.xdata.xpath.ReplacementXPathFilter;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.util.RuntimeWrapException;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.data.primitive.XmlAtomicValueCastException;
import com.tibco.xml.data.primitive.XmlItem;
import com.tibco.xml.data.primitive.XmlSequence;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 */
public final class PredicateExpr extends Expr
{
    private final Expr mLeft;
    private final Expr mFilter;
    private final String mLeftwhitespace;

    public PredicateExpr(Expr left, Expr filter, TextRange range, String leftwhitespace, String whitespace) {
        super(range, whitespace);
        mLeft = left;
        mFilter = filter;
        mLeftwhitespace = leftwhitespace;
    }

    public String getRepresentationClosure() {
        return mLeftwhitespace;
    }

    public String getExprValue() {
        return null;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_PREDICATE;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info) {
        // The left side is evaluated once for each item on the context:
        if (SmSequenceTypeSupport.isPreviousError(context.getInput()))
        {
            // need evalType to call through:
            mLeft.evalType(context,info);
            mFilter.evalType(context,info);
            return info.recordReturnType(this,SMDT.PREVIOUS_ERROR);
        }
        // Use leftType to type check inside the []
        SmSequenceType leftType = mLeft.evalType(context,info);

        // The filter needs to be evaluated against a <single> item of the above:
        SmSequenceType leftSingleType = leftType.prime();
        SmSequenceType got = mFilter.evalType(context.createWithInput(leftSingleType),info);
        if (SmSequenceTypeSupport.isPreviousError(got))
        {
            //WCETODO FIXME. ( can't remember what the fix is??)
            if (mFilter.getExprTypeCode()==ExprTypeCode.EXPR_MARKER)
            {
                // special case it as a take 1-of-filter:
                return info.recordReturnType(this,leftSingleType);
            }
            // no further error:
            return info.recordReturnType(this,leftType);
        }
        addSillyPredicateWarnings(info);
        boolean number = false;
        if (SmSequenceTypeSupport.isNumber(got))
        {
            number = true;
        }
        else
        {
            // Add special case for nodes which contain numbers:
            if (SmSequenceTypeSupport.isNumber(got.typedValue(true))) // use 'true' here always because that's the nature of the check;
                // we're specifically looking for nodes that are declared numbers, xpath 1.0 or 2.0.
            {
                String msg = ResourceBundleManager.getMessage(MessageCode.COMPARED_AS_BOOLEAN_NUMBER);
                ErrorMessage em = new ErrorMessage(ErrorMessage.TYPE_WARNING,msg,mFilter.getTextRange());
                em.setFilter(new ReplacementXPathFilter(mFilter,Parser.parse("number(" + mFilter.toExactString() + ")")));
                info.addError(em);
            }
            else
            {
                // Will be converted to a boolean, check it as such:
                // Is a boolean, do check.
                info.addErrors(TypeChecker.checkConversion(got,SMDT.BOOLEAN,mFilter.getTextRange(),info.getXPathCheckArguments()));
            }
        }
        if (number) {
            // single-ize:
            // Multiply the cardinality of the result with the input (context):
            boolean cvalset = false;
            double nval = 1;
            SmCardinality xo = leftType.quantifier();
            if (got.getConstantValue()!=null) {
                XmlSequence seq = got.getConstantValue();
                XmlItem value = seq.getItem(0);
                if(value instanceof XmlAtomicValue) {
                   try {
                      nval = ((XmlAtomicValue)value).castAsDouble();
                   }
                   catch (XmlAtomicValueCastException e) {
                      throw new RuntimeWrapException(e);
                   }
                }
/*
                String val = got.getConstantValue();
                try {
                    nval = Double.parseDouble(val);
                } catch (Exception e) {
                }
*/
                if (nval<=0)
                {
                    if (info.getXPathCheckArguments().getCheckMiscWarnings())
                    {
                        info.addError(new ErrorMessage(ErrorMessage.TYPE_WARNING,ResourceBundleManager.getMessage(MessageCode.XPATH_INDEXES_1_BASED),mFilter.getTextRange()));
                    }
                }
                else
                {
                    cvalset = true;
                }
            }
            if (xo.getMaxOccurs()==0)
            {
                return info.recordReturnType(this,leftType);
            }
            SmSequenceType singleInstanceAfterPredicate;
            if (cvalset && xo.getMinOccurs()>(nval-1))
            {
                // i.e. if we're OrderLine[1] and OrderLine has minOccurs of 1, then we WILL get 1:
                singleInstanceAfterPredicate = leftType.prime();
            }
            else
            {
                // i.e. if we're OrderLine[1] and OrderLine has minOccurs of 0, then we might get 1:
                singleInstanceAfterPredicate = SmSequenceTypeSupport.createWithItemOptional(leftType.prime());
            }

            // Now multiply that by the # of things expected coming in:
//            XOccurrence q = context.getInput().quantifier();
//            XType repeats = XTypeFactory.createRepeats(singleInstanceAfterPredicate.getContext(),singleInstanceAfterPredicate,q).simplify(false);
            return info.recordReturnType(this,singleInstanceAfterPredicate);
        } else {
            SmSequenceType optional = SmSequenceTypeSupport.createWithItemOptional(leftType,true);
            // true -> Use 'unknown' optional, since the cardinality here <may> in fact be 0,1 (or 1,1) (if the filter were picking out 1 item).
            // but we can't really tell, so just it as unknown to avoid potential warnings.
            return info.recordReturnType(this,optional);
        }
    }

    public ExprContext assertEvaluatesTo(ExprContext ec, SmSequenceType type, SmCardinality optionalSpecifiedCardinality)
    {
        // Because of the predicate, the 'type' only applies to <some> of the nodes.  Since we don't
        // have a reasonable way to record this, we cannot do anything.
        return ec;
    }

    public ExprContext assertMatch(ExprContext ec)
    {
        // This is effectively saying that the predicate is true, yipee:
        ExprContext ec2 = mLeft.assertMatch(ec);
        // Given above is true, now eval type:
        SmSequenceType t = mLeft.evalType(ec2, new EvalTypeInfo());
        ExprContext givenFilterTrue = mFilter.assertBooleanValue(ec2,true);
        return givenFilterTrue;
    }

    /**
     * Adds warnings for silly (non-sensical) predicates that people have done
     */
    private void addSillyPredicateWarnings(EvalTypeInfo info) {
        // 1) Check for something[position()] --- this doesn't make sense.
        if (isPositionFunction(mFilter))
        {
            info.addError(new ErrorMessage(ErrorMessage.TYPE_WARNING,ResourceBundleManager.getMessage(MessageCode.POSITION_INSIDE_FILTER),mFilter.getTextRange()));
        }
    }

    private static boolean isPositionFunction(Expr expr) {
        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_FUNCTION_CALL)
        {
            if (expr.getExprValue().equals("position"))
            { // the position function
                return true;
            }
        }
        return false;
    }

    public Expr[] getChildren() {
        return new Expr[] {mLeft,mFilter};
    }

    public void format(StringBuffer toBuffer, int style) {
        if (style==STYLE_TO_DEBUG_STRING) {
            toBuffer.append('(');
        }
        boolean isExact = style==STYLE_TO_EXACT_STRING;
        mLeft.format(toBuffer,style);
        toBuffer.append('[');
        if (isExact) {
            toBuffer.append(mLeftwhitespace);
        }
        mFilter.format(toBuffer,style);
        toBuffer.append(']');
        if (isExact) {
            toBuffer.append(getWhitespace());
        }
        if (style==STYLE_TO_DEBUG_STRING) {
            toBuffer.append(')');
        }
    }
}
