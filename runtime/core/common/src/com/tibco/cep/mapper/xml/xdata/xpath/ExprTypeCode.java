package com.tibco.cep.mapper.xml.xdata.xpath;

/**
 * The codes for XPath expression components.<br>
 * @see Expr
 */
public final class ExprTypeCode
{
    private ExprTypeCode() {
        // not for instantiation.
    }

    public static final int EXPR_ADD = 10;
    public static final int EXPR_SUBTRACT = 11;
    public static final int EXPR_MULTIPLY = 12;
    public static final int EXPR_DIVIDE = 13;
    public static final int EXPR_MOD = 14;
    public static final int EXPR_NEGATIVE = 15;

    public static final int EXPR_EQUALS = 20;
    public static final int EXPR_NOT_EQUALS = 21;
    public static final int EXPR_GREATER_THAN = 22;
    public static final int EXPR_LESS_THAN = 23;
    public static final int EXPR_GREATER_THAN_EQUALS = 24;
    public static final int EXPR_LESS_THAN_EQUALS = 25;

    public static final int EXPR_OR = 30;
    public static final int EXPR_AND = 31;
    public static final int EXPR_UNION = 32;
    public static final int EXPR_PAREN = 33; // Always has 1 argument
    public static final int EXPR_SEQUENCE = 34; // Has 2 arguments.
    public static final int EXPR_EMPTY_SEQUENCE = 35; // Has 0 arguments.

    public static final int EXPR_ANCESTOR_AXIS = 50;
    public static final int EXPR_ANCESTOR_OR_SELF_AXIS = 51;
    public static final int EXPR_ATTRIBUTE_AXIS = 52;
    public static final int EXPR_CHILD_AXIS = 53;
    public static final int EXPR_DESCENDANT_AXIS = 54;
    public static final int EXPR_DESCENDANT_OR_SELF_AXIS = 55;
    public static final int EXPR_FOLLOWING_AXIS = 56;
    public static final int EXPR_FOLLOWING_SIBLING_AXIS = 57;
    public static final int EXPR_NAMESPACE_AXIS = 58;
    public static final int EXPR_PARENT_AXIS = 59;
    public static final int EXPR_PRECEDING_AXIS = 60;
    public static final int EXPR_PRECEDING_SIBLING_AXIS = 61;
    public static final int EXPR_SELF_AXIS = 62;

    public static final int EXPR_NAME_TEST = 100;
    public static final int EXPR_NODE_NODE_TEST = 101;
    public static final int EXPR_PI_NODE_TEST = 102;
    public static final int EXPR_COMMENT_NODE_TEST = 103;
    public static final int EXPR_TEXT_NODE_TEST = 104;

    public static final int EXPR_FUNCTION_CALL = 200;
    public static final int EXPR_ABSOLUTE = 201; // absolute, i.e. /
    public static final int EXPR_STEP = 202; // i.e. child::a
    public static final int EXPR_LOCATION_PATH = 203; // i.e. a/b
    public static final int EXPR_VARIABLE_REFERENCE = 204; // i.e. $a
    public static final int EXPR_PREDICATE = 205; // i.e. a/b

    public static final int EXPR_LITERAL_STRING = 220;
    public static final int EXPR_NUMBER = 221;

    public static final int EXPR_FOR = 222;
    public static final int EXPR_IF = 223;

    public static final int EXPR_RANGE = 230;
   
    public static final int EXPR_COMMENT = 300; // xpath 2.0 comment.

    public static final int EXPR_ERROR = 400; // error.
    public static final int EXPR_MARKER = 500; // bogus expr, for marker text (<< this >>)
    public static final int EXPR_CODE_COMPLETE = 600; // bogus expr, for code completion.
    public static final int EXPR_CODE_COMPLETE_NAME_TEST = 601; // bogus expr, for code completion.
}
