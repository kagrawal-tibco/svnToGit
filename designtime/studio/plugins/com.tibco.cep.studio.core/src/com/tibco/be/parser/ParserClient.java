package com.tibco.be.parser;

import com.tibco.be.parser.tree.RootNode;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Jun 29, 2004
 * Time: 9:20:14 PM
 * To change this template use File | Settings | File Templates.
 * 
 * 
 * A minimal parser client for parsing condition / action blocks entered in the UI
 */
public interface ParserClient {
    /**
     * For every condition statement successfully parsed in the when block, the parser will
     * call this method, passing the parse tree for that statement with whenTree as the root node.
     * @param whenTree root node of a successfully parsed condition statement
     */ 
    void addWhenTree(RootNode whenTree);

    /**
     * For every action statement successfully parsed in the then block, the parser will
     * call this method, passing the parse tree for that statement with thenTree as the root node. 
     * @param thenTree
     */ 
    void addThenTree(RootNode thenTree);

    /**
     * When a parse error occurrs, the parser will call this method,
     * passing in the ParseException that was thrown
     * @param error
     */
    void addError(ParseException error);
    
    /**
     * When a parse error occurrs, the parser will call this method,
     * passing in the ParseException that was thrown
     * @param error
     * @param message user message to be displayed instead of a standard message
     */ 
    void addError(ParseException error, String message);
}
