package com.tibco.be.util;

/**
 * Similar to a StringBuffer or a List, but for dealing with byte arrays. Useful
 * for dealing with streams that collect data but the actual size of the stream
 * is not known. Use of this object is no different than trying to manage growable
 * byte buffers by hand.
 */
public class ByteArray
{
    private byte[] data;
    private int size = 0;

    /**
     * Creates a ByteArray with an initial capacity of 128 bytes.
     */
    public ByteArray()
    {
        this(128);
    }

    /**
     * Creates a ByteArray with the specified initial capacity
     * @param size
     */
    public ByteArray(int size)
    {
        data = new byte[size];
    }

    /**
     * Creates a ByteArray with the same contents as the provided array.
     * @param a
     */
    public ByteArray(byte[] a)
    {
        int count = a.length;
        data = new byte[count];
        System.arraycopy(a, 0, data, 0, count);
    }

    /**
     * Adds a byte
     * @param b
     */
    public void add(byte b)
    {
        ensureCapacity(size + 1);
        data[size++] = b;
    }

    /**
     * Adds all bytes in the buffer
     * @param buf
     */
    public void add(byte[] buf)
    {
        add(buf, buf.length);
    }

    /**
     * Adds the specified number of bytes to the ByteArray from the buffer provided
     * @param buf
     * @param count
     */
    public void add(byte[] buf, int count)
    {
        add(buf,0,count);
    }

    /**
     * Adds the specied range of bytes to the ByteArray
     * @param buf
     * @param start
     * @param count
     */
    public void add(byte[] buf, int start, int count)
    {
        ensureCapacity(size + count);
        System.arraycopy(buf, start, data, size, count);
        size += count;
    }

    /**
     * Removes the byte at the especified index and returns the removed element
     * @param index
     * @return  The removed element
     */
    public byte remove(int index)
    {
        byte oldValue = data[index];

        int numMoved = size - index - 1;
        if (numMoved > 0)
        {
            System.arraycopy(data, index + 1, data, index, numMoved);
        }
        return oldValue;
    }

    /**
     * Sets the value of the specified index to b
     * @param index
     * @param b
     * @return old value.
     */
    public byte set(int index, byte b)
    {
        byte oldValue = data[index];
        data[index] = b;
        return oldValue;
    }

    /**
     * Sets the index for the next write to zero.
     */
    public void reset()
    {
        size = 0;
    }

    /**
     * Returns the number of bytes in the ByteArray
     * @return the number of bytes in the ByteArray
     */
    public int size()
    {
        return size;
    }

    /**
     * Returns the maximun number of bytes that this buffer can accomodate without resizing.
     * @return The maximun number of bytes that this buffer can accomodate without resizing
     */
    public int capacity()
    {
        return data.length;
    }

    /**
     * Returns the contents as a byte array
     * @return The contents as a byte array.
     */
    public byte[] getValue()
    {
        byte[] retVal = new byte[size];
        System.arraycopy(data, 0, retVal, 0, size);

        return retVal;
    }

    /**
     * Shrinks the ByteArray to accomodate data currently in the buffer
     */
    public void trimToSize()
    {
        if (data.length != size)
            data = getValue();
    }

    private void ensureCapacity(int minCapacity)
    {
        int oldCapacity = data.length;
        if (minCapacity > oldCapacity)
        {
            byte oldData[] = data;
            int newCapacity = (oldCapacity * 2) + 1;
            if (newCapacity < minCapacity)
            {
                newCapacity = minCapacity;
            }
            data = new byte[newCapacity];
            System.arraycopy(oldData, 0, data, 0, size);
        }
    }
}