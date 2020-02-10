package com.tibco.cep.mapper.xml.xdata.xpath;

import java.util.ArrayList;

/**
 * Converts unicode character input into an array of XPath Tokens.
 * Errors are indicated by adding tokens with getType()==Token.TYPE_ERROR,
 * the text of the error message is in Token.getValue()
 */
public final class Lexer
{
    private final CharStream m_charStream; // the input stream
    private final ArrayList mOutputTokens = new ArrayList(16); // the output tokens.
    private final boolean mPreviousLineIndicatesOperator;
    private final boolean mXQueryTypeMode;

    public static final String UNTERMINATED_LITERAL_ERROR = "Unterminated literal";
    public static final String UNTERMINATED_COMMENT_ERROR = "Unterminated comment";

    private Lexer(CharStream cs, boolean previousLineIndicatesOperator, boolean xqueryTypeMode) {
        m_charStream = cs;
        mPreviousLineIndicatesOperator = previousLineIndicatesOperator;
        mXQueryTypeMode = xqueryTypeMode;
    }

    /**
     * Lexes but adds a special code-complete token which is interpreted later.
     */
    public static Token[] lexCodeComplete(String inputString) {
        return lex(new CharStream(inputString+"%%"));//@@ for now.
    }

    public static Token[] lex(String inputString) {
        return lex(new CharStream(inputString));
    }

    public static Token[] lex(CharStream stream) {
        return lex(stream,false);
    }

    public static Token[] lex(CharStream stream, boolean previousLineIndicatesOperator) {
        Lexer l = new Lexer(stream,previousLineIndicatesOperator,false);
        l.run();
        return (Token[]) l.mOutputTokens.toArray(new Token[l.mOutputTokens.size()]);
    }

    public static Token[] lex(CharStream stream, boolean previousLineIndicatesOperator, boolean xqueryTypeMode) {
        Lexer l = new Lexer(stream,previousLineIndicatesOperator,xqueryTypeMode);
        l.run();
        return (Token[]) l.mOutputTokens.toArray(new Token[l.mOutputTokens.size()]);
    }

    private void run() {
        while (!m_charStream.isEOF()) {
            Token t = readExprToken();
            if (t!=null) { // whitespace is skipped
                mOutputTokens.add(t);
            }
        }
    }

    /**
     * Used for disambiguation rule #1, mainly for '*' matching versus '*' multiply.
     */
    private boolean isPreceedingTokenIndicatingOperator() {
        // exists & one of: @, ::, (, [, or an OPERATOR
        Token prevTok = lastToken();
        if (prevTok==null) {
            // no token before, see if what previous line said (if no previous line, can't expect an operator, so this
            // will be false)
            return mPreviousLineIndicatesOperator;
        }
        if (mXQueryTypeMode) {
            return false;
        }
        return isTokenIndicatingNextOperator(prevTok);
    }

    public static boolean isTokenIndicatingNextOperator(Token prevTok) {
        // one of @, ::, (, [
        int type = prevTok.getType();
        switch (type) {
            case Token.TYPE_ATTRIBUTE: // (not attribute axis)
            case Token.TYPE_LOCATION_STEP:
            case Token.TYPE_LPAREN:
            case Token.TYPE_LBRACKET:
            case Token.TYPE_COMMA:
                // no way this is an operator following one of these:
                return false;
        }
        if (prevTok.isAxis()) {
            // i.e. '::'
            // after an axis we can't have an operator.
            return false;
        }
        // ... or an operator
        if (prevTok.isOperator()) {
            // don't have two operators in a row, so false
            return false;
        }
        if (type==Token.TYPE_ERROR) {
            // helps reduce # of errors in a row.
            return false;
        }
        if (type==Token.TYPE_COMMENT) {
            return false;
        }

        // otherwise it IS indicating an operator:
        return true;
    }

    private Token lastToken() {
        if (mOutputTokens.size()>0) {
            return (Token) mOutputTokens.get(mOutputTokens.size()-1);
        }
        return null;
    }

