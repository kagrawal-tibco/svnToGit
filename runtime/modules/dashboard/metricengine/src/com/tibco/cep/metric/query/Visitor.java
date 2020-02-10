package com.tibco.cep.metric.query;


import static com.tibco.cep.metric.condition.ast.filterParser.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.regex.Matcher;

import org.antlr.runtime.tree.Tree;

import com.tibco.be.jdbcstore.impl.DBConceptMap;
import com.tibco.be.jdbcstore.impl.DBEntityMap;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.studio.core.adapters.ConceptAdapter;

public class Visitor {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(Visitor.class);

    private static final String ESCAPE_CHAR = "@";

    private static final String SYSTEM_PROPERTY_ID = "id";
    private static final String SYSTEM_PROPERTY_EXTID = "extId";
    private static final String SYSTEM_PROPERTY_TIME_LAST_MODIFIED = "time_last_modified";
    private boolean isDrilldown = false;
    private int bindIndex = 0;
    private List bindValueList = new ArrayList();
    private List<Integer> bindTypeList = new ArrayList<Integer>();
    // This map is for debugging and should be removed later
    private Map<Integer, Object> bindIndexValueMap = new HashMap<Integer, Object>();

    protected Visitor(List valueList, List<Integer> typeList) {
        bindValueList = valueList;
        bindTypeList = typeList;
    }

    protected final void visit(final Tree tree, StringBuffer sql, Map<String, Object> bindValues,
            DBConceptMap entityMap) throws Exception {
        int tokenType = tree.getType();
        switch (tokenType) {
        case PARENTHESES_EXPRESSION:
            sql.append("(");
            visitSelectorNode(tree, sql, bindValues, entityMap);
            sql.append(")");
            break;
        case TOK_SELECTOR:
            visitSelectorNode(tree, sql, bindValues, entityMap);
            break;
        case TOK_NOT:
            visitNotNode(tree, sql, bindValues, entityMap);
            break;
        case TOK_LIKE:
            visitLikeNode(tree, sql, bindValues, entityMap, true);
            break;
        case TOK_NOTLIKE:
//			visitNotLikeNode(tree, sql, bindValues, entityMap);
            visitLikeNode(tree, sql, bindValues, entityMap, false);
            break;
        case TOK_NOTBETWEEN:
        case TOK_OR:
            visitOrNode(tree, sql, bindValues, entityMap);
            break;
        case TOK_NOR:
            // FIX THIS - What is this??
            visitNorNode(tree, sql, bindValues, entityMap);
            break;
        case TOK_IN:
            visitInNode(tree, sql, bindValues, entityMap);
            break;
        case UNARY_MINUS:
            visitUnaryOpNode(tree, sql, bindValues, entityMap);
            break;
        case TOK_ABS:
            // FIX THIS - What is this??
            visitUnaryOpNode(tree, sql, bindValues, entityMap);
            break;
        case TOK_GT:
        case TOK_GE:
        case TOK_LT:
        case TOK_LE:
        case TOK_NE:
        case TOK_EQ:
        case TOK_IS:
        case TOK_ISNOT:
        case TOK_PLUS:
        case TOK_MINUS:
        case TOK_STAR:
        case TOK_SLASH:
        case TOK_MOD:
        case TOK_CONCAT:
        case TOK_BETWEEN:
        case TOK_AND:
            visitBinaryOpNode(tree, sql, bindValues, entityMap);
            break;
        case BIND_VARIABLE_EXPRESSION:
        case PROPERTY:
        case IDENTIFIER:
        case TOK_NULL:
        case TOK_TRUE:
        case TOK_FALSE:
        case DecimalLiteral:
        case HexLiteral:
        case OctalLiteral:
        case StringLiteral:
        case DateLiteral:
        case TimeLiteral:
        case DateTimeLiteral:
        case DIGITS:
        case TOK_APPROXIMATE_NUMERIC_LITERAL:
        case TOK_EXACT_NUMERIC_LITERAL:
            visitIdentifierAndLiteralNode(tree, sql, bindValues, entityMap);
            break;
        default:
            throw new Exception(
                    "AST is either invalid or uses an unsupported op");
        }
    }

    // Not being called right now
    // in gets translated into 'or' operation
    private void visitInNode(final Tree tree, StringBuffer sql, Map<String, Object> bindValues,
            DBConceptMap entityMap) throws Exception {
        sql.append(" in (");
        visit(tree.getChild(0), sql, bindValues, entityMap);
        visit(tree.getChild(1), sql, bindValues, entityMap);
        sql.append(") ");
    }

