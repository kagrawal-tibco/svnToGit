package com.tibco.cep.kernel.helper;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 26, 2006
 * Time: 1:15:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShortHashList {
    static final int   DEFAULT_INITIAL_CAPACITY = 1;
    static final short MAXIMUM_CAPACITY = Short.MAX_VALUE;
    static final float DEFAULT_LOAD_FACTOR = 1.0f;

    transient EntryHead[] table;
//    transient int   numEntry;
    transient short uniqueKeys;
    transient short threshold;

    public ShortHashList() {
        threshold  = (short)(DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
        table = new EntryHead[DEFAULT_INITIAL_CAPACITY];
//        numEntry   = 0;
        uniqueKeys = 0;
    }

    public ShortHashList(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);

        // Find a power of 2 >= initialCapacity
        int capacity = 1;
        while (capacity < initialCapacity)
            capacity <<= 1;

        threshold = (short)(capacity * DEFAULT_LOAD_FACTOR);
        table = new EntryHead[capacity];

//        numEntry   = 0;
        uniqueKeys = 0;
    }

    //synchronized
    public int uniqueKeys() {
        return uniqueKeys;
    }

    static int indexFor(short key, int length) {
        int h = key;
        h += ~(h << 9);
        h ^=  (h >>> 14);
        h +=  (h << 4);
        h ^=  (h >>> 10);
        return h & (length-1);
    }

    //synchronized
    public void add(short key, Object obj) {
        int i = indexFor(key, table.length);

        EntryHead e = table[i];
        if(e == null) {
            EntryHead newEntry = new EntryHead(key, obj, null, null);
            table[i] = newEntry;
            uniqueKeys++;
        }
        else {
            boolean found = false;
            for(; e != null; e = e.nextUnmatch) {
                if(e.key == key) {
                    Entry newEntry = new Entry(obj, e.nextMatch);
                    e.nextMatch = newEntry;
//                    e.numEntries++;
                    found = true;
                    break;
                }
            }
            //not found and not inserted
            if (!found) {
                EntryHead newEntry = new EntryHead(key, obj, null, table[i]);
                table[i] = newEntry;
                uniqueKeys++;
            }
        }
//        numEntry++;

        //resize if unique HashKey is greater or equal to the threshold
        if(uniqueKeys > threshold) {
            resize(2 * table.length);
        }
    }

    //synchronized
    public boolean remove(short key, Object obj) {
        int i = indexFor(key, table.length);

        EntryHead e = table[i];
        EntryHead prevUnmatch = null;
        if(e != null) {
            for(; e != null; e = e.nextUnmatch) {
                if(e.key == key) {
                    //found the list
                    Entry curr = e;
                    Entry prev = null;
                    do {
                        if(curr.obj.equals(obj)) {
                            if(curr instanceof EntryHead) {
                                if(curr.nextMatch == null) {
                                    if(prevUnmatch == null)
                                        table[i] = ((EntryHead)curr).nextUnmatch;
                                    else
                                        prevUnmatch.nextUnmatch = ((EntryHead)curr).nextUnmatch;
                                    uniqueKeys--;
                                }
                                else {
                                    curr.obj = curr.nextMatch.obj;
                                    curr.nextMatch = curr.nextMatch.nextMatch;
                                }
                            }
                            else {
                                prev.nextMatch = curr.nextMatch;
                            }
                            return true;
                        }
                        prev = curr;
                        curr = curr.nextMatch;
                    }
                    while(curr != null);
                }
                prevUnmatch = e;
            }
        }
        return false;
    }

    //synchronized
    public Entry removelist(short key) {
        int i = indexFor(key, table.length);

        EntryHead e =  table[i];
        EntryHead prev = e;

        while(e != null) {
            if(e.key == key) {   //found list
                if(prev == e) {
                    table[i] = e.nextUnmatch;
                }
                else {
                    prev.nextUnmatch = e.nextUnmatch;
                }
                uniqueKeys--;
//                numEntry -= e.numEntries;
                return e;
            }
            prev = e;
            e = e.nextUnmatch;
        }
        return null;
    }

    public Iterator listIterator() {
        return new EntryHeadIterator();
    }

    //synchronized
    void resize(int newCapacity) {
        int oldCapacity = table.length;
        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Short.MAX_VALUE;
            return;
        }

        EntryHead[] newTable = new EntryHead[newCapacity];
        //transfer to new table here
        for(int j = 0; j < oldCapacity; j++) {
            EntryHead matchedColumn = table[j];
            if (matchedColumn != null) {
                table[j] = null;
                do {
                    EntryHead nextUnMatch = matchedColumn.nextUnmatch;
                    int i = indexFor(matchedColumn.key, newCapacity);
                    matchedColumn.nextUnmatch = newTable[i];
                    newTable[i] = matchedColumn;
                    matchedColumn = nextUnMatch;
                } while (matchedColumn != null);
            }
        }

        table = newTable;
        threshold = (short)(newCapacity * DEFAULT_LOAD_FACTOR);
    }

    public String contentHashForm() {
        String ret = new String(">>> ShortHashList lenght=" + table.length + " uniqueKeys=" + uniqueKeys +
                                /*" numEntry=" + numEntry + */"<<<" + Format.BRK);
        for(int i = 0; i < table.length; i++) {
            if(table[i] != null) {
                EntryHead hashColumn = table[i];
                ret += "\tTable[" + i + "]:" + Format.BRK;
                while(hashColumn != null) {
                    ret += "\t\t key=" + hashColumn.key + Format.BRK + "\t\t\t";
                    String strColumn = new String();
                    Entry eachEntry = hashColumn;
                    while(eachEntry != null) {
                        strColumn = strColumn + eachEntry.obj + " ";
                        eachEntry = eachEntry.nextMatch;
                    }
                    ret += strColumn;
                    hashColumn = hashColumn.nextUnmatch;
                    ret += Format.BRK ;
                }
            }
        }
        return ret;
    }

    static public class Entry {
        public Object obj;
        public Entry nextMatch;

        Entry(Object _obj, Entry match) {
            obj         = _obj;
            nextMatch   = match;
        }
    }

    static public class EntryHead extends Entry {
        final public short key;
        EntryHead   nextUnmatch;

        EntryHead(short _key, Object _obj, Entry match, EntryHead unmatch) {
            super(_obj, match);
            key         = _key;
            nextUnmatch = unmatch;
        }
    }

    class EntryHeadIterator implements Iterator {
        EntryHead  m_cursor;
        int        m_currIndex;

        EntryHeadIterator() {
            m_currIndex = 0;
            m_cursor = toNextTableEntry();
        }

        private EntryHead toNextTableEntry() {
            while(m_currIndex != table.length) {
                if(table[m_currIndex] != null) {
                    return table[m_currIndex++];
                }
                m_currIndex++;
            }
            return null;
        }

        public boolean hasNext() {
            return m_cursor != null;
        }

        public Object next() {
            EntryHead  ret = m_cursor;
            m_cursor  = m_cursor.nextUnmatch;
            if(m_cursor == null) {
                m_cursor = toNextTableEntry();
            }
            return ret;
        }

        public void remove() {
            throw new RuntimeException("EntryHeadIterator.remove() is not implemented, should use TupleTable.remove() to remove a tuple");
        }
    }

}