    private Token readExprToken() {
        // '(' | ')' | '[' | ']' | '.' | '..' | '@' | ',' | '::'
        char n = m_charStream.peek();
        switch (n) {
            case '(': return buildOp(Token.TYPE_LPAREN);
            case ')': return buildOp(Token.TYPE_RPAREN);
            case '[': return buildOp(Token.TYPE_LBRACKET);
            case ']': return buildOp(Token.TYPE_RBRACKET);
            case '-': return buildOp(Token.TYPE_MINUS);
            case '+': return buildOp(Token.TYPE_PLUS);
            case ',': return buildOp(Token.TYPE_COMMA);
            case '#': return buildComment(); // not official xpath
            case '@': return buildOp(Token.TYPE_ATTRIBUTE);
            case '*': return buildStar();
            case '=': return buildOp(Token.TYPE_EQUALS);
            case '?': return buildOp(Token.TYPE_QUESTION_MARK);
            case '>': return buildGreaterThan();
            case '<': return buildLessThan();
            case '!': return buildNotEquals();
            case '{': return buildLBracket();
            case '\"': case '\'': return readLiteralString();
            case '0':case '1':case '2':case '3':case '4':case '5': case '6': case '7':case '8':case '9':
                return readNumber();
            case '.': return buildDot();
            case '$': return readVariable();
            // operators:
            case '/': return buildSlash();
            case ' ': case '\r': case '\n': case '\t': return skipWhitespace();
            case '|': return buildOp(Token.TYPE_UNION);
            case '&': return buildOp(Token.TYPE_AMPERSAND);
            case ':': return buildError("Unexpected :"); // build error here, otherwise, too confusing.
        }
        // parse as qname:

        int startingPos = m_charStream.getPosition();
        String name = readQName();
        // Check for code complete immediately after the prefix:
        if (m_charStream.peek()==':' && m_charStream.peek2()=='%' && m_charStream.peek3()=='%') {
            m_charStream.pop();
            m_charStream.pop();
            m_charStream.pop();
            TextRange codeCompleteRange = new TextRange(startingPos,m_charStream.getPosition());
            return new Token(Token.TYPE_CODE_COMPLETE,getTrailingWhitespace(),codeCompleteRange, name + ":");
        }
        // Check for normal code complete:
        if (m_charStream.peek()=='%' && m_charStream.peek2()=='%') { // special, code complete marker
            m_charStream.pop();
            m_charStream.pop();
            TextRange codeCompleteRange = new TextRange(startingPos,m_charStream.getPosition());
            return new Token(Token.TYPE_CODE_COMPLETE,getTrailingWhitespace(),codeCompleteRange, name);
        }
        if (name.length()==0) {
            // some sort of error, unrecognized token:
            m_charStream.pop();
            TextRange errRange = new TextRange(startingPos,m_charStream.getPosition());
            return new Token(Token.TYPE_ERROR,getTrailingWhitespace(),errRange,"Unrecognized token");
        }
        if (isPreceedingTokenIndicatingOperator()) {
            return readOperatorName(name,startingPos);
        }
        int endingPos = m_charStream.getPosition();
        String whitespace = getTrailingWhitespace();
        if (m_charStream.peek()=='(') {
            // If looks like a function or a node-test so this check needs to be done beforehand:
            if (name.equals("if")) {
                TextRange range = new TextRange(startingPos,endingPos);
                return new Token(Token.TYPE_IF,whitespace,range);
            }

            // this was a function or a node test:
            return readFunctionOrNodeType(name,whitespace,startingPos,endingPos);
        }
        if (m_charStream.peek()==':' && m_charStream.peek2()==':') {
            return readAxisName(name,startingPos,whitespace);
        }
        if (m_charStream.peek()==':' && m_charStream.peek2()=='*' && name.indexOf(':')==-1) {
            // production NCName ':' '*'
            m_charStream.pop();
            m_charStream.pop();
            TextRange range = new TextRange(startingPos,m_charStream.getPosition());
            return new Token(Token.TYPE_NAME_TEST,getTrailingWhitespace(),range,name + ":*");
        }
        if (m_charStream.peek()=='$' && name.equals("for")) {
            TextRange range = new TextRange(startingPos,m_charStream.getPosition());
            return new Token(Token.TYPE_FOR,whitespace,range);
        }
        TextRange range = new TextRange(startingPos,endingPos);
        return new Token(Token.TYPE_NAME_TEST,whitespace,range,name);
    }

