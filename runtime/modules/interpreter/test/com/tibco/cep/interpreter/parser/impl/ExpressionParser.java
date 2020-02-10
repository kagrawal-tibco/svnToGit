package com.tibco.cep.interpreter.parser.impl;

import com.tibco.cep.interpreter.parser.Parser;
import com.tibco.cep.interpreter.parser.ParserResult;
import com.tibco.cep.query.ast.parser.BEOqlLexer;
import com.tibco.cep.query.ast.parser.BEOqlParser;
import com.tibco.cep.query.exception.ParseException;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;

import java.io.ByteArrayInputStream;

/**
 * User: nprade
 * Date: 8/25/11
 * Time: 5:47 PM
 */
public class ExpressionParser
        implements Parser {

    private static final String PARSING_CHARSET = "UTF-8";


    public ExpressionParser() {
    }


    private CommonTree makeAST(
            BEOqlParser parser)  //todo: independence from query module
            throws Exception {

        final BEOqlParser.expr_return r;
        try {
            r = parser.expr();

        } catch (RecognitionException e) {
            final String[] tokens = new String[]{e.token.getText()};
            throw new ParseException(parser.getErrorMessage(e, tokens), e.line, e.charPositionInLine);

        } catch (RuntimeException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof RecognitionException) {
                final RecognitionException re = (RecognitionException) cause;

                if (re.token != null) {
                    final String[] tokens = new String[]{re.token.getText()};
                    throw new ParseException(parser.getErrorMessage(re, tokens), re.line, re.charPositionInLine);
                } else {
                    throw re;
                }
            } else if (cause instanceof Exception) {
                throw (Exception) cause;
            } else {
                throw e;
            }
        }

        return (CommonTree) r.getTree();
    }


    @Override
    public ParserResult parse(
            String input)
            throws Exception {

        return this.parse(new ByteArrayInputStream(input.getBytes(PARSING_CHARSET)), PARSING_CHARSET);
    }


    @Override
    public ParserResult parse(
            ByteArrayInputStream input,
            String charset)
            throws Exception {

        final ANTLRInputStream is = new ANTLRInputStream(input, charset);
        final BEOqlLexer lexer = new BEOqlLexer(is); //todo: independence from query module
        final CommonTokenStream cts = new CommonTokenStream(lexer);
        final BEOqlParser parser = new BEOqlParser(cts);
        final CommonTree ast = this.makeAST(parser);
        return new ExpressionParserResult(ast);
    }


}
