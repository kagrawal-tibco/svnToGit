package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.TypeChecker;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class EmptyXFunction extends DefaultLastArgRequiredXFunction
{
    public static final ExpandedName NAME = ExpandedName.makeName(TibXPath20Functions.NAMESPACE, "empty");

    /**
     * For FunctionList
     */
    EmptyXFunction()
    {
        super(NAME,SMDT.BOOLEAN,SMDT.REPEATING_ITEM);
    }

    public SmSequenceType typeCheck(SmSequenceType[] argsType, ExprContext context, EvalTypeInfo state, Expr functionCallExpr, Expr[] args)
    {
        state.addErrors(TypeChecker.checkMustBeOptional(argsType[0],args[0].getTextRange(),false));
        return getReturnType();
    }
}
