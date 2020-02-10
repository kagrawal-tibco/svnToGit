package com.tibco.cep.runtime.service.om;

import com.tibco.cep.kernel.core.base.AbstractElementHandle;
import com.tibco.cep.kernel.core.base.AbstractEventHandle;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.session.locks.LockManager;
import com.tibco.cep.runtime.session.sequences.SequenceManager;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Oct 25, 2006
 * Time: 9:37:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ObjectBasedStore extends ObjectStore {
    AbstractEventHandle getNewEventExtHandle(com.tibco.cep.kernel.model.entity.Event obj, AbstractEventHandle _next, boolean forceSet);

    AbstractEventHandle getNewEventHandle(com.tibco.cep.kernel.model.entity.Event obj, AbstractEventHandle _next,boolean forceSet);

    AbstractElementHandle getNewElementExtHandle(Element obj, AbstractElementHandle _next, boolean forceSet);

    AbstractElementHandle getNewElementHandle(Element obj, AbstractElementHandle _next,boolean forceSet);

    interface ObjectBasedStoreHandle {
    }
    public void registerType(TypeManager.TypeDescriptor entityType);
    public void registerType(Class entityClass);
    public LockManager getLockManager();
    public Element getElementFromStore(long id);
    public Entity getElementFromStore(String extId);
//    public Entity getElementFromStore(Class entityClass, String extId);
    public SequenceManager getSequenceManager();

}
