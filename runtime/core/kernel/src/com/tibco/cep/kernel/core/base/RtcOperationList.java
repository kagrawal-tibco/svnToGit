package com.tibco.cep.kernel.core.base;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Oct 18, 2006
 * Time: 9:11:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class RtcOperationList {

    private RtcListEntry rtcOperationListHead = new RtcListEntry(null);
    private RtcListEntry rtcOperationListLast = rtcOperationListHead;

    private RtcListEntryCache rtcListEntryCache;
    private boolean ops;

    private final TrimmingMap rtcHandleEntryMap;  //should be expand to do some other stuff.  move part of the BaseHandle stuff into here
    
    private Object trigger;

    public RtcOperationList(boolean isMultiEngineMode) {
        rtcListEntryCache = new RtcListEntryCache();
        ops = false;
        trigger = null;
        //if (isMultiEngineMode)
        rtcHandleEntryMap = new TrimmingMap();
    }

    public boolean hasOps() {
        return ops;
    }

    public boolean isEmpty() {
        return rtcOperationListHead.next == null;
    }

    public Object getTrigger() {
    	return trigger;
    }
    
    public void setTrigger(Object trigger) {
    	this.trigger = trigger;
    }
    
    public void saveAndClearMutableDirtyBitArray(BaseHandle handle, int[] dirtyBitArray) {
        if(rtcHandleEntryMap == null) {
            if (dirtyBitArray != null) {
                for(int i=0; i < dirtyBitArray.length; i++) {
                    dirtyBitArray[i] = 0;
                }
            }
        }
        else {
            RtcListEntry rtcListEntry = (RtcListEntry) rtcHandleEntryMap.get(handle);
            if (rtcListEntry == null) {
                if (dirtyBitArray != null) {
                    for(int i=0; i < dirtyBitArray.length; i++) {
                        dirtyBitArray[i] = 0;
                    }
                }
                return;
            }
            if(rtcListEntry.mutableDirtyBitArray == null) {
                if (dirtyBitArray == null) {
                    return;
                }
                rtcListEntry.mutableDirtyBitArray = new int[dirtyBitArray.length];
            }
            if (dirtyBitArray != null) {
                for(int i=0; i < dirtyBitArray.length; i++) {
                    rtcListEntry.mutableDirtyBitArray[i] |= dirtyBitArray[i];
                    dirtyBitArray[i] = 0;
                }
            }
        }           
    }

    public void setRtcTouchHandle(BaseHandle handle) {
        if(!handle.rtcAlreadyIncluded()) {
            RtcListEntry rtcListEntry = rtcListEntryCache.get(handle);
            rtcOperationListLast.next = rtcListEntry;
            rtcOperationListLast = rtcOperationListLast.next;
            if(rtcHandleEntryMap != null)
                rtcHandleEntryMap.put(handle, rtcListEntry);
            handle.touch();
        }
    }

    public void setRtcAsserted(BaseHandle handle) {
        ops = true;
        if(handle.rtcAlreadyIncluded()) {
            handle.setRtcAsserted();
        }
        else {
            RtcListEntry rtcListEntry = rtcListEntryCache.get(handle);
            rtcOperationListLast.next = rtcListEntry;
            rtcOperationListLast = rtcOperationListLast.next;
            if(rtcHandleEntryMap != null)
                rtcHandleEntryMap.put(handle, rtcListEntry);
            handle.setRtcAsserted();
        }
    }

    public void setRtcModified(BaseHandle handle) {
        ops = true;
        if(handle.rtcAlreadyIncluded()) {
            if(!handle.isRtcModified()) {
                handle.setRtcModified();
            }
        }
        else {
            RtcListEntry rtcListEntry = rtcListEntryCache.get(handle);
            rtcOperationListLast.next = rtcListEntry;
            rtcOperationListLast = rtcOperationListLast.next;
            if(rtcHandleEntryMap != null)
                rtcHandleEntryMap.put(handle, rtcListEntry);
            handle.setRtcModified();
        }
    }

    public void setRtcDeleted(BaseHandle handle) {
        ops = true;
        if(handle.rtcAlreadyIncluded()) {
            handle.setRtcDeleted();
        }
        else {
            RtcListEntry rtcListEntry = rtcListEntryCache.get(handle);
            rtcOperationListLast.next = rtcListEntry;
            rtcOperationListLast = rtcOperationListLast.next;
            if(rtcHandleEntryMap != null)
                rtcHandleEntryMap.put(handle, rtcListEntry);  //actually, done need
            handle.setRtcDeleted();
        }
    }

    public void setRtcReverseRef(BaseHandle handle) {
        ops = true;
        if(handle.rtcAlreadyIncluded()) {
            handle.setRtcReverseRef();
        }
        else {
            RtcListEntry rtcListEntry = rtcListEntryCache.get(handle);
            rtcOperationListLast.next = rtcListEntry;
            rtcOperationListLast = rtcOperationListLast.next;
            if(rtcHandleEntryMap != null)
                rtcHandleEntryMap.put(handle, rtcListEntry);
            handle.setRtcReverseRef();
        }
    }

    public void setRtcContainerRef(BaseHandle handle) {
        ops = true;
        if(handle.rtcAlreadyIncluded()) {
            handle.setRtcContainerRef();
        }
        else {
            RtcListEntry rtcListEntry = rtcListEntryCache.get(handle);
            rtcOperationListLast.next = rtcListEntry;
            rtcOperationListLast = rtcOperationListLast.next;
            if(rtcHandleEntryMap != null)
                rtcHandleEntryMap.put(handle, rtcListEntry);
            handle.setRtcContainerRef();
        }
    }

    public void setRtcStateChanged(BaseHandle handle) {
        ops = true;
        if(handle.rtcAlreadyIncluded()) {
            handle.setRtcStateChanged();
        }
        else {
            RtcListEntry rtcListEntry = rtcListEntryCache.get(handle);
            rtcOperationListLast.next = rtcListEntry;
            rtcOperationListLast = rtcOperationListLast.next;
            if(rtcHandleEntryMap != null)
                rtcHandleEntryMap.put(handle, rtcListEntry);
            handle.setRtcStateChanged();
        }
    }

    void setRtcScheduled(BaseHandle handle) {
        ops = true;
        if(handle.rtcAlreadyIncluded()) {
            handle.setRtcScheduled();
        }
        else {
            RtcListEntry rtcListEntry = rtcListEntryCache.get(handle);
            rtcOperationListLast.next = rtcListEntry;
            rtcOperationListLast = rtcOperationListLast.next;
            if(rtcHandleEntryMap != null)
                rtcHandleEntryMap.put(handle, rtcListEntry);
            handle.setRtcScheduled();
        }
    }

    public void reset() {
        ops = false;
        trigger = null;
        if(rtcHandleEntryMap != null)
            rtcHandleEntryMap.clear();
        RtcListEntry curr = rtcOperationListHead.next;
        if(curr == null) return;
        do {
            curr.handle.rtcClearAll();
            curr.recycle();
            curr = curr.next;
        } while(curr != null);
        rtcOperationListHead.next = null;
        rtcOperationListLast = rtcOperationListHead;
    }

    public String toString() {
        RtcListEntry curr = rtcOperationListHead.next;
        if(curr == null) return "";
        StringBuffer sb = new StringBuffer();
        do {
            sb.append("[Object=").append(curr.handle.getObject()).append(", RtcOps=").append(Integer.toBinaryString(curr.handle.rtcStatus)).append("]");
            curr = curr.next;
        } while(curr != null);
        return sb.toString();
    }

    public Iterator iterator() {
        return new HandleIterator();
    }

    public Iterator nonClearingIterator() {
        return new NonClearingHandleIterator();
    }

    public Iterator RtcListEntryIterator() {
        return new RtcListEntryIterator();
    }

    class HandleIterator implements Iterator {
        RtcListEntry currPointer = rtcOperationListHead.next;

        public void remove() {
            throw new RuntimeException("HandleIterator.remove() is not implemented");
        }

        public Object next() {
            BaseHandle handle = currPointer.handle;
            currPointer.recycle();
            currPointer = currPointer.next;
            return handle;
        }

        public boolean hasNext() {
            if(currPointer == null) {  //reset everything
                ops = false;
                if(rtcHandleEntryMap != null)
                    rtcHandleEntryMap.clear();
                rtcOperationListHead.next = null;
                rtcOperationListLast = rtcOperationListHead;
                return false;
            }
            else
                return true;
        }
    }

    class NonClearingHandleIterator implements Iterator {
        RtcListEntry currPointer = rtcOperationListHead.next;

        public void remove() {
            throw new RuntimeException("NonClearingHandleIterator.remove() is not implemented");
        }

        public Object next() {
            BaseHandle handle = currPointer.handle;
//            currPointer.recycle();
            currPointer = currPointer.next;
            return handle;
        }

        public boolean hasNext() {
            if(currPointer == null) {  //reset everything
//                ops = false;
//                rtcOperationListHead.next = null;
//                rtcOperationListLast = rtcOperationListHead;
                return false;
            }
            else
                return true;
        }
    }

    class RtcListEntryIterator implements Iterator {
        RtcListEntry currPointer = rtcOperationListHead.next;

        public void remove() {
            throw new RuntimeException("NonClearingHandleIterator.remove() is not implemented");
        }

        public Object next() {
            RtcListEntry ret = currPointer;
            currPointer = currPointer.next;
            return ret;
        }

        public boolean hasNext() {
            if(currPointer == null) {  //reset everything
                ops = false;
                if(rtcHandleEntryMap != null)
                    rtcHandleEntryMap.clear();
                rtcOperationListHead.next = null;
                rtcOperationListLast = rtcOperationListHead;
                return false;
            }
            else
                return true;
        }
    }


    static public class RtcListEntry {
        public BaseHandle handle;
        public int[] mutableDirtyBitArray;
        protected RtcListEntry next;
        public void recycle() {}

        public RtcListEntry(BaseHandle _handle) {
            handle = _handle;
        }

    }

    static class RtcListEntryCache {
        final static int MAX_CACHE_SIZE = Integer.parseInt(System.getProperty("com.tibco.cep.kernel.rtcList.cacheSize", "200"));

        RtcListEntryEx head;
        int size;
        int created;

        public RtcListEntryCache() {
            head = new RtcListEntryEx(null);
            size = 0;
            created = 0;
        }

        public RtcListEntry get(BaseHandle handle) {
            if(size == 0) {
                if(created < MAX_CACHE_SIZE) {
                    created++;
                    return new RtcListEntryEx(handle);
                }
                else
                    return new RtcListEntry(handle);
            }
            RtcListEntryEx ret = head.cacheNext;
            head.cacheNext = head.cacheNext.cacheNext;
            ret.reset(handle);
            size--;
            return ret;
        }

        void put(RtcListEntryEx e) {
            e.cacheNext = head.cacheNext;
            head.cacheNext = e;
            size++;
        }

        class RtcListEntryEx extends RtcListEntry {
            RtcListEntryEx cacheNext;
            RtcListEntryEx(BaseHandle _handle) {
                super(_handle);
            }
            protected void reset(BaseHandle _handle) {
                handle = _handle;
                next = null;
            }
            public void recycle(){
                handle = null;
                mutableDirtyBitArray = null;
                put(this);
            }
        }
    }
    
    static private class TrimmingMap {
       private byte Clear_Count_Threshold = 4;
       private float Sparse_Clear_Fraction = .5f;
       private float Resize_Fraction = .5f;
       
       private HashMap map = new HashMap();
       private int maxSize = 0;
       private byte sparseClearCount = 0;
        
       public Object get(Object key) {
           return map.get(key);
       }

       public void put(Object key, Object value) {
           if(map.put(key, value) == null) {
               ++maxSize;
           }
       }

       public void clear() {
           if (!map.isEmpty()) {
               //sparse clear is clearing the map when it holds less than half the max size
               if (map.size() < maxSize * Sparse_Clear_Fraction) {
                   ++sparseClearCount;
               }
               else {
                   sparseClearCount = 0;
               }

               //shrink the map if Clear_Count_Threshold non-empty sparse clears in a row
               if (sparseClearCount >= Clear_Count_Threshold) {
                   maxSize *= Resize_Fraction;
                   //instead of clearing, replace with a new map with smaller capacity
                   map = new HashMap(maxSize);
                   sparseClearCount = 0;
               }
               else {
                   map.clear();
               }
           }
       }
   }
}
