package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.ErrorMessage;
import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.MessageCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinitionList;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

public final class VariableReferenceExpr extends Expr
{
    private final ExpandedName mVariableName;
    private final String mLeftwhitespace;

    public VariableReferenceExpr(ExpandedName variableName,
                                 TextRange range,
                                 String leftwhitespace,
                                 String trailingWhitespace) {
        super(range, trailingWhitespace);
        mVariableName = variableName;
        if (leftwhitespace==null) {
            mLeftwhitespace = "";
        } else {
            mLeftwhitespace = leftwhitespace;
        }
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        VariableDefinitionList vars = context.getVariables();
        VariableDefinition v = vars.getVariable(mVariableName);
        if (v==null)
        {
            info.addError(new ErrorMessage(ResourceBundleManager.getMessage(MessageCode.VARIABLE_NOT_DEFINED),getTextRange()));
            return info.recordReturnType(this,SMDT.PREVIOUS_ERROR);
        }
        return info.recordReturnType(this,v.getLocatedType());
    }

    public ExprContext assertEvaluatesTo(ExprContext ec, SmSequenceType type, SmCardinality optionalSpecifiedCardinality)
    {
        VariableDefinitionList v2 = (VariableDefinitionList)ec.getVariables().clone();
        v2.set(new VariableDefinition(mVariableName,type));
        v2.lock();
        return ec.createWithVariableList(v2);
    }

    public String getExprValue() {
        return mVariableName.getLocalName();
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_VARIABLE_REFERENCE;
    }

    public Expr[] getChildren() {
        return NO_CHILDREN;
    }

    public String getRepresentationClosure() {
        return mLeftwhitespace;
    }

    public void format(StringBuffer toBuffer, int style) {
        toBuffer.append('$');
        boolean isExact = style==STYLE_TO_EXACT_STRING;
        if (isExact) {
            toBuffer.append(mLeftwhitespace);
        }
        toBuffer.append(mVariableName);
        if (isExact) {
            toBuffer.append(getWhitespace());
        }
    }
}
