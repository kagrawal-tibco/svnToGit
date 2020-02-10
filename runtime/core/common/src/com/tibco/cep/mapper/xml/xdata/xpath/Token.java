package com.tibco.cep.mapper.xml.xdata.xpath;

/**
 * An XPath token
 */
public class Token
{
    public static final int TYPE_LPAREN = 1; // '('
    public static final int TYPE_RPAREN = 2; // ')'
    public static final int TYPE_LBRACKET = 3; // '['
    public static final int TYPE_RBRACKET = 4; // ']'
    public static final int TYPE_LITERAL_STRING = 5; // "some string"
    public static final int TYPE_LITERAL_STRING_SINGLE_QUOTE = 6; // 'some string'
    public static final int TYPE_ATTRIBUTE = 7; // '@'
    public static final int TYPE_LOCATION_STEP = 8; // '::'
    public static final int TYPE_SLASH = 9; // '/'
    public static final int TYPE_NAME_TEST = 10; // * or qname or...
    public static final int TYPE_MULTIPLY = 11; // *
    /**
     * Special one, indicates a token error, the value is the error message.
     */
    public static final int TYPE_ERROR = 12; // special, for illegal token.
    public static final int TYPE_SELF = 13; // '.'
    public static final int TYPE_NUMBER = 14;
    public static final int TYPE_VARIABLE_REFERENCE = 15;
    public static final int TYPE_EOF = 16;
    public static final int TYPE_OR = 17;
    public static final int TYPE_AND = 18;
    public static final int TYPE_SLASH_SLASH = 19;
    public static final int TYPE_MOD = 20;
    public static final int TYPE_DIV = 21;
    public static final int TYPE_EQUALS = 22;
    public static final int TYPE_NOT_EQUALS = 23;
    public static final int TYPE_GREATER_THAN_EQUALS = 24;
    public static final int TYPE_LESS_THAN_EQUALS = 25;
    public static final int TYPE_GREATER_THAN = 26;
    public static final int TYPE_LESS_THAN = 27;
    public static final int TYPE_UNION = 28;
    public static final int TYPE_PLUS = 29;
    public static final int TYPE_MINUS = 30;
    public static final int TYPE_FUNCTION_NAME = 31;
    public static final int TYPE_PARENT = 32; // '..'
    public static final int TYPE_AMPERSAND = 33; // '&'

    public static final int TYPE_CHILD_AXIS = 50;
    public static final int TYPE_ANCESTOR_AXIS = 51;
    public static final int TYPE_DESCENDANT_AXIS = 52;
    public static final int TYPE_PARENT_AXIS = 53;
    public static final int TYPE_FOLLOWING_SIBLING_AXIS = 54;
    public static final int TYPE_PRECEDING_SIBLING_AXIS = 55;
    public static final int TYPE_FOLLOWING_AXIS = 56;
    public static final int TYPE_PRECEDING_AXIS = 57;
    public static final int TYPE_ATTRIBUTE_AXIS = 58; // attribute::
    public static final int TYPE_NAMESPACE_AXIS = 59;
    public static final int TYPE_SELF_AXIS = 60;
    public static final int TYPE_DESCENDANT_OR_SELF_AXIS = 61;
    public static final int TYPE_ANCESTOR_OR_SELF_AXIS = 62;

    public static final int TYPE_COMMENT_NODE_TYPE = 70;
    public static final int TYPE_TEXT_NODE_TYPE = 71;
    public static final int TYPE_PROCESSING_INSTRUCTION_NODE_TYPE = 72;
    public static final int TYPE_NODE_NODE_TYPE = 73;

    public static final int TYPE_COMMA = 75; // ','

    // XPath 2.0 specific:
    public static final int TYPE_FOR = 76; // for (does not include $)
    public static final int TYPE_IN = 77;
    public static final int TYPE_RETURN = 78;
    public static final int TYPE_IF = 79;
    public static final int TYPE_THEN = 80;
    public static final int TYPE_ELSE = 81;
    public static final int TYPE_TO = 82;

    public static final int TYPE_COMMENT = 90; // XPath 2.0

    public static final int TYPE_QUESTION_MARK = 90;

    // misc extra:
    public static final int TYPE_USER_COMMENT = 101; // (not real xpath, added)
    public static final int TYPE_MARKER_STRING = 102; // (not real xpath, added, used for marking 'to-be-filled-in' sections, delimited by '<< >>')
    public static final int TYPE_CODE_COMPLETE = 103; // (not real xpath, added, used for marking code complete block)

    public static final int TYPE_AVT_FRAGMENT = 151; // For AttributeValueTemplates, represents 'text' there.

    private final int mType;
    private final String mValue;
    private final TextRange mTextRange;
    private final String mTrailingWhitespace;
    private final String mLiteral;


