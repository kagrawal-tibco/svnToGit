package com.tibco.cep.kernel.core.base.tuple.tabletable;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.TableIterator;
import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.helper.IdentifierUtil;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
* User: aamaya
* Date: Oct 7, 2009
* Time: 7:20:23 PM
* To change this template use File | Settings | File Templates.
*/
public class ObjectTableWithKeyTableImpl extends ObjectTableTableImpl implements ObjectTableWithKeyTable
{
    protected transient ObjectKeyEntry[] keyTable;
    protected transient int              keyTableNumEntry;
    protected transient int              keyTableThreshold;

    public ObjectTableWithKeyTableImpl() {
        super();
        _reset();
    }
    
    public void reset() {
        super.reset();
        _reset();
    }
    
    private void _reset() {
        keyTable=new ObjectKeyEntry[JoinTable.DEFAULT_INITIAL_CAPACITY];
        threshold=keyTableThreshold=((int)(JoinTable.DEFAULT_INITIAL_CAPACITY * JoinTable.DEFAULT_LOAD_FACTOR));
        numEntry=keyTableNumEntry=0;
    }
    
    public Handle[] getAllHandles(int key) {  //this function is called another thread, need to sync the key table when read
        TableIterator ite = keyIterator(key);
        if(ite.hasNext()) {
            Handle[] ret = new Handle[64];
            int i = 0;
            //synchronized(this) {
            do {
                if(i == ret.length) { //resize
                    Handle[] newRet = new Handle[ret.length * 2];
                    System.arraycopy(ret, 0, newRet, 0, ret.length);
                    ret = newRet;
                }
                ret[i] = ((Handle[])ite.next())[0];
                i++;
            } while(ite.hasNext());
            //make sure it ends with a null value
            if(i < ret.length) ret[i] = null;
            return ret;
        } else {
            return NULL_ARR;
        }
    }

    public boolean add(Handle objHandle, int key) {
        int indexTable = JoinTable.indexFor(objHandle.hashCode(), table.length);
        for (ObjectEntry e = table[indexTable]; e != null; e = e.next) {
            if (e.objHandle == objHandle) {
                if(((ObjectKeyEntry)e).key != key) {
                    throw new RuntimeException("Program Error: key stored is not equal to new key");
                }
                return false;
            }
        }
        ObjectKeyEntry newEntry = new ObjectKeyEntry(objHandle, table[indexTable], key);
        table[indexTable] = newEntry;
        if (numEntry++ >= threshold)
            resizeTable(2* table.length);

        //store in keyTable
        int indexKeyTable = JoinTable.indexFor(key, keyTable.length);
        //synchronized(this) {
        newEntry.nextKeyEntry = keyTable[indexKeyTable];
        keyTable[indexKeyTable] = newEntry;
        if(++ keyTableNumEntry>= keyTableThreshold) {
            resizeKeyTable(2* keyTable.length);
        }
        //}
        return true;
    }

    public boolean remove(Handle objHandle) {
        int i = JoinTable.indexFor(objHandle.hashCode(), table.length);
        ObjectEntry prev = table[i];
        ObjectEntry e = prev;

        while (e != null) {
            ObjectEntry next = e.next;
            if (e.objHandle == objHandle) {
                numEntry--;
                _removeKey((ObjectKeyEntry)e);
                if (prev == e)
                    table[i] = next;
                else
                    prev.next = next;
                return true;
            }
            prev = e;
            e = next;
        }
        return false;
    }

    private void _removeKey(ObjectKeyEntry entry) {
        int i = JoinTable.indexFor(entry.key, keyTable.length);
        ObjectKeyEntry prev = keyTable[i];
        ObjectKeyEntry e = prev;

        while (e != null) {
            ObjectKeyEntry next = e.nextKeyEntry;
            if (e == entry) {
                --keyTableNumEntry;
                //synchronized(this) {
                if (prev == e)
                    keyTable[i] = next;
                else
                    prev.nextKeyEntry = next;
                //}
                return;
            }
            prev = e;
            e = next;
        }
        throw new RuntimeException("Program Error: Can't find ObjectKeyEntry for removal");
    }

    public TableIterator keyIterator(int key) {
        if(numEntry == 0) return NULL_ITER;
        else return ObjectKeyEntryIterator.newInstance(key, this);
    }

    void resizeKeyTable(int newCapacity) {
        int oldCapacity = keyTable.length;
        if (oldCapacity == JoinTable.MAXIMUM_CAPACITY) {
            keyTableThreshold=Integer.MAX_VALUE;
            return;
        }
        ObjectKeyEntry[] newTable = new ObjectKeyEntry[newCapacity];
        for (int j = 0; j < keyTable.length; j++) {
            ObjectKeyEntry e = keyTable[j];
            if (e != null) {
                keyTable[j] = null;
                do {
                    ObjectKeyEntry nextKeyEntry = e.nextKeyEntry;
                    int i = JoinTable.indexFor(e.key, newCapacity);
                    e.nextKeyEntry = newTable[i];
                    newTable[i] = e;
                    e = nextKeyEntry;
                } while (e != null);
            }
        }
        keyTable=newTable;
        //keyTableThreshold=((int)(newCapacity * container.getLoadFactor()));
        keyTableThreshold=((int)(newCapacity * JoinTable.DEFAULT_LOAD_FACTOR));
    }

