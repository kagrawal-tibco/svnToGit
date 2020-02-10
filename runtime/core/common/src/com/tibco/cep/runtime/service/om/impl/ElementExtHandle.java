package com.tibco.cep.runtime.service.om.impl;

import com.tibco.cep.kernel.core.base.AbstractElementHandle;
import com.tibco.cep.kernel.core.base.ElementHandleMap;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Dec 6, 2010
 * Time: 6:34:42 PM
 * To change this template use File | Settings | File Templates.
 */

//Class created in common package to allow casting _element to ConceptImpl 
public class ElementExtHandle extends ElementHandleMap.ElementExtHandle
{
    public ElementExtHandle(Element _element, AbstractElementHandle _next, TypeInfo _typeInfo) {
        super(_element, _next, _typeInfo);
        //copied from CacheConceptExHandle, fix for BE-10215
        ((ConceptImpl)_element).setHandle(this);
    }
}
