package com.tibco.be.model.functions.impl;

import com.tibco.cep.designtime.model.Entity;
import com.tibco.xml.data.primitive.ExpandedName;


/**
 * @author ishaan
 * @version Nov 1, 2004, 7:00:35 PM
 */
public class StateModelFunctionCategory extends ModelFunctionCategory {
    public StateModelFunctionCategory(ExpandedName categoryName, boolean allowSubCategories, boolean allowPredicates, Entity entity) {
        super(categoryName, allowSubCategories, allowPredicates, entity);
    }

    public StateModelFunctionCategory(ExpandedName categoryName, Entity entity) {
        super(categoryName, entity);
    }

    public String toString() {
        return '"' + super.toString() + '"';
    }
}
