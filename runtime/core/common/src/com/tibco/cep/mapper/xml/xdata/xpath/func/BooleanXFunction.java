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
public final class BooleanXFunction extends DefaultLastArgOptionalXFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName("boolean");

    public BooleanXFunction()
    {
        super(NAME,SMDT.BOOLEAN,SMDT.ITEM);
    }

    public boolean isIndependentOfFocus(int numArgs) {
        if (numArgs==0)
        {
            // implicitly gets '.' in this case, so no.
            return false;
        }
        // otherwise, yes.
        return true;
    }

    public SmSequenceType typeCheck(SmSequenceType[] argsType, ExprContext context, EvalTypeInfo state, Expr functionCallExpr, Expr[] args)
    {
        // Do more extensive type-checking here:
        SmSequenceType arg;
        if (argsType.length>0)
        {
            arg = argsType[0];
        }
        else
        {
            arg = context.getInput();
        }
        state.addErrors(TypeChecker.checkConversion(arg,SMDT.BOOLEAN,args[0].getTextRange(),state.getXPathCheckArguments()));
        return getReturnType();
    }
}
