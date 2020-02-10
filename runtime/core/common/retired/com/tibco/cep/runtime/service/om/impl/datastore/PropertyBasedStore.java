package com.tibco.cep.runtime.service.om.impl.datastore;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.service.om.ObjectStore;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Oct 25, 2006
 * Time: 9:38:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PropertyBasedStore extends ObjectStore {

    Property getProperty(Concept subject, String className);

    Property getProperty(long subjectId, String className);

    void addProperty(Property p);

    void removeProperty(Property p);

    void removeProperty(Concept subject, String className);

    void modifyProperty(Property p);

    interface PropertyBasedStoreHandle {
    }
}
