package com.tibco.cep.kernel.core.base.tuple.tabletable;

import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.TableIterator;
import com.tibco.cep.kernel.core.base.tuple.TupleRow;
import com.tibco.cep.kernel.core.base.tuple.TupleRowWithKey;
import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.helper.IdentifierUtil;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
* User: aamaya
* Date: Oct 7, 2009
* Time: 8:09:20 PM
* To change this template use File | Settings | File Templates.
*/
public class TupleTableWithKeyTableImpl implements TupleTableWithKeyTable 
{
    protected static final int DEFAULT_UNIQUE_KEY = 0;

    protected transient RowHeadEntry[]    table;
    protected transient int uniqueHashKey;
    protected transient int numEntry;
    protected transient int threshold;

    public TupleTableWithKeyTableImpl() {
        _reset();
    }
    
    public void reset() {
        _reset();
    }
    private void _reset() {
        table=new RowHeadEntry[JoinTable.DEFAULT_INITIAL_CAPACITY];
        threshold=((int)(JoinTable.DEFAULT_INITIAL_CAPACITY * JoinTable.DEFAULT_LOAD_FACTOR));
        numEntry=0;
        uniqueHashKey = DEFAULT_UNIQUE_KEY;
    }

    public boolean isEmpty() {
        return numEntry == 0;
    }

    public int size() {
        return numEntry;
    }

    public void lock() {}

    public void unlock() {}

    public TableIterator keyIterator(int key) {
        if(numEntry == 0) return NULL_ITER;
        else return KeyIterator.newInstance(key, this);
    }

    public void clearAllElements(JoinTable container) {
        RowIterator ite = new RowIterator(this);
        while(ite.hasNext()) {
            TupleRow row = ite.nextTupleRow();
            row.unassociateTupleElements(container.getTableId());
        }
    }

    public TableIterator iterator() {
        if(numEntry == 0) return NULL_ITER;
        else return new RowIterator(this);
    }

    public MigrationIterator<TupleRowWithKey> migrationIterator() {
        if(numEntry == 0) return MigrationIterator.NULL_ITER;
        return new MigrationRowIterator(this);
    }
    
    public boolean add(TupleRow row, JoinTable container) {
        TupleRowWithKey rowWithKey = (TupleRowWithKey) row;
        int i = JoinTable.indexFor(rowWithKey.rowKey, table.length);

        RowHeadEntry e = table[i];
        if(e == null) {
            //synchronized(this) {
            RowHeadEntry newEntry = new RowHeadEntry(container.getTableId(), rowWithKey);
            table[i] = newEntry;
            uniqueHashKey++;
            //resize if unique HashKey is greater or equal to the threshold
            if(uniqueHashKey >= threshold) {
                resize(2 * table.length);
            }
            //}
        }
        else {
            boolean found = false;
            for(; e != null; e = e.nextUnmatch) {
                if(e.row.rowKey == rowWithKey.rowKey) {
                    RowEntry newEntry = new RowEntry(container.getTableId(), rowWithKey, e.nextMatch);
                    e.nextMatch = newEntry;
//                    e.numEntries++;
                    found = true;
                    break;
                }
            }
            //not found and not inserted
            if (!found) {
                //synchronized(this) {
                RowHeadEntry newEntry = new RowHeadEntry(container.getTableId(), rowWithKey, table[i]);
                table[i] = newEntry;
                uniqueHashKey++;
                //resize if unique HashKey is greater or equal to the threshold
                if(uniqueHashKey >= threshold) {
                    resize(2 * table.length);
                }
                //}
            }
        }
        numEntry++;

        return true;  //always return true, never check for existence??
    }

