package com.tibco.cep.mapper.xml.xdata.xpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.mapper.xml.nsutils.NamespaceManipulationUtils;
import com.tibco.cep.mapper.xml.xdata.CharacterEscapeUtils;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.NameTestExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.expr.NodeTestExpr;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibExtFunctions;
import com.tibco.cep.mapper.xml.xdata.xpath.func.TibXPath20Functions;
import com.tibco.cep.mapper.xml.xdata.xpath.func.XFunction;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.data.primitive.NamespaceContextRegistry;
import com.tibco.xml.data.primitive.PrefixToNamespaceResolver;
import com.tibco.xml.data.primitive.QName;
import com.tibco.xml.schema.SmElement;
import com.tibco.xml.schema.SmSequenceType;

/**
 * Utility functions for manipulating Exprs.
 */
public final class ExprUtilities
{
    /**
     * Rebuilds an Expr where oldName has been replaced with newName in variables.
     * Handles the tough cases including locally defined variables --- they themselves will be
     * renamed to have '1', '2', suffixes.
     * @param takenVars The set of taken variable names so that renaming won't overlap them.
     */
    public static Expr renameVariable(Expr expr, String oldName, String newName, Set takenVars) {
        int tc = expr.getExprTypeCode();
        if (tc==ExprTypeCode.EXPR_VARIABLE_REFERENCE) {
            String vn = expr.getExprValue();
            if (vn.equals(oldName)) {
                // replace:
                return Expr.create(tc,expr.getChildren(),newName,expr.getWhitespace(),expr.getRepresentationClosure());
            }
            return expr;
        }

        Expr[] children = expr.getChildren();
        if (children.length==0) {
            return expr;
        }
        Expr[] newChildren = new Expr[children.length];
        for (int i=0;i<children.length;i++) {
            Expr c = children[i];
            newChildren[i] = renameVariable(c,oldName,newName,takenVars);
        }
        if (tc==ExprTypeCode.EXPR_FOR) {
            String vn = expr.getExprValue();
            if (vn.equals(oldName)) {
                String newLocalName = getNewVarName(oldName,takenVars);
                return Expr.create(tc,newChildren,newLocalName,expr.getWhitespace(),expr.getRepresentationClosure());
            }
        }
        return Expr.create(tc,newChildren,expr.getExprValue(),expr.getWhitespace(),expr.getRepresentationClosure());
    }

