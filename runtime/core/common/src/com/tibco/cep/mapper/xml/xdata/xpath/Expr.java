package com.tibco.cep.mapper.xml.xdata.xpath;

import java.util.List;
import java.util.Map;

import com.tibco.cep.mapper.util.ResourceBundleManager;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.AbsoluteExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.AddExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.AncestorAxisExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.AncestorOrSelfAxisExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.AndExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.AttributeAxisExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.AxisExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.ChildAxisExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.CodeCompleteNameTestExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.CodeCompleteStepExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.CommentExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.CommentNodeTestExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.DescendantAxisExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.DescendantOrSelfAxisExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.DivideExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.EmptySequenceExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.EqualsExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.ErrorExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.FollowingAxisExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.FollowingSiblingAxisExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.ForExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.FunctionCallExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.GreaterThanEqualsExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.GreaterThanExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.IfExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.LessThanEqualsExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.LessThanExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.LiteralStringExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.LocationPathExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.MarkerExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.ModExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.MultiplyExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.NameTestExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.NamespaceAxisExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.NegativeExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.NodeNodeTestExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.NodeTestExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.NotEqualsExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.NumberExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.OrExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.PINodeTestExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.ParenExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.ParentAxisExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.PrecedingAxisExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.PrecedingSiblingAxisExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.PredicateExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.RangeExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.SelfAxisExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.SequenceExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.StepExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.SubtractExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.TextNodeTestExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.UnionExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.VariableReferenceExpr;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmSequenceType;
import com.tibco.xml.schema.helpers.NoNamespace;

/**
 * A description of an XPath expression.<br>
 * Provides type-analysis and introspection capabilities; not an executable representation.
 */
public abstract class Expr
{
    static
    {
        // Register messages (maybe not the best place to do this...)
        ResourceBundleManager.addResourceBundle("com.tibco.cep.mapper.xml.xdata.xpath.MessageCode", null);
//        ResourceBundleManager.addResourceBundle("XPath","com.tibco.xml.xdata.xpath.MessageCode");
    }
    private final TextRange mTextRange;
    protected static final Expr[] NO_CHILDREN = new Expr[0];
    private int mCachedHashCode; // 0 if not yet set.
    private final String mWhitespace;

    private static final TextRange EMPTY_RANGE = new TextRange(0,0);

    protected Expr(TextRange range, String whitespace) {
        if (range==null) {
            // fixme to be an assert maybe...
            range = EMPTY_RANGE;
        }
        mTextRange = range;
        if (whitespace==null) {
            mWhitespace = "";
        } else {
            mWhitespace = whitespace;
        }
    }

    /**
     * Returns the state given that this expression matches.<br>
     * There is a subtle difference between this and {@link #assertBooleanValue} -- that returns the
     * state given that expr.evalBoolean()=true, this returns the state given that node()=expr.eval().
     * @param ec The expression context (the state a-priori).
     * @return The state given this is true.
     */
    public ExprContext assertMatch(ExprContext ec)
    {
        // default behavior: leave state alone.
        return ec;
    }

    /**
     * Returns the state given that the boolean value of the expression evaluates to true.<br>
     * @param ec The expression context (the state a-priori).
     * @return The state given this is true.
     */
    public ExprContext assertBooleanValue(ExprContext ec, boolean isTrue)
    {
        // default behavior: leave state alone.
        return ec;
    }

    /**
     *
     * @param ec
     * @param type
     * @param optionalSpecifiedCardinality If null, the type will be replaced 1-for-1 (cardinality)
     * for whatever it replaces.  Otherwise, the replaced type will take the specified cardinality.
     * @return
     */
    public ExprContext assertEvaluatesTo(ExprContext ec, SmSequenceType type, SmCardinality optionalSpecifiedCardinality)
    {
        // default behavior: leave state alone.
        return ec;
    }

