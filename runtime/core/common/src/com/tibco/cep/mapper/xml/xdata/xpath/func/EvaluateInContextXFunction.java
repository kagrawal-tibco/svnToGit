package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * Implements a dynamic xpath.
 */
public final class EvaluateInContextXFunction implements XFunction
{
    private static final ExpandedName NAME = ExpandedName.makeName(TibExtFunctions.NAMESPACE,"evaluate");

    /**
     * For FunctionList
     */
    EvaluateInContextXFunction()
    {
    }

    public int getMinimumNumArgs()
    {
        return 1;
    }

    public int getNumArgs()
    {
        return 2;
    }

    public SmSequenceType getArgType(int argNum, int totalArgs)
    {
        // (This one takes nodes first if there is 1 arg, and a string 2nd, otherwise takes a string first)
        if (totalArgs==2)
        {
            if (argNum==0)
            {
                return SMDT.REPEATING_NODE;
            }
            return SMDT.STRING;
        }
        else
        {
            return SMDT.STRING;
        }
    }

    public boolean getLastArgRepeats()
    {
        return false;
    }

    public ExpandedName getName() {
        return NAME;
    }

    public SmSequenceType getReturnType()
    {
        return SMDT.REPEATING_NODE;
    }

    public boolean hasNonSubTreeTraversal()
    {
        return true; // it might.
    }

    public boolean isIndependentOfFocus(int numArgs)
    {
        if (numArgs==0)
        {
            // implicit .
            return false;
        }
        return true;
    }

    public SmSequenceType typeCheck(SmSequenceType[] argsType, ExprContext context, EvalTypeInfo state, Expr functionCallExpr, Expr[] args)
    {
        // can't do much here.... it's totally dynamic.
        return getReturnType();
    }
}