    public static List getGlobalVariables(String exprStr)
    {
        if (exprStr.indexOf("$_globalVariables")>=0)
        {
            ArrayList list = new ArrayList();
            getGlobalVariables(Parser.parse(exprStr),list);
            return list;
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Finds all the global variable names used (as referenced by $_globalVariables/ns:globalVariables/name).
     * @param e The expression to check
     * @param addToList The list to which variables (added as Strings) are added.
     */
    public static void getGlobalVariables(Expr e, List addToList)
    {
        // Look for $_globalVariables/globalVariables/(varName) where varName can have slashes, too.

        Expr at = e;
        for (;;)
        {
            Expr[] ch = at.getChildren();
            if (at.getExprTypeCode()==ExprTypeCode.EXPR_LOCATION_PATH)
            {
                Expr lc = ch[0];
                if (lc.getExprTypeCode()==ExprTypeCode.EXPR_LOCATION_PATH)
                {
                    Expr[] lc1 = lc.getChildren();
                    Expr lc0 = lc1[0];
                    if (lc0.getExprTypeCode()==ExprTypeCode.EXPR_VARIABLE_REFERENCE && lc0.getExprValue().equals("_globalVariables"))
                    {
                        // Take everything to the right of the 2 slashes:
                        String str = e.toExactString();
                        int ioc = str.indexOf("/");
                        if (ioc>=0)
                        {
                            String s2 = str.substring(ioc+1);
                            int ioc2 = s2.indexOf("/");
                            if (ioc2>=0)
                            {
                                String s3 = s2.substring(ioc2+1).trim();
                                addToList.add(s3);
                            }
                        }
                        return;
                    }
                    else
                    {
                        at = lc;
                        continue;
                    }
                }
            }
            break;
        }
        Expr[] ch = e.getChildren();
        for (int i=0;i<ch.length;i++)
        {
            getGlobalVariables(ch[i],addToList);
        }
    }

    /**
     * Finds all the variables declared on a particular expression.
     * @param e The expression.
     * @param addToSet The set to which to add the variable names (String)
     */
    public static void getDeclaredVariables(Expr e, Set addToSet)
    {
        if (e.getExprTypeCode()==ExprTypeCode.EXPR_FOR)
        {
            String v = e.getExprValue();
            addToSet.add(v);
        }
        Expr[] ch = e.getChildren();
        for (int i=0;i<ch.length;i++)
        {
            getDeclaredVariables(ch[i],addToSet);
        }
    }

    public static ErrorMessage[] gatherLexicalErrors(Expr expr)
    {
        ArrayList temp = new ArrayList();
        gatherLexicalErrors(expr,temp);
        return (ErrorMessage[]) temp.toArray(new ErrorMessage[0]);
    }

    private static void gatherLexicalErrors(Expr expr, ArrayList toList)
    {
        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_ERROR)
        {
            toList.add(new ErrorMessage(expr.getExprValue(),expr.getTextRange()));
        }
        Expr[] ch = expr.getChildren();
        for (int i=0;i<ch.length;i++)
        {
            gatherLexicalErrors(ch[i],toList);
        }
    }

    /**
     * Rebuilds an Expr where the functions have been renamed.
     * @param oldToNewNameMap A map of {@link ExpandedName} to {@link ExpandedName} for old to new name.
     */
    public static Expr renameFunctions(Expr expr, HashMap oldToNewNameMap, NamespaceContextRegistry namespaceContextRegistry, FunctionResolver fr)
    {
        int tc = expr.getExprTypeCode();
        String newValue = expr.getExprValue();
        if (tc==ExprTypeCode.EXPR_FUNCTION_CALL)
        {
            String vn = newValue;
            QName qn = new QName(vn);
            ExpandedName ename;
            try
            {
                ename = qn.getExpandedName(namespaceContextRegistry);
            }
            catch (Exception e)
            {
                ename = null;
            }
            if (ename!=null)
            {
                ExpandedName nn = (ExpandedName) oldToNewNameMap.get(ename);
                if (nn!=null)
                {
                    FunctionNamespace ns = fr.getNamespace(nn.getNamespaceURI());
                    String spfx = null;
                    if (ns!=null)
                    {
                        spfx = ns.getSuggestedPrefix();
                    }
                    newValue = NamespaceManipulationUtils.getOrAddQNameFromExpandedName(nn,namespaceContextRegistry,spfx).toString();
                }
            }
        }

        Expr[] children = expr.getChildren();
        Expr[] newChildren = new Expr[children.length];
        for (int i=0;i<children.length;i++)
        {
            Expr c = children[i];
            newChildren[i] = renameFunctions(c,oldToNewNameMap,namespaceContextRegistry,fr);
        }
        return Expr.create(tc,newChildren,newValue,expr.getWhitespace(),expr.getRepresentationClosure());
    }

    /**
     * Rebuilds an Expr where element oldOld has been replaced with newName.<br>
     * (This is a convenience function for {@link #renameElements}.)
     */
    public static Expr renameElement(Expr expr, ExprContext ec, SmElement oldEl, ExpandedName newName, NamespaceContextRegistry ni)
    {
        if (ni==null)
        {
            throw new NullPointerException("Null namespace importer");
        }
        HashMap singleMap = new HashMap();
        singleMap.put(oldEl,newName);
        return renameElements(expr,ec,singleMap,ni);
    }

    /**
     * Rebuilds an Expr where element oldOld has been replaced with newName.<br>
     * (This is a convenience function for {@link #renameElements}.)
     */
    public static Expr renameElement(Expr expr, ExprContext ec, SmElement oldEl, ExpandedName newNamePath[], NamespaceContextRegistry ni)
    {
        if (ni==null)
        {
            throw new NullPointerException("Null namespace importer");
        }
        HashMap singleMap = new HashMap();
        singleMap.put(oldEl,newNamePath);
        return renameElements(expr,ec,singleMap,ni);
    }

    /**
     * Rebuilds an Expr where element oldOld has been replaced with newName.
     * Handles the tough cases including locally defined variables --- they themselves will be
     * renamed to have '1', '2', suffixes.
     * @param elements A map of old {@link SmElement} to either an {@link ExpandedName} or an array of {@link ExpandedName}.
     * If an array is passed in, it is treated as a child-axis path.
     */
    public static Expr renameElements(Expr expr, ExprContext ec, Map elements, NamespaceContextRegistry ni)
    {
        EvalTypeInfo eti = new EvalTypeInfo();
        // Maybe replace w/ a callback mechanism on EvalTypeInfo rather than a generic record (for efficiency)
        eti.setRecordReturnTypes(true);
        eti.setRecordExprContexts(true);
        expr.evalType(ec,eti);
        return renameElementsInternal(expr,eti,elements,ni);
    }

    private static Expr renameElementsInternal(Expr expr, EvalTypeInfo info, Map elements, NamespaceContextRegistry ni)
    {
        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_STEP)
        {
            Expr[] children = expr.getChildren();
            Expr axis = children[0];
            if (axis.getExprTypeCode()==ExprTypeCode.EXPR_PARENT_AXIS)
            {
                return renameElementsForParentAxis(expr,info,elements,ni);
            }
            else
            {
                SmSequenceType ret = info.getReturnType(expr);
                SmSequenceType pt = ret.prime(); // who cares about card, etc.
                if (pt.getParticleTerm() instanceof SmElement)
                {
                    Object elOrPath = elements.get(pt.getParticleTerm());
                    if (elOrPath!=null)
                    {
                        Expr nodeTest = children[1];
                        if (axis.getExprTypeCode()==ExprTypeCode.EXPR_SELF_AXIS)
                        {
                            if (nodeTest.getExprTypeCode()==ExprTypeCode.EXPR_NODE_NODE_TEST)
                            {
                                // this is just '.'; we are already on the node, can't do anything.
                                return expr;
                            }
                            // Rename the name test to the <<last>> part:
                            ExpandedName lastName;
                            if (elOrPath instanceof ExpandedName[])
                            {
                                ExpandedName[] nn = (ExpandedName[]) elOrPath;
                                lastName = nn[nn.length-1];
                            }
                            else
                            {
                                lastName = (ExpandedName) elOrPath;
                            }
                            QName qn = NamespaceManipulationUtils.getOrAddQNameFromExpandedName(lastName,ni);
                            String nns = qn.toString();
                            Expr nnt = Expr.create(ExprTypeCode.EXPR_NAME_TEST,new Expr[0],nns,expr.getWhitespace(),expr.getRepresentationClosure());
                            Expr rr = Expr.create(ExprTypeCode.EXPR_STEP,new Expr[] {axis,nnt},null,expr.getWhitespace(),expr.getRepresentationClosure());

                            return rr;
                        }
                        ExpandedName first = elOrPath instanceof ExpandedName ? (ExpandedName) elOrPath : ((ExpandedName[])elOrPath)[0];
                        // keep axis the same....
                        Expr nnt = createNewNameTestForRename(children[1],first,ni);
                        Expr rr = Expr.create(ExprTypeCode.EXPR_STEP,new Expr[] {axis,nnt},null,expr.getWhitespace(),expr.getRepresentationClosure());
                        if (elOrPath instanceof ExpandedName[])
                        {
                            ExpandedName[] e = (ExpandedName[]) elOrPath;
                            // add remaning:
                            for (int i=1;i<e.length;i++)
                            {
                                QName qn2 = NamespaceManipulationUtils.getOrAddQNameFromExpandedName(e[i],ni);
                                Expr np = Parser.parse(qn2.toString());
                                rr = Expr.create(ExprTypeCode.EXPR_LOCATION_PATH,new Expr[] {rr,np},null,"",null);
                            }
                            return rr;
                        }
                        else
                        {
                            return rr;
                        }
                    }
                }
            }
        }
        Expr[] c = expr.getChildren();
        Expr[] nc = new Expr[c.length];
        for (int i=0;i<c.length;i++)
        {
            nc[i] = renameElementsInternal(c[i],info,elements,ni);
        }
        return Expr.create(expr.getExprTypeCode(),nc,expr.getExprValue(),expr.getWhitespace(),expr.getRepresentationClosure());
    }

