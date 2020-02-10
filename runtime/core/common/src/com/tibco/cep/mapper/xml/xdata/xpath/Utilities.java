package com.tibco.cep.mapper.xml.xdata.xpath;

import java.util.ArrayList;
import java.util.List;

import com.tibco.xml.data.primitive.NamespaceResolver;
import com.tibco.xml.schema.SmAttribute;
import com.tibco.xml.schema.SmCardinality;
import com.tibco.xml.schema.SmDataComponent;
import com.tibco.xml.schema.SmModelGroup;
import com.tibco.xml.schema.SmParticleTerm;
import com.tibco.xml.schema.SmSequenceType;

/**
 * XPath utilities (rename later)
 */
public final class Utilities
{
    private Utilities() {
    }

    /**
     * This is a literal replace, it does not deal with paths.
     * @param original
     * @param snippet
     * @param replaceWith
     * @return The new expression.
     */
    public static Expr replace(Expr original, Expr snippet, Expr replaceWith) {
        if (original.contentEquals(snippet)) {
            return replaceWith;
        }
        Expr[] c = original.getChildren();
        Expr[] nc = new Expr[c.length];
        for (int i=0;i<c.length;i++) {
            Expr r = replace(c[i],snippet,replaceWith);
            if (r!=c[i]) {
                if (nc==null) {
                    nc = new Expr[c.length];
                }
                nc[i] = r;
            }
        }
        if (nc==null) {
            return original;
        }
        for (int i=0;i<c.length;i++) {
            if (nc[i]==null) {
                nc[i] = c[i];
            }
        }
        return Expr.create(
                original.getExprTypeCode(),
                nc,
                original.getExprValue(),
                original.getWhitespace(),
                original.getRepresentationClosure()
        );
    }

