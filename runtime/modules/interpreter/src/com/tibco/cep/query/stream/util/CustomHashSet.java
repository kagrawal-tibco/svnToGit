package com.tibco.cep.query.stream.util;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Author: Ashwin Jayaprakash Date: Jan 28, 2008 Time: 6:02:57 PM
 */

/**
 * Custom {@link java.util.Set} implementation that is faster and takes less space than {@link
 * java.util.HashSet}.
 * <p/>
 * 1) This does not use a {@link java.util.Map} internally and so saves space by not having that
 * unused "value" pointer in the "entry" object.
 * <p/>
 * 2) The objects are added directly into the "table" instead of being wrapped by an "entry" object.
 * Only on collisions do they get wrapped. So, when there is no collision, there are only the
 * objects directly in the table and so there is no allocation during such adds.
 */
public final class CustomHashSet<K> extends AbstractSet<K> implements CustomCollection<K> {
    private static final FixedKeys nullKey = FixedKeys.NULL;

    /**
     * {@value}.
     */
    public static final int maxCapacity = 1 << 30;

    /**
     * {@value}.
     */
    public static final int DEFAULT_INITIAL_CAPACITY = 16;

    /**
     * {@value}.
     */
    public static final float DEFAULT_LOAD_FACTOR = 0.65f;

    private Object[] table;

    private int size;

    private int threshold;

    private int initialCapacity;

    private float loadFactor;

    public CustomHashSet() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    /**
     * @param initialCapacity Must be a power of 2.
     * @param loadFactor
     */
    public CustomHashSet(int initialCapacity, float loadFactor) {
        reset(initialCapacity, loadFactor);
    }

    private void reset(int initialCapacity, float loadFactor) {
        this.initialCapacity = initialCapacity;
        this.table = new Object[initialCapacity];
        this.loadFactor = loadFactor;
        this.threshold = calcThreshold(initialCapacity);

        this.size = 0;
    }

    public void clear(boolean shrinkToInitialCapacity) {
        clearTable(table);

        if (shrinkToInitialCapacity) {
            reset(initialCapacity, loadFactor);
        }
    }

    /**
     * Just clears the internal array. Does not shrink back unlike {@link #clear(boolean)}.
     */
    @Override
    public void clear() {
        if (size > 0) {
            clearTable(table);
            size = 0;
        }
    }

    private void clearTable(Object[] theTable) {
        for (int i = 0; i < theTable.length; i++) {
            Object column = theTable[i];

            if (column == null) {
                continue;
            }

            theTable[i] = null;

            if (column instanceof CollisionEntry == false) {
                continue;
            }
            else {
                CollisionEntry ce = (CollisionEntry) column;
                CollisionEntry nextCE = null;
                do {
                    ce.value = null;

                    nextCE = ce.nextEntry;
                    ce.nextEntry = null;
                }
                while ((ce = nextCE) != null);
            }
        }
    }

    /**
     * Uses {@link #loadFactor}.
     *
     * @param capacity
     * @return
     */
    private int calcThreshold(int capacity) {
        int t = (capacity == maxCapacity) ? /* Don't ask again. */Integer.MAX_VALUE
                : (int) (capacity * loadFactor);

        return t;
    }

    /**
     * Increases in powers of 2. Max capacity: {@link #maxCapacity}.
     *
     * @param currentCapacity
     * @return
     */
    private int calcNewCapacity(int currentCapacity) {
        int c = currentCapacity << 1;

        if (c > maxCapacity || (currentCapacity > 0 && c <= 0 /* Overflow. */)) {
            c = maxCapacity;
        }

        return c;
    }

    private static Object wrapKey(Object k) {
        return (k == null) ? nullKey : k;
    }

    /**
     * @param originalHash
     * @return
     */
    private static int calcGoodHash(int originalHash) {
        int h = originalHash;

        // Note: Obtained from java.util.HashMap.
        /*
         * This function ensures that hashCodes that differ only by constant
         * multiples at each bit position have a bounded number of collisions
         * (approximately 8 at default load factor).
         */
        h ^= (h >>> 20) ^ (h >>> 12);
        h = h ^ (h >>> 7) ^ (h >>> 4);

        return h;
    }

    /**
     * @param hash
     * @param tableLength
     * @return
     */
    private static int getIndex(int hash, int tableLength) {
        return hash & (tableLength - 1);
    }

