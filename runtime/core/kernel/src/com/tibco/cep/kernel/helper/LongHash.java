package com.tibco.cep.kernel.helper;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 7, 2006
 * Time: 3:24:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class LongHash {
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final int MAXIMUM_CAPACITY = 1 << 30;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    transient int   threshold;
    transient float loadFactor;
    transient int   size;

    Entry[] table;

    public LongHash(int initialCapacity, float _loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (_loadFactor <= 0 || Float.isNaN(_loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " + _loadFactor);

        // Find a power of 2 >= initialCapacity
        int capacity = 1;
        while (capacity < initialCapacity)
            capacity <<= 1;

        table      = new Entry[capacity];
        loadFactor = _loadFactor;
        threshold  = (int)(capacity * _loadFactor);
        size       = 0;
    }

    public LongHash(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    public LongHash() {
        loadFactor = DEFAULT_LOAD_FACTOR;
        threshold  = (int)(DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
        size       = 0;
        table      = new Entry[DEFAULT_INITIAL_CAPACITY];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public long[] get(long key) {
        int i = indexFor(key, table.length);
        Entry e = table[i];
        while (e != null) {
            if (e.key == key)
                return e.value;
            e = e.next;
        }
        return null;
    }

    public long[] remove(long key)  {
        int i = indexFor(key, table.length);
        Entry prev = table[i];
        Entry e = prev;

        while (e != null) {
            Entry next = e.next;
            if (e.key == key) {
                size--;
                if (prev == e)
                    table[i] = next;
                else
                    prev.next = next;
                return e.value;
            }
            prev = e;
            e = next;
        }
        return null;
    }

    public long[] add(long key, long value) {
        int i = indexFor(key, table.length);
        Entry e = table[i];
        while (e != null) {
            if (e.key == key) {
                e.add(value);
                return e.value;
            }
            e = e.next;
        }
        e = new Entry(table[i], key, value);
        table[i] = e;
        if (size++ >= threshold)
            resize(2 * table.length);
        return e.value;
    }

    public void createKey(long key) {
        int i = indexFor(key, table.length);
        Entry e = table[i];
        while (e != null) {
            if (e.key == key) {
                return;
            }
            e = e.next;
        }
        table[i] = new Entry(table[i], key);
        if (size++ >= threshold)
            resize(2 * table.length);
    }

    public boolean addIfKeyExist(long key, long value) {
        int i = indexFor(key, table.length);
        Entry e = table[i];
        while (e != null) {
            if (e.key == key) {
                e.add(value);
                return true;
            }
            e = e.next;
        }
        return false;
    }

    //return true if the value is really removed
    public boolean remove(long key, long value) {
        int i = indexFor(key, table.length);
        Entry prev = table[i];
        Entry e = prev;

        while (e != null) {
            Entry next = e.next;
            if (e.key == key) {
                if(!e.remove(value)) return false;
                if(e.value.length == 0) {
                    size--;
                    if (prev == e)
                        table[i] = next;
                    else
                        prev.next = next;
                }
                return true;
            }
            prev = e;
            e = next;
        }
        return false;
    }

    public boolean removeIfKeyExist(long key, long value) {
        int i = indexFor(key, table.length);
        Entry prev = table[i];
        Entry e = prev;

        while (e != null) {
            Entry next = e.next;
            if (e.key == key) {
                return e.remove(value);
            }
            prev = e;
            e = next;
        }
        return false;
    }

    public boolean updateIfKeyExit(long key, long oldValue, long newValue) {
        int i = indexFor(key, table.length);
        Entry e = table[i];
        while (e != null) {
            if (e.key == key) {
                if(!e.replace(oldValue, newValue)) {
                    e.add(newValue);
                }
                return true;
            }
            e = e.next;
        }
        return false;
    }

    public void resize(int newCapacity) {
        Entry[] oldTable = table;
        int oldCapacity = oldTable.length;
        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }
        Entry[] newTable = new Entry[newCapacity];
        for (int j = 0; j < table.length; j++) {
            Entry e = table[j];
            if (e != null) {
                table[j] = null;
                do {
                    Entry next = e.next;
                    int i = indexFor(e.key, newCapacity);
                    e.next = newTable[i];
                    newTable[i] = e;
                    e = next;
                } while (e != null);
            }
        }
        table = newTable;
        threshold = (int)(newCapacity * loadFactor);
    }

    int indexFor(long key, int length) {
        return ((int)(key ^ (key >>> 32))) & (length-1);
    }

    public String toString() {
        String ret = new String("lenght=" + table.length + " size=" + size + Format.BRK);
        for(int i = 0; i < table.length; i++) {
            if(table[i] != null) {
                Entry e = table[i];
                ret += "\t\tTable[" + i + "]: ";
                while(e != null) {
                    ret += "(Key=" + e.key + " Value=";
                    for(int j = 0; j < e.value.length; j++) {
                        ret += e.value[j];
                        if(j != (e.value.length -1)) {
                            ret += ",";
                        }
                    }
                    ret += ") ";
                    e = e.next;
                }
                ret += Format.BRK;
            }
        }
        return ret;
    }

    class Entry {
        public Entry next;
        public long  key;
        public long[] value;

        Entry(Entry _next, long _key, long _value) {
            key      = _key;
            value    = new long[1];
            value[0] = _value;
            next     = _next;
        }

        Entry(Entry _next, long _key, long[] _value) {
            key      = _key;
            value    = new long[_value.length];
            System.arraycopy(_value, 0, value, 0, _value.length);
            value = _value;
            next     = _next;
        }

        Entry(Entry _next, long _key) {
            key      = _key;
            value    = new long[0];
            next     = _next;
        }

        Entry(long _key, long _value) {
            key      = _key;
            value    = new long[1];
            value[0] = _value;
        }

        void add(long[] _value) {
            long[] newArr = new long[value.length + _value.length];
            System.arraycopy(value, 0, newArr, 0, value.length);
            System.arraycopy(_value, 0, newArr, value.length, _value.length);
            value = newArr;
        }

        void add(long _value) {
            long[] newArr = new long[value.length + 1];
            System.arraycopy(value, 0, newArr, 0, value.length);
            newArr[value.length] = _value;
            value = newArr;
        }

        boolean remove(long _value) {
            long newArr[] = new long[value.length -1];
            boolean found = false;

            for(int i = value.length -1; i >= 0; i--) {
                if(!found) {
                    if(value[i] == _value) {
                        found = true;
                    }
                    else if(i == 0) {
                        return false;
                    }
                    else {
                        newArr[i-1] = value[i];
                    }
                }
                else {
                    newArr[i] = value[i];
                }
            }
            value = newArr;
            return true;
        }

        boolean replace(long oldValue, long newValue) {
            for(int i = 0; i < value.length; i++) {
                if(value[i] == oldValue) {
                    value[i] = newValue;
                    return true;
                }
            }
            return false;
        }
    }
}
