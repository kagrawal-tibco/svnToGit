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
public final class ConcatSequenceXFunction extends DefaultLastArgRequiredXFunction
{
    public static final ExpandedName NAME = TibExtFunctions.makeName("concat-sequence");

    /**
     * For FunctionList
     */
    ConcatSequenceXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.REPEATING_STRING);
    }
    
    public SmSequenceType typeCheck(SmSequenceType[] argsType, ExprContext context, EvalTypeInfo state, Expr functionCallExpr, Expr[] args)
    {
        // Do a further check so that if someone passes in a non-repeating thing, we warn:

        // We're guaranteed to have the correct # of elements in the array, so don't check:
        state.addErrors(TypeChecker.checkMoreThanOne(argsType[0],args[0].getTextRange()));
        return getReturnType();
    }
}
