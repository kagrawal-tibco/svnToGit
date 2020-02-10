package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;

/**
 */
public final class UnionExpr extends OperatorExpr
{
    public UnionExpr(Expr left, Expr right, TextRange range, String whitespace) {
        super(left,right,range, whitespace);
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_UNION;
    }

    public String getOperatorName() {
        return "|";
    }

    public SmSequenceType evalOpType(final SmSequenceType lhs, final SmSequenceType rhs, ExprContext context, EvalTypeInfo info) {
        SmSequenceType orhs = SmSequenceTypeFactory.createRepeats(rhs,SmCardinality.OPTIONAL); // not in the spec, but this makes it more accurate w.r.t. occurrence.
        SmSequenceType seq = SmSequenceTypeFactory.createSequence(lhs,orhs);
        SmSequenceType prime = seq.prime();
        SmSequenceType simplified = prime.mergeDuplicateChoices();
        return SmSequenceTypeFactory.createRepeats(simplified,seq.quantifier());
    }
}
