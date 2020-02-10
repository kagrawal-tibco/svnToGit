package com.tibco.be.parser;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: May 19, 2004
 * Time: 6:35:17 PM
 * To change this template use Options | File Templates.
 */
public interface RuleVisitor {

    public void setRuleName(String name) throws Exception;
    public void addDeclarator(Class clazz, String var) throws Exception;
    public void addConidition(String condition, String [] identifiers) throws Exception;
    public void addAction(String action, boolean isAssignment, String identifier ) throws Exception;
}
