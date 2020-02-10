package com.tibco.be.parser.impl;

import java.io.FileReader;
import java.io.Reader;

import com.tibco.be.parser.ParseException;
import com.tibco.be.parser.RuleGrammar;


/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: May 23, 2004
 * Time: 5:04:18 PM
 * To change this template use Options | File Templates.
 */
public class JRuleCC {


    public JRuleCC() {

    }

    public void parseRule(Reader  r) throws ParseException {

        RuleGrammar grammer;
        grammer = new RuleGrammar(r);
        grammer.CompilationUnit();
    }


    public void parseExpression(Reader r) throws ParseException {

        RuleGrammar grammer;
        grammer = new RuleGrammar(r);
        grammer.Expression();
    }

    public static void main(String args[]) {

        try {
            JRuleCC cc = new JRuleCC();
            cc.parseRule(new FileReader(args[0]));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



}