    //FIX THIS - This is not correct right now.
    //Needs tobe translated into !a and !b and !c
    private void visitNorNode(final Tree tree, StringBuffer sql, Map<String, Object> bindValues,
            DBConceptMap entityMap) throws Exception {
        for (int i = 0; i < tree.getChildCount(); i++) {
            Tree node = tree.getChild(i);
            if (i > 0) {
                sql.append(" and ");
            }
            sql.append("not (");
            visit(node, sql, bindValues, entityMap);
            sql.append(")");
        }
    }

    private void visitOrNode(final Tree tree, StringBuffer sql, Map<String, Object> bindValues,
            DBConceptMap entityMap) throws Exception {
        int i = 0;
        for (; i < tree.getChildCount(); i++) {
            if (i == 0) {
                sql.append("(");
            }
            Tree node = tree.getChild(i);
            if (i > 0) {
                sql.append(" or ");
            }
            visit(node, sql, bindValues, entityMap);
        }
        if (i > 0) {
            sql.append(")");
        }
    }

    /*
    private void visitNotLikeNode(final Tree tree, StringBuffer sql, Map<String, Object> bindValues,
            DBConceptMap entityMap) throws Exception {
        visitLikeNode(tree, sql, bindValues, entityMap);
        Object result = stack.pop();
        if (Ops.checkBoolean(result)) {
            if (result.equals(true)) {
                stack.push(new Boolean(false));
            } else {
                stack.push(new Boolean(true));
            }
        } else {
            stack.push(result);
        }
    }
    */

    private void visitLikeNode(final Tree tree, StringBuffer sql, Map<String, Object> bindValues,
            DBConceptMap entityMap, boolean evalToTrue) throws Exception {
        visit(tree.getChild(0), sql, bindValues, entityMap);

        if (evalToTrue) {
            sql.append(" like ");
        }
        else {
            sql.append(" not like ");
        }
        //remember the current bind index
        int currBindIndex = bindIndex;
        visit(tree.getChild(1), sql, bindValues, entityMap);
        if ((bindIndex - currBindIndex) > 1) {
            throw new Exception("Invalid "+ (evalToTrue == true ? "" : "not") + " like usage");
        }
        String value = (String) bindValueList.remove(currBindIndex);
        boolean startsWith = value.startsWith("%");
        boolean endsWith = value.endsWith("%");
        if (startsWith == true) {
            value = value.substring(1);
        }
        if (endsWith == true) {
            value = value.substring(0, value.length() - 1);
        }
        if (value.contains("%")  == true || value.contains("_")  == true) {
            //escape the escape character in the value
            value = value.replaceAll(ESCAPE_CHAR, ESCAPE_CHAR+ESCAPE_CHAR);
            //escape %
            value = value.replaceAll("%", ESCAPE_CHAR+"%");
            //escape _
            value = value.replaceAll("_", ESCAPE_CHAR+"_");
            //add the escape hint to the SQL
            sql.append(" escape '");
            sql.append(ESCAPE_CHAR);
            sql.append("'");

        }
        if (startsWith == true) {
            value = "%"+value;
        }
        if (endsWith == true) {
            value = value + "%";
        }
        bindValueList.add(currBindIndex, value);
    }

    private void visitNotNode(final Tree tree, StringBuffer sql, Map<String, Object> bindValues,
            DBConceptMap entityMap) throws Exception {
        visit(tree.getChild(0), sql, bindValues, entityMap);
    }

    private void visitSelectorNode(final Tree tree, StringBuffer sql, Map<String, Object> bindValues,
            DBConceptMap entityMap) throws Exception {
        Tree child = tree.getChild(0);
        visit(child, sql, bindValues, entityMap);
    }

    private void visitBinaryOpNode(final Tree tree, StringBuffer sql, Map<String, Object> bindValues,
            DBConceptMap entityMap) throws Exception {
        int tokenType = tree.getType();

        visit(tree.getChild(0), sql, bindValues, entityMap);
        switch (tokenType) {
        case TOK_GT:
            sql.append(" > ");
            break;
        case TOK_GE:
            sql.append(" >= ");
            break;
        case TOK_LT:
            sql.append(" < ");
            break;
        case TOK_LE:
            sql.append(" <= ");
            break;
        case TOK_NE:
            sql.append(" != ");
            break;
        case TOK_IS:
            sql.append(" is ");
            break;
        case TOK_ISNOT:
            sql.append(" is not ");
            break;
        case TOK_EQ:
            sql.append(" = ");
            break;
        case TOK_PLUS:
            sql.append(" + ");
            break;
        case TOK_CONCAT:
            sql.append(" concat ");
            break;
        case TOK_BETWEEN:
        case TOK_AND:
            sql.append(" and ");
            break;
        case TOK_MINUS:
            sql.append(" - ");
            break;
        case TOK_STAR:
            sql.append(" * ");
            break;
        case TOK_SLASH:
            sql.append(" / ");
            break;
        case TOK_MOD:
            sql.append(" mod ");
            break;
        default:
            throw new Exception("[" + tokenType + "] is not a valid binary op");
        }
        visit(tree.getChild(1), sql, bindValues, entityMap);
    }

