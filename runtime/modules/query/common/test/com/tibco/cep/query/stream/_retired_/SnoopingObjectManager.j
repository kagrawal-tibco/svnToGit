package com.tibco.cep.query.stream._retired_;

import java.util.HashMap;
import java.util.Iterator;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.RtcOperationList;
import com.tibco.cep.kernel.service.impl.DefaultObjectManager;
import com.tibco.cep.query.stream.impl.rete.Helper;
import com.tibco.cep.query.stream.impl.rete.input.ReteEntityChangeListener;
import com.tibco.cep.runtime.model.event.TimeEvent;

/*
 * Author: Ashwin Jayaprakash Date: Oct 11, 2007 Time: 11:07:35 AM
 */

public class SnoopingObjectManager extends DefaultObjectManager {
    protected final HashMap<Long, Object> store;

    protected ReteEntityChangeListener listener;

    public SnoopingObjectManager(String name) {
        super(name);

        this.store = new HashMap<Long, Object>();
    }

    public ReteEntityChangeListener getListener() {
        return listener;
    }

    public Object fetchInstance(String entityClassName, long id) {
        return store.get(id);
    }

    @Override
    public void applyChanges(RtcOperationList rtcList) {
        Iterator ite = rtcList.iterator();
        while (ite.hasNext()) {
            BaseHandle handle = (BaseHandle) ite.next();
            Object obj = handle.getObject();

            if (handle.isRtcAsserted_AND_Deleted()) {
                // Assert and Delete in same RTC. Do nothing.
            }
            else {
                if (obj instanceof TimeEvent == false) {
                    long reteId = Helper.extractId(obj);

                    if (handle.isRtcDeleted()) {
                        listener.onDelete(obj.getClass().getName(), reteId);
                    }
                    else if (handle.isRtcAsserted()) {
                        listener.onNew(obj.getClass().getName(), reteId);
                    }
                    else if (handle.isRtcAnyModification()) {
                        // Send delete first.
                        listener.onDelete(obj.getClass().getName(), reteId);

                        listener.onNew(obj.getClass().getName(), reteId);
                    }
                }
            }

            handle.rtcClearAll();
        }
    }
}