    public String contentListForm(JoinTable container) {
        String idrStr = "";
        if(container.getIdentifiers() != null) {
            idrStr = IdentifierUtil.toString(container.getIdentifiers());
        }
        String ret = ">>> ObjectTable id=" + container.getTableId() + " length=" + table.length +
                " numEntry=" + numEntry + " Identifiers= " + idrStr + " <<<" + Format.BRK;
        ret += "\t Objects :" + Format.BRK + "\t\t";
        for(int i = 0; i < table.length; i++) {
            if(table[i] != null) {
                ObjectEntry cursor = table[i];
                while(cursor != null) {
                    ret += "[Handle hashCode:" + cursor.objHandle.hashCode() + ", key:" + ((ObjectKeyEntry)cursor).key + ", " + ((BaseHandle)cursor.objHandle).printInfo() + "] ";
                    cursor = cursor.next;
                }
            }
        }
        ret += Format.BRK;
        return ret;
    }

    public String contentHashForm(JoinTable container) {
        String idrStr = "";
        if(container.getIdentifiers() != null) {
            idrStr = IdentifierUtil.toString(container.getIdentifiers());
        }
        String ret = ">>> ObjectTable id=" + container.getTableId() + " Identifiers= " + idrStr + Format.BRK;
        ret += "\tObjectTable length=" + table.length + " numEntry=" + numEntry;
        ret += Format.BRK + "\tKeyTable length=" + keyTable.length + " numEntry=" + keyTableNumEntry;
        ret += " <<<" + Format.BRK;
        for(int i = 0; i < table.length; i++) {
            if(table[i] != null) {
                ObjectEntry cursor = table[i];
                ret += "\tObjectTable[" + i + "]:" + Format.BRK + "\t\t";
                while(cursor != null) {
                    ret += "[Handle hashCode:" + cursor.objHandle.hashCode() + ", " + ((BaseHandle)cursor.objHandle).printInfo() + "] ";
                    cursor = cursor.next;
                }
                ret += Format.BRK;
            }
        }
        for(int i = 0; i < keyTable.length; i++) {
            if(keyTable[i] != null) {
                ObjectKeyEntry cursor = keyTable[i];
                ret += "\tKeyTable[" + i + "]:" + Format.BRK + "\t\t";
                while(cursor != null) {
                    ret += "[Handle key:" + cursor.key + ", " + ((BaseHandle)cursor.objHandle).printInfo() + "] ";
                    cursor = cursor.nextKeyEntry;
                }
                ret += Format.BRK;
            }
        }
        ret += Format.BRK;
        return ret;
    }
    
    public MigrationIterator<KeyTuple> migrationIterator() {
        if(numEntry == 0) return MigrationIterator.NULL_ITER;
        return new ObjectMigrationIterator(this);
    }
    
    private static class ObjectMigrationIterator extends ObjectEntryIterator implements MigrationIterator<KeyTuple>{
        private ObjectMigrationIterator(ObjectTableTableImpl containerTable) {
            super(containerTable);
        }

        public KeyTuple next_migrate() {
            ObjectKeyEntry ret =  (ObjectKeyEntry) m_cursor;
            advanceCursor();
            return new KeyTuple(ret.key, ret.objHandle);
        }
    }

    static public class ObjectKeyEntry extends ObjectEntry
    {
        int key;
        ObjectKeyEntry nextKeyEntry;
        ObjectKeyEntry(Handle _objHandle, ObjectEntry _next, int _key) {
            super(_objHandle, _next);
            key = _key;
        }
    }

    static public class ObjectKeyEntryIterator implements TableIterator
    {
        protected ObjectKeyEntry m_cursor;
        protected int             m_key;
        protected Handle[]        m_retObject;

        protected ObjectKeyEntryIterator(int key, ObjectKeyEntry startCursor) {
            m_key = key;
            m_cursor = startCursor;
            m_retObject = new Handle[1];
        }
        
        protected static TableIterator newInstance(int key, ObjectTableWithKeyTableImpl container) {
            ObjectKeyEntry cursor = findStart(key, container);
            if(cursor == null) return NULL_ITER;
            else return new ObjectKeyEntryIterator(key, cursor);
        }
        
        protected static ObjectKeyEntry findStart(int key, ObjectTableWithKeyTableImpl container) {
            return findNext(key, container.keyTable[JoinTable.indexFor(key, container.keyTable.length)]);
        }
        
        protected static ObjectKeyEntry findNext(int key, ObjectKeyEntry cursor) {
            while(cursor != null && cursor.key != key) {
                cursor = cursor.nextKeyEntry;
            }
            return cursor;
        }

        public boolean hasNext() {
            return m_cursor != null;
        }

        public Handle[] next() {
            m_retObject[0] = m_cursor.objHandle;
            m_cursor = findNext(m_key, m_cursor.nextKeyEntry);
            return m_retObject;
        }

        public void remove() {
            throw new RuntimeException("KeyIterator.remove() is not implemented, should use ObjectTable.remove() to remove a object");
        }
    }
}