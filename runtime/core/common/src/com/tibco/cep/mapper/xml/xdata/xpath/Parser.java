package com.tibco.cep.mapper.xml.xdata.xpath;

import java.util.ArrayList;

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
import com.tibco.xml.schema.helpers.NoNamespace;

/**
 * Parses XPath tokens into Expr.
 */
public final class Parser
{
    private final TokenStream mTokenStream;
    private final Expr mExpr;
    private ErrorMessageList mErrorMessageList = ErrorMessageList.EMPTY_LIST;

    private boolean m_alreadyHadUnexpected; // hacky, but prevents unexpected error pile-out; when refactored to only use error markers, this won't be required.

    /**
     * Shortcut, helper fn.
     */
    public static Expr parse(String xpath) {
        return new Parser(Lexer.lex(xpath)).getExpression();
    }

    public static Expr parse(CharStream xpath) {
        return new Parser(Lexer.lex(xpath)).getExpression();
    }

    public static ExprAndErrors parseWithErrors(String xpath)
    {
        Parser p = new Parser(Lexer.lex(xpath));
        return new ExprAndErrors(p.getExpression(),p.getErrorMessageList());
    }

    public Parser(TokenStream tokenStream) {
        mTokenStream = tokenStream;
        Expr r = parseExpr();
        if (mTokenStream.peek().getType()!=Token.TYPE_EOF) {
            Token firstUnexpectedToken = mTokenStream.peek();
            StringBuffer exact = new StringBuffer();
            exact.append(r.toExactString());
            while (mTokenStream.peek().getType()!=Token.TYPE_EOF)
            {
                Token p = mTokenStream.peek();
                exact.append(p.toExactString());
                exact.append(p.getTrailingWhitespace());
                mTokenStream.pop();
            }
            //WCETODO Unify error handling to ONLY use marker expressions (and then use a simple traverser to find them)
            handleUnexpectedError(firstUnexpectedToken);

            //WCETODO make error have children; preserve initial 'ok' part (i.e. r)
            String msg = ResourceBundleManager.getMessage(MessageCode.LEX_UNEXPECTED);
            r = new ErrorExpr(getCurrentRange(firstUnexpectedToken),msg,exact.toString(),"");
        }
        mExpr = r;
    }

    public Parser(Token[] tokens) {
        this(new TokenStream(tokens));
    }

    public Expr getExpression() {
        return mExpr;
    }

    public ErrorMessageList getErrorMessageList() {
        return mErrorMessageList;
    }

    /**
    [14]    Expr    ::=    OrExpr
[15]    PrimaryExpr    ::=    VariableReference
   | '(' Expr ')'
   | Literal
   | Number
   | FunctionCall

3.2 Function Calls     */
    private Expr parseExpr() {
        return parseSequenceExpr();
    }

    /**
     * Parses ExprSingle (, ExprSingle*) from XP2.0.
     */
    private Expr parseSequenceExpr() {
        Token t = mTokenStream.peek();
        Expr r = parseSingleExpr();
        Token tok2 = mTokenStream.peek();
        if (tok2.getType()==Token.TYPE_COMMA) {
            String ws = tok2.getTrailingWhitespace();
            mTokenStream.pop();
            Expr s = parseSequenceExpr();
            TextRange range = getCurrentRange(t);
            return new SequenceExpr(r,s,range,ws);
        }
        return r;
    }

    private Expr parseSingleExpr()
    {
        Token tok = mTokenStream.peek();
        if (tok.getType()==Token.TYPE_COMMENT) {
            mTokenStream.pop();
            Expr e = parseSingleExpr();
            TextRange range = getCurrentRange(tok);
            return new CommentExpr(tok.getValue(),e,true,range,tok.getTrailingWhitespace());
        }
        Expr r = parseOrExpr();
        Token tok2 = mTokenStream.peek();
        if (tok2.getType()==Token.TYPE_COMMENT) {
            mTokenStream.pop();
            TextRange range = getCurrentRange(tok2);
            return new CommentExpr(tok2.getValue(),r,false,range,tok2.getTrailingWhitespace());
        }
        return r;
    }