    public ExprContext assertTypedValueEvaluatesTo(ExprContext ec, SmSequenceType type)
    {
        // default behavior: leave state alone.
        return ec;
    }

    /**
     * Returns true if any part of this expression traverses off of the subtree passed in by the context.
     * So a child axis step returns 'false', but a parent axis step would return true.  Certain functions,
     * i.e. 'lang' also return true.
     */
    public boolean hasNonSubTreeTraversal() {
        // default just recurses:
        Expr[] c = getChildren();
        for (int i=0;i<c.length;i++) {
            if (c[i].hasNonSubTreeTraversal()) {
                return true;
            }
        }
        return false;
    }

    public boolean requiresNodeSorting() {
        // default just recurses:
        Expr[] c = getChildren();
        for (int i=0;i<c.length;i++) {
            if (c[i].requiresNodeSorting()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if any part of this expression has a 'search' axis, i.e. //, where it goes more than 1 level deep.
     */
    public boolean isMultiLevel() {
        // default just recurses:
        Expr[] c = getChildren();
        for (int i=0;i<c.length;i++) {
            if (c[i].isMultiLevel()) {
                return true;
            }
        }
        return false;
    }

    public final XPathTypeReport evalType(ExprContext context) {
        EvalTypeInfo info = new EvalTypeInfo();
        info.setRecordErrors(true);
        SmSequenceType t = evalType(context,info);
        return new XPathTypeReport(t,info.getErrors());
    }

    /**
     */
    public abstract SmSequenceType evalType(ExprContext context, EvalTypeInfo state);

    /**
     * Called by evalType.  Implementations call evalType() for children, this records
     * an entry in the exprToType map. Implementation of evalTypeImpl should just forward exprToType.
     */
    protected final SmSequenceType evalTypeImpl(ExprContext context, Map exprToType, List errors) {
        return null;
    }

    /**
     * Returns a hashCode corresponding to equals, the text range is <b>not</b>
     * considered as part code.
     * This is a 'deep' hash code.
     */
    public final int contentHashCode() {
        if (mCachedHashCode!=0) {
            return mCachedHashCode;
        }
        int hc = getExprTypeCode();
        Expr[] c = getChildren();
        for (int i=0;i<c.length;i++) {
            hc += c[i].contentHashCode();
        }
        String val = getExprValue();
        if (val!=null) {
            hc += val.hashCode();
        }
        if (hc==0) hc=1; // doesn't really matter, 0 means not cached.
        mCachedHashCode = hc;
        return hc;
    }

    public abstract Expr[] getChildren();

    /**
     * Identity hashCode, equivalent to System.identityHashCode(this)
     */
    public final int hashCode() {
        return super.hashCode();
    }

    /**
     * Identity comparison, equivalent to this==to.
     * @see #contentEquals(Expr)
     */
    public final boolean equals(Object to) {
        return this==to;
    }

    /**
     * Returns true if the expressions are deep equals.
     * The text range is <b>not</b> considered as part of the comparison.
     */
    public final boolean contentEquals(Expr to) {
        if (contentHashCode()!=to.contentHashCode()) {
            return false; // quick optimization
        }
        String val = getExprValue();
        String toval = to.getExprValue();
        if ((val==null) != (toval==null)) {
            return false;
        }
        if (val!=null) {
            if (!val.equals(toval)) {
                return false;
            }
        }
        Expr[] children = getChildren();
        Expr[] toChildren = to.getChildren();
        if (children.length!=toChildren.length) {
            return false;
        }
        for (int i=0;i<children.length;i++) {
            if (!children[i].contentEquals(toChildren[i])) {
                return false;
            }
        }
        return true;
    }

    public static Expr create(int exprTypeCode, Expr[] children, String value, String whitespace, String repClosure) {

       TextRange range = null;
       // If type code indicates an error, we should not attempt to create any
       // type of expression other than an ErrorExpr.  So, we'll check here and
       // return immediately if that's the case.
       if(exprTypeCode == ExprTypeCode.EXPR_ERROR) {
          return new ErrorExpr(range,repClosure,value,whitespace);
       }

        // Giant switch statement to create these based on type code
        // (better than exposing all ctrs)
        if (value!=null) {
            switch (exprTypeCode) {
                case ExprTypeCode.EXPR_FUNCTION_CALL: return new FunctionCallExpr(value,children,range,repClosure,whitespace);
                case ExprTypeCode.EXPR_VARIABLE_REFERENCE: {
                   return new VariableReferenceExpr(ExpandedName.makeName(NoNamespace.URI, value),
                                                    range,
                                                    repClosure,
                                                    whitespace);
                }
                case ExprTypeCode.EXPR_LITERAL_STRING: return new LiteralStringExpr(value,range,whitespace,repClosure!=null);
                case ExprTypeCode.EXPR_NUMBER: return new NumberExpr(value,range,whitespace);
                case ExprTypeCode.EXPR_PI_NODE_TEST: return new PINodeTestExpr(value,range,whitespace);
                case ExprTypeCode.EXPR_NAME_TEST: return new NameTestExpr(value,range,whitespace);
                case ExprTypeCode.EXPR_MARKER: return new MarkerExpr(value,range,whitespace);
                case ExprTypeCode.EXPR_CODE_COMPLETE_NAME_TEST: return new CodeCompleteNameTestExpr(value,range);

                // xpath 2.0
                case ExprTypeCode.EXPR_FOR: return new ForExpr(children,value,range,whitespace,repClosure);
            }
        }
        if (children.length==3) {
            if (exprTypeCode==ExprTypeCode.EXPR_IF) {
                return new IfExpr(children[0],children[1],children[2],range,repClosure);
            }
        }
        if (children.length==2) {
            Expr left = children[0];
            Expr right = children[1];
            switch (exprTypeCode) {
                case ExprTypeCode.EXPR_ADD: return new AddExpr(left,right,range,whitespace);
                case ExprTypeCode.EXPR_SUBTRACT: return new SubtractExpr(left,right,range,whitespace);
                case ExprTypeCode.EXPR_MULTIPLY: return new MultiplyExpr(left,right,range,whitespace);
                case ExprTypeCode.EXPR_DIVIDE: return new DivideExpr(left,right,range,whitespace);
                case ExprTypeCode.EXPR_MOD: return new ModExpr(left,right,range,whitespace);

                case ExprTypeCode.EXPR_EQUALS: return new EqualsExpr(left,right,range,whitespace);
                case ExprTypeCode.EXPR_NOT_EQUALS: return new NotEqualsExpr(left,right,range,whitespace);
                case ExprTypeCode.EXPR_GREATER_THAN: return new GreaterThanExpr(left,right,range,whitespace);
                case ExprTypeCode.EXPR_GREATER_THAN_EQUALS: return new GreaterThanEqualsExpr(left,right,range,whitespace);
                case ExprTypeCode.EXPR_LESS_THAN: return new LessThanExpr(left,right,range,whitespace);
                case ExprTypeCode.EXPR_LESS_THAN_EQUALS: return new LessThanEqualsExpr(left,right,range,whitespace);

                case ExprTypeCode.EXPR_UNION: return new UnionExpr(left,right,range,whitespace);
                case ExprTypeCode.EXPR_OR: return new OrExpr(left,right,range,whitespace);
                case ExprTypeCode.EXPR_AND: return new AndExpr(left,right,range,whitespace);

                case ExprTypeCode.EXPR_RANGE: return new RangeExpr(left, right, range, whitespace);
                  
                case ExprTypeCode.EXPR_STEP: return new StepExpr((AxisExpr)left,(NodeTestExpr)right,range,repClosure!=null,whitespace);
                case ExprTypeCode.EXPR_LOCATION_PATH: return new LocationPathExpr(left,right,range,whitespace);
                case ExprTypeCode.EXPR_PREDICATE: return new PredicateExpr(left,right,range,repClosure,whitespace);
                case ExprTypeCode.EXPR_SEQUENCE: return new SequenceExpr(left,right,range,whitespace);
            }
            throw new IllegalArgumentException("Expr type " + exprTypeCode + " does not accept 2 arguments");
        }
        if (children.length==1) {
            Expr arg = children[0];
            switch (exprTypeCode) {
                case ExprTypeCode.EXPR_NEGATIVE: return new NegativeExpr(arg,range,whitespace);
                case ExprTypeCode.EXPR_ABSOLUTE: return new AbsoluteExpr(arg,range,whitespace);
                case ExprTypeCode.EXPR_PAREN: return new ParenExpr(arg,range,repClosure,whitespace);
                case ExprTypeCode.EXPR_COMMENT: return new CommentExpr(value,arg,"left".equals(repClosure),range,whitespace);
            }
            throw new IllegalArgumentException("Expr type " + exprTypeCode + " does not accept 1 argument");
        }
        // zero
        switch (exprTypeCode) {
            case ExprTypeCode.EXPR_ANCESTOR_AXIS: return new AncestorAxisExpr(range, repClosure, whitespace);
            case ExprTypeCode.EXPR_ANCESTOR_OR_SELF_AXIS: return new AncestorOrSelfAxisExpr(range, repClosure, whitespace);
            case ExprTypeCode.EXPR_ATTRIBUTE_AXIS: return new AttributeAxisExpr(range, repClosure, whitespace);
            case ExprTypeCode.EXPR_CHILD_AXIS: return new ChildAxisExpr(range, repClosure, whitespace);
            case ExprTypeCode.EXPR_DESCENDANT_AXIS: return new DescendantAxisExpr(range, repClosure, whitespace);
            case ExprTypeCode.EXPR_DESCENDANT_OR_SELF_AXIS: return new DescendantOrSelfAxisExpr(range,repClosure,whitespace);
            case ExprTypeCode.EXPR_FOLLOWING_AXIS: return new FollowingAxisExpr(range,repClosure,whitespace);
            case ExprTypeCode.EXPR_FOLLOWING_SIBLING_AXIS: return new FollowingSiblingAxisExpr(range,repClosure,whitespace);
            case ExprTypeCode.EXPR_NAMESPACE_AXIS: return new NamespaceAxisExpr(range,repClosure,whitespace);
            case ExprTypeCode.EXPR_PARENT_AXIS: return new ParentAxisExpr(range,repClosure,whitespace);
            case ExprTypeCode.EXPR_PRECEDING_AXIS: return new PrecedingAxisExpr(range,repClosure,whitespace);
            case ExprTypeCode.EXPR_PRECEDING_SIBLING_AXIS: return new PrecedingSiblingAxisExpr(range,repClosure,whitespace);
            case ExprTypeCode.EXPR_SELF_AXIS: return new SelfAxisExpr(range,repClosure,whitespace);

            case ExprTypeCode.EXPR_COMMENT_NODE_TEST: return new CommentNodeTestExpr(range,whitespace);
            case ExprTypeCode.EXPR_PI_NODE_TEST: return new PINodeTestExpr(null,range,whitespace); // appears in another case for non-null
            case ExprTypeCode.EXPR_NODE_NODE_TEST: return new NodeNodeTestExpr(range,whitespace);
            case ExprTypeCode.EXPR_TEXT_NODE_TEST: return new TextNodeTestExpr(range,repClosure,whitespace);
            case ExprTypeCode.EXPR_ABSOLUTE: return new AbsoluteExpr(null,range,whitespace);
//            case ExprTypeCode.EXPR_ERROR: return new ErrorExpr(range,repClosure,value,whitespace);
            case ExprTypeCode.EXPR_CODE_COMPLETE: return new CodeCompleteStepExpr(value,range);
            case ExprTypeCode.EXPR_EMPTY_SEQUENCE: return new EmptySequenceExpr(range,whitespace);
        }
        throw new IllegalArgumentException("Expr type " + exprTypeCode + " does not accept 0 args");
    }

    public abstract int getExprTypeCode();

    public abstract String getExprValue();

    /**
     * Optionally returns an additional string which preserves any input text formatting (i.e. extra whitespace areas,
     * single vs. double quotes, etc.)
     */
    public String getRepresentationClosure() {
        return null;
    }

    /**
     * From the original unicode text input, the range corresponding to this Expr.
     * @return The TextRange.  It cannot be null, but the range may be 0,0 indicating not available.
     */
    public final TextRange getTextRange() {
        return mTextRange;
    }

    /**
     * From the original unicode text input, the whitespace for this expression (where it belongs depends on the type of expression)
     */
    public final String getWhitespace() {
        return mWhitespace;
    }

    /**
     * Reproduces the expression exactly as it was input (only leading & trailing whitespace is lost)
     */
    public final String toExactString() {
        StringBuffer sb = new StringBuffer();
        format(sb,STYLE_TO_EXACT_STRING);
        return sb.toString();
    }

	/**
	 * Prints the expression in a canonical abbreviated form.
	 */
	public final String toString() {
	    StringBuffer sb = new StringBuffer();
	    format(sb,STYLE_TO_STRING);
	    return sb.toString();
	}

	/**
	 * Prints the expression in a canonical abbreviated form w/out any prefixes.
	 */
	public final String toStringNoPrefix() {
	    StringBuffer sb = new StringBuffer();
	    format(sb,STYLE_TO_STRING_NO_PREFIX);
	    return sb.toString();
	}

    /**
     * Prints the expression in unabbreviated form.
     */
    public final String toUnabbreviatedString() {
        StringBuffer sb = new StringBuffer();
        format(sb,STYLE_TO_UNABBREVIATED_STRING);
        return sb.toString();
    }

    /**
     * Prints the expression with parentheses around steps, for diagnostic/debugging purposes.
     * Normal parens, i.e. (34) are printed as <lparen>34<rparen>
     */
    public final String toDebugString() {
        StringBuffer sb = new StringBuffer();
        format(sb,STYLE_TO_DEBUG_STRING);
        return sb.toString();
    }

    /**
     * Used by {@link #format(java.lang.StringBuffer, int)} to indicate the {@link #toString()} style.
     */
    public static final int STYLE_TO_STRING = 0;

    /**
     * Used by {@link #format(java.lang.StringBuffer, int)} to indicate the {@link #toDebugString()} style.
     */
    public static final int STYLE_TO_DEBUG_STRING = 1;

    /**
     * Used by {@link #format(java.lang.StringBuffer, int)} to indicate the {@link #toUnabbreviatedString()} style.
     */
    public static final int STYLE_TO_UNABBREVIATED_STRING = 2;

    /**
     * Used by {@link #format(java.lang.StringBuffer, int)} to indicate the {@link #toExactString()} style.
     */
    public static final int STYLE_TO_EXACT_STRING = 3;

	/**
	 * Used by {@link #format(java.lang.StringBuffer, int)} to indicate the {@link #toStringNoPrefix()} style.
	 */
	public static final int STYLE_TO_STRING_NO_PREFIX = 4;

    /**
     * Used by the {@link #toString(), #toExactString(), #toUnabbreviatedString(), #toDebugString()}.
     * Need to use StringBuffer here (rather than concat string) because for very long XPaths (>20 location steps),
     * the cost of formatting because prohibitive.
     * @param toBuffer The buffer to write to.
     * @param style One of {@link #STYLE_TO_STRING}, {@link #STYLE_TO_DEBUG_STRING}, {@link #STYLE_TO_EXACT_STRING},
     * or {@link #STYLE_TO_UNABBREVIATED_STRING}.
     */
    public abstract void format(StringBuffer toBuffer, int style);
}
