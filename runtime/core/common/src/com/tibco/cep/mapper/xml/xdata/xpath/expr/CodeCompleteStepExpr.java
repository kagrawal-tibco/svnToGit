package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import java.util.ArrayList;
import java.util.Arrays;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.CodeCompleteData;
import com.tibco.cep.mapper.xml.xdata.xpath.CodeCompleteResult;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

/**
 * A pseudo-Expr subclass used for doing Code Complete text completion.
 */
public final class CodeCompleteStepExpr extends NodeTestExpr
{
    private String mName;

    public CodeCompleteStepExpr(String name, TextRange range) {
        super(range,"");
        mName = name;
    }

    public int getExprTypeCode()
    {
        return ExprTypeCode.EXPR_CODE_COMPLETE; // for now.
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info) {
        ArrayList ret = new ArrayList();
        SmSequenceType nameMatchAgainst = SmSequenceTypeFactory.createChoice(context.getInput().attributeAxis(),context.getInput().childAxis()).simplify(false); // assume attribute or child axis:
        ExprContext nameMatchInput = context.createWithInput(nameMatchAgainst);
        CodeCompleteNameTestExpr.addCodeCompletesForNameTest(nameMatchInput,mName,true,ret);
        CodeCompleteResult[] res = (CodeCompleteResult[]) ret.toArray(new CodeCompleteResult[0]);
        Arrays.sort(res,CodeCompleteNameTestExpr.ORDERER);
        info.setCodeComplete(new CodeCompleteData(context.getInput(),info.getCodeCompleteReturnType(),res,getTextRange()));
        // Add an error --- someone could actually type this (%%)!
        String msg = ResourceBundleManager.getMessage(MessageCode.UNEXPECTED_TOKEN,"%%");
        info.addError(new ErrorMessage(msg,getTextRange()));
        return SMDT.PREVIOUS_ERROR;
    }

    public void format(StringBuffer toBuffer, int style) {
        toBuffer.append("%%");
    }

    public String getExprValue() {
        return mName;
    }

    public String getTestString() {
        return mName + "%%";//node()";
    }
}