    private Expr optionallyParsePrimaryExpr() {
        Token tok = mTokenStream.peek();
        switch (tok.getType()) {
            case Token.TYPE_VARIABLE_REFERENCE:
                mTokenStream.pop();
                TextRange range = getCurrentRange(tok);
                ExpandedName exName = ExpandedName.makeName(NoNamespace.URI, tok.getValue());
                return new VariableReferenceExpr(exName,range,"",tok.getTrailingWhitespace());
            case Token.TYPE_LPAREN:{
                mTokenStream.pop();
                // Check for empty sequence case, too.
                Token t2 = mTokenStream.peek();
                if (t2.getType()==Token.TYPE_RPAREN)
                {
                    mTokenStream.pop();
                    return new EmptySequenceExpr(getCurrentRange(t2),tok.getTrailingWhitespace());
                }
                Expr body = parseExpr();
                Token rparen = mTokenStream.peek();
                if (rparen.getType()!=Token.TYPE_RPAREN) {
                    handleExpectedError(")",rparen.getTextRange());
                    String r = "(" + tok.getTrailingWhitespace() + body.toExactString();
                    return new ErrorExpr(getCurrentRange(tok),"unexpected",r,"");
                    // all errors after the 1st one are suspect...
                }
                mTokenStream.pop();
                return new ParenExpr(body,getCurrentRange(tok),tok.getTrailingWhitespace(),rparen.getTrailingWhitespace());
            }
            case Token.TYPE_LITERAL_STRING:
                mTokenStream.pop();
                return new LiteralStringExpr(tok.getValue(),getCurrentRange(tok),tok.getTrailingWhitespace(),false);
            case Token.TYPE_LITERAL_STRING_SINGLE_QUOTE:
                mTokenStream.pop();
                return new LiteralStringExpr(tok.getValue(),getCurrentRange(tok),tok.getTrailingWhitespace(),true);
            case Token.TYPE_NUMBER:
                mTokenStream.pop();
                // Check to see if we have a range expression, e.g. "3 to 5".
                Expr retVal = parseRangeExpression(tok);
                if(retVal == null) {
                   // Not a range expression.  Just return a number.
                   retVal = new NumberExpr(tok.getValue(),getCurrentRange(tok),tok.getTrailingWhitespace());
                }
                return retVal;
            case Token.TYPE_FUNCTION_NAME:
                return parseFunctionCall();
        }
        // nope, not one of those:
        return null;
    }
    private Expr parseRangeExpression(Token tok1) {
       Expr retVal = null;
       Token toToken = mTokenStream.peek();
       if(toToken.getType() == Token.TYPE_TO) {
          mTokenStream.pop();
          // Now, get last number in range expression.
          Token tok2 = mTokenStream.peek();
          if(tok2.getType() == Token.TYPE_NUMBER) {
             mTokenStream.pop();

             Expr firstNumber = new NumberExpr(tok1.getValue(),getCurrentRange(tok1),tok1.getTrailingWhitespace());

             TextRange textRange = getCurrentRange(tok2);
             String trailingWhitespace = tok2.getTrailingWhitespace();
             Expr lastNumber = new NumberExpr(tok2.getValue(),getCurrentRange(tok2),tok2.getTrailingWhitespace());
             retVal = new RangeExpr(firstNumber, lastNumber, textRange, trailingWhitespace);
          }
       }
       return retVal;
    }
    private Expr parseFunctionCall() {
        Token tok = mTokenStream.pop();
        String functionName = tok.getValue();
        Token lparen = mTokenStream.pop();
        StringBuffer wsBuffer = new StringBuffer();
        wsBuffer.append(tok.getTrailingWhitespace());
        wsBuffer.append(",");
        if (lparen.getType()!=Token.TYPE_LPAREN) {
            handleExpectedError("(",lparen.getTextRange());
        }
        wsBuffer.append(lparen.getTrailingWhitespace());
        wsBuffer.append(",");
        ArrayList args = new ArrayList();
        Token noArgParen = mTokenStream.peek();
        Token rparen = null;
        if (noArgParen.getType()==Token.TYPE_RPAREN) {
            mTokenStream.pop();
            rparen = noArgParen;
        } else {
            for (;;) {
                Expr e = parseSingleExpr();
                args.add(e);
                Token n = mTokenStream.peek();
                if (n.getType()==Token.TYPE_COMMA) {
                    mTokenStream.pop();
                    wsBuffer.append(n.getTrailingWhitespace());
                    wsBuffer.append(",");
                } else {
                    if (n.getType()==Token.TYPE_RPAREN) {
                        mTokenStream.pop();
                        rparen = n;
                        break;
                    } else {
                        Token at = mTokenStream.peek();
                        mTokenStream.pop();
                        handleExpectedError(", or )",at.getTextRange());
                        rparen = at;
                        break;
                    }
                }
            }
        }
        Expr[] aargs = (Expr[]) args.toArray(new Expr[args.size()]);
        TextRange range = getCurrentRange(tok);
        String ws = wsBuffer.toString();
        FunctionCallExpr r = new FunctionCallExpr(functionName,aargs,range,ws,rparen.getTrailingWhitespace());
        Token n = mTokenStream.peek();
        if (n.getType()==Token.TYPE_CODE_COMPLETE) {
            mTokenStream.pop();
            return new LocationPathExpr(r,new CodeCompleteStepExpr(tok.getValue(),getCurrentRange(tok)),getCurrentRange(tok),"");
        }
        return r;
    }

