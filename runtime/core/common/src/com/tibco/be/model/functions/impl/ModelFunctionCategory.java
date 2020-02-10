package com.tibco.be.model.functions.impl;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.xml.data.primitive.ExpandedName;


/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Oct 7, 2004
 * Time: 8:09:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ModelFunctionCategory extends FunctionsCategoryImpl {
    Entity m_entity;
    ModelFunctionCategory(ExpandedName categoryName, Entity entity) {
        super(categoryName);    //To change body of overridden methods use File | Settings | File Templates.
        m_entity = entity;
    }

    ModelFunctionCategory(ExpandedName categoryName, boolean allowSubCategories, boolean allowPredicates, Entity entity) {
        super(categoryName, allowSubCategories, allowPredicates);    //To change body of overridden methods use File | Settings | File Templates.
        m_entity = entity;
    }

    public Entity getEntity() {
        return m_entity;
    }
}
