package com.tibco.cep.runtime.service.om.api;

import java.io.Serializable;

public interface Filter extends Serializable {
    boolean evaluate(Object o, FilterContext context);
}
