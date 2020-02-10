/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import com.tibco.cep.kernel.core.base.AbstractEventHandle;
import com.tibco.cep.kernel.core.base.ExtIdHandle;
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
 * Time: 10:21:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class CacheEventExHandle extends AbstractEventHandle implements ExtIdHandle, CacheHandle, ObjectBasedStore.ObjectBasedStoreHandle, ObjectStore.ObjectStoreHandle {

    private long id;
    private String extId;
    private Event ref;
    private EventContext ctx;


    public CacheEventExHandle(WorkingMemoryImpl wm, Event _event) {
        this(wm,_event,false);
    }

    public CacheEventExHandle(WorkingMemoryImpl wm, Event _event, boolean forceSet) {
        super(null, wm.getTypeInfo(_event.getClass()));
        id = _event.getId();
        extId = _event.getExtId();

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
//        Event ret = ref;
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
//            ref =  (Event) entity;
//        }
//        return entity;
//    }
    /*
    public Object getObject() {
        if(ref != null) return ref;
        EventAdapter ead = (EventAdapter) ((TangosolStore)getWorkingMemory().getObjectManager()).getObjectFromStore(id);
        Event e = ead.getEvent();
        //c.setHandle(this); TODO Not required - Nick?
        return e;
    } */

    public synchronized Object getObject() {
        Event ret = ref;
        if(ret != null) return ret;
        WorkingMemoryImpl wm = (WorkingMemoryImpl) getWorkingMemory();
        Event evt= ((DefaultDistributedCacheBasedStore) wm.getObjectManager()).getEventFromCache(this);
        if (evt==null)
            return null;
        if (evt instanceof SimpleEvent) {
            if (ctx != null) {
                ((SimpleEvent)evt).setContext(ctx);
            }
        }

        if(WorkingMemoryImpl.executingInside(wm)) {
            wm.recordTouchHandle(this);
            ref =  evt;
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
            return "object:" + refTypeName + "@" + id + "@" + extId + "(virtual), status:" + Integer.toBinaryString(status & 0xFF);
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


    public String getExtId() {
        return extId;
    }

    public ExtIdHandle getNextExtIdEntry() {
        throw new UnsupportedOperationException("getNextExtIdEntry in CacheEventExHandle");
    }

    public void setNextExtIdEntry(ExtIdHandle next) {
        throw new UnsupportedOperationException("setNextExtIdEntry in CacheEventExHandle");
    }

    public HandleType getHandleType() {
        return HandleType.EVENT;
    }
}