    @SuppressWarnings("unchecked")
    private void visitUnaryOpNode(final Tree tree, StringBuffer sql, Map<String, Object> bindValues,
            DBConceptMap entityMap) throws Exception {

        //remember the current bind index
        int currBindIndex = bindIndex;
        visit(tree.getChild(0), sql, bindValues, entityMap);

        int tokenType = tree.getType();
        switch (tokenType) {
        case UNARY_MINUS:
            if ((bindIndex - currBindIndex) > 1) {
                throw new Exception("Invalid unary op usage");
            }
            switch (bindTypeList.get(currBindIndex)) {
                case DBEntityMap.FTYPE_INTEGER:
                    bindValueList.add(currBindIndex, (Integer)bindValueList.remove(currBindIndex)*-1);
                    break;
                case DBEntityMap.FTYPE_LONG:
                    bindValueList.add(currBindIndex, (Long)bindValueList.remove(currBindIndex)*-1);
                    break;
                case DBEntityMap.FTYPE_DOUBLE:
                    bindValueList.add(currBindIndex, (Double)bindValueList.remove(currBindIndex)*-1);
                    break;
            }
            break;
        case TOK_ABS:
            if ((bindIndex - currBindIndex) > 1) {
                throw new Exception("Invalid unary op usage");
            }
            switch (bindTypeList.get(currBindIndex)) {
                case DBEntityMap.FTYPE_INTEGER:
                    bindValueList.add(currBindIndex, Math.abs((Integer)bindValueList.remove(currBindIndex)));
                    break;
                case DBEntityMap.FTYPE_LONG:
                    bindValueList.add(currBindIndex, Math.abs((Long)bindValueList.remove(currBindIndex)));
                    break;
                case DBEntityMap.FTYPE_DOUBLE:
                    bindValueList.add(currBindIndex, Math.abs((Double)bindValueList.remove(currBindIndex)));
                    break;
            }
            break;
        default:
            throw new Exception("[" + tokenType + "] is not a valid unary op");
        }
    }

