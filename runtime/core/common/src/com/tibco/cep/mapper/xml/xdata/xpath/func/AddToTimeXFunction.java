/*******************************************************************************
 * Copyright 2002 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 * A tibco extension function to add time to a time.
 */
public final class AddToTimeXFunction implements XFunction
{
    public static final ExpandedName NAME = ExpandedName.makeName(TibExtFunctions.NAMESPACE,"add-to-time");

    /**
     * For FunctionList
     */
    AddToTimeXFunction()
    {
    }

    public ExpandedName getName()
    {
        return NAME;
    }

    public boolean getLastArgRepeats()
    {
        return false;
    }

    public int getMinimumNumArgs()
    {
        return 4;
    }

    public int getNumArgs()
    {
        return 4;
    }

    public SmSequenceType getArgType(int argNum, int totalArgs)
    {
        if (argNum==0)
        {
            return SMDT.STRING;
        }
        return SMDT.DOUBLE;
    }

    public SmSequenceType getReturnType()
    {
        return SMDT.STRING;
    }

    public boolean hasNonSubTreeTraversal() {
        return false;
    }

    public boolean isIndependentOfFocus(int numArgs) {
        return true;
    }

    public SmSequenceType typeCheck(SmSequenceType[] argsType, ExprContext context, EvalTypeInfo state, Expr functionCallExpr, Expr[] args)
    {
        return getReturnType();
    }
}