    // assumes 1 character error.
    private Token buildError(String msg) {
        int startingPos = m_charStream.getPosition();
        m_charStream.pop();
        TextRange range = new TextRange(startingPos,startingPos+1);
        return new Token(Token.TYPE_ERROR,getTrailingWhitespace(),range,msg);
    }

    private Token buildGreaterThan() {
        if (m_charStream.peek2()!='=') {
            return buildOp(Token.TYPE_GREATER_THAN);
        }
        int startPos = m_charStream.getPosition();
        m_charStream.pop();
        m_charStream.pop();
        return new Token(Token.TYPE_GREATER_THAN_EQUALS,getTrailingWhitespace(),new TextRange(startPos,startPos+2));
    }

    private Token buildLessThan() {
        // Allow '<< marker >>' to be recognized as a special marker token:
        if (m_charStream.peek2()=='<') {
            return readMarkerString();
        }
        if (m_charStream.peek2()!='=') {
            return buildOp(Token.TYPE_LESS_THAN);
        }
        int startPos = m_charStream.getPosition();
        m_charStream.pop();
        m_charStream.pop();
        return new Token(Token.TYPE_LESS_THAN_EQUALS,getTrailingWhitespace(),new TextRange(startPos,startPos+2));
    }

    private Token buildStar() {
        if (isPreceedingTokenIndicatingOperator()) {
            return buildOp(Token.TYPE_MULTIPLY);
        }
        int startingPos = m_charStream.getPosition();
        m_charStream.pop();
        TextRange range = new TextRange(startingPos,m_charStream.getPosition());
        return new Token(Token.TYPE_NAME_TEST,getTrailingWhitespace(),range,"*");
    }

    private Token readFunctionOrNodeType(String name, String whitespace, int startPos, int endPos) {
        TextRange range = new TextRange(startPos,endPos);
        if (name.equals("comment")) {
            return new Token(Token.TYPE_COMMENT_NODE_TYPE,whitespace,range);
        }
        if (name.equals("text")) {
            return new Token(Token.TYPE_TEXT_NODE_TYPE,whitespace,range);
        }
        if (name.equals("processing-instruction")) {
            return new Token(Token.TYPE_PROCESSING_INSTRUCTION_NODE_TYPE,whitespace,range);
        }
        if (name.equals("node")) {
            return new Token(Token.TYPE_NODE_NODE_TYPE,whitespace,range);
        }
        // o.w. must be a function:
        return new Token(Token.TYPE_FUNCTION_NAME,whitespace,range,name);
    }

    private Token readAxisName(String name, int startPos, String whitespace) {
        m_charStream.pop(); // ':'
        m_charStream.pop(); // ':'
        int endPos = m_charStream.getPosition();
        TextRange range = new TextRange(startPos,endPos);
        // for this case, the 'value' is treated as the whitespace before the '::', hacky, yes.
        if (name.equals("ancestor")) return new Token(Token.TYPE_ANCESTOR_AXIS,getTrailingWhitespace(),range,whitespace);
        if (name.equals("ancestor-or-self")) return new Token(Token.TYPE_ANCESTOR_OR_SELF_AXIS,getTrailingWhitespace(),range,whitespace);
        if (name.equals("attribute")) return new Token(Token.TYPE_ATTRIBUTE_AXIS,getTrailingWhitespace(),range,whitespace);
        if (name.equals("child")) return new Token(Token.TYPE_CHILD_AXIS,getTrailingWhitespace(),range,whitespace);
        if (name.equals("descendant")) return new Token(Token.TYPE_DESCENDANT_AXIS,getTrailingWhitespace(),range,whitespace);
        if (name.equals("descendant-or-self")) return new Token(Token.TYPE_DESCENDANT_OR_SELF_AXIS,getTrailingWhitespace(),range,whitespace);
        if (name.equals("following")) return new Token(Token.TYPE_FOLLOWING_AXIS,getTrailingWhitespace(),range,whitespace);
        if (name.equals("following-sibling")) return new Token(Token.TYPE_FOLLOWING_SIBLING_AXIS,getTrailingWhitespace(),range,whitespace);
        if (name.equals("namespace")) return new Token(Token.TYPE_NAMESPACE_AXIS,getTrailingWhitespace(),range,whitespace);
        if (name.equals("preceding")) return new Token(Token.TYPE_PRECEDING_AXIS,getTrailingWhitespace(),range,whitespace);
        if (name.equals("preceding-sibling")) return new Token(Token.TYPE_PRECEDING_SIBLING_AXIS,getTrailingWhitespace(),range,whitespace);
        if (name.equals("parent")) return new Token(Token.TYPE_PARENT_AXIS,getTrailingWhitespace(),range,whitespace);
        if (name.equals("self")) return new Token(Token.TYPE_SELF_AXIS,getTrailingWhitespace(),range,whitespace);
        return new Token(Token.TYPE_ERROR,getTrailingWhitespace(),range,"Expected Axis");
    }

