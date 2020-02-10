package com.tibco.cep.runtime.service.om.api;

public interface FilterDef<K extends Filter, Q extends FilterContext>  {
    K getInstance();
    Q getContext();
    
}