    private boolean internalAdd(K k) {
        final Object key = wrapKey(k);
        final int origHash = key.hashCode();
        final int memoizedHash = calcGoodHash(origHash);
        final int index = getIndex(memoizedHash, table.length);
        final Object tableColumn = table[index];

        // Empty slot. Put it here.
        if (tableColumn == null) {
            table[index] = key;

            return true;
        }
        // Collisions exist.
        else if (tableColumn instanceof CollisionEntry) {
            CollisionEntry ce = (CollisionEntry) tableColumn;

            for (; ;) {
                if (memoizedHash == ce.memoizedHash && (key == ce.value || key.equals(ce.value))) {
                    // Key already exists.
                    return false;
                }
                else if (ce.nextEntry == null) {
                    ce.nextEntry = new CollisionEntry(key, memoizedHash);

                    return true;
                }

                ce = ce.nextEntry;
            }
        }
        // New collision.
        else if (origHash != tableColumn.hashCode() ||
                (key != tableColumn && key.equals(tableColumn) == false)) {
            CollisionEntry ce = new CollisionEntry(key, memoizedHash);
            int h = calcGoodHash(tableColumn.hashCode());
            ce.nextEntry = new CollisionEntry(tableColumn, h);
            table[index] = ce;

            return true;
        }

        // No collision, but key already exists.
        return false;
    }

    private boolean internalRemove(Object k) {
        final Object key = wrapKey(k);
        final int origHash = key.hashCode();
        final int memoizedHash = calcGoodHash(origHash);
        final int index = getIndex(memoizedHash, table.length);
        final Object tableColumn = table[index];

        if (tableColumn == null) {
            //Not present.
            return false;
        }
        else if (tableColumn instanceof CollisionEntry) {
            CollisionEntry ce = (CollisionEntry) tableColumn;
            CollisionEntry prevCE = null;

            do {
                if (memoizedHash == ce.memoizedHash && (key == ce.value || key.equals(ce.value))) {
                    if (prevCE == null) {
                        table[index] = ce.nextEntry;
                    }
                    else {
                        prevCE.nextEntry = ce.nextEntry;
                    }

                    //Discard ce.
                    ce.value = null;
                    ce.nextEntry = null;

                    // Found it
                    return true;
                }

                prevCE = ce;
            }
            while ((ce = ce.nextEntry) != null);

            return false;
        }
        else
        if (origHash == tableColumn.hashCode() && (key == tableColumn || key.equals(tableColumn))) {
            table[index] = null;

            // Found it.
            return true;
        }

        return false;
    }

    private void enlargeTable() {
        final Object[] oldTable = table;
        final int newTableSize = calcNewCapacity(oldTable.length);

        if (newTableSize > oldTable.length) {
            Object[] newTable = new Object[newTableSize];

            // Replace the old Table with the new one.
            table = newTable;
            threshold = calcThreshold(newTableSize);

            for (int i = 0; i < oldTable.length; i++) {
                final Object oldTableColumn = oldTable[i];

                if (oldTableColumn == null) {
                    continue;
                }
                else if (oldTableColumn instanceof CollisionEntry) {
                    CollisionEntry ce = (CollisionEntry) oldTableColumn;
                    CollisionEntry nextCE = null;

                    do {
                        K k = (K) ce.value;
                        internalAdd(k);

                        ce.value = null;
                        nextCE = ce.nextEntry;
                        ce.nextEntry = null;
                    }
                    while ((ce = nextCE) != null);
                }
                else {
                    K k = (K) oldTableColumn;
                    internalAdd(k);
                }

                oldTable[i] = null;
            }
        }
        else {
            /*
             * If the new capacity has not changed, then it means that we've
             * reached the limit. Cannot enlarge any further.
             */
            /*
             * todo Issue warning.
             */
        }
    }

    // ---------

    public boolean add(K k) {
        boolean b = internalAdd(k);
        if (b) {
            size++;

            if (size >= threshold) {
                enlargeTable();
            }
        }

        return b;
    }

    public boolean remove(Object k) {
        boolean b = internalRemove(k);
        if (b) {
            size--;
        }

        return b;
    }

    public boolean addAll(Collection<? extends K> c) {
        int oldSize = size;

        // todo Provide resize hint instead of resizing over and over.

        for (K k : c) {
            add(k);
        }

        return size != oldSize;
    }

    /**
     * @param k
     * @return <code>null</code> if the {@link #contains(Object)} returns false. Otherwise, return
     *         the value stored in this set. If the key itself is <code>null</code> and the set
     *         already has a <code>null</code>, then return <code>null</code>.
     */
    public K getOriginalIfPresent(Object k) {
        final Object key = wrapKey(k);
        final int origHash = key.hashCode();
        final int memoizedHash = calcGoodHash(origHash);
        final int index = getIndex(memoizedHash, table.length);
        final Object tableColumn = table[index];

        if (tableColumn == null) {
            return null;
        }
        // Collisions exist.
        else if (tableColumn instanceof CollisionEntry) {
            CollisionEntry ce = (CollisionEntry) tableColumn;

            do {
                if (memoizedHash == ce.memoizedHash && (key == ce.value || key.equals(ce.value))) {
                    return (K) ce.value;
                }
            }
            while ((ce = ce.nextEntry) != null);

            return null;
        }
        else
        if (origHash == tableColumn.hashCode() && (key == tableColumn || key.equals(tableColumn))) {
            return (K) tableColumn;
        }

        return null;
    }

