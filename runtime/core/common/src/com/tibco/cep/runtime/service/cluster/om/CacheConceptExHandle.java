/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.om;

import com.tibco.cep.kernel.core.base.AbstractElementHandle;
import com.tibco.cep.kernel.core.base.ExtIdHandle;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.service.om.ObjectBasedStore;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 28, 2006
 * Time: 10:11:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class CacheConceptExHandle extends AbstractElementHandle implements ExtIdHandle, CacheHandle, ObjectBasedStore.ObjectBasedStoreHandle {

    private long id;
    private String extId;
    private Element ref;

    public CacheConceptExHandle(WorkingMemoryImpl wm, Element _concept) {
        this(wm,_concept,false);
    }

    public CacheConceptExHandle(WorkingMemoryImpl wm, Element _concept, boolean forceSet) {
        super(null, wm.getTypeInfo(_concept.getClass()));
        id = _concept.getId();
        extId = _concept.getExtId();

        if(WorkingMemoryImpl.executingInside(wm)) {
            ((WorkingMemoryImpl) wm).recordTouchHandle(this);
        }

        if (forceSet || !_concept.isLoadedFromCache()) {
            ref = (Element) _concept;
        }

        ((ConceptImpl)_concept).setHandle(this);

    }

    public Object getRef() {
        return ref;
    }

    public boolean hasRef() {
        return ref != null;
    }

    public long getId() {
        return id;
    }

    public void touch(WorkingMemory wm) {
        if (wm != null) {
            if(WorkingMemoryImpl.executingInside(wm)) {
                ((WorkingMemoryImpl) wm).recordTouchHandle(this);
            }
        }
    }

//    public Object setRef(Object entity) {
//        Element ret = ref;
//        if(ret != null) return ret;
//        WorkingMemoryImpl wm = (WorkingMemoryImpl) getWorkingMemory();
//        ((ConceptImpl)entity).setHandle(this);
//
//        if(WorkingMemoryImpl.executingInside(wm)) {
//            wm.recordTouchHandle(this);
//            ref = (Element) entity;
//        }
//        return entity;
//    }


    public synchronized Object getObject() {
        Element ret = ref;
        if(ret != null) {
            return ret;
        }
        WorkingMemoryImpl wm = (WorkingMemoryImpl) getWorkingMemory();
        ConceptImpl c= (ConceptImpl) ((DefaultDistributedCacheBasedStore) wm.getObjectManager()).getConceptFromCache(this);
        if (c == null) {
            return null;
            //throw new RuntimeException("PROGRAM ERROR: Object Not Found For Handle =" + this.getTypeInfo().getType().getName() + ", id=" + this.getElementId());
        }
        if(WorkingMemoryImpl.executingInside(wm)) {
            c.setHandle(this);
            ref = c;
            wm.recordTouchHandle(this);
            return ref;
        }
        else {
            return c;
        }
    }

    public String printInfo() {
        if(ref == null) {
            String refTypeName = "";
            if(getTypeInfo() != null && getTypeInfo().getType() != null) refTypeName = getTypeInfo().getType().getName();
          //toBinaryString coerces to int so mask off all bits that aren't part of a byte
            return "object:" + refTypeName + "@" + id + "@" + extId + "(virtual), status:" + Integer.toBinaryString(status & 0xFF);
        } else
            return "object:" + ref + ", status=" + Integer.toBinaryString(status & 0xFF);
    }

    public synchronized void removeRef() {
        if(ref != null && ref instanceof ConceptImpl){
            ConceptImpl c = (ConceptImpl) ref;
            c.setHandle(null);
        }

        ref = null;
    }

    public long getElementId() {
        return id;
    }

    public String getExtId() {
        return extId;
    }

    public ExtIdHandle getNextExtIdEntry() {
        throw new UnsupportedOperationException("getNextExtIdEntry in CacheConceptExHandle");
    }

    public void setNextExtIdEntry(ExtIdHandle next) {
        throw new UnsupportedOperationException("setNextExtIdEntry in CacheConceptExHandle");
    }

   public HandleType getHandleType() {
       return HandleType.ELEMENT;
   }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CacheConceptExHandle");
        sb.append("{id=").append(id);
        sb.append(", extId='").append(extId).append('\'');
        sb.append(", ref=").append(ref);
        sb.append('}');
        return sb.toString();
    }
}

