/**
 * 
 */
package com.tibco.be.parser.codegen;

import java.util.ArrayList;

import com.tibco.be.parser.ParseException;
import com.tibco.be.parser.ParserClient;
import com.tibco.be.parser.RuleError;
import com.tibco.be.parser.Token;
import com.tibco.be.parser.tree.NameNode;
import com.tibco.be.parser.tree.RootNode;

public class ActionOnlyParserClient implements ParserClient {


    //elements are ProdctionNodes
    public ArrayList thenTrees = new ArrayList();
    //elements are RuleErrors
    public ArrayList errors = new ArrayList();


    public void newRule() {
    }


    public void setName(String name) {
    }


    public void setPackageName(String packageName) {
    }


    public void addAttribute(Token attribute, String value) {
    }


    public void addImport(NameNode importType) {
    }


    public void addDeclaration(String declType, String declName) {
    }


    public void addWhenTree(RootNode whenTree) {
    }


    public void addThenTree(RootNode thenTree) {
        thenTrees.add(thenTree);
    }


    public void addError(ParseException error) {
        errors.add(RuleError.makeSyntaxError(error));
    }


    public void addError(ParseException error, String message) {
        errors.add(RuleError.makeSyntaxError(error, message));
    }
}