    private static Expr createNewNameTestForRename(Expr oldNameTest, ExpandedName first, NamespaceContextRegistry ni)
    {
        if (oldNameTest.getExprTypeCode()==ExprTypeCode.EXPR_NODE_NODE_TEST)
        {
            return oldNameTest;
        }
        if (oldNameTest.getExprTypeCode()!=ExprTypeCode.EXPR_NAME_TEST)
        {
            return oldNameTest;
        }
        String n = oldNameTest.getExprValue();
        if ("*".equals(n))
        {
            return oldNameTest;
        }
        QName qn = NamespaceManipulationUtils.getOrAddQNameFromExpandedName(first,ni);
        String nns = qn.toString();
        return Expr.create(ExprTypeCode.EXPR_NAME_TEST,new Expr[0],nns,oldNameTest.getWhitespace(),oldNameTest.getRepresentationClosure());
    }

    private static Expr renameElementsForParentAxis(Expr expr, EvalTypeInfo info, Map elements, NamespaceContextRegistry ni)
    {
        SmSequenceType ret = info.getExprContext(expr).getInput();
        SmSequenceType pt = ret.prime(); // who cares about card, etc.
        if (pt.getParticleTerm() instanceof SmElement)
        {
            Object elOrPath = elements.get(pt.getParticleTerm());
            if (elOrPath!=null)
            {
                if (elOrPath instanceof ExpandedName)
                {
                    // no biggie.
                    return expr; //WCETODO handle if non-node() nametest.
                }
                else
                {
                    ExpandedName[] ea = (ExpandedName[]) elOrPath;
                    StringBuffer retb = new StringBuffer();
                    for (int i=0;i<ea.length-1;i++)
                    {
                        retb.append("..");
                        if (i>0)
                        {
                            retb.append("/");
                        }
                    }
                    retb.append("/");
                    retb.append(expr.toExactString()); // WCETODO handle if non-node() nametest.
                    return Parser.parse(retb.toString());
                }
            }
        }
        return expr; // no change.
    }

    /**
     * Rebuilds an Expr where oldPrefix has been replaced with newPrefix in all uses.
     */
    public static Expr renamePrefix(Expr expr, String oldPrefix, String newPrefix)
    {
        HashMap map = new HashMap();
        map.put(oldPrefix,newPrefix);
        return renamePrefixes(expr,map);
    }

    public static String renamePrefixes(String expr, Map oldToNewPrefixMap)
    {
        return renamePrefixes(Parser.parse(expr),oldToNewPrefixMap).toExactString();
    }

    /**
     * Rebuilds an Expr where oldPrefix has been replaced with newPrefix in all uses.
     * @param oldToNewPrefixMap A map of old (String) prefix to new (String) prefix.
     */
    public static Expr renamePrefixes(Expr expr, Map oldToNewPrefixMap)
    {

        int tc = expr.getExprTypeCode();
        Expr[] children = expr.getChildren();
        Expr[] newChildren;
        if (children.length==0)
        {
            newChildren = children;
        }
        else
        {
            newChildren = new Expr[children.length];
            for (int i=0;i<children.length;i++)
            {
                Expr c = children[i];
                newChildren[i] = renamePrefixes(c,oldToNewPrefixMap);
            }
        }
        if (tc==ExprTypeCode.EXPR_VARIABLE_REFERENCE || tc==ExprTypeCode.EXPR_NAME_TEST || tc==ExprTypeCode.EXPR_FUNCTION_CALL) {
            String vn = expr.getExprValue();
            QName qn = new QName(vn);
            QName rqn = replacePrefix(qn,oldToNewPrefixMap);
            // replace:
            return Expr.create(tc,newChildren,rqn.toString(),expr.getWhitespace(),expr.getRepresentationClosure());
        }
        return Expr.create(tc,newChildren,expr.getExprValue(),expr.getWhitespace(),expr.getRepresentationClosure());
    }

    private static QName replacePrefix(QName qname, Map oldToNewPrefixMap)
    {
        String n = (String)oldToNewPrefixMap.get(qname.getPrefix());
        if (n!=null)
        {
            return new QName(n,qname.getLocalName());
        }
        return qname;
    }

