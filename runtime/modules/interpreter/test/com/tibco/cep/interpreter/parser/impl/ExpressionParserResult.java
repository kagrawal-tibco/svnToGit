package com.tibco.cep.interpreter.parser.impl;

import com.tibco.cep.interpreter.parser.ParserResult;
import org.antlr.runtime.tree.CommonTree;


/**
 * User: nprade
 * Date: 8/25/11
 * Time: 6:02 PM
 */
public class ExpressionParserResult
        implements ParserResult {


    private CommonTree ast;


    public ExpressionParserResult(
            CommonTree ast) {

        this.ast = ast;
    }


    @Override
    public CommonTree getAST() {  //todo: independence from ANTLR?

        return this.ast;
    }


}
