package com.tibco.be.parser.impl;

import java.util.ArrayList;

import com.tibco.be.parser.RuleVisitor;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: May 19, 2004
 * Time: 6:49:11 PM
 * To change this template use Options | File Templates.
 */
public class JRuleImpl implements RuleVisitor {

    ArrayList mDeclaredClasses = new ArrayList();
    ArrayList mIdentifiers = new ArrayList();
    String mRuleName;
    String mPackageName;
    ArrayList mImports = new ArrayList();

    protected JRuleImpl() {

    }
    public void addAction(String action, boolean isAssignment, String identifier) throws Exception {
    }

    public void addConidition(String condition, String[] identifiers) throws Exception {
    }

    public void addDeclarator(Class clazz, String var) throws Exception {
        mDeclaredClasses.add(clazz);
        mIdentifiers.add(var);
    }

    public void addImport(String importPackage) {
        mImports.add(importPackage);
    }

    public void setRuleName(String name) throws Exception {
    }


}