    public Token(int type, String trailingWhitespace, TextRange range)
    {
        this(type,trailingWhitespace,range,null,null);
    }

    public Token(int type, String trailingWhitespace, TextRange range, String value)
    {
        this(type,trailingWhitespace,range,value,null);
    }

    public Token(int type, String trailingWhitespace, TextRange range, String value, String literalText)
    {
        mType = type;
        mValue = value;
        mTextRange = range;
        mTrailingWhitespace = trailingWhitespace;
        mLiteral = literalText;
    }

    public int getType() {
        return mType;
    }

    /**
     * Gets the constant value associated with this token, if any (NOTE: for errors, this will be the error message).<br>
     * For unterminated literal, use {@link #getLiteralText} get retrieve original text.
     */
    public String getValue() {
        return mValue;
    }

    public String getLiteralText()
    {
        return mLiteral;
    }

    public String getTrailingWhitespace() {
        return mTrailingWhitespace;
    }

    public TextRange getTextRange() {
        return mTextRange;
    }

    public boolean isAxis() {
        switch (mType) {
            case TYPE_ANCESTOR_AXIS:
            case TYPE_ANCESTOR_OR_SELF_AXIS:
            case TYPE_ATTRIBUTE_AXIS:
            case TYPE_CHILD_AXIS:
            case TYPE_DESCENDANT_AXIS:
            case TYPE_PARENT_AXIS:
            case TYPE_FOLLOWING_SIBLING_AXIS:
            case TYPE_PRECEDING_SIBLING_AXIS:
            case TYPE_FOLLOWING_AXIS:
            case TYPE_PRECEDING_AXIS:
            case TYPE_NAMESPACE_AXIS:
            case TYPE_SELF_AXIS:
            case TYPE_DESCENDANT_OR_SELF_AXIS:
                return true;
        }
        return false;
    }

    public boolean isNodeType() {
        switch (mType) {
            case TYPE_COMMENT_NODE_TYPE:
            case TYPE_TEXT_NODE_TYPE:
            case TYPE_PROCESSING_INSTRUCTION_NODE_TYPE:
            case TYPE_NODE_NODE_TYPE:
                return true;
        }
        return false;
    }

    public boolean isLiteralConstant() {
        switch (mType) {
            case TYPE_LITERAL_STRING:
            case TYPE_LITERAL_STRING_SINGLE_QUOTE:
            case TYPE_NUMBER:
                return true;
        }
        return false;
    }

    public boolean isOperator() {
        switch (mType) {
            case TYPE_OR:
            case TYPE_AND:
            case TYPE_DIV:
            case TYPE_MOD:
            case TYPE_MULTIPLY:
            case TYPE_SLASH:
            case TYPE_SLASH_SLASH:
            case TYPE_UNION:
            case TYPE_AMPERSAND:
            case TYPE_PLUS:
            case TYPE_MINUS:
            case TYPE_EQUALS:
            case TYPE_NOT_EQUALS:
            case TYPE_LESS_THAN:
            case TYPE_LESS_THAN_EQUALS:
            case TYPE_GREATER_THAN:
            case TYPE_GREATER_THAN_EQUALS:

            //xpath 2.0
            case TYPE_IN:
            case TYPE_FOR:
            case TYPE_RETURN:
            case TYPE_IF:
            case TYPE_THEN:
            case TYPE_ELSE:
            case TYPE_TO:
                return true;
        }
        return false;
    }

