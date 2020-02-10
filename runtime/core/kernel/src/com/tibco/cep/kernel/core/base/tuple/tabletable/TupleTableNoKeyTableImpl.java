package com.tibco.cep.kernel.core.base.tuple.tabletable;

import com.tibco.cep.kernel.core.base.tuple.JoinTable;
import com.tibco.cep.kernel.core.base.tuple.TableIterator;
import com.tibco.cep.kernel.core.base.tuple.TupleRow;
import com.tibco.cep.kernel.core.base.tuple.TupleRowNoKey;
import com.tibco.cep.kernel.helper.Format;
import com.tibco.cep.kernel.helper.IdentifierUtil;
import com.tibco.cep.kernel.model.knowledgebase.Handle;

/**
 * Created by IntelliJ IDEA.
* User: aamaya
* Date: Oct 7, 2009
* Time: 7:48:36 PM
* To change this template use File | Settings | File Templates.
*/
public class TupleTableNoKeyTableImpl implements TupleTableNoKeyTable
{
    private transient RowEntry[]    table;
    protected int threshold;
    protected int numEntry;

    public TupleTableNoKeyTableImpl() {
        _reset();
    }

    public void reset() {
        _reset();
    }
    
    private void _reset() {
        table= new RowEntry[JoinTable.DEFAULT_INITIAL_CAPACITY];
        threshold  = (int)(JoinTable.DEFAULT_INITIAL_CAPACITY * JoinTable.DEFAULT_LOAD_FACTOR);
        numEntry = 0;
    }

    public boolean isEmpty() {
        return numEntry == 0;
    }

    public int size() {
        return numEntry;
    }

    public void lock() {}
    public void unlock() {}
    
    public boolean add(TupleRow row, JoinTable container) {
        int i = JoinTable.indexFor(row.hashCode(), table.length);
        for (RowEntry e = table[i]; e != null; e = e.next) {
            if (e.row == row) {
                return false;  //row already there
            }
        }
        //synchronized(this) {
        RowEntry newEntry = new RowEntry(container.getTableId(), (TupleRowNoKey) row, table[i]);
        table[i] = newEntry;
        if (numEntry++ >= threshold)
            resize(2* table.length);
        //}
        return true;
    }

    public TupleRow remove(TupleRow row) {
        int i = JoinTable.indexFor(row.hashCode(), table.length);
        RowEntry prev = table[i];
        RowEntry e = prev;

        while (e != null) {
            RowEntry next = e.next;
            if (e.row == row) {
                numEntry--;
                //synchronized(this) {
                if (prev == e)
                    table[i] = next;
                else
                    prev.next = next;
                //}
                return e.row;
            }
            prev = e;
            e = next;
        }
        return null;
    }
    
    public void clearAllElements(JoinTable container) {
        for(RowIterator rowIt = new RowIterator(this); rowIt.hasNext();) {
            TupleRow row = rowIt.nextTupleRow();
            row.unassociateTupleElements(container.getTableId());
        }
    }

    private void resize(int newCapacity) {
        int oldCapacity = table.length;
        if (oldCapacity == JoinTable.MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        RowEntry[] newTable = new RowEntry[newCapacity];
        for (int j = 0; j < oldCapacity; j++) {
            RowEntry e = table[j];
            if (e != null) {
                table[j] = null;
                do {
                    RowEntry next = e.next;
                    int i = JoinTable.indexFor(e.row.hashCode(), newCapacity);
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
    
    public Handle[][] getAllRows() {  //this function is called another thread, need to sync the object table when read
        Handle[][] ret = new Handle[numEntry][];
        int i = 0;
        //synchronized(this) {
            TableIterator ite = iterator();
            while(ite.hasNext()) {
                ret[i] = ((Handle[])ite.next());
                i++;
            }
        //}
        return ret;
    }
    
    public TableIterator iterator() {
        if(numEntry ==0) return NULL_ITER;
        else return new RowIterator(this);
    }
    
    public MigrationIterator<TupleRowNoKey> migrationIterator() {
        if(numEntry ==0) return MigrationIterator.NULL_ITER;
        return new MigrationRowIterator(this);
    }
    
    public String contentHashForm(JoinTable container) {
        String idrStr = "";
        if(container.getIdentifiers() != null) {
            idrStr = IdentifierUtil.toString(container.getIdentifiers());
        }
        String ret = ">>> TupleTable id=" + container.getTableId() + " length=" + table.length +
                " numEntry=" + numEntry + " Identifiers= " + idrStr + " <<<" + Format.BRK;
        for(int i = 0; i < table.length; i++) {
            if(table[i] != null) {
                RowEntry hashColumn = table[i];
                ret += "\tTable[" + i + "]:" + Format.BRK;
                ret += "\t\t hashKey=" + hashColumn.row.hashCode() + Format.BRK + "\t\t\t";
                String strColumn = "";
                RowEntry eachEntry = hashColumn;
                while(eachEntry != null) {
                    strColumn = strColumn + eachEntry.row.toString() + " ";
                    eachEntry = eachEntry.next;
                }
                ret += strColumn;
                ret += Format.BRK;
            }
        }
        return ret;
    }

    public String contentListForm(JoinTable container) {
        String idrStr = "";
        if(container.getIdentifiers() != null) {
            idrStr = IdentifierUtil.toString(container.getIdentifiers());
        }
        String ret = ">>> TupleTable id=" + container.getTableId() + " length=" + table.length +
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

    public static class RowIterator implements TableIterator {
        protected RowEntry next;      // next entry to return
        protected int index;          // current slot
        protected TupleTableNoKeyTableImpl container;

        protected RowIterator(TupleTableNoKeyTableImpl containerTable) {
            init(containerTable);
        }
        
        protected void init(TupleTableNoKeyTableImpl containerTable) {
            container = containerTable;
            RowEntry[] t = container.table;
            int i = t.length;
            RowEntry n = null;
            if (container.numEntry != 0) { // advance to first entry
                while (i > 0 && (n = t[--i]) == null) ;
            }
            next = n;
            index = i;

        }

        public boolean hasNext() {
            return next != null;
        }

        protected TupleRow nextTupleRow() {
            RowEntry e = next;
            advanceNext();
            return e.row;
        }


        public Handle[] next() {
            RowEntry e = next;
            advanceNext();
            return e.row.objHandles;
        }
        
        protected void advanceNext() {
            RowEntry n = next.next;
            int i = index;
            while (n == null && i > 0)
                n = container.table[--i];
            index = i;
            next = n;
        }

        public void remove() {
            throw new RuntimeException("RowIterator.remove() is not implemented, should use TupleTable.remove() to remove a tuple");
        }
    }
    
    private static class MigrationRowIterator extends RowIterator implements MigrationIterator<TupleRowNoKey>
    {
        private MigrationRowIterator(TupleTableNoKeyTableImpl container) {
            super(container);
        }

        public TupleRowNoKey next_migrate() {
            return (TupleRowNoKey)nextTupleRow();
        }
    }

    private static class RowEntry {
        final public TupleRowNoKey  row;
        public RowEntry next;

        private RowEntry(short tableId, TupleRowNoKey v, RowEntry n) {
            row    = v;
            next   = n;
            v.associateTupleElements(tableId);
        }

        private RowEntry(short tableId, TupleRowNoKey v) {
            row  = v;
            next = null;
            v.associateTupleElements(tableId);
        }
    }
}