    private Token readOperatorName(String name, int startPos) {
        int endPos = m_charStream.getPosition();
        TextRange range = new TextRange(startPos,endPos);
        if (name.equals("or")) return new Token(Token.TYPE_OR,getTrailingWhitespace(),range);
        if (name.equals("and")) return new Token(Token.TYPE_AND,getTrailingWhitespace(),range);
        if (name.equals("mod")) return new Token(Token.TYPE_MOD,getTrailingWhitespace(),range);
        if (name.equals("div")) return new Token(Token.TYPE_DIV,getTrailingWhitespace(),range);
        if (name.equals("in")) return new Token(Token.TYPE_IN,getTrailingWhitespace(),range);
        if (name.equals("return")) return new Token(Token.TYPE_RETURN,getTrailingWhitespace(),range);
        if (name.equals("then")) return new Token(Token.TYPE_THEN,getTrailingWhitespace(),range);
       if (name.equals("else")) return new Token(Token.TYPE_ELSE,getTrailingWhitespace(),range);
       if (name.equals("to")) {
          return new Token(Token.TYPE_TO,getTrailingWhitespace(),range);
       }
        return new Token(Token.TYPE_ERROR,getTrailingWhitespace(),range,"Expected operator",name);
    }

    // handles '.' and all of its cases:
    private Token buildDot() {
        char nc = m_charStream.peek2();
        if (Character.isDigit(nc)) {
            return readNumber();
        }
        Token ret;
        int startPos = m_charStream.getPosition();
        if (nc=='.') {
            m_charStream.pop();
            m_charStream.pop();
            ret = new Token(Token.TYPE_PARENT,getTrailingWhitespace(),new TextRange(startPos,startPos+2));
        } else {
            m_charStream.pop();
            ret = new Token(Token.TYPE_SELF,getTrailingWhitespace(),new TextRange(startPos,startPos+1));
        }
        return ret;
    }

    private Token buildSlash() {
        int pos = m_charStream.getPosition();
        int nc = m_charStream.peek2();
        m_charStream.pop();
        if (nc=='/') {
            m_charStream.pop();
            return new Token(Token.TYPE_SLASH_SLASH,getTrailingWhitespace(),new TextRange(pos,pos+2));
        }
        return new Token(Token.TYPE_SLASH,getTrailingWhitespace(),new TextRange(pos,pos+1));
    }

    private Token buildNotEquals() {
        // assumes we've already hit '!'
        int next = m_charStream.peek2();
        int startPos = m_charStream.getPosition();
        if (next=='=') {
            Token ret = new Token(Token.TYPE_NOT_EQUALS,getTrailingWhitespace(),new TextRange(startPos,startPos+2));
            m_charStream.pop();
            m_charStream.pop();
            return ret;
        } else {
            m_charStream.pop();
        }
        // error.
        return new Token(Token.TYPE_ERROR,getTrailingWhitespace(),new TextRange(startPos,startPos+1),"Got ! without =");
    }

