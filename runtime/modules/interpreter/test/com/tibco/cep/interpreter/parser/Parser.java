package com.tibco.cep.interpreter.parser;


import java.io.ByteArrayInputStream;

/**
 * User: nprade
 * Date: 8/25/11
 * Time: 5:38 PM
 */
public interface Parser {


    ParserResult parse(String input) throws Exception;


    ParserResult parse(ByteArrayInputStream input, String charset) throws Exception;


}
