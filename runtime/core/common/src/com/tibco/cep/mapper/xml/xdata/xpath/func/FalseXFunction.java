package com.tibco.cep.mapper.xml.xdata.xpath.func;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.values.XsBoolean;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.flavor.XSDL;
import com.tibco.xml.schema.xtype.SMDT;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

/**
 */
public final class FalseXFunction extends DefaultLastArgRequiredXFunction
{
    public static final ExpandedName NAME = ExpandedName.makeName("false");

    private static final SmSequenceType BOOLEAN_FALSE = SmSequenceTypeFactory.createAtomic(XSDL.BOOLEAN,
                                                                                           XsBoolean.TRUE);

    public FalseXFunction()
    {
        super(NAME,SMDT.BOOLEAN);
    }

    public SmSequenceType typeCheck(SmSequenceType[] argsType, ExprContext context, EvalTypeInfo state, Expr functionCallExpr, Expr[] args)
    {
        return BOOLEAN_FALSE;
    }
}
