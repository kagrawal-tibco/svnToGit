package com.tibco.cep.interpreter.evaluator.factory.impl;

import com.tibco.cep.query.model.impl.TypeInfoImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: nprade
 * Date: 9/7/11
 * Time: 2:16 PM
 */
public class StringLiteralFactory {

    private static final Pattern ESCAPE_SEQUENCE_PATTERN = Pattern.compile(
            "\\\\(?:(?:u(\\d{1,3}))|(?:0(\\d{1,3}))|(\\\")|(\\\\)|(b)|(f)|(r)|(n)|(t))");

    DecimalLiteralFactory decimalLiteralFactory = new DecimalLiteralFactory();

    public TypedValue make(
            String inputValue
    ) throws Exception {

        final StringBuffer result = new StringBuffer();

        final Matcher m = ESCAPE_SEQUENCE_PATTERN.matcher(inputValue.trim());
        while (m.find()) {
            if (!m.group(1).isEmpty()) {
                m.appendReplacement(result, "" + Character.forDigit(
                        (Integer) this.decimalLiteralFactory.make(m.group(1), 16).getValue(), 10));
            }
            else if (!m.group(2).isEmpty()) {
                m.appendReplacement(result, "" + Character.forDigit(
                        (Integer) this.decimalLiteralFactory.make(m.group(2), 8).getValue(), 10));
            }
            else if (!m.group(3).isEmpty()) {
                m.appendReplacement(result, "\"");
            }
            else if (!m.group(4).isEmpty()) {
                m.appendReplacement(result, "\"");
            }
            else if (!m.group(5).isEmpty()) {
                m.appendReplacement(result, "\\");
            }
            else if (!m.group(6).isEmpty()) {
                m.appendReplacement(result, "\b");
            }
            else if (!m.group(7).isEmpty()) {
                m.appendReplacement(result, "\f");
            }
            else if (!m.group(8).isEmpty()) {
                m.appendReplacement(result, "\r");
            }
            else if (!m.group(9).isEmpty()) {
                m.appendReplacement(result, "\r");
            }
            else if (!m.group(10).isEmpty()) {
                m.appendReplacement(result, "\t");
            }
        }
        m.appendTail(result);


        return new TypedValue(TypeInfoImpl.STRING, result.toString());
    }


}