    /**
     * @see <a href="http://www.w3.org/TR/REC-xml-names/#NT-NCName"/>
     */
    private String readQName() {
        String name = readNCName();
        if (name.length()==0) return name; // not a name
        if (m_charStream.peek()==':' && startsNCName(m_charStream.peek2())) {
            m_charStream.pop();
            String ncname = readNCName();
            return name + ":" + ncname;
        }
        return name;
    }

    private boolean startsNCName(char c) {
        if (c==0) return false;
        return Character.isLetter(c) || c=='_';
    }

    private String readNCName() {
        char first = m_charStream.peek();
        if (!startsNCName(first)) return "";
        StringBuffer sb = new StringBuffer(16);
        // get first part:
        sb.append(first);
        m_charStream.pop();
        // add rest:
        for (;;) {
            char c = m_charStream.peek();
            if (c==0) break;
            if (Character.isLetterOrDigit(c) || c=='_' || c=='-' || c=='.') {
                sb.append(c);
                m_charStream.pop();
            } else {
                break;
            }
        }
        return sb.toString();
    }

    private Token readLiteralString() {
        int startPos = m_charStream.getPosition();
        char first = m_charStream.peek(); // either ' or "
        m_charStream.pop();
        StringBuffer sb = new StringBuffer(16);
        for (;;) {
            if (m_charStream.isEOF()) { // can't use c==0, because otherwise strings can't contain /u0000!
                TextRange range = new TextRange(startPos,m_charStream.getPosition());
                return new Token(Token.TYPE_ERROR,getTrailingWhitespace(),range,UNTERMINATED_LITERAL_ERROR,first + sb.toString());
            }
            char c = m_charStream.peek();
            m_charStream.pop();
            if (c==first) { // matching either ' or "
                break;
            }
            sb.append(c);
        }
        int endPos = m_charStream.getPosition();
        int type;
        if (first=='\'') {
            type = Token.TYPE_LITERAL_STRING_SINGLE_QUOTE;
        } else {
            type = Token.TYPE_LITERAL_STRING;
        }
        return new Token(type,getTrailingWhitespace(),new TextRange(startPos,endPos),sb.toString());
    }

    private Token readMarkerString() {
        int startPos = m_charStream.getPosition();
        m_charStream.pop();// '<'
        m_charStream.pop();// '<'
        StringBuffer sb = new StringBuffer(16);
        for (;;) {
            char c = m_charStream.peek();
            if (c=='>' && m_charStream.peek2()=='>') { // matching either ' or "
                m_charStream.pop();
                m_charStream.pop();
                break;
            }
            if (c==0) {
                // allow unterminated, too.
                break;
            }
            m_charStream.pop();
            sb.append(c);
        }
        int endPos = m_charStream.getPosition();
        return new Token(Token.TYPE_MARKER_STRING,getTrailingWhitespace(),new TextRange(startPos,endPos),sb.toString());
    }

    private String getTrailingWhitespace() {
        StringBuffer sb = null;
        for (;;) {
            char c = m_charStream.peek();
            if (c!=0 && Character.isWhitespace(c)) {
                if (sb==null) {
                    sb = new StringBuffer();
                }
                sb.append(c);
                m_charStream.pop();
            } else {
                if (c==0) {
                    // strip trailing whitespace.
                    return "";
                }
                break;
            }
        }
        if (sb==null) {
            return "";
        }
        return sb.toString();
    }

    private Token buildLBracket() {
        int startingPos = m_charStream.getPosition();
        if (m_charStream.peek2()=='-' && m_charStream.peek3()=='-') {
            // check for comments.
            String cmt = skipComment();
            if (!cmt.endsWith("--}")) { // unterminated
                TextRange range = new TextRange(startingPos,m_charStream.getPosition());
                return new Token(Token.TYPE_ERROR,getTrailingWhitespace(),range,UNTERMINATED_COMMENT_ERROR);
            }
            TextRange range = new TextRange(startingPos,m_charStream.getPosition());
            return new Token(Token.TYPE_COMMENT,getTrailingWhitespace(),range,cmt);
        }
        m_charStream.pop();
        TextRange errRange = new TextRange(startingPos,m_charStream.getPosition());
        return new Token(Token.TYPE_ERROR,getTrailingWhitespace(),errRange,"Unrecognized token");
    }