    public TupleRow remove(TupleRow row) {
        int key = ((TupleRowWithKey)row).rowKey;
        int i = JoinTable.indexFor(key, table.length);

        RowHeadEntry e = table[i];
        RowHeadEntry prevUnmatch = null;
        if(e != null) {
            for(; e != null; e = e.nextUnmatch) {
                if(e.row.rowKey == key) {
                    //found the list
                    RowEntry curr = e;
                    RowEntry prev = null;
                    do {
                        if(curr.row == row) {
                            if(curr instanceof RowHeadEntry) {
                                if(curr.nextMatch == null) {
                                    //synchronized(this) {
                                    if(prevUnmatch == null)
                                        table[i] = ((RowHeadEntry)curr).nextUnmatch;
                                    else
                                        prevUnmatch.nextUnmatch = ((RowHeadEntry)curr).nextUnmatch;
                                    uniqueHashKey--;
                                    //}
                                    numEntry--;
                                    return curr.row;
                                }
                                else {
                                    TupleRow retRow = curr.row;
                                    //synchronized(this) {  //todo - need synch as value of this entry is being modified
                                        curr.row = curr.nextMatch.row;
                                        curr.nextMatch = curr.nextMatch.nextMatch;
                                    //}
                                    numEntry--;
                                    return retRow;
                                }
                            }
                            else {
                                //synchronized(this) {
                                prev.nextMatch = curr.nextMatch;
                                //}
                                numEntry--;
                                return curr.row;
                            }
                        }
                        prev = curr;
                        curr = curr.nextMatch;
                    }
                    while(curr != null);
                }
                prevUnmatch = e;
            }
        }
        return null;
    }
    
    public Handle[][] getAllRows(int key) {  //this function is called another thread, need to sync the key table when read
        //synchronized(this) {
        TableIterator ite = keyIterator(key);
        if(ite.hasNext()) {
            Handle[][] ret = new Handle[64][];
            int i = 0;
            do {
                if(i == ret.length) { //resize
                    Handle[][] newRet = new Handle[ret.length * 2][];
                    System.arraycopy(ret, 0, newRet, 0, ret.length);
                    ret = newRet;
                }
                ret[i] = ((Handle[])ite.next());
                i++;
            } while(ite.hasNext());
            //make sure it ends with a null value
            if(i < ret.length) ret[i] = null;
            return ret;
        } else {
            return NULL_ARR;
        }
    }

    private void resize(int newCapacity) {
        int oldCapacity = table.length;
        if (oldCapacity == JoinTable.MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }

        RowHeadEntry[] newTable = new RowHeadEntry[newCapacity];
        //transfer to new table here
        for(int j = 0; j < oldCapacity; j++) {
            RowHeadEntry matchedColumn = table[j];
            if (matchedColumn != null) {
                table[j] = null;
                do {
                    RowHeadEntry nextUnMatch = matchedColumn.nextUnmatch;
                    int i = JoinTable.indexFor(matchedColumn.row.rowKey, newCapacity);
                    matchedColumn.nextUnmatch = newTable[i];
                    newTable[i] = matchedColumn;
                    matchedColumn = nextUnMatch;
                } while (matchedColumn != null);
            }
        }

        table = newTable;
        //threshold = (int)(newCapacity * container.getLoadFactor());
        threshold = (int)(newCapacity * JoinTable.DEFAULT_LOAD_FACTOR);
    }

    public String contentHashForm(JoinTable container) {
        String idrStr = "";
        if(container.getIdentifiers() != null) {
            idrStr = IdentifierUtil.toString(container.getIdentifiers());
        }
        String ret = ">>> TupleTable id=" + container.getTableId() + " length=" + table.length + " uniqueHashKey=" + uniqueHashKey +
                " numEntry=" + numEntry + " Identifiers= " + idrStr + " <<<" + Format.BRK;
        for(int i = 0; i < table.length; i++) {
            if(table[i] != null) {
                RowHeadEntry hashColumn = table[i];
                ret += "\tTable[" + i + "]:" + Format.BRK;
                while(hashColumn != null) {
                    ret += "\t\t hashKey=" + hashColumn.row.rowKey + Format.BRK + "\t\t\t";
                    String strColumn = "";
                    RowEntry eachEntry = hashColumn;
                    while(eachEntry != null) {
                        strColumn = strColumn + eachEntry.row.toString() + " ";
                        eachEntry = eachEntry.nextMatch;
                    }
                    ret += strColumn;
                    hashColumn = hashColumn.nextUnmatch;
                    ret += Format.BRK;
                }
            }
        }
        return ret;
    }

    public String contentListForm(JoinTable container) {
        String idrStr = "";
        if(container.getIdentifiers() != null) {
            idrStr = IdentifierUtil.toString(container.getIdentifiers());
        }
        String ret = ">>> TupleTable id=" + container.getTableId() + " length=" + table.length + " uniqueHashKey=" + uniqueHashKey +
                " numEntry=" + numEntry + " Identifiers= " + idrStr + " <<<" + Format.BRK;
        ret += "\t Tuples :" + Format.BRK + "\t\t";
        String nonIteratorStr = "";
        RowIterator ite = (RowIterator) iterator();
        while(ite.hasNext()) {
            nonIteratorStr += ite.nextTupleRow().toString() + " ";
        }
        ret += nonIteratorStr + Format.BRK;
        return ret;
    }