    private Expr parseOrExpr() {
        Token startingTok = mTokenStream.peek();
        Expr expr = parseAndExpr();
        Token tok = mTokenStream.peek();
        while (tok.getType()==Token.TYPE_OR) {
            mTokenStream.pop();
            Expr expr2 = parseAndExpr();
            expr = new OrExpr(expr,expr2,getCurrentRange(startingTok),tok.getTrailingWhitespace());
            tok = mTokenStream.peek();
        }
        return expr;
    }

    private Expr parseAndExpr() {
        Token startingTok = mTokenStream.peek();
        Expr expr = parseForExpr();
        Token tok = mTokenStream.peek();
        while (tok.getType()==Token.TYPE_AND) {
            mTokenStream.pop();
            Expr expr2 = parseForExpr();
            expr = new AndExpr(expr,expr2, getCurrentRange(startingTok),tok.getTrailingWhitespace());
            tok = mTokenStream.peek();
        }
        return expr;
    }

    /**
     * This is an XPath 2.0 feature:
     */
    private Expr parseForExpr() {
        Token tok = mTokenStream.peek();
        if (tok.getType()==Token.TYPE_FOR) {
            String leadingWS = tok.getTrailingWhitespace();
            mTokenStream.pop();

            ArrayList clauses = new ArrayList();
            for (;;) {
                Token t = mTokenStream.pop();
                if (t.getType()!=Token.TYPE_VARIABLE_REFERENCE)
                {
                    handleExpectedError(ResourceBundleManager.getMessage(MessageCode.LEX_EXPECTED_VAR_NAME),t.getTextRange());
                }
                String inWS = t.getTrailingWhitespace();
                String qname = t.getValue();
                t = mTokenStream.pop();
                if (t.getType()!=Token.TYPE_IN)
                {
                    handleExpectedError("'in'",t.getTextRange()); // 'in' is Locale indep.
                }
                Expr expr = parseSingleExpr();
                clauses.add(new ForExpr.Clause(qname,expr,leadingWS,inWS,t.getTrailingWhitespace()));
                if (mTokenStream.peek().getType()!=Token.TYPE_COMMA) {
                    break;
                }
                leadingWS = mTokenStream.peek().getTrailingWhitespace();
                mTokenStream.pop();
            }
            Token t = mTokenStream.pop();
            if (t.getType()!=Token.TYPE_RETURN) {
                handleExpectedError("'return'",t.getTextRange()); // 'return' is Locale indep.
            }
            String afterReturnWS = t.getTrailingWhitespace();
            Expr returns = parseSingleExpr();
            ForExpr.Clause[] clausesa = (ForExpr.Clause[]) clauses.toArray(new ForExpr.Clause[clauses.size()]);
            return new ForExpr(clausesa,returns,afterReturnWS,tok.getTextRange());
        }
        return parseIfExpr();
    }

