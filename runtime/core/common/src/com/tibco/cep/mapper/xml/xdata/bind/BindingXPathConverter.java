/*******************************************************************************
 * Copyright 2001 by TIBCO Software, Inc.
 * ALL RIGHTS RESERVED.
 */

package com.tibco.cep.mapper.xml.xdata.bind;


/**
 * A set of functions that, used by InstructionBinding and associated converters that convert XPath.
 */
final class BindingXPathConverter
{
    /*
    public static String getNamespaceAdjustedFormula(BindingReport report, NamespaceMapper namespaceMapper) {
        return getNamespaceAdjustedFormula(report.getXPathExpression(), report.getContext(), namespaceMapper);
    }

    public static String getNamespaceAdjustedFormula(String formula, ExprContext context, NamespaceMapper namespaceMapper) {
        return getNamespaceAdjustedFormula(new Parser(Lexer.lex(formula)).getExpression(), context, namespaceMapper);
    }

    public static String getNamespaceAdjustedFormula(Expr expr, ExprContext context, NamespaceMapper namespaceMapper) {
        if (expr == null) {
            return null;
        }
        String fixedFormula;
        if (namespaceMapper!=null) {
            Expr encodedFormula = XPathNamespaceConverter.encodeNamespaces(expr,context,namespaceMapper);
            Expr strDecoded = encodedFormula.replaceEntityRefsWithCharacters();
            Expr xalanized = strDecoded.xalanizeFunctions(context.getFunctionResolver(),namespaceMapper); // CHANGE TO THIS LATER: strDecoded.enscriptFunctions(XPathFunctionSet.BASIC,namespaceMapper);
            // Also convert strings from entity references to normal:
            fixedFormula = xalanized.toExactString();
        } else {
            fixedFormula = expr.toExactString();
        }
        return fixedFormula;
    }*/
}
