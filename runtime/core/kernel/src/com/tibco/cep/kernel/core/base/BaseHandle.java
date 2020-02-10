package com.tibco.cep.kernel.core.base;

import java.util.Iterator;
import java.util.LinkedList;

import com.tibco.cep.kernel.core.base.tuple.TupleElement;
import com.tibco.cep.kernel.core.base.tuple.TupleRow;
import com.tibco.cep.kernel.helper.ShortHashList;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.TypeInfo;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 26, 2006
 * Time: 1:10:41 AM
 * To change this template use File | Settings | File Templates.
 */
abstract public class BaseHandle implements TupleElement, Handle, BaseHandleWrapper {

    private static final byte DIRTY          = 0x01;      //for each rule action execution
    private static final byte ASSERTED       = 0x02;      //tell whether the object is asserted
    private static final byte MARKED_DELETE  = 0x04;      //tell whether the object will be retracted
    private static final byte MARKED_ASSERT  = 0x08;      //tell whether the object will be assert

    private static final byte EVICT_BEEN_EVICTED_ = 0x10;    //has been evicted earlier
    private static final byte IN_RETE             = 0x20;    //inside rete

    protected static final byte RETRACTED    = 0x40;      //tell whether the object is retracted
    //indicates that a property was actually modified and needs to be persisted vs the DIRTY flag which is set in the parent by
    //contained children to force rete re-evaluation even if the parent doesn't actually need to be persisted
    protected static final byte PROPERTY_MODIFIED = (byte)0x80;      //tell whether the object is retracted
    
    private static final byte ASSERTED_OR_RETRACTED_OR_MARKED_DELETE = (byte)(ASSERTED | MARKED_DELETE | RETRACTED);
    private static final byte MARKED_ASSERT_OR_ASSERTED_OR_RETRACTED_OR_MARKED_DELETE = (byte)(MARKED_ASSERT | ASSERTED | MARKED_DELETE | RETRACTED);
    private static final byte RETRACTED_OR_MARKED_DELETE = (byte)(RETRACTED | MARKED_DELETE );
    private static final byte DIRTY_OR_RETRACTED_OR_MARKED_DELETE = (byte)(DIRTY | RETRACTED | MARKED_DELETE );

    private static final byte RTC_ASSERTED      = 0x01;    //asserted in current RTC
    private static final byte RTC_MODIFIED      = 0x02;    //modified in current RTC
    private static final byte RTC_DELETED       = 0x04;    //deleted in current RTC
    private static final byte RTC_REVERSE_REF   = 0x08;    //reverse ref is modified in current RTC
    private static final byte RTC_CONTAINER_REF = 0x10;    //container ref is modified in current RTC
    private static final byte RTC_TOUCHED       = 0x20;    //the handle is being touched in current RTC
    private static final byte RTC_STATE_CHANGED = 0x40;    //state changes in current RTC
    private static final byte RTC_SCHEDULED     = 0x40;    //scheduled in current RTC

    private static final byte RTC_ALL                       = (byte) (RTC_ASSERTED | RTC_MODIFIED | RTC_DELETED | RTC_REVERSE_REF | RTC_CONTAINER_REF | RTC_TOUCHED | RTC_STATE_CHANGED | RTC_SCHEDULED);
    private static final byte RTC_ASSERTED_DELETED          = (byte) (RTC_ASSERTED | RTC_DELETED);
    private static final byte RTC_ASSERTED_ANY_MODIFICATION = (byte) (RTC_ASSERTED | RTC_MODIFIED| RTC_REVERSE_REF | RTC_CONTAINER_REF);
    private static final byte RTC_ANY_MODIFICATION          = (byte) (RTC_MODIFIED| RTC_REVERSE_REF | RTC_CONTAINER_REF);



    private ShortHashList  m_tuples    = null;
    protected byte         status      = 0;
    public  int            agendaCount = 0;

    protected TypeInfo typeInfo;
    protected byte     rtcStatus   = 0;

	 protected BaseHandle(TypeInfo _typeInfo) {
        typeInfo = _typeInfo;
    }
	
    /**
     * Get the working memory for which this handle is belonged to
     * @return the working memory for which this handle is belonged to
     */
    public WorkingMemory getWorkingMemory() {
        return typeInfo.getWorkingMemory();
    }

    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(TypeInfo info) {  //package protected, shouldn't expose
        typeInfo = info;
    }

//    public Object setRef(Object obj) {
//        return obj;
//    }

    public boolean isInRete() {
        return (status & IN_RETE) != 0;
    }

    public void setInRete() {
        status |= IN_RETE;
    }

    public void clearInRete() {
        status &= ~IN_RETE;
    }

    public boolean isEvicted() {
        return (status & EVICT_BEEN_EVICTED_) != 0;
    }

