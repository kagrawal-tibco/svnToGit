package com.tibco.cep.kernel.core.base;

import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/*
* Author: Suresh Subramani / Date: 11/28/11 / Time: 3:56 PM
*/
public interface HandleManger {

    Handle getElementHandle(long id);

    Handle getElementHandle(String extId,Class elementClass);

    Handle getEventHandle(long id);

    Handle getEventHandle(String extId,Class eventClass);

    Handle getHandle(Object entity);

    public boolean handleExists(Object object);

    public BaseHandle createHandle(Object object) throws DuplicateExtIdException;


}