    private Expr parseIfExpr() {
        Token tok = mTokenStream.peek();
        if (tok.getType()==Token.TYPE_IF) {
            mTokenStream.pop();
            String lWS = tok.getTrailingWhitespace();
            Token t = mTokenStream.peek();
            if (t.getType()!=Token.TYPE_LPAREN)
            {
                handleExpectedError("(",t.getTextRange());
            }
            mTokenStream.pop();
            Expr condition = parseExpr();
            Token t2 = mTokenStream.peek();
            String rWS = t2.getTrailingWhitespace();
            if (t2.getType()!=Token.TYPE_RPAREN)
            {
                String r = "if" + lWS + "(" + condition.toExactString();
                handleExpectedError("(",t2.getTextRange());
                return new ErrorExpr(getCurrentRange(tok),ResourceBundleManager.getMessage(MessageCode.LEX_EXPECTED,")"),r,"");
            }
            mTokenStream.pop();
            Token thenToken = mTokenStream.peek();
            if (thenToken.getType()!=Token.TYPE_THEN)
            {
                String r = "if" + lWS + "(" + condition.toExactString() + ")" + rWS;
                handleExpectedError("then",thenToken.getTextRange()); // 'then' is Locale indep.
                return new ErrorExpr(getCurrentRange(tok),ResourceBundleManager.getMessage(MessageCode.LEX_EXPECTED,"then"),r,"");
            }
            String thenWS = thenToken.getTrailingWhitespace();
            mTokenStream.pop();
            Expr then = parseSingleExpr();
            Token elseToken = mTokenStream.peek();
            String elseWS = elseToken.getTrailingWhitespace();
            if (elseToken.getType()!=Token.TYPE_ELSE)
            {
                handleExpectedError("else",elseToken.getTextRange()); // 'else' is Locale indep.
                String r = "if" + lWS + "(" + condition.toExactString() + ")" + rWS + "then" + thenWS + then.toExactString();
                return new ErrorExpr(getCurrentRange(tok),ResourceBundleManager.getMessage(MessageCode.LEX_EXPECTED,"then"),r,"");
            }
            mTokenStream.pop();
            Expr elsee = parseSingleExpr();
            String wsString = lWS + "," + rWS + "," + thenWS + "," + elseWS;
            return new IfExpr(condition,then,elsee,getCurrentRange(tok),wsString);
        }
        return parseEqualityExpr();
    }

    private Expr parseEqualityExpr() {
        Token startingTok = mTokenStream.peek();
        Expr expr = parseRelationalExpr();
        Token tok = mTokenStream.peek();
        while (tok.getType()==Token.TYPE_EQUALS || tok.getType()==Token.TYPE_NOT_EQUALS) {
            boolean eq = tok.getType()== Token.TYPE_EQUALS;
            mTokenStream.pop();
            Expr expr2 = parseRelationalExpr();
            TextRange range = getCurrentRange(startingTok);
            if (eq) {
                expr = new EqualsExpr(expr,expr2,range,tok.getTrailingWhitespace());
            } else {
                expr = new NotEqualsExpr(expr,expr2,range,tok.getTrailingWhitespace());
            }
            tok = mTokenStream.peek();
        }
        return expr;
    }

    private Expr parseRelationalExpr() {
        Token startingTok = mTokenStream.peek();
        Expr expr = parseAdditiveExpr();
        Token tok = mTokenStream.peek();
        int ttype = tok.getType();
        while (ttype==Token.TYPE_GREATER_THAN || ttype==Token.TYPE_LESS_THAN ||
             ttype==Token.TYPE_GREATER_THAN_EQUALS || ttype==Token.TYPE_LESS_THAN_EQUALS)
        {
            mTokenStream.pop();
            Expr expr2 = parseAdditiveExpr();
            TextRange range = getCurrentRange(startingTok);
            String whitespace = tok.getTrailingWhitespace();
            switch (ttype) {
                case Token.TYPE_GREATER_THAN: expr = new GreaterThanExpr(expr,expr2,range,whitespace);break;
                case Token.TYPE_LESS_THAN: expr = new LessThanExpr(expr,expr2,range,whitespace);break;
                case Token.TYPE_GREATER_THAN_EQUALS: expr = new GreaterThanEqualsExpr(expr,expr2,range,whitespace);break;
                case Token.TYPE_LESS_THAN_EQUALS: expr = new LessThanEqualsExpr(expr,expr2,range,whitespace);break;
            }
            tok = mTokenStream.peek();
            ttype = tok.getType();
        }
        return expr;
    }

    private Expr parseAdditiveExpr() {
        Token startingTok = mTokenStream.peek();
        Expr expr = parseMultiplicativeExpr();
        Token tok = mTokenStream.peek();
        while (tok.getType()==Token.TYPE_PLUS || tok.getType()==Token.TYPE_MINUS) {
            boolean pl = tok.getType()== Token.TYPE_PLUS;
            mTokenStream.pop();
            Expr expr2 = parseMultiplicativeExpr();
            TextRange range = getCurrentRange(startingTok);
            if (pl) {
                expr = new AddExpr(expr,expr2,range,tok.getTrailingWhitespace());
            } else {
                expr = new SubtractExpr(expr,expr2,range,tok.getTrailingWhitespace());
            }
            tok = mTokenStream.peek();
        }
        return expr;
    }

