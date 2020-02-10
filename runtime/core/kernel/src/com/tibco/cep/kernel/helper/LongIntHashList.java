package com.tibco.cep.kernel.helper;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Aug 28, 2007
 * To change this template use File | Settings | File Templates.
 */
public class LongIntHashList {
    static final int   DEFAULT_INITIAL_CAPACITY = 1;
    static final int MAXIMUM_CAPACITY = Integer.MAX_VALUE;
    static final float DEFAULT_LOAD_FACTOR = 1.0f;

    transient LongIntHashList.EntryHead[] table;
//    transient int   numEntry;
    transient long uniqueKeys;
    transient int threshold;

    public LongIntHashList() {
        threshold  = (int)(LongIntHashList.DEFAULT_INITIAL_CAPACITY * LongIntHashList.DEFAULT_LOAD_FACTOR);
        table = new LongIntHashList.EntryHead[LongIntHashList.DEFAULT_INITIAL_CAPACITY];
//        numEntry   = 0;
        uniqueKeys = 0;
    }

    public LongIntHashList(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        if (initialCapacity > LongIntHashList.MAXIMUM_CAPACITY)
            initialCapacity = LongIntHashList.MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);

        // Find a power of 2 >= initialCapacity
        int capacity = 1;
        while (capacity < initialCapacity)
            capacity <<= 1;

        threshold = (short)(capacity * LongIntHashList.DEFAULT_LOAD_FACTOR);
        table = new LongIntHashList.EntryHead[capacity];

//        numEntry   = 0;
        uniqueKeys = 0;
    }

    //synchronized
    public long uniqueKeys() {
        return uniqueKeys;
    }

    
    static int indexFor(long longKey, int intKey, int length) {
        return (((int)(longKey ^ (longKey >>> 32))) ^ intKey) & (length-1);
    }

    //synchronized
    public void add(long longKey, int intKey, Object obj) {
        int i = LongIntHashList.indexFor(longKey, intKey, table.length);

        LongIntHashList.EntryHead e = table[i];
        if(e == null) {
            LongIntHashList.EntryHead newEntry = new LongIntHashList.EntryHead(longKey, intKey, obj, null, null);
            table[i] = newEntry;
            uniqueKeys++;
        }
        else {
            boolean found = false;
            for(; e != null; e = e.nextUnmatch) {
                if(e.longKey == longKey && e.intKey == intKey) {
                    LongIntHashList.Entry newEntry = new LongIntHashList.Entry(obj, e.nextMatch);
                    e.nextMatch = newEntry;
//                    e.numEntries++;
                    found = true;
                    break;
                }
            }
            //not found and not inserted
            if (!found) {
                LongIntHashList.EntryHead newEntry = new LongIntHashList.EntryHead(longKey, intKey, obj, null, table[i]);
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
    public boolean remove(long longKey, int intKey, Object obj) {
        int i = LongIntHashList.indexFor(longKey, intKey, table.length);

        LongIntHashList.EntryHead e = table[i];
        LongIntHashList.EntryHead prevUnmatch = null;
        if(e != null) {
            for(; e != null; e = e.nextUnmatch) {
                if(e.longKey == longKey && e.intKey == intKey) {
                    //found the list
                    LongIntHashList.Entry curr = e;
                    LongIntHashList.Entry prev = null;
                    do {
                        if(curr.obj.equals(obj)) {
                            if(curr instanceof LongIntHashList.EntryHead) {
                                if(curr.nextMatch == null) {
                                    if(prevUnmatch == null)
                                        table[i] = ((LongIntHashList.EntryHead)curr).nextUnmatch;
                                    else
                                        prevUnmatch.nextUnmatch = ((LongIntHashList.EntryHead)curr).nextUnmatch;
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
    public LongIntHashList.Entry removelist(long longKey, int intKey) {
        int i = LongIntHashList.indexFor(longKey, intKey, table.length);

        LongIntHashList.EntryHead e =  table[i];
        LongIntHashList.EntryHead prev = e;

        while(e != null) {
            if(e.longKey == longKey && e.intKey == intKey) {   //found list
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
        return new LongIntHashList.EntryHeadIterator();
    }

    //synchronized
    void resize(int newCapacity) {
        int oldCapacity = table.length;
        if (oldCapacity == LongIntHashList.MAXIMUM_CAPACITY) {
            threshold = Short.MAX_VALUE;
            return;
        }

        LongIntHashList.EntryHead[] newTable = new LongIntHashList.EntryHead[newCapacity];
        //transfer to new table here
        for(int j = 0; j < oldCapacity; j++) {
            LongIntHashList.EntryHead matchedColumn = table[j];
            if (matchedColumn != null) {
                table[j] = null;
                do {
                    LongIntHashList.EntryHead nextUnMatch = matchedColumn.nextUnmatch;
                    int i = LongIntHashList.indexFor(matchedColumn.longKey, matchedColumn.intKey, newCapacity);
                    matchedColumn.nextUnmatch = newTable[i];
                    newTable[i] = matchedColumn;
                    matchedColumn = nextUnMatch;
                } while (matchedColumn != null);
            }
        }

        table = newTable;
        threshold = (short)(newCapacity * LongIntHashList.DEFAULT_LOAD_FACTOR);
    }

    public String contentHashForm() {
        String ret = new String(">>> ShortHashList lenght=" + table.length + " uniqueKeys=" + uniqueKeys +
                                /*" numEntry=" + numEntry + */"<<<" + Format.BRK);
        for(int i = 0; i < table.length; i++) {
            if(table[i] != null) {
                LongIntHashList.EntryHead hashColumn = table[i];
                ret += "\tTable[" + i + "]:" + Format.BRK;
                while(hashColumn != null) {
                    ret += "\t\t key=" + hashColumn.longKey + "," + hashColumn.intKey + Format.BRK + "\t\t\t";
                    String strColumn = new String();
                    LongIntHashList.Entry eachEntry = hashColumn;
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
        public LongIntHashList.Entry nextMatch;

        Entry(Object _obj, LongIntHashList.Entry match) {
            obj         = _obj;
            nextMatch   = match;
        }
    }

    static public class EntryHead extends LongIntHashList.Entry {
        final public long longKey;
        final public int intKey;
        LongIntHashList.EntryHead   nextUnmatch;

        EntryHead(long _longKey, int _intKey, Object _obj, LongIntHashList.Entry match, LongIntHashList.EntryHead unmatch) {
            super(_obj, match);
            longKey = _longKey;
            intKey = _intKey;
            nextUnmatch = unmatch;
        }
    }

    class EntryHeadIterator implements Iterator {
        LongIntHashList.EntryHead  m_cursor;
        int        m_currIndex;

        EntryHeadIterator() {
            m_currIndex = 0;
            m_cursor = toNextTableEntry();
        }

        private LongIntHashList.EntryHead toNextTableEntry() {
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
            LongIntHashList.EntryHead  ret = m_cursor;
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
