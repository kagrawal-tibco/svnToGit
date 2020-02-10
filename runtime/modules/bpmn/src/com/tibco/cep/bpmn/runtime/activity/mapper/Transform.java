package com.tibco.cep.bpmn.runtime.activity.mapper;

/*
* Author: Suresh Subramani / Date: 2/8/12 / Time: 4:43 PM
*/
public interface Transform {
    enum Type {
        CLONE,
        COPY_ON_WRITE,
        RULEFUNCTION,
        MAPPER
    }

    Type getType();
}
