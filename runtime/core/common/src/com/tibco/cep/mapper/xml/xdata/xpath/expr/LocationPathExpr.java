package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

public final class LocationPathExpr extends Expr
{
    private final Expr mStep1;
    private final Expr mStep2;

    public LocationPathExpr(Expr step1, Expr step2, TextRange range, String trailingWhitespace) {
        super(range, trailingWhitespace);
        mStep1 = step1;
        mStep2 = step2;
    }

    public boolean requiresNodeSorting() {
        if (super.requiresNodeSorting()) {
            return true;
        }
        if (requiresDupFiltering()) {
            return true;
        }
        return false;
    }

    private boolean requiresDupFiltering() {
        return  mStep2.hasNonSubTreeTraversal() || mStep1.isMultiLevel();
    }

    public String getExprValue() {
        return null;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo info)
    {
        SmSequenceType step1 = mStep1.evalType(context,info);
        SmSequenceType step1Prime = step1.prime();

        SmCardinality step1q = step1.quantifier();
        ExprContext nc = context.createWithInput(step1Prime);
        ExprContext nc2 = nc.createInsideLocationPath(true); // for code complete.
        SmSequenceType r = mStep2.evalType(nc2,info);
        if (SmSequenceTypeSupport.isPreviousError(r) || step1q.equals(SmCardinality.EXACTLY_ONE))
        {
            // Either an error or input is exactly one; don't need to add repeats:
            return info.recordReturnType(this,r);
        }
        else
        {
            SmSequenceType repeats = SmSequenceTypeFactory.createRepeats(r.getContext(),r,step1q).simplify(false);
            return info.recordReturnType(this,repeats);
        }
    }

    public ExprContext assertBooleanValue(ExprContext ec, boolean isTrue)
    {
        SmSequenceType t = mStep1.evalType(ec,new EvalTypeInfo());//WCETODO eval type info??
        ExprContext nc = ec.createWithInput(t);
        ExprContext nc2 = mStep2.assertBooleanValue(nc,isTrue);
        return mStep1.assertEvaluatesTo(nc2,nc2.getInput(), null);
    }

    public ExprContext assertMatch(ExprContext ec)
    {
        ExprContext s1c = mStep1.assertMatch(ec);
        SmSequenceType t = mStep1.evalType(s1c,new EvalTypeInfo());//WCETODO eval type info??
        ExprContext nc = ec.createWithInput(t);
        ExprContext nc2 = mStep2.assertMatch(nc);
        return mStep1.assertEvaluatesTo(s1c,nc2.getInput(), null);
    }

    public ExprContext assertEvaluatesTo(ExprContext ec, SmSequenceType type, SmCardinality optionalSpecifiedOccurrence)
    {
        SmSequenceType t = mStep1.evalType(ec,new EvalTypeInfo());
        // Strip the occurs here because assert (used in Coercions) is for the leaf type only, so we don't want
        // to propagate repeats down the line:
        ExprContext nc = ec.createWithInput(SmSequenceTypeSupport.stripOccursAndParens(t));
        ExprContext nc2 = mStep2.assertEvaluatesTo(nc,type, optionalSpecifiedOccurrence);
        SmSequenceType i = nc2.getInput();
        return mStep1.assertEvaluatesTo(ec,i, null); // only pass in occurrence to 'leaf' type (i.e. alpha/beta/gamma -> only affects gamma)
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_LOCATION_PATH;
    }

    public Expr[] getChildren() {
        return new Expr[] {mStep1,mStep2};
    }

    public void format(StringBuffer toBuffer, int style) {
        if (style==STYLE_TO_DEBUG_STRING) {
            toBuffer.append('(');
        }
        mStep1.format(toBuffer,style);
        if (style==STYLE_TO_DEBUG_STRING || style==STYLE_TO_UNABBREVIATED_STRING) {
            toBuffer.append('/');
        } else {
            if (!isSlashSlashAbbrev()) {
                toBuffer.append('/');
            }
        }
        if (style==STYLE_TO_EXACT_STRING) {
            toBuffer.append(getWhitespace());
        }
        mStep2.format(toBuffer,style);
        if (style==STYLE_TO_DEBUG_STRING) {
            toBuffer.append(')');
        }
    }

    private boolean isSlashSlashAbbrev() {
        if (isLeftSlashSlashAbbrev(mStep2)) {
            return true;
        }
        if (mStep1.getExprTypeCode()==ExprTypeCode.EXPR_LOCATION_PATH) {
            if (!isRightSlashSlashAbbrev(mStep1)) {
                return false;
            }
            Expr s2 = stripPredicate(mStep2);
            if (s2.getExprTypeCode()==ExprTypeCode.EXPR_STEP) {
                StepExpr se = (StepExpr) s2;
                return se.isAbbrevChild();
            }
        } else {
            if (isRightSlashSlashAbbrev(mStep1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the first non-predicate child (essentially removes predicates), i.e.
     * alpha[1][1] -> alpha
     * @param e The expression, not null.
     * @return The expression w/o predicate, never null.
     */
    private static Expr stripPredicate(Expr e)
    {
        if (e.getExprTypeCode()==ExprTypeCode.EXPR_PREDICATE)
        {
            return stripPredicate(e.getChildren()[0]);
        }
        return e;
    }

    private boolean isRightSlashSlashAbbrev(Expr e) {
        if (e.getExprTypeCode()==ExprTypeCode.EXPR_STEP) {
            boolean v = ((StepExpr)e).isSlashSlashAbbrev();
            return v;
        }
        if (e.getExprTypeCode()==ExprTypeCode.EXPR_LOCATION_PATH) {
            boolean v = isRightSlashSlashAbbrev(e.getChildren()[1]);
            return v;
        }
        return false;
    }

    private boolean isLeftSlashSlashAbbrev(Expr e) {
        if (e.getExprTypeCode()==ExprTypeCode.EXPR_STEP) {
            boolean v = ((StepExpr)e).isSlashSlashAbbrev();
            return v;
        }
        if (e.getExprTypeCode()==ExprTypeCode.EXPR_LOCATION_PATH) {
            boolean v = isLeftSlashSlashAbbrev(e.getChildren()[0]);
            return v;
        }
        if (e.getExprTypeCode()==ExprTypeCode.EXPR_PREDICATE) {
            boolean v = isLeftSlashSlashAbbrev(e.getChildren()[0]);
            return v;
        }
        return false;
    }
}