    public void evict() {
        status |= EVICT_BEEN_EVICTED_;
    }

    public boolean rtcAlreadyIncluded() {
//        return (rtcStatus & RTC_ALL) != 0;
        return rtcStatus != 0;
    }

    public void rtcClearAll() {
        rtcStatus = 0;
    }

    public boolean isRtcAsserted_OR_AnyModification() {
        return (rtcStatus & RTC_ASSERTED_ANY_MODIFICATION) != 0;
    }

    public boolean isRtcAsserted_AND_Deleted() {
        return ((rtcStatus & RTC_ASSERTED_DELETED) == RTC_ASSERTED_DELETED);
    }

    public boolean isRtcAsserted() {
        return (rtcStatus & RTC_ASSERTED) != 0;
    }

    public boolean isRtcModified() {
        return (rtcStatus & RTC_MODIFIED) != 0;
    }

    public boolean isRtcAnyModification() {
        return (rtcStatus & RTC_ANY_MODIFICATION) != 0;
    }

    public boolean isRtcAnyModificationButReverseRef() {
        return (rtcStatus & RTC_ANY_MODIFICATION & ~RTC_REVERSE_REF) != 0;
    }

    public boolean isRtcDeleted() {
        return (rtcStatus & RTC_DELETED) != 0;
    }

    public boolean isTouched() {
        return (rtcStatus & RTC_TOUCHED) != 0;
    }

    public boolean isRtcStateChanged() {
        return (rtcStatus & RTC_STATE_CHANGED) != 0;
    }

    public boolean isRtcScheduled() {
        return (rtcStatus & RTC_SCHEDULED) != 0;
    }

    public boolean isRtcContainerRefModified() {
        return (rtcStatus & RTC_CONTAINER_REF) != 0;
    }

    public boolean isRtcReverseRefModified() {
        return (rtcStatus & RTC_REVERSE_REF) != 0;
    }

    protected void setRtcReverseRef() {
        rtcStatus |= RTC_REVERSE_REF;
    }

    protected void setRtcContainerRef() {
        rtcStatus |= RTC_CONTAINER_REF;
    }

    protected void setRtcModified() {
        rtcStatus |= RTC_MODIFIED;
    }

    protected void setRtcAsserted() {
        rtcStatus |= RTC_ASSERTED;
    }

    protected void setRtcDeleted() {
        rtcStatus |= RTC_DELETED;
    }

    protected void touch() {
        rtcStatus |= RTC_TOUCHED;
    }

    protected void setRtcStateChanged() {
        rtcStatus |= RTC_STATE_CHANGED;
    }

    protected void setRtcScheduled() {
        rtcStatus |= RTC_SCHEDULED;
    }

    ////////////////////////////////////  non RTC operation /////////////////////////////////////////////////////////////
    public boolean okMarkAssert() {
        if((status & MARKED_ASSERT_OR_ASSERTED_OR_RETRACTED_OR_MARKED_DELETE) != 0) return false;
        status |= MARKED_ASSERT;
        return true;
    }
    
    public boolean isMarkedAssert() {
        return (status & MARKED_ASSERT) != 0;
    }
	
    public boolean isAsserted() {
        return (status & ASSERTED) != 0;
    }

    public void setAsserted() {
        status |= ASSERTED;
    }

    public void clearAsserted() {
        status &= ~ASSERTED;
    }

    public boolean okMarkDirty() {
        if((status & DIRTY_OR_RETRACTED_OR_MARKED_DELETE) != 0) return false;
        status |= DIRTY;
        return true;
    }
    
    public boolean isDirty() {
    	return (status & DIRTY) != 0;
    }

    public void clearDirty() {
        status &= ~DIRTY;
        status &= ~PROPERTY_MODIFIED;
    }
    
    public void setPropertyModified() {
    	status |= PROPERTY_MODIFIED;
    }
    
    public boolean isPropertyModified() {
    	return (status & PROPERTY_MODIFIED) != 0;
    }

    public boolean isRetracted() {
        return (status & RETRACTED) != 0;
    }

    public void setRetracted() {
        status |= RETRACTED;
    }

    public boolean okMarkDelete() {
        if((status & RETRACTED_OR_MARKED_DELETE) != 0) return false;
        status |= MARKED_DELETE;
        return true;
    }

    public boolean isMarkedDelete() {
        return (status & MARKED_DELETE) != 0;
    }

    public boolean isRetracted_OR_isMarkedDelete() {
        return (status & RETRACTED_OR_MARKED_DELETE) != 0;
    }

    public boolean isAsserted_OR_isRetracted_OR_isMarkedDelete() {
        return (status & ASSERTED_OR_RETRACTED_OR_MARKED_DELETE) != 0;
    }

