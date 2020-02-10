package com.tibco.be.parser;

import static com.tibco.be.parser.RuleGrammarConstants.ARRAY_ACCESS;
import static com.tibco.be.parser.RuleGrammarConstants.ASSIGN;
import static com.tibco.be.parser.RuleGrammarConstants.AT;
import static com.tibco.be.parser.RuleGrammarConstants.BEGIN_ATTRIBUTES;
import static com.tibco.be.parser.RuleGrammarConstants.BEGIN_COMMENT_END;
import static com.tibco.be.parser.RuleGrammarConstants.BEGIN_COMMENT_START;
import static com.tibco.be.parser.RuleGrammarConstants.BEGIN_DISPLAY_KEYWORDS;
import static com.tibco.be.parser.RuleGrammarConstants.BEGIN_JAVAWORDS;
import static com.tibco.be.parser.RuleGrammarConstants.BEGIN_KEYWORDS;
import static com.tibco.be.parser.RuleGrammarConstants.BEGIN_LITERALS;
import static com.tibco.be.parser.RuleGrammarConstants.BEGIN_OPERATORS;
import static com.tibco.be.parser.RuleGrammarConstants.BEGIN_RESERVED_WORDS;
import static com.tibco.be.parser.RuleGrammarConstants.BEGIN_SEPARATORS;
import static com.tibco.be.parser.RuleGrammarConstants.BEGIN_WHITESPACE;
import static com.tibco.be.parser.RuleGrammarConstants.BLOCK_STATEMENT;
import static com.tibco.be.parser.RuleGrammarConstants.DOT;
import static com.tibco.be.parser.RuleGrammarConstants.END_ATTRIBUTES;
import static com.tibco.be.parser.RuleGrammarConstants.END_COMMENT_END;
import static com.tibco.be.parser.RuleGrammarConstants.END_COMMENT_START;
import static com.tibco.be.parser.RuleGrammarConstants.END_DISPLAY_KEYWORDS;
import static com.tibco.be.parser.RuleGrammarConstants.END_JAVAWORDS;
import static com.tibco.be.parser.RuleGrammarConstants.END_KEYWORDS;
import static com.tibco.be.parser.RuleGrammarConstants.END_LITERALS;
import static com.tibco.be.parser.RuleGrammarConstants.END_OPERATORS;
import static com.tibco.be.parser.RuleGrammarConstants.END_RESERVED_WORDS;
import static com.tibco.be.parser.RuleGrammarConstants.END_SEPARATORS;
import static com.tibco.be.parser.RuleGrammarConstants.END_WHITESPACE;
import static com.tibco.be.parser.RuleGrammarConstants.EQ;
import static com.tibco.be.parser.RuleGrammarConstants.ERROR;
import static com.tibco.be.parser.RuleGrammarConstants.GE;
import static com.tibco.be.parser.RuleGrammarConstants.GT;
import static com.tibco.be.parser.RuleGrammarConstants.IDENTIFIER;
import static com.tibco.be.parser.RuleGrammarConstants.LE;
import static com.tibco.be.parser.RuleGrammarConstants.LINE_STATEMENT;
import static com.tibco.be.parser.RuleGrammarConstants.LT;
import static com.tibco.be.parser.RuleGrammarConstants.MINUS;
import static com.tibco.be.parser.RuleGrammarConstants.MINUSEQ;
import static com.tibco.be.parser.RuleGrammarConstants.NE;
import static com.tibco.be.parser.RuleGrammarConstants.PCNT;
import static com.tibco.be.parser.RuleGrammarConstants.PCNTEQ;
import static com.tibco.be.parser.RuleGrammarConstants.PLUS;
import static com.tibco.be.parser.RuleGrammarConstants.PLUSEQ;
import static com.tibco.be.parser.RuleGrammarConstants.SC_AND;
import static com.tibco.be.parser.RuleGrammarConstants.SC_OR;
import static com.tibco.be.parser.RuleGrammarConstants.SLASH;
import static com.tibco.be.parser.RuleGrammarConstants.SLASHEQ;
import static com.tibco.be.parser.RuleGrammarConstants.STAR;
import static com.tibco.be.parser.RuleGrammarConstants.STAREQ;
import static com.tibco.be.parser.RuleGrammarConstants.XSLT_LITERAL;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jul 8, 2004
 * Time: 5:48:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class TokenUtils {
    public static final boolean isReservedWord(Token t) {
        return t.kind > BEGIN_RESERVED_WORDS && t.kind < END_RESERVED_WORDS;
    }
    
    public static final boolean isKeyword(Token t) {
        return t.kind > BEGIN_KEYWORDS && t.kind < END_KEYWORDS;
    }
    
    //returns true if the token represents a keyword that is allowed to appear in the rule editor (ex. "true", "null")
    public static final boolean isDisplayKeyword(Token t) {
        return t.kind > BEGIN_DISPLAY_KEYWORDS && t.kind < END_DISPLAY_KEYWORDS;
    }
    
    public static final boolean isJavaReservedWord(Token t) {
        return t.kind > BEGIN_JAVAWORDS && t.kind < END_JAVAWORDS;
    }
    
    public static final boolean isAttributeKeyword(Token t) {
        return t.kind > BEGIN_ATTRIBUTES && t.kind < END_ATTRIBUTES;
    }
    
    public static final boolean isLiteral(Token t) {
        return t.kind > BEGIN_LITERALS && t.kind < END_LITERALS;
    }
    
    public static final boolean isIdentifier(Token t) {
        return t.kind == IDENTIFIER;
    }
    
    public static final boolean isSeparator(Token t) {
        return t.kind > BEGIN_SEPARATORS && t.kind < END_SEPARATORS;
    }
    
    public static final boolean isOperator(Token t) {
        return t.kind > BEGIN_OPERATORS && t.kind < END_OPERATORS;
    }
    
    public static final boolean isWhitespace(Token t) {
        return t.kind > BEGIN_WHITESPACE && t.kind < END_WHITESPACE;
    }
    
    public static final boolean isCommentStart(Token t) {
        return t.kind > BEGIN_COMMENT_START && t.kind < END_COMMENT_START;
    }
    
    public static final boolean isCommentEnd(Token t) {
        return t.kind > BEGIN_COMMENT_END && t.kind < END_COMMENT_END;
    }
    
    public static final boolean isErrorToken(Token t) {
        return t.kind == ERROR;
    }
    
    public static final boolean isXSLTLiteral(Token t) {
        return t.kind == XSLT_LITERAL;
    }
    
    public static final boolean isAssignOp(Token op) {
        return op.kind == ASSIGN;
    }

    public static final boolean isBinaryArithmeticOp(Token op) {
        int kind = op.kind;
        return (
                kind == PLUS || kind == MINUS
                || kind == STAR || kind == SLASH
                || kind == PCNT
                );
    }

    public static final boolean isCompoundAssignment(Token op) {
        int kind = op.kind;
        return (
                kind == PLUSEQ || kind == MINUSEQ
                        || kind == STAREQ || kind == SLASHEQ
                        || kind == PCNTEQ
        );
    }    

    public static final boolean isBinaryLogicalOp(Token op) {
        return (op.kind == SC_AND || op.kind == SC_OR);
    }

    public static final boolean isEqualityOp(Token op) {
        return (op.kind == EQ || op.kind == NE);
    }

    public static final boolean isComparisonOp(Token op) {
        int kind = op.kind;
        return (
                kind == GE || kind == GT
                || kind == LE || kind == LT
                || kind == EQ || kind == NE
                );
    }
    
    public static final boolean isPropertyAccess(Token op) {
        return op.kind == DOT;
    }
    
    public static final boolean isAttributeAccess(Token op) {
        return op.kind == AT;
    }
    
    public static final boolean isArrayAccess(Token op) {
        return op.kind == ARRAY_ACCESS;
    }
    
    public static final boolean isStatement(Token op) {
        return (op.kind == LINE_STATEMENT) || (op.kind == BLOCK_STATEMENT);
    }
}
