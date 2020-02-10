package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 */
public final class IfExpr extends Expr
{
    private final Expr mCondition;
    private final Expr mThen;
    private final Expr mElse;
    private final String m_extraWhitespace;

    public IfExpr(Expr condition, Expr then, Expr elsee, TextRange range, String extraWhitespace)
    {
        super(range,""); // whitespace is a real pain here.
        mCondition = condition;
        mThen = then;
        mElse = elsee;
        m_extraWhitespace = extraWhitespace;
    }

    public Expr[] getChildren() {
        return new Expr[] {mCondition,mThen,mElse};
    }

    public String getExprValue() {
        return null;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_IF;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo state)
    {
        SmSequenceType ct = mCondition.evalType(context,state);
        state.addErrors(TypeChecker.checkConversion(ct,SMDT.BOOLEAN,mCondition.getTextRange(),state.getXPathCheckArguments()));
        SmSequenceType tt = mThen.evalType(context,state);
        SmSequenceType et = mElse.evalType(context,state);
        if (tt==et) { // cool.
            return tt;
        }
        if (tt.equalsType(et)) {
            return tt; // still pretty cool.
        }
        if (SmSequenceTypeSupport.isPreviousError(tt) || SmSequenceTypeSupport.isPreviousError(et)) {
            return SMDT.PREVIOUS_ERROR;
        }
        return SmSequenceTypeFactory.createChoice(tt,et);
    }

    public void format(StringBuffer toBuffer, int style)
    {
        String ws = m_extraWhitespace;
        if (ws==null)
        {
            ws = "";
        }
        boolean isExact = style==STYLE_TO_EXACT_STRING;
        toBuffer.append("if");
        int from = 0;
        if (isExact)
        {
            int i = ws.indexOf(',',from);
            if (i>=0)
            {
                toBuffer.append(ws.substring(from,i));
                from = i+1;
            }
            else
            {
                toBuffer.append(' '); // canonical.
            }
        }
        else
        {
            toBuffer.append(' ');
        }
        toBuffer.append("(");

        mCondition.format(toBuffer,style);
        toBuffer.append(")");
        if (isExact)
        {
            int i = ws.indexOf(',',from);
            if (i>=0)
            {
                toBuffer.append(ws.substring(from,i));
                from = i+1;
            }
            else
            {
                toBuffer.append(' '); // canonical.
            }
        }
        else
        {
            toBuffer.append(' ');
        }
        toBuffer.append("then");
        if (isExact)
        {
            int i = ws.indexOf(',',from);
            if (i>=0)
            {
                String s = ws.substring(from,i);
                if (s.length()==0)
                {
                    s = " "; // need something always.
                }
                toBuffer.append(s);
                from = i+1;
            }
            else
            {
                toBuffer.append(' '); // canonical.
            }
        }
        else
        {
            toBuffer.append(' ');
        }
        mThen.format(toBuffer,style);
        if (!isExact)
        {
            toBuffer.append(' ');
        }
        else
        {
            // hacky, but just in case someone earlier forgot trailing whitespace:
            if (!Character.isWhitespace(toBuffer.charAt(toBuffer.length()-1)))
            {
                toBuffer.append(' ');
            }
        }
        toBuffer.append("else");
        if (isExact)
        {
            int i = ws.length();
            if (i>from)
            {
                String s = ws.substring(from,i);
                if (s.length()==0)
                {
                    s = " "; // need something always.
                }
                toBuffer.append(s);
                from = i+1;
            }
            else
            {
                toBuffer.append(' '); // canonical.
            }
        }
        else
        {
            toBuffer.append(' ');
        }
        mElse.format(toBuffer,style);
    }

    public String getRepresentationClosure()
    {
        return m_extraWhitespace;
    }
}