    private Expr parseMultiplicativeExpr() {
        Token startingTok = mTokenStream.peek();
        Expr expr = parseUnaryExpr();
        Token tok = mTokenStream.peek();
        while (tok.getType()==Token.TYPE_MULTIPLY || tok.getType()==Token.TYPE_DIV ||
            tok.getType()==Token.TYPE_MOD)
        {
            mTokenStream.pop();
            Expr expr2 = parseUnaryExpr();
            TextRange range = getCurrentRange(startingTok);
            String whitespace = tok.getTrailingWhitespace();
            switch (tok.getType()) {
                case Token.TYPE_MULTIPLY: expr = new MultiplyExpr(expr,expr2,range,whitespace); break;
                case Token.TYPE_DIV: expr = new DivideExpr(expr,expr2,range,whitespace); break;
                case Token.TYPE_MOD: expr = new ModExpr(expr,expr2,range,whitespace); break;
            }
            tok = mTokenStream.peek();
        }
        return expr;
    }

    private Expr parseUnaryExpr() {
        Token tok = mTokenStream.peek();
        if (tok.getType()==Token.TYPE_MINUS) {
            mTokenStream.pop();
            Expr expr = parseUnaryExpr(); // this is dubious... why allow two - in a row?
            // it's in the spec, though.
            return new NegativeExpr(expr,getCurrentRange(tok),tok.getTrailingWhitespace());
        }
        return parseUnionExpr();
    }

    private Expr parseUnionExpr() {
        Token startingTok = mTokenStream.peek();
        Expr expr = parsePathExpr();
        Token tok = mTokenStream.peek();
        while (tok.getType()==Token.TYPE_UNION) {
            mTokenStream.pop();
            Expr expr2 = parsePathExpr();
            TextRange range = getCurrentRange(startingTok);
            expr = new UnionExpr(expr,expr2,range,tok.getTrailingWhitespace());
            tok = mTokenStream.peek();
        }
        return expr;
    }

    private Expr parsePathExpr() {
        // See if it's a filter expr (equivalent to checking if is a primary):
        Token tok = mTokenStream.peek();
        if (tok.getType()==Token.TYPE_MARKER_STRING) {
            handleUnexpectedError(tok);
            mTokenStream.pop();
            return new MarkerExpr(tok.getValue(),tok.getTextRange(),tok.getTrailingWhitespace());
        }
        Expr primary = optionallyParsePrimaryExpr();
        if (primary!=null) {
            Expr filter = parsePredicateExpr(primary,tok);
            Token tt = mTokenStream.peek();
            if (tt.getType()==Token.TYPE_SLASH || tt.getType()==Token.TYPE_SLASH_SLASH) {
                return parseRelativeLocationPathRHS(filter,tok);
            }
            return filter;
        }
        return parseLocationPath();
    }

    private Expr parsePredicateExpr(Expr primary, Token startingTok) {
        while (mTokenStream.peek().getType()==Token.TYPE_LBRACKET) {
            Token ltok = mTokenStream.peek();
            mTokenStream.pop(); // assumed '['
            Expr pred = parseExpr();
            Token rtok = mTokenStream.peek();
            if (rtok.getType()!=Token.TYPE_RBRACKET)
            {
                handleExpectedError("]",rtok.getTextRange());
                String r = primary.toExactString() + "[" + ltok.getTrailingWhitespace() + pred.toExactString();
                primary = new PredicateExpr(primary, pred, getCurrentRange(startingTok), ltok.getTrailingWhitespace(),rtok.getTrailingWhitespace());
                return new ErrorExpr(getCurrentRange(startingTok),ResourceBundleManager.getMessage(MessageCode.LEX_UNEXPECTED), r, "", primary);
            }
            else
            {
                mTokenStream.pop();
            }
            primary = new PredicateExpr(primary, pred, getCurrentRange(startingTok), ltok.getTrailingWhitespace(),rtok.getTrailingWhitespace());
        }
        return primary;
    }

    private Expr parseLocationPath() {
        Token tok = mTokenStream.peek();
        if (tok.getType()==Token.TYPE_SLASH) {
            return parseAbsoluteLocationPath();
        }
        if (tok.getType()==Token.TYPE_SLASH_SLASH) {
            return parseAbsoluteLocationPath();
        }
        return parseRelativeLocationPath();
    }