    public static void getAllVariables(Expr expr, List varList) {
        if (expr==null) {
            return;
        }
        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_VARIABLE_REFERENCE) {
            String varName = expr.getExprValue();
            if (!varList.contains(varName)) {
                varList.add(varName);
            }
        }
        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_FOR)
        {
            String vn = expr.getExprValue();
            ArrayList temp = new ArrayList();
            getAllVariablesOnChildren(expr,temp);
            temp.remove(vn); // if it was there.
            for (int i=0;i<temp.size();i++)
            {
                String v = (String) temp.get(i);
                if (!varList.contains(v))
                {
                    varList.add(v);
                }
            }
            return;
        }
        getAllVariablesOnChildren(expr,varList);
    }

    private static void getAllVariablesOnChildren(Expr expr, List varList)
    {
        Expr[] children = expr.getChildren();
        for (int i=0;i<children.length;i++) {
            getAllVariables(children[i],varList);
        }
    }

    /**
     * Does a replace of a simple path string, but does not traverse inside of [] (which changes context)
     * @param original
     * @param snippet
     * @param replaceWith
     * @return
     */
    public static Expr replaceLeadingPath(Expr original, Expr snippet, Expr replaceWith) {
        if (original.contentEquals(snippet)) {
            return replaceWith;
        }
        if (original.getExprTypeCode()==ExprTypeCode.EXPR_PREDICATE) {
            return original;
        }
        Expr[] c = original.getChildren();
        Expr[] nc = new Expr[c.length];
        for (int i=0;i<c.length;i++) {
            Expr r = replace(c[i],snippet,replaceWith);
            if (r!=c[i]) {
                if (nc==null) {
                    nc = new Expr[c.length];
                }
                nc[i] = r;
            }
        }
        if (nc==null) {
            return original;
        }
        for (int i=0;i<c.length;i++) {
            if (nc[i]==null) {
                nc[i] = c[i];
            }
        }
        return Expr.create(
                original.getExprTypeCode(),
                nc,
                original.getExprValue(),
                original.getWhitespace(),
                original.getRepresentationClosure()
        );
    }

    public static String[] getAsArray(String xpath) {
        Expr e = new Parser(Lexer.lex(xpath)).getExpression();
        return getAsArray(e);
    }

    public static String fromArray(String[] path) {
        return fromArray(path,path.length);
    }

    /**
     * Converts an array of paths into a relative xpath expression.
     * Zero length is special cased to '.'
     */
    public static String fromArray(String[] path, int length) {
        StringBuffer sb = new StringBuffer();
        if (length==0) {
            return ".";
        }
        for (int i=0;i<length;i++) {
            if (i>0) {
                sb.append("/");
            }
            sb.append(path[i]);
        }
        return sb.toString();
    }

    public static final PathStep ROOT = new PathStep(null,"<root>");

    private static class PathStep {
        public static PathStep create(String[] steps) {
            if (steps==null || steps.length==0) {
                return ROOT;
            }
            PathStep prev = ROOT;
            for (int i=0;i<steps.length;i++) {
                prev = new PathStep(prev,steps[i]);
            }
            return prev;
        }

        public PathStep(PathStep prev, String step) {
            this.prev = prev;
            this.step = step;
        }

        public int depth() {
            if (prev!=null) {
                return prev.depth()+1;
            }
            if (this==ROOT) {
                return 0;
            }
            throw new RuntimeException("Illegal PathStep");
        }

        public String[] toArray() {
            String[] ret = new String[depth()];
            PathStep step = this;
            for (int i=0;i<ret.length;i++) {
                ret[ret.length-(i+1)] = step.step;
                step = step.prev;
            }
            return ret;
        }

        public String toString() {
            return Utilities.fromArray(toArray());
        }

        public final PathStep prev;
        public final String step;
    }

    public static String[][] getPaths(String[] leadingPath, Expr expr) {
        PathStep leading = PathStep.create(leadingPath);
        ArrayList additional = new ArrayList();
        PathStep[] step = getPathSteps(expr,leading,additional);
        String[][] ss = new String[step.length+additional.size()][];
        for (int i=0;i<step.length;i++) {
            ss[i] = step[i].toArray();
        }
        for (int i=0;i<additional.size();i++) {
            ss[i+step.length] = ((PathStep)additional.get(i)).toArray();
        }
        return ss;
    }

    private static final PathStep[] NO_STEPS = new PathStep[0];

    /**
     * Additional paths needed to handle a/b[x='true]/c (predicate stuff)
     */
    private static PathStep[] getPathSteps(Expr expr, PathStep previous, ArrayList additionalPaths) {
        int tc = expr.getExprTypeCode();
        switch (tc) {
            case ExprTypeCode.EXPR_ABSOLUTE: {
                Expr[] children = expr.getChildren();
                if (children.length==1) {
                    return getPathSteps(expr.getChildren()[0],ROOT,additionalPaths);
                } else {
                    return new PathStep[] {ROOT};
                }
            }
            case ExprTypeCode.EXPR_LOCATION_PATH: {
                Expr[] children = expr.getChildren();
                PathStep[] p = getPathSteps(children[0],previous,additionalPaths);
                ArrayList ret = new ArrayList();
                for (int i=0;i<p.length;i++) {
                    PathStep[] m = getPathSteps(children[1],p[i],additionalPaths);
                    for (int j=0;j<m.length;j++) {
                        ret.add(m[j]);
                    }
                }
                return (PathStep[]) ret.toArray(new PathStep[ret.size()]);
            }
            case ExprTypeCode.EXPR_VARIABLE_REFERENCE: {
                return new PathStep[] {new PathStep(ROOT,"$" + expr.getExprValue())};
            }
            case ExprTypeCode.EXPR_STEP: {
                Expr[] children = expr.getChildren();
                PathStep s = getExprStepPathSteps(children[0],children[1],previous);
                if (s==null) {
                    return NO_STEPS;
                }
                return new PathStep[] {s};
            }
            case ExprTypeCode.EXPR_PREDICATE: {
                Expr[] children = expr.getChildren();
                PathStep[] p = getPathSteps(children[0],previous,additionalPaths);
                for (int i=0;i<p.length;i++) {
                    PathStep[] m = getPathSteps(children[1],p[i],additionalPaths);
                    for (int j=0;j<m.length;j++) {
                        additionalPaths.add(m[j]);
                    }
                }
                // Tricky --- we added additional paths above (because those are the end of the line)
                // but we could be inside of a larger step which may continue, i.e.
                // a/b[c=d]/x ... this will return a/b, but have added a/b/c and a/b/d to additional paths.
                return p;
            }
            case ExprTypeCode.EXPR_FUNCTION_CALL: {
                String fn = expr.getExprValue();
                if (isPseudoVariableFunction(fn))
                {
                    return new PathStep[] {new PathStep(ROOT,expr.getExprValue()+"()")};
                }
            }
        }
        // default:
        Expr[] children = expr.getChildren();
        ArrayList steps = null;
        for (int i=0;i<children.length;i++) {
            PathStep[] st = getPathSteps(children[i],previous,additionalPaths);
            for (int ai=0;ai<st.length;ai++) {
                if (steps==null) {
                    steps = new ArrayList();
                }
                steps.add(st[ai]);
            }
        }
        if (steps==null) {
            return NO_STEPS;
        }
        return (PathStep[]) steps.toArray(new PathStep[steps.size()]);
    }

    /**
     * Indicates if this is a pseudo-variable function, such as current-group(), where it is more of a path than
     * a function.
     * @param fnName The function name.
     */
    private static boolean isPseudoVariableFunction(String fnName)
    {
        if ("current-group".equals(fnName))
        {
            return true;
        }
        return false;
    }

    private static PathStep getExprStepPathSteps(Expr axis, Expr test, PathStep previous) {
        boolean isAttr = false;
        int aTypeCode = axis.getExprTypeCode();
        if (aTypeCode==ExprTypeCode.EXPR_CHILD_AXIS) {
        } else {
            if (aTypeCode==ExprTypeCode.EXPR_ATTRIBUTE_AXIS) {
                isAttr = true;
            } else {
                if (aTypeCode==ExprTypeCode.EXPR_PARENT_AXIS) {
                    return previous.prev;
                }
                if (aTypeCode==ExprTypeCode.EXPR_SELF_AXIS) {
                    return previous;
                }
                // don't understand.
                return null;
            }
        }
        int eTypeCode = test.getExprTypeCode();
        if (eTypeCode==ExprTypeCode.EXPR_NAME_TEST) {
            return new PathStep(previous,isAttr ? "@" + test.getExprValue() : test.getExprValue());
        }
        if (eTypeCode==ExprTypeCode.EXPR_TEXT_NODE_TEST) {
            return new PathStep(previous,"text()");
        }
        if (eTypeCode==ExprTypeCode.EXPR_NODE_NODE_TEST) {
            return new PathStep(previous,"node()");
        }
        if (eTypeCode==ExprTypeCode.EXPR_COMMENT_NODE_TEST) {
            return new PathStep(previous,"comment()");
        }
        if (eTypeCode==ExprTypeCode.EXPR_PI_NODE_TEST) {
            return new PathStep(previous,"processing-instruction()");
        }
        return null;
    }

    /**
     * Makes an absolute path out of a relative one; understands '..' in relative.
     * Returns null if you dropped off the face of the planet (off parent).
     */
    public static String[] makeAbsolute(String[] leadingPath, String[] relative) {
        if (leadingPath==null) {
            return relative;
        }
        ArrayList ret = new ArrayList(leadingPath.length+relative.length);
        for (int i=0;i<leadingPath.length;i++) {
            ret.add(leadingPath[i]);
        }
        for (int i=0;i<relative.length;i++) {
            String r = relative[i];
            if (r.equals("..")) {
                if (ret.size()==0) {
                    return null;//new String[0];
                }
                ret.remove(ret.size()-1);
            } else {
                ret.add(r);
            }
        }
        return (String[]) ret.toArray(new String[ret.size()]);
    }


    /**
     * For a pure path expression (i.e. a/b/@c), returns an array of path steps, otherwise null.
     * The path '.' returns a zero length array.
     */
    public static String[] getAsArray(Expr expr) {
        return getAsArray(expr,null);
    }

    /**
     * Rename, move -- does not deal with '..' or '.'; really only deals with child/attribute axis, just breaks into a fragment:
     * Does not deal with functions, operators, etc.
     * @param expr
     * @return
     */
    public static String[] getAsArrayPreservePredicates(Expr expr) {
        ArrayList temp = new ArrayList();
        getAsArrayPreservePredicates(expr,temp);
        return (String[]) temp.toArray(new String[0]);
    }

    private static void getAsArrayPreservePredicates(Expr expr, ArrayList ret) {
        int tc = expr.getExprTypeCode();
        switch (tc) {
            case ExprTypeCode.EXPR_ABSOLUTE: {
                Expr[] children = expr.getChildren();
                if (children.length==1) {
                    getAsArrayPreservePredicates(expr.getChildren()[0],ret);
                }
                return;
            }
            case ExprTypeCode.EXPR_LOCATION_PATH: {
                Expr[] children = expr.getChildren();
                getAsArrayPreservePredicates(children[0],ret);
                getAsArrayPreservePredicates(children[1],ret);
                return;
            }
            case ExprTypeCode.EXPR_VARIABLE_REFERENCE: {
                ret.add("$" + expr.getExprValue());
                return;
            }
            case ExprTypeCode.EXPR_STEP: {
                Expr[] children = expr.getChildren();
                boolean isAttr = children[0].getExprTypeCode()==ExprTypeCode.EXPR_ATTRIBUTE_AXIS;
                ret.add((isAttr ? "@" : "") +children[1].getExprValue());
                return;
            }
            case ExprTypeCode.EXPR_PREDICATE: {
                Expr[] children = expr.getChildren();
                String[] str = getAsArrayPreservePredicates(children[0]);
                for (int i=0;i<str.length-1;i++) {
                    ret.add(str[i]);
                }
                ret.add(str[str.length-1] + "[" + children[1].toExactString() + "]");
                return;
            }
        }
    }

    public static String[] getAsArray(Expr expr, String[] leadingPath) {
        String[][] str = getPaths(leadingPath,expr);
        if (str.length==0) {
            return null;
        }
        return str[0];
    }