    private void visitIdentifierAndLiteralNode(final Tree tree,
            StringBuffer sql, Map<String, Object> bindValues, DBConceptMap entityMap) throws Exception {

        int tokenType = tree.getType();
        String text = tree.getText();
        Object result = null;
        java.sql.Timestamp ts = null;

        switch (tokenType) {
        case BIND_VARIABLE_EXPRESSION:
            result = bindValues.get(text.substring(1));
            break;
        case PROPERTY:
            if (text != null && text.substring(1).equalsIgnoreCase(SYSTEM_PROPERTY_ID)) {
                if (isDrilldown) {
                    //sql.append("T.PID$");
                    sql.append("T.");
                    sql.append(ConceptAdapter.METRIC_DVM_PARENT_ID_NAME.toUpperCase());
                }
                else {
                    sql.append("T.ID$");
                }
            } else if (text != null && text.substring(1).equalsIgnoreCase(SYSTEM_PROPERTY_EXTID)) {
                sql.append("T.EXTID$");
            } else if (text != null && text.substring(1).equalsIgnoreCase(SYSTEM_PROPERTY_TIME_LAST_MODIFIED)) {
                sql.append("T.TIME_LAST_MODIFIED$");
            } else {
                logger.log(Level.INFO, "Unknown property field : " + text);
                sql.append("T." + text);
            }
            break;
        case IDENTIFIER:
            //result = sql.getPropertyValue(text);
            DBEntityMap.DBFieldMap fMap = null;
            if (entityMap != null) {
                fMap = entityMap.getFieldMap(text);
            }
            if (fMap == null) {
                logger.log(Level.INFO, "Cannot find mapping for " + text);
                sql.append("T." + text);
            }
            else {
                sql.append("T.");
                sql.append(fMap.tableFieldName);
            }
            break;
        case StringLiteral:
            //sql.append(text.replace('"', '\''));;
            sql.append("?");
            text = text.substring(1, text.length() - 1);
            result = text.replaceAll(Matcher.quoteReplacement("\\\\"), Matcher.quoteReplacement("\\"));
            bindIndexValueMap.put(bindIndex, result);
            bindValueList.add(result);
            bindTypeList.add(DBEntityMap.FTYPE_STRING);
            bindIndex++;
            break;
            /*
        case TOK_NULL:
            result = null;
            sql.append(" null ");
            break;
            */
        case TOK_TRUE:
            //sql.append(" true ");
            sql.append("?");
            bindIndexValueMap.put(bindIndex, new Integer(1));
            bindValueList.add(new Integer(1));
            bindTypeList.add(DBEntityMap.FTYPE_BOOLEAN);
            bindIndex++;
            break;
        case TOK_FALSE:
            sql.append("?");
            //sql.append("{" + bindIndex + "}");
            bindIndexValueMap.put(bindIndex, new Integer(0));
            bindValueList.add(new Integer(0));
            bindTypeList.add(DBEntityMap.FTYPE_BOOLEAN);
            bindIndex++;
            break;
        case DIGITS:
        case HexLiteral:
        case OctalLiteral:
            result = Long.valueOf(text);
            sql.append("?");
            bindIndexValueMap.put(bindIndex, result);
            bindValueList.add(result);
            bindTypeList.add(DBEntityMap.FTYPE_LONG);
            bindIndex++;
            break;
        case TOK_EXACT_NUMERIC_LITERAL:
        case TOK_APPROXIMATE_NUMERIC_LITERAL:
        case DecimalLiteral:
            sql.append("?");
            result = Double.valueOf(text);
            bindIndexValueMap.put(bindIndex, result);
            bindValueList.add(result);
            bindTypeList.add(DBEntityMap.FTYPE_DOUBLE);
            bindIndex++;
            break;
            /*
        case StringLiteral:
        case CharLiteral:
            result = text.substring(1, text.length() - 1);
            break;
            */
        case DateLiteral:
            sql.append("?");
            ts = convertToTimestamp(text.substring(1, text.length() - 1), "yyyy-MM-dd");
            bindIndexValueMap.put(bindIndex, ts);
            bindValueList.add(ts);
            bindTypeList.add(DBEntityMap.FTYPE_TIMESTAMP);
            bindIndex++;
            break;
        case TimeLiteral:
            sql.append("?");
            ts = convertToTimestamp(text.substring(1, text.length() - 1), "HH:mm:ss.SSSZ");
            bindIndexValueMap.put(bindIndex, ts);
            bindValueList.add(ts);
            bindTypeList.add(DBEntityMap.FTYPE_TIMESTAMP);
            bindIndex++;
            break;
        case DateTimeLiteral:
            sql.append("?");
            ts = convertToTimestamp(text.substring(1, text.length() - 1), "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            bindIndexValueMap.put(bindIndex, ts);
            bindValueList.add(ts);
            bindTypeList.add(DBEntityMap.FTYPE_TIMESTAMP);
            bindIndex++;
            break;
        case TOK_NULL :
            sql.append(text);
            break;
        default:
            logger.log(Level.INFO, "Invalid literal or identifier : " + text);
            /*
        default:
            result = false;
            //sql.append(text);
            sql.append("{" + bindIndex + "}");
            result = text.substring(1, text.length() - 1);
            bindIndexValueMap.put(bindIndex, result);
            bindIndex++;
            */
        }
    }

    private java.sql.Timestamp convertToTimestamp(String value, String format) throws Exception {
        SimpleDateFormat timeFormat = new SimpleDateFormat(format);
        Date result = timeFormat.parse(value);
        java.sql.Timestamp ts = null;
        if (result != null) {
            ts = new java.sql.Timestamp(((Date) result).getTime());
        }
        else {
            logger.log(Level.INFO, "Invalid datetime value : " + value);
            ts = new java.sql.Timestamp(0);
        }
        return ts;
    }

    protected void setDrilldown(boolean isDrilldown) {
        this.isDrilldown = isDrilldown;
    }

    protected int getBindIndex() {
        return bindIndex;
    }

    protected Map getBindIndexValueMap() {
        return bindIndexValueMap;
    }

    protected List getBindValueList() {
        return bindValueList;
    }

    protected List<Integer> getBindTypeList() {
        return bindTypeList;
    }
}