    private Expr parseAbsoluteLocationPath() {
        Token tok = mTokenStream.peek();
        if (tok.getType()==Token.TYPE_SLASH) {
            mTokenStream.pop();
            if (isRelativeLocationPath()) {
                Expr rel = parseRelativeLocationPath();
                return new AbsoluteExpr(rel,getCurrentRange(tok),tok.getTrailingWhitespace());
            } else {
                return new AbsoluteExpr(null,getCurrentRange(tok),tok.getTrailingWhitespace());
            }
        }
        if (tok.getType()==Token.TYPE_SLASH_SLASH) {
            mTokenStream.pop();
            Expr rel = parseRelativeLocationPath();
            // Expand to descendant-or-self::node()
            TextRange range = getCurrentRange(tok);
            Expr expanded = new StepExpr(
                    new DescendantOrSelfAxisExpr(range,"",""),
                    new NodeNodeTestExpr(range,""),
                    range,
                    true, // abbreviated.
                    tok.getTrailingWhitespace()
            );
            Expr expandedRel = new LocationPathExpr(expanded,rel,getCurrentRange(tok),tok.getTrailingWhitespace());
            return new AbsoluteExpr(expandedRel,range,tok.getTrailingWhitespace());
        }
        // assumed TYPE_SLASH or TYPE_SLASH_SLASH, was an error.
        throw new RuntimeException("Internal Error: Shouldn't get here");
    }

    private Expr parseRelativeLocationPath() {
        Expr step = parseStep();
        Token stok = mTokenStream.peek();
        return parseRelativeLocationPathRHS(step,stok);
    }

    private boolean isRelativeLocationPath() {
        Token tok = mTokenStream.peek();
        if (tok.isAxis()) {
            return true;
        }
        if (tok.getType()==Token.TYPE_NAME_TEST) {
            return true;
        }
        if (tok.getType()==Token.TYPE_SELF) {
            return true;
        }
        if (tok.getType()==Token.TYPE_PARENT) {
            return true;
        }
        if (tok.getType()==Token.TYPE_PARENT) {
            return true;
        }
        if (tok.getType()==Token.TYPE_ATTRIBUTE) {
            return true;
        }
        if (tok.isNodeType()) {
            return true;
        }
        return false;
    }

    private Expr parseRelativeLocationPathRHS(Expr step, Token startingTok) {
        Token tok = mTokenStream.peek();
        while (tok.getType()==Token.TYPE_SLASH || tok.getType()==Token.TYPE_SLASH_SLASH) {
            mTokenStream.pop();
            if (tok.getType()==Token.TYPE_SLASH_SLASH) {
                TextRange range = getCurrentRange(tok);
                // expand // -> descendant-or-self::node()
                Expr expanded = new StepExpr(
                        new DescendantOrSelfAxisExpr(range,"",""),
                        new NodeNodeTestExpr(range,""),
                        range,
                        true, // abbreviated
                        tok.getTrailingWhitespace());
                step = new LocationPathExpr(step,expanded,range,tok.getTrailingWhitespace());
            }
            Expr step2 = parseStep();
            step = new LocationPathExpr(step,step2,getCurrentRange(startingTok),tok.getTrailingWhitespace());
            tok = mTokenStream.peek();
        }
        return step;
    }

