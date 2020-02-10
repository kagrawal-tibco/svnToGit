package com.tibco.be.model.functions.impl;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.service.channel.Channel;
import com.tibco.xml.data.primitive.ExpandedName;


/**
 * @author ishaan
 * @version Jan 7, 2005, 6:34:35 PM
 */
public class ChannelModelFunctionCategory extends ModelFunctionCategory {
    protected ExpandedName m_functionName;
    /**
     * @param model
     */
    public ChannelModelFunctionCategory(ExpandedName categoryName, Channel model) {
        this(categoryName, true, true, model);
    }

    ChannelModelFunctionCategory(ExpandedName categoryName, boolean allowSubCategories, boolean allowPredicates, Channel model) {
        super(categoryName, allowSubCategories, allowPredicates, model);
        m_functionName = categoryName;
    }

    public ExpandedName getName() {
        return m_functionName;
    }

    public Class getReturnClass() {
        return void.class;
    }

    public Class[] getThrownExceptions() {
        return new Class[0];
    }

    public Class[] getArguments() {
        return new Class[0];
    }

    public String code() {
        return "";
    }

    public boolean isValidInCondition() {
        return false;
    }

    public boolean isValidInAction() {
        return false;
    }

    public boolean isTimeSensitive() {
        return false;
    }

    public boolean requiresAsync() {
        return false;
    }

    public String getDocumentation() {
        return "";
    }

    public boolean doesModify() {
        return false;
    }

    String[] getParameterNames() {
        return new String[0];
    }

    public Entity[] getEntityArguments() {
        return new Entity[0];
    }

    public Entity getEntityReturnType() {
        return null;
    }

    String getDescription() {
        return "";
    }
}