    private String skipComment() {
        StringBuffer ret = new StringBuffer();
        ret.append("{--");
        m_charStream.pop();
        m_charStream.pop();
        m_charStream.pop();
        while (m_charStream.peek()!='-' || m_charStream.peek2()!='-' || m_charStream.peek3()!='}') {
            if (m_charStream.peek()==0) {
                return ret.toString();
            }
            ret.append(m_charStream.peek());
            m_charStream.pop();
        }
        ret.append("--}");
        m_charStream.pop();
        m_charStream.pop();
        m_charStream.pop();
        return ret.toString();
    }

    private Token skipWhitespace() {
        for (;;) {
            char c = m_charStream.peek();
            if (c!=0 && Character.isWhitespace(c)) {
                m_charStream.pop();
            } else {
                break;
            }
        }
        return null;
    }

    // Reads a number.
    private Token readNumber() {
        int startPos = m_charStream.getPosition();
        StringBuffer sb = new StringBuffer(16);
        boolean hasDot = false;

        // The support for 'e' and 'E' is a partial implementation of XPath 2.0's support for this.
        boolean hasE = false;

        boolean hasMinusE = false;
        for (;;) {
            char c = m_charStream.peek();
            if (c!=0 && (Character.isDigit(c) || c=='.' || c=='e' || c=='E' || c=='-' || c=='+')) {
                if (c=='.') {
                    if (hasDot) {
                        break;
                    }
                    hasDot = true;
                }
                if (c=='e' || c=='E') {
                    if (hasE) {
                        break;
                    }
                    hasE = true;
                }
                if (c=='-' || c=='+') {
                    if (hasE && !hasMinusE) {
                        hasMinusE = true;
                    } else {
                        break;
                    }
                }
                sb.append(c);
                m_charStream.pop();
            } else {
                break;
            }
        }
        char last = sb.charAt(sb.length()-1);
        int endPos = m_charStream.getPosition();
        if (last=='e' || last=='E' || last=='-' || last=='+') {
            return new Token(Token.TYPE_ERROR,getTrailingWhitespace(),new TextRange(startPos,endPos),"Invalid number");
        }
        return new Token(Token.TYPE_NUMBER,getTrailingWhitespace(),new TextRange(startPos,endPos),sb.toString());
    }

    private Token readVariable() {
        int startPos = m_charStream.getPosition();
        m_charStream.pop(); // '$'
        String name = readQName();
        int endPos = m_charStream.getPosition();
        TextRange range = new TextRange(startPos,endPos);
        if (m_charStream.peek()=='%' && m_charStream.peek2()=='%') { // special, code complete marker
            m_charStream.pop();
            m_charStream.pop();
            TextRange codeCompleteRange = new TextRange(startPos,m_charStream.getPosition());
            return new Token(Token.TYPE_CODE_COMPLETE,getTrailingWhitespace(),codeCompleteRange, "$" + name);
        }
        if (name.length()==0) {
            return new Token(Token.TYPE_ERROR,getTrailingWhitespace(),range,"Invalid variable name");
        }
        return new Token(Token.TYPE_VARIABLE_REFERENCE,getTrailingWhitespace(),range,name);
    }

    // helper function:
    private Token buildOp(int tokenType) {
        int startPos = m_charStream.getPosition();
        TextRange range = new TextRange(startPos,startPos+1);
        m_charStream.pop();
        String ws = getTrailingWhitespace();
        Token ret = new Token(tokenType,ws,range);
        return ret;
    }

    private Token buildComment() {
        // all the way to the end:
        int startPos = m_charStream.getPosition();
        m_charStream.pop(); // pop #
        StringBuffer sb = new StringBuffer();
        while (!m_charStream.isEOF()) {
            char c = m_charStream.peek();
            m_charStream.pop();
            sb.append(c);
        }
        int endPos = m_charStream.getPosition();
        return new Token(Token.TYPE_USER_COMMENT,getTrailingWhitespace(),new TextRange(startPos,endPos),sb.toString());
    }
}