    private Expr parseStep() {
        Token tok = mTokenStream.peek();
        if (tok.getType()==Token.TYPE_SELF) {
            mTokenStream.pop();
            TextRange range = getCurrentRange(tok);
            // abbreviation expansion: self::node()
            Expr left = new StepExpr(
                    new SelfAxisExpr(range,"",""),
                    new NodeNodeTestExpr(range,""),
                    range,
                    true, // abbreviated.
                    tok.getTrailingWhitespace());
            return parsePredicateExpr(left,tok);
        }
        if (tok.getType()==Token.TYPE_PARENT) {
            mTokenStream.pop();
            TextRange range = getCurrentRange(tok);
            // abbreviation expansion: parent::node()
            Expr left = new StepExpr(
                    new ParentAxisExpr(range,"",""),
                    new NodeNodeTestExpr(range,""),
                    range,
                    true, // abbreviated.
                    tok.getTrailingWhitespace());
            return parsePredicateExpr(left,tok);
        }
        AxisExpr axis;
        boolean abbreviated;
        if (tok.getType()==Token.TYPE_CODE_COMPLETE) {
            mTokenStream.pop();
            return new CodeCompleteStepExpr(tok.getValue(),getCurrentRange(tok));
        }
        if (tok.isAxis()) {
            axis = parseAxisSpecifier();
            abbreviated = false;
        } else {
            if (tok.getType()==Token.TYPE_ATTRIBUTE) {
                mTokenStream.pop();
                axis = new AttributeAxisExpr(getCurrentRange(tok), "", tok.getTrailingWhitespace());
                abbreviated = true;
            } else {
                // Handle some common errors here:
                if (tok.getType()==Token.TYPE_EOF)
                {
                    handleUnexpectedError(tok);
                    return new ErrorExpr(tok.getTextRange(), "EOF", "", "");
                }
                if (tok.getType()==Token.TYPE_RPAREN)
                {
                    handleUnexpectedError(tok);
                    return new ErrorExpr(tok.getTextRange(), "Expected argument", "", ""); // no trailing whitespace here.
                }
                // child axis is the default:
                int atPos = tok.getTextRange().getStartPosition();
                TextRange range = new TextRange(atPos,atPos);
                axis = new ChildAxisExpr(range, "", tok.getTrailingWhitespace());
                abbreviated = true;
                // (Spec's grammar isn't clear on this, but the text clearly
                // indicates this & it's obviously needed to parse most of the examples)
            }
        }
        Expr nodeTest = parseNodeTest();
        if (nodeTest instanceof ErrorExpr)
        {
            return nodeTest;
        }
        Expr left = new StepExpr(
                axis,
                (NodeTestExpr)nodeTest,
                getCurrentRange(tok),
                abbreviated,
                tok.getTrailingWhitespace());
        // not listed in the grammar as a filter expr, but the logic is the same:
        return parsePredicateExpr(left,tok);
    }

    private AxisExpr parseAxisSpecifier() {
        Token tok = mTokenStream.peek();
        mTokenStream.pop();
        TextRange range = getCurrentRange(tok);
        String trailingWhitespace = tok.getTrailingWhitespace();
        String axisWhitespace = tok.getValue(); // for axes, the value is considered the whitespace before the '::'
        switch (tok.getType()) {
            case Token.TYPE_ANCESTOR_AXIS: return new AncestorAxisExpr(range,axisWhitespace,trailingWhitespace);
            case Token.TYPE_ANCESTOR_OR_SELF_AXIS: return new AncestorOrSelfAxisExpr(range,axisWhitespace,trailingWhitespace);
            case Token.TYPE_ATTRIBUTE_AXIS: return new AttributeAxisExpr(range,axisWhitespace,trailingWhitespace);
            case Token.TYPE_CHILD_AXIS: return new ChildAxisExpr(range,axisWhitespace,trailingWhitespace);
            case Token.TYPE_DESCENDANT_AXIS: return new DescendantAxisExpr(range,axisWhitespace,trailingWhitespace);
            case Token.TYPE_DESCENDANT_OR_SELF_AXIS: return new DescendantOrSelfAxisExpr(range,axisWhitespace,trailingWhitespace);
            case Token.TYPE_FOLLOWING_AXIS: return new FollowingAxisExpr(range,axisWhitespace,trailingWhitespace);
            case Token.TYPE_FOLLOWING_SIBLING_AXIS: return new FollowingSiblingAxisExpr(range,axisWhitespace,trailingWhitespace);
            case Token.TYPE_NAMESPACE_AXIS: return new NamespaceAxisExpr(range,axisWhitespace,trailingWhitespace);
            case Token.TYPE_PRECEDING_AXIS: return new PrecedingAxisExpr(range,axisWhitespace,trailingWhitespace);
            case Token.TYPE_PRECEDING_SIBLING_AXIS: return new PrecedingSiblingAxisExpr(range,axisWhitespace,trailingWhitespace);
            case Token.TYPE_PARENT_AXIS: return new ParentAxisExpr(range,axisWhitespace,trailingWhitespace);
            case Token.TYPE_SELF_AXIS: return new SelfAxisExpr(range,axisWhitespace,trailingWhitespace);
        }
        // can only happen if missed an axis or this fn called incorrectly:
        throw new RuntimeException("Internal error: Unknown axis " + tok);
    }

