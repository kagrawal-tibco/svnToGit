package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.type.XTypeSimplifier;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.XmlSequence;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 */
public final class StepExpr extends Expr
{
    private final AxisExpr mAxis;
    private final NodeTestExpr mNodeTest;
    private final boolean mAbbreviated;

    public StepExpr(AxisExpr axis, NodeTestExpr nodeTest, TextRange range, boolean abbreviated, String trailingWhitespace) {
        super(range, trailingWhitespace);
        mAxis = axis;
        mNodeTest = nodeTest;
        mAbbreviated = abbreviated;
    }

    public String getRepresentationClosure() {
        return mAbbreviated ? "abbreviated" : null;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        info.recordExprContext(this,context);
        SmSequenceType rtype = mAxis.evalType(context,info);
        if (rtype==null)
        {
            throw new NullPointerException("Axis " + mAxis.getClass().getName() + " returned null type");
        }
        ExprContext ncontext = context.createWithInput(rtype);
        SmSequenceType match = mNodeTest.evalType(ncontext,info);
        if (match==null)
        {
            throw new NullPointerException("Node test " + mNodeTest.getClass().getName() + " returned null type");
        }
        if (SmSequenceTypeSupport.isNone(context.getInput()))
        {
            // mark this as an error; we shouldn't even be evaluating here.
            info.addError(new ErrorMessage("No input available.",getTextRange())); // need way better message.
            match = SMDT.PREVIOUS_ERROR;
        }
        checkMagicAttributes(match,info);
        // One more
        return info.recordReturnType(this,match);
    }

    private void checkMagicAttributes(SmSequenceType match, EvalTypeInfo info)
    {
        // Check for xsi:nil not being appropriate:
        SmSequenceType rt = SmSequenceTypeSupport.stripOccursAndParens(match);
        ExpandedName ename = rt.getName();
        if (ename!=null && ename.getLocalName().equals(XSDL.ATTR_NIL.getName()) && ename.equals(XSDL.ATTR_NIL.getExpandedName()))
        {
            XmlSequence cv = rt.typedValue(true).getConstantValue();
//            if ("false".equals(cv))
           SmSequenceType typedValue = rt.typedValue(true);
           // added 'cv != null' to the check here.  Not sure if it is valid, but without it, it reported
           // invalid "Element not nillable" errors
           if(cv != null && false == typedValue.isNillable()) 
           {
               info.addError(new ErrorMessage(ErrorMessage.TYPE_WARNING,"Element not nillable",getTextRange()));
           }
        }
    }

    public ExprContext assertBooleanValue(ExprContext ec, boolean isTrue)
    {
        ExprContext ec2 = ec.createWithInput(mAxis.evalType(ec,new EvalTypeInfo()));
        ExprContext ec3 = mNodeTest.assertBooleanValue(ec2,isTrue);
        SmSequenceType input = ec3.getInput();
        SmSequenceType i2 = XTypeSimplifier.removeChoiceDuplicates(input); // required, o.w. can get messy choice.
        return mAxis.assertEvaluatesTo(ec,i2, null);
    }

    public ExprContext assertMatch(ExprContext ec)
    {
        SmSequenceType n1 = mAxis.evalType(ec,new EvalTypeInfo());
        ExprContext n = ec.createWithInput(n1);
        ExprContext ec2 = mNodeTest.assertMatch(n);
        return mAxis.assertEvaluatesTo(ec, ec2.getInput(), null);
    }

    public ExprContext assertEvaluatesTo(ExprContext ec, SmSequenceType type, SmCardinality optionalSpecifiedOccurrence)
    {
        // Since the node test can't really add anything, just call the axis to let it know what's up.
        SmSequenceType n1 = mAxis.evalType(ec,new EvalTypeInfo());
        ExprContext n = ec.createWithInput(n1);
        ExprContext ec2 = mNodeTest.assertEvaluatesTo(n,type, optionalSpecifiedOccurrence);
        SmSequenceType i2 = XTypeSimplifier.removeChoiceDuplicates(ec2.getInput()); // required, o.w. can get messy choice.
        return mAxis.assertEvaluatesTo(ec, i2, optionalSpecifiedOccurrence);
    }

