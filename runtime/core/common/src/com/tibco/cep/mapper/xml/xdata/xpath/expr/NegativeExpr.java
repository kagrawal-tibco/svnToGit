package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.data.primitive.XmlAtomicValue;
import com.tibco.xml.data.primitive.XmlItem;
import com.tibco.xml.data.primitive.XmlSequence;
import com.tibco.xml.data.primitive.values.XsDouble;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

/**
 */
public final class NegativeExpr extends Expr
{
    private Expr mRight;

    public NegativeExpr(Expr right, TextRange range, String trailingWhitespace) {
        super(range, trailingWhitespace);
        mRight = right;
    }

    public String getExprValue() {
        return null;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info) {
        SmSequenceType prevRet = info.getCodeCompleteReturnType();
        info.setCodeCompleteReturnType(SMDT.DOUBLE);
        int priorWarnings = info.getErrorCount();

        SmSequenceType t = mRight.evalType(context,info); // add type check...

        // Avoid giving 2 warnings on the same number:
        boolean hadNumberWarning = mRight instanceof NumberExpr && (priorWarnings!=info.getErrorCount());

        info.setCodeCompleteReturnType(prevRet);
//        String val = t.getConstantValue();
        String val = null;
        XmlSequence seq = t.getConstantValue();
        XmlItem item = seq.getItem(0);
        if(item instanceof XmlAtomicValue) {
           val = ((XmlAtomicValue)item).castAsString();
        }
        if (val!=null && !hadNumberWarning)
        {
            try
            {
                String nsval;
                if (val.startsWith("-"))
                {
                    nsval = val.substring(1);
                }
                else
                {
                    nsval = "-" + val;
                }
                NumberExpr.checkNumber(nsval,info,getTextRange());
                return info.recordReturnType(this,SmSequenceTypeFactory.createAtomic(
                        XSDL.DOUBLE,
                        XsDouble.compile(nsval)));
            }
            catch (Exception e)
            {
            }
        }
        return info.recordReturnType(this,SMDT.DOUBLE);
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_NEGATIVE;
    }

    public Expr[] getChildren() {
        return new Expr[] {mRight};
    }

    public void format(StringBuffer toBuffer, int style) {
        if (style==STYLE_TO_DEBUG_STRING) {
            toBuffer.append("(");
        }
        toBuffer.append('-');
        if (style==STYLE_TO_EXACT_STRING) {
            toBuffer.append(getWhitespace());
        }
        if (style==STYLE_TO_DEBUG_STRING) {
            toBuffer.append(' '); // for clarity.
        }
        mRight.format(toBuffer,style);
        if (style==STYLE_TO_DEBUG_STRING) {
            toBuffer.append(")");
        }
    }
}