    private Expr parseNodeTest() {
        Token tok = mTokenStream.peek();
        if (tok.getType()==Token.TYPE_NAME_TEST) {
            mTokenStream.pop();
            return new NameTestExpr(tok.getValue(),getCurrentRange(tok),tok.getTrailingWhitespace());
        }
        if (tok.isNodeType()) {
            return parseNodeType();
        }
        if (tok.getType()==Token.TYPE_CODE_COMPLETE) {
            mTokenStream.pop();
            return new CodeCompleteNameTestExpr(tok.getValue(),getCurrentRange(tok));
        }
        mTokenStream.pop();
        handleUnexpectedError(tok);
        // return bogus thing after error:
        return new ErrorExpr(getCurrentRange(tok),ResourceBundleManager.getMessage(MessageCode.LEX_UNEXPECTED),tok.toString(),tok.getTrailingWhitespace());
    }

    private NodeTestExpr parseNodeType() {
        Token tok = mTokenStream.peek();
        mTokenStream.pop();
        if (tok.getType()==Token.TYPE_PROCESSING_INSTRUCTION_NODE_TYPE) {
            Token lparen = mTokenStream.pop();
            if (lparen.getType()!=Token.TYPE_LPAREN) {
                handleExpectedError("(",lparen.getTextRange());
            }
            Token literal = mTokenStream.peek();
            String literalVal = null;
            if (literal.getType()==Token.TYPE_LITERAL_STRING) {
                mTokenStream.pop();
                literalVal = literal.getValue();
            }
            Token rparen = mTokenStream.pop();
            if (rparen.getType()!=Token.TYPE_RPAREN) {
                handleExpectedError(")",rparen.getTextRange());
            }
            return new PINodeTestExpr(literalVal,getCurrentRange(tok),tok.getTrailingWhitespace());
        }
        Token lparen = mTokenStream.pop();
        Token rparen = mTokenStream.pop();
        // include parens in range of these expressions:
        TextRange range = getCurrentRange(tok);
        if (lparen.getType()!=Token.TYPE_LPAREN) {
            handleExpectedError("(",lparen.getTextRange());
        }
        if (rparen.getType()!=Token.TYPE_RPAREN) {
            handleExpectedError(")",rparen.getTextRange());
        }
        switch (tok.getType()) {
            case Token.TYPE_COMMENT_NODE_TYPE: return new CommentNodeTestExpr(range,rparen.getTrailingWhitespace());
            case Token.TYPE_TEXT_NODE_TYPE: return new TextNodeTestExpr(range,tok.getTrailingWhitespace(),rparen.getTrailingWhitespace());
            case Token.TYPE_NODE_NODE_TYPE: return new NodeNodeTestExpr(range,rparen.getTrailingWhitespace());
        }
        // can't get here except by bug:
        throw new RuntimeException("Internal Error: Unknown node test");
    }

    // Utility function, given the starting token, returns the range of the text
    // up to <b>but not including</b> the current token.
    private TextRange getCurrentRange(Token from) {
        TextRange to = mTokenStream.getPreviousRange();
        return new TextRange(from.getTextRange(),to);
    }

    // Utility function, generates a uniform error for when a certain token was expected
    // but not found.
    private void handleExpectedError(String token, TextRange range)
    {
        handleError(new ErrorMessage(ResourceBundleManager.getMessage(MessageCode.LEX_EXPECTED,token),range));
    }

    // Utility function, generates a uniform error for when a certain token was unexpected
    private void handleUnexpectedError(Token token) {
        if (m_alreadyHadUnexpected)
        {
            return;
        }
        m_alreadyHadUnexpected = true;
        if (token.getType()==Token.TYPE_ERROR) {
            handleError(new ErrorMessage(token.getValue(),token.getTextRange()));
        } else {
            if (token.getType()==Token.TYPE_EOF) {
                String msg = ResourceBundleManager.getMessage(MessageCode.LEX_UNEXPECTED_END);
                handleError(new ErrorMessage(msg,token.getTextRange()));
            } else {
                if (token.getType()==Token.TYPE_MARKER_STRING)
                {
                    handleError(new ErrorMessage(ErrorMessage.TYPE_MARKER,ResourceBundleManager.getMessage(MessageCode.FORMULA_HAS_MARKERS),null,token.getTextRange()));
                }
                else
                {
                    String msg = ResourceBundleManager.getMessage(MessageCode.LEX_UNEXPECTED);
                    handleError(new ErrorMessage(msg,token.getTextRange()));
                }
            }
        }
    }

    private void handleError(ErrorMessage error) {
        mErrorMessageList = mErrorMessageList.addMessage(error);
    }
}
