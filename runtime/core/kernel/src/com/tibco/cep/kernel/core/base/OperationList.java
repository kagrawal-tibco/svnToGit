package com.tibco.cep.kernel.core.base;

import java.util.Iterator;
import java.util.List;

import com.tibco.cep.kernel.core.base.cache.AssertEntryCache;
import com.tibco.cep.kernel.core.base.cache.ModifyEntryCache;
import com.tibco.cep.kernel.core.base.cache.NonZeroTTLEntryCache;
import com.tibco.cep.kernel.core.base.cache.RetractEntryCache;
import com.tibco.cep.kernel.core.base.cache.StateChangeEntryCache;
import com.tibco.cep.kernel.core.base.cache.ZeroTTLEventEntryCache;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.service.ResourceManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 12, 2006
 * Time: 1:49:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class OperationList {
    private WorkingMemoryImpl m_wm;
    private BaseObjectManager m_om;

    private Entry opListHead = new Entry();
    private Entry opListLast = opListHead;

    private Entry zeroTTLEventsHead = new Entry();
    private Entry zeroTTLEventsLast = zeroTTLEventsHead;

    private Entry nonZeroTTLEventsHead = new Entry();
    private Entry nonZeroTTLEventsLast = nonZeroTTLEventsHead;

    private AssertEntryCache       assertEntryCache;
    private ModifyEntryCache       modifyEntryCache;
    private RetractEntryCache      retractEntryCache;
    private NonZeroTTLEntryCache   nonZeroTTLEntryCache;
    private ZeroTTLEventEntryCache zeroTTLEventEntryCache;
    private StateChangeEntryCache  stateChangeEntryCache;
    final private Logger logger;

    public OperationList(WorkingMemoryImpl wm, BaseObjectManager om) {
        m_wm   = wm;
        m_om   = om;
        assertEntryCache       = new AssertEntryCache();
        modifyEntryCache       = new ModifyEntryCache();
        retractEntryCache      = new RetractEntryCache();
        nonZeroTTLEntryCache   = new NonZeroTTLEntryCache();
        zeroTTLEventEntryCache  = new ZeroTTLEventEntryCache();
        stateChangeEntryCache   = new StateChangeEntryCache();
        logger = m_wm.getLogManager().getLogger(OperationList.class);
    }

    public Handle opAssertObj(Object obj) throws DuplicateExtIdException {
        try {
            BaseHandle handle;
            if(obj instanceof Element) {
                List children = ((Element)obj).getChildren();
                if (children != null) {
                    Iterator ite = children.iterator();
                    while(ite.hasNext()) {
                        Element child = (Element) ite.next();
                        handle =  m_om.getAddElementHandle(child);
                        if(handle.okMarkAssert()) {
                            opListLast.next = assertEntryCache.get(handle);
                            opListLast = opListLast.next;
                        }
                    }
                }
                handle = m_om.getAddElementHandle((Element) obj);
            }
            else{
                handle = m_om.getAddHandle(obj);
            }
            if(handle.okMarkAssert()) {
                opListLast.next = assertEntryCache.get(handle);
                opListLast = opListLast.next;
            }
            return handle;
        } catch (DuplicateExtIdException e) {
            if(obj instanceof Entity) {
                removeAll((Entity)obj);
            }
            throw e;
        }
    }

    private void removeAll(Entity parent) {
        BaseHandle handle;
        String allChildren = "";
        if(parent instanceof Element) {
            //get all the children and delete them
            List children = ((Element)parent).getChildren();
            if (children != null) {
                Iterator ite = children.iterator();
                while(ite.hasNext()) {
                    Element child = (Element) ite.next();
                    handle = m_om.getElementHandle(child);
                    if(handle == null) {
                        child.delete();
                    }
                    else if(handle.okMarkDelete()) {
                        opListLast.next = retractEntryCache.get(handle);
                        opListLast = opListLast.next;
                        if(allChildren.length() ==0)
                            allChildren += child;
                        else
                            allChildren = allChildren + ", " + child;
                    }
                }
            }
            handle = m_om.getElementHandle((Element) parent);
            if(handle == null) {
                ((Element) parent).delete();
            }
        }
        else {
            handle = m_om.getHandle(parent);
        }
        if (handle != null && handle.okMarkDelete()) {
            opListLast.next = retractEntryCache.get(handle);
            opListLast = opListLast.next;
        }
        if(allChildren.length() == 0)
            logger.log(Level.ERROR,ResourceManager.formatString("duplicte.extId.remove.object", parent));
        else
            logger.log(Level.ERROR,ResourceManager.formatString("duplicte.extId.remove.objects", parent, allChildren));
    }

    public boolean opStateChangeObj(BaseHandle handle, int index) {
        //if(handle.okMarkDirty()) {
            opListLast.next = stateChangeEntryCache.get(handle, index);
            opListLast = opListLast.next;
            return true;
        //}
//        else {
//            System.err.println("Operation-List: !okMarkDirty" + handle.getObject() + ", index = " + index);
//            return false;
//        }
    }

//    public boolean opModifyObj(BaseHandle handle) {
//    	return opModifyObj(handle, true);
//    }
	public boolean opModifyObj(BaseHandle handle, boolean recordThis) {
        if(handle.okMarkDirty()) {
            opListLast.next = modifyEntryCache.get(handle);
            opListLast = opListLast.next;
            if(recordThis) handle.setPropertyModified();
            return true;
        }
        //already marked dirty above with recordThis=false
        else if(recordThis && handle.isDirty()) {
        	handle.setPropertyModified();
        }
        
        return false;
    }

    public void opRetractObj(Handle handle) {
//        String str;
//        if(handle == null) str = "null handle";
//        else if(handle != null && handle.getObject() == null) str = "null object";
//        else str = handle.getObject() + "" + handle.getTypeInfo();
//        this.m_wm.getLogger().logInfo("opRetractObj " + str);
        if(handle == null) return;
        if(handle instanceof AbstractElementHandle) {
            Object elem = handle.getObject();
            if(elem != null) {
                List children = (((Element)elem).getChildren());
                if (children != null) {
                    Iterator ite = children.iterator();
                    while(ite.hasNext()) {
                        BaseHandle childHandle =  m_om.getElementHandle((Element)ite.next());
                        if (childHandle != null) {
                            if(childHandle.okMarkDelete()) {
                                opListLast.next = retractEntryCache.get(childHandle);
                                opListLast = opListLast.next;
                            }
                        }
                    }
                }
            }
        }
        if(((BaseHandle)handle).okMarkDelete()) {
            opListLast.next = retractEntryCache.get((BaseHandle)handle);
            opListLast = opListLast.next;
        }
    }

    public void opCleanupHandle(Handle handle) {
        if(handle == null) return;
        if(((BaseHandle)handle).okMarkDelete()) {
            opListLast.next = new CleanupHandleEntry((BaseHandle) handle);
            opListLast = opListLast.next;
        }
    }

    public boolean performOps() {
        return performOps(true);
    }


    public boolean performOps(boolean forwardChain) {
        Entry curr = opListHead.next;
        if(curr == null) return false;
        try {
            do {
                ((OpEntry)curr).execute(m_wm, forwardChain);
                Entry next = curr.next;
                try {
                    curr.recycle();
                } finally {
                    curr = next;
                }
            } while(curr != null);
        } finally {
            clear(curr);
        }
        return true;
    }

    protected void clear(Entry curr) {
        opListHead.next = null;
        opListLast = opListHead;
        while(curr != null) {
            Entry next = curr.next;
            curr.recycle();
            curr = next;
        }
    }
    
    public void clear() {
        clear(opListHead.next);
    }
    
    public boolean isEmpty() {
        return opListHead.next == null;
    }

    public String performOpsListToString() {
        int size = 0;
        Entry curr = opListHead.next;
        if(curr == null) return "size=0, list=null";
        StringBuffer buf = new StringBuffer();
        do{
            curr.printEntry(buf);
            curr = curr.next;
            size++;
        } while(curr != null);
        return "size=" + size + ", list=" + buf.toString();
    }

    //for handling zero TTL Event
    public void zeroTTLEvent(AbstractEventHandle evtHandle) {
        zeroTTLEventsLast.next = zeroTTLEventEntryCache.get(evtHandle);
        zeroTTLEventsLast = zeroTTLEventsLast.next;
    }

    public void retractZeroTTLEvents() {
        Entry entry = zeroTTLEventsHead.next;
        try {
            while(entry != null) {
                //entry.retractEvents this may call other methods which eventually
                //come back to this point so remove the current entry from the list
                //so that it doesn't get processed twice
                zeroTTLEventsHead.next = entry.next;
                if(zeroTTLEventsLast == entry) zeroTTLEventsLast = zeroTTLEventsHead;
                ((ZeroTTLEventEntry)entry).retractEvents(m_wm);
                //put recycle in a try so that if recycle throws an exception after adding
                //entry back to the entry, cache, the outer finally block won't recycle
                //entry another time
                Entry next = zeroTTLEventsHead.next;
                try {
                    //entry must not be recycled twice or it will be in the entry cache twice
                    //and zeroTTLEvent will assign entry.next to itself leading to an infinite loop here.
                    entry.recycle();
                } finally {
                    //entry.retractEvents this may call other methods which eventually
                    //come back to this point 
                    // so zeroTTLEventsHead.next may no longer be entry.next
                    entry = next;
                }
                
            }
        } finally {
            try {
                while (entry != null) {
                    Entry next = zeroTTLEventsHead.next;
                    entry.recycle();
                    entry = next;
                }
            } finally {
                //ensure these fields get reset even if the above loops throws an exception
                zeroTTLEventsHead.next = null;
                zeroTTLEventsLast = zeroTTLEventsHead;
            }
        }
    }

    //for handling non-zero TTL Event
    public void nonZeroTTLEvent(AbstractEventHandle evtHandle) {
        nonZeroTTLEventsLast.next = nonZeroTTLEntryCache.get(evtHandle);
        nonZeroTTLEventsLast = nonZeroTTLEventsLast.next;
    }

    public void startTTLTimers() {
        Entry curr = nonZeroTTLEventsHead.next;
        if(curr == null) return;
        try {
            do{
                ((NonZeroTTLEventEntry)curr).startExpiryTimer(m_wm);
                Entry next = curr.next;
                try {
                    curr.recycle();
                } finally {
                    curr = next;
                }
            } while(curr != null);
        } finally {
            nonZeroTTLEventsHead.next = null;
            nonZeroTTLEventsLast = nonZeroTTLEventsHead;
            while(curr != null) {
                Entry next = curr.next;
                curr.recycle();
                curr = next;
            }
        }
    }

    static public class Entry {
        protected Entry next;
        void printEntry(StringBuffer buffer) {}
        protected void recycle() {}
    }

    //event ttl management ////////////////////////////////////

    static public class ZeroTTLEventEntry extends Entry {
        protected AbstractEventHandle handle;
        public ZeroTTLEventEntry(AbstractEventHandle _handle) {
            handle = _handle;
        }
        void retractEvents(WorkingMemoryImpl wm) {
            Event evt = (Event) handle.getObject();
            if(wm.retractHandleInternal(handle)) {
                if(evt != null && evt.hasExpiryAction()) {
                    wm.eventExpiryActions(evt);
                }
            }
        }
        void printEntry(StringBuffer buffer) {
            buffer.append("[ZeroTTLEvent:").append(handle.getObject()).append("] ");
        }
    }

    static public class NonZeroTTLEventEntry extends Entry {
        protected AbstractEventHandle handle;
        public NonZeroTTLEventEntry(AbstractEventHandle _handle) {
            handle = _handle;
        }
        void startExpiryTimer(WorkingMemoryImpl wm) {
            if(!handle.isRetracted()) {
                Object obj = handle.getObject();
                if(obj != null) {
                    wm.m_timeManager.scheduleEventExpiry(handle, ((Event)obj).getTTL());
                }
            }
        }
        void printEntry(StringBuffer buffer) {
            buffer.append("[NonZeroTTLEvent:").append(handle.getObject()).append("] ");
        }
    }

    //object management ////////////////////////////////////

    abstract static public class OpEntry extends Entry {
        protected BaseHandle handle;
        void execute(WorkingMemoryImpl wm) {
            execute(wm, true);
        }
        abstract void execute(WorkingMemoryImpl wm, boolean forwardChain);
    }

    static public class AssertEntry extends OpEntry {
        public AssertEntry(BaseHandle _handle) {
            handle = _handle;
        }
        void execute(WorkingMemoryImpl wm) {
            wm.assertHandleInternal(handle, true);
        }
        void execute(WorkingMemoryImpl wm,boolean forwardChain) {
            wm.assertHandleInternal(handle,forwardChain);
        }

        void printEntry(StringBuffer buffer) {
            buffer.append("[Assert:").append(handle.getObject()).append("] ");
        }

        public String toString() {
            return "Asserted:" + handle.getObject();
        }
    }

    static public class ModifyEntry extends OpEntry {
        public ModifyEntry(BaseHandle _handle) {
            handle = _handle;
        }
        void execute(WorkingMemoryImpl wm) {
            wm.modifyHandleInternal(handle, null, handle.isPropertyModified());
        }
        void execute(WorkingMemoryImpl wm,boolean forwardChain) {
            wm.modifyHandleInternal(handle, null, handle.isPropertyModified(), forwardChain);
        }

        void printEntry(StringBuffer buffer) {
            buffer.append("[Modify:").append(handle.getObject()).append("] ");
        }
        public String toString() {
            return "Modified:" + handle.getObject();
        }
    }

    static public class StateChangeEntry extends OpEntry {
        protected int index;

        public StateChangeEntry(BaseHandle _handle, int index) {
            handle = _handle;
            this.index=index;
        }

        void execute(WorkingMemoryImpl wm) {
            wm.stateChangeInternal(handle, index);
        }

        void execute(WorkingMemoryImpl wm,boolean forwardChain) {
            wm.stateChangeInternal(handle, index);
        }

        void printEntry(StringBuffer buffer) {
            buffer.append("[StateChange:").append(handle.getObject()).append("] ");
        }
        public String toString() {
            return "StateChange:" + handle.getObject();
        }
    }

    static public class RetractEntry extends OpEntry {
        public RetractEntry(BaseHandle _handle) {
            handle = _handle;
        }
        void execute(WorkingMemoryImpl wm) {
            execute(wm, true);
        }

        void execute(WorkingMemoryImpl wm, boolean forwardChain) {
            //TODO AA remove this if it is unsafe for handles to be null
            if(handle != null) {
                wm.retractHandleInternal(handle, forwardChain);
            }
        }

        void printEntry(StringBuffer buffer) {
            buffer.append("[Retract:").append(handle == null ? handle : handle.getObject()).append("] ");
        }
        public String toString() {
            return "Retracted:" + handle.getObject();
        }
    }

    static public class CleanupHandleEntry extends OpEntry {
        public CleanupHandleEntry(BaseHandle _handle) {
            handle = _handle;
        }
        void execute(WorkingMemoryImpl wm) {
            wm.cleanupHandleInternal(handle);
        }

        void execute(WorkingMemoryImpl wm, boolean forwardChain) {
            wm.cleanupHandleInternal(handle);
        }

        void printEntry(StringBuffer buffer) {
            buffer.append("[cleanupHandle:").append(handle).append("] ");
        }
        public String toString() {
            return "cleanupHandle:" + handle;
        }
    }
}

