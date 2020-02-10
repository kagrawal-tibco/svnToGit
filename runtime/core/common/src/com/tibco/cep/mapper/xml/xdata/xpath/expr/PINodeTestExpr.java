package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.Token;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.helpers.NoNamespace;

/**
 */
public final class PINodeTestExpr extends NodeTestExpr
{
    private String mInstructionName;

    public PINodeTestExpr(String instructionName, TextRange range, String trailingWhitespace) {
        super(range,trailingWhitespace);
        mInstructionName = instructionName;
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_PI_NODE_TEST;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo state) {
        return context.getInput().processingInstructionTest(
                ExpandedName.makeName(NoNamespace.URI, mInstructionName),
                null); // jtb -- "null" may be improper value...???
    }

    public String getExprValue() {
        return null;
    }

    public String getTestString() {
        return "processing-instruction()";
    }

    public void format(StringBuffer toBuffer, int style) {
        String name = mInstructionName==null ? "" : Token.formatLiteralString(mInstructionName);
        toBuffer.append("processing-instruction(");
        toBuffer.append(name);
        toBuffer.append(')');
        if (style==STYLE_TO_EXACT_STRING) {
            toBuffer.append(getWhitespace());
        }
    }
}
