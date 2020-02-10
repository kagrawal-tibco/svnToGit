/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import com.tibco.cep.kernel.core.base.AbstractEventHandle;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.runtime.model.event.EventContext;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.cep.runtime.service.om.ObjectBasedStore;
import com.tibco.cep.runtime.service.om.ObjectStore;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 28, 2006
 * Time: 10:14:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class CacheEventHandle extends AbstractEventHandle implements CacheHandle, ObjectBasedStore.ObjectBasedStoreHandle, ObjectStore.ObjectStoreHandle {

    private long id;
    private Event ref;
    private EventContext ctx;


    public CacheEventHandle(WorkingMemoryImpl wm, Event _event, AbstractEventHandle _next) {
        this(wm,_event,_next,false);
    }

    public CacheEventHandle(WorkingMemoryImpl wm, Event _event, AbstractEventHandle _next, boolean forceSet) {
        super(_next, wm.getTypeInfo(_event.getClass()));
        id = _event.getId();

        if(WorkingMemoryImpl.executingInside(wm)) {
            ((WorkingMemoryImpl) wm).recordTouchHandle(this);
        }

        if (forceSet || !_event.isLoadedFromCache()) {
            ref = _event;
        }
    }

    public Object getRef() {
        return ref;
    }
    public long getId() {
        return id;
    }

    public boolean hasRef() {
        return ref != null;
    }

    public void touch(WorkingMemory wm) {
        if (wm != null) {
            if(WorkingMemoryImpl.executingInside(wm)) {
                ((WorkingMemoryImpl) wm).recordTouchHandle(this);
            }
        }
    }

//    public Object setRef(Object entity) {
//        Event ret  = ref;
//        if(ret != null) return ret;
//        WorkingMemoryImpl wm = (WorkingMemoryImpl) getWorkingMemory();
//        if (entity instanceof SimpleEvent) {
//            if (ctx != null) {
//                ((SimpleEvent)entity).setContext(ctx);
//            }
//        }
//
//        if(WorkingMemoryImpl.executingInside(wm)) {
//            wm.recordTouchHandle(this);
//            ref = (Event) entity;
//        }
//        return entity;
//    }

    public synchronized Object getObject() {
        Event ret  = ref;
        if(ret != null) return ret;
        WorkingMemoryImpl wm = (WorkingMemoryImpl) getWorkingMemory();
        Event evt= ((DefaultDistributedCacheBasedStore) wm.getObjectManager()).getEventFromCache(this);
        if(evt==null)
            return null;
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
          //toBinaryString coerces to int so mask off all bits that aren't part of a byte
            return "object:" + refTypeName + "@" + id + "(virtual), status:" + Integer.toBinaryString(status & 0xFF);
        } else
            return "object:" + ref + ", status:" + Integer.toBinaryString(status & 0xFF);
    }

    public synchronized void removeRef() {
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

