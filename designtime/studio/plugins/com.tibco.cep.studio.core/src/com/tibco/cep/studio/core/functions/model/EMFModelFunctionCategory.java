package com.tibco.cep.studio.core.functions.model;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.xml.data.primitive.ExpandedName;

public class EMFModelFunctionCategory extends EMFFunctionsCategoryImpl {
    Entity m_entity;
    EMFModelFunctionCategory(ExpandedName categoryName, Entity entity) {
        super(categoryName);    //To change body of overridden methods use File | Settings | File Templates.
        m_entity = entity;
    }

    EMFModelFunctionCategory(ExpandedName categoryName, boolean allowSubCategories, boolean allowPredicates, Entity entity) {
        super(categoryName, allowSubCategories, allowPredicates);    //To change body of overridden methods use File | Settings | File Templates.
        m_entity = entity;
    }

    public Entity getEntity() {
        return m_entity;
    }
}
