package com.tibco.cep.studio.core.functions.model;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.xml.data.primitive.ExpandedName;

public class EMFStateModelFunctionCategory extends EMFModelFunctionCategory {

    public EMFStateModelFunctionCategory(ExpandedName categoryName, Entity entity) {
        super(categoryName, entity);
    }

    public String toString() {
        return '"' + super.toString() + '"';
    }

}
