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
public abstract class AbstractNumericGroupXFunction extends DefaultLastArgRequiredXFunction
{
    /**
     * For FunctionList
     */
    AbstractNumericGroupXFunction(ExpandedName name)
    {
        super(name,SMDT.DOUBLE,SMDT.REPEATING_DOUBLE);
    }

    public SmSequenceType typeCheck(SmSequenceType[] argsType, ExprContext context, EvalTypeInfo state, Expr functionCallExpr, Expr[] args)
    {
        // Although we allow more 0,1, it is non-sense to pass in something that can never have more than 1; check for that.
        if (state.getXPathCheckArguments().getIncludePrimitiveTypeConversions())
        {
            state.addErrors(TypeChecker.checkMoreThanOne(argsType[0],args[0].getTextRange()));
        }
        return getReturnType();
    }
}
