package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SMDT;

/**
 */
public final class Base64ToHexXFunction extends DefaultLastArgOptionalXFunction
{
    private static final ExpandedName NAME = TibExtFunctions.makeName("base64-to-hex");

    Base64ToHexXFunction()
    {
        super(NAME,SMDT.STRING,SMDT.STRING,SMDT.STRING);
    }

    public SmSequenceType typeCheck(SmSequenceType[] argsType, ExprContext context, EvalTypeInfo state, Expr functionCallExpr, Expr[] args)
    {
        return getReturnType();
    }
}