    /**
     * Returns true if the expression . and position AND last().
     * @param expr
     * @param functionResolver
     * @param pnr
     * @return
     * @throws PrefixToNamespaceResolver.PrefixNotFoundException
     */
    public static boolean isIndependentOfDotAndPosition(Expr expr, FunctionResolver functionResolver, PrefixToNamespaceResolver pnr) throws PrefixToNamespaceResolver.PrefixNotFoundException {
        int tc = expr.getExprTypeCode();
        if (tc==ExprTypeCode.EXPR_VARIABLE_REFERENCE) {
            return true;
        }
        if (tc==ExprTypeCode.EXPR_LITERAL_STRING || tc==ExprTypeCode.EXPR_NUMBER) {
            return true;
        }
        boolean checkChildren = false;
        if (tc==ExprTypeCode.EXPR_FUNCTION_CALL) {
            String f = expr.getExprValue();
            QName qn = new QName(f);
            String pfx = qn.getPrefix();
            FunctionNamespace fn;
            if (pfx.length()==0) {
                fn = functionResolver.getNamespace(null);
            } else {
                String ns = pnr.getNamespaceURIForPrefix(qn.getPrefix());
                fn = functionResolver.getNamespace(ns);
            }
            if (fn==null) {
                return false; // don't know this function...
            }
            XFunction xf = fn.get(qn.getLocalName());
            if (xf==null) {
                return false; // don't know this function.
            }
            if (!xf.isIndependentOfFocus(expr.getChildren().length)) {
                return false;
            }
            // even if it is true for the function itself, we must check the children:
            checkChildren = true;
        }
        if (tc==ExprTypeCode.EXPR_LOCATION_PATH) {
            // if the left-hand-side is ok:
            Expr[] c = expr.getChildren();
            // then there's nothing in the right hand side that could be dependent on context.
            // so something like: b/a[.] is indep. because the '.' inside the [] <is> set by those brackets; it is not
            // the same '.' from an expression like substring(.,'hello').
            return isIndependentOfDotAndPosition(c[0],functionResolver,pnr);
        }
        if (isOperator(tc)) {
            checkChildren = true;
        }
        if (tc==ExprTypeCode.EXPR_PAREN || tc==ExprTypeCode.EXPR_PREDICATE) {
            checkChildren = true;
        }
        if (checkChildren) {
            // If all the children are ok, then we are ok:
            Expr[] c = expr.getChildren();
            for (int i=0;i<c.length;i++) {
                if (!isIndependentOfDotAndPosition(c[i],functionResolver,pnr)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Returns true if the expression is just a constant (either numeric, string, or boolean).
     * @param expr
     */
    public static boolean isSimpleConstant(Expr expr)
    {
        int tc = expr.getExprTypeCode();
        if (tc==ExprTypeCode.EXPR_LITERAL_STRING || tc==ExprTypeCode.EXPR_NUMBER)
        {
            return true;
        }
        if (tc==ExprTypeCode.EXPR_FUNCTION_CALL)
        {
            String n = expr.getExprValue();
            if (n.equals("true") || n.equals("false"))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the expression is just a function with no arguments.
     * @param expr
     */
    public static boolean isNoArgFunction(Expr expr)
    {
        int tc = expr.getExprTypeCode();
        if (tc==ExprTypeCode.EXPR_FUNCTION_CALL)
        {
            return expr.getChildren().length==0;
        }
        return false;
    }

    /**
     * Returns true if the expression is just a constant (either numeric, string, or boolean).
     * @param expr
     */
    public static boolean isVariableReference(Expr expr)
    {
        int tc = expr.getExprTypeCode();
        if (tc==ExprTypeCode.EXPR_VARIABLE_REFERENCE)
        {
            return true;
        }
        return false;
    }

    /**
     * Same as other version, this one just uses strings for xpath.
     */
    public static String renameVariable(String xpathExpr, String oldName, String newName, Set takenVars) {
        Expr e = Parser.parse(xpathExpr);
        Expr r = renameVariable(e,oldName,newName,takenVars);
        return r.toExactString();
    }

    private static String getNewVarName(String vn, Set takenVars) {
        for (int i=1;;i++) {
            String nn = vn + i;
            if (!takenVars.contains(nn)) {
                return nn;
            }
        }
    }

    public static String decodeXalanJavaFunction(String name) {
        if (name.equals("concatSequence")) { // HACK hardcoded for now... fixme
            return "concat-sequence";
        }
        if (name.equals("concatSequenceFormat")) {
            return "concat-sequence-format";
        }
        if (name.equals("base64ToHex")) {
            return "base64-to-hex";
        }
        if (name.equals("base64ToString")) {
            return "base64-to-string";
        }
        if (name.equals("hexToBase64")) {
            return "hex-to-base64";
        }
        if (name.equals("hexToString")) {
            return "hex-to-string";
        }
        if (name.equals("stringToBase64")) {
            return "string-to-base64";
        }
        if (name.equals("stringToHex")) {
            return "string-to-hex";
        }
        if (name.equals("substringAfterLast")) {
            return "substring-after-last";
        }
        if (name.equals("substringBeforeLast")) {
            return "substring-before-last";
        }
        if (name.equals("ifAbsent")) {
            return "if-absent";
        }
        if (name.equals("padAndLimit")) {
            return "pad-and-limit";
        }
        if (name.equals("getCenturyFromDateTime")) {
            return "get-Century-from-dateTime";
        }
        if (name.equals("getCenturyFromDate")) {
            return "get-Century-from-date";
        }
        if (name.equals("getYearFromDateTime")) {
            return "get-Year-from-dateTime";
        }
        if (name.equals("getYearFromDate")) {
            return "get-Year-from-date";
        }
        if (name.equals("getMonthFromDateTime")) {
            return "get-month-from-dateTime";
        }
        if (name.equals("getMonthFromDate")) {
            return "get-month-from-date";
        }
        if (name.equals("getDayFromDateTime")) {
            return "get-day-from-dateTime";
        }
        if (name.equals("getDayFromDate")) {
            return "get-day-from-date";
        }
        if (name.equals("getHourFromDateTime")) {
            return "get-hour-from-dateTime";
        }
        if (name.equals("getHourFromTime")) {
            return "get-hour-from-time";
        }
        if (name.equals("getMinutesFromDateTime")) {
            return "get-minutes-from-dateTime";
        }
        if (name.equals("getMinutesFromTime")) {
            return "get-minutes-from-time";
        }
        if (name.equals("getSecondsFromDateTime")) {
            return "get-seconds-from-dateTime";
        }
        if (name.equals("getSecondsFromTime")) {
            return "get-seconds-from-time";
        }
        if (name.equals("getTimezoneFromDateTime")) {
            return "get-timezone-from-dateTime";
        }
        if (name.equals("getTimezoneFromDate")) {
            return "get-timezone-from-date";
        }
        if (name.equals("getTimezoneFromTime")) {
            return "get-timezone-from-time";
        }
        if (name.equals("createDateTime")) {
            return "create-dateTime";
        }
        if (name.equals("createDate")) {
            return "create-date";
        }
        if (name.equals("createTime")) {
            return "create-time";
        }
        if (name.equals("currentDateTime")) {
            return "current-dateTime";
        }
        if (name.equals("formatDateTime")) {
            return "format-dateTime";
        }
        if (name.equals("formatDate")) {
            return "format-date";
        }
        if (name.equals("formatTime")) {
            return "format-time";
        }
        if (name.equals("parseDateTime")) {
            return "parse-dateTime";
        }
        if (name.equals("parseDate")) {
            return "parse-date";
        }
        if (name.equals("parseTime")) {
            return "parse-time";
        }
        if (name.equals("upperCase")) {
            return "upper-case";
        }
        if (name.equals("lowerCase")) {
            return "lower-case";
        }
        if (name.equals("currentGroup")) {
            return "current-group";
        }
        if (name.equals("roundFraction")) {
            return "round-fraction";
        }
        if (name.equals("stringRoundFraction")) {
            return "string-round-fraction";
        }
        if (name.equals("indexOf")) {
            return "index-of";
        }
        if (name.equals("lastIndexOf")) {
            return "last-index-of";
        }
        return name;
    }

    /**
     * Tests if this expression is a parent step, i.e. '..' = parent::node()
     * @param expr The expression to test
     * @return True if it is, false otherwise.
     */
    public static boolean isParentStep(Expr expr) {
        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_STEP) {
            Expr[] c = expr.getChildren();
            if (c[0].getExprTypeCode()==ExprTypeCode.EXPR_PARENT_AXIS) {
                if (c[1].getExprTypeCode()==ExprTypeCode.EXPR_NODE_NODE_TEST) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isOperator(int typeCode) {
        switch (typeCode) {
            case ExprTypeCode.EXPR_ADD:
            case ExprTypeCode.EXPR_DIVIDE:
            case ExprTypeCode.EXPR_MULTIPLY:
            case ExprTypeCode.EXPR_SUBTRACT:
            case ExprTypeCode.EXPR_AND:
            case ExprTypeCode.EXPR_OR:
                return true;
        }
        return false;
    }

    /**
     * Adds namespace prefixes to formulas (when they can be unambiguously figured out)
     * @param context The expression context.
     * @param mapper New namespaces are added to this list (if already there, does nothing)
     * todo currently takes two NamespaceImporters, one in the context, the other explicitly.  Should
     * be consolidated.
     */
    static public final Expr encodeNamespaces(Expr expr, ExprContext context, NamespaceContextRegistry mapper) {
        EvalTypeInfo info = new EvalTypeInfo();
        info.setRecordNamespaceResolutions(true);
        expr.evalType(context,info);
        return cloneWithNamespaces(expr,info,mapper);
    }

    /**
     * Strips namespace prefixes from formulas (when they can be unambiguously figured out).
     * Unclear what the use-case is here... but it is symmetric to
     * {@link #encodeNamespaces}
     * @param context The expression context.
     */
    public static final Expr decodeNamespaces(Expr expr, ExprContext context) {
        EvalTypeInfo info = new EvalTypeInfo();
        info.setRecordPrefixResolutions(true);
        expr.evalType(context,info);
        return cloneWithoutNamespaces(expr,info);
    }

    /**
     * Strips out both parens and predicates from the xpath.
     */
    public static final Expr removeParensAndPredicates(Expr expr)
    {
        int tc = expr.getExprTypeCode();
        if (tc==ExprTypeCode.EXPR_PAREN) {
            return removeParensAndPredicates(expr.getChildren()[0]);
        }
        if (tc==ExprTypeCode.EXPR_PREDICATE)
        {
            return removeParensAndPredicates(expr.getChildren()[0]);
        }
        String value = expr.getExprValue();
        Expr[] children = expr.getChildren();
        Expr[] nchildren;
        if (children.length>0) {
            nchildren = new Expr[children.length];
            for (int i=0;i<nchildren.length;i++) {
                nchildren[i] = removeParensAndPredicates(children[i]);
            }
        } else {
            nchildren = children;
        }
        String trailingWhitespace = expr.getWhitespace();
        String repClosure = expr.getRepresentationClosure();
        return Expr.create(tc,nchildren,value,trailingWhitespace,repClosure);
    }

    private static final Expr cloneWithNamespaces(Expr expr, EvalTypeInfo info, NamespaceContextRegistry namespaceToPrefix) {
        int tc = expr.getExprTypeCode();
        if (tc==ExprTypeCode.EXPR_NAME_TEST)
        {
            if (info.hasNamespaceResolution((NodeTestExpr)expr))
            {
                String namespaceURI = info.getNamespaceResolution((NodeTestExpr)expr);
                String prefix = namespaceToPrefix.getOrAddPrefixForNamespaceURI(namespaceURI);
                String name = expr.getExprValue();
                QName qn = new QName(name);
                QName qname2 = new QName(prefix,qn.getLocalName());
                return Expr.create(tc,new Expr[0],qname2.toString(),expr.getWhitespace(),expr.getRepresentationClosure());
            }
        }
        String value = expr.getExprValue();
        Expr[] children = expr.getChildren();
        Expr[] nchildren;
        if (children.length>0) {
            nchildren = new Expr[children.length];
            for (int i=0;i<nchildren.length;i++) {
                nchildren[i] = cloneWithNamespaces(children[i],info,namespaceToPrefix);
            }
        } else {
            nchildren = children;
        }
        String trailingWhitespace = expr.getWhitespace();
        String repClosure = expr.getRepresentationClosure();
        return Expr.create(tc,nchildren,value,trailingWhitespace,repClosure);
    }

    private static final Expr cloneWithoutNamespaces(Expr expr, EvalTypeInfo info) {
        int tc = expr.getExprTypeCode();
        if (tc==ExprTypeCode.EXPR_NAME_TEST) {
            String prefix = info.getPrefixResolution((NodeTestExpr)expr);
            if (prefix!=null) {
                String name = expr.getExprValue();
                int i = name.indexOf(':');
                if (i>=0) {
                    String ncname = name.substring(i+1);
                    return new NameTestExpr(ncname,expr.getTextRange(),expr.getWhitespace());
                }
            }
        }
        String value = expr.getExprValue();
        Expr[] children = expr.getChildren();
        Expr[] nchildren;
        if (children.length>0) {
            nchildren = new Expr[children.length];
            for (int i=0;i<nchildren.length;i++) {
                nchildren[i] = cloneWithoutNamespaces(children[i],info);
            }
        } else {
            nchildren = children;
        }
        return Expr.create(tc,nchildren,value,expr.getWhitespace(),expr.getRepresentationClosure());
    }

    /**
     * For an expression, replaces entity-refs (i.e. &nl;, etc.) with their actual string values inside string literals.
     */
    public static final Expr replaceCharactersWithEntityRefs(Expr expr) {
        Expr[] ch = expr.getChildren();
        Expr[] nchildren;
        if (ch.length==0) {
            nchildren = ch;
        } else {
            nchildren = new Expr[ch.length];
            for (int i=0;i<ch.length;i++) {
                nchildren[i] = replaceCharactersWithEntityRefs(ch[i]);
            }
        }
        String strVal = expr.getExprValue();
        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_LITERAL_STRING) {
            strVal = CharacterEscapeUtils.escapeCharacters(strVal,true,false); // false -> don't do quot;
        }
        return Expr.create(expr.getExprTypeCode(),nchildren,strVal,expr.getWhitespace(),expr.getRepresentationClosure());
    }

    /**
     * For an expression, replaces the non-editable characters (i.e. new-line, carriage-return, etc.) inside string literals
     * with entity reference equivalents.
     */
    static public final Expr replaceEntityRefsWithCharacters(Expr expr) {
        Expr[] ch = expr.getChildren();
        Expr[] nchildren;
        if (ch.length==0) {
            nchildren = ch;
        } else {
            nchildren = new Expr[ch.length];
            for (int i=0;i<ch.length;i++) {
                nchildren[i] = replaceEntityRefsWithCharacters(ch[i]);
            }
        }
        String strVal = expr.getExprValue();
        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_LITERAL_STRING) {
            strVal = CharacterEscapeUtils.unescapeCharacters(strVal);
        }
        return Expr.create(expr.getExprTypeCode(),nchildren,strVal,expr.getWhitespace(),expr.getRepresentationClosure());
    }

    public static String escapeXPathCharacterRefs(String xpath)
    {
        if (xpath==null)
        {
            return null;
        }
        //WCETODO can actually optimize this quite a bit; doesn't need to parse into Expr, just tokens.
        return ExprUtilities.replaceCharactersWithEntityRefs(com.tibco.cep.mapper.xml.xdata.xpath.Parser.parse(xpath)).toExactString();
    }

    public static String unescapeXPathCharacterRefs(String xpath)
    {
        //WCETODO can actually optimize this quite a bit; doesn't need to parse into Expr, just tokens.
        if (xpath==null)
        {
            return null;
        }
        return ExprUtilities.replaceEntityRefsWithCharacters(com.tibco.cep.mapper.xml.xdata.xpath.Parser.parse(xpath)).toExactString();
    }

    /**
     * Converts an XPath expression by removing xalan java extension function encoded functions.
     * @param namespaceContextRegistry The XiNode resolver for the xpath context.
     * @return The new expression.
     */
    static public final Expr decodeXalanJavaExtensionFunctions(Expr expr, FunctionResolver functionResolver, NamespaceContextRegistry namespaceContextRegistry) {
        Expr[] children = expr.getChildren();
        Expr[] nchildren;
        if (children.length==0) {
            nchildren = children;
        } else {
            nchildren = new Expr[children.length];
            for (int i=0;i<children.length;i++) {
                nchildren[i] = decodeXalanJavaExtensionFunctions(children[i],functionResolver,namespaceContextRegistry);
            }
        }

        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_FUNCTION_CALL) {
            String value = expr.getExprValue();
            QName qname = new QName(value);
            String prefix = qname.getPrefix();
            if (prefix != null && prefix.length()>0) {
                String lname = qname.getLocalName();
                String ns;
                try {
                    ns = namespaceContextRegistry.getNamespaceURIForPrefix(prefix);
                } catch (PrefixToNamespaceResolver.PrefixNotFoundException nfe) {
                    ns = "";
                }
                if (XALAN_XSLT_NAMESPACE.equals(ns)) {
                    String javaFunctionName = lname.substring(lname.lastIndexOf('.')+1);
                    String javaClassName = lname.substring(0,lname.lastIndexOf('.'));
                    String fnQName = computeXPathFunctionQNameFromJava(
                            javaClassName,
                            javaFunctionName,
                            functionResolver,
                            namespaceContextRegistry
                    );
                    return Expr.create(expr.getExprTypeCode(),nchildren,fnQName,expr.getWhitespace(),expr.getRepresentationClosure());
                }
            }
        }
        return Expr.create(expr.getExprTypeCode(),nchildren,expr.getExprValue(),expr.getWhitespace(),expr.getRepresentationClosure());
    }

    /**
     * Computes the XPath function qname from a java class and function name.
     */
    private static String computeXPathFunctionQNameFromJava(String javaClassName, String javaFunctionName, FunctionResolver functionResolver, NamespaceContextRegistry namespaceContextRegistry)
    {
        String xpathFunctionPrefix;
        String xpathFunctionLocalName;
        if (javaClassName.equals(TibExtFunctions.class.getName()) || javaClassName.equals(TibXPath20Functions.class.getName())) {
            xpathFunctionLocalName = decodeXalanJavaFunction(javaFunctionName);
            xpathFunctionPrefix = "";
        } else {
            String javans = "java://" + javaClassName;
            FunctionNamespace fns = functionResolver.getNamespace(javans);
            if (fns==null)
            {
                fns = FunctionResolverSupport.getNamespaceUnambiguous(functionResolver,javaFunctionName);
            }
            if (fns==null) {
                // Shouldn't happen unless function has disappeared or something like that...
                xpathFunctionPrefix = javaClassName; // just make up a prefix..
                xpathFunctionLocalName = javaFunctionName;
            } else {
                xpathFunctionPrefix = namespaceContextRegistry.getOrAddPrefixForNamespaceURI(fns.getNamespaceURI(),fns.getSuggestedPrefix());
                xpathFunctionLocalName = javaFunctionName;
            }
        }
        String fnQName = new QName(xpathFunctionPrefix,xpathFunctionLocalName).toString();
        return fnQName;
    }

    /**
     *
     */
    static public final Expr descriptFunctions(Expr expr, FunctionResolver functions, PrefixToNamespaceResolver prefixToNamespaceResolver) {
        Expr[] children = expr.getChildren();
        Expr[] nchildren;
        if (children.length==0) {
            nchildren = children;
        } else {
            nchildren = new Expr[children.length];
            for (int i=0;i<children.length;i++) {
                nchildren[i] = descriptFunctions(children[i],functions,prefixToNamespaceResolver);
            }
        }

        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_FUNCTION_CALL) {
            String value = expr.getExprValue();
            QName qname = new QName(value);
            String prefix = qname.getPrefix();
            if (prefix != null && prefix.length()>0) {
                String lname = qname.getLocalName();
                String functionName = value;
                String ns;
                try {
                    ns = prefixToNamespaceResolver.getNamespaceURIForPrefix(prefix);
                } catch (Exception e) {
                    ns = null;
                }
                if (ns!=null) {
                    FunctionNamespace ffn = functions.getNamespace(ns);
                    if (ffn!=null && ffn.isBuiltIn()) {
                        functionName = lname;
                    }
                }
                return Expr.create(expr.getExprTypeCode(),nchildren,functionName,expr.getWhitespace(),expr.getRepresentationClosure());
            }
        }
        return Expr.create(expr.getExprTypeCode(),nchildren,expr.getExprValue(),expr.getWhitespace(),expr.getRepresentationClosure());
    }

    /**
     * Returns the combination of two lists, as an optimization will return the other list if one list is empty.<br>
     * Neither list is changed.<br>
     * (This method belongs somewhere else; relocate & deprecate if possible)
     * @param l1 The first list, never null.
     * @param l2 The second list, never null.
     * @return A list containing the first list concatenated to the second; may be the same as l1 or l2 if the other is empty.
     */
    public static List concatLists(List l1, List l2)
    {
        if (l1.size()==0)
        {
            return l2;
        }
        if (l2.size()==0)
        {
            return l1;
        }
        ArrayList temp = new ArrayList();
        temp.addAll(l1);
        temp.addAll(l2);
        return temp;
    }
    /*
     * Takes all functions in an expression and converts them to xalan style namespace encodings.
     *
    public final Expr enscriptFunctionsXalanStyle(Expr expr, FunctionResolver functions, NamespaceMapper mapper, Set excludedPrefixes)
    {
        Expr[] children = expr.getChildren();
        Expr[] nchildren;
        if (children.length==0) {
            nchildren = children;
        } else {
            nchildren = new Expr[children.length];
            for (int i=0;i<children.length;i++) {
                nchildren[i] = enscriptFunctionsXalanStyle(children[i],functions,mapper,excludedPrefixes);
            }
        }

        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_FUNCTION_CALL) {
            String value = expr.getExprValue();
            XFunction f = FunctionResolverSupport.getXFunctionUnambiguous(value);
            if (f!=null)
            {
                if (f.getName().getNamespaceURI()!=null)
                {
                    String pfx = mapper.registerOrGetPrefixForNamespaceURI(f.getName().getNamespaceURI(),null);
                    excludedPrefixes.add(pfx);
                    return Expr.create(expr.getExprTypeCode(),nchildren,pfx + ":" + value,expr.getWhitespace(),expr.getRepresentationClosure());
                }
            }
        }
        return Expr.create(expr.getExprTypeCode(),nchildren,expr.getExprValue(),expr.getWhitespace(),expr.getRepresentationClosure());
    }*/

    public static final String XALAN_XSLT_NAMESPACE = "http://xml.apache.org/xslt/java";


    /*
     * Converts an expression to an new expression by changing its 'extension function' calls to work as Xalan-java
     * extensions.
     * @param expr The expression.
     * @param functionResolver The function resolver to work against (indicating which is built-in versus extension)
     * @param mapper The namespace mapper, required for registering a the xalan extension namespace, if required.
     * @param excludedPrefixes The excluded prefixes for the containing stylesheet, required because the xalan extension should be excluded.
     * @return The new expression.
     *
    static public final Expr encodeXalanJavaExtensionFunctions(Expr expr, FunctionResolver functionResolver, NamespaceMapper mapper, Set excludedPrefixes) {
        Expr[] children = expr.getChildren();
        Expr[] nchildren;
        if (children.length==0) {
            nchildren = children;
        } else {
            nchildren = new Expr[children.length];
            for (int i=0;i<children.length;i++) {
                nchildren[i] = encodeXalanJavaExtensionFunctions(children[i],functionResolver,mapper,excludedPrefixes);
            }
        }

        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_FUNCTION_CALL) {
            String value = expr.getExprValue();
            QName qname = new QName(value);
            if (qname.getPrefix()!=null && qname.getPrefix().length()>0) { // pos...
                String namespace;
                try {
                    namespace = mapper.getNamespaceURIForPrefix(qname.getPrefix());
                } catch (PrefixToNamespaceResolver.PrefixNotFoundException nnfe) {
                    namespace = null;
                }
                if (namespace!=null) {
                    FunctionNamespace ef = functionResolver.getNamespace(namespace);
                    if (ef!=null) {
                        String src = ef.getSrc();
                        if (src.startsWith("java://")) { //hacky.
                            src = src.substring("java://".length());
                        }
                        mapper.addNamespaceURI("java",XALAN_XSLT_NAMESPACE);
                        String nf = src + "." + mungeFunctionNameToJava(qname.getLocalName());
                        return Expr.create(expr.getExprTypeCode(),nchildren,nf,expr.getWhitespace(),expr.getRepresentationClosure());
                    }
                }
            } else {
                // hack, just save as Xalan for now, later make it work off of XSLT 1.1 script specification.
                XFunction f = functionResolver.getXFunctionUnambiguous(value);
                if (f!=null) {
                    if (f.getName().getNamespaceURI()!=null) {
                        String namespace = f.getName().getNamespaceURI();
                        FunctionNamespace ef = functionResolver.getNamespace(namespace);
                        if (ef!=null) {
                            String src = ef.getSrc();
                            String pfx = mapper.registerOrGetPrefixForNamespaceURI("http://xml.apache.org/xslt/java","java");// true->exclude.
                            excludedPrefixes.add(pfx);
                            String nf = src + "." + mungeFunctionNameToJava(qname.getLocalName());
                            return Expr.create(expr.getExprTypeCode(),nchildren,nf,expr.getWhitespace(),expr.getRepresentationClosure());
                        }
                    }
                }
            }
        }
        return Expr.create(expr.getExprTypeCode(),nchildren,expr.getExprValue(),expr.getWhitespace(),expr.getRepresentationClosure());
    }*/

    /**
     * A utility method which converts an Expr so that all of its unprefixed functions are resolved against
     * the function set automatically generating prefixes for those missing namespaces.
     * @param expr The expression to work with.
     * @param xPathFunctionSet The function set
     * @param namespaceMapper The namespace mapper to use to generate new prefixes.
     * @return
     */
    static public Expr encodeFunctionNamespaces(Expr expr, FunctionResolver xPathFunctionSet, NamespaceContextRegistry namespaceMapper) {
        Expr[] children = expr.getChildren();
        Expr[] nchildren;
        if (children.length==0) {
            nchildren = children;
        } else {
            nchildren = new Expr[children.length];
            for (int i=0;i<children.length;i++) {
                nchildren[i] = encodeFunctionNamespaces(children[i],xPathFunctionSet,namespaceMapper);
            }
        }

        if (expr.getExprTypeCode()==ExprTypeCode.EXPR_FUNCTION_CALL) {
            String value = expr.getExprValue();
            QName qname = new QName(value);
            if (qname.getPrefix()!=null && qname.getPrefix().length()>0)
            {
                // pos...
                // no change, there already is a prefix...
            }
            else
            {
                XFunction f = FunctionResolverSupport.getXFunctionUnambiguous(xPathFunctionSet,value);
                if (f!=null) {
                    if (f.getName().getNamespaceURI()!=null) {
                        String namespace = f.getName().getNamespaceURI();
                        String newPrefix = namespaceMapper.getOrAddPrefixForNamespaceURI(namespace);
                        String newFunction = newPrefix + ":" + qname.getLocalName();
                        return Expr.create(expr.getExprTypeCode(),nchildren,newFunction,expr.getWhitespace(),expr.getRepresentationClosure());
                    }
                }
            }
        }
        return Expr.create(expr.getExprTypeCode(),nchildren,expr.getExprValue(),expr.getWhitespace(),expr.getRepresentationClosure());
    }

    /*
    private static String mungeFunctionNameToJava(String name) {
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<name.length();i++) {
            char c = name.charAt(i);
            if (c=='-') {
                // capitalize character following a '-'
                i++;
                if (i<name.length()) {
                    c = name.charAt(i);
                    char nc = Character.toUpperCase(c);
                    sb.append(nc);
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }*/
}
