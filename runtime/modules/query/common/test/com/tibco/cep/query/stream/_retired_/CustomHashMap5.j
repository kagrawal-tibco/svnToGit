package com.tibco.cep.query.stream._retired_;

import com.tibco.cep.query.stream.util.Helper;

import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/*
 * Author: Ashwin Jayaprakash Date: Feb 18, 2008 Time: 2:02:45 PM
 */

public class CustomHashMap5<K, V> extends AbstractMap<K, V> implements Map<K, V>, Cloneable,
        Serializable {
    /**
     * The default initial capacity - MUST be a power of two.
     */
    static final int DEFAULT_INITIAL_CAPACITY = 16;

    static final int FRAGMENT_SIZE = 12288;

    static final int FRAGMENT_SIZE_LOG2 = Helper.floorLogBase2(FRAGMENT_SIZE);

    /**
     * The maximum capacity, used if a higher value is implicitly specified by
     * either of the constructors with arguments. MUST be a power of two <= 1<<30.
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * The load factor used when none specified in constructor.
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * The table, resized as necessary. Length MUST Always be a power of two.
     */
    transient Entry[][] table;

    /**
     * The number of key-value mappings contained in this identity hash map.
     */
    transient int size;

    /**
     * The next size value at which to resize (capacity * load factor).
     * 
     * @serial
     */
    int threshold;

    /**
     * The load factor for the hash table.
     * 
     * @serial
     */
    final float loadFactor;

    transient int virtualLength;

    transient int fragmentActualSizeForMod;

    /**
     * The number of times this CustomHashMap5 has been structurally modified
     * Structural modifications are those that change the number of mappings in
     * the CustomHashMap5 or otherwise modify its internal structure (e.g.,
     * rehash). This field is used to make iterators on Collection-views of the
     * CustomHashMap5 fail-fast. (See ConcurrentModificationException).
     */
    transient volatile int modCount;

    /**
     * Constructs an empty <tt>CustomHashMap5</tt> with the specified initial
     * capacity and load factor.
     * 
     * @param initialCapacity
     *            The initial capacity.
     * @param loadFactor
     *            The load factor.
     * @throws IllegalArgumentException
     *             if the initial capacity is negative or the load factor is
     *             nonpositive.
     */
    public CustomHashMap5(int initialCapacity, float loadFactor) {
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

        int[] vlAndBuckets = calcVLAndBuckets(capacity);
        int buckets = vlAndBuckets[1];
        table = new Entry[buckets][];
        virtualLength = vlAndBuckets[0];
        fragmentActualSizeForMod = calcActualFragmentSizeForMod(virtualLength);
        if (virtualLength <= FRAGMENT_SIZE) {
            table[0] = new Entry[virtualLength];
        }

        this.loadFactor = loadFactor;
        threshold = (int) (capacity * loadFactor);

        init();
    }

    /**
     * Constructs an empty <tt>CustomHashMap5</tt> with the specified initial
     * capacity and the default load factor (0.75).
     * 
     * @param initialCapacity
     *            the initial capacity.
     * @throws IllegalArgumentException
     *             if the initial capacity is negative.
     */
    public CustomHashMap5(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructs an empty <tt>CustomHashMap5</tt> with the default initial
     * capacity (16) and the default load factor (0.75).
     */
    public CustomHashMap5() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);

        int[] vlAndBuckets = calcVLAndBuckets(DEFAULT_INITIAL_CAPACITY);
        int buckets = vlAndBuckets[1];
        table = new Entry[buckets][];
        virtualLength = vlAndBuckets[0];
        if (virtualLength <= FRAGMENT_SIZE) {
            table[0] = new Entry[virtualLength];
        }

        init();
    }

    /**
     * @param capacity
     *            Has to be a multiple of FRAGMENT_SIZE if greater than
     *            FRAGMENT_SIZE.
     */
    private static int[] calcVLAndBuckets(int capacity) {
        int vl = capacity;
        int buckets = capacity >> FRAGMENT_SIZE_LOG2;

        if (buckets == 0) {
            buckets = 1;
            vl = capacity;
        }
        else if ((buckets * FRAGMENT_SIZE) < capacity) {
            int remainder = (capacity & (FRAGMENT_SIZE - 1));
            vl = capacity + remainder;
        }

        return new int[] { vl, buckets };
    }

    private static int calcActualFragmentSizeForMod(int virtualLength) {
        int x = (virtualLength <= FRAGMENT_SIZE) ? virtualLength : FRAGMENT_SIZE;
        return x - 1;
    }

    /**
     * Constructs a new <tt>CustomHashMap5</tt> with the same mappings as the
     * specified <tt>Map</tt>. The <tt>CustomHashMap5</tt> is created with
     * default load factor (0.75) and an initial capacity sufficient to hold the
     * mappings in the specified <tt>Map</tt>.
     * 
     * @param m
     *            the map whose mappings are to be placed in this map.
     * @throws NullPointerException
     *             if the specified map is null.
     */
    public CustomHashMap5(Map<? extends K, ? extends V> m) {
        this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1, DEFAULT_INITIAL_CAPACITY),
                DEFAULT_LOAD_FACTOR);
        putAllForCreate(m);
    }

    // internal utilities

    /**
     * Initialization hook for subclasses. This method is called in all
     * constructors and pseudo-constructors (clone, readObject) after
     * CustomHashMap5 has been initialized but before any entries have been
     * inserted. (In the absence of this method, readObject would require
     * explicit knowledge of subclasses.)
     */
    void init() {
    }

    /**
     * Value representing null keys inside tables.
     */
    static final Object NULL_KEY = new Object();

    /**
     * Returns internal representation for key. Use NULL_KEY if key is null.
     */
    static <T> T maskNull(T key) {
        return key == null ? (T) NULL_KEY : key;
    }

    /**
     * Returns key represented by specified internal representation.
     */
    static <T> T unmaskNull(T key) {
        return (key == NULL_KEY ? null : key);
    }

    /**
     * Whether to prefer the old supplemental hash function, for compatibility
     * with broken applications that rely on the internal hashing order. Set to
     * true only by hotspot when invoked via -XX:+UseNewHashFunction or
     * -XX:+AggressiveOpts
     */
    private static final boolean useNewHash;
    static {
        useNewHash = false;
    }

    private static int oldHash(int h) {
        h += ~(h << 9);
        h ^= (h >>> 14);
        h += (h << 4);
        h ^= (h >>> 10);
        return h;
    }

    private static int newHash(int h) {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * Applies a supplemental hash function to a given hashCode, which defends
     * against poor quality hash functions. This is critical because
     * CustomHashMap5 uses power-of-two length hash tables, that otherwise
     * encounter collisions for hashCodes that do not differ in lower bits.
     */
    static int hash(int h) {
        return useNewHash ? newHash(h) : oldHash(h);
    }

    static int hash(Object key) {
        return hash(key.hashCode());
    }

    /**
     * Check for equality of non-null reference x and possibly-null y.
     */
    static boolean eq(Object x, Object y) {
        return x == y || x.equals(y);
    }

    /**
     * Returns index for hash code h.
     */
    static int indexFor(int h, int length) {
        return h & (length - 1);
    }

    /**
     * Returns the number of key-value mappings in this map.
     * 
     * @return the number of key-value mappings in this map.
     */
    public int size() {
        return size;
    }

    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     * 
     * @return <tt>true</tt> if this map contains no key-value mappings.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the value to which the specified key is mapped in this identity
     * hash map, or <tt>null</tt> if the map contains no mapping for this key.
     * A return value of <tt>null</tt> does not <i>necessarily</i> indicate
     * that the map contains no mapping for the key; it is also possible that
     * the map explicitly maps the key to <tt>null</tt>. The
     * <tt>containsKey</tt> method may be used to distinguish these two cases.
     * 
     * @param key
     *            the key whose associated value is to be returned.
     * @return the value to which this map maps the specified key, or
     *         <tt>null</tt> if the map contains no mapping for this key.
     * @see #put(Object, Object)
     */
    public V get(Object key) {
        if (key == null)
            return getForNullKey();
        int hash = hash(key.hashCode());

        int i = indexFor(hash, virtualLength);
        int arrayPos = i & fragmentActualSizeForMod;
        i = i >> FRAGMENT_SIZE_LOG2;
        Entry[] array = table[i];
        if (array == null) {
            return null;
        }

        for (Entry<K, V> e = array[arrayPos]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
                return e.value;
        }
        return null;
    }

    private V getForNullKey() {
        int hash = hash(NULL_KEY.hashCode());

        int i = indexFor(hash, virtualLength);
        int arrayPos = i & fragmentActualSizeForMod;
        i = i >> FRAGMENT_SIZE_LOG2;
        Entry[] array = table[i];
        if (array == null) {
            return null;
        }

        Entry<K, V> e = array[arrayPos];
        while (true) {
            if (e == null)
                return null;
            if (e.key == NULL_KEY)
                return e.value;
            e = e.next;
        }
    }

    /**
     * Returns <tt>true</tt> if this map contains a mapping for the specified
     * key.
     * 
     * @param key
     *            The key whose presence in this map is to be tested
     * @return <tt>true</tt> if this map contains a mapping for the specified
     *         key.
     */
    public boolean containsKey(Object key) {
        Object k = maskNull(key);
        int hash = hash(k.hashCode());

        int i = indexFor(hash, virtualLength);
        int arrayPos = i & fragmentActualSizeForMod;
        i = i >> FRAGMENT_SIZE_LOG2;
        Entry[] array = table[i];
        if (array == null) {
            return false;
        }

        Entry<K, V> e = array[arrayPos];
        while (e != null) {
            if (e.hash == hash && eq(k, e.key))
                return true;
            e = e.next;
        }
        return false;
    }

    /**
     * Returns the entry associated with the specified key in the
     * CustomHashMap5. Returns null if the CustomHashMap5 contains no mapping
     * for this key.
     */
    Entry<K, V> getEntry(Object key) {
        Object k = maskNull(key);
        int hash = hash(k.hashCode());

        int i = indexFor(hash, virtualLength);
        int arrayPos = i & fragmentActualSizeForMod;
        i = i >> FRAGMENT_SIZE_LOG2;
        Entry[] array = table[i];
        if (array == null) {
            return null;
        }

        Entry<K, V> e = array[arrayPos];
        while (e != null && !(e.hash == hash && eq(k, e.key)))
            e = e.next;
        return e;
    }

    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for this key, the old value is
     * replaced.
     * 
     * @param key
     *            key with which the specified value is to be associated.
     * @param value
     *            value to be associated with the specified key.
     * @return previous value associated with specified key, or <tt>null</tt>
     *         if there was no mapping for key. A <tt>null</tt> return can
     *         also indicate that the CustomHashMap5 previously associated
     *         <tt>null</tt> with the specified key.
     */
    public V put(K key, V value) {
        if (key == null)
            return putForNullKey(value);
        int hash = hash(key.hashCode());

        int i = indexFor(hash, virtualLength);
        Entry[] array = null;

        if (virtualLength > FRAGMENT_SIZE) {
            int index = i & fragmentActualSizeForMod;
            i = i >> FRAGMENT_SIZE_LOG2;

            array = table[i];
            if (array == null) {
                array = table[i] = new Entry[fragmentActualSizeForMod + 1];
            }

            i = index;
        }
        else {
            array = table[0];
        }

        for (Entry<K, V> e = array[i]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }

        modCount++;
        addEntry(hash, key, value, array, i);
        return null;
    }

    private V putForNullKey(V value) {
        int hash = hash(NULL_KEY.hashCode());

        int i = indexFor(hash, virtualLength);
        int arrayPos = i & fragmentActualSizeForMod;
        i = i >> FRAGMENT_SIZE_LOG2;
        Entry[] array = table[i];
        if (array == null) {
            array = table[i] = new Entry[Math.min(virtualLength, FRAGMENT_SIZE)];
        }

        Entry<K, V> e = array[arrayPos];
        for (; e != null; e = e.next) {
            if (e.key == NULL_KEY) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }

        modCount++;
        addEntry(hash, (K) NULL_KEY, value, array, arrayPos);
        return null;
    }

    /**
     * This method is used instead of put by constructors and pseudoconstructors
     * (clone, readObject). It does not resize the table, check for
     * comodification, etc. It calls createEntry rather than addEntry.
     */
    private void putForCreate(K key, V value) {
        K k = maskNull(key);
        int hash = hash(k.hashCode());

        /**
         * Look for preexisting entry for key. This will never happen for clone
         * or deserialize. It will only happen for construction if the input Map
         * is a sorted map whose ordering is inconsistent w/ equals.
         */
        int i = indexFor(hash, virtualLength);
        int arrayPos = i & fragmentActualSizeForMod;
        i = i >> FRAGMENT_SIZE_LOG2;
        Entry[] array = table[i];
        if (array == null) {
            array = table[i] = new Entry[Math.min(virtualLength, FRAGMENT_SIZE)];
        }

        Entry<K, V> e = array[arrayPos];
        for (; e != null; e = e.next) {
            if (e.hash == hash && eq(k, e.key)) {
                e.value = value;
                return;
            }
        }

        createEntry(hash, k, value, array, arrayPos);
    }

    void putAllForCreate(Map<? extends K, ? extends V> m) {
        for (Iterator<? extends Map.Entry<? extends K, ? extends V>> i = m.entrySet().iterator(); i
                .hasNext();) {
            Map.Entry<? extends K, ? extends V> e = i.next();
            putForCreate(e.getKey(), e.getValue());
        }
    }

    /**
     * Rehashes the contents of this map into a new array with a larger
     * capacity. This method is called automatically when the number of keys in
     * this map reaches its threshold. If current capacity is MAXIMUM_CAPACITY,
     * this method does not resize the map, but sets threshold to
     * Integer.MAX_VALUE. This has the effect of preventing future calls.
     * 
     * @param newCapacity
     *            the new capacity, MUST be a power of two; must be greater than
     *            current capacity unless current capacity is MAXIMUM_CAPACITY
     *            (in which case value is irrelevant).
     */
    void resize(int newCapacity) {
        int oldCapacity = virtualLength;
        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }

        int[] vlAndBuckets = calcVLAndBuckets(newCapacity);
        virtualLength = vlAndBuckets[0];
        fragmentActualSizeForMod = calcActualFragmentSizeForMod(virtualLength);

        if (virtualLength <= FRAGMENT_SIZE) {
            Entry[] newArray = new Entry[virtualLength];
            transfer1(table[0], newArray);
            table[0] = newArray;
        }
        else {
            int buckets = vlAndBuckets[1];
            Entry[][] newTable = new Entry[buckets][];
            transfer2(newTable);
            table = newTable;
        }

        threshold = (int) (virtualLength * loadFactor);
    }

    /**
     * Transfer all entries from current table to newTable.
     */
    private void transfer1(Entry[] srcArray, Entry[] newArray) {
        for (int m = 0; m < srcArray.length; m++) {
            Entry<K, V> e = srcArray[m];
            if (e != null) {
                srcArray[m] = null;
                do {
                    Entry<K, V> next = e.next;

                    int i = indexFor(e.hash, virtualLength);
                    e.next = newArray[i];
                    newArray[i] = e;

                    e = next;
                } while (e != null);
            }
        }
    }

    private void transfer2(Entry[][] newTable) {
        Entry[][] srcTable = table;

        LinkedList<Entry[]> pooledArrays = new LinkedList<Entry[]>();

        for (int j = 0; j < srcTable.length; j++) {
            Entry[] srcArray = srcTable[j];
            if (srcArray != null) {
                for (int m = 0; m < srcArray.length; m++) {
                    Entry<K, V> e = srcArray[m];
                    if (e != null) {
                        srcArray[m] = null;
                        do {
                            Entry<K, V> next = e.next;

                            int i = indexFor(e.hash, virtualLength);
                            int arrayPos = i & fragmentActualSizeForMod;
                            i = i >> FRAGMENT_SIZE_LOG2;

                            Entry[] newArray = newTable[i];
                            if (newArray == null) {
                                int newFragmentSize = fragmentActualSizeForMod + 1;

                                Entry[] newFragment = null;
                                if (pooledArrays.size() > 0) {
                                    newFragment = pooledArrays.removeFirst();
                                }
                                else {
                                    newFragment = new Entry[newFragmentSize];
                                }

                                newArray = newTable[i] = newFragment;
                            }

                            e.next = newArray[arrayPos];
                            newArray[arrayPos] = e;

                            e = next;
                        } while (e != null);
                    }
                }
                pooledArrays.add(srcArray);
            }
        }

        pooledArrays.clear();
    }

    /**
     * Copies all of the mappings from the specified map to this map These
     * mappings will replace any mappings that this map had for any of the keys
     * currently in the specified map.
     * 
     * @param m
     *            mappings to be stored in this map.
     * @throws NullPointerException
     *             if the specified map is null.
     */
    public void putAll(Map<? extends K, ? extends V> m) {
        int numKeysToBeAdded = m.size();
        if (numKeysToBeAdded == 0)
            return;

        /*
         * Expand the map if the map if the number of mappings to be added is
         * greater than or equal to threshold. This is conservative; the obvious
         * condition is (m.size() + size) >= threshold, but this condition could
         * result in a map with twice the appropriate capacity, if the keys to
         * be added overlap with the keys already in this map. By using the
         * conservative calculation, we subject ourself to at most one extra
         * resize.
         */
        if (numKeysToBeAdded > threshold) {
            int targetCapacity = (int) (numKeysToBeAdded / loadFactor + 1);
            if (targetCapacity > MAXIMUM_CAPACITY)
                targetCapacity = MAXIMUM_CAPACITY;
            int newCapacity = table.length;
            while (newCapacity < targetCapacity)
                newCapacity <<= 1;
            if (newCapacity > table.length)
                resize(newCapacity);
        }

        for (Iterator<? extends Map.Entry<? extends K, ? extends V>> i = m.entrySet().iterator(); i
                .hasNext();) {
            Map.Entry<? extends K, ? extends V> e = i.next();
            put(e.getKey(), e.getValue());
        }
    }

    /**
     * Removes the mapping for this key from this map if present.
     * 
     * @param key
     *            key whose mapping is to be removed from the map.
     * @return previous value associated with specified key, or <tt>null</tt>
     *         if there was no mapping for key. A <tt>null</tt> return can
     *         also indicate that the map previously associated <tt>null</tt>
     *         with the specified key.
     */
    public V remove(Object key) {
        Entry<K, V> e = removeEntryForKey(key);
        return (e == null ? null : e.value);
    }

    /**
     * Removes and returns the entry associated with the specified key in the
     * CustomHashMap5. Returns null if the CustomHashMap5 contains no mapping
     * for this key.
     */
    Entry<K, V> removeEntryForKey(Object key) {
        return null;
        // ???
        // Object k = maskNull(key);
        // int hash = hash(k.hashCode());
        // int i = indexFor(hash, table.length);
        // Entry<K, V> prev = table[i];
        // Entry<K, V> e = prev;
        //
        // while (e != null) {
        // Entry<K, V> next = e.next;
        // if (e.hash == hash && eq(k, e.key)) {
        // modCount++;
        // size--;
        // if (prev == e)
        // table[i] = next;
        // else
        // prev.next = next;
        // e.recordRemoval(this);
        // return e;
        // }
        // prev = e;
        // e = next;
        // }
        //
        // return e;
    }

    /**
     * Special version of remove for EntrySet.
     */
    Entry<K, V> removeMapping(Object o) {
        if (!(o instanceof Map.Entry))
            return null;

        Map.Entry<K, V> entry = (Map.Entry<K, V>) o;
        Object k = maskNull(entry.getKey());
        int hash = hash(k.hashCode());

        int i = indexFor(hash, virtualLength);
        int arrayPos = i & fragmentActualSizeForMod;
        i = i >> FRAGMENT_SIZE_LOG2;
        Entry[] array = table[i];
        if (array == null) {
            return null;
        }

        Entry<K, V> prev = array[arrayPos];
        Entry<K, V> e = prev;

        // ???
        // while (e != null) {
        // Entry<K, V> next = e.next;
        // if (e.hash == hash && e.equals(entry)) {
        // modCount++;
        // size--;
        // if (prev == e)
        // table[i] = next;
        // else
        // prev.next = next;
        // e.recordRemoval(this);
        // return e;
        // }
        // prev = e;
        // e = next;
        // }

        return e;
    }

    /**
     * Removes all mappings from this map.
     */
    public void clear() {
        // ???
        // modCount++;
        // Entry[] tab = table;
        // for (int i = 0; i < tab.length; i++)
        // tab[i] = null;
        // size = 0;
    }

    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value.
     * 
     * @param value
     *            value whose presence in this map is to be tested.
     * @return <tt>true</tt> if this map maps one or more keys to the
     *         specified value.
     */
    public boolean containsValue(Object value) {
        // ???
        // if (value == null)
        // return containsNullValue();
        //
        // Entry[] tab = table;
        // for (int i = 0; i < tab.length; i++)
        // for (Entry e = tab[i]; e != null; e = e.next)
        // if (value.equals(e.value))
        // return true;
        return false;
    }

    /**
     * Special-case code for containsValue with null argument
     */
    private boolean containsNullValue() {
        // ???
        // Entry[] tab = table;
        // for (int i = 0; i < tab.length; i++)
        // for (Entry e = tab[i]; e != null; e = e.next)
        // if (e.value == null)
        // return true;
        return false;
    }

    /**
     * Returns a shallow copy of this <tt>CustomHashMap5</tt> instance: the
     * keys and values themselves are not cloned.
     * 
     * @return a shallow copy of this map.
     */
    public Object clone() {
        CustomHashMap5<K, V> result = null;
        try {
            result = (CustomHashMap5<K, V>) super.clone();
        }
        catch (CloneNotSupportedException e) {
            // assert false;
        }
        // ???
        // result.table = new Entry[table.length];
        result.entrySet = null;
        result.modCount = 0;
        result.size = 0;
        result.init();
        result.putAllForCreate(this);

        return result;
    }

    static class Entry<K, V> implements Map.Entry<K, V> {
        final K key;

        V value;

        final int hash;

        Entry<K, V> next;

        /**
         * Create new entry.
         */
        Entry(int h, K k, V v, Entry<K, V> n) {
            value = v;
            next = n;
            key = k;
            hash = h;
        }

        public K getKey() {
            return CustomHashMap5.<K> unmaskNull(key);
        }

        public V getValue() {
            return value;
        }

        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry e = (Map.Entry) o;
            Object k1 = getKey();
            Object k2 = e.getKey();
            if (k1 == k2 || (k1 != null && k1.equals(k2))) {
                Object v1 = getValue();
                Object v2 = e.getValue();
                if (v1 == v2 || (v1 != null && v1.equals(v2)))
                    return true;
            }
            return false;
        }

        public int hashCode() {
            return (key == NULL_KEY ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
        }

        public String toString() {
            return getKey() + "=" + getValue();
        }

        /**
         * This method is invoked whenever the value in an entry is overwritten
         * by an invocation of put(k,v) for a key k that's already in the
         * CustomHashMap5.
         */
        void recordAccess(CustomHashMap5<K, V> m) {
        }

        /**
         * This method is invoked whenever the entry is removed from the table.
         */
        void recordRemoval(CustomHashMap5<K, V> m) {
        }
    }

    /**
     * Add a new entry with the specified key, value and hash code to the
     * specified bucket. It is the responsibility of this method to resize the
     * table if appropriate. Subclass overrides this to alter the behavior of
     * put method.
     */
    void addEntry(int hash, K key, V value, Entry[] array, int bucketIndex) {
        Entry<K, V> e = array[bucketIndex];
        array[bucketIndex] = new Entry<K, V>(hash, key, value, e);
        if (size++ >= threshold) {
            resize(2 * virtualLength);
        }
    }

    /**
     * Like addEntry except that this version is used when creating entries as
     * part of Map construction or "pseudo-construction" (cloning,
     * deserialization). This version needn't worry about resizing the table.
     * Subclass overrides this to alter the behavior of CustomHashMap5(Map),
     * clone, and readObject.
     */
    void createEntry(int hash, K key, V value, Entry[] array, int bucketIndex) {
        Entry<K, V> e = array[bucketIndex];
        array[bucketIndex] = new Entry<K, V>(hash, key, value, e);
        size++;
    }

    private abstract class HashIterator<E> implements Iterator<E> {
        Entry<K, V> next; // next entry to return

        int expectedModCount; // For fast-fail

        int index; // current slot

        Entry<K, V> current; // current entry

        HashIterator() {
            // ???
            // expectedModCount = modCount;
            // Entry[] t = table;
            // int i = t.length;
            // Entry<K, V> n = null;
            // if (size != 0) { // advance to first entry
            // while (i > 0 && (n = t[--i]) == null)
            // ;
            // }
            // next = n;
            // index = i;
        }

        public boolean hasNext() {
            return next != null;
        }

        Entry<K, V> nextEntry() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            Entry<K, V> e = next;
            // ???
            // if (e == null)
            // throw new NoSuchElementException();
            //
            // Entry<K, V> n = e.next;
            // Entry[] t = table;
            // int i = index;
            // while (n == null && i > 0)
            // n = t[--i];
            // index = i;
            // next = n;
            return current = e;
        }

        public void remove() {
            if (current == null)
                throw new IllegalStateException();
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            Object k = current.key;
            current = null;
            CustomHashMap5.this.removeEntryForKey(k);
            expectedModCount = modCount;
        }

    }

    private class ValueIterator extends HashIterator<V> {
        public V next() {
            return nextEntry().value;
        }
    }

    private class KeyIterator extends HashIterator<K> {
        public K next() {
            return nextEntry().getKey();
        }
    }

    private class EntryIterator extends HashIterator<Map.Entry<K, V>> {
        public Map.Entry<K, V> next() {
            return nextEntry();
        }
    }

    // Subclass overrides these to alter behavior of views' iterator() method
    Iterator<K> newKeyIterator() {
        return new KeyIterator();
    }

    Iterator<V> newValueIterator() {
        return new ValueIterator();
    }

    Iterator<Map.Entry<K, V>> newEntryIterator() {
        return new EntryIterator();
    }

    // Views

    private transient Set<Map.Entry<K, V>> entrySet = null;

    /**
     * Returns a set view of the keys contained in this map. The set is backed
     * by the map, so changes to the map are reflected in the set, and
     * vice-versa. The set supports element removal, which removes the
     * corresponding mapping from this map, via the <tt>Iterator.remove</tt>,
     * <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt>, and
     * <tt>clear</tt> operations. It does not support the <tt>add</tt> or
     * <tt>addAll</tt> operations.
     * 
     * @return a set view of the keys contained in this map.
     */
    public Set<K> keySet() {
        // ???
        // Set<K> ks = keySet;
        // return (ks != null ? ks : (keySet = new KeySet()));

        return null;
    }

    private class KeySet extends AbstractSet<K> {
        public Iterator<K> iterator() {
            return newKeyIterator();
        }

        public int size() {
            return size;
        }

        public boolean contains(Object o) {
            return containsKey(o);
        }

        public boolean remove(Object o) {
            return CustomHashMap5.this.removeEntryForKey(o) != null;
        }

        public void clear() {
            CustomHashMap5.this.clear();
        }
    }

    /**
     * Returns a collection view of the values contained in this map. The
     * collection is backed by the map, so changes to the map are reflected in
     * the collection, and vice-versa. The collection supports element removal,
     * which removes the corresponding mapping from this map, via the
     * <tt>Iterator.remove</tt>, <tt>Collection.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
     * operations. It does not support the <tt>add</tt> or <tt>addAll</tt>
     * operations.
     * 
     * @return a collection view of the values contained in this map.
     */
    public Collection<V> values() {
        // ???
        // Collection<V> vs = values;
        // return (vs != null ? vs : (values = new Values()));

        return null;
    }

    private class Values extends AbstractCollection<V> {
        public Iterator<V> iterator() {
            return newValueIterator();
        }

        public int size() {
            return size;
        }

        public boolean contains(Object o) {
            return containsValue(o);
        }

        public void clear() {
            CustomHashMap5.this.clear();
        }
    }

    /**
     * Returns a collection view of the mappings contained in this map. Each
     * element in the returned collection is a <tt>Map.Entry</tt>. The
     * collection is backed by the map, so changes to the map are reflected in
     * the collection, and vice-versa. The collection supports element removal,
     * which removes the corresponding mapping from the map, via the
     * <tt>Iterator.remove</tt>, <tt>Collection.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
     * operations. It does not support the <tt>add</tt> or <tt>addAll</tt>
     * operations.
     * 
     * @return a collection view of the mappings contained in this map.
     * @see Map.Entry
     */
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> es = entrySet;
        return (es != null ? es : (entrySet = (Set<Map.Entry<K, V>>) (Set) new EntrySet()));
    }

    private class EntrySet extends AbstractSet/* <Map.Entry<K,V>> */{
        public Iterator/* <Map.Entry<K,V>> */iterator() {
            return newEntryIterator();
        }

        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<K, V> e = (Map.Entry<K, V>) o;
            Entry<K, V> candidate = getEntry(e.getKey());
            return candidate != null && candidate.equals(e);
        }

        public boolean remove(Object o) {
            return removeMapping(o) != null;
        }

        public int size() {
            return size;
        }

        public void clear() {
            CustomHashMap5.this.clear();
        }
    }

    /**
     * Save the state of the <tt>CustomHashMap5</tt> instance to a stream
     * (i.e., serialize it).
     * 
     * @serialData The <i>capacity</i> of the CustomHashMap5 (the length of the
     *             bucket array) is emitted (int), followed by the <i>size</i>
     *             of the CustomHashMap5 (the number of key-value mappings),
     *             followed by the key (Object) and value (Object) for each
     *             key-value mapping represented by the CustomHashMap5 The
     *             key-value mappings are emitted in the order that they are
     *             returned by <tt>entrySet().iterator()</tt>.
     */
    private void writeObject(java.io.ObjectOutputStream s) throws IOException {
        Iterator<Map.Entry<K, V>> i = entrySet().iterator();

        // Write out the threshold, loadfactor, and any hidden stuff
        s.defaultWriteObject();

        // Write out number of buckets
        s.writeInt(table.length);

        // Write out size (number of Mappings)
        s.writeInt(size);

        // Write out keys and values (alternating)
        while (i.hasNext()) {
            Map.Entry<K, V> e = i.next();
            s.writeObject(e.getKey());
            s.writeObject(e.getValue());
        }
    }

    private static final long serialVersionUID = 362498820763181265L;

    // ???
    // /**
    // * Reconstitute the <tt>CustomHashMap5</tt> instance from a stream (i.e.,
    // * deserialize it).
    // */
    // private void readObject(java.io.ObjectInputStream s) throws IOException,
    // ClassNotFoundException {
    // // Read in the threshold, loadfactor, and any hidden stuff
    // s.defaultReadObject();
    //
    // // Read in number of buckets and allocate the bucket array;
    // int numBuckets = s.readInt();
    // table = new Entry[numBuckets];
    //
    // init(); // Give subclass a chance to do its thing.
    //
    // // Read in size (number of Mappings)
    // int size = s.readInt();
    //
    // // Read the keys and values, and put the mappings in the CustomHashMap5
    // for (int i = 0; i < size; i++) {
    // K key = (K) s.readObject();
    // V value = (V) s.readObject();
    // putForCreate(key, value);
    // }
    // }

    // These methods are used when serializing HashSets
    int capacity() {
        return table.length;
    }

    float loadFactor() {
        return loadFactor;
    }
}