/*    public static String makeAbsoluteXPath(String[] context, String xpath) {
        Expr beforee = new Parser(Lexer.lexCodeComplete(leadingXPath)).getExpression();
        Expr expr = makeAbsoluteXPath(context,xpath);
        if (expr==beforee) {
            return xpath; // no change.
        }
        return beforee.toString();
    }*/

    /**
     * This is a utility function used for computing the 'context-sensitive' xpath
     * out of existing xpath & context.  Mostly used for when dropping a path text
     * somewhere inside of an xpath string.
     */
    public static String makeAbsoluteXPath(SmSequenceType context, String xPath, NamespaceResolver namespaceResolver) {
        /*// In the case where there is no context (as marked by EMPTY_ELEMENT), do nothing.
        if (context.getParticleTerm()==com.tibco.xml.xdata.UtilitySchema.EMPTY_ELEMENT) {
            return xPath;
        }*/
        // add code complete marker:
        SmSequenceType contextType = context;

        String[] patha = contextType.getNodePath(namespaceResolver);
        String path = absolutizePathPart(patha,xPath);
        return path;
    }

    /**
     * Indicates if the xpath is relative (i.e. inside the context of) the current context.
     * @param exprContext The context.
     * @param xpath The xpath string.
     */
    public static boolean isRelativeXPath(ExprContext exprContext, String xpath)
    {
        if (xpath==null)
        {
            throw new NullPointerException("Null xpath");
        }
        String[] patha = exprContext.getInput().getNodePath(exprContext.getNamespaceMapper());
        if (patha==null)
        {
            return true;
        }
        String path = fromArray(patha);
        return xpath.startsWith(path);
    }

    /**
     * This is a utility function used for computing the 'context-sensitive' xpath
     * out of existing xpath & context.  Does <b>NOT</b> put in any filters.
     */
    public static String makeRelativeXPath(ExprContext exprContext, String leadingXPath, String insertXPath, SmSequenceType optionalTargetType)
    {
        if (insertXPath==null)
        {
            throw new NullPointerException("Null xpath");
        }
        // add code complete marker:
        Token[] t = Lexer.lexCodeComplete(leadingXPath);
        Expr beforee = new Parser(t).getExpression();
        //Expr inserte = new Parser(Lexer.lex(insertXPath)).getExpression();
        if (beforee==null) {
            return insertXPath; // whatever.
        }
        EvalTypeInfo info = new EvalTypeInfo();
        beforee.evalType(exprContext,info);
        CodeCompleteData ccd = info.getCodeComplete();
        if (ccd==null)
        { // probably a bug, but let it go here & return it:
            return insertXPath;
        }
        SmSequenceType contextType = ccd.getContextType();

        String[] patha = contextType.getNodePath(exprContext.getNamespaceMapper());
        String path = relativizePathPart(patha,insertXPath);
//        System.out.println(Utilities.fromArray(patha) + " and " + insertXPath + " and " + path);
        if (leadingXPath!=null && leadingXPath.length()>0)
        {
            optionalTargetType = ccd.getExpectedReturnType();
        }

        if (optionalTargetType==null)
        {
            return path;
        }
        return path;
    }

    /**
     * Inserts marker filters in xpaths to indicate that the left-hand-side repetition needs to be cut-down.
     * @param xpath The xpath expression.
     * @return
     */
    public static String insertFilters(ExprContext exprContext, String xpath)
    {
        return insertFilters(exprContext,Parser.parse(xpath),null);
    }

    /**
     * Inserts marker filters in xpaths to indicate that the left-hand-side repetition needs to be cut-down.
     * @param xpath The xpath expression.
     * @param filterMarkerStr The string to use as a marker, excluding the '<<' and '>>'.  If null, will use a default.
     * @return
     */
    public static String insertFilters(ExprContext exprContext, String xpath, String filterMarkerStr)
    {
        return insertFilters(exprContext,Parser.parse(xpath),filterMarkerStr);
    }

    private static String insertFilters(ExprContext exprContext, Expr expr, String marker)
    {
        int maxFilters = 10; // just to keep it real, any bugs shouldn't lock up the system...
        for (int i=0;i<maxFilters;i++) { // keep adding filters until we're done:
            String r1 = insertFiltersInternal(exprContext, expr, marker);
            if (r1.equals(expr.toExactString())) {
                return r1;
            }
            expr = new Parser(Lexer.lex(r1)).getExpression();
        }
        return expr.toExactString();
    }

    private static String insertFiltersInternal(ExprContext exprContext, Expr expr, String marker)
    {
        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_LOCATION_PATH) {
            String leading = insertFiltersInternal(exprContext,expr.getChildren()[0],marker);
            if (!leading.equals(expr.getChildren()[0].toString()))
            {
                return leading + "/" + expr.getChildren()[1];
            }
        }
        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_PREDICATE) {
            return expr.toString();
        }
        SmSequenceType singularContextType = exprContext.getInput().prime();
        ExprContext singularizedContext = exprContext.createWithInput(singularContextType);
        EvalTypeInfo info = new EvalTypeInfo();
        SmSequenceType xtype = expr.evalType(singularizedContext,info);
        SmCardinality xtypec = xtype.quantifier();
        if (xtypec.getMaxOccurs()>1 && xtypec.isKnown() && !expr.toString().endsWith("..")) {
            String leading = expr.toString();
            if (!leading.endsWith(">>]")) // hacky but effective test.
            {
                if (marker==null)
                {
                    marker = "filter"; // default value, for internationalized, never pass in null.
                }
                return expr.toString() + "[<< " + marker + " >>]";
            }
        }
        return expr.toString();
    }

    /**
     * Returns a new xpath where all absolute paths (matching the context path) have been replaced by relative paths.
     * @param contextPath The context path, broken into steps.
     * @param xpath The XPath.
     * @return The new xpath.
     */
    private static String relativizePathPart(String[] contextPath, String xpath)
    {
        return relativizePathPart(contextPath,Parser.parse(xpath)).toExactString();
    }

    private static Expr relativizePathPart(String[] contextPath, Expr e)
    {
        int tc = e.getExprTypeCode();
        if (tc==ExprTypeCode.EXPR_STEP || tc==ExprTypeCode.EXPR_LOCATION_PATH || tc==ExprTypeCode.EXPR_VARIABLE_REFERENCE || tc==ExprTypeCode.EXPR_PREDICATE || tc==ExprTypeCode.EXPR_ABSOLUTE)
        {
            return Parser.parse(relativizePathLocPart(contextPath,e.toExactString()));
        }
        if (e.getExprTypeCode()==ExprTypeCode.EXPR_FUNCTION_CALL && isPseudoVariableFunction(e.getExprValue()))
        {
            return e;
        }
        Expr[] children = e.getChildren();
        Expr[] nc = e.getChildren();
        for (int i=0;i<nc.length;i++)
        {
            nc[i] = relativizePathPart(contextPath,children[i]);
        }
        return Expr.create(tc,nc,e.getExprValue(),e.getWhitespace(),e.getRepresentationClosure());
    }

    /**
     * Internally used by {@link #relativizePathPart}, this works on a location path (or step, etc.) only, and
     * does not deal with functions, =, etc.<br>
     * This method is called by {@link #relativizePathPart} to handle location paths only; {@link #relativizePathPart}
     * deals with functions, = , etc.
     */
    private static String relativizePathLocPart(String[] contextPath, String insertXPath)
    {
        if (insertXPath==null)
        {
            throw new NullPointerException("Null insert xpath");
        }
        if (contextPath==null) {
            // not relative.
            Token[] t = Lexer.lex(insertXPath);
            if (t.length>0 && t[0].getType()==Token.TYPE_VARIABLE_REFERENCE) {
                // it starts with a variable, so absolute doesn't make sense.
                return insertXPath;
            }
            return "/" + insertXPath;
        }
        if (contextPath.length==0) {
            // the context == the root, just return it:
            return insertXPath;
        }
        Token[] t = Lexer.lex(insertXPath);
        if (t.length==0) {
            return insertXPath;
        }
        int tt = t[0].getType();
        if (tt!=Token.TYPE_NAME_TEST && tt!=Token.TYPE_SLASH && tt!=Token.TYPE_VARIABLE_REFERENCE && tt!=Token.TYPE_FUNCTION_NAME) {
            return insertXPath;
        }
        for (int trx=contextPath.length;trx>0;trx--) {
            // make into a pure contextPath:
            int numDotDots = contextPath.length-trx;
            StringBuffer sb = new StringBuffer();
            if (contextPath.length==0 || (!contextPath[0].startsWith("$") && !contextPath[0].endsWith("()"))) {
                sb.append("/");
            }
            for (int i=0;i<trx;i++) {
                sb.append(contextPath[i]);
                sb.append("/");
            }
            String basePath = sb.toString();
            if (insertXPath.startsWith(basePath)) {
                return getDotDots(numDotDots) + insertXPath.substring(basePath.length());
            }
            if (insertXPath.equals(basePath.substring(0,basePath.length()-1))) {
                if (numDotDots>0) {
                    String dds = getDotDots(numDotDots);
                    return dds.substring(0,dds.length()-1); // remove last '/'
                }
                return ".";
            }
        }
        // not inside the context, just return it:

        return insertXPath;
    }

    /**
     * Returns a new xpath where all relative paths have been replaced by absolute paths (where the context path is
     * used to determine absolute).
     * @param contextPath The context contextPath, broken into steps.
     * @param xpath The XPath.
     * @return The new xpath.
     */
    private static String absolutizePathPart(String[] contextPath, String xpath) {
        if (contextPath==null) {
            return xpath;
        }
        if (contextPath.length==0) {
            // the context == the root, just return it:
            return xpath;
        }
        Token[] t = Lexer.lex(xpath);
        if (t.length==0) {
            return xpath;
        }
        Parser p = new Parser(t);
        Expr e = p.getExpression();
        Expr e2 = absolutizePathPart(contextPath,e);
        return e2.toExactString();
    }

    private static Expr absolutizePathPart(String[] path, Expr e)
    {
        int tc = e.getExprTypeCode();
        if (tc==ExprTypeCode.EXPR_LOCATION_PATH || tc==ExprTypeCode.EXPR_STEP || tc==ExprTypeCode.EXPR_PREDICATE)
        {
            return absolutizeExprLocPart(path,e.toExactString());
        }
        if (tc==ExprTypeCode.EXPR_VARIABLE_REFERENCE)
        {
            return e; // no change.
        }
        Expr[] ch = e.getChildren();
        Expr[] nc = new Expr[ch.length];
        for (int i=0;i<ch.length;i++)
        {
            nc[i] = absolutizePathPart(path,ch[i]);
        }
        return Expr.create(tc,nc,e.getExprValue(),e.getWhitespace(),e.getRepresentationClosure());
    }

    /**
     * Used by {@link #absolutizeExprLocPart} to handle location and step paths, not functions, =, etc., which
     * are handled by {@link #absolutizePathPart}.
     */ 
    private static Expr absolutizeExprLocPart(String[] path, String insertXPath)
    {
        Token[] t = Lexer.lex(insertXPath);
        if (t.length==0)
        {
            return Parser.parse(insertXPath);
        }
        int tt = t[0].getType();
        if (tt==Token.TYPE_VARIABLE_REFERENCE) {
            // already absolute.
            return Parser.parse(insertXPath);
        }
        if (tt!=Token.TYPE_NAME_TEST && tt!=Token.TYPE_SLASH && tt!=Token.TYPE_VARIABLE_REFERENCE && tt!=Token.TYPE_PARENT && tt!=Token.TYPE_ATTRIBUTE && tt!=Token.TYPE_SELF) {
            return Parser.parse(insertXPath);
        }
        StringBuffer sb = new StringBuffer();
        int dotdots = countDotDots(insertXPath);
        for (int i=0;i<path.length-dotdots;i++) {
            String s = path[i];
            if (i==0 && !s.startsWith("$")) {
                sb.append("/");
            }
            if (i>0) {
                sb.append("/");
            }
            sb.append(s);
        }
        String prefix = sb.toString();
        String dotdotless = insertXPath.substring(dotdots*3);
        String ddret;
        if (dotdotless.startsWith("."))
        {
            ddret = prefix + dotdotless.substring(1);
        }
        else
        {
            ddret = prefix + "/" + dotdotless;
        }
        return Parser.parse(ddret);
    }

    private static String getDotDots(int count) {
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<count;i++) {
            sb.append("../");
        }
        return sb.toString();
    }

    private static int countDotDots(String str) {
        int ct = 0;
        for (;;) {
            if (str.startsWith("../")) {
                ct++;
                str = str.substring(3);
            } else {
                break;
            }
        }
        return ct;
    }

    public static String getXPath(SmParticleTerm[] termPath) {
        if (termPath.length==0) {
            return ".";
        }
        StringBuffer sb = new StringBuffer();
        boolean addedStep = false;
        for (int i=0;i<termPath.length;i++) {
            if (addedStep) {
                sb.append("/");
            }
            SmParticleTerm step = termPath[i];
            if (step instanceof SmModelGroup) {
                // anonymous.
                addedStep = false;
                if (i==termPath.length-1) {
                    sb.append("*");// not always correct (but most of the time it is)
                    // this was more correct, but not updated to SmType yet: getIntroducedXPath(step,))
                }
            } else {
                String name;
                if (step instanceof SmDataComponent) {
                    if (step instanceof SmAttribute) {
                        String stepName = step.getName();
                        if (stepName.equals("xml:lang")) { // special case, not handled right by SmStuff
                            return "@lang";
                        }
                        name = "@" + stepName;
                    } else {
                        if (step==com.tibco.cep.mapper.xml.xdata.UtilitySchema.CONTENT_ELEMENT) {
                            name = "text()";
                        } else {
                            name = step.getName();
                        }
                    }
                } else {
                    // wildcard.
                    name = "*";
                }
                sb.append(name);
                addedStep = true;
            }
        }
        return sb.toString();
    }

    public static TextRange getInsertedRange(String xpath, String insertXPath, TextRange range) {
        int start = Math.min(xpath.length(),range.getStartPosition());
        return new TextRange(start,start+insertXPath.length());
    }

    public static String computeActualDropString(ExprContext context, String existingXpath, String insertXPath, SmSequenceType targetType, TextRange range) {
        if (insertXPath==null) { //?
            return "";
        }
        Token[] t = Lexer.lex(insertXPath);
        for (int i=0;i<t.length;i++) {
            // If it contains a marker string, drop it in unmodified.
            if (t[i].getType()==Token.TYPE_MARKER_STRING) {
                return insertXPath;
            }
        }
        int start = range.getStartPosition();
        String before = existingXpath.substring(0,Math.min(existingXpath.length(),start));
        SmSequenceType icontext = context.getInput();
        if (icontext==null) {
            return existingXpath;
        }
        return Utilities.makeRelativeXPath(context,before,insertXPath,targetType);
    }

    /**
     * Really just a string utility.
     */
    public static String insertAt(String xpath, String insertXPath, TextRange range) {
        int start = range.getStartPosition();
        if (start>xpath.length()) {
            return xpath + insertXPath;
        }
        String before = xpath.substring(0,start);
        int end = Math.min(xpath.length(),range.getEndPosition());
        String after = xpath.substring(end,xpath.length());
        return before + insertXPath + after;
    }

    public static class DropTextInfo {
        public TextRange range;
        public String prefix; // additional stuff to add before drop
        public String suffix; // additional stuff to add after drop

        public String toString() {
            return prefix + "[" + range + "]" + suffix;
        }
    }

    /**
     * For xpath drag and drop, gets the 'best' range where an insert should take place.
     */
    public static DropTextInfo getDropTargetRange(int cpos, String text, int selStart, int selEnd) {
        return getDropTargetRange(cpos,text,selStart,selEnd,false);
    }

    public static DropTextInfo getDropTargetRange(int cpos, String text, int selStart, int selEnd, boolean onlyConsiderMarkers) {
        CharStream charStream = new CharStream(text);
        DropTextInfo info = new DropTextInfo();
        info.prefix = "";
        info.suffix = "";
        Token[] tokens = Lexer.lex(charStream);

        // First look for insert markers.
        for (int i=0;i<tokens.length;i++) {
            Token t = tokens[i];
            if (t.getType()==Token.TYPE_MARKER_STRING) {
                TextRange candidate = t.getTextRange();
                if (candidate.containsPosition(cpos)) {
                    info.range = candidate;
                    return info;
                }
            }
        }
        // check selection:
        if (selStart>selEnd) {
            int t = selEnd;
            selEnd = selStart;
            selStart = t;
        }
        if (selStart!=selEnd && selStart>=0) {
            TextRange selRange = new TextRange(selStart,selEnd);
            if (selRange.containsPosition(cpos)) {
                info.range = selRange;
                return info;
            }
        }
        if (onlyConsiderMarkers)
        {
            // ok, give up.
            return null;
        }
        // Next, look for a decent token or a path:
        for (int i=0;i<tokens.length;i++) {
            Token t = tokens[i];
            TextRange candidate = t.getTextRange();
            boolean in = candidate.containsPosition(cpos);
            if (i<tokens.length-1) {
                // consider whitespace between tokens, too:
                if (tokens[i+1].getTextRange().getStartPosition()>cpos) {
                    if (candidate.getEndPosition()<cpos) {
                        in = true;
                    }
                }
            }
            if (in) {
                // expand to include all paths:
                Token first = tokens[i];
                for (int j=i;j>=0;j--) {
                    Token t2 = tokens[j];
                    int tt = t2.getType();
                    if (j<i && t2.getTextRange().getEndPosition()!=tokens[j+1].getTextRange().getStartPosition()) {
                        break;
                    }
                    if (tt==Token.TYPE_SLASH || tt==Token.TYPE_NAME_TEST || tt==Token.TYPE_ATTRIBUTE || tt==Token.TYPE_PARENT || tt==Token.TYPE_VARIABLE_REFERENCE) {
                        first = t2;
                        continue;
                    }
                    if (tt==Token.TYPE_VARIABLE_REFERENCE) {
                        if (j==i) { // only go through 1 variable for the first thing...
                            first = t2;
                            continue;
                        }
                    }
                    break;
                }
                Token last = tokens[i];
                for (int j=i;j<tokens.length;j++) {
                    Token t2 = tokens[j];
                    int tt = t2.getType();
                    if (j>i && t2.getTextRange().getStartPosition()!=tokens[j-1].getTextRange().getEndPosition()) {
                        break;
                    }
                    if (tt==Token.TYPE_SLASH || tt==Token.TYPE_NAME_TEST || tt==Token.TYPE_ATTRIBUTE || tt==Token.TYPE_PARENT || tt==Token.TYPE_VARIABLE_REFERENCE) {
                        last = t2;
                        continue;
                    }
                    break;
                }
                if (first==last) {
                    int tt = first.getType();
                    if (tt==Token.TYPE_COMMA) {
                        // add after the comma:
                        int ep = first.getTextRange().getEndPosition();
                        info.suffix = ",";
                        info.range = new TextRange(ep,ep);
                        return info;
                    }
                    if (tt==Token.TYPE_LPAREN) {
                        // add after the lparen:
                        int ep = first.getTextRange().getEndPosition();
                        info.range = new TextRange(ep,ep);
                        info.suffix = ",";
                        return info;
                    }
                    if (tt==Token.TYPE_RPAREN) {
                        // add before the rparen:
                        int sp = first.getTextRange().getStartPosition();
                        info.range = new TextRange(sp,sp); // +1 -> For leading prefix comma.
                        info.prefix = ",";
                        return info;
                    }
                }
                info.range = new TextRange(first.getTextRange(),last.getTextRange());
                return info;
            }
        }
        if (cpos<=0) {
            // ok, the beginning is good:
            info.range = new TextRange(0,0);
            return info;
        }
        if (cpos<text.length()) {
            // somewhere in the whitespace middle, perhaps
            info.range = new TextRange(cpos,cpos);
            return info;
        }
        // ... ok, just add to the end:
        info.range = new TextRange(text.length(),text.length());
        return info;
    }

    /**
     * For xpath drag and drop, gets the 'best' range where a surround drop should take place.
     * @return The best range, or null, if none is appropriate.
     */
    public static DropTextInfo getSurroundDropTargetRange(int cpos, String text, int selStart, int selEnd) {
        CharStream charStream = new CharStream(text);
        DropTextInfo info = new DropTextInfo();
        info.prefix = "";
        info.suffix = "";
        Token[] tokens = Lexer.lex(charStream);

        // First look for insert markers.
        for (int i=0;i<tokens.length;i++) {
            Token t = tokens[i];
            if (t.getType()==Token.TYPE_MARKER_STRING) {
                TextRange candidate = t.getTextRange();
                if (candidate.containsPosition(cpos)) {
                    // don't drop on markers.
                    return null;
                }
            }
        }
        // check selection:
        if (selStart>selEnd) {
            int t = selEnd;
            selEnd = selStart;
            selStart = t;
        }
        if (selStart!=selEnd && selStart>=0) {
            TextRange selRange = new TextRange(selStart,selEnd);
            if (selRange.containsPosition(cpos)) {
                info.range = selRange;
                return info;
            }
        }
        // Next, look for a decent token or a path:
        for (int i=0;i<tokens.length;i++) {
            Token t = tokens[i];
            TextRange candidate = t.getTextRange();
            boolean in = candidate.containsPosition(cpos);
            if (i<tokens.length-1) {
                // consider whitespace between tokens, too:
                if (tokens[i+1].getTextRange().getStartPosition()>cpos) {
                    if (candidate.getEndPosition()<cpos) {
                        in = true;
                    }
                }
            }
            if (in) {
                // expand to include all paths:
                Token first = tokens[i];
                int ft = first.getType();
                if (ft==Token.TYPE_LPAREN || ft==Token.TYPE_RPAREN || ft==Token.TYPE_LBRACKET || ft==Token.TYPE_RBRACKET || ft==Token.TYPE_COMMA)
                {
                    // can't surround one of these.
                    return null;
                }
                for (int j=i;j>=0;j--) {
                    Token t2 = tokens[j];
                    int tt = t2.getType();
                    if (j<i && t2.getTextRange().getEndPosition()!=tokens[j+1].getTextRange().getStartPosition()) {
                        break;
                    }
                    if (tt==Token.TYPE_SLASH || tt==Token.TYPE_NAME_TEST || tt==Token.TYPE_ATTRIBUTE || tt==Token.TYPE_PARENT || tt==Token.TYPE_VARIABLE_REFERENCE) {
                        first = t2;
                        continue;
                    }
                    if (tt==Token.TYPE_LPAREN || tt==Token.TYPE_RPAREN || tt==Token.TYPE_LBRACKET || tt==Token.TYPE_RBRACKET || tt==Token.TYPE_COMMA)
                    {
                        break;
                    }
                    if (tt==Token.TYPE_VARIABLE_REFERENCE) {
                        if (j==i) { // only go through 1 variable for the first thing...
                            first = t2;
                            continue;
                        }
                    }
                    break;
                }
                Token last = tokens[i];
                for (int j=i;j<tokens.length;j++) {
                    Token t2 = tokens[j];
                    int tt = t2.getType();
                    if (j>i && t2.getTextRange().getStartPosition()!=tokens[j-1].getTextRange().getEndPosition()) {
                        break;
                    }
                    if (tt==Token.TYPE_SLASH || tt==Token.TYPE_NAME_TEST || tt==Token.TYPE_ATTRIBUTE || tt==Token.TYPE_PARENT || tt==Token.TYPE_VARIABLE_REFERENCE) {
                        last = t2;
                        continue;
                    }
                    break;
                }
                if (first==last) {
                    int tt = first.getType();
                    if (tt==Token.TYPE_COMMA) {
                        // add after the comma:
                        int ep = first.getTextRange().getEndPosition();
                        info.suffix = ",";
                        info.range = new TextRange(ep,ep);
                        return info;
                    }
                    if (tt==Token.TYPE_LPAREN) {
                        // add after the lparen:
                        int ep = first.getTextRange().getEndPosition();
                        info.range = new TextRange(ep,ep);
                        info.suffix = ",";
                        return info;
                    }
                    if (tt==Token.TYPE_RPAREN) {
                        // add before the rparen:
                        int sp = first.getTextRange().getStartPosition();
                        info.range = new TextRange(sp,sp); // +1 -> For leading prefix comma.
                        info.prefix = ",";
                        return info;
                    }
                }
                // Do a quick check for functions, if we're dropping around a function call, surround it:
                if (first==last && tokens.length>i+1)
                {
                    Token next = tokens[i+1];
                    if (next.getType()==Token.TYPE_LPAREN)
                    {
                        int li = findMatchingRParen(tokens,i+2);
                        if (li<0)
                        {
                            return null;
                        }
                        last = tokens[li];
                    }
                }

                info.range = new TextRange(first.getTextRange(),last.getTextRange());
                return info;
            }
        }
        /*
        if (cpos<=0) {
            // ok, the beginning is good, surround everything.
            info.range = new TextRange(0,text.length());
            return info;
            return null;
        }
        if (cpos<text.length()) {
            // somewhere in the whitespace middle, perhaps
            info.range = new TextRange(cpos,cpos);
            return info;
        }*/
        // after end, don't use it.
        return null;
        /*
        // ... ok, just add to the end:
        info.range = new TextRange(text.length(),text.length());
        return info;*/
    }

    private static int findMatchingRParen(Token[] tokens, int startAt)
    {
        for (int i=startAt;i<tokens.length;i++)
        {
            Token t = tokens[i];
            if (t.getType()==Token.TYPE_RPAREN)
            {
                return i;
            }
            if (t.getType()==Token.TYPE_LPAREN)
            {
                i = findMatchingRParen(tokens,i+1);
                if (i<0)
                {
                    return -1;
                }
            }
        }
        return -1;
    }
}
