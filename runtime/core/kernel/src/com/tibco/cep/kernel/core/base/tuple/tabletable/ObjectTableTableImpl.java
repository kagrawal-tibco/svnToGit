package com.tibco.cep.kernel.core.base.tuple.tabletable;

import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.TableIterator;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Oct 8, 2009
 * Time: 4:06:18 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class ObjectTableTableImpl implements ObjectTableTable
{
    protected transient ObjectEntry[]  table;
    protected int threshold;
    protected int numEntry;

    public ObjectTableTableImpl() {
        _reset();
    }

    public void reset() {
        _reset();
    }

    private void _reset() {
        numEntry = 0;
        table= new ObjectEntry[JoinTable.DEFAULT_INITIAL_CAPACITY];
        threshold  = (int)(JoinTable.DEFAULT_INITIAL_CAPACITY * JoinTable.DEFAULT_LOAD_FACTOR);
    }

    public boolean isEmpty() {
        return numEntry == 0;
    }

    public int size() {
        return numEntry;
    }

    public void lock() {}

    public void unlock() {}

    public TableIterator iterator() {
        if(numEntry == 0) return NULL_ITER;
        else return new ObjectEntryIterator(this);
    }

    protected void resizeTable(int newCapacity) {
        int oldCapacity = table.length;
        if (oldCapacity == JoinTable.MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        ObjectEntry[] newTable = new ObjectEntry[newCapacity];
        for (int j = 0; j < table.length; j++) {
            ObjectEntry e = table[j];
            if (e != null) {
                table[j] = null;
                do {
                    ObjectEntry next = e.next;
                    int i = JoinTable.indexFor(e.objHandle.hashCode(), newCapacity);
                    e.next = newTable[i];
                    newTable[i] = e;
                    e = next;
                } while (e != null);
            }
        }
        table=newTable;
        //threshold = (int)(newCapacity * container.getLoadFactor());
        threshold = (int)(newCapacity * JoinTable.DEFAULT_LOAD_FACTOR);
    }

    static protected class ObjectEntry
    {
        protected final Handle objHandle;
        protected ObjectEntry next;

        ObjectEntry(Handle _objHandle,  ObjectEntry _next) {
            objHandle = _objHandle;
            next      = _next;
        }
    }

    static public class ObjectEntryIterator implements TableIterator
    {
        protected ObjectEntry m_cursor;
        protected int          m_currIndex;
        protected Handle[]     m_retObject;
        protected ObjectTableTableImpl container;

        protected ObjectEntryIterator(ObjectTableTableImpl containerTable) {
            init(containerTable);
        }

        protected void init(ObjectTableTableImpl containerTable) {
            container = containerTable;
            m_currIndex = 0;
            m_cursor    = toNextTableEntry();
            if(m_cursor != null) m_retObject = new Handle[1];
        }

        private ObjectEntry toNextTableEntry() {
            while(m_currIndex != container.table.length) {
                if(container.table[m_currIndex] != null) {
                    return container.table[m_currIndex++];
                }
                m_currIndex++;
            }
            return null;
        }

        public boolean hasNext() {
            return m_cursor != null;
        }

        public Handle[] next() {
            m_retObject[0] = m_cursor.objHandle;
            advanceCursor();
            return m_retObject;
        }

        protected void advanceCursor() {
            m_cursor  = m_cursor.next;
            if(m_cursor == null) {
                m_cursor = toNextTableEntry();
            }
        }

        public void remove() {
            throw new RuntimeException("ObjectEntryIterator.remove() is not implemented, should use ObjectTable.remove() to remove a tuple");
        }
    }

}