    public void copyStatus(BaseHandle handle) {
        handle.rtcStatus = this.rtcStatus;
    }

    public String printInfo() {
    	//toBinaryString coerces to int so mask off all bits that aren't part of a byte
        return "object:" + getObject() + ", status:" + Integer.toBinaryString(status & 0xFF);
    }

    //function specific for Tuple interface++
    public void registerTupleRow(short tableId, TupleRow row) {
        synchronized(this) {
            if(m_tuples == null) {
                m_tuples = new ShortHashList();
            }
            m_tuples.add(tableId, row);
        }
    }

    public void removeFromTables() {
        ShortHashList temp = null;
        synchronized (this) {
            if (m_tuples != null) {
                temp = m_tuples;
                m_tuples = null;
            }

        }
        if(temp != null) {
            Iterator ite = temp.listIterator();
            while(ite.hasNext()) {
                ShortHashList.Entry e = (ShortHashList.Entry) ite.next();
                short tableId = ((ShortHashList.EntryHead)e).key;
                do {
                    ((TupleRow)e.obj).removeTupleFromTable(tableId);
                    e = e.nextMatch;
                } while(e != null);
            }
        }
    }

    public void removeFromTable(short tableId) {      //***** need to synchronized
        LinkedList list = null;
        synchronized(this) {
            if( m_tuples != null) {
                ShortHashList.Entry e = m_tuples.removelist(tableId);
                if(m_tuples.uniqueKeys() == 0)
                    m_tuples = null;
                if(e != null) {
                    list = new LinkedList();
                    do {
                        list.add(e.obj);
                        e = e.nextMatch;
                    }  while(e != null);
                }
            }
        }
        if(list != null) {
            Iterator ite = list.iterator();
            while(ite.hasNext()) {
                TupleRow row = (TupleRow) ite.next();
                row.removeTupleFromTable(tableId);
            }
        }
    }

    public void unregisterTupleRow(short tableId, TupleRow row) {
        synchronized(this) {
            if( m_tuples != null) {
                m_tuples.remove(tableId, row);
                if(m_tuples.uniqueKeys() == 0)
                    m_tuples = null;
            }
        }
    }

    public void unregisterTupleRows(short tableId) {
        synchronized(this) {
            if( m_tuples != null) {
                m_tuples.removelist(tableId);
                if(m_tuples.uniqueKeys() == 0)
                    m_tuples = null;
            }
        }
    }
    //function specific for Tuple interface--
    
    public BaseHandle getBaseHandle() {
        return this;
    }

    //Get sharing level of the type of this instance.  May be SHARED or UNSHARED.
    public EntitySharingLevel getSharingLevel() {
        return typeInfo.getLocalSharingLevel();
    }
    
    public byte getRTCStatus() {
    	return rtcStatus;
    }
}

//------------------- Assert flag --------------------------
//clearAsserted()
//	- ReteWM.fireRepeatEvent 	- OK
//
//
//
//setAsserted()
//	- ReteWM.loadObject 		- OK
//	- ReteWM.fireRepeatEvent 	- OK
//	- ReteWM.retrieveObject		- removed **
//	- ReteWM.assertHandleInternal	- OK
//	- ReteWm.loadEvictedHandle - Added
//
//
//isAsserted()  -- NOT USE NOW!!!
//	- ReteWM.unloadObject		- removed **
//	- ReteWm.unloadElement		- removed **
//	- ReteWM.unloadEvent		- removed **
//	- ReteWM.retractHandleInternal		- removed **
//
//
//ASSERTED_OR_RETRACTED_OR_MARKED_DELETE
//	isAsserted_OR_isRetracted_OR_isMarkedDelete()
//		- ReteWM.loadObject		- Added
//		- ReteWM.assertHandleInternal 	- OK
//
//
//MARKED_ASSERT_OR_ASSERTED_OR_RETRACTED_OR_MARKED_DELETE
//	okMarkAssert()
//		- OperationList.opAssertObj 	- OK
//
//
//
//
//BaseHandle - OK
//
//------------------- evict --------------------------
//isEvicted()
//	- ReteWM.loadObjectToAddedRule()		- OK
//	- ReteWM.retrieveObject()			- OK
//
//evict()
//	- ReteWM.evictHandle()				- OK
//	- DefaultDistributedCacheBasedStore.loadEvictedElementHandle()	- OK
//	- DefaultDistributedCacheBasedStore.loadEvictedEventHandle();	- OK
//
//
//------------------- putInRete -----------------------
//
//putInRete()
//	- ReteWM.loadObject
//	- ReteWM.reloadObject (remove first then add)
//	- ReteWM.fireRepeatEvent
//	- ReteWM.retrieveObject
//	- ReteWM.assertHandleInternal