    public ExprContext assertTypedValueEvaluatesTo(ExprContext ec, SmSequenceType type)
    {
        // Since the node test can't really add anything, just call the axis to let it know what's up.
        SmSequenceType n1 = mAxis.evalType(ec,new EvalTypeInfo());
        ExprContext n = ec.createWithInput(n1);
        ExprContext ec2 = mNodeTest.assertTypedValueEvaluatesTo(n,type);
        SmSequenceType i2 = XTypeSimplifier.removeChoiceDuplicates(ec2.getInput()); // required, o.w. can get messy choice.
        return mAxis.assertEvaluatesTo(ec, i2, null);
    }

    public String getExprValue() {
        return null;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_STEP;
    }

    public Expr[] getChildren() {
        return new Expr[] {mAxis,mNodeTest};
    }

    /**
     * Formats the expression in its abbreviated format (if there is one), otherwise does no formatting, returns
     * if it did any formatting.
     * @param toBuffer Write to the buffer.
     * @return true if it has an abbreviated format & was written out, false if it doesn't & was not written out.
     */
    private boolean formatAbbrevString(StringBuffer toBuffer, int style) {
        // Check for abbreviations:
	    boolean exact = style == STYLE_TO_EXACT_STRING;
        if (mAxis instanceof SelfAxisExpr && mNodeTest instanceof NodeNodeTestExpr) {
            if (exact) {
                toBuffer.append(".");
                toBuffer.append(getWhitespace());
            } else {
                toBuffer.append(".");
            }
            return true;
        }
        if (mAxis instanceof ParentAxisExpr && mNodeTest instanceof NodeNodeTestExpr) {
            if (exact) {
                toBuffer.append("..");
                toBuffer.append(getWhitespace());
            } else {
                toBuffer.append("..");
            }
            return true;
        }
        if (mAxis instanceof ChildAxisExpr) {
	        mNodeTest.format(toBuffer,style);
            return true;
        }
        if (mAxis instanceof AttributeAxisExpr) {
            toBuffer.append("@");
            if (exact) {
                toBuffer.append(getWhitespace());
            }
	        mNodeTest.format(toBuffer,style);
            return true;
        }
        if (mAxis instanceof DescendantOrSelfAxisExpr && mNodeTest instanceof NodeNodeTestExpr) {
            toBuffer.append("//");
            if (exact) {
                toBuffer.append(getWhitespace());
            }
            return true;
        }
        // no abbreviation.
        return false;
    }

    public boolean isSlashSlashAbbrev() {
        return mAxis instanceof DescendantOrSelfAxisExpr && mNodeTest instanceof NodeNodeTestExpr && mAbbreviated;
    }

    public boolean isAbbrevChild() {
        return mAxis instanceof ChildAxisExpr && mAbbreviated;
    }

    public final void format(StringBuffer toBuffer, int style) {
        // First see if it has an abbreviated format, and if so, write it out that way:
        boolean abbreviatedFormat;
        if (style==STYLE_TO_STRING || style == STYLE_TO_STRING_NO_PREFIX) {
            abbreviatedFormat = formatAbbrevString(toBuffer,style);
        } else {
            if (style==STYLE_TO_EXACT_STRING && mAbbreviated) {
                abbreviatedFormat = formatAbbrevString(toBuffer,style);
            } else {
                abbreviatedFormat = false;
            }
        }
        // It is not (for whatever reason above), an abbreviated format, so it needs to be output now:
        if (!abbreviatedFormat) {
            mAxis.format(toBuffer,style);
            mNodeTest.format(toBuffer,style);
        }
    }
}