    public static class RowIterator implements TableIterator
    {
        protected RowEntry m_entryCursor;
        protected RowHeadEntry m_headCursor;
        protected int               m_tableRow;
        protected TupleTableWithKeyTableImpl container;


        protected RowIterator(TupleTableWithKeyTableImpl containerTable) {
            init(containerTable);
        }
        
        protected void init(TupleTableWithKeyTableImpl containerTable) {
            container = containerTable;
            m_tableRow    = 0;
            m_headCursor  = toNextEntry();
            m_entryCursor = m_headCursor;
        }

        RowHeadEntry toNextEntry() {
            while(m_tableRow < container.table.length) {
                if(container.table[m_tableRow] != null) {
                    return container.table[m_tableRow];
                }
                m_tableRow++;
            }
            return null;
        }

        public boolean hasNext() {
            return m_entryCursor != null;
        }

        protected TupleRow nextTupleRow() {
            RowEntry ret = m_entryCursor;
            advanceCursor();
            return ret.row;
        }

        public Handle[] next() {
            RowEntry ret = m_entryCursor;
            advanceCursor();
            return ret.row.objHandles;
        }
        
        protected void advanceCursor() {
            if(m_entryCursor.nextMatch != null) {
                m_entryCursor = m_entryCursor.nextMatch;
            }
            else if(m_headCursor.nextUnmatch != null) {
                m_entryCursor = m_headCursor.nextUnmatch;
                m_headCursor = m_headCursor.nextUnmatch;
            }
            else {
                m_tableRow++;
                m_headCursor = toNextEntry();
                m_entryCursor = m_headCursor;
            }
        }

        public void remove() {
            throw new RuntimeException("RowIterator.remove() is not implemented, should use TupleTable.remove() to remove a tuple");
        }
    }
    
    private static class MigrationRowIterator extends RowIterator implements MigrationIterator<TupleRowWithKey>
    {
        private MigrationRowIterator(TupleTableWithKeyTableImpl containerTable) {
            super(containerTable);
        }

        public TupleRowWithKey next_migrate() {
            return (TupleRowWithKey)nextTupleRow();
        }
    }

    public static class KeyIterator implements TableIterator {
        protected int               m_key;
        protected RowEntry m_entry;

        protected KeyIterator(int key, RowEntry start) {
            m_key             = key;
            m_entry = start;
        }
        
        protected static TableIterator newInstance(int key, TupleTableWithKeyTableImpl container) {
            RowEntry start = findStart(key, container);
            if(start == null) return NULL_ITER;
            else return new KeyIterator(key, start);
        }
        
        protected static RowEntry findStart(int key, TupleTableWithKeyTableImpl container) {
            RowEntry cursor = container.table[JoinTable.indexFor(key, container.table.length)];
            while(cursor !=null && cursor.row.rowKey != key) {
                cursor = ((RowHeadEntry)cursor).nextUnmatch;
            }
            return cursor;
        }

        public boolean hasNext() {
            return m_entry != null;
        }

        public Handle[] next() {
            RowEntry ret = m_entry;
            m_entry      = m_entry.nextMatch;
            return ret.row.objHandles;
        }

        public void remove() {
            throw new RuntimeException("KeyIterator.remove() is not implemented, should use TupleTable.remove() to remove a tuple");
        }
    }

    static public class RowEntry {
        public TupleRowWithKey  row;
        public RowEntry nextMatch;

        private RowEntry(short tableId, TupleRowWithKey v, RowEntry match) {
            row         = v;
            nextMatch   = match;
            v.associateTupleElements(tableId);
        }

        private RowEntry(short tableId, TupleRowWithKey v) {
            row       = v;
            nextMatch = null;
            v.associateTupleElements(tableId);
        }
    }

    static private class RowHeadEntry extends RowEntry
    {
        public RowHeadEntry nextUnmatch;

        private RowHeadEntry(short tableId, TupleRowWithKey v, RowHeadEntry unmatch, RowEntry match) {
            super(tableId, v, match);
            nextUnmatch = unmatch;
        }

        private RowHeadEntry(short tableId, TupleRowWithKey v, RowHeadEntry unmatch) {
            super(tableId, v);
            nextUnmatch = unmatch;
        }

        private RowHeadEntry(short tableId, TupleRowWithKey v) {
            super(tableId, v);
            nextUnmatch = null;
        }
    }
}