    public static String getTokenTypeString(int tt) {
        switch (tt) {
            // Axis:
            case TYPE_ANCESTOR_AXIS: return "ancestor::";
            case TYPE_ANCESTOR_OR_SELF_AXIS: return "ancestor-or-self::";
            case TYPE_ATTRIBUTE_AXIS: return "attribute::";
            case TYPE_CHILD_AXIS: return "child::";
            case TYPE_DESCENDANT_AXIS: return "descendant::";
            case TYPE_DESCENDANT_OR_SELF_AXIS: return "descendant-or-self::";
            case TYPE_FOLLOWING_AXIS: return "following::";
            case TYPE_FOLLOWING_SIBLING_AXIS: return "following-sibling::";
            case TYPE_NAMESPACE_AXIS: return "namespace::";
            case TYPE_PARENT_AXIS: return "parent::";
            case TYPE_PRECEDING_AXIS: return "preceding::";
            case TYPE_PRECEDING_SIBLING_AXIS: return "preceding-sibling::";
            case TYPE_SELF_AXIS: return "self::";

            // operators:
            case TYPE_MULTIPLY: return "op-*"; // to be clear.
            case TYPE_OR: return "op-or";
            case TYPE_AND: return "op-and";
            case TYPE_DIV: return "op-div";
            case TYPE_MOD: return "op-mod";
            case TYPE_EQUALS: return "=";
            case TYPE_NOT_EQUALS: return "!=";
            case TYPE_GREATER_THAN: return ">";
            case TYPE_LESS_THAN: return "<";
            case TYPE_GREATER_THAN_EQUALS: return ">=";
            case TYPE_LESS_THAN_EQUALS: return "<=";
            case TYPE_MINUS: return "-";
            case TYPE_PLUS: return "+";
            case TYPE_UNION: return "|";
            case TYPE_AMPERSAND: return "&";

            // name/node tests:
            case TYPE_NAME_TEST: return "<name test>";
            case TYPE_COMMENT_NODE_TYPE: return "comment";
            case TYPE_NODE_NODE_TYPE: return "node";
            case TYPE_TEXT_NODE_TYPE: return "text";
            case TYPE_PROCESSING_INSTRUCTION_NODE_TYPE: return "processing-instruction";

            case TYPE_LITERAL_STRING: return "<literal>";
            case TYPE_LITERAL_STRING_SINGLE_QUOTE: return "<literal-single-quote>";
            case TYPE_ERROR: return "<error>";
            case TYPE_NUMBER: return "<number>";
            case TYPE_LPAREN: return "(";
            case TYPE_RPAREN: return ")";
            case TYPE_LBRACKET: return "[";
            case TYPE_RBRACKET: return "]";
            case TYPE_SLASH: return "/";
            case TYPE_SLASH_SLASH: return "//";
            case TYPE_ATTRIBUTE: return "@";
            case TYPE_SELF: return ".";
            case TYPE_PARENT: return "..";
            case TYPE_COMMA: return ",";
            case TYPE_FUNCTION_NAME: return "<function-name>";
            case TYPE_VARIABLE_REFERENCE: return "variable-ref";

            case TYPE_QUESTION_MARK: return "?";

            // xpath 2.0
            case TYPE_FOR: return "op-for";
            case TYPE_IN: return "op-in";
            case TYPE_RETURN: return "op-return";
            case TYPE_IF: return "op-if";
            case TYPE_THEN: return "op-then";
            case TYPE_ELSE: return "op-else";
            case TYPE_TO: return "op-to";

            // pseudo
            case TYPE_EOF: return "<eof>";

            // extra
            case TYPE_MARKER_STRING: return "<marker>";
            case TYPE_CODE_COMPLETE: return "<code-complete>";
        }
        return "(" + tt + ")";
    }

    /**
     * Utility function, formats a literal string by adding either " or ' to
     * the front & end. " is used unless the string contains a ".
     */
    public static String formatLiteralString(String literalStr) {
        if (literalStr.indexOf('"')!=-1) {
            return "'" + literalStr + "'";
        }
        return '"' + literalStr + '"';
    }

    /**
     * Utility function, formats a literal string by adding either " or ' to
     * the front & end. ' is used unless the string contains a '.
     */
    public static String formatLiteralStringSingleQuoteDefault(String literalStr) {
        if (literalStr.indexOf('\'')!=-1) {
            return "\"" + literalStr + "\"";
        }
        return '\'' + literalStr + '\'';
    }

    /**
     * Gets the exact string, excluding trailing whitespace.
     * @return The exact string.
     */
    public String toExactString()
    {
        switch (mType)
        {
            case TYPE_ERROR:
                {
                    if (mLiteral==null)
                    {
                        return mValue;// shouldn't happen, but just in case.
                    }
                    return mLiteral;
                }
            case TYPE_FOR: return "for";
            case TYPE_IN: return "in";
            case TYPE_RETURN: return "return";
            case TYPE_IF: return "if";
            case TYPE_THEN: return "then";
            case TYPE_ELSE: return "else";
            case TYPE_TO: return "to";
        }
        return toString();
    }

    /**
     * Formats as a string, for debugging / diagnostics, use {@link #toExactString} for getting exact string.
     */
    public String toString() {
        switch (mType) {
            case TYPE_NAME_TEST:
                return mValue;
            case TYPE_AVT_FRAGMENT:
                return mValue;
            case TYPE_LITERAL_STRING: // double quote.
                return formatLiteralString(getValue());
            case TYPE_LITERAL_STRING_SINGLE_QUOTE:
                return formatLiteralStringSingleQuoteDefault(getValue());
            case TYPE_ERROR:
                return "error:'" + mValue + "'";
            case TYPE_NUMBER:
                return mValue;
            case TYPE_VARIABLE_REFERENCE:
                return "$" + mValue;
            case TYPE_FUNCTION_NAME:
                return mValue;
            case TYPE_MARKER_STRING:
                return "<<" + mValue + ">>";
            case TYPE_CODE_COMPLETE:
                return mValue + "%%";
        }
        return getTokenTypeString(mType);
    }
}
