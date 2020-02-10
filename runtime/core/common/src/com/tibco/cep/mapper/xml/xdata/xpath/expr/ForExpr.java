package com.tibco.cep.mapper.xml.xdata.xpath.expr;

import java.util.StringTokenizer;

import com.tibco.cep.mapper.xml.xdata.xpath.EvalTypeInfo;
import com.tibco.cep.mapper.xml.xdata.xpath.Expr;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprContext;
import com.tibco.cep.mapper.xml.xdata.xpath.ExprTypeCode;
import com.tibco.cep.mapper.xml.xdata.xpath.TextRange;
import com.tibco.cep.mapper.xml.xdata.xpath.VariableDefinition;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.helpers.NoNamespace;
import com.tibco.xml.schema.xtype.SmSequenceTypeFactory;
import com.tibco.xml.schema.xtype.helpers.SmSequenceTypeSupport;

/**
 */
public final class ForExpr extends Expr
{
    private final Clause[] mClauses;
    private final Expr mReturns;

    public static class Clause {
        public final String mVarQName;
        public final Expr mExpr;
        public final String mBeforeVarWhitespace;
        public final String mBeforeInWhitespace;
        public final String mAfterInWhitespace;

        public Clause(String varQName, Expr expr, String beforeVarWhitespace, String beforeInWhitepace, String afterInWhitespace) {
            mExpr = expr;
            mVarQName = varQName;
            mBeforeVarWhitespace = beforeVarWhitespace;
            mBeforeInWhitespace = beforeInWhitepace;
            mAfterInWhitespace = afterInWhitespace;
        }
    }

    public Clause getClause(int index) {
        return mClauses[index];
    }

    public ForExpr(Clause[] clauses, Expr returns, String afterReturnWS, TextRange range) {
        super(range,afterReturnWS); // whitespace is be a real pain here.
        mClauses = clauses;
        mReturns = returns;
    }

    /**
     * Constructor for use in cloning (kind of messy), humans should use the other constructor.
     */
    public ForExpr(Expr[] children, String value, TextRange range, String whitespace, String whitespaceClosure) {
        super(range,whitespace); // whitespace will be a pain.
        int nc = (children.length-1);
        StringTokenizer st = new StringTokenizer(value," ");
        StringTokenizer whitesp = new StringTokenizer(whitespaceClosure,",");

        Clause[] c = new Clause[nc];
        for (int i=0;i<c.length;i++) {
            String varName;
            if (!st.hasMoreTokens()) {
                varName = "__var" + i; // missing, shouldn't happen, just recover...
            } else {
                varName = st.nextToken();
            }
            String ws1 = " ";
            String ws2 = " ";
            String ws3 = " ";
            if (whitesp.hasMoreTokens()) {
                ws1 = whitesp.nextToken();
            }
            if (whitesp.hasMoreTokens()) {
                ws2 = whitesp.nextToken();
            }
            if (whitesp.hasMoreTokens()) {
                ws3 = whitesp.nextToken();
            }
            c[i] = new Clause(varName,children[i],ws1,ws2,ws3);
        }
        mClauses = c;
        mReturns = children[children.length-1];
    }

    public Expr[] getChildren() {
        int l = mClauses.length;
        Expr[] t = new Expr[l+1];
        for (int i=0;i<mClauses.length;i++) {
            t[i] = mClauses[i].mExpr;
        }
        t[mClauses.length] = mReturns;
        return t;
    }

    public String getExprValue() {
        // this is the concatination of all variable qnames separated by a space:
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<mClauses.length;i++) {
            if (i>0) sb.append(' ');
            sb.append(mClauses[i].mVarQName);
        }
        return sb.toString();
    }

    public int getExprTypeCode() {
        return ExprTypeCode.EXPR_FOR;
    }

    public SmSequenceType evalType(ExprContext context, EvalTypeInfo state) {
        ExprContext nc = context;
        for (int i=0;i<mClauses.length;i++) {
            Clause c = mClauses[i];
            SmSequenceType t = c.mExpr.evalType(context,state);
            SmSequenceType nt = t.prime();
            ExpandedName varName = ExpandedName.makeName(NoNamespace.URI, c.mVarQName);
            nc = nc.createWithNewVariable(new VariableDefinition(varName,nt));
        }
        SmSequenceType t = mReturns.evalType(nc,state);
        SmSequenceType rt = makeRepeatingXType(t);

        return state.recordReturnType(this,rt); // for now...
    }

    private static SmSequenceType makeRepeatingXType(SmSequenceType input)
    {
        if (SmSequenceTypeSupport.isPreviousError(input))
        {
            return input;
        }
        return SmSequenceTypeFactory.createRepeats(input.prime(),SmCardinality.REPEATING);
    }

    public Expr getReturnExpr() {
        return mReturns;
    }

    public String getRepresentationClosure() {
        // whitespace:
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<mClauses.length;i++) {
            if (i>0) {
                sb.append(',');
            }
            Clause c = mClauses[i];
            sb.append(c.mBeforeVarWhitespace);
            sb.append(',');
            sb.append(c.mBeforeInWhitespace);
            sb.append(',');
            sb.append(c.mAfterInWhitespace);
        }
        return sb.toString();
    }

    public void format(StringBuffer toBuffer, int style) {
        toBuffer.append("for");
        boolean isExact = style==STYLE_TO_EXACT_STRING;
        if (!isExact) {
            toBuffer.append(' ');
        }
        for (int i=0;i<mClauses.length;i++) {
            if (i>0) {
                toBuffer.append(',');
                if (!isExact) {
                    toBuffer.append(' ');
                }
            }
            Clause c = mClauses[i];
            if (isExact) {
                toBuffer.append(c.mBeforeVarWhitespace);
            }
            toBuffer.append('$');
            toBuffer.append(c.mVarQName);
            if (isExact) {
                toBuffer.append(c.mBeforeInWhitespace);
            } else {
                toBuffer.append(' ');
            }
            toBuffer.append("in");
            if (isExact) {
                toBuffer.append(c.mAfterInWhitespace);
            } else {
                toBuffer.append(' ');
            }
            c.mExpr.format(toBuffer,style);
        }
        if (isExact) {
            char beforeReturn = toBuffer.charAt(toBuffer.length()-1);
            if (!Character.isWhitespace(beforeReturn)) {
                toBuffer.append(' ');
            }
        } else {
            toBuffer.append(' ');
        }
        toBuffer.append("return");
        if (isExact) {
            toBuffer.append(getWhitespace());
        } else {
            toBuffer.append(' ');
        }
        mReturns.format(toBuffer,style);
    }
}
