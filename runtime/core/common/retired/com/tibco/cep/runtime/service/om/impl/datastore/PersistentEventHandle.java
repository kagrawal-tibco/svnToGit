package com.tibco.cep.runtime.service.om.impl.datastore;

import com.tibco.cep.kernel.core.base.AbstractEventHandle;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.service.om.ObjectStore;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Nov 21, 2006
 * Time: 5:40:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class PersistentEventHandle extends AbstractEventHandle implements PropertyBasedStore.PropertyBasedStoreHandle, PersistentHandle, ObjectStore.ObjectStoreHandle {

    private long id;
    private Event ref;
    private EventContext ctx;

    public PersistentEventHandle(Event _event, AbstractEventHandle _next, TypeInfo _typeInfo) {
        super(_next, _typeInfo);
        id = _event.getId();
        ref = _event;
    }


    public Object getRef() {
        return ref;
    }

    public boolean hasRef() {
        return ref != null;
    }

    public Object setRef(Object entity) {
        if(ref != null) return ref;
        WorkingMemoryImpl wm = (WorkingMemoryImpl) getWorkingMemory();
        if (entity instanceof SimpleEvent) {
            if (ctx != null) {
                ((SimpleEvent)entity).setContext(ctx);
            }
        }
        if(WorkingMemoryImpl.executingInside(wm)) {
            wm.recordTouchHandle(this);
            ref = (Event) entity;
        }
        return entity;
    }

    public Object getObject() {
        if(ref != null) return ref;
        WorkingMemoryImpl wm = (WorkingMemoryImpl) getWorkingMemory();
        Event evt= ((PersistentStore)wm.getObjectManager()).getEventFromCache(id);
        if (evt instanceof SimpleEvent) {
            if (ctx != null) {
                ((SimpleEvent)evt).setContext(ctx);
            }
        }

        if(WorkingMemoryImpl.executingInside(wm)) {
            wm.recordTouchHandle(this);
            ref = evt;
            return ref;
        }
        else {
            return evt;
        }
    }

    public String printInfo() {
        if(ref == null) {
            String refTypeName = "";
            if(getTypeInfo() != null && getTypeInfo().getType() != null) refTypeName = getTypeInfo().getType().getName();
            return "object:" + refTypeName + "@" + id + "(virtual), status:" + Integer.toBinaryString(status);
        } else
            return "object:" + ref + ", status:" + Integer.toBinaryString(status);
    }

    public void removeRef() {
        if (ref instanceof SimpleEvent) {
            ctx = ((SimpleEvent)ref).getContext();
            if(ctx != null && !ctx.canReply())
                ctx = null;
            ref = null;
        }
        else if((ref instanceof TimeEvent) && !((TimeEvent)ref).isRepeating()) {
            ref = null;
        }
    }

    public long getEventId() {
        return id;
    }

}