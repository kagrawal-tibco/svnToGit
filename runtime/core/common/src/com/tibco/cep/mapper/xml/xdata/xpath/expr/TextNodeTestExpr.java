package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 */
public final class TextNodeTestExpr extends NodeTestExpr
{
    private String mLeftWhitespace;

    public TextNodeTestExpr(TextRange range, String leftWhitespace, String trailingWhitespace) {
        super(range, trailingWhitespace);
        if (leftWhitespace==null) {
            mLeftWhitespace = "";
        } else {
            mLeftWhitespace = leftWhitespace;
        }
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_TEXT_NODE_TEST;
    }

    public String getExprValue() {
        return null;
    }

    public String getTestString() {
        return "text()";
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo state) {
        SmSequenceType t = context.getInput().textTest();
        if (SmSequenceTypeSupport.isVoid(t)) {
            state.addError(new ErrorMessage("No matching text()",getTextRange()));
            return SMDT.PREVIOUS_ERROR;
        }
        return t;
    }

    public String getRepresentationClosure() {
        return mLeftWhitespace;
    }

    public void format(StringBuffer toBuffer, int style) {
        toBuffer.append("text");
        if (style==STYLE_TO_EXACT_STRING) {
            toBuffer.append(mLeftWhitespace);
        }
        toBuffer.append("()");
        if (style==STYLE_TO_EXACT_STRING) {
            toBuffer.append(getWhitespace());
        }
    }
}
