package com.tibco.cep.runtime.service.om;


import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.service.om.exception.OMException;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Jun 30, 2006
 * Time: 1:58:07 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ObjectStore extends ObjectManager {

    public interface ObjectStoreHandle extends Handle {

//        Object setRef(Object entity);

        Object getRef();

    }

    void addInstance(Concept c);

    void removeInstance(Concept c);

    void addEvent(Event e);

    void removeEvent(Event e);

    void recover() throws OMException;

}