    public boolean contains(Object k) {
        final Object key = wrapKey(k);
        final int origHash = key.hashCode();
        final int memoizedHash = calcGoodHash(origHash);
        final int index = getIndex(memoizedHash, table.length);
        final Object tableColumn = table[index];

        if (tableColumn == null) {
            return false;
        }
        // Collisions exist.
        else if (tableColumn instanceof CollisionEntry) {
            CollisionEntry ce = (CollisionEntry) tableColumn;

            do {
                if (memoizedHash == ce.memoizedHash && (key == ce.value || key.equals(ce.value))) {
                    return true;
                }
            }
            while ((ce = ce.nextEntry) != null);

            return false;
        }
        else
        if (origHash == tableColumn.hashCode() && (key == tableColumn || key.equals(tableColumn))) {
            return true;
        }

        return false;
    }

    public boolean containsAll(Collection<?> c) {
        boolean b = false;

        for (Object object : c) {
            b = contains(object);

            if (b == false) {
                break;
            }
        }

        return b;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public ReusableIterator<K> iterator() {
        return new MyIterator();
    }

    public boolean retainAll(Collection<?> c) {
        int oldSize = size;

        for (Iterator<K> iter = iterator(); iter.hasNext();) {
            K k = iter.next();

            if (c.contains(k) == false) {
                iter.remove();
            }
        }

        return size != oldSize;
    }

    public int size() {
        return size;
    }

    public Object[] toArray() {
        Object[] array = new Object[size];

        int i = 0;
        for (K k : this) {
            array[i++] = k;
        }

        return array;
    }

    public <T> T[] toArray(T[] a) {
        T[] newA = a;
        if (a.length < size) {
            newA = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }

        Object[] array = newA;
        int i = 0;
        for (K k : this) {
            array[i++] = k;
        }

        return (T[]) array;
    }

    // ---------

    protected class MyIterator implements ReusableIterator<K> {
        private Object[] array;

        private int nextColumnPos;

        private CollisionEntry lastEntryThatWasRead;

        private CollisionEntry prevLastEntryThatWasRead;

        protected MyIterator() {
            doReset();
        }

        private void doReset() {
            array = CustomHashSet.this.table;

            //Move to the first non-null column.
            int i = 0;
            while (i < array.length && array[i] == null) {
                i++;
            }
            nextColumnPos = i;

            lastEntryThatWasRead = null;
            prevLastEntryThatWasRead = null;
        }

        public void reset() {
            doReset();
        }

        public boolean hasNext() {
            //First check if the chain still has elements.
            if (lastEntryThatWasRead != null && lastEntryThatWasRead.nextEntry != null) {
                return true;
            }

            //Now, move to the next item in the table and keep checking.
            int x = nextColumnPos;
            while (x < array.length) {
                if (array[x] != null) {
                    return true;
                }

                x++;
            }

            return false;
        }

        public K next() {
            prevLastEntryThatWasRead = lastEntryThatWasRead;
            if (lastEntryThatWasRead != null &&
                    (lastEntryThatWasRead = lastEntryThatWasRead.nextEntry) != null) {
                return (K) lastEntryThatWasRead.value;
            }

            Object obj = null;
            while (nextColumnPos < array.length && (obj = array[nextColumnPos]) == null) {
                nextColumnPos++;
            }

            //Reached the end.
            if (obj == null) {
                throw new NoSuchElementException();
            }

            nextColumnPos++;

            if (obj instanceof CollisionEntry) {
                lastEntryThatWasRead = (CollisionEntry) obj;
                return (K) lastEntryThatWasRead.value;
            }

            return (K) obj;
        }

        public void remove() {
            final int currPos = nextColumnPos - 1;

            CustomHashSet.this.size--;

            if (lastEntryThatWasRead == null) {
                array[currPos] = null;

                return;
            }

            //This is the first entry in the chain.
            if (array[currPos] == lastEntryThatWasRead) {
                lastEntryThatWasRead.value = null;
                array[currPos] = lastEntryThatWasRead.nextEntry;
                lastEntryThatWasRead.nextEntry = null;

                //Reset the positions.
                lastEntryThatWasRead = null;
                if (array[currPos] != null) {
                    nextColumnPos--;
                }

                return;
            }

            if (prevLastEntryThatWasRead == null) {
                array[currPos] = lastEntryThatWasRead.nextEntry;

                //Reset the pointer.
                nextColumnPos--;
            }
            else {
                prevLastEntryThatWasRead.nextEntry = lastEntryThatWasRead.nextEntry;
            }

            lastEntryThatWasRead.value = null;
            lastEntryThatWasRead.nextEntry = null;

            //Reset the pointer.
            lastEntryThatWasRead = prevLastEntryThatWasRead;
            prevLastEntryThatWasRead = null;
        }
    }

    //----------

    protected static class CollisionEntry {
        private Object value;

        private int memoizedHash;

        private CollisionEntry nextEntry;

        protected CollisionEntry(Object value, int memoizedHash) {
            this.value = value;
            this.memoizedHash = memoizedHash;
        }
    }